package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.dao.UsuarioLoginDao;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.general.model.dao.ContratoUsuarioDao;
import org.gestoresmadrid.core.general.model.dao.UsuarioDao;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegamComun.accesos.view.dto.FuncionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.evolucionUsuario.service.ServicioEvolucionUsuario;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

	private static final long serialVersionUID = -3166432429891288991L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioUsuarioImpl.class);

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private ContratoUsuarioDao contratousuarioDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioEvolucionUsuario servicioEvolucionUsuario;

	@Autowired
	private UsuarioLoginDao usuarioLoginDao;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	@Override
	@Transactional(readOnly=true)
	public List<UsuarioVO> getUsuarioPorNif(String nif) {
		List<UsuarioVO> lista = null;
		try {
			if(nif != null && !nif.isEmpty()){
				return usuarioDao.getUsuarioPorNifYEstado(nif, new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()));
			}
		} catch (Exception e) {
			log.error("Error al obtener la lista con los usuarios con el nif: " + nif + ", error: ", e);
			lista = null;
		}
		return lista;
	}
	
	@Override
	@Transactional
	public UsuarioDto getUsuarioDto(BigDecimal idUsuario) {
		UsuarioVO usuario = null;
		try {
			usuario = usuarioDao.getUsuario(idUsuario.longValue());
			return conversor.transform(usuario, UsuarioDto.class);
		} catch (Exception e) {
			log.error("Error al obtener el usuario: " + idUsuario, e);
		} finally {
			usuarioDao.evict(usuario);
		}
		return null;
	}

	@Override
	@Transactional
	public UsuarioVO getUsuario(BigDecimal idUsuario) {
		try {
			return usuarioDao.getUsuario(idUsuario.longValue());
		} catch (Exception e) {
			log.error("Error al obtener el usuario: " + idUsuario, e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean guardarUsuario(UsuarioDto usuario) {
		ResultBean result = new ResultBean();
		try {
			result = guardar(conversor.transform(usuario, UsuarioVO.class));

		} catch (Exception e) {
			log.error("Error al guardar el usuario. Mensaje: " + e.getMessage(), e);
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardar(UsuarioVO usuarioVO) {
		ResultBean result = new ResultBean();
		try {
			if (usuarioVO.getEstadoUsuario() == null) {
				usuarioVO.setEstadoUsuario(new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()));
			}
			if (usuarioVO.getAnagrama() == null || usuarioVO.getAnagrama().isEmpty()) {
				String anagramaUsuario = Anagrama.obtenerAnagramaFiscal(usuarioVO.getApellidosNombre().toUpperCase(), usuarioVO.getNif().toUpperCase());
				usuarioVO.setAnagrama(anagramaUsuario);
			}
			modificarDatosCombos(usuarioVO);
			modificarDatosMayusculas(usuarioVO);
			if (usuarioVO.getIdUsuario() == null) {
				if (EstadoUsuario.Habilitado.getValorEnum().equals(usuarioVO.getEstadoUsuario().toString())) {
					boolean existe = usuarioDao.numUsuariosPorNifEstado(usuarioVO.getNif(), BigDecimal.valueOf(Estado.Habilitado.getValorEnum())) > 0;
					if (existe) {
						result.setError(true);
						result.setMensaje("El usuario ya está habilitado en otro contrato con ese NIF");
						return result;
					}
				} else if (!Estado.Habilitado.getValorEnum().equals(usuarioVO.getEstadoUsuario().toString())) {
					usuarioVO.setFechaFin(new Date());
				}
				usuarioVO.setFechaAlta(new Date());
				Long idUsuario = (Long) usuarioDao.guardar(usuarioVO);
				result.addAttachment(ID_USUARIO, idUsuario);
			} else {
				usuarioDao.actualizar(usuarioVO);
				result.addAttachment(ID_USUARIO, usuarioVO.getIdUsuario());
			}
		} catch (Exception e) {
			log.error("Error al guardar el usuario. Mensaje: " + e.getMessage(), e);
			result.setMensaje(e.getMessage());
			result.setError(true);
		}
		return result;
	}

	private void modificarDatosMayusculas(UsuarioVO usuarioVO) {
		if (usuarioVO.getNif() != null) {
			usuarioVO.setNif(usuarioVO.getNif().toUpperCase());
		}
		if (usuarioVO.getApellidosNombre() != null) {
			usuarioVO.setApellidosNombre(usuarioVO.getApellidosNombre().toUpperCase());
		}
		if (usuarioVO.getAnagrama() != null) {
			usuarioVO.setAnagrama(usuarioVO.getAnagrama().toUpperCase());
		}
		if (usuarioVO.getIdGrupo() != null) {
			usuarioVO.setIdGrupo(usuarioVO.getIdGrupo().toUpperCase());
		}
	}

	private void modificarDatosCombos(UsuarioVO usuarioVO) {
		usuarioVO.setIdGrupo(utiles.convertirCombo(usuarioVO.getIdGrupo()));
	}

	@Transactional
	public ResultBean actualizarJefaturaJPTUsuario(BigDecimal idUsuario, String jefatura) {
		ResultBean resultado = new ResultBean();

		UsuarioVO usuario = usuarioDao.getUsuario(idUsuario.longValue());
		usuario.setJefaturaJPT(jefatura);

		try {
			usuarioDao.guardar(usuario);
			resultado.setMensaje("Usuario actualizado correctamente.");
		} catch (Exception e) {
			log.error("Ha ocurrido un error al actualizarJefaturaJPTUsuario en ServicioUsuarioImpl", e);
			resultado.setError(true);
			resultado.setMensaje("Ha ocurrido un error al actualizar su configuración.");
		}
		return resultado;

	}

	@Override
	public String getJefaturaProvincial(long idUsuario) {
		return usuarioDao.getJefaturaProvincialPorUsuario(idUsuario);
	}

	@Override
	@Transactional
	public UsuarioDto getGrupoUsuarioPorNIF(String nif) {
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setNif(nif);
		usuarioVO.setEstadoUsuario(new BigDecimal(1));
		try {
			List<UsuarioVO> lista = usuarioDao.obtenerGrupoUsuarioPorNif(usuarioVO);
			if (lista != null && !lista.isEmpty()) {
				return convertirUsuarioVOToUsuarioDto(lista.get(0));
			}
		} catch (Exception e) {
			log.error("Error al obtener el usuario por su NIF:" + nif + " , error: " + e.getMessage());
		}
		return null;
	}

	@Transactional
	@Override
	public ResultBean deshabilitarUsuario(BigDecimal idUsuario, Date fecha, Boolean salirSesion) {
		ResultBean resultBean = new ResultBean();
		UsuarioVO usuario = usuarioDao.getUsuario(idUsuario.longValue());
		// Contratos del usuario
		List<ContratoUsuarioVO> ListaContratosUsu = contratousuarioDao.getContratosPorUsuario(idUsuario);

		if (usuario != null && !BigDecimal.valueOf(Estado.Eliminado.getValorEnum()).equals(usuario.getEstadoUsuario())) {
			if (fecha == null) {
				fecha = new Date();
			}

			if (BigDecimal.valueOf(Estado.Deshabilitado.getValorEnum()).equals(usuario.getEstadoUsuario())) {
				resultBean.setError(true);
				resultBean.setMensaje("El usuario '" + usuario.getApellidosNombre() + "' ya esta deshabilitado en este contrato");
			} else {
				usuario.setFechaFin(fecha);
				usuario.setEstadoUsuario(BigDecimal.valueOf(Estado.Deshabilitado.getValorEnum()));
				usuarioDao.actualizar(usuario);

				String GestionarUsuariosPorContrato = gestorPropiedades.valorPropertie("gestionar.usuarios.contrato");
				if (GestionarUsuariosPorContrato != null && "SI".equals(GestionarUsuariosPorContrato)) {

					Calendar fechaCambio = Calendar.getInstance();

					// 1- Desasignar los contratos que el usuario tenga asociados
					for (ContratoUsuarioVO contrato : ListaContratosUsu) {
						// Si viene de salir se guardara estadoUSUARIO=6
						if (salirSesion != null && salirSesion) {

							contrato.setEstadoUsuarioContrato(new BigDecimal("6")); // Le actualiza a 6 todos los contratos?

						} else {
							contrato.setEstadoUsuarioContrato(BigDecimal.ZERO);
							// Guardar evolucion
							servicioEvolucionUsuario.guardarEvolucionUsuario(idUsuario, new Timestamp(fechaCambio.getTimeInMillis()), contrato.getContrato().getIdContrato().longValue(),
									TipoActualizacion.MOD, new BigDecimal(EstadoUsuario.SinAsociar.getValorEnum()), new BigDecimal(EstadoUsuario.Asociado.getValorEnum()), contrato.getContrato()
											.getIdContrato().longValue(), usuario.getNif());
							fechaCambio.add(Calendar.SECOND, 1);

						}

					}

					// 2- Se deshabilita de la gestoria.
					servicioEvolucionUsuario.guardarEvolucionUsuario(idUsuario, new Timestamp(fechaCambio.getTimeInMillis()), null, TipoActualizacion.MOD, new BigDecimal(EstadoUsuario.Deshabilitado
							.getValorEnum()), new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()), null, usuario.getNif());
					fechaCambio.add(Calendar.SECOND, 1);
				}
			}
		}
		return resultBean;
	}

	@Transactional
	@Override
	public ResultBean habilitarUsuario(BigDecimal idUsuario, Boolean salirSesion) {
		ResultBean resultBean = new ResultBean();
		List<ContratoUsuarioVO> ListaContratosUsu = null;

		UsuarioVO usuario = usuarioDao.getUsuario(idUsuario.longValue());
		Calendar fechaCambio = Calendar.getInstance();

		if (salirSesion != null && salirSesion) {
			ListaContratosUsu = contratousuarioDao.getContratosAnterioresPorUsuario(idUsuario);
		} else {
			// Contratos del usuario
			ListaContratosUsu = contratousuarioDao.getContratosPorUsuario(idUsuario);
		}

		if (usuario != null && !BigDecimal.valueOf(Estado.Eliminado.getValorEnum()).equals(usuario.getEstadoUsuario())) {
			// Comprobar que no exista en otro contrato
			BigDecimal estadoHabilitado = BigDecimal.valueOf(Estado.Habilitado.getValorEnum());
			boolean existe = usuarioDao.numUsuariosPorNifEstado(usuario.getNif(), estadoHabilitado) > 0;
			if (!existe || (ListaContratosUsu != null && !ListaContratosUsu.isEmpty() && ListaContratosUsu.get(0).getEstadoUsuarioContrato().equals(new BigDecimal("6")))) {
				usuario.setEstadoUsuario(estadoHabilitado);
				usuario.setFechaFin(null);
				usuarioDao.actualizar(usuario);

				String GestionarUsuariosPorContrato = gestorPropiedades.valorPropertie("gestionar.usuarios.contrato");
				if (GestionarUsuariosPorContrato != null && "SI".equals(GestionarUsuariosPorContrato)) {
					// COMPROBAR SI VIENE DE CERRAR SESION POR QUE HAY QUE VOLVER A CAMBIAR AL CONTRATO ORIGINAL
					if (salirSesion != null && salirSesion == true) {
						for (ContratoUsuarioVO contrato : ListaContratosUsu) {
							servicioContrato.asociarUsuarioContrato(new String[] { String.valueOf(idUsuario) }, contrato.getId().getIdContrato());
						}
					}

					// Guardar evolucion
					servicioEvolucionUsuario.guardarEvolucionUsuario(idUsuario, new Timestamp(fechaCambio.getTimeInMillis()), null, TipoActualizacion.MOD, new BigDecimal(EstadoUsuario.Habilitado
							.getValorEnum()), new BigDecimal(EstadoUsuario.Deshabilitado.getValorEnum()), null, usuario.getNif());
					fechaCambio.add(Calendar.SECOND, 1);
				}
			} else {
				resultBean.setError(true);
				resultBean.setMensaje("El usuario '" + usuario.getApellidosNombre() + "' no se puede habilitar porque esta habilitado en otro contrato");
			}
		}
		return resultBean;
	}

	@Transactional
	@Override
	public void eliminarUsuario(BigDecimal idUsuario, Date fecha) {
		UsuarioVO usuario = usuarioDao.getUsuario(idUsuario.longValue());

		if (fecha == null) {
			fecha = new Date();
		}
		usuario.setFechaFin(fecha);
		usuario.setEstadoUsuario(BigDecimal.valueOf(Estado.Eliminado.getValorEnum()));
		usuarioDao.actualizar(usuario);

		// PROPERTIE
		String GestionarUsuariosPorContrato = gestorPropiedades.valorPropertie("gestionar.usuarios.contrato");
		if (GestionarUsuariosPorContrato != null && "SI".equals(GestionarUsuariosPorContrato)) {
			// Contratos del usuario
			List<ContratoUsuarioVO> ListaContratosUsu = contratousuarioDao.getContratosPorUsuario(idUsuario);

			Calendar fechaCambio = Calendar.getInstance();
			// 1- Se desasigna los contratos que tenia asociados el usuario
			for (ContratoUsuarioVO contrato : ListaContratosUsu) {
				contrato.setEstadoUsuarioContrato(BigDecimal.ZERO);
				// Guardar evolucion
				servicioEvolucionUsuario.guardarEvolucionUsuario(idUsuario, new Timestamp(fechaCambio.getTimeInMillis()), null, TipoActualizacion.MOD, new BigDecimal(EstadoUsuario.Eliminado
						.getValorEnum()), usuario.getEstadoUsuario(), contrato.getId().getIdContrato(), usuario.getNif());
				fechaCambio.add(Calendar.SECOND, 1);
			}
			// 2- Se elimina el usuario de la gestoria.

			servicioEvolucionUsuario.guardarEvolucionUsuario(idUsuario, new Timestamp(fechaCambio.getTimeInMillis()), null, TipoActualizacion.MOD, new BigDecimal(EstadoUsuario.Eliminado
					.getValorEnum()), usuario.getEstadoUsuario(), null, usuario.getNif());
			fechaCambio.add(Calendar.SECOND, 1);
		}
		// Se les quita el permiso
		servicioPermisos.eliminarPermisosUsuarios(idUsuario.longValue());
	}

	private UsuarioDto convertirUsuarioVOToUsuarioDto(UsuarioVO usuarioVO) {
		return conversor.transform(usuarioVO, UsuarioDto.class);
	}

	@Override
	@Transactional
	public List<UsuarioDto> getUsuarioPorNumColegiado(String numColegiado) {
		try {
			List<UsuarioVO> usuario = usuarioDao.getUsuarioPorNumColegiado(numColegiado);
			return conversor.transform(usuario, UsuarioDto.class);
		} catch (Exception e) {
			log.error("Error al obtener los usuarios para el numero de colegiado: " + numColegiado, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<UsuarioDto> getUsuariosHabilitadosPorNumColegiado(String numColegiado) {
		try {
			List<UsuarioVO> usuario = usuarioDao.getUsuariosHabilitadosPorNumColegiado(numColegiado);
			return conversor.transform(usuario, UsuarioDto.class);
		} catch (Exception e) {
			log.error("Error al obtener los usuarios para el numero de colegiado: " + numColegiado, e);
		}
		return null;
	}

	@Override
	@Transactional
	public List<UsuarioDto> getUsuariosPorContrato(Long idContrato) {
		try {
			List<UsuarioVO> usuario = usuarioDao.getUsuariosPorContrato(idContrato);

			return conversor.transform(usuario, UsuarioDto.class);
		} catch (Exception e) {
			log.error("Error al obtener los usuarios para el contrato: " + idContrato, e);
		}
		return null;
	}

	@Override
	@Transactional
	public UsuarioDto usuarionConPermisos(BigDecimal idUsuario, Long idContrato) {
		UsuarioDto usuario = getUsuarioDto(idUsuario);
		List<FuncionDto> permisos = servicioPermisos.obtenerPermisosUsuario(idContrato, idUsuario.longValue());
		if (permisos != null && !permisos.isEmpty()) {
			usuario.setPermisos(permisos);
		}
		return usuario;
	}

	@Override
	@Transactional
	public List<ContratoUsuarioVO> getContratosAnterioresPorUsuario(String idSesion) {
		List<ContratoUsuarioVO> ListaContratosUsu = null;
		ListaContratosUsu = contratousuarioDao.getContratosAnterioresPorUsuario(usuarioLoginDao.getUsuarioLoginVO(idSesion).getIdUsuario());

		return ListaContratosUsu;
	}
}