package es.iam.sbae.sbintopexterna.latest;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-09-06T12:10:10.803+02:00
 * Generated source version: 3.1.12
 * 
 */
public final class InteroperabilidadExterna_InteroperabilidadExternaPort_Client {

	private static final QName SERVICE_NAME = new QName("http://sbintopexterna.sbae.iam.es", "InteroperabilidadExternaService");

	private InteroperabilidadExterna_InteroperabilidadExternaPort_Client() {
	}

	public static void main(String args[]) throws java.lang.Exception {
		URL wsdlURL = InteroperabilidadExternaService.WSDL_LOCATION;
		if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
			File wsdlFile = new File(args[0]);
			try {
				if (wsdlFile.exists()) {
					wsdlURL = wsdlFile.toURI().toURL();
				} else {
					wsdlURL = new URL(args[0]);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		InteroperabilidadExternaService ss = new InteroperabilidadExternaService(wsdlURL, SERVICE_NAME);
		InteroperabilidadExterna port = ss.getInteroperabilidadExternaPort();

		{
		System.out.println("Invoking realizaPeticion...");
		es.iam.sbae.sbintopexterna.latest.PeticionExterna _realizaPeticion_peticionExterna = new es.iam.sbae.sbintopexterna.latest.PeticionExterna();
		org.w3c.dom.Element _realizaPeticion_peticionExternaAny = null;
		_realizaPeticion_peticionExterna.setAny(_realizaPeticion_peticionExternaAny);
		es.iam.sbae.sbintopexterna.latest.RespuestaPeticion _realizaPeticion__return = port.realizaPeticion(_realizaPeticion_peticionExterna);
		System.out.println("realizaPeticion.result=" + _realizaPeticion__return);

		}

		System.exit(0);
	}

}