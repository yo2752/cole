package org.gestoresmadrid.oegamBajas.service.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.DatosColegio;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.DatosExpediente;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.DatosGestor;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.DatosGestoria;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.Indicadores;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.Solicitante;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.Tasas;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosEspecificos.Titular;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosInteresado;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosRemitente;
import org.gestoresmadrid.oegamBajas.btv.view.xml.DatosVehiculo;
import org.gestoresmadrid.oegamBajas.btv.view.xml.ObjectFactory;
import org.gestoresmadrid.oegamBajas.btv.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegamBajas.btv.view.xml.TipoOrganismo;
import org.gestoresmadrid.oegamBajas.btv.view.xml.TipoTextoLegal;
import org.gestoresmadrid.oegamBajas.btv.view.xml.TipoTramite;
import org.gestoresmadrid.oegamBajas.utiles.XmlBTVFactory;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import trafico.beans.jaxb.pruebaCertificado.DatosFirmados;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildBtv implements Serializable{

	private static final long serialVersionUID = 7494088468861935372L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildBtv.class);
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	GestorPropiedades gestorPropiedades;

	public SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objectFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objectFactory.createSolicitudPruebaCertificado();
		DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}


	public SolicitudRegistroEntrada rellenarXmlTramitacionBtv(TramiteTrafBajaVO tramiteTraficoBaja) {
		ObjectFactory objectFactory = new ObjectFactory();
		SolicitudRegistroEntrada solicitudRegistroEntrada = objectFactory.createSolicitudRegistroEntrada();
		solicitudRegistroEntrada.setDatosFirmados(objectFactory.createDatosFirmados());
		/*--------------------- Datos Especificos --------------------------------------*/
		
		solicitudRegistroEntrada.getDatosFirmados().setDatosEspecificos(objectFactory.createDatosEspecificos());
		DatosColegio datosColegio = objectFactory.createDatosEspecificosDatosColegio();
		datosColegio.setId(tramiteTraficoBaja.getContrato().getColegio().getColegio());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosColegio(datosColegio);
		
		DatosExpediente datosExpediente = objectFactory.createDatosEspecificosDatosExpediente();
		datosExpediente.setJefatura(tramiteTraficoBaja.getJefaturaTrafico().getJefatura());
		datosExpediente.setMotivoTransmision(MotivoBaja.convertirMotivosBtvXml(tramiteTraficoBaja.getMotivoBaja()));
		datosExpediente.setTipoTramite(TipoTramite.TIPO_TRAMITE);
		datosExpediente.setFechaTramite(utilesFecha.invertirCadenaFechaSinSeparador(utilesFecha.formatoFecha("dd/MM/yyyy", new Date())));
		if (StringUtils.isBlank(tramiteTraficoBaja.getJefaturaTrafico().getSucursal())) {
			datosExpediente.setSucursal("");
		} else {
			datosExpediente.setSucursal(tramiteTraficoBaja.getJefaturaTrafico().getSucursal());
		}
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosExpediente(datosExpediente);

		DatosGestor datosGestor = objectFactory.createDatosEspecificosDatosGestor();
		datosGestor.setDOI(tramiteTraficoBaja.getContrato().getColegiado().getUsuario().getNif());
		datosGestor.setFiliacion("FISICA");
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosGestor(datosGestor);
		
		DatosGestoria datosGestoria = objectFactory.createDatosEspecificosDatosGestoria();
		datosGestoria.setId(tramiteTraficoBaja.getContrato().getColegiado().getNumColegiado());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosGestoria(datosGestoria);
		
		DatosVehiculo datosVehiculo = objectFactory.createDatosVehiculo();
		if (StringUtils.isNotBlank(tramiteTraficoBaja.getVehiculo().getBastidor())){
			if(tramiteTraficoBaja.getVehiculo().getBastidor().length() > 6){
				int tamBastidor = tramiteTraficoBaja.getVehiculo().getBastidor().length();
				datosVehiculo.setBastidor(tramiteTraficoBaja.getVehiculo().getBastidor().substring(tamBastidor - 6,tamBastidor));
			}else{
				datosVehiculo.setBastidor(tramiteTraficoBaja.getVehiculo().getBastidor());
			}
		} else {
			datosVehiculo.setBastidor("");
		}
		datosVehiculo.setDatosMatriculacion(objectFactory.createDatosMatriculacion());
		datosVehiculo.getDatosMatriculacion().setMatricula(tramiteTraficoBaja.getVehiculo().getMatricula());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosVehiculo(datosVehiculo);
		
		Indicadores indicadores = objectFactory.createDatosEspecificosIndicadores();
		indicadores.setPaisDestino(tramiteTraficoBaja.getPais().getSigla2());
		
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setNumeroExpedienteGestor(tramiteTraficoBaja.getNumExpediente().toString());
		
		Tasas tasas = objectFactory.createDatosEspecificosTasas();
		if(tramiteTraficoBaja.getTasa() != null && tramiteTraficoBaja.getTasa().getCodigoTasa() != null 
				&& !tramiteTraficoBaja.getTasa().getCodigoTasa().isEmpty()){
			tasas.setTasaTramite(tramiteTraficoBaja.getTasa().getCodigoTasa());
		} else {
			tasas.setTasaTramite("");
		}
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTasas(tasas);
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTextoLegal(TipoTextoLegal.TEXTO_LEGAL);
		
		for(IntervinienteTraficoVO intervinienteTraficoVO : tramiteTraficoBaja.getIntervinienteTraficosAsList()){
			if(TipoInterviniente.Titular.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
				Titular titular = objectFactory.createDatosTitular();
				titular.setDoi(intervinienteTraficoVO.getPersona().getId().getNif());
				for(IntervinienteTraficoVO repreTitular : tramiteTraficoBaja.getIntervinienteTraficosAsList()){
					if(TipoInterviniente.RepresentanteTitular.getValorEnum().equals(repreTitular.getId().getTipoInterviniente())){
						titular.setDatosRepresentante(objectFactory.createDatosRepresentante());
						titular.getDatosRepresentante().setDoi(repreTitular.getPersona().getId().getNif());
						break;
					}
				}
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTitular(titular);
			} else if(TipoInterviniente.Adquiriente.getValorEnum().equals(intervinienteTraficoVO.getId().getTipoInterviniente())){
				Solicitante solicitante = objectFactory.createDatosSolicitante();
				solicitante.setDoi(intervinienteTraficoVO.getPersona().getId().getNif());
				for(IntervinienteTraficoVO repreAdquiriente : tramiteTraficoBaja.getIntervinienteTraficosAsList()){
					if(TipoInterviniente.RepresentanteAdquiriente.getValorEnum().equals(repreAdquiriente.getId().getTipoInterviniente())){
						solicitante.setDatosRepresentante(objectFactory.createDatosRepresentante());
						solicitante.getDatosRepresentante().setDoi(repreAdquiriente.getPersona().getId().getNif());
						break;
					}
				}
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setSolicitante(solicitante);
				indicadores.setAcreditacionPosesion("SI");
			}
		}
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setIndicadores(indicadores);
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor("");
		
		/*--------------------- Datos Genericos --------------------------------------*/
		
		solicitudRegistroEntrada.getDatosFirmados().setDatosGenericos(objectFactory.createDatosGenericos());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setAsunto(objectFactory.createAsunto());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setDestino(objectFactory.createDestino());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setOrganismo(TipoOrganismo.DGT);
		
		int coma = tramiteTraficoBaja.getContrato().getColegiado().getUsuario().getApellidosNombre().indexOf(",");
		String nombreColegiado = tramiteTraficoBaja.getContrato().getColegiado().getUsuario().getApellidosNombre().substring(coma + 1,
				tramiteTraficoBaja.getContrato().getColegiado().getUsuario().getApellidosNombre().length());
		String apeColegiado = tramiteTraficoBaja.getContrato().getColegiado().getUsuario().getApellidosNombre().substring(0, coma);
		
		DatosInteresado datosInteresados = objectFactory.createDatosInteresado();
		datosInteresados.setInteresado(objectFactory.createInteresado());
		datosInteresados.getInteresado().setApellidos(apeColegiado);
		datosInteresados.getInteresado().setCorreoElectronico(tramiteTraficoBaja.getContrato().getColegiado().getUsuario().getCorreoElectronico());
		datosInteresados.getInteresado().setDocumentoIdentificacion(objectFactory.createDocumentoIdentificacionInteresado());
		datosInteresados.getInteresado().getDocumentoIdentificacion().setNumero(tramiteTraficoBaja.getContrato().getColegiado().getUsuario().getNif());
		datosInteresados.getInteresado().setNombre(nombreColegiado);
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setInteresados(datosInteresados);
		
		DatosRemitente datosRemitente = objectFactory.createDatosRemitente();
		datosRemitente.setCorreoElectronico(tramiteTraficoBaja.getContrato().getColegio().getCorreoElectronico());
		datosRemitente.setDocumentoIdentificacion(objectFactory.createDocumentoIdentificacionRemitente());
		datosRemitente.getDocumentoIdentificacion().setNumero(tramiteTraficoBaja.getContrato().getColegio().getCif());
		datosRemitente.setNombre(tramiteTraficoBaja.getContrato().getColegio().getNombre());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setRemitente(datosRemitente);
		solicitudRegistroEntrada.setVersion("1.0");
		solicitudRegistroEntrada.setFirma("");
		
		return solicitudRegistroEntrada;
	}

	public ResultadoBajasBean firmaColegiado(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias, BigDecimal numExpediente) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			byte[] xmlFirmado = firmarXml(solicitudRegistroEntrada,alias);
			if (xmlFirmado != null) {
				try {
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(new String(Base64.encodeBase64(xmlFirmado), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					log.error("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegiado para el expediente: " + numExpediente +", error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegiado para el expediente: " + numExpediente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha surgido un error a la hora de generar el xml para firmar el colegiado para el expediente: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de firmar el colegiado el tag de datos especificos para el expediente: " + numExpediente +", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha surgido un error a la hora de firmar el colegiado el tag de datos especificos para el expediente: " + numExpediente);
		}
		return resultado;
	}

	private byte[] firmarXml(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias) throws UnsupportedEncodingException {
		byte[] xmlFirmado = null;
		String xmlAfirmar = getXmlAfirmarPorTag(solicitudRegistroEntrada);
		if (xmlAfirmar != null) {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] datosAfirmar = new String(xmlAfirmar.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			xmlFirmado = utilesViafirma.firmarXmlPorAlias(datosAfirmar, alias);
		}
		return xmlFirmado;
	}
	
	private String getXmlAfirmarPorTag(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		XmlBTVFactory xmlBTVFactory = new XmlBTVFactory();
		String xml = xmlBTVFactory.toXML(solicitudRegistroEntrada);
		// Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");
		// Obtenemos los datos que realmente se tienen que firmar
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
	
	public ResultadoBajasBean firmaColegio(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias, BigDecimal numExpediente) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			byte[] xmlFirmado = firmarXml(solicitudRegistroEntrada, alias);
			if (xmlFirmado != null) {
				try {
					solicitudRegistroEntrada.setFirma(new String(Base64.encodeBase64(xmlFirmado), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					log.error("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegio para el expediente: " + numExpediente + ", error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de codificar en Base 64 el la firma del colegio para el expediente: " + numExpediente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de firmar los datos por parte del colegio para el expediente: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de firmar el colegio la peticion de baja telematica para el expediente: " + numExpediente +", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de firmar el colegio la peticin de baja telematica para el expediente: " + numExpediente);
		}
		return resultado;
	}

	public ResultadoBajasBean validarXml(SolicitudRegistroEntrada solicitudRegistroEntrada, String nombreXml) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		File fichero = new File(nombreXml);
		try {
			XmlBTVFactory xmlBTVFactory = new XmlBTVFactory();
			Marshaller marshaller = (Marshaller) xmlBTVFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream(nombreXml));
			// Lo validamos contra el XSD
			try {
				xmlBTVFactory.validarXMLBTV(fichero);
				resultado.setXml(xmlBTVFactory.toXML(solicitudRegistroEntrada));
			} catch (OegamExcepcion e) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error en la validacion del expediente: " + e.getMensajeError1());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml con el xsd de validaci�n, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el xml con el xsd de validaci�n");
		}
		if(fichero.exists()){
			fichero.delete();
		}
		return resultado;
	}

	public ResultadoBajasBean generarXmlTramitacion(TramiteTrafBajaVO tramiteTrafBajaVO) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			SolicitudRegistroEntrada solicitudRegistroEntrada = rellenarXmlTramitacionBtv(tramiteTrafBajaVO);
			if(solicitudRegistroEntrada != null){
				resultado = firmaColegiado(solicitudRegistroEntrada, tramiteTrafBajaVO.getContrato().getColegiado().getAlias(), tramiteTrafBajaVO.getNumExpediente());
				if(!resultado.getError()){
					resultado = firmaColegio(solicitudRegistroEntrada, gestorPropiedades.valorPropertie("trafico.claves.colegio.alias"), tramiteTrafBajaVO.getNumExpediente());
					if(!resultado.getError()){
						String nombreXml = ConstantesGestorFicheros.TRAMITAR_BTV + "_" + tramiteTrafBajaVO.getNumExpediente();
						resultado = validarXml(solicitudRegistroEntrada, nombreXml);
						if(!resultado.getError()){
							gestorDocumentos.guardarFichero(ConstantesGestorFicheros.TRAMITAR_BTV, ConstantesGestorFicheros.XML_ENVIO, 
									utiles.transformExpedienteFecha(tramiteTrafBajaVO.getNumExpediente()), 
									nombreXml, ConstantesGestorFicheros.EXTENSION_XML, resultado.getXml().getBytes("UTF-8"));
							resultado.setNombreFichero(nombreXml);
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha surgido un error a la hora de rellenar el xml con los datos del tramite: " + tramiteTrafBajaVO.getNumExpediente());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el XML para la tramitacion BTV del expediente: " + tramiteTrafBajaVO.getNumExpediente() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el XML para la tramitacion BTV del expediente: " + tramiteTrafBajaVO.getNumExpediente());
		}
 		return resultado;
	}


	public void borraFicheroSiExisteConExtension(BigDecimal numExpediente, String nombreXml) throws OegamExcepcion {
		gestorDocumentos.borraFicheroSiExisteConExtension(ConstantesGestorFicheros.TRAMITAR_BTV, ConstantesGestorFicheros.XML_ENVIO, utiles.transformExpedienteFecha(numExpediente),
				nombreXml, ConstantesGestorFicheros.EXTENSION_XML);
		
	}
	
}
