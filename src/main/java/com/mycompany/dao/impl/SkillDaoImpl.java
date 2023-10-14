package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDao;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.entity.Skill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SkillDaoImpl extends AbstractDao implements SkillDaoInter {
    private Skill getSkill(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Skill(id, name);
    }
    
    @Override
    public boolean addSkill(Skill skill) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO"
                    + " SKILLS(NAME)"
                    + " VALUES(?)");
            stmt.setString(1, skill.getName());
            return stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SkillDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Skill getSkillById(int id) {
        Skill result = null;
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();        
            stmt.execute("""
                            SELECT *
                            FROM SKILLS
                            WHERE ID = 
                        """ + id);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result = getSkill(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkillDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    @Override
    public List<Skill> getAllSkills() {
        List<Skill> result = new ArrayList<>();
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();
            stmt.execute("""
                            SELECT *
                            FROM SKILLS
                            """);
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result.add(getSkill(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkillDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean updateSkill(Skill skill) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("""
                                                            UPDATE SKILLS SET 
                                                            NAME = ?, 
                                                            WHERE ID = ?
                                                            """);
            stmt.setString(1, skill.getName());
            stmt.setInt(2, skill.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SkillDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean removeSkill(int id) {
        try (Connection con = connect()) {
            Statement stmt = con.createStatement();
            return stmt.execute("""
                                DELETE FROM SKILLS 
                                WHERE ID = 
                                """ + id);
        } catch (SQLException ex) {
            Logger.getLogger(SkillDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}