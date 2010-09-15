package com.brutalbits.osgimuck.webservice;

import com.brutalbits.osgimuck.greeter.api.Watch;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;
import org.osgi.framework.ServiceReference;

@WebService
public class WatchWebService {

   @WebMethod public String currentTime() {
       Watch watch = getService(Watch.class);
       System.out.println("WatchService: OSGi service is: " + watch);
       if (watch == null) {
          return "I don't have a watch";
       } else {
          return watch.currentTime();
       }
   }

   /**
     * This method looks up service of given type in OSGi service registry and returns if found.
     * Returns null if no such service is available,
     */
   private static <T> T getService(Class<T> type) {
       BundleContext ctx = BundleReference.class.cast(WatchWebService.class.getClassLoader()).getBundle().getBundleContext();
       ServiceReference ref = ctx.getServiceReference(type.getName());
       return ref != null ? type.cast(ctx.getService(ref)) : null;
   }
}
