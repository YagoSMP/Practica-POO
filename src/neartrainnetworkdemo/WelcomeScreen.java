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
public class WelcomeScreen extends Screen {
    private TranslatorManager tm;
    
    public WelcomeScreen(SelfOrderKiosk kiosk, TranslatorManager tm) {
        super(kiosk);
        this.tm = tm;
    }
    
    @Override
    public Screen show(OperationContext context) {
        // Restablecemos los botones
        configureButtons();
        
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = context.getTranslator();
        
        // Modo 0: 6 botones y una imagen/ descripcion
        kiosk.setMode(0);
        
        // Configuracion de la parte visual
        kiosk.setTitle(translator.translate("Venta de tickets de Cercanías"));
        kiosk.setImage("./config/TrainNetwork.png");
        kiosk.setDescription("");
        
        // Configuracion de los botones
        kiosk.setOption('A', translator.translate("Seleccione la estación de origen"));
        kiosk.setOption('B', "Cambiar idioma / Change language");
        
        // Bucle de eventos
        while(true) {
            char event = kiosk.waitEvent(30);
            
            if(event == 'A') {
                System.out.println("Ir a la pagina de seleccion de estacion\n");
                return new StationSelectionScreen(kiosk, "Seleccione la estación de origen", true);
            }
            else if(event == 'B') {
                System.out.println("Ir a la pagina de cambio de idioma\n");
                return new LanguageSelectionScreen(kiosk, tm);
            }
        }
    }
}
