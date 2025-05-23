package autonoma.furiaenlacarretera.views;

import autonoma.furiaenlacarretera.elements.GameField;
import autonoma.furiaenlacarretera.elements.Jugador;
import gamebase.elements.GraphicContainer;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.JOptionPane;

/**
 *
 * @author Kamii
 */
public class GameWindow extends javax.swing.JFrame implements GraphicContainer {

    public static final int _WIDTH = 850;
    public static final int _HEIGHT = 550;
    private GameField gameField;

    // Double Buffer
    private BufferedImage imagenBuffer;
    private Graphics gImagenBuffer;
    String[] options = {"Sí", "No"};

    /**
     * Creates new form GameWindow
     */
    public GameWindow(GameField g) {
        gameField = g;
        setUndecorated(true);
        initComponents();

        this.setLocationRelativeTo(null);

        this.setSize(_WIDTH, _HEIGHT);

        // Crear la imagen y cargarla en memoria
        this.imagenBuffer = new BufferedImage(_WIDTH, _HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.gImagenBuffer = this.imagenBuffer.getGraphics();
    }

    private void manejarFinDePartida() {
        int opcion = JOptionPane.showOptionDialog(
                this,
                "¿Quieres reiniciar la partida?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        boolean continuar = (opcion == JOptionPane.YES_OPTION);
        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
            reiniciarPartida();
        } else {
            System.exit(0);
        }

    }

    private void reiniciarPartida() {
        gameField.getSprites().clear();
        gameField.setPartidaTerminada(false);
        gameField.addJugador();
        gameField.refresh();
    }

    public void terminarPartida() {
        manejarFinDePartida();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }if (evt.getKeyCode() == KeyEvent.VK_LEFT |
             evt.getKeyCode() == KeyEvent.VK_RIGHT) 
        {
            gameField.keyPressed(evt.getKeyCode());
        }

    }//GEN-LAST:event_formKeyPressed

    @Override
    public void update(Graphics g) {
        // Pintar el FoodField encima del fondo
        if (gameField != null) {
            gameField.paint(gImagenBuffer);
//            if (gameField.getPlayer() != null) {
//                gImagenBuffer.setColor(Color.YELLOW);
//                gImagenBuffer.drawString("Puntaje: " + gameField.getPlayer().getPuntaje(), 20, 50);
//            }
        }

        //dibuja la imagen completa en pantalla
        g.drawImage(imagenBuffer, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public Rectangle getBordes() {
        return new Rectangle(WIDTH, HEIGHT);
    }

    /**
     * Refresca la interfaz gráfica de la ventana del juego.
     */
    @Override
    public void refresh() {
        this.repaint();
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
