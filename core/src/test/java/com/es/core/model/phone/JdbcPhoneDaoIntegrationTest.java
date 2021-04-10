package com.es.core.model.phone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/context/test-config.xml")
public class JdbcPhoneDaoIntegrationTest {
    private static final String SQL_MAX_PHONE_ID_QUERY = "SELECT MAX(id) FROM phones";
    private static final String SQL_COUNT_PHONES_QUERY = "SELECT COUNT(*) FROM phones";
    private static final String SQL_COUNT_COLORS_QUERY = "SELECT COUNT (*) FROM phone2color WHERE phoneId = ";
    private static final Long EXISTING_PHONE_WITH_COLORS_KEY = 1001L;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    private Phone phone;

    @Before
    public void setUp() {
        phone = new Phone();
        phone.setBrand("Samsung");
        phone.setModel("Galaxy S 10");
    }

    @Test
    public void testFindAllWithLimitLessThenMaxReturnsLimit() {
        Integer currentLines = currentLinesCount();

        List<Phone> found = phoneDao.findAll(0, currentLines);

        Assert.isTrue(found.size() == currentLines, "If requested limit is le then table size, list of the same size must be returned");

    }

    @Test
    public void testFindAllWithLimitMoreThenMaxReturnsMax() {
        Integer currentLines = currentLinesCount();

        List<Phone> found = phoneDao.findAll(0, currentLines + 1);

        Assert.isTrue(found.size() == currentLines, "If requested limit is more then table size, returned list must be same as table size");
    }

    @Test
    public void testFindAllWithOffsetEqualToMaxReturnsEmptyList() {
        Integer currentLines = currentLinesCount();

        List<Phone> found = phoneDao.findAll(currentLines, currentLines);

        Assert.isTrue(found.isEmpty(), "If requested offset is more then table size, returned list must be empty");
    }

    @Test
    public void testFindAllWithOffsetLessThenMaxReturnsListWithMaxMinusOffsetElements() {
        Integer currentLines = currentLinesCount();
        Integer difference = 1;

        List<Phone> found = phoneDao.findAll(currentLines - difference, currentLines);

        Assert.isTrue(found.size() == difference, "If size limit exceeded, size of returned list must be equal to size - offset");
    }

    @Test
    public void testFindAllWithNegativeOffsetReturnsLimit() {
        Integer currentLines = currentLinesCount();

        List<Phone> found = phoneDao.findAll(-1, currentLines);

        assertEquals(currentLines.intValue(), found.size());
    }

    @Test
    public void testFindAllWithNegativeLimitReturnsAllLines() {
        Integer currentLines = currentLinesCount();

        List<Phone> found = phoneDao.findAll(0, -1);

        assertEquals(currentLines.intValue(), found.size());
    }


    @Test
    public void testGetOnNotExistingPhoneIsEmptyOptional() {
        Assert.isTrue(!phoneDao.get(currentMaxPhoneId() + 1).isPresent(), "Not existing phone ID must return empty Optional");
    }

    @Test
    public void testGetExistingByIdReturnsPhoneWithColors() {
        Optional<Phone> found = phoneDao.get(EXISTING_PHONE_WITH_COLORS_KEY);

        Assert.isTrue(found.isPresent(), "Existing phone ID must return phone");

        Assert.notEmpty(found.get().getColors(), "Existing phone with colors must have at least one color");
    }

    @Test
    public void testGetExistingByIdReturnsPhoneWithAllColorsAvailable() {
        Phone found = phoneDao.get(EXISTING_PHONE_WITH_COLORS_KEY).get();

        assertEquals(countColorsForPhone(EXISTING_PHONE_WITH_COLORS_KEY).intValue(), found.getColors().size());
    }

    @Test
    @DirtiesContext
    public void testAddNewPhoneIncreasesMaxIdByOne() {
        Long maxIdBefore = currentMaxPhoneId();

        phoneDao.save(phone);

        Long maxIdAfterSave = currentMaxPhoneId();
        Assert.isTrue(maxIdAfterSave == maxIdBefore + 1, "After row added ID must be increased by one");
    }

    @Test
    @DirtiesContext
    public void testAddPhoneIncreasesMaxIdByOne() {
        Long maxIdBefore = currentMaxPhoneId();

        phoneDao.save(phone);

        Long maxIdAfterSave = currentMaxPhoneId();
        Assert.isTrue(maxIdAfterSave == maxIdBefore + 1, "After row added ID must be increased by one");
    }

    private Long currentMaxPhoneId() {
        return jdbcTemplate.queryForObject(SQL_MAX_PHONE_ID_QUERY, Long.class);
    }

    private Integer currentLinesCount() {
        return jdbcTemplate.queryForObject(SQL_COUNT_PHONES_QUERY, Integer.class);
    }

    private Integer countColorsForPhone(Long phoneId) {
        return jdbcTemplate.queryForObject(SQL_COUNT_COLORS_QUERY + phoneId, Integer.class);
    }
}