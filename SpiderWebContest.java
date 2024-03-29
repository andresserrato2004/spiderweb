
import java.awt.*;
import java.util.*;
// el problema se llama Spider Walk, se trata de una telaraña que recibe n hilos, m puentes entre hilos y s es el hilo favorito para dormir de la araña
// el rpoblema inicialemte tiene unos puentes entre hilos, con una distancia de el centro de la araña y un hilo inicial donde esta el puente,
// el otro extremo del puente es el hilo siguiente que esta en contra de las manecillas del reloj, el problema pide que se calcule la cantidad minima de puentes
// que la araña debe poner para que de determinado hilo de start, llege a su favorito s, el problema se resuelve con un segment tree, donde se calcula la distancia
// minima de cada hilo a s, y se va actualizando con los puentes que se van poniendo

// la clase Node es para guardar los valores de los nodos del segment tree
class Node {
    public int min_dec, min_inc;
}

public class SpiderWebContest {
    // N es el numero máximo de hilos
    static final int N = 200005;
    // INF es la distancia máxima que hay desde el centro de la telaraña hasta el extremo de un hilo
    static final int INF = (int) 1e9;
    // seg es el segment tree que se va a usar para guardar los valores de los nodos
    static Node[] seg = new Node[N * 4];
    // n es el número de hilos, s es el hilo favorito de la araña
    static int n, s;

    private SpiderWeb spiderweb;
    private final Canvas canva = new  Canvas("spiderwebcontest", 800, 800, Color.WHITE);
    private boolean simulatefake = false;

    private ArrayList<Object> colors = new ArrayList<>();


    /**
     * updateMin es una funcion que se usa para actualizar el valor de un nodo del segment tree por el minimo entre el actual y el valor que se le pasa
     *
     * @param x   es el valor actual del nodo
     * @param val es el valor que se le quiere asignar al nodo
     * @return el minimo entre x y val
     **/
    static int updateMin(int x, int val) {
        return Math.min(x, val);
    }

    /**
     * down es una funcion que se usa para propagar los valores de los nodos del segment tree hacia sus hijos cuando se hace un update en un nodo
     *
     * @param l          es el limite izquierdo del rango que se esta viendo
     * @param r          es el limite derecho del rango que se esta viendo
     * @param nodoActual es el nodo que se esta viendo
     **/
    static void down(int l, int r, int nodoActual) {
        int mid = (l + r) / 2;
        if (seg[nodoActual].min_dec != INF) {
            seg[nodoActual * 2].min_dec = updateMin(seg[nodoActual * 2].min_dec, seg[nodoActual].min_dec);
            seg[nodoActual * 2 + 1].min_dec = updateMin(seg[nodoActual * 2 + 1].min_dec, seg[nodoActual].min_dec - (mid + 1 - l));
        }
        if (seg[nodoActual].min_inc != INF) {
            seg[nodoActual * 2].min_inc = updateMin(seg[nodoActual * 2].min_inc, seg[nodoActual].min_inc);
            seg[nodoActual * 2 + 1].min_inc = updateMin(seg[nodoActual * 2 + 1].min_inc, seg[nodoActual].min_inc + (mid + 1 - l));
        }
        // despues de propagar los valores se reinician los valores del nodo actual
        seg[nodoActual].min_dec = seg[nodoActual].min_inc = INF;
    }

    /**
     * build es una funcion que se usa para construir el segment tree con los valores iniciales de los nodos hoja que son los hilos de la telaraña
     * en los nodos hoja, min_dec y min_inc son la distancia minima de un hilo a s sin puentes inicialmente
     *
     * @param l          es el limite izquierdo del rango que se esta viendo
     * @param r          es el limite derecho del rango que se esta viendo
     * @param nodoActual es el nodo que se esta viendo
     */
    static void build(int l, int r, int nodoActual) {
        seg[nodoActual].min_dec = INF;
        seg[nodoActual].min_inc = INF;
        if (l == r) {
            seg[nodoActual].min_dec = seg[nodoActual].min_inc = Math.min(Math.abs(l - s), Math.min(Math.abs(n + l - s), Math.abs(l - n - s)));
            return;
        }
        int mid = (l + r) / 2;
        build(l, mid, nodoActual * 2);
        build(mid + 1, r, nodoActual * 2 + 1);
    }

