package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.sega.justProf.view.xml.DatosFirmados;
import org.gestoresmadrid.oegam2comun.sega.justProf.view.xml.DatosFirmados.DatosVehiculo;
import org.gestoresmadrid.oegam2comun.sega.justProf.view.xml.DatosFirmados.Titular;
import org.gestoresmadrid.oegam2comun.sega.justProf.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.sega.justProf.view.xml.SolicitudJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.sega.justProf.view.xml.TipoDatoNombre;
import org.gestoresmadrid.oegam2comun.sega.utiles.XMLJustificantesProSegaFactory;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.Persona;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.utiles.XMLPruebaCertificado;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.DocumentosJustificante;
import trafico.utiles.enumerados.MotivoJustificante;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTramiteTraficoJustificante;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildJustifProfSega implements Serializable{

	private static final long serialVersionUID = -3100288808664355086L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildJustifProfSega.class);

	private String TEXTO_LEGAL_JUSTIFICANTES = "LOS DATOS CONSIGNADOS EN ESTE XML SE SUMINISTRAN PARA LA EMISIÓN DE UN JUSTIFICANTE PROFESIONAL";

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioTipoVehiculo servicioTipoVehiculo;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Utiles utiles;

	private Boolean comprobarCaducidadCertificado(String aliasColegiado) {
		boolean esOk = false;
		SolicitudPruebaCertificado solicitudPruebaCertificado = obtenerSolicitudPruebaCertificado(aliasColegiado);
		XMLPruebaCertificado xmlPruebaCertificado = new XMLPruebaCertificado();
		String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma = utilesViafirma.firmarPruebaCertificadoCaducidad(xml, aliasColegiado);
		if (idFirma != null) {
			esOk = true;
		}
		return esOk;
	}

	public SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objctFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objctFactory.createSolicitudPruebaCertificado();
		trafico.beans.jaxb.pruebaCertificado.DatosFirmados datosFirmados = objctFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}

	public ResultadoJustificanteProfesional generarXmlJustiProfesional(TramiteTraficoBean tramite, Persona titular,
			TipoTramiteTrafico tipoTramite, Integer diasValidez, BigDecimal idContrato,MotivoJustificante motivo, DocumentosJustificante documentos, boolean idFuerzasArmadas) {
		ResultadoJustificanteProfesional resultado = new ResultadoJustificanteProfesional(Boolean.FALSE);
		try {
			ObjectFactory objFactory = new ObjectFactory();
			SolicitudJustificanteProfesional solicitudJustificanteProfesional = objFactory.createSolicitudJustificanteProfesional();
			solicitudJustificanteProfesional.setDatosFirmados(objFactory.createDatosFirmados());
			//Datos Firmados
			DatosFirmados datosFirmados = objFactory.createDatosFirmados();
			//Campos simples
			datosFirmados.setDiasValidez(diasValidez);
			datosFirmados.setDocumentos(documentos.getNombreEnum());
			datosFirmados.setMotivo(motivo.getValorEnum());
			datosFirmados.setTipoTramite(TipoTramiteTraficoJustificante.TRANSFERENCIA.getNombreEnum());

			ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);

			datosFirmados.setDOIColegio(contratoDto.getColegioDto().getCif());
			datosFirmados.setNombreColegio(contratoDto.getColegioDto().getNombre());

			//Obtener el colegio del gestor
			datosFirmados.setDOIGestor(contratoDto.getColegiadoDto().getUsuario().getNif());
			datosFirmados.setDOIGestoria(contratoDto.getColegiadoDto().getUsuario().getNif());

			datosFirmados.setNumeroColegiadoGestor(contratoDto.getColegiadoDto().getNumColegiado());

			PersonaDto colegiadoCompleto = servicioPersona.obtenerColegiadoCompleto(tramite.getNumColegiado(), tramite.getIdContrato());

			String nombreGestor = colegiadoCompleto.getNombre() + " " + colegiadoCompleto.getApellido1RazonSocial() + " ";
			if (colegiadoCompleto.getApellido2()!=null){
				nombreGestor = nombreGestor+ " " + colegiadoCompleto.getApellido2();
			}
			datosFirmados.setNombreGestor(nombreGestor);
			String descProvincia = "";
			if(colegiadoCompleto.getDireccionDto() != null && colegiadoCompleto.getDireccionDto().getIdProvincia() != null && !colegiadoCompleto.getDireccionDto().getIdProvincia().isEmpty()){
				descProvincia = servicioDireccion.obtenerNombreProvincia(colegiadoCompleto.getDireccionDto().getIdProvincia());
			}
			datosFirmados.setProvinciaFecha(descProvincia);

			JefaturaTraficoDto jefaturaTraficoDto = servicioJefaturaTrafico.getJefatura(tramite.getJefaturaTrafico().getJefaturaProvincial());

			datosFirmados.setJefatura(jefaturaTraficoDto.getDescripcion());

			datosFirmados.setProvinciaDespachoProfesional(servicioDireccion.obtenerNombreProvincia(contratoDto.getIdProvincia()));

			String direccionContrato = null;
			TipoViaVO tipoVia = servicioTipoVia.getTipoVia(contratoDto.getIdTipoVia());
			if (tipoVia != null) {
				direccionContrato = tipoVia.getIdTipoViaDgt();
			} else {
				direccionContrato = contratoDto.getIdTipoVia();
			}
			direccionContrato += " " + contratoDto.getVia() + " Nº " + contratoDto.getNumero();
			if ("2440".equals(contratoDto.getColegiadoDto().getNumColegiado())) {
				direccionContrato += " - " + servicioDireccion.obtenerNombreMunicipio(contratoDto.getIdMunicipio(), contratoDto.getIdProvincia());
			}
			datosFirmados.setDireccionGestor(direccionContrato);
			datosFirmados.setTextoLegal(TEXTO_LEGAL_JUSTIFICANTES); 
			datosFirmados.setDatosVehiculo(rellenarDatosVehiculos(tramite.getVehiculo(),objFactory));
			datosFirmados.setTitular(rellenarDatosTitular(titular,idFuerzasArmadas,objFactory));
			solicitudJustificanteProfesional.setDatosFirmados(datosFirmados);
			solicitudJustificanteProfesional.setVersion("1.0");
			solicitudJustificanteProfesional.setFirmaGestor(new byte[0]); 

			resultado = realizarFirmaSega(solicitudJustificanteProfesional, contratoDto.getColegiadoDto().getAlias());
			if(!resultado.isError()){
				resultado = validarYGuardarXml(solicitudJustificanteProfesional,tramite.getNumExpediente());
			}
		} catch (Throwable e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el xml para la solicitud de justificantes.");
			log.error("Ha sucedido un error a la hora de generar el xml para la solicitud de justificantes, error: ",e);
		}
		return resultado;
	}

	private Titular rellenarDatosTitular(Persona titularBean, boolean idFuerzasArmadas, ObjectFactory objFactory) {
		Titular titular = new Titular();
		if(idFuerzasArmadas){
			titular.setFA(ConstantesTrafico.PREFIJO_ID_FUERZAS_ARMADAS + titularBean.getFa());
		}else{
			titular.setDOI(titularBean.getNif());
		}
		ObjectFactory objectFactory = new ObjectFactory();
		TipoDatoNombre datosTitular = objectFactory.createTipoDatoNombre();

		if(TipoPersona.Juridica.equals(titularBean.getTipoPersona())){
			datosTitular.setRazonSocial(titularBean.getApellido1RazonSocial());
		} else{
			datosTitular.setNombre(titularBean.getNombre()); 
			datosTitular.setPrimerApellido(titularBean.getApellido1RazonSocial());
			datosTitular.setSegundoApellido(titularBean.getApellido2());
		}

		titular.setDatosNombre(datosTitular);
		titular.setDireccion(titularBean.getDireccion().getTipoVia().getIdTipoVia()+ " "  + 
				titularBean.getDireccion().getNombreVia()	+ " Nº " + titularBean.getDireccion().getNumero());
		if(idFuerzasArmadas && titularBean.getFa() != null && !titularBean.getFa().equals("")){
			titular.setFA(ConstantesTrafico.PREFIJO_ID_FUERZAS_ARMADAS + titularBean.getFa());
		}else{
			titular.setDOI(titularBean.getNif());
		}

		titular.setMunicipio(servicioDireccion.obtenerNombreMunicipio(titularBean.getDireccion().getMunicipio().getIdMunicipio(),
				titularBean.getDireccion().getMunicipio().getProvincia().getIdProvincia()));
		return titular;
	}

	private DatosVehiculo rellenarDatosVehiculos(VehiculoBean vehiculo, ObjectFactory objFactory) {
		DatosVehiculo datosVehiculo = objFactory.createDatosFirmadosDatosVehiculo();

		datosVehiculo.setMarca(servicioVehiculo.obtenerNombreMarca(vehiculo.getCdMarca(), true));
		datosVehiculo.setMatricula(vehiculo.getMatricula());
		datosVehiculo.setBastidor(vehiculo.getBastidor());
		datosVehiculo.setModelo(vehiculo.getModelo());
		datosVehiculo.setTipo(servicioVehiculo.obtenerTipoVehiculoDescripcion(vehiculo.getTipoVehiculoBean().getTipoVehiculo()));
		//---
		return datosVehiculo;
	}

	public ResultadoJustificanteProfesional realizarFirmaSega(SolicitudJustificanteProfesional solicitudJustificanteProfesional, String alias) {
		ResultadoJustificanteProfesional resultFirma = new ResultadoJustificanteProfesional(Boolean.FALSE);
		try {
			Boolean noEsCert = comprobarCaducidadCertificado(alias);
			if(noEsCert){
				String xmlAfirmar = getXmlAFirmar(solicitudJustificanteProfesional);
				UtilesViafirma utilesViafirma = new UtilesViafirma();
				byte[] datosAfirmar = new String(xmlAfirmar.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
				byte[] xmlFirmado = utilesViafirma.firmarJustificanteProfesional(datosAfirmar, alias);
				if (xmlFirmado != null) {
					solicitudJustificanteProfesional.setFirmaGestor(xmlFirmado);
				} else {
					resultFirma.setError(Boolean.TRUE);
					resultFirma.setMensajeError("Ha sucedido un error a la hora de firmar el justificante.");
				}
			}else{
				resultFirma.setError(Boolean.TRUE);
				resultFirma.setMensajeError("No se puede generar el justificante porque el certificado del colegiado se encuentra actualmente caducado");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la firma de justificantes, error: ",e);
			resultFirma.setError(Boolean.TRUE);
			resultFirma.setMensajeError("Ha sucedido un error a la hora de realizar la firma.");
		}
		return resultFirma;
	}

	private String getXmlAFirmar(SolicitudJustificanteProfesional solicitudJustificanteProfesional) throws OegamExcepcion {
		XMLJustificantesProSegaFactory factory = new XMLJustificantesProSegaFactory();
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

	public ResultadoJustificanteProfesional validarYGuardarXml(SolicitudJustificanteProfesional solicitudJustificanteProfesional, BigDecimal numExpediente) {
		ResultadoJustificanteProfesional resultado = new ResultadoJustificanteProfesional(Boolean.FALSE);
		String nombreXml = ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + numExpediente;
		File fichero = new File(nombreXml);
		try {
			XMLJustificantesProSegaFactory factory =  new XMLJustificantesProSegaFactory();
			Marshaller marshaller = (Marshaller) factory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudJustificanteProfesional, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				factory. validarJustificanteProfesional(fichero);
			} catch (OegamExcepcion e) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Error en la validacion del justificante: " + e.getMensajeError1());
				if(fichero.exists()){
					fichero.delete();
				}
				return resultado;
			}
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.ENVIO,
					Utilidades.transformExpedienteFecha(numExpediente), nombreXml ,
					ConstantesGestorFicheros.EXTENSION_XML, new XMLJustificantesProSegaFactory().toXML(solicitudJustificanteProfesional).getBytes("UTF-8"));
			resultado.setNombreXML(nombreXml);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de validar y guardar el xml del justificante profesional, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el justificante profesional.");
		}
		if(fichero.exists()){
			fichero.delete();
		}
		return resultado;
	}

}