/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
 
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author faithninja
 */
public class StartScreenController implements ScreenController {
    public Nifty nifty;
    public Screen screen;
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty=nifty;
        this.screen=screen;
        System.out.println("" + screen.getScreenId() + " - bind(" + screen.getScreenId() + ")");
    }
 
    public void onStartScreen() {
        //magic happens in xml-file
        
        System.out.println("" + screen.getScreenId() + " - onStartScreen");
    }
 
    public void onEndScreen() {
        screen.getRootElement().resetAllEffects();
        //magic happens in xml-file
        
        System.out.println("" + screen.getScreenId() + " - onEndScreen");
    }
    
    /** custom methods */ 
    public void start(String nextScreen) {
        System.out.println("" + screen.getScreenId() + " - goToScreen(" + nextScreen + ")");
        
        nifty.gotoScreen(nextScreen);  // switch to another screen
    }
 
    public void quit() {
        nifty.getCurrentScreen().endScreen(null);
        long count = System.currentTimeMillis();
        while(count>System.currentTimeMillis()-1000){
            nifty.getCurrentScreen().resetLayout();
            //nifty.update();
            //nifty.render(true);
            //nifty.getCurrentScreen().endScreen(null);
        }
        nifty.exit();
        System.exit(0);
    }
}