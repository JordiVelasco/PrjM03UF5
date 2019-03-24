package invasion_extraterrestre;


import java.util.Comparator;

public class Nave_espacial extends Graficos implements Comparator<Nave_espacial> {

    Juego juego;
    String name;


    public Nave_espacial(Juego juego,String imagen,int x,int y)
    {
        super(imagen,x,y);

        this.juego = juego;
    }

    public Nave_espacial(String name, String imagen,int x,int y)
    {
        super(imagen,x,y);

        this.name = name;
    }

    public Nave_espacial(String imagen,int x,int y)
    {
        super(imagen,x,y);

        this.name = name;
    }


    @Override
    public void mover(long valor) {

        if ((desplazamiento_columna < 0) && (columna< 10))  //comprobar limite izquierdo
        {
            return; //return sin nada (ninguna accion)
        }

        if ((desplazamiento_columna > 0) && (columna> 750)) //comprobar limite derecho
        {
            return; //return sin nada (ninguna accion)
        }

        super.mover(valor); // se siga movimiendo
    }

    public String getName() {
        return name;
    }

    @Override
    public void colisiona_con(Graficos grafico) {

        if (grafico instanceof Alien) //si la imagen es de tipo alien llamamos a notificar perdedor
        {
            juego.notificar_perdedor();
        }
    }

    @Override
    public int compare(Nave_espacial o1, Nave_espacial o2) {

        Nave_espacial s1=(Nave_espacial)o1;
        Nave_espacial s2=(Nave_espacial)o2;

        return s1.name.compareTo(s2.name);
        }

    }
