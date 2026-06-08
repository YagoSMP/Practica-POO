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
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author yagos
 */
public class TariffCalculator {
    private HashMap<Integer, BigDecimal> tariffs;
    private List<String> zoneList;
    private int maxZones;
    
    // Recibe la lista de las zonas A, B1, B2...
    public TariffCalculator(List<String> zoneList) {
        this.zoneList = zoneList;
        this.tariffs = new HashMap<>();
        this.maxZones = 0;
        loadTariffs("./config/tariffs.csv");
    }
    
    private void loadTariffs(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            
            while((line = br.readLine()) != null) {
                if(isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                line = line.replace("\"", "");
                String[] parts = line.split(",");
                
                if(parts.length >= 2) {
                    int zonesCrossed = Integer.parseInt(parts[0].trim());
                    BigDecimal price = new BigDecimal(parts[1].trim());
                    
                    tariffs.put(zonesCrossed, price);
                    
                    if(zonesCrossed > maxZones) {
                        maxZones = zonesCrossed;
                    }
                }
            }
        }
        catch(IOException e) {
            System.err.println("Error al leer el archivo de las tarifas: " + e.getMessage());
        }
    }
    
    public BigDecimal calculatePrice(TrainStation origin, TrainStation destination) {
        if(origin == null || destination == null) {
            return BigDecimal.ZERO;
        }
        
        String zoneOrigin = origin.getZone();
        String zoneDestination = destination.getZone();
        
        // Buscamos en que posicion del array esta cada zona
        int idxOrigin = zoneList.indexOf(zoneOrigin);
        int idxDestination = zoneList.indexOf(zoneDestination);
        
        // Hay algun error con la zona, devuelve la tarifa maxima
        if(idxOrigin == -1 || idxDestination == -1) {
            return tariffs.get(maxZones);
        }
        
        int zonesCrossed;
        
        // Misma linea
        if(origin.getLine().equals(destination.getLine())) {
            zonesCrossed = Math.abs(idxOrigin - idxDestination);
        }
        // Distinta linea
        else {
            zonesCrossed = idxOrigin + idxDestination;
        }
        
        // Si se supera el maximo, maxima tarifa
        if(zonesCrossed > maxZones) {
            zonesCrossed = maxZones;
        }
        
        return tariffs.get(zonesCrossed);
    }
}
