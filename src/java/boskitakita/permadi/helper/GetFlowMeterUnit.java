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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hello Permadi
 */
public class GetFlowMeterUnit extends EntityBase {

    public ArrayList<String> flowUnit(int flow, String date, String site) {
        ArrayList<String> unitList = new ArrayList<>();
        con = connect();
        try {

            if (flow == 1) {
                String query = "SELECT unit_id AS unit1 FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != ''"
                        + " GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                }

            } else if (flow == 2) {
                String query = "SELECT unit_id AS unit1, "
                        + "(SELECT unit_id FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2"
                        + "FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                }
            } else if (flow == 3) {
                String query = "SELECT unit_id AS unit1,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 2,1) AS unit3"
                        + " FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1 ";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                    unitList.add(rs.getString("unit3"));
                }
            } else if (flow == 4) {
                String query = "SELECT unit_id AS unit1, "
                        + "(SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 2,1) AS unit3,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 3,1) AS unit4"
                        + " FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1 ";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                    unitList.add(rs.getString("unit3"));
                    unitList.add(rs.getString("unit4"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return unitList;
    }

    public ArrayList<String> flowUnitDay(int flow, String date, String site) {
        ArrayList<String> unitList = new ArrayList<>();
        con = connect();
        try {

            if (flow == 1) {
                String query = "SELECT unit_id AS unit1 FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != ''"
                        + " GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                }

            } else if (flow == 2) {
                String query = "SELECT unit_id AS unit1, "
                        + "(SELECT unit_id FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2"
                        + "FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                }
            } else if (flow == 3) {
                String query = "SELECT unit_id AS unit1,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 2,1) AS unit3"
                        + " FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1 ";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                    unitList.add(rs.getString("unit3"));
                }
            } else if (flow == 4) {
                String query = "SELECT unit_id AS unit1, "
                        + "(SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 2,1) AS unit3,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 3,1) AS unit4"
                        + " FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,4,6) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1 ";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                    unitList.add(rs.getString("unit3"));
                    unitList.add(rs.getString("unit4"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return unitList;
    }
     public ArrayList<String> flowUnitMonth(int flow, String date, String site) {
        ArrayList<String> unitList = new ArrayList<>();
        con = connect();
        try {

            if (flow == 1) {
                String query = "SELECT unit_id AS unit1 FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != ''"
                        + " GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                }

            } else if (flow == 2) {
                String query = "SELECT unit_id AS unit1, "
                        + "(SELECT unit_id FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2"
                        + "FROM serial_data_results  WHERE  site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                }
            } else if (flow == 3) {
                String query = "SELECT unit_id AS unit1,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 2,1) AS unit3"
                        + " FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1 ";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                    unitList.add(rs.getString("unit3"));
                }
            } else if (flow == 4) {
                String query = "SELECT unit_id AS unit1, "
                        + "(SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1,1) AS unit2,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 2,1) AS unit3,"
                        + " (SELECT unit_id FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 3,1) AS unit4"
                        + " FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + date + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' GROUP BY unit_id ORDER BY unit_id ASC LIMIT 1 ";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    unitList.add(rs.getString("unit1"));
                    unitList.add(rs.getString("unit2"));
                    unitList.add(rs.getString("unit3"));
                    unitList.add(rs.getString("unit4"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return unitList;
    }
}
