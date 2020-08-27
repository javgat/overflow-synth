/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.modelo;

/**
 * 
 * Representación de ondas cuadradas
 *
 * @author Javier Gatón Herguedas (javgat)
 * 
 */
public class Square implements Waveform {

    @Override
    public double calculateVal(double initVal, double val, int cont, int steps) {
        return initVal;
    }

    @Override
    public double getVal(double originalTime, double timeLeft, boolean up) {
        if(up)
            return 1;
        else
            return 0;
    }
    
}
