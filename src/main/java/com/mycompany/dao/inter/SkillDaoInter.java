package com.mycompany.dao.inter;

import com.mycompany.entity.Skill;
import java.util.List;

public interface SkillDaoInter {
    boolean addSkill(Skill skill);
    Skill getSkillById(int id);
    List<Skill> getAllSkills();
    boolean updateSkill(Skill skill);
    boolean removeSkill(int id);
}