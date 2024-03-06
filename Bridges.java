import java.awt.geom.Line2D;


public class Bridges {
    private String color;
    private boolean isVisible;
    private final float x1;
    private final float x2;
    private final float y1;
    private final float y2;

    public Bridges(float x1, float y1, float x2, float y2) {
        color = "black";
        isVisible = false;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }

    public float getY2() {
        return y2;
    }

    public void changeColor(String newColor) {
        color = newColor;
    }

    public void makeVisible() {
        isVisible = true;
        draw();
    }

    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            Line2D Bridges = new Line2D.Float(x1, y1, x2, y2);
            canvas.draw(this, color, Bridges);
            canvas.wait(10);
        }
    }

    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    public String getColor() {
        return color;
    }

    public float calcularDistancia() {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}