package com.mycompany.dao.inter;

import com.mycompany.entity.User;
import java.util.List;

public interface UserDaoInter {
    boolean addUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean removeUser(int id);
}