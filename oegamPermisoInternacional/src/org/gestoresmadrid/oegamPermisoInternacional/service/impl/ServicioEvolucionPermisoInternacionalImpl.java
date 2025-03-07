package org.gestoresmadrid.oegamPermisoInternacional.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.EvolucionPermisoInternacionalDao;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.EvolucionPermisoInterVO;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioEvolucionPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.EvolucionPermisoInterBean;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionPermisoInternacionalImpl implements ServicioEvolucionPermisoInternacional {

	private static final long serialVersionUID = -5300353687462811145L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionPermisoInternacionalImpl.class);

	@Autowired
	EvolucionPermisoInternacionalDao evolucionPermisoInternacionalDao;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public void guardar(Long idPermiso, String estadoAnterior, String estadoNuevo, Long idUsuario, String tipoActuacion) {
		EvolucionPermisoInterVO evolucion = new EvolucionPermisoInterVO();
		try {
			evolucion.setIdPermiso(idPermiso);
			evolucion.setIdUsuario(idUsuario);

			evolucion.setEstadoAnterior(StringUtils.isNotBlank(estadoAnterior) ? estadoAnterior : "0");

			evolucion.setEstadoNuevo(estadoNuevo);
			evolucion.setFechaCambio(new Date());
			evolucion.setTipoActuacion(tipoActuacion);
			evolucionPermisoInternacionalDao.guardar(evolucion);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del permiso internacional: " + evolucion.getIdPermiso(), e, evolucion.getIdPermiso().toString());
		}
	}

	@Override
	public List<EvolucionPermisoInterBean> convertirListaEnBeanPantalla(List<EvolucionPermisoInterVO> lista) {
		List<EvolucionPermisoInterBean> listaBean = new ArrayList<>();
		for (EvolucionPermisoInterVO evolucionPermisoInterVO : lista) {
			EvolucionPermisoInterBean evInterBean = new EvolucionPermisoInterBean();
			evInterBean.setEstadoAnterior(EstadoTramitesInterga.convertirTexto(evolucionPermisoInterVO.getEstadoAnterior()));
			evInterBean.setEstadoNuevo(EstadoTramitesInterga.convertirTexto(evolucionPermisoInterVO.getEstadoNuevo()));
			evInterBean.setFecha(utilesFecha.formatoFecha("dd/MM/yyyy HH:mm:ss", evolucionPermisoInterVO.getFechaCambio()));
			evInterBean.setOperacion(TipoActualizacion.convertirValor(evolucionPermisoInterVO.getTipoActuacion()).getNombreEnum());
			evInterBean.setUsuario(evolucionPermisoInterVO.getUsuario().getApellidosNombre());
			listaBean.add(evInterBean);
		}
		return listaBean;
	}
}