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
    public void start() {
        // Cargamos los datos
        TranslatorManager translatorManager = new TranslatorManager();
        TrainNetwork trainNetwork = new TrainNetwork("./config/stations.csv", "./config/Obras.txt");
        
        // Creamos el kiosk y el context
        SelfOrderKiosk kiosk = new SelfOrderKiosk();
        OperationContext context = new OperationContext(trainNetwork);
        
        // Le pasamos al context el idioma por defecto
        context.setTranslator(translatorManager.getCurrentTranslator());
        
        // Bucle principal
        Screen currentScreen = new WelcomeScreen(kiosk, translatorManager);
        
        while(true) {
            // Si se devuelve null volvemos a pantalla de inicio
            if(currentScreen == null) {
                currentScreen = new WelcomeScreen(kiosk, translatorManager);
                // Nuevo context
                context = new OperationContext(trainNetwork);
                context.setTranslator(translatorManager.getCurrentTranslator());
            }
            
            // show() pausa la ejecucion hasta que el usuario hace algo,
            // luego nos devuelve la siguiente pantalla a la que hay que ir
            currentScreen = currentScreen.show(context);
        }
    }
}
