class RWbasic {
    private int data = 0;

    public int read() {
        return data;
    }

    public void write() {
        int tmp = data; // (a) Legge il valore di data
        tmp++; // (b) Aumenta di 1 il valore
        // Simula un ritardo durante la scrittura
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data = tmp; // (c) Assegna il nuovo valore a data
    }

    public static class Reader implements Runnable {
        private RWbasic rw;

        public Reader(RWbasic rw) {
            this.rw = rw;
        }

        @Override
        public void run() {
            int value = rw.read();
            System.out.println(Thread.currentThread().getName() + " - Valore letto: " + value);
        }
    }

    public static class Writer implements Runnable {
        private RWbasic rw;

        public Writer(RWbasic rw) {
            this.rw = rw;
        }

        @Override
        public void run() {
            rw.write();
            System.out.println(Thread.currentThread().getName() + " - Scrittura completata");
        }
    }

    public static void main(String[] args) {
        RWbasic rw = new RWbasic();
        int numReaders = 50;
        int numWriters = 50;

        Thread[] readerThreads = new Thread[numReaders];
        for (int i = 0; i < numReaders; i++) {
            readerThreads[i] = new Thread(new Reader(rw), "Lettore-" + i);
            readerThreads[i].start();
        }

        Thread[] writerThreads = new Thread[numWriters];
        for (int i = 0; i < numWriters; i++) {
            writerThreads[i] = new Thread(new Writer(rw), "Scrittore-" + i);
            writerThreads[i].start();
        }

        // Attende che tutti i thread del lettore finiscano
        for (Thread readerThread : readerThreads) {
            try {
                readerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Attende che tutti i thread dello scrittore finiscano
        for (Thread writerThread : writerThreads) {
            try {
                writerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Valore finale del data: " + rw.read());
    }
}
