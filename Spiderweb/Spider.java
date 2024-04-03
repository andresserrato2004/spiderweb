package Spiderweb;
import shapes.*;

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
    boolean isSitting;
    boolean isLive = true;
    
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
        body.moveVertical(yPosition - body.getDiameter());
        body.moveHorizontal(xPosition - body.getDiameter());
        body.changeSize(20);
        body.changeColor("black");
        organizeHead();
        organizeFeet();
    }

    
    /*
     * Organiza la cabeza de la araña.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    private void organizeHead() {
        this.head = new Circle();
        head.changeSize(6);
        head.moveVertical(body.getyPosition() - (body.getDiameter() / 3) - 15);
        head.moveHorizontal(body.getxPosition() - (body.getDiameter()/2 + 3));
        head.changeColor("red");
    }
    
    /*
     * Organiza las patas de la araña.
     * Este método es utilizado internamente y no está destinado a ser llamado directamente.
     */
    private void organizeFeet() {
        feet.clear();
        int centerX = (int) (body.getxPosition() + body.getDiameter() / 2 - 1);
        int centerY = (int) (body.getyPosition() + body.getDiameter() / 2 - 1);
        for (int i = 1; i <= 8; i++) {
            int endX = centerX + (i <= 4 ? 20 : -20);
            int endY = centerY + (i <= 4 ? (3 - i) * 10 : (i - 6) * 10);
            feet.add(new Line(centerX, centerY, endX, endY));
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
    public float getXPosition() {
        return body.getxPosition() + (body.getDiameter() / 2);
    }

    /**
     * Devuelve la posición y actual de la araña.
     * 
     * @return La posición y actual de la araña.
     */
    public float getYPosition() {
        return body.getyPosition() + (body.getDiameter() / 2);
    }
    
    /**
     * Método que mueve la araña a las coordenadas especificadas.
     * 
     * @param xdistance La nueva coordenada x de la posición de la araña.
     * @param ydistance La nueva coordenada y de la posición de la araña.
     */
   public void moveTo(float xdistance, float ydistance) {
       float dx = xdistance - getXPosition();
       float dy = ydistance - getYPosition();
       body.moveHorizontal(dx);
       body.moveVertical(dy);
       boolean wasVisible = body.getisVisible();
       makeInvisible();
       organizeFeet();
       organizeHead();
       if (wasVisible){
           makeVisible();
       }
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
   }

   public boolean isSpiderSitting(){
       return this.isSitting;
   }

   public void spiderSit(){
       head.changeColor("yellow");
       this.isSitting = true;
   }
}

        