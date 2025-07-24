package nl.jessenagel.optimization.cemtrouting;

import jakarta.persistence.*;

@Entity

public class CEMTEdge {
    @Id
    @GeneratedValue
    private long id;
    private double length;
    private CEMTKlasse klasse;
    @ManyToOne
    private CEMTNode neighbour;


    public CEMTEdge() {

    }

    public CEMTEdge(CEMTNode neighbour, double length, CEMTKlasse klasse) {
        this.neighbour = neighbour;
        this.length = length;
        this.klasse = klasse;
    }

    public CEMTNode getNeighbour() {
        return neighbour;
    }

    public long getId() {
        return id;
    }

    public double getLength() {
        return length;
    }

    public CEMTKlasse getKlasse() {
        return klasse;
    }
}
