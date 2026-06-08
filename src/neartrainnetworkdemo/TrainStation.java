/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.util.Objects;

/**
 *
 * @author yagos
 */
public class TrainStation implements Comparable<TrainStation>{
    private String name;
    private String zone;
    private String line;

    // CONSTRUCTOR
    public TrainStation(String name, String zone, String line) {
        this.name = name;
        this.zone = zone;
        this.line = line;
    }

    // GETTER
    public String getName() {
        return name;
    }

    public String getZone() {
        return zone;
    }

    public String getLine() {
        return line;
    }

    // TOSTRING
    @Override
    public String toString() {
        return name;
    }

    // EQUALS Y HASHCODE
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrainStation other = (TrainStation) obj;
        return Objects.equals(this.name, other.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // COMPARE TO
    // Para ordenar alfabeticamente en el TreeSet
    @Override
    public int compareTo(TrainStation other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
