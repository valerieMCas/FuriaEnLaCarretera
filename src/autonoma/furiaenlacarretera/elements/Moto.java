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

    /**
     * metodo de disminuir comburtible
     */
    public void disminuirCombustible() {
        timerCombustible = new Timer(5000, e -> {
            fuel -= 10;
            
            if (fuel <= 0) {
                fuel = 0;
                timerCombustible.stop();
                System.out.println("Se acabó el combustible. Fin del juego.");
            } else {
                System.out.println("Combustible actual: " + fuel);
            }
        });
        timerCombustible.start();
    }

    /**
     * metodo de recargar comburtible
     */
    public void recargarCombustible(int cantidad){
        if (cantidad ==5){
            this.fuel=100;
        }
    }
}
