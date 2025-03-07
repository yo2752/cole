package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioConsultaTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioFacturacionTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.CertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.IreportTablaCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioConsultaTasaImpl implements ServicioConsultaTasa {

	private static final long serialVersionUID = -3560258654230310447L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaTasaImpl.class);

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioTramiteTraficoSolInfo servicioTramiteTraficoSolInfo;

	@Autowired
	private ServicioFacturacionTasa servicioFacturacionTasa;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoCertificadoTasasBean generarCertificadoTasas(String listaChecksConsultaTasa, String listaChecksConsultaTasaPegatinas, BigDecimal idContrato) {
		ResultadoCertificadoTasasBean resultado = new ResultadoCertificadoTasasBean(false);
		try {
			if((listaChecksConsultaTasa == null || listaChecksConsultaTasa.isEmpty()) 
					&& (listaChecksConsultaTasaPegatinas == null || listaChecksConsultaTasaPegatinas.isEmpty())){
				resultado.setError(true);
				resultado.setMensajeError("Debe seleccionar alguna tasa para generar el certificado.");
			}else{
				List<CertificadoTasasBean> listaCertificados = new ArrayList<>();
				if(listaChecksConsultaTasa != null && !listaChecksConsultaTasa.isEmpty()){
					generarCertificadoTasas(listaChecksConsultaTasa,resultado, listaCertificados);
				}
				if(listaChecksConsultaTasaPegatinas != null && !listaChecksConsultaTasaPegatinas.isEmpty()){
					generarCertificaTasasPegatinas(listaChecksConsultaTasaPegatinas,resultado, listaCertificados);
				}
				if(!listaCertificados.isEmpty()){
					generarPdfCertificadosTasas(listaCertificados,resultado,idContrato);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el certificado de las tasas seleccionadas, error: ",e);
			resultado.setError(true);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el certificado de las tasas seleccionadas.");
		}
		return resultado;
	}

	@Override
	public void generarCertificadoTasaCambSer(TramiteTraficoDto tramiteTraficoDto, TramiteTrafTranDto tramiteTrafTranDto, List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado) {
		TasaDto tasaCambServDto = servicioTasa.getTasaCodigoTasa(tramiteTrafTranDto.getCodigoTasaCamser());
		ResultBean resultValidar = verificarDatosTasaCambServicio(tramiteTrafTranDto, tasaCambServDto);
		if(!resultValidar.getError()){
			PersonaDto titularFacturacion = getTitularFacturacionTasaExpediente(tramiteTrafTranDto.getNumExpediente());
			if(titularFacturacion != null){
				String matricula = null;
				if(tramiteTrafTranDto.getVehiculoDto().getMatricula() == null || tramiteTrafTranDto.getVehiculoDto().getMatricula().isEmpty()){
					matricula = tramiteTrafTranDto.getVehiculoDto().getBastidor();
				}else{
					matricula = tramiteTrafTranDto.getVehiculoDto().getMatricula();
				}
				resultado.addOk();
				listaCertificados.add(new CertificadoTasasBean(tramiteTrafTranDto.getAdquiriente().getPersona(), titularFacturacion, tasaCambServDto, matricula));
				resultado.aniadirMensajeListaOk("Para el nº de expediente: " + tramiteTrafTranDto.getNumExpediente() + ", la tasa : " + tasaCambServDto.getCodigoTasa() + ", de cambio de servicio se ha generado correctamente.");
				setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(tramiteTraficoDto.getTasa().getFechaAsignacion()),resultado);
				resultado.addOk();
				listaCertificados.add(new CertificadoTasasBean(tramiteTrafTranDto.getAdquiriente().getPersona(), titularFacturacion, tramiteTrafTranDto.getTasa(), matricula));
				resultado.aniadirMensajeListaOk("Para el nº de expediente: " + tramiteTrafTranDto.getNumExpediente() + ", la tasa : " + tramiteTrafTranDto.getTasa().getCodigoTasa() + ", se ha generado correctamente.");
				setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(tramiteTraficoDto.getTasa().getFechaAsignacion()),resultado);
			}else{
				resultado.addError();
				resultado.aniadirMensajeListaError("No se ha recuperado el nif del titular de facturación para el expediente: " + tramiteTrafTranDto.getNumExpediente());
			}
		}else{
			resultado.addError();
			resultado.aniadirMensajeListaError(resultValidar.getListaMensajes().get(0));
		}
	}

	@Override
	public void generarCertificadoTasaConsultaTramite(TramiteTraficoDto tramiteTraficoDto, List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado) {
		Boolean esOk = true;
		ResultBean resultValidar = validarTasaCertificadoDesdeTramite(tramiteTraficoDto);
		if(!resultValidar.getError()){
			ResultBean resultValidarEstadosTramite = validarEstadoTramiteTasa(tramiteTraficoDto);
			if(!resultValidarEstadosTramite.getError()){
				PersonaDto titularFacturacion = null;
				if(!TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())){
					titularFacturacion = getTitularFacturacionTasaExpediente(tramiteTraficoDto.getNumExpediente());
					if(titularFacturacion == null){
						resultado.addError();
						resultado.aniadirMensajeListaError("No se ha recuperado el nif del titular de facturación para el expediente " + tramiteTraficoDto.getNumExpediente());
						esOk = false;
					}
				}else{
					titularFacturacion = new PersonaDto();
					titularFacturacion.setNombre("Duplicado sin Titular de Facturación");
					titularFacturacion.setNif("");
				}
				if(esOk){
					String matricula = null;
					if(tramiteTraficoDto.getVehiculoDto().getMatricula() == null 
							|| tramiteTraficoDto.getVehiculoDto().getMatricula().isEmpty()){
						matricula = tramiteTraficoDto.getVehiculoDto().getBastidor();
					}else{
						matricula = tramiteTraficoDto.getVehiculoDto().getMatricula();
					}
					listaCertificados.add(new CertificadoTasasBean(getTitularVehiculo(tramiteTraficoDto), titularFacturacion, tramiteTraficoDto.getTasa(), matricula));
					resultado.addOk();
					resultado.aniadirMensajeListaOk("Tasa: " + tramiteTraficoDto.getTasa().getCodigoTasa() + ", generada correctamente para el expediente: " + tramiteTraficoDto.getNumExpediente() + ".");
					setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(tramiteTraficoDto.getTasa().getFechaAsignacion()),resultado);
				}
			}else{
				resultado.addError();
				resultado.aniadirMensajeListaError(resultValidarEstadosTramite.getListaMensajes().get(0));
			}
		}else{
			resultado.addError();
			resultado.aniadirMensajeListaError(resultValidar.getListaMensajes().get(0));
		}
	}

	@Override
	public void generarCertificadoSolInfoDesdeConsultaTramite(TramiteTraficoDto tramiteTraficoDto, List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado) {
		PersonaDto titularFacturacion = getTitularFacturacionTasaExpediente(tramiteTraficoDto.getNumExpediente());
		if(titularFacturacion != null){
			ResultBean resultBeanTramSolicitud = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfo(tramiteTraficoDto.getNumExpediente());
			if(!resultBeanTramSolicitud.getError()){
				TramiteTrafSolInfoDto tramiteTrafSolInfoDto = (TramiteTrafSolInfoDto) resultBeanTramSolicitud.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
				if(tramiteTrafSolInfoDto != null && tramiteTrafSolInfoDto.getSolicitudes() != null && !tramiteTrafSolInfoDto.getSolicitudes().isEmpty()){
					for(SolicitudInformeVehiculoDto solicitudInformeVehiculoDto : tramiteTrafSolInfoDto.getSolicitudes()){
						ResultBean resultValidarTasaSolInfo = validarTasaTramiteSolicitud(solicitudInformeVehiculoDto, tramiteTraficoDto.getNumExpediente());
						if(!resultValidarTasaSolInfo.getError()){
							String matricula = null;
							if(solicitudInformeVehiculoDto.getVehiculo().getMatricula() == null || solicitudInformeVehiculoDto.getVehiculo().getMatricula().isEmpty()){
								matricula = solicitudInformeVehiculoDto.getVehiculo().getBastidor();
							}else{
								matricula = solicitudInformeVehiculoDto.getVehiculo().getMatricula();
							}
							listaCertificados.add(new CertificadoTasasBean(new PersonaDto(), titularFacturacion, solicitudInformeVehiculoDto.getTasa(), matricula));
							resultado.addOk();
							resultado.aniadirMensajeListaOk("La tasa : " +solicitudInformeVehiculoDto.getTasa().getCodigoTasa() + ", asociada al expediente " + tramiteTraficoDto.getNumExpediente() + " de solicitud de información se ha generado correctamente.");
							setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(solicitudInformeVehiculoDto.getTasa().getFechaAsignacion()),resultado);
						}else{
							resultado.addError();
							resultado.aniadirMensajeListaError(resultValidarTasaSolInfo.getListaMensajes().get(0));
						}
					}
				}else{
					resultado.addError();
					resultado.aniadirMensajeListaError("No se ha recuperado las solicitudes de informacion de vehiculo para el expediente " + tramiteTraficoDto.getNumExpediente());
				}
			}else{
				resultado.addError();
				resultado.aniadirMensajeListaError("No se ha recuperado datos para el expediente " + tramiteTraficoDto.getNumExpediente());
			}
		}else{
			resultado.addError();
			resultado.aniadirMensajeListaError("No se ha recuperado el nif del titular de facturación para el expediente: " + tramiteTraficoDto.getNumExpediente());
		}

	}

	@Override
	public void generarPdfCertificadosTasas(List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado, BigDecimal idContrato) {
		try {
			ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
			String rutaPlantilla = gestorPropiedades.valorPropertie("trafico.ruta.certificado.tasas.plantilla");
			String nombreSecretario = gestorPropiedades.valorPropertie("ICOGAM.secretario.junta.nombre");
			String nifSecretario = gestorPropiedades.valorPropertie("ICOGAM.secretario.junta.nif");
			if(nombreSecretario == null || nombreSecretario.isEmpty() || nifSecretario == null || nifSecretario.isEmpty()){
				resultado.setError(true);
				resultado.setMensajeError("No se han configurado correctamente los datos del secretario de la junta del ICOGAM requeridos para el certificado de tasas");
			}else{
				HashMap<String,Object> parametros = new HashMap<String,Object>();
				parametros.put("nombreColegiado", contratoDto.getColegiadoDto().getUsuario().getApellidosNombre());
				parametros.put("numeroColegiado", contratoDto.getColegiadoDto().getNumColegiado());
				parametros.put("fechaInicio", utilesFecha.formatoFecha("dd/MM/yyyy", resultado.getFechaInicio()));
				parametros.put("fechaFin", utilesFecha.formatoFecha("dd/MM/yyyy",resultado.getFechaFin()));
				parametros.put("nombreSecretario", nombreSecretario);
				Date fechaActual = new Date();
				JRBeanArrayDataSource arrayBeans = new JRBeanArrayDataSource(convertirCetificadoTasaBeanToArrayIreport(listaCertificados));
				JasperPrint print = JasperFillManager.fillReport(rutaPlantilla, parametros, arrayBeans);
				String nombreFichero = "certificadoTasas_" + contratoDto.getColegiadoDto().getNumColegiado() +"_" + utilesFecha.formatoFecha("ddMMyyyyHHMMSSFF", fechaActual);
				byte[] byteFinal = JasperExportManager.exportReportToPdf(print);
				gestorDocumentos.guardarFichero(ConstantesGestorFicheros.TASAS, ConstantesGestorFicheros.CERTIFICADO_TASAS, utilesFecha.getFechaConDate(fechaActual), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF, byteFinal);
				resultado.setNombreFichero(nombreFichero);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el pdf de certificados de tasas, error: ", e);
			resultado.setError(true);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el pdf de certificados de tasas.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el pdf de certificados de tasas, error: ", e);
			resultado.setError(true);
			resultado.setMensajeError("Ha sucedido un error a la hora de guardar el pdf de certificados de tasas.");
		}
	}

	@Override
	public File getFicheroCertificadosTasa(String nombreFichero) {
		FileResultBean fichero = null;
		String fecha = nombreFichero.split("_")[2];
		try {
			fichero =  gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TASAS, ConstantesGestorFicheros.CERTIFICADO_TASAS,
					utilesFecha.getFechaConDate(utilesFecha.formatoFecha("ddMMyyyyHHMMSSFF", fecha)), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
			if(fichero.getFile() != null){
				return fichero.getFile();
			}
		} catch (OegamExcepcion e) {
			log.error("Error al imprimir el certificado de tasas, error: ",e);
		}
		return null;
	}

	private IreportTablaCertificadoTasasBean[] convertirCetificadoTasaBeanToArrayIreport(List<CertificadoTasasBean> listaCertificados) {
		IreportTablaCertificadoTasasBean[] array = new IreportTablaCertificadoTasasBean[listaCertificados.size()];
		int cont = 0;
		for(CertificadoTasasBean certificadoTasasBean : listaCertificados){
			IreportTablaCertificadoTasasBean ireportTablaCertificadoTasasBean = conversor.transform(certificadoTasasBean, IreportTablaCertificadoTasasBean.class);
			String titularVehiculo = "";
			if(certificadoTasasBean.getTitularVehiculo().getNombre() != null){
				titularVehiculo = certificadoTasasBean.getTitularVehiculo().getNombre();
			}
			if(certificadoTasasBean.getTitularVehiculo().getApellido1RazonSocial() != null){
				titularVehiculo += " " + certificadoTasasBean.getTitularVehiculo().getApellido1RazonSocial();
			}
			if(certificadoTasasBean.getTitularVehiculo().getApellido2() != null){
				titularVehiculo += " " + certificadoTasasBean.getTitularVehiculo().getApellido2();
			}
			ireportTablaCertificadoTasasBean.setTitularVehiculo(titularVehiculo);

			String titularFacturacion = "";
			if(certificadoTasasBean.getTitularFacturacion().getNombre() != null){
				titularFacturacion = certificadoTasasBean.getTitularFacturacion().getNombre();
			}
			if(certificadoTasasBean.getTitularFacturacion().getApellido1RazonSocial() != null){
				titularFacturacion += " " + certificadoTasasBean.getTitularFacturacion().getApellido1RazonSocial();
			}
			if(certificadoTasasBean.getTitularFacturacion().getApellido2() != null){
				titularFacturacion += " " + certificadoTasasBean.getTitularFacturacion().getApellido2();
			}
			if(certificadoTasasBean.getTitularFacturacion().getNif() != null) {
				titularFacturacion += " " + certificadoTasasBean.getTitularFacturacion().getNif();
			}
			ireportTablaCertificadoTasasBean.setTitularFacturacion(titularFacturacion);
			if(certificadoTasasBean.getTasa().getFechaAsignacion() != null){
				try {
					ireportTablaCertificadoTasasBean.setFechaAplicacion(utilesFecha.formatoFecha("dd/MM/yyyy",certificadoTasasBean.getTasa().getFechaAsignacion().getDate()));
				} catch (ParseException e) {
					log.error("Ha sucedido un error a la hora de formatear la fecha de asignación de la tasa: " + certificadoTasasBean.getTasa().getCodigoTasa() + ", error: " ,e);
					ireportTablaCertificadoTasasBean.setFechaAplicacion("");
				}
			}else{
				ireportTablaCertificadoTasasBean.setFechaAplicacion("");
			}

			try {
				ireportTablaCertificadoTasasBean.setFechaCompra(utilesFecha.formatoFecha("dd/MM/yyyy",certificadoTasasBean.getTasa().getFechaAlta().getDate()));
			} catch (ParseException e) {
				log.error("Ha sucedido un error a la hora de formatear la fecha de compra de la tasa: " + certificadoTasasBean.getTasa().getCodigoTasa() + ", error: " ,e);
				ireportTablaCertificadoTasasBean.setFechaCompra("");
			}
			array[cont++] = ireportTablaCertificadoTasasBean;
		}

		return array;
	}

	private void generarCertificaTasasPegatinas(String listaChecksConsultaTasaPegatinas, ResultadoCertificadoTasasBean resultado, List<CertificadoTasasBean> listaCertificados) {
		String[] numTasas = listaChecksConsultaTasaPegatinas.replaceAll(" ", "").split(",");
		for(String codPegatina : numTasas){
			TasaDto tasaDto = servicioTasa.getTasaPegatinaCodigoTasa(codPegatina);
			ResultBean resultValidar = validarTasaPegatinaCertificado(tasaDto,codPegatina);
			if(!resultValidar.getError()){
				TramiteTrafFacturacionDto tramiteTrafFacturacionDto = servicioFacturacionTasa.getFacturacionTasa(null, codPegatina);
				String matricula = "";
				PersonaDto titularFacturacion = null;
				if(tramiteTrafFacturacionDto == null){
					if (TipoTasaDGT.UnoTres.getValorEnum().equals(tasaDto.getTipoTasa())) {
						resultado.addError();
						resultado.aniadirMensajeListaOk("La tasa de pegatina con código " + codPegatina + " no está asociada a un tramite de facturación de tasas");
						continue;
					}
				}else{
					if(tramiteTrafFacturacionDto.getFechaAplicacion() != null){
						tasaDto.setFechaAsignacion(tramiteTrafFacturacionDto.getFechaAplicacion());
					}

					if (tramiteTrafFacturacionDto.getMatricula() != null && !tramiteTrafFacturacionDto.getMatricula().isEmpty()) {
						matricula = tramiteTrafFacturacionDto.getMatricula();
					} else {
						matricula = tramiteTrafFacturacionDto.getBastidor();
					}
					if(tramiteTrafFacturacionDto.getPersona() != null) {
						titularFacturacion = tramiteTrafFacturacionDto.getPersona();
					}else{
						titularFacturacion = new PersonaDto();
					}
				}
				listaCertificados.add(new CertificadoTasasBean(new PersonaDto(), titularFacturacion, tasaDto, matricula));
				resultado.addOk();
				resultado.aniadirMensajeListaOk("Tasa Pegatina: " +codPegatina + ", generada correctamente.");
				Fecha fechaTasa = tasaDto.getFechaAsignacion() != null ? tasaDto.getFechaAsignacion() : tasaDto.getFechaAlta();
				setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(fechaTasa),resultado);
			}else{
				resultado.addError();
				resultado.aniadirMensajeListaError(resultValidar.getListaMensajes().get(0));
			}
		}
	}

	private void setFechaInicioFechaFin(Date fechaTasa, ResultadoCertificadoTasasBean resultado) {
		if (fechaTasa != null) {
			if (resultado.getFechaInicio() == null) {
				resultado.setFechaInicio(fechaTasa);
				resultado.setFechaFin(fechaTasa);
			} else {
				if (fechaTasa.getTime() < resultado.getFechaInicio().getTime()) {
					resultado.setFechaInicio(fechaTasa);
				}
				if (fechaTasa.getTime() > resultado.getFechaFin().getTime()) {
					resultado.setFechaFin(fechaTasa);
				}
			}
		}
	}

	private ResultBean validarTasaPegatinaCertificado(TasaDto tasaDto, String codPegatina) {
		ResultBean resultado = new ResultBean();
		if (tasaDto == null) {
			resultado.setError(true);
			resultado.addMensajeALista("No existen datos para la tasa de pegatina: " + codPegatina);
			return resultado;
		}

		if (Decision.No.getValorEnum() == tasaDto.getImportadoIcogam().intValue()) {
			resultado.setError(true);
			resultado.addMensajeALista("La tasa de pegatina con código " + tasaDto.getCodigoTasa() + " no ha sido importada desde el ICOGAM");
			return resultado;
		}

		return resultado;
	}

	private void generarCertificadoTasas(String listaChecksConsultaTasa, ResultadoCertificadoTasasBean resultado, List<CertificadoTasasBean> listaCertificados) {
		String[] numTasas = listaChecksConsultaTasa.replaceAll(" ", "").split(",");
		for(String codTasa : numTasas){
			TasaDto tasaDto = servicioTasa.getTasaCodigoTasa(codTasa);
			ResultBean resultValidar = validarTasaCertificado(tasaDto,codTasa);
			if(!resultValidar.getError()){
				if (TipoTramiteTrafico.Inteve.getValorEnum().equals(tasaDto.getTramiteTrafico().getTipoTramite())) {
					ResultBean resultValidarEstadosTramite = validarEstadoTramiteTasaInteve(tasaDto);
					if (!resultValidarEstadosTramite.getError()) {
						PersonaDto titularFacturacion = new PersonaDto();
						titularFacturacion.setNombre("Inteve sin Titular de Facturación");
						titularFacturacion.setNif("");

						TramiteTraficoSolInteveVO traficoSolInteveVO = (TramiteTraficoSolInteveVO) servicioTramiteTrafico.getTramiteInteve(tasaDto.getTramiteTrafico().getNumExpediente(), tasaDto.getCodigoTasa());
						String matricula = null;
						if (traficoSolInteveVO.getMatricula() == null || traficoSolInteveVO.getMatricula().isEmpty()) {
							matricula = traficoSolInteveVO.getBastidor();
						} else {
							matricula = traficoSolInteveVO.getMatricula();
						}
						TramiteTraficoDto tramiteTraficoDto = servicioTramiteTrafico.getTramiteTraficoDto(tasaDto.getTramiteTrafico().getNumExpediente(), true);
						listaCertificados.add(new CertificadoTasasBean(getTitularVehiculo(tramiteTraficoDto), titularFacturacion, tasaDto, matricula));
						resultado.addOk();
						resultado.aniadirMensajeListaOk("Tasa: " + tasaDto.getCodigoTasa() + ", generada correctamente.");
						setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(tasaDto.getFechaAsignacion()), resultado);
					} else {
						resultado.addError();
						resultado.aniadirMensajeListaError(resultValidarEstadosTramite.getListaMensajes().get(0));
					}
				} else {
					if(tasaDto.getTramiteTrafico().getTasa() != null 
							&& codTasa.equals(tasaDto.getTramiteTrafico().getTasa().getCodigoTasa())){
						ResultBean resultValidarEstadosTramite = validarEstadoTramiteTasa(tasaDto.getTramiteTrafico());
						if(!resultValidarEstadosTramite.getError()){
							PersonaDto titularFacturacion = null;
							if(!TipoTramiteTrafico.Duplicado.getValorEnum().equals(tasaDto.getTramiteTrafico().getTipoTramite())){
								titularFacturacion = getTitularFacturacionTasaExpediente(tasaDto.getTramiteTrafico().getNumExpediente());
								if(titularFacturacion == null){
									resultado.addError();
									resultado.aniadirMensajeListaError("No se ha recuperado el nif del titular de facturación para el expediente " + tasaDto.getTramiteTrafico().getNumExpediente());
									continue;
								}
							}else{
								titularFacturacion = new PersonaDto();
								titularFacturacion.setNombre("Duplicado sin Titular de Facturación");
								titularFacturacion.setNif("");
							}
							TramiteTraficoDto tramiteTraficoDto = servicioTramiteTrafico.getTramiteTraficoDto(tasaDto.getTramiteTrafico().getNumExpediente(), true);
							String matricula = null;
							if(tramiteTraficoDto.getVehiculoDto().getMatricula() == null
									|| tramiteTraficoDto.getVehiculoDto().getMatricula().isEmpty()){
								matricula = tramiteTraficoDto.getVehiculoDto().getBastidor();
							}else{
								matricula = tramiteTraficoDto.getVehiculoDto().getMatricula();
							}
							listaCertificados.add(new CertificadoTasasBean(getTitularVehiculo(tramiteTraficoDto), titularFacturacion, tasaDto, matricula));
							resultado.addOk();
							resultado.aniadirMensajeListaOk("Tasa: " + tasaDto.getCodigoTasa() + ", generada correctamente.");
							setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(tasaDto.getFechaAsignacion()),resultado);
						}else{
							resultado.addError();
							resultado.aniadirMensajeListaError(resultValidarEstadosTramite.getListaMensajes().get(0));
						}
					}else{
						ResultBean resultBean = esTasaCambioServicioOTramiteSolicitud(tasaDto,listaCertificados);
						if(resultBean.getError()){
							resultado.addError();
							resultado.aniadirMensajeListaError(resultBean.getListaMensajes().get(0));
						}else{
							resultado.addOk();
							resultado.aniadirMensajeListaOk(resultBean.getListaMensajes().get(0));
							setFechaInicioFechaFin(utilesFecha.convertirFechaEnDate(tasaDto.getFechaAsignacion()),resultado);
						}
					}
				}
			}else{
				resultado.addError();
				resultado.aniadirMensajeListaError(resultValidar.getListaMensajes().get(0));
			}
		}
	}

	private PersonaDto getTitularVehiculo(TramiteTraficoDto tramiteTraficoDto) {
		String tipoInterviniente = null;
		if(TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTraficoDto.getTipoTramite()) ||
				TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())){
			TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(tramiteTraficoDto.getNumExpediente(), false);
			if(tramiteTrafTranDto != null){
				if(TipoTransferencia.tipo5.getValorEnum().equals(tramiteTrafTranDto.getTipoTransferencia())){
					tipoInterviniente = TipoInterviniente.Compraventa.getValorEnum();
				}else{
					tipoInterviniente = TipoInterviniente.Adquiriente.getValorEnum();
				}
			}
		} else if (TipoTramiteTrafico.Inteve.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())) {
			tipoInterviniente = TipoInterviniente.Solicitante.getValorEnum();
		} else {
			tipoInterviniente = TipoInterviniente.Titular.getValorEnum();
		}
		if (tramiteTraficoDto.getIntervinienteTraficos() != null && !tramiteTraficoDto.getIntervinienteTraficos().isEmpty()) {
			for (IntervinienteTraficoDto intervinienteDto : tramiteTraficoDto.getIntervinienteTraficos()) {
				if (tipoInterviniente.equals(intervinienteDto.getTipoInterviniente())) {
					return intervinienteDto.getPersona();
				}
			}
		}
		return null;
	}

	private PersonaDto getTitularFacturacionTasaExpediente(BigDecimal numExpediente) {
		PersonaDto titularFacturacion = null;
		TramiteTrafFacturacionDto tramiteTrafFacturacionDto = servicioTramiteTrafico.getTramiteFacturacion(numExpediente);
		if (tramiteTrafFacturacionDto != null && tramiteTrafFacturacionDto.getNif() != null && !tramiteTrafFacturacionDto.getNif().isEmpty()) {
			titularFacturacion = servicioPersona.getPersona(tramiteTrafFacturacionDto.getNif(), tramiteTrafFacturacionDto.getNumColegiado());
		}
		return titularFacturacion;
	}

	private ResultBean validarEstadoTramiteTasa(TramiteTraficoDto tramiteTraficoDto) {
		ResultBean resultado = new ResultBean();

		if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())
				|| TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())
				|| TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())){
			if(!EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTraficoDto.getEstado()) 
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTraficoDto.getEstado())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTraficoDto.getEstado())){
				resultado.setError(true);
				resultado.addMensajeALista("El estado de la tasa " + tramiteTraficoDto.getTasa().getCodigoTasa() + " no permite su inclusión en el certificado.");
			}
		} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())
				|| TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())) {
			if (!EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTraficoDto.getEstado())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTraficoDto.getEstado())
					&& !EstadoTramiteTrafico.Finalizado_Excel.getValorEnum().equals(tramiteTraficoDto.getEstado())
						&& !EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum().equals(tramiteTraficoDto.getEstado())) {
				resultado.setError(true);
				resultado.addMensajeALista("El estado de la tasa " + tramiteTraficoDto.getTasa().getCodigoTasa() + " no permite su inclusión en el certificado.");
			}
		}
		return resultado;
	}

	private ResultBean validarEstadoTramiteTasaInteve(TasaDto tasaDto) {
		ResultBean resultado = new ResultBean();

		if (TipoTramiteTrafico.Inteve.getValorEnum().equals(tasaDto.getTramiteTrafico().getTipoTramite())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tasaDto.getTramiteTrafico().getEstado())) {
			resultado.setError(true);
			resultado.addMensajeALista("El estado de la tasa " + tasaDto.getCodigoTasa() + " no permite su inclusión en el certificado.");
		}
		return resultado;
	}

	private ResultBean validarTasaCertificado(TasaDto tasaDto, String codTasa) {
		ResultBean resultado = new ResultBean();
		if(tasaDto == null){
			resultado.setError(true);
			resultado.addMensajeALista("No existen datos para la tasa: " + codTasa);
			return resultado;
		}

		if(tasaDto.getTramiteTrafico() == null){
			resultado.setError(true);
			resultado.addMensajeALista("No se ha encontrado el expediente de la tasa: " + tasaDto.getCodigoTasa());
			return resultado;
		}

		if(Decision.No.getValorEnum() == tasaDto.getImportadoIcogam().intValue()){
			resultado.setError(true);
			resultado.addMensajeALista("La tasa con nº de expediente " 
					+ tasaDto.getTramiteTrafico().getNumExpediente() + " no ha sido importada desde el ICOGAM");
			return resultado;
		}

		return resultado;
	}

	private ResultBean validarTasaCertificadoDesdeTramite(TramiteTraficoDto tramiteTraficoDto) {
		ResultBean resultado = new ResultBean();

		if(tramiteTraficoDto.getTasa() == null){
			resultado.setError(true);
			resultado.addMensajeALista("No se ha encontrado tasa para el expediente: " + tramiteTraficoDto.getNumExpediente());
			return resultado;
		}

		if(Decision.No.getValorEnum() == tramiteTraficoDto.getTasa().getImportadoIcogam().intValue()){
			resultado.setError(true);
			resultado.addMensajeALista("La tasa con nº de expediente " 
					+ tramiteTraficoDto.getTasa().getNumExpediente() + " no ha sido importada desde el ICOGAM");
			return resultado;
		}
		return resultado;
	}

	private ResultBean esTasaCambioServicioOTramiteSolicitud(TasaDto tasaDto, List<CertificadoTasasBean> listaCertificados) {
		ResultBean resultado = new ResultBean();
		if(TipoTramiteTrafico.Transmision.getValorEnum().equals(tasaDto.getTramiteTrafico().getTipoTramite())
			|| TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tasaDto.getTramiteTrafico().getTipoTramite())){
			resultado = esTasaCambioServicio(tasaDto,listaCertificados);
		}else if(TipoTramiteTrafico.Solicitud.getValorEnum().equals(tasaDto.getTramiteTrafico().getTipoTramite())){
			resultado = esTasaTramiteSolicitud(tasaDto,listaCertificados);
		}
		return resultado;
	}

	private ResultBean esTasaTramiteSolicitud(TasaDto tasaDto,List<CertificadoTasasBean> listaCertificados) {
		ResultBean resultado = new ResultBean();
		SolicitudInformeVehiculoDto solInformeVehiculoDto = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfoPorCodTasa(tasaDto.getCodigoTasa());
		if(solInformeVehiculoDto != null 
				&& solInformeVehiculoDto.getTramiteTrafico() != null 
				&& solInformeVehiculoDto.getTramiteTrafico().getNumExpediente() != null){
			resultado = validarTasaTramiteSolicitud(solInformeVehiculoDto, tasaDto.getNumExpediente());
			if(!resultado.getError()){
				PersonaDto titularFacturacion = getTitularFacturacionTasaExpediente(solInformeVehiculoDto.getTramiteTrafico().getNumExpediente());
				if(titularFacturacion != null){
					String matricula = null;
					if(solInformeVehiculoDto.getVehiculo().getMatricula() == null || solInformeVehiculoDto.getVehiculo().getMatricula().isEmpty()){
						matricula = solInformeVehiculoDto.getVehiculo().getBastidor();
					}else{
						matricula = solInformeVehiculoDto.getVehiculo().getMatricula();
					}
					listaCertificados.add(new CertificadoTasasBean(null, titularFacturacion, tasaDto, matricula));
					resultado.addMensajeALista("La tasa : " + tasaDto.getCodigoTasa() + ", de solicitud de información se ha generado correctamente.");
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No se ha recuperado el nif del titular de facturación para la tasa: " + tasaDto.getCodigoTasa());
				}
			}
		}else{
			resultado.setError(true);
			resultado.addMensajeALista("No existen datos del tramite de solicitud de información para la tasa: " + tasaDto.getCodigoTasa());
		}
		return resultado;
	}

	private ResultBean validarTasaTramiteSolicitud(SolicitudInformeVehiculoDto solInformeVehiculoDto, BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean();
		if(solInformeVehiculoDto.getTasa() == null){
			resultado.setError(true);
			resultado.addMensajeALista("No existe tasa asociada al nº de expediente: " + numExpediente + ", de solicitud de información.");
			return resultado;
		}
		if(Decision.No.getValorEnum() == solInformeVehiculoDto.getTasa().getImportadoIcogam().intValue()){
			resultado.setError(true);
			resultado.addMensajeALista("La tasa de la solicitud de información del trámite con nº de expediente "
					+ numExpediente + " no ha sido importada desde el ICOGAM");
			return resultado;
		}

		if (solInformeVehiculoDto.getVehiculo() == null && solInformeVehiculoDto.getVehiculo().getIdVehiculo() == null) {
			resultado.setError(true);
			resultado.addMensajeALista("El expediente " + numExpediente + " no tiene asociado vehiculo.");
			return resultado;
		}
		return resultado;
	}

	private ResultBean esTasaCambioServicio(TasaDto tasaDto, List<CertificadoTasasBean> listaCertificados) {
		ResultBean resultado = new ResultBean();
		TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmisionPorTasaCamser(tasaDto.getCodigoTasa(),true);
		if(tramiteTrafTranDto != null){
			resultado = verificarDatosTasaCambServicio(tramiteTrafTranDto, tasaDto);
			if(!resultado.getError()){
				PersonaDto titularFacturacion = getTitularFacturacionTasaExpediente(tramiteTrafTranDto.getNumExpediente());
				if(titularFacturacion != null){
					String matricula = null;
					if(tramiteTrafTranDto.getVehiculoDto().getMatricula() == null || tramiteTrafTranDto.getVehiculoDto().getMatricula().isEmpty()){
						matricula = tramiteTrafTranDto.getVehiculoDto().getBastidor();
					}else{
						matricula = tramiteTrafTranDto.getVehiculoDto().getMatricula();
					}
					listaCertificados.add(new CertificadoTasasBean(tramiteTrafTranDto.getAdquiriente().getPersona(), titularFacturacion, tasaDto, matricula));
					resultado.addMensajeALista("La tasa : " + tasaDto.getCodigoTasa() + ", de cambio de servicio se ha generado correctamente.");
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No se ha recuperado el nif del titular de facturación para la tasa: " + tasaDto.getCodigoTasa());
				}
			}
		}else{
			resultado.setError(true);
			resultado.addMensajeALista("No existen datos del tramite para la tasa: " + tasaDto.getCodigoTasa());
		}
		return resultado;
	}

	private ResultBean verificarDatosTasaCambServicio(TramiteTrafTranDto tramiteTrafTranDto, TasaDto tasaDto) {
		ResultBean resultado = new ResultBean();

		if(!EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafTranDto.getEstado()) &&
			!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafTranDto.getEstado()) &&
			!EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafTranDto.getEstado())){
			resultado.setError(true);
			resultado.addMensajeALista("El estado de la tasa" + tramiteTrafTranDto.getCodigoTasaCamser() + " no permite su inclusión en el certificado.");
			return resultado;
		}

		if (tasaDto == null) {
			resultado.setError(true);
			resultado.addMensajeALista("Para el tramite de cambio servicio con nº de expediente "
					+ tramiteTrafTranDto.getNumExpediente() + " no existen datos de su tasa en la base de datos.");
			return resultado;
		}

		if(Decision.No.getValorEnum() == tasaDto.getImportadoIcogam().intValue()){
			resultado.setError(true);
			resultado.addMensajeALista("La tasa del cambio servicio del trámite con nº de expediente "
					+ tramiteTrafTranDto.getNumExpediente() + " no ha sido importada desde el ICOGAM");
			return resultado;
		}

		if(tramiteTrafTranDto.getTasa() == null){
			resultado.setError(true);
			resultado.addMensajeALista("El trámite con nº de expediente "
					+ tramiteTrafTranDto.getNumExpediente() + " no tiene tasa asociada.");
			return resultado;
		}

		if(Decision.No.getValorEnum() == tramiteTrafTranDto.getTasa().getImportadoIcogam().intValue()){
			resultado.setError(true);
			resultado.addMensajeALista("La tasa del trámite con nº de expediente "
					+ tramiteTrafTranDto.getNumExpediente() + " no ha sido importada desde el ICOGAM");
			return resultado;
		}

		if(tramiteTrafTranDto.getAdquiriente() == null ||
				tramiteTrafTranDto.getAdquiriente().getPersona() == null ||
				tramiteTrafTranDto.getAdquiriente().getPersona().getNif() == null ||
				tramiteTrafTranDto.getAdquiriente().getPersona().getNif().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista("El expediente " + tramiteTrafTranDto.getNumExpediente() + " no tiene asociado ningún adquiriente.");
			return resultado;
		}

		if(tramiteTrafTranDto.getVehiculoDto() == null || tramiteTrafTranDto.getVehiculoDto().getIdVehiculo() == null){
			resultado.setError(true);
			resultado.addMensajeALista("El expediente " + tramiteTrafTranDto.getNumExpediente() + " no tiene asociado vehiculo.");
			return resultado;
		}
		return resultado;
	}

}