/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package neartrainnetworkdemo;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 *
 * @author jvelez
 */
public class NearTrainNetworkDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UrjcBankServer bank = new UrjcBankServer();
        SelfOrderKiosk kiosk = new SelfOrderKiosk();
        kiosk.setTitle("Venta de tickets de Cercanías");
        kiosk.setDescription("Vaya texto largo que estoy poniendo aquí\nAdemás, puedo poner retornos de carro.\n\nY más cosas.");
        kiosk.setOption('A', "Pulsame para dibujar una imagen");
        kiosk.setOption('B', "Pulsame para cambiar a modo de muchos botones");
        kiosk.setOption('C', "Lee tarjeta");
        kiosk.setOption('E', null);
        kiosk.setOption('F', "Pulsame para volver al modo de pocos botones");
        while (true) {
            char option = kiosk.waitEvent(30); 
            
            System.out.println("Se ha detectado el evento: [" + option + "]");
            
            /*OPCIONES*/
            switch (option) {
                case 'A' -> kiosk.setImage("./config/TrainNetwork.png");
                case 'B' -> kiosk.setMode(1);
                case 'F' -> kiosk.setMode(0);
                case '1' -> kiosk.setImage(null);
                case 'C' -> kiosk.setDescription(Long.toString(kiosk.getCardNumber()));
                case 'U' -> kiosk.setTitle("Has pulsado el último botón");
            }
        }
    }
    
}
