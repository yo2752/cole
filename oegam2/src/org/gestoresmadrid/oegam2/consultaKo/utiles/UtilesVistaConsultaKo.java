package org.gestoresmadrid.oegam2.consultaKo.utiles;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.enumerados.EstadoKo;
import org.gestoresmadrid.core.consultaKo.model.enumerados.TipoDocumentoKoEnum;
import org.gestoresmadrid.core.consultaKo.model.enumerados.TipoTramiteKo;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaConsultaKo implements Serializable {

	private static final long serialVersionUID = -6650737998523692208L;

	private static UtilesVistaConsultaKo utilesVistaConsultaKo;

	@Autowired
	private ServicioContrato servicioContrato;


	public static UtilesVistaConsultaKo getInstance() {
		if (utilesVistaConsultaKo == null) {
			utilesVistaConsultaKo = new UtilesVistaConsultaKo();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaConsultaKo);
		}
		return utilesVistaConsultaKo;
	}

	public EstadoKo[] getEstados() {
		return EstadoKo.values();
	}
	

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public TipoTramiteKo[] getTipoTramite(){
		return TipoTramiteKo.values();
	}
	
	public TipoDocumentoKoEnum[] getTipoDocumento(){
		return TipoDocumentoKoEnum.values();
	}
	
	public JefaturasJPTEnum[] getJefaturasJPTEnum(){
		return JefaturasJPTEnum.values();
	}
	
}
