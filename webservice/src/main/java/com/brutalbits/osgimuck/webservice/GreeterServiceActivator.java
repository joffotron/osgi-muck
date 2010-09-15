package com.brutalbits.osgimuck.webservice;

import com.brutalbits.osgimuck.greeter.api.Greeter;
import javax.xml.ws.Endpoint;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class GreeterServiceActivator
  implements BundleActivator
{
  private Greeter _greeterDataProvider;

  @Override
  public void start( final BundleContext context )
    throws Exception
  {
    System.out.println( "Starting GreeterServiceActivator Webservice" );
    final ServiceReference greeterServiceRef = context.getServiceReference( Greeter.class.getName() );
    if ( greeterServiceRef != null )
    {
      _greeterDataProvider = (Greeter) context.getService( greeterServiceRef );
    }

    System.out.println( "publishing endpoint with: "  + _greeterDataProvider.getGreeting() );
    Endpoint.publish( "http://localhost:8080/GreeterService", new GreeterService( _greeterDataProvider ) );
  }

  @Override
  public void stop( final BundleContext context )
    throws Exception
  {
  }
}
