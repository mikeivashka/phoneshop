package com.es.core.model.stock;

import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/context/test-config.xml")
public class JdbcStockDaoIntegrationTest {
    private static final Long EXISTING_PHONE_WITH_STOCK_KEY = 1001L;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private StockDao stockDao;

    private Phone phone;

    @Before
    public void setUp() {
        phone = new Phone();
    }

    @Test
    public void testGetStockForPhoneReturnsCorrectStock() {
        Integer expectedReservedStock = getReservedStockForPhone(EXISTING_PHONE_WITH_STOCK_KEY);
        phone.setId(EXISTING_PHONE_WITH_STOCK_KEY);

        assertEquals(expectedReservedStock, stockDao.getStockForPhone(phone).getReserved());
    }

    @Test
    public void testGetStockForPhoneReturnsCorrectReserved() {
        Integer expectedStock = getStockForPhone(EXISTING_PHONE_WITH_STOCK_KEY);
        phone.setId(EXISTING_PHONE_WITH_STOCK_KEY);

        assertEquals(expectedStock, stockDao.getStockForPhone(phone).getStock());
    }

    @Test
    public void testReservePhonesIncreasesReserved() {
        Integer stockBefore = getReservedStockForPhone(EXISTING_PHONE_WITH_STOCK_KEY);

        stockDao.reservePhone(EXISTING_PHONE_WITH_STOCK_KEY, 1);

        assertEquals(stockBefore + 1, getReservedStockForPhone(EXISTING_PHONE_WITH_STOCK_KEY).intValue());
    }

    private Integer getStockForPhone(Long phoneId) {
        return jdbcTemplate.queryForObject("SELECT (stock) FROM stocks WHERE phoneId = " + phoneId, Integer.class);
    }

    private Integer getReservedStockForPhone(Long phoneId) {
        return jdbcTemplate.queryForObject("SELECT (reserved) FROM stocks WHERE phoneId = " + phoneId, Integer.class);
    }

}