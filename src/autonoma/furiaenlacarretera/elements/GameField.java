package autonoma.furiaenlacarretera.elements;

import autonoma.furiaenlacarretera.elements.Cone;
import autonoma.furiaenlacarretera.views.GameWindow;
import gamebase.elements.EscritorArchivoTextoPlano;
import gamebase.elements.LectorArchivoTextoPlano;
import gamebase.elements.Sprite;
import gamebase.elements.SpriteContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.Timer;
/**
 *
 * @author Kamii
 */
public class GameField extends SpriteContainer {

    /**
     * Atributos
     */
    private EscritorArchivoTextoPlano escritor;
    private LectorArchivoTextoPlano lector;
    private Jugador jugador;
    private int offsetX = 0;
    private int offsetY = 0;
    private static final int movimineto = 5;
    private boolean partidaTerminada = false;
    private Timer gameTimer;
    private Thread contadorTiempo;
    private Thread ponerGasolina;

    private int maxScore = 0;

    public GameField(int x, int y, int height, int width, String mapaSeleccionado) {
        super(x, y, height, width);
        setImage(mapaSeleccionado);
    }

    /**
     * Metodos de acceso
     */
//    public Player getPlayer() {
//        return player;
//    }
    public boolean getPartidaTerminada() {
        return partidaTerminada;
    }

