/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.helper;

import boskitakita.permadi.service.GetDataByHourService;
import boskitakita.permadi.util.EntityBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hello Permadi
 */
public class CountGrossFlow extends EntityBase {

    public HashMap<String, String> countPerHour(String unit, String date, String site) {
        HashMap<String, String> response = new HashMap<String, String>();
        String grossPerUnit = null;
        con = connect();
        try {
            String query = "SELECT sum(gross_deliver) AS fmTotal FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8)= '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id = '" + unit + "' GROUP BY site_id ORDER BY site_id ASC ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            grossPerUnit = rs.getString("fmTotal");

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        response.put("unit_id", unit);
        response.put("total", grossPerUnit);
        return response;
    }

    public HashMap<String, String> countPerDay(String unit, String date, String site) {
        HashMap<String, String> response = new HashMap<String, String>();
        con = connect();
        String grossPerUnit = null;
        try {
            String query = "SELECT sum(gross_deliver) AS fmTotal FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6)= '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id = '" + unit + "' GROUP BY site_id ORDER BY site_id ASC ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grossPerUnit = rs.getString("fmTotal");
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        response.put("unit_id", unit);
        response.put("total", grossPerUnit);
        return response;
    }

    public String countPerMonth(String unit, String date, String site) {
        String response = null;

        return response;
    }

    public String countPerYear(String unit, String date, String site) {
        String response = null;

        return response;
    }

    public String countPerSite(String date) {
        String response = null;

        return response;
    }

    public String countPerFlow(String date, String site) {
        String response = null;

        return response;
    }
}
