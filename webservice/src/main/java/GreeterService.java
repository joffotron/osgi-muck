import com.brutalbits.osgimuck.greeter.api.Greeter;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

@WebService( targetNamespace = "http://brutalbits.com/osgimuck" )
public class GreeterService
  implements BundleActivator
{
  private Greeter _greeter;

  @WebMethod
  public String remoteGreet()
  {
    if ( _greeter != null )
    {
      return _greeter.getGreeting();
    }
    else
    {
      return "No Greeter Service!";
    }
  }

  @Override
  public void start( final BundleContext context )
    throws Exception
  {
    final ServiceReference greeterServiceRef = context.getServiceReference( Greeter.class.getName() );
    if ( greeterServiceRef != null )
    {
      _greeter = (Greeter) context.getService( greeterServiceRef );
    }
  }

  @Override
  public void stop( final BundleContext context )
    throws Exception
  {
    _greeter = null;
  }
}
