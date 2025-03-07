package org.gestoresmadrid.oegam2.vehiculo.controller.action;

import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ConsultaTramiteTraficoVehiculoBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetalleVehiculoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 7033749740362756974L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DetalleVehiculoAction.class);

	@Resource
	private ModelPagination modeloTramiteTraficoPaginated;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private Long idVehiculo;

	private VehiculoDto vehiculoDto;
	private VehiculoDto vehiculoPreverDto;

	private ConsultaTramiteTraficoVehiculoBean consultaTramiteTraficoVehiculoBean;

	private String estado;

	public String detalle() throws Throwable {
		if (idVehiculo != null) {
			vehiculoDto = servicioVehiculo.getVehiculoDto(idVehiculo, null, null, null, null, null);
			if (vehiculoDto != null && vehiculoDto.getIdVehiculoPrever() != null) {
				vehiculoPreverDto = servicioVehiculo.getVehiculoDto(vehiculoDto.getIdVehiculoPrever().longValue(), null, null, null, null, null);
			}
		}
		inicio();
		recuperarEstadoTramite();
		return SUCCESS;
	}

	public String modificacion() throws Throwable {
		if (!utilesColegiado.tienePermisoMantenimientoVehiculo() || !utilesColegiado.tienePermisoMantenimientoVehiculoGeneral()) {
			addActionError("No tiene permiso para realizar esta acción.");
			return SUCCESS;
		}
		UsuarioDto usuario = servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal());
		ResultBean result = servicioVehiculo.guardarVehiculoConPrever(vehiculoDto, null, ServicioVehiculo.TIPO_TRAMITE_MANTENIMIENTO, usuario, null,
				ServicioVehiculo.CONVERSION_VEHICULO_MANTENIMIENTO, vehiculoPreverDto, utilesColegiado.tienePermisoAdmin());
		if (result.getError()) {
			String mensajeError = "Se ha producido un error al guardar el vehículo: ";
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes()) {
					mensajeError += " - " + mensaje;
				}
			} else {
				mensajeError += result.getMensaje();
			}
			addActionError(mensajeError);
		} else {
			idVehiculo = (Long) result.getAttachment(ServicioVehiculo.ID_VEHICULO);
			vehiculoDto = servicioVehiculo.getVehiculoDto(idVehiculo, null, null, null, null, null);
			addActionMessage("El vehículo se ha guardado correctamente." + (null != result.getMensaje() ? result.getMensaje() : ""));
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}
		}
		return SUCCESS;
	}

	public String consultaDatosITV() throws Throwable {
		ResultBean result = servicioVehiculo.consultaDatosItv(vehiculoDto);
		if (!result.getError()) {
			vehiculoDto = (VehiculoDto) result.getAttachment(ServicioVehiculo.VEHICULO);
		}
		return SUCCESS;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (idVehiculo != null) {
			consultaTramiteTraficoVehiculoBean.setIdVehiculo(idVehiculo);
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			if (utilesColegiado.getIdContratoSessionBigDecimal() != null) {
				consultaTramiteTraficoVehiculoBean.setIdContrato(utilesColegiado.getIdContratoSession());
			}
		} else {
			consultaTramiteTraficoVehiculoBean.setIdContrato(null);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloTramiteTraficoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaTramiteTraficoVehiculoBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaTramiteTraficoVehiculoBean = (ConsultaTramiteTraficoVehiculoBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaTramiteTraficoVehiculoBean = new ConsultaTramiteTraficoVehiculoBean();
		consultaTramiteTraficoVehiculoBean.setIdVehiculo(idVehiculo);

		if (!utilesColegiado.tienePermisoAdmin()) {
			if (utilesColegiado.getIdContratoSessionBigDecimal() != null) {
				consultaTramiteTraficoVehiculoBean.setIdContrato(utilesColegiado.getIdContratoSession());
			}
		} else {
			consultaTramiteTraficoVehiculoBean.setIdContrato(null);
		}

		setSort("fechaUltModif");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	// Mantis 19278. David Sierra: Se recupera el estado del trámite asociado al vehiculo para utilizarlo por pantalla 
	private void recuperarEstadoTramite() {
		@SuppressWarnings("unchecked")
		List <TramiteTrafDto> lista = (List<TramiteTrafDto>) getLista().getList();
		if (!lista.isEmpty()) {
			setEstado(lista.get(0).getEstado());
		}
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public VehiculoDto getVehiculoDto() {
		return vehiculoDto;
	}

	public void setVehiculoDto(VehiculoDto vehiculoDto) {
		this.vehiculoDto = vehiculoDto;
	}

	public VehiculoDto getVehiculoPreverDto() {
		return vehiculoPreverDto;
	}

	public void setVehiculoPreverDto(VehiculoDto vehiculoPreverDto) {
		this.vehiculoPreverDto = vehiculoPreverDto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}