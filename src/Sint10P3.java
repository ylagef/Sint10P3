import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;

public class Sint10P3 extends HttpServlet {
    public static LinkedList<Document> listaInterpretes = new LinkedList<Document>(); //Lista donde se almacenarán los intérpretes.
    public static XPathFactory xPathfactory = XPathFactory.newInstance();
    public static XPath xpath = xPathfactory.newXPath();

    public void init() {
        crearListaInterpretes(listaInterpretes);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        inicio(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String valorFase = req.getParameter("fase");
        String valorConsulta = req.getParameter("consulta");
        switch (Integer.parseInt(valorFase)) {
            case 1:
                if (valorConsulta.equals("1")) {
                    try {
                        pantalla21(req, res);
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        pantalla22(req, res);
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 21:
                try {
                    pantalla31(req, res);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                break;
            case 31:
                try {
                    pantalla41(req, res);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                break;
            case 22:
                try {
                    pantalla32(req, res);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                break;
            case 32:
                try {
                    pantalla42(req, res);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                break;
            case 42:
                try {
                    pantalla52(req, res);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                inicio(req, res);
                break;
            default:
                inicio(req, res);
                break;
        }
    }

    //IMPRIME LA PANTALLA DE INICIO
    public void inicio(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        LinkedList<String> errAux = new LinkedList<String>();
        LinkedList<String> fAux = new LinkedList<String>();

        for (int i = 0; i < errores.size(); i++) {
            if (!errAux.contains(errores.get(i))) {
                errAux.add(errores.get(i));
            }
        }

        for (int i = 0; i < ficherosError.size(); i++) {
            if (!fAux.contains(ficherosError.get(i))) {
                fAux.add(ficherosError.get(i));
            }
        }
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'/>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'/>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        for (int i = 0; i < errAux.size(); i++) {
            out.println("<h4>" + errAux.get(i) + "</h4>");
        }
        for (int i = 0; i < fAux.size(); i++) {
            out.println("<h5>" + fAux.get(i) + "</h5>");
        }
        out.println("<h3>Selecciona una consulta:</h3>");
        out.println("<form method='POST' action='?fase=1'>");
        out.println("<input type='radio' name='consulta' value='1' checked> Lista de canciones de un álbum<br>");
        out.println("<input type='radio' name='consulta' value='2'> Número de canciones de un estilo<br>");
        out.println("<p><input type='submit' value='Enviar' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 2 DE LA CONSULTA 1
    public void pantalla21(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        LinkedList<String> listaNombres = getNombreInterpretes();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1</h2>");
        if (listaNombres.size() > 0) {
            out.println("<h3>Selecciona un intéprete:</h3>");
            out.println("<form method='POST' action='?fase=21' >");
            for (int i = 0; i < listaNombres.size(); i++) {
                out.println("<input type='radio' name='interprete' value='" + listaNombres.get(i) + "'> " + listaNombres.get(i) + "<br>");
            }
            out.println("<input type='radio' name='interprete' value='todos' checked> Todos <br>");
            out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        } else {
            out.println("<h3>Lo sentimos, no existen intérpretes.</h3>");
            out.println("<form method='POST' action='?fase=21' >");
        }
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 3 DE LA CONSULTA 1
    public void pantalla31(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        LinkedList<String> listaAlbums = getAlbumsInterprete(req.getParameter("interprete"));
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1, Intérprete=" + req.getParameter("interprete") + "</h2>");
        if (listaAlbums.size() > 0) {
            out.println("<h3>Selecciona un álbum:</h3>");
            out.println("<form method='POST' action='?fase=31' >");
            out.println("<input type='hidden' name='interprete' value='" + req.getParameter("interprete") + "'>");
            for (int i = 0; i < listaAlbums.size(); i++) {
                out.println("<input type='radio' name='album' value='" + listaAlbums.get(i) + "'> " + listaAlbums.get(i) + "<br>");
            }
            out.println("<input type='radio' name='album' value='todos' checked> Todos <br>");
            out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        } else {
            out.println("<h3>Lo sentimos, el intérprete seleccionado no tiene álbums.</h3>");
            out.println("<form method='POST' action='?fase=31' >");
        }

        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=1&consulta=1\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 4 DE LA CONSULTA 1
    public void pantalla41(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        LinkedList<String> listaCanciones = getCancionesAlbum(req.getParameter("interprete"), req.getParameter("album"));
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1, Intérprete=" + req.getParameter("interprete") + ", Álbum=" + req.getParameter("album") + "</h2>");
        if (listaCanciones.size() > 0) {
            out.println("<h3>Estas son sus canciones:</h3>");
            out.println("<form method='POST' action='?fase=41' >");
            out.println("<input type='hidden' name='interprete' value='" + req.getParameter("interprete") + "'>");
            out.println("<input type='hidden' name='album' value='" + req.getParameter("album") + "'>");
            for (int i = 0; i < listaCanciones.size(); i++) {
                out.println("<h6>" + listaCanciones.get(i) + "</h6>");
            }
        } else {
            out.println("<h3>Los sentimos, no existen canciones.</h3>");
            out.println("<form method='POST' action='?fase=41' >");
        }
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=21\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    /////////////////////////////////////////////////// CONSULTA 2 ////////////////////////////////////////////////
//FASE 2 DE LA CONSULTA 2
    public void pantalla22(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        LinkedList<String> listaAnhos = getAnhos();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2</h2>");
        if (listaAnhos.size() > 0) {
            out.println("<h3>Selecciona un año:</h3>");
            out.println("<form method='POST' action='?fase=22' >");
            for (int i = 0; i < listaAnhos.size(); i++) {
                out.println("<input type='radio' name='anho' value='" + listaAnhos.get(i) + "'> " + listaAnhos.get(i) + "<br>");
            }
            out.println("<input type='radio' name='anho' value='todos' checked> Todos <br>");
            out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        } else {
            out.println("<h3>Lo sentimos, no existen años.</h3>");
            out.println("<form method='POST' action='?fase=22' >");
        }
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 3 DE LA CONSULTA 2
    public void pantalla32(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        LinkedList<String> listaAlbums = getAlbumsAnhos(req.getParameter("anho"));
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        ;
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2, Año=" + req.getParameter("anho") + "</h2>");
        if (listaAlbums.size() > 0) {
            out.println("<h3>Selecciona un álbum:</h3>");
            out.println("<form method='POST' action='?fase=32' >");
            out.println("<input type='hidden' name='anho' value='" + req.getParameter("anho") + "'>");
            for (int i = 0; i < listaAlbums.size(); i++) {
                out.println("<input type='radio' name='album' value='" + listaAlbums.get(i) + "'> " + listaAlbums.get(i) + "<br>");
            }
            out.println("<input type='radio' name='album' value='todos' checked> Todos <br>");
            out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        } else {
            out.println("<h3>Lo sentimos, no existen álbums de ese año.</h3>");
            out.println("<form method='POST' action='?fase=32' >");
        }
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=1&consulta=2\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 4 DE LA CONSULTA 2
    public void pantalla42(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        LinkedList<String> listaEstilos = getEstilos(req.getParameter("anho"), req.getParameter("album"));
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2, Año=" + req.getParameter("anho") + ", Álbum=" + req.getParameter("album") + "</h2>");
        if (listaEstilos.size() > 0) {
            out.println("<h3>Selecciona un estilo:</h3>");
            out.println("<form method='POST' action='?fase=42' >");
            out.println("<input type='hidden' name='anho' value='" + req.getParameter("anho") + "'>");
            out.println("<input type='hidden' name='album' value='" + req.getParameter("album") + "'>");
            for (int i = 0; i < listaEstilos.size(); i++) {
                out.println("<input type='radio' name='estilo' value='" + listaEstilos.get(i) + "'> " + listaEstilos.get(i) + "<br>");
            }
            out.println("<input type='radio' name='estilo' value='todos' checked> Todos <br>");
            out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        } else {
            out.println("<h3>Lo sentimos, no existen estilos.</h3>");
            out.println("<form method='POST' action='?fase=42' >");
        }
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=22\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 5 DE LA CONSULTA 2
    public void pantalla52(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"es\">");
        out.println("<head>");
        out.println("<meta charset=\"ISO 8859\" />");
        out.println("<title>Práctica 3</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2, Año=" + req.getParameter("anho") + ", Álbum=" + req.getParameter("album") + ", Estilo=" + req.getParameter("estilo") + "</h2>");
        out.println("<form method='POST' action='fase=52' >");
        out.println("<input type='hidden' name='anho' value='" + req.getParameter("anho") + "'>");
        out.println("<input type='hidden' name='album' value='" + req.getParameter("album") + "'>");
        out.println("<input type='hidden' name='estilo' value='" + req.getParameter("estilo") + "'>");
        out.println("<h3> El número de canciones es de: " + getNumCancionesEstilo(req.getParameter("anho"), req.getParameter("album"), req.getParameter("estilo")) + " </h3>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=32\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//GESTIÓN DE LOS .XML Y CREAR LA LISTA CON LOS DOCUMENTOS
//CREA LA LINKEDLIST DE DOCUMENTOS CON TODOS LOS XML
    public static void crearListaInterpretes(LinkedList<Document> listaInterpretes) {
        XML_DTD_ErrorHandler errorHandler;
        errorHandler = new XML_DTD_ErrorHandler();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String IN = null;
        try {
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(errorHandler);
            String URL = "http://clave.det.uvigo.es:8080/~sintprof/15-16/p2/sabina.xml";
            LinkedList<String> listaLeidos = new LinkedList<String>(); //AQUÍ SE ALMACENAN LOS QUE YA SE LEYERON Y SE INTRODUJERON EN LISTAINTERPRETES
            LinkedList<String> listaNoLeidos = new LinkedList<String>(); //SE LEYÓ EL CAMPO IML PERO NO SE INTRODUJO EN LISTAINTERPRETES
            LinkedList<String> listaIML = new LinkedList<String>(); //LISTA DE IMLS PARA AÑADIR
            listaNoLeidos.add(URL);
            while (listaNoLeidos.size() > 0) {
                if (añadirIMLS(listaIML, (String) listaNoLeidos.getFirst())) { //Añade a la lista de IML los de los campos IML del doc Sabina
                    listaLeidos.add(listaNoLeidos.getFirst()); //Sabina ya está leído
                    IMLStoNoLeidos(listaIML, listaNoLeidos, listaLeidos); //Pasa los IML (String) a la lista de los No Leídos (Document)
                    listaNoLeidos.removeFirst();
                } else {
                    listaNoLeidos.removeFirst();
                }
            }
            for (int p = 0; p < listaLeidos.size(); p++) {
                Document docAuxiliar;
                IN = (String) listaLeidos.get(p);
                if (IN.startsWith("http:")) {
                    docAuxiliar = builder.parse(new URL(IN).openStream());
                } else {
                    docAuxiliar = builder.parse(new URL("http://clave.det.uvigo.es:8080/~sintprof/15-16/p2/" + IN).openStream());
                }
                listaInterpretes.add(docAuxiliar);
            }
        } catch (SAXException e) {
            if (errorHandler.hasError() || errorHandler.hasWarning() || errorHandler.hasFatalError()) {
                ficherosError.add("Fichero erróneo: " + IN);
                errores.add(errorHandler.getMessage());
                errorHandler.clear();
            } else {
                errores.add("Error: " + e.toString());
                ficherosError.add("Fichero erróneo: " + IN);
            }
        } catch (IOException e) {
            if (errorHandler.hasError() || errorHandler.hasWarning() || errorHandler.hasFatalError()) {
                ficherosError.add("Fichero erróneo: " + IN);
                errores.add(errorHandler.getMessage());
                errorHandler.clear();
            } else {
                errores.add("Error: " + e.toString());
                ficherosError.add("Fichero erróneo: " + IN);
            }
        } catch (ParserConfigurationException e) {
            if (errorHandler.hasError() || errorHandler.hasWarning() || errorHandler.hasFatalError()) {
                ficherosError.add("Fichero erróneo: " + IN);
                errores.add(errorHandler.getMessage());
                errorHandler.clear();
            } else {
                errores.add("Error: " + e.toString());
                ficherosError.add("Fichero erróneo: " + IN);
            }
        } catch (XPathExpressionException e) {
            if (errorHandler.hasError() || errorHandler.hasWarning() || errorHandler.hasFatalError()) {
                ficherosError.add("Fichero erróneo: " + IN);
                errores.add(errorHandler.getMessage());
                errorHandler.clear();
            } else {
                errores.add("Error: " + e.toString());
                ficherosError.add("Fichero erróneo: " + IN);
            }
        }
    }

    public static LinkedList<String> errores = new LinkedList<String>();
    public static LinkedList<String> ficherosError = new LinkedList<String>();

    //LEE EN LOS CAMPOS IML LOS ENLACES Y LOS AÑADE A LA LISTA DE IMLS
    public static boolean añadirIMLS(LinkedList listaIML, String IN) throws XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder;
        XML_DTD_ErrorHandler errorHandler;
        errorHandler = new XML_DTD_ErrorHandler();
        Document documento = null;
        try {
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(errorHandler);
            if (IN.startsWith("http:")) {
                documento = builder.parse(new URL(IN).openStream());
            } else {
                documento = builder.parse(new URL("http://clave.det.uvigo.es:8080/~sintprof/15-16/p2/" + IN).openStream());
            }
        } catch (ParserConfigurationException e) {
            if (errorHandler.hasError() || errorHandler.hasWarning() || errorHandler.hasFatalError()) {
                ficherosError.add("Fichero erróneo: " + IN);
                errores.add(errorHandler.getMessage());
                errorHandler.clear();
            } else {
                errores.add("Error: " + e.toString());
                ficherosError.add("Fichero erróneo: " + IN);
            }
            return false;
        } catch (IOException e) {
            if (errorHandler.hasError() || errorHandler.hasWarning() || errorHandler.hasFatalError()) {
                ficherosError.add("Fichero erróneo: " + IN);
                errores.add(errorHandler.getMessage());
                errorHandler.clear();
            } else {
                errores.add("Error: " + e.toString());
                ficherosError.add("Fichero erróneo: " + IN);
            }
            return false;
        } catch (SAXException e) {
            if (errorHandler.hasError() || errorHandler.hasWarning() || errorHandler.hasFatalError()) {
                ficherosError.add("Fichero erróneo: " + IN);
                errores.add(errorHandler.getMessage());
                errorHandler.clear();
            } else {
                errores.add("Error: " + e.toString());
                ficherosError.add("Fichero erróneo: " + IN);
            }
            return false;
        }
        XPathExpression expr = xpath.compile("/Interprete/Album/Cancion/Version/IML/text()");
        NodeList nodes = (NodeList) expr.evaluate(documento, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) { //Añade los IML del documento a la lista de IMLS
            listaIML.add(nodes.item(i).getNodeValue());
        }
        return true;
    }

    //PASA LOS IML QUE NO ESTÉN YA LEÍDOS A LA LISTA DE NO LEÍDOS
    public static void IMLStoNoLeidos(LinkedList listaIML, LinkedList listaNL, LinkedList listaL) {
        for (int i = 0; i < listaIML.size(); i++) { //Encontrar los enlaces a otros IML en los campos versión y añadir los documentos relacionados a la lista
            if (!listaL.contains(listaIML.get(i))) {
                listaNL.add(listaIML.get(i));
            }
        }
        listaIML.clear();
    }

    //////// MÉTODOS PARA ACCEDER A LA INFORMACIÓN DEPENDIENDO DE LA PANTALLA EN LA QUE SE ESTÉ
///////////////////////////////////////////////////////////////////////////////////
///////////////// CONSULTA 1
///////////////////////////////////////////////////////////////////////////////////
//DEVUELVE EL NOMBRE DE TODOS LOS INTÉRPRETES
    public static LinkedList<String> getNombreInterpretes() throws XPathExpressionException {
        LinkedList<String> interpretes = new LinkedList<String>();
        XPathExpression expr = xpath.compile("/Interprete/Nombre/NombreC/text() | /Interprete/Nombre/NombreG/text()");
        for (int i = 0; i < listaInterpretes.size(); i++) {
            Document doc = listaInterpretes.get(i);
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int j = 0; j < nodes.getLength(); j++)
                if (!interpretes.contains(nodes.item(j).getNodeValue())) {
                    interpretes.add(nodes.item(j).getNodeValue());
                }
        }
        return interpretes;
    }

    //DEVUELVE LOS NOMBRES DE LOS ÁLBUMS DEL INTERPRETE SELECCIONADO (O TODOS)
    public static LinkedList<String> getAlbumsInterprete(String interprete) throws XPathExpressionException {
        LinkedList<String> albums = new LinkedList<String>();
        XPathExpression exprAlbum;
        if (interprete.equalsIgnoreCase("todos")) {
            exprAlbum = xpath.compile("/Interprete/Album/NombreA/text()");
        } else {
            exprAlbum = xpath.compile("/Interprete[Nombre/NombreC='" + interprete + "' or Nombre/NombreG='" + interprete + "']/Album/NombreA/text()");
        }
        for (int i = 0; i < listaInterpretes.size(); i++) {
            NodeList nodesA = (NodeList) exprAlbum.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);
            for (int j = 0; j < nodesA.getLength(); j++) {
                if (!albums.contains(nodesA.item(j).getTextContent())) {
                    albums.add(nodesA.item(j).getTextContent());
                }
            }
        }
        Collections.sort(albums);
        return albums;
    }

    //DEVUELVE LAS CANCIONES DE LOS ÁLBUMS DEL INTERPRETE SELECCIONADO (O TODAS)
    public static LinkedList<String> getCancionesAlbum(String interprete, String album) throws XPathExpressionException {
        LinkedList<String> canciones = new LinkedList<String>();
        XPathExpression exprCancion;
        XPathExpression exprDur;
        XPathExpression exprDesc;
        if (interprete.equalsIgnoreCase("todos")) {
            if (album.equalsIgnoreCase("todos")) {
                exprCancion = xpath.compile("/Interprete/Album/Cancion/NombreT");
                exprDur = xpath.compile("/Interprete/Album/Cancion/Duracion");
            } else {
                exprCancion = xpath.compile("/Interprete/Album[NombreA='" + album + "']/Cancion/NombreT");
                exprDur = xpath.compile("/Interprete/Album[NombreA='" + album + "']/Cancion/Duracion");
            }
        } else {
            if (album.equalsIgnoreCase("todos")) {
                exprCancion = xpath.compile("/Interprete[Nombre/NombreC='" + interprete + "' or Nombre/NombreG='" + interprete + "']/Album/Cancion/NombreT");
                exprDur = xpath.compile("/Interprete[Nombre/NombreC='" + interprete + "' or Nombre/NombreG='" + interprete + "']/Album/Cancion/Duracion");
            } else {
                exprCancion = xpath.compile("/Interprete[Nombre/NombreC='" + interprete + "' or Nombre/NombreG='" + interprete + "']/Album[NombreA='" + album + "']/Cancion/NombreT");
                exprDur = xpath.compile("/Interprete[Nombre/NombreC='" + interprete + "' or Nombre/NombreG='" + interprete + "']/Album[NombreA='" + album + "']/Cancion/Duracion");
            }
        }
        for (int i = 0; i < listaInterpretes.size(); i++) {
            NodeList nodesC = (NodeList) exprCancion.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);
            NodeList nodesD = (NodeList) exprDur.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);
            for (int k = 0; k < nodesC.getLength(); k++) {

                NodeList nodesDesc = (NodeList) xpath.evaluate("../text()", nodesC.item(k), XPathConstants.NODESET);
                String descripcion = "";

                for (int j = 0; j < nodesDesc.getLength(); j++) {
                    descripcion = descripcion + nodesDesc.item(j).getTextContent().trim();
                }
                if (!canciones.contains(nodesC.item(k).getTextContent())) {
                    String nombreDurDesc = ("" + nodesC.item(k).getTextContent() + " (" + nodesD.item(k).getTextContent() + ", " + descripcion + ")");
                    canciones.add(nombreDurDesc);
                }
            }
        }
        return canciones;
    }


    ///////////////////////////////////////////////////////////////////////////////////
/////////////// CONSULTA 2
///////////////////////////////////////////////////////////////////////////////////
//DEVUELVE LOS DIFERENTES AÑOS DE LOS QUE HAY CANCIONES
    public static LinkedList<String> getAnhos() throws XPathExpressionException {
        LinkedList<String> anhos = new LinkedList<String>();
        XPathExpression expr = xpath.compile("/Interprete/Album/Año/text()");
        for (int i = 0; i < listaInterpretes.size(); i++) {
            NodeList nodes = (NodeList) expr.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);
            for (int j = 0; j < nodes.getLength(); j++)
                if (!anhos.contains(nodes.item(j).getNodeValue())) {
                    anhos.add(nodes.item(j).getNodeValue());
                }
        }
        Collections.sort(anhos);
        return anhos;
    }

    //DEVUELVE LOS NOMBRES DE LOS ÁLBUMS DEL AÑO SELECCIONADO (O TODOS)
    public static LinkedList<String> getAlbumsAnhos(String anho) throws XPathExpressionException {
        LinkedList<String> albums = new LinkedList<String>();
        XPathExpression exprAlbum;
        if (anho.equalsIgnoreCase("todos")) {
            exprAlbum = xpath.compile("/Interprete/Album/NombreA/text()");
        } else {
            exprAlbum = xpath.compile("/Interprete/Album[Año='" + anho + "']/NombreA/text()");
        }
        for (int i = 0; i < listaInterpretes.size(); i++) {
            NodeList nodesAlbum = (NodeList) exprAlbum.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);
            for (int j = 0; j < nodesAlbum.getLength(); j++) {
                if (!albums.contains(nodesAlbum.item(j).getTextContent())) {
                    albums.add(nodesAlbum.item(j).getTextContent());
                }
            }
        }
        Collections.sort(albums);
        return albums;
    }

    //DEVUELVE LOS ESTILOS DE LAS CANCIONES QUE PERTENECEN AL ÁLBUM DEL AÑO SELECCIONADO (O TODOS)
    public static LinkedList<String> getEstilos(String anho, String album) throws XPathExpressionException {
        LinkedList<String> estilos = new LinkedList<String>();
        XPathExpression exprEstilo;
        if (anho.equalsIgnoreCase("todos")) {
            if (album.equalsIgnoreCase("todos")) {
                exprEstilo = xpath.compile("/Interprete/Album/Cancion/@estilo");
            } else {
                exprEstilo = xpath.compile("/Interprete/Album[NombreA='" + album + "']/Cancion/@estilo");
            }
        } else {
            if (album.equalsIgnoreCase("todos")) {
                exprEstilo = xpath.compile("/Interprete/Album[Año='" + anho + "']/Cancion/@estilo");
            } else {
                exprEstilo = xpath.compile("Interprete/Album[NombreA='" + album + "' and Año='" + anho + "']/Cancion/@estilo");
            }
        }
        for (int i = 0; i < listaInterpretes.size(); i++) {
            NodeList nodesEstilo = (NodeList) exprEstilo.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);
            for (int k = 0; k < nodesEstilo.getLength(); k++) {
                if (!estilos.contains(nodesEstilo.item(k).getTextContent())) {
                    estilos.add(nodesEstilo.item(k).getTextContent());
                }
            }
        }
        Collections.sort(estilos);
        return estilos;
    }

    //DEVUELVE EL NÚMERO DE CANCIONES DE UN ESTILO
    public static int getNumCancionesEstilo(String anho, String album, String estilo) throws XPathExpressionException {
        int cantidad = 0;
        XPathExpression exprEstilo;
        if (estilo.equalsIgnoreCase("todos")) {
            if (anho.equalsIgnoreCase("todos")) {
                if (album.equalsIgnoreCase("todos")) {
                    exprEstilo = xpath.compile("/Interprete/Album/Cancion/@estilo");
                } else {
                    exprEstilo = xpath.compile("/Interprete/Album[NombreA='" + album + "']/Cancion/@estilo");
                }
            } else {
                if (album.equalsIgnoreCase("todos")) {
                    exprEstilo = xpath.compile("/Interprete/Album[Año='" + anho + "']/Cancion/@estilo");
                } else {
                    exprEstilo = xpath.compile("Interprete/Album[NombreA='" + album + "' and Año='" + anho + "']/Cancion/@estilo");
                }
            }
        } else {
            if (anho.equalsIgnoreCase("todos")) {
                if (album.equalsIgnoreCase("todos")) {
                    exprEstilo = xpath.compile("/Interprete/Album/Cancion[@estilo='" + estilo + "']");
                } else {
                    exprEstilo = xpath.compile("/Interprete/Album[NombreA='" + album + "']/Cancion[@estilo='" + estilo + "']");
                }
            } else {
                if (album.equalsIgnoreCase("todos")) {
                    exprEstilo = xpath.compile("/Interprete/Album[Año='" + anho + "']/Cancion[@estilo='" + estilo + "']");
                } else {
                    exprEstilo = xpath.compile("Interprete/Album[NombreA='" + album + "' and Año='" + anho + "']/Cancion[@estilo='" + estilo + "']");
                }
            }
        }
        for (int i = 0; i < listaInterpretes.size(); i++) {
            NodeList nodesEstilo = (NodeList) exprEstilo.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);
            for (int k = 0; k < nodesEstilo.getLength(); k++) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public static class XML_DTD_ErrorHandler extends DefaultHandler {
        public boolean error;
        public boolean warning;
        public boolean fatalerror;
        public String message;

        public XML_DTD_ErrorHandler() {
            error = false;
            warning = false;
            fatalerror = false;
            message = null;
        }

        public void warning(SAXParseException spe) throws SAXException {
            warning = true;
            message = "Warning: " + spe.getMessage();
            throw new SAXException();
        }

        public void error(SAXParseException spe) throws SAXException {
            error = true;
            message = "Error: " + spe.getMessage();
            throw new SAXException();
        }

        public void fatalerror(SAXParseException spe) throws SAXException {
            fatalerror = true;
            message = "Fatal Error: " + spe.getMessage();
            throw new SAXException();
        }

        public boolean hasWarning() {
            return warning;
        }

        public boolean hasError() {
            return error;
        }

        public boolean hasFatalError() {
            return fatalerror;
        }

        public String getMessage() {
            return message;
        }

        public void clear() {
            warning = false;
            error = false;
            fatalerror = false;
            message = null;
        }
    }
}
