package com.es.core.cart;

import com.es.core.model.phone.PhoneDao;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service("cartService")
@SessionScope(proxyMode = ScopedProxyMode.INTERFACES)
public class HttpSessionCartService implements CartService {

    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        cart.getCartItems().merge(phoneId, quantity, Math::addExact);
        recalculateCartStatistics();
    }

    @Override
    public void update(Map<Long, Long> items) {
        cart.setCartItems(items);
        recalculateCartStatistics();
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().remove(phoneId);
        recalculateCartStatistics();
    }

    private void recalculateCartStatistics() {
        Long itemsCount = cart.getCartItems()
                .values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
        BigDecimal totalPrice = BigDecimal.valueOf(cart.getCartItems()
                .entrySet()
                .stream()
                .mapToDouble(entry ->
                        entry.getValue() *
                                phoneDao.get(entry.getKey()).orElseThrow(IllegalArgumentException::new)
                                        .getPrice()
                                        .doubleValue())
                .sum()
        );
        cart.setTotalItemsCount(itemsCount);
        cart.setTotalPrice(totalPrice);
    }
}
