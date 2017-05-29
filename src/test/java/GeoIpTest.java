import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;
import geoipservice.GeoIP;
import geoipservice.GeoIPService;
import geoipservice.GeoIPServiceSoap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.JaxWsHandlerResolver;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class GeoIpTest {

    private String googleIp = "8.8.8.8";
    private String country = "United States";
    private GeoIPService geoIPService;
    private GeoIPServiceSoap geoIPServiceSoap;

    @BeforeClass
    public void setUp() {
        geoIPService = new GeoIPService();
        geoIPServiceSoap = geoIPService.getGeoIPServiceSoap();
        geoIPService.setHandlerResolver(new JaxWsHandlerResolver());
    }

    @Test
    public void geoIpTestPositive() {
        GeoIP geoIP = geoIPServiceSoap.getGeoIP(googleIp);
        assertEquals(country, geoIP.getCountryName());
    }

    @Test
    public void geoIpTestNegativeInjection() {
        GeoIP geoIP = geoIPServiceSoap.getGeoIP(googleIp, "google"); //Bug
        assertEquals(country, geoIP.getCountryName());
    }

    //    @Test(expectedExceptions = ServerSOAPFaultException.class)
    @Test
    public void geoIpTestNegativeNull() {
        GeoIPServiceSoap geoIPServiceSoap = geoIPService.getGeoIPServiceSoap();
        try {
            geoIPServiceSoap.getGeoIP(null);
        } catch (ServerSOAPFaultException exception) {
            assertTrue(exception.getMessage().contains("Value cannot be null"));
        }
    }

    @Test
    public void geoIpTestNegativeInvalidIp() {
        GeoIP geoIP = geoIPServiceSoap.getGeoIP(""); //Bug
        assertEquals(0, geoIP.getReturnCode());
        assertEquals("", geoIP.getIP());
        assertEquals("Invalid IP address", geoIP.getReturnCodeDetails());
    }
}
