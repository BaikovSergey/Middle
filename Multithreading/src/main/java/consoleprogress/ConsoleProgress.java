package consoleprogress;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
            char[] symbol = {'|', '/', '-', '\\'};
            int index = 0;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(500);
                    System.out.print("\rLoading: " + symbol[index]);
                    index++;
                    if (index >= symbol.length) {
                        index = 0;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
