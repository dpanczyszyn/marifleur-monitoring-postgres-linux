package pl.marifleur.microservice.postgres.linux.util.process.cpu;

import pl.marifleur.microservice.postgres.linux.util.process.ProcessUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CPUInfoUtil {

    private final ProcessUtil processUtil = new ProcessUtil();

    public double getCPUUsage() {
        String cpuIdleAsString = processUtil.run("mpstat -P ALL | grep all | awk '{print $12}'");
        cpuIdleAsString = cpuIdleAsString.replace(",", ".");
        double cpuUsage = (100 - Double.parseDouble(cpuIdleAsString));
        BigDecimal bigDecimal = new BigDecimal(cpuUsage).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public int getNumberOfCPUs() {
        String numberOfCPUsAsString = processUtil.run("lscpu | grep 'CPU(s):' | awk 'NR == 1 {print $2}'").trim();
        return Integer.parseInt(numberOfCPUsAsString);
    }
}
