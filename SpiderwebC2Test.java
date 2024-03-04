import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
    
public class SpiderwebC2Test {
    private SpiderWeb spiderWeb;

    @BeforeEach
    public void before() {
        spiderWeb = new SpiderWeb(150, 6);
        spiderWeb.addBridge("red", 150, 4);
        spiderWeb.addSpot("green", 2);
    }
    
    private void assertTrue(boolean ok) {
    }
    
    private void assertArrayEquals(ArrayList<String> bridges, String[] result) {
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
        //assertArrayEquals( spiderWeb.spots(), valorDevolver);
    }
    //noOk
    @Test
    public void accordingGSNotShouldAddBridge() {
        spiderWeb.addBridge("red", 85,4);
        assertFalse(spiderWeb.ok());
    }
    
    //relocateBridge
    //ok
    @Test
    public void accordingGSShouldRelocateBridge() {
        spiderWeb.relocateBridge("red", 230);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldRelocateBridge() {
        spiderWeb.relocateBridge("red", -20);
        assertFalse(spiderWeb.ok());
    }
    
    //delBridge
    //ok
    @Test
    public void accordingGSShouldDelBridge() {
        spiderWeb.delBridge("red");
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldDelBridge() {
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
    //spiderLastPath
    
    //bridge-tupla de colores de ls bridges
    //ok
    @Test
    public void accordingGSShouldBridges() {
        String[] result = {"red", "blue"};
        assertArrayEquals(spiderWeb.bridges(), result);
    }
    //noOk--ndeberia pasar porque no hay ningun puente blanco
    @Test
    public void accordingGSNotBridges() {
        String[] resultt = {"white"};
        assertArrayEquals(spiderWeb.bridges(), resultt);
    }
    //bridge
    
    //spots-colores de los spots
    //ok
    @Test
    public void accordingGSShouldSpots() {
        String[] result = {"green"};
        assertArrayEquals(spiderWeb.spots(), result);
    }
    //noOk
    @Test
    public void accordingGSNotSpots() {
        String[] resultt = {"white"};
        assertArrayEquals(spiderWeb.spots(), resultt);
    }
    
    //spot-donde esta ubicado el spot
    //ok
    @Test
    public void accordingGSShouldSpot() {
        spiderWeb.spot("red");
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotSpot() {
        spiderWeb.spot("white");
        assertFalse(spiderWeb.ok());
    }
}
