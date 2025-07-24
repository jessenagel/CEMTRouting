package nl.jessenagel.optimization.cemtrouting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CEMTNode.class)
class CEMTNodeTest {

    @Test
    void contextLoads() {
    }

    @Test
    void newCoordinate(){
        CEMTNode CEMTNode = new CEMTNode(1.0, 1.0);
        assert CEMTNode.getLatitude() == 1.0;
        assert CEMTNode.getLongitude() == 1.0;
    }

    @Test
    void twoCoordinates(){
        CEMTNode CEMTNode1 = new CEMTNode(1.0, 1.0);
        CEMTNode CEMTNode2 = new CEMTNode(2.0, 2.0);
        assert CEMTNode1.equals(CEMTNode1);
        assert !CEMTNode1.equals(CEMTNode2);
    }

    @Test
    void twoCoordinatesEqual(){
        CEMTNode CEMTNode1 = new CEMTNode(1.0, 1.0);
        CEMTNode CEMTNode2 = new CEMTNode(1.0, 1.0);
        assert CEMTNode1.equals(CEMTNode2);
    }

}