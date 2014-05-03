/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
 
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectEventId;
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
        //System.out.println("" + screen.getScreenId() + " - bind(" + screen.getScreenId() + ")");
    }
 
    public void onStartScreen() {
        nifty.getCurrentScreen().findElementByName("start-panel-menu").startEffect(EffectEventId.onCustom, null,"playStartSound");
        //System.out.println("" + screen.getScreenId() + " - onStartScreen");
    }
 
    public void onEndScreen() {
        //make sure all effects wwill still be working by reseting them
        screen.getRootElement().resetAllEffects();
        //System.out.println("" + screen.getScreenId() + " - onEndScreen");
    }
    
    /** custom methods */ 
    public void start(String nextScreen) {
        nifty.gotoScreen(nextScreen);  // switch to another screen
        //System.out.println("" + screen.getScreenId() + " - goToScreen(" + nextScreen + ")");
    }
 
    public void quit() {
        nifty.exit();
        
        // wait for nifty to apply onEndScreen effects
        new ExitDelay(1000);
    }
}