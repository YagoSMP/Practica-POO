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
public class SuccessScreen extends Screen {
    public SuccessScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(0);
        
        Translator translator = context.getTranslator();
        kiosk.setTitle(translator.translate("Pago aceptado"));
        kiosk.setDescription(translator.translate("Retire tarjeta y billete"));
        kiosk.setImage(null);
        
        char event = kiosk.waitEvent(10);
        
        // Si retira la tarjeta volvemos a inicio
        if(event == '2') {
            return null;
        }
        
        // Si pasan 10s volvemos a inicio
        return null;
    }
}
