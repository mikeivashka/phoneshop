package com.es.core.cart;

import com.es.core.model.phone.CartItem;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private Set<CartItem> cartItems;

    public Cart() {
        this.cartItems = new LinkedHashSet<>();
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
