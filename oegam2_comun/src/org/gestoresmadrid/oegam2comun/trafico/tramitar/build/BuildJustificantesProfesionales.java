package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.beans.jaxb.pruebaCertificado.DatosFirmados;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.beans.schemas.generated.justificantesProf.es.sea.profproof.tipos.TipoDatoNombre;
import trafico.beans.schemas.generated.justificantesProf.generated.DatosFirmados.DatosVehiculo;
import trafico.beans.schemas.generated.justificantesProf.generated.DatosFirmados.Titular;
import trafico.beans.schemas.generated.justificantesProf.generated.ObjectFactory;
import trafico.beans.schemas.generated.justificantesProf.generated.SolicitudJustificanteProfesional;
import trafico.utiles.XMLJustificantesProFactory;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.DocumentosJustificante;
import trafico.utiles.enumerados.TipoTramiteTraficoJustificante;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildJustificantesProfesionales implements Serializable{

	private static final long serialVersionUID = -3100288808664355086L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildJustificantesProfesionales.class);

	private static final String NOMBRE_XML = "nombreXml";

	private String TEXTO_LEGAL_JUSTIFICANTES = "LOS DATOS CONSIGNADOS EN ESTE XML SE SUMINISTRAN PARA LA EMISIÓN DE UN JUSTIFICANTE PROFESIONAL";

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioTipoVehiculo servicioTipoVehiculo;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Utiles utiles;

	public BuildJustificantesProfesionales() {}

	public SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objectFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objectFactory.createSolicitudPruebaCertificado();
		DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}

	public SolicitudJustificanteProfesional obtenerSolicitudJustificante(JustificanteProfVO justificanteProfVO, ContratoDto contratoDto, IntervinienteTraficoDto titular) {
		SolicitudJustificanteProfesional solicitudJustificanteProfesional = null;

		ObjectFactory objctFactory = new ObjectFactory();
		PersonaDto colegiadoCompleto = servicioPersona.obtenerColegiadoCompleto(justificanteProfVO.getTramiteTrafico().getNumColegiado(), contratoDto.getIdContrato());

		solicitudJustificanteProfesional = objctFactory.createSolicitudJustificanteProfesional();
		solicitudJustificanteProfesional.setDatosFirmados(objctFactory.createDatosFirmados());
		solicitudJustificanteProfesional.setVersion("1.0");

		if(justificanteProfVO.getDiasValidez() == null || justificanteProfVO.getDiasValidez().intValue() > 30){
			solicitudJustificanteProfesional.getDatosFirmados().setDiasValidez((new BigDecimal(ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO)).intValue());
		}else{
			solicitudJustificanteProfesional.getDatosFirmados().setDiasValidez(justificanteProfVO.getDiasValidez().intValue());
		}

		if(justificanteProfVO.getDocumentos()==null){
			solicitudJustificanteProfesional.getDatosFirmados().setDocumentos(DocumentosJustificante.CambioTitular.getNombreEnum());
		}else{
			solicitudJustificanteProfesional.getDatosFirmados().setDocumentos(DocumentosJustificante.convertir(justificanteProfVO.getDocumentos()).getNombreEnum());
		}
		solicitudJustificanteProfesional.getDatosFirmados().setMotivo(justificanteProfVO.getMotivo());

		solicitudJustificanteProfesional.getDatosFirmados().setDOIColegio(contratoDto.getColegioDto().getCif());
		solicitudJustificanteProfesional.getDatosFirmados().setNombreColegio(contratoDto.getColegioDto().getNombre());
		solicitudJustificanteProfesional.getDatosFirmados().setDOIGestor(colegiadoCompleto.getNif());
		solicitudJustificanteProfesional.getDatosFirmados().setDOIGestoria(colegiadoCompleto.getNif());
		solicitudJustificanteProfesional.getDatosFirmados().setNumeroColegiadoGestor(colegiadoCompleto.getNumColegiado());

		String nombreGestor = colegiadoCompleto.getNombre() + " " + colegiadoCompleto.getApellido1RazonSocial() + " ";
		if (colegiadoCompleto.getApellido2() != null){
			nombreGestor = nombreGestor+ " " + colegiadoCompleto.getApellido2();
		}
		solicitudJustificanteProfesional.getDatosFirmados().setNombreGestor(nombreGestor);
		solicitudJustificanteProfesional.getDatosFirmados().setProvinciaFecha(servicioDireccion.obtenerNombreProvincia(colegiadoCompleto.getDireccionDto().getIdProvincia()));
		if(justificanteProfVO.getTramiteTrafico().getJefaturaTrafico().getDescripcion() != null){
			solicitudJustificanteProfesional.getDatosFirmados().setJefatura(justificanteProfVO.getTramiteTrafico().getJefaturaTrafico().getDescripcion());
		} else {
			JefaturaTraficoDto jefaturaTraficoDto = servicioJefaturaTrafico.getJefatura(justificanteProfVO.getTramiteTrafico().getJefaturaTrafico().getJefaturaProvincial());
			if(jefaturaTraficoDto != null){
				solicitudJustificanteProfesional.getDatosFirmados().setJefatura(jefaturaTraficoDto.getDescripcion());
			}
		}
		solicitudJustificanteProfesional.getDatosFirmados().setTipoTramite(TipoTramiteTraficoJustificante.TRANSFERENCIA.getNombreEnum());
		solicitudJustificanteProfesional.getDatosFirmados().setProvinciaDespachoProfesional(servicioDireccion.obtenerNombreProvincia(contratoDto.getIdProvincia()));

		String direccionContrato = null;
		TipoViaVO tipoVia = servicioTipoVia.getTipoVia(contratoDto.getIdTipoVia());
		if (tipoVia != null) {
			direccionContrato = tipoVia.getIdTipoViaDgt();
		} else {
			direccionContrato = contratoDto.getIdTipoVia();
		}
		direccionContrato += " " + contratoDto.getVia() + " Nº " + contratoDto.getNumero();
		if ("2440".equals(justificanteProfVO.getTramiteTrafico().getNumColegiado())) {
			direccionContrato += " - " + servicioDireccion.obtenerNombreMunicipio(contratoDto.getIdMunicipio(), contratoDto.getIdProvincia());
		}
		solicitudJustificanteProfesional.getDatosFirmados().setDireccionGestor(direccionContrato);
		solicitudJustificanteProfesional.getDatosFirmados().setTextoLegal(TEXTO_LEGAL_JUSTIFICANTES); 
		solicitudJustificanteProfesional.getDatosFirmados().setDatosVehiculo(rellenarDatosVehiculos(justificanteProfVO.getTramiteTrafico().getVehiculo()));
		solicitudJustificanteProfesional.getDatosFirmados().setTitular(rellenarDatosTitular(titular));
		solicitudJustificanteProfesional.setFirmaGestor(new byte[0]); 
		return solicitudJustificanteProfesional;
	}

	private Titular rellenarDatosTitular(IntervinienteTraficoDto titularTramite) {
		Titular titular = new Titular();
		if(titularTramite.getPersona().getFa() != null && !titularTramite.getPersona().getFa().isEmpty()){
			titular.setFA(ConstantesTrafico.PREFIJO_ID_FUERZAS_ARMADAS + titularTramite.getPersona().getFa());
		}else{
			titular.setDOI(titularTramite.getPersona().getNif());
		}
		TipoDatoNombre datosTitular = new TipoDatoNombre();
		if(TipoPersona.Juridica.getValorEnum().equals(titularTramite.getPersona().getTipoPersona())){
			datosTitular.setRazonSocial(titularTramite.getPersona().getApellido1RazonSocial());
		} else {
			datosTitular.setNombre(titularTramite.getPersona().getNombre()); 
			datosTitular.setPrimerApellido(titularTramite.getPersona().getApellido1RazonSocial());
			datosTitular.setSegundoApellido(titularTramite.getPersona().getApellido2());
		}
		titular.setDatosNombre(datosTitular);
		String direccionTitular = null;
		TipoViaVO tipoVia = servicioTipoVia.getTipoVia(titularTramite.getDireccion().getIdTipoVia());
		if (tipoVia != null) {
			direccionTitular = tipoVia.getNombre() + " " ;
		} else if(titularTramite.getDireccion().getIdTipoVia() != null && !titularTramite.getDireccion().getIdTipoVia().isEmpty()){
			direccionTitular = titularTramite.getDireccion().getIdTipoVia() + " " ;
		}
		direccionTitular += titularTramite.getDireccion().getNombreVia() + " Nº " + titularTramite.getDireccion().getNumero();
		titular.setDireccion(direccionTitular);
		titular.setMunicipio(servicioDireccion.obtenerNombreMunicipio(titularTramite.getDireccion().getIdMunicipio(), titularTramite.getDireccion().getIdProvincia()));
		return titular;
	}

	private DatosVehiculo rellenarDatosVehiculos(VehiculoVO vehiculoVO) {
		DatosVehiculo datosVehiculo = new DatosVehiculo();
		datosVehiculo.setMarca(servicioVehiculo.obtenerNombreMarca(vehiculoVO.getCodigoMarca(), false));
		datosVehiculo.setMatricula(vehiculoVO.getMatricula());
		datosVehiculo.setBastidor(vehiculoVO.getBastidor());
		datosVehiculo.setModelo(vehiculoVO.getModelo());
		datosVehiculo.setTipo(servicioVehiculo.obtenerTipoVehiculoDescripcion(vehiculoVO.getTipoVehiculo()));
		return datosVehiculo;
	}

	public ResultBean realizarFirma(SolicitudJustificanteProfesional solicitudJustificanteProfesional, String alias) {
		ResultBean resultFirma = new ResultBean(false);
		try {
			String xmlAfirmar = getXmlAFirmar(solicitudJustificanteProfesional);
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] datosAfirmar = new String(xmlAfirmar.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			byte[] xmlFirmado = utilesViafirma.firmarJustificanteProfesional(datosAfirmar, alias);
			if (xmlFirmado != null) {
				solicitudJustificanteProfesional.setFirmaGestor(xmlFirmado);
			} else {
				resultFirma = new ResultBean(true, "Ha sucedido un error a la hora de firmar el justificante.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la firma de justificantes, error: ",e);
			resultFirma.setError(true);
			resultFirma.addMensajeALista("Ha sucedido un error a la hora de realizar la firma.");
		}
		return resultFirma;
	}

	private String getXmlAFirmar(SolicitudJustificanteProfesional solicitudJustificanteProfesional) throws OegamExcepcion {
		XMLJustificantesProFactory factory = new XMLJustificantesProFactory();
		String xml = factory.toXML(solicitudJustificanteProfesional);
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");

		//Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String tagAfirmar = xml.substring(inicio, fin);
		String encodedAFirmar = null;
		try {
			encodedAFirmar = utiles.doBase64Encode(tagAfirmar.getBytes("UTF-8"));
			encodedAFirmar = encodedAFirmar.replaceAll("\n", "");
			if (log.isInfoEnabled()) {
				log.info("BTV ENCODED A FIRMAR: " + encodedAFirmar);
			}
		} catch (Exception e) {
			log.error("Error al codificar en base 64 el UTF-8");
			encodedAFirmar = "";
		}
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		return xmlAFirmar;
	}

	public ResultBean validarYGuardarXml(SolicitudJustificanteProfesional solicitudJustificanteProfesional, BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		String nombreXml = ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + numExpediente;
		File fichero = new File(nombreXml);
		try {
			XMLJustificantesProFactory factory =  new XMLJustificantesProFactory();
			Marshaller marshaller = (Marshaller) factory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudJustificanteProfesional, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				factory.validarJustificanteProfesional(fichero);
			} catch (OegamExcepcion e) {
				resultado = new ResultBean(true, "Error en la validación del justificante: " + e.getMensajeError1());
				if(fichero.exists()){
					fichero.delete();
				}
				return resultado;
			}

			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.ENVIO,
					Utilidades.transformExpedienteFecha(numExpediente), nombreXml ,
					ConstantesGestorFicheros.EXTENSION_XML, new XMLJustificantesProFactory().toXML(solicitudJustificanteProfesional).getBytes("UTF-8"));
			resultado.addAttachment(NOMBRE_XML, nombreXml);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de validar y guardar el xml del justificante profesional, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de generar el justificante profesional.");
		}
		if(fichero.exists()){
			fichero.delete();
		}
		return resultado;
	}

}