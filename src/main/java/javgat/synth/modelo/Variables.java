/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.modelo;

/**
 *
 * Variables diversas del sintetizador unidas en un solo objeto
 * 
 * @author Javier Gatón Herguedas (javgat)
 * 
 */
public class Variables {
    
    private double limite, errorDelay, vol, duration, factorDelay;
    private boolean continua;
    
    public Variables(double limite){
        this.limite = limite;
        errorDelay = 0;
        vol = 30;
        continua = true;
        duration=1;
        factorDelay = 1;
    }
    
    /**
     * Obtención de la variable que indica si continua sonando o se apaga
     * @return 
     */
    public boolean getContinua(){
        return continua;
    }
    
    /**
     * Modificación de la variable que indica si sale o se queda en el bucle
     * y si sale significa que apaga
     * @param cont 
     */
    public void setContinua(boolean cont){
        continua = cont;
    }
    
    /**
     * Obtención del limite de frecuencias
     * @return 
     */
    public double getLimite(){
        return limite;
    }
    
    /**
     * Modificación del limite
     * @param limite 
     */
    public void setLimite(double limite){
        this.limite = limite;
    }

    /**
     * Obtención del wait error delay, que deforma el sonido
     * @return 
     */
    public double getWaitErrorDelay() {
        return errorDelay;
    }
    
    /**
     * Modificación del wait error delay
     * @param errorDelay 
     */
    public void setWaitErrorDelay(double errorDelay){
        this.errorDelay = errorDelay;
    }
    
    /**
     * Obtención del volumen total
     * @return Volumen total
     */
    public double getVolume(){
        return vol;
    }
    
    /**
     * Modificación del volumen
     * @param volume 
     */
    public void setVolume(double volume){
        vol = volume;
    }
    
    /**
     * Obtención del factor delay, factor que multiplica frecuencia (tono y 
     * velocidad)
     * @return Factor delay
     */
    public double getFactorDelay(){
        return factorDelay;
    }
    
    /**
     * Modificación del factor delay
     * @param factorDelay 
     */
    public void setFactorDelay(double factorDelay){
        this.factorDelay = factorDelay;
    }

}
