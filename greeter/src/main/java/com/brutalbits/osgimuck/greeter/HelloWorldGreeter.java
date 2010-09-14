package com.brutalbits.osgimuck.greeter;

import com.brutalbits.osgimuck.greeter.api.Greeter;

public class HelloWorldGreeter
  implements Greeter
{
  private final static String GREETING = "Hello, World!";

  @Override
  public String getGreeting()
  {
    return GREETING;
  }

  @Override
  public void printGreeting()
  {
    System.out.println( GREETING );
  }
}