    /**
     * modify es una funcion que se usa para modificar los valores de los nodos del segment tree en un rango determinado con un valor determinado
     *
     * @param L          Representa el límite izquierdo del rango en el que se realizará la modificación.
     * @param R          Representa el límite derecho del rango en el que se realizará la modificación.
     * @param l          Representa el límite izquierdo del rango actual del nodo en el árbol de segmentos.
     * @param r          Representa el límite derecho del rango actual del nodo en el árbol de segmentos.
     * @param nodoActual Representa el índice del nodo actual en el árbol de segmentos.
     * @param v          Es el valor con el que se va a modificar el nodo
     * @param type       Es el tipo de modificación que se va a realizar, si es 1 se va a modificar el valor min_dec, si es 0 se va a modificar el valor min_inc
     */
    static void modify(int L, int R, int l, int r, int nodoActual, int v, int type) {
        // si el rango que se esta viendo esta completamente dentro del rango que se quiere modificar se modifica el valor del nodo
        if (L <= l && R >= r) {
            if (type == 1)
                seg[nodoActual].min_dec = Math.min(seg[nodoActual].min_dec, v);
            else
                seg[nodoActual].min_inc = Math.min(seg[nodoActual].min_inc, v);
            return;
        }
        // se llama a la funcion down para propagar los valores de los nodos hacia sus hijos
        down(l, r, nodoActual);
        int mid = (l + r) / 2;
        // si el rango que se quiere modificar esta en el rango izquierdo se llama recursivamente a modify con el nodo izquierdo
        if (L <= mid) {
            modify(L, Math.min(mid, R), l, mid, nodoActual * 2, v, type);
            if (type == 1)
                v -= mid + 1 - L;
            else
                v += mid + 1 - L;
        }
        // si el rango que se quiere modificar esta en el rango derecho se llama recursivamente a modify con el nodo derecho
        if (R > mid)
            modify(Math.max(L, mid + 1), R, mid + 1, r, nodoActual * 2 + 1, v, type);
    }

    /**
     * La función smodify se utiliza para modificar el valor de un solo nodo en el árbol de segmentos
     *
     * @param x          es el nodo que se va a modificar
     * @param l          es el limite izquierdo del rango que se esta viendo
     * @param r          es el limite derecho del rango que se esta viendo
     * @param nodoActual es el nodo que se esta viendo
     * @param v          es el valor que se le va a asignar al nodo
     */
    static void smodify(int x, int l, int r, int nodoActual, int v) {
        // si el nodo que se quiere modificar es una hoja se le asigna el valor v
        if (l == r) {
            seg[nodoActual].min_dec = seg[nodoActual].min_inc = v;
            return;
        }
        // se llama a la funcion down para propagar los valores de los nodos hacia sus hijos
        down(l, r, nodoActual);
        int mid = (l + r) / 2;
        // si el nodo que se quiere modificar esta en el rango izquierdo se llama recursivamente a smodify con el nodo izquierdo
        if (x <= mid)
            smodify(x, l, mid, nodoActual * 2, v);
            // si el nodo que se quiere modificar esta en el rango derecho se llama recursivamente a smodify con el nodo derecho
        else
            smodify(x, mid + 1, r, nodoActual * 2 + 1, v);
    }

