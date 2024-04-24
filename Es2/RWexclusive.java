public class RWexclusive extends RWbasic {
    @Override
    public synchronized int read() {
        return super.read();
    }

    @Override
    public synchronized int write() {
        return super.write();
    }
}