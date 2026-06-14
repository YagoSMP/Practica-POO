/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.io.File;
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
        // Si esta en el map el español ponemos el español
        if(translatorMap.containsKey("ES")) {
            currentTranslator = translatorMap.get("ES");
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
