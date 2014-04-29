/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
 
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
 
public class MyStartScreen implements ScreenController {
    public Nifty nifty;
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty=nifty;
        System.out.println("bind(" + screen.getScreenId() + ")");
    }
 
    public void onStartScreen() {
        System.out.println("onStartScreen");
    }
 
    public void onEndScreen() {
        System.out.println("onEndScreen");
    }
    
    /** custom methods */ 
    public void startGame(String nextScreen) {
        System.out.println("goToScreen(nextScreen)");
        
        nifty.gotoScreen(nextScreen);  // switch to another screen
    // start the game and do some more stuff...
    }
 
    public void quitGame() {
        System.exit(0);
    }
}