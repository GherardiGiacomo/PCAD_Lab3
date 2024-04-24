public class RW extends RWbasic {
    private int readerCount = 0;
    private boolean isWriting = false;

    @Override
    public synchronized int read() {
        while (isWriting) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        readerCount++;
        int data = super.read();
        readerCount--;
        if (readerCount == 0) {
            notifyAll();
        }
        return data;
    }

    @Override
    public synchronized int write() {
        while (isWriting || readerCount > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isWriting = true;
        int data = super.write();
        isWriting = false;
        notifyAll();
        return data;
    }
}