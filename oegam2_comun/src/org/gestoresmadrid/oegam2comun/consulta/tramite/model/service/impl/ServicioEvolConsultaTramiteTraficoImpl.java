package org.gestoresmadrid.oegam2comun.consulta.tramite.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.consulta.tramite.model.service.ServicioEvolConsultaTramiteTrafico;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.EvolucionConsultaTramiteTraficoBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioEvolConsultaTramiteTraficoImpl implements ServicioEvolConsultaTramiteTrafico{

	private static final long serialVersionUID = -6036235112772710414L;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<EvolucionConsultaTramiteTraficoBean> convertirListaEnBeanPantalla(List<EvolucionTramiteTraficoVO> lista) {
		List<EvolucionConsultaTramiteTraficoBean> listaBean = new ArrayList<EvolucionConsultaTramiteTraficoBean>();
		for(EvolucionTramiteTraficoVO evolucionTramiteTraficoVO : lista) {
			EvolucionConsultaTramiteTraficoBean evConsultaTramTraf = new EvolucionConsultaTramiteTraficoBean();
			evConsultaTramTraf.setNumExpediente(evolucionTramiteTraficoVO.getId().getNumExpediente());
			evConsultaTramTraf.setEstadoAnterior(EstadoTramiteTrafico.convertirTexto(evolucionTramiteTraficoVO.getId().getEstadoAnterior().toString()));
			evConsultaTramTraf.setEstadoNuevo(EstadoTramiteTrafico.convertirTexto(evolucionTramiteTraficoVO.getId().getEstadoNuevo().toString()));
			evConsultaTramTraf.setUsuario(evolucionTramiteTraficoVO.getUsuario().getApellidosNombre());
			evConsultaTramTraf.setFecha(utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucionTramiteTraficoVO.getId().getFechaCambio()));
			listaBean.add(evConsultaTramTraf);
		}
		return listaBean;
	}

}
