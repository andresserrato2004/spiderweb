import java.util.*;
/**
 * Write a description of class angulos here.
 * 
 * @author (Andres Serrato - Zayra Gutierrez) 
 * @version (17/02/20)
 */

/**
 * Una clase para representar un par de elementos de tipos arbitrarios.
 * @param <X> Tipo del primer elemento del par.
 * @param <Y> Tipo del segundo elemento del par.
 */
class Pair<X,Y>{
    public X first;
    public Y second;
    
    /**
     * Crea un nuevo par de elementos.
     * @param first El primer elemento del par.
     * @param second El segundo elemento del par.
     */
    public Pair (X first, Y second){
        this.first = first;
        this.second = second;
    }
    
    /**
     * Obtiene el primer elemento del par.
     * @return El primer elemento del par.
     */
    public X getFirst(){
        return first;
    }
    
    /**
     * Obtiene el segundo elemento del par.
     * @return El segundo elemento del par.
     */
    public Y getSecond(){
        return second;
    }
    
     public String toString() {
        return "(" + first.toString() + ", " + second.toString() + ")";
    }
    
}

    /**
     * Una clase para generar una lista de pares de coordenadas a partir de un radio y una cantidad.
     */
public class angles{
    private List<Pair<Float, Float>> list;
    private float cant;
    
    /**
     * Crea una nueva instancia de la clase angles.
     * @param radio El radio del círculo.
     * @param count La cantidad de pares de coordenadas a generar.
     */
    public angles(int radio , int count){
        this.list = new ArrayList<>();
        float totalAngle;
        float angle;
        angle = 0;
        totalAngle = 360;
        this.cant = totalAngle/count;
        while (angle < totalAngle){
            list.add(new Pair<>((float)Math.round((radio*Math.cos(Math.toRadians(angle)))),(float)Math.round((radio*Math.sin(Math.toRadians(angle))))));
            angle += cant;
        }
        //for (Pair pair : list){
        //    System.out.println(pair);
        //}
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
