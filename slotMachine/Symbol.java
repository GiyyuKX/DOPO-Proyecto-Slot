
/**
 * A Symbol from a wheel
 *
 * @author Santiago Vargas
 */
public class Symbol
{
    private String color;
    private Star star;

    public Symbol(String color){
        this.color = color;
        this.star = new Star(40, 100, color, 70);
    }

    public void moveHorizontal(int distance){
        star.moveHorizontal(distance);
    }

    public void moveVertical(int distance){
        star.moveVertical(distance);
    }

    public void makeVisible(){
        star.makeVisible();
    }

    public void makeInvisible(){
        star.makeInvisible();
    }
    
    public String getColor(){
        return this.color;
    }
    
    public void setPosition(int x, int y){
        int dx = x - star.getX();
        int dy = star.getY() - y;
    
        star.moveHorizontal(dx);
        star.moveVertical(dy);
    }
}