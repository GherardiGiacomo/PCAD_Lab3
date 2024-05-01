import java.util.concurrent.Semaphore;

public class RWext extends RWbasic {
    private int readerCount = 0;
    private boolean isWriting = false;
    private Semaphore semaphore = new Semaphore(0);

    @Override
    public synchronized int read() {
        while (isWriting) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interruzione del thread " + Thread.currentThread().getName());
            }
        }
        readerCount++;
        int tmp = super.read();
        readerCount--;
        if (readerCount == 0) {
            notifyAll();
        }
        if (semaphore.availablePermits() > 0) {
             try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interruzione del thread " + Thread.currentThread().getName());
            }
        }
        return tmp;
    }

    @Override
    public synchronized int write() {
        while (isWriting || readerCount > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interruzione del thread " + Thread.currentThread().getName());
            }
        }
        isWriting = true;
        int tmp = super.write();
        isWriting = false;
        semaphore.release();
        notifyAll();
        return tmp;
    }
}