package autonoma.furiaenlacarretera.elements;

import gamebase.elements.EscritorArchivoTextoPlano;
import gamebase.elements.LectorArchivoTextoPlano;
import gamebase.elements.Sprite;
import gamebase.elements.SpriteContainer;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Jugador jugador;
    private int offsetX = 0; // desplazamiento horizontal del fondo
    private int offsetY = 0; // desplazamiento vertical si lo necesitas
    private static final int movimineto = 5;

    

    private int maxScore = 0;

    public GameField(int x, int y, int height, int width) {
        super(x, y, height, width);
        setImage("mapa.jpeg");
    }

    /**
     * Metodos de acceso
     */
//    public Player getPlayer() {
//        return player;
//    }
    public List<Sprite> getSprites() {
        return sprites;
    }
    
    /**
     * Metodo para agregar el carro a la pista
     */
    public void addCar() {
        Car.create(this, this, new Random());
    }

    /**
     * Metodo para agregar la persona a la pista
     */
    public void addPerson() {
        int xt = (int) (Math.random() * (width - Cone.WIDTH_CONE));
        int yt = (int) (Math.random() * (height - Cone.HEIGH_CONE));

        Person person = new Person(xt, yt, Cone.WIDTH_CONE, Cone.HEIGH_CONE, this);

        this.sprites.add(person);
    }
    public void addJugador() {
        int startX = width / 2 - 25;  // centrado horizontalmente, restando la mitad del ancho del jugador
        int startY = height - 100;    // cerca de la parte inferior

        this.jugador = new Jugador(startX, startY, 50, 40);
        this.sprites.add(jugador);
    }


    /**
     * Metodo para agregar el cono a la pista
     */
    public void addCone() {
        int xt = (int) (Math.random() * (width - Cone.WIDTH_CONE));
        int yt = (int) (Math.random() * (height - Cone.HEIGH_CONE));

        Cone cone = new Cone(xt, yt, Cone.WIDTH_CONE, Cone.HEIGH_CONE, this);

        add(cone);
    }

    /**
     * Metodo para agregar el carro a la pista
     */
    public void addCurrency() {
        int xt = (int) (Math.random() * (width - Currency.WIDTH_CURRENCY));
        int yt = (int) (Math.random() * (height - Currency.HEIGH_CURRENCY));

        Currency currency = new Currency(xt, yt, Cone.WIDTH_CONE, Cone.HEIGH_CONE, this);

        add(currency);
    }

    public void eliminarElement(ElementType element) {
        // Aumenta el puntaje del jugador por cada pulga eliminada
        sprites.remove(element);
    }

    public void update() {
        // 1. Mover obstáculos, monedas y demás elementos hacia abajo (simulando avance)
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            if (sprite instanceof ElementType) {
                ElementType element = (ElementType) sprite;
                element.setY(element.getY() + element.getStep()); // mueve verticalmente

                // Elimina elemento si sale del campo
                if (element.getY() > height) {
                    eliminarElement(element);
                }
            }
        }
        int margenSuperior = (int)(height * 0.1);
        int margenInferior = (int)(height * 0.7);

        if (jugador.getY() < margenSuperior && offsetY > 0) {
            offsetY -= movimineto;
            jugador.setY(margenSuperior);
        }
        else if (jugador.getY() > margenInferior && offsetY + height < getImage().getHeight(null)) {
            offsetY += movimineto;
            jugador.setY(margenInferior);
        }



//        // 2. Actualizar posición del jugador si se está moviendo entre carriles
//        // (Asumiendo que tienes lógica para mover el jugador horizontalmente)
//        // 3. Revisar colisiones jugador vs obstáculos/monedas
//        for (Sprite sprite : sprites) {
//            if (sprite.getBordes().intersects(car.getBordes())) {
//                if (sprite instanceof Obstacle) {
//                    // Colisión con obstáculo
//                    manejarColisionObstaculo();
//                    break;
//                } else if (sprite instanceof Currency) {
//                    // Recolectar moneda
//                    recolectarMoneda((Currency) sprite);
//                    eliminarElement((ElementType) sprite);
//                }
//            }
//        }
//
//        // 4. Consumir gasolina con el tiempo / distancia recorrida
//        consumirGasolina();
//
//        // 5. Verificar si se acabó la gasolina
//        if (gasolina <= 0) {
//            manejarFinDeJuegoPorGasolina();
//        }
        refresh();  // Refresca pantalla
    }

    @Override
    public void paint(Graphics g) {
        
        g.drawImage(getImage(), 0, -offsetY, getImage().getWidth(null), getImage().getHeight(null), null);

        // Copiar la lista para evitar problemas de concurrencia
        List<Sprite> copiaSprites = new ArrayList<>(sprites);

        for (Sprite sprite : copiaSprites) {
            if (sprite != null) {   // <-- Aquí chequeas que no sea null
                sprite.paint(g);
            }
        }

        //jugador.paint(g);
    }
    public void keyPressed(KeyEvent e) {
        if (jugador != null) {
            jugador.mover(e);
            refresh(); // o repaint()
        }
    }


    @Override
    public Rectangle getBordes() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

}
