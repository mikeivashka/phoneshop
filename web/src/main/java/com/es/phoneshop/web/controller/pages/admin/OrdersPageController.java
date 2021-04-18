package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderService;
import com.es.core.model.order.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderService orderService;

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "ordersAdmin";
    }

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "orderDetailsAdminPage";
    }

    @PostMapping("/{orderId}")
    public String setStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        orderService.updateStatus(orderService.getOrderById(orderId), status);
        return "redirect:" + orderId;
    }
}
