package invasion_extraterrestre;

import java.awt.Image;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;



public class Configuracion_imagen {

    static Configuracion_imagen configuracion = new Configuracion_imagen();

    HashMap hashmap= new HashMap(); //guardar imagenes trasparentes





    public Dibujo_imagen transparentar_imagen(String grafico) {

        if (hashmap.get(grafico) != null)  //si esta en el hashmap
        {
            return (Dibujo_imagen) hashmap.get(grafico); //devuelve el grafico convertido en la posicion indicada
        }

        BufferedImage imagen_buffer = null;  //para guardar imagenes en el buffer

        try { //guardar ruta de los graficos
            URL url = this.getClass().getClassLoader().getResource(grafico); //obtener clase en tiempo de ejecucion de la variable configuracion resource ruta

            if (url == null) // si esta vacia no se ha encontrado el gradico
            {
                System.err.println("No se encontró el archivo: "+grafico);
            }

            imagen_buffer = ImageIO.read(url); //guardar imagen buffer le pasamos la ruta

        } catch (IOException e){
            System.err.println("No se pudo cargar la imagen: "+grafico);
        }

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(); //guardar configuracion pantalla predeterminada

        Image imagen = gc.createCompatibleImage(imagen_buffer.getWidth(),imagen_buffer.getHeight(),Transparency.BITMASK); //guardar imagen del bufer transparente  y compatable con las caracteristicas de la pantalla

        imagen.getGraphics().drawImage(imagen_buffer,0,0,null); //dibujar imagen metodo drawimage

        Dibujo_imagen dibujo = new Dibujo_imagen(imagen); //dibujar imagen del tamaño y posicion que se le pasara

        hashmap.put(grafico,dibujo); //colocar la imagen dibujada y transparente que esta guardada en dibujo en hashmap

        return dibujo; //imagen ya dibujada
    }

}