    /**
     * La función query se utiliza para hacer una consulta en el árbol de segmentos y obtener el valor minimo de un nodo en un rango determinado
     *
     * @param x          la posición del nodo que se quiere consultar
     * @param l          el limite izquierdo del rango que se esta viendo
     * @param r          el limite derecho del rango que se esta viendo
     * @param nodoActual el nodo que se esta viendo
     * @return el valor minimo del nodo que se quiere consultar
     */
    static int query(int x, int l, int r, int nodoActual) {
        // si el rango que se esta viendo es igual al nodo que se quiere consultar se retorna el valor minimo del nodo
        if (l == r) return Math.min(seg[nodoActual].min_dec, seg[nodoActual].min_inc);
        // se llama a la funcion down para propagar los valores de los nodos hacia sus hijos
        down(l, r, nodoActual);
        int mid = (l + r) / 2;
        // si el nodo que se quiere consultar esta en el rango izquierdo se llama recursivamente a query con el nodo izquierdo
        if (x <= mid) return query(x, l, mid, nodoActual * 2);
        // si el nodo que se quiere consultar esta en el rango derecho se llama recursivamente a query con el nodo derecho
        return query(x, mid + 1, r, nodoActual * 2 + 1);
    }


    private void nodeUpdater(ArrayList<int[]> vec){
        for (int[] pair : vec) {
            // se calcula el hilo inicial y el hilo siguiente
            int hiloInicial = pair[1], hiloSiguiente = pair[1] % n + 1;
            // se calcula el valor minimo entre el valor actual del nodo y el valor del nodo siguiente mas 1
            int lval = Math.min(query(hiloInicial, 1, n, 1), query(hiloSiguiente % n + 1, 1, n, 1) + 1);
            int rval = Math.min(query(hiloSiguiente, 1, n, 1), query((hiloInicial + n - 2) % n + 1, 1, n, 1) + 1);
            // se actualizan los valores de los nodos del segment tree con los valores calculados
            smodify(hiloInicial, 1, n, 1, rval);
            smodify(hiloSiguiente, 1, n, 1, lval);
            // se actualizan los valores de los nodos del segment tree con los valores calculados
            modify(hiloInicial, n, 1, n, 1, rval, 0);
            modify(1, hiloInicial, 1, n, 1, rval + n + 1 - hiloInicial, 0);
            modify(hiloSiguiente, n, 1, n, 1, lval, 0);
            modify(1, hiloSiguiente, 1, n, 1, lval + n + 1 - hiloSiguiente, 0);

            modify(1, hiloInicial, 1, n, 1, rval + hiloInicial - 1, 1);
            modify(hiloInicial, n, 1, n, 1, rval + n, 1);
            modify(1, hiloSiguiente, 1, n, 1, lval + hiloSiguiente - 1, 1);
            modify(hiloSiguiente, n, 1, n, 1, lval + n, 1);
        }

    }

    private void initialitesegmentree(){
        for (int i = 0; i < N * 4; i++) {
            seg[i] = new Node();
        }
    }

