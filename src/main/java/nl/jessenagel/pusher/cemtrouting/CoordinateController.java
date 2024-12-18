package nl.jessenagel.pusher.cemtrouting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoordinateController {
    Logger logger = LogManager.getLogger(CoordinateController.class);
    private final CEMTGraph graph;
    public CoordinateController(CEMTGraph graph) {
        this.graph = graph;
    }
    @GetMapping("/coordinate")
    @Transactional(timeout=30)
    public Coordinate coordinate(@RequestParam(value = "point", defaultValue = "Empty") String point) {
        logger.info("Request for coordinate {}", point);
        if (point.equals("Empty")) {
            return new Coordinate(0.0, 0.0);
        }
        //point should be formatted as "lat,lon"
        String[] from = point.split(",");
        if (from.length != 2) {
            return new Coordinate(0.0, 0.0);
        }
        double fromLat;
        double fromLon;
        try {
            fromLat = Double.parseDouble(from[0]);
            fromLon = Double.parseDouble(from[1]);
        } catch (NumberFormatException e) {
            logger.info("Error parsing coordinates");
            return new Coordinate(0.0, 0.0);
        }
        //Get the CEMTNode
        CEMTNode fromNode = graph.getNodeOrNearest(fromLat, fromLon);
        if (fromNode == null) {
            logger.info("Error getting node");
            return new Coordinate(0.0, 0.0);
        }
        return new Coordinate(fromNode.getLatitude(), fromNode.getLongitude());
    }
}
