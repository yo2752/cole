package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.AplicacionDao;
import org.gestoresmadrid.core.general.model.dao.UsuarioDao;
import org.gestoresmadrid.core.general.model.dao.UsuarioFuncionSinAccesoDao;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoPK;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.oegam2comun.model.service.ServicioAplicacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioFuncion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegamComun.accesos.view.dto.FuncionDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;

@Service
public class ServicioPermisosImpl implements ServicioPermisos {

	private static final long serialVersionUID = -1224069871183622614L;

	@Autowired
	private ServicioAplicacion servicioAplicacion;

	@Autowired
	private UsuarioFuncionSinAccesoDao usuarioFuncionSinAccesoDao;

	@Autowired
	private AplicacionDao aplicacionDao;

	@Autowired
	private TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	private ServicioFuncion servicioFuncion;

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	Utiles utiles;

	@Transactional
	@Override
	public boolean tienePermisoElContrato(Long idContrato, String codigoFuncion, String codigoAplicacion) {
		// TODO comprobar que se tiene permisos para la aplicacion.
		if (!aplicacionDao.tieneAplicacion(idContrato, codigoAplicacion)) {
			return false;
		}

		UsuarioFuncionSinAccesoVO usuario = new UsuarioFuncionSinAccesoVO();
		UsuarioFuncionSinAccesoPK id = new UsuarioFuncionSinAccesoPK();
		id.setCodigoAplicacion(codigoAplicacion);
		id.setCodigoFuncion(codigoFuncion);
		id.setIdContrato(idContrato);
		id.setIdUsuario((long) 0);
		usuario.setId(id);
		List<UsuarioFuncionSinAccesoVO> lista = usuarioFuncionSinAccesoDao.buscar(usuario);

		return lista == null || lista.isEmpty();
	}

	@Transactional
	@Override
	public boolean tienePermisoElUsuario(Long idUsuario, Long idContrato, String codigoFuncion, String codigoAplicacion) {
		if (!tienePermisoElContrato(idContrato, codigoFuncion, codigoAplicacion)) {
			return false;
		}
		UsuarioFuncionSinAccesoVO usuario = new UsuarioFuncionSinAccesoVO();
		UsuarioFuncionSinAccesoPK id = new UsuarioFuncionSinAccesoPK();
		id.setCodigoAplicacion(codigoAplicacion);
		id.setCodigoFuncion(codigoFuncion);
		id.setIdContrato(idContrato);
		id.setIdUsuario(idUsuario);
		usuario.setId(id);
		List<UsuarioFuncionSinAccesoVO> lista = usuarioFuncionSinAccesoDao.buscar(usuario);

		return lista == null || lista.isEmpty();
	}

	@Override
	@Transactional
	public boolean usuarioTienePermisoSobreTramites(String[] numExpediente, Long idContrato) {
		BigDecimal[] l = utiles.convertirStringArrayToBigDecimalArray(numExpediente);
		if (l == null || l.length == 0) {
			return true;
		}
		int numeroElementos = tramiteTraficoDao.usuarioTienePermisoSobreTramites(l, idContrato);
		if (numeroElementos > 0) {
			return false;
		}
		return true;
	}

	@Transactional(readOnly = true)
	public List<UsuarioFuncionSinAccesoVO> compruebaAcceso(String codigoFuncion) {
		return usuarioFuncionSinAccesoDao.compruebaAcceso(codigoFuncion);
	}

	@Override
	@Transactional
	public ResultBean asignarPermiso(BigDecimal idContrato, String codFuncion, String codAplicacion, String numColegiado, Long idUsuario) {
		ResultBean resultBean = null;
		// No se puede Asignar una función si la función padre no está asignada
		FuncionVO funcion = servicioFuncion.getFuncion(codAplicacion, codFuncion);
		if (idUsuario == null) {
			idUsuario = 0L;
		}
		if (funcion != null) {
			if (funcion.getCodFuncionPadre() != null) {
				List<UsuarioFuncionSinAccesoVO> sinAcceso = usuarioFuncionSinAccesoDao.getUsuarioFuncionSinAcceso(codAplicacion, funcion.getCodFuncionPadre(), idContrato.longValue(), idUsuario);
				if (sinAcceso != null && !sinAcceso.isEmpty()) {
					resultBean = new ResultBean(Boolean.TRUE, "No se puede asignar una función que no este asignada su función padre");
				}
			}
		} else {
			resultBean = new ResultBean(Boolean.TRUE, "No se ha encontrado la opcion de menu");
		}

		// Se puede asignar el pemiso (borrar la entrada de usuario_funcion_sin_acceso
		// En cualquier caso eliminaremos el contenido de permisos desasignados correspondientes a esa función y todos los dependientes */
		if (resultBean == null) {
			borrar(codAplicacion, codFuncion, idContrato, idUsuario);
			// Se deben actualizar los permisos del usuario por lo que se actualizará el campo de fecha_renovación a sysdate
			actualizarFechaRenovacion(numColegiado);
			resultBean = new ResultBean();
		}
		return resultBean;
	}

