/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.service;

import boskitakita.permadi.entity.Userlogin;
import boskitakita.permadi.util.HitMe;
import boskitakita.permadi.util.Utility;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Bachtiar M Permadi
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterService {

    public RegisterService() {

    }

    @POST
    @Path("/doregister")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(Userlogin entity) {
        String response = "";
        String name = entity.getName();
        String username = entity.getUsername();
        String password = entity.getPassword();
        int role = entity.getRole();
        String otherOne = entity.getOtherone();
        String otherTwo = entity.getOthertwo();
        String otherThree = entity.getOtherthree();
        Date todaysDate = new Date();
        DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String createdAt = df.format(todaysDate);


        int retCode = registerUser(name, username, password, role, otherOne, otherTwo, otherThree, createdAt);
        if (retCode == 0) {
            response = Utility.constructJSON("register", true);
        } else if (retCode == 1) {
            response = Utility.constructJSON("register", false, "Username sudah terdaftar");
        } else if (retCode == 2) {
            response = Utility.constructJSON("register", false, "Spesial karakter tidak di ijinkan pada username");
        } else if (retCode == 3) {
            response = Utility.constructJSON("register", false, "Username sudah terdaftar");
        }
        return response;
    }

    private int registerUser(String name, String username, String password, Integer role, String otherOne, String otherTwo, String otherThree, String createdAt) {
        System.out.println("Inside checkCredentials");
        int result = 3;
        if (Utility.isNotNull(name) && Utility.isNotNull(username) && Utility.isNotNull(password) && Utility.isNotNull(role.toString()) && Utility.isNotNull(otherTwo) && Utility.isNotNull(otherThree) && Utility.isNotNull(createdAt)) {
            try {
                if (HitMe.insertUser(name, username, password, role, otherOne, otherTwo, otherThree, createdAt)) {
                    System.out.println("RegisterUSer if");
                    result = 0;
                }
            } catch (SQLException sqle) {
                System.out.println("RegisterUSer catch sqle");
                //When Primary key violation occurs that means user is already registered
                if (sqle.getErrorCode() == 1064) {
                    result = 2;
                } //When special characters are used in name,username or password

            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
                result = 3;
            }
        } else {
            System.out.println("Inside checkCredentials else");
            result = 1;
        }

        return result;
    }

}
