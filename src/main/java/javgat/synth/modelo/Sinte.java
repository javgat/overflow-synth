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
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

import java.util.ArrayList;

public class Sinte {

    public static double position(int i, double freq, int rate){
        double bitsWave = rate/freq;
        double posOnda = i % bitsWave;
        return posOnda/bitsWave;
    }

    public static void step(ArrayList<Wave> waves, double timeStep){
        for(int j = 0; j < waves.size(); j++){
            waves.get(j).subTime(timeStep);
        }
    }
    
    public static double calculateValue(ArrayList<Wave> waves){
        double value = 0;
        double fraction = 1.0/waves.size();
        for(int j = 0; j < waves.size(); j++){
            if(waves.get(j).isUp() && waves.get(j).isOn())
                value += fraction*waves.get(j).getVolume();
        }
        return value;
    }

    public static void wait(double microsecs, int sampleRate, double value, byte[] buf, SourceDataLine sdl, Variables limit){
        int steps = (int) (microsecs * (sampleRate/1000000.0));
        /*
        for(int i = 0; i < steps; i++){
            buf[0] = (byte)(value*30);
            sdl.write( buf, 0, 1 );
        }
        */
        
        byte valor = (byte)(value*limit.getVolume());
        int cont = 0;
        for(int i = 0; i < buf.length; i++){
            buf[i] = valor;
        }
        while(cont<steps-buf.length){
            sdl.write( buf, 0, buf.length );
            cont += buf.length;
        }
        sdl.write(buf, 0, steps-cont);
        
    }
    
    
    private static ArrayList<Double> calculateValues(ArrayList<Wave> waves) {
        ArrayList<Double> values = new ArrayList<>();
        for(int i = 0; i < waves.size(); i++){
            values.add(waves.get(i).getVal());
        }
        return values;
    }
    
    public static double calculatePoint(ArrayList<Double> initVals, ArrayList<Wave> waves, int cont, int steps){
        double value = 0;
        for(int j = 0; j < waves.size(); j++){
            Wave w = waves.get(j);
            if(w.isOn()){
                value += w.calculateVal(initVals.get(j), w.getVal(), cont, steps) * w.getVolume();
            }
        }
        return value;
    }
    
    public static void mergeSounds(ArrayList<Double> initVals, ArrayList<Wave> waves, double timeWaited, 
            int sampleRate, byte[] buf, SourceDataLine sdl, Variables limit){
        int steps = (int) (timeWaited * (sampleRate/1000000.0));
        double value;
        int cont = 0;
        while(cont<(steps-buf.length)){
            for(int i = 0; i < buf.length; i++){
                
                value = calculatePoint(initVals, waves, cont, steps);
                buf[i] = (byte)(value*limit.getVolume());
                cont++;
            }
            sdl.write( buf, 0, buf.length );
        }
        int buflen2 = steps-cont;
        for(int i = 0; i < buflen2; i++){
            value = calculatePoint(initVals, waves, cont, steps);
            buf[i] = (byte)(value*limit.getVolume());
            cont++;
        }
        sdl.write(buf, 0, buflen2);        
        
    }

    public static void synth(AudioFormat af, int sampleRate, ArrayList<Wave> waves, Variables limit, int bufferSize){
        ArrayList<Double> initVals = new ArrayList<>();
        try{
            SourceDataLine sdl = AudioSystem.getSourceDataLine( af );
            //sdl.open();
            sdl.open(af, bufferSize);
            byte[] buf = new byte[bufferSize];
            sdl.start();
            Wave min = null;
            double timeStep = 1000000/(float)sampleRate;
            double value = 0;
            while(limit.getContinua()) {
                
                for(int j = 0; j < waves.size(); j++){
                    if(waves.get(j).isOn()){
                        min = waves.get(0);
                        break;
                    }
                }
                if(min != null){
                    for(int j = 1; j < waves.size(); j++){
                        if(waves.get(j).getTimeLeft() < min.getTimeLeft() && waves.get(j).isOn())
                            min = waves.get(j);						
                    }
                    initVals = calculateValues(waves);
                    //value = calculateValue(waves);
                    double timeWaited = min.getTimeLeft()%limit.getLimite();	
                    double timeNotWaited = min.getTimeLeft();
                    for(int j = 0; j < waves.size(); j++){
                        if(waves.get(j).isOn())
                            waves.get(j).subTime(timeNotWaited);
                    }
                    mergeSounds(initVals, waves, timeWaited+limit.getWaitErrorDelay(), sampleRate, buf, sdl, limit);
                    //wait(timeWaited + limit.getWaitErrorDelay(), sampleRate, value, buf, sdl, limit);
                }

            }
            sdl.drain();
            sdl.stop();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}