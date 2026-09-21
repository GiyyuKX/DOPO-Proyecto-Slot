
/**
 * Superclass Shape with the basic methods and atributes for Star and Rectangle
 *
 * @author Santiago Vargas
 */
public abstract class Shape
{
    protected int x;
    protected int y;
    protected String color;
    protected boolean isVisible;    
    
    protected Shape(int x, int y, String color){
        this.x = x;
        this.y = y;
        this.color = color;
        this.isVisible = false;
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
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
    /**
     * Move the star horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        x += distance;
        draw();
    }

    /**
     * Move the star vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        y -= distance;
        draw();
    }
    
    /*
     * Erase the shape on screen.
     */
    protected void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }     
    
    /*
     * Draw the shape with current specifications on screen.
     */
    protected abstract void draw();
    
    public int getX(){
       return x;
    }
    
    public int getY(){
        return y;
    }
}