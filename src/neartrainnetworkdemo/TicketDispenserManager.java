/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import sienens.SelfOrderKiosk;

/**
 *
 * @author yagos
 */
public class TicketDispenserManager {
    private TranslatorManager translatorManager;
    
    public void start() {
        System.out.println("Iniciando el sistema SAVBC...");
        
        // Cargamos los datos
        translatorManager = new TranslatorManager();
        TrainNetwork trainNetwork = new TrainNetwork("./config/stations.csv");
        
        // Creamos el kiosk y el contexto
        SelfOrderKiosk kiosk = new SelfOrderKiosk();
        OperationContext context = new OperationContext(trainNetwork);
        
        // Le pasamos al contexto el idioma por defecto
        context.setTranslator(translatorManager.getCurrentTranslator());
        
        // Bucle principal
        Screen currentScreen = new WelcomeScreen(kiosk, translatorManager);
        
        while(true) {
            if(currentScreen == null) {
                currentScreen = new WelcomeScreen(kiosk, translatorManager);
                // Vaciamos el contexto con cada iteracion
                context.setOrigin(null);
                context.setDestination(null);
            }
            
            // show() pausa la ejecucion hasta que el usuario hace algo,
            // luego nos devuelve la siguiente pantalla a la que hay que ir
            currentScreen = currentScreen.show(context);
        }
    }
    
    // GETTER
    // Para que algunas pantallas puedan cambiar el idioma general
    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }
}