    private ArrayList<int[]> readBridges(int[][] bridges, int m){
        ArrayList<int[]> vec1 = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int[] pair = new int[2];
            pair[0] = bridges[i][0];
            pair[1] = bridges[i][1];
            vec1.add(pair);
        }
        return vec1;
    }


    private ArrayList<int[]> createBridges1(ArrayList<int[]> bridges, int sit) {
        ArrayList<int[]> vec1 = new ArrayList<>();
        ArrayList<int[]> newBidges = new ArrayList<>();
        int cantBridges = query(sit, 1, n, 1);
        for (int strand = n; strand >= 1; strand--) {
            for (int distance = 1; distance < spiderweb.getRadio(); distance++) {
                String color = canva.generateRandomColor();
                if (spiderweb.verifyBridge(color, distance, strand,false)) {
                    int[] pair = new int[]{distance, strand};
                    bridges.add(pair);
                    vec1 = new ArrayList<>(bridges);
                    vec1.sort((a, b) -> b[0] - a[0]);
                    build(1, n, 1);
                    nodeUpdater(vec1);

                    if (query(sit, 1, n, 1) >= cantBridges) {
                        // Eliminar el último puente añadido
                        bridges.remove(bridges.size() - 1);
                    } else {
                        cantBridges = query(sit, 1, n, 1);
                        newBidges.add(pair);
                        colors.add(color);
                        spiderweb.addBridge(color, pair[0], pair[1]);
                    }
                }
            }
        }
        return newBidges;
    }


   private ArrayList<int[]> createBridges(ArrayList<int[]> bridges, int sit) {
        ArrayList<int[]> vec1 = new ArrayList<>();
        ArrayList<int[]> newBidges = new ArrayList<>();

        int cantBridges = query(sit, 1, n, 1);

        for (int strand = 1; strand <= n; strand++) {
            for (int distance = spiderweb.getRadio() ; distance > 1; distance--) {
                String color = canva.generateRandomColor();
                if (spiderweb.verifyBridge(color, distance, strand,false)) {
                    int[] pair = new int[]{distance, strand};
                    bridges.add(pair);
                    vec1 = new ArrayList<>(bridges);
                    vec1.sort((a, b) -> b[0] - a[0]);
                    build(1, n, 1);
                    nodeUpdater(vec1);

                    if (query(sit, 1, n, 1) >= cantBridges) {
                        // Eliminar el último puente añadido
                        bridges.remove(bridges.size() - 1);
                    } else {
                        cantBridges = query(sit, 1, n, 1);
                        newBidges.add(pair);
                        colors.add(color);
                        spiderweb.addBridge(color, pair[0], pair[1]);
                    }
                }
            }
        }
    return newBidges;
    }
    /**
     * La función main es la función principal del programa, en esta función se lee la entrada del problema y se llama a las funciones necesarias para resolver el problema
     */
    public void simulate(int strands, int favorite, int[][] bridges, int strand){
        ArrayList<int[]> vec = new ArrayList<>();

        // se leen los valores de n, m y s, siendo n el número de hilos, m el número de puentes y s el hilo favorito de la araña
        n = strands;
        int m = bridges.length;
        s = favorite;
        if (!simulatefake) {
            spiderweb = new SpiderWeb(strands, favorite, bridges);
        }
        // se crea la telaraña
        colors.clear();
        spiderweb.spiderSit(strand);
        initialitesegmentree();
        // se lee la entrada de los puentes y se guardan en un vector
        vec = readBridges(bridges, m);
        // se ordena el vector de puentes de mayor a menor
        vec.sort((a, b) -> b[0] - a[0]);
        // se construye el segment tree con los valores iniciales de los nodos hoja
        build(1, n, 1);
        nodeUpdater(vec);

        ArrayList<int[]> newbridges = createBridges(vec, strand);
        if(query(strand, 1, n, 1) != 0){
            for (Object color1 : colors) {
                spiderweb.delBridge((String) color1);
            }
            vec = readBridges(bridges, m);
            vec.sort((a, b) -> b[0] - a[0]);
            build(1, n, 1);
            nodeUpdater(vec);

            colors.clear();

            newbridges = createBridges1(vec, strand);

        }
        System.out.println(query(strand, 1, n, 1));

        makeVisilbe();

        spiderweb.spiderWalk(true);
        spiderweb.spiderWalk(false);

        for (Object color1 : colors) {
            spiderweb.delBridge((String) color1);
        }

    }

    public void simulate(int strands, int favorite, int[][] bridges){
        spiderweb = new SpiderWeb(strands,favorite,bridges);
        simulatefake = true;
        for (int i = 1; i <= strands; i++) {
            simulate(strands, favorite, bridges, i);
        }

    }


    public void makeVisilbe (){
        spiderweb.makeVisible();
    }
    public void makeInvisible (){
        spiderweb.makeInvisible();
    }
    public void finish (){
        spiderweb.finish();
    }
    public void walk(){
        spiderweb.spiderWalk(true);
    }
}

