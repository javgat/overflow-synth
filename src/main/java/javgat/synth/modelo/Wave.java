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
public class Wave{

    private double originalTime; //microseconds
    private double timeLeft, volume;
    private boolean up, changed, on;
    private Waveform wf;

    public Wave(double hertz){
        originalTime = 1000000/(2*hertz);
        timeLeft = originalTime;
        changed = false;
        //originalTime > 0
        up = true;
        volume = 1;
        on = true;
        wf = new Square();
    }

    public Wave(double oTime, boolean up){
        originalTime = oTime;
        timeLeft = originalTime;
        changed = false;
        //originalTime > 0
        this.up = up;
        volume = 1;
        on = true;
        wf = new Square();
    }

    public void setOriginalTime(double oT){
        originalTime = oT;
    }

    public double getOriginalTime(){
        return originalTime;
    }

    public double getTimeLeft(){
        return timeLeft;
    }

    public void subTime(double t){
        timeLeft = timeLeft - t;
        while(timeLeft<=0){
            changed = true;
            timeLeft += originalTime;
            up = !up;
        }
    }

    public void setVolume(double vol){
        volume = vol;
    }

    public double getVolume(){
        return volume;
    }

    public boolean isUp(){
        return up;
    }

    public boolean hasChanged(){
        if(changed){
            changed = false;
            return true;
        }else
            return false;
    }

    public boolean isOn(){
        return on;
    }

    public void setOn(boolean on){
        this.on = on;
    }
    
    public Waveform getWaveform(){
        return wf;
    }
    
    public void setWaveform(Waveform wf){
        this.wf = wf;
    }

    public double getVal(){
        return wf.getVal(getOriginalTime(), getTimeLeft(), isUp());
    }

    public double calculateVal(double initVal, double val, int cont, int steps) {
        return wf.calculateVal(initVal, val, cont, steps);
    }
}