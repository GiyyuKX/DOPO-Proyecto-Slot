import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

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
    private Random random = new Random(); //Spins
    
    public SlotMachine(){
        this.body = new Rectangle(20, 20, 170, 170, "black");
        this.leftLight = new Circle(20, 20, 70, "magenta");
        this.rightLight = new Circle(120, 20, 70, "red");
        operationOK = true;
    }
    
    public void addWheel(int pos){
        //Verify pos is valid
        if (pos < 1 || pos > wheels.size() + 1){
            operationFail("Posicion dada incorrecta...");
            return;
        }
        
        wheels.add(pos - 1, new Wheel());
        operationOK = true;
        repositionWheels();
    }
    
    public void delWheel(int pos){
        //Verify pos is valid
        if (pos < 1 || pos > wheels.size()){
            operationFail("Posicion dada incorrecta...");
            return;
        }
        Wheel sWheel = wheels.get(pos - 1);
        if (sWheel.getActualSymbol() != null) {
            sWheel.delSymbol(sWheel.getActualSymbol().getColor());
        }
        wheels.remove(pos - 1);
        operationOK = true;
        repositionWheels();
    }
    
    public void addSymbol(int pos, String color){
        //Verify pos is valid
        if (pos < 1 || pos > wheels.size()){
            operationFail("Posicion dada incorrecta...");
            return;
        }
        wheels.get(pos - 1).addSymbol(color);
        operationOK = true;
    }
    
    public void delSymbol(String symbol){
        operationOK = false; //Si no encuentra el simbolo en ninguna rueda
        for(Wheel wheel : wheels){
            if (wheel.containsSymbol(symbol)){
                wheel.delSymbol(symbol);
                operationOK = true;
            }
        }
        
        if (!operationOK) {
            operationFail("El simbolo no existe en ninguna rueda");
            return;
        }
        isJackpot();
    }
    
    public void placeSymbol(int wheel, String symbol){
        if (wheel < 1 || wheel > wheels.size()){
            operationFail("Rueda no existe...");
            return;
        }
                
        Wheel selectedWheel = wheels.get(wheel - 1);
        
        if (selectedWheel.locked()){
            //TOASK falla si intenta spinear una rueda bloqueada? operationFail("Rueda no existe...");
            return;
        }
        
        if(selectedWheel.containsSymbol(symbol)) {
            selectedWheel.placeSymbol(symbol);
            operationOK = true;
        } else {
            operationFail("No se encontro el simbolo...");
        }
    }
    
    public void spin(int wheel){
        if (wheel < 1 || wheel > wheels.size()){
            operationFail("Rueda no existe...");
            return;
        }
        
        Wheel sWheel = wheels.get(wheel - 1);
        
        if(sWheel.getSymbolsSize() >= 1){
            int idxRandom = random.nextInt(sWheel.getSymbolsSize());
            String color = sWheel.getSymbolColor(idxRandom);
            placeSymbol(wheel, color);
            operationOK = true;
        } else {
            operationFail("La rueda no tiene simbolos...");
        }
        
        isJackpot();
    }
    
    public void spin(){
        for (int i = 0; i < wheels.size(); i++){
            Wheel sWheel = wheels.get(i);
           
            if (sWheel.getSymbolsSize() >= 1){
                int idxRandom = random.nextInt(sWheel.getSymbolsSize());
                String color = sWheel.getSymbolColor(idxRandom);
                placeSymbol(i + 1, color);
                operationOK = true;
            } else{
                operationFail("Una rueda no tiene simbolos...");
                return;
            }
        }
        
        isJackpot();
    }
    
    public String[] symbols(){
        ArrayList<String> allSymbolsColors = new ArrayList<>();
        
        for (Wheel wheel: wheels){
            if (wheel.getSymbolsSize() > 0){
                for(int i = 0; i < wheel.getSymbolsSize(); i++){
                    allSymbolsColors.add(wheel.getSymbolColor(i));
                }
            }
        }
        
        return allSymbolsColors.toArray(new String[0]);
    }
    
    public int distinctSymbols(){
        String[] symbols = symbols();
        Set<String> distinctSymbols = new HashSet<>(Arrays.asList(symbols));
        return distinctSymbols.size();
    }
    
    public String[] configuration(){
        ArrayList<String> symbols = new ArrayList<>();
        
        for (Wheel wheel: wheels){
            if(wheel.getActualSymbol() != null){
                symbols.add(wheel.getActualSymbol().getColor());
            }
        }
        
        return symbols.toArray(new String[0]);
    }
    
    public boolean isJackpot(){
        if(wheels.size() == 0){
            setColors("magenta", "red", "black");
            return false;
        }
        
        String firstColor = null;
        for (int i = 0; i < wheels.size(); i++) {
            Symbol actualSymbol = wheels.get(i).getActualSymbol();
            if (actualSymbol == null){
                setColors("magenta", "red", "black");
                return false;
            }
                        
            if (i == 0){
                firstColor = actualSymbol.getColor();
            }
            
            if (!firstColor.equals(actualSymbol.getColor())){
                setColors("magenta", "red", "black");
                return false;
            }
        }
        
        setColors("yellow", "green", "blue");
        return true;
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
        body.makeInvisible();
        leftLight.makeInvisible();
        rightLight.makeInvisible();
        
        for(Wheel wheel : wheels){
            wheel.makeInvisible();
        }
    
        visible = false;
    }
    
    public void exit(){
        Canvas.getCanvas().close();
        //TOASK Exit deberia eliminar su propia instancia?
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
        
        isJackpot();
    }
    
    private void operationFail(String message){
        operationOK = false;
            if (visible) {
                JOptionPane.showMessageDialog(null, message);
            }
    }
    
    private void setColors(String left, String right, String bodyColor) {
        leftLight.changeColor(left);
        rightLight.changeColor(right);
        body.changeColor(bodyColor); 
        if (visible){
            makeVisible();
        }
    }
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CICLO 2
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    public void swap(int wheel1, int wheel2){
        if (wheel1 < 1 || wheel1 > wheels.size() || wheel2 < 1 || wheel2 > wheels.size()){
            operationFail("Alguna rueda no existe...");
            return;
        }
        
        if (wheel1 == wheel2){
            operationFail("La rueda es la misma...");
            return;
        }
            
        Wheel wheel_swapped = wheels.get(wheel1 - 1);
        wheels.set(wheel1 - 1, wheels.get(wheel2 - 1));
        wheels.set(wheel2 - 1, wheel_swapped);
        operationOK = true;
        repositionWheels();
    }
    
    public void lock(int wheel){
        if (wheel < 1 || wheel > wheels.size()){
            operationFail("La rueda no existe...");
            return;
        }
        
        Wheel sWheel = wheels.get(wheel - 1);
        sWheel.lock();
    }
    
    public void unlock(int wheel){
        if (wheel < 1 || wheel > wheels.size()){
            operationFail("La rueda no existe...");
            return;
        }
        
        Wheel sWheel = wheels.get(wheel - 1);
        sWheel.unlock();
    }
    
    public void spin(int wheel, int steps){
        if (wheel < 1 || wheel > wheels.size()){
            operationFail("Rueda no existe...");
            return;
        }
        
        Wheel sWheel = wheels.get(wheel - 1);
        
        for (int i = 0; i < steps; i++){
            spin(wheel);
            Canvas.getCanvas().wait(270);
        }
    }
    
    public void spin(String[] setSymbols){
        //["red","green","blue"]
        
        for (int i = 0; i < setSymbols.length; i++) {
            Wheel currentWheel = wheels.get(i);
    
            //Valida símbolo no existe
            if (!currentWheel.containsSymbol(setSymbols[i])) {
                operationFail("El simbolo no existe en la rueda");
                return;
            }
    
            //Si no tiene simbolo ponemos uno
            if (currentWheel.getActualSymbol() == null) {
                spin(i + 1);
            }
    
            //Spin hasta encontrar el símbolo solicitado
            while (!setSymbols[i].equals(currentWheel.getActualSymbol().getColor())) {
                spin(i + 1);
                Canvas.getCanvas().wait(200);
            }
        }
    }
}