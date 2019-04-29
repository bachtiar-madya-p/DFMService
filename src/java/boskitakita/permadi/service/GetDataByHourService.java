/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.service;

import boskitakita.permadi.helper.CountFlowMeter;
import boskitakita.permadi.helper.GetFlowMeterUnit;
import boskitakita.permadi.helper.CountGrossFlow;
import boskitakita.permadi.entity.ModelByHour;
import boskitakita.permadi.iface.IGetDataByHourDataAccess;
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
@Path("/gbhour")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetDataByHourService extends EntityBase implements IGetDataByHourDataAccess<ModelByHour> {

    @GET
    @Path("tabled/data/{dd}/{mm}/{yy}/{site}")
    @Transactional
    @Override
    public List<ModelByHour> getDataTable(@PathParam("dd") String date, @PathParam("mm") String month, @PathParam("yy") String year, @PathParam("site") String site) {
        List<ModelByHour> listData = new ArrayList<>();
        try {
            con = connect();
            String query = "SELECT SUBSTRING(`finish`,1,8) AS date, `unit_id`, SUBSTR(`start`,10,17) AS start, SUBSTR(`finish`,10,17) AS finish,`sale_number`,`meter_number`,`gross_deliver` FROM `serial_data_results`"
                    + " WHERE SUBSTR(finish,1,8) = ? AND site_id = ? AND duplicate = '' and unit_id != '' AND gross_deliver !='' ORDER BY finish, sale_number ASC ";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, date + "/" + month + "/" + year);
            stmt.setString(2, site);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelByHour data = new ModelByHour();
                data.setDate(rs.getString("date"));
                data.setUnitId(rs.getString("unit_id"));
                data.setStart(rs.getString("start"));
                data.setFinish(rs.getString("finish"));
                data.setSaleNumber(rs.getString("sale_number"));
                data.setMeterNumber(rs.getString("meter_number"));
                data.setGrossDeliver(rs.getString("gross_deliver"));

                listData.add(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GetDataByHourService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(GetDataByHourService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listData;
    }

    @GET
    @Path("chart/data/{dd}/{mm}/{yy}/{site}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, HashMap<String, String>>> getDataChart(@PathParam("dd") String date, @PathParam("mm") String month, @PathParam("yy") String year, @PathParam("site") String site) {
        ArrayList<HashMap<String, HashMap<String, String>>> listResponse = new ArrayList<HashMap<String, HashMap<String, String>>>();
        con = connect();
        String dDate = date + "/" + month + "/" + year;
        try {
            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unit = rs.getString("unit");
                HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
                HashMap<String, String> gross = new HashMap<String, String>();
                for (int m = 0; m <= 23; m++) {
                    String time;
                    if (m <= 9) {
                        time = "0" + m;
                    } else {
                        time = Integer.toString(m);
                    }
                    String query = "SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results "
                            + "WHERE site_id = '"+site+"' AND SUBSTR(FINISH,1,8) = '"+dDate+"' and SUBSTR(finish,10,2) = '"+time+"' AND duplicate = '' and gross_deliver != '' and unit_id != '' and unit_id = '"+unit+"'  ";
                    PreparedStatement stmt2 = con.prepareStatement(query);
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        gross.put(time, rs2.getString("total"));
                    }
                }
                data.put(unit, gross);
                listResponse.add(data);

            }
        } catch (Exception ex) {
            Logger.getLogger(GetDataByHourService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(GetDataByHourService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listResponse;
    }

    @GET
    @Path("flow/data/{dd}/{mm}/{yy}/{site}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, String>> getByFlowmeter(@PathParam("dd") String date,
            @PathParam("mm") String month,
            @PathParam("yy") String year,
            @PathParam("site") String site
    ) {
        ArrayList<HashMap<String, String>> listResponse = new ArrayList<HashMap<String, String>>();
        con = connect();
        String dDate = date + "/" + month + "/" + year;
        try {
            String unitQuery = "SELECT DISTINCT(unit_id) as unit FROM serial_data_results "
                    + " WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8) = '" + dDate + "' AND duplicate = '' and meter_number != '' and unit_id != ''  ORDER BY unit_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String unit = rs.getString("unit");
                String query = "SELECT sum(gross_deliver) AS fmTotal FROM serial_data_results WHERE site_id = '" + site + "' AND SUBSTR(FINISH,1,8)= '" + dDate + "' AND duplicate = '' and gross_deliver != '' and unit_id = '" + unit + "' GROUP BY site_id ORDER BY site_id ASC ";
                PreparedStatement stmt2 = con.prepareStatement(query);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put(unit, rs2.getString("fmTotal"));
                    listResponse.add(data);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(GetDataByHourService.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(GetDataByHourService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listResponse;
    }

}
