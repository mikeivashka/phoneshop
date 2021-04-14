package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    OrderService orderService;

    @GetMapping("/{orderId}")
    public String getOverview(@PathVariable Long orderId, Model model){
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "orderOverview";
    }
}
