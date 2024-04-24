public class RWbasic {
    private int data = 0;
    int tmp = 0;

    protected int read() {
        return data;
    }

    protected int write() {
        tmp = data;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp = tmp + 1;
        data = tmp;
        return data;
    }

    public static class Reader implements Runnable {
        private RWbasic rw;
        private int id;

        public Reader(RWbasic rw, int id) {
            this.rw = rw;
            this.id = id;
        }

        @Override
        public void run() {
            int i = rw.read();
            System.out.println("Thread lettore " + id + " ha letto il valore " + i);
        }
    }

    public static class Writer implements Runnable {
        private RWbasic rw;
        private int id;

        public Writer(RWbasic rw, int id) {
            this.rw = rw;
            this.id = id;
        }

        @Override
        public void run() {
            int writtenValue = rw.write();
            System.out.println("Il thread scrittore " + id + " incrementa di 1 il valore di data che in questo momento vale:" + writtenValue);
        }
    }

    public static void main(String[] args) {
        RW rw = new RW();
        Thread[] threads = new Thread[100]; 
        for (int i = 0; i < 50; i++) { 
            threads[i] = new Thread(new Reader(rw, i), "Reader " + i);
            threads[i + 50] = new Thread(new Writer(rw, i), "Writer " + i);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Il valore finale di data é: " + rw.read());
    }
}