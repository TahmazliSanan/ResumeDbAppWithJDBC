package com.mycompany.main;

import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*
            Thightly coupling
            UserDaoInter udi2 = new UserDaoImpl();
         */

        // Loosely coupling
        EmploymentHistoryDaoInter ehdi = Context.instanceOfEmploymentHistoryDao();
        System.out.println(ehdi.getEmploymentHistoryByUserId(1));
    }
}