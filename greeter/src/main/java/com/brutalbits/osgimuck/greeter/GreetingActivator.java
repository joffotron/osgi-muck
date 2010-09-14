package com.brutalbits.osgimuck.greeter;

import com.brutalbits.osgimuck.greeter.api.Greeter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class GreetingActivator
  implements BundleActivator
{

  @Override
  public void start( final BundleContext context )
    throws Exception
  {
    System.out.println( "Starting Greeter Service" );
    context.registerService( Greeter.class.getName(), new HelloWorldGreeter(), null );
  }

  @Override
  public void stop( final BundleContext context )
    throws Exception
  {
    System.out.println( "Shutting down greeter :(" );
  }
}
