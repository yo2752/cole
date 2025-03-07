package org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoTutela;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioConsultaInterviniente;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.view.bean.ResultadoConsultaIntervinienteTrafBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaIntervinienteImpl implements ServicioConsultaInterviniente {

	private static final long serialVersionUID = -2866684443982756548L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaIntervinienteImpl.class);

	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	Conversor conversor;

	@Override
	public ResultadoConsultaIntervinienteTrafBean eliminarIntervinientes(String codSeleccionados) {
		ResultadoConsultaIntervinienteTrafBean resultado = new ResultadoConsultaIntervinienteTrafBean();
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] datosInterviniente = codSeleccionados.split("_");
				if (datosInterviniente.length == 4) {
					ResultBean resultEliminar = servicioIntervinienteTrafico.eliminarInterviniete(datosInterviniente[0],
							datosInterviniente[1], datosInterviniente[2], datosInterviniente[3]);
					if (resultEliminar.getError()) {
						resultado.addError();
						resultado.addListaError(resultEliminar.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk(resultEliminar.getMensaje());
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Faltan datos obligatorios para poder eliminar el interviniente.");
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Debe seleccionar algún interviniente para poder eliminarlo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los intervinientes seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar los intervinientes seleccionados.");
		}
		return resultado;
	}

	@Override
	public List<IntervinienteTraficoVO> convertirListaEnBeanPantalla(List<IntervinienteTraficoVO> list) {
		if (list != null && !list.isEmpty()) {
			List<IntervinienteTraficoVO> listaInterv = new ArrayList<>();
			for (IntervinienteTraficoVO intervinienteTraficoVO : list) {
				intervinienteTraficoVO.setIdMotivoTutela(TipoTutela.convertirValor(intervinienteTraficoVO.getIdMotivoTutela()));
				listaInterv.add(intervinienteTraficoVO);
			}
			return listaInterv;
		}
		return Collections.emptyList();
	}

}