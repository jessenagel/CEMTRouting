package nl.jessenagel.optimization.cemtrouting;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {CEMTGraph.class, CEMTNode.class,  CEMTEdge.class})
public class CEMTKlasseTest {
    @Test
    public void testCEMTKlasse() {
        // Test the CEMTKlasse enum
        for (CEMTKlasse klasse : CEMTKlasse.values()) {
            System.out.println(klasse);
        }
        //Test compareTo method
        CEMTKlasse klasse1 = CEMTKlasse.RA;
        CEMTKlasse klasse2 = CEMTKlasse.RB;
        int result = klasse1.compareTo(klasse2);
        System.out.println("Comparison result: " + result);

    }
}
