package Spiderweb;

import java.util.ArrayList;

public class Spot extends Line {

    /**
     * Constructor de la clase Line.
     * Crea una nueva línea con los puntos extremos especificados por las coordenadas (x1, y1) y (x2, y2).
     *
     * @param x1 La coordenada x del primer punto.
     * @param y1 La coordenada y del primer punto.
     * @param x2 La coordenada x del segundo punto.
     * @param y2 La coordenada y del segundo punto.
     */
    public Spot(float x1, float y1, float x2, float y2) {
        super(x1, y1, x2, y2);
    }


    public ArrayList<Integer> getMidPointSpot() {
        ArrayList<Integer> midPoint = new ArrayList<>();
        int midX = (int) ((x2 + x1) / 2);
        int midY = (int) ((y2 + y1) / 2);
        midPoint.add(midX);
        midPoint.add(midY);
        midPoint.add((int) ((midX + x2) / 2));
        midPoint.add((int) ((midY + y2) / 2));
        midPoint.add((int) ((midX + x1) / 2));
        midPoint.add((int) ((midY + y1) / 2));
        midPoint.add((int) ((((midX + x2) / 2)) + x2) / 2);
        midPoint.add((int) ((((midY + y2) / 2)) + y2) / 2);

        return midPoint;
    }
}

