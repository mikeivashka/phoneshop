package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderService;
import com.es.phoneshop.web.controller.dto.CreateOrderForm;
import com.es.phoneshop.web.controller.dto.OrderFormItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes("orderForm")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @ModelAttribute("orderForm")
    public CreateOrderForm populateOrderForm() {
        return buildOrderForm(orderService.createOrderFromCart());
    }

    @GetMapping
    public String getOrder(Model model) {
        if (cartService.getCart().isEmpty()) {
            return "redirect:cart";
        }
        model.addAttribute("order", orderService.createOrderFromCart());
        return "orderPage";
    }

    @PostMapping
    public String placeOrder(@ModelAttribute("orderForm") @Valid CreateOrderForm orderForm,
                             BindingResult bindingResult,
                             Model model,
                             SessionStatus sessionStatus) {
        Order order = orderService.createOrderFromCart();
        if (!bindingResult.hasErrors()) {
            addFormDataToOrder(order, orderForm);
            orderService.placeOrder(order);
            sessionStatus.setComplete();
            return "redirect:/orderOverview/" + order.getId();
        } else {
            List<FieldError> outOfStockErrors = bindingResult.getFieldErrors("orderItems[*");
            if (!outOfStockErrors.isEmpty()) {
                handleOutOfStockErrors(outOfStockErrors);
                order = orderService.createOrderFromCart();
            }
            copyOrderItemsToForm(order, orderForm);
            model.addAttribute("order", order);
            return "orderPage";
        }
    }

    private CreateOrderForm buildOrderForm(Order order) {
        CreateOrderForm form = new CreateOrderForm();
        BeanUtils.copyProperties(order, form, "orderItems");
        copyOrderItemsToForm(order, form);
        return form;
    }

    private void copyOrderItemsToForm(Order order, CreateOrderForm form) {
        form.setOrderItems(order.getOrderItems().stream()
                .map(item -> new OrderFormItem(item.getPhone(), item.getQuantity()))
                .collect(Collectors.toList())
        );
    }

    private void handleOutOfStockErrors(List<FieldError> outOfStockErrors) {
        outOfStockErrors.stream()
                .map(e -> (OrderFormItem) e.getRejectedValue())
                .forEach(rejectedItem -> cartService.remove(rejectedItem.getPhone().getId()));
    }

    private void addFormDataToOrder(Order order, CreateOrderForm orderForm) {
        BeanUtils.copyProperties(orderForm, order, "orderItems");
        order.setOrderItems(orderForm.getOrderItems().stream()
                .map(formOrderItem -> new OrderItem(formOrderItem.getPhone(), formOrderItem.getQuantity(), order))
                .collect(Collectors.toList()));
    }
}
