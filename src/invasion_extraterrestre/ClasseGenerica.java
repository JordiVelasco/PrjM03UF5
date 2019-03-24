package invasion_extraterrestre;
import java.util.ArrayList;
import java.util.Iterator;

public class ClasseGenerica<T> implements Iterable<T>{

    private ArrayList<T> lista= new ArrayList<T>();
    private int tope;


    public ClasseGenerica(int tope) {
        super();
        this.tope = tope;
    }

    public void add(T objeto ) {
        if (lista.size()<=tope) {

            lista.add(objeto);
        }
        else {

            throw new RuntimeException("no caben mas");
        }

        }

        public Iterator<T> iterator() {
            return lista.iterator();
        }

    public int getTope() {
        return tope;
    }
}

