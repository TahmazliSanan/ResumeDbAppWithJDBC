package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDao;
import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.entity.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryDaoImpl extends AbstractDao implements CountryDaoInter {
    private Country getCountry(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String nationality = rs.getString("nationality");
        return new Country(id, name, nationality);
    }
    
    @Override
    public boolean addCountry(Country country) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO"
                    + " COUNTRIES(NAME, NATIONALITY)"
                    + " VALUES(?, ?)");
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getNationality());
            return stmt.execute();
        } catch (Exception ex) {
            Logger.getLogger(CountryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @Override
    public Country getCountryById(int id) {
        Country result = null;
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();        
            stmt.execute("""
                            SELECT *
                            FROM COUNTRIES
                            WHERE ID = 
                        """ + id);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result = getCountry(rs);
            }
        } catch (Exception ex) {
            Logger.getLogger(CountryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    @Override
    public List<Country> getAllCountries() {
        List<Country> result = new ArrayList<>();
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();
            stmt.execute("""
                            SELECT *
                            FROM COUNTRIES
                            """);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result.add(getCountry(rs));
            }
        } catch (Exception ex) {
            Logger.getLogger(CountryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean updateCountry(Country country) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("""
                                                            UPDATE COUNTRIES SET 
                                                            NAME = ?, 
                                                            NATIONALITY = ?
                                                            WHERE ID = ?
                                                            """);
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getNationality());
            stmt.setInt(3, country.getId());
            return stmt.execute();
        } catch (Exception ex) {
            Logger.getLogger(CountryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean removeCountry(int id) {
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();
            return stmt.execute("""
                                DELETE FROM COUNTRIES 
                                WHERE ID = 
                                """ + id);
        } catch (Exception ex) {
            Logger.getLogger(CountryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}