package ws;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.gescogroup.blackbox.MATEServiceLocator;
import com.gescogroup.blackbox.MATEWS;
import com.gescogroup.blackbox.MATEWSBindingStub;
import com.gescogroup.blackbox.ProfessionalProofServiceLocator;
import com.gescogroup.blackbox.ProfessionalProofWS;
import com.gescogroup.blackbox.ProfessionalProofWSBindingStub;
import com.gescogroup.blackbox.ws.SoapJustificanteProfWSHandler;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * 
 * @author César Sánchez 
 * Debe leer archivos de properties de base de datos una sola vez, para generar objetos tipo Gestor para cada proyecto
 *
 */
public class StubFactory {

	//Objetos stub
	private static ProfessionalProofWSBindingStub stubJustificantes;

	//private static MATEWSBindingStub stubMate;

	private static MATEWSBindingStub stubMate;

//	private static PeticionSincronaSoapBindingStub stubIvtm;

	//Nombres de las propiedades de las URL

	private final static String TIMEOUT_PROP_MATE_ELECTRONIC = "webservice.mateElectronic.timeOut";

	private final static String TIMEOUT__PROP_JUSTIFICANTES = "webservice.justificantesPro.timeOut";

	//nombres de las propiedades para la inicializacion del stub
	private static final String WEBSERVICE_JUSTIFICANTES_PRO = "webservice.justificantesPro.url";

	private static final String WEBSERVICE_MATE_ELECTRONIC = "webservice.mateElectronic.url";

	//valores de timeout por defecto
	private final static int TIMEOUT_ELECTRONIC_MATE = 120000;

	private static final int TIMEOUT_JUSTIFICANTES_PRO = 120000;

