package autonoma.furiaenlacarretera.elements;

import gamebase.elements.EscritorArchivoTextoPlano;
import gamebase.elements.LectorArchivoTextoPlano;
import gamebase.elements.SpriteContainer;
import java.awt.Graphics;
import java.awt.Rectangle;


/**
 *
 * @author Kamii
 */
public class GameField extends SpriteContainer {

    /**
     * Atributos
     */
    /**
     * Atributo de la instancia de la clase Player
     */
    //private Player player;
 
    //String[] options = {"Sí", "No"};

    private EscritorArchivoTextoPlano escritor;
    private LectorArchivoTextoPlano lector;

    private int maxScore = 0;

    public GameField(int x, int y, int height, int width) {
        super(x, y, height, width);
    }

    @Override
    public void paint(Graphics g) {
    }

    @Override
    public Rectangle getBordes() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void refresh() {
    }

}
