package org.example.use_shutdown_hook;

public class App 
{
    static Thread t;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                close("shutdownHook");
            }
        }));

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (Stopper.isRunning()) {
                    try {
                        System.out.println("app is running..., " + i++);
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    static void close(String cause) {
        //execute only once
        if(Stopper.isStopped()){
            return;
        }

        System.out.println("app is stopping..., cause: " + cause);

        // set stop signal is true
        Stopper.stop();

        try {
            //thread sleep 3 seconds for thread quietly stop
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (t.isAlive()) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("app is stopped");
    }


}
