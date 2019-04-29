/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.iface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.PathParam;

/**
 *
 * @author Hello Permadi
 */
public interface IGetDataBySiteDataAccess<T> {

    public List<T> getDataTable(@PathParam("yy") String year);

    public ArrayList<HashMap<String, HashMap<String, String>>> getDataChart(@PathParam("yy") String year);

    public ArrayList<HashMap<String, String>> getByFlowmeter(@PathParam("yy") String year);

}
