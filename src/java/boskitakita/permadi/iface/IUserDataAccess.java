/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.iface;

import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Hello Permadi
 */
public interface IUserDataAccess<T> {

    public Response create(T entity);

    public Response edit(T entity);

    public Response remove(@PathParam("id") Integer id);

    public List<T> findByName(@PathParam("name") String name);

    public List<T> findByUserName(@PathParam("username") String username);

    public List<T> findAll();

}
