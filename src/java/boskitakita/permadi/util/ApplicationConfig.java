/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boskitakita.permadi.util;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author root
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(MultiPartFeature.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {

        resources.add(boskitakita.permadi.service.GetDataByDaysService.class);
        resources.add(boskitakita.permadi.service.GetDataByHourService.class);
        resources.add(boskitakita.permadi.service.GetDataByMonthService.class);
        resources.add(boskitakita.permadi.service.GetDataBySiteService.class);
        resources.add(boskitakita.permadi.service.GetDataByYearService.class);
        resources.add(boskitakita.permadi.service.LoginService.class);
        resources.add(boskitakita.permadi.service.PostDataService.class);
        resources.add(boskitakita.permadi.service.RegisterService.class);

    }

}
