package nl.jessenagel.pusher.cemtrouting;


import net.sf.geographiclib.Geodesic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CEMTGraph {
    Logger logger = LogManager.getLogger(RouteController.class);

    private final Map<CEMTNode, List<CEMTEdge>> adj;
    private boolean directed = false;

    public CEMTGraph() {
        adj = new HashMap<>();
    }

    public void addVertex(CEMTNode CEMTNode) {
        adj.putIfAbsent(CEMTNode, new LinkedList<>());
    }

    public void addEdge(CEMTNode a, CEMTNode b, double distance, CEMTKlasse klasse) {
        adj.putIfAbsent(a, new LinkedList<>()); //add node
        adj.putIfAbsent(b, new LinkedList<>()); //add node
        CEMTEdge edge1 = new CEMTEdge(b, distance, klasse);
        adj.get(a).add(edge1); //add edge
        if (!directed) { //undirected
            CEMTEdge cemtEdge2 = new CEMTEdge(a, distance, klasse);
            adj.get(b).add(cemtEdge2);
        }
    }


    //Find the edge between two nodes, Time O(n) Space O(1), n is number of neighbors
    private CEMTEdge findEdgeByNodes(CEMTNode a, CEMTNode b) {
        if (!adj.containsKey(a) || !adj.containsKey(b)) //invalid input
            return null;
        List<CEMTEdge> ne = adj.get(a);
        for (CEMTEdge edge : ne) {
            if (edge.getNeighbour().equals(b)) {
                return edge;
            }
        }
        return null;
    }

    //Remove direct connection between a and b, Time O(n) Space O(1)
    public boolean removeEdge(CEMTNode a, CEMTNode b) {
        if (!adj.containsKey(a) || !adj.containsKey(b)) //invalid input
            return false;
        List<CEMTEdge> ne1 = adj.get(a);
        List<CEMTEdge> ne2 = adj.get(b);
        if (ne1 == null || ne2 == null)
            return false;
        CEMTEdge edge1 = findEdgeByNodes(a, b);
        if (edge1 == null)
            return false;
        ne1.remove(edge1);
        if (!directed) {//undirected
            CEMTEdge edge2 = findEdgeByNodes(b, a);
            if (edge2 == null)
                return false;
            ne2.remove(edge2);
        }
        return true;
    }

    //Remove a node including all its edges, Time O(V+E) Space O(1),
    //V is number of nodes, E is number of edges
    public boolean removeNode(CEMTNode a) {
        if (!adj.containsKey(a)) //invalid input
            return false;
        if (!directed) { //undirected
            List<CEMTEdge> ne1 = adj.get(a);
            for (CEMTEdge edge : ne1) {
                CEMTEdge edge1 = findEdgeByNodes(edge.getNeighbour(), a);
                adj.get(edge.getNeighbour()).remove(edge1);
            }
        } else { //directed
            for (CEMTNode key : adj.keySet()) {
                CEMTEdge edge2 = findEdgeByNodes(key, a);
                if (edge2 != null)
                    adj.get(key).remove(edge2);
            }
        }
        adj.remove(a);
        return true;
    }


    //Check whether there is node by its key, Time O(1) Space O(1)
    public boolean hasNode(CEMTNode key) {
        return adj.containsKey(key);
    }

    //Check whether there is direct connection between two nodes, Time O(n), Space O(1)
    public boolean hasEdge(CEMTNode a, CEMTNode b) {
        CEMTEdge edge1 = findEdgeByNodes(a, b);
        if (directed) {//directed
            return edge1 != null;
        } else { //undirected or bi-directed
            CEMTEdge edge2 = findEdgeByNodes(b, a);
            return edge1 != null && edge2 != null;
        }
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public int numberOfNodes() {
        return adj.size();
    }

    public int numberOfEdges() {
        int count = 0;
        for (CEMTNode key : adj.keySet()) {
            count += adj.get(key).size();
        }
        return count;
    }

    @Override
    public String toString() {
        return "CEMTGraph{" +
                "adj=" + adj +
                ", directed=" + directed +
                '}';
    }

    public List<CEMTNode> getRoute(CEMTNode from, CEMTNode to, CEMTKlasse klasse) {
        return dijkstra(from, to, klasse);
    }

    public Map<CEMTNode, Map<CEMTNode, Double>> getDistanceMatrix(List<CEMTNode> nodes, CEMTKlasse klasse) {
        HashMap<CEMTNode, Map<CEMTNode, Double>> distanceMatrix = new HashMap<>();
        for (CEMTNode nodeFrom : nodes) {
            distanceMatrix.put(nodeFrom, new HashMap<>());
            for (CEMTNode nodeTo : nodes) {
                if (nodeFrom.equals(nodeTo)) {
                    distanceMatrix.get(nodeFrom).put(nodeTo, 0.0);
                } else {
                    List<CEMTNode> path = dijkstra(nodeFrom, nodeTo, klasse);
                    if (path.isEmpty()) {
                        distanceMatrix.get(nodeFrom).put(nodeTo, -1.0);
                    } else {
                        double length = calculateRouteLength(path);
                        distanceMatrix.get(nodeFrom).put(nodeTo, length);
                    }
                }
            }
        }
        return distanceMatrix;
    }

    private List<CEMTNode> dijkstra(CEMTNode from, CEMTNode to, CEMTKlasse minimumCEMTClass) {
        Map<CEMTNode, Double> distance = new HashMap<>();
        Map<CEMTNode, CEMTNode> previous = new HashMap<>();
        PriorityQueue<CEMTNode> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
        Set<CEMTNode> visited = new HashSet<>();
        distance.put(from, 0.0);
        queue.add(from);
        logger.debug("Dijkstra: from {} to {}", from, to);
        while (!queue.isEmpty()) {
            CEMTNode current = queue.poll();
            logger.debug("Dijkstra: visiting {}", current);
            if (current.equals(to)) {
                List<CEMTNode> path = new ArrayList<>();
                while (previous.containsKey(current)) {
                    path.add(current);
                    current = previous.get(current);
                }
                path.add(from);
                Collections.reverse(path);
                logger.debug("Dijkstra: found path {} with distance {}", path, distance.get(to));
                if (distance.get(current) >= 100000000) {
                    return new ArrayList<>();
                }
                return path;
            }
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            boolean allUnreachable = true;
            for (CEMTEdge edge : adj.get(current)) {
                CEMTNode neighbour = edge.getNeighbour();
                double newDist = distance.get(current) + edge.getLength();
                if (edge.getKlasse().compareTo(minimumCEMTClass) < 0) {
                    newDist = 1000000000;
                }
                if (newDist < 1000000000) {
                    allUnreachable = false;
                }
                if (newDist < distance.getOrDefault(neighbour, Double.MAX_VALUE)) {

                    distance.put(neighbour, newDist);
                    previous.put(neighbour, current);
                    queue.add(neighbour);
                }

            }
            if (allUnreachable) {
                logger.debug("All neighbours of {} are unreachable", current);
                return new ArrayList<>();
            }
        }
        logger.debug("Dijkstra: no path found from {} to {}", from, to);
        return new ArrayList<>();
    }

    public List<CEMTNode> getNodes() {
        return new ArrayList<>(adj.keySet());
    }

    public CEMTNode getNode(double latitude, double longitude) {
        for (CEMTNode node : adj.keySet()) {
            if (node.getLatitude() == latitude && node.getLongitude() == longitude) {
                return node;
            }
        }
        return null;
    }

    public double calculateRouteLength(List<CEMTNode> route) {
        double length = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            CEMTEdge edge = findEdgeByNodes(route.get(i), route.get(i + 1));
            if (edge == null) {
                return 0;
            }
            length += edge.getLength();
        }
        return length;
    }

    public CEMTNode getNodeOrNearest(double fromLat, double fromLon) {
        CEMTNode fromNode = getNode(fromLat, fromLon);
        if (fromNode == null) {
            double minDist = Double.MAX_VALUE;
            CEMTNode nearest = null;
            for (CEMTNode node : adj.keySet()) {
                double dist = Geodesic.WGS84.Inverse(node.getLatitude(), node.getLongitude(), fromLat, fromLon).s12;
                if (dist < minDist) {
                    minDist = dist;
                    nearest = node;
                }
            }
            return nearest;
        }
        return fromNode;
    }
}