	//atributos para cargar los valores de los properties
	private static final ILoggerOegam log = LoggerOegam.getLogger(StubFactory.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	//atributos Bean que almacenarán los valores de los properties.

/*
	public static StubFactory getInstance()  {
		log.trace("getInstance");
		if (instance == null)
			try {
				instance = new StubFactory();
			} catch (NamingException e) {
				log.error(e);
			} catch (OegamExcepcion e) {
				log.error(e);
			}
			log.trace("devolvemos instancia de StubFactory");
			return instance;
	}	*/

	public StubFactory() throws OegamExcepcion, NamingException {
		log.trace("creación StubFactory");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * 
	 * @param numExpediente 
	 * @return El stub de conexion con el webservice de Justificantes Profesionales.
	 * @throws OegamExcepcion
	 * @throws MalformedURLException
	 * @throws ServiceException
	 */
	public static ProfessionalProofWSBindingStub getStubJustificantes(BigDecimal numExpediente)throws OegamExcepcion, MalformedURLException, ServiceException {
		if (stubJustificantes==null){
			GestorPropiedades gestorPropiedades = ContextoSpring.getInstance().getBean(GestorPropiedades.class);
			String timeOut = gestorPropiedades.valorPropertie(TIMEOUT__PROP_JUSTIFICANTES);
			URL miUrl = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_JUSTIFICANTES_PRO));
			ProfessionalProofWS professionalProofWS = null;
			if(miUrl != null){
				ProfessionalProofServiceLocator proofServiceLocator = new ProfessionalProofServiceLocator();
				javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),proofServiceLocator.getProfessionalProofServiceWSDDServiceName());

				@SuppressWarnings("unchecked")
				List<HandlerInfo> list = proofServiceLocator.getHandlerRegistry().getHandlerChain(portName);
				HandlerInfo logHandlerInfo = new HandlerInfo();
				logHandlerInfo.setHandlerClass(SoapJustificanteProfWSHandler.class);

				Map<String, Object> filesConfig = new HashMap<String, Object>();
				filesConfig.put(SoapJustificanteProfWSHandler.PROPERTY_KEY_ID, numExpediente.toString());
				logHandlerInfo.setHandlerConfig(filesConfig);

				list.add(logHandlerInfo);

				professionalProofWS = proofServiceLocator.getProfessionalProofService(miUrl);
				stubJustificantes = (ProfessionalProofWSBindingStub) professionalProofWS;
				stubJustificantes.setTimeout(Integer.parseInt(timeOut));
			}
		}
		return stubJustificantes;
	}

	/**
	 * El stub de conexion con el webservice de Mate
	 * @return
	 * @throws OegamExcepcion
	 * @throws MalformedURLException
	 * @throws ServiceException
	 */
	public static MATEWSBindingStub getStubMateElectronic() throws OegamExcepcion,MalformedURLException, ServiceException {
		GestorPropiedades gestorPropiedades = ContextoSpring.getInstance().getBean(GestorPropiedades.class);
		String timeOut= gestorPropiedades.valorPropertie(TIMEOUT_PROP_MATE_ELECTRONIC); 
		URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_MATE_ELECTRONIC));

		if (null != miURL) {
			log.info("LOG_ELECTRONIC_MATE: miURL: " + miURL.toString());
		}

		MATEWS servicio = new MATEServiceLocator().getMATEService(miURL);

		log.info("LOG_ELECTRONIC_MATE: generado servicio.");

		stubMate  = (MATEWSBindingStub) servicio;

		log.info("LOG_ELECTRONIC_MATE: generado stub.");

		if (timeOut!=null && !"".equals(timeOut))
			stubMate.setTimeout(Integer.parseInt(timeOut));
		else stubMate.setTimeout(TIMEOUT_ELECTRONIC_MATE); 

		log.info("LOG_ELECTRONIC_MATE: timeout: "+TIMEOUT_ELECTRONIC_MATE);

		return stubMate;
	}

	public static ProfessionalProofWSBindingStub getStubJustificantesVerificacion() throws ServiceException, MalformedURLException {
		if (stubJustificantes==null) {
			GestorPropiedades gestorPropiedades = ContextoSpring.getInstance().getBean(GestorPropiedades.class);
			String timeOut = gestorPropiedades.valorPropertie(TIMEOUT__PROP_JUSTIFICANTES); 
			URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_JUSTIFICANTES_PRO));

			ProfessionalProofWS servicio = (ProfessionalProofWS) new ProfessionalProofServiceLocator().getProfessionalProofService(miURL);

			if (null != miURL) {
				log.info("LOG_JUSTIFICANTES_PROFESSIONAL: miURL: " + miURL.toString());
			}
			stubJustificantes = (ProfessionalProofWSBindingStub) servicio;

			log.info("LOG_JUSTIFICANTES_PROFESSIONAL: generado stubJustificantes.");

			stubJustificantes.setTimeout(Integer.parseInt(timeOut));

			if (timeOut!=null && !"".equals(timeOut))
				stubJustificantes.setTimeout(Integer.parseInt(timeOut));
			else
				stubJustificantes.setTimeout(TIMEOUT_JUSTIFICANTES_PRO);

			log.info("LOG_JUSTIFICANTES_PROFESSIONAL: timeout: "+TIMEOUT_JUSTIFICANTES_PRO );
		}

		return stubJustificantes;
	}

//	public static PeticionSincronaSoapBindingStub getStubIvtm() throws OegamExcepcion, MalformedURLException, ServiceException {
//		String timeOut= gestorPropiedades.valorPropertie(TIMEOUT_PROP_IVTM); 
//		URL miURL = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_IVTM));
//
//		log.info("LOG_2013_IVTM: Generando el servicio");
//
//		PeticionSincronaLocator servicio = new PeticionSincronaLocator();
//
//		log.info("LOG_2013_IVTM: Generando el Stub");
//
//		stubIvtm = (PeticionSincronaSoapBindingStub) servicio.getPeticionSincrona(miURL);
//
//		if(timeOut!=null && !"".equals(timeOut)){
//			stubIvtm.setTimeout(Integer.parseInt(timeOut));
//			log.info("LOG_IVTM: timeout (properties): "+timeOut);
//		}else{
//			stubIvtm.setTimeout(TIMEOUT_IVTM);
//			log.info("LOG_IVTM: timeout (default): "+TIMEOUT_IVTM);
//		}
//		return stubIvtm;
//	}
}