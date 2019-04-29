/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.service;

import boskitakita.permadi.entity.ModelByDay;
import boskitakita.permadi.entity.ModelByHour;
import boskitakita.permadi.entity.ModelByMonth;
import boskitakita.permadi.helper.CountFlowMeter;
import boskitakita.permadi.helper.GetFlowMeterUnit;
import boskitakita.permadi.helper.CountGrossFlow;
import boskitakita.permadi.iface.IGetDataByDayDataAccess;
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
@Path("/gbday")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetDataByDaysService extends EntityBase implements IGetDataByDayDataAccess<ModelByDay> {

    @GET
    @Path("tabled/data/{mm}/{yy}/{site}")
    @Transactional
    @Override
    public List<ModelByDay> getDataTable(@PathParam("mm") String month, @PathParam("yy") String year, @PathParam("site") String site) {
        String dDate = month + "/" + year;
        List<ModelByDay> listResponse = new ArrayList<>();
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(finish,4,6) = '" + dDate + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            List<String> unit = new ArrayList<>();
            while (rs.next()) {
                unit.add(rs.getString("unit"));
            }
            Integer dd = getMaxDay(site, month, year);
            for (int d = 1; d <= dd; d++) {
                String day;
                if (d <= 9) {
                    day = "0" + d;
                } else {
                    day = Integer.toString(d);
                }
                String query = "select site_id AS site,  SUBSTR(finish,1,2) AS day,"
                        + "(SELECT  IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(finish,7,2) = '" + year + "' AND SUBSTR(finish,1,2) = '" + day + "' AND site_id = '" + site + "' AND duplicate = '' AND meter_number != '' AND unit_id = '" + unit.get(0) + "' ) AS FM1Total,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(finish,7,2) = '" + year + "' AND SUBSTR(finish,1,2) = '" + day + "' AND site_id = '" + site + "' AND duplicate = '' AND meter_number != '' AND unit_id = '" + unit.get(1) + "' ) AS FM2Total,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(finish,7,2) = '" + year + "' AND SUBSTR(finish,1,2) = '" + day + "' AND site_id = '" + site + "' AND duplicate = '' AND meter_number != '' AND unit_id = '" + unit.get(2) + "' ) AS FM3Total,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(finish,7,2) = '" + year + "' AND SUBSTR(finish,1,2) = '" + day + "' AND site_id = '" + site + "' AND duplicate = '' AND meter_number != '' AND unit_id = '" + unit.get(3) + "' ) AS FM4Total "
                        + "FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(finish,1,2) = '" + day + "' AND substr(finish,4,2) = '" + month + "'  AND SUBSTR(finish,7,2) = '" + year + "' AND finish != '' GROUP BY SUBSTR(finish,1,2)";
                PreparedStatement stmt2 = con.prepareStatement(query);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    ModelByDay data = new ModelByDay();
                    data.setsSite(rs2.getString("site"));
                    data.setDay(day);
                    data.setFm1Gross(rs2.getString("FM1Total"));
                    data.setFm2Gross(rs2.getString("FM2Total"));
                    data.setFm3Gross(rs2.getString("FM3Total"));
                    data.setFm4Gross(rs2.getString("FM4Total"));

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
    @Path("chart/data/{mm}/{yy}/{site}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, HashMap<String, String>>> getDataChart(@PathParam("mm") String month, @PathParam("yy") String year, @PathParam("site") String site) {
        ArrayList<HashMap<String, HashMap<String, String>>> listResponse = new ArrayList<HashMap<String, HashMap<String, String>>>();
        String dDate = month + "/" + year;
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unit = rs.getString("unit");
                HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
                HashMap<String, String> gross = new HashMap<String, String>();
                Integer dd = getMaxDay(site, month, year);
                for (int d = 1; d <= dd; d++) {
                    String day;
                    if (d <= 9) {
                        day = "0" + d;
                    } else {
                        day = Integer.toString(d);
                    }
                    String query = "SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(finish,1,2) = '" + day + "' AND SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(finish,7,2) = '" + year + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' and unit_id = '" + unit + "' ";
                    PreparedStatement stmt2 = con.prepareStatement(query);
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        gross.put(day, rs2.getString("total"));
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
    @Path("flow/data/{mm}/{yy}/{site}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, String>> getByFlowmeter(@PathParam("mm") String month, @PathParam("yy") String year, @PathParam("site") String site) {
        ArrayList<HashMap<String, String>> listResponse = new ArrayList<HashMap<String, String>>();
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unit = rs.getString("unit");
                String query = "SELECT sum(gross_deliver) AS fmTotal FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(FINISH,7,2)= '" + year + "' AND duplicate = '' and gross_deliver != '' and unit_id = '" + unit + "' GROUP BY site_id ORDER BY site_id ASC ";
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

    public int getMaxDay(String site, String mm, String yy) {
        int response = 0;
        con = connect();
        try {
            String query = "SELECT MAX(SUBSTR(finish,1,2) ) AS lenght FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(finish,4,2) = '" + mm + "' AND SUBSTR(finish,7,2) = '" + yy + "' AND duplicate = '' and gross_deliver != '' and unit_id != '' ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            response = rs.getInt("lenght");

        } catch (SQLException ex) {
            Logger.getLogger(GetDataByDaysService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

}
