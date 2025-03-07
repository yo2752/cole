package org.gestoresmadrid.oegam2.clonarTramites.controller.action;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.clonarTramites.model.service.ServicioClonarTramites;
import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ClonarTramitesDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ClonarTramitesAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 4310447633567792196L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ClonarTramitesAction.class);

	private static final String COLUMDEFECT = "numExpediente";
	private static final String ERROR_AL_OBTENER_EXPEDIENTE_PARA_CLONAR = "Error al obtener el expediente para clonar.";

	private ClonarTramitesDto clonarTramitesDto;

	private String numsExpediente;

	private String tipoTramiteSeleccionado;

	private String estadoTramiteSeleccionado;

	private String bastidorSeleccionado;

	@Autowired
	private ServicioClonarTramites servicioClonarTramites;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		cargarFiltroInicial();
		if (clonarTramitesDto != null) {
			actualizarPaginatedList();
			ResultBean result = servicioClonarTramites.seleccionarPestaniasClonar(clonarTramitesDto);
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else if (!result.getError()) {
				clonarTramitesDto = (ClonarTramitesDto) result.getAttachment("clonarTramitesDTO");
			} else {
				addActionError(ERROR_AL_OBTENER_EXPEDIENTE_PARA_CLONAR);
			}
		} else {
			addActionError(ERROR_AL_OBTENER_EXPEDIENTE_PARA_CLONAR);
		}
		return SUCCESS;
	}

	public String clonar() {
		if (clonarTramitesDto == null) {
			addActionError(ERROR_AL_OBTENER_EXPEDIENTE_PARA_CLONAR);
		}
		ResultBean result = null;
		actualizarPaginatedList();
		result = servicioClonarTramites.comprobacionDatosClonacion(clonarTramitesDto);
		if (result != null) {
			addActionError(result.getListaMensajes().get(0));
			return SUCCESS;
		}

		result = servicioClonarTramites.clonarTramites(clonarTramitesDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (!result.getError()) {
			for (String texto : result.getListaMensajes()) {
				addActionMessage(texto);
			}
		} else if (result.getError()) {
			addActionError(result.getListaMensajes().get(0));
		}

		return SUCCESS;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return null;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {}

	@Override
	protected String actualizarPaginatedList() {
		cargarFiltroInicial();
		List<ClonarTramitesDto> listaClonar = new ArrayList<>();
		listaClonar.add(clonarTramitesDto);
		setLista(new PaginatedListImpl(1, getPage(), getDir(), getSort(), 1, listaClonar));
		return getResultadoPorDefecto();
	}

	@Override
	protected void cargarFiltroInicial() {
		if (clonarTramitesDto == null) {
			clonarTramitesDto = new ClonarTramitesDto();
			if (getNumsExpediente() != null) {
				clonarTramitesDto.setNumExpediente(numsExpediente);
			}
			if (estadoTramiteSeleccionado != null) {
				clonarTramitesDto.setEstado(estadoTramiteSeleccionado);
			}
			if (tipoTramiteSeleccionado != null) {
				clonarTramitesDto.setTipoTramite(tipoTramiteSeleccionado);
			}
			if (bastidorSeleccionado != null) {
				clonarTramitesDto.setBastidor(bastidorSeleccionado);
			}
		}
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	protected Object getBeanCriterios() {
		return clonarTramitesDto;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.clonarTramitesDto = (ClonarTramitesDto) object;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorClonarTramites";
	}

	public ClonarTramitesDto getClonarTramitesDto() {
		return clonarTramitesDto;
	}

	public void setClonarTramitesDto(ClonarTramitesDto clonarTramitesDto) {
		this.clonarTramitesDto = clonarTramitesDto;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public String getTipoTramiteSeleccionado() {
		return tipoTramiteSeleccionado;
	}

	public void setTipoTramiteSeleccionado(String tipoTramiteSeleccionado) {
		this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
	}

	public String getEstadoTramiteSeleccionado() {
		return estadoTramiteSeleccionado;
	}

	public void setEstadoTramiteSeleccionado(String estadoTramiteSeleccionado) {
		this.estadoTramiteSeleccionado = estadoTramiteSeleccionado;
	}

	public ServicioClonarTramites getServicioClonarTramites() {
		return servicioClonarTramites;
	}

	public void setServicioClonarTramites(ServicioClonarTramites servicioClonarTramites) {
		this.servicioClonarTramites = servicioClonarTramites;
	}

	public String getBastidorSeleccionado() {
		return bastidorSeleccionado;
	}

	public void setBastidorSeleccionado(String bastidorSeleccionado) {
		this.bastidorSeleccionado = bastidorSeleccionado;
	}
}