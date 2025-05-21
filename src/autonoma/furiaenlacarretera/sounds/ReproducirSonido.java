package autonoma.furiaenlacarretera.sounds;

import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Clase utilitaria para reproducir sonidos en formato WAV. Utiliza la API
 * javax.sound.sampled para cargar y reproducir clips de audio.
 */
public class ReproducirSonido {

    /**
     * Clip estático que almacena el sonido que se está reproduciendo
     * actualmente. Al ser estático, se comparte entre todas las llamadas a
     * playSound.
     */
    private static Clip clip;

    /**
     * Reproduce un archivo de sonido WAV desde la ruta de recursos indicada. Si
     * ya hay un sonido en reproducción, lo detiene antes de iniciar uno nuevo.
     *
     * @param nombreArchivo
     */
    public static void playSound(String nombreArchivo) {
        try {
            detener();
            InputStream inputStream = ReproducirSonido.class.getResourceAsStream("/autonoma/furiaenlacarretera/sounds/" + nombreArchivo);
            if (inputStream == null) {
                System.out.println("Archivo no encontrado: " + nombreArchivo);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error al reproducir sonido: " + e.getMessage());
        }
    }

    public static void detener() {
        try {
            if (clip != null) {
                clip.stop();
                clip.flush();
                clip.close();
                clip = null;
            }
        } catch (Exception e) {
            System.out.println("Error al detener sonido: " + e.getMessage());
        }
    }
}
