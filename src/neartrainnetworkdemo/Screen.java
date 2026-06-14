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
public abstract class Screen {
    // Todas las pantallas compartiran la misma referencia al kiosk
    private SelfOrderKiosk kiosk;
    
    // CONSTRUCTOR
    public Screen(SelfOrderKiosk kiosk) {
        this.kiosk = kiosk;
    }

    // GETTER
    public SelfOrderKiosk getSelfOrderKiosk() {
        return kiosk;
    }
    
    // Borrar todos los botones para que las clases hijas configuren solo los suyos con un kiosk limpio
    protected void configureButtons() {
        for(char c = 'A'; c <= 'U'; c++) {
            kiosk.setOption(c, null);
        }
    }
    
    // Metodo polimorfico
    // Recibe el contexto de la operacion y ejecuta la logica de la pantalla
    // Devuelve la siguiente pantalla
    public abstract Screen show(OperationContext context);
}
