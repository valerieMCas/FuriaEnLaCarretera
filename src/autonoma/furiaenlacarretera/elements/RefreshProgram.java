package autonoma.furiaenlacarretera.elements;

import autonoma.furiaenlacarretera.views.GameWindow;

/**
 *
 * @author Maria Camila Prada Cortes
 * @version 1.0.0
 * @since 2025-05-20
 */
public class RefreshProgram implements Runnable{
private GameField gameField;
    private GameWindow gameWindow;
    private boolean running;
    private Thread thread;
    /**
     * Constructor
     */
    public RefreshProgram(GameField gameField, GameWindow gameWindow) {
        this.gameField = gameField;
        this.gameWindow = gameWindow;
        running = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            //gameField.update();  // mueve los elementos y refresca
            try {
                Thread.sleep(30);  // 30ms como lo planeabas
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
    }
        
}
