package org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.EvoDuplicadoPermisoConducirDao;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.EvolucionDuplicadoPermCondVO;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioEvoDuplicadoPermisoConducir;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.EvolucionDuplPermCondBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvoDuplicadoPermisoConducirImpl implements ServicioEvoDuplicadoPermisoConducir {

	private static final long serialVersionUID = -5589679405363340178L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvoDuplicadoPermisoConducirImpl.class);

	@Autowired
	EvoDuplicadoPermisoConducirDao evoDuplicadoPermisoConducirDao;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public void guardar(Long idDuplicadoPermCond, String estadoAnterior, String estadoNuevo, Long idUsuario, String tipoActuacion) {
		EvolucionDuplicadoPermCondVO evolucion = new EvolucionDuplicadoPermCondVO();
		try {
			evolucion.setIdDuplicadoPermCond(idDuplicadoPermCond);
			evolucion.setIdUsuario(idUsuario);
			if (StringUtils.isNotBlank(estadoAnterior)) {
				evolucion.setEstadoAnterior(estadoAnterior);
			} else {
				evolucion.setEstadoAnterior("0");
			}
			evolucion.setEstadoNuevo(estadoNuevo);
			evolucion.setFechaCambio(new Date());
			evolucion.setTipoActuacion(tipoActuacion);
			evoDuplicadoPermisoConducirDao.guardar(evolucion);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del duplicado permiso conducir: " + evolucion.getIdDuplicadoPermCond(), e, evolucion.getIdDuplicadoPermCond().toString());
		}
	}

	@Override
	public List<EvolucionDuplPermCondBean> convertirListaEnBeanPantalla(List<EvolucionDuplicadoPermCondVO> lista) {
		List<EvolucionDuplPermCondBean> listaBean = new ArrayList<EvolucionDuplPermCondBean>();
		for (EvolucionDuplicadoPermCondVO evolucionDuplicadoPermCondVO : lista) {
			EvolucionDuplPermCondBean evInterBean = new EvolucionDuplPermCondBean();
			evInterBean.setEstadoAnterior(EstadoTramitesInterga.convertirTexto(evolucionDuplicadoPermCondVO.getEstadoAnterior()));
			evInterBean.setEstadoNuevo(EstadoTramitesInterga.convertirTexto(evolucionDuplicadoPermCondVO.getEstadoNuevo()));
			evInterBean.setFecha(utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucionDuplicadoPermCondVO.getFechaCambio()));
			evInterBean.setOperacion(TipoActualizacion.convertirValor(evolucionDuplicadoPermCondVO.getTipoActuacion()).getNombreEnum());
			evInterBean.setUsuario(evolucionDuplicadoPermCondVO.getUsuario().getApellidosNombre());
			listaBean.add(evInterBean);
		}
		return listaBean;
	}
}
