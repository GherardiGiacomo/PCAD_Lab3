public class RW extends RWbasic {
    private int count = 0;
    private boolean isWriting = false;

    @Override
    public synchronized int read() {
        while (isWriting) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Errore: Il thread " + Thread.currentThread().getName()+"è stato interrotto");
            }
        }
        count++;
        int tmp = super.read();
        count--;
        if (count == 0) {
            notifyAll();
        }
        return tmp;
    }

    @Override
    public synchronized int write() {
        while (isWriting || count > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Errore: Il thread " + Thread.currentThread().getName()+"è stato interrotto");
            }
        }
        isWriting = true;
        int tmp = super.write();
        isWriting = false;
        notifyAll();
        return tmp;
    }
} 