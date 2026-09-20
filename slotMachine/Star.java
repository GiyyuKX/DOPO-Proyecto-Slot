import java.awt.*;

/**
 * A star that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0
 */

public class Star{
    
    public static int POINTS = 5;
    
    private int size;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Create a new star at default position with default color.
     */
    public Star(){
        size = 60;
        xPosition = 140;
        yPosition = 15;
        color = "yellow";
        isVisible = false;
    }

    /**
     * Make this star visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this star invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the star a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the star a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the star a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the star a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the star horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the star vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition -= distance;
        draw();
    }

    /**
     * Slowly move the star horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the star vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
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
    
    /**
     * Change the color. 
     * @param newColor the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

    /*
     * Draw the star with current specifications on screen.
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = new int[2 * POINTS];
            int[] ypoints = new int[2 * POINTS];
            int outer = size / 2;
            int inner = size / 5;
            int centerX = xPosition + outer;
            int centerY = yPosition + outer;
            
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

    /*
     * Erase the star on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}