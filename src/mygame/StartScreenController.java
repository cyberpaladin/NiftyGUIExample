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
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty=nifty;
        System.out.println("bind(" + screen.getScreenId() + ")");
    }
 
    public void onStartScreen() {
        System.out.println("onStartScreen");
    }
 
    public void onEndScreen() {
        /*Collection<String> screens = nifty.getAllScreensName();
        for(String each:screens){
            if(!nifty.getScreen(each).isNull()){
                nifty.getScreen(each).endScreen(null);
            }
        }*/
        
        System.out.println("onEndScreen");
    }
    
    /** custom methods */ 
    public void start(String nextScreen) {
        System.out.println("goToScreen(" + nextScreen + ")");
        
        nifty.gotoScreen(nextScreen);  // switch to another screen
    }
 
    public void quit() {
        //onEndScreen();
        System.exit(0);
    }
}