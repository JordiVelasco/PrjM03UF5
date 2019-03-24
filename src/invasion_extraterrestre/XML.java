package invasion_extraterrestre;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Classe que llegeix un document mitjançant JAXP i SAX
 */
public class XML {

    public static void main(String argv[]) {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            File stocks = new File("Jugadors.xml");
            /**
             * Classe Interna que gestiona els esdeveniments
             */
            DefaultHandler handler = new DefaultHandler() {

                boolean bCodigo = false;
                boolean bNombre = false;
                boolean bTiempo = false;

                @Override
                public void startElement(String uri, String nomLocal, String nomElement,
                                         Attributes atributs) throws SAXException {

                    //System.out.println("Inici d'element :" + nomElement);

                    if (nomElement.equalsIgnoreCase("codigo")) {
                        bCodigo = true;
                    }

                    if (nomElement.equalsIgnoreCase("nombre")) {
                        bNombre = true;
                    }

                    if (nomElement.equalsIgnoreCase("tiempo")) {
                        bTiempo = true;
                    }
                }

                @Override
                public void endElement(String uri, String localName,
                                       String nomElement) throws SAXException {

                    //System.out.println("Final d'element :" + nomElement);

                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {

                    if (bCodigo) {
                        System.out.println("Código: " + new String(ch, start, length));
                        bCodigo = false;
                    }
                    if (bNombre) {
                        System.out.println("Nombre: " + new String(ch, start, length));
                        bNombre = false;
                    }
                    if (bTiempo) {
                        System.out.println("Tiempo: " + new String(ch, start, length));
                        bTiempo = false;
                    }
                }

            };

            saxParser.parse(stocks, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
