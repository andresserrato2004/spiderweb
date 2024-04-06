package test;

import Spiderweb.SpiderWeb;
import Spiderweb.SpiderWebContest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.Assert.*;


public class spiderwebatest{
    private SpiderWeb spiderWeb = new SpiderWeb(200,7);
    private final SpiderWebContest spiderWebcontest = new SpiderWebContest();

    @Test
    public void simulateSecondconstructor1(){
        int[][] array = {{20,1},{40,3},{60,3},{80,7},{100,5}};
        spiderWebcontest.simulate(7, 6, array);
        System.out.println("¿Pasó la prueba? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        scanner.close();
        assertEquals("si", userResponse);
        spiderWebcontest.makeInvisible();
    }

    @Test
    public void solve1(){
        int[][] array = {{20,1},{40,3},{60,3},{80,7},{100,5}};
        int[] res = {2, 1, 1, 1, 0, 1 ,2};
        assertArrayEquals(res, spiderWebcontest.solve(7, 6, array));
    }

    @Test
    public void simulateSecondconstructor2(){
        int[][] array = {{10,1},{20,2},{30,3},{40,4}};
        spiderWebcontest.simulate(4, 4, array, 1);
        spiderWebcontest.makeVisilbe();
        System.out.println("¿Pasó la prueba? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        scanner.close();
        assertEquals("si", userResponse);
        spiderWebcontest.makeInvisible();
    }

    @Test
    public void solve2(){
        int[][] array = {{10,1},{20,2},{30,3},{40,4}};
        int[] res = {1, 0, 1, 1};
        assertArrayEquals(res, spiderWebcontest.solve(4, 4, array));
    }

    @Test
    public void bridges1(){
        spiderWeb.addBridge("transformer", "red",100, 1);
        spiderWeb.addBridge("weak", "blue", 120, 2);
        spiderWeb.addBridge("mobile", "green", 140, 3);
        spiderWeb.addSpot("bouncy", "yellow", 5);
        spiderWeb.makeVisible();
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        spiderWeb.addBridge("black" , 160, 5);
        spiderWeb.spiderWalk(false);
        System.out.println("¿Pasó la prueba? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        scanner.close();
        assertEquals("si", userResponse);
        spiderWeb.makeInvisible();
    }

    @Test
    public void spotsbouncy1(){
        spiderWeb.addBridge("transformer", "red",100, 1);
        spiderWeb.addBridge("weak", "blue", 120, 2);
        spiderWeb.addBridge("mobile", "green", 140, 3);
        spiderWeb.addSpot("bouncy", "magenta", 5);
        spiderWeb.addSpot("bouncy", "blue", 6);
        spiderWeb.makeVisible();
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        spiderWeb.spiderWalk(false);
        System.out.println("¿Pasó la prueba? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        scanner.close();
        assertEquals("si", userResponse);
        spiderWeb.makeInvisible();
    }

    @Test
    public void bridgetransformer1(){
        spiderWeb.addBridge("transformer", "red",100, 1);
        spiderWeb.addBridge("weak", "blue", 120, 2);
        spiderWeb.addBridge("mobile", "green", 140, 3);
        spiderWeb.makeVisible();
        spiderWeb.spiderSit(3);
        spiderWeb.spiderWalk(true);
        spiderWeb.delBridge("red");
        spiderWeb.spiderWalk(false);
        System.out.println("¿Pasó la prueba? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        scanner.close();
        assertEquals("si", userResponse);
        spiderWeb.makeInvisible();
    }

    @Test
    public void spotkiller(){
        spiderWeb.addBridge("transformer", "red",100, 1);
        spiderWeb.addBridge("weak", "blue", 120, 2);
        spiderWeb.addBridge("mobile", "green", 140, 3);
        spiderWeb.addSpot("bouncy", "yellow", 5);
        spiderWeb.addSpot("killer", "red", 6);
        spiderWeb.makeVisible();
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        System.out.println("¿Pasó la prueba? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        scanner.close();
        assertEquals("si", userResponse);
        spiderWeb.makeInvisible();
    }

    @Test
    public void spotkiller2(){
        spiderWeb.addBridge("transformer", "red",100, 1);
        spiderWeb.addBridge("weak", "blue", 120, 2);
        spiderWeb.addBridge("mobile", "green", 140, 3);
        spiderWeb.addSpot("bouncy", "yellow", 5);
        spiderWeb.addSpot("killer", "red", 6);
        spiderWeb.makeVisible();
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        spiderWeb.spiderSit(1);
        System.out.println("¿Pasó la prueba? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine();
        scanner.close();
        assertEquals("si", userResponse);
        spiderWeb.makeInvisible();
    }
}
