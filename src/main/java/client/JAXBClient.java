package client;

import geoipservice.GeoIPServiceSoap;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class JAXBClient {

    public GeoIPServiceSoap geoIPServiceSoap;

    public JAXBClient() {
        try {
            URL url = new URL("http://www.webservicex.net/geoipservice.asmx?WSDL");

            //1st argument service URI, refer to wsdl document above
            //2nd argument is service name, refer to wsdl document above
            QName qname = new QName("http://www.webservicex.net/", "GeoIPService");

            Service service = Service.create(url, qname);

            geoIPServiceSoap = service.getPort(GeoIPServiceSoap.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
