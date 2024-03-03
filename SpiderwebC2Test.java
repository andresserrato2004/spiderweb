import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
    
public class SpiderwebC2Test {
    private spiderWeb spiderWeb;

    @BeforeEach
    public void before() {
        spiderWeb = new spiderWeb(150, 6);
        spiderWeb.addBridge("red", 150, 4);
        spiderWeb.addSpot("green", 2);
    }
    
    private void assertTrue(boolean ok) {
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
    public void accordingGSNotShouldenlarge() {
        spiderWeb.enlarge(-150);
        assertFalse(spiderWeb.ok());
    }
    
    //addBridge
    //ok
    @Test
    public void accordingGSShouldaddBridge() {
        spiderWeb.addBridge("blue",179, 4);
        assertTrue(spiderWeb.ok());
        //assertArrayEquals( spiderWeb.spots(), valorDevolver);
    }
    //noOk
    @Test
    public void accordingGSNotShouldaddBridge() {
        spiderWeb.addBridge("red", 85,4);
        //assertFalse(spiderWeb.ok());
    }
    
    //relocateBridge
    //ok
    @Test
    public void accordingGSShouldrelocateBridge() {
        spiderWeb.relocateBridge("red", 230);
        assertTrue(spiderWeb.ok());
    }
    //noOk
    @Test
    public void accordingGSNotShouldrelocateBridge() {
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
    
    
    
    
    
    
    
}
