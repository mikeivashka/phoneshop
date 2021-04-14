package com.es.core.model.stock;

import com.es.core.model.phone.Phone;

public interface StockDao {

    void reservePhone(Long phoneId, Integer toReserve);

    Stock getStockForPhone(Phone phone);
}
