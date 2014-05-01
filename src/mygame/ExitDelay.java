/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author faithninja
 */
public class ExitDelay implements Runnable{
    private final int delay;
        
    public ExitDelay(int delay) {
        this.delay = delay;
        this.start();
    }

    private void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {

        }
        System.exit(0);
    }
}
