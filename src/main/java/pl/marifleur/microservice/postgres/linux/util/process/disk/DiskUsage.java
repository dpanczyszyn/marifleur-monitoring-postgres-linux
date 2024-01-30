package pl.marifleur.microservice.postgres.linux.util.process.disk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiskUsage {

    private String partitionName;
    private long total;
    private long used;
    private long available;
    private double usagePercentage;

    @Override
    public String toString() {
        return "DiskUsageDTO{" +
                "partitionName='" + partitionName + '\'' +
                ", total='" + total + '\'' +
                ", used='" + used + '\'' +
                ", available='" + available + '\'' +
                ", usagePercentage='" + usagePercentage + '\'' +
                '}';
    }
}
