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
public class CommunicationErrorScreen extends Screen {
    public CommunicationErrorScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(0);
        
        Translator translator = context.getTranslator();
        kiosk.setTitle(translator.translate("Error de Conexión"));
        kiosk.setDescription(translator.translate("Retire tarjeta"));
        kiosk.setImage(null);
        
        // Bucle infinito hasta que saque la tarjeta
        while(true) {
            char event = kiosk.waitEvent(30);
            // Tarjeta extraida
            if(event == '2') {
                // Volvemos al inicio
                return null;
            }
        }
    }
}
