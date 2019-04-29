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
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginService {

    public LoginService() {

    }

    @POST
    @Path("/dologin")
    @Transactional
    public String login(Userlogin entity) throws JSONException {
        String response = "";
        String username = entity.getUsername();
        String pwd = entity.getPassword();

        int retCode = checkUser(username, pwd);
        if (retCode == 0) {
            response = Utility.constructJSON("login", true);
        } else if (retCode == 1) {
            response = Utility.constructJSON("login", false, "Username atau password salah");
        } else if (retCode == 2) {
            response = Utility.constructJSON("login", false, "Error occured");
        }
        return response;
    }

    private int checkUser(String username, String pwd) {
        System.out.println("Inside checkCredentials");
        int result = 3;
        if (Utility.isNotNull(username) && Utility.isNotNull(pwd)) {
            try {
                if (HitMe.chkLogin(username, pwd) == 1) {
                    System.out.println("RegisterUSer if");
                    result = 0;
                } else if (HitMe.chkLogin(username, pwd) == 2) {
                    result = 1;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("Inside checkCredentials catch e ");
                result = 2;
            }
            //When Primary key violation occurs that means user is already registered
            //When special characters are used in name,username or password

        } else {
            System.out.println("Inside checkCredentials else");
            result = 2;
        }

        return result;
    }
}
