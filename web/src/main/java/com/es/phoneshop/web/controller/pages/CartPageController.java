package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.controller.dto.CartEntryUpdateForm;
import com.es.phoneshop.web.controller.dto.CartUpdateForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        Map<Phone, Long> cart = cartService.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("cartUpdateForm", extractUpdateFormFromCart(cart));
        return "cartPage";
    }

    @PutMapping
    public String updateCart(@ModelAttribute @Valid CartUpdateForm form, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            cartService.update(parseUpdateForm(form));
            return "redirect:cart";
        } else {
            model.addAttribute("cart", cartService.getCart());
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

    private CartUpdateForm extractUpdateFormFromCart(Map<Phone, Long> cart) {
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setCartEntries(cart.entrySet().stream().map(entry -> {
                    CartEntryUpdateForm formEntry = new CartEntryUpdateForm();
                    formEntry.setProductId(entry.getKey().getId());
                    formEntry.setQuantity(entry.getValue().toString());
                    return formEntry;
                }).collect(Collectors.toList())
        );
        return cartUpdateForm;
    }

    private Map<Long, Long> parseUpdateForm(CartUpdateForm form) {
        return form.getCartEntries()
                .stream()
                .collect(Collectors.toMap(CartEntryUpdateForm::getProductId, entry -> Long.valueOf(entry.getQuantity())));
    }
}
