package org.rwth.bb4.dao;

import java.util.List;

import org.rwth.bb4.model.User;

public interface UserDao {
 
    void saveUser(User user);
     
    List<User> findAllUser();
     
    void deleteUserByAcntID(String AcntId);
}