package no.kodeklubben.trondheim;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 *
 * Enkel klasse for å laste ned et værvarsel fra Yr.no og skrive ut på standard out
 * Mer informasjon om gratis værdata fra Yr.no: http://om.yr.no/verdata/xml/
 *
 * @author Frode Lundgren <frodelu@yahoo-inc.com>
 *
 */
public class YrForecast {
    final String serviceBaseURL = "http://www.yr.no/sted/";
    final XPath xpath = XPathFactory.newInstance().newXPath();
    DocumentBuilder docBuilder = null;

    /**
     * Nødvendig standardkode for å sette opp en XML-parser
     * @return En document builder som brukes til XML parsing
     */
    private DocumentBuilder setupDocumentBuilder() {
        // Setter opp Javas DOM XML parser
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println("Fikk ikke laget XML-parser - avslutter...");
        }

        return docBuilder;
    }

    /**
     * Evaluerer og returnerer XPath-uttrykket mot parset XML
     *
     * @param xmlDocument XML-dokumentet du parser fra
     * @param xPathExpression XPath-uttrykket du ønsker å evaluere
     * @param returnType Returtypen du vil ha tilbake, typisk XPathConstants.STRING eller XPathConstants.NODESET
     * @return Resultatet av XPath-evalueringen som String eller NodeList avhengig av returnType. Returerer null ved feil.
     */
    private Object getXPathValue(Document xmlDocument, String xPathExpression, QName returnType) {

        // Evaluerer og returnerer XPath-uttrykket mot parset XML
        XPathExpression expr;
        try {
            expr = xpath.compile(xPathExpression);
            return expr.evaluate(xmlDocument, returnType);
        } catch (XPathExpressionException e) {
            System.err.println("Ugyldig XPath uttrykk - avslutter...");
            return null;
        }
    }

    private String getXPathStringValue(Document xmlDocument, String xPathExpression) {
        return (String) getXPathValue(xmlDocument, xPathExpression, XPathConstants.STRING);
    }

    private NodeList getXPathNodeList(Document xmlDocument, String xPathExpression) {
        return (NodeList) getXPathValue(xmlDocument, xPathExpression, XPathConstants.NODESET);
    }

    /**
     * Skriver ut værvarsel for oppgitt sted på standard output
     *
     * @param country Land, f.eks. "Norge"
     * @param county Fylke, f.eks. "Sør-Trøndelag"
     * @param municipality Kommune, f.eks. "Trondheim"
     * @param place Sted, f.eks. "Byåsen"
     */
    public void printForecast(String country, String county, String municipality, String place) {
        String requestUrlString = serviceBaseURL + country + "/" + county + "/" +
                                  municipality + "/" + place + "/forecast.xml";

        // Sett opp docBuilder om den ikke allerede er satt opp
        if (docBuilder == null) {
            docBuilder = setupDocumentBuilder();
            if (docBuilder == null) {
                return;
            }
        }

        // Laster ned og parser XML
        Document doc;
        try {
            doc = docBuilder.parse(requestUrlString);
        } catch (SAXException |IOException e) {
            System.err.println("Klarte ikke parse XML - avslutter...");
            return;
        }

        // Skriv ut temperatur akkurat nå (første timesvarsel)
        String temperature = getXPathStringValue(doc, "/weatherdata/observations/weatherstation[1]/temperature/@value");
        String unit = getXPathStringValue(doc, "/weatherdata/observations/weatherstation[1]/temperature/@unit");
        System.out.println("Temperatur nå er: " + temperature + " grader " + unit + "\n");

        // Skriv ut temperaturer for det neste døgnet:
        System.out.println("Temperaturer de neste dagene:");
        int numForeCasts = getXPathNodeList(doc, "/weatherdata/forecast/tabular/time").getLength();
        for (int i = 1; i <= numForeCasts; i++) {
            temperature =
                getXPathStringValue(doc, "/weatherdata/forecast/tabular/time[" + i + "]/temperature/@value");
            unit =
                getXPathStringValue(doc, "/weatherdata/forecast/tabular/time[" + i + "]/temperature/@unit");
            String fromTime =
                getXPathStringValue(doc, "/weatherdata/forecast/tabular/time[" + i + "]/@from");
            String toTime =
                getXPathStringValue(doc, "/weatherdata/forecast/tabular/time[" + i + "]/@to");

            System.out.println("   Fra " + fromTime + " til " + toTime + ": " + temperature + " grader " + unit);

        }
    }

    public static void main(String[] args) {
        YrForecast yr = new YrForecast();
        yr.printForecast("Norge", "Sør-Trøndelag", "Trondheim", "Byåsen");
    }
}
