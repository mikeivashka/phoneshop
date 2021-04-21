package com.es.core.model.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpSessionCartService implements CartService {

    private final Cart cart;

    private final PhoneDao phoneDao;

    public HttpSessionCartService(Cart cart, PhoneDao phoneDao) {
        this.cart = cart;
        this.phoneDao = phoneDao;
    }

    @Override
    public Map<Phone, Integer> getCart() {
        return cart.getCartItems().stream()
                .collect(Collectors.toMap(CartItem::getPhone, CartItem::getQuantity, Integer::sum));
    }

    @Override
    public Set<CartItem> getCartItems() {
        return cart.getCartItems();
    }

    @Override
    public void addPhone(Long phoneId, Integer quantity) {
        Phone phoneToAdd = phoneDao.get(phoneId).orElseThrow(IllegalArgumentException::new);
        Optional<CartItem> cartItemOptional = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getPhone().getId().equals(phoneToAdd.getId()))
                .findFirst();
        if (cartItemOptional.isPresent()) {
            cartItemOptional.get().setQuantity(cartItemOptional.get().getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(phoneToAdd, quantity));
        }
    }

    @Override
    public void update(Map<Long, Integer> items) {
        Set<CartItem> cartItemsToBeSet = items.entrySet()
                .stream()
                .map(e -> new CartItem(phoneDao.get(e.getKey()).orElseThrow(IllegalArgumentException::new), e.getValue()))
                .collect(Collectors.toSet());
        cart.setCartItems(cartItemsToBeSet);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(item -> item.getPhone().getId().equals(phoneId));
    }

    @Override
    public BigDecimal getTotalPrice() {
        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Integer getTotalItemsCount() {
        return cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Override
    public void clearCart() {
        cart.getCartItems().clear();
    }
}
