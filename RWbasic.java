public class RWbasic {
    private int data = 0;

    int read() {
        return data;
    }

    private void write() {
        int tmp;
        tmp = data;
        tmp = tmp + 1;
        data = tmp;
    }

    public static class Reader implements Runnable {
        private RWbasic rw;
        private int id;

        public Reader(RWbasic rw,int id) {
            this.rw = rw;
            this.id = id;
        }
        @Override
        public void run() {
            int i = rw.read();
            System.out.println(id+i);

        }
    }
    public static class Writer implements Runnable {
        private RWbasic rw;
        private int id;

        public Writer(RWbasic rw,int id) {
            this.rw = rw;
            this.id = id;
        }
        @Override
        public void run() {
            rw.write();
        }
    }
    public static void main(String[] args){
        RWbasic rw = new RWbasic();
        for (int i=0; i<50; i++) {
            new Thread(new Reader(rw,i)).start();
            new Thread(new Writer(rw,i)).start();
        }
    }
}
