package invasion_extraterrestre;

public class Alien extends Graficos implements IAlien {

    Juego juego;
    static String name="";

    public Alien(Juego juego,String imagen,int columna,int fila)
    {
        super(imagen,columna,fila);

        this.juego = juego;

        desplazamiento_columna = -75; //variable de graficos que controla el desplazamiento horizontal de las imagenes
    }

    public Alien(String imagen,int columna,int fila)
    {
        super(imagen,columna,fila);


        desplazamiento_columna = -75; //variable de graficos que controla el desplazamiento horizontal de las imagenes
    }

    public static String AsignarNombre(int aliens){

        for (int i = 0; i < aliens ; i++) {
            name="Alien Numero "+ i;

        }
    return name;
    }

    public static String getName() {
        return name;
    }

    @Override
    public void mover(long valor) {

        if ((desplazamiento_columna < 0) && (columna < 10)) // lateral izquierdo para que no se pasen de la pntalla
        {
            juego.alcanzado_limite = true;; //asignamos true
        }

        if ((desplazamiento_columna > 0) && (columna > 750)) //lateral derecho
        {
            juego.alcanzado_limite = true;;
        }

        super.mover(valor); // si no se han superado llamamos al metodo mover clase graficos y le pasamos el valor
    }

    @Override
    public void avanzar_aliens() //si se alcanza el limite llamamos a avanzar alien
    {
        desplazamiento_columna = -desplazamiento_columna; // cambiamos el signo para que se desplazen en sentido contrario

        fila += 15;

        if (fila > 570)
        {
            juego.notificar_perdedor(); // si las filas superan 570 el jugador muere
        }
    }

    @Override
    public void colisiona_con(Graficos grafico) {

    }

    @Override
    public String Chillar() {

        return "AAAAAhhhhhh";

    }


//    @Override
//    public String toString() {
//        return "el nombre de este alien es" + name;
//    }
}
