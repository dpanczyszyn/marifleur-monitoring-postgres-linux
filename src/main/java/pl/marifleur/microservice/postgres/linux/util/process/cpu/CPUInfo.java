package pl.marifleur.microservice.postgres.linux.util.process.cpu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CPUInfo {

    private double cpuUsage;
    private int numberOfCPUs;
}
