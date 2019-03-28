package ro.pub.cs.systems.eim;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class PracticalTest01Var03Service extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = intent.getStringExtra("info1");
        String grupa = intent.getStringExtra("info2");
        processingThread = new ProcessingThread(this, name, grupa);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    private String nume;
    private String grupa;
    private int counter;

    ProcessingThread(Context context, String nume, String grupa) {
        this.context = context;
        this.nume = nume;
        this.grupa = grupa;
        this.counter = 0;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
//        Log.e("Nume", nume);
//        Log.e("Grupa", grupa);
        String print = counter++ % 2 == 0 ? nume : grupa;
        intent.setAction("actiune");
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + print);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}