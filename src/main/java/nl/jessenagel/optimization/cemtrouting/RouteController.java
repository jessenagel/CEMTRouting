package nl.jessenagel.optimization.cemtrouting;

import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteController {
    Logger logger = LogManager.getLogger(RouteController.class);
    private final CEMTGraph graph;
    public RouteController(CEMTGraph graph) {
        this.graph = graph;
    }
    @GetMapping("/route")
    @Transactional(timeout=30)
    public Route route(@RequestParam(value = "pointFrom", defaultValue = "Empty") String pointFrom, @RequestParam(value = "pointTo", defaultValue = "Empty") String pointTo, @RequestParam(value = "CEMTClass", defaultValue = "Empty") String CEMTClass, @RequestParam(value = "nearestNodeMethod", defaultValue = "nearest") String nearestNodeMethod) {
        logger.info("Request for route from {} to {}", pointFrom, pointTo);
        if (pointFrom.equals("Empty") || pointTo.equals("Empty")) {
            return new Route(1, "Empty",new ArrayList<>(), -1.0);
        }
        //pointFrom and pointTo should be formatted as "lat,lon"
        String[] from = pointFrom.split(",");
        String[] to = pointTo.split(",");
        if (from.length != 2 || to.length != 2) {
            return new Route(1, "Error: 'pointFrom' and 'pointTo' should be formatted as 'lat,lon'.", new ArrayList<>(), -1.0);
        }
        double fromLat;
        double fromLon;
        double toLat;
        double toLon;
        CEMTKlasse klasse = CEMTKlasse.zero;
        if(!CEMTClass.equals("Empty")){
            klasse = CEMTParser.parse(CEMTClass);
            if(klasse==null){
                return new Route(1, "Error, CEMTClass not found",new ArrayList<>(), -1.0);
            }
        }

        try {
            fromLat = Double.parseDouble(from[0]);
            fromLon = Double.parseDouble(from[1]);
            toLat = Double.parseDouble(to[0]);
            toLon = Double.parseDouble(to[1]);
        } catch (NumberFormatException e) {
            return new Route(1, "Error: Invalid latitude or longitude format.", new ArrayList<>(), -1.0);
        }
        //Get the CEMTNodes
        CEMTNode fromNode = null;
        CEMTNode toNode = null;
        if(nearestNodeMethod.equals("nearest")) {
            fromNode = graph.getNodeOrNearest(fromLat, fromLon);
            toNode = graph.getNodeOrNearest(toLat, toLon);
        }else if (nearestNodeMethod.equals("class")){
            fromNode = graph.getNodeOrNearest(fromLat, fromLon, klasse);
            toNode = graph.getNodeOrNearest(toLat, toLon, klasse);
        }else{
            return new Route(1, "Error: Unknown nearestNodeMethod: " + nearestNodeMethod, new ArrayList<>(), -1.0);
        }
        if (fromNode == null || toNode == null) {
            return new Route(1, "Error: Unable to find nodes near the provided coordinates.", new ArrayList<>(), -1.0);
        }

        //Calculate the route by calling the CEMTGraph
        List<CEMTNode> route;
        try {
            route = graph.getRoute(fromNode, toNode, klasse);
        } catch (Exception e) {
            logger.error("Error calculating route: ", e);
            return new Route(1, "Error: An error occurred while calculating the route.", new ArrayList<>(), -1.0);
        }
        if (route.isEmpty()) {
            return new Route(1, "Error: Route not found. There may be no route possible with the requested CEMT Class.", new ArrayList<>(), -1.0);
        }

        List<List<Double>> coordinates = new ArrayList<>();
        for (CEMTNode node: route) {
            List<Double> temp = new ArrayList<>();
            temp.add(node.getLatitude());
            temp.add(node.getLongitude());
            coordinates.add(temp);
        }
        double distance = graph.calculateRouteLength(route);

        return new Route(1, "OK", coordinates, distance);

    }
}
