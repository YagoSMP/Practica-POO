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
public class StationSelectionScreen extends CarruselScreen {
    private String screenTitle;
    private boolean isOrigin;
    
    public StationSelectionScreen(SelfOrderKiosk kiosk, String screenTitle, boolean isOrigin) {
        super(kiosk);
        this.screenTitle = screenTitle;
        this.isOrigin = isOrigin;
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(1); // Modo de 21 botones
        
        Translator translator = context.getTranslator();
        kiosk.setTitle(translator.translate(screenTitle));
        kiosk.setDescription("");
        
        TrainStation[] estaciones = context.getTrainNetwork().getStationArray();
        
        while(true) {
            configureNavigationButtons();
            // Sobreescribimos T para traducir Cancelar
            kiosk.setOption('T', translator.translate("Cancelar"));
            configureSelectionButtons(estaciones);
            
            char event = kiosk.waitEvent(30);
            
            // Si el event esta entre la A y la O es que ha elegido una estacion
            if(event >= 'A' && event <= 'O') {
                int selectedIndex = index + (event - 'A');
                
                // Asegurarnos que no ha pulsado un boton vacio
                if(selectedIndex < estaciones.length) {
                    TrainStation estacionElegida = estaciones[selectedIndex];
                    
                    if(isOrigin) {
                        context.setOrigin(estacionElegida);
                        
                        return new StationSelectionScreen(kiosk, "Seleccione la estación de destino", false);
                    }
                    else {
                        context.setDestination(estacionElegida);
                        
                        // FIN DE COMPRA
                        return new FamilyDiscountScreen(kiosk);
                    }
                }
            }
            // Si pulsa Anterior R o Siguiente U navegamos por la paginas
            else if(event == 'S' || event == 'U') {
                updateIndex(event, estaciones.length);
            }
            // Si pulsa Cancelar T
            else if(event == 'T') {
                return null;
            }
        }
    }
}
