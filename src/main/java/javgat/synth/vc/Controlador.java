/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.vc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javgat.synth.modelo.Sawtooth;
import javgat.synth.modelo.Sine;
import javgat.synth.modelo.Sinte;
import javgat.synth.modelo.Square;
import javgat.synth.modelo.Triangle;
import javgat.synth.modelo.Variables;
import javgat.synth.modelo.Wave;
import javgat.synth.modelo.Waveform;

/**
 *
 * Controlador de la interfaz de Overflow Synth
 * 
 * @author Javier Gatón Herguedas (javgat)
 * 
 */
public class Controlador {
    
    private final Vista view;
    private boolean on, spinnerChanged, sliderChanged;
    private ArrayList<Wave> waves;
    private Variables limite;
    private Thread hilo;
    
    public Controlador(Vista vista){
        view = vista;
        waves = new ArrayList<>();
        limite = new Variables(20000000.0);
        on = false;
        
        waves.add(new Wave(440));
        waves.add(new Wave(440));
        waves.add(new Wave(440));
        waves.add(new Wave(440));
        
        spinnerChanged = false;
        sliderChanged = false;
        
    }
    
    /**
     * Cuando se modifique algún slider de la vista se llamará a este método
     * Modifica los valores asociados a estos sliders
     */
    public void sliderChanged(){
        if(!spinnerChanged){
            sliderChanged = true;
            ArrayList<Double> times, vols;
            times = view.getOriginalTimesSlider();
            vols = view.getVolumes();
            double limitVal, waitD, vol, factD;
            DecimalFormat df = new DecimalFormat("0.000");
            String value;

            limitVal = view.getLimitSlider();
            waitD = view.getWaitDelaySlider();
            view.setWaitDSpinner(waitD);

            factD = view.getFactDelaySlider();
            view.setFactDSpinner(factD);
            vol = view.getVolume();

            limite.setLimite(limitVal);
            view.setLimitSpinner(limitVal);
            limite.setWaitErrorDelay(waitD);
            limite.setFactorDelay(factD);
            limite.setVolume(vol);
            for(int i = 0; i < waves.size(); i++){
                waves.get(i).setOriginalTime(times.get(i));
                waves.get(i).setVolume(vols.get(i));

                view.changeTimeNumber(times.get(i), i);
            }
        }else{
            spinnerChanged = false;
        }
    }
    
    /**
     * Apaga el sintetizador
     */
    private void setOff(){
        on = false;
        limite.setContinua(false);
        view.luzOff();
    }
    
    /**
     * Enciende el sintetizador, hace que suene y genera un nuevo objeto Sinte
     */
    public void setOn(){
        if(!on){
            on = true;
            view.luzOn();
            limite.setContinua(true);
            
            int sampleRate = view.getSampleRate();
            //100000;
            AudioFormat af = new AudioFormat( (float )sampleRate, 8, 1, true, false );

            int bufferSize = view.getBufferSize();
            //4400;

            hilo = new Thread(() -> {
                Sinte.synth(af, sampleRate, waves, limite, bufferSize);
            });
            hilo.start();
            
            sliderChanged();
            switchButton();
        }else{
            setOff();
        }
    }

    /**
     * Cuando hay alguna pulsación de botón de onda se activa y las controla
     */
    public void switchButton() {
        ArrayList<Boolean> enc = view.getOndasOn();
        for(int i = 0; i < waves.size(); i++){
            waves.get(i).setOn(enc.get(i));
            view.setOnWave(i, enc.get(i));
        }
    }

    /**
     * Cuando se modifica algún combobox (los dropdown) actualiza los valores
     */
    void comboChanged() {
        ArrayList<String> wfs = view.getWaveforms();
        for(int i = 0; i < waves.size(); i++){
            Waveform wf;
            switch(wfs.get(i)){
                case "Square":
                    wf = new Square();
                    break;
                case "Triangle":
                    wf = new Triangle();
                    break;
                case "Sawtooth":
                    wf = new Sawtooth();
                    break;
                case "Sine":
                    wf = new Sine();
                    break;
                default:
                    wf = new Square();
            }
            waves.get(i).setWaveform(wf);
        }
    }

    void spinnerChanged() {
        if(!sliderChanged){
            spinnerChanged = true;
            ArrayList<Double> times, vols;
            times = view.getOriginalTimesSpinner();
            double limitVal = view.getLimitSpinner();
            limite.setLimite(limitVal);
            view.setLimitSlider(limitVal);
            
            double waitD, factD;

            waitD = view.getWaitDelaySpinner();
            
            view.setWaitDSlider(waitD);

            factD = view.getFactDelaySpinner();
            
            view.setFactDSlider(factD);

            limite.setWaitErrorDelay(waitD);
            limite.setFactorDelay(factD);
            for(int i = 0; i < waves.size(); i++){
                waves.get(i).setOriginalTime(times.get(i));

                view.changeTimeSlider(times.get(i), i);
            }
            
        }else{
            sliderChanged = false;
        }
    }
    
}
