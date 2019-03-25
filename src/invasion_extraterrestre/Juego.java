package invasion_extraterrestre;



import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.JFrame;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;


public class Juego extends Canvas { //hereda de canvas
    BufferStrategy buffer; //memoria para guardar graficos para evitar parpadeos
    ArrayList lista_imagenes= new ArrayList(); //guardar imagenes
    ArrayList lista_eliminados = new ArrayList(); //aliens eliminados
    Graficos nave; //guardar imagen de nave colocar cada misil que se lanzara desde la nave
    double desplazamiento = 300; //velocidad nave
    long tiempo_ultimo_disparo = 0;
    long intervalo_disparo = 500; //intervalo de disparos
    int num_aliens; //controlar numero de aliens
    String mensaje = ""; //guardar mensajes de ganador o perdedor
    boolean juego_iniciado= true; //se ha inciado un juego
    boolean tecla_no_pulsada = true; //tecla pulsada para saber si se ha pulsado una tecla para iniciar el juego
    boolean flecha_izquierda_pulsada = false; //controlar teclas mover nave
    boolean flecha_derecha_pulsada = false; //controlar teclas mover nave
    boolean disparado= false; //saber si se ha disparado un misil
    boolean alcanzado_limite = false; //nave o aliens limites de la ventana
    int iterator = 0;

    public Juego() {
        JFrame ventana = new JFrame(); //crear ventana
        ventana.getContentPane().setPreferredSize(new Dimension(800,600)); //poner las dimensiones de la ventana
        ventana.getContentPane().add(this); //añadimos el juego que estamos creando a la ventana
        setIgnoreRepaint(true);//ignoramos los mensajes de repintado de las imagenes con true
        ventana.pack(); //adaptamos la dimension de la pantalla al menor tamaño posible para que se visualice todo
        ventana.setResizable(false); //evitar redimensionamiento
        ventana.setVisible(true); //visible ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cerrar ventana y juego
        addKeyListener(new Control_tecla_pulsada());//detectar teclas le pasamos un objeto que creamos de una clase para especificar las funciones
        //requestFocus();
        createBufferStrategy(2); //crear bufer si ponemos 0 o 1 las imagenes no cargan bien
        buffer = getBufferStrategy(); //con get bufferstrategy los obtenemos y lo asignamos a buffer
        añadir_imagenes();//añadir las imagenes de los aliens y la nave
    }

    void iniciar_juego() { //cada vez que se comienza un juego
        lista_imagenes.clear(); //limpiamos todas las imagenes
        añadir_imagenes(); //las volvemos a añadir
        flecha_izquierda_pulsada = false; //y ponenos todos los valores en false para que no se guarden de la partida anterior
        flecha_derecha_pulsada = false;
        disparado= false;
    }

    void añadir_imagenes() {//le pasamos el juego (this) el path de la img la columna y la fila donde se inciiara
        nave = new Nave_espacial(this,"imagenes/nave.gif",370,550); //guardamos en la variable nave un objeto de nave espacial
        lista_imagenes.add(nave);//añadir la nave a la lista de imagenes
        num_aliens = 0; //aliens en 0
        for (int f=0; f<5; f++){ //añadimos los aliens en 5 filas y 12 columnas
            for (int c=0;c<12;c++){
                Graficos alien = new Alien(this,"imagenes/alien.gif",100+(c*50),(50)+f*30); //creando un objeto de tipo grafico llamado alien
                lista_imagenes.add(alien);//se van añadiendo uno a uno con el bucle a lista_imagenes
                num_aliens++;//y vamos sumando los aliens
                System.out.println(((Alien) alien).Chillar());
            }
        }
       System.out.println(Alien.AsignarNombre());
    }

    public void notificar_perdedor(){ //notificar el perdedor
        mensaje = "HAS PERDIDO, SUCIO TERRICOLA"; //asignamos el mensaje
        tecla_no_pulsada = true; //ponemos true ya sabemos que cuando se pulse de nuevo una tecla se iniciara un nuevo juego
    }

    public void notificar_ganador(){
        mensaje = "HAS GANADO, PERO VOLVEREMOS...";
        tecla_no_pulsada = true; //ponemos true ya sabemos que cuando se pulse de nuevo una tecla se iniciara un nuevo juego
    }

    public void descontar_eliminados() {
        num_aliens--; //decrementar en 1 el numero de aliens
        if (num_aliens == 0){
            notificar_ganador(); // el jugador ha ganado
        }
        for (int i=0;i<lista_imagenes.size();i++){ //bucle for para recorrer la lista de imagenes
            Graficos grafico= (Graficos) lista_imagenes.get(i); //crear objetos graficos llamado grafico  vamos guardando cada gradico de la lista de imagenes
            if (grafico instanceof Alien) { //metodo instanceOf podemos comprobar si es igual a alien el grafico
                grafico.desplazamiento_columna = grafico.desplazamiento_columna * 1.02; //aumentamos la velocidad
            }
        }
    }

    public void añadir_misil() { //se llama cada vez que pulsamos el espacio
        if (System.currentTimeMillis() - tiempo_ultimo_disparo < intervalo_disparo){//comprobamos si la diferencia entre el tiempo actual y el tiempo ultimo disparo es menos que el intervalo
            return; //si es asi hacemos return sin ejecutar ninguna funcion
        }
        tiempo_ultimo_disparo = System.currentTimeMillis();//igualamos el tiempo d eultimo disparo al tiempo actual
        Misil misil = new Misil(this,"imagenes/misil.gif",((int)nave.columna) + 10,((int)nave.fila)-30);
        lista_imagenes.add(misil);//creamos un objeto de tipo misil al que le pasamos el juego, imganen,el int de la nave +10 y la fila
    }

    public void controlar_juego(){ //como obtener imagenes como moverlas y que hacer cuando colisionan
        long ultimo_tiempo = System.currentTimeMillis(); //guardar en ultimo tiempo el tiempo actual
        while (juego_iniciado) { //MIENTRAS EL JUEGO ESTA INICIADO
            long valor = System.currentTimeMillis() - ultimo_tiempo;//guardar la diferencia del tiempo actual y el ultimo tiempo
            ultimo_tiempo = System.currentTimeMillis();// despues de asignarle el valor volvemos a poner la variable en el tiempo actual
            Graphics2D G2D = (Graphics2D) buffer.getDrawGraphics();//variable de tipo Graphics2D donde guardamos los graficos que tenemos en el buffer convertidos en 2D
            G2D.setColor(Color.black);//ponemos el color negro como color actual de los graficos en 2d
            G2D.fillRect(0,0,800,600);//creamos un rectangulo en 2d del mismo tamaño que la ventana
            if (!tecla_no_pulsada){//si se ha pulsado un tecla
                for (int i=0;i<lista_imagenes.size();i++){ //bucle for usando lista imagenes para mover todos los graficos
                    Graficos grafico= (Graficos) lista_imagenes.get(i);
                   // System.out.println(grafico.toString());
                    grafico.mover(valor);//se llamara a los distintos metodos de las clases mover dependiendo del grafico
                }
            }
            for (int i=0;i<lista_imagenes.size();i++){ //recorremos lista imagenes
                Graficos grafico= (Graficos) lista_imagenes.get(i);
                grafico.dibujar_graficos(G2D);//dibujara cada grafico de la lista en 2d
            }
            for (int k=0; k<lista_imagenes.size(); k++){
                for (int s=k+1; s<lista_imagenes.size(); s++){
                    Graficos grafico1 = (Graficos) lista_imagenes.get(k);//grafico1 bucle exterior
                    Graficos grafico2 = (Graficos) lista_imagenes.get(s);//grafico2 bucle interior
                    if (grafico1.choca(grafico2)){//llamamos al metodo choca para saber si los graficos han chocado
                        grafico1.colisiona_con(grafico2);//si han colusionado llamamos al metodo colisiona_con para realizar las funciones
                        grafico2.colisiona_con(grafico1);
                    }
                }
            }
            lista_imagenes.removeAll(lista_eliminados);//borramos los aliens eliminados de la lista imagenes
            lista_eliminados.clear();//limpiamos la lista de eliminados
            if (alcanzado_limite){//comprobar si los alienes al alcanzado el limite izquierdo o derecho
                for (int i=0;i<lista_imagenes.size();i++){
                    Graficos grafico = (Graficos) lista_imagenes.get(i);
                    grafico.avanzar_aliens();//llamamos a este metodo para que vayan avanzando
                }
                alcanzado_limite = false; //si no lo al alcanzado ponemos en false
            }
            if (tecla_no_pulsada){//si no se ha pulsado una tecla para iniciar el juego
                G2D.setColor(Color.white);//color
                G2D.drawString(mensaje,(800-G2D.getFontMetrics().stringWidth(mensaje))/2,250);//escribir mensaje de ganador o perdedor
                G2D.drawString("Pulse una tecla para continuar",300,400);
            }
            G2D.dispose();//eliminamos todos los recursos del sistema graphics 2d
            buffer.show();//hacemos visible la siguiente imagen del buffer
            nave.desplazamiento_columna = 0;//ponemos en el centro la nave
            if (flecha_izquierda_pulsada) {
                nave.desplazamiento_columna = -desplazamiento;//movemos la nave hacia la izquierda
            }else if (flecha_derecha_pulsada){
                nave.desplazamiento_columna = desplazamiento; //movemos la nave hacia la derecha
            }if(disparado) {
                añadir_misil(); //fisparamos misiles
            }
            iterator ++;
            try { Thread.sleep(10); } catch (Exception e) {}//pausamos el juego 10 milisegundos
        }
    }


