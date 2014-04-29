/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
 
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
 
public class MyStartScreen extends AbstractAppState implements ScreenController {
    Application app;
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }
 
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
 
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
 
    public void bind(Nifty nifty, Screen screen) {
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
        //app.nifty.gotoScreen(nextScreen);  // switch to another screen
    // start the game and do some more stuff...
    }
 
    public void quitGame() {
        System.exit(0);
    }
}