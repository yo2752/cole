package org.gestoresmadrid.oegam2.controller.action;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoCambioServicio;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCambioServicioBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafCambioServicioDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaTramiteTraficoCambioServicioAction extends ActionBase {

	private static final long serialVersionUID = 7392473041688739478L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaTramiteTraficoCambioServicioAction.class);

	private String matriculaBusqueda;
	private String nifBusqueda;
	private String tipoIntervinienteBuscar;

	private TramiteTrafCambioServicioDto tramiteTraficoCambServ;

	@Autowired
	private ServicioTramiteTraficoCambioServicio servicioTramiteTraficoCambioServicio;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() throws Throwable {
		if(tramiteTraficoCambServ != null && tramiteTraficoCambServ.getNumExpediente() != null) {
			cargarExpediente();
		} else {
			if (tramiteTraficoCambServ == null) {
				tramiteTraficoCambServ = new TramiteTrafCambioServicioDto();
			}
			if (tramiteTraficoCambServ.getJefaturaTraficoDto() == null) {
				tramiteTraficoCambServ.setJefaturaTraficoDto(new JefaturaTraficoDto());
			}
			tramiteTraficoCambServ.setTipoTramite(TipoTramiteTrafico.CambioServicio.getValorEnum());
			tramiteTraficoCambServ.getJefaturaTraficoDto().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
			tramiteTraficoCambServ.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			tramiteTraficoCambServ.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}
		return SUCCESS;
	}

	public String guardar() {
		try {
			ResultadoCambioServicioBean resultado = servicioTramiteTraficoCambioServicio.guardarTramite(tramiteTraficoCambServ,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), false, utilesColegiado.tienePermisoAdmin());
			if(resultado.isError()) {
				if(resultado.getListaMensajes()!=null){
					addActionError(resultado.getMensajeError());
				}
			}else {
				addActionMessage(
						"El trámite de cambio de servicio " + tramiteTraficoCambServ.getNumExpediente() + " se ha guardado correctamente.");
			}
			cargarExpediente();
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el trámite, error: ", e);
			addActionError("Ha sucedido un error a la hora de guardar el trámite.");
		}
		return SUCCESS;
	}

	public String validar() {
		try {
			ResultadoCambioServicioBean resultado = servicioTramiteTraficoCambioServicio.validarTramite(tramiteTraficoCambServ, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.isError()) {
				addActionError(resultado.getMensajeError());
			}else {
				cargarExpediente();
				addActionMessage("El trámite ha sido validado correctamente");
			}
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el trámite, error: ", e);
			addActionError("Ha sucedido un error a la hora de validar el trámite.");
		}
		return SUCCESS;
	}

	public String pendientesEnvioExcel() {
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
		}

		ResultBean respuesta = servicioTramiteTraficoCambioServicio.pendientesEnvioExcel(tramiteTraficoCambServ, utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (respuesta != null && respuesta.getError()) {
			for (String error : respuesta.getListaMensajes()) {
				addActionError(error);
			}
		} else {
			addActionMessage("Trámite Pendiente Envío Excel.");
			cargarExpediente();
		}
		return SUCCESS;
	}

	public void cargarExpediente() {
		if (tramiteTraficoCambServ != null && tramiteTraficoCambServ.getNumExpediente()!= null) {
			tramiteTraficoCambServ = servicioTramiteTraficoCambioServicio.getTramiteCambServ(tramiteTraficoCambServ.getNumExpediente(), true);
			if (tramiteTraficoCambServ == null) {
				addActionError("No se ha podido recuperar el trámite.");
			}
		}
	}

	public String consultarPersona() {
		if(tramiteTraficoCambServ != null) {
			ResultadoCambioServicioBean resultado =  servicioTramiteTraficoCambioServicio.consultarPersona(tramiteTraficoCambServ,tipoIntervinienteBuscar);
			if(!resultado.isError()) {
				return SUCCESS;
			}
		}else {
			addActionError("No se ha podido recuperar el trámite");
		}
		return SUCCESS;
	}

	public String buscarVehiculo() {
		if(tramiteTraficoCambServ != null) {
			ResultadoCambioServicioBean resultado = servicioTramiteTraficoCambioServicio.consultaVehiculo(tramiteTraficoCambServ,tramiteTraficoCambServ.getVehiculoDto().getMatricula());
			if(!resultado.isError()) {
				return SUCCESS;
			}
		}else {
			addActionError("Se debe de rellenar la matrícula");
		}
		return SUCCESS;
	}

	public TramiteTrafCambioServicioDto getTramiteTraficoCambServ() {
		return tramiteTraficoCambServ;
	}

	public void setTramiteTraficoCambServ(TramiteTrafCambioServicioDto tramiteTraficoCambServ) {
		this.tramiteTraficoCambServ = tramiteTraficoCambServ;
	}

	public String getMatriculaBusqueda() {
		return matriculaBusqueda;
	}

	public void setMatriculaBusqueda(String matriculaBusqueda) {
		this.matriculaBusqueda = matriculaBusqueda;
	}

	public ServicioTramiteTraficoCambioServicio getServicioTramiteTraficoCambioServicio() {
		return servicioTramiteTraficoCambioServicio;
	}

	public void setServicioTramiteTraficoCambioServicio(
			ServicioTramiteTraficoCambioServicio servicioTramiteTraficoCambioServicio) {
		this.servicioTramiteTraficoCambioServicio = servicioTramiteTraficoCambioServicio;
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
	}

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

}