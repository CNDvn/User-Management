/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.account;

import hieubd.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author CND
 */
public class AccountDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    private void closeDB() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public void addNewUser(AccountDTO user) throws NamingException, SQLException {
        con = DBHelper.getConnection();
        try {
            if (con != null) {
                String sql = "INSERT INTO Account(username, password, fullname, email, phone, photo, statusID, roleID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, user.getUsername());
                stm.setString(2, user.getPassword());
                stm.setString(3, user.getFullname());
                stm.setString(4, user.getEmail());
                stm.setString(5, user.getPhone());
                stm.setString(6, user.getPhoto());
                stm.setInt(7, user.getStatusId());
                stm.setInt(8, user.getRoleId());
                stm.executeUpdate();
            }
        } finally {
            closeDB();
        }
    }

    public boolean checkUsernameExisted(String username) throws NamingException, SQLException {
        con = DBHelper.getConnection();
        try {
            if (con != null) {
                String sql = "SELECT username "
                        + "FROM Account "
                        + "WHERE username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeDB();
        }
        return false;
    }

    public AccountDTO checkLogin(String username, String password) throws NamingException, SQLException {
        con = DBHelper.getConnection();
        try {
            if (con != null) {
                String sql = "SELECT username, password, fullname, email, phone, photo, statusID, roleID "
                        + "FROM Account "
                        + "WHERE username = ? AND password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return new AccountDTO(rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("fullname"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("photo"),
                            rs.getInt("statusID"),
                            rs.getInt("roleID"));
                }
            }
        } finally {
            closeDB();
        }

        return null;
    }

}
