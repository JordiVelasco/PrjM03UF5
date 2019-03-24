package invasion_extraterrestre;


import java.awt.Graphics;

import java.awt.Image;


public class Dibujo_imagen {

    Image imagen;

    public Dibujo_imagen(Image imagen)
    {
        this.imagen = imagen; //aliens misil nave
    }


    public int getWidth() //obtener anchura imagen
    {
        return imagen.getWidth(null);
    }


    public int getHeight() //obtener altura imagen
    {
        return imagen.getHeight(null);
    }


    public void dibujar(Graphics grafico,int x,int y) // dibujar imagen con 3 parametros imagen, x e y
    {
        grafico.drawImage(imagen,x,y,null); //el 4 prametro es uno raro y no lo necesitamos (imageObserver)
    }
}











