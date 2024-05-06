public class RWbasic {
    protected int data; 

    public RWbasic() {
        this.data = 0;
    }

    public int read() {
        return data;
    }

    public int write() {
        int tmp = data;
        tmp++;
        try {
            Thread.sleep((int) (Math.random() * 100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interruzione del thread " + Thread.currentThread().getName());
        }
        data = tmp;
        return tmp;
    }

    public static class Reader implements Runnable {
        private RWexclusive rw;

        public Reader(RWexclusive rw2) {
            this.rw = rw2;
        }

        public void run() {
            this.rw.read();
        }
    }

    public static class Writer implements Runnable {
        private RWexclusive rw;

        public Writer(RWexclusive rw2) {
            this.rw = rw2;
        }

        public void run() {
            int tmp = this.rw.write();
            System.out.println(Thread.currentThread().getName() +" scrive. data ora vale: " + tmp);
        }
    }

  public static void main(String[] args) {
        RWexclusive rw = new RWexclusive();
        int N_THREADS = 50;

        Thread[] Lettori = new Thread[N_THREADS];
        Thread[] Scrittori = new Thread[N_THREADS];

        for (int i = 0; i < N_THREADS; i++) {
            Scrittori[i] = new Thread(new Writer(rw), "Scrittore n" + i);
            Scrittori[i].start();

            try {
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < N_THREADS; i++) {
            try {
                Scrittori[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < N_THREADS; i++) {
            Lettori[i] = new Thread(new Reader(rw) ,"Lettore n"+ i);
            Lettori[i].start();
        }

        for (int i = 0; i < N_THREADS; i++) {
            try {
                Lettori[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Il valore finale di data: " + rw.read());
    }
} 
