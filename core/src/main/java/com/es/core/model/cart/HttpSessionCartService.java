package com.es.core.model.cart;

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
    private CartCalculationService cartCalculationService;

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public Map<Phone, Integer> getCart() {
        return cart.getCartItems().stream()
                .collect(Collectors.toMap(CartItem::getPhone, CartItem::getQuantity, (x, y) -> y, LinkedHashMap<Phone, Integer>::new));
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
        cart.getCartItems().removeIf(cartItem -> cartItem.getPhone().getId().equals(phoneId));
    }

    @Override
    public BigDecimal getTotalPrice() {
        return cartCalculationService.calculateCartTotal(cart.getCartItems());
    }

    @Override
    public Integer getTotalItemsCount() {
        return cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
