import java.awt.*;

/**
 * A star that can be manipulated and that draws itself on a canvas.
 * 
 * @author Santiago Vargas
 */

public class Star extends Shape{
    
    public static int POINTS = 5;
    private int size;

    /**
     * Create a new star at default position with default color.
     */
    public Star(int x, int y, String color, int size){
        super(x, y, color);
        this.size = size;
    }

    /**
     * Change the size to the new size.
     * @param newSize the new size in pixels. newSize must be >=0.
     */
    public void changeSize(int newSize){
        erase();
        size = newSize;
        draw();
    }

    /*
     * Draw the star with current specifications on screen.
     */
    @Override
    protected void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = new int[2 * POINTS];
            int[] ypoints = new int[2 * POINTS];
            int outer = size / 2;
            int inner = size / 5;
            int centerX = x + outer;
            int centerY = y + outer;
            
            for(int i = 0; i < 2 * POINTS; i++){
                int radius;
                if(i % 2 == 0) {
                    radius = outer;
                } else {
                    radius = inner;
                }
                double angle = -Math.PI / 2 + i * Math.PI / POINTS;
                xpoints[i] = centerX + (int)(radius * Math.cos(angle));
                ypoints[i] = centerY + (int)(radius * Math.sin(angle));
            }

            canvas.draw(this, color, new Polygon(xpoints, ypoints, 2 * POINTS));
            canvas.wait(10);
        }
    }
}