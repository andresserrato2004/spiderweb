import java.util.*;
import java.io.File;
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
public class spiderWeb {
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
    private Map<String,Line> bridgesColor;
    private Map<String,Integer> spotColor;
    private Map<Integer,ArrayList<Line>> bridgesByStrand = new HashMap<Integer,ArrayList<Line>>();
    private ArrayList<String> bridgesNoUsed = new ArrayList<String>();
    private List<Integer> hilosTomados;

    /**
     * Constructor de la clase spiderWeb.
     * Crea una nueva red de telaraña con el radio y la cantidad de brazos especificados.
     *
     * @param radio El radio de la telaraña.
     * @param strands La cantidad de brazos de la telaraña.
     */
    public spiderWeb(int radio, int strands){
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
            Line bridge = bridgesColor.get(color);
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
            Line bridge = bridgesColor.get(color);
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
        //se verifica que no se repita el color
        boolean colorRepe = false;
        for(String color0 : colorBridges){
           if (color0 == color){
               colorRepe = true;
            }
        }
        if (colorRepe){
            JOptionPane.showMessageDialog(null, "No se puede añadir puente del mismo color.");
            return;
        }
        // Cálculo de las coordenadas del puente
        angleFirstStrand = (firstStrand - 1) * angle;
        angleSecondStrand = firstStrand * angle;
        this.firstStrand = firstStrand;
        pointBridge = 0;
        xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
        yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
        x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
        y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));
        // Creación y configuración del puente
        Line bridge = new Line(xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard - y2Bridge);
        bridge.changeColor(color);
        bridgesColor.put(color, bridge);
        showBridges(pointBridge);
        colorBridges.add(color);
        bridgesNoUsed.add(color);
        bridgesByStrand.get(firstStrand-1).add(bridge);
    }

     /**
     * Relocaliza un puente existente en la red de telaraña.
     *
     * @param color El color del puente que se desea relocalizar.
     * @param distance La nueva distancia desde el centro hasta el punto donde comienza el puente.
     */
    public void relocateBridge(String color, int distance){
        pointBridge = 1;

        xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
        yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
        x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
        y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));

        Line bridge = new Line (xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard -y2Bridge);
        bridge.changeColor(color);
        hideBridges();
        bridgesColor.put(color, bridge);
        showBridges(pointBridge);
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

        Line bridge = new Line (xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard -y2Bridge);
        bridge.changeColor(color);
        hideBridges();
        bridgesColor.put(color, bridge);
        showBridges(pointBridge);
    }

    /**
     * Elimina un puente de la red de telaraña.
     *
     * @param color El color del puente que se desea eliminar.
     */
    public void delBridge(String color){
        Line delbridge = bridgesColor.get(color);
        delbridge.makeInvisible();
        bridgesColor.remove(color);
        colorBridges.remove(color);
        bridgesNoUsed.remove(color);
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
            JOptionPane.showMessageDialog(null, "No se puede añadir puente del mismo color.");
            return;
        }
        Line arm = lineList.get(strand-1);
        arm.changeColor(color);
        lineList.set(strand-1,arm);
        makeVisible();
        spotColor.put(color,strand-1);
        colorsports.add(color);
    }

    /**
     * Elimina un punto de referencia de la red de telaraña.
     *
     * @param color El color del punto de referencia que se desea eliminar.
     */
    public void delSpot(String color){
        int strand = spotColor.get(color);
        Line arm = lineList.get(strand);
        arm.changeColor("black");
        lineList.set(strand,arm);
        makeVisible();
        spotColor.remove(color);
        colorsports.remove(color);
    }

    /**
     * Oculta todos los puentes de la red de telaraña.
     */
    private void hideBridges(){
        for(String color: bridgesColor.keySet()){
            Line bridge = bridgesColor.get(color);
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
                Line bridge = bridgesColor.get(color);
                bridge.makeVisible();
            }
        }else{
            for(String color: bridgesColor.keySet()){
                Line bridge = bridgesColor.get(color);
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
        this.strand = strand;
    }

    /**
     * Hace que la araña camine a lo largo del brazo de la telaraña.
     *
     * @param advance Indica si la araña debe avanzar en el brazo o retroceder.
     */
    public void spiderWalk(boolean advance){
        moveSpider((int)strand);
    }

    /**
     * Mueve la araña a lo largo del brazo de la telaraña.
     *
     * @param strand El número del brazo por el cual se mueve la araña.
     */
    private void moveSpider(int strand) {
        if (bridgesByStrand.get(strand - 1).size() == 0) {
            // No hay puentes en este brazo, simplemente mueve la araña al final del brazo
            Pair<Float, Float> cordenatepair = lists.get(strand);
            this.x1cordenate = cordenatepair.getFirst();
            this.y1cordenate = cordenatepair.getSecond();
            spider.moveTo((int) x1cordenate, (int) y1cordenate);
        } else {
            // Hay un puente en este brazo, atraviesa el puente y mueve la araña al siguiente brazo
            Line bridge = bridgesByStrand.get(strand - 1).get(0);
            // Calcula las coordenadas relativas al puente
            this.x1cordenate = bridge.getX1();
            this.y1cordenate = bridge.getY1();
            this.x2cordenate = bridge.getX2();
            this.y2cordenate = bridge.getY2();
            // Mueve la araña al inicio del puente
            spider.moveTo((int) x1cordenate, (int) y1cordenate);
            // Mueve la araña al final del puente
            spider.moveTo((int) -x2cordenate, (int) -y2cordenate);
           }
    }

    /**
     * Devuelve una lista de los colores de los puentes en la red de telaraña.
     *
     * @return Una lista de los colores de los puentes.
     */
    public ArrayList<String> bridges() {
        // Construir una cadena para almacenar los colores de los puentes
        String bridgeColorsMessage = "Colores de los puentes:\n";
        for (String color : colorBridges) {
            bridgeColorsMessage += color + "\n";
        }
        // Mostrar el mensaje con los colores de los puentes
        JOptionPane.showMessageDialog(null, bridgeColorsMessage, "Colores", JOptionPane.INFORMATION_MESSAGE);
        return null;
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
     * Retorna una lista de hilos (strands) en los cuales se encuentra un puente dado su color.
     *
     * @param color El color del puente.
     * @return Una lista de enteros que representan los hilos donde se encuentra el puente.
     */
    public ArrayList<Integer> bridge(String color) {
        ArrayList<Integer> strandsWithBridge = new ArrayList<>();
        for (int i = 0; i < strands; i++) {
            ArrayList<Line> bridges = bridgesByStrand.get(i);
            for (Line bridge : bridges) {
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
        for (int strand : bridgesByStrand.keySet()){
            for (Line l : bridgesByStrand.get(strand)){
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
        makeVisible();
        }

    /**
     * Aumenta el tamaño de la red de telaraña según un porcentaje dado.
     *
     * @param porcentage El porcentaje por el cual se aumentará el tamaño de la red.
     */
    public void enlarge(int porcentage){
        makeInvisible();
        this.radio = radio*(100+porcentage)/100;
        list = new angles(radio, strands);
        this.angle = list.getCant();
        this.lists = list.getList();
        this.lineList = new ArrayList<>();
        cordenates();

        for (String color :  spotColor.keySet()){
            int strand = spotColor.get(color);
            Line arm = lineList.get(strand);
            arm.changeColor(color);
            lineList.set(strand, arm);
        }
        makeVisible();
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
}
