package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @GetMapping
    public void getCart() {
        cartService.getCart();
    }

    @GetMapping(value = "/minicart")
    public String getMinicart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "miniCart";
    }

    @PutMapping
    public void updateCart() {
        cartService.update(null);
    }


}
