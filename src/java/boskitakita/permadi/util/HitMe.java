/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.util;

import boskitakita.permadi.entity.Userlogin;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Hello Permadi
 */
public class HitMe {

    public static int chkLogin(String username, String pwd) throws Exception {
        int isUserAvailable = 2;
        Connection dbConn = null;
        try {
            try {
                dbConn = DbConnection.createConnection();
            } catch (Exception e) {
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT `id`, `name`, `username`, `password`, `role`, `otherone`, `othertwo`, `otherthree`, `created_at` FROM `userlogin` WHERE `username` = '" + username + "' AND password = md5('" + pwd + "')";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Userlogin user = new Userlogin();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUsername(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setRole(rs.getInt(5));
                user.setOtherone(rs.getString(6));
                user.setOthertwo(rs.getString(7));
                user.setOtherthree(rs.getString(8));
                isUserAvailable = 1;

            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return isUserAvailable;
    }

    public static boolean insertUser(String name, String username, String password, int role, String otherOne, String otherTwo, String otherThree, String createdAt)
            throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DbConnection.createConnection();
            } catch (Exception e) {
            }
            if (checkUsername(username) == 0) {
                Statement stmt = dbConn.createStatement();
                String query = "INSERT INTO `userlogin`(`name`, `username`, `password`, `role`, `otherone`, `othertwo`, `otherthree`, `created_at`) "
                        + "VALUES ('" + name + "','" + username + "', md5('" + password + "'),'" + role + "','" + otherOne + "','" + otherTwo + "','" + otherThree + "','" + createdAt + "')";
                //System.out.println(query);
                int records = stmt.executeUpdate(query);
                //System.out.println(records);
                //When record is successfully inserted
                if (records > 0) {
                    insertStatus = true;
                }
            } else if (checkUsername(username) == 1) {
                insertStatus = false;
            }

        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }

    public static int checkUsername(String username)
            throws SQLException, Exception {
        int isUserAvailable = 0;
        Connection dbConn = null;
        try {
            try {
                dbConn = DbConnection.createConnection();
            } catch (Exception e) {
            }

            Statement stmt = dbConn.createStatement();
            String query = "SELECT `id`, `name`, `username`, `password`, `role`, `otherone`, `othertwo`, `otherthree`, `created_at` FROM `userlogin` WHERE `username` = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Userlogin user = new Userlogin();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUsername(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setRole(rs.getInt(5));
                user.setOtherone(rs.getString(6));
                user.setOthertwo(rs.getString(7));
                user.setOtherthree(rs.getString(8));
                isUserAvailable = 1;

            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return isUserAvailable;
    }

}
