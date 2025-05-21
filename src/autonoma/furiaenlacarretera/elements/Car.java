package autonoma.furiaenlacarretera.elements;

import gamebase.elements.GraphicContainer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Maria Camila Prada Cortes
 * @version 1.0.0
 * @since 2025-05-20
 */
public class Car extends ElementType {

    public static final int WIDTH_CAR = 48;
    public static final int HEIGH_CAR = 80;

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

    @Override
    public void update(Graphics g) {
        g.drawImage(imagenBuffer, 0, 0, this);
        //paint(g);
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("Paint carro");
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
