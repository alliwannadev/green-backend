package alliwannadev.shop.supports.snowflake;

import java.time.Instant;

public class Snowflake {

	private static final int UNUSED_BITS = 1;
	private static final int TIMESTAMP_BITS = 41;
	private static final int DATACENTER_ID_BITS = 5;
	private static final int WORKER_ID_BITS = 5;
	private static final int SEQUENCE_BITS = 12;

	private static final long MAX_DATA_CENTER_ID = (1L << DATACENTER_ID_BITS) - 1;
	private static final long MAX_WORKER_ID = (1L << WORKER_ID_BITS) - 1;
	private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

	private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
	private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
	private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

	// Custom Epoch (2025-04-30T09:41:51.570716Z)
	private static final long DEFAULT_CUSTOM_EPOCH = 1746006111570L;

	private final long dataCenterId;
	private final long nodeId;
	private final long customEpoch;

	private volatile long sequence = 0L;
	private volatile long lastTimestamp = -1L;

	public Snowflake(
			long dataCenterId,
			long nodeId,
			long customEpoch
	) {
		if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
			throw new IllegalArgumentException(String.format("datacenterId must be between %d and %d", 0, MAX_DATA_CENTER_ID));
		}
		if (nodeId > MAX_WORKER_ID || nodeId < 0) {
			throw new IllegalArgumentException(String.format("workerId must be between %d and %d", 0, MAX_WORKER_ID));
		}

		this.dataCenterId = dataCenterId;
		this.nodeId = nodeId;
		this.customEpoch = customEpoch;
	}

	public Snowflake(
			long dataCenterId,
			long nodeId
	) {
		this(dataCenterId, nodeId, DEFAULT_CUSTOM_EPOCH);
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

		long id = ((currentTimestamp - customEpoch) << TIMESTAMP_LEFT_SHIFT)
				| (dataCenterId << DATA_CENTER_ID_SHIFT)
				| (nodeId << WORKER_ID_SHIFT)
				| sequence;

		return id;
	}

	private long getTimestamp() {
		return Instant.now().toEpochMilli();
	}

	private long waitNextMillis(long currentTimestamp) {
		while (currentTimestamp == lastTimestamp) {
			currentTimestamp = getTimestamp();
		}
		return currentTimestamp;
	}
}
