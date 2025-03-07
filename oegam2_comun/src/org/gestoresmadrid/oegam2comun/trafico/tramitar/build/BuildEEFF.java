package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.utils.XmlConsultaEeffFactory;
import org.gestoresmadrid.oegam2comun.trafico.eeff.utils.XmlEeffFactory;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ResultadoConsultaEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Asunto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.DatosEspecificos.Operaciones;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.DatosEspecificos.Representacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Destino;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Operacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.Remitente;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.xml.TipoDatos;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.beans.jaxb.consultaEEFF.RespuestaEEFF;
import trafico.beans.jaxb.pruebaCertificado.DatosFirmados;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildEEFF implements Serializable{

	private static final long serialVersionUID = -7024449134959476416L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildEEFF.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	public SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias) {
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objectFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objectFactory.createSolicitudPruebaCertificado();
		DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}

	public SolicitudRegistroEntrada obtenerSolicitudLiberacionEEFF(LiberacionEEFFVO eeffLiberacionVO, IntervinienteTraficoDto titularDto, ContratoDto contratoDto) {
		ObjectFactory objectFactory = new ObjectFactory();
		SolicitudRegistroEntrada solicitudRegistroEntrada = objectFactory.createSolicitudRegistroEntrada();
		solicitudRegistroEntrada.setVersion("3.0");
		solicitudRegistroEntrada.setDatosFirmados(objectFactory.createDatosFirmados());
		solicitudRegistroEntrada.getDatosFirmados().setDatosGenericos(objectFactory.createDatosGenericos());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setRemitente(rellenarRemitente(contratoDto,objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setAsunto(rellenarAsunto(objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setDestino(rellenarDestino(objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().setDatosEspecificos(objectFactory.createDatosEspecificos());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setOperaciones(rellenarOperacionesLiberacion(eeffLiberacionVO,titularDto,objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setRepresentacion(new Representacion());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getRepresentacion().setRepresentado(eeffLiberacionVO.getNifRepresentado());
		return solicitudRegistroEntrada;
	}

	private Operaciones rellenarOperacionesLiberacion(LiberacionEEFFVO eeffLiberacionVO, IntervinienteTraficoDto titularDto, ObjectFactory objectFactory) {
		Operaciones operaciones = objectFactory.createDatosEspecificosOperaciones();
		Operacion operacion = new Operacion();
		operacion.setCodigoOperacion("EEFF_LIBERACION");
		operacion.setDatos(rellenarDatosLiberacion(eeffLiberacionVO,titularDto,objectFactory));
		operacion.setFir(objectFactory.createTipoFIR());
		operacion.getFir().setCif(eeffLiberacionVO.getFirCif());
		operacion.getFir().setMarca(eeffLiberacionVO.getFirMarca());
		operacion.setTarjeta(objectFactory.createTipoTarjeta());
		operacion.getTarjeta().setBastidor(eeffLiberacionVO.getTarjetaBastidor());
		operacion.getTarjeta().setNive(eeffLiberacionVO.getTarjetaNive());
		operaciones.setOperacion(operacion);
		return operaciones;
	}

	private TipoDatos rellenarDatosLiberacion(LiberacionEEFFVO eeffLiberacionVO, IntervinienteTraficoDto titularDto, ObjectFactory objectFactory) {
		TipoDatos tipoDatos = objectFactory.createTipoDatos();
		tipoDatos.setDiligencia(objectFactory.createTipoDatosDiligencia());

		tipoDatos.getDiligencia().setDiDireccion(objectFactory.createDireccion());
		if(titularDto.getDireccion() != null){
			String municipio = "";
			if(titularDto.getDireccion().getIdTipoVia() != null && !titularDto.getDireccion().getIdTipoVia().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setTipoVia(titularDto.getDireccion().getIdTipoVia());
			}
			if(titularDto.getDireccion().getNombreVia() != null && !titularDto.getDireccion().getNombreVia().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setNombreVia(titularDto.getDireccion().getNombreVia());
			}
			if(titularDto.getDireccion().getNumero() != null && !titularDto.getDireccion().getNumero().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setNum(titularDto.getDireccion().getNumero());
			}
			if(titularDto.getDireccion().getIdProvincia() != null && !titularDto.getDireccion().getIdProvincia().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setProvincia(titularDto.getDireccion().getIdProvincia());
				municipio = titularDto.getDireccion().getIdProvincia();
			}
			if(titularDto.getDireccion().getIdMunicipio() != null && !titularDto.getDireccion().getIdMunicipio().isEmpty()){
				municipio += titularDto.getDireccion().getIdMunicipio();
				tipoDatos.getDiligencia().getDiDireccion().setMunicipio(municipio);
			}
			if(titularDto.getDireccion().getBloque() != null && !titularDto.getDireccion().getBloque().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setBloque(titularDto.getDireccion().getBloque());
			}
			if(titularDto.getDireccion().getCodPostal() != null && !titularDto.getDireccion().getCodPostal().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setCodigoPostal(titularDto.getDireccion().getCodPostal());
			} else if(titularDto.getDireccion().getCodPostalCorreos() != null && !titularDto.getDireccion().getCodPostalCorreos().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setCodigoPostal(titularDto.getDireccion().getCodPostalCorreos());
			}
			if(titularDto.getDireccion().getEscalera() != null && !titularDto.getDireccion().getEscalera().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setEscalera(titularDto.getDireccion().getEscalera());
			}
			if(titularDto.getDireccion().getPlanta() != null && !titularDto.getDireccion().getPlanta().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setPiso(titularDto.getDireccion().getPlanta());
			}
			if(titularDto.getDireccion().getPuerta() != null && !titularDto.getDireccion().getPuerta().isEmpty()){
				tipoDatos.getDiligencia().getDiDireccion().setPuerta(titularDto.getDireccion().getPuerta());
			}
		}
		if(titularDto.getPersona() != null){
			tipoDatos.getDiligencia().setDiDni(titularDto.getPersona().getNif());
			tipoDatos.getDiligencia().setDiNombre(titularDto.getPersona().getNombre());
			tipoDatos.getDiligencia().setDiPrimerApellidoRazonSocial(titularDto.getPersona().getApellido1RazonSocial());
			tipoDatos.getDiligencia().setDiSegundoApellido(titularDto.getPersona().getApellido2());
		}
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		tipoDatos.getDiligencia().setDiFechaFactura(dataFormat.format(eeffLiberacionVO.getFechaFactura()));
		tipoDatos.getDiligencia().setDiImporte(eeffLiberacionVO.getImporte());
		tipoDatos.getDiligencia().setDiNumeroFactura(eeffLiberacionVO.getNumFactura());
		return tipoDatos;
	}

	private Destino rellenarDestino(ObjectFactory objectFactory) {
		Destino destino = objectFactory.createDestino();
		destino.setCodigo("101001");
		destino.setDescripcion("DGT-Vehículos");
		return destino;
	}

	private Asunto rellenarAsunto(ObjectFactory objectFactory) {
		Asunto asunto = objectFactory.createAsunto();
		asunto.setCodigo("OBCT");
		asunto.setDescripcion("Operaciones Básicas de gestión de Custodio virtual de Tarjetas ITV");
		return asunto;
	}

	private Remitente rellenarRemitente(ContratoDto contratoDto, ObjectFactory objectFactory) {
		Remitente remitente = objectFactory.createRemitente();
		int inicioNombre = contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().indexOf(", ");
		String nombre = contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().substring(inicioNombre + 2, contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().length()); 
		String apellidos = contratoDto.getColegiadoDto().getUsuario().getApellidosNombre().substring(0,inicioNombre);
		remitente.setNombre(nombre);
		remitente.setApellidos(apellidos);
		remitente.setDocumentoIdentificacion(objectFactory.createRemitenteDocumentoIdentificacion());
		remitente.getDocumentoIdentificacion().setNumero(contratoDto.getCif());
		remitente.setCorreoElectronico(contratoDto.getCorreoElectronico());
		return remitente;
	}

	private Remitente rellenarRemitenteConsultaEEFF(ContratoVO contrato, ObjectFactory objectFactory) {
		Remitente remitente = objectFactory.createRemitente();
		int inicioNombre = contrato.getColegiado().getUsuario().getApellidosNombre().indexOf(", ");
		String nombre = contrato.getColegiado().getUsuario().getApellidosNombre().substring(inicioNombre + 2, contrato.getColegiado().getUsuario().getApellidosNombre().length()); 
		String apellidos = contrato.getColegiado().getUsuario().getApellidosNombre().substring(0,inicioNombre);
		remitente.setNombre(nombre);
		remitente.setApellidos(apellidos);
		remitente.setDocumentoIdentificacion(objectFactory.createRemitenteDocumentoIdentificacion());
		remitente.getDocumentoIdentificacion().setNumero(contrato.getCif());
		remitente.setCorreoElectronico(contrato.getCorreoElectronico());
		return remitente;
	}

	public ResultBean realizarFirmaLiberacion(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias, BigDecimal numExpediente) {
		ResultBean resultFirma = new ResultBean(Boolean.FALSE);
		try {
			String xmlAfirmar = getXmlAFirmar(solicitudRegistroEntrada);
			if(xmlAfirmar != null && !xmlAfirmar.isEmpty()){
				UtilesViafirma utilesViafirma = new UtilesViafirma();
				String xmlFirmado = utilesViafirma.firmarEEFF(xmlAfirmar.getBytes("UTF-8"), alias);
				if(xmlFirmado != null && !xmlFirmado.isEmpty()){
					String nombreXml = "EEFFLIBERACION_"+ numExpediente;
					gestorDocumentos.guardarFichero(ConstantesGestorFicheros.EEFF, ConstantesGestorFicheros.EEFFLIBERACION,
							Utilidades.transformExpedienteFecha(numExpediente), nombreXml ,	ConstantesGestorFicheros.EXTENSION_XML,
							xmlFirmado.getBytes("UTF-8"));
					resultFirma.addAttachment(ServicioEEFFNuevo.NOMBRE_XML, nombreXml);
				} else {
					resultFirma.setError(Boolean.TRUE);
					resultFirma.setMensaje("Ha sucedido un error a la hora de firmar el xml.");
				}
			} else {
				resultFirma.setError(Boolean.TRUE);
				resultFirma.setMensaje("Ha sucedido un error a la hora de generar el xml de la liberacion para su firma.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de firmar el xml de liberacion, error: ",e);
			resultFirma.setError(Boolean.TRUE);
			resultFirma.setMensaje("Ha sucedido un error a la hora de firmar el xml.");
		}
		return resultFirma;
	}

	public ResultBean realizarFirmaConsulta(SolicitudRegistroEntrada solicitudRegistroEntrada, String alias, BigDecimal numExpediente) {
		ResultBean resultFirma = new ResultBean(Boolean.FALSE);
		try {
			String xmlAfirmar = getXmlAFirmar(solicitudRegistroEntrada);
			if(xmlAfirmar != null && !xmlAfirmar.isEmpty()){
				UtilesViafirma utilesViafirma = new UtilesViafirma();
				if(utilesViafirma.firmarPruebaCertificadoCaducidad(xmlAfirmar, alias)==null){
					log.error("Ha sucedido un error a la hora de firmar el xml para realizar la consulta EEFF");
					resultFirma.setError(Boolean.TRUE);
					resultFirma.setMensaje("Ha sucedido un error a la hora de firmar el xml para realizar la consulta EEFF.");
				}
				String xmlFirmado = utilesViafirma.firmarEEFF(xmlAfirmar.getBytes("UTF-8"), alias);
				if(xmlFirmado != null && !xmlFirmado.isEmpty()){
					String nombreXml = "EEFFCONSULTA_"+ numExpediente;
					gestorDocumentos.guardarFichero(ConstantesGestorFicheros.EEFF, ConstantesGestorFicheros.EEFFCONSULTA,
							Utilidades.transformExpedienteFecha(numExpediente), nombreXml ,	ConstantesGestorFicheros.EXTENSION_XML,
							xmlFirmado.getBytes("UTF-8"));
					resultFirma.addAttachment(ServicioEEFFNuevo.NOMBRE_XML, nombreXml);
				} else {
					resultFirma.setError(Boolean.TRUE);
					resultFirma.setMensaje("Ha sucedido un error a la hora de firmar el xml.");
				}
			} else {
				resultFirma.setError(Boolean.TRUE);
				resultFirma.setMensaje("Ha sucedido un error a la hora de generar el xml de la consulta para su firma.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de firmar el xml para realizar la consulta EEFF, error: ",e);
			resultFirma.setError(Boolean.TRUE);
			resultFirma.setMensaje("Ha sucedido un error a la hora de firmar el xml para realizar la consulta EEFF.");
		}
		return resultFirma;
	}

	private String getXmlAFirmar(SolicitudRegistroEntrada solicitudRegistroEntrada) throws JAXBException {
		XmlEeffFactory xmlEeffFactory = new XmlEeffFactory();
		String xml = xmlEeffFactory.toXML(solicitudRegistroEntrada);
		xml = xml.replace("__", "_");
		xml = xml.replaceAll("\n *<", "<");
		return xml;
	}

	public ResultadoConsultaEEFFDto getResultadoConsultaEEFF(BigDecimal numExpediente) {
		RespuestaEEFF respuestaEEFF = getXmlRespuestaConsultaEEFF(numExpediente);
		if(respuestaEEFF != null){
			return convertirRespuestaEEFFtoResultadoDto(respuestaEEFF);
		}
		return null;
	}

	private ResultadoConsultaEEFFDto convertirRespuestaEEFFtoResultadoDto(RespuestaEEFF respuestaEEFF) {
		ResultadoConsultaEEFFDto resultadoConsultaEEFFDto = new ResultadoConsultaEEFFDto();
		if(respuestaEEFF.getInfoErrores() != null){
			if(respuestaEEFF.getInfoErrores().getInfoErrorDTO() != null){
				resultadoConsultaEEFFDto.setCodigoError(respuestaEEFF.getInfoErrores().getInfoErrorDTO().getCodigoError());
				resultadoConsultaEEFFDto.setDescripcionError(respuestaEEFF.getInfoErrores().getInfoErrorDTO().getDescripcionError());
			}
		}else{
			if(respuestaEEFF.getDatossimpleeitv() != null){
				if(respuestaEEFF.getDatossimpleeitv().getBaseeitvdto() != null){
					resultadoConsultaEEFFDto.setTarjetaBastidor(respuestaEEFF.getDatossimpleeitv().getBaseeitvdto().getBastidor());
					resultadoConsultaEEFFDto.setTarjetaNive(respuestaEEFF.getDatossimpleeitv().getBaseeitvdto().getNive());
				}
				resultadoConsultaEEFFDto.setConcesionarioComercial(respuestaEEFF.getDatossimpleeitv().getConcesionarioClienteComercial());
				resultadoConsultaEEFFDto.setCustodiaActual(respuestaEEFF.getDatossimpleeitv().getCustodioActual());
				resultadoConsultaEEFFDto.setCustodioSiguiente(respuestaEEFF.getDatossimpleeitv().getCustodioSiguiente());
				resultadoConsultaEEFFDto.setEstadoFinanciero(respuestaEEFF.getDatossimpleeitv().getDenominacionEstadoFinanciero());
				resultadoConsultaEEFFDto.setNifCliente(respuestaEEFF.getDatossimpleeitv().getDninifNieClienteFinal());
				resultadoConsultaEEFFDto.setEstadoBastidor(respuestaEEFF.getDatossimpleeitv().getEstadoBastidor());
				resultadoConsultaEEFFDto.setFechaFacturaFinal(respuestaEEFF.getDatossimpleeitv().getFechaFacturaFinal());
				resultadoConsultaEEFFDto.setFirCif(respuestaEEFF.getDatossimpleeitv().getFirCif());
				resultadoConsultaEEFFDto.setFirMarca(respuestaEEFF.getDatossimpleeitv().getFIRMarca());
				resultadoConsultaEEFFDto.setImporteFacturaFinal(respuestaEEFF.getDatossimpleeitv().getImporteFacturaFinal());
				resultadoConsultaEEFFDto.setNumeroFacturaFinal(respuestaEEFF.getDatossimpleeitv().getNumeroFacturaFinal());
				resultadoConsultaEEFFDto.setCustodioAnterior(respuestaEEFF.getDatossimpleeitv().getCustodioAnterior());
				resultadoConsultaEEFFDto.setCustodioFinal(respuestaEEFF.getDatossimpleeitv().getCustodioFinal());
				resultadoConsultaEEFFDto.setEntidadCredito(respuestaEEFF.getDatossimpleeitv().getEntidadCredito());
				if(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV() != null){
					resultadoConsultaEEFFDto.setCustodioActualAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioActualAnterior());
					resultadoConsultaEEFFDto.setCustodioAnteriorAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioAnteriorAnterior());
					resultadoConsultaEEFFDto.setCustodioFinalAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioFinalAnterior());
					resultadoConsultaEEFFDto.setCustodioSiguienteAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getCustodioSiguienteAnterior());
					resultadoConsultaEEFFDto.setDenominacioNEstadoFinancieroAnterior(respuestaEEFF.getDatossimpleeitv().getDatosHistoricosITV().getDenominacionEstadoFinancieroAnterior());
				}

				if(respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString() != null){
					String nombre = "";
					String apellidos = "";
					for(int i = 0;i< respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString().size();i++){
						if(i==0){
							nombre = ", " + respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString().get(i);
						}else{
							apellidos += respuestaEEFF.getDatossimpleeitv().getNombreApellidosClienteFinal().getString().get(i) + " ";
						}
					}
					resultadoConsultaEEFFDto.setNombreApellidosCliente(apellidos + nombre);
				}
				List<String> listaDireccionCliente = null;
 				if(respuestaEEFF.getDatossimpleeitv().getDireccionCliente().getString() != null){
 					listaDireccionCliente = new ArrayList<String>();
 					for(int e=0;e<respuestaEEFF.getDatossimpleeitv().getDireccionCliente().getString().size();e++){
						listaDireccionCliente.add(respuestaEEFF.getDatossimpleeitv().getDireccionCliente().getString().get(e));
					}
				} else {
					listaDireccionCliente = Collections.emptyList();
				}
 				resultadoConsultaEEFFDto.setDireccionCliente(listaDireccionCliente);
			}
		}
		return resultadoConsultaEEFFDto;
	}

	private RespuestaEEFF getXmlRespuestaConsultaEEFF(BigDecimal numExpediente) {
		String nombreFicheroResp = "RESPUESTA_WS_" + numExpediente;
		RespuestaEEFF respuestaEEFF = null;
		try {
			File file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesEEFF.EEFF, ConstantesEEFF.EEFF_SUBTIPO_CONSULTA,
					Utilidades.transformExpedienteFecha(numExpediente),nombreFicheroResp,ConstantesGestorFicheros.EXTENSION_XML).getFile();
			if(file != null){
				respuestaEEFF = new XmlConsultaEeffFactory().getRespuestaEEFF(file);
			}
		} catch (OegamExcepcion e) {
			log.error("Error al leer el fichero."+e);
		}
		return respuestaEEFF;
	}

	public SolicitudRegistroEntrada obtenerSolicitudConsultaEEFF(ConsultaEEFFVO consultaEEFFVO) {
		ObjectFactory objectFactory = new ObjectFactory();
		SolicitudRegistroEntrada solicitudRegistroEntrada = objectFactory.createSolicitudRegistroEntrada();
		solicitudRegistroEntrada.setVersion("3.0");
		solicitudRegistroEntrada.setDatosFirmados(objectFactory.createDatosFirmados());
		solicitudRegistroEntrada.getDatosFirmados().setDatosGenericos(objectFactory.createDatosGenericos());
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setRemitente(rellenarRemitenteConsultaEEFF(consultaEEFFVO.getContrato(),objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setAsunto(rellenarAsunto(objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().setDestino(rellenarDestino(objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().setDatosEspecificos(objectFactory.createDatosEspecificos());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setOperaciones(rellenarOperacionesConsulta(consultaEEFFVO,objectFactory));
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setRepresentacion(new Representacion());
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getRepresentacion().setRepresentado(consultaEEFFVO.getNifRepresentado());
		return solicitudRegistroEntrada;
	}

	private Operaciones rellenarOperacionesConsulta(ConsultaEEFFVO consultaEEFFVO, ObjectFactory objectFactory) {
		Operaciones operaciones = objectFactory.createDatosEspecificosOperaciones();
		Operacion operacion = new Operacion();
		operacion.setCodigoOperacion("EEFF_CONSULTA");
		operacion.setFir(objectFactory.createTipoFIR());
		operacion.getFir().setCif(consultaEEFFVO.getFirCif());
		operacion.getFir().setMarca(consultaEEFFVO.getFirMarca());
		operacion.setTarjeta(objectFactory.createTipoTarjeta());
		operacion.getTarjeta().setBastidor(consultaEEFFVO.getTarjetaBastidor());
		operacion.getTarjeta().setNive(consultaEEFFVO.getTarjetaNive());
		operaciones.setOperacion(operacion);
		return operaciones;
	}

}