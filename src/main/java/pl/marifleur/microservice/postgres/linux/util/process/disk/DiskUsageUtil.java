package pl.marifleur.microservice.postgres.linux.util.process.disk;

import pl.marifleur.microservice.postgres.linux.util.process.ProcessUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DiskUsageUtil {

    private final ProcessUtil processUtil = new ProcessUtil();

    public String getCurrentPartitionName() {
        return processUtil.run("df -h . | awk 'NR==2 {print $1}'").trim();
    }

    public String getCurrentPartitionName(String username, String password) {
        return processUtil.runAsUser(username, password, "df -h . | awk 'NR==2 {print $1}'").trim();
    }

    public DiskUsage getPartitionDiskUsage(String partitionName) {
        String diskUsageAsString = processUtil.run("df -k \"" + partitionName + "\" | awk 'NR==2'");
        return getDiskUsageDTOFromString(diskUsageAsString);
    }

    public DiskUsage getCurrentPartitionDiskUsage() {
        return getPartitionDiskUsage(getCurrentPartitionName());
    }

    private DiskUsage getDiskUsageDTOFromString(String diskUsageAsString) {
        DiskUsage diskUsage = new DiskUsage();

        List<String> diskUsageParams = new ArrayList<>(List.of(diskUsageAsString.split(" ")));
        diskUsageParams.removeIf(s -> s.trim().isEmpty());

        if (diskUsageParams.size() >= 5) {
            diskUsage.setPartitionName(diskUsageParams.get(0));
            diskUsage.setTotal(Long.parseLong(diskUsageParams.get(1)));
            diskUsage.setUsed(Long.parseLong(diskUsageParams.get(2)));
            diskUsage.setAvailable(Long.parseLong(diskUsageParams.get(3)));
            double usagePercentage = (double) diskUsage.getUsed() / diskUsage.getTotal() * 100;
            BigDecimal bigDecimal = new BigDecimal(usagePercentage).setScale(2, RoundingMode.HALF_UP);
            diskUsage.setUsagePercentage(bigDecimal.doubleValue());
            return diskUsage;
        } else {
            throw new IndexOutOfBoundsException("error while getting disk usage data");
        }
    }
}
