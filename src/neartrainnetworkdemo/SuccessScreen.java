/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        
        // Hacemos el print del ticket y lo guardamos en el txt
        writePaymentToLog(context);
        
        // Bucle infinito hasta que saque la tarjeta
        while(true) {
            char event = kiosk.waitEvent(30);
            // Tarjeta extraida
            if(event == '2') {
                return null;
            }
        }
    }
    
    private void writePaymentToLog(OperationContext context) {
        System.out.println("\n---TICKET GENERADO---");
        System.out.println(context.getDescription());
        System.out.println("---------------------\n");
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("tickets_log.txt", true))) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            
            bw.write("Fecha: " + dtf.format(now) + "\n");
            bw.write(context.getDescription() + "\n\n");
        }
        catch(IOException e) {
            System.err.println("Error al guardar el log del ticket: " + e.getMessage());
        }
    }
}
