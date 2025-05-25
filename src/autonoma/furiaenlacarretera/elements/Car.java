package autonoma.furiaenlacarretera.elements;

import gamebase.elements.GraphicContainer;
import gamebase.elements.Sprite;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025-05-20
 */
public class Car extends ElementType {

    public static final int WIDTH_CAR = 18;
    public static final int HEIGH_CAR = 20;

    private BufferedImage imagenBuffer;
    private Graphics g_imagenBuffer;
    private int paso;

    public Car(int x, int y, int width, int height, GraphicContainer g) {
        super(x, y, width, height);
        setImage("carroDeEspaldas.png");

        gameContainer = g;
        paso = (int) ((Math.random() * 10) + 5);
        //paso = 1;
        if (gameContainer != null) {
            hilo.start();
        }

        //creamos la imagen en memoria
        imagenBuffer = new BufferedImage(WIDTH_CAR,
                HEIGH_CAR,
                BufferedImage.TYPE_INT_RGB
        );
        //obtenemos los graficos
        g_imagenBuffer = imagenBuffer.getGraphics();
    }
    public void registerHitGameField(GameField gameField) {
        if (gameField != null) {
            gameField.getSprites().add(this);
        }
    }

    // Método para evitar superposición y crear el carro
    public static void create(GameField gameField, GraphicContainer container, Random rand) {
        int intentosMaximos = 100;
        int intentos = 0;
        boolean seSobrepone = true;

        int x = 0;
        int y = 0;

        while (intentos < intentosMaximos && seSobrepone) {
            x = rand.nextInt(container.getBordes().width - WIDTH_CAR);
            y = rand.nextInt(container.getBordes().height - HEIGH_CAR);

            Rectangle nuevo = new Rectangle(x, y, WIDTH_CAR, HEIGH_CAR);
            seSobrepone = false;

            for (Sprite elemento : gameField.getSprites()) {
                Rectangle existente = new Rectangle(
                    elemento.getX(),
                    elemento.getY(),
                    elemento.getWidth(),
                    elemento.getHeight()
                );

                if (nuevo.intersects(existente)) {
                    seSobrepone = true;
                    break;
                }
            }

            intentos++;
        }

        if (!seSobrepone) {
            Car carro = new Car(x, y, WIDTH_CAR, HEIGH_CAR, container);
            carro.registerHitGameField(gameField);
        } else {
            System.out.println("No se pudo colocar el carro sin superposición tras " + intentosMaximos + " intentos.");
        }
    }
    @Override
    public void update(Graphics g) {
        g.drawImage(imagenBuffer, 0, 0, this);
        //paint(g);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);

    }
    public int getPaso() {
        return paso;
    }

    public void setPaso(int paso) {
        this.paso = paso;
    }

    @Override
    public boolean mover() {

        int direccion = (int) (Math.random() * 3);

        int nx = x;
        int ny = y;
        switch (direccion) {
            case 0:   //ARRIBA

                ny -= paso;

                break;

            case 1:  //ABAJO
                ny += paso;
                break;

            case 2: // IZQUIERDA
                nx -= paso;
                break;

            case 3:  // DERECHA
                nx += paso;
                break;
        }
        if (!fueraContenedor(nx, ny)) {
            x = nx;
            y = ny;
            System.out.println("Me movi");
            if (gameContainer != null) {
                gameContainer.refresh();
            }

            return true;
        }

        return false;

    }

  

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
            if (!jugando) {
                pausar();
            } else {
                dePausar();
            }

            if (isPausa()) {
                continue;
            }

            mover();
        }
    }

    @Override
    public void delete(GameField gameField) {
        if (gameField != null) {
            gameField.getSprites().remove(this);
        }
    }
}
