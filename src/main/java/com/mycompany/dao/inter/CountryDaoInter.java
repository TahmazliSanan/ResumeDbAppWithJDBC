package com.mycompany.dao.inter;

import com.mycompany.entity.Country;
import java.util.List;

public interface CountryDaoInter {
    boolean addCountry(Country country);
    Country getCountryById(int id);
    List<Country> getAllCountries();
    boolean updateCountry(Country country);
    boolean removeCountry(int id);
}