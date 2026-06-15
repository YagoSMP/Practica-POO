/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
                
                try {
                    // Comprobar si el servidor esta caido
                    if(!bank.comunicationAvaiable()) {
                        return new CommunicationErrorScreen(kiosk, bank);
                    }
                    
                    // Intento de cobro
                    boolean pagoAceptado = bank.doOperation(cardNumber, amountInCents);
                    
                    // Pago aceptado
                    if(pagoAceptado) {
                        // Datos del ticket
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        
                        String ticketContent = "Fecha: " + dtf.format(now) + "\n" + 
                                "Origen: " + context.getOrigin().getName() + " (Zona " + context.getOrigin().getZone() + ")\n" + 
                                "Destino: " + context.getDestination().getName() + " (Zona " + context.getDestination().getZone() + ")\n" + 
                                "Precio: " + context.getPrice() + " euros";
        
                        // Imprimir ticket
                        List<String> ticketLines = Arrays.asList(ticketContent.split("\n"));
                        kiosk.print(ticketLines);
                        
                        // Hacemos el print del ticket y lo guardamos en el txt
                        writePaymentToLog(ticketContent);
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
                    return new CommunicationErrorScreen(kiosk, bank);
                }
            }
        }
    }
    
    private void writePaymentToLog(String ticketContent) {
        // Guardar en el archivo
        String logFileName = "ventas_" + LocalDate.now().toString() + ".txt";
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(logFileName, true))) {
            bw.write(ticketContent + "\n\n");
        }
        catch(IOException e) {
            System.err.println("Error al guardar el log del ticket: " + e.getMessage());
        }
    }
}
