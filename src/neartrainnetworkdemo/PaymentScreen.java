/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.math.BigDecimal;
import javax.naming.CommunicationException;
import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 *
 * @author yagos
 */
public class PaymentScreen extends Screen {
    private UrjcBankServer bank;
    
    public PaymentScreen (SelfOrderKiosk kiosk) {
        super(kiosk);
        this.bank = new UrjcBankServer();
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(0);
        
        Translator translator = context.getTranslator();
        kiosk.setTitle(translator.translate("El precio de la compra es") + ": " + context.getPrice() + " euros");
        kiosk.setDescription(context.getDescription() + "\n\n" + translator.translate("Introduzca tarjeta de crédito"));
        kiosk.setImage(null);
        
        kiosk.setOption('F', translator.translate("Cancelar"));
        
        while(true) {
            char event = kiosk.waitEvent(30);
            
            if(event == 'F') {
                return null;
            }
            // Se detecta la entrada de tarjeta
            else if(event == '1') {
                long cardNumber = kiosk.getCardNumber();
                
                int amountInCents = context.getPrice().multiply(new BigDecimal("100")).intValue();
                
                System.out.println(amountInCents); // Prueba de lo que devuelve amountInCents
                
                try {
                    // Comprobar si el servidor esta caido
                    if(!bank.comunicationAvaiable()) {
                        return new CommunicationErrorScreen(kiosk);
                    }
                    
                    // Intento de cobro
                    boolean pagoAceptado = bank.doOperation(cardNumber, amountInCents);
                    
                    // Pago aceptado
                    if(pagoAceptado) {
                        // Pantalla de exito
                        return new SuccessScreen(kiosk);
                    }
                    // Pago rechazado
                    else {
                        kiosk.setTitle(translator.translate("Pago rechazado"));
                        kiosk.setDescription(translator.translate("Retire tarjeta"));
                        
                        kiosk.setOption('F', null);
                        
                        while(kiosk.waitEvent(30) != '2') {
                        }
                        
                        return null;
                    }
                }
                catch(CommunicationException e) {
                    return new CommunicationErrorScreen(kiosk);
                }
            }
        }
    }
}
