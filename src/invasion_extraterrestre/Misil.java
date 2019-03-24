package invasion_extraterrestre;


public class Misil extends Graficos implements Comparable<Misil>{

    double desplazamiento = -300;

    int potencia;

    Juego juego;

    boolean misil_disparado = false;


    public Misil(Juego juego, String imagen, int x, int y) {
        super(imagen, x, y);

        this.juego = juego;

        desplazamiento_fila = desplazamiento; //desplazamiento fila variable graficos controla la velocidad
    }

    public Misil(int potencia, String imagen, int x, int y) {
        super(imagen, x, y);

        this.potencia = potencia;

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

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    @Override
    public int compareTo(Misil t) {
        if (this.potencia == t.potencia)		return 0;
        else if (this.potencia > t.potencia)	return 1;
        else 							        return -1;
    }
}