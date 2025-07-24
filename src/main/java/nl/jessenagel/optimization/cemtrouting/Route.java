package nl.jessenagel.optimization.cemtrouting;


import java.util.List;

public record Route(long id, String status, List<List<Double>> path, double distance) {
    }
