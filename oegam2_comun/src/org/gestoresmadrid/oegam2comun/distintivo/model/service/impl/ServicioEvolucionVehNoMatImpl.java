package org.gestoresmadrid.oegam2comun.distintivo.model.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.dao.EvolucionVehNoMatOegamDao;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo.EvolucionVehNoMatVO;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioEvolucionVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.EvolucionVehNoMatrOegamBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionVehNoMatImpl implements ServicioEvolucionVehNoMat {

	@Autowired
	EvolucionVehNoMatOegamDao evolucionVehNoMatOegamDao;

	private static final long serialVersionUID = 4410999315891164609L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionVehNoMatImpl.class);

	@Override
	public List<EvolucionVehNoMatrOegamBean> convertirListaVOEnBeanPantalla(List<EvolucionVehNoMatVO> listaBBDD) {
		try {
			List<EvolucionVehNoMatrOegamBean> lista = new ArrayList<>();
			for (EvolucionVehNoMatVO evVehNoMatVO : listaBBDD) {
				EvolucionVehNoMatrOegamBean evOegamBean = new EvolucionVehNoMatrOegamBean();
				evOegamBean.setMatricula(evVehNoMatVO.getMatricula());
				evOegamBean.setFechaCambio(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(evVehNoMatVO.getFechaCambio()));
				evOegamBean.setTipoActuacion(OperacionPrmDstvFicha.convertirValor(evVehNoMatVO.getTipoActuacion()));
				if (evVehNoMatVO.getUsuario() != null && evVehNoMatVO.getUsuario().getApellidosNombre() != null
						&& !evVehNoMatVO.getUsuario().getApellidosNombre().isEmpty()) {
					evOegamBean.setUsuario(evVehNoMatVO.getUsuario().getApellidosNombre());
				}
				lista.add(evOegamBean);
			}
			return lista;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir la lista de la evolucion de los duplicados de distintivos, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean guardarEvolucionVehiculo(Long idVehNoMatOegam, String matricula,
			BigDecimal idUsuario, String tipoOperacion, String estadoAnt, String estadoNuevo, Date fecha,
			String docId) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			EvolucionVehNoMatVO evolucionVehNoMatVO = rellenarEvolucion(idVehNoMatOegam, matricula, idUsuario,
					tipoOperacion, estadoAnt, estadoNuevo, fecha, docId);
			evolucionVehNoMatOegamDao.guardar(evolucionVehNoMatVO);
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar la evolución del vehículo.");
			log.error("Ha sucedido un error a la hora de guardar la evolucion del vehiculo no matriculado en OEgam con matricula: " + matricula + ",error : ", e);
		}
		return resultado;
	}

	private EvolucionVehNoMatVO rellenarEvolucion(Long idVehNoMatOegam, String matricula, BigDecimal idUsuario,
			String tipoOperacion, String estadoAnt, String estadoNuevo, Date fecha, String docId) {
		EvolucionVehNoMatVO evolucionVehNoMatVO = new EvolucionVehNoMatVO();
		evolucionVehNoMatVO.setFechaCambio(fecha);
		evolucionVehNoMatVO.setIdUsuario(idUsuario.longValue());
		evolucionVehNoMatVO.setMatricula(matricula);
		evolucionVehNoMatVO.setIdVehNoMatOegam(idVehNoMatOegam);
		evolucionVehNoMatVO.setTipoActuacion(tipoOperacion);
		evolucionVehNoMatVO.setEstadoAnt(estadoAnt);
		evolucionVehNoMatVO.setEstadoNuevo(estadoNuevo);
		evolucionVehNoMatVO.setDocId(docId);
		return evolucionVehNoMatVO;
	}

}