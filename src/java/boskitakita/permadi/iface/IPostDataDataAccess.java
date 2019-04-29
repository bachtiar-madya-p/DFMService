/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.iface;

import javax.ws.rs.core.Response;

/**
 *
 * @author Hello Permadi
 */
public interface IPostDataDataAccess <T> {
    
    public Response create(T entity);
    
}
