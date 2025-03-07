package trafico.matriculacion.electronicMate;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.Utiles;

import com.gescogroup.blackbox.ElectronicMATERequest;
import com.gescogroup.blackbox.ElectronicMATEResponse;
import com.gescogroup.blackbox.Form06ExcemptionType;
import com.gescogroup.blackbox.MateError;

import es.dgt.www.vehiculos.esquema.RespuestaMatriculacion;
import es.dgt.www.vehiculos.esquema.RespuestaMatriculacionInfoMatricula;
import es.dgt.www.vehiculos.esquema.RespuestaMatriculacionListadoErroresError;
import es.dgt.www.vehiculos.esquema.RespuestaMatriculacionResultado;
import trafico.beans.DatosCardMateBean;
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
public class DGTSolicitudElectronicMate {
	
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(DGTSolicitudElectronicMate.class);

	/**
	 * Realiza la presentaci�n del registro de matriculaci�n que recibe como
	 * par�metros en el servicio MATEGE de la DGT y devuelve la respuesta que
	 * recibe como resultado.
	 * 
	 * @param peticionXML
	 *            String con formato XML q contiene la informaci�n del registro
	 *            de matriculacion a presentar
	 * @return Devuelve el resultado de la matriculaci�n que es un objeto
	 *         {@link RespuestaMatriculacion}
	 * @throws Exception 
	 * @throws Exception
	 * @throws OegamExcepcion 
	 */
	public static RespuestaMatriculacion presentarDGTElectronicMate(String peticionXML,
			DatosCardMateBean datosCardMateBean) throws Exception, OegamExcepcion {

			log.info("GTMS2011_ELECTRONIC_MATE: ENTRA EN BLACK BOX");
			
			ElectronicMATERequest req = componerRequest(peticionXML, datosCardMateBean);
			
			try {
				new UtilesWSMate().cargarAlmacenesTrafico();
				ElectronicMATEResponse electronicMateResponse = StubFactory.getStubMateElectronic().sendElectronicMATE(req);
				log.info(" ");
				log.info("------GTMS2011_ELECTRONIC_MATE: RECIBIDA RESPUESTA DEL WEBSERVICE-----");
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
				return  extraerRespuestaWS(electronicMateResponse);
				} 
			catch (Exception e) 
				{
				log.error("GTMS2011_ELECTRONIC_MATE: EXCEPCION: Traza: ", e);
				throw e;
				}
	}







