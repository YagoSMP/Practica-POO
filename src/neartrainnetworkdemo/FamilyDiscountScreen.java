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
public class FamilyDiscountScreen extends Screen {
    public FamilyDiscountScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(0);
        
        Translator translator = context.getTranslator();
        kiosk.setTitle(translator.translate("¿Tiene familia numerosa?"));
        kiosk.setImage(null);
        
        kiosk.setOption('A', translator.translate("Sí"));
        kiosk.setOption('B', translator.translate("No"));
        kiosk.setOption('F', translator.translate("Cancelar"));
        
        while(true) {
            char event = kiosk.waitEvent(30);
            
            // Seleccionan Si
            if(event == 'A') {
                // Aplicamos el descuento
                context.hasFamilyDiscount();
                return new PaymentScreen(kiosk);
            }
            // Seleccionan No
            else if(event == 'B') {
                return new PaymentScreen(kiosk);
            }
            // Cancelar
            else if(event == 'F') {
                return null;
            }
        }
    }
}