	private void borrar(String codAplicacion, String codFuncion, BigDecimal idContrato, Long idUsuario) {
		usuarioFuncionSinAccesoDao.borrar(codAplicacion, codFuncion, idContrato.longValue(), idUsuario);
		// Obtenemos hijos para borrarlos tambien
		List<FuncionVO> funcionesHijos = servicioFuncion.getFuncionesHijos(codAplicacion, codFuncion);
		if (funcionesHijos != null && !funcionesHijos.isEmpty()) {
			for (FuncionVO funcionHijo : funcionesHijos) {
				usuarioFuncionSinAccesoDao.borrar(codAplicacion, funcionHijo.getId().getCodigoFuncion(), idContrato.longValue(), idUsuario);
			}
		}
	}

	@Override
	@Transactional
	public ResultBean desasignarPermiso(BigDecimal idContrato, String codFuncion, String codAplicacion, String numColegiado, Long idUsuario) {
		ResultBean resultBean = new ResultBean();
		try {
			List<FuncionVO> funciones = servicioFuncion.getFuncionesHijosYPadre(codAplicacion, codFuncion);
			if (funciones != null && !funciones.isEmpty()) {
				if (idUsuario == null) {
					idUsuario = 0L;
				}
				for (FuncionVO funcion : funciones) {
					UsuarioFuncionSinAccesoVO sinAcceso = new UsuarioFuncionSinAccesoVO();
					UsuarioFuncionSinAccesoPK id = new UsuarioFuncionSinAccesoPK();
					id.setCodigoAplicacion(funcion.getId().getCodigoAplicacion());
					id.setCodigoFuncion(funcion.getId().getCodigoFuncion());
					id.setIdContrato(idContrato.longValue());
					id.setIdUsuario(idUsuario);
					sinAcceso.setId(id);
					usuarioFuncionSinAccesoDao.guardarOActualizar(sinAcceso);
				}
				actualizarFechaRenovacion(numColegiado);
			}
		} catch (Exception e) {
			Log.error("Error al desasignar permisos", e);
			resultBean.setError(true);
			resultBean.setMensaje("Error al desasignar permisos");
		}

		return resultBean;
	}

	private void actualizarFechaRenovacion(String numColegiado) {
		List<UsuarioVO> usuarios = usuarioDao.getUsuariosNumColegiado(numColegiado);
		if (usuarios != null) {
			Date date = new Date();
			for (UsuarioVO usuario : usuarios) {
				usuario.setFechaRenovacion(date);
				usuarioDao.actualizar(usuario);
			}
		}
	}

	@Override
	@Transactional
	public List<FuncionDto> obtenerPermisosContrato(Long idContrato) {
		List<FuncionDto> funcionesFinales = new ArrayList<>();
		List<FuncionDto> funciones = new ArrayList<>();

		List<ContratoAplicacionVO> aplicacionesContrato = servicioAplicacion.getAplicacionesPorContrato(idContrato);

		if (aplicacionesContrato != null && !aplicacionesContrato.isEmpty()) {
			for (ContratoAplicacionVO aplicacionContrato : aplicacionesContrato) {
				List<FuncionDto> funcionesAplicacion = servicioFuncion.getFuncionesPorAplicacion(aplicacionContrato.getId().getCodigoAplicacion());
				if (funcionesAplicacion != null && !funcionesAplicacion.isEmpty()) {
					funciones.addAll(funcionesAplicacion);
				}
			}
		}

		if (funciones != null && !funciones.isEmpty()) {
			List<UsuarioFuncionSinAccesoVO> funcionesSinAcceso = usuarioFuncionSinAccesoDao.getUsuarioFuncionSinAcceso(null, null, idContrato, (long) 0);

			for (FuncionDto funcion : funciones) {
				boolean permisoActivo = true;
				if (funcionesSinAcceso != null && !funcionesSinAcceso.isEmpty()) {
					for (UsuarioFuncionSinAccesoVO funcionSinAcceso : funcionesSinAcceso) {
						if (funcionSinAcceso.getId().getCodigoFuncion().equals(funcion.getCodigoFuncion())) {
							permisoActivo = false;
							break;
						}
					}
				}
				if (permisoActivo) {
					funcionesFinales.add(funcion);
				}
			}
		}
		return funcionesFinales;
	}

