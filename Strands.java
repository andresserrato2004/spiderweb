import java.awt.geom.Line2D;

public class Strands {
    private String color;
    private boolean isVisible;
    private final float x1;
    private final float x2;
    private final float y1;
    private final float y2;

/**
     * Constructor de la clase Line.
     * Crea una nueva línea con los puntos extremos especificados por las coordenadas (x1, y1) y (x2, y2).
     *
     * @param x1 La coordenada x del primer punto.
     * @param y1 La coordenada y del primer punto.
     * @param x2 La coordenada x del segundo punto.
     * @param y2 La coordenada y del segundo punto.
     */
    public Strands(float x1, float y1, float x2, float y2) {
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
     * Devuelve el color de la línea.
     *
     * @return El color de la línea.
     */
    public String getColor() {
        return color;
    }

    /**
     * Cambia el color de la línea.
     *
     * @param color El nuevo color de la línea.
     */
    public void setColor(String color) {
        this.color = color;
        draw();
    }

    /**
     * Hace visible esta línea en el lienzo. Si ya estaba visible, no hace nada.
     */
    public void makeVisible() {
        isVisible = true;
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
     * Dibuja la línea con los atributos actuales en el lienzo.
     */

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Line2D.Float(x1, y1, x2, y2));
            canvas.wait(10);
        }
    }

    /**
     * Borra la línea del lienzo.
     */
    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    public void changeColor(String newColor){
        color = newColor;
    }
}



