// Import the necessary Java synchronization and scheduling classes.

import java.util.concurrent.CountDownLatch;

/**
 * L'essence :
 * 
 * CountDownLatch permet de mettre en place le barrier pattern.
 * 
 * Les sémaphores doivent être 2 pour alterner correctement. Les threads n'ont pas besoin de savoir qui ils sont (ping ou pong) grâce à
 * l'inversion des sémaphores à l'intantiation.
 * 
 * Pour démarrer avec le bon texte (ping) il suffit de bloquer l'autre sémaphore.
 * 
 * @class PingPongRight
 * 
 * @brief This class implements a Java program that creates two instances of the PlayPingPongThread and start these thread instances to
 *        correctly alternate printing "Ping" and "Pong", respectively, on the console display.
 */
public class PingPongRight {
    /**
     * Number of iterations to run the test program.
     */
    public static int mMaxIterations = 10;

    /**
     * Latch that will be decremented each time a thread exits.
     */
    public static CountDownLatch latch = new CountDownLatch(2); // TODO - You fill in here

    /**
     * @class PlayPingPongThread
     * 
     * @brief This class implements the ping/pong processing algorithm using the SimpleSemaphore to alternate printing "ping" and "pong" to
     *        the console display.
     */
    public static class PlayPingPongThread extends Thread {
        /**
         * Constructor initializes the data member.
         */
        public PlayPingPongThread(SimpleSemaphore pingSem, SimpleSemaphore pongSem, String stringToPrint) {
            // TODO - You fill in here.
            mPingSemaphore = pingSem;
            mPongSemaphore = pongSem;
            mStringToPrint = stringToPrint;
            // if (mStringToPrint.equals("Ping!")) {
            // mPongSemaphore.acquireUninterruptibly(); // lock pong (pingSem stays unlocked)
            // }
        }

        /**
         * Main event loop that runs in a separate thread of control and performs the ping/pong algorithm using the SimpleSemaphores.
         */
        public void run() {
            // TODO - You fill in here.

            for (int loopsDone = 1; loopsDone <= mMaxIterations; loopsDone++) {
                // if (mStringToPrint.equals("Ping!")) {
                mPingSemaphore.acquireUninterruptibly();
                System.out.println(mStringToPrint + " (" + loopsDone + ")");
                mPongSemaphore.release();
                // } else {
                // mPongSemaphore.acquireUninterruptibly();
                // System.out.println(mStringToPrint + " (" + loopsDone + ")");
                // mPingSemaphore.release();
                // }
            }
            latch.countDown();
        }

        /**
         * String to print (either "ping!" or "pong"!) for each iteration.
         */
        // TODO - You fill in here.
        String mStringToPrint;

        /**
         * The two SimpleSemaphores use to alternate pings and pongs.
         */
        // TODO - You fill in here.
        SimpleSemaphore mPingSemaphore;
        SimpleSemaphore mPongSemaphore;
    }

    /**
     * The main() entry point method into PingPongRight program.
     */
    public static void main(String[] args) {
        try {
            // Create the ping and pong SimpleSemaphores that control
            // alternation between threads.

            // TODO - You fill in here.
            SimpleSemaphore pingSemaphore = new SimpleSemaphore(1, true);
            SimpleSemaphore pongSemaphore = new SimpleSemaphore(1, true);

            System.out.println("Ready...Set...Go!");

            // Create the ping and pong threads, passing in the string
            // to print and the appropriate SimpleSemaphores.
            pongSemaphore.acquire(); // lock pong (pingSem stays unlocked)
            PlayPingPongThread ping = new PlayPingPongThread(pingSemaphore, pongSemaphore, "Ping!");
            PlayPingPongThread pong = new PlayPingPongThread(pongSemaphore, pingSemaphore, "Pong!");

            // Initiate the ping and pong threads, which will call the
            // run() hook method.
            ping.start();
            pong.start();

            // Use barrier synchronization to wait for both threads to
            // finish.

            // TODO - replace replace the following line with a
            // CountDownLatch barrier synchronizer call that waits for
            // both threads to finish.
            latch.await();

        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Done!");
    }
}
