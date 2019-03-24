package invasion_extraterrestre;



import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Graficos {

    Dibujo_imagen dibujo_imagen; //guardar graficos transparentes
    double columna;
    double fila;
    double desplazamiento_columna;
    double desplazamiento_fila;

    Rectangle grafico1 = new Rectangle(); //crear rectangulo invisible en la misma posicion de las imagenes
    Rectangle grafico2 = new Rectangle(); //para saber cuando chocan

    public Graficos(String imagen,int columna,int fila)
    {
        this.dibujo_imagen = Configuracion_imagen.configuracion.transparentar_imagen(imagen); //guardar el grafico transparente

        this.columna = columna;

        this.fila = fila;
    }

    public void mover(long valor)
    {
        columna += (valor * desplazamiento_columna) / 1000;

        fila += (valor * desplazamiento_fila) / 500;
    }

    public void dibujar_graficos (Graphics grafico)
    {
        dibujo_imagen.dibujar(grafico,(int)columna,(int) fila);
    }

    public void avanzar_aliens() {}

    public boolean choca(Graficos imagen) //si un alien choca con la nave o misil con alien
    {
        grafico1.setBounds((int)columna,(int)fila,dibujo_imagen.getWidth(),dibujo_imagen.getHeight()); //colocal las imagenes en los 2 rectangulos

        grafico2.setBounds((int)imagen.columna,(int)imagen.fila,imagen.dibujo_imagen.getWidth(),imagen.dibujo_imagen.getHeight());

        return grafico1.intersects(grafico2);
    }

    public abstract void colisiona_con(Graficos imagen);
}