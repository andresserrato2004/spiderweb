package Spiderweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Transformer extends Bridges {
    private String color;
    private int strand;
    private boolean isSpot;
    private boolean showmensage;

    public Transformer(float x1, float y1, float x2, float y2, int hiloInicial, int hiloFinal, float distance, String color, int strand) {
        super(x1, y1, x2, y2, hiloInicial, hiloFinal, distance);
        this.color = color;
        this.strand = strand;
        this.isSpot = false;
        this.showmensage = true;
    }

    public void addSpot(String color, int strand) {
        
        if (Objects.equals(this.color, color) && this.strand == strand) {
            isSpot = true;
        }
    }

    public int[] bridge(String color) {
        if (Objects.equals(this.color, color) && isSpot) {
            int[] strands = new int[2];
            if (strand == 1) {
                strands[0] = strand;
                strands[1] = strands.length - 1;
            } else {
                strands[0] = strand;
                strands[1] = strand - 2;
            }
            return strands;
        }
        return new int[0];
    }
}
