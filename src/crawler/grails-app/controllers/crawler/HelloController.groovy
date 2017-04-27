package crawler

import java.net.InetAddress;

public class HelloController {
  def world(){
  def addr = InetAddress.getLocalHost();
  def hostname = addr.getHostName();
    render "Hello from ${addr} ${hostname}"
  }
}
