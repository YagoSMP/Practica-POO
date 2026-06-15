/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 *
 * @author yagos
 */
public class CommunicationErrorScreen extends Screen {
    private UrjcBankServer bank;
    
    public CommunicationErrorScreen(SelfOrderKiosk kiosk, UrjcBankServer bank) {
        super(kiosk);
        this.bank = bank;
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
            char event = kiosk.waitEvent(5);
            // Tarjeta extraida
            if(event == '2') {
                // Volvemos al inicio
                return null;
            }
            
            if(bank.comunicationAvaiable()) {
                return null;
            }
        }
    }
}
