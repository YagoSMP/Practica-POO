/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yagos
 */
public class TranslatorManager {
    private Translator currentTranslator;
    private Map<String, Translator> translatorMap;
    
    public TranslatorManager() {
        this.translatorMap = new HashMap<>();
        loadTranslators();
        loadDefaultTranslator();
    }
    
    private void loadTranslators() {
        // Creamos la referencia a la ruta de la carpeta
        File folder = new File("./dictionaries");
        // Obtenemos un array con el contenido de la carpeta
        File[] listOfFiles = folder.listFiles();
        
        // Añadir traducciones al map
        if(listOfFiles != null) {
            for(File file: listOfFiles) {
                // Solo nos fijamos en los archivos .csv
                if(file.isFile() && file.getName().endsWith(".csv")) {
                    Translator translator = new Translator(file.getPath());
                    translatorMap.put(translator.toString(), translator);
                }
            }
        }
        else {
            System.err.println("No se encuentra la carpeta dictionaries");
        }
    }
    
    private void loadDefaultTranslator() {
        String defaultLang = "Espanol";
        File configFile = new File("./config/default_language.txt");
        
        // Leer el idioma por defecto
        if(configFile.exists()) {
            try(BufferedReader br = new BufferedReader(new FileReader(configFile))) {
                String line = br.readLine();
                if(line != null && !line.trim().isEmpty()) {
                    String raw = line.trim();
                    // Formatear
                    defaultLang = raw.substring(0, 1).toUpperCase() + raw.substring(1).toLowerCase();
                }
            }
            catch(IOException e) {
                System.err.println("Error al leer el fichero de idioma por defecto: " + e.getMessage());
            }
        }
        
        // Si esta en el map el idioma por defecto lo ponemos
        if(translatorMap.containsKey(defaultLang)) {
            currentTranslator = translatorMap.get(defaultLang);
        } 
        // Si no esta el español pero el map tiene contenido, seleccionar el primero disponible
        else if(!translatorMap.isEmpty()) {
            currentTranslator = translatorMap.values().iterator().next();
        }
    }

    public Translator getCurrentTranslator() {
        return currentTranslator;
    }

    public void setCurrentTranslator(Translator translator) {
        this.currentTranslator = translator;
    }

    public Translator[] getTranslatorArray() {
        // Como toArray devuelve Object[], le pasamos un array vacio de muestra para indicar el tipo
        return translatorMap.values().toArray(new Translator[0]);
    }
}
