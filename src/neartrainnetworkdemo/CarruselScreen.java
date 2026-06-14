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
public abstract class CarruselScreen extends Screen {
    protected int index; // Controla en que pagina estamos
    protected final int PAGE_SIZE = 15; // De la A a la O (15)
    
    public CarruselScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
        this.index = 0;
    }
    
    protected void configureNavigationButtons() {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setOption('S', "<<");
        kiosk.setOption('U', ">>");
        // Boton de Cancelar
        kiosk.setOption('T', "Cancelar");
    }
    
    protected void configureSelectionButtons(Object[] items) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        
        // Limpiamos los botones de la A a la P por si la pagina anterior tenia mas elementos
        for(char c = 'A'; c <= 'O'; c++) {
            kiosk.setOption(c, null);
        }
        
        // Ponemos los elementos correspondientes en cada boton
        for(int i = 0; i < PAGE_SIZE; i++) {
            int currentItemIndex = index + i;
            
            // Si aun quedan elementos en el array los ponemos en el boton
            if(currentItemIndex < items.length) {
                char buttonChar = (char) ('A' + i); // Convierte 0 en 'A', 1 en 'B'...
                kiosk.setOption(buttonChar, items[currentItemIndex].toString());
            }
        }
    }
    
    // Actualiza el indice de la pagina si pulsamos Siguiente U o Anterior R
    protected void updateIndex(char event, int totalItems) {
        if(event == 'U' && (index + PAGE_SIZE) < totalItems) {
            index += PAGE_SIZE; // Pasamos a la pagina siguiente
        } 
        else if(event == 'S' && index >= PAGE_SIZE) {
            index -= PAGE_SIZE; // Volvemos a la pagina anterior
        }
    }
}
