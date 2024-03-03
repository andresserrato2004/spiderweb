import java.util.*;
/**
 * La clase Spider representa una araña en un lienzo.
 * La araña está compuesta por un cuerpo, una cabeza y ocho patas.
 * La araña puede estar sentada o en movimiento.
 * 
 * Esta clase proporciona métodos para controlar la visibilidad de la araña,
 * moverla a una nueva posición, y cambiar su estado entre sentada y de pie.
 * 
 * Ejemplo de uso:
 * 
 * Spider arana = new Spider(100, 100);
 * arana.makeVisible();
 * arana.spiderSit(0); // La araña se sienta
 * arana.move(200, 200); // La araña se mueve a la posición (200, 200)
 * 
 * @author (Andres Serrato - Zayra Gutierrez) 
 * @version (18/02/2024)
 */
public class Spider {
    private boolean isVisible;
    private Circle body;
    private Circle head;
    private ArrayList<Line> feet;
    private int xPosition;
    private int yPosition;
    private boolean isSitting;
    private Rectangle rectangle;
    
    /**
     * Crea una nueva araña en la posición especificada por las coordenadas (xPosition, yPosition).
     * 
     * @param xPosition La coordenada x de la posición inicial de la araña.
     * @param yPosition La coordenada y de la posición inicial de la araña.
     */
    public Spider(int xPosition, int yPosition) {
        this.body = new Circle();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.feet = new ArrayList<Line>();  
        beginDraw();
    }
    
    /**
     * Verifica si la araña está sentada o no.
     * 
     * @return true si la araña está sentada, false si no lo está.
     */
    public boolean isSitting() {
        return isSitting;
    }
    
    /*
     * Dibuja los componentes de la araña en el lienzo.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    private void beginDraw() {
        body.moveVertical(xPosition - body.getDiameter());
        body.moveHorizontal(xPosition - body.getDiameter());
        body.changeColor("black");
        organizeHead();
        organizeFeet();
        organizeRectangle();
    }
    
    /*
     * Organiza el rectángulo que representa el cuerpo de la araña.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    private void organizeRectangle() {
        this.rectangle = new Rectangle();
        rectangle.changeSize(10, 20);
        rectangle.moveVertical(body.getyPosition() + 16);
        rectangle.moveHorizontal(body.getxPosition() - 64);
        rectangle.changeColor("green");
    }
    
    /*
     * Organiza la cabeza de la araña.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    private void organizeHead() {
        this.head = new Circle();
        head.changeSize(10);
        head.moveVertical(body.getyPosition() - (body.getDiameter() / 3) - 15);
        head.moveHorizontal(body.getxPosition() - body.getDiameter() / 3);
        head.changeColor("red");
    }
    
    /*
     * Organiza las patas de la araña.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    private void organizeFeet() {
        feet.clear();        
        int j = 30;
        for (int i = 1; i <=8; i ++){
            if (i <= 4){
                feet.add(new Line(body.getxPosition() + head.getDiameter() / 2, body.getyPosition() + head.getDiameter(), body.getxPosition() + 40, body.getyPosition() + j));
                if  (i != 3){
                    j -= 10;
                }else{
                    j -= 5;
                }
            }else if (i == 5) {
                j = 30;                 
                feet.add(new Line(body.getxPosition() + head.getDiameter(), body.getyPosition() + head.getDiameter(), body.getxPosition() - 15, body.getyPosition() + j));
            }else{
                if  (i != 7){
                    j -= 10;
                }else{
                    j -= 5;
                }
                feet.add(new Line(body.getxPosition() + head.getDiameter(), body.getyPosition() + head.getDiameter(), body.getxPosition() - 15, body.getyPosition() + j));
            }
        }
    }
    
    /**
     * Hace visible a la araña en el lienzo.
     */
    public void makeVisible() {
        body.makeVisible();
        head.makeVisible();
        for(Line feet: feet){
            feet.makeVisible();
        }
    }
    
    /**
     * Hace invisible a la araña en el lienzo.
     */
    public void makeInvisible() {
        body.makeInvisible();
        head.makeInvisible();
        for(Line feet: feet){
            feet.makeInvisible();
        }
    }
    
    /**
     * Devuelve la posición x actual de la araña.
     * 
     * @return La posición x actual de la araña.
     */
    public int getXPosition() {
        return body.getxPosition() + (body.getDiameter() / 2);
    }

    /**
     * Devuelve la posición y actual de la araña.
     * 
     * @return La posición y actual de la araña.
     */
    public int getYPosition() {
        return body.getyPosition() + (body.getDiameter() / 2);
    }
    
    /**
     * Método que mueve la araña a las coordenadas especificadas.
     * 
     * @param xdistance La nueva coordenada x de la posición de la araña.
     * @param ydistance La nueva coordenada y de la posición de la araña.
     */
   public void moveTo(int xdistance, int ydistance) {
        int dx = xdistance - getXPosition();
        int dy = ydistance - getYPosition();
        body.moveHorizontal(dx);
        body.moveVertical(dy);
        makeInvisible();
        organizeFeet();
        organizeHead();
        makeVisible();
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

        