	private static ElectronicMATERequest componerRequest(String peticionXML,	DatosCardMateBean datosCardMateBean) throws UnsupportedEncodingException {
		ElectronicMATERequest req = new ElectronicMATERequest();

		//req.setForm06ExcemptionValue(form06ExcemptionValue); 
		/*
		 * agencyFiscalId CIF de la gestor�a que tramita el expediente.
		*/
		
		req.setAgencyFiscalId(datosCardMateBean.getAgencyFiscalId());
		log.info("GTMS2011_ELECTRONIC_MATE: agencyFiscalId: " + datosCardMateBean.getAgencyFiscalId());

		/*
		 * customDossierNumber N�mero de expediente en la plataforma origen.
		 * Se obtendr� de TraficoTramiteBean.getNumExpediente()
		 */
		
		String customDossierNumber = datosCardMateBean.getCustomDossierNumber(); 
		
		req.setCustomDossierNumber(customDossierNumber);


		log.info("GTMS2011_ELECTRONIC_MATE: customDossierNumber: "
				+ req.getCustomDossierNumber());

		/*
		 * externalSystemFiscalId CIF de la plataforma que realiza la
		 * conexi�n a la Caja Negra Es el que se usa para la firma en los
		 * PDF. Se obtendr� de TraficoTramiteBean.getNumExpediente()
		 */

		String externalSystemFiscalId = datosCardMateBean.getExternalSystemFiscalID(); 
		

		req.setExternalSystemFiscalId(externalSystemFiscalId);
		
		log.info("GTMS2011_ELECTRONIC_MATE: externalSystemFiscalId: "
				+ externalSystemFiscalId);

		/*
		 * firstMatriculationDate Fecha de primera matriculaci�n del
		 * veh�culo. Matriculaci�n -> Pesta�a: Veh�culo, �rea: Veh�culo
		 * usado, Campo: Fecha 1� matriculaci�n
		 */

		Calendar firstMatriculationDate = Calendar.getInstance();
		String fecha; 
			fecha = datosCardMateBean.getFirstMatriculationDate();			  
		String anno = "";
		String mes = "";
		String dia = "";

		if (null != fecha && fecha.length() >= 8) {
			fecha = fecha.trim();
			dia = fecha.substring(0, 2);
			mes = fecha.substring(3, 5);
			anno = fecha.substring(6, 10);

			firstMatriculationDate.set(Integer.parseInt(anno), Integer
					.parseInt(mes), Integer.parseInt(dia));
		} else {
			firstMatriculationDate = null;
		}

		log.info("GTMS2011_ELECTRONIC_MATE: firstMatriculationDate: " + anno + "-"
				+ mes + "-" + dia);
		req.setFirstMatriculationDate(null);//copiado de la antigua. 

		/*
		 * fiscalRepresentantBirthDate Fecha de nacimiento del representante
		 * legal del titular del veh�culo a matricular en caso de que
		 * exista. No disponemos actualmente de ese dato. S�lo tenemos la
		 * fecha de nacimiento del titular.
		 */
		Calendar fiscalRepresentantBirthDate = null;
		req.setFiscalRepresentantBirthDate(fiscalRepresentantBirthDate);
		log.info("GTMS2011_ELECTRONIC_MATE: fiscalRepresentantBirthDate: " + fiscalRepresentantBirthDate); 
		req.setFiscalRepresentantBirthDate(null);
		
		/*
		 * hasForm05 Indica si el expediente tiene un 05 asociado.
		 * Matriculaci�n -> Pesta�a: 576, 05 y 06, Campo: Reducci�n, no
		 * sujecci�n o exenci�n solicitada (05)
		 */

		Boolean hasForm05 = true;
		String form05Key = datosCardMateBean.getForm05Key();  
		if (null == form05Key || form05Key.trim().length() == 0) {

			form05Key = null;
			hasForm05 = false;
		}

		/*
		 * form05Key Indica la Clave 05 que se informa en caso de que el
		 * expediente tenga un 05 asociado (es decir, si hasForm05 = true).
		 */
		log.info("GTMS2011_ELECTRONIC_MATE: hasForm05: " + hasForm05);
		req.setHasForm05(hasForm05);
		log.info("GTMS2011_ELECTRONIC_MATE: form05Key: " + form05Key);
		req.setForm05Key(form05Key);

		/*
		 * hasForm06 Indica si el expediente tiene un 06 asociado.
		 * Matriculaci�n -> Pesta�a: 576, 05 y 06, Campo: No sujecci�n o
		 * exenci�n solicitada (06)
		 */
		
		Boolean hasForm06 = true;
		String form06ExcemptionValue = datosCardMateBean.getForm06ExcemptionValue(); 

		/*
		 * form06ExcemptionValue Valor de exenci�n 06 que se informa en caso
		 * de que el expediente tenga un 06 asociado. Matriculaci�n ->
		 * Pesta�a: 576, 05 y 06, Campo: No sujecci�n o exenci�n solicitada
		 * (06)
		 */
		
		if (null == form06ExcemptionValue
				|| form06ExcemptionValue.trim().length() == 0) {

			form06ExcemptionValue = null;
			hasForm06 = false;
		}

		/*
		 * form06ExcemptionType Clave de exenci�n 06 que se informa en caso
		 * de que el expediente tenga un 06 asociado.
		 */

		String form06ExcemptionType = null;

		if (hasForm06) {

			form06ExcemptionType = datosCardMateBean.getForm06ExcemptionKey(); 
			
			if (null == form06ExcemptionValue
					|| form06ExcemptionValue.trim().length() == 0
					|| form06ExcemptionValue.equalsIgnoreCase("NO HAY")) 
				{
				form06ExcemptionType = Form06ExcemptionType.NOSUBJECT.getValue();
				}
			else {
				form06ExcemptionType = Form06ExcemptionType.EXCEMPTION.getValue();
				}
		}
		log.info("GTMS2011_ELECTRONIC_MATE: hasForm06: " + hasForm06);
		log.info("GTMS2011_ELECTRONIC_MATE: form06ExcemptionValue: "
				+ form06ExcemptionValue);
		log.info("GTMS2011_ELECTRONIC_MATE: form06ExcemptionType: "
				+ form06ExcemptionType);

		req.setHasForm06(hasForm06);
		req.setForm06ExcemptionValue(form06ExcemptionValue);
		req.setForm06ExcemptionType(form06ExcemptionType);

		/*
		 * hasForm576 Indica si el expediente tiene un 576 asociado.
		 * Matriculaci�n -> Pesta�a: 576, 05 y 06, �rea: Datos de Pago del
		 * Modelo 576, Campo: Exento o cuota 0, Campo: NRC Si la casilla
		 * Exento o cuota 0 est� marcada o si NRC tiene valor, entonces
		 * hasForm576=true
		 */

		Boolean hasForm576 = false;
		String exento_o_cuota0 = datosCardMateBean.getHasForm576();  
		String NRC = datosCardMateBean.getHasForm576();  

		if (exento_o_cuota0 != null
				&& (exento_o_cuota0.equals("1") || exento_o_cuota0
						.equals("true"))) {
			if (hasForm06) {
				hasForm576 = false;
			} else {
				hasForm576 = true;
			}
		}

		if (NRC != null && NRC.trim().length() == 0) {
			hasForm576 = true;
		}

		log.info("GTMS2011_ELECTRONIC_MATE: hasForm576: " + hasForm576);
		req.setHasForm576(hasForm576);

		/*
		 * itvCardType Tipo de tarjeta ITV. Matriculaci�n -> Pesta�a:
		 * Veh�culo, Campo: Tipo de tarjeta ITV
		 */

		String itvCardType = datosCardMateBean.getItvCardType();  
		log.info("GTMS2011_ELECTRONIC_MATE: itvCardType: " + itvCardType);
		req.setItvCardType(itvCardType);

		/*
		 * kmUsed Kil�metros del veh�culo. Matriculaci�n -> Pesta�a:
		 * Veh�culo, Campo: N� de kil�metros de uso
		 */

		String strKmUsed = datosCardMateBean.getKmUsed();  
		Integer kmUsed = null;
		try {
			kmUsed = Integer.parseInt(strKmUsed);
			}
		catch (Throwable e) {
			kmUsed = null;
			}
		
		log.info("GTMS2011_ELECTRONIC_MATE: kmUsed: " + strKmUsed);

		req.setKmUsed(kmUsed);
		/*
		 * vehicleSerialNumber - Bastidor del veh�culo
		 */
		String vehicleSerialNumber = datosCardMateBean.getVehichleSerialNumber(); 
		if (null != vehicleSerialNumber) {
			log.info("GTMS2011_ELECTRONIC_MATE: vehicleSerialNumber: " + vehicleSerialNumber);
		}

		req.setSerialNumber(vehicleSerialNumber);

		/*
		 * vehicleSerialNumber - Bastidor del veh�culo
		 */
		String vehicleKind = datosCardMateBean.getVehicleKind(); 
		if (null != vehicleKind) {
			log.info("GTMS2011_ELECTRONIC_MATE: vehicleKind: " + vehicleKind);
		}
		req.setVehicleKind(vehicleKind);
		
		 /** xmlB64 XML en Base 64 con los datos necesarios para matricular el
		 * veh�culo a la DGT, tambi�n denominada Solicitud de registro de
		 * entrada.
		 */
		 
		 log.debug("GTMS2010_MATEGE PETICION XML ="+peticionXML);
//		 String xmlObjB64 = UtilesRegistradores.doBase64Encode(peticionXML.getBytes("UTF-8"));
		 String xmlObjB64 = ContextoSpring.getInstance().getBean(Utiles.class).doBase64Encode(peticionXML.getBytes("UTF-8"));
		 log.info("GTMS2010_MATEGE BASE64Encoder.encode(peticionXML.getBytes(UTF-8)) =" +xmlObjB64);
		 req.setXmlB64(xmlObjB64);
		return req;
	}



