package com.es.core.model.phone;

import com.es.core.enumeration.SortField;
import com.es.core.enumeration.SortOrder;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    Optional<Phone> get(String model);

    void save(Phone phone);

    List<Phone> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder);

    List<Phone> query(String query, int offset, int limit, SortField sortField, SortOrder sortOrder);

    Long countQueryResults(String query);

    default List<Phone> findAll(int offset, int limit) {
        return findAll(offset, limit, SortField.PRICE, SortOrder.ASC);
    }
}
