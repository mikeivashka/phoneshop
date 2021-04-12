package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.controller.dto.CartUpdateForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        Map<Phone, Long> cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartPage";
    }

    @PutMapping
    public String updateCart(@ModelAttribute CartUpdateForm form, HttpServletRequest req, Model model) {
        NumberFormat numberFormat = NumberFormat.getIntegerInstance(req.getLocale());
        Map<Long, String> errors = new HashMap<>();
        Map<Long, Long> updatedCart = new HashMap<>();
        long quantity;
        for (int i = 0; i < form.getProductIds().size(); i++) {
            try {
                quantity = numberFormat.parse(form.getQuantities().get(i)).longValue();
                if (quantity < 1L) {
                    throw new IllegalArgumentException();
                }
                updatedCart.put(form.getProductIds().get(i), Long.valueOf(form.getQuantities().get(i)));
            } catch (IllegalArgumentException | ParseException e) {
                errors.put(form.getProductIds().get(i), "Enter a positive number");
            }
        }
        if (errors.isEmpty()) {
            cartService.update(updatedCart);
            return "redirect:cart";
        } else {
            model.addAttribute("cart", cartService.getCart());
            model.addAttribute("errors", errors);
            return "cartPage";
        }
    }

    @RequestMapping(value = "/minicart",
            method = {RequestMethod.GET, RequestMethod.PUT})
    public String getMinicart(Model model) {
        model.addAttribute("cartTotalPrice", cartService.getTotalPrice());
        model.addAttribute("cartTotalItemsCount", cartService.getTotalItemsCount());
        return "miniCart";
    }
}
