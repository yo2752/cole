package trafico.transmision.justificantesPro;

import java.math.BigDecimal;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.comun.BeanDatosFiscales;
import org.gestoresmadrid.utilidades.components.Utiles;

import com.gescogroup.blackbox.ProfessionalProofResponse;
import com.gescogroup.blackbox.ProfessionalProofVerificationResponse;

import trafico.utiles.UtilesWSMate;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import ws.StubFactory;

/**
 * Clase que genera la Solicitud de Matriculaci�n de los tr�mites que se quieren
 * presentar en la DGT
 * 
 * @author C�sar S�nchez
 * 
 */
public class DGTSolicitudProfessionalProof {
	
	

	private static final ILoggerOegam log = LoggerOegam.getLogger(DGTSolicitudProfessionalProof.class);

	/**
	 * Realiza la emisi�n de un Justificante Profesional a partir de los datos que se
	 * env�an a SEA, tales como titular, veh�culo, entre otros (Consultar la documentaci�n).
	 * @param numExpediente 
	 * 
	 * @param peticionXML, datosEitvBean
	 *            La petici�n que se va a enviar y los datos necesarios para la validaci�n en el WS de SEA
	 * @return Devuelve el Justificante Profesional
	 *         {@link ProfessionalProofResponse}
	 * @throws Exception 
	 * @throws OegamExcepcion 
	 */
	
	
	public static ProfessionalProofResponse presentarDGTIssueProfessionalProof(String peticionXML,
			BeanDatosFiscales datosFiscales, BigDecimal numExpediente) throws Exception, OegamExcepcion,java.net.ConnectException {
			
			
			log.info("presentarDGTIssueProfessionalProof: agencyFiscalId: " + datosFiscales.getAgencyfiscalld());
			
			
			/*
			 * externalSystemFiscalId CIF de la plataforma que realiza la
			 * conexi�n a la Caja Negra Es el que se usa para la firma en los
			 * PDF. Se obtendr� de TraficoTramiteBean.getNumExpediente()
			 */
			
			String externalSystemFiscalId = datosFiscales.getExternalSystemFiscall(); 
			
			log.info("presentarDGTIssueProfessionalProof: externalSystemFiscalId: " + externalSystemFiscalId);

			 /** xmlB64 XML en Base 64 con los datos necesarios para matricular el
			 * veh�culo a la DGT, tambi�n denominada Solicitud de registro de
			 * entrada.
			 */
			 
			 log.debug("LOG_ISSUE_PROFESSIONAL PETICION XML ="+peticionXML);
			 String xmlObjB64 = null;
			 ProfessionalProofResponse respuestaProfessionalProof = new ProfessionalProofResponse();
//			 xmlObjB64 = UtilesRegistradores.doBase64Encode(peticionXML.getBytes("UTF-8"));
			 xmlObjB64 = ContextoSpring.getInstance().getBean(Utiles.class).doBase64Encode(peticionXML.getBytes("UTF-8"));
			
			try {
				new UtilesWSMate().cargarAlmacenesTrafico();

				respuestaProfessionalProof = StubFactory.getStubJustificantes(numExpediente).issueProfessionalProof(
						datosFiscales.getExternalSystemFiscall(), datosFiscales.getExternalSystemFiscall(), xmlObjB64);
				log.info(" ");
				log.info("------LOG_ISSUE_PROFESSIONAL: RECIBIDA RESPUESTA DEL WEBSERVICE-----");
				

				/*
				 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX:
				 * dossierNumber (String): N�mero que identifica el tr�mite
				 * dentro de la aplicaci�n Black Box. codeHG (String): C�digo de
				 * homologaci�n de gestores que sirve para verificar la validez
				 * del n�mero de expediente. dgtResponse (String): Resultado
				 * �ntegro del tr�mite MATEGE que la DGT devuelve. errorCodes
				 * (List): Lista de parejas de c�digo de error y valor para la
				 * petici�n tramitada. plateNumber (String): Matr�cula asignada
				 * al veh�culo en caso de que el proceso de matriculaci�n
				 * finaliza correctamente.
				 */

				log.info(respuestaProfessionalProof.getProofNumber());
				log.info(respuestaProfessionalProof.getProfessionalProofPdf());
				log.info(respuestaProfessionalProof.getErrors());

			} catch (Exception e) {
				log.error("LOG_ISSUE_PROFESSIONAL: EXCEPCION: Traza: ", e, numExpediente.toString());
				throw e;
			}
			log.info("LOG_ISSUE_PROFESSIONAL: SALE DE ISSUEPROFESSIONALPROOF");
			
			return respuestaProfessionalProof;
	}


	/**
	 * Realiza la verificaci�n de un Justificante Profesional a partir del n�mero de seguridad
	 * y recibe como resultado, si ese Justificante ha sido emitido por SEA o no.
	 * 
	 * @param peticion
	 *            String con el CSV (C�digo Seguro de Verificaci�n) del Justificante
	 *            Profesional
	 * @return Devuelve el resultado de la validaci�n
	 *         {@link ProfessionalProofVerificationResponse}
	 * @throws Exception 
	 * @throws OegamExcepcion 
	 */
	
	public static ProfessionalProofVerificationResponse presentarDGTVerifyProfessionalProof(String peticion) throws Exception, OegamExcepcion,java.net.ConnectException {

			log.info("LOG_VERIFY_PROFESSIONAL: ENTRA EN BLACK BOX");
			
			 ProfessionalProofVerificationResponse respuestaProfessionalProof = new ProfessionalProofVerificationResponse();
			
			try {
				new UtilesWSMate().cargarAlmacenesTrafico();

				respuestaProfessionalProof = StubFactory.getStubJustificantesVerificacion().verifyProfessionalProof(peticion);
				log.info(" ");
				log.info("LOG_VERIFY_PROFESSIONAL: RECIBIDA RESPUESTA DEL WEBSERVICE-----");
				

				/*
				 * VALORES DEVUELTOS EN LA RESPUESTA AL SERVICIO BLACK BOX:
				 */ 

				log.info("V�lido: " + respuestaProfessionalProof.isVerificationSuccessful());
				log.info(respuestaProfessionalProof.getErrors());

			} catch (Exception e) {
				log.error("LOG_VERIFY_PROFESSIONAL: EXCEPCION: Traza: ", e);
				throw e;
			}
			log.info("LOG_VERIFY_PROFESSIONAL: SALE DE ISSUEPROFESSIONALPROOF");
			
			return respuestaProfessionalProof;
	}
	
}
