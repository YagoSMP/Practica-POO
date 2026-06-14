/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neartrainnetworkdemo;

import java.math.BigDecimal;

/**
 *
 * @author yagos
 */
public class OperationContext {
    private TrainStation origin;
    private TrainStation destination;
    private BigDecimal price;
    private TrainNetwork trainNetwork;
    private Translator translator;
    
    // CONSTRUCTOR
    public OperationContext(TrainNetwork trainNetwork) {
        this.trainNetwork = trainNetwork;
        this.price = BigDecimal.ZERO;
    }

    // GETTER Y SETTER
    public TrainStation getOrigin() {
        return origin;
    }

    public void setOrigin(TrainStation origin) {
        this.origin = origin;
        calculatePrice();
    }

    public TrainStation getDestination() {
        return destination;
    }

    public void setDestination(TrainStation destination) {
        this.destination = destination;
        calculatePrice();
    }

    public TrainNetwork getTrainNetwork() {
        return trainNetwork;
    }

    public Translator getTranslator() {
        return translator;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    private void calculatePrice() {
        if(origin != null && destination != null) {
            this.price = trainNetwork.getPrice(origin, destination);
        }
    }
    
    public String getDescription() {
        String o = (origin != null) ? origin.getName(): "No seleccionado";
        String d = (destination != null) ? destination.getName(): "No seleccionado";
        return "Origen: " + o + "\nDestino: " + d + "\nPrecio: " + price + " euros";
    }
}
