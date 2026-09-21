import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Random;

/**
 * Slot machine with full game logic
 *
 * @author Santiago Vargas
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels = new ArrayList<>();
    private Rectangle body;
    private Circle leftLight;
    private Circle rightLight;
    private boolean visible;
    private boolean operationOK;
    private Random random = new Random(); //Para los spins
    
    public SlotMachine(){
        this.body = new Rectangle(20, 20, 170, 170, "black");
        this.leftLight = new Circle(20, 20, 70, "red");
        this.rightLight = new Circle(120, 20, 70, "red");
    }
    
    public void addWheel(int pos){
        wheels.add(pos - 1, new Wheel());
        repositionWheels();
    }
    
    public void delWheel(int pos){
        wheels.remove(pos - 1);
        repositionWheels();
    }
    
    public void addSymbol(int pos, String color){
        wheels.get(pos - 1).addSymbol(color);
    }
    
    public void delSymbol(String symbol){
        for(Wheel wheel : wheels){
            wheel.delSymbol(symbol);
        }
    }
    
    public void placeSymbol(int wheel, String symbol){
        Wheel selectedWheel = wheels.get(wheel - 1);
        
        if(selectedWheel.containsSymbol(symbol)) {
            selectedWheel.placeSymbol(symbol);
            operationOK = true;
        } else {
            operationFail("No se encontro el simbolo...");
        }
    }
    
    public void spin(int wheel){
        Wheel sWheel = wheels.get(wheel - 1);
        
        if(sWheel.getSymbolsSize() >= 1){
            int idxRandom = random.nextInt(sWheel.getSymbolsSize());
            String color = sWheel.getSymbolColor(idxRandom);
            placeSymbol(wheel, color);
        } else {
            operationFail("La rueda no tiene simbolos...");
        }
    }
    
    public void spin(){
        for (int i = 0; i < wheels.size(); i++){
            Wheel sWheel = wheels.get(i);
            if (sWheel.getSymbolsSize() >= 1){
                int idxRandom = random.nextInt(sWheel.getSymbolsSize());
                String color = sWheel.getSymbolColor(idxRandom);
                System.out.println(color);
                placeSymbol(i + 1, color);
            }
        }
    }
    
    public String[] symbols(){
        return null;
    }
    
    public int distinctSymbols(){
        return 0;
    }
    
    public String[] configuration(){
        return null;
    }
    
    public boolean isJackpot(){
        return false;
    }
    
    public void makeVisible(){
        body.makeVisible();
        leftLight.makeVisible();
        rightLight.makeVisible();
        
        for(Wheel wheel : wheels){
            wheel.makeVisible();
        }
    
        visible = true;
    }
    
    public void makeInvisible(){
    
    }
    
    public void exit(){
    
    }
    
    public boolean ok(){
        return operationOK;
    }
    
    private void repositionWheels(){
        int x = 40;
        int y = 100;
        int width = Math.max(body.x, 30 + wheels.size() * 80); //Para el body de la slot
        int rightLightX = 20 + width - 70;
        
        body.changeSize(170, width);
        rightLight.moveHorizontal(rightLightX - rightLight.getX());
        
        for(Wheel wheel : wheels){
            wheel.setPosition(x, y);
            x += 80;
        }
        
        if(visible){
            makeVisible();
        }
    }
    
    private void operationFail(String message){
        operationOK = false;
            if (visible) {
                JOptionPane.showMessageDialog(null, message);
            }
    }
}