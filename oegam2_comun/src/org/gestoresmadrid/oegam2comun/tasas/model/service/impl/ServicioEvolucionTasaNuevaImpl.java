package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaPK;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegam2comun.evolucionTasa.view.dto.EvolucionTasaDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioEvolucionTasaNueva;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionTasaNuevaImpl implements ServicioEvolucionTasaNueva {

	private static final long serialVersionUID = 2314081917531413264L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionTasaNuevaImpl.class);


	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private EvolucionTasaDao evolucionTasaDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Transactional
	@Override
	public ResultBean insertarEvolucionTasa(TasaVO tasa, String accion) {
		EvolucionTasaVO evolucionTasa = new EvolucionTasaVO();
		ResultBean resultado = new ResultBean();
		try {
			resultado.setError(false);
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal());
			evolucionTasa.setAccion(accion);
			if (tasa.getNumExpediente() != null) {
				evolucionTasa.setNumExpediente(tasa.getNumExpediente());
			} else {
				evolucionTasa.setNumExpediente(null);
			}
			evolucionTasa.setTasa(tasa);
			EvolucionTasaPK id = new EvolucionTasaPK();
			id.setCodigoTasa(tasa.getCodigoTasa());
			id.setFechaHora(utilesFecha.getFechaActualDesfaseBBDD());
			evolucionTasa.setId(id);
			evolucionTasa.setUsuario(conversor.transform(usuario, UsuarioVO.class));

			evolucionTasaDao.guardar(evolucionTasa);
		} catch (Exception e) {
			log.error("Error ak guardar la evolución de tasas");
		} finally {
			evolucionTasaDao.evict(tasa);
		}
		return resultado;
	}

	@Override
	public void guardarEvolucion(Date fecha,Long idUsuario, String tipoActuacion) {
		EvolucionTasaVO evolucionTasaVO = rellenarEvolucion(fecha, idUsuario,tipoActuacion);
		if (evolucionTasaVO != null) {
			evolucionTasaDao.guardar(evolucionTasaVO);
		} else {
			log.error("No se puede guardar una evolucion de una consulta de la Tasa, si el objeto esta vacio.");
		}

	}

	private EvolucionTasaVO rellenarEvolucion (Date fecha, Long idUsuario, String tipoActuacion) {
		EvolucionTasaVO evolucionDocumentoTasaVO = new EvolucionTasaVO();
		EvolucionTasaPK id = new EvolucionTasaPK();
		id.setFechaHora(fecha);
		evolucionDocumentoTasaVO.setId(id);
		evolucionDocumentoTasaVO.setAccion(tipoActuacion);
		return evolucionDocumentoTasaVO;
	}

	@Override
	public List<EvolucionTasaDto> convertirListaVoToDto(List<EvolucionTasaVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista, EvolucionTasaDto.class);
		}
		return Collections.emptyList();
	
	}
	@Transactional
	@Override
	public ResultBean eliminarEvolucionTasa(String idstasaSeleccion) {
		ResultBean resultBean = new ResultBean();
			try {
				List<EvolucionTasaVO> listaEvolTasa = evolucionTasaDao.getListaEvolucionesPorTasa(idstasaSeleccion);
				if (listaEvolTasa != null && !listaEvolTasa.isEmpty()) {
					for (EvolucionTasaVO evoluciontasaVO : listaEvolTasa) {
						evolucionTasaDao.borrar(evoluciontasaVO);
					}
				}
			} catch (Exception e) {
				Log.error("Ha sucedido un error a la hora de eliminar las evoluciones de la consulta de tasa, error: ", e);
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("Ha sucedido un error a la hora de eliminar las evoluciones de la consulta.");
			}
			return resultBean;
		}
	
}
	
