package org.gestoresmadrid.oegam2.registradores.controller.action.financiadores;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFinanciador;
import org.gestoresmadrid.oegam2comun.registradores.view.beans.ConsultaFinanciadoresBean;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class RecuperarFinanciadoresAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7589119630482491609L;
	private static final String RESULTADO_LIST = "listar";

	private static ILoggerOegam log = LoggerOegam.getLogger(RecuperarFinanciadoresAction.class);

	private List<IntervinienteRegistroDto> financiadores;

	private String identificador;
	private IntervinienteRegistroDto financiador;

	@Autowired
	private ServicioFinanciador servicioFinanciador;

	@Resource
	private ModelPagination modeloFinanciadoresPaginated;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ConsultaFinanciadoresBean consultaFinanciadoresBean;

	@SkipValidation
	public String borrar() {
		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {
			try {
				ResultBean response = servicioFinanciador.borrarFinanciador(getIdentificador());
				if (response.getError()){
					for (String mensaje : response.getListaMensajes())
						addActionError(mensaje);
				} else {
					addActionMessage("Financiador borrado correctamente");
				}
			} catch (Exception e) {
				log.error("Error borrando financiador con id=[" + getIdentificador() + "]", e);
				addActionError("No se pudo borrar el financiador" + " (" + e.getMessage() + ").");
			}
		}
		return buscar();
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public IntervinienteRegistroDto getFinanciador() {
		return financiador;
	}

	public void setFinanciador(IntervinienteRegistroDto financiador) {
		this.financiador = financiador;
	}

	public List<IntervinienteRegistroDto> getFinanciadores() {
		return financiadores;
	}

	public void setFinanciadores(List<IntervinienteRegistroDto> financiadores) {
		this.financiadores = financiadores;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return RESULTADO_LIST;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloFinanciadoresPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaFinanciadoresBean == null) {
			consultaFinanciadoresBean = new ConsultaFinanciadoresBean();
		}
		consultaFinanciadoresBean.setTipoInterviniente("021");
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaFinanciadoresBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaFinanciadoresBean == null) {
			consultaFinanciadoresBean = new ConsultaFinanciadoresBean();
		}
		consultaFinanciadoresBean.setTipoInterviniente("021");
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio() && !utilesColegiado.tienePermisoEspecial()) {
			consultaFinanciadoresBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		setSort("nif");
		setDir(GenericDaoImplHibernate.ordenDes);
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaFinanciadoresBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaFinanciadoresBean = (ConsultaFinanciadoresBean) object;
	}

	public ConsultaFinanciadoresBean getConsultaFinanciadoresBean() {
		return consultaFinanciadoresBean;
	}

	public void setConsultaFinanciadoresBean(ConsultaFinanciadoresBean consultaFinanciadoresBean) {
		this.consultaFinanciadoresBean = consultaFinanciadoresBean;
	}

}