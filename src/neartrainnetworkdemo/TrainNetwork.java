/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author yagos
 */
public class TrainNetwork {
    private TreeSet<TrainStation> stationSet;
    private HashMap<String, TrainStation> stationMap;
    private TariffCalculator calculator;
    private HashSet<String> stationsInWorks;
    
    public TrainNetwork(String stationsFileName, String stationsInWork) {
        this.stationSet = new TreeSet<>();
        this.stationMap = new HashMap<>();
        this.stationsInWorks = new HashSet<>();
        loadStationGraph(stationsFileName);
        loadStationsInWork(stationsInWork);
        
        // Creamos la lista de zonas ordenada
        List<String> zones = Arrays.asList("A", "B1", "B2", "B3", "C1", "C2", "E1", "Zona Verde");
        this.calculator = new TariffCalculator(zones);
    }
    
    private void loadStationGraph(String fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true; // Para saltarnos la cabecera de los archivos
            
            while((line = br.readLine()) != null) {
                
                // Si es la primera linea o la cabecera la saltamos
                if(isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Eliminamos las "" antes de separar
                line = line.replace("\"", "");
                String[] parts = line.split(",");
                
                if(parts.length >= 3) {
                    // Linea, Estacion, Zona
                    String trainLine = parts[0].trim();
                    String name = parts[1].trim();
                    String zone = parts[2].trim();
                    
                    TrainStation station = new TrainStation(name, zone, trainLine);
                    
                    stationSet.add(station);
                    stationMap.put(name, station);
                }
            }
        }
        catch(IOException e) {
            System.err.println("Error al leer el archivo de las estaciones: " + e.getMessage());
        }
    }
    
    private void loadStationsInWork(String fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                if(!line.trim().isEmpty()) {
                    stationsInWorks.add(line.trim());
                }
            }
        }
        catch(IOException e) {
            System.err.println("Error al cargar el archivo de estaciones en obras: " + e.getMessage());
        }
    }
    
    public boolean isInWork(TrainStation station) {
        if(stationsInWorks.contains(station.getName())) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public TrainStation[] getStationArray() {
        return stationSet.toArray(new TrainStation[0]);
    }
    
    public TrainStation getStation(String stationName) {
        return stationMap.get(stationName);
    }
    
    public BigDecimal getPrice(TrainStation origin, TrainStation destination) {
        return calculator.calculatePrice(origin, destination);
    }
}
