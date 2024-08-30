package nl.jessenagel.pusher.cemtrouting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity

public class CEMTNode {


    @Id@GeneratedValue
    private Long id;
    private double latitude;
    private double longitude;

    public CEMTNode(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CEMTNode() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString(){
        return "CEMTNode{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CEMTNode that = (CEMTNode) o;
        return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
