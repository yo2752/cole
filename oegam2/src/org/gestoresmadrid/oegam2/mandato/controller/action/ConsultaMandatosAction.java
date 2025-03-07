package org.gestoresmadrid.oegam2.mandato.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.mandato.model.service.ServicioMandato;
import org.gestoresmadrid.oegam2comun.mandato.view.bean.MandatoFilterBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaMandatosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -8915963665020640809L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaMandatosAction.class);

	private MandatoFilterBean mandato;

	private String codSeleccionados;

	@Resource
	ModelPagination modeloMandatoPaginated;

	@Autowired
	ServicioMandato servicioMandato;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String eliminar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] idMandatos = codSeleccionados.split("-");
			ResultBean resultado = servicioMandato.eliminarVariosMandatos(idMandatos, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
					addActionMessage("Mandatos parcialmente borrados. Errores: ");
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				} else {
					addActionMessage("Mandatos borrados correctamente");
				}
			}
		} else {
			addActionError("Debe seleccionar algun tramite para poder realizar su operacion.");
		}
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloMandatoPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		mandato.setVisible(Boolean.TRUE);
	}

	@Override
	protected void cargarFiltroInicial() {
		if (mandato == null) {
			mandato = new MandatoFilterBean();
		}
		setSort("fechaMandato");
		setDir("asc");
	}

	@Override
	protected Object getBeanCriterios() {
		return mandato;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		mandato = (MandatoFilterBean) object;
	}

	public MandatoFilterBean getMandato() {
		return mandato;
	}

	public void setMandato(MandatoFilterBean mandato) {
		this.mandato = mandato;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}
}
