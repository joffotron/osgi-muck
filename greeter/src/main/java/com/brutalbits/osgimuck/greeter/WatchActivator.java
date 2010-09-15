package com.brutalbits.osgimuck.greeter;

import com.brutalbits.osgimuck.greeter.api.Watch;
import java.util.Properties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class WatchActivator
  implements BundleActivator
{
  public void start( BundleContext ctx )
  {
    ctx.registerService( Watch.class.getName(), new WatchImpl(), new Properties() );
  }

  public void stop( BundleContext ctx )
  {
  }
}
