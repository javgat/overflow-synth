/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.modelo;

/**
 *
 * @author Javier
 */
public class Variables {
    
    private double limite, errorDelay, vol;
    private boolean continua;
    
    public Variables(double limite, double errorDelay){
        this.limite = limite;
        this.errorDelay = errorDelay;
        this.vol = 30;
        continua = true;
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

}
