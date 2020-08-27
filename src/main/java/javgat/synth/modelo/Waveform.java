/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javgat.synth.modelo;

/**
 *
 * Interfaz de las formas de las ondas que pueden tener las ondas
 * 
 * @author Javier Gatón Herguedas (javgat)
 * 
 */
public interface Waveform {

    /**
     * Valor de sonido de una onda dados un valor inicial y cuanto tiempo paso
     * @param initVal Valor inicial entre 0 y 1
     * @param val Valor final
     * @param cont Cantidad de pasos que han transcurrido
     * @param steps Pasos totales entre inicial y final
     * @return Valor lineal en el punto, entre 0 y 1
     */
    public double calculateVal(double initVal, double val, int cont, int steps);

    /**
     * Devuelve el valor de sonido de una onda que se encuentre en el estado indicado
     * @param originalTime Tiempo original
     * @param timeLeft Tiempo restante
     * @param up Estado arriba o abajo
     * @return Valor lineal de la onda, entre 1 y 0
     */
    public double getVal(double originalTime, double timeLeft, boolean up);
    
}
