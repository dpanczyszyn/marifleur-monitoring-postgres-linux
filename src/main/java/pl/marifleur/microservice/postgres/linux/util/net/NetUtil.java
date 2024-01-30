package pl.marifleur.microservice.postgres.linux.util.net;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;
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

    public String getMicroserviceBaseUrl(ServerProperties serverProperties) throws Exception {
        if (Objects.nonNull(serverProperties)) {
            String contextPath = serverProperties.getServlet().getContextPath();
            String port = String.valueOf(serverProperties.getPort());

            if (Objects.nonNull(contextPath)) {
                return "http://" + getLocalHostIpAddress() + ":" + port + contextPath;
            } else {
                return "http://" + getLocalHostIpAddress() + ":" + port;
            }
        } else {
            throw new RuntimeException("server properties is null");
        }
    }
}
