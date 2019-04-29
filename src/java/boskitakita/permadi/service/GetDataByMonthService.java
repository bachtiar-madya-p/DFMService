/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.service;


import boskitakita.permadi.entity.ModelByMonth;
import boskitakita.permadi.iface.IGetDataByMonthDataAccess;
import boskitakita.permadi.util.EntityBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hello Permadi
 */
@Path("/gbmonth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetDataByMonthService extends EntityBase implements IGetDataByMonthDataAccess<ModelByMonth> {

    @GET
    @Path("tabled/data/{yy}/{site}")
    @Transactional
    @Override
    public List<ModelByMonth> getDataTable(@PathParam("yy") String year, @PathParam("site") String site) {
        String dDate = year;
        List<ModelByMonth> listResponse = new ArrayList<>();
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unit = rs.getString("unit");
                String query = "SELECT IFNULL(SUM(gross_deliver),0) AS total1, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '02' AND unit_id = '" + unit + "') AS total2, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '03' AND unit_id = '" + unit + "') AS total3, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '04' AND unit_id = '" + unit + "') AS total4, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '05' AND unit_id = '" + unit + "') AS total5, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '06' AND unit_id = '" + unit + "') AS total6, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '07' AND unit_id = '" + unit + "') AS total7, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '08' AND unit_id = '" + unit + "') AS total8, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '09' AND unit_id = '" + unit + "') AS total9, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '10' AND unit_id = '" + unit + "') AS total10,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '11' AND unit_id = '" + unit + "') AS total11,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '12' AND unit_id = '" + unit + "') AS total12 "
                        + "FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '01' AND unit_id = '" + unit + "'";
                PreparedStatement stmt2 = con.prepareStatement(query);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    ModelByMonth data = new ModelByMonth();
                    data.setSiteId(site);
                    data.setUnitID(unit);
                    data.setJan(rs2.getString("total1"));
                    data.setFeb(rs2.getString("total2"));
                    data.setMar(rs2.getString("total3"));
                    data.setApr(rs2.getString("total4"));
                    data.setMei(rs2.getString("total5"));
                    data.setJun(rs2.getString("total6"));
                    data.setJul(rs2.getString("total7"));
                    data.setAgs(rs2.getString("total8"));
                    data.setSep(rs2.getString("total9"));
                    data.setOkt(rs2.getString("total10"));
                    data.setNov(rs2.getString("total11"));
                    data.setDes(rs2.getString("total12"));

                    listResponse.add(data);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByDaysService.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(GetDataByDaysService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listResponse;
    }

    @GET
    @Path("chart/data/{yy}/{site}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, HashMap<String, String>>> getDataChart(@PathParam("yy") String year, @PathParam("site") String site) {
        String dDate = year;
        ArrayList<HashMap<String, HashMap<String, String>>> listResponse = new ArrayList<HashMap<String, HashMap<String, String>>>();
        con = connect();
        try {

            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unit = rs.getString("unit");
                HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
                HashMap<String, String> gross = new HashMap<String, String>();
                for (int m = 1; m <= 12; m++) {
                    String month;
                    if (m <= 9) {
                        month = "0" + m;
                    } else {
                        month = Integer.toString(m);
                    }
                    String query = "SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results "
                            + " WHERE site_id = '" + site + "' AND SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(finish,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != '' AND duplicate = '' and meter_number != '' and unit_id = '" + unit + "' ";
                    PreparedStatement stmt2 = con.prepareStatement(query);
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        gross.put(month, rs2.getString("total"));
                    }
                }
                data.put(unit, gross);
                listResponse.add(data);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByDaysService.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(GetDataByDaysService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listResponse;
    }

    @GET
    @Path("flow/data/{yy}/{site}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, String>> getByFlowmeter(@PathParam("yy") String year, @PathParam("site") String site) {
        String dDate = year;
        ArrayList<HashMap<String, String>> listResponse = new ArrayList<HashMap<String, String>>();
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unit = rs.getString("unit");
                String query = "SELECT sum(gross_deliver) AS fmTotal FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2)= '" + year + "' AND duplicate = '' and gross_deliver != '' and unit_id = '" + unit + "' GROUP BY site_id ORDER BY site_id ASC ";
                PreparedStatement stmt2 = con.prepareStatement(query);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put(unit, rs2.getString("fmTotal"));
                    listResponse.add(data);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByDaysService.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(GetDataByDaysService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listResponse;
    }

    public String getMaxMonth(String site, String yy) {
        String response = null;
        con = connect();
        try {
            String query = "SELECT MAX(SUBSTR(finish,4,2) ) AS lenght FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(finish,7,2) = '" + yy + "' AND duplicate = '' and gross_deliver != '' and unit_id != ''  ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            response = rs.getString("lenght");

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByDaysService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

}
