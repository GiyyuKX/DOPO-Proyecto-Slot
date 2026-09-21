import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle extends Shape{

    public static final double PI=3.1416;
    private int diameter;

    public Circle(int x, int y, int diameter, String color){
        super(x, y, color);
        this.diameter = diameter;
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }
    
    /*
     * Draw the circle with current specifications on screen.
     */
    @Override
    protected void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(x, y, 
                diameter, diameter));
            canvas.wait(10);
        }
    }
}
