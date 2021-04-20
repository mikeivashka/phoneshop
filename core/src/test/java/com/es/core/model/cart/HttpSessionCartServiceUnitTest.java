package com.es.core.model.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceUnitTest {
    private static final int PHONE_1_QUANTITY = 2;
    private static final int PHONE_2_QUANTITY = 1;
    private static final int PHONE_3_QUANTITY = 3;
    private static final int TOTAL_QUANTITY = PHONE_1_QUANTITY + PHONE_2_QUANTITY + PHONE_3_QUANTITY;
    private static final double PHONE_1_PRICE = 200d;
    private static final double PHONE_2_PRICE = 50d;
    private static final double PHONE_3_PRICE = 20.5d;
    private static final double TOTAL_PRICE = PHONE_1_PRICE * PHONE_1_QUANTITY + PHONE_2_PRICE * PHONE_2_QUANTITY + PHONE_3_PRICE * PHONE_3_QUANTITY;
    private static final Long PHONE_1_ID = 1L;
    private static final Long PHONE_2_ID = 2L;
    private static final Long PHONE_3_ID = 3L;
    @InjectMocks
    @Spy
    private HttpSessionCartService cartService;

    @Mock
    private Phone phone1;

    @Mock
    private Phone phone2;

    @Mock
    private Phone phone3;

    private Set<CartItem> cartItems;

    @Spy
    private Cart cart;

    @Mock
    private PhoneDao phoneDao;

    @Before
    public void setUp() {
        when(phoneDao.get(PHONE_1_ID)).thenReturn(Optional.of(phone1));
        when(phoneDao.get(PHONE_2_ID)).thenReturn(Optional.of(phone2));
        when(phoneDao.get(PHONE_3_ID)).thenReturn(Optional.of(phone3));
        when(phone1.getPrice()).thenReturn(BigDecimal.valueOf(PHONE_1_PRICE));
        when(phone2.getPrice()).thenReturn(BigDecimal.valueOf(PHONE_2_PRICE));
        when(phone3.getPrice()).thenReturn(BigDecimal.valueOf(PHONE_3_PRICE));
        when(phone1.getId()).thenReturn(PHONE_1_ID);
        when(phone2.getId()).thenReturn(PHONE_2_ID);
        when(phone3.getId()).thenReturn(PHONE_3_ID);
        cartItems = new HashSet<>(Arrays.asList(new CartItem(phone1, PHONE_1_QUANTITY),
                new CartItem(phone2, PHONE_2_QUANTITY),
                new CartItem(phone3, PHONE_3_QUANTITY)
        ));
        when(cart.getCartItems()).thenReturn(cartItems);
    }

    @Test
    public void getCartTotalPriceTest() {
        BigDecimal actual = cartService.getTotalPrice();

        assertEquals(BigDecimal.valueOf(TOTAL_PRICE), actual);
    }

    @Test
    public void getCartTotalItemsCountTest() {
        long actual = cartService.getTotalItemsCount();

        assertEquals(TOTAL_QUANTITY, actual);
    }

    @Test
    public void getCartTotalItemsCountOnEmptyCartReturnsZeroTest() {
        when(cart.getCartItems()).thenReturn(new HashSet<>());

        Integer expected = 0;
        assertEquals(expected, cartService.getTotalItemsCount());
    }

    @Test
    public void getCartTotalPriceOnEmptyCartReturnsZeroTest() {
        when(cart.getCartItems()).thenReturn(new HashSet<>());

        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, cartService.getTotalPrice());
    }

    @Test
    public void deleteFromCartOnExistingItemTest() {
        cartService.remove(phone1.getId());

        assertFalse(cartService.getCart().containsKey(phone1));
    }

    @Test
    public void addToCartNewItemIsSavedInCartTest() {
        when(cart.getCartItems()).thenReturn(new HashSet<>());

        cartService.addPhone(PHONE_1_ID, PHONE_1_QUANTITY);

        assertTrue(cartService.getCart().containsKey(phone1));
        assertEquals(Integer.valueOf(PHONE_1_QUANTITY), cartService.getCart().get(phone1));
    }

    @Test
    public void getCartReturnsConvertedCartTest() {
        Map<Phone, Integer> expected = new HashMap<>();
        expected.put(phone1, PHONE_1_QUANTITY);
        expected.put(phone2, PHONE_2_QUANTITY);
        expected.put(phone3, PHONE_3_QUANTITY);

        assertEquals(expected, cartService.getCart());
    }

    @Test
    public void updateCartSavesProvidedItems() {
        when(cart.getCartItems()).thenCallRealMethod();
        Map<Long, Integer> updatedCart = new HashMap<>();
        updatedCart.put(PHONE_1_ID, PHONE_1_QUANTITY);
        updatedCart.put(PHONE_2_ID, PHONE_2_QUANTITY);
        updatedCart.put(PHONE_3_ID, PHONE_3_QUANTITY);

        cartService.update(updatedCart);

        assertTrue(cartService.getCart().containsKey(phone1));
        assertTrue(cartService.getCart().containsKey(phone2));
        assertTrue(cartService.getCart().containsKey(phone3));
    }

}
