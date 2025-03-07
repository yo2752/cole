package org.gestoresmadrid.oegam2.creditos.controller.action;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.gestoresmadrid.oegamCreditos.service.ServicioTipoCreditos;
import org.gestoresmadrid.oegamCreditos.view.bean.ResultCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.bean.TipoCreditosBean;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoDto;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TipoCreditosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -5209257698986054441L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(TipoCreditosAction.class);

	private static final String PANTALLA_NUEVO_MODIFICAR_TIPO_CREDITO = "nuevoTipoCredito";
	private static final String PANTALLA_DETALLE_TIPO_CREDITO = "detalleTipoCredito";

	private TipoCreditosBean tipoCreditosBean;

	private String check;
	private String checkTramitesAsignados;

	private boolean nuevoTipoCredito;

	private TipoCreditoDto tipoCreditoDto;

	private String tipoCredito;

	private List<TipoTramiteDto> listaTiposTramitesCreditos;

	@Resource
	private ModelPagination modeloTipoCreditosPaginated;

	@Autowired
	private ServicioTipoCreditos servicioTipoCreditos;

	public String nuevo() {
		setNuevoTipoCredito(Boolean.TRUE);
		return PANTALLA_NUEVO_MODIFICAR_TIPO_CREDITO;
	}

	public String guardar() {
		ResultCreditosBean resultado = new ResultCreditosBean();

		try {
			tipoCreditoDto.setEstado(BigDecimal.ONE);
			resultado = servicioTipoCreditos.guardarOActualizarTipoCredito(tipoCreditoDto, checkTramitesAsignados);
		} catch (Exception e) {
			resultado.addError("Error al guardar el tipo de crédito.");
			log.error(e.getMessage());
		}
		if (resultado.getError()) {
			if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
				for (String validacion : resultado.getListaMensajes()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
			// Recuperamos la lista de trámites
			if (!isNuevoTipoCredito()) {
				listaTiposTramitesCreditos = servicioTipoCreditos.getTiposTramitesCreditos();
			}
			return PANTALLA_NUEVO_MODIFICAR_TIPO_CREDITO;
		} else {
			addActionMessage(resultado.getMensaje());
		}
		return buscar();
	}

	public String detalle() {

		if (StringUtils.isNotBlank(getTipoCredito())) {

			setTipoCreditoDto(null);
			TipoCreditoDto tipo = servicioTipoCreditos.getTipoCredito(getTipoCredito());
			if (tipo == null) {
				addActionError("No se ha podido recuperar el tipo crédito: " + getTipoCredito() + ".");
			} else {
				setTipoCreditoDto(tipo);
				// Recuperamos la lista de trámites
				listaTiposTramitesCreditos = servicioTipoCreditos.getTiposTramitesCreditos();
			}

		}
		return PANTALLA_DETALLE_TIPO_CREDITO;
	}

	public String modificar() {
		setNuevoTipoCredito(Boolean.FALSE);

		setTipoCreditoDto(null);

		String[] tiposCreditos = check.split(",");
		for (String tipoCred : tiposCreditos) {
			TipoCreditoDto tipo = servicioTipoCreditos.getTipoCredito(tipoCred);
			if (tipo == null) {
				addActionError("No se ha podido recuperar el tipo crédito: " + tipoCred + ".");
			} else {
				setTipoCreditoDto(tipo);
				// Recuperamos la lista de trámites
				listaTiposTramitesCreditos = servicioTipoCreditos.getTiposTramitesCreditos();
			}
		}

		return PANTALLA_NUEVO_MODIFICAR_TIPO_CREDITO;

	}

	// Para eliminar un crédito ponemos su estado a cero para deshabilitarlo.
	public String eliminar() {
		String[] tiposCreditos = check.split(",");
		for (String tipoCred : tiposCreditos) {
			TipoCreditoDto tipo = servicioTipoCreditos.getTipoCredito(tipoCred);
			if (tipo == null) {
				addActionError("No se ha podido recuperar el tipo crédito: " + tipoCred + ".");
			} else {
				tipo.setEstado(BigDecimal.ZERO);
				ResultCreditosBean respuesta = servicioTipoCreditos.guardarOActualizarTipoCredito(tipo, checkTramitesAsignados);

				if (respuesta != null && !respuesta.getError()) {
					addActionMessage("El tipo crédito: " + tipoCred + " se ha eliminado correctamente.");
				} else {
					addActionError("El tipo crédito: " + tipoCred + " no se ha eliminado. " + respuesta.getMensaje() + ".");
				}
			}
		}

		return buscar();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (tipoCreditosBean == null) {
			tipoCreditosBean = new TipoCreditosBean();
		}

		tipoCreditosBean.setEstado(BigDecimal.ONE);
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloTipoCreditosPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return tipoCreditosBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.tipoCreditosBean = (TipoCreditosBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {}

	public TipoCreditosBean getTipoCreditosBean() {
		return tipoCreditosBean;
	}

	public void setTipoCreditosBean(TipoCreditosBean tipoCreditosBean) {
		this.tipoCreditosBean = tipoCreditosBean;
	}

	public ModelPagination getModeloTipoCreditosPaginated() {
		return modeloTipoCreditosPaginated;
	}

	public void setModeloTipoCreditosPaginated(ModelPagination modeloTipoCreditosPaginated) {
		this.modeloTipoCreditosPaginated = modeloTipoCreditosPaginated;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public TipoCreditoDto getTipoCreditoDto() {
		return tipoCreditoDto;
	}

	public void setTipoCreditoDto(TipoCreditoDto tipoCreditoDto) {
		this.tipoCreditoDto = tipoCreditoDto;
	}

	public List<TipoTramiteDto> getListaTiposTramitesCreditos() {
		return listaTiposTramitesCreditos;
	}

	public void setListaTiposTramitesCreditos(List<TipoTramiteDto> listaTiposTramitesCreditos) {
		this.listaTiposTramitesCreditos = listaTiposTramitesCreditos;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public boolean isNuevoTipoCredito() {
		return nuevoTipoCredito;
	}

	public void setNuevoTipoCredito(boolean nuevoTipoCredito) {
		this.nuevoTipoCredito = nuevoTipoCredito;
	}

	public String getCheckTramitesAsignados() {
		return checkTramitesAsignados;
	}

	public void setCheckTramitesAsignados(String checkTramitesAsignados) {
		this.checkTramitesAsignados = checkTramitesAsignados;
	}

}