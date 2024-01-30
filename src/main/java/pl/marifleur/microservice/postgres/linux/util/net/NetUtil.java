package pl.marifleur.microservice.postgres.linux.util.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.stream.Stream;

public class NetUtil {

    public String getLocalHostIpAddress() throws SocketException {
        Stream<NetworkInterface> networkInterfaces = NetworkInterface.networkInterfaces();
        for (NetworkInterface networkInterface : networkInterfaces.toList()) {
            if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        }
        return null;
    }
}
