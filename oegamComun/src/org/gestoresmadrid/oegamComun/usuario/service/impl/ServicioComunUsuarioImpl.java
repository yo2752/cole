package org.gestoresmadrid.oegamComun.usuario.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.general.model.dao.ContratoUsuarioDao;
import org.gestoresmadrid.core.general.model.dao.UsuarioDao;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.evolucionUsuario.service.ServicioEvolucionUsuario;
import org.gestoresmadrid.oegamComun.usuario.service.ServicioComunUsuario;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunUsuarioImpl implements ServicioComunUsuario{

	private static final long serialVersionUID = -5642377676126008029L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunUsuarioImpl.class);
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	ContratoUsuarioDao contratoUsuarioDao;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	ServicioComunContrato servicioComunContrato;
	
	@Autowired
	ServicioEvolucionUsuario servicioEvolucionUsuario;
	
	@Autowired
	Conversor conversor;
	
	@Override
	@Transactional(readOnly=true)
	public UsuarioVO getUsuarioPorNifYColegiado(String nif, String numColegiado) {
		try {
			return usuarioDao.getUsuarioPorNifYColegiado(nif, numColegiado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el usuario del nif: " + nif + " y del colegiado: " + numColegiado + " , error: ", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<UsuarioDto> getListaUsuariosColegio() {
		try {
			List<UsuarioVO> listaUsuarioBBDD = usuarioDao.getUsuarioPorNumColegiado(gestorPropiedades.valorPropertie("trafico.numcolegiado.administrador"));
			if(listaUsuarioBBDD != null && !listaUsuarioBBDD.isEmpty()){
				return conversor.transform(listaUsuarioBBDD, UsuarioDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de Usuarios para el colegio, error: ", e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public void actualizarDatosConexionUsuario(Long idUsuario) {
		UsuarioVO usuarioVO = getUsuario(idUsuario);
		usuarioVO.setFechaRenovacion(null);
		usuarioVO.setUltimaConexion(new Date());
		usuarioDao.actualizar(usuarioVO);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<UsuarioVO> getUsuarioPorNif(String nif, BigDecimal estado) {
		try {
			if(nif != null && !nif.isEmpty()){
				return usuarioDao.getUsuarioPorNifYEstado(nif, estado);
			}
		} catch (Exception e) {
			log.error("Error al obtener la lista con los usuarios con el nif: " + nif + ", error: ", e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public ResultadoBean deshabilitarUsuario(BigDecimal idUsuario, Date fecha, Boolean salirSesion) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			UsuarioVO usuario = usuarioDao.getUsuario(idUsuario.longValue());
			if(usuario != null){
				List<ContratoUsuarioVO> ListaContratosUsu = contratoUsuarioDao.getContratosPorUsuario(idUsuario);
				if (!EstadoUsuario.Eliminado.getValorEnum().equals(usuario.getEstadoUsuario().toString())) {
					if(EstadoUsuario.Deshabilitado.getValorEnum().equals(usuario.getEstadoUsuario().toString())){
						resultado.setError(true);
						resultado.setMensaje("El usuario '" + usuario.getApellidosNombre() + "' ya esta deshabilitado en este contrato");
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
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El usuario no se puede deshabilitar puesto que actualmente se encuentra Eliminado.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos en BBDD para ese usuario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de deshabilitar el usuario: " + idUsuario + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de deshabilitar el usuario.");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public UsuarioVO getUsuario(Long idUsuario) {
		return usuarioDao.getUsuario(idUsuario);
	}
	
	@Override
	@Transactional
	public ResultadoBean habilitarUsuario(BigDecimal idUsuario, Boolean salirSesion) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			UsuarioVO usuario = usuarioDao.getUsuario(idUsuario.longValue());
			if(usuario != null){
				List<ContratoUsuarioVO> ListaContratosUsu = null;
				if (salirSesion != null && salirSesion) {
					ListaContratosUsu = contratoUsuarioDao.getContratosAnterioresPorUsuario(idUsuario);
				} else {
					ListaContratosUsu = contratoUsuarioDao.getContratosPorUsuario(idUsuario);
				}
				if (!EstadoUsuario.Eliminado.getValorEnum().equals(usuario.getEstadoUsuario().toString())) {
					// Comprobar que no exista en otro contrato
					BigDecimal estadoHabilitado = new BigDecimal(Estado.Habilitado.getValorEnum());
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
									servicioComunContrato.asociarUsuarioContrato(new String[] { String.valueOf(idUsuario) }, contrato.getId().getIdContrato());
								}
							}
							// Guardar evolucion
							Calendar fechaCambio = Calendar.getInstance();
							servicioEvolucionUsuario.guardarEvolucionUsuario(idUsuario, new Timestamp(fechaCambio.getTimeInMillis()), null, TipoActualizacion.MOD, new BigDecimal(EstadoUsuario.Habilitado
									.getValorEnum()), new BigDecimal(EstadoUsuario.Deshabilitado.getValorEnum()), null, usuario.getNif());
							fechaCambio.add(Calendar.SECOND, 1);
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El usuario '" + usuario.getApellidosNombre() + "' no se puede habilitar porque esta habilitado en otro contrato");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El usuario no se puede habilitar puesto que actualmente se encuentra Eliminado.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos en BBDD para ese usuario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de habilitar el usuario: " + idUsuario + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de habilitar el usuario.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoBean altaUsuarioDesdeOtro(UsuarioVO usuarioBBDD, String numColegiado) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			List<UsuarioVO> listaUsuario = usuarioDao.getUsuarioPorNifYEstado(usuarioBBDD.getNif(), new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()));
			if(listaUsuario == null || listaUsuario.isEmpty()){
				UsuarioVO usuarioVO = new UsuarioVO();
				usuarioVO.setAnagrama(usuarioBBDD.getAnagrama());
				usuarioVO.setApellidosNombre(usuarioBBDD.getApellidosNombre());
				usuarioVO.setCorreoElectronico(usuarioBBDD.getCorreoElectronico());
				usuarioVO.setEstadoUsuario(new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()));
				usuarioVO.setFechaAlta(new Date());
				usuarioVO.setJefaturaJPT(usuarioBBDD.getJefaturaJPT());
				usuarioVO.setNif(usuarioBBDD.getNif());
				usuarioVO.setNumColegiado(numColegiado);
				usuarioVO.setNumColegiadoNacional(usuarioBBDD.getNumColegiadoNacional());
				usuarioVO.setIdGrupo(usuarioBBDD.getIdGrupo());
				usuarioDao.guardar(usuarioVO);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ya existe un usuario habilitado con el NIF: " + usuarioBBDD.getNif());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de dar de alta el usuario con nif: " + usuarioBBDD.getNif() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de dar de alta el usuario con nif: " + usuarioBBDD.getNif());
		}
/**
 * UsuarioContratoBean usuarioContratoBean = new UsuarioContratoBean();
						usuarioContratoBean.setAnagrama(usuario.getAnagrama());
						usuarioContratoBean.setApellidosNombre(usuario.getApellidos_nombre());
						usuarioContratoBean.setCorreoElectronico(usuario.getCorreo_electronico());
						usuarioContratoBean.setEstadoUsuario(Estado.Habilitado.getValorEnum().toString());
						usuarioContratoBean.setIdGrupoUsuario("-1");
						usuarioContratoBean.setNif(usuario.getNif());
						usuarioContratoBean.setNumColegiado(contratoBean.getDatosColegiado().getNumColegiado());
 */
		return resultado;
	}
	
	
}
