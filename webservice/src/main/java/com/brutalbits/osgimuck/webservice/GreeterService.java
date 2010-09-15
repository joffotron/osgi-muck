package com.brutalbits.osgimuck.webservice;

import com.brutalbits.osgimuck.greeter.api.Greeter;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService( targetNamespace = "http://brutalbits.com/osgimuck" )
public class GreeterService
{
  private Greeter _greeter;

  public GreeterService()
  {
    //nop
    System.out.println( "GreeterService.GreeterService - " + System.identityHashCode( this ) );
  }

  public GreeterService( final Greeter greeterDataProvider )
  {
    System.out.println( "GreeterService.GreeterService with provider: " + greeterDataProvider.getGreeting() +
                        " - " + System.identityHashCode( this ) );
    _greeter = greeterDataProvider;
  }

  @WebMethod
  public String remoteGreet()
  {
    throw new RuntimeException( "Fuck you!");
    //System.out.println( "GreeterService.remoteGreet - " + System.identityHashCode( this ));
    //if ( _greeter != null )
    //{
    //  return _greeter.getGreeting();
    //}
    //else
    //{
    //  return "No Greeter Service!";
    //}
  }

}
