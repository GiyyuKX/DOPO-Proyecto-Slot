import java.util.ArrayList;

/**
 * A Wheel from a slotmachine
 *
 * @author Santiago Vargas
 */
public class Wheel
{
    private ArrayList<Symbol> symbols = new ArrayList<>();
    private Symbol actualSymbol;
    private Rectangle body;
    private boolean visible;
       
    public Wheel(){
        this.body = new Rectangle(20, 20, 70, 70, "white");
    }
    
    public void addSymbol(String color){
        symbols.add(new Symbol(color));
    }
    
    public void delSymbol(String color){
        for (int i = 0; i < symbols.size(); i++){
            if (symbols.get(i).getColor().equals(color)) {
                
                if (symbols.get(i) == actualSymbol) {
                    actualSymbol.makeInvisible();
                    actualSymbol = null;
                }
                    symbols.remove(i);
                i--;
            }
        }
    }
    
    public void placeSymbol(String color){
        if (actualSymbol != null && actualSymbol.getColor().equals(color)){
            return;
        }
        
        for (Symbol symbol : symbols){
            if (symbol.getColor().equals(color)) {
                if(actualSymbol != null){
                    actualSymbol.makeInvisible();
                }
                actualSymbol = symbol;
                actualSymbol.setPosition(body.getX(), body.getY());
                
                if (visible){
                    actualSymbol.makeVisible();
                }
                
                return;
            }
        }
    }
    
    public boolean containsSymbol(String color){
        for (Symbol symbol : symbols){
            if (symbol.getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }
    
    public void makeVisible(){
        body.makeVisible();
        visible = true;
        
        if(actualSymbol != null){
            actualSymbol.makeVisible();
        }
    }
    
    public void makeInvisible(){
        body.makeInvisible();
        visible = false;
        
        if(actualSymbol != null){
            actualSymbol.makeInvisible();
        }
    }
    
    public int getSymbolsSize(){
        return symbols.size();
    }
    
    public ArrayList<Symbol> getSymbols(){
        return symbols;
    }
    
    public String getSymbolColor(int pos){
        return symbols.get(pos).getColor();
    }
    
    public Symbol getActualSymbol(){
        return this.actualSymbol;
    }
    
    public void setPosition(int x, int y){
        int dx = x - body.getX();
        int dy = body.getY() - y;
    
        body.moveHorizontal(dx);
        body.moveVertical(dy);
    
        if(actualSymbol != null){
            actualSymbol.moveHorizontal(dx);
            actualSymbol.moveVertical(dy);
        }
    }
}