/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.vc;

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
    private boolean on, spinnerChanged, sliderChanged, isFreq;
    private ArrayList<Wave> waves;
    private ArrayList<Boolean> inFreq;
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
        inFreq = new ArrayList<>();
        inFreq.add(false);
        inFreq.add(false);
        inFreq.add(false);
        inFreq.add(false);
        
        isFreq = false;
        
    }
    
    private void commonChanges(double limitVal, double waitD, double factD){
        limite.setLimite(limitVal);
        limite.setWaitErrorDelay(waitD);
        limite.setFactorDelay(factD);
        if(factD >= 1)
            view.setFDStep(0.1);
        else
            view.setFDStep(0.01);
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

            limitVal = view.getLimitSlider();
            waitD = view.getWaitDelaySlider();
            factD = view.getFactDelaySlider();
            vol = view.getVolume();
            
            commonChanges(limitVal, waitD, factD);
            limite.setVolume(vol);
            
            view.setLimitSpinner(limitVal);
            view.setWaitDSpinner(waitD);
            view.setFactDSpinner(factD);
            
            double spinnerNumber;
            for(int i = 0; i < waves.size(); i++){
                waves.get(i).setOriginalTime(times.get(i));
                waves.get(i).setVolume(vols.get(i));
                    
                if(inFreq.get(i))
                    spinnerNumber = demipToHz(times.get(i));
                else
                    spinnerNumber = times.get(i);
                view.changeTimeNumber(spinnerNumber, i);
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
    public void comboChanged() {
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

    public void spinnerChanged() {
        if(!sliderChanged){
            spinnerChanged = true;
            ArrayList<Double> times;
            times = view.getTimeValuesSpinner();
            double limitVal = view.getLimitSpinner();
            double waitD, factD;
            waitD = view.getWaitDelaySpinner();
            factD = view.getFactDelaySpinner();
            
            commonChanges(limitVal, waitD, factD);
            
            view.setLimitSlider(limitVal);
            view.setWaitDSlider(waitD);
            view.setFactDSlider(factD);
            
            double time;
            for(int i = 0; i < waves.size(); i++){
                if(inFreq.get(i)){
                    time = hzToDemip(times.get(i));
                }else
                    time = times.get(i);
                waves.get(i).setOriginalTime(time);

                view.changeTimeSlider(time, i);
            }
            
        }else{
            sliderChanged = false;
        }
    }
    
    public static double demipToHz(double demip){
        double period = 2*demip/1000000;
        return 1/period;
    }
    
    public static double hzToDemip(double hz){
        double period = 1/hz;
        return 1000000*period/2;
    }
    
    public void wavesToFreq(){
        view.changeLabelToFreq();
        double min, max;
        min = demipToHz(view.getWaveSpinnerMax());
        max = demipToHz(view.getWaveSpinnerMin());
        view.setWavesSpinnersData(0.1, min, max);
        for(int i = 0; i < waves.size(); i++){
            inFreq.set(i, true);
            double value = demipToHz(waves.get(i).getOriginalTime());
            view.setWaveSpinnerValue(value, i);
        }
    }
    
    public void wavesToPeriod(){
        view.changeLabelToDPeriod();
        double min, max;
        min = hzToDemip(view.getWaveSpinnerMax());
        max = hzToDemip(view.getWaveSpinnerMin());
        view.setWavesSpinnersData(1, min, max);
        for(int i = 0; i < waves.size(); i++){
            inFreq.set(i, false);
            double value = waves.get(i).getOriginalTime();
            view.setWaveSpinnerValue(value, i);
        }
    }
    
    public void freqPeriod(){
        if(isFreq){
            isFreq = false;
            wavesToPeriod();
        }else{
            isFreq = true;
            wavesToFreq();
        }
    }
    
}