	@Override
	@Transactional
	public List<FuncionDto> obtenerPermisosContratoPorAplicacion(Long idContrato, String codigoAplicacion) {
		List<FuncionDto> funcionesFinales = new ArrayList<FuncionDto>();

		List<FuncionDto> funciones = servicioFuncion.getFuncionesPorAplicacion(codigoAplicacion);

		if (funciones != null && !funciones.isEmpty()) {
			List<UsuarioFuncionSinAccesoVO> funcionesSinAcceso = usuarioFuncionSinAccesoDao.getUsuarioFuncionSinAcceso(codigoAplicacion, null, idContrato, (long) 0);

			for (FuncionDto funcion : funciones) {
				funcion.setAsignada(true);
				if (funcionesSinAcceso != null && !funcionesSinAcceso.isEmpty()) {
					for (UsuarioFuncionSinAccesoVO funcionSinAcceso : funcionesSinAcceso) {
						if (funcionSinAcceso.getId().getCodigoFuncion().equals(funcion.getCodigoFuncion())) {
							funcion.setAsignada(false);
							break;
						}
					}
				}
				if (funcion.getCodFuncionPadre() == null || funcion.getCodFuncionPadre().isEmpty()) {
					funcion.setEsPadre(true);
				}
				funcionesFinales.add(funcion);
			}
			funcionesFinales = marcarPadres(funcionesFinales);
		}
		return funcionesFinales;
	}

	private List<FuncionDto> marcarPadres(List<FuncionDto> listaFunciones) {
		ArrayList<FuncionDto> funcionesModificadas = new ArrayList<>();
		for (FuncionDto funcion : listaFunciones) {
			boolean esPadre = false;
			List<FuncionDto> listaAuxiliar = listaFunciones;
			for (FuncionDto funcionPadre : listaAuxiliar) {
				if (funcion.getCodigoFuncion().equals(funcionPadre.getCodFuncionPadre())) {
					esPadre = true;
					break;
				}
			}
			funcion.setEsPadre(esPadre);
			funcionesModificadas.add(funcion);
		}
		return funcionesModificadas;
	}

	@Override
	@Transactional
	public List<FuncionDto> obtenerPermisosUsuario(Long idContrato, Long idUsuario) {
		List<FuncionDto> funcionesFinales = new ArrayList<FuncionDto>();
		List<FuncionDto> funciones = obtenerPermisosContrato(idContrato);
		if (funciones != null && !funciones.isEmpty()) {
			List<UsuarioFuncionSinAccesoVO> funcionesSinAcceso = usuarioFuncionSinAccesoDao.getUsuarioFuncionSinAcceso(null, null, idContrato, idUsuario);

			for (FuncionDto funcion : funciones) {
				funcion.setAsignada(true);
				if (funcionesSinAcceso != null && !funcionesSinAcceso.isEmpty()) {
					for (UsuarioFuncionSinAccesoVO funcionSinAcceso : funcionesSinAcceso) {
						if (funcionSinAcceso.getId().getCodigoFuncion().equals(funcion.getCodigoFuncion())) {
							funcion.setAsignada(false);
						}
					}
				}
				funcionesFinales.add(funcion);
			}
			funcionesFinales = marcarPadres(funcionesFinales);
		}
		return funcionesFinales;
	}

	@Override
	@Transactional
	public void eliminarPermisosUsuarios(Long idUsuario) {
		List<UsuarioFuncionSinAccesoVO> funcionesSinAcceso = usuarioFuncionSinAccesoDao.getUsuarioFuncionSinAcceso(null, null, null, idUsuario);
		for (UsuarioFuncionSinAccesoVO funcionSinAcceso : funcionesSinAcceso) {
			usuarioFuncionSinAccesoDao.borrar(funcionSinAcceso);
		}
	}
}
