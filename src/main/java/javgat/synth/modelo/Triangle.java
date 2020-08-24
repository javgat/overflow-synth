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
public class Triangle implements Waveform{

    @Override
    public double calculateVal(double initVal, double val, int cont, int steps) {
        double value = (cont/(double)steps)*(initVal-val);
        return (initVal - value);
    }

    @Override
    public double getVal(double originalTime, double timeLeft, boolean up) {
        // up = going up
        double dif = originalTime - timeLeft;
        if(dif == 0){
            if(up)
                return 0;
            else
                return 1;
        }
        double step = 1/originalTime;
        if(up)
            return dif*step;
        else
            return 1-(dif*step);
    }
    
}
