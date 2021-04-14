package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.dto.AddToCartRequest;
import com.es.phoneshop.web.controller.dto.AddToCartResponse;
import com.es.phoneshop.web.controller.dto.DeleteFromCartRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController("ajaxController")
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    private static final String ADD_TO_CART_SUCCESS_MESSAGE = "Successfully added to cart";
    @Resource
    private CartService cartService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AddToCartResponse addPhone(
            @Valid @RequestBody AddToCartRequest requestModel, BindingResult bindingResult) {
        AddToCartResponse responseModel = new AddToCartResponse();
        responseModel.setSuccess(!bindingResult.hasErrors());
        if (!bindingResult.hasErrors()) {
            cartService.addPhone(requestModel.getProductId(), Long.valueOf(requestModel.getQuantity()));
            responseModel.setMessage(ADD_TO_CART_SUCCESS_MESSAGE);
        } else {
            responseModel.setMessage(bindingResult.getFieldErrors("quantity")
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", ")));
        }
        responseModel.setCartQuantity(cartService.getTotalItemsCount());
        responseModel.setCartSubtotal(cartService.getTotalPrice().doubleValue());
        return responseModel;
    }

    @DeleteMapping
    public void deletePhone(@RequestBody DeleteFromCartRequest request) {
        cartService.remove(request.getProductId());
    }
}
