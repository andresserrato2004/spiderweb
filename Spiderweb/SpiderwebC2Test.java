package Spiderweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.LinkedTransferQueue;

import static org.junit.jupiter.api.Assertions.*;


public class SpiderwebC2Test {
    private SpiderWeb spiderWeb;

    @BeforeEach
    public void before() {
        spiderWeb = new SpiderWeb(150, 6);
        spiderWeb.addBridge("red", 150, 4);
        spiderWeb.addSpot("green", 2);
    }
    //addStrand
    //ok
    @Test
    public void accordingGSShouldAddStrand() {
        spiderWeb.addStrand();
        assertTrue(spiderWeb.ok());
    }

    //enlarge
    //ok
    @Test
    public void accordingGSShouldEnlarge() {
        spiderWeb.enlarge(150);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldEnlarge() {
        spiderWeb.enlarge(-150);
        assertFalse(spiderWeb.ok());
    }

    //addBridge
    //ok
    @Test
    public void accordingGSShouldAddBridge() {
        spiderWeb.addBridge("blue",179, 4);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldAddBridge() {
        spiderWeb.addBridge("yellow", 85,4);
        spiderWeb.addBridge("yellow", 80,1);
        assertFalse(spiderWeb.ok());
    }

    //relocateBridge
    //ok
    @Test
    public void accordingGSShouldRelocateBridge() {
        spiderWeb.addBridge("red", 85,4);
        spiderWeb.relocateBridge("red", 230);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldRelocateBridge() {
        spiderWeb.addBridge("red", 85,4);
        spiderWeb.relocateBridge("red", -20);
        assertFalse(spiderWeb.ok());
    }


    //delBridge
    //ok
    @Test
    public void accordingGSShouldDelBridge() {
        spiderWeb.addBridge("red", 85,4);
        spiderWeb.delBridge("red");
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldDelBridge() {
        spiderWeb.addBridge("red", 85,4);
        spiderWeb.delBridge("purple");
        assertFalse(spiderWeb.ok());
    }

    //addSpot
    //ok
    @Test
    public void accordingGSShouldAddSpot() {
        spiderWeb.addSpot("blue", 4);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldAddSpot() {
        spiderWeb.addSpot("red", 4);
        spiderWeb.addSpot("red", 5);
        assertFalse(spiderWeb.ok());
    }

    //delSpot
    //ok
    @Test
    public void accordingGSShouldDelSpot() {
        spiderWeb.delSpot("green");
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldDelSpot() {
        spiderWeb.delSpot("balck");
        assertFalse(spiderWeb.ok());
    }

    //spiderSit
    //ok
    @Test
    public void accordingGSShouldSpiderSit() {
        spiderWeb.spiderSit(4);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotSpiderSit() {
        spiderWeb.spiderSit(-4);
        assertFalse(spiderWeb.ok());
    }

    //spiderWalk
    //ok
    public void accordingGSShouldSpiderWalk() {
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    public void accordingGSShouldNotSpiderWalk() {
        spiderWeb.spiderWalk(true);
        assertTrue(spiderWeb.ok());
    }

    //spiderLastPath

    //bridge-tupla de colores de ls bridges
    //ok
//    @Test
//    public void accordingGSShouldBridges() {
//        String[] result = {"red"};
//        System.out.println(spiderWeb.bridges());
//        assertArrayEquals(result, spiderWeb.bridges().toArray());
//    //noOk--ndeberia pasar porque no hay ningun puente blanco
//    }
    @Test
    public void accordingGSNotBridges() {
        String[] resultt = {"white"};
        assertNotEquals(spiderWeb.bridges(), resultt);
    }


    //bridge

    //spots-colores de los spots
    //ok
//    @Test
//    public void accordingGSShouldSpots() {
//        String[] result = {"green"};
//        spiderWeb.addSpot("green", 4);
//
//        assertArrayEquals(result, spiderWeb.spots().toArray());
//    }
//    //noOk
//    @Test
//    public void accordingGSNotSpots() {
//        String[] resultt = {"white"};
//        assertNotEquals(resultt, new ArrayList[]{spiderWeb.spots()});
//    }

    //spot-donde esta ubicado el spot
    //ok
    @Test
    public void accordingGSShouldSpot() {
        spiderWeb.addSpot("red", 4);
        spiderWeb.spot("red");
        int result = 4;
        assertEquals(result, spiderWeb.spot("red"));
    }
    //noOk
    @Test
    public void accordingGSNotSpot() {
        spiderWeb.spot("white");
        assertFalse(spiderWeb.ok());
    }
}