    public void setPartidaTerminada(boolean partidaTerminada) {
        this.partidaTerminada = partidaTerminada;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    private boolean hayColision(Rectangle nuevoRect) {
        for (Sprite sprite : sprites) {
            if (sprite.getBoundaries().intersects(nuevoRect)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para agregar el carro a la pista
     */
    public void addCar() {
        int minX = 165; // Por ejemplo, límite izquierdo del carril
        int maxX = 360; // Límite derecho del carril
        int intentosMaximos = 100;
        int intentos = 0;

        int width = Car.WIDTH_CAR;
        int height = Car.HEIGH_CAR;
        int jugadorY = jugador != null ? jugador.getY() : getHeight();
        while (intentos < intentosMaximos) {
            int x = minX + (int) (Math.random() * (maxX - minX - width));
            int y = (int) (Math.random() * (jugadorY - height - 50));

            Rectangle nuevoRect = new Rectangle(x, y, width, height);

            if (!hayColision(nuevoRect)) {
                Car carro = new Car(x, y, width, height, this);
                this.sprites.add(carro);
                break;
            }

            intentos++;
        }

        if (intentos == intentosMaximos) {
            System.out.println("No se pudo colocar el carro sin superposición tras " + intentosMaximos + " intentos.");
        }
    }
    /**
     * Metodo para agregar la gasolina a la pista
     */
    public void addGasolina(){
        int minX = 165; // Por ejemplo, límite izquierdo del carril
        int maxX = 360;
        int width = 50; // tamaño estimado, ajusta según tu sprite
        int height = 50;
        int intentosMaximos = 100;
        int intentos = 0;

        int jugadorY = jugador != null ? jugador.getY() : getHeight();

        while (intentos < intentosMaximos) {
            int x = minX + (int)(Math.random() * (maxX - minX - width));
            int y = (int)(Math.random() * (jugadorY - height - 50));

            Rectangle nuevoRect = new Rectangle(x, y, width, height);

            if (!hayColision(nuevoRect)) {
                Gasolina gasolina = new Gasolina(x, y, width, height, this);
                this.sprites.add(gasolina);
                System.out.println("Gasolina agregada en (" + x + ", " + y + ")");
                break;
            }

            intentos++;
        }

        if (intentos == intentosMaximos) {
            System.out.println("No se pudo colocar la gasolina sin superposición tras " + intentosMaximos + " intentos.");
        }

    }

    /**
     * Metodo para agregar la persona a la pista
     */
    public void addPerson() {
        int minX = 165;
        int maxX = 360;

        int intentos = 0;
        int maxIntentos = 50;
        int jugadorY = jugador != null ? jugador.getY() : getHeight();
        while (intentos < maxIntentos) {
            int xt = minX + (int) (Math.random() * (maxX - minX - Cone.WIDTH_CONE));
            int yt = (int) (Math.random() * (jugadorY - HEIGHT - 50));
            Rectangle nuevoRect = new Rectangle(xt, yt, Cone.WIDTH_CONE, Cone.HEIGH_CONE);
            if (!hayColision(nuevoRect)) {
                Person person = new Person(xt, yt, Cone.WIDTH_CONE, Cone.HEIGH_CONE, this);
                this.sprites.add(person);
                break;
            }
            intentos++;
        }
    }

    public void addJugador() {
        int startX = width / 2 - 25;  // centrado horizontalmente, restando la mitad del ancho del jugador
        int startY = height - 100;    // cerca de la parte inferior

        this.jugador = new Jugador(startX, startY, 50, 40);
        this.jugador.setMoto(new Moto());
        this.sprites.add(jugador);
    }

    /**
     * Metodo para agregar la moneda a la pista
     */
    public void addCurrency() {
        int minX = 165;
        int maxX = 360;

        int intentos = 0;
        int maxIntentos = 50;
        int jugadorY = jugador != null ? jugador.getY() : getHeight();
        while (intentos < maxIntentos) {
            int xt = minX + (int) (Math.random() * (maxX - minX - Currency.WIDTH_CURRENCY));
            int yt = (int) (Math.random() * (jugadorY - HEIGHT - 50));

            Rectangle nuevoRect = new Rectangle(xt, yt, Currency.WIDTH_CURRENCY, Currency.HEIGH_CURRENCY);
            if (!hayColision(nuevoRect)) {
                Currency currency = new Currency(xt, yt, Currency.WIDTH_CURRENCY, Currency.HEIGH_CURRENCY, this);
                add(currency);
                break;
            }
            intentos++;
        }
    }
    public void iniciarContadorTiempo() {
        contadorTiempo =new Thread(() -> {
            while (!partidaTerminada) {
                try {
                    Thread.sleep(2000);
                    if (jugador != null) {
                        jugador.aumentarPuntaje(1);
                        System.out.println("Puntaje: " + jugador.getPuntaje());
                        refresh();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        contadorTiempo.start();
    }
    public void iniciarGasolina() {
        ponerGasolina = new Thread(() -> {
            while (!partidaTerminada) {
                try {
                    Thread.sleep(20000); // 20 segundos
                    if (!partidaTerminada) {
                        addGasolina();
                        refresh();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        ponerGasolina.start();
    }
    public void eliminarElement(ElementType element) {
        // Aumenta el puntaje del jugador por cada pulga eliminada
        sprites.remove(element);
    }

    /**
     * Finaliza la partida actual.
     *
     *
     * elimina todos los sprites del campo de juego, refresca la pantalla y
     * muestra un mensaje de finalización en la consola. Si el contenedor del
     * juego es una ventana la cierra automáticamente.
     */
    public void finalizarPartida() {
        this.partidaTerminada = true; // Detener la lógica del juego
        if (contadorTiempo != null && contadorTiempo.isAlive()) {
            contadorTiempo.interrupt();  // Detener el hilo correctamente
        }
        this.sprites.clear();
        this.refresh();
        System.out.println("Partida finalizada.");

        if (gameContainer instanceof GameWindow) {
            ((GameWindow) gameContainer).terminarPartida();
        }
    }
    public void menejarCombustible(){
        jugador.consumirConbustible();
    }

    /**
     * Actualiza el estado del campo de juego en cada ciclo del juego.
     *
     * Este método realiza varias operaciones: - Consume combustible de la moto
     * del jugador. - Verifica si el jugador se queda sin vidas o sin
     * combustible, y finaliza la partida si es necesario. - Mueve los elementos
     * (carros, conos, personas, monedas, etc.) hacia abajo, simulando avance. -
     * Elimina los elementos que salen de la pantalla. - Desplaza la imagen de
     * fondo para crear un efecto de desplazamiento continuo. - Refresca la
     * pantalla para mostrar los cambios.
     */
    public void update() {
        
        if (jugador.getCantidadVidas() <= 0 || jugador.getMoto().isEstaSinConbustible()) {
            finalizarPartida();
            return;
        }

        //Mover obstaculos, monedas y demás elementos hacia abajo (simulando avance)
        // 1. Mover obstáculos, monedas y demás elementos hacia abajo (simulando avance)
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            if (sprite instanceof ElementType) {
                ElementType element = (ElementType) sprite;

                element.setY(element.getY() + movimineto);

                // Elimina el elemento si ya pasó la parte inferior del campo
                if (element.getY() > height) {
                    eliminarElement(element);
                }
            }
        }
        // Movimiento automático del fondo
        offsetY -= movimineto;
        if (offsetY <= 0) {
            offsetY = getImage().getHeight(null);
        }
        processCollisionMotorbike();
        refresh();  // Refresca pantalla
    }

    private void processCollisionMotorbike() {
        for (int i = sprites.size() - 1; i >= 0; i--) {
            // instanceof es un operador que se usa para verificar si un objeto es de un tipo específico
            if (sprites.get(i) instanceof ElementType) {
                // Convierte el objeto a tipo ElementType para poder trabajar con él como tal.
                ElementType element = (ElementType) sprites.get(i);

                if (jugador.checkCollision(element)) {
                    if (element instanceof Car) {
                        sprites.remove(element);
                    } else if (element instanceof Person) {
                        sprites.remove(element);
                    } else if (element instanceof Currency) {
                        sprites.remove(element);
                    } else {
                        System.out.println("ERROR: GameField.processCollisionMotorbike. Unknown type of ElementType");
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Image fondo = getImage();
        int fondoAlto = fondo.getHeight(null);

        // Calcula la posición vertical del fondo para hacer scroll infinito
        int y1 = -offsetY % fondoAlto;

        // Dibuja dos veces la imagen para rellenar todo el panel
        g.drawImage(fondo, 0, y1, getWidth(), fondoAlto, null);
        g.drawImage(fondo, 0, y1 + fondoAlto, getWidth(), fondoAlto, null);
        if (!partidaTerminada) {
            g.setColor(Color.WHITE);
            g.drawString("Puntaje: " + jugador.getPuntaje(), 10, 20);
        }

        // Copiar la lista para evitar problemas de concurrencia
        List<Sprite> copiaSprites = new ArrayList<>(sprites);

        for (Sprite sprite : copiaSprites) {
            if (sprite != null) {
                sprite.paint(g);
            }
        }
        //jugador.paint(g);
    }

    public void keyPressed(int code) {
        if (code == KeyEvent.VK_LEFT
                | code == KeyEvent.VK_RIGHT) {
            if (jugador != null) {
                jugador.mover(code);
                processCollisionMotorbike();
                refresh();
            }
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
