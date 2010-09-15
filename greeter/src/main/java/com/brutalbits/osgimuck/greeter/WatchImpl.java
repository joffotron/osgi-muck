package com.brutalbits.osgimuck.greeter;

import com.brutalbits.osgimuck.greeter.api.Watch;

class WatchImpl
  implements Watch
{
  public String currentTime()
  {
    return new java.util.Date().toString();
  }
}
