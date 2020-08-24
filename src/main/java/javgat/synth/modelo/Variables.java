/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.modelo;

/**
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
    
    public boolean getContinua(){
        return continua;
    }
    
    public void setContinua(boolean cont){
        continua = cont;
    }
    
    public double getLimite(){
        return limite;
    }
    
    public void setLimite(double limite){
        this.limite = limite;
    }

    public double getWaitErrorDelay() {
        return errorDelay;
    }
    
    public void setWaitErrorDelay(double errorDelay){
        this.errorDelay = errorDelay;
    }
    
    public double getVolume(){
        return vol;
    }
    
    public void setVolume(double volume){
        vol = volume;
    }
    
    public double getDuration(){
        return duration;
    }
    
    public void setDuration(double duration){
        this.duration=duration;
    }
    
    public double getFactorDelay(){
        return factorDelay;
    }
    
    public void setFactorDelay(double factorDelay){
        this.factorDelay = factorDelay;
    }

}
