package alliwannadev.shop.supports.distributedlock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class DistributedLockExecutor {

    private final RedissonClient redissonClient;

    public void execute(
            String lockName,
            long waitMilliSecond,
            long leaseMilliSecond,
            Runnable logic
    ) {
        RLock lock = redissonClient.getLock(lockName);
        try {
            boolean isLocked = lock.tryLock(
                    waitMilliSecond,
                    leaseMilliSecond,
                    TimeUnit.MILLISECONDS
            );
            if (!isLocked) {
                throw new IllegalStateException("[%s] lock 획득 실패".formatted(lockName));
            }

            logic.run();
        } catch (InterruptedException ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
