package com.es.core.model.phone;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    public static final String SQL_GET_COLORS_QUERY = "SELECT colors.id, colors.code AS colorCode " +
            "FROM colors JOIN phone2color ON colors.id = phone2color.colorId " +
            "WHERE phoneId = ";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        List<Phone> matches = jdbcTemplate.query("SELECT * FROM phones WHERE id = " + key, new BeanPropertyRowMapper<>(Phone.class));
        if (!matches.isEmpty()) {
            matches.get(0).setColors(queryColors(key));
            return Optional.of(matches.get(0));
        }
        return Optional.empty();
    }

    public void save(final Phone phone) {
        @SuppressWarnings("unchecked")
        Map<String, Object> objectAsMap = new ObjectMapper().convertValue(phone, Map.class);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
        simpleJdbcInsert.execute(objectAsMap);
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("select * from phones offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
    }

    private Set<Color> queryColors(final Long key) {
        List<Color> matches = jdbcTemplate.query(SQL_GET_COLORS_QUERY + key, new BeanPropertyRowMapper<>(Color.class));
        return new HashSet<>(matches);
    }
}
