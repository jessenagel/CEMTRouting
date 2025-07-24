package nl.jessenagel.optimization.cemtrouting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CEMTGraph.class, CEMTNode.class,  CEMTEdge.class})
public class CEMTGraphTest
{
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

    @Test
    void addVertex() {
        CEMTGraph graph = new CEMTGraph();
        CEMTNode a = new CEMTNode(1, 1);
        CEMTNode b = new CEMTNode(2, 2);
        CEMTNode c = new CEMTNode(3, 3);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        assertEquals(3, graph.numberOfNodes());
        assertTrue(graph.hasNode(a));
        assertTrue(graph.hasNode(b));
        assertTrue(graph.hasNode(c));
    }

    @Test
    void addEdge() {
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
        assertEquals(6, graph.numberOfEdges());
    }

    @Test
    void removeEdge() {
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
        assertEquals(6, graph.numberOfEdges());
        graph.removeEdge(a, b);
        assertEquals(4, graph.numberOfEdges());
    }

    @Test
    void removeNode() {
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
        graph.removeNode(a);
        assertEquals(2, graph.numberOfNodes());
        assertEquals(2, graph.numberOfEdges());
    }

    @Test
    void hasNode() {
        CEMTGraph graph = new CEMTGraph();
        CEMTNode a = new CEMTNode(1, 1);
        CEMTNode b = new CEMTNode(2, 2);
        CEMTNode c = new CEMTNode(3, 3);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        assertTrue(graph.hasNode(a));
        assertTrue(graph.hasNode(b));
        assertTrue(graph.hasNode(c));
        assertFalse(graph.hasNode(new CEMTNode(4, 4)));
    }

    @Test
    void hasEdge() {
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
        assertTrue(graph.hasEdge(a, b));
        assertTrue(graph.hasEdge(b, c));
        assertTrue(graph.hasEdge(c, a));
        assertFalse(graph.hasEdge(a, new CEMTNode(4, 4)));
    }

    @Test
    void isDirected() {
        CEMTGraph graph = new CEMTGraph();
        assertFalse(graph.isDirected());
        graph.setDirected(true);
        assertTrue(graph.isDirected());
        graph.setDirected(false);
        assertFalse(graph.isDirected());
    }

    @Test
    void setDirected() {
        CEMTGraph graph = new CEMTGraph();
        graph.setDirected(true);
        assertTrue(graph.isDirected());
        graph.setDirected(false);
        assertFalse(graph.isDirected());
    }

    @Test
    void numberOfNodes() {
        CEMTGraph graph = new CEMTGraph();
        CEMTNode a = new CEMTNode(1, 1);
        CEMTNode b = new CEMTNode(2, 2);
        CEMTNode c = new CEMTNode(3, 3);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        assertEquals(3, graph.numberOfNodes());
        graph.removeNode(a);
        assertEquals(2, graph.numberOfNodes());
    }

    @Test
    void numberOfEdges() {
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
        assertEquals(6, graph.numberOfEdges());
        graph.removeEdge(a, b);
        assertEquals(4, graph.numberOfEdges());
    }

    @Test
    void getRoute() {
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
        assertEquals(3, graph.getRoute(a, c).size());
    }

    @Test
    void getDistanceMatrix() {
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
        List<CEMTNode> nodes = List.of(a, b, c);
        assertEquals(3, graph.getDistanceMatrix(nodes, CEMTKlasse.III).size());

    }

    @Test
    void getNodes() {
        CEMTGraph graph = new CEMTGraph();
        CEMTNode a = new CEMTNode(1, 1);
        CEMTNode b = new CEMTNode(2, 2);
        CEMTNode c = new CEMTNode(3, 3);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        assertEquals(3, graph.getNodes().size());
        assertTrue(graph.getNodes().contains(a));
        assertTrue(graph.getNodes().contains(b));
        assertTrue(graph.getNodes().contains(c));
    }

    @Test
    void getNode() {
        CEMTGraph graph = new CEMTGraph();
        CEMTNode a = new CEMTNode(1, 1);
        CEMTNode b = new CEMTNode(2, 2);
        CEMTNode c = new CEMTNode(3, 3);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        assertEquals(a, graph.getNode(1, 1));
        assertEquals(a, graph.getNode(1.0, 1.0));
        assertEquals(b, graph.getNode(2, 2));
        assertEquals(c, graph.getNode(3, 3));
        assertNull(graph.getNode(1.1, 1));
    }

    @Test
    void calculateRouteLength() {
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
        List<CEMTNode> routeOne = graph.getRoute(a,c);
        List<CEMTNode> routeTwo = graph.getRoute(a,b);
        List<CEMTNode> routeThree = graph.getRoute(b,c);

        assertEquals(3, graph.calculateRouteLength(routeOne));
        assertEquals(1, graph.calculateRouteLength(routeTwo));
        assertEquals(2, graph.calculateRouteLength(routeThree));
        graph.removeEdge(a, b);
        assertEquals(-1.0, graph.calculateRouteLength(routeOne));
        routeOne = graph.getRoute(a,c);
        assertEquals(4.0, graph.calculateRouteLength(routeOne));
        routeTwo = graph.getRoute(a,b);
        assertEquals(6, graph.calculateRouteLength(routeTwo));
        routeThree = graph.getRoute(b,c);

        assertEquals(2, graph.calculateRouteLength(routeThree));

    }

    @Test
    void getNodeOrNearestWithoutClass() {
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

        assertEquals(a, graph.getNodeOrNearest(1.1, 1.1));
        assertEquals(b, graph.getNodeOrNearest(2.1, 2.1));
        assertEquals(c, graph.getNodeOrNearest(3.1, 3.1));
    }

    @Test
    void getNodeOrNearestWithClass() {
        CEMTGraph graph = new CEMTGraph();
        CEMTNode a = new CEMTNode(1, 0);
        CEMTNode b = new CEMTNode(0, 0);
        CEMTNode c = new CEMTNode(0.5, 2);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addEdge(a, b, 1, CEMTKlasse.II);
        graph.addEdge(b, c, Math.sqrt(17)/2, CEMTKlasse.III);
        graph.addEdge(a, c, Math.sqrt(17)/2, CEMTKlasse.I);
        assertEquals(a, graph.getNodeOrNearest(1.0, 0, CEMTKlasse.I));
        assertEquals(a, graph.getNodeOrNearest(1.0, 0, CEMTKlasse.II));
        assertEquals(b, graph.getNodeOrNearest(1.1, 0,CEMTKlasse.III));
        assertEquals(c, graph.getNodeOrNearest(0.6, 2.1,CEMTKlasse.III));
        assertEquals(b, graph.getNodeOrNearest(1.0, 0, CEMTKlasse.III));

    }
}
