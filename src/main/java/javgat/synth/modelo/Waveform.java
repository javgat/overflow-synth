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
public interface Waveform {

    public double calculateVal(double initVal, double val, int cont, int steps);

    public double getVal(double originalTime, double timeLeft, boolean up);
    
}
