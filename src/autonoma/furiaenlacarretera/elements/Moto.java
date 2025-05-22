/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.furiaenlacarretera.elements;

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
     * constructor
     */
    public Moto() {
        this.fuel = 100;
    }
    /**
     * metodo de disminuir comburtible
     */
    public void disminuirCombustible() {
        try {
            Thread.sleep(5000); 
            fuel -= 10;  
            if (fuel < 0) {
                 System.out.println("Se acabe el juego ");
            }
            System.out.println("Combustible actual: " + fuel);
        } catch (InterruptedException e) {
        }
    }
    /**
     * metodo de recargar comburtible
     */
    public void recargarCombustible(int cantidad){
        if (cantidad ==15){
            this.fuel=100;
        }
    }
}
