package org.gestoresmadrid.oegam2.utiles;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.docbase.enumerados.DocBaseEstadoEnum;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.TipoCarpeta;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaDocBase implements Serializable {

	private static final long serialVersionUID = -1556671130109130108L;

	private static UtilesVistaDocBase utilesVistaDocBase;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaDocBase getInstance() {
		if (utilesVistaDocBase == null) {
			utilesVistaDocBase = new UtilesVistaDocBase();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaDocBase);
		}
		return utilesVistaDocBase;
	}

	public TipoCarpeta[] getTiposCarpeta() {
		return TipoCarpeta.values();
	}

	public DocBaseEstadoEnum[] getEstadosGestionDocBase() {
		if (utilesColegiado.tienePermisoAdmin() || utilesColegiado.usuariosTrafico()) {
			return DocBaseEstadoEnum.getEstadosGestionDBAmin();
		} else {
			return DocBaseEstadoEnum.getEstadosGestionDB();
		}
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}
}