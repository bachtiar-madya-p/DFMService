/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Hello Permadi
 */
public class DbConnection {

    private static final String DBCLASS = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/postenergy?zeroDateTimeBehavior=convertToNull";
    private static final String DBUSER = "root";
    private static final String DBPWD = "rootadmin";

    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(DBCLASS);
            con = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            return con;
        }
    }
}
