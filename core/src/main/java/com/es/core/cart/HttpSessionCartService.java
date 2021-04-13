package com.es.core.cart;

import com.es.core.model.phone.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public Map<Phone, Long> getCart() {
        return cart.getCartItems().stream()
                .collect(Collectors.toMap(CartItem::getPhone, CartItem::getQuantity, (x, y) -> y, LinkedHashMap<Phone, Long>::new));
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
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
    public void update(Map<Long, Long> items) {
        Set<CartItem> cartItemsToBeSet = items.entrySet()
                .stream()
                .map(e -> new CartItem(phoneDao.get(e.getKey()).orElseThrow(IllegalArgumentException::new), e.getValue()))
                .collect(Collectors.toSet());
        cart.setCartItems(cartItemsToBeSet);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhone().getId().equals(phoneId));
    }

    @Override
    public BigDecimal getTotalPrice() {
        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long getTotalItemsCount() {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }
}
