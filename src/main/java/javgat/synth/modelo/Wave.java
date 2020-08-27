/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.modelo;

/**
 *
 * Onda virtual del sintetizador
 * 
 * @author Javier Gatón Herguedas (javgat)
 * 
 */
public class Wave{

    private double originalTime; //microseconds
    private double timeLeft, volume;
    private boolean up, changed, on;
    private Waveform wf;

    /**
     * Constructor con los hercios
     * @param hertz Hercios
     */
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

    /**
     * Constructor con el tiempo original (medio periodo) y estado inicial
     * @param oTime Tiempo original
     * @param up Estado inicial
     */
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

    /**
     * Modifica el tiempo original de la onda de un estado (medio periodo)
     * @param oT 
     */
    public void setOriginalTime(double oT){
        originalTime = oT;
    }

    /**
     * Devuelve el tiempo original
     * @return 
     */
    public double getOriginalTime(){
        return originalTime;
    }

    /**
     * Devuelve el tiempo que queda para cambiar de estado
     * @return 
     */
    public double getTimeLeft(){
        return timeLeft;
    }

    /**
     * Hace que pase una cantidad de tiempo a la onda
     * @param t tiempo pasado
     */
    public void subTime(double t){
        timeLeft = timeLeft - t;
        while(timeLeft<=0){
            changed = true;
            timeLeft += originalTime;
            up = !up;
        }
    }

    /**
     * Modifica el volumen individual de la onda
     * @param vol 
     */
    public void setVolume(double vol){
        volume = vol;
    }

    /**
     * Devuelve el volumen individual de la onda
     * @return 
     */
    public double getVolume(){
        return volume;
    }

    /**
     * Devuelve el estado de la onda
     * @return True si esta arriba
     */
    public boolean isUp(){
        return up;
    }

    /**
     * Devuelve si la onda esta encendida o apagada
     * @return True si encendida
     */
    public boolean isOn(){
        return on;
    }

    /**
     * Modifica el estado encendido/apagado
     * @param on true encendido
     */
    public void setOn(boolean on){
        this.on = on;
    }
    
    /**
     * Devuelve la forma de la onda asociada
     * @return 
     */
    public Waveform getWaveform(){
        return wf;
    }
    
    /**
     * Modifica la onda de la forma que está asociada
     * @param wf 
     */
    public void setWaveform(Waveform wf){
        this.wf = wf;
    }

    /**
     * Devuelve el valor de sonido actual de la onda
     * @return 
     */
    public double getVal(){
        return wf.getVal(getOriginalTime(), getTimeLeft(), isUp());
    }

    /**
     * Valor de sonido de una onda dados un valor inicial y cuanto tiempo paso
     * @param initVal Valor inicial entre 0 y 1
     * @param val Valor final
     * @param cont Cantidad de pasos que han transcurrido
     * @param steps Pasos totales entre inicial y final
     * @return Valor lineal en el punto, entre 0 y 1
     */
    public double calculateVal(double initVal, double val, int cont, int steps) {
        return wf.calculateVal(initVal, val, cont, steps);
    }
}