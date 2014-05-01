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
public class SimScreenController implements ScreenController{
    public Nifty nifty;
    public Screen screen;
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty=nifty;
        this.screen=screen;
        System.out.println("" + screen.getScreenId() + " - bind(" + screen.getScreenId() + ")");
    }
 
    public void onStartScreen() {
        //magic happens in xml-file
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-top-buttons").startEffect(EffectEventId.onCustom, null, "onShow");
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-menu").startEffect(EffectEventId.onCustom, null, "onShow");
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-bottom-buttons").startEffect(EffectEventId.onCustom, null, "onShow");
        
        System.out.println("" + screen.getScreenId() + " - onStartScreen");
    }
 
    public void onEndScreen() {
        screen.getRootElement().resetAllEffects();
        //magic happens in xml-file
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-top-buttons").startEffect(EffectEventId.onCustom, null, "onHide");
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-menu").startEffect(EffectEventId.onCustom, null, "onHide");
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-bottom-buttons").startEffect(EffectEventId.onCustom, null, "onHide");
        
        System.out.println("" + screen.getScreenId() + " - onEndScreen");
    }
    
    public void onHide(){
        
    }
    
    public void onShow(){
        
    }
    
    /** custom methods */ 
    public void start(String nextScreen) {
        System.out.println("" + screen.getScreenId() + " - goToScreen(" + nextScreen + ")");
        
        nifty.gotoScreen(nextScreen);  // switch to another screen
    }
 
    public void quit() {
        //onExit();
        nifty.getCurrentScreen().endScreen(null);
        //nifty.wait(0);
        nifty.exit();
        System.exit(0);
    }
}
