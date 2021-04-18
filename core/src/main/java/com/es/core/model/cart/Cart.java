package com.es.core.model.cart;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component("cart")
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private Set<CartItem> cartItems;

    public Cart() {
        this.cartItems = new HashSet<>();
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
