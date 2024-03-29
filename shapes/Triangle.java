package shapes;

import java.awt.*;

public class Triangle extends Canvas {

    public static int VERTICES=3;

    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    public Triangle(){
        super( "Triangle", 200, 200, Color.white);
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = false;
    }

    public void makeVisible(){
        isVisible = true;
        draw();
    }

    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    public void moveRight(){
        moveHorizontal(20);
    }

    public void moveLeft(){
        moveHorizontal(-20);
    }

    public void moveUp(){
        moveVertical(-20);
    }

    public void moveDown(){
        moveVertical(20);
    }

    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

    private void draw(){
        if(isVisible) {
            int[] xpoints = { xPosition, xPosition + (width/2), xPosition - (width/2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            draw(this, color, new Polygon(xpoints, ypoints, 3));
            wait(10);
        }
    }

    private void erase(){
        if(isVisible) {
            erase(this);
        }
    }
}