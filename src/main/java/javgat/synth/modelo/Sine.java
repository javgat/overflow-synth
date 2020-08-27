/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.modelo;

/**
 *
 * Representación de ondas sinusoidales
 * 
 * @author Javier Gatón Herguedas (javgat)
 * 
 */
public class Sine implements Waveform {

    /*
    * Transporte del valor al valor negativo que tendría, ya que el eje es
    * entre 0 y 1 entonces la mitad es 0.5 y no 0 como quizá sería más intuitivo
    */
    private double transportNegative(double val){
        return (val-0.5)*2;
    }
    
    @Override
    public double calculateVal(double initVal, double val, int cont, int steps) {
        double initPi = Math.acos(transportNegative(initVal));
        double valPi = Math.acos(transportNegative(val));
        double cos;
        if(valPi > initPi){
            double value = cont * ((valPi-initPi)/steps);
            cos = Math.cos(initPi + value);
        }else{
            double value = cont * ((initPi-valPi)/steps);
            cos = Math.cos(initPi - value);
        }
        return 0.5 + cos/2;
    }

    @Override
    public double getVal(double originalTime, double timeLeft, boolean up) {
        // up = goin up
        double dif = originalTime - timeLeft;
        if(dif == 0){
            if(up)
                return 0;
            else
                return 1;
        }
        double step = Math.PI/(originalTime);
        double valueCos = Math.cos(dif*step);
        if(up)
            valueCos = -valueCos;
        return 0.5 + valueCos/2;
    }
    
}
