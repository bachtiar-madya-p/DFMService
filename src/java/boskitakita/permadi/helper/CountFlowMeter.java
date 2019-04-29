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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hello Permadi
 */
public class CountFlowMeter extends EntityBase{
    
     public int countFlow(String date, String site) {
        int response = 0;
        con = connect();
        try {
            String query = "SELECT COUNT(DISTINCT(unit_id)) as unitFlow FROM serial_data_results "
                    + " WHERE SUBSTR(FINISH,1,8) = '" + date + "' AND site_id = '" + site + "' AND duplicate = '' and unit_id != ''and gross_deliver != '' ORDER BY unit_id ASC ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                response = rs.getInt(1);
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
     public int countFlow2(String date, String site) {
        int response = 0;
        con = connect();
        try {
            String query = "SELECT COUNT(DISTINCT(unit_id)) as unitFlow FROM serial_data_results "
                    + " WHERE SUBSTR(FINISH,4,6)  = '" + date + "' AND site_id = '" + site + "' AND duplicate = '' and unit_id != ''and gross_deliver != '' ORDER BY unit_id ASC ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                response = rs.getInt(1);
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
     
         public int countFlow3(String date, String site) {
        int response = 0;
        con = connect();
        try {
            String query = "SELECT COUNT(DISTINCT(unit_id)) as unitFlow FROM serial_data_results "
                    + " WHERE SUBSTR(FINISH,7,2)  = '" + date + "' AND site_id = '" + site + "' AND duplicate = '' and unit_id != ''and gross_deliver != '' ORDER BY unit_id ASC ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                response = rs.getInt(1);
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
