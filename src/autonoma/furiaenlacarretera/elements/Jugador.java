/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.furiaenlacarretera.elements;

import static autonoma.furiaenlacarretera.elements.Car.HEIGH_CAR;
import static autonoma.furiaenlacarretera.elements.Car.WIDTH_CAR;
import gamebase.elements.GraphicContainer;
import gamebase.elements.Sprite;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Valerie Moreno
 * @since 21/5/2025
 * @version 1.0.0
 */
public class Jugador extends Sprite{
    /**
     * Atributos
     * 
     */
    public static final int WIDTH_JUGADOR = 50;
    public static final int HEIGH_JUGADOR = 50;
    /**
     * atributo de puntaje
     */
    private int puntaje;
    /**
     * atributo de fuel
     */
    private int fuel;
    private BufferedImage imagenBuffer;
    private Graphics g_imagenBuffer;

    /**
     * constructor
     */
    
    public Jugador(int x, int y, int width, int height) {
        super(x, y, height, width);
        this.puntaje = 0;
        this.fuel = 100;
        setImage("chicaMoto.png");
        imagenBuffer = new BufferedImage(WIDTH_JUGADOR,
                HEIGH_JUGADOR,
                BufferedImage.TYPE_INT_RGB
        );
        //obtenemos los graficos
        g_imagenBuffer = imagenBuffer.getGraphics();
    }
    

    @Override
    public void paint(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public void aumentarPuntaje(int score ){
        this.puntaje +=score;
    }
    public void consumirConbustible(){
        
    }
    
}
