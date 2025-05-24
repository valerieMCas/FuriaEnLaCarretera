/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.furiaenlacarretera.elements;

import javax.swing.Timer;

/**
 *@author Valerie Moreno
 * @since 21/5/2025
 * @version 1.0.0
 */
public class Moto {
    /**
     * atributos
     */
    /**
     * atributo de fuel
     */
    private int fuel;
    /**
     * atributo timer
     */
    private Timer timerCombustible;
    /**
     * constructor
     */
    public Moto() {    
        
        this.fuel = 10;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    
    public void consumirCombustible() {
        fuel -= 1;
        if (fuel < 0) {
            fuel = 0;
        }
    }

    public boolean estaSinCombustible() {
        return fuel <= 0;
    }

    public void recargarCombustible(int cantidad) {
        fuel += cantidad;
        if (fuel > 100) {
            fuel = 100;
        }
    }
}
