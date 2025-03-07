package trafico.beans.utiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.itextpdf.text.pdf.codec.Base64;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.DecisionTrafico;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.ga_trans_exportar.DATOS620;
import trafico.beans.jaxb.ga_trans_exportar.DATOSADQUIRIENTE;
import trafico.beans.jaxb.ga_trans_exportar.DATOSARRENDADOR;
import trafico.beans.jaxb.ga_trans_exportar.DATOSCOMPRAVENTA;
import trafico.beans.jaxb.ga_trans_exportar.DATOSLIMITACION;
import trafico.beans.jaxb.ga_trans_exportar.DATOSPRESENTACION;
import trafico.beans.jaxb.ga_trans_exportar.DATOSPRESENTADOR;
import trafico.beans.jaxb.ga_trans_exportar.DATOSTRANSMITENTE;
import trafico.beans.jaxb.ga_trans_exportar.DATOSVEHICULO;
import trafico.beans.jaxb.ga_trans_exportar.DIRECCIONDISTINTAADQUIRIENTE;
import trafico.beans.jaxb.ga_trans_exportar.FORMATOGA;
import trafico.beans.jaxb.ga_trans_exportar.TRANSMISION;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XMLTransmision {

	private static ILoggerOegam log = LoggerOegam.getLogger(XMLTransmision.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesConversiones utilesConversiones;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public XMLTransmision() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Método que con el dato del tipo FORMATOGA obtenga un FileOutputStream con el fichero XML generado para devolverlo formateado dentro de un ResultBean.
	 * @param formatoTransmision
	 * @return
	 * @throws FileNotFoundException
	 */
	public ResultBean FORMATOGAtoXML(FORMATOGA ga, String idSession) {
		ResultBean resultado = new ResultBean();
		// Clasificar objeto en archivo.
		String nodo = gestorPropiedades.valorPropertie(ConstantesGestorFicheros.RUTA_ARCHIVOS_TEMP);
		File salida = new File(nodo + "/temp/trans" + idSession + ".xml");
		try {
			// Crear clasificador
			Marshaller m = new XMLGAFactory().getTransExportacionContext().createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			m.marshal(ga, salida);
			byte[] bytes = utiles.getBytesFromFile(salida);
			resultado.addAttachment(ConstantesTrafico.BYTESXML, bytes);
		} catch (Exception e) {
			log.error(e);
			resultado.setError(true);
			resultado.setMensaje("Error al generar el fichero XML");
			return resultado;
		} finally {
			salida.delete();
		}
		return resultado;
	}

	/**
	 * Método que va a obtener un dato de tipo FORMATOGA de Transmisión con los datos formateados del listado de trámites de transmisión que le hayamos pasado. Devolverá null si se produce algún error
	 * @param listaTransmision
	 * @param xmlSesion
	 * @return
	 */
	public FORMATOGA convertirAFORMATOGA(List<TramiteTraficoTransmisionBean> listaTransmision, boolean xmlSesion) {
		FORMATOGA ga = instanciarCompleto();

		// Añado Cabecera utilesColegiado.getDetalleColegiado

		if (xmlSesion) {
			//Añado Cabecera
			ga.getCABECERA().getDATOSGESTORIA().setNIF(utilesColegiado.getNifUsuario());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(utilesColegiado.getApellidosNombreUsuario());
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(utilesColegiado.getNumColegiadoSession());
			String sigla = utilesConversiones.getSiglaProvinciaFromNombre(utilesConversiones.getNombreProvincia(utilesColegiado.getProvinciaDelContrato()));
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(sigla);
			ga.getCABECERA().getDATOSGESTORIA().setTIPODGT(Base64.encodeBytes(Constantes.ORIGEN_OEGAM.getBytes()));
		}
		// Si se importa el XML del contrato de la gestoría del trámite a exportar
		// Añado Cabecera
		else {
			ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
			ContratoDto contrato = servicioContrato.getContratoDto(listaTransmision.get(0).getTramiteTraficoBean().getIdContrato());

			ga.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
			ga.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
			ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(contrato.getColegiadoDto().getNumColegiado());
			String sigla = utilesConversiones.getSiglaProvinciaFromNombre(utilesConversiones.getNombreProvincia(contrato.getIdProvincia()));
			ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA(sigla);
			ga.setPlataforma("OEGAM");
		}

		List<TRANSMISION> lista = ga.getTRANSMISION();
		// Añado los trámites
		for (int i = 0; i < listaTransmision.size(); i++) {
			lista.add(convertirBeanToGa(listaTransmision.get(i)));
		}
		ga.setTransmision(lista);

		return ga;
	}

	/**
	 * Método que realizará una instanciación completa del FORMATOGA dejándolo preparado para su posterior utilización.
	 * @return FORMATOGA
	 */
	private FORMATOGA instanciarCompleto() {
		trafico.beans.jaxb.ga_trans_exportar.ObjectFactory factory = new trafico.beans.jaxb.ga_trans_exportar.ObjectFactory();
		FORMATOGA ga = factory.createFORMATOGA();

		// CABECERA
		ga.setCABECERA(factory.createCABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createDATOSGESTORIA());
		ga.getCABECERA().getDATOSGESTORIA().setNIF("");
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE("");
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL("");
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA("");

		ga.setFechaCreacion((new Date()).toString());

		ga.setTransmision(new ArrayList<TRANSMISION>());
		return ga;
	}

	/**
	 * Segunda parte de la instanciación, en la que instanciaremos una unidad de TRANSMISION que se corresponde con un trámite.
	 * @return TRANSMISION
	 */
	private TRANSMISION instanciarTRANSMISION() {
		trafico.beans.jaxb.ga_trans_exportar.ObjectFactory factory = new trafico.beans.jaxb.ga_trans_exportar.ObjectFactory();
		TRANSMISION trans = factory.createTRANSMISION();

		trans.setNUMERODOCUMENTO("");
		trans.setREFERENCIAPROPIA("");
		trans.setFECHACREACION("");
		trans.setFECHADEVOLUCION("");
		trans.setMATRICULA("");
		trans.setOBSERVACIONES("");
		trans.setACREDITACION("");
		trans.setACTAADJUDICACION("");
		trans.setCAMBIOSERVICIO("");
		trans.setCONSENTIMIENTO("");
		trans.setCONTRATOCOMPRAVENTA("");
		trans.setIMPRESOPERMISO("");
		trans.setRENTING("");
		trans.setSENTENCIAADJUDICACION("");

		// DATOS DESTINATARIO
		DATOSADQUIRIENTE datosadquiriente = factory.createDATOSADQUIRIENTE();
		datosadquiriente.setAPELLIDO1RAZONSOCIALADQUIRIENTE("");
		datosadquiriente.setDNIADQUIRIENTE("");
		datosadquiriente.setAPELLIDO1RAZONSOCIALADQUIRIENTE("");
		datosadquiriente.setAPELLIDO2ADQUIRIENTE("");
		datosadquiriente.setNOMBREADQUIRIENTE("");
		datosadquiriente.setSIGLASDIRECCIONADQUIRIENTE("");
		datosadquiriente.setNOMBREVIADIRECCIONADQUIRIENTE("");
		datosadquiriente.setNUMERODIRECCIONADQUIRIENTE("");
		datosadquiriente.setLETRADIRECCIONADQUIRIENTE("");
		datosadquiriente.setESCALERADIRECCIONADQUIRIENTE("");
		datosadquiriente.setPISODIRECCIONADQUIRIENTE("");
		datosadquiriente.setPUERTADIRECCIONADQUIRIENTE("");
		datosadquiriente.setMUNICIPIOADQUIRIENTE("");
		datosadquiriente.setPUEBLOADQUIRIENTE("");
		datosadquiriente.setPROVINCIAADQUIRIENTE("");
		datosadquiriente.setCPADQUIRIENTE("");
		datosadquiriente.setTELEFONOADQUIRIENTE("");
		datosadquiriente.setBLOQUEDIRECCIONADQUIRIENTE("");
		datosadquiriente.setKMDIRECCIONADQUIRIENTE("");
		datosadquiriente.setHMDIRECCIONADQUIRIENTE("");
		datosadquiriente.setACTUALIZACIONDOMICILIOADQUIRIENTE("");
		datosadquiriente.setAUTONOMOADQUIRIENTE("");
		datosadquiriente.setCODIGOIAEADQUIRIENTE("");
		datosadquiriente.setSEXOADQUIRIENTE("");
		datosadquiriente.setFECHANACIMIENTOADQUIRIENTE("");

		// DATOS REPRESENTANTE
		datosadquiriente.setNOMBREREPRESENTANTEADQUIRIENTE("");
		datosadquiriente.setAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE("");
		datosadquiriente.setAPELLIDO2REPRESENTANTEADQUIRIENTE("");
		datosadquiriente.setDNIREPRESENTANTEADQUIRIENTE("");
		datosadquiriente.setDOCUMENTOSREPRESENTANTEADQUIRIENTE("");
		datosadquiriente.setCONCEPTOREPRESENTANTEADQUIRIENTE("");
		datosadquiriente.setMOTIVOREPRESENTANTEADQUIRIENTE("");
		datosadquiriente.setFECHAINICIOTUTELAADQUIERIENTE("");
		datosadquiriente.setFECHAFINTUTELAADQUIERIENTE("");
		datosadquiriente.setNOMBREAPELLIDOSREPRESENTANTEADQUIRIENTE("");

		// DATOS CONDUCTOR HABITUAL
		datosadquiriente.setNOMBRECONDUCTORHABITUAL("");
		datosadquiriente.setAPELLIDO1RAZONSOCIALCONDUCTORHABITUAL("");
		datosadquiriente.setAPELLIDO2CONDUCTORHABITUAL("");
		datosadquiriente.setDNICONDUCTORHABITUAL("");
		datosadquiriente.setFECHAINICIOCONDUCTORHABITUAL("");
		datosadquiriente.setFECHAFINCONDUCTORHABITUAL("");
		datosadquiriente.setFECHANACIMIENTOADQUIRIENTE("");

		trans.setDATOSADQUIRIENTE(datosadquiriente);

		// DATOS PRESENTADOR
		DATOSPRESENTADOR datospresentador = factory.createDATOSPRESENTADOR();
		datospresentador.setAPELLIDO1RAZONSOCIALPRESENTADOR("");
		datospresentador.setDNIPRESENTADOR("");
		datospresentador.setSEXOPRESENTADOR("");
		datospresentador.setAPELLIDO1RAZONSOCIALPRESENTADOR("");
		datospresentador.setAPELLIDO2PRESENTADOR("");
		datospresentador.setNOMBREPRESENTADOR("");
		datospresentador.setSIGLASDIRECCIONPRESENTADOR("");
		datospresentador.setNOMBREVIADIRECCIONPRESENTADOR("");
		datospresentador.setNUMERODIRECCIONPRESENTADOR("");
		datospresentador.setLETRADIRECCIONPRESENTADOR("");
		datospresentador.setESCALERADIRECCIONPRESENTADOR("");
		datospresentador.setPISODIRECCIONPRESENTADOR("");
		datospresentador.setPUERTADIRECCIONPRESENTADOR("");
		datospresentador.setMUNICIPIOPRESENTADOR("");
		datospresentador.setPROVINCIAPRESENTADOR("");
		datospresentador.setCPPRESENTADOR("");
		datospresentador.setTELEFONOPRESENTADOR("");
		datospresentador.setPUEBLOPRESENTADOR("");
		datospresentador.setBLOQUEDIRECCIONPRESENTADOR("");
		datospresentador.setHMDIRECCIONPRESENTADOR("");
		datospresentador.setKMDIRECCIONPRESENTADOR("");

		trans.setDATOSPRESENTADOR(datospresentador);

		// DATOS TRANSMITENTE
		DATOSTRANSMITENTE datostransmitente = factory.createDATOSTRANSMITENTE();
		datostransmitente.setAPELLIDO1RAZONSOCIALTRANSMITENTE("");
		datostransmitente.setDNITRANSMITENTE("");
		datostransmitente.setAPELLIDO1RAZONSOCIALTRANSMITENTE("");
		datostransmitente.setAPELLIDO2TRANSMITENTE("");
		datostransmitente.setNOMBRETRANSMITENTE("");
		datostransmitente.setFECHANACIMIENTOTRANSMITENTE("");
		datostransmitente.setSIGLASDIRECCIONTRANSMITENTE("");
		datostransmitente.setNOMBREVIADIRECCIONTRANSMITENTE("");
		datostransmitente.setNUMERODIRECCIONTRANSMITENTE("");
		datostransmitente.setLETRADIRECCIONTRANSMITENTE("");
		datostransmitente.setESCALERADIRECCIONTRANSMITENTE("");
		datostransmitente.setPISODIRECCIONTRANSMITENTE("");
		datostransmitente.setPUERTADIRECCIONTRANSMITENTE("");
		datostransmitente.setMUNICIPIOTRANSMITENTE("");
		datostransmitente.setPUEBLOTRANSMITENTE("");
		datostransmitente.setPROVINCIATRANSMITENTE("");
		datostransmitente.setCPTRANSMITENTE("");
		datostransmitente.setTELEFONOTRANSMITENTE("");
		datostransmitente.setSEXOTRANSMITENTE("");
		datostransmitente.setBLOQUEDIRECCIONTRANSMITENTE("");
		datostransmitente.setHMDIRECCIONTRANSMITENTE("");
		datostransmitente.setKMDIRECCIONTRANSMITENTE("");
		datostransmitente.setNUMEROTITULARES("");

		// DATOS REPRESENTANTE TRANSMITENTE
		datostransmitente.setDNIREPRESENTANTETRANSMITENTE("");
		// DRC@28-09-2012 Incidenca:2334
		datostransmitente.setDOCUMENTOSREPRESENTANTETRANSMITENTE("");
		datostransmitente.setNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE("");
		datostransmitente.setNOMBREREPRESENTANTETRANSMITENTE("");
		datostransmitente.setAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE("");
		datostransmitente.setAPELLIDO2REPRESENTANTETRANSMITENTE("");
		datostransmitente.setCONCEPTOREPRESENTANTETRANSMITENTE("");
		datostransmitente.setMOTIVOREPRESENTANTETRANSMITENTE("");
		datostransmitente.setFECHAFINTUTELATRANSMITENTE("");
		datostransmitente.setFECHAINICIOTUTELATRANSMITENTE("");

		// DATOS PRIMER COTITULAR
		datostransmitente.setAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setDNIPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setAPELLIDO2PRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setNOMBREPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setSIGLASDIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setNOMBREVIADIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setNUMERODIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setLETRADIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setESCALERADIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setPISODIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setPUERTADIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setMUNICIPIOPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setPUEBLOPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setPROVINCIAPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setCPPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setTELEFONOPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setSEXOPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setBLOQUEDIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setHMDIRECCIONPRIMERCOTITULARTRANSMITENTE("");
		datostransmitente.setKMDIRECCIONPRIMERCOTITULARTRANSMITENTE("");

		// DATOS SEGUNDO COTITULAR
		datostransmitente.setAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setDNISEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setAPELLIDO2SEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setNOMBRESEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setSIGLASDIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setNOMBREVIADIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setNUMERODIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setLETRADIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setESCALERADIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setPISODIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setPUERTADIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setMUNICIPIOSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setPUEBLOSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setPROVINCIASEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setCPSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setTELEFONOSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setSEXOSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setBLOQUEDIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");
		datostransmitente.setKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE("");

		trans.setDATOSTRANSMITENTE(datostransmitente);

		// DATOS ARRENDADOR
		DATOSARRENDADOR datosarrendador = factory.createDATOSARRENDADOR();
		datosarrendador.setDNIARRENDADOR("");
		datosarrendador.setAPELLIDO1RAZONSOCIALARRENDADOR("");
		datosarrendador.setAPELLIDO2ARRENDADOR("");
		datosarrendador.setNOMBREARRENDADOR("");
		datosarrendador.setNOMBREAPELLIDOSARRENDADOR("");
		datosarrendador.setSIGLASDIRECCIONARRENDADOR("");
		datosarrendador.setNOMBREVIADIRECCIONARRENDADOR("");
		datosarrendador.setNUMERODIRECCIONARRENDADOR("");
		datosarrendador.setLETRADIRECCIONARRENDADOR("");
		datosarrendador.setESCALERADIRECCIONARRENDADOR("");
		datosarrendador.setPISODIRECCIONARRENDADOR("");
		datosarrendador.setPUERTADIRECCIONARRENDADOR("");
		datosarrendador.setMUNICIPIOARRENDADOR("");
		datosarrendador.setFECHANACIMIENTOARRENDADOR("");
		datosarrendador.setPUEBLODIRECCIONARRENDADOR("");
		datosarrendador.setSEXOARRENDADOR("");
		datosarrendador.setTELEFONOARRENDADOR("");
		datosarrendador.setHMDIRECCIONARRENDADOR("");
		datosarrendador.setKMDIRECCIONARRENDADOR("");
		datosarrendador.setBLOQUEDIRECCIONARRENDADOR("");
		datosarrendador.setACTUALIZACIONDOMICILIOARRENDADOR("");
		datosarrendador.setAUTONOMOARRENDADOR("");
		datosarrendador.setCODIGOIAEARRENDADOR("");
		datosarrendador.setPROVINCIAARRENDADOR("");
		datosarrendador.setCPARRENDADOR("");

		// DATOS REPRESENTANTE ARRENDADOR
		datosarrendador.setNOMBREAPELLIDOSREPRESENTANTEARRENDADOR("");
		datosarrendador.setNOMBREREPRESENTANTEARRENDADOR("");
		datosarrendador.setAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR("");
		datosarrendador.setAPELLIDO2REPRESENTANTEARRENDADOR("");
		datosarrendador.setCONCEPTOREPRESENTANTEARRENDADOR("");
		datosarrendador.setMOTIVOREPRESENTANTEARRENDADOR("");
		datosarrendador.setFECHAINICIOTUTELAARRENDADOR("");
		datosarrendador.setFECHAFINTUTELAARRENDADOR("");
		datosarrendador.setDNIREPRESENTANTEARRENDADOR("");

		trans.setDATOSARRENDADOR(datosarrendador);

		// DRC@26-09-2012 Incidencia: 2358
		// DATOS COMPRA-VENTA
		DATOSCOMPRAVENTA datoscompraventa = factory.createDATOSCOMPRAVENTA();
		datoscompraventa.setDNICOMPRAVENTA("");
		datoscompraventa.setAPELLIDO1RAZONSOCIALCOMPRAVENTA("");
		datoscompraventa.setAPELLIDO2COMPRAVENTA("");
		datoscompraventa.setNOMBRECOMPRAVENTA("");
		datoscompraventa.setNOMBREAPELLIDOSCOMPRAVENTA("");
		datoscompraventa.setSIGLASDIRECCIONCOMPRAVENTA("");
		datoscompraventa.setNOMBREVIADIRECCIONCOMPRAVENTA("");
		datoscompraventa.setNUMERODIRECCIONCOMPRAVENTA("");
		datoscompraventa.setLETRADIRECCIONCOMPRAVENTA("");
		datoscompraventa.setESCALERADIRECCIONCOMPRAVENTA("");
		datoscompraventa.setPISODIRECCIONCOMPRAVENTA("");
		datoscompraventa.setPUERTADIRECCIONCOMPRAVENTA("");
		datoscompraventa.setMUNICIPIOCOMPRAVENTA("");
		datoscompraventa.setFECHANACIMIENTOCOMPRAVENTA("");
		datoscompraventa.setPUEBLODIRECCIONCOMPRAVENTA("");
		datoscompraventa.setSEXOCOMPRAVENTA("");
		datoscompraventa.setTELEFONOCOMPRAVENTA("");
		datoscompraventa.setHMDIRECCIONCOMPRAVENTA("");
		datoscompraventa.setKMDIRECCIONCOMPRAVENTA("");
		datoscompraventa.setBLOQUEDIRECCIONCOMPRAVENTA("");
		datoscompraventa.setACTUALIZACIONDOMICILIOCOMPRAVENTA("");
		datoscompraventa.setAUTONOMOCOMPRAVENTA("");
		datoscompraventa.setCODIGOIAECOMPRAVENTA("");
		datoscompraventa.setPROVINCIACOMPRAVENTA("");
		datoscompraventa.setCPCOMPRAVENTA("");

		// DATOS REPRESENTANTE COMPRA-VENTA
		datoscompraventa.setNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA("");
		datoscompraventa.setNOMBREREPRESENTANTECOMPRAVENTA("");
		datoscompraventa.setAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA("");
		datoscompraventa.setAPELLIDO2REPRESENTANTECOMPRAVENTA("");
		datoscompraventa.setCONCEPTOREPRESENTANTECOMPRAVENTA("");
		datoscompraventa.setMOTIVOREPRESENTANTECOMPRAVENTA("");
		datoscompraventa.setFECHAINICIOTUTELACOMPRAVENTA("");
		datoscompraventa.setFECHAFINTUTELACOMPRAVENTA("");
		datoscompraventa.setDNIREPRESENTANTECOMPRAVENTA("");

		trans.setDATOSCOMPRAVENTA(datoscompraventa);

		// DATOS 620
		DATOS620 datos620 = factory.createDATOS620();
		datos620.setANIOFABRICACION("");
		datos620.setESTADO620("");
		datos620.setBASEIMPONIBLE("");
		datos620.setCARACTERISTICAS("");
		datos620.setCARBURANTE("");
		datos620.setCILINDRADA("");
		datos620.setCUOTATRIBUTARIA("");
		datos620.setEXENTO("");
		datos620.setFECHADEVENGO("");
		datos620.setFECHAPRIMERAMATRICULACION("");
		datos620.setFUNDAMENTOEXENCION("");
		datos620.setFUNDAMENTONOSUJETO("");
		datos620.setMARCA620("");
		datos620.setMARCA620DESC("");
		datos620.setMODELO620("");
		datos620.setMODELO620DESC("");
		datos620.setNOSUJETO("");
		datos620.setNUMCILINDROS("");
		datos620.setOFICINALIQUIDADORA("");
		datos620.setPORCENTAJEADQUISICION("");
		datos620.setPORCENTAJEREDUCCIONANUAL("");
		datos620.setPOTENCIAFISCAL("");
		datos620.setTIPOGRAVAMEN("");
		datos620.setTIPOVEHICULO("");
		datos620.setVALORDECLARADO("");

		DIRECCIONDISTINTAADQUIRIENTE direccionDistintaAdquriente = factory.createDIRECCIONDISTINTAADQUIRIENTE();
		direccionDistintaAdquriente.setCPDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setESCALERADIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setLETRADIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setMUNICIPIODISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setNOMBREVIADIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setNUMERODIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setPISODIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setPROVINCIADISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setPUEBLODISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setPUERTADIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setSIGLASDIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setBLOQUEDIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setHMDIRECCIONDISTINTAADQUIRIENTE("");
		direccionDistintaAdquriente.setKMDIRECCIONDISTINTAADQUIRIENTE("");

		datos620.setDIRECCIONDISTINTAADQUIRIENTE(direccionDistintaAdquriente);

		trans.setDATOS620(datos620);

		// DATOS LIMITACIÓN
		DATOSLIMITACION datosLimitacion = factory.createDATOSLIMITACION();
		datosLimitacion.setFECHALIMITACION("");
		datosLimitacion.setFINANCIERALIMITACION("");
		datosLimitacion.setNUMEROREGISTROLIMITACION("");
		datosLimitacion.setTIPOLIMITACION("");

		trans.setDATOSLIMITACION(datosLimitacion);

		// DATOS PRESENTACIÓN
		DATOSPRESENTACION datosPresentacion = factory.createDATOSPRESENTACION();
		datosPresentacion.setCODIGOELECTRONICOTRANSFERENCIA("");
		datosPresentacion.setCODIGOTASA("");
		datosPresentacion.setCODIGOTASACAMBIO("");
		datosPresentacion.setCODIGOTASAINFORME("");
		datosPresentacion.setFECHAPRESENTACION("");
		datosPresentacion.setJEFATURAPROVINCIAL("");
		datosPresentacion.setTIPOTASA("");
		datosPresentacion.setALTAIVTM("");
		datosPresentacion.setCODIGOELECTRONICOMATRICULACION("");
		datosPresentacion.setDUA("");
		datosPresentacion.setEXENTOCEM("");
		datosPresentacion.setEXENTOIEDMT("");
		datosPresentacion.setEXENTOISD("");
		datosPresentacion.setEXENTOITP("");
		datosPresentacion.setIDNOSUJECCION06("");
		datosPresentacion.setIDREDUCCION05("");
		datosPresentacion.setMODELOIEDMT("");
		datosPresentacion.setMODELOISD("");
		datosPresentacion.setMODELOITP("");
		datosPresentacion.setNOSUJETOIEDMT("");
		datosPresentacion.setNOSUJETOISD("");
		datosPresentacion.setNOSUJETOITP("");
		datosPresentacion.setNUMADJUDICACIONISD("");
		datosPresentacion.setNUMAUTOITP("");
		datosPresentacion.setNUMEROFACTURA("");
		datosPresentacion.setTIPOTASACAMBIO("");
		datosPresentacion.setTIPOTASAINFORME("");

		trans.setDATOSPRESENTACION(datosPresentacion);

		// DATOS VEHICULO
		DATOSVEHICULO datosVehiculo = factory.createDATOSVEHICULO();
		datosVehiculo.setCALLEDIRECCIONVEHICULO("");
		datosVehiculo.setCONCEPTOITV("");
		datosVehiculo.setDECLARACIONRESPONSABILIDAD("");
		datosVehiculo.setESTACIONITV("");
		datosVehiculo.setFECHACADUCIDADITV("");
		datosVehiculo.setFECHAITV("");
		datosVehiculo.setFECHAMATRICULACION("");
		datosVehiculo.setMARCA("");
		datosVehiculo.setMODELO("");
		datosVehiculo.setMODOADJUDICACION("");
		datosVehiculo.setMOTIVOITV("");
		datosVehiculo.setNUMEROBASTIDOR("");
		datosVehiculo.setNUMERODIRECCIONVEHICULO("");
		datosVehiculo.setPROVINCIAVEHICULO("");
		datosVehiculo.setPUEBLOVEHICULO("");
		datosVehiculo.setSERVICIODESTINO("");
		datosVehiculo.setTIPOTRANSFERENCIA("");
		datosVehiculo.setTIPOIDVEHICULO("");
		datosVehiculo.setSERVICIO("");
		datosVehiculo.setSERVICIOANTERIOR("");

		trans.setDATOSVEHICULO(datosVehiculo);

		return trans;
	}

	/**
	 * Método que realizará la conversión de una línea y tendrá que adaptarse a las restricciones del xsd.
	 * @param bean
	 * @return
	 */
	private TRANSMISION convertirBeanToGa(TramiteTraficoTransmisionBean bean) {
		TRANSMISION trans = instanciarTRANSMISION();

		TramiteTraficoBean tramite = bean.getTramiteTraficoBean();

		if (null != tramite.getNumExpediente()) {
			trans.setNUMERODOCUMENTO(null != tramite.getNumExpediente() ? tramite.getNumExpediente().toString() : "");
			trans.setREFERENCIAPROPIA(null != tramite.getReferenciaPropia() ? tramite.getReferenciaPropia() : "");
			trans.setFECHACREACION(null != tramite.getFechaCreacion() ? tramite.getFechaCreacion().toString() : "");
			trans.setMATRICULA(null != tramite.getVehiculo().getMatricula() ? tramite.getVehiculo().getMatricula() : "");
			trans.setOBSERVACIONES(null != tramite.getAnotaciones() ? tramite.getAnotaciones() : "");

			trans.setCAMBIOSERVICIO(null != bean.getCambioServicio() && "true".equals(bean.getCambioServicio()) ? DecisionTrafico.Si.getValorEnum() : "");
			trans.setIMPRESOPERMISO(null != bean.getImpresionPermiso() && "true".equals(bean.getImpresionPermiso()) ? DecisionTrafico.Si.getValorEnum() : "");
			trans.setRENTING(null != bean.getTramiteTraficoBean().getRenting() && "true".equals(bean.getTramiteTraficoBean().getRenting()) ? DecisionTrafico.Si.getValorEnum() : "");
			trans.setCONSENTIMIENTO(null != bean.getConsentimiento() && "true".equals(bean.getConsentimiento()) ? DecisionTrafico.Si.getValorEnum() : "");
			// DVV: Descomentarse para unificación
			//trans.setADJUDICACION(null != bean.getAdjudicacion() && "true".equals(bean.getAdjudicacion()) ? DecisionTrafico.Si.getValorEnum() : "");
			// DVV: Comentarse para unificación
			trans.setACTAADJUDICACION(null != bean.getActaSubasta() && "true".equals(bean.getActaSubasta()) ? DecisionTrafico.Si.getValorEnum() : "");
			trans.setCONTRATOCOMPRAVENTA(null != bean.getContratoFactura() && "true".equals(bean.getContratoFactura()) ? DecisionTrafico.Si.getValorEnum() : "");
			// DVV: Comentarse para unificación
			trans.setSENTENCIAADJUDICACION(null != bean.getSentenciaJudicial() && "true".equals(bean.getSentenciaJudicial()) ? DecisionTrafico.Si.getValorEnum() : "");
			trans.setACREDITACION(null != bean.getAcreditaHerenciaDonacion() ? bean.getAcreditaHerenciaDonacion().getValorEnum() : "");
			trans.setSIMULTANEA(bean.getSimultanea() != null ? bean.getSimultanea() : new BigDecimal(-1));
			// DATOS LIMITACIÓN

			DATOSLIMITACION datosLimitacion = trans.getDATOSLIMITACION();

			datosLimitacion.setFECHALIMITACION(null != tramite.getFechaIedtm() ? tramite.getFechaIedtm().toString() : "");
			datosLimitacion.setFINANCIERALIMITACION(null != tramite.getFinancieraIedtm() ? tramite.getFinancieraIedtm() : "");
			datosLimitacion.setNUMEROREGISTROLIMITACION(null != tramite.getNumRegIedtm() && !tramite.getNumRegIedtm().getValorEnum().equals("-1") ? tramite.getNumRegIedtm().getValorEnum() : "");
			datosLimitacion.setTIPOLIMITACION(null != tramite.getIedtm() && "TRUE".equalsIgnoreCase(tramite.getIedtm()) ? "E" : "");

			trans.setDATOSLIMITACION(datosLimitacion);
		}

		trans.setFECHADEVOLUCION("");

		// DATOS DESTINATARIO
		IntervinienteTrafico adquiriente = bean.getAdquirienteBean();

		if (null != adquiriente.getPersona().getNif()) {
			DATOSADQUIRIENTE datosadquiriente = trans.getDATOSADQUIRIENTE();
			Persona persona = adquiriente.getPersona();

			if (null != persona.getNif()) {
				datosadquiriente.setAPELLIDO1RAZONSOCIALADQUIRIENTE(null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "");
				datosadquiriente.setDNIADQUIRIENTE(null != persona.getNif() ? persona.getNif() : "");
				datosadquiriente.setAPELLIDO2ADQUIRIENTE(null != persona.getApellido2() ? persona.getApellido2() : "");
				datosadquiriente.setNOMBREADQUIRIENTE(null != persona.getNombre() ? persona.getNombre() : "");
				datosadquiriente.setFECHANACIMIENTOADQUIRIENTE(null != persona.getFechaNacimientoBean() ? persona.getFechaNacimientoBean().toString() : "");
				datosadquiriente.setSEXOADQUIRIENTE(null != persona.getSexo() ? persona.getSexo() : "");
				datosadquiriente.setACTUALIZACIONDOMICILIOADQUIRIENTE(null != adquiriente.getCambioDomicilio() && "true".equals(adquiriente.getCambioDomicilio()) ? DecisionTrafico.Si.getValorEnum()
						: "");
				datosadquiriente.setAUTONOMOADQUIRIENTE(null != adquiriente.getAutonomo() && "true".equals(adquiriente.getAutonomo()) ? DecisionTrafico.Si.getValorEnum() : "");
				datosadquiriente.setCODIGOIAEADQUIRIENTE(null != adquiriente.getCodigoIAE() ? adquiriente.getCodigoIAE() : "");
				datosadquiriente.setFECHACADUCIDADNIFADQUIRIENTE(null != persona.getFechaCaducidadNif() ? persona.getFechaCaducidadNif().toString() : "");
				datosadquiriente.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOADQUIRIENTE(null != persona.getFechaCaducidadAlternativo() ? persona.getFechaCaducidadAlternativo().toString() : "");
				datosadquiriente.setTIPODOCUMENTOSUSTITUTIVOADQUIRIENTE(null != persona.getTipoDocumentoAlternativo() ? persona.getTipoDocumentoAlternativo().getValorEnum() : "");

				Direccion direccion = persona.getDireccion();

				if (null != direccion.getIdDireccion()) {
					String sigla = null != direccion.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) : "";
					datosadquiriente.setSIGLASDIRECCIONADQUIRIENTE(sigla);
					datosadquiriente.setNOMBREVIADIRECCIONADQUIRIENTE(null != direccion.getNombreVia() ? direccion.getNombreVia() : "");
					datosadquiriente.setNUMERODIRECCIONADQUIRIENTE(null != direccion.getNumero() ? direccion.getNumero() : "");
					datosadquiriente.setLETRADIRECCIONADQUIRIENTE(null != direccion.getLetra() ? direccion.getLetra() : "");
					datosadquiriente.setESCALERADIRECCIONADQUIRIENTE(null != direccion.getEscalera() ? direccion.getEscalera() : "");
					datosadquiriente.setPISODIRECCIONADQUIRIENTE(null != direccion.getPlanta() ? direccion.getPlanta() : "");
					datosadquiriente.setPUERTADIRECCIONADQUIRIENTE(null != direccion.getPuerta() ? direccion.getPuerta() : "");
					datosadquiriente.setMUNICIPIOADQUIRIENTE(null != direccion.getMunicipio() ? direccion.getMunicipio().getNombre() : "");
					datosadquiriente.setPUEBLOADQUIRIENTE(null != direccion.getPueblo() ? direccion.getPueblo() : "");

					datosadquiriente.setBLOQUEDIRECCIONADQUIRIENTE(null != direccion.getBloque() ? direccion.getBloque() : "");
					datosadquiriente.setHMDIRECCIONADQUIRIENTE(null != direccion.getHm() ? direccion.getHm() : "");
					datosadquiriente.setKMDIRECCIONADQUIRIENTE(null != direccion.getPuntoKilometrico() ? direccion.getPuntoKilometrico() : "");

					String letraProvincia = null != direccion.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia())
							: "";
					datosadquiriente.setPROVINCIAADQUIRIENTE(letraProvincia);
					datosadquiriente.setCPADQUIRIENTE(null != direccion.getCodPostal() ? direccion.getCodPostal() : "");
				}

				datosadquiriente.setTELEFONOADQUIRIENTE(null != persona.getTelefonos() ? persona.getTelefonos() : "");

				IntervinienteTrafico representanteAdquiriente = bean.getRepresentanteAdquirienteBean();
				if (null != representanteAdquiriente.getPersona().getNif()) {

					datosadquiriente.setDNIREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getPersona().getNif() ? representanteAdquiriente.getPersona().getNif() : "");
					datosadquiriente.setDOCUMENTOSREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getDatosDocumento() ? representanteAdquiriente.getDatosDocumento() : "");

					datosadquiriente.setFECHACADUCIDADNIFREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getPersona().getFechaCaducidadNif() ? representanteAdquiriente.getPersona()
							.getFechaCaducidadNif().toString() : "");
					datosadquiriente.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getPersona().getFechaCaducidadAlternativo()
							? representanteAdquiriente.getPersona().getFechaCaducidadAlternativo().toString() : "");
					datosadquiriente.setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getPersona().getTipoDocumentoAlternativo() ? representanteAdquiriente
							.getPersona().getTipoDocumentoAlternativo().getValorEnum() : "");

					String nombre = "";
					nombre += representanteAdquiriente.getPersona().getApellido1RazonSocial() != null ? (representanteAdquiriente.getPersona().getApellido1RazonSocial() + " ") : "";
					nombre += representanteAdquiriente.getPersona().getApellido2() != null ? (representanteAdquiriente.getPersona().getApellido2() + " ") : "";
					nombre += representanteAdquiriente.getPersona().getNombre() != null ? (", " + representanteAdquiriente.getPersona().getNombre()) : "";
					nombre = nombre.trim();

					datosadquiriente.setNOMBREAPELLIDOSREPRESENTANTEADQUIRIENTE(nombre);

					datosadquiriente.setAPELLIDO1RAZONSOCIALREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getPersona().getApellido1RazonSocial() ? representanteAdquiriente.getPersona()
							.getApellido1RazonSocial() : "");
					datosadquiriente.setAPELLIDO2REPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getPersona().getApellido2() ? representanteAdquiriente.getPersona().getApellido2() : "");
					datosadquiriente.setNOMBREREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getPersona().getNombre() ? representanteAdquiriente.getPersona().getNombre() : "");

					datosadquiriente.setCONCEPTOREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getConceptoRepre() ? representanteAdquiriente.getConceptoRepre().getNombreEnum() : "");
					datosadquiriente.setMOTIVOREPRESENTANTEADQUIRIENTE(null != representanteAdquiriente.getIdMotivoTutela() ? representanteAdquiriente.getIdMotivoTutela().getValorEnum() : "");
					datosadquiriente.setFECHAINICIOTUTELAADQUIERIENTE(null != representanteAdquiriente.getFechaInicio() ? representanteAdquiriente.getFechaInicio().toString() : "");
					datosadquiriente.setFECHAFINTUTELAADQUIERIENTE(null != representanteAdquiriente.getFechaFin() ? representanteAdquiriente.getFechaFin().toString() : "");
				}

				IntervinienteTrafico conductorHabitual = bean.getConductorHabitualBean();
				if (null != conductorHabitual.getPersona().getNif()) {

					datosadquiriente.setDNICONDUCTORHABITUAL(null != conductorHabitual.getPersona().getNif() ? conductorHabitual.getPersona().getNif() : "");

					datosadquiriente.setAPELLIDO1RAZONSOCIALCONDUCTORHABITUAL(null != conductorHabitual.getPersona().getApellido1RazonSocial() ? conductorHabitual.getPersona()
							.getApellido1RazonSocial() : "");
					datosadquiriente.setAPELLIDO2CONDUCTORHABITUAL(null != conductorHabitual.getPersona().getApellido2() ? conductorHabitual.getPersona().getApellido2() : "");
					datosadquiriente.setNOMBRECONDUCTORHABITUAL(null != conductorHabitual.getPersona().getNombre() ? conductorHabitual.getPersona().getNombre() : "");

					datosadquiriente.setFECHANACIMIENTOCONDUCTORHABITUAL(null != conductorHabitual.getPersona().getFechaNacimientoBean() ? conductorHabitual.getPersona().getFechaNacimientoBean()
							.toString() : "");
					datosadquiriente.setFECHAINICIOCONDUCTORHABITUAL(null != conductorHabitual.getFechaInicio() ? conductorHabitual.getFechaInicio().toString() : "");
					datosadquiriente.setFECHAFINCONDUCTORHABITUAL(null != conductorHabitual.getFechaFin() ? conductorHabitual.getFechaFin().toString() : "");

					datosadquiriente.setFECHACADUCIDADNIFCONDUCTORHABITUAL(null != conductorHabitual.getPersona().getFechaCaducidadNif() ? conductorHabitual.getPersona().getFechaCaducidadNif()
							.toString() : "");
					datosadquiriente.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL(null != conductorHabitual.getPersona().getFechaCaducidadAlternativo() ? conductorHabitual.getPersona()
							.getFechaCaducidadAlternativo().toString() : "");
					datosadquiriente.setTIPODOCUMENTOSUSTITUTIVOCONDUCTORHABITUAL(null != conductorHabitual.getPersona().getTipoDocumentoAlternativo() ? conductorHabitual.getPersona()
							.getTipoDocumentoAlternativo().getValorEnum() : "");

				}
			}

			trans.setDATOSADQUIRIENTE(datosadquiriente);
		}

		// DATOS PRESENTADOR
		IntervinienteTrafico presentador = bean.getPresentadorBean();

		if (null != presentador.getPersona().getNif()) {
			DATOSPRESENTADOR datospresentador = trans.getDATOSPRESENTADOR();

			Persona persona = presentador.getPersona();
			if (null != persona.getNif()) {
				datospresentador.setAPELLIDO1RAZONSOCIALPRESENTADOR(null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "");
				datospresentador.setDNIPRESENTADOR(null != persona.getNif() ? persona.getNif() : "");
				datospresentador.setAPELLIDO2PRESENTADOR(null != persona.getApellido2() ? persona.getApellido2() : "");
				datospresentador.setNOMBREPRESENTADOR(null != persona.getNombre() ? persona.getNombre() : "");
				datospresentador.setTELEFONOPRESENTADOR(null != persona.getTelefonos() ? persona.getTelefonos() : "");
				datospresentador.setSEXOPRESENTADOR(null != persona.getSexo() ? persona.getSexo() : "");

				datospresentador.setFECHACADUCIDADNIFPRESENTADOR(null != persona.getFechaCaducidadNif() ? persona.getFechaCaducidadNif().toString() : "");
				datospresentador.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRESENTADOR(null != persona.getFechaCaducidadAlternativo() ? persona.getFechaCaducidadAlternativo().toString() : "");
				datospresentador.setTIPODOCUMENTOSUSTITUTIVOPRESENTADOR(null != persona.getTipoDocumentoAlternativo() ? persona.getTipoDocumentoAlternativo().getValorEnum() : "");

				Direccion direccion = persona.getDireccion();
				if (null != direccion.getIdDireccion()) {
					String sigla = null != direccion.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) : "";
					datospresentador.setSIGLASDIRECCIONPRESENTADOR(sigla);
					datospresentador.setNOMBREVIADIRECCIONPRESENTADOR(null != direccion.getNombreVia() ? direccion.getNombreVia() : "");
					datospresentador.setNUMERODIRECCIONPRESENTADOR(null != direccion.getNumero() ? direccion.getNumero() : "");
					datospresentador.setLETRADIRECCIONPRESENTADOR(null != direccion.getLetra() ? direccion.getLetra() : "");
					datospresentador.setESCALERADIRECCIONPRESENTADOR(null != direccion.getEscalera() ? direccion.getEscalera() : "");
					datospresentador.setPISODIRECCIONPRESENTADOR(null != direccion.getPlanta() ? direccion.getPlanta() : "");
					datospresentador.setPUERTADIRECCIONPRESENTADOR(null != direccion.getPuerta() ? direccion.getPuerta() : "");
					datospresentador.setMUNICIPIOPRESENTADOR(null != direccion.getMunicipio() ? direccion.getMunicipio().getNombre() : "");

					String letraProvincia = null != direccion.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia())
							: "";

					datospresentador.setPROVINCIAPRESENTADOR(letraProvincia);
					datospresentador.setCPPRESENTADOR(null != direccion.getCodPostal() ? direccion.getCodPostal() : "");
					datospresentador.setBLOQUEDIRECCIONPRESENTADOR(null != direccion.getBloque() ? direccion.getBloque() : "");
					datospresentador.setHMDIRECCIONPRESENTADOR(null != direccion.getHm() ? direccion.getHm() : "");
					datospresentador.setKMDIRECCIONPRESENTADOR(null != direccion.getPuntoKilometrico() ? direccion.getPuntoKilometrico() : "");
				}
			}
			trans.setDATOSPRESENTADOR(datospresentador);
		}

		// DATOS TRANSMITENTE
		IntervinienteTrafico transmitente = bean.getTransmitenteBean();

		if (null != transmitente.getPersona().getNif()) {
			DATOSTRANSMITENTE datostransmitente = trans.getDATOSTRANSMITENTE();
			datostransmitente.setNUMEROTITULARES(bean.getNumTitulares() != null ? bean.getNumTitulares().toString() : "");

			Persona persona = transmitente.getPersona();
			if (null != persona.getNif()) {
				datostransmitente.setAPELLIDO1RAZONSOCIALTRANSMITENTE(null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "");
				datostransmitente.setDNITRANSMITENTE(null != persona.getNif() ? persona.getNif() : "");
				datostransmitente.setAPELLIDO2TRANSMITENTE(null != persona.getApellido2() ? persona.getApellido2() : "");
				datostransmitente.setNOMBRETRANSMITENTE(null != persona.getNombre() ? persona.getNombre() : "");
				datostransmitente.setFECHANACIMIENTOTRANSMITENTE(null != persona.getFechaNacimientoBean() ? persona.getFechaNacimientoBean().toString() : "");
				datostransmitente.setTELEFONOTRANSMITENTE(null != persona.getTelefonos() ? persona.getTelefonos() : "");
				datostransmitente.setSEXOTRANSMITENTE(null != persona.getSexo() ? persona.getSexo() : "");

				datostransmitente.setFECHACADUCIDADNIFTRANSMITENTE(null != persona.getFechaCaducidadNif() ? persona.getFechaCaducidadNif().toString() : "");
				datostransmitente.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOTRANSMITENTE(null != persona.getFechaCaducidadAlternativo() ? persona.getFechaCaducidadAlternativo().toString() : "");
				datostransmitente.setTIPODOCUMENTOSUSTITUTIVOTRANSMITENTE(null != persona.getTipoDocumentoAlternativo() ? persona.getTipoDocumentoAlternativo().getValorEnum() : "");

				datostransmitente.setAUTONOMOTRANSMITENTE(null != transmitente.getAutonomo() && "true".equals(transmitente.getAutonomo()) ? DecisionTrafico.Si.getValorEnum() : "");
				datostransmitente.setCODIGOIAETRANSMITENTE(null != transmitente.getCodigoIAE() ? transmitente.getCodigoIAE() : "");

				Direccion direccion = persona.getDireccion();

				if (null != direccion.getIdDireccion()) {
					String sigla = null != direccion.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) : "";

					datostransmitente.setSIGLASDIRECCIONTRANSMITENTE(sigla);
					datostransmitente.setNOMBREVIADIRECCIONTRANSMITENTE(null != direccion.getNombreVia() ? direccion.getNombreVia() : "");
					datostransmitente.setNUMERODIRECCIONTRANSMITENTE(null != direccion.getNumero() ? direccion.getNumero() : "");
					datostransmitente.setLETRADIRECCIONTRANSMITENTE(null != direccion.getLetra() ? direccion.getLetra() : "");
					datostransmitente.setESCALERADIRECCIONTRANSMITENTE(null != direccion.getEscalera() ? direccion.getEscalera() : "");
					datostransmitente.setPISODIRECCIONTRANSMITENTE(null != direccion.getPlanta() ? direccion.getPlanta() : "");
					datostransmitente.setPUERTADIRECCIONTRANSMITENTE(null != direccion.getPuerta() ? direccion.getPuerta() : "");
					datostransmitente.setMUNICIPIOTRANSMITENTE(null != direccion.getMunicipio() ? direccion.getMunicipio().getNombre() : "");
					datostransmitente.setPUEBLOTRANSMITENTE(null != direccion.getPueblo() ? direccion.getPueblo() : "");

					String letraProvincia = null != direccion.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia())
							: "";

					datostransmitente.setPROVINCIATRANSMITENTE(letraProvincia);
					datostransmitente.setCPTRANSMITENTE(null != direccion.getCodPostal() ? direccion.getCodPostal() : "");
					datostransmitente.setBLOQUEDIRECCIONTRANSMITENTE(null != direccion.getBloque() ? direccion.getBloque() : "");
					datostransmitente.setHMDIRECCIONTRANSMITENTE(null != direccion.getHm() ? direccion.getHm() : "");
					datostransmitente.setKMDIRECCIONTRANSMITENTE(null != direccion.getPuntoKilometrico() ? direccion.getPuntoKilometrico() : "");
				}
			}

			IntervinienteTrafico representanteTransmitente = bean.getRepresentanteTransmitenteBean();

			if (null != representanteTransmitente.getPersona().getNif()) {
				datostransmitente.setDNIREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getPersona().getNif() ? representanteTransmitente.getPersona().getNif() : "");
				// DRC@28-09-2012 Incidencia:2334
				datostransmitente.setDOCUMENTOSREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getDatosDocumento() ? representanteTransmitente.getDatosDocumento() : "");
				// datostransmitente.setDOCUMENTOSREPRESENTANTETRANSMITENTE(null!=representanteTransmitente.getDatosDocumento()?
				// representanteTransmitente.getDatosDocumento():"");
				// datostransmitente.setNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE(
				// (null!=representanteTransmitente.getPersona() && null!=representanteTransmitente.getPersona().getNombre()?representanteTransmitente.getPersona().getNombre():"") +
				// (null!=representanteTransmitente.getPersona() && null!=representanteTransmitente.getPersona().getApellido1RazonSocial()?representanteTransmitente.getPersona().getApellido1RazonSocial():"") +
				// (null!=representanteTransmitente.getPersona() && null!=representanteTransmitente.getPersona().getApellido2()?representanteTransmitente.getPersona().getApellido2():""));

				String nombre = "";
				nombre += representanteTransmitente.getPersona().getApellido1RazonSocial() != null ? (representanteTransmitente.getPersona().getApellido1RazonSocial() + " ") : "";
				nombre += representanteTransmitente.getPersona().getApellido2() != null ? (representanteTransmitente.getPersona().getApellido2() + " ") : "";
				nombre += representanteTransmitente.getPersona().getNombre() != null ? (", " + representanteTransmitente.getPersona().getNombre()) : "";
				nombre = nombre.trim();

				datostransmitente.setNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE(nombre);
				datostransmitente.setAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getPersona().getApellido1RazonSocial() ? representanteTransmitente.getPersona()
						.getApellido1RazonSocial() : "");
				datostransmitente.setAPELLIDO2REPRESENTANTETRANSMITENTE(null != representanteTransmitente.getPersona().getApellido2() ? representanteTransmitente.getPersona().getApellido2() : "");
				datostransmitente.setNOMBREREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getPersona().getNombre() ? representanteTransmitente.getPersona().getNombre() : "");

				datostransmitente.setFECHACADUCIDADNIFREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getPersona().getFechaCaducidadNif() ? representanteTransmitente.getPersona()
						.getFechaCaducidadNif().toString() : "");
				datostransmitente.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getPersona().getFechaCaducidadAlternativo()
						? representanteTransmitente.getPersona().getFechaCaducidadAlternativo().toString() : "");
				datostransmitente.setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getPersona().getTipoDocumentoAlternativo() ? representanteTransmitente
						.getPersona().getTipoDocumentoAlternativo().getValorEnum() : "");

				datostransmitente.setCONCEPTOREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getConceptoRepre() ? representanteTransmitente.getConceptoRepre().getNombreEnum() : "");
				datostransmitente.setMOTIVOREPRESENTANTETRANSMITENTE(null != representanteTransmitente.getIdMotivoTutela() ? representanteTransmitente.getIdMotivoTutela().getValorEnum() : "");
				datostransmitente.setFECHAINICIOTUTELATRANSMITENTE(null != representanteTransmitente.getFechaInicio() ? representanteTransmitente.getFechaInicio().toString() : "");
				datostransmitente.setFECHAFINTUTELATRANSMITENTE(null != representanteTransmitente.getFechaFin() ? representanteTransmitente.getFechaFin().toString() : "");
			}

			// PRIMER Y SEGUNDO COTITULAR

			IntervinienteTrafico primerCotitular = bean.getPrimerCotitularTransmitenteBean();

			if (null != primerCotitular.getPersona().getNif()) {
				Persona personaCot1 = primerCotitular.getPersona();
				if (null != personaCot1.getNif()) {
					datostransmitente.setAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getApellido1RazonSocial() ? personaCot1.getApellido1RazonSocial() : "");
					datostransmitente.setDNIPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getNif() ? personaCot1.getNif() : "");
					datostransmitente.setAPELLIDO2PRIMERCOTITULARTRANSMITENTE(null != personaCot1.getApellido2() ? personaCot1.getApellido2() : "");
					datostransmitente.setNOMBREPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getNombre() ? personaCot1.getNombre() : "");
					datostransmitente.setFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getFechaNacimientoBean() ? personaCot1.getFechaNacimientoBean().toString() : "");
					datostransmitente.setTELEFONOPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getTelefonos() ? personaCot1.getTelefonos() : "");
					datostransmitente.setSEXOPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getSexo() ? personaCot1.getSexo() : "");
					datostransmitente.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getFechaCaducidadAlternativo() ? personaCot1.getFechaCaducidadAlternativo()
							.toString() : "");
					datostransmitente.setFECHACADUCIDADNIFPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getFechaCaducidadNif() ? personaCot1.getFechaCaducidadNif().toString() : "");
					datostransmitente.setTIPODOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE(null != personaCot1.getTipoDocumentoAlternativo() ? personaCot1.getTipoDocumentoAlternativo()
							.getValorEnum() : "");
					Direccion direccion = personaCot1.getDireccion();

					if (null != direccion.getIdDireccion()) {
						String sigla = null != direccion.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) : "";

						datostransmitente.setSIGLASDIRECCIONPRIMERCOTITULARTRANSMITENTE(sigla);
						datostransmitente.setNOMBREVIADIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getNombreVia() ? direccion.getNombreVia() : "");
						datostransmitente.setNUMERODIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getNumero() ? direccion.getNumero() : "");
						datostransmitente.setLETRADIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getLetra() ? direccion.getLetra() : "");
						datostransmitente.setESCALERADIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getEscalera() ? direccion.getEscalera() : "");
						datostransmitente.setPISODIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getPlanta() ? direccion.getPlanta() : "");
						datostransmitente.setPUERTADIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getPuerta() ? direccion.getPuerta() : "");
						datostransmitente.setMUNICIPIOPRIMERCOTITULARTRANSMITENTE(null != direccion.getMunicipio() ? direccion.getMunicipio().getNombre() : "");
						datostransmitente.setPUEBLOPRIMERCOTITULARTRANSMITENTE(null != direccion.getPueblo() ? direccion.getPueblo() : "");

						String letraProvincia = null != direccion.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia()
								.getIdProvincia()) : "";

						datostransmitente.setPROVINCIAPRIMERCOTITULARTRANSMITENTE(letraProvincia);
						datostransmitente.setCPPRIMERCOTITULARTRANSMITENTE(null != direccion.getCodPostal() ? direccion.getCodPostal() : "");
						datostransmitente.setBLOQUEDIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getBloque() ? direccion.getBloque() : "");
						datostransmitente.setHMDIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getHm() ? direccion.getHm() : "");
						datostransmitente.setKMDIRECCIONPRIMERCOTITULARTRANSMITENTE(null != direccion.getPuntoKilometrico() ? direccion.getPuntoKilometrico() : "");
					}
				}
			}

			IntervinienteTrafico segundoCotitular = bean.getSegundoCotitularTransmitenteBean();
			if (null != segundoCotitular.getPersona().getNif()) {
				Persona personaCot2 = segundoCotitular.getPersona();

				if (null != personaCot2.getNif()) {
					datostransmitente.setAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getApellido1RazonSocial() ? personaCot2.getApellido1RazonSocial() : "");
					datostransmitente.setDNISEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getNif() ? personaCot2.getNif() : "");
					datostransmitente.setAPELLIDO2SEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getApellido2() ? personaCot2.getApellido2() : "");
					datostransmitente.setNOMBRESEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getNombre() ? personaCot2.getNombre() : "");
					datostransmitente.setFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getFechaNacimientoBean() ? personaCot2.getFechaNacimientoBean().toString() : "");
					datostransmitente.setTELEFONOSEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getTelefonos() ? personaCot2.getTelefonos() : "");
					datostransmitente.setSEXOSEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getSexo() ? personaCot2.getSexo() : "");
					datostransmitente.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getFechaCaducidadAlternativo() ? personaCot2.getFechaCaducidadAlternativo()
							.toString() : "");
					datostransmitente.setFECHACADUCIDADNIFSEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getFechaCaducidadNif() ? personaCot2.getFechaCaducidadNif().toString() : "");
					datostransmitente.setTIPODOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE(null != personaCot2.getTipoDocumentoAlternativo() ? personaCot2.getTipoDocumentoAlternativo()
							.getValorEnum() : "");
					Direccion direccion = personaCot2.getDireccion();

					if (null != direccion.getIdDireccion()) {
						String sigla = null != direccion.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) : "";

						datostransmitente.setSIGLASDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(sigla);
						datostransmitente.setNOMBREVIADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getNombreVia() ? direccion.getNombreVia() : "");
						datostransmitente.setNUMERODIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getNumero() ? direccion.getNumero() : "");
						datostransmitente.setLETRADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getLetra() ? direccion.getLetra() : "");
						datostransmitente.setESCALERADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getEscalera() ? direccion.getEscalera() : "");
						datostransmitente.setPISODIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getPlanta() ? direccion.getPlanta() : "");
						datostransmitente.setPUERTADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getPuerta() ? direccion.getPuerta() : "");
						datostransmitente.setMUNICIPIOSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getMunicipio() ? direccion.getMunicipio().getNombre() : "");
						datostransmitente.setPUEBLOSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getPueblo() ? direccion.getPueblo() : "");

						String letraProvincia = null != direccion.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia()
								.getIdProvincia()) : "";

						datostransmitente.setPROVINCIASEGUNDOCOTITULARTRANSMITENTE(letraProvincia);
						datostransmitente.setCPSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getCodPostal() ? direccion.getCodPostal() : "");
						datostransmitente.setBLOQUEDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getBloque() ? direccion.getBloque() : "");
						datostransmitente.setHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getHm() ? direccion.getHm() : "");
						datostransmitente.setKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(null != direccion.getPuntoKilometrico() ? direccion.getPuntoKilometrico() : "");
					}
				}
			}
			trans.setDATOSTRANSMITENTE(datostransmitente);
			// Puede que el único dato que venga sea el número de titulares
		} else if (bean.getNumTitulares() != null) {
			DATOSTRANSMITENTE datostransmitente = trans.getDATOSTRANSMITENTE();
			datostransmitente.setNUMEROTITULARES(bean.getNumTitulares() != null ? bean.getNumTitulares().toString() : "");
			trans.setDATOSTRANSMITENTE(datostransmitente);
		}

		// DRC@26-09-2012 Incidencia: 2358
		// DATOS ARRENDADOR
		IntervinienteTrafico arrendador = bean.getArrendatarioBean();

		if (null != arrendador.getPersona().getNif()) {
			DATOSARRENDADOR datosarrendador = trans.getDATOSARRENDADOR();
			Persona persona = arrendador.getPersona();

			if (null != persona.getNif()) {
				datosarrendador.setDNIARRENDADOR(null != persona.getNif() ? persona.getNif() : "");

				datosarrendador.setAPELLIDO1RAZONSOCIALARRENDADOR(null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "");
				datosarrendador.setAPELLIDO2ARRENDADOR(null != persona.getApellido2() ? persona.getApellido2() : "");
				datosarrendador.setNOMBREARRENDADOR(null != persona.getNombre() ? persona.getNombre() : "");
				datosarrendador.setACTUALIZACIONDOMICILIOARRENDADOR(null != arrendador.getCambioDomicilio() && "true".equals(arrendador.getCambioDomicilio()) ? DecisionTrafico.Si.getValorEnum() : "");
				datosarrendador.setAUTONOMOARRENDADOR(null != arrendador.getAutonomo() && "true".equals(arrendador.getAutonomo()) ? DecisionTrafico.Si.getValorEnum() : "");
				datosarrendador.setCODIGOIAEARRENDADOR(null != arrendador.getCodigoIAE() ? arrendador.getCodigoIAE() : "");
				datosarrendador.setFECHACADUCIDADNIFARRENDADOR(null != persona.getFechaCaducidadNif() ? persona.getFechaCaducidadNif().toString() : "");
				datosarrendador.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOARRENDADOR(null != persona.getFechaCaducidadAlternativo() ? persona.getFechaCaducidadAlternativo().toString() : "");
				datosarrendador.setTIPODOCUMENTOSUSTITUTIVOARRENDADOR(null != persona.getTipoDocumentoAlternativo() ? persona.getTipoDocumentoAlternativo().getValorEnum() : "");

				String nombre = "";
				nombre += arrendador.getPersona().getApellido1RazonSocial() != null ? (arrendador.getPersona().getApellido1RazonSocial() + " ") : "";
				nombre += arrendador.getPersona().getApellido2() != null ? (arrendador.getPersona().getApellido2() + " ") : "";
				nombre += arrendador.getPersona().getNombre() != null ? (", " + arrendador.getPersona().getNombre()) : "";
				nombre = nombre.trim();

				datosarrendador.setNOMBREAPELLIDOSARRENDADOR(nombre);
				// datosarrendador.setNOMBREAPELLIDOSARRENDADOR(
				// (null!=arrendador.getPersona() && null!=arrendador.getPersona().getNombre()?arrendador.getPersona().getNombre():"") +
				// (null!=arrendador.getPersona() && null!=arrendador.getPersona().getApellido1RazonSocial()?arrendador.getPersona().getApellido1RazonSocial():"") +
				// (null!=arrendador.getPersona() && null!=arrendador.getPersona().getApellido2()?arrendador.getPersona().getApellido2():""));
				datosarrendador.setSEXOARRENDADOR(null != persona.getSexo() ? persona.getSexo() : "");
				datosarrendador.setTELEFONOARRENDADOR(null != persona.getTelefonos() ? persona.getTelefonos() : "");
				datosarrendador.setFECHANACIMIENTOARRENDADOR(null != persona.getFechaNacimientoBean() ? persona.getFechaNacimientoBean().toString() : "");

				Direccion direccion = persona.getDireccion();
				if (null != direccion.getIdDireccion()) {
					String sigla = null != direccion.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) : "";

					datosarrendador.setSIGLASDIRECCIONARRENDADOR(sigla);
					datosarrendador.setNOMBREVIADIRECCIONARRENDADOR(null != direccion.getNombreVia() ? direccion.getNombreVia() : "");
					datosarrendador.setNUMERODIRECCIONARRENDADOR(null != direccion.getNumero() ? direccion.getNumero() : "");
					datosarrendador.setLETRADIRECCIONARRENDADOR(null != direccion.getLetra() ? direccion.getLetra() : "");
					datosarrendador.setESCALERADIRECCIONARRENDADOR(null != direccion.getEscalera() ? direccion.getEscalera() : "");
					datosarrendador.setPISODIRECCIONARRENDADOR(null != direccion.getPlanta() ? direccion.getPlanta() : "");
					datosarrendador.setPUERTADIRECCIONARRENDADOR(null != direccion.getPuerta() ? direccion.getPuerta() : "");
					datosarrendador.setMUNICIPIOARRENDADOR(null != direccion.getMunicipio() ? direccion.getMunicipio().getNombre() : "");
					datosarrendador.setPUEBLODIRECCIONARRENDADOR(null != direccion.getPueblo() ? direccion.getPueblo() : "");
					datosarrendador.setBLOQUEDIRECCIONARRENDADOR(null != direccion.getBloque() ? direccion.getBloque() : "");
					datosarrendador.setHMDIRECCIONARRENDADOR(null != direccion.getHm() ? direccion.getHm() : "");
					datosarrendador.setKMDIRECCIONARRENDADOR(null != direccion.getPuntoKilometrico() ? direccion.getPuntoKilometrico() : "");

					String letraProvincia = null != direccion.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia())
							: "";

					datosarrendador.setPROVINCIAARRENDADOR(letraProvincia);
					datosarrendador.setCPARRENDADOR(null != direccion.getCodPostal() ? direccion.getCodPostal() : "");
				}
			}

			IntervinienteTrafico representanteArrendador = bean.getRepresentanteArrendatarioBean();

			if (null != representanteArrendador.getPersona().getNif()) {
				datosarrendador.setDNIREPRESENTANTEARRENDADOR(null != representanteArrendador.getPersona().getNif() ? representanteArrendador.getPersona().getNif() : "");

				String nombre = "";
				nombre += representanteArrendador.getPersona().getApellido1RazonSocial() != null ? (representanteArrendador.getPersona().getApellido1RazonSocial() + " ") : "";
				nombre += representanteArrendador.getPersona().getApellido2() != null ? (representanteArrendador.getPersona().getApellido2() + " ") : "";
				nombre += representanteArrendador.getPersona().getNombre() != null ? (", " + representanteArrendador.getPersona().getNombre()) : "";
				nombre = nombre.trim();

				datosarrendador.setNOMBREAPELLIDOSREPRESENTANTEARRENDADOR(nombre);
				datosarrendador.setDOCUMENTOSREPRESENTANTEARRENDADOR(null != representanteArrendador.getDatosDocumento() ? representanteArrendador.getDatosDocumento() : "");
				datosarrendador.setAPELLIDO1RAZONSOCIALREPRESENTANTEARRENDADOR(null != representanteArrendador.getPersona().getApellido1RazonSocial() ? representanteArrendador.getPersona()
						.getApellido1RazonSocial() : "");
				datosarrendador.setAPELLIDO2REPRESENTANTEARRENDADOR(null != representanteArrendador.getPersona().getApellido2() ? representanteArrendador.getPersona().getApellido2() : "");
				datosarrendador.setNOMBREREPRESENTANTEARRENDADOR(null != representanteArrendador.getPersona().getNombre() ? representanteArrendador.getPersona().getNombre() : "");

				datosarrendador.setFECHACADUCIDADNIFREPRESENTANTEARRENDADOR(null != representanteArrendador.getPersona().getFechaCaducidadNif() ? representanteArrendador.getPersona()
						.getFechaCaducidadNif().toString() : "");
				datosarrendador.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR(null != representanteArrendador.getPersona().getFechaCaducidadAlternativo() ? representanteArrendador
						.getPersona().getFechaCaducidadAlternativo().toString() : "");
				datosarrendador.setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTEARRENDADOR(null != representanteArrendador.getPersona().getTipoDocumentoAlternativo() ? representanteArrendador.getPersona()
						.getTipoDocumentoAlternativo().getValorEnum() : "");
				datosarrendador.setCONCEPTOREPRESENTANTEARRENDADOR(null != representanteArrendador.getConceptoRepre() ? representanteArrendador.getConceptoRepre().getNombreEnum() : "");
				datosarrendador.setMOTIVOREPRESENTANTEARRENDADOR(null != representanteArrendador.getIdMotivoTutela() ? representanteArrendador.getIdMotivoTutela().getValorEnum() : "");
				datosarrendador.setFECHAINICIOTUTELAARRENDADOR(null != representanteArrendador.getFechaInicio() ? representanteArrendador.getFechaInicio().toString() : "");
				datosarrendador.setFECHAFINTUTELAARRENDADOR(null != representanteArrendador.getFechaFin() ? representanteArrendador.getFechaFin().toString() : "");

				// datosarrendador.setNOMBREAPELLIDOSREPRESENTANTEARRENDADOR(
				// (null!=representanteArrendador.getPersona() && null!=representanteArrendador.getPersona().getNombre()?representanteArrendador.getPersona().getNombre():"") +
				// (null!=representanteArrendador.getPersona() && null!=representanteArrendador.getPersona().getApellido1RazonSocial()?representanteArrendador.getPersona().getApellido1RazonSocial():"") +
				// (null!=representanteArrendador.getPersona() && null!=representanteArrendador.getPersona().getApellido2()?representanteArrendador.getPersona().getApellido2():""));
			}
			trans.setDATOSARRENDADOR(datosarrendador);
		}

		// DATOS COMPRA-VENTA
		IntervinienteTrafico compraventa = bean.getPoseedorBean();

		if (null != compraventa.getPersona().getNif()) {
			DATOSCOMPRAVENTA datoscompraventa = trans.getDATOSCOMPRAVENTA();
			Persona persona = compraventa.getPersona();

			if (null != persona.getNif()) {
				datoscompraventa.setDNICOMPRAVENTA(null != persona.getNif() ? persona.getNif() : "");

				datoscompraventa.setAPELLIDO1RAZONSOCIALCOMPRAVENTA(null != persona.getApellido1RazonSocial() ? persona.getApellido1RazonSocial() : "");
				datoscompraventa.setAPELLIDO2COMPRAVENTA(null != persona.getApellido2() ? persona.getApellido2() : "");
				datoscompraventa.setNOMBRECOMPRAVENTA(null != persona.getNombre() ? persona.getNombre() : "");
				datoscompraventa.setACTUALIZACIONDOMICILIOCOMPRAVENTA(null != compraventa.getCambioDomicilio() && "true".equals(compraventa.getCambioDomicilio()) ? DecisionTrafico.Si.getValorEnum()
						: "");
				datoscompraventa.setAUTONOMOCOMPRAVENTA(null != compraventa.getAutonomo() && "true".equals(compraventa.getAutonomo()) ? DecisionTrafico.Si.getValorEnum() : "");
				datoscompraventa.setCODIGOIAECOMPRAVENTA(null != compraventa.getCodigoIAE() ? compraventa.getCodigoIAE() : "");

				String nombre = "";
				nombre += compraventa.getPersona().getApellido1RazonSocial() != null ? (compraventa.getPersona().getApellido1RazonSocial() + " ") : "";
				nombre += compraventa.getPersona().getApellido2() != null ? (compraventa.getPersona().getApellido2() + " ") : "";
				nombre += compraventa.getPersona().getNombre() != null ? (", " + compraventa.getPersona().getNombre()) : "";
				nombre = nombre.trim();

				datoscompraventa.setNOMBREAPELLIDOSCOMPRAVENTA(nombre);
				// datoscompraventa.setNOMBREAPELLIDOSCOMPRAVENTA(
				// (null!=compraventa.getPersona() && null!=compraventa.getPersona().getNombre()?compraventa.getPersona().getNombre():"") +
				// (null!=compraventa.getPersona() && null!=compraventa.getPersona().getApellido1RazonSocial()?compraventa.getPersona().getApellido1RazonSocial():"") +
				// (null!=compraventa.getPersona() && null!=compraventa.getPersona().getApellido2()?compraventa.getPersona().getApellido2():""));
				datoscompraventa.setSEXOCOMPRAVENTA(null != persona.getSexo() ? persona.getSexo() : "");
				datoscompraventa.setTELEFONOCOMPRAVENTA(null != persona.getTelefonos() ? persona.getTelefonos() : "");
				datoscompraventa.setFECHANACIMIENTOCOMPRAVENTA(null != persona.getFechaNacimientoBean() ? persona.getFechaNacimientoBean().toString() : "");
				datoscompraventa.setFECHACADUCIDADNIFCOMPRAVENTA(null != persona.getFechaCaducidadNif() ? persona.getFechaCaducidadNif().toString() : "");
				datoscompraventa.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOCOMPRAVENTA(null != persona.getFechaCaducidadAlternativo() ? persona.getFechaCaducidadAlternativo().toString() : "");
				datoscompraventa.setTIPODOCUMENTOSUSTITUTIVOCOMPRAVENTA(null != persona.getTipoDocumentoAlternativo() ? persona.getTipoDocumentoAlternativo().getValorEnum() : "");

				Direccion direccion = persona.getDireccion();
				if (null != direccion.getIdDireccion()) {
					String sigla = null != direccion.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccion.getTipoVia().getIdTipoVia()) : "";

					datoscompraventa.setSIGLASDIRECCIONCOMPRAVENTA(sigla);
					datoscompraventa.setNOMBREVIADIRECCIONCOMPRAVENTA(null != direccion.getNombreVia() ? direccion.getNombreVia() : "");
					datoscompraventa.setNUMERODIRECCIONCOMPRAVENTA(null != direccion.getNumero() ? direccion.getNumero() : "");
					datoscompraventa.setLETRADIRECCIONCOMPRAVENTA(null != direccion.getLetra() ? direccion.getLetra() : "");
					datoscompraventa.setESCALERADIRECCIONCOMPRAVENTA(null != direccion.getEscalera() ? direccion.getEscalera() : "");
					datoscompraventa.setPISODIRECCIONCOMPRAVENTA(null != direccion.getPlanta() ? direccion.getPlanta() : "");
					datoscompraventa.setPUERTADIRECCIONCOMPRAVENTA(null != direccion.getPuerta() ? direccion.getPuerta() : "");
					datoscompraventa.setMUNICIPIOCOMPRAVENTA(null != direccion.getMunicipio() ? direccion.getMunicipio().getNombre() : "");
					datoscompraventa.setPUEBLODIRECCIONCOMPRAVENTA(null != direccion.getPueblo() ? direccion.getPueblo() : "");
					datoscompraventa.setBLOQUEDIRECCIONCOMPRAVENTA(null != direccion.getBloque() ? direccion.getBloque() : "");
					datoscompraventa.setHMDIRECCIONCOMPRAVENTA(null != direccion.getHm() ? direccion.getHm() : "");
					datoscompraventa.setKMDIRECCIONCOMPRAVENTA(null != direccion.getPuntoKilometrico() ? direccion.getPuntoKilometrico() : "");

					String letraProvincia = null != direccion.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia())
							: "";

					datoscompraventa.setPROVINCIACOMPRAVENTA(letraProvincia);
					datoscompraventa.setCPCOMPRAVENTA(null != direccion.getCodPostal() ? direccion.getCodPostal() : "");
				}
			}

			IntervinienteTrafico representanteCompraVenta = bean.getRepresentantePoseedorBean();

			if (null != representanteCompraVenta.getPersona().getNif()) {
				datoscompraventa.setDNIREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getPersona().getNif() ? representanteCompraVenta.getPersona().getNif() : "");

				String nombre = "";
				nombre += representanteCompraVenta.getPersona().getApellido1RazonSocial() != null ? (representanteCompraVenta.getPersona().getApellido1RazonSocial() + " ") : "";
				nombre += representanteCompraVenta.getPersona().getApellido2() != null ? (representanteCompraVenta.getPersona().getApellido2() + " ") : "";
				nombre += representanteCompraVenta.getPersona().getNombre() != null ? (", " + representanteCompraVenta.getPersona().getNombre()) : "";
				nombre = nombre.trim();

				datoscompraventa.setNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA(nombre);

				datoscompraventa.setDOCUMENTOSREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getDatosDocumento() ? representanteCompraVenta.getDatosDocumento() : "");
				datoscompraventa.setAPELLIDO1RAZONSOCIALREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getPersona().getApellido1RazonSocial() ? representanteCompraVenta.getPersona()
						.getApellido1RazonSocial() : "");
				datoscompraventa.setAPELLIDO2REPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getPersona().getApellido2() ? representanteCompraVenta.getPersona().getApellido2() : "");
				datoscompraventa.setNOMBREREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getPersona().getNombre() ? representanteCompraVenta.getPersona().getNombre() : "");

				datoscompraventa.setFECHACADUCIDADNIFREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getPersona().getFechaCaducidadNif() ? representanteCompraVenta.getPersona()
						.getFechaCaducidadNif().toString() : "");
				datoscompraventa.setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getPersona().getFechaCaducidadAlternativo() ? representanteCompraVenta
						.getPersona().getFechaCaducidadAlternativo().toString() : "");
				datoscompraventa.setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getPersona().getTipoDocumentoAlternativo() ? representanteCompraVenta
						.getPersona().getTipoDocumentoAlternativo().getValorEnum() : "");
				datoscompraventa.setCONCEPTOREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getConceptoRepre() ? representanteCompraVenta.getConceptoRepre().getNombreEnum() : "");
				datoscompraventa.setMOTIVOREPRESENTANTECOMPRAVENTA(null != representanteCompraVenta.getIdMotivoTutela() ? representanteCompraVenta.getIdMotivoTutela().getValorEnum() : "");
				datoscompraventa.setFECHAINICIOTUTELACOMPRAVENTA(null != representanteCompraVenta.getFechaInicio() ? representanteCompraVenta.getFechaInicio().toString() : "");
				datoscompraventa.setFECHAFINTUTELACOMPRAVENTA(null != representanteCompraVenta.getFechaFin() ? representanteCompraVenta.getFechaFin().toString() : "");

				// datoscompraventa.setNOMBREAPELLIDOSREPRESENTANTECOMPRAVENTA(
				// (null!=representanteCompraVenta.getPersona() && null!=representanteCompraVenta.getPersona().getNombre()?representanteCompraVenta.getPersona().getNombre():"") +
				// (null!=representanteCompraVenta.getPersona() && null!=representanteCompraVenta.getPersona().getApellido1RazonSocial()?representanteCompraVenta.getPersona().getApellido1RazonSocial():"") +
				// (null!=representanteCompraVenta.getPersona() && null!=representanteCompraVenta.getPersona().getApellido2()?representanteCompraVenta.getPersona().getApellido2():""));
			}
			trans.setDATOSCOMPRAVENTA(datoscompraventa);
		}

		// DATOS 620
		DATOS620 datos620 = trans.getDATOS620();
		VehiculoBean vehiculo = bean.getTramiteTraficoBean().getVehiculo();

		if (null != vehiculo.getIdVehiculo()) {
			datos620.setTIPOVEHICULO(null != vehiculo.getTipVehi() ? utilesConversiones.tipVehiAntiguo(vehiculo.getTipVehi().getValorEnum()) : "");
			datos620.setANIOFABRICACION(null != vehiculo.getAnioFabrica() ? vehiculo.getAnioFabrica() : "");
			datos620.setCARACTERISTICAS(null != vehiculo.getCaracteristicas() ? vehiculo.getCaracteristicas() : "");
			datos620.setCARBURANTE(null != vehiculo.getCarburanteBean() ? vehiculo.getCarburanteBean().getIdCarburante() : "");
			datos620.setCILINDRADA(null != vehiculo.getCilindrada() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(new BigDecimal(vehiculo.getCilindrada()), 7) : "");
			datos620.setFECHAPRIMERAMATRICULACION(null != vehiculo.getFechaPrimMatri() ? vehiculo.getFechaPrimMatri().toString() : "");

			if (null != datos620.getTIPOVEHICULO() && !"".equals(datos620.getTIPOVEHICULO())
					&& ("A".equals(datos620.getTIPOVEHICULO()) || "B".equals(datos620.getTIPOVEHICULO()) || "T".equals(datos620.getTIPOVEHICULO()) || "X".equals(datos620.getTIPOVEHICULO()))) {
				datos620.setMARCA620(null != vehiculo.getCdMarca() ? vehiculo.getCdMarca() : "");
				datos620.setMARCA620DESC("");
				datos620.setMODELO620(null != vehiculo.getCdModVeh() ? vehiculo.getCdModVeh() : "");
				datos620.setMODELO620DESC("");
			} else {
				datos620.setMARCA620("");
				datos620.setMARCA620DESC(null != vehiculo.getCdMarca() ? vehiculo.getCdMarca() : "");
				datos620.setMODELO620("");
				datos620.setMODELO620DESC(null != vehiculo.getCdModVeh() ? vehiculo.getCdModVeh() : "");
			}

			datos620.setNUMCILINDROS(null != vehiculo.getNumCilindros() ? vehiculo.getNumCilindros().toString() : "");
			datos620.setPOTENCIAFISCAL(null != vehiculo.getPotenciaFiscal() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(vehiculo.getPotenciaFiscal(), 5) : "");

			// Más datos del vehículo
			DATOSVEHICULO datosVehiculo = new DATOSVEHICULO();

			datosVehiculo.setCONCEPTOITV(null != vehiculo.getConceptoItv() ? vehiculo.getConceptoItv().toUpperCase() : "");
			datosVehiculo.setDECLARACIONRESPONSABILIDAD(null != vehiculo.getConsentimiento() ? ("true".equals(vehiculo.getConsentimiento()) ? DecisionTrafico.Si.getValorEnum() : DecisionTrafico.No
					.getValorEnum()) : "");
			datosVehiculo.setESTACIONITV(null != vehiculo.getEstacionItv() ? vehiculo.getEstacionItv() : "");
			datosVehiculo.setFECHACADUCIDADITV(null != vehiculo.getFechaItv() ? vehiculo.getFechaItv().toString() : "");
			datosVehiculo.setFECHAITV(null != vehiculo.getFechaInspeccion() ? vehiculo.getFechaInspeccion().toString() : "");
			datosVehiculo.setFECHAMATRICULACION(null != vehiculo.getFechaMatriculacion() ? vehiculo.getFechaMatriculacion().toString() : "");
			datosVehiculo.setMARCA(null != vehiculo.getMarcaBean() ? vehiculo.getMarcaBean().getMarca() : "");
			datosVehiculo.setMODELO(null != vehiculo.getModelo() ? vehiculo.getModelo() : "");
			datosVehiculo.setMODOADJUDICACION(null != bean.getModoAdjudicacion() ? bean.getModoAdjudicacion().toString() : "");
			datosVehiculo.setMOTIVOITV(null != vehiculo.getMotivoItv() ? vehiculo.getMotivoItv().getIdMotivo() : "");
			datosVehiculo.setNUMEROBASTIDOR(null != vehiculo.getBastidor() ? vehiculo.getBastidor() : "");
			// datosVehiculo.setSERVICIODESTINO(null!=vehiculo.getServicioTraficoBean()?utilesConversiones.servicioIdBDtoCodXML(vehiculo.getServicioTraficoBean().getIdServicio()):"");
			datosVehiculo.setSERVICIOANTERIOR(null != vehiculo.getServicioTraficoBean() ? vehiculo.getServicioTraficoAnteriorBean().getIdServicio() : "");
			datosVehiculo.setSERVICIO(null != vehiculo.getServicioTraficoBean() ? vehiculo.getServicioTraficoBean().getIdServicio() : "");
			datosVehiculo.setTIPOTRANSFERENCIA(null != bean.getTipoTransferencia() ? bean.getTipoTransferencia().toString() : "");
			datosVehiculo.setTIPOIDVEHICULO(null != bean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo() ? bean.getTramiteTraficoBean().getVehiculo()
					.getTipoVehiculoBean().getTipoVehiculo() : "");

			// DRC@24-09-2012 Incidencia: 2358
			datosVehiculo.setTARA(null != bean.getTramiteTraficoBean().getVehiculo().getTara() ? bean.getTramiteTraficoBean().getVehiculo().getTara() : "");
			datosVehiculo.setPESOMMA(null != bean.getTramiteTraficoBean().getVehiculo().getPesoMma() ? bean.getTramiteTraficoBean().getVehiculo().getPesoMma() : "");
			datosVehiculo.setPLAZAS(null != bean.getTramiteTraficoBean().getVehiculo().getPlazas() ? bean.getTramiteTraficoBean().getVehiculo().getPlazas().toString() : "");
			datosVehiculo.setKILOMETRAJE(null != bean.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros() ? bean.getTramiteTraficoBean().getVehiculo()
					.getVehiculoTramiteTraficoBean().getKilometros().toString() : "");
			if (bean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia() != null
					&& !bean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia().equals("-1")) {
				String siglaProvincia = "";
				// Hay que convertir al formato tipo provincia para guardarlo en el xml. Al igual que cuando se importe se convertirá a número.
				// Esto es un poco chapuza. Se quitará cuando se pase el campo provincia primera matricula a String.
				if (Integer.parseInt(bean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia()) > 0
						&& Integer.parseInt(bean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia()) < 10) {
					siglaProvincia = utilesConversiones.getSiglasFromIdProvincia("0" + bean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia());
				} else {
					siglaProvincia = utilesConversiones.getSiglasFromIdProvincia(bean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia());
				}
				datosVehiculo.setPROVINCIAPRIMERAMATRICULA(siglaProvincia);
			}
			// DRC@26-02-2013 Incidencia: 3658
			datosVehiculo.setCHECKCADUCIDADITV(null != vehiculo.getCheckFechaCaducidadITV() ? ("true".equals(vehiculo.getCheckFechaCaducidadITV()) ? DecisionTrafico.Si.getValorEnum()
					: DecisionTrafico.No.getValorEnum()) : "");

			Direccion direccionVehiculo = vehiculo.getDireccionBean();

			if (null != direccionVehiculo.getIdDireccion()) {
				datosVehiculo.setCALLEDIRECCIONVEHICULO(null != direccionVehiculo.getNombreVia() ? direccionVehiculo.getNombreVia() : "");
				datosVehiculo.setNUMERODIRECCIONVEHICULO(null != direccionVehiculo.getNumero() ? direccionVehiculo.getNumero() : "");

				String letraProvincia = null != direccionVehiculo.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccionVehiculo.getMunicipio().getProvincia()
						.getIdProvincia()) : "";
				datosVehiculo.setPROVINCIAVEHICULO(letraProvincia);
				datosVehiculo.setPUEBLOVEHICULO(null != direccionVehiculo.getPueblo() ? direccionVehiculo.getPueblo() : "");
			} else {
				datosVehiculo.setCALLEDIRECCIONVEHICULO("");
				datosVehiculo.setNUMERODIRECCIONVEHICULO("");
				datosVehiculo.setPROVINCIAVEHICULO("");
				datosVehiculo.setPUEBLOVEHICULO("");
			}

			trans.setDATOSVEHICULO(datosVehiculo);
		}
		datos620.setESTADO620(null != bean.getEstado620() ? bean.getEstado620().getValorEnum() : "");
		datos620.setBASEIMPONIBLE(null != bean.getBaseImponible620() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(bean.getBaseImponible620(), 8) : "");
		datos620.setCUOTATRIBUTARIA(null != bean.getCuotaTributaria620() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(bean.getCuotaTributaria620(), 7) : "");
		datos620.setEXENTO(null != bean.getExento620() && "true".equals(bean.getExento620()) ? DecisionTrafico.Si.getValorEnum() : "");
		datos620.setFECHADEVENGO(null != bean.getFechaDevengoItp620() ? bean.getFechaDevengoItp620().toString() : "");
		datos620.setFUNDAMENTOEXENCION(null != bean.getFundamentoExencion620() ? bean.getFundamentoExencion620() : "");
		datos620.setFUNDAMENTONOSUJETO(null != bean.getFundamentoNoSujeccion620() ? bean.getFundamentoNoSujeccion620() : "");
		datos620.setNOSUJETO(null != bean.getNoSujeto620() && "true".equals(bean.getNoSujeto620()) ? DecisionTrafico.Si.getValorEnum() : "");
		datos620.setOFICINALIQUIDADORA(null != bean.getOficinaLiquidadora620().getOficinaLiquidadora() ? bean.getOficinaLiquidadora620().getOficinaLiquidadora() : "");
		datos620.setPORCENTAJEADQUISICION(null != bean.getPorcentajeAdquisicion620() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(bean.getPorcentajeAdquisicion620(), 5) : "");
		datos620.setPORCENTAJEREDUCCIONANUAL(null != bean.getPorcentajeReduccionAnual620() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(bean.getPorcentajeReduccionAnual620(), 5) : "");
		datos620.setTIPOGRAVAMEN(null != bean.getTipoGravamen620() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(bean.getTipoGravamen620(), 5) : "4,00");
		datos620.setVALORDECLARADO(null != bean.getValorDeclarado620() ? utiles.bigDecimalToStringDosDecimalesPuntosComas(bean.getValorDeclarado620(), 8) : "");
		Direccion direccionDistinta = bean.getDireccionBean620();

		if (direccionDistinta.getIdDireccion() != null) {
			DIRECCIONDISTINTAADQUIRIENTE direccionDistintaAdquriente = datos620.getDIRECCIONDISTINTAADQUIRIENTE();
			direccionDistintaAdquriente.setCPDISTINTAADQUIRIENTE(null != direccionDistinta.getCodPostal() ? direccionDistinta.getCodPostal() : "");
			direccionDistintaAdquriente.setESCALERADIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getEscalera() ? direccionDistinta.getEscalera() : "");
			direccionDistintaAdquriente.setLETRADIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getLetra() ? direccionDistinta.getLetra() : "");
			direccionDistintaAdquriente.setMUNICIPIODISTINTAADQUIRIENTE(null != direccionDistinta.getMunicipio() ? direccionDistinta.getMunicipio().getNombre() : "");
			direccionDistintaAdquriente.setNOMBREVIADIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getNombreVia() ? direccionDistinta.getNombreVia() : "");
			direccionDistintaAdquriente.setNUMERODIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getNumero() ? direccionDistinta.getNumero() : "");
			direccionDistintaAdquriente.setPISODIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getPlanta() ? direccionDistinta.getPlanta() : "");
			String letraProvincia = null != direccionDistinta.getMunicipio().getProvincia().getIdProvincia() ? utilesConversiones.getSiglasFromIdProvincia(direccionDistinta.getMunicipio().getProvincia()
					.getIdProvincia()) : "";

			direccionDistintaAdquriente.setPROVINCIADISTINTAADQUIRIENTE(letraProvincia);
			direccionDistintaAdquriente.setPUEBLODISTINTAADQUIRIENTE(null != direccionDistinta.getPueblo() ? direccionDistinta.getPueblo() : "");
			direccionDistintaAdquriente.setPUERTADIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getPuerta() ? direccionDistinta.getPuerta() : "");
			String sigla = null != direccionDistinta.getTipoVia() ? utilesConversiones.getIdTipoViaDGTFromIdTipoVia(direccionDistinta.getTipoVia().getIdTipoVia()) : " ";

			direccionDistintaAdquriente.setSIGLASDIRECCIONDISTINTAADQUIRIENTE(sigla);
			direccionDistintaAdquriente.setBLOQUEDIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getBloque() ? direccionDistinta.getBloque() : "");
			direccionDistintaAdquriente.setHMDIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getHm() ? direccionDistinta.getHm() : "");
			direccionDistintaAdquriente.setKMDIRECCIONDISTINTAADQUIRIENTE(null != direccionDistinta.getPuntoKilometrico() ? direccionDistinta.getPuntoKilometrico() : "");

			datos620.setDIRECCIONDISTINTAADQUIRIENTE(direccionDistintaAdquriente);
		} else {
			datos620.setDIRECCIONDISTINTAADQUIRIENTE(null);
		}

		trans.setDATOS620(datos620);

		// faltan estos por meter
		// DATOS PRESENTACIÓN
		DATOSPRESENTACION datosPresentacion = new DATOSPRESENTACION();
		datosPresentacion.setFECHAPRESENTACION(null != bean.getTramiteTraficoBean().getFechaPresentacion() ? bean.getTramiteTraficoBean().getFechaPresentacion().toString() : "");
		datosPresentacion.setJEFATURAPROVINCIAL(null != bean.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial() ? bean.getTramiteTraficoBean().getJefaturaTrafico()
				.getJefaturaProvincial() : "");
		datosPresentacion.setFECHAFACTURA(null != bean.getTramiteTraficoBean().getFechaFactura() ? bean.getTramiteTraficoBean().getFechaFactura().toString() : "");
		datosPresentacion.setFECHACONTRATO(null != bean.getTramiteTraficoBean().getFechaContrato() ? bean.getTramiteTraficoBean().getFechaContrato().toString() : "");

		/*
		 * Mantis 0003992: 2717. Exdportar datos XML Gilberto Pedroso Tasas Esperando a ver si se dejan o se quitan.-->Se dejan por petición del Cliente.
		 */
		datosPresentacion.setTIPOTASA(null != bean.getTramiteTraficoBean().getTasa() ? bean.getTramiteTraficoBean().getTasa().getTipoTasa() : "");
		datosPresentacion.setCODIGOTASA(null != bean.getTramiteTraficoBean().getTasa() ? bean.getTramiteTraficoBean().getTasa().getCodigoTasa() : "");

		if (bean.getCodigoTasaCambioServicio() != null && bean.getCodigoTasaCambioServicio() != "") {
			datosPresentacion.setCODIGOTASACAMBIO(bean.getCodigoTasaCambioServicio());
			datosPresentacion.setTIPOTASACAMBIO("4.1");// El tipo de tasa para CAMBIO siempre es 4.1
		} else {
			datosPresentacion.setCODIGOTASACAMBIO("");
			datosPresentacion.setTIPOTASACAMBIO("");// El tipo de tasa para CAMBIO siempre es 4.1

		}
		if (bean.getCodigoTasaInforme() != null && bean.getCodigoTasaInforme() != "") {
			datosPresentacion.setCODIGOTASAINFORME(bean.getCodigoTasaInforme());
			datosPresentacion.setTIPOTASAINFORME("4.1");// El tipo de tasa para INFORME siempre es 4.1
		} else {
			datosPresentacion.setCODIGOTASAINFORME("");
			datosPresentacion.setTIPOTASAINFORME("");
		}
		// Pagos ITP
		/*
		 * Mantis 0002977: Notifificaciones - NOTIFICATION CTIT - Gilberto Pedroso Cuando es Notificacion CTIT y viene vacio se deja igual Mantis 0003789: tipo transferencia ENTREGA COMPRAVENTA
		 */
		if (null != bean.getTipoTransferencia()) {
			if (!bean.getTipoTransferencia().getValorEnum().equals("4") && !bean.getTipoTransferencia().getValorEnum().equals("5")) {
				datosPresentacion.setCODIGOELECTRONICOTRANSFERENCIA(null != bean.getCetItp() ? bean.getCetItp() : "00000000");
			} else {
				datosPresentacion.setCODIGOELECTRONICOTRANSFERENCIA(bean.getCetItp());
			}
		}
		datosPresentacion.setEXENTOITP(null != bean.getExentoItp() && "true".equals(bean.getExentoItp()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion.setNOSUJETOITP(null != bean.getNoSujetoItp() && "true".equals(bean.getNoSujetoItp()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion.setMODELOITP(null != bean.getModeloItp() ? bean.getModeloItp() : "");
		datosPresentacion.setNUMAUTOITP(null != bean.getNumAutoItp() ? bean.getNumAutoItp() : "");

		// Pagos ISD
		datosPresentacion.setEXENTOISD(null != bean.getExentoIsd() && "true".equals(bean.getExentoIsd()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion.setNOSUJETOISD(null != bean.getNoSujetoIsd() && "true".equals(bean.getNoSujetoIsd()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion.setMODELOISD(null != bean.getModeloIsd() ? bean.getModeloIsd() : "");
		datosPresentacion.setNUMADJUDICACIONISD(null != bean.getNumAutoIsd() ? bean.getNumAutoIsd() : "");

		// Pagos IEDMT
		datosPresentacion.setCODIGOELECTRONICOMATRICULACION(null != bean.getTramiteTraficoBean().getCemIedtm() ? bean.getTramiteTraficoBean().getCemIedtm() : "");
		datosPresentacion.setEXENTOCEM(null != bean.getTramiteTraficoBean().getExentoCem() && "true".equals(bean.getTramiteTraficoBean().getExentoCem()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion
				.setEXENTOIEDMT(null != bean.getTramiteTraficoBean().getExentoIedtm() && "true".equals(bean.getTramiteTraficoBean().getExentoIedtm()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion.setIDNOSUJECCION06(null != bean.getIdNoSujeccion06() ? bean.getIdNoSujeccion06().getValorEnum() : "");
		datosPresentacion.setIDREDUCCION05(null != bean.getIdReduccion05() ? bean.getIdReduccion05().getValorEnum() : "");
		datosPresentacion.setMODELOIEDMT(null != bean.getTramiteTraficoBean().getModeloIedtm() ? bean.getTramiteTraficoBean().getModeloIedtm() : "");
		datosPresentacion.setNOSUJETOIEDMT(null != bean.getTramiteTraficoBean().getNoSujetoIedtm() && "true".equals(bean.getTramiteTraficoBean().getNoSujetoIedtm()) ? DecisionTrafico.Si
				.getValorEnum() : "");

		//Mantis 25415 ValorReal
		datosPresentacion.setVALORREAL(bean.getValorReal() != null ? bean.getValorReal().toString() : "0");

		datosPresentacion.setALTAIVTM(null != bean.getAltaIvtm() && "true".equals(bean.getAltaIvtm()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion.setDUA(null != bean.getDua() && "true".equals(bean.getDua()) ? DecisionTrafico.Si.getValorEnum() : "");
		datosPresentacion.setNUMEROFACTURA(null != bean.getFactura() ? bean.getFactura() : "");

		// DRC@31-10-2012 Incidenca: 2765
		if (bean != null && bean.getProvinciaCet() != null && bean.getProvinciaCet().getIdProvincia() != null)
			datosPresentacion.setPROVINCIACET(utilesConversiones.getSiglasFromIdProvincia(bean.getProvinciaCet().getIdProvincia()));
		else
			datosPresentacion.setPROVINCIACET("");

		// DRC@25-01-2013 Incidenca: 3437
		if (bean != null && bean.getProvinciaCem() != null && bean.getProvinciaCem().getIdProvincia() != null)
			datosPresentacion.setPROVINCIACEM(utilesConversiones.getSiglasFromIdProvincia(bean.getProvinciaCem().getIdProvincia()));
		else
			datosPresentacion.setPROVINCIACEM("");

		datosPresentacion.setCEMA(tramite != null && tramite.getCema() != null ? tramite.getCema() : "");

		trans.setDATOSPRESENTACION(datosPresentacion);

		cambiarNullsPorVacios(trans);
		return trans;
	}

	/**
	 * Método auxiliar que cambiará de un bean los campos nulos por campos vacíos.
	 * @param objeto
	 */
	private void cambiarNullsPorVacios(Object objeto) {
		if (objeto != null && objeto.getClass() != BigDecimal.class && objeto.getClass() != Timestamp.class) {
			if (objeto == null || "".equals(objeto))
				return;
			Field[] campos = objeto.getClass().getDeclaredFields();
			for (int i = 0; i < campos.length; i++) {
				String parametro = campos[i].toString();
				String[] separar = parametro.split("\\.");
				parametro = separar[separar.length - 1];
				Class clase = dameClasedeParametro(objeto, parametro);
				if (clase == String.class) {
					try {
						Method metodoGet = objeto.getClass().getMethod("get" + parametro.toUpperCase());
						if (metodoGet.invoke(objeto, new Object[0]) == null) {
							Method metodoSet = objeto.getClass().getMethod("set" + parametro.toUpperCase(), String.class);
							metodoSet.invoke(objeto, "");
						}
					} catch (Exception e) {
						try {
							Method metodoGet = objeto.getClass().getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1));
							if (metodoGet.invoke(objeto, new Object[0]) == null) {
								Method metodoSet = objeto.getClass().getMethod("set" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1), String.class);
								metodoSet.invoke(objeto, "");
							}
						} catch (Exception e1) {
							log.error(e);
						}
					}
				} else {
					try {
						Method metodoGet = objeto.getClass().getMethod("get" + parametro.toUpperCase());
						cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
					} catch (Exception e) {
						log.error(e);
						Method metodoGet;
						try {
							metodoGet = objeto.getClass().getMethod("get" + parametro.substring(0, 1).toUpperCase() + parametro.substring(1));
							cambiarNullsPorVacios(metodoGet.invoke(objeto, new Object[0]));
						} catch (Exception e1) {
							log.error(e1);
						}
					}
				}
			}
		}
	}

	/**
	 * Método auxiliar del anterior para obtener la clase un objeto bean.
	 * @param bean
	 * @param nombreParametro
	 * @return
	 */
	private Class<?> dameClasedeParametro(Object bean, String nombreParametro) {
		Object value;
		Object entidad;
		try {
			entidad = bean.getClass().newInstance();
		} catch (Exception e) {
			log.error(e);
			return null;
		}
		try {
			Method getter = null;
			try {
				getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro).toUpperCase());
			} catch (Exception e) {
				log.error(e);
			}
			value = getter.invoke(entidad, new Object[0]);
			// Si el objeto obtenido es NULL, intentamos setearle uno de los parametros esperados
			if (value == null) {
				Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro).toUpperCase(), String.class);
				setter.invoke(entidad, new String(""));
				getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro).toUpperCase());
				value = getter.invoke(entidad, new Object[0]);
			}
			// log.debug("class:"+value.getClass());
			entidad = null;
			return value.getClass();
		} catch (Exception ex) {
			try {
				Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro).toUpperCase(), BigDecimal.class);
				setter.invoke(entidad, new BigDecimal(0));
				Method getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro).toUpperCase());
				value = getter.invoke(entidad, new Object[0]);
				// log.debug("class:"+value.getClass());
				entidad = null;
				return value.getClass();
			} catch (Exception ex2) {
				try {
					Method setter = entidad.getClass().getMethod("set" + String.valueOf(nombreParametro).toUpperCase(), Timestamp.class);
					setter.invoke(entidad, Timestamp.valueOf("2000-01-01 00:00:00"));
					Method getter = entidad.getClass().getMethod("get" + String.valueOf(nombreParametro).toUpperCase());
					value = getter.invoke(entidad, new Object[0]);
					// log.debug("class:"+value.getClass());
					entidad = null;
					return value.getClass();
				} catch (Exception ex3) {
					log.debug("El parametro " + nombreParametro + " no tiene una clase conocida (String, BigDecimal, Timestamp)");
					entidad = null;
					return null;
				}
			}
		}
	}
}