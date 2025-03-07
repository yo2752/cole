package org.gestoresmadrid.oegam2comun.trafico.tramitar.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.gestoresmadrid.core.model.enumerados.AcreditacionTrafico;
import org.gestoresmadrid.core.model.enumerados.ModoAdjudicacion;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.Asunto;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosAdquirente;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionActividad;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionDerecho;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionDerecho.FallecimientoDonacion;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionDerecho.MotivoTransmision;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.IEDMT;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.ISD;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.ITP;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionExencion;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionNoSujecion;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.ITP.AcreditacionPago;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.IVTM;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.Arrendatario;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.ConductorHabitual;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.CotitularesTransmitentes;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosAutonomo;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosColegio;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosExpediente;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosGestor;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosGestoria;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosTutela;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosTutela.TutelaAdquirente;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosTutela.TutelaTransmiente;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosVehiculo;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosVehiculo.DatosITV;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosVehiculo.DatosMatriculacion;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.DatosVehiculo.DatosServicio;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.Poseedor;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.Tasas;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosFirmados;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosGenericos;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosGenericos.Interesados;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosInteresado;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonales;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonales.DatosFiliacion.PersonaFisica;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonales.DatosFiliacion.PersonaJuridica;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente.DatosFiliacion;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosRemitente;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosRemitente.DocumentoIdentificacion;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosRepresentante;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosTransmitente;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosTransmitenteConAutonomo;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.Destino;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.Domicilio;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoCEM;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoConsentimiento;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoOrganismo;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoResultadoITV;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoSI;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoSINO;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.TipoTextoLegal;
import org.gestoresmadrid.oegam2comun.sega.utiles.XmlCheckCTITSegaFactory;
import org.gestoresmadrid.oegam2comun.sega.utiles.XmlCtitSegaFactory;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLPruebaCertificado;
import trafico.utiles.enumerados.Paso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildCtitSega implements Serializable{

	private static final long serialVersionUID = 6193854519040558033L;

	private ILoggerOegam log = LoggerOegam.getLogger(BuildCtitSega.class);

	private static final String CADENA_VACIA = "";
	private static final String TEXTO_SOLICITUD = "Solicitud de Cambio de Titularidad de Vehículos Online para Gestores";
	private static final String CTVG = "CTVG";
	private static final String _101001 = "101001";
	private static final String DGT_VEHÍCULOS = "DGT - Vehículos";
	private static final String TRUE = "true";
	private static final String _0 = "0";
	private static final String SN = "sn";
	private static final String ESP = "ESP";
	private static final String _00000000 = "00000000";
	private static final String _1_0 = "1.0";
	private static final String UTF_8 = "UTF-8";
	private static final String ERROR = "ERROR";

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesConversiones utilesConversiones;

	public ResultadoCtitBean generarXmlSegundoEnvioFullCtit(TramiteTrafTranDto tramite, String xmlPrimerEnvio){
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			if(xmlPrimerEnvio != null && !xmlPrimerEnvio.isEmpty()){
				SolicitudRegistroEntrada solicitudRegistroEntrada = getSolicitudRegistroEntradaToXml(xmlPrimerEnvio,tramite.getNumExpediente());
				if(solicitudRegistroEntrada != null){
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getTasas().getTasaTramiteOrTasaInforme().add(null);
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getAcreditacionDerecho().setConsentimiento(tramite.getConsentimiento() == null?
						TipoConsentimiento.NO:tramite.getConsentimiento().equals("true") ? TipoConsentimiento.SI : TipoConsentimiento.N_A);
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(new byte[0]);
					solicitudRegistroEntrada.setFirma(new byte[0]);
					
					resultado = anhadirFirmasTransTelematicaHSM(solicitudRegistroEntrada,tramite.getContrato().getColegiadoDto().getAlias());
					if(!resultado.getError()){
						String nombreFichero = ConstantesGestorFicheros.NOMBRE_CTIT + tramite.getNumExpediente();
						solicitudRegistroEntrada = resultado.getSolicitudRegistroEntrada();
						resultado = validacionXSD(solicitudRegistroEntrada, nombreFichero);
						if(!resultado.getError()){
							resultado = guardarXmlEnvioTransTelematico(solicitudRegistroEntrada,tramite.getNumExpediente(),nombreFichero);
						}
					}
					if(resultado.getError()){
						resultado.setExcepcion(new Exception(resultado.getMensajeError()));
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se puede crear el xml del segundo envio porque el xml del primer envio esta vacio.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede crear el xml del segundo envio porque el xml del primer envio esta vacio.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de crear el xml del segundo envio de FullCtit para el expediente: " +tramite.getNumExpediente() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception(e.getMessage()));
		}

		return resultado;
	}

	private SolicitudRegistroEntrada getSolicitudRegistroEntradaToXml(String xmlPrimerEnvio, BigDecimal numExpediente) throws JAXBException, OegamExcepcion {
		FileResultBean file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.CTIT, 
					ConstantesGestorFicheros.CTITENVIO, Utilidades.transformExpedienteFecha(numExpediente), xmlPrimerEnvio, ConstantesGestorFicheros.EXTENSION_XML);
		if(file != null && file.getFile() != null){
			Unmarshaller unmarshaller = new XmlCtitSegaFactory().getContext().createUnmarshaller();
			return (SolicitudRegistroEntrada) unmarshaller.unmarshal(file.getFile());
		}
		return null;
	}

	public ResultadoCtitBean generarXmlTransmisionTelematica(TramiteTraficoTransmisionBean tramite, Paso paso) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		ContratoDto contratoDto = servicioContrato.getContratoDto(tramite.getTramiteTraficoBean().getIdContrato());
		JefaturaTraficoDto jefaturaTrafDto = servicioJefaturaTrafico.getJefatura(tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial());
		if(contratoDto == null){
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("No se encuentran datos del contrato del tramite para poder realizar la transmision telemática.");
		} else if(jefaturaTrafDto == null){
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("No se han encontrado datos de la jefatura del tramite.");
		}

		if(!resultado.getError()){
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			ObjectFactory objFactory = new ObjectFactory();

			SolicitudRegistroEntrada solRegEntrada = objFactory.createSolicitudRegistroEntrada();

			//NODO SECUNDARIO: Datos firmados
			DatosFirmados datosFirmados = objFactory.createDatosFirmados();

			//NODO TERCIARIO: Datos genéricos
			DatosGenericos datosGenericos = objFactory.createDatosGenericos();

			//Organismo
			datosGenericos.setOrganismo(TipoOrganismo.DGT);

			//Datos remitente
			DatosRemitente datosRemitente = objFactory.createDatosRemitente();

			datosRemitente.setNombre(contratoDto.getColegioDto().getColegio());
			datosRemitente.setApellidos(CADENA_VACIA);
			DocumentoIdentificacion docRemitente = objFactory.createDatosRemitenteDocumentoIdentificacion();
			docRemitente.setNumero(contratoDto.getColegioDto().getCif());
			datosRemitente.setDocumentoIdentificacion(docRemitente);
			datosRemitente.setCorreoElectronico(contratoDto.getColegioDto().getCorreoElectronico());

			//Seteamos
			datosGenericos.setRemitente(datosRemitente);

			PersonaDto colegiadoCompleto = servicioPersona.obtenerColegiadoCompleto(tramite.getTramiteTraficoBean().getNumColegiado(), tramite.getTramiteTraficoBean().getIdContrato());

			//Interesados
			Interesados interesados = objFactory.createDatosGenericosInteresados();
			//Datos interesado
			DatosInteresado datosInteresado = objFactory.createDatosInteresado();
			datosInteresado.setNombre(colegiadoCompleto.getNombre());
			datosInteresado.setApellidos(colegiadoCompleto.getApellido1RazonSocial() +" "+colegiadoCompleto.getApellido2());
			org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosInteresado.DocumentoIdentificacion docInteresado = objFactory.createDatosInteresadoDocumentoIdentificacion();
			docInteresado.setNumero(contratoDto.getColegiadoDto().getUsuario().getNif());
			datosInteresado.setDocumentoIdentificacion(docInteresado);
			datosInteresado.setCorreoElectronico(contratoDto.getColegiadoDto().getUsuario().getCorreoElectronico());

			//Seteamos el interesado
			interesados.setInteresado(datosInteresado);

			//Seteamos
			datosGenericos.setInteresados(interesados);

			//Asunto
			Asunto asunto = objFactory.createAsunto();
			asunto.setCodigo(CTVG);
			asunto.setDescripcion(TEXTO_SOLICITUD);
			//Seteamos
			datosGenericos.setAsunto(asunto);

			//Destino
			Destino destino = objFactory.createDestino();
			destino.setCodigo(_101001);
			destino.setDescripcion(DGT_VEHÍCULOS);
			//Seteamos
			datosGenericos.setDestino(destino);

			//Seteamos los DATOS GENÉRICOS
			datosFirmados.setDatosGenericos(datosGenericos);

			//NODO TERCIARIO: DATOS ESPECIFICOS
			DatosEspecificos datosEspecificos = objFactory.createDatosEspecificos();

			//Datos Colegio
			DatosColegio datosColegio = objFactory.createDatosEspecificosDatosColegio();
			datosColegio.setId(contratoDto.getColegioDto().getColegio());
			//Seteamos
			datosEspecificos.setDatosColegio(datosColegio);

			//Datos Gestoria
			DatosGestoria datosGestoria = objFactory.createDatosEspecificosDatosGestoria();
			datosGestoria.setId(tramite.getTramiteTraficoBean().getNumColegiado());

			//Seteamos
			datosEspecificos.setDatosGestoria(datosGestoria);

			//Datos Gestor
			DatosGestor datosGestor = objFactory.createDatosEspecificosDatosGestor();
			datosGestor.setFiliacion("FISICA");
			datosGestor.setDOI(contratoDto.getColegiadoDto().getUsuario().getNif());

			//Seteamos
			datosEspecificos.setDatosGestor(datosGestor);

			//Nº Expediente Gestor
			datosEspecificos.setNumeroExpedienteGestor(tramite.getTramiteTraficoBean().getNumExpediente().toString());

			//Datos Expediente
			DatosExpediente datosExpediente = objFactory.createDatosEspecificosDatosExpediente();

			//Cambiado por Mantis 1551
			// DRC@01-10-2012 Incidencia: 2493
			if (tramite != null && tramite.getTipoTransferencia() != null && tramite.getTipoTransferencia().getValorEnum() != null) {
				datosExpediente.setTipoTramite(TipoTransferencia.mapeoTipoTransferencia(tramite.getTipoTransferencia().getValorEnum()));
			}
			if (tramite != null && tramite.getModoAdjudicacion() != null){
				datosExpediente.setMotivoTransmision(tramite.getModoAdjudicacion().getValorEnum());
			}else{
				datosExpediente.setMotivoTransmision(CADENA_VACIA);
			}
			datosExpediente.setFechaTramite(format.format(new Date()));

			datosExpediente.setJefatura(jefaturaTrafDto.getJefatura());
			datosExpediente.setSucursal(jefaturaTrafDto.getSucursal()!=null?jefaturaTrafDto.getSucursal():CADENA_VACIA);

			if (tramite.getTipoTransferencia() != null && TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum())) {
				datosExpediente.setImpresionPermisoCirculacion(TipoSINO.NO);
			} else {
				datosExpediente.setImpresionPermisoCirculacion(TRUE.equals(tramite.getImpresionPermiso()) ? TipoSINO.SI : TipoSINO.NO);
			}
			datosEspecificos.setDatosExpediente(datosExpediente);
			//Tasas
			Tasas tasas = objFactory.createDatosEspecificosTasas();
			if(tramite.getTramiteTraficoBean().getTasa() != null &&
					tramite.getTramiteTraficoBean().getTasa().getCodigoTasa() != null) {
				String tasa = utiles.rellenarCeros(tramite.getTramiteTraficoBean().getTasa().getCodigoTasa(), 12);
				tasas.getTasaTramiteOrTasaInforme().add(objFactory.createDatosEspecificosTasasTasaTramite(tasa));
			}
			if (tramite.getCodigoTasaInforme() != null && !tramite.getCodigoTasaInforme().isEmpty()
					 && !"-1".equals(tramite.getCodigoTasaInforme())) {
				String tasa = utiles.rellenarCeros(tramite.getCodigoTasaInforme(), 12);
				tasas.getTasaTramiteOrTasaInforme().add(objFactory.createDatosEspecificosTasasTasaInforme(tasa));
			}

			if(tramite.getCodigoTasaCambioServicio()!=null && !"-1".equals(tramite.getCodigoTasaCambioServicio())) {
				String tasa = utiles.rellenarCeros(tramite.getCodigoTasaCambioServicio(), 12);
				tasas.setTasaCambioServicio(tasa);
			}

			//Seteamos
			datosEspecificos.setTasas(tasas);

			//Datos Vehículo
			//Obtenemos el bean vehículo
			VehiculoBean vehiculo = tramite.getTramiteTraficoBean().getVehiculo();
			DatosVehiculo datosVehiculo = objFactory.createDatosEspecificosDatosVehiculo();

			//Datos Matriculación
			DatosMatriculacion datosMatriculacion = objFactory.createDatosEspecificosDatosVehiculoDatosMatriculacion();
			datosMatriculacion.setMatricula(vehiculo.getMatricula());
			try{
				if (vehiculo.getFechaMatriculacion() != null && vehiculo.getFechaMatriculacion().getTimestamp() != null)
					datosMatriculacion.setFechaMatriculacion(format.format(vehiculo.getFechaMatriculacion().getTimestamp()));
				else
					datosMatriculacion.setFechaMatriculacion(CADENA_VACIA);
			}catch(ParseException e){
				log.error("Error al parsear la fecha de matriculación del vehículo, error: ",e);
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("Error al parsear la fecha de matriculación del vehículo");
			}

			//Seteamos los datos de matriculación
			datosVehiculo.setDatosMatriculacion(datosMatriculacion);

			//Datos Servicio
			DatosServicio datosServicio = objFactory.createDatosEspecificosDatosVehiculoDatosServicio();
			datosServicio.setCambioServicio(TRUE.equals(tramite.getCambioServicio())?TipoSINO.SI:TipoSINO.NO);
			if(TRUE.equals(tramite.getCambioServicio()) && !CADENA_VACIA.equals(vehiculo.getServicioTraficoBean().getIdServicio()) && !"-1".equals(vehiculo.getServicioTraficoBean().getIdServicio())){
				datosServicio.setServicio(vehiculo.getServicioTraficoBean().getIdServicio());
			}

			//Seteamos los datos de servicio
			datosVehiculo.setDatosServicio(datosServicio);
			datosVehiculo.setRenting(TRUE.equals(tramite.getTramiteTraficoBean().getRenting())?TipoSINO.SI:TipoSINO.NO);
			datosVehiculo.setTipoVehiculo(vehiculo.getTipoVehiculoBean()!=null?vehiculo.getTipoVehiculoBean().getTipoVehiculo():CADENA_VACIA);

			//Datos ITV
			if(!TRUE.equals(tramite.getTramiteTraficoBean().getVehiculo().getConsentimiento())){
				DatosITV datosITV = objFactory.createDatosEspecificosDatosVehiculoDatosITV();
				datosITV.setResultadoITV(TipoResultadoITV.F);
				try {
					// DRC@01-10-2012 Incidencia: 2493
					if (vehiculo.getFechaInspeccion() != null && vehiculo.getFechaInspeccion().getTimestamp() != null)
						datosITV.setFechaRealizacion(format.format(vehiculo.getFechaInspeccion().getTimestamp()));
					else 
						datosITV.setFechaRealizacion(CADENA_VACIA);
				}catch(ParseException e){
					log.error("Error al parsear la fecha de inspección del vehículo, error: ",e);
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeLista("Error al parsear la fecha de inspección del vehículo");
				}
				try {
					// DRC@01-10-2012 Incidencia: 2493
					if (vehiculo.getFechaItv() != null && vehiculo.getFechaItv().getTimestamp() != null)
						datosITV.setFechaCaducidad(format.format(vehiculo.getFechaItv().getTimestamp()));
					else
						datosITV.setFechaCaducidad(CADENA_VACIA);
				} catch(ParseException e) {
					log.error("Error al parsear la fecha de caducidad de la ITV del vehículo, error: ",e);
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeLista("Error al parsear la fecha de caducidad de la ITV del vehículo.");
				}

				String estacionITV = vehiculo.getEstacionItv();

				if(estacionITV!=null && !CADENA_VACIA.equals(estacionITV)){
					String estacion = estacionITV.substring(estacionITV.length()-2, estacionITV.length());
					String provEstacion = estacionITV.substring(0, estacionITV.length()-2);
					datosITV.setEstacion(_0+estacion);
					datosITV.setProvincia(provEstacion);
				}
				datosITV.setMotivo(vehiculo.getMotivoItv()!=null && vehiculo.getMotivoItv().getIdMotivo()!=null?vehiculo.getMotivoItv().getIdMotivo():CADENA_VACIA);
				//Seteamos los datos de ITV
				datosVehiculo.setDatosITV(datosITV);
				datosVehiculo.setDatosITV(null);
			}
			//Domicilio del vehiculo
			// incidencia 3038: Localización del vehículo - Transmisión CTIT y BAJAS (BLOQUEAR) atc	
			// Si esta chekeado la aceptación del cambio de localización se incluye la dirección en caso de que exista
			Direccion dirVehiculo;
			if(null==tramite.getTipoTransferencia() ||
					!tramite.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum()) ){//Antes era tipo 3
				dirVehiculo = tramite.getAdquirienteBean().getPersona().getDireccion();
			} else {
				dirVehiculo = tramite.getPoseedorBean().getPersona().getDireccion();
			}
			// La direccion del vehiculo ya ha sido validada y no hay que comprobar si esta completa.
			if(TRUE.equals(tramite.getConsentimientoCambio())){
				dirVehiculo = vehiculo.getDireccionBean();
			}
			//JRG. 18-02-2013 En la ultima actualización del WS de fullCtit se pide que en caso de que no haya de via se inserte "SN".
			//Antes se comprobaba si tenia numero de via. Con eso se daba por hecho que se había rellenado la direccion. Ahora va a comprobar el nombre de via
			if(dirVehiculo!=null && dirVehiculo.getNombreVia()!=null && !(CADENA_VACIA).equals(dirVehiculo.getNombreVia())) {
				Domicilio domicilioVehiculo = objFactory.createDomicilio();
				domicilioVehiculo.setMunicipio(dirVehiculo.getMunicipio().getProvincia().getIdProvincia()+dirVehiculo.getMunicipio().getIdMunicipio());
				domicilioVehiculo.setLocalidad(dirVehiculo.getPueblo()!=null?dirVehiculo.getPueblo():CADENA_VACIA);
				domicilioVehiculo.setProvincia(dirVehiculo.getMunicipio().getProvincia().getIdProvincia());
				domicilioVehiculo.setCodigoPostal(dirVehiculo.getCodPostal());
				domicilioVehiculo.setTipoVia(dirVehiculo.getTipoVia().getIdTipoVia());
				domicilioVehiculo.setNombreVia(utilesConversiones.ajustarCamposIne(dirVehiculo.getNombreVia()));
				//JRG. 18-02-2013 En la ultima actualización del WS de fullCtit se pide que en caso de que no haya de via se inserte "SN".
				if (!(CADENA_VACIA).equals(dirVehiculo.getNombreVia())){
					//Si se ha metido direccion tiene que tener numero de via o SN.
					if (null!=dirVehiculo.getNumero() && !dirVehiculo.getNumero().equals("")){
						domicilioVehiculo.setNumero(dirVehiculo.getNumero());}
					else{
						domicilioVehiculo.setNumero(SN);}
				}else{
					//Si no tiene direccion le mete cadena vacia.
					domicilioVehiculo.setNumero(CADENA_VACIA);
				}

				domicilioVehiculo.setKilometro(dirVehiculo.getPuntoKilometrico()!=null?dirVehiculo.getPuntoKilometrico():CADENA_VACIA);
				domicilioVehiculo.setHectometro(dirVehiculo.getHm()!=null?dirVehiculo.getHm():CADENA_VACIA);
				domicilioVehiculo.setBloque(dirVehiculo.getBloque()!=null?dirVehiculo.getBloque():CADENA_VACIA);			
				domicilioVehiculo.setPortal(dirVehiculo.getPortal()!=null && dirVehiculo.getPortal().length() <= 2 ?dirVehiculo.getPortal():CADENA_VACIA);
				domicilioVehiculo.setEscalera(dirVehiculo.getEscalera()!=null?dirVehiculo.getEscalera():CADENA_VACIA);
				domicilioVehiculo.setPlanta(dirVehiculo.getPlanta()!=null?dirVehiculo.getPlanta():CADENA_VACIA);
				domicilioVehiculo.setPuerta(dirVehiculo.getPuerta()!=null?dirVehiculo.getPuerta():CADENA_VACIA);
				domicilioVehiculo.setPais(ESP);
				//Seteamos el domicilio del vehiculo
				datosVehiculo.setDomicilio(domicilioVehiculo);
				//Seteamos
				datosEspecificos.setDatosVehiculo(datosVehiculo);
				// Fin 3038 
			}
			//Datos Tutela
			if((tramite.getRepresentanteAdquirienteBean()!=null && tramite.getRepresentanteAdquirienteBean().getPersona()!=null
					&& null!=tramite.getRepresentanteAdquirienteBean().getPersona().getNif() && !CADENA_VACIA.equals(tramite.getRepresentanteAdquirienteBean().getPersona().getNif())
					&& null!=tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela() && !CADENA_VACIA.equals(tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela())) ||
					(tramite.getRepresentanteTransmitenteBean()!=null && tramite.getRepresentanteTransmitenteBean().getPersona()!=null 
					&& tramite.getRepresentanteTransmitenteBean().getPersona().getNif()!=null && !CADENA_VACIA.equals(tramite.getRepresentanteTransmitenteBean().getPersona().getNif()) 
					&& null!=tramite.getRepresentanteTransmitenteBean().getIdMotivoTutela() && !CADENA_VACIA.equals(tramite.getRepresentanteTransmitenteBean().getIdMotivoTutela()))){

				DatosTutela datosTutela = objFactory.createDatosEspecificosDatosTutela();
				//Tutela Transmitente
				if(tramite.getRepresentanteTransmitenteBean()!=null 
						&& tramite.getRepresentanteTransmitenteBean().getPersona()!=null
						&& tramite.getRepresentanteTransmitenteBean().getPersona().getNif()!=null
						&& !CADENA_VACIA.equals(tramite.getRepresentanteTransmitenteBean().getPersona().getNif())
						&& null!=tramite.getRepresentanteTransmitenteBean().getIdMotivoTutela() &&
						!CADENA_VACIA.equals(tramite.getRepresentanteTransmitenteBean().getIdMotivoTutela())){
					TutelaTransmiente tutelaTransmitente = objFactory.createDatosEspecificosDatosTutelaTutelaTransmiente();
					tutelaTransmitente.setDOITutor(tramite.getRepresentanteTransmitenteBean().getPersona().getNif());
					datosTutela.setTutelaTransmiente(tutelaTransmitente);
				}
				//Tutela Adquiriente
				if(tramite.getRepresentanteAdquirienteBean()!=null &&
						tramite.getRepresentanteAdquirienteBean().getPersona()!=null &&
						tramite.getRepresentanteAdquirienteBean().getPersona().getNif() !=null &&
						!CADENA_VACIA.equals(tramite.getRepresentanteAdquirienteBean().getPersona().getNif())
						&& null!=tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela() &&
						!CADENA_VACIA.equals(tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela())){
					//TIPO TRANSFERENCIA TRADE NO LLEVA ADQUIRIENTE NI SU REPRESENTANTE. (antes tipo3)
					if (!tramite.getTipoTransferencia().equals(TipoTransferencia.tipo5)){
						TutelaAdquirente tutelaAdquiriente = objFactory.createDatosEspecificosDatosTutelaTutelaAdquirente();
						tutelaAdquiriente.setTipoTutela(null!=tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela()?tramite.getRepresentanteAdquirienteBean().getIdMotivoTutela().getValorEnum():CADENA_VACIA);
						tutelaAdquiriente.setDOITutor(tramite.getRepresentanteAdquirienteBean().getPersona().getNif());
						//Seteamos la tutela del adquiriente
						datosTutela.setTutelaAdquirente(tutelaAdquiriente);}
					}
					//Seteamos
					datosEspecificos.setDatosTutela(datosTutela);
				}

				//Titular Transmitente
				DatosTransmitente datosTransmitente = objFactory.createDatosTransmitente();
				DatosTransmitenteConAutonomo datosTransmitenteConAutonomo = objFactory.createDatosTransmitenteConAutonomo();
				Persona transmitente = tramite.getTransmitenteBean().getPersona();

				//Datos personales
				DatosPersonalesTransmitente datosPersonalesTransmitente = objFactory.createDatosPersonalesTransmitente();

				//Datos Filiacion
				DatosFiliacion datosFiliacionTransmitente = objFactory.createDatosPersonalesTransmitenteDatosFiliacion();
				if(TipoPersona.Fisica.getValorEnum().equals(transmitente.getTipoPersona().getValorEnum())){
					//Persona Fisica
					org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente.DatosFiliacion.PersonaFisica personaFisica =
							objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaFisica();
					personaFisica.setNombre(transmitente.getNombre());
					personaFisica.setPrimerApellido(transmitente.getApellido1RazonSocial());
					personaFisica.setSegundoApellido(!CADENA_VACIA.equals(transmitente.getApellido2())?transmitente.getApellido2():CADENA_VACIA);
					try{
						// DRC@01-10-2012 Incidencia: 2493
						if (transmitente.getFechaNacimientoBean()!=null && transmitente.getFechaNacimientoBean().getTimestamp() != null)
							personaFisica.setFechaNacimiento(format.format(transmitente.getFechaNacimientoBean().getTimestamp()));
						else 
							personaFisica.setFechaNacimiento(null);
					}catch(ParseException e){
						log.error("Error al parsear la fecha de nacimiento del transmitente, error: ",e);
						resultado.setError(Boolean.TRUE);
						resultado.addMensajeLista("Error al parsear la fecha de nacimiento del transmitente.");
					}
					datosFiliacionTransmitente.setPersonaFisica(personaFisica);
				} else {
					//Persona Jurídica
					org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente.DatosFiliacion.PersonaJuridica personaJuridica = 
							objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaJuridica();
					personaJuridica.setRazonSocial(transmitente.getApellido1RazonSocial());
					datosFiliacionTransmitente.setPersonaJuridica(personaJuridica);
				}

				datosPersonalesTransmitente.setDatosFiliacion(datosFiliacionTransmitente);
				datosPersonalesTransmitente.setSexo("H".equals(transmitente.getSexo())?"M":transmitente.getSexo());
				datosPersonalesTransmitente.setDOI(transmitente.getNif());
				datosTransmitente.setDatosTransmitente(datosPersonalesTransmitente);
				datosTransmitenteConAutonomo.setDatosTransmitente(datosPersonalesTransmitente);
				if(tramite != null && tramite.getRepresentanteTransmitenteBean() !=null && tramite.getRepresentanteTransmitenteBean().getPersona() !=null &&
						tramite.getRepresentanteTransmitenteBean().getPersona().getNif() != null && 
						!(CADENA_VACIA).equals(tramite.getRepresentanteTransmitenteBean().getPersona().getNif())){
					//Datos representante
					//si es tutela no es representante.
					if (tramite != null && tramite.getRepresentanteTransmitenteBean() != null && tramite.getRepresentanteTransmitenteBean().getConceptoRepre()!= null &&
							!ConceptoTutela.Tutela.getValorEnum().equals(tramite.getRepresentanteTransmitenteBean().getConceptoRepre().getValorEnum())) {
						DatosRepresentante datosRepresentanteTransmitente = objFactory.createDatosRepresentante();
						datosRepresentanteTransmitente.setDOI(tramite.getRepresentanteTransmitenteBean().getPersona().getNif());
						datosTransmitente.setDatosRepresentante(datosRepresentanteTransmitente);
						datosTransmitenteConAutonomo.setDatosRepresentante(datosRepresentanteTransmitente);
					}
				}
				//Inserta valor para el campo Autonomo_Transmitente SI o NO
				if (tramite.getTransmitenteBean()!=null && TRUE.equalsIgnoreCase(tramite.getTransmitenteBean().getAutonomo())){
					datosTransmitenteConAutonomo.setAutonomoTransmitente(TipoSINO.SI);
				}
				datosEspecificos.setTitularTransmitente(datosTransmitenteConAutonomo);

				//Cotitulares Transmitentes
				if((tramite.getPrimerCotitularTransmitenteBean()!=null && tramite.getPrimerCotitularTransmitenteBean().getPersona()!=null &&
						tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif())) ||
						(tramite.getSegundoCotitularTransmitenteBean()!=null && tramite.getSegundoCotitularTransmitenteBean().getPersona()!=null &&
							tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif()))){

					CotitularesTransmitentes cotitularesTransmitentes = objFactory.createDatosEspecificosCotitularesTransmitentes();
					//Si hay primer cotitular...
					if(tramite.getPrimerCotitularTransmitenteBean()!=null && tramite.getPrimerCotitularTransmitenteBean().getPersona()!=null &&
							tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getPrimerCotitularTransmitenteBean().getPersona().getNif())){
						DatosTransmitente datosPrimerCotitularTransmitente = objFactory.createDatosTransmitente();
						Persona primerCotitular = tramite.getPrimerCotitularTransmitenteBean().getPersona();
						// Datos representante:
						if(tramite.getRepresentantePrimerCotitularTransmitenteBean() != null && tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona() != null &&
								tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getNif() != null && 
								!tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getNif().equals("")){
							DatosRepresentante datosRepresentante = objFactory.createDatosRepresentante();
							datosRepresentante.setDOI(tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getNif());
							datosPrimerCotitularTransmitente.setDatosRepresentante(datosRepresentante);
						}
						//Datos personales
						DatosPersonalesTransmitente datosPersonalesPrimerCotitularTransmitente = objFactory.createDatosPersonalesTransmitente();
						//Datos Filiacion
						DatosFiliacion datosFiliacion = objFactory.createDatosPersonalesTransmitenteDatosFiliacion();
						if(TipoPersona.Fisica.getValorEnum().equals(primerCotitular.getTipoPersona().getValorEnum())){
							//Persona Fisica
							org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente.DatosFiliacion.PersonaFisica personaFisica =
									objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaFisica();
							personaFisica.setNombre(primerCotitular.getNombre());
							personaFisica.setPrimerApellido(primerCotitular.getApellido1RazonSocial());
							personaFisica.setSegundoApellido(!CADENA_VACIA.equals(primerCotitular.getApellido2())?primerCotitular.getApellido2():CADENA_VACIA);
							try {
								// DRC@01-10-2012 Incidencia: 2493
								if (primerCotitular.getFechaNacimientoBean() != null && primerCotitular.getFechaNacimientoBean().getTimestamp() != null)
									personaFisica.setFechaNacimiento(format.format(primerCotitular.getFechaNacimientoBean().getTimestamp()));
								else
									personaFisica.setFechaNacimiento(CADENA_VACIA);
							} catch(ParseException e) {
								log.error("Error al parsear la fecha de nacimiento del primer cotitular transmitente, error: ",e);
								resultado.setError(Boolean.TRUE);
								resultado.addMensajeLista("Error al parsear la fecha de nacimiento del primer cotitular transmitente.");
							}
							datosFiliacion.setPersonaFisica(personaFisica);
						} else {
							//Persona Jurídica
							org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente.DatosFiliacion.PersonaJuridica personaJuridica = 
									objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaJuridica();
							personaJuridica.setRazonSocial(primerCotitular.getApellido1RazonSocial());
							datosFiliacion.setPersonaJuridica(personaJuridica);
						}

						datosPersonalesPrimerCotitularTransmitente.setDatosFiliacion(datosFiliacion);
						datosPersonalesPrimerCotitularTransmitente.setSexo(primerCotitular.getSexo().equals("H")?"M":primerCotitular.getSexo());
						datosPersonalesPrimerCotitularTransmitente.setDOI(primerCotitular.getNif());
						datosPrimerCotitularTransmitente.setDatosTransmitente(datosPersonalesPrimerCotitularTransmitente);
						cotitularesTransmitentes.getTransmitente().add(datosPrimerCotitularTransmitente);
					}

					if(tramite.getSegundoCotitularTransmitenteBean()!=null && tramite.getSegundoCotitularTransmitenteBean().getPersona()!=null &&
							tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif()!=null 
							&& !(CADENA_VACIA).equals(tramite.getSegundoCotitularTransmitenteBean().getPersona().getNif())){

						DatosTransmitente datosSegundoCotitularTransmitente = objFactory.createDatosTransmitente();
						Persona segundoCotitular = tramite.getSegundoCotitularTransmitenteBean().getPersona();
						// Datos representante:
						if(tramite.getRepresentanteSegundoCotitularTransmitenteBean() != null && tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona() != null &&
								tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getNif() != null && 
								!tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getNif().equals("")){
							DatosRepresentante datosRepresentante = objFactory.createDatosRepresentante();
							datosRepresentante.setDOI(tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getNif());
							datosSegundoCotitularTransmitente.setDatosRepresentante(datosRepresentante);
						}
						//Datos personales
						DatosPersonalesTransmitente datosPersonalesSegundoCotitularTransmitente = objFactory.createDatosPersonalesTransmitente();
						//Datos Filiacion
						DatosFiliacion datosFiliacion = objFactory.createDatosPersonalesTransmitenteDatosFiliacion();
						if(TipoPersona.Fisica.getValorEnum().equals(segundoCotitular.getTipoPersona().getValorEnum())){
							//Persona Fisica
							org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente.DatosFiliacion.PersonaFisica personaFisica =
									objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaFisica();
							personaFisica.setNombre(segundoCotitular.getNombre());
							personaFisica.setPrimerApellido(segundoCotitular.getApellido1RazonSocial());
							personaFisica.setSegundoApellido(!CADENA_VACIA.equals(segundoCotitular.getApellido2())?segundoCotitular.getApellido2():CADENA_VACIA);
							try {
								// DRC@01-10-2012 Incidencia: 2493
								if (segundoCotitular != null && segundoCotitular.getFechaNacimientoBean() != null)
									personaFisica.setFechaNacimiento(format.format(segundoCotitular.getFechaNacimientoBean().getTimestamp()));
								else
									personaFisica.setFechaNacimiento(CADENA_VACIA);
							} catch(ParseException e) {
								log.error("Error al parsear la fecha de nacimiento del segundo cotitular transmitente., error: ",e);
								resultado.setError(Boolean.TRUE);
								resultado.addMensajeLista("Error al parsear la fecha de nacimiento del segundo cotitular transmitente.");
							}
							//Seteamos la persona física
							datosFiliacion.setPersonaFisica(personaFisica);
						} else {
							//Persona Jurídica
							org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonalesTransmitente.DatosFiliacion.PersonaJuridica personaJuridica = 
									objFactory.createDatosPersonalesTransmitenteDatosFiliacionPersonaJuridica();
							personaJuridica.setRazonSocial(segundoCotitular.getApellido1RazonSocial());
							//Seteamos la persona jurídica
							datosFiliacion.setPersonaJuridica(personaJuridica);
						}
						datosPersonalesSegundoCotitularTransmitente.setDatosFiliacion(datosFiliacion);
						datosPersonalesSegundoCotitularTransmitente.setSexo(segundoCotitular.getSexo().equals("H")?"M":segundoCotitular.getSexo());
						datosPersonalesSegundoCotitularTransmitente.setDOI(segundoCotitular.getNif());
						datosSegundoCotitularTransmitente.setDatosTransmitente(datosPersonalesSegundoCotitularTransmitente);
						cotitularesTransmitentes.getTransmitente().add(datosSegundoCotitularTransmitente);
					}
					datosEspecificos.setCotitularesTransmitentes(cotitularesTransmitentes);
				}
				String codigoIAE = null;
				//Adquiriente
				if(tramite.getAdquirienteBean()!=null && tramite.getAdquirienteBean().getPersona()!=null &&
						tramite.getAdquirienteBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getAdquirienteBean().getPersona().getNif())){

					if (!tramite.getTipoTransferencia().equals(TipoTransferencia.tipo5)) { //TIPO TRANSFERENCIA TRADE NO LLEVA ADQUIRIENTE (Antes tipo3)
						DatosAdquirente datosAdquiriente = objFactory.createDatosAdquirente();
						Persona adquiriente = tramite.getAdquirienteBean().getPersona();
						//Datos personales
						DatosPersonales datosPersonalesAdquiriente = objFactory.createDatosPersonales();
						//Datos Filiacion
						org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonales.DatosFiliacion datosFiliacionAdquiriente = objFactory.createDatosPersonalesDatosFiliacion();
						if(TipoPersona.Fisica.getValorEnum().equals(adquiriente.getTipoPersona().getValorEnum())){
							//Persona Fisica
							PersonaFisica personaFisica = objFactory.createDatosPersonalesDatosFiliacionPersonaFisica();
							personaFisica.setNombre(adquiriente.getNombre());
							personaFisica.setPrimerApellido(adquiriente.getApellido1RazonSocial());
							personaFisica.setSegundoApellido(!CADENA_VACIA.equals(adquiriente.getApellido2())?adquiriente.getApellido2():CADENA_VACIA);
							try{
								// DRC@01-10-2012 Incidencia: 2493
								if (adquiriente.getFechaNacimientoBean() != null && adquiriente.getFechaNacimientoBean().getTimestamp() != null)
									personaFisica.setFechaNacimiento(format.format(adquiriente.getFechaNacimientoBean().getTimestamp()));
								else
									personaFisica.setFechaNacimiento(CADENA_VACIA);
							}catch(ParseException e){
								log.error("Error al parsear la fecha de nacimiento del adquiriente, error: ",e);
								resultado.setError(Boolean.TRUE);
								resultado.addMensajeLista("Error al parsear la fecha de nacimiento del adquiriente.");
							}
							datosFiliacionAdquiriente.setPersonaFisica(personaFisica);
						} else {
							//Persona Jurídica
							PersonaJuridica personaJuridica = objFactory.createDatosPersonalesDatosFiliacionPersonaJuridica();
							personaJuridica.setRazonSocial(adquiriente.getApellido1RazonSocial());
							//Seteamos la persona jurídica
							datosFiliacionAdquiriente.setPersonaJuridica(personaJuridica);
						}
						datosPersonalesAdquiriente.setDatosFiliacion(datosFiliacionAdquiriente);
						datosPersonalesAdquiriente.setSexo(adquiriente.getSexo().equals("H")?"M":adquiriente.getSexo());
						datosPersonalesAdquiriente.setDOI(adquiriente.getNif());
						datosAdquiriente.setDatosAdquirente(datosPersonalesAdquiriente);
						if(tramite.getRepresentanteAdquirienteBean()!=null && tramite.getRepresentanteAdquirienteBean().getPersona()!=null &&
								tramite.getRepresentanteAdquirienteBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getRepresentanteAdquirienteBean().getPersona().getNif())){
							//Datos representante
							DatosRepresentante datosRepresentanteAdquiriente = objFactory.createDatosRepresentante();
							datosRepresentanteAdquiriente.setDOI(tramite.getRepresentanteAdquirienteBean().getPersona().getNif());
							datosAdquiriente.setDatosRepresentante(datosRepresentanteAdquiriente);
						}
						datosAdquiriente.setActualizacionDomicilio(TRUE.equals(tramite.getAdquirienteBean().getCambioDomicilio())?TipoSINO.SI:TipoSINO.NO);
						//Domicilio Adquiriente
						Direccion dirAdquiriente = adquiriente.getDireccion();
						Domicilio domicilioAdquiriente = objFactory.createDomicilio();
						String idProvinciaAdquiriente = dirAdquiriente.getMunicipio().getProvincia().getIdProvincia()!=null &&
							!"-1".equals(dirAdquiriente.getMunicipio().getProvincia().getIdProvincia())?dirAdquiriente.getMunicipio().getProvincia().getIdProvincia():CADENA_VACIA;
						String idMunicipioAdquiriente = dirAdquiriente.getMunicipio().getIdMunicipio()!=null &&
							!"-1".equals(dirAdquiriente.getMunicipio().getIdMunicipio())?dirAdquiriente.getMunicipio().getIdMunicipio():CADENA_VACIA;
						String idPuebloAdquiriente = dirAdquiriente.getPueblo()!=null &&
							!"-1".equals(dirAdquiriente.getPueblo())?dirAdquiriente.getPueblo():CADENA_VACIA;
							String idTipoViaAdquiriente = dirAdquiriente.getTipoVia().getIdTipoVia()!=null &&
							!"-1".equals(dirAdquiriente.getTipoVia().getIdTipoVia())?dirAdquiriente.getTipoVia().getIdTipoVia():CADENA_VACIA;
						String idNombreViaAdquiriente = dirAdquiriente.getNombreVia()!=null &&
							!"-1".equals(dirAdquiriente.getNombreVia())?dirAdquiriente.getNombreVia():CADENA_VACIA;

						domicilioAdquiriente.setMunicipio(idProvinciaAdquiriente+idMunicipioAdquiriente);
						domicilioAdquiriente.setLocalidad(idPuebloAdquiriente);
						domicilioAdquiriente.setProvincia(idProvinciaAdquiriente);
						domicilioAdquiriente.setCodigoPostal(null!=dirAdquiriente.getCodPostal()?dirAdquiriente.getCodPostal():CADENA_VACIA);
						domicilioAdquiriente.setTipoVia(idTipoViaAdquiriente);
						domicilioAdquiriente.setNombreVia(utilesConversiones.ajustarCamposIne(idNombreViaAdquiriente));
						//JRG. 18-02-2013 En la ultima actualización del WS de fullCtit se pide que en caso de que no haya de via se inserte "SN".
						if (!(CADENA_VACIA).equals(idNombreViaAdquiriente)){
							//Si se ha metido direccion tiene que tener numero de via o SN.
							if (null!=dirAdquiriente.getNumero() && !dirAdquiriente.getNumero().equals("")){
								domicilioAdquiriente.setNumero(dirAdquiriente.getNumero());}
							else{
								domicilioAdquiriente.setNumero(SN);}
						}else{
							//Si no tiene direccion le mete cadena vacia.
							domicilioAdquiriente.setNumero(CADENA_VACIA);
						}
						domicilioAdquiriente.setNumero(null!=dirAdquiriente.getNumero()&& !dirAdquiriente.getNumero().equals("")?dirAdquiriente.getNumero():SN);
						domicilioAdquiriente.setKilometro(dirAdquiriente.getPuntoKilometrico()!=null?dirAdquiriente.getPuntoKilometrico():CADENA_VACIA);
						domicilioAdquiriente.setHectometro(dirAdquiriente.getHm()!=null?dirAdquiriente.getHm():CADENA_VACIA);
						domicilioAdquiriente.setBloque(dirAdquiriente.getBloque()!=null?dirAdquiriente.getBloque():CADENA_VACIA);
						domicilioAdquiriente.setPortal(dirAdquiriente.getPortal()!=null && dirAdquiriente.getPortal().length() <= 2 ?dirAdquiriente.getPortal():CADENA_VACIA);
						domicilioAdquiriente.setEscalera(dirAdquiriente.getEscalera()!=null?dirAdquiriente.getEscalera():CADENA_VACIA);
						domicilioAdquiriente.setPlanta(dirAdquiriente.getPlanta()!=null?dirAdquiriente.getPlanta():CADENA_VACIA);
						domicilioAdquiriente.setPuerta(dirAdquiriente.getPuerta()!=null?dirAdquiriente.getPuerta():CADENA_VACIA);
						domicilioAdquiriente.setPais(ESP);
						datosAdquiriente.setDomicilio(domicilioAdquiriente);
						datosEspecificos.setTitularAdquirente(datosAdquiriente);
					}//fin de tipo transferencia no trade.
				}
				//Datos autónomo
				DatosAutonomo datosAutonomoAdquiriente = objFactory.createDatosEspecificosDatosAutonomo();
				datosAutonomoAdquiriente.setAutonomo( null!=tramite.getAdquirienteBean() &&
					null!=tramite.getAdquirienteBean().getAutonomo() && 
					TRUE.equals(tramite.getAdquirienteBean().getAutonomo())?TipoSINO.SI:TipoSINO.NO);

				if( null!=tramite.getAdquirienteBean() && 
						null!=tramite.getAdquirienteBean().getCodigoIAE() &&
						!"-1".equals(tramite.getAdquirienteBean().getCodigoIAE())){
					codigoIAE = tramite.getAdquirienteBean().getCodigoIAE();
				}
				datosAutonomoAdquiriente.setCodigoIAE(codigoIAE);
				datosEspecificos.setDatosAutonomo(datosAutonomoAdquiriente);
				//Poseedor
				if(tramite.getPoseedorBean()!=null && tramite.getPoseedorBean().getPersona()!=null &&
					tramite.getPoseedorBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getPoseedorBean().getPersona().getNif())){

					Poseedor poseedor = objFactory.createDatosEspecificosPoseedor();
					Persona poseedorPersona = tramite.getPoseedorBean().getPersona();
					//Datos personales
					DatosPersonales datosPersonalesPoseedor = objFactory.createDatosPersonales();
					//Datos Filiacion
					org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonales.DatosFiliacion datosFiliacionPoseedor = objFactory.createDatosPersonalesDatosFiliacion();
					if(TipoPersona.Fisica.getValorEnum().equals(poseedorPersona.getTipoPersona().getValorEnum())){
						//Persona Fisica
						PersonaFisica personaFisica = objFactory.createDatosPersonalesDatosFiliacionPersonaFisica();
						personaFisica.setNombre(poseedorPersona.getNombre());
						personaFisica.setPrimerApellido(poseedorPersona.getApellido1RazonSocial());
						personaFisica.setSegundoApellido(!CADENA_VACIA.equals(poseedorPersona.getApellido2())?poseedorPersona.getApellido2():CADENA_VACIA);
						try {
							// DRC@01-10-2012 Incidencia: 2493
							if (poseedorPersona.getFechaNacimientoBean() != null && poseedorPersona.getFechaNacimientoBean().getTimestamp() != null)
								personaFisica.setFechaNacimiento(format.format(poseedorPersona.getFechaNacimientoBean().getTimestamp()));
							else
								personaFisica.setFechaNacimiento(CADENA_VACIA);
						} catch(ParseException e) {
							log.error("Error al parsear la fecha de nacimiento del poseedor, error: ",e);
							resultado.setError(Boolean.TRUE);
							resultado.addMensajeLista("Error al parsear la fecha de nacimiento del poseedor.");
						}
						datosFiliacionPoseedor.setPersonaFisica(personaFisica);
					} else {
						//Persona Jurídica
						PersonaJuridica personaJuridica = objFactory.createDatosPersonalesDatosFiliacionPersonaJuridica();
						personaJuridica.setRazonSocial(poseedorPersona.getApellido1RazonSocial());
						datosFiliacionPoseedor.setPersonaJuridica(personaJuridica);
					}
					datosPersonalesPoseedor.setDatosFiliacion(datosFiliacionPoseedor);
					datosPersonalesPoseedor.setDOI(poseedorPersona.getNif());
					datosPersonalesPoseedor.setSexo(poseedorPersona.getSexo().equals("H")?"M":poseedorPersona.getSexo());
					poseedor.setDatosPoseedor(datosPersonalesPoseedor);
					codigoIAE = null;
					if(!"-1".equals(tramite.getPoseedorBean().getCodigoIAE()) &&
							tramite.getPoseedorBean().getCodigoIAE()!=null	){
						codigoIAE = tramite.getPoseedorBean().getCodigoIAE();
					}
					poseedor.setCodigoIAE(null!=codigoIAE?codigoIAE:CADENA_VACIA);
					if(tramite.getRepresentantePoseedorBean()!=null && tramite.getRepresentantePoseedorBean().getPersona()!=null &&
							tramite.getRepresentantePoseedorBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getRepresentantePoseedorBean().getPersona().getNif())){
						//Datos representante
						DatosRepresentante datosRepresentantePoseedor = objFactory.createDatosRepresentante();
						datosRepresentantePoseedor.setDOI(tramite.getRepresentantePoseedorBean().getPersona().getNif());
						poseedor.setDatosRepresentante(datosRepresentantePoseedor);
					}
					poseedor.setActualizacionDomicilio(TRUE.equals(tramite.getAdquirienteBean().getCambioDomicilio())?TipoSINO.SI:TipoSINO.NO);
					//Domicilio Poseedor
					Domicilio domicilioPoseedor = objFactory.createDomicilio();
					Direccion dirPoseedor = poseedorPersona.getDireccion();

					String idProvinciaPoseedor = dirPoseedor.getMunicipio().getProvincia().getIdProvincia()!=null &&
						!"-1".equals(dirPoseedor.getMunicipio().getProvincia().getIdProvincia())?dirPoseedor.getMunicipio().getProvincia().getIdProvincia():CADENA_VACIA;
					String idMunicipioPoseedor = dirPoseedor.getMunicipio().getIdMunicipio()!=null &&
						!"-1".equals(dirPoseedor.getMunicipio().getIdMunicipio())?dirPoseedor.getMunicipio().getIdMunicipio():CADENA_VACIA;
					String idPuebloPoseedor = dirPoseedor.getPueblo()!=null &&
						!"-1".equals(dirPoseedor.getPueblo())?dirPoseedor.getPueblo():CADENA_VACIA;
					String idTipoViaPoseedor = dirPoseedor.getTipoVia().getIdTipoVia()!=null &&
						!"-1".equals(dirPoseedor.getTipoVia().getIdTipoVia())?dirPoseedor.getTipoVia().getIdTipoVia():CADENA_VACIA;
					String idNombreViaPoseedor = dirPoseedor.getNombreVia()!=null &&
						!"-1".equals(dirPoseedor.getNombreVia())?dirPoseedor.getNombreVia():CADENA_VACIA;

					domicilioPoseedor.setMunicipio(idProvinciaPoseedor+idMunicipioPoseedor);
					domicilioPoseedor.setLocalidad(idPuebloPoseedor);
					domicilioPoseedor.setProvincia(idProvinciaPoseedor);
					domicilioPoseedor.setCodigoPostal(null!=dirPoseedor.getCodPostal()?dirPoseedor.getCodPostal():CADENA_VACIA);
					domicilioPoseedor.setTipoVia(idTipoViaPoseedor);
					domicilioPoseedor.setNombreVia(utilesConversiones.ajustarCamposIne(idNombreViaPoseedor));
					//JRG. 18-02-2013 En la ultima actualización del WS de fullCtit se pide que en caso de que no haya de via se inserte "SN".
					if (!(CADENA_VACIA).equals(idNombreViaPoseedor)){
						//Si se ha metido direccion tiene que tener numero de via o SN.
						if (null!=dirPoseedor.getNumero() && !dirPoseedor.getNumero().equals("")){
							domicilioPoseedor.setNumero(dirPoseedor.getNumero());}
						else{
							domicilioPoseedor.setNumero(SN);}
					}else{
						domicilioPoseedor.setNumero(CADENA_VACIA);
					}
					domicilioPoseedor.setKilometro(dirPoseedor.getPuntoKilometrico()!=null?dirPoseedor.getPuntoKilometrico():CADENA_VACIA);
					domicilioPoseedor.setHectometro(dirPoseedor.getHm()!=null?dirPoseedor.getHm():CADENA_VACIA);
					domicilioPoseedor.setBloque(dirPoseedor.getBloque()!=null?dirPoseedor.getBloque():CADENA_VACIA);
					domicilioPoseedor.setPortal(dirPoseedor.getPortal()!=null && dirPoseedor.getPortal().length() <= 2 ?dirPoseedor.getPortal():CADENA_VACIA);
					domicilioPoseedor.setEscalera(dirPoseedor.getEscalera()!=null?dirPoseedor.getEscalera():CADENA_VACIA);
					domicilioPoseedor.setPlanta(dirPoseedor.getPlanta()!=null?dirPoseedor.getPlanta():CADENA_VACIA);
					domicilioPoseedor.setPuerta(dirPoseedor.getPuerta()!=null?dirPoseedor.getPuerta():CADENA_VACIA);
					domicilioPoseedor.setPais(ESP);
					poseedor.setDomicilio(domicilioPoseedor);
					datosEspecificos.setPoseedor(poseedor);
				}
				//Arrendatario
				if(TRUE.equals(tramite.getTramiteTraficoBean().getRenting()) && tramite.getArrendatarioBean()!=null && tramite.getArrendatarioBean().getPersona()!=null &&
					tramite.getArrendatarioBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getArrendatarioBean().getPersona().getNif())){
					Arrendatario arrendatario = objFactory.createDatosEspecificosArrendatario();
					Persona arrendatarioPersona = tramite.getArrendatarioBean().getPersona();
					//Datos personales
					DatosPersonales datosPersonalesArrendatario = objFactory.createDatosPersonales();
					//Datos Filiacion
					org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosPersonales.DatosFiliacion datosFiliacionArrendatario = objFactory.createDatosPersonalesDatosFiliacion();
					if(TipoPersona.Fisica.getValorEnum().equals(tramite.getArrendatarioBean().getPersona().getTipoPersona().getValorEnum())){
						//Persona Fisica
						PersonaFisica personaFisica = objFactory.createDatosPersonalesDatosFiliacionPersonaFisica();
						personaFisica.setNombre(arrendatarioPersona.getNombre());
						personaFisica.setPrimerApellido(arrendatarioPersona.getApellido1RazonSocial());
						personaFisica.setSegundoApellido(!CADENA_VACIA.equals(arrendatarioPersona.getApellido2())?arrendatarioPersona.getApellido2():CADENA_VACIA);
						try {
							// DRC@01-10-2012 Incidencia: 2493
							if (arrendatarioPersona.getFechaNacimientoBean() != null && !arrendatarioPersona.getFechaNacimientoBean().equals(""))
								personaFisica.setFechaNacimiento(format.format(arrendatarioPersona.getFechaNacimientoBean().getTimestamp()));
							else
								personaFisica.setFechaNacimiento(CADENA_VACIA);
						} catch(ParseException e) {
							log.error("Error al parsear la fecha de nacimiento del arrendatario, error: ",e);
							resultado.setError(Boolean.TRUE);
							resultado.addMensajeLista("Error al parsear la fecha de nacimiento del arrendatario.");
						}
						datosFiliacionArrendatario.setPersonaFisica(personaFisica);
					} else {
						//Persona Jurídica
						PersonaJuridica personaJuridica = objFactory.createDatosPersonalesDatosFiliacionPersonaJuridica();
						personaJuridica.setRazonSocial(arrendatarioPersona.getApellido1RazonSocial());
						datosFiliacionArrendatario.setPersonaJuridica(personaJuridica);
					}
					datosPersonalesArrendatario.setDatosFiliacion(datosFiliacionArrendatario);
					datosPersonalesArrendatario.setSexo(arrendatarioPersona.getSexo().equals("H")?"M":arrendatarioPersona.getSexo());
					datosPersonalesArrendatario.setDOI(arrendatarioPersona.getNif());
					arrendatario.setDatosArrendatario(datosPersonalesArrendatario);
					//Domicilio Arrendatario
					Domicilio domicilioArrendatario = objFactory.createDomicilio();
					Direccion dirArrendatario = arrendatarioPersona.getDireccion();
					
					String idProvinciaArrendatario = dirArrendatario.getMunicipio().getProvincia().getIdProvincia()!=null &&
						!"-1".equals(dirArrendatario.getMunicipio().getProvincia().getIdProvincia())?dirArrendatario.getMunicipio().getProvincia().getIdProvincia():CADENA_VACIA;
					String idMunicipioArrendatario = dirArrendatario.getMunicipio().getIdMunicipio()!=null &&
						!"-1".equals(dirArrendatario.getMunicipio().getIdMunicipio())?dirArrendatario.getMunicipio().getIdMunicipio():CADENA_VACIA;
					String idPuebloArrendatario = dirArrendatario.getPueblo()!=null &&
						!"-1".equals(dirArrendatario.getPueblo())?dirArrendatario.getPueblo():CADENA_VACIA;
					String idTipoViaArrendatario = dirArrendatario.getTipoVia().getIdTipoVia()!=null &&
						!"-1".equals(dirArrendatario.getTipoVia().getIdTipoVia())?dirArrendatario.getTipoVia().getIdTipoVia():CADENA_VACIA;
					String idNombreViaArrendatario = dirArrendatario.getNombreVia()!=null &&
						!"-1".equals(dirArrendatario.getNombreVia())?dirArrendatario.getNombreVia():CADENA_VACIA;

					domicilioArrendatario.setMunicipio(idProvinciaArrendatario+idMunicipioArrendatario);
					domicilioArrendatario.setLocalidad(idPuebloArrendatario);
					domicilioArrendatario.setProvincia(idProvinciaArrendatario);
					domicilioArrendatario.setCodigoPostal(null!=dirArrendatario.getCodPostal()?dirArrendatario.getCodPostal():CADENA_VACIA);
					domicilioArrendatario.setTipoVia(idTipoViaArrendatario);
					domicilioArrendatario.setNombreVia(utilesConversiones.ajustarCamposIne(idNombreViaArrendatario));
					//JRG. 18-02-2013 En la ultima actualización del WS de fullCtit se pide que en caso de que no haya de via se inserte "SN".
					if (!(CADENA_VACIA).equals(idNombreViaArrendatario)){
						//Si se ha metido direccion tiene que tener numero de via o SN.
						if (null!=dirArrendatario.getNumero() && !dirArrendatario.getNumero().equals("")){
							domicilioArrendatario.setNumero(dirArrendatario.getNumero());}
						else{
							domicilioArrendatario.setNumero(SN);}
					}else{
						//Si no tiene dirección le mete cadena vacía.
						domicilioArrendatario.setNumero(CADENA_VACIA);
					}
					domicilioArrendatario.setKilometro(dirArrendatario.getPuntoKilometrico()!=null?dirArrendatario.getPuntoKilometrico():CADENA_VACIA);
					domicilioArrendatario.setHectometro(dirArrendatario.getHm()!=null?dirArrendatario.getHm():CADENA_VACIA);
					domicilioArrendatario.setBloque(dirArrendatario.getBloque()!=null?dirArrendatario.getBloque():CADENA_VACIA);
					domicilioArrendatario.setPortal(dirArrendatario.getPortal()!=null && dirArrendatario.getPortal().length() <= 2 ?dirArrendatario.getPortal():CADENA_VACIA);
					domicilioArrendatario.setEscalera(dirArrendatario.getEscalera()!=null?dirArrendatario.getEscalera():CADENA_VACIA);
					domicilioArrendatario.setPlanta(dirArrendatario.getPlanta()!=null?dirArrendatario.getPlanta():CADENA_VACIA);
					domicilioArrendatario.setPuerta(dirArrendatario.getPuerta()!=null?dirArrendatario.getPuerta():CADENA_VACIA);
					domicilioArrendatario.setPais(ESP);
					arrendatario.setDomicilio(domicilioArrendatario);
					datosEspecificos.setArrendatario(arrendatario);
				}
				//Conductor Habitual
				if(tramite.getConductorHabitualBean()!=null && tramite.getConductorHabitualBean().getPersona()!=null &&
						tramite.getConductorHabitualBean().getPersona().getNif()!=null && !(CADENA_VACIA).equals(tramite.getConductorHabitualBean().getPersona().getNif())){
					ConductorHabitual conductorHabitual = objFactory.createDatosEspecificosConductorHabitual();
					conductorHabitual.setDOI(tramite.getConductorHabitualBean().getPersona().getNif());
					try{
						conductorHabitual.setFechaNacimiento(tramite.getConductorHabitualBean().getPersona().getFechaNacimientoBean()!=null && 
							!CADENA_VACIA.equals(tramite.getConductorHabitualBean().getPersona().getFechaNacimientoBean().getAnio())
							?format.format(tramite.getConductorHabitualBean().getPersona().getFechaNacimientoBean().getTimestamp()):CADENA_VACIA);
					}catch(ParseException e){
						log.error("Error al parsear la fecha de nacimiento del conductor habitual, error: ",e);
						resultado.setError(Boolean.TRUE);
						resultado.addMensajeLista("Error al parsear la fecha de nacimiento del conductor habitual.");
					}
					datosEspecificos.setConductorHabitual(conductorHabitual);
				}
				//Acreditacion Derecho
				AcreditacionDerecho acreditacionDerecho = objFactory.createDatosEspecificosAcreditacionDerecho();
				acreditacionDerecho.setSolicitud(TipoSI.SI);
				//en el primer paso de los envios al Full en caso de tramitable con incidencia, Antes tipo1
				// DRC@01-10-2012 Incidencia: 2493
				if (tramite != null && tramite.getTipoTransferencia() != null &&
						(tramite.getTipoTransferencia().equals(TipoTransferencia.tipo1) ||
						tramite.getTipoTransferencia().equals(TipoTransferencia.tipo2) ||
						tramite.getTipoTransferencia().equals(TipoTransferencia.tipo3)) &&
						Paso.UnoFullIncidencias.getValorEnum().equals(paso.getValorEnum())) {
					acreditacionDerecho.setConsentimiento(TipoConsentimiento.NO);
				} else {
					if(tramite.getCodigoTasaInforme()!=null && !"-1".equals(tramite.getCodigoTasaInforme())) {
						acreditacionDerecho.setConsentimiento(TRUE.equals(tramite.getConsentimiento())?TipoConsentimiento.SI:TipoConsentimiento.N_A);
					} else {
						acreditacionDerecho.setConsentimiento(TipoConsentimiento.N_A);
					}
				}
				//Motivo Transmisión
				MotivoTransmision motivoTransmision = objFactory.createDatosEspecificosAcreditacionDerechoMotivoTransmision();
				if(tramite.getFactura()!=null && !CADENA_VACIA.equals(tramite.getFactura())){
					motivoTransmision.setFactura(tramite.getFactura());
				} else {
					motivoTransmision.setContratoCompraventa(TRUE.equals(tramite.getContratoFactura())?TipoSINO.SI:TipoSINO.NO);
				}
				acreditacionDerecho.setMotivoTransmision(motivoTransmision);
				//Fallecimiento / Donación
				// DRC@01-10-2012 Incidencia: 2493
				if (tramite.getModoAdjudicacion() != null) {
					if (ModoAdjudicacion.tipo3.getValorEnum().equals(tramite.getModoAdjudicacion().getValorEnum())) {			
						FallecimientoDonacion fallecimientoDonacion = objFactory.createDatosEspecificosAcreditacionDerechoFallecimientoDonacion();
						if(tramite.getAcreditaHerenciaDonacion()!=null && 
							tramite.getAcreditaHerenciaDonacion().getValorEnum().equals(AcreditacionTrafico.Herencia.getValorEnum())) {
								fallecimientoDonacion.setAcreditacionHerencia(TipoSINO.SI);
						}
						if(tramite.getAcreditaHerenciaDonacion()!=null && 
							tramite.getAcreditaHerenciaDonacion().getValorEnum().equals(AcreditacionTrafico.Posesion.getValorEnum())
							||tramite.getAcreditaHerenciaDonacion().getValorEnum().equals(AcreditacionTrafico.Donacion.getValorEnum())) {
							fallecimientoDonacion.setAcreditacionPosesion(TipoSINO.SI);
						}
						acreditacionDerecho.setFallecimientoDonacion(fallecimientoDonacion);
					}
				}
				datosEspecificos.setAcreditacionDerecho(acreditacionDerecho);
				//Acreditación Fiscal
				AcreditacionFiscal acreditacionFiscal = objFactory.createDatosEspecificosAcreditacionFiscal();
				String provinciaImpuestos = "";
				// DRC@01-10-2012 Incindecia: 2493
				if(tramite != null
						&& tramite.getProvinciaCet() != null
						&& tramite.getProvinciaCet().getIdProvincia() != null
						&& !tramite.getProvinciaCet().getIdProvincia().isEmpty()
						&& !"-1".equalsIgnoreCase(tramite.getProvinciaCet().getIdProvincia())){
					provinciaImpuestos = tramite.getProvinciaCet().getIdProvincia();
				} else if(tramite != null
						&& tramite.getProvinciaCem() != null
						&& tramite.getProvinciaCem().getIdProvincia() != null
						&& !tramite.getProvinciaCem().getIdProvincia().isEmpty()
						&& !"-1".equalsIgnoreCase(tramite.getProvinciaCem().getIdProvincia())){
					provinciaImpuestos = tramite.getProvinciaCem().getIdProvincia();
				} else if(tramite != null
						&& tramite.getAdquirienteBean()!=null
						&& tramite.getAdquirienteBean().getPersona()!=null
						&& tramite.getAdquirienteBean().getPersona().getDireccion()!=null
						&& tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio()!=null
						&& tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia()!=null
						&& tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null
						&& !tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("")
						&& !tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("-1")){				
					provinciaImpuestos = tramite.getAdquirienteBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia();
				} else if(tramite != null
						&& tramite.getPoseedorBean()!=null
						&& tramite.getPoseedorBean().getPersona()!=null
						&& tramite.getPoseedorBean().getPersona().getDireccion()!=null
						&& tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio()!=null
						&& tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia()!=null
						&& tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null
						&& !tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("")
						&& !tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equalsIgnoreCase("-1")){		
					provinciaImpuestos = tramite.getPoseedorBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia();
				} else if(tramite != null
						&& tramite.getTramiteTraficoBean() !=null
						&& tramite.getTramiteTraficoBean().getVehiculo()!=null
						&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()!=null 
						&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio()!=null
						&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia()!=null 
						&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia()!=null
						&& !"-1".equals(tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia())){
					provinciaImpuestos = tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getMunicipio().getProvincia().getIdProvincia();
				} else if(!tramite.getTipoTransferencia().getValorEnum().equals("5")) {
					resultado.setError(Boolean.TRUE);
					resultado.addMensajeLista("El vehículo o titular (adquiriente), deben tener una dirección asociada.");
				}
				String provinciaImpuestosCet= "00";
				String codigoElectronico = null!=tramite.getCetItp() && !CADENA_VACIA.equals(tramite.getCetItp())?tramite.getCetItp():_00000000;
				if(!codigoElectronico.equals(_00000000)){
					provinciaImpuestosCet = provinciaImpuestos;
				}
				if(null!=tramite.getTipoTransferencia()){
					if(!tramite.getTipoTransferencia().getValorEnum().equals("4") && !tramite.getTipoTransferencia().getValorEnum().equals("5")){
						if(!tramite.getModoAdjudicacion().getValorEnum().equals(ModoAdjudicacion.tipo3.getValorEnum()) &&
							(tramite.getFactura()==null || (CADENA_VACIA).equals(tramite.getFactura()))){

							ITP itp = objFactory.createDatosEspecificosAcreditacionFiscalITP();
							//Acreditación Pago
							if(!TRUE.equals(tramite.getExentoItp()) && !TRUE.equals(tramite.getNoSujetoItp())){
								AcreditacionPago acreditacionPagoITP = 
										objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionPago();
								TipoCEM cetItp = objFactory.createTipoCEM();

								cetItp.setCodigoElectronico(codigoElectronico);

								cetItp.setCodigoProvincia(provinciaImpuestosCet);
								acreditacionPagoITP.setCET(cetItp);
								acreditacionPagoITP.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():CADENA_VACIA);
								itp.setAcreditacionPago(acreditacionPagoITP);
							}
							if(TRUE.equals(tramite.getExentoItp())){
								//Acreditación de exención
								AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionExencion();
								acreditacionExencion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():null);
								acreditacionExencion.setExencion(TipoSI.SI);
								TipoCEM cetItp = objFactory.createTipoCEM();
								cetItp.setCodigoElectronico(codigoElectronico);
								cetItp.setCodigoProvincia(provinciaImpuestosCet);
								acreditacionExencion.setCET(cetItp);
								itp.setAcreditacionExencion(acreditacionExencion);
							} else if(TRUE.equals(tramite.getNoSujetoItp())){
								//Acreditación de no sujección
								AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionNoSujecion();
								acreditacionNoSujecion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():CADENA_VACIA);
								acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
								TipoCEM cetItp = objFactory.createTipoCEM();
								cetItp.setCodigoElectronico(codigoElectronico);
								cetItp.setCodigoProvincia(provinciaImpuestosCet);
								acreditacionNoSujecion.setCET(cetItp);
								itp.setAcreditacionNoSujecion(acreditacionNoSujecion);
							}
							acreditacionFiscal.setITP(itp);
						}
					}else{
						if(null!=tramite.getCetItp() && !CADENA_VACIA.equals(tramite.getCetItp())){
							if(!tramite.getModoAdjudicacion().getValorEnum().equals(ModoAdjudicacion.tipo3.getValorEnum())
									&& (tramite.getFactura()==null || (CADENA_VACIA).equals(tramite.getFactura()))){
								ITP itp = objFactory.createDatosEspecificosAcreditacionFiscalITP();
								//Acreditación Pago
								if(!TRUE.equals(tramite.getExentoItp()) && !TRUE.equals(tramite.getNoSujetoItp())){
									AcreditacionPago acreditacionPagoITP = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionPago();
									TipoCEM cetItp = objFactory.createTipoCEM();
									cetItp.setCodigoElectronico(tramite.getCetItp());
									cetItp.setCodigoProvincia(provinciaImpuestosCet);
									acreditacionPagoITP.setCET(cetItp);
									acreditacionPagoITP.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():CADENA_VACIA);
									itp.setAcreditacionPago(acreditacionPagoITP);
								}
								if(TRUE.equals(tramite.getExentoItp())){
									//Acreditación de exención
									AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionExencion();
									acreditacionExencion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():null);
									acreditacionExencion.setExencion(TipoSI.SI);
									TipoCEM cetItp = objFactory.createTipoCEM();
									cetItp.setCodigoElectronico(tramite.getCetItp());
									cetItp.setCodigoProvincia(provinciaImpuestosCet);
									acreditacionExencion.setCET(cetItp);
									itp.setAcreditacionExencion(acreditacionExencion);
								} else if(TRUE.equals(tramite.getNoSujetoItp())){
									//Acreditación de no sujección
									AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalITPAcreditacionNoSujecion();
									acreditacionNoSujecion.setModelo(!"-1".equals(tramite.getModeloItp())?tramite.getModeloItp():CADENA_VACIA);
									acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
									TipoCEM cetItp = objFactory.createTipoCEM();
									cetItp.setCodigoElectronico(tramite.getCetItp());
									cetItp.setCodigoProvincia(provinciaImpuestosCet);
									acreditacionNoSujecion.setCET(cetItp);
									itp.setAcreditacionNoSujecion(acreditacionNoSujecion);
								}
								acreditacionFiscal.setITP(itp);
							}
						}
					}
				}
				//ISD
				if(tramite.getModoAdjudicacion().getValorEnum().equals(ModoAdjudicacion.tipo3.getValorEnum())
						&& (tramite.getFactura()==null || (CADENA_VACIA).equals(tramite.getFactura()))){
					ISD isd = null;
					if(TRUE.equals(tramite.getExentoIsd())){
						//Acreditación de exención
						isd = objFactory.createDatosEspecificosAcreditacionFiscalISD();
						org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.ISD.AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalISDAcreditacionExencion();
						acreditacionExencion.setModelo(tramite.getModeloIsd() != null && !"-1".equals(tramite.getModeloIsd())?tramite.getModeloIsd():CADENA_VACIA);
						acreditacionExencion.setExencion(TipoSI.SI);
						isd.setAcreditacionExencion(acreditacionExencion);
					} else if(TRUE.equals(tramite.getNoSujetoIsd())){
						//Acreditación de no sujección
						isd = objFactory.createDatosEspecificosAcreditacionFiscalISD();
						org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.ISD.AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalISDAcreditacionNoSujecion();
						acreditacionNoSujecion.setModelo(tramite.getModeloIsd() != null && !"-1".equals(tramite.getModeloIsd())?tramite.getModeloIsd():CADENA_VACIA);
						acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
						isd.setAcreditacionNoSujecion(acreditacionNoSujecion);
					}
					if(isd != null){
						acreditacionFiscal.setISD(isd);
					}
				}
				String provinciaImpuestosCem= "00";
				String codigoElectronicoMat = null!=tramite.getTramiteTraficoBean().getCemIedtm() && !CADENA_VACIA.equals(tramite.getTramiteTraficoBean().getCemIedtm())?tramite.getTramiteTraficoBean().getCemIedtm():_00000000;
				if(!codigoElectronicoMat.equals(_00000000)){
					provinciaImpuestosCem = provinciaImpuestos; 
				}
				//IEDTM
				IEDMT iedmt = objFactory.createDatosEspecificosAcreditacionFiscalIEDMT();
				//Acreditación Pago
				if(!TRUE.equals(tramite.getTramiteTraficoBean().getExentoIedtm()) && !TRUE.equals(tramite.getTramiteTraficoBean().getNoSujetoIedtm())){
					org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.IEDMT.AcreditacionPago acreditacionPagoIEDMT = objFactory.createDatosEspecificosAcreditacionFiscalIEDMTAcreditacionPago();
					acreditacionPagoIEDMT.setModelo(null!=tramite.getTramiteTraficoBean().getModeloIedtm() && !"-1".equals(tramite.getTramiteTraficoBean().getModeloIedtm())?tramite.getTramiteTraficoBean().getModeloIedtm():"576");
					TipoCEM cem = objFactory.createTipoCEM();
					cem.setCodigoElectronico(codigoElectronicoMat);
					// DRC@24-01-2013 Incidencia Mantia: 3437
					if (tramite != null && tramite.getProvinciaCem() != null && tramite.getProvinciaCem().getIdProvincia() != null 
							&& !tramite.getProvinciaCem().getIdProvincia().equalsIgnoreCase("-1")){
						cem.setCodigoProvincia(tramite.getProvinciaCem().getIdProvincia());
					} else {
						cem.setCodigoProvincia(provinciaImpuestosCem);
					}
					acreditacionPagoIEDMT.setCEM(cem);
					iedmt.setAcreditacionPago(acreditacionPagoIEDMT);
				}
				if(TRUE.equals(tramite.getTramiteTraficoBean().getExentoIedtm())){
					//Acreditación de exención
					org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.IEDMT.AcreditacionExencion acreditacionExencion = objFactory.createDatosEspecificosAcreditacionFiscalIEDMTAcreditacionExencion();
					acreditacionExencion.setModelo(!"-1".equals(tramite.getTramiteTraficoBean().getModeloIedtm())?tramite.getTramiteTraficoBean().getModeloIedtm():"576");
					acreditacionExencion.setExencion(TipoSI.SI);
					TipoCEM cem = objFactory.createTipoCEM();
					cem.setCodigoElectronico(codigoElectronicoMat);
					// DRC@24-01-2013 Incidencia Mantia: 3437
					if (tramite != null && tramite.getProvinciaCem() != null && tramite.getProvinciaCem().getIdProvincia() != null){
						cem.setCodigoProvincia(tramite.getProvinciaCem().getIdProvincia());
					}else{
						cem.setCodigoProvincia(provinciaImpuestosCem);
					}
					acreditacionExencion.setCEM(cem);
					iedmt.setAcreditacionExencion(acreditacionExencion);
				} else if(TRUE.equals(tramite.getTramiteTraficoBean().getNoSujetoIedtm())){
					//Acreditación de no sujección
					org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.DatosEspecificos.AcreditacionFiscal.IEDMT.AcreditacionNoSujecion acreditacionNoSujecion = objFactory.createDatosEspecificosAcreditacionFiscalIEDMTAcreditacionNoSujecion();
					acreditacionNoSujecion.setModelo(!"-1".equals(tramite.getTramiteTraficoBean().getModeloIedtm())?tramite.getTramiteTraficoBean().getModeloIedtm():"576");
					acreditacionNoSujecion.setNoSujecion(TipoSI.SI);
					TipoCEM cem = objFactory.createTipoCEM();
					cem.setCodigoElectronico(codigoElectronicoMat);
					// DRC@24-01-2013 Incidencia Mantia: 3437
					if (tramite != null && tramite.getProvinciaCem() != null && tramite.getProvinciaCem().getIdProvincia() != null){
						cem.setCodigoProvincia(tramite.getProvinciaCem().getIdProvincia());
					}else{
						cem.setCodigoProvincia(provinciaImpuestosCem);
					}
					acreditacionNoSujecion.setCEM(cem);
					iedmt.setAcreditacionNoSujecion(acreditacionNoSujecion);
					if(tramite.getTramiteTraficoBean().getCemIedtm() == "00000000"){
						tramite.getProvinciaCem().setIdProvincia("00");
					}
				}
				acreditacionFiscal.setIEDMT(iedmt);
				//IVTM
				IVTM ivtm = objFactory.createDatosEspecificosAcreditacionFiscalIVTM();
				ivtm.setAltaIVTM(TRUE.equals(tramite.getAltaIvtm())?TipoSINO.SI:TipoSINO.NO);
				acreditacionFiscal.setIVTM(ivtm);
				datosEspecificos.setAcreditacionFiscal(acreditacionFiscal);
				//Acreditación Actividad
				AcreditacionActividad acreditacionActividad = objFactory.createDatosEspecificosAcreditacionActividad();
				acreditacionActividad.setVehiculosAgricolas(Boolean.TRUE.equals(tramite.getTramiteTraficoBean().getVehiculo().getVehiculoAgricola())?TipoSINO.SI:TipoSINO.NO);
				datosEspecificos.setAcreditacionActividad(acreditacionActividad);
				//Texto Legal
				datosEspecificos.setTextoLegal(TipoTextoLegal.ESTE_COLEGIO_DE_GESTORES_ADMINISTRATIVOS_HA_VERIFICADO_QUE_LA_SOLICITUD_DE_CAMBIO_DE_TITULARIDAD_PRESENTADA_CUMPLE_TODAS_LAS_OBLIGACIONES_ESTABLECIDAS_EN_EL_REGLAMENTO_GENERAL_DE_VEHÍCULOS_Y_RESTO_DE_NORMATIVA_APLICABLE_ASÍ_COMO_LAS_DERIVADAS_DE_LA_APLICACIÓN_DE_LAS_INSTRUCCIONES_DE_LA_DIRECCIÓN_GENERAL_DE_TRÁFICO_VIGENTES_EN_EL_MOMENTO_DE_LA_SOLICITUD_Y_DEL_PROTOCOLO_DE_CAMBIO_DE_TITULARIDAD_FIRMADO_ENTRE_EL_CONSEJO_GENERAL_DE_COLEGIOS_DE_GESTORES_ADMINISTRATIVOS_Y_LA_DIRECCIÓN_GENERAL_DE_TRÁFICO);
				//Firma Gestor
				datosEspecificos.setFirmaGestor(new byte[0]);
				datosFirmados.setDatosEspecificos(datosEspecificos);
				if(null!=tramite.getFactura() && !CADENA_VACIA.equals(tramite.getFactura())){
					acreditacionFiscal.setIVA(TipoSI.SI);
					if (datosFirmados.getDatosEspecificos().getAcreditacionFiscal()!=null
							&&datosFirmados.getDatosEspecificos().getAcreditacionFiscal()!=null
								&&datosFirmados.getDatosEspecificos().getAcreditacionFiscal().getITP()!=null
									&&datosFirmados.getDatosEspecificos().getAcreditacionFiscal().getITP().getAcreditacionPago()!=null) {
						datosFirmados.getDatosEspecificos().getAcreditacionFiscal().getITP().getAcreditacionPago().setCET(null);
					}
				}
				solRegEntrada.setDatosFirmados(datosFirmados);
				solRegEntrada.setFirma(new byte[0]);
				solRegEntrada.setVersion(_1_0);

				if(!resultado.getError()) {
					resultado = anhadirFirmasTransTelematicaHSM(solRegEntrada,contratoDto.getColegiadoDto().getAlias());
					if(!resultado.getError()){
						String nombreFichero = ConstantesGestorFicheros.NOMBRE_CTIT + tramite.getTramiteTraficoBean().getNumExpediente();
						solRegEntrada = resultado.getSolicitudRegistroEntrada();
						resultado = validacionXSD(solRegEntrada, nombreFichero);
						if(!resultado.getError()){
							resultado = guardarXmlEnvioTransTelematico(solRegEntrada,tramite.getTramiteTraficoBean().getNumExpediente(),nombreFichero);
						}
					}
			}
		}
		return resultado;
	}

	private ResultadoCtitBean guardarXmlEnvioTransTelematico(SolicitudRegistroEntrada solRegEntrada, BigDecimal numExpediente, String nombreFichero) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			XmlCtitSegaFactory xmlFactory = new XmlCtitSegaFactory();
			String xmlFirmado = xmlFactory.toXML(solRegEntrada);
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CTIT);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.CTITENVIO);
			ficheroBean.setNombreDocumento(nombreFichero);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			ficheroBean.setSobreescribir(true);
			gestorDocumentos.crearFicheroXml(ficheroBean, XmlCheckCTITSegaFactory.NAME_CONTEXT, solRegEntrada, xmlFirmado, null);
			resultado.setNombreXml(nombreFichero);
		} catch (OegamExcepcion e) {
			resultado.setMensajeError("No se pudo generar el fichero.");
			resultado.setError(Boolean.TRUE);
			log.error("Error al guardar el fichero, error: ", e);
		} catch (Throwable e) {
			resultado.setMensajeError("No se pudo generar el fichero XML.");
			resultado.setError(Boolean.TRUE);
			log.error("Error al guardar el XML de CTIT en disco, error: ", e);
		}
		return resultado;
	}

	private ResultadoCtitBean validacionXSD(SolicitudRegistroEntrada solicitudRegistroEntrada, String nombreFichero) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			XmlCtitSegaFactory xmlCtitSegaFactory = new XmlCtitSegaFactory();
			Marshaller marshaller = (Marshaller) xmlCtitSegaFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream(nombreFichero));
			File fichero = new File(nombreFichero);
			String resultVal = xmlCtitSegaFactory.validarXMLCtitSega(fichero);
			if(!"CORRECTO".equals(resultVal)){
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista(resultVal);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el xml de envio contra el xsd, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de validar el xml de envío.");
		}
		return resultado;
	}

	private ResultadoCtitBean anhadirFirmasTransTelematicaHSM(SolicitudRegistroEntrada solRegEntrada, String alias) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		resultado = realizarFirmaColegiado(solRegEntrada,alias);
		if(!resultado.getError()){
			resultado = realizarFirmaColegio(solRegEntrada, gestorPropiedades.valorPropertie("trafico.claves.colegio.alias"));
		}
		if(!resultado.getError()){
			resultado.setSolicitudRegistroEntrada(solRegEntrada);
		}
		return resultado;
	}

	private ResultadoCtitBean realizarFirmaColegio(SolicitudRegistroEntrada solRegEntrada, String alias) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			Boolean noEsCertCaducado = comprobarCaducidadCertificado(alias);
			if(noEsCertCaducado){
				resultado = firmarSolicitud(solRegEntrada,alias);
				if(!resultado.getError()){
					try {
						solRegEntrada.setFirma(resultado.getXmlFimado().getBytes(UTF_8));
					} catch (Exception e) {
						resultado.setError(Boolean.TRUE);
						resultado.addMensajeLista("Error al adjuntar la firma del colegiado a la solicitud.");
						log.error("Error al adjuntar la firma del colegiado a la solicitud, error: ",e);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("No se puede realizar la tramitación telemática porque el certificado del colegio se encuentra caducado en estos momentos.");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Ha sucedido un error a la hora de firmar la solicitud de transmision por parte del colegiado, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de firmar la solicitud de transmision.");
		}
		return resultado;
	}

	public ResultadoCtitBean realizarFirmaColegiado(SolicitudRegistroEntrada solRegEntrada, String alias)  {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		try {
			Boolean noEsCertCaducado = comprobarCaducidadCertificado(alias);
			if(noEsCertCaducado){
				resultado = firmarSolicitud(solRegEntrada,alias);
				if(!resultado.getError()){
					try {
						solRegEntrada.getDatosFirmados().getDatosEspecificos().setFirmaGestor(resultado.getXmlFimado().getBytes(UTF_8));
					} catch (Exception e) {
						resultado.setError(Boolean.TRUE);
						resultado.addMensajeLista("Error al adjuntar la firma del colegiado a la solicitud.");
						log.error("Error al adjuntar la firma del colegiado a la solicitud, error: ",e);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeLista("No se puede realizar la tramitación telemática porque el certificado del colegiado se encuentra caducado en estos momentos.");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Ha sucedido un error a la hora de firmar la solicitud de transmision por parte del colegiado, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensajeLista("Ha sucedido un error a la hora de firmar la solicitud de transmision.");
		}
		return resultado;
	}

	private ResultadoCtitBean firmarSolicitud(SolicitudRegistroEntrada solRegEntrada, String alias) throws UnsupportedEncodingException  {
		ResultadoCtitBean resultadoCtitBean = new ResultadoCtitBean(Boolean.FALSE);
		XmlCtitSegaFactory xmlCtitSegaFactory = new XmlCtitSegaFactory(); 
		String xml = xmlCtitSegaFactory.toXML(solRegEntrada);
		//Quitamos los namespaces porque da error
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");

		//Obtenemos los datos que realmente se tienen que firmar
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		String datosFirmados = xml.substring(inicio, fin);
		log.info("LOG_CTIT DATOS FIRMADOS "+datosFirmados);
		log.info("Datos a firmar:" + datosFirmados);

		//Se codifican los datos a firmar en base 64
		String encodedAFirmar = CADENA_VACIA;

		encodedAFirmar = utiles.doBase64Encode(datosFirmados.getBytes(UTF_8));
		encodedAFirmar = encodedAFirmar.replaceAll("\n", CADENA_VACIA);
		log.info("LOG_CTIT ENCODED A FIRMAR: "+encodedAFirmar);

		//Se construye el XML que contiene los datos a firmar
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		log.info("XML a firmar:" + xmlAFirmar);
		if(xmlAFirmar != null && !xmlAFirmar.isEmpty()){
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			String idFirma = utilesViafirma.firmarMensajeTransmisionServidor(xmlAFirmar, alias);
			log.info("idFirma Trans telematica= " + idFirma);
			if(ERROR.equals(idFirma)){
				resultadoCtitBean.setMensajeError("Ha ocurrido un error durante el proceso de firma en servidor del tramite");
				resultadoCtitBean.setError(Boolean.TRUE); 
			} else {
				resultadoCtitBean.setXmlFimado(utilesViafirma.getDocumentoFirmado(idFirma));
				log.info("XML custodiado recuperado a traves del idFirma=" +idFirma); 
				log.debug("XML firmado : " + resultadoCtitBean.getXmlFimado()); 
			}
		}
		return resultadoCtitBean;
	}

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
		solicitudPruebaCertificado = objctFactory.createSolicitudPruebaCertificado();
		trafico.beans.jaxb.pruebaCertificado.DatosFirmados datosFirmados = objctFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		return solicitudPruebaCertificado;
	}

}