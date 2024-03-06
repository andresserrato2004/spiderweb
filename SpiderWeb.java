import java.util.*;
import javax.swing.JOptionPane;

/**
 * Clase que representa una red de telaraña con puentes y puntos de referencia.
 * Esta clase permite agregar, eliminar y reubicar puentes y puntos de referencia,
 * así como también mover una araña a lo largo de los puentes.
 *
 * La red de telaraña está formada por una serie de líneas que representan los brazos
 * de la telaraña, y puentes que conectan estos brazos. Los puentes pueden ser de diferentes
 * colores y se pueden agregar a una posición específica de un brazo.
 *
 * Los puntos de referencia son también líneas, y se pueden agregar a un brazo para indicar
 * un punto de interés.
 * 
 * @author Andres Serrato-Zayra Gutierrez
 * @version 18/02/2024
 */
public class SpiderWeb {
    // Atributos de la clase
    private int radio;
    private int strands;
    private int pointBridge;
    private float angle;
    private float xStard;
    private float yStard;
    private float xBridge;
    private float yBridge;
    private float x2Bridge;
    private float y2Bridge;
    private float angleFirstStrand;
    private float angleSecondStrand;
    private float firstStrand;
    private float strand;
    private boolean isVisible;
    private boolean spiderCenter;
    private boolean goodList;
    private float x1cordenate;
    private float y1cordenate;
    private float x2cordenate;
    private float y2cordenate;
    private Line bringe;
    private Circle circle;
    private angles list;
    private List<Pair<Float, Float>> lists;
    private ArrayList<Line> lineList;
    private Spider spider;
    private ArrayList<String> colorBridges = new ArrayList<String>();
    private ArrayList<String> colorsports = new ArrayList<String>();
    private Map<String,Bridges> bridgesColor;
    private Map<String,Integer> spotColor;
    private Map<Integer,ArrayList<Bridges>> bridgesByStrand = new HashMap<Integer,ArrayList<Bridges>>();
    private ArrayList<String> bridgesNoUsed = new ArrayList<String>();
    private List<Integer> hilosTomados;
    private boolean isOk;
    private ArrayList<Line> recorrido = new ArrayList<Line>();

    /**
     * Constructor de la clase spiderWeb.
     * Crea una nueva red de telaraña con el radio y la cantidad de brazos especificados.
     *
     * @param radio El radio de la telaraña.
     * @param strands La cantidad de brazos de la telaraña.
     */
    public SpiderWeb(int radio, int strands){
        // Inicialización de los atributos
        this.list = new angles(radio, strands);
        this.radio = radio;
        this.strands = strands;
        this.angle = list.getCant();
        this.lists = list.getList();
        this.bridgesColor = new HashMap<>();
        this.spotColor = new HashMap<>();
        isVisible = false;
        spiderCenter = true;
        goodList = false;
        this.lineList = new ArrayList<Line>();
        xStard = 300;
        yStard = 300;

        for(int i = 0; i < strands ;i++){
            bridgesByStrand.put(i, new ArrayList<>());
        }
        cordenates();
    }

    public SpiderWeb(int strand, int bridge, int favoritestrand){
        radio = 200;
        this.strands = strand;
        this.list = new angles(radio, strands);
        this.angle = list.getCant();
        this.lists = list.getList();
        this.bridgesColor = new HashMap<>();
        this.spotColor = new HashMap<>();
        isVisible = false;
        spiderCenter = true;
        goodList = false;
        this.lineList = new ArrayList<Line>();
        xStard = 300;
        yStard = 300;
        for(int i = 0; i < strands ;i++){
            bridgesByStrand.put(i, new ArrayList<>());
        }
        cordenates();
    }
    
    /**
     * Calcula las coordenadas de los brazos de la telaraña y crea líneas para representarlos.
     */
    private void cordenates(){
        for (Pair<Float, Float> pair : lists){
            float x;
            float y;
            x = pair.getFirst();
            y = pair.getSecond();
            Line arm = new Line(xStard, yStard, x+xStard, yStard-y);
            lineList.add(arm);
        }
    }


    /**
     * Hace visible la red de telaraña y todos los elementos.
     * Si la red no ha sido visible antes, muestra las líneas de los brazos
     * y una araña en el centro. Si ya ha sido visible, simplemente hace visible
     * nuevamente los elementos.
     */
    public void makeVisible(){
        if(!isVisible){
            for(Line arms : lineList){
                arms.makeVisible();
            }
            spider = new Spider((int) xStard, (int) yStard);

            for (String color : bridgesColor.keySet()) {
            Bridges bridge = bridgesColor.get(color);
            bridge.makeVisible();
            }
            spider.makeVisible();
        }
    }

    /**
     * Hace invisible la red de telaraña y todos los elementos.
     * Oculta todas las líneas y los puentes de la red de telaraña.
     */
    public void makeInvisible(){
        for(Line arms : lineList){
            arms.makeInvisible();
        }
        spider.makeInvisible();
        for (String color : bridgesColor.keySet()) {
            Bridges bridge = bridgesColor.get(color);
            bridge.makeInvisible();
        }
        spider.makeInvisible();
    }


    /**
     * Agrega un puente a la red de telaraña.
     *
     * @param color El color del puente.
     * @param distance La distancia desde el centro hasta el punto donde comienza el puente.
     * @param firstStrand El número del brazo donde se conectará el puente.
     */
    public void addBridge(String color, int distance, int firstStrand){
        boolean colorRepe = false;
        for(String color0 : colorBridges){
           if (color0 == color){
               colorRepe = true;
            }
        }
        if (colorRepe){
            JOptionPane.showMessageDialog(null, "No se puede añadir puente del mismo color.");
            isOk = false;
        }else{
            angleFirstStrand = (firstStrand - 1) * angle;
            angleSecondStrand = firstStrand * angle;
            this.firstStrand = firstStrand;
            pointBridge = 0;
            xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
            yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
            x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
            y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));
            // Creación y configuración del puente
            Bridges bridge = new Bridges(xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard - y2Bridge);
            bridge.changeColor(color);
            bridgesColor.put(color, bridge);
            showBridges(pointBridge);
            colorBridges.add(color);
            bridgesNoUsed.add(color);
            bridgesByStrand.get(firstStrand-1).add(bridge);
            isOk = true;
        }
    }

     /**
     * Relocaliza un puente existente en la red de telaraña.
     *
     * @param color El color del puente que se desea relocalizar.
     * @param distance La nueva distancia desde el centro hasta el punto donde comienza el puente.
     */
    public void relocateBridge(String color, int distance){
        if (distance < 0) {
            JOptionPane.showMessageDialog(null, "No se puede reubicar el puente con una distancia negativa.");
            isOk = false;
        }else{
            pointBridge = 1;
            xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
            yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
            x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
            y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));
            Bridges bridge = new Bridges (xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard -y2Bridge);
            bridge.changeColor(color);
            hideBridges();
            bridgesColor.put(color, bridge);
            showBridges(pointBridge);
            int index = 0;
            for (int i =  0; i<strands; i++){
                ArrayList<Bridges> listBridge = bridgesByStrand.get(i);
                for (Bridges l : listBridge){
                    if (l.getColor() == color){
                        bridgesByStrand.get(i).set(index, bridge);
                    }
                    index += 1;
                }
                index = 0;
            }
            isOk = true;
        }
    }

    /**
     * Relocaliza automáticamente un puente en un brazo específico de la red de telaraña.
     */
    private void relocateBridgeAutomatico(String color, int FirstStrand, int distance){
        pointBridge = 1;
        angleFirstStrand = (firstStrand - 1) * angle;
        angleSecondStrand = firstStrand * angle;
        this.firstStrand = firstStrand;
        pointBridge = 0;
        xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
        yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
        x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
        y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));
        Bridges bridge = new Bridges (xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard -y2Bridge);
        bridge.changeColor(color);
        hideBridges();
        bridgesColor.put(color, bridge);
        int index = 0;
            for (int i =  0; i<strands; i++){
                ArrayList<Bridges> listBridge = bridgesByStrand.get(i);
                for (Bridges l : listBridge){
                    if (l.getColor() == color){
                        bridgesByStrand.get(i).set(index, bridge);
                    }
                    index += 1;
                }
                index = 0;
            }
        showBridges(pointBridge);
    }

    /**
     * Elimina un puente de la red de telaraña.
     *
     * @param color El color del puente que se desea eliminar.
     */
    public boolean delBridge(String color){
        Bridges delbridge = bridgesColor.get(color);
        if (delbridge == null) {
            JOptionPane.showMessageDialog(null, "El puente no existe.");
            this.isOk = false; 
        } else {
            delbridge.makeInvisible();
            bridgesColor.remove(color);
            colorBridges.remove(color);
            bridgesNoUsed.remove(color);
            isOk= true;
        }
        return isOk;
    }
    
    /**
     * Agrega un punto de referencia a la red de telaraña.
     *
     * @param color El color del punto de referencia.
     * @param strand El número del brazo donde se agregará el punto de referencia.
     */
   public void addSpot(String color, int strand){
        boolean colorRepe = false;
        for(String color0 : colorsports){
           if (color0 == color){
               colorRepe = true;
            }
        }
        if (colorRepe){
            isOk = false;
            JOptionPane.showMessageDialog(null, "No se puede añadir spot del mismo color.");
        }else{
            Line arm = lineList.get(strand-1);
            arm.changeColor(color);
            lineList.set(strand-1,arm);
            makeVisible();
            spotColor.put(color,strand-1);
            colorsports.add(color);
            isOk = true;
        }
    }

    /**
     * Elimina un punto de referencia de la red de telaraña.
     *
     * @param color El color del punto de referencia que se desea eliminar.
     */
    public void delSpot(String color){
        if (!spotColor.containsKey(color)) {
            JOptionPane.showMessageDialog(null, "El spot no existe.");
            this.isOk = false;
        } else {
            int strand = spotColor.get(color);
            Line arm = lineList.get(strand);
            arm.changeColor("black");
            lineList.set(strand, arm);
            makeVisible();
            spotColor.remove(color);
            colorsports.remove(color);
            isOk = true;
            }
        return;
    }
    
    /**
     * Oculta todos los puentes de la red de telaraña.
     */
    private void hideBridges(){
        for(String color: bridgesColor.keySet()){
            Bridges bridge = bridgesColor.get(color);
            bridge.makeInvisible();
        }
    }

    /**
     * Muestra todos los puentes de la red de telaraña.
     *
     * @param pointBridge Un indicador de si se debe mostrar el puente en una posición específica.
     */
    private void showBridges(int pointBridge){
        if (pointBridge == 0){
            for(String color: bridgesColor.keySet()){
                Bridges bridge = bridgesColor.get(color);
                bridge.makeVisible();
            }
        }else{
            for(String color: bridgesColor.keySet()){
                Bridges bridge = bridgesColor.get(color);
                bridge.makeVisible();
            }
        }
    }

    /**
     * Hace que la araña se siente en un brazo específico de la telaraña.
     *
     * @param strand El número del brazo donde se desea que la araña se siente.
     */
    public void spiderSit(int strand){
        if (strand > 0){
            this.strand = strand;
            isOk = true;
        }else{
            JOptionPane.showMessageDialog(null, "El strand no existe para sentar a la araña.");
            isOk = false;
        }
    }

    /**
     * Hace que la araña camine a lo largo del brazo de la telaraña.
     *
     * @param advance Indica si la araña debe avanzar en el brazo o retroceder.
     */
    public void spiderWalk(boolean advance){
        ArrayList<ArrayList<Integer>> walk = isPosible((int)strand-1);
        if (advance){
            int xAnterior = 300;
            int yAnterior = 300;
            for (ArrayList<Integer> point : walk){
               spider.moveTo(point.get(0),point.get(1));
               Line l = new Line(xAnterior, yAnterior,point.get(0), point.get(1));
               l.changeColor("red");
               l.makeVisible();
               recorrido.add(l);
               xAnterior = point.get(0);
               yAnterior = point.get(1);
            }
        }else{
            ArrayList<Integer> finishPoint = new ArrayList<Integer>();
            float x2 = 300;
            float y2 = 300;
            finishPoint.add((int) x2);
            finishPoint.add((int) y2);
            walk.add(0,finishPoint);
            for (int i = walk.size() - 1; i >= 0; i--) {
                ArrayList<Integer> point = walk.get(i);
                spider.moveTo(point.get(0), point.get(1));
            }
            eraseRecorrido();
        }
    }
    
    private void eraseRecorrido(){
        for (Line l: recorrido){
            l.makeInvisible();
        }
        recorrido = new ArrayList<Line>();
    }
    
    /**
     * Determina si es posible avanzar a lo largo del brazo de la telaraña desde una posición dada.
     *
     * @param strand El número del brazo de la telaraña desde el que se quiere comprobar si es posible avanzar.
     * @return Una lista de listas de enteros que representan los puntos a lo largo del brazo de la telaraña que se pueden alcanzar desde la posición dada.
     */
    private ArrayList<ArrayList<Integer>> isPosible(int strand){
        boolean finishWalk = true;
        ArrayList<ArrayList<Integer>> walk = new ArrayList<ArrayList<Integer>>();
        hilosTomados = new ArrayList<Integer>();
        while (finishWalk){
            if (spotColor.get(colorsports.get(0)) == strand){
                hilosTomados.add(strand +1);
                finishWalk = false;
                ArrayList<Integer> finishPoint = new ArrayList<Integer>();
                Line arm = lineList.get(strand);
                float x2 = arm.getX2();
                float y2 = arm.getY2();
                finishPoint.add((int) x2);
                finishPoint.add((int) y2);
                walk.add(finishPoint);
                break;
            }
            if (bridgesByStrand.get(strand).size() > 0){
                hilosTomados.add(strand +1);
                Bridges brigde = bridgesByStrand.get(strand).get(0);
                float x1 = brigde.getX1();
                float x2 = brigde.getX2();
                float y1 = brigde.getY1();
                float y2 = brigde.getY2();
                ArrayList<Integer> firtPoint = new ArrayList<Integer>();
                ArrayList<Integer> secondPoint = new ArrayList<Integer>();
                firtPoint.add((int) x1);
                firtPoint.add((int) y1);
                secondPoint.add((int) x2);
                secondPoint.add((int) y2);
                walk.add(firtPoint);
                walk.add(secondPoint);
                strand += 1;
            }else{
                finishWalk = false;
                System.out.println("No termina el camino.");
            }
        }
        return walk;
    }
    
    /**
     * Devuelve una lista de colores de los spots que son alcanzables desde la posición actual de la araña en la telaraña.
     *
     * @return Una lista de cadenas que representan los colores de los spots alcanzables desde la posición actual de la araña.
     */
     
    public ArrayList<String> reachableSpot(){
        boolean finishWalk = true;
        ArrayList<String> spots = new ArrayList<String>();
        for (String color : colorsports){
            int hilos = (int) this.strand;
            System.out.println(colorsports);
            while (finishWalk){
                if (spotColor.get(color) == hilos-1 && lineList.get(hilos-1).getColor() == color){
                    finishWalk = false;
                    spots.add(color);
                }
                else if (bridgesByStrand.get(hilos-1).size() > 0){
                    hilos += 1;
                }
                else{
                    finishWalk = false;
                }
                
            }
            finishWalk = true;
        }
        return spots;
    }
    
    
    
    /**
     * Devuelve una lista de los colores de los puentes en la red de telaraña.
     *
     * @return Una lista de los colores de los puentes.
     */
    public ArrayList<String> bridges() {
        System.out.println(colorBridges);
        return  colorBridges;
    }

    /**
     * Devuelve una lista de los colores de los spots en la red de telaraña.
     *
     * @return Una lista de los colores de los spots.
     */
    public ArrayList<String> spots() {
        StringBuilder spotColorsMessage = new StringBuilder("Colores de los spots:\n");
        for (String color : colorsports) {
            spotColorsMessage.append(color).append("\n");
        }
        JOptionPane.showMessageDialog(null, spotColorsMessage.toString(), "Colores de los spots", JOptionPane.INFORMATION_MESSAGE);
        return null;
        }
    
    /**
     * Devuelve el número del brazo donde se encuentra un punto de referencia dado su color.
     *
     * @param color El color del punto de referencia.
     * @return El número del brazo donde se encuentra el punto de referencia, 
     */   
    public int spot(String color) {
        if (!spotColor.containsKey(color)) {
            JOptionPane.showMessageDialog(null, "El spot de color " + color + " no existe en la telaraña.");
            isOk = false;
            return -1;
        } else {
            isOk = true;
            return spotColor.get(color) + 1;
        }
    }


    /**
     * Retorna una lista de hilos (strands) en los cuales se encuentra un puente dado su color.
     *
     * @param color El color del puente.
     * @return Una lista de enteros que representan los hilos donde se encuentra el puente.
     */
    public ArrayList<Integer> bridge(String color) {
        ArrayList<Integer> strandsWithBridge = new ArrayList<>();
        for (int i = 0; i < strands; i++) {
            ArrayList<Bridges> bridges = bridgesByStrand.get(i);
            for (Bridges bridge : bridges) {
                if (bridge.getColor()==color ) {
                    if (strands-1 == i){
                        strandsWithBridge.add(i + 1);
                        strandsWithBridge.add(1);
                        break;
                    }
                    else{
                        strandsWithBridge.add(i + 1);
                        strandsWithBridge.add(i+2);
                        break;
                    }
                }
            }
        }
        //manda el mensaje por una ventanita
        String strandsMessage = "Strand por los que atraviesa el puente:\n";
        for (Integer strand : strandsWithBridge) {
            strandsMessage += strand + "\n";
        }
        JOptionPane.showMessageDialog(null, strandsMessage, "Strand", JOptionPane.INFORMATION_MESSAGE);
        return strandsWithBridge;
        }


    /**
     * Agrega un nuevo brazo a la red de telaraña.
     *
     * Este método incrementa el número de brazos en la red de telaraña, recalcula las coordenadas
     * de los brazos con el nuevo número de brazos y hace visible nuevamente la red de telaraña.
     */
    public void addStrand(){
        makeInvisible();
        strands += 1;
        list = new angles(radio, strands);
        this.angle = list.getCant();
        this.lists = list.getList();
        this.lineList = new ArrayList<>();
        cordenates();
        bridgesByStrand.put(strands-1, new ArrayList<>());
        for (int strand : bridgesByStrand.keySet()){
            for (Bridges l : bridgesByStrand.get(strand)){
                float distance = l.calcularDistancia();
                relocateBridgeAutomatico(l.getColor(), strand, (int) distance);
            }
        }
        for (String color :  spotColor.keySet()){
            int strand = spotColor.get(color);
            Line arm = lineList.get(strand);
            arm.changeColor(color);
            lineList.set(strand, arm);
        }
        eraseRecorrido();
        makeVisible();
    }

    /**
     * Aumenta el tamaño de la red de telaraña según un porcentaje dado.
     *
     * @param porcentage El porcentaje por el cual se aumentará el tamaño de la red.
     */
    public boolean enlarge(int porcentage) {
        makeInvisible();
        if (porcentage < 0) {
            JOptionPane.showMessageDialog(null, "No se puede agrandar con numeros negativos.", "Error", JOptionPane.INFORMATION_MESSAGE);
            makeVisible(); 
            isOk = false;
        }else{
            this.radio = radio * (100 + porcentage) / 100;
            list = new angles(radio, strands);
            this.angle = list.getCant();
            this.lists = list.getList();
            this.lineList = new ArrayList<>();
            cordenates();
            for (String color : spotColor.keySet()) {
                int strand = spotColor.get(color);
                Line arm = lineList.get(strand);
                arm.changeColor(color);
                lineList.set(strand, arm);
            }
            makeVisible();
            isOk = true;
        }
        return isOk;
    }

    /**
     * Devuelve una lista de puentes sin usar en la red de telaraña.
     *
     * @return Una lista de cadenas que representan los colores de los puentes sin usar.
     */
    public ArrayList<String> unUsedBridges(){
        return bridgesNoUsed;
    }


    /**
     * Obtiene el listado de los hilos tomados por la araña la última vez que caminó.
     *
     * @return Una lista de enteros que representan los números de los brazos tomados por la araña.
     */
    public List<Integer> spiderLastPath() {
        return hilosTomados;
    }

    /**
     * Finaliza la clase y realiza las operaciones necesarias para limpiar los datos y hacer invisible la telaraña y la araña.
     * Cierra la ventana.
     */
    public void finish() {
        makeInvisible();
        if (spider != null) {
            spider.makeInvisible();
            spider = null;
        }
        lineList.clear();
        bridgesColor.clear();
        spotColor.clear();
        bridgesByStrand.clear();
        System.exit(0);
    }
    
    /**
     * Devuelve el estado de los metodos.
     *
     * @return {@code true} si la araña está en un estado válido para continuar con las operaciones,
     *         {@code false} de lo contrario.
     */
    public boolean ok(){
        return isOk;
    }
}
