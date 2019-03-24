package invasion_extraterrestre;


public class Misil extends Graficos {

    double desplazamiento = -300;

    Juego juego;

    boolean misil_disparado = false;


    public Misil(Juego juego, String imagen, int x, int y) {
        super(imagen, x, y);

        this.juego = juego;

        desplazamiento_fila = desplazamiento; //desplazamiento fila variable graficos controla la velocidad
    }


    @Override
    public void mover(long valor) {

        super.mover(valor); //para mover el misil (y)

    }


    @Override
    public void colisiona_con(Graficos grafico) {

        if (misil_disparado) {
            return; //cada misil elimina solamente a un alien
        }

        if (grafico instanceof Alien)  // si el misil se topa con la imagen alien hacemos:
        {
            juego.lista_eliminados.add(grafico); //lo añadimos a lista eliminados

            juego.descontar_eliminados(); // los escuenta para que no aparezcan

            misil_disparado = true;
        }
    }
}
