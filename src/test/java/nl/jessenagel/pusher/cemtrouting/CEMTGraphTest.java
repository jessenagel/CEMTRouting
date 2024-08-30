package nl.jessenagel.pusher.cemtrouting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CEMTGraph.class, CEMTNode.class,  CEMTEdge.class})
public class CEMTGraphTest
{
    @Test
    void ContextLoads()
    {
    }
    @Test
    void buildGraph(){
        CEMTGraph graph = new CEMTGraph();
        CEMTNode a = new CEMTNode(1, 1);
        CEMTNode b = new CEMTNode(2, 2);
        CEMTNode c = new CEMTNode(3, 3);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addEdge(a, b, 1, CEMTKlasse.III);
        graph.addEdge(b, c, 2, CEMTKlasse.III);
        graph.addEdge(a, c, 4, CEMTKlasse.III);
        assertEquals(3, graph.numberOfNodes());
        assertEquals(6, graph.numberOfEdges());
        assertTrue(graph.hasEdge(a, b));
        assertTrue(graph.hasEdge(b, c));
        assertTrue(graph.hasEdge(c, a));
        assertTrue(graph.hasEdge(a, c));

        graph.removeEdge(a, b);
        graph.removeEdge(b, c);
        graph.removeEdge(c, a);
        graph.removeNode(a);
        graph.removeNode(b);
        graph.removeNode(c);
        assertEquals(0, graph.numberOfNodes());
        assertEquals(0, graph.numberOfEdges());
    }
}
