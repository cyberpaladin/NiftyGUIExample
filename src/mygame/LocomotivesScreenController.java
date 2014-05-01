/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author faithninja
 */
public class LocomotivesScreenController implements ScreenController{
    private Nifty nifty;
    private Screen screen;
    
    // angle in radian to rotate the tachometer pointer 
    private double angle=0;
    
    //test speed to play around with the GUI of the first locomotive
    private int speed;
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty=nifty;
        this.screen=screen;
        System.out.println("" + screen.getScreenId() + " - bind(" + screen.getScreenId() + ")");
    }
 
    public void onStartScreen() {
        //magic happens in xml-file
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-top-buttons").startEffect(EffectEventId.onCustom, null, "onShow");
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-bottom-buttons").startEffect(EffectEventId.onCustom, null, "onShow");
        //nifty.getCurrentScreen().findElementByName("locomotives-panel-bottom-buttons").startEffect(EffectEventId.onCustom, null, "onShow");
        
        /*try {
            makeTacho(Math.toRadians(242),"tachometer-made.png");
            setTacho("Interface/tachometer-made.png","tacho-1");
        } catch (IOException ex) {
            Logger.getLogger(LocomotivesScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }*/

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
    
    /** custom methods */ 
    public void start(String nextScreen) {
        System.out.println("" + screen.getScreenId() + " - goToScreen(" + nextScreen + ")");
        
        nifty.gotoScreen(nextScreen);  // switch to another screen
    }
 
    public void quit() {
        nifty.exit();
        new ExitDelay(1000);
    }
    
    public void clicked(int x, int y){
        int imgX = nifty.getCurrentScreen().findElementByName("locomotives-panel-controller-tacho-center-1").getX();
        int imgY = nifty.getCurrentScreen().findElementByName("locomotives-panel-controller-tacho-center-1").getY();
        
        //mouse coordinates relatively to the top left image corner
        int relX = x-imgX;
        int relY = y-imgY;
        
        //mouse coordinates relatively to the image middle
        int adjacent = -relX+120; //hint: adjacent (side) is the English term for ankathete
        int opposite = -relY+105; //hint:  opposite (side) is the English term for gegenkathete
        
        //calculate angle for the tachometer
        if(adjacent<0){
            angle = Math.atan((double)opposite/adjacent)+Math.PI/2;
        }else{
            angle = Math.atan((double)opposite/adjacent)+Math.PI*3/2;
        }
        
        try {
            makeTacho(angle,"tachometer-made.png");
            setTacho("Interface/tachometer-made.png","tacho-1");
        } catch (IOException ex) {
            Logger.getLogger(LocomotivesScreenController.class.getName()).log(Level.SEVERE, "can't make tacho", ex);
        }
        
        System.out.println("Calculation:");
        System.out.println("Adjacent: "+imgX+"-"+x+"+120 = "+adjacent);
        System.out.println("Opposite: "+imgY+"-"+y+"+105 = "+opposite);
        System.out.println("Angle: "+angle+" --> "+Math.toDegrees(angle));
    }
    
    private void makeTacho(double angle, String targetname) throws IOException {
        // load source images
        BufferedImage background = ImageIO.read(new File("assets/Interface/tachometer.png"));
        BufferedImage pointer = ImageIO.read(new File("assets/Interface/pointer.png"));

        // create the new image
        BufferedImage tacho = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);


        // paint tachometer background, preserving the alpha channels
        Graphics g = tacho.getGraphics();
        g.drawImage(background, 0, 0, null);


        // create the transform, note that the transformations happen in reversed order (so check them backwards)
        AffineTransform at = new AffineTransform();

        // 4. translate it to the center of the component
        at.translate(background.getWidth() / 2, background.getHeight() / 2);

        // 3. do the actual rotation
        at.rotate(angle);

        // 2. just a scale because this image is big
        at.scale(0.9, 0.9);

        // 1. translate the object so that you rotate it around the 
        //    center (easier :))
        at.translate(-pointer.getWidth()/2, -pointer.getHeight()/2);

        // draw the image
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(pointer, at, null);

        // Save as new image
        ImageIO.write(tacho, "PNG", new File("assets/Interface/"+targetname));

        // get rid of all graphic elements
        background.flush();
        background = null;
        pointer.flush();
        pointer = null;
        tacho.flush();
        tacho = null;
        g.dispose();
        g2d.dispose();
    }
        
    private void setTacho(String path, String id){
        // load or create new image
        NiftyImage tachometer = nifty.createImage(path, false);
        
        // find old image
        Element niftyElement = nifty.getCurrentScreen().findElementByName(id);
        
        // swap old with new image
        niftyElement.getRenderer(ImageRenderer.class).setImage(tachometer);
    }
}
