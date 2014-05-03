/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
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
    int randomnumber =0;
    int previousrandomnumber=0;
    final String imagename = "tachometer-made.png";
    final String xmlelementid = "tacho-1";
    private double angle = Math.toRadians(242); // angle in radian to rotate the tachometer pointer, default pointing to zero
    private int speed; //test speed to play around with the GUI of the first locomotive
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty=nifty;
        this.screen=screen;
        //System.out.println("" + screen.getScreenId() + " - bind(" + screen.getScreenId() + ")");
    }
 
    public void onStartScreen() {
        //System.out.println("" + screen.getScreenId() + " - onStartScreen");
    }
 
    public void onEndScreen() {
        //make sure all effects wwill still be working by reseting them
        screen.getRootElement().resetAllEffects();
        
        //delete tacho image from tmp files
        deleteTacho(); 
        //System.out.println("" + screen.getScreenId() + " - onEndScreen");
    }
    
    /** custom methods */ 
    public void start(String nextScreen) {
        nifty.gotoScreen(nextScreen);  // switch to another screen
        //System.out.println("" + screen.getScreenId() + " - goToScreen(" + nextScreen + ")");
    }
 
    public void quit() {
        nifty.exit();
        deleteTacho();
        
        // wait for nifty to apply onEndScreen effects
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
        
        //make sure pointer doesn't go out of range
        if(angle<Math.toRadians(200) && Math.toRadians(156)<angle){
            angle=Math.toRadians(156);
        }
        if(angle<Math.toRadians(242) && Math.toRadians(199)<angle){
            angle=Math.toRadians(242);
        }
        
        //make tacho image and set it to the GUI
        try {
            makeTacho(angle);
            setTacho("Interface/tmp/");
        } catch (IOException ex) {
            Logger.getLogger(LocomotivesScreenController.class.getName()).log(Level.SEVERE, "can't make tacho", ex);
        }

        //System.out.println("Calculation:");
        //System.out.println("Adjacent: "+imgX+"-"+x+"+120 = "+adjacent);
        //System.out.println("Opposite: "+imgY+"-"+y+"+105 = "+opposite);
        //System.out.println("Angle: "+angle+" --> "+Math.toDegrees(angle));
    }
    
    private void makeTacho(double angle) throws IOException {
        // load source images
        BufferedImage background = ImageIO.read(new File("assets/Interface/tachometer.png"));
        BufferedImage pointer = ImageIO.read(new File("assets/Interface/pointer.png"));

        // create the new image
        BufferedImage tacho = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // paint tachometer background, preserving the alpha channels
        Graphics2D g = tacho.createGraphics();
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

        // add pointer over background
        g.drawImage(pointer, at, null);

        previousrandomnumber = randomnumber;
        while(previousrandomnumber == randomnumber){
            randomnumber = new Random().nextInt(899)+100;
        }
        
        // save as new image
        ImageIO.write(tacho, "PNG", new File("assets/Interface/tmp/"+randomnumber+imagename));
        //ImageIO.write(tacho, "PNG", new File("assets/Interface/"+targetname));

        // get rid of all graphic elements
        g.dispose();
        background.flush();
        pointer.flush();
        tacho.flush();
    }
        
    private void setTacho(String path) throws IOException{
        // load or create new image
        NiftyImage tachometer = nifty.createImage(path+randomnumber+imagename, false);
        
        // find old image
        Element niftyElement = nifty.getCurrentScreen().findElementByName(xmlelementid);
        
        // swap old with new image
        niftyElement.getRenderer(ImageRenderer.class).setImage(tachometer);
        
        // delete previous tachometer
        try {
            Path newpath = Paths.get("assets/"+path+previousrandomnumber+imagename);
            Files.delete(newpath);
        } catch (Exception e) {
            System.out.format("Sorry, couldn't delete tachometer. %n%s%n",e);
        }
    }
    private void deleteTacho(){
        try {
            Path newpath = Paths.get("assets/Interface/tmp/"+randomnumber+imagename);
            Files.delete(newpath);
        } catch (Exception e) {
            System.out.format("Sorry, couldn't delete tachometer. %n%s%n",e);
        }
    }
}
