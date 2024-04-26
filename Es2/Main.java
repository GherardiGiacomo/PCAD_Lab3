class Reader implements Runnable {
    private RWexclusive rw;

    public Reader(RWexclusive rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        int value = rw.read();
        System.out.println(Thread.currentThread().getName() + " - Valore letto: " + value);
    }
}

class Writer implements Runnable {
    private RWexclusive rw;

    public Writer(RWexclusive rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        rw.write();
        System.out.println(Thread.currentThread().getName() + " - Scrittura completata");
    }
}

public class Main {
    public static void main(String[] args) {
        RWexclusive rw = new RWexclusive(); // Utilizziamo RWexclusive anziché RWbasic
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

        // // Attende che tutti i thread del lettore finiscano
        for (Thread readerThread : readerThreads) {
            try {
                readerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // // Attende che tutti i thread dello scrittore finiscano
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
