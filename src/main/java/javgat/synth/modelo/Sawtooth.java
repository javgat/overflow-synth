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
public class Sawtooth implements Waveform{

    @Override
    public double calculateVal(double initVal, double val, int cont, int steps) {
        if(initVal > val){
            double value = (cont/(double)steps)*(initVal-val);
            return (initVal - value);
        }else{
            double value = (cont)*(initVal-val+1)/steps;
            double result = initVal - value;
            if(result < 0){
                result = result+1;
            }
            return result;
        }
    }

    @Override
    public double getVal(double originalTime, double timeLeft, boolean up) {
        //up = mitad superior, sawtooth que desciende
        double dif = originalTime - timeLeft;
        if(dif == 0){
            if(up)
                return 1;
            else
                return 0.5;
        }
        double step = 0.5/originalTime;
        if(up)
            return 1-(dif*step);
        else
            return 0.5-(dif*step);
    }
    
}
