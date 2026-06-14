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
public class LanguageSelectionScreen extends CarruselScreen {
    private TranslatorManager tm;
    
    public LanguageSelectionScreen(SelfOrderKiosk kiosk, TranslatorManager tm) {
        super(kiosk);
        this.tm = tm;
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(1); // 21 botones
        
        // Translator translator = context.getTranslator();
        kiosk.setTitle("Seleccione un idioma / Select a language");
        kiosk.setDescription("");
        
        Translator[] idiomas = tm.getTranslatorArray();
        
        while(true) {
            configureNavigationButtons();
            configureSelectionButtons(idiomas);
            
            char event = kiosk.waitEvent(30);
            
            // Si el event esta entre la A y la O es que ha elegido un idioma
            if(event >= 'A' && event <= 'O') {
                int selectedIndex = index + (event - 'A');
                
                // Asgurarnos que no ha pulsado un boton vacio
                if(selectedIndex < idiomas.length) {
                    Translator idiomaElegido = idiomas[selectedIndex];
                    
                    // Cambiamos el idioma global
                    tm.setCurrentTranslator(idiomaElegido);
                    context.setTranslator(idiomaElegido);
                    
                    // Volvemos a la pagina inicial
                    return new WelcomeScreen(kiosk, tm);
                }
            }
            // Si pulsa Anterior R o Siguiente U navegamos por la paginas
            else if(event == 'S' || event == 'U') {
                updateIndex(event, idiomas.length);
            }
            // Si pulsa Cancelar T
            else if(event == 'T') {
                return new WelcomeScreen(kiosk, tm);
            }
        }
    }
}
