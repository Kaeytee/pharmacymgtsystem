package com.pharmacy.functionalities;

import com.pharmacy.dao.PersonnelDAO;
import com.pharmacy.entities.Personnel;
import com.pharmacy.utils.DatabaseUtils;

import java.sql.SQLException;

public class LoginService {
    private PersonnelDAO personnelDAO;

    public LoginService() {
        try {
            this.personnelDAO = new PersonnelDAO(DatabaseUtils.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String username, String plainPassword) {
        Personnel user = getUserFromDatabase(username);

        if (user != null) {
            return user.verifyPassword(plainPassword);
        }
        return false;
    }

    private Personnel getUserFromDatabase(String username) {
        try {
            return personnelDAO.getPersonnelByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(String username, String plainPassword) {
        String hashedPassword = Personnel.hashPassword(plainPassword);
        Personnel newUser = new Personnel(username, hashedPassword);

        try {
            personnelDAO.addPersonnel(newUser);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
