package com.brutalbits.osgimuck.greeter;

import com.brutalbits.osgimuck.greeter.api.Greeter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class GreetingActivator
  implements BundleActivator
{
  private ServiceRegistration _registration;

  @Override
  public void start( final BundleContext context )
    throws Exception
  {
    System.out.println( "Starting Greeter Service" );
    _registration = context.registerService( Greeter.class.getName(), new HelloWorldGreeter(), null );
  }

  @Override
  public void stop( final BundleContext context )
    throws Exception
  {
    System.out.println( "Shutting down greeter :(" );
    _registration.unregister();
  }
}
