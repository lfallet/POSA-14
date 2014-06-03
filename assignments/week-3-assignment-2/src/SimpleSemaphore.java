import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore implementation using Java a ReentrantLock and a ConditionObject. It must implement
 *        both "Fair" and "NonFair" semaphore semantics, just like Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Constructor initialize the data members.
     */
    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here
        mReentrantLock = new ReentrantLock(fair);
        noMoreSemaphore = mReentrantLock.newCondition();
        mAvailablePermitsCount = new SimpleAtomicLong(permits);
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
        mReentrantLock.lockInterruptibly();
        try {
            while (mAvailablePermitsCount.get() == 0) {
                noMoreSemaphore.await();
            }
            mAvailablePermitsCount.decrementAndGet();
        } finally {
            mReentrantLock.unlock();
        }
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
        mReentrantLock.lock();
        try {
            while (mAvailablePermitsCount.get() == 0) {
                noMoreSemaphore.awaitUninterruptibly();
            }
            mAvailablePermitsCount.decrementAndGet();
        } finally {
            mReentrantLock.unlock();
        }
    }

    /**
     * Return one permit to the semaphore.
     */
    public void release() {
        // TODO - you fill in here
        mReentrantLock.lock();
        try {
            noMoreSemaphore.signalAll();
            mAvailablePermitsCount.incrementAndGet();
        } finally {
            mReentrantLock.unlock();
        }
    }

    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    private ReentrantLock mReentrantLock;

    /**
     * Define a ConditionObject to wait while the number of permits is 0.
     */
    // TODO - you fill in here
    private Condition noMoreSemaphore;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here
    private SimpleAtomicLong mAvailablePermitsCount;
}
