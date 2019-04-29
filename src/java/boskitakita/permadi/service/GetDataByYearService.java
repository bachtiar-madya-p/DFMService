/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.service;

import boskitakita.permadi.entity.ModelBySite;
import boskitakita.permadi.entity.ModelByYear;
import boskitakita.permadi.iface.IGetDataByYearDataAccess;
import boskitakita.permadi.util.EntityBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
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
@Path("/gbyear")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetDataByYearService extends EntityBase implements IGetDataByYearDataAccess<ModelByYear> {

    @GET
    @Path("tabled/data/{year}")
    @Transactional
    @Override
    public List<ModelByYear> getDataTable(@PathParam("year") String year) {
        List<ModelByYear> listResponse = new ArrayList<>();
        con = connect();
        try {
            String unitQuery = "SELECT DISTINCT(site_id) as site FROM serial_data_results "
                    + "WHERE  SUBSTR(FINISH,7,2) = '" + year + "' AND duplicate = '' and meter_number != '' and site_id != ''  ORDER BY site_id ASC";
            PreparedStatement stmt = con.prepareStatement(unitQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DateFormat dateFormat = new SimpleDateFormat("y");
                Date date = new Date();
                int dYear = Integer.parseInt(dateFormat.format(date));
                String thisYear = String.valueOf(dYear).substring(2);
                String scnYear = String.valueOf(dYear - 1).substring(2);
                String thrYear = String.valueOf(dYear - 2).substring(2);
                String fhrYear = String.valueOf(dYear - 3).substring(2);
                String vhrYear = String.valueOf(dYear - 4).substring(2);
                System.out.println(thisYear);
                String site = rs.getString("site");
                String query = "SELECT IFNULL(SUM(gross_deliver),0) AS total1,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE duplicate = '' and meter_number != '' and unit_id != '' AND SUBSTR(start,7,2) = '" + scnYear + "'  AND site_id = '" + site + "') AS total2,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE duplicate = '' and meter_number != '' and unit_id != '' AND SUBSTR(start,7,2) = '" + thrYear + "'  AND site_id = '" + site + "') AS total3,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE duplicate = '' and meter_number != '' and unit_id != '' AND SUBSTR(start,7,2) = '" + fhrYear + "'  AND site_id = '" + site + "') AS total4,"
                        + "(SELECT IFNULL(SUM(gross_deliver),0) AS total FROM serial_data_results WHERE duplicate = '' and meter_number != '' and unit_id != '' AND SUBSTR(start,7,2) = '" + vhrYear + "'  AND site_id = '" + site + "') AS total5 "
                        + "FROM serial_data_results WHERE SUBSTR(start,7,2) = '" + thisYear + "' AND duplicate = '' and meter_number != '' and unit_id != ''  AND site_id = '" + site + "'";
                PreparedStatement stmt2 = con.prepareStatement(query);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    ModelByYear data = new ModelByYear();
                    data.setSiteId(site);
                    data.setFstyear(rs2.getString("total1"));
                    data.setScdyear(rs2.getString("total2"));
                    data.setThryear(rs2.getString("total3"));
                    data.setFhryear(rs2.getString("total4"));
                    data.setVhryear(rs2.getString("total5"));

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
    @Path("chart/data/{year}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, HashMap<String, String>>> getDataChart(@PathParam("year") String year) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @GET
    @Path("flow/data/{year}")
    @Transactional
    @Override
    public ArrayList<HashMap<String, String>> getByFlowmeter(@PathParam("year") String year) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
