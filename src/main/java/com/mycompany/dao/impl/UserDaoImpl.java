package com.mycompany.dao.impl;

import com.mycompany.entity.Country;
import com.mycompany.entity.User;
import com.mycompany.dao.inter.AbstractDao;
import com.mycompany.dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl extends AbstractDao implements UserDaoInter {
    private User getUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        Date birthDate = rs.getDate("birth_date");
        int birthPlaceId = rs.getInt("birth_place_id");
        String birthPlaceStr = rs.getString("birth_place");
        int nationalityId = rs.getInt("nationality_id");
        String nationalityStr = rs.getString("nationality");
        Country birthPlace = new Country(birthPlaceId, birthPlaceStr, null);
        Country nationality = new Country(nationalityId, null, nationalityStr);
        return new User(id, name, surname, email, phone, birthDate, birthPlace, nationality);
    }
    
    @Override
    public boolean addUser(User user) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO"
                    + " USERS(NAME, SURNAME, EMAIL, PHONE)"
                    + " VALUES(?, ?, ?, ?)");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            return stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public User getUserById(int userId) {
        User result = null;
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();        
            stmt.execute("""
                            SELECT
                                U.*,
                                C.NAME AS BIRTH_PLACE,
                                N.NATIONALITY
                            FROM USERS U
                            LEFT JOIN COUNTRIES N ON U.NATIONALITY_ID = N.ID
                            LEFT JOIN COUNTRIES C ON U.BIRTH_PLACE_ID = C.ID
                            HAVING ID = 
                        """ + userId);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result = getUser(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();
            stmt.execute("""
                            SELECT
                         	U.*,
                         	C.NAME AS BIRTH_PLACE,
                         	N.NATIONALITY
                            FROM USERS U
                            LEFT JOIN COUNTRIES N ON U.NATIONALITY_ID = N.ID
                            LEFT JOIN COUNTRIES C ON U.BIRTH_PLACE_ID = C.ID
                            """);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result.add(getUser(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE USERS SET"
                    + " NAME = ?,"
                    + " SURNAME = ?,"
                    + " EMAIL = ?,"
                    + " PHONE = ?"
                    + " WHERE ID = ?");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setInt(5, user.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean removeUser(int id) {
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();
            return stmt.execute("DELETE FROM USERS"
                    + " WHERE ID = " + id);
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}