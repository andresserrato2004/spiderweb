package test;

import Spiderweb.SpiderWeb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpiderwebC4Test {
    private SpiderWeb spiderWeb;

    @BeforeEach
    public void before() {
        spiderWeb = new SpiderWeb(200, 6);
        spiderWeb.addBridge("red", 150, 4);
        spiderWeb.addSpot("green", 2);
    }

    //addStrand
    //ok
    @Test
    public void shouldAddStrandSuccessfully() {
        spiderWeb.addStrand();
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldEnlargeSuccessfully() {
        spiderWeb.enlarge(150);
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotEnlargeWithNegativeValue() {
        spiderWeb.enlarge(-150);
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldAddBridgeSuccessfully() {
        spiderWeb.addBridge("blue", 179, 4);
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotAddBridgeWithSameColor() {
        spiderWeb.addBridge("yellow", 85, 4);
        spiderWeb.addBridge("yellow", 80, 1);
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldRelocateBridgeSuccessfully() {
        spiderWeb.addBridge("red", 85, 4);
        spiderWeb.relocateBridge("red", 199);
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotRelocateBridgeWithNegativeValue() {
        spiderWeb.addBridge("red", 85, 4);
        spiderWeb.relocateBridge("red", -20);
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldDeleteBridgeSuccessfully() {
        spiderWeb.addBridge("red", 85, 4);
        spiderWeb.delBridge("red");
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotDeleteNonexistentBridge() {
        spiderWeb.addBridge("red", 85, 4);
        spiderWeb.delBridge("purple");
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldAddSpotSuccessfully() {
        spiderWeb.addSpot("blue", 4);
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotAddSpotWithSameColor() {
        spiderWeb.addSpot("red", 4);
        spiderWeb.addSpot("red", 5);
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldDeleteSpotSuccessfully() {
        spiderWeb.delSpot("green");
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotDeleteNonexistentSpot() {
        spiderWeb.delSpot("balck");
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldSitSpiderSuccessfully() {
        spiderWeb.spiderSit(4);
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotSitSpiderWithNegativeValue() {
        spiderWeb.spiderSit(-4);
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldWalkSpiderSuccessfully() {
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldNotWalkSpiderWithoutSitting() {
        spiderWeb.spiderWalk(true);
        assertFalse(spiderWeb.ok());
    }

    @Test
    public void shouldReturnCorrectBridges() {
        String[] result = {"red"};
        assertArrayEquals(result, spiderWeb.bridges());
    }

    @Test
    public void shouldNotReturnIncorrectBridges() {
        String[] resultt = {"white"};
        assertNotEquals(spiderWeb.bridges(), resultt);
    }

    @Test
    public void shouldReturnCorrectSpots() {
        String[] result = {"green"};
        spiderWeb.addSpot("green", 4);
        assertArrayEquals(result, spiderWeb.spots());
    }

    @Test
    public void shouldNotReturnIncorrectSpots() {
        String[] resultt = {"white"};
        assertNotEquals(resultt, spiderWeb.spots());
    }

    @Test
    public void shouldReturnCorrectSpotLocation() {
        spiderWeb.addSpot("red", 4);
        spiderWeb.spot("red");
        int result = 4;
        assertEquals(result, spiderWeb.spot("red"));
    }

    @Test
    public void shouldNotReturnNonexistentSpot() {
        spiderWeb.spot("white");
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede agregar un puente con una longitud negativa
    @Test
    public void shouldNotAddBridgeWithNegativeLength() {
        spiderWeb.addBridge("blue", -50, 4);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede agregar un puente en una posición de hebra negativa
    @Test
    public void shouldNotAddBridgeWithNegativeStrandPosition() {
        spiderWeb.addBridge("blue", 50, -4);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede agregar un puente en una posición de hebra mayor que el número de hebras
    @Test
    public void shouldNotAddBridgeWithStrandPositionGreaterThanStrandCount() {
        spiderWeb.addBridge("blue", 50, 10);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede reubicar un puente a una longitud negativa
    @Test
    public void shouldNotRelocateBridgeToNegativeLength() {
        spiderWeb.addBridge("red", 85, 4);
        spiderWeb.relocateBridge("red", -50);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede agregar un lugar con una posición de hebra negativa
    @Test
    public void shouldNotAddSpotWithNegativeStrandPosition() {
        spiderWeb.addSpot("blue", -4);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede agregar un lugar en una posición de hebra mayor que el número de hebras
    @Test
    public void shouldNotAddSpotWithStrandPositionGreaterThanStrandCount() {
        spiderWeb.addSpot("blue", 10);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede sentar la araña en una posición de hebra mayor que el número de hebras
    @Test
    public void shouldNotSitSpiderWithStrandPositionGreaterThanStrandCount() {
        spiderWeb.spiderSit(10);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que la araña no puede caminar si no está sentada
    @Test
    public void shouldNotWalkSpiderIfNotSitting() {
        spiderWeb.spiderWalk(true);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que la araña puede caminar si está sentada
    @Test
    public void shouldWalkSpiderIfSitting() {
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        assertTrue(spiderWeb.ok());
    }

    // Prueba para verificar que la araña puede caminar si no hay puentes
    @Test
    public void shouldWalkSpiderIfNoBridges() {
        spiderWeb = new SpiderWeb(150, 6); // Crear una nueva telaraña sin puentes
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        assertTrue(spiderWeb.ok());
    }


    // Prueba para verificar que no se puede reubicar un puente que no existe
    @Test
    public void shouldNotRelocateNonexistentBridge() {
        spiderWeb.relocateBridge("purple", 50);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede reubicar un puente a una posición de hebra negativa
    @Test
    public void shouldNotRelocateBridgeToNegativeStrandPosition() {
        spiderWeb.addBridge("red", 85, 4);
        spiderWeb.relocateBridge("red", -4);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede reubicar un puente a una posición de hebra mayor que el número de hebras
    @Test
    public void shouldNotRelocateBridgeToStrandPositionGreaterThanStrandCount() {
        spiderWeb.addBridge("red", 85, 4);
        spiderWeb.relocateBridge("red", 10);
        assertTrue(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede sentar la araña en una posición de hebra negativa
    @Test
    public void shouldNotSitSpiderWithNegativeStrandPosition() {
        spiderWeb.spiderSit(-4);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar que la araña puede caminar en la dirección opuesta si no hay puentes en esa dirección
    @Test
    public void shouldNotWalkSpiderInOppositeDirectionIfNoBridges() {
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(false);
        assertTrue(spiderWeb.ok());
    }

    // Prueba para verificar que la araña puede caminar en la dirección opuesta si hay puentes en esa dirección
    @Test
    public void shouldWalkSpiderInOppositeDirectionIfBridges() {
        spiderWeb.addBridge("blue", 150, 1);
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(false);
        assertTrue(spiderWeb.ok());
    }

    // Prueba para verificar que no se puede obtener la ubicación de un lugar que no existe
    @Test
    public void shouldNotReturnNonexistentSpotLocation() {
        spiderWeb.spot("purple");
        assertFalse(spiderWeb.ok());
    }


    // Prueba para verificar que el constructor de SpiderWeb está inicializando correctamente la instancia
    @Test
    public void shouldInitializeSpiderWebCorrectly() {
        SpiderWeb spiderWeb = new SpiderWeb(200, 6);
        assertNotNull(spiderWeb);
        assertTrue(spiderWeb.ok());
    }

    // Prueba para verificar que se manejan correctamente los valores límite al agregar un puente
    @Test
    public void shouldHandleBoundaryValuesWhenAddingBridge() {
        spiderWeb.addBridge("blue", 200, 6);
        assertTrue(spiderWeb.ok());
        spiderWeb.addBridge("green", 200, 7);
        assertFalse(spiderWeb.ok());
    }

    // Prueba para verificar el estado inicial de SpiderWeb
    @Test
    public void shouldHandleInitialState() {
        SpiderWeb spiderWeb = new SpiderWeb(200, 6);
        assertTrue(spiderWeb.ok());
    }

    // Prueba para verificar el orden de las operaciones
    @Test
    public void shouldHandleOperationOrder() {
        spiderWeb.delBridge("red");
        spiderWeb.addBridge("red", 85, 4);
        assertTrue(spiderWeb.ok());
    }

    // Prueba para verificar la interacción entre métodos
    @Test
    public void shouldHandleMethodInteraction() {
        spiderWeb.addSpot("blue", 4);
        spiderWeb.addBridge("blue", 50, 4);
        assertTrue(spiderWeb.ok());
    }

    @Test
    public void shouldAddAllOfBridges() {
        // Agregar todos los tipos de puentes
        spiderWeb.addBridge("red", 151, 1);
        spiderWeb.addBridge("blue", 100, 2);
        spiderWeb.addBridge("green", 50, 3);
        spiderWeb.addBridge("yellow", 75, 4);
        spiderWeb.addBridge("purple", 125, 5);

        // Verificar que todos los puentes se agregaron correctamente
        String[] expectedBridges = {"red", "blue", "green", "yellow", "purple"};
        assertArrayEquals(expectedBridges, spiderWeb.bridges());
    }

    @Test
    public void shouldAddAllTypesOfSpots() {
        // Agregar todos los tipos de lugares
        spiderWeb.addSpot("red", 1);
        spiderWeb.addSpot("blue", 3);
        spiderWeb.addSpot("green", 2);
        spiderWeb.addSpot("yellow", 4);
        spiderWeb.addSpot("purple", 5);

        // Verificar que todos los lugares se agregaron correctamente
        String[] expectedSpots = {"green", "red", "blue", "yellow", "purple"};
        assertArrayEquals(expectedSpots, spiderWeb.spots());
    }


    // estos son los tipos de puentes transformer fixed weak mobile y los tipos de spots bouncy  y killer
    @Test
    public void shouldAddAllTypesOfBridges() {
        // Agregar todos los tipos de puentes
        spiderWeb.addBridge("transformer", "green", 50, 1);
        spiderWeb.addBridge("fixed", "yellow", 75, 2);
        spiderWeb.addBridge("weak", "purple", 125, 3);
        spiderWeb.addBridge("mobile", "blue", 90, 2);
        assertTrue(spiderWeb.ok());

    }

    @Test
    public  void lastpath(){
        spiderWeb.addBridge("yellow", 75, 1);
        spiderWeb.addBridge("blue", 100, 2);
        spiderWeb.addBridge("green", 120, 3);
        spiderWeb.addBridge("red", 150, 4);
        spiderWeb.spiderSit(1);
        spiderWeb.spiderWalk(true);
        int[] result = {1, 2, 3, 4, 5};
        assertArrayEquals(result, spiderWeb.spiderLastPath());

    }

}