package org.gestoresmadrid.oegam2.trafico.inteve.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamInteve.service.ServicioTramiteInteve;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoInteveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaTramiteTraficoInteveAction extends ActionBase {

	private static final long serialVersionUID = -3259675087787240139L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaTramiteTraficoInteveAction.class);

	private static final String ALTA_TRAMITE = "altaTramiteInteve";
	private static final String DESCARGAR_SOL_INTEVE = "descargarSolInteve";

	private TramiteTraficoInteveDto tramiteInteve;

	private BigDecimal numExpediente;

	private String codSeleccionados;
	private String codigo;

	private InputStream inputStream;
	private String fileName;

	@Autowired
	ServicioTramiteInteve servicioTramiteInteve;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String detalle() {
		if (numExpediente != null) {
			tramiteInteve = new TramiteTraficoInteveDto();
			tramiteInteve.setNumExpediente(numExpediente);
			ResultadoTramiteInteveBean resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
			if (!resultado.getError()) {
				tramiteInteve = resultado.getTramiteInteve();
			} else {
				addActionError(resultado.getMensaje());
				tramiteInteve = new TramiteTraficoInteveDto();
				tramiteInteve.setUsuarioDto(new UsuarioDto());
			}
		} else {
			tramiteInteve = new TramiteTraficoInteveDto();
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			tramiteInteve.setContrato(contrato);
		}
		return ALTA_TRAMITE;
	}

	public String eliminarSolicitudes() {
		ResultadoTramiteInteveBean resultado = null;
		try {
			resultado = servicioTramiteInteve.eliminarSolicitudes(codSeleccionados, tramiteInteve.getContrato().getIdContrato().longValue(), utilesColegiado.getIdUsuarioSession());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				if(resultado.getListaMensajes() == null || resultado.getListaMensajes().isEmpty()){
					addActionMessage("Solicitudes eliminadas correctamente.");
				} else {
					addActionMessage("Solcitudes eliminadas correctamente con los siguientes errores: ");
					String mensaje = "";
					for(String mensajeError : resultado.getResumen().getListaErrores()){
						mensaje += mensajeError + "\n";
					}
					addActionError(mensaje);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar las solictudes seleccionadas, error: ",e);
		}
		resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
		if (!resultado.getError()) {
			tramiteInteve = resultado.getTramiteInteve();
		}
		return ALTA_TRAMITE;
	}

	public String descargarSolicitudes() {
		String pagina = ALTA_TRAMITE;
		ResultadoTramiteInteveBean resultado = null;
		try {
			resultado = servicioTramiteInteve.recuperarDocumento(codSeleccionados);
			if (!resultado.getError() && resultado.getNumOkWS() > 0) {
				inputStream = new FileInputStream(resultado.getFichero());
				fileName = resultado.getNombreFichero();
				if(resultado.getEsZip()){
					servicioTramiteInteve.borrarZip(resultado.getFichero());
				}
				pagina = DESCARGAR_SOL_INTEVE;
			} else if(resultado.getResumen().getListaErrores() != null && !resultado.getResumen().getListaErrores().isEmpty()){
				String mensaje = "Han sucedido los siguientes mensajes: ";
				for(String mensajeError : resultado.getResumen().getListaErrores()){
					mensaje += mensajeError + "\n";
				}
				addActionError(mensaje);
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (FileNotFoundException ex) {
			addActionError("No se ha podido recuperar el fichero");
			log.error("No se ha podido recuperar el fichero", ex);
		} catch (Exception ex) {
			log.error("Existe un error al tratar de descargar el fichero.", ex);
			addActionError("Existe un error al tratar de descargar el fichero.");
		}
		resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
		if (!resultado.getError()) {
			tramiteInteve = resultado.getTramiteInteve();
		}
		return pagina;
	}
	
	public String reiniciarSolicitudes(){
		ResultadoTramiteInteveBean resultado = null;
		try {
			resultado = servicioTramiteInteve.reiniciarEstadosSolicitudes(codSeleccionados);
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				if(resultado.getListaMensajes() == null || resultado.getListaMensajes().isEmpty()){
					addActionMessage("Solicitudes reiniciadas correctamente.");
				} else {
					addActionMessage("Solcitudes reiniciadas correctamente con los siguientes errores: ");
					String mensaje = "";
					if (resultado.getResumen() != null && !CollectionUtils.isEmpty(resultado.getResumen().getListaErrores())) {
						for(String mensajeError : resultado.getResumen().getListaErrores()){
							mensaje += mensajeError + "\n";
						}						
					} else if (!CollectionUtils.isEmpty(resultado.getListaMensajes())) {
						for(String mensajeError : resultado.getListaMensajes()){
							mensaje += mensajeError + "\n";
						}						
					}
					addActionError(mensaje);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reiniciar las solicitudes seleccionadas, error: ", e);
			addActionError("Ha sucedido un error a la hora de reiniciar las solicitudes seleccionadas.");
		}
		resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
		if (!resultado.getError()) {
			tramiteInteve = resultado.getTramiteInteve();
		}
		return ALTA_TRAMITE;
	}

	public String guardar() {
		ResultadoTramiteInteveBean resultado = servicioTramiteInteve.guardarTramiteInteve(tramiteInteve, utilesColegiado.getIdUsuarioSession());
		if (!resultado.getError()) {
			List<String> listaMensajesError = null;
			if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
				listaMensajesError = resultado.getListaMensajes();
			}
			tramiteInteve.setNumExpediente(resultado.getNumExpediente());
			resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
			if (!resultado.getError()) {
				tramiteInteve = resultado.getTramiteInteve();
				if (listaMensajesError != null && !listaMensajesError.isEmpty()) {
					addActionMessage("Trámite guardado correctamente con los siguientes errores: ");
					for (String mensaje : listaMensajesError) {
						addActionError(mensaje);
					}
				} else {
					addActionMessage("Trámite guardado correctamente");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError(resultado.getMensaje());
//			if(resultado.getNumExpediente() != null){
//				tramiteInteve.setNumExpediente(resultado.getNumExpediente());
//			}
//			if(tramiteInteve.getNumExpediente() != null){
//				resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
//				if (!resultado.getError()) {
//					tramiteInteve = resultado.getTramiteInteve();
//				} else {
//					addActionError(resultado.getMensaje());
//				}
//			}
		}
		return ALTA_TRAMITE;
	}

	public String validar() {
		ResultadoTramiteInteveBean resultado = servicioTramiteInteve.validarTramite(tramiteInteve.getNumExpediente(), utilesColegiado.getIdUsuarioSession());
		if (!resultado.getError()) {
			addActionMessage("Trámite validado correctamente.");
		} else {
			addActionError(resultado.getMensaje());
		}
		resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
		if (!resultado.getError()) {
			tramiteInteve = resultado.getTramiteInteve();
		} else {
			addActionError(resultado.getMensaje());
		}
		return ALTA_TRAMITE;
	}

	public String solicitar() {
		ResultadoTramiteInteveBean resultado = servicioTramiteInteve.solicitarTramite(tramiteInteve.getNumExpediente(), utilesColegiado.getIdUsuarioSession());
		if (!resultado.getError()) {
			addActionMessage("Trámite solicitado correctamente.");
		} else {
			addActionError(resultado.getMensaje());
		}
		resultado = servicioTramiteInteve.obtenerTramitePantalla(tramiteInteve);
		if (!resultado.getError()) {
			tramiteInteve = resultado.getTramiteInteve();

		} else {
			addActionError(resultado.getMensaje());
		}
		return ALTA_TRAMITE;
	}

	public TramiteTraficoInteveDto getTramiteInteve() {
		return tramiteInteve;
	}

	public void setTramiteInteve(TramiteTraficoInteveDto tramiteInteve) {
		this.tramiteInteve = tramiteInteve;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
