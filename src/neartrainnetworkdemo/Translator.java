/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yagos
 */
public class Translator {
    private Map<String, String> dictionary;
    private String languageName; // Para mostrar en el boton el nombre del idioma
    
    public Translator(String fileName) {
        this.dictionary = new HashMap<>();
        loadDictionary(fileName);
    }
    
    private void loadDictionary(String fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            
            while((line = br.readLine()) != null) {
                if(isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                line = line.replace("\"", "");
                String [] parts = line.split(",", 2); // Cortar como maximo 2 partes
                
                if(parts.length == 2) {
                    dictionary.put(parts[0].trim(), parts[1].trim());
                }
            }
            
            //Añadir el nombre correspondiente al idioma del archivo. English
            // Extraer el nombre del archivo sin la extension
            String normalPath = fileName.replace("\\", "/");
            String name = normalPath.substring(normalPath.lastIndexOf("/") + 1, normalPath.lastIndexOf("."));
            
            //Formatear para primera letra en mayuscula y el resto en minuscula
            languageName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
        catch(IOException e) {
            System.err.println("Error al cargar el diccionario: " + fileName);
        }
    }
    
    public String translate(String text) {
        return dictionary.getOrDefault(text, text);
    }

    @Override
    public String toString() {
        return languageName;
    }
}
