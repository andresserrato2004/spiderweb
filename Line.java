import java.awt.geom.Line2D;

/**
 * La clase Line representa una línea en un plano bidimensional.
 * Las líneas se definen por dos puntos en coordenadas (x, y).
 * Las coordenadas de los puntos extremos de la línea pueden ser fraccionarias.
 * 
 * Esta clase proporciona métodos para acceder y manipular las coordenadas de los puntos,
 * así como para cambiar el color de la línea, hacerla visible o invisible, y dibujarla en un lienzo.
 * 
 * Para dibujar la línea en un lienzo, se utiliza la clase Canvas, que proporciona los métodos
 * necesarios para dibujar y borrar elementos gráficos en una ventana.
 * 
 * Ejemplo de uso:
 * 
 * Line linea = new Line(10.5f, 20.5f, 30.5f, 40.5f);
 * linea.changeColor("red");
 * linea.makeVisible();
 * 
 * @author (Andres Serrato - Zayra Gutierrez) 
 * @version (18/02/2024)
 */
public class Line {
    protected String color;
    protected boolean isVisible;
    protected float x1;
    protected float x2;
    protected float y1;
    protected float y2;
    
    /**
     * Constructor de la clase Line.
     * Crea una nueva línea con los puntos extremos especificados por las coordenadas (x1, y1) y (x2, y2).
     * 
     * @param x1 La coordenada x del primer punto.
     * @param y1 La coordenada y del primer punto.
     * @param x2 La coordenada x del segundo punto.
     * @param y2 La coordenada y del segundo punto.
     */
    public Line(float x1, float y1, float x2, float y2) {
        color = "black";
        isVisible = false;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    /**
     * Devuelve la coordenada x del primer punto de la línea.
     * 
     * @return La coordenada x del primer punto.
     */
    public float getX1() {
        return x1;
    }
    
    /**
     * Devuelve la coordenada x del segundo punto de la línea.
     * 
     * @return La coordenada x del segundo punto.
     */
    public float getX2() {
        return x2;
    }
    
    /**
     * Devuelve la coordenada y del primer punto de la línea.
     * 
     * @return La coordenada y del primer punto.
     */
    public float getY1() {
        return y1;
    }
    
    /**
     * Devuelve la coordenada y del segundo punto de la línea.
     * 
     * @return La coordenada y del segundo punto.
     */
    public float getY2() {
        return y2;
    }
    
    /**
     * Hace visible esta línea en el lienzo. Si ya estaba visible, no hace nada.
     */
    public void makeVisible() {
        isVisible = true;

        System.out.println("isBridges ");
        draw();
    }
    
    /**
     * Hace invisible esta línea en el lienzo. Si ya estaba invisible, no hace nada.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    /**
     * Cambia el color de esta línea.
     * 
     * @param newColor El nuevo color de la línea (por ejemplo, "red", "blue", "green").
     */
    public void changeColor(String newColor) {
        color = newColor;
    }
    
    /*
     * Dibuja la línea con las especificaciones actuales en la pantalla.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            Line2D line = new Line2D.Float(x1, y1, x2, y2);
            canvas.draw(this, color, line);
            canvas.wait(10);
        }
    }
    
    /*
     * Borra la línea de la pantalla.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    public String getColor(){
        return color;
    }
    
    @Override
    public String toString() {
        return "Line from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")";
    }
    
    public void slowMove(int xdistance, int ydistance) {
        int deltaX, deltaY;

        if (xdistance < 0) {
            deltaX = -1;
            xdistance = -xdistance;
         } else {
            deltaX = 1;
         }

         if (ydistance < 0) {
            deltaY = -1;
            ydistance = -ydistance;
         } else {
            deltaY = 1;
         }

         for (int i = 0; i < Math.max(xdistance, ydistance); i++) {
            if (i < xdistance) {
                 x1 += deltaX;
                 x2 += deltaX;
                 draw();
             }
        
             if (i < ydistance) {
                y1-= deltaY;
                y2-= deltaY;
                draw();
             }
         }
    }
    
    public float calcularDistancia() {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
}
