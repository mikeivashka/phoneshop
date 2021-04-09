package com.es.core.model.phone;


import com.es.core.enumeration.SortField;
import com.es.core.enumeration.SortOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component("phoneDao")
public class JdbcPhoneDao implements PhoneDao {
    private static final String SQL_WHERE_FILTER = "WHERE price IS NOT NULL " +
            "AND stock > 0" +
            "AND LOWER(model) LIKE LOWER(?)";
    private static final String SQL_GET_COLORS_QUERY = "SELECT colors.id, colors.code FROM colors JOIN phone2color ON colors.id = phone2color.colorId WHERE phoneId = ";
    private static final String SQL_FIND_PRODUCTS = "SELECT * FROM phones";
    private static final String SQL_FIND_PRODUCTS_NONNULL_PRICE_POSITIVE_STOCK = "SELECT * FROM phones JOIN stocks ON phones.id = stocks.phoneId " + SQL_WHERE_FILTER;
    private static final String SQL_COUNT_PRODUCTS_NONNULL_PRICE_POSITIVE_STOCK = "SELECT COUNT(*) FROM phones JOIN stocks ON phones.id = stocks.phoneId " + SQL_WHERE_FILTER;
    private static final String SQL_ORDERED_OFFSET_LIMIT = "ORDER BY %s %s OFFSET ? LIMIT ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Phone> get(final Long key) {
        List<Phone> matches = jdbcTemplate.query("SELECT * FROM phones WHERE id = " + key, new BeanPropertyRowMapper<>(Phone.class));
        if (!matches.isEmpty()) {
            matches.get(0).setColors(queryColors(key));
            return Optional.of(matches.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void save(final Phone phone) {
        @SuppressWarnings("unchecked")
        Map<String, Object> objectAsMap = new ObjectMapper().convertValue(phone, Map.class);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
        simpleJdbcInsert.execute(objectAsMap);
    }

    @Override
    public Long countQueryResults(String query) {
        return jdbcTemplate.queryForObject(SQL_COUNT_PRODUCTS_NONNULL_PRICE_POSITIVE_STOCK, Long.class, String.format("%%%s%%", query));
    }

    @Override
    public List<Phone> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder) {
        String sql = SQL_FIND_PRODUCTS + " " + String.format(SQL_ORDERED_OFFSET_LIMIT, sortField.getColumnName(), sortOrder.name());
        List<Phone> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Phone.class), offset, limit);
        result.forEach(phone -> phone.setColors(queryColors(phone.getId())));
        return result;
    }

    @Override
    public List<Phone> query(String query, int offset, int limit, SortField sortField, SortOrder sortOrder) {
        String searchPattern = String.format("%%%s%%", query);
        String sql = SQL_FIND_PRODUCTS_NONNULL_PRICE_POSITIVE_STOCK + " " + String.format(SQL_ORDERED_OFFSET_LIMIT, sortField.getColumnName(), sortOrder.name());
        List<Phone> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Phone.class), searchPattern, offset, limit);
        result.forEach(phone -> phone.setColors(queryColors(phone.getId())));
        return result;
    }

    private Set<Color> queryColors(final Long key) {
        List<Color> matches = jdbcTemplate.query(SQL_GET_COLORS_QUERY + key, new BeanPropertyRowMapper<>(Color.class));
        return new HashSet<>(matches);
    }
}