    class Control_tecla_pulsada extends KeyAdapter {//clase interna hereda de keyadapter
        int pulsaciones = 1;//nos servira para controlar si se ha pulsado una tecla para iniciar el juego

        public void keyPressed(KeyEvent e) { //sobreescribimos el metodo keypress se invoca cuando se ha pulsado una tecla
            if (tecla_no_pulsada){ // comprobamos si no se ha iniciaod una tecla para iniciar el juego
                return;//volvemos sin ejecutar nada
            }if (e.getKeyCode() == KeyEvent.VK_LEFT){//comprbamos si se ha pulsado la tecla izquierda
                flecha_izquierda_pulsada = true; //ponemos en true
            }if (e.getKeyCode() == KeyEvent.VK_RIGHT){ // comprobamos si se ha pulsado la tecla derecha
                flecha_derecha_pulsada = true; //ponemos en true
            }if (e.getKeyCode() == KeyEvent.VK_SPACE){ //comprobamos si se ha pulsado el espacio
                disparado= true; //ponemos en true
            }
        }

        public void keyReleased(KeyEvent e) { //sobrescribimos metodo keyrealesed  que se invoca cuando se ha liberado la tecla
            if (tecla_no_pulsada){ //comprobamos si no se ha iniciaod una tecla para iniciar el juego
                return; //volvemos sin hacer nada
            }if (e.getKeyCode() == KeyEvent.VK_LEFT){ //hacemos todo lo contrario a lo de antes{
                flecha_izquierda_pulsada = false;
            }if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                flecha_derecha_pulsada = false;
            }if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                disparado= false;
            }
        }

        public void keyTyped(KeyEvent e) { //solo se invoca si se ha invocado teclas que no son funciones
            if (tecla_no_pulsada){ //si no se ha pulsado
                if (pulsaciones == 1){ //si pulsaciones es = 1
                    tecla_no_pulsada = false; //ya se ha pulsado una tecla para iniciar un juego nuevo
                    iniciar_juego(); //empezar juego
                    pulsaciones = 0; //pulsaciones == 0
                } else { //si es distinto solo se iniciara un juego cuando pulsaciones sea igual a 1
                    pulsaciones++;
                }
            }
        }
    }

    public static void main(String argv[]) {
        // ---------------------------------------------------Clase generica-------------------------------------------------------------------------------------

        ClasseGenerica<Alien> alienX= new ClasseGenerica<>(50);
        ClasseGenerica<Nave_espacial> naveX= new ClasseGenerica<>(10);

        Alien alien2 = new Alien("imagenes/alien.gif",100+(50),(50)+30);
        Nave_espacial nave2 = new Nave_espacial("imagenes/nave.gif",100+(50),(50)+30);
        Stack pila = new Stack();
        LinkedList cola = new LinkedList();

        cola.offer(alien2);
        pila.push(alien2);
        alienX.add(alien2);
        naveX.add(nave2);
        System.out.println("CLASE GENERICA   " + alienX.getTope());
        System.out.println("CLASE GENERICA   " + naveX.getTope());

        // ------------------------------------------------------------------ comparable ------------------------------------------------------------------------

        Misil m1 = new Misil(50,"imagenes/misil.gif",10,10);
        Misil m2 = new Misil(50,"imagenes/misil.gif",10,10);
        Misil m3 = new Misil(85,"imagenes/misil.gif",10,10);

        System.out.println("COMPRABLE A   " + m1.compareTo(m2));
        System.out.println("COMPRABLE B   " + m1.compareTo(m3));

        // ------------------------------------------------------------------ comparator ------------------------------------------------------------------------

        Nave_espacial n1 = new Nave_espacial("Halcon","imagenes/misil.gif",10,10);
        Nave_espacial n2 = new Nave_espacial("Halcon","imagenes/misil.gif",10,10);
        Nave_espacial n3 = new Nave_espacial("Halcon Mil","imagenes/misil.gif",10,10);

        System.out.println("COMPARATOR A    "+n2.compare(n1,n2));
        System.out.println("COMPARATOR B    "+n2.compare(n1,n3));

       new Juego().controlar_juego(); //instanciamos la clase juego y llamamos al metodo controlar juego
    }
}
