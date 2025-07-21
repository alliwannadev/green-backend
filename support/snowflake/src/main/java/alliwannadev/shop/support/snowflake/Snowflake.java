package alliwannadev.shop.support.snowflake;

import java.time.Instant;

public class Snowflake {

    public static final int NODE_COUNT = 1024;

    private static final int UNUSED_BITS = 1; // Sign bit, Unused (always set to 0)
    private static final int TIMESTAMP_BITS = 41;
    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final long MAX_NODE_ID = (1L << NODE_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    // Custom Epoch (2025-04-30T09:41:51.570716Z)
    private static final long DEFAULT_CUSTOM_EPOCH = 1746006111570L;

    private final long nodeId;

    private volatile long sequence = 0L;
    private volatile long lastTimestamp = -1L;

    public Snowflake(long nodeId) {
        if (nodeId < 0 || nodeId > MAX_NODE_ID) {
            throw new IllegalArgumentException(String.format("nodeId must be between %d and %d", 0, MAX_NODE_ID));
        }

        this.nodeId = nodeId;
    }

    public synchronized long nextId() {
        long currentTimestamp = getTimestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Clock is Moving Backwards!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        long id = currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS)
                | (nodeId << SEQUENCE_BITS)
                | sequence;

        return id;
    }

    private long getTimestamp() {
        return Instant.now().toEpochMilli() - DEFAULT_CUSTOM_EPOCH;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = getTimestamp();
        }

        return currentTimestamp;
    }
}
