package nl.jessenagel.optimization.cemtrouting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteControllerTest {

    @Test
    void route() {
        CEMTSystem cemtSystem = new CEMTSystem();
        CEMTGraph graph = cemtSystem.cemtGraph();
        //Populate the graph with fake data
        CEMTNode a = new CEMTNode(0, 0);
        CEMTNode b = new CEMTNode(3, 1);
        CEMTNode c = new CEMTNode(5, 1);
        CEMTNode d = new CEMTNode(7, 1);
        CEMTNode e = new CEMTNode(8, 0);
        CEMTNode f = new CEMTNode(5, 3);
        CEMTNode g = new CEMTNode(6, 4);
        CEMTNode h = new CEMTNode(3, 3);
        CEMTNode i = new CEMTNode(0, 1);
        CEMTNode j = new CEMTNode(1, 3);
        CEMTNode k = new CEMTNode(2, 5);
        CEMTNode l = new CEMTNode(1, 4);
        CEMTNode m = new CEMTNode(2, 4);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addVertex(j);
        graph.addVertex(k);
        graph.addVertex(l);
        graph.addVertex(m);
        graph.addEdge(a, b, 4, CEMTKlasse.III);
        graph.addEdge(b, c, 2, CEMTKlasse.III);
        graph.addEdge(c, d, 2, CEMTKlasse.III);
        graph.addEdge(d, e, 2, CEMTKlasse.III);
        graph.addEdge(c, f, 2, CEMTKlasse.III);
        graph.addEdge(f, g, 2, CEMTKlasse.I);
        graph.addEdge(f, h, 2, CEMTKlasse.III);
        graph.addEdge(h, i, 5, CEMTKlasse.II);
        graph.addEdge(h, m, 2, CEMTKlasse.III);
        graph.addEdge(m, k, 1, CEMTKlasse.I);
        graph.addEdge(m, j, 2, CEMTKlasse.I);
        graph.addEdge(m, l, 2, CEMTKlasse.III);


        RouteController routeController = new RouteController(graph);
        //Simple route
        Route route = routeController.route("0,0", "3,1", "Empty", "nearest");
        assertNotNull(route);
        assertEquals("OK", route.status());
        assertFalse(route.path().isEmpty());
        assertEquals(4.0, route.distance());
        //Simple route with not exact coordinates
        Route route2 = routeController.route("0.1,0.1", "3,1", "Empty", "nearest");
        assertNotNull(route2);
        assertEquals("OK", route2.status());
        assertFalse(route2.path().isEmpty());
        assertEquals(4.0, route2.distance());
        //Route with CEMT class
        Route route3 = routeController.route("0,0", "3,1", "III", "nearest");
        assertNotNull(route3);
        assertEquals("OK", route3.status());
        assertFalse(route3.path().isEmpty());
        assertEquals(4.0, route3.distance());
        //Route with CEMT class that is not possible, because nearest is chosen as option
        Route route4 = routeController.route("6,4", "3,1", "III", "nearest");
        assertNotNull(route4);
        assertEquals("Error: Route not found. There may be no route possible with the requested CEMT Class.", route4.status());
        assertTrue(route4.path().isEmpty());
        assertEquals(-1.0, route4.distance());
        //Route with CEMT class that is possible, because class is chosen as option
        Route route5 = routeController.route("6,4", "3,1", "III", "class");
        assertNotNull(route5);
        assertEquals("OK", route5.status());
        assertFalse(route5.path().isEmpty());
        assertEquals(4.0, route5.distance());

    }
}