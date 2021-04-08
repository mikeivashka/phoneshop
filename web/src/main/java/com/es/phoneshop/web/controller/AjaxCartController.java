package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @PostMapping
    public void addPhone(Long phoneId, Long quantity) {
        cartService.addPhone(phoneId, quantity);
    }
}
