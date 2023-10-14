package com.mycompany.main;

import com.mycompany.dao.impl.CountryDaoImpl;
import com.mycompany.dao.impl.EmploymentHistoryDaoImpl;
import com.mycompany.dao.impl.SkillDaoImpl;
import com.mycompany.dao.impl.UserDaoImpl;
import com.mycompany.dao.impl.UserSkillDaoImpl;
import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.dao.inter.UserSkillDaoInter;

public class Context {
    public static UserDaoInter instanceOfUserDao() {
        return new UserDaoImpl();
    }
    
    public static UserSkillDaoInter instanceOfUserSkillDao() {
        return new UserSkillDaoImpl();
    }
    
    public static EmploymentHistoryDaoInter instanceOfEmploymentHistoryDao() {
        return new EmploymentHistoryDaoImpl();
    }
    
    public static CountryDaoInter instanceOfCountryDao() {
        return new CountryDaoImpl();
    }
    
    public static SkillDaoInter instanceOfSkillDao() {
        return new SkillDaoImpl();
    }
}