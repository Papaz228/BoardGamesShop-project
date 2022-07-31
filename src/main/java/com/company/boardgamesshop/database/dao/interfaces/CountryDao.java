package com.company.boardgamesshop.database.dao.interfaces;

import com.company.boardgamesshop.entity.Country;

import java.util.List;

public interface CountryDao {
    List<Country> getAllCountriesByLocalId(Long localId);
}
