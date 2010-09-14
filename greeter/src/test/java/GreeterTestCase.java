import com.brutalbits.osgimuck.greeter.api.Greeter;
import com.brutalbits.osgimuck.greeter.HelloWorldGreeter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GreeterTestCase
{
  private Greeter _greeter;

  @BeforeMethod
  public void setUp()
  {
    _greeter = new HelloWorldGreeter();
  }

  @Test
  public void greeterReturnsGreeting()
  {
    assertEquals( _greeter.getGreeting(), "Hello, World!" );
  }

}
