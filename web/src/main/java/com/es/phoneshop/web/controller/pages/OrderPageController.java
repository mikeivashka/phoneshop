package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartCalculationService;
import com.es.core.model.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderService;
import com.es.core.model.cart.CartItem;
import com.es.phoneshop.web.controller.dto.CreateOrderForm;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes("orderForm")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartCalculationService cartCalculationService;

    @Resource
    private CartService cartService;

    @ModelAttribute("orderForm")
    public CreateOrderForm populate() {
        return buildOrderForm();
    }

    @GetMapping
    public String getOrder(Model model) {
        if (cartService.getCart().isEmpty()) {
            return "redirect:cart";
        }
        model.addAttribute("orderForm", buildOrderForm());
        return "orderPage";
    }

    @PostMapping
    public String placeOrder(@ModelAttribute("orderForm") @Valid CreateOrderForm orderForm, BindingResult bindingResult, Model model, SessionStatus sessionStatus) {
        if (!bindingResult.hasErrors()) {
            Order order = parseOrderForm(orderForm);
            orderService.placeOrder(order);
            sessionStatus.setComplete();
            cartService.clearCart();
            return "redirect:/orderOverview/" + order.getId();
        } else {
            List<FieldError> outOfStockErrors = bindingResult.getFieldErrors("orderItems[*");
            if (!outOfStockErrors.isEmpty()) {
                handleOutOfStockErrors(outOfStockErrors, orderForm);
            }
            return "orderPage";
        }
    }

    private CreateOrderForm buildOrderForm() {
        CreateOrderForm orderForm = new CreateOrderForm();
        orderForm.setOrderItems(new ArrayList<>(cartService.getCartItems()));
        recalculate(orderForm);
        return orderForm;
    }

    private void recalculate(CreateOrderForm orderForm) {
        orderForm.setSubtotal(cartCalculationService.calculateCartTotal(orderForm.getOrderItems()));
        orderForm.setDeliveryPrice(orderService.getDeliveryPrice());
        orderForm.setTotalPrice(orderForm.getSubtotal().add(orderForm.getDeliveryPrice()));
    }

    private void handleOutOfStockErrors(List<FieldError> outOfStockErrors, CreateOrderForm orderForm) {
        outOfStockErrors.stream()
                .map(e -> (CartItem) e.getRejectedValue())
                .forEach(rejectedItem -> {
                    cartService.remove(rejectedItem.getPhone().getId());
                    orderForm.getOrderItems().remove(rejectedItem);
                });
        recalculate(orderForm);
    }

    private Order parseOrderForm(CreateOrderForm orderForm) {
        Order order = new Order();
        BeanUtils.copyProperties(orderForm, order, "orderItems");
        order.setOrderItems(orderForm.getOrderItems().stream()
                .map(cartItem -> new OrderItem(cartItem, order))
                .collect(Collectors.toList()));
        return order;
    }
}
