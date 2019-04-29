/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.service;

import boskitakita.permadi.entity.SerialDataResults;
import boskitakita.permadi.iface.IPostDataDataAccess;
import boskitakita.permadi.util.EntityBase;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hello Permadi
 */
@Path("/postdata")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostDataService extends EntityBase implements IPostDataDataAccess<SerialDataResults> {

    @POST
    @Path("/create")
    @Transactional
    @Override
    public Response create(SerialDataResults entity) {
        try {
            con = connect();
            String query = "INSERT INTO `serial_data_results`(`uploaded`, `site_id`, `ID_start`, `ID_end`, `data_state`, `ticket_no`, `start`, `finish`, `start_count`, `start_count_uom`, `end_count`, `end_count_uom`,"
                    + " `gross_deliver`, `gross_deliver_uom`, `avg_flow_rate`, `avg_flow_rate_uom`, `after_avg_flow_rate`, `sale_number`, `meter_number`, `unit_id`, `duplicate`, `other_one`, `other_two`, `other_three`, `other_four`, `other_five`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, entity.getUploaded());
            stmt.setString(2, entity.getSiteId());
            stmt.setLong(3, entity.getIDstart());
            stmt.setLong(4, entity.getIDend());
            stmt.setString(5, entity.getDataState());   
            stmt.setString(6, entity.getTicketNo());
            stmt.setString(7, entity.getStart());
            stmt.setString(8, entity.getFinish());
            stmt.setString(9, entity.getStartCount());
            stmt.setString(10, entity.getStartCountUom());
            stmt.setString(11, entity.getEndCount());
            stmt.setString(12, entity.getEndCountUom());
            stmt.setString(13, entity.getGrossDeliver());
            stmt.setString(14, entity.getGrossDeliverUom());
            stmt.setString(15, entity.getAvgFlowRate());
            stmt.setString(16, entity.getAvgFlowRateUom());
            stmt.setString(17, entity.getAfterAvgFlowRate());
            stmt.setString(18, entity.getSaleNumber());
            stmt.setString(19, entity.getMeterNumber());
            stmt.setString(20, entity.getUnitId());
            stmt.setString(21, entity.getDuplicate());
            stmt.setString(22, entity.getOtherOne());
            stmt.setString(23, entity.getOtherTwo());
            stmt.setString(24, entity.getOtherThree());
            stmt.setString(25, entity.getOtherFour());
            stmt.setString(26, entity.getOtherFive());

            int rs = stmt.executeUpdate();
            System.out.println(rs + " records inserted");
            if (rs != 0) {
                return Response.status(Response.Status.CREATED).build();

            } else {
                disconect();
                return Response.status(Response.Status.EXPECTATION_FAILED).build();
            }

        } catch (SQLException ex) {
            disconect();
            Logger.getLogger(PostDataService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
