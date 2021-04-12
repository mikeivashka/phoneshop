package com.es.core.cart;

import com.es.core.model.phone.CartItem;
import com.es.core.model.phone.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceUnitTest {
    @InjectMocks
    @Spy
    private HttpSessionCartService cartService;

    @Mock
    private Cart cart;

    @Test
    public void getCartTotalPriceTest() {
        double[] prices = {200, 350, 400.5f, 150};
        long[] quantities = {2, 2, 1, 3};

        Phone phone;
        Set<CartItem> cartItems = new HashSet<>();
        for (int i = 0; i < prices.length; i++) {
            phone = Mockito.mock(Phone.class);
            when(phone.getPrice()).thenReturn(BigDecimal.valueOf(prices[i]));
            cartItems.add(new CartItem(phone, quantities[i]));
        }

        when(cart.getCartItems()).thenReturn(cartItems);

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < prices.length; i++) {
            sum = sum.add(BigDecimal.valueOf(prices[i] * quantities[i]));
        }
        BigDecimal expected = sum;
        BigDecimal actual = cartService.getTotalPrice();

        assertEquals(expected, actual);
    }

    @Test
    public void getCartTotalItemsCountTest() {
        long[] quantities = {2, 2, 1, 3};

        Phone phone;
        Set<CartItem> cartItems = new HashSet<>();
        for (long quantity : quantities) {
            phone = Mockito.mock(Phone.class);
            cartItems.add(new CartItem(phone, quantity));
        }

        when(cart.getCartItems()).thenReturn(cartItems);

        long sum = 0L;
        for (long quantity : quantities) {
            sum += quantity;
        }
        Long expected = sum;
        Long actual = cartService.getTotalItemsCount();

        assertEquals(expected, actual);
    }

    @Test
    public void getCartTotalItemsCountOnEmptyCartReturnsZero(){
        when(cart.getCartItems()).thenReturn(new HashSet<>());

        Long expected = 0L;
        assertEquals(expected, cartService.getTotalItemsCount());
    }

    @Test
    public void getCartTotalPriceOnEmptyCartReturnsZero(){
        when(cart.getCartItems()).thenReturn(new HashSet<>());

        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, cartService.getTotalPrice());
    }

}