	private static RespuestaMatriculacion extraerRespuestaWS(			
			ElectronicMATEResponse respuesta) throws Exception {
		
		log.info("GTMS2011_ELECTRONIC_MATE: customDossierNumber: "+ respuesta.getDossierNumber());
		RespuestaMatriculacionInfoMatricula respuestaMatriculacionInfoMatricula = new RespuestaMatriculacionInfoMatricula();
		RespuestaMatriculacionListadoErroresError[] respuestaMatriculacionListadoErrores = null;
		RespuestaMatriculacionResultado respuestaMatriculacionResultado = null;
		RespuestaMatriculacion respuestaMatriculacion = new RespuestaMatriculacion();
		String matricula = respuesta.getPlateNumber();
		log.info("GTMS2011_ELECTRONIC_MATE: matricula: " + matricula);

		respuestaMatriculacionInfoMatricula.setMatricula(matricula);
		respuestaMatriculacionInfoMatricula.setPC_Temporal_PDF(respuesta.getLicense());
		if (respuesta.getItvCard()!=null){
			respuestaMatriculacionInfoMatricula.setFicha_tecnica_PDF(respuesta.getItvCard());}

		MateError[] bbErrores = respuesta.getErrorCodes();

		if (null == bbErrores || bbErrores.length == 0)
			{
			respuestaMatriculacion.setListadoErrores(null);
			respuestaMatriculacionResultado = RespuestaMatriculacionResultado.OK;
			log.info("GTMS2011_ELECTRONIC_MATE: RESPUESTA SIN ERRORES");
			}
		else 
			{
			respuestaMatriculacionListadoErrores = new RespuestaMatriculacionListadoErroresError[bbErrores.length];
			for (int i = 0; i < bbErrores.length; i++) 
				{
				respuestaMatriculacionListadoErrores[i] = new RespuestaMatriculacionListadoErroresError();
				respuestaMatriculacionListadoErrores[i].setCodigo(bbErrores[i].getKey());
				respuestaMatriculacionListadoErrores[i].setDescripcion(bbErrores[i].getMessage());
//				log.error("lista de errores" + respuestaMatriculacionListadoErrores[i].getCodigo());
//				log.error("lista descrpcion de errores: "
//						+ respuestaMatriculacionListadoErrores[i].getDescripcion());
				}

			respuestaMatriculacionResultado = RespuestaMatriculacionResultado.ERROR;
			log.error("GTMS2011_ELECTRONIC_MATE: RESPUESTA CON ERRORES :" + respuestaMatriculacionResultado);
			}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		respuestaMatriculacionInfoMatricula.setFechaMatriculacion(sdf.format(new Date()));
		respuestaMatriculacion.setInfoMatricula(respuestaMatriculacionInfoMatricula);
		respuestaMatriculacion.getInfoMatricula().setMatricula(matricula);
//				respuestaMatriculacion.setCodeHG(respuesta.getCodeHG());
		respuestaMatriculacion.setNumeroExpedienteColegio(respuesta.getDossierNumber());
		respuestaMatriculacion.setListadoErrores(respuestaMatriculacionListadoErrores);
		respuestaMatriculacion.setResultado(respuestaMatriculacionResultado);
//		if (respuesta.getItvCard()!=null)
//		log.info("GTMS2011_ELECTRONIC_MATE: ItvCard" + UtilesRegistradores.doBase64Decode(respuesta.getItvCard(),"UTF-8"));
		log.info("GTMS2011_ELECTRONIC_MATE: SALE DE MATECARD");
		return respuestaMatriculacion;
	}

	
}
