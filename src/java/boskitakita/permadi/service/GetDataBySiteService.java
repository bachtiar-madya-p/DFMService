/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.service;

import boskitakita.permadi.entity.ModelByMonth;
import boskitakita.permadi.entity.ModelBySite;
import boskitakita.permadi.iface.IGetDataBySiteDataAccess;
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
@Path("/gbsite")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetDataBySiteService extends EntityBase implements IGetDataBySiteDataAccess<ModelBySite> {

    @GET
    @Path("tabled/data/{yy}")
    @Transactional
    @Override
    public List<ModelBySite> getDataTable(@PathParam("yy") String year) {

        List<ModelBySite> listResponse = new ArrayList<>();
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(site_id) as site FROM serial_data_results"
                    + " WHERE SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and site_id != '' ORDER BY site_id ASC ";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String site = rs.getString("site");
                String query = "SELECT IFNULL(SUM(gross_deliver),0) AS total1, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '02') AS total2, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '03') AS total3, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '04') AS total4, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '05') AS total5, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '06') AS total6, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '07') AS total7, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '08') AS total8, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '09') AS total9, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '10') AS total10, "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '11') AS total11,  "
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '12') AS total12  "
                        + "FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND SUBSTR(FINISH,4,2) = '01'";
                PreparedStatement stmt2 = con.prepareStatement(query);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    ModelBySite data = new ModelBySite();
                    data.setSiteId(site);
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
    @Path("chart/data/{yy}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, HashMap<String, String>>> getDataChart(@PathParam("yy") String year) {
        ArrayList<HashMap<String, HashMap<String, String>>> listResponse = new ArrayList<HashMap<String, HashMap<String, String>>>();
        con = connect();
        try {

            String unitQuery = "SELECT DISTINCT(site_id) as site FROM serial_data_results "
                    + " WHERE SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String site = rs.getString("site");
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
                            + " WHERE site_id = '" + site + "' AND SUBSTR(finish,4,2) = '" + month + "' AND SUBSTR(finish,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != '' AND duplicate = '' and meter_number != '' ";
                    PreparedStatement stmt2 = con.prepareStatement(query);
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        gross.put(month, rs2.getString("total"));
                    }
                }
                data.put(site, gross);
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
    @Path("flow/data/{yy}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, String>> getByFlowmeter(@PathParam("yy") String year) {
        ArrayList<HashMap<String, String>> listResponse = new ArrayList<HashMap<String, String>>();
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(site_id) as site FROM serial_data_results"
                    + " WHERE SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and site_id != '' ORDER BY site_id ASC ";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String site = rs.getString("site");
                String query = "SELECT sum(gross_deliver) AS fmTotal FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2)= '" + year + "' AND duplicate = '' and gross_deliver != '' GROUP BY  SUBSTR(FINISH,7,2) ORDER BY site_id ASC ";
                PreparedStatement stmt2 = con.prepareStatement(query);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put(site, rs2.getString("fmTotal"));
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

}
