package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.dto.AddToCartRequestModel;
import com.es.phoneshop.web.controller.dto.AddToCartResponseModel;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("ajaxController")
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AddToCartResponseModel addPhone(
            @Validated @RequestBody AddToCartRequestModel requestModel) {
        AddToCartResponseModel responseModel = new AddToCartResponseModel();
        cartService.addPhone(requestModel.getProductId(), requestModel.getQuantity());
        responseModel.setSuccess(true);
        responseModel.setCartQuantity(cartService.getCart().getTotalItemsCount());
        responseModel.setCartSubtotal(cartService.getCart().getTotalPrice().doubleValue());
        return responseModel;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
//    public AddToCartResponseModel handleValidationExceptions() {
//        AddToCartResponseModel responseModel = new AddToCartResponseModel();
//        responseModel.setSuccess(false);
//        responseModel.setMessage("Wrong format");
//        return responseModel;
//    }
}
