import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;
import java.lang.String;

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
    private int radio;
    private int strands;
    private float angle;
    private final float xStard;
    private final float yStard;
    private float xBridge;
    private float yBridge;
    private float x2Bridge;
    private float y2Bridge;
    private float angleFirstStrand;
    private float angleSecondStrand;
    private float strand;
    private boolean isVisible;
    private boolean isBridges;
    boolean isSpot = true;
    private angles list;
    private List<Pair<Float, Float>> lists;
    private ArrayList<Strands> lineList;
    private Spider spider;
    private final ArrayList<String> colorBridges = new ArrayList<String>();
    private final ArrayList<String> colorSports = new ArrayList<String>();
    private final Map<String,Bridges> bridgesColor;
    private final Map<String,Integer> bridgeStrand = new HashMap<>();
    private final Map<String,Integer> spotColor;
    private final Map<Integer,ArrayList<Bridges>> bridgesByStrand = new HashMap<Integer,ArrayList<Bridges>>();
    private final ArrayList<String> bridgesNoUsed = new ArrayList<String>();
    private List<Integer> hilosTomados;
    private boolean isOk;
    private ArrayList<Line> recorrido = new ArrayList<Line>();
    private ArrayList<Bridges> used = new ArrayList<Bridges>();



    /**
     * Constructor de la clase spiderWeb.
     * Crea una nueva red de telaraña con el radio y la cantidad de brazos especificados.
     *
     * @param radio El radio de la telaraña.
     * @param strands La cantidad de brazos de la telaraña.
     */
    public SpiderWeb(int radio, int strands){

        this.list = new angles(radio, strands);
        this.radio = radio;
        this.strands = strands;
        this.angle = list.getCant();
        this.lists = list.getList();
        this.bridgesColor = new HashMap<>();
        this.spotColor = new HashMap<>();
        isVisible = false;
        isBridges = false;
        this.lineList = new ArrayList<Strands>();
        xStard = 300;
        yStard = 300;
        spider = new Spider((int) xStard, (int) yStard);
        for(int i = 0; i < strands ;i++){
            bridgesByStrand.put(i, new ArrayList<>());
        }
        cordenates();
    }

    public SpiderWeb(int strand, int favoritestrand, int[][] bridgesData){


        radio = 200;
        this.strands = strand;
        Canvas canvas = new Canvas("SpiderWeb Canvas", 700, 700, Color.white);

        this.list = new angles(radio, strands);
        this.angle = list.getCant();
        this.lists = list.getList();
        this.bridgesColor = new HashMap<>();
        this.spotColor = new HashMap<>();
        isVisible = false;
        isBridges = false;
        this.lineList = new ArrayList<Strands>();
        xStard = 300;
        yStard = 300;
        spider = new Spider((int) xStard, (int) yStard);
        for(int i = 0; i < strands ;i++){
            bridgesByStrand.put(i, new ArrayList<>());
        }
        for (int[] bridgeData : bridgesData) {
            int firststrand = bridgeData[1];
            int distance = bridgeData[0];
            String color = canvas.generateRandomColor();
            addBridge(color, distance, firststrand);
        }
        cordenates();
        addSpot("yellow", favoritestrand);

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
            Strands arm = new Strands(xStard, yStard, x+xStard, yStard-y);
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
        isSpot = false;

        if(!isVisible && !isSpot){
            for (String color : bridgesColor.keySet()) {
                Bridges bridge = bridgesColor.get(color);
                bridge.makeVisible();
            }
            for(Strands arms : lineList){
                arms.makeVisible();
            }
            spider.makeVisible();
            isBridges = true;
            isVisible = true;
            for (Line l: recorrido){
                l.makeVisible();
            }
        }
    }

    /**
     * Hace invisible la red de telaraña y todos los elementos.
     * Oculta todas las líneas y los puentes de la red de telaraña.
     */
    public void makeInvisible(){
        for(Strands arms : lineList){
            arms.makeInvisible();
        }
        spider.makeInvisible();
        for (String color : bridgesColor.keySet()) {
            Bridges bridge = bridgesColor.get(color);
            bridge.makeInvisible();
        }
        spider.makeInvisible();
        isBridges = false;
        isSpot = true;
        isVisible = false;
        for (Line l: recorrido){
            l.makeInvisible();
        }

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
            if (Objects.equals(color0, color)) {
                colorRepe = true;
                break;
            }
        }
        if (colorRepe){
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "No se puede añadir puente del mismo color.");
            }
            isOk = false;
        }else{
            angleFirstStrand = (firstStrand - 1) * angle;
            angleSecondStrand = firstStrand * angle;
            xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
            yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
            x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
            y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));
            int endStrand = firstStrand;
            if(firstStrand == strands){
                endStrand = 0;
            }
            Bridges bridge = new Bridges(xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard - y2Bridge,firstStrand-1,endStrand, distance);
            bridge.changeColor(color);
            bridgesColor.put(color, bridge);
            colorBridges.add(color);
            bridgesNoUsed.add(color);
            bridgesByStrand.get(firstStrand-1).add(bridge);
            if(firstStrand == strands){
                bridgesByStrand.get(0).add(bridge);    
            }
            else
            {
                bridgesByStrand.get(firstStrand).add(bridge);
            }
            
            bridgeStrand.put(color, firstStrand);
            isOk = true;
            if (isBridges){
                bridge.makeVisible();
            }

        }
    }

     /**
     * Relocaliza un puente existente en la red de telaraña.
     *
     * @param color El color del puente que se desea relocalizar.
     * @param distance La nueva distancia desde el centro hasta el punto donde comienza el puente.
     */
    public void relocateBridge(String color, float distance){
        angleFirstStrand = (bridgeStrand.get(color) - 1) * angle;
        angleSecondStrand = bridgeStrand.get(color) * angle;
        if (distance < 0) {
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "No se puede reubicar el puente con una distancia negativa.");
            }
            isOk = false;
        }else{
            xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
            yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
            x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
            y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));
            Bridges bridge = new Bridges (xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard -y2Bridge,bridgesColor.get(color).hiloInicial,bridgesColor.get(color).hiloFinal, distance);
            bridge.changeColor(color);
            hideBridges();
            bridgesColor.put(color, bridge);
            if (!isSpot){
                showBridges();
            }
            int index = 0;
            for (int i =  0; i<strands; i++){
                ArrayList<Bridges> listBridge = bridgesByStrand.get(i);
                for (Bridges l : listBridge){
                    if (Objects.equals(l.getColor(), color)){
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
    private void relocateBridgeAutomatico(String color, float distance){

        angleFirstStrand = (bridgeStrand.get(color) - 1) * angle;
        angleSecondStrand = bridgeStrand.get(color) * angle;
        xBridge = distance * (float) Math.cos(Math.toRadians(angleFirstStrand));
        yBridge = distance * (float) Math.sin(Math.toRadians(angleFirstStrand));
        x2Bridge = distance * (float) Math.cos(Math.toRadians(angleSecondStrand));
        y2Bridge = distance * (float) Math.sin(Math.toRadians(angleSecondStrand));
        Bridges bridge = new Bridges (xStard + xBridge, yStard - yBridge, xStard + x2Bridge , yStard -y2Bridge,bridgesColor.get(color).hiloInicial,bridgesColor.get(color).hiloFinal, distance );
        bridge.changeColor(color);


        hideBridges();
        bridgesColor.put(color, bridge);
        int index = 0;
            for (int i =  0; i<strands; i++){
                ArrayList<Bridges> listBridge = bridgesByStrand.get(i);
                for (Bridges l : listBridge){
                    if (Objects.equals(l.getColor(), color)){
                        bridgesByStrand.get(i).set(index, bridge);
                    }
                    index += 1;
                }
                index = 0;
            }
        if (isBridges){
            showBridges();
        }

    }

    /**
     * Elimina un puente de la red de telaraña.
     *
     * @param color El color del puente que se desea eliminar.
     */
    public boolean delBridge(String color){
        Bridges delbridge = bridgesColor.get(color);
        if (delbridge == null) {
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "El puente no existe.");
            }
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
        for(String color0 : colorSports){
            if (Objects.equals(color0, color)) {
                colorRepe = true;
                break;
            }
        }
        if (colorRepe){
            isOk = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "No se puede añadir spot del mismo color.");
            }
        }else{
            Strands arm = lineList.get(strand-1);
            arm.changeColor(color);
            lineList.set(strand-1,arm);
            if (!isSpot){
                makeVisible();
                arm.makeVisible();
            }
            spotColor.put(color,strand-1);
            colorSports.add(color);
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
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "El spot no existe.");
            }
            this.isOk = false;
        } else {
            int strand = spotColor.get(color);

            Strands arm = lineList.get(strand);
            arm.changeColor("black");
            lineList.set(strand, arm);
            if(!isSpot){
                for(Strands arms : lineList){
                    arms.makeVisible();
                }
                spider.makeVisible();

            }
            spotColor.remove(color);
            colorSports.remove(color);
            isOk = true;
            }
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
     */
    private void showBridges(){
        for(String color: bridgesColor.keySet()){
            Bridges bridge = bridgesColor.get(color);
            bridge.makeVisible();
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
            spider.spiderSit();
            isOk = true;
        }else{
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "El strand no existe para sentar a la araña.");
            }
            isOk = false;
        }
    }

    /**
     * Hace que la araña camine a lo largo del brazo de la telaraña.
     *
     * @param advance Indica si la araña debe avanzar en el brazo o retroceder.
     */
    public void spiderWalk(boolean advance) {
        if (this.spider.isSpiderSitting()) {
            ArrayList<ArrayList<Float>> walk = isPosible((int) strand - 1);
            if(walk.isEmpty()) {
                if (isVisible) {
                    JOptionPane.showMessageDialog(null, "La araña no tiene posibilidad de llegar al camino preferido desde el hilo:" + strand, "No llega al hilo preferido", JOptionPane.INFORMATION_MESSAGE);
                }
                return;
            }
            if (advance) {
                float xAnterior = 300;
                float yAnterior = 300;
                for (ArrayList<Float> point : walk) {
                    spider.moveTo(point.get(0), point.get(1));
                    Line l = new Line(xAnterior, yAnterior, point.get(0), point.get(1));
                    l.changeColor("blue");
                    l.makeVisible();
                    recorrido.add(l);
                    xAnterior = point.get(0);
                    yAnterior = point.get(1);
                }
            } else {
                ArrayList<Float> finishPoint = new ArrayList<Float>();
                finishPoint.addAll(Arrays.asList((float)300,(float)300));
                walk.add(0, finishPoint);
                for (int i = walk.size() - 1; i >= 0; i--) {
                    ArrayList<Float> point = walk.get(i);
                    spider.moveTo(point.get(0), point.get(1));
                }
                eraseRecorrido();
            }
        }else{
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "La araña no puede caminar", "Error en caminar", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }



    private void eraseRecorrido(){
        for (Line l: recorrido){
            l.makeInvisible();
        }
        recorrido = new ArrayList<Line>();
    }

    /**
     * Determina el siguiente puente que tiene que tomar la araña teniendo en cuenta que solo puede ir hacia adelante.
     *
     * @param listBrigde es una lista de puentes que tiene el hilo donde esta actualmente la araña
     * @param strand es el hilo donde esta parada la araña
     * @param xSpiderActual ySpiderActual es la posicion actual de la araña para poder calcular cual brige
     * @return devuelve un Map de un elemento que indica si encontro el bridge y el puente mas cercano hacia adelante de la araña.
     */
    private Map<Boolean,Bridges> nextBridge(ArrayList<Bridges> listBrigde, int strand, float xSpiderActual, float ySpiderActual){
        float distanceMinSB = 3000;
        float distanceSpider = (float) Math.sqrt(Math.pow(xSpiderActual - xStard, 2) + Math.pow(ySpiderActual - yStard, 2));
        Bridges bridges = new Bridges(0,0,0,0,1,2,0);
        Map<Boolean,Bridges> brigdeMap = new HashMap<Boolean,Bridges>();
        boolean foundBridge = false;
        for (Bridges b: listBrigde){
            ArrayList<Float> points = b.returnPoint(strand);
            float distance = (float) Math.sqrt(Math.pow(points.get(0) - xStard, 2)+ Math.pow(points.get(1) - yStard, 2));
            // nos fijamos que la distancia sea mayor a la distancia de la araña asi tomamos un puente que esta hacia delante de la araña y ademas nos aseguramos que sea el mas cercano a la araña
            if (distance > distanceSpider && distance-distanceSpider < distanceMinSB){
                foundBridge = true;
                distanceMinSB = b.distance - distanceSpider;
                bridges = b;
            }
        }
        brigdeMap.put(foundBridge, bridges);
        return brigdeMap;
    }



    /**
     * Determina si es posible avanzar a lo largo del brazo de la telaraña desde una posición dada hasta el primer puente encontrado.
     *
     * @param strand El número del brazo de la telaraña desde el que se quiere comprobar si es posible avanzar.
     * @return Una lista de listas de enteros que representan los puntos a lo largo del brazo de la telaraña hasta el primer puente encontrado.
     */

    private ArrayList<ArrayList<Float>> isPosible(int strand) {
        ArrayList<ArrayList<Float>> walk = new ArrayList<>();
        hilosTomados = new ArrayList<>();
        float xSpiderActual = 300;
        float ySpiderActual = 300;
        boolean foundBridge = false;
        while (!foundBridge) {
            Map<Boolean,Bridges> bridgeMap = nextBridge(bridgesByStrand.get(strand), strand, xSpiderActual, ySpiderActual);
            boolean bridgeExists = new ArrayList<>(bridgeMap.keySet()).get(0);
            if (bridgesByStrand.get(strand).size() > 0 && bridgeExists) {
                hilosTomados.add(strand + 1);
                Bridges bridge = bridgeMap.get(bridgeExists);
                ArrayList<Float> points = bridge.returnPointAcomodados(strand);
                ArrayList<Float> firstPoint = new ArrayList<>(Arrays.asList(points.get(0),points.get(1)));
                ArrayList<Float> secondPoint = new ArrayList<>(Arrays.asList(points.get(2),points.get(3)));
                xSpiderActual = points.get(2);
                ySpiderActual = points.get(3);
                walk.addAll(Arrays.asList(firstPoint,secondPoint));
                strand = (bridge.hiloInicial == strand) ? (strand == strands-1 ? 0 : strand + 1) : (strand == 0 ? strands -1 : strand - 1);
            } else if (spotColor.get(colorSports.get(0)) == strand) {
                hilosTomados.add(strand + 1);
                Strands arm = lineList.get(strand);
                ArrayList<Float> finishPoint = new ArrayList<>(Arrays.asList(arm.getX2(),arm.getY2()));
                walk.add(finishPoint);
                foundBridge = true;
            } else {
                if (spotColor.get(colorSports.get(0)) != strand) {
                    walk.clear();
                    break;
                }
                strand = (strand >= strands-1) ? 0 : strand + 1;
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
        for (String color : colorSports){
            int hilos = (int) this.strand;

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
        StringBuilder message = new StringBuilder();
        for (String color : colorBridges) {
            message.append(color).append("\n");
        }
        if (isVisible){
            JOptionPane.showMessageDialog(null, message.toString(), "Colores de Puentes", JOptionPane.INFORMATION_MESSAGE);
        }
        return colorBridges;
    }

    /**
     * Devuelve una lista de los colores de los spots en la red de telaraña.
     *
     * @return Una lista de los colores de los spots.
     */
    public ArrayList<String> spots() {
        StringBuilder spotColorsMessage = new StringBuilder("Colores de los spots:\n");
        for (String color : colorSports) {
            spotColorsMessage.append(color).append("\n");
        }
        if (isVisible){
            JOptionPane.showMessageDialog(null, spotColorsMessage.toString(), "Colores de los spots", JOptionPane.INFORMATION_MESSAGE);
        }
        return colorSports;
        }
    
    /**
     * Devuelve el número del brazo donde se encuentra un punto de referencia dado su color.
     *
     * @param color El color del punto de referencia.
     * @return El número del brazo donde se encuentra el punto de referencia, 
     */   
    public int spot(String color) {
        if (!spotColor.containsKey(color)) {
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "El spot de color " + color + " no existe en la telaraña.");
            }
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
                if (Objects.equals(bridge.getColor(), color)) {
                    strandsWithBridge.add(i + 1);
                    break;

                }
            }
        }
        //manda el mensaje por una ventanita
        StringBuilder strandsMessage = new StringBuilder("Strand por los que atraviesa el puente:\n");
        for (int i = 0; i < strandsWithBridge.size(); i++) {
            strandsMessage.append(strandsWithBridge.get(i));
            // Agregar coma si no es el último elemento
            if (i < strandsWithBridge.size() - 1) {
                strandsMessage.append(", ");
            }
        }
        if (isVisible){
            JOptionPane.showMessageDialog(null, strandsMessage.toString(), "Strand", JOptionPane.INFORMATION_MESSAGE);
        }
        return strandsWithBridge;
    }


    /**
     * Agrega un nuevo brazo a la red de telaraña.
     *
     * Este método incrementa el número de brazos en la red de telaraña, recalcula las coordenadas
     * de los brazos con el nuevo número de brazos y hace visible nuevamente la red de telaraña.
     */
    public void addStrand(){
        boolean wasVisible = isVisible;
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
                float distance = l.distance;
                l.makeInvisible();
                relocateBridgeAutomatico(l.getColor(), distance);
            }
        }
        for (String color :  spotColor.keySet()){
            int strand = spotColor.get(color);
            Strands arm = lineList.get(strand);
            arm.changeColor(color);
            
            lineList.set(strand, arm);
        }
        eraseRecorrido();
        if (wasVisible && !isVisible) {
            makeVisible();

        }
    }

    /**
     * Aumenta el tamaño de la red de telaraña según un porcentaje dado.
     *
     * @param porcentage El porcentaje por el cual se aumentará el tamaño de la red.
     */
    public boolean enlarge(int porcentage) {
        if (porcentage < 0) {
            if(isVisible){
                JOptionPane.showMessageDialog(null, "No se puede agrandar con numeros negativos.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            isOk = false;
        }else{
            boolean wasVisible = isVisible;
            makeInvisible();
            this.radio = radio * (100 + porcentage) / 100;
            list = new angles(radio, strands);
            this.angle = list.getCant();
            this.lists = list.getList();
            this.lineList = new ArrayList<>();
            cordenates();
            for (String color : spotColor.keySet()) {
                int strand = spotColor.get(color);
                Strands arm = lineList.get(strand);
                arm.changeColor(color);
                lineList.set(strand, arm);
            }
            if (wasVisible && !isVisible) {
                makeVisible();
            }

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
        System.exit(0);
    }

    private class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getFirst() {
            return key;
        }

        public V getSecond() {
            return value;
        }
    }
    private class angles {
        private final List<Pair<Float, Float>> list;
        private final float cant;

        /**
         * Crea una nueva instancia de la clase angles.
         * @param radio El radio del círculo.
         * @param count La cantidad de pares de coordenadas a generar.
         */
        public angles(int radio , int count){
            this.list = new ArrayList<>();
            float totalAngle;
            float angle;
            angle = 1;
            totalAngle = 360;
            this.cant = totalAngle/count;
            while (angle < totalAngle){
                list.add(new Pair<>((float)Math.round((radio*Math.cos(Math.toRadians(angle)))),(float)Math.round((radio*Math.sin(Math.toRadians(angle))))));
                angle += cant;
            }
        }

        /**
         * Obtiene la lista de pares de coordenadas generada.
         * @return La lista de pares de coordenadas.
         */
        public List<Pair<Float, Float>> getList(){
            return list;
        }

        /**
         * Obtiene la cantidad de ángulo entre los pares de coordenadas.
         * @return La cantidad de ángulo entre los pares de coordenadas.
         */
        public float getCant(){
            return cant;
        }
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
