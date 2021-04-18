package com.es.core.model.stock;

import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;

import java.util.List;

public interface StockDao {

    void reservePhone(Long phoneId, Integer toReserve);

    void applyReserved(List<? extends CartItem> items);

    void cancelReserved(List<? extends CartItem> items);

    Stock getStockForPhone(Phone phone);
}
