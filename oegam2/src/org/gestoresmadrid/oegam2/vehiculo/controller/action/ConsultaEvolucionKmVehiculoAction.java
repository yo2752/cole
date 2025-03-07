package org.gestoresmadrid.oegam2.vehiculo.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.vehiculo.view.beans.ConsultaVehiculoGuardarTramiteBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaEvolucionKmVehiculoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 2982606968308392976L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaEvolucionKmVehiculoAction.class);

	@Resource
	private ModelPagination modeloVehiculoTramiteTraficoPaginated;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private Long idVehiculo;
	private String numColegiado;

	private ConsultaVehiculoGuardarTramiteBean consultaVehiculoGuardarTramiteBean;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		consultaVehiculoGuardarTramiteBean.setNumColegiado(numColegiado != null && !numColegiado.isEmpty() ? numColegiado : utilesColegiado.getNumColegiadoSession());
		if (idVehiculo != null) {
			consultaVehiculoGuardarTramiteBean.setIdVehiculo(idVehiculo);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloVehiculoTramiteTraficoPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaVehiculoGuardarTramiteBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaVehiculoGuardarTramiteBean = (ConsultaVehiculoGuardarTramiteBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		consultaVehiculoGuardarTramiteBean = new ConsultaVehiculoGuardarTramiteBean();

		consultaVehiculoGuardarTramiteBean.setNumColegiado(numColegiado != null && !numColegiado.isEmpty() ? numColegiado : utilesColegiado.getNumColegiadoSession());
		consultaVehiculoGuardarTramiteBean.setIdVehiculo(idVehiculo);
		setSort("fechaHora");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}