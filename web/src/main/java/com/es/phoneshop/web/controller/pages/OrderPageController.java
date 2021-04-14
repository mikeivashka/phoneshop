package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes("order")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @ModelAttribute("order")
    public Order populateOrder() {
        return new Order();
    }

    @GetMapping
    public String getOrder(Model model) {
        Map<Phone, Integer> cart = cartService.getCart();
        if (cart.isEmpty()) {
            return "redirect:cart";
        }
        model.addAttribute("order", orderService.createOrder(cart));
        return "orderPage";
    }

    @PostMapping
    public String placeOrder(@ModelAttribute @Valid Order order, BindingResult bindingResult, Model model, SessionStatus sessionStatus) {
        if (!bindingResult.hasErrors()) {
            sessionStatus.setComplete();
            orderService.placeOrder(order);
            return "redirect:/orderOverview/" + order.getId();
        } else {
            List<FieldError> outOfStockErrors = bindingResult.getFieldErrors("orderItems[*");
            if (!outOfStockErrors.isEmpty()) {
                handleOutOfStockErrors(outOfStockErrors, order);
            }
            model.addAttribute("order", order);
            return "orderPage";
        }
    }

    private void handleOutOfStockErrors(List<FieldError> outOfStockErrors, Order order) {
        outOfStockErrors.stream()
                .map(e -> (OrderItem) e.getRejectedValue())
                .forEach(rejectedItem -> {
                    cartService.remove(rejectedItem.getPhone().getId());
                    order.getOrderItems().remove(rejectedItem);
                });
        orderService.recalculatePrices(order);
    }
}
