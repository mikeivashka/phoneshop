package com.es.core.model.stock;

import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.stream.Collectors;

public class JdbcStockDao implements StockDao {
    private static final String SQL_GET_STOCK_FOR_PHONE = "SELECT stock, reserved FROM stocks WHERE phoneId = %d";
    private static final String SQL_RESERVE_STOCK_FOR_PHONE = "UPDATE stocks SET reserved = reserved + ? WHERE phoneId = ?";
    private static final String SQL_APPLY_RESERVED_STOCK_FOR_PHONE = "UPDATE stocks SET reserved = reserved - ?, stock = stock - ? WHERE phoneId = ?";
    private static final String SQL_CANCEL_RESERVED_STOCK_FOR_PHONE = "UPDATE stocks SET reserved = reserved - ? WHERE phoneId = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcStockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void reservePhone(Long phoneId, Integer toReserve) {
        jdbcTemplate.update(SQL_RESERVE_STOCK_FOR_PHONE, toReserve, phoneId);
    }

    @Override
    public void applyReserved(List<? extends CartItem> items) {
        List<Object[]> batchArgs = items.stream()
                .map(item -> new Object[]{item.getQuantity(), item.getQuantity(), item.getPhone().getId()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(SQL_APPLY_RESERVED_STOCK_FOR_PHONE, batchArgs);
    }

    @Override
    public void cancelReserved(List<? extends CartItem> items) {
        List<Object[]> batchArgs = items.stream()
                .map(item -> new Object[]{item.getQuantity(), item.getPhone().getId()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(SQL_CANCEL_RESERVED_STOCK_FOR_PHONE, batchArgs);
    }

    @Override
    public Stock getStockForPhone(Phone phone) {
        String sql = String.format(SQL_GET_STOCK_FOR_PHONE, phone.getId());
        Stock stock = jdbcTemplate.queryForObject(sql, getRowMapperForStock());
        stock.setPhone(phone);
        return stock;
    }

    private RowMapper<Stock> getRowMapperForStock() {
        return (rs, rowNum) -> {
            Stock stock = new Stock();
            stock.setStock(rs.getInt("stock"));
            stock.setReserved(rs.getInt("reserved"));
            return stock;
        };
    }
}
