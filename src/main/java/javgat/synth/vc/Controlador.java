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
 * @author Javier Gatón Herguedas (javgat)
 * 
 */
public class Controlador {
    
    private final Vista view;
    private boolean on;
    private ArrayList<Wave> waves;
    private Variables limite;
    private Thread hilo;
    
    public Controlador(Vista vista){
        view = vista;
        waves = new ArrayList<>();
        limite = new Variables(20000000.0,0);
        on = false;
        
        waves.add(new Wave(440));
        waves.add(new Wave(440));
        waves.add(new Wave(440));
        waves.add(new Wave(440));
        
    }
    
    public void sliderChanged(){
        ArrayList<Double> times, vols;
        times = view.getOriginalTimes();
        vols = view.getVolumes();
        double limitVal, waitD, vol;
        limitVal = view.getLimit();
        waitD = view.getWaitDelay();
        vol = view.getVolume();
        
        limite.setLimite(limitVal);
        limite.setWaitErrorDelay(waitD);
        limite.setVolume(vol);
        DecimalFormat df = new DecimalFormat("0.000");
        String value;
        for(int i = 0; i < waves.size(); i++){
            waves.get(i).setOriginalTime(times.get(i));
            waves.get(i).setVolume(vols.get(i));
            value = df.format(times.get(i));
            view.setTimeLabel(value, i);
        }
    }
    
    private void setOff(){
        on = false;
        limite.setContinua(false);
        view.luzOff();
    }
    
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

    public void switchButton() {
        ArrayList<Boolean> enc = view.getOndasOn();
        for(int i = 0; i < waves.size(); i++){
            waves.get(i).setOn(enc.get(i));
            view.setOnWave(i, enc.get(i));
        }
    }

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
    
}
