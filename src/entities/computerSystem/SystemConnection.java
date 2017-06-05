package entities.computerSystem;

import java.io.Serializable;

/**
 * Created by hadgehog on 20.02.14.
 */
public class SystemConnection implements Serializable {
    public static double DEFAULT_BANDWIDTH = 1;
    public static int DEFAULT_PHYSICAL_LINK_NUMBER = 1;
    public static boolean DUPLEX = true;
    private double bandwidth = 0;
    private int fromProcessorId;
    private int toProcessorId;

    public SystemConnection(int fromProcessorId, int toProcessorId, double bandwidth) {
        this(fromProcessorId, toProcessorId);
        this.bandwidth = bandwidth;
    }

    public SystemConnection(int fromProcessorId, int toProcessorId) {
        this.bandwidth = DEFAULT_BANDWIDTH;
        this.fromProcessorId = fromProcessorId;
        this.toProcessorId = toProcessorId;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public int getFromProcessorId() {
        return fromProcessorId;
    }

    public int getToProcessorId() {
        return toProcessorId;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

    public static boolean isDUPLEX() {
        return DUPLEX;
    }

    public static void setDUPLEX(boolean DUPLEX) {
        SystemConnection.DUPLEX = DUPLEX;
    }
}
