package nl.jessenagel.pusher.cemtrouting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StatsController {
    private final CEMTGraph graph;
    public StatsController(CEMTGraph graph) {
        this.graph = graph;
    }


    @GetMapping("/stats")
    public Stats stats(){
        List<String> nodes = new ArrayList<>();
        for(CEMTNode node : graph.getNodes()){
            nodes.add(node.toString());
        }
        return new Stats(String.valueOf(graph.numberOfNodes()), nodes);
    }
}
