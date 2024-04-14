package Spiderweb;


import java.util.ArrayList;


public class Bridges extends Line{
    int hiloInicial, hiloFinal;
    float distance;

    public Bridges(float x1, float y1, float x2, float y2, int hiloInicial, int hiloFinal, float distance) {
        super(x1,y1,x2,y2);
        this.hiloInicial = hiloInicial;
        this.hiloFinal = hiloFinal;
        this.distance = distance;
    }


    public ArrayList<Integer> getMidPoint() {
        ArrayList<Integer> midPoint = new ArrayList<>();
        int midX = (int) ((x2 + x1) / 2);
        int midY = (int) ((y2 + y1) / 2);
        midPoint.add(midX);
        midPoint.add(midY);
        midPoint.add((int) ((midX + x2) / 2));
        midPoint.add((int) ((midY + y2) / 2));
        midPoint.add((int) ((midX + x1) / 2));
        midPoint.add((int) ((midY + y1) / 2));
        midPoint.add((int) ((((midX + x2) / 2))+ x2)/2);
        midPoint.add((int) ((((midY + y2) / 2))+ y2)/2);


        return midPoint;
    }

    public ArrayList<Float> returnPoint(int hilo){
        ArrayList<Float> points = new ArrayList<>();
        if (hilo == hiloFinal){
            points.add(x2);
            points.add(y2);
        }else{
            points.add(x1);
            points.add(y1);
        }
        return points;
    }
    
    public ArrayList<Float> returnPointAcomodados(int hilo){
        ArrayList<Float> points = new ArrayList<>();
        if (hilo == hiloFinal){
            points.add(x2);
            points.add(y2);
            points.add(x1);
            points.add(y1);
        }else{
            points.add(x1);
            points.add(y1);
            points.add(x2);
            points.add(y2);
        }
        return points;
    }

    public float getDistance(){
        return distance;
    }


}