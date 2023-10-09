package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDao;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.Skill;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSkillDaoImpl extends AbstractDao implements UserSkillDaoInter {
    private UserSkill getUserSkill(ResultSet rs) throws SQLException {
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");
        return new UserSkill(null, new User(userId), new Skill(skillId, skillName), power);
    }
    
    @Override
    public List<UserSkill> getAllSkillsByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("""
                                                            SELECT
                                                                U.*,
                                                          	US.SKILL_ID,
                                                          	S.NAME AS SKILL_NAME,
                                                          	US.POWER
                                                            FROM USER_SKILLS US
                                                            LEFT JOIN USERS U ON US.USER_ID = U.ID
                                                            LEFT JOIN SKILLS S ON US.SKILL_ID = S.ID
                                                            WHERE US.USER_ID = ?
                                                            """);
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                result.add(getUserSkill(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}