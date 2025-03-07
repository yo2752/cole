package org.gestoresmadrid.oegamComun.acceso.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.oegamComun.acceso.model.bean.MenuFuncionBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.PermisoUsuarioBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.ResultadoAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioAccesos;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioUrl;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.funcion.service.ServicioComunFuncion;
import org.gestoresmadrid.oegamComun.funcion.service.ServicioFuncionFavoritos;
import org.gestoresmadrid.oegamComun.funcion.service.ServicioFuncionSinAcceso;
import org.gestoresmadrid.oegamComun.usuario.service.ServicioComunUsuario;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.UserAuthorizations;

@Service
public class ServicioAccesosImpl implements ServicioAccesos {

	private static final long serialVersionUID = 8319929589753829628L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioAccesosImpl.class);

	@Autowired
	ServicioComunUsuario servicioComunUsuario;

	@Autowired
	ServicioComunContrato servicioComunContrato;

	@Autowired
	ServicioFuncionSinAcceso servicioFuncionSinAcceso;

	@Autowired
	ServicioComunFuncion servicioComunFuncion;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioFuncionFavoritos servicioFuncionFavoritos;

	@Autowired
	ServicioUrl servicioUrl;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoAccesoBean generarUserAuthorization(List<String> listaUrlsAcceso) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			resultado.setUserAuthorizations(new UserAuthorizations(new ArrayList<String>(), new ArrayList<String>()));
			if ("SI".equals(gestorPropiedades.valorPropertie("check.authorized.access"))) {
				resultado.getUserAuthorizations().setAuthorizedUrls(listaUrlsAcceso);
				resultado.getUserAuthorizations().setSecuralizedUrls(servicioUrl.getListaUrlsSecuralized());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el UserAuthorization, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el UserAuthorization.");
		}
		return resultado;
	}

	@Override
	public ResultadoAccesoBean actualizarDatosConexionUsuario(Long idUsuario) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			servicioComunUsuario.actualizarDatosConexionUsuario(idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los datos de conexion del usuario: " + idUsuario + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los datos de conexion del usuario.");
		}
		return resultado;
	}

	@Override
	public ResultadoAccesoBean obtenerFavoritosContrato(Long idContrato) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			resultado.setListaFavoritosContrato(servicioFuncionFavoritos.obtenerListaFavoritosContrato(idContrato));
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los menus favoritos para el contrato: " + idContrato + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los menus favoritos para el contrato." );
		}
		return resultado;
	}

	@Override
	public ResultadoAccesoBean getUsuariosActivo(String nif) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			List<UsuarioVO> listaUsuarios = servicioComunUsuario.getUsuarioPorNif(nif, new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()));
			if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
				if (listaUsuarios.size() == 1) {
					resultado.setUsuario(listaUsuarios.get(0));
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Actualmente el NIF: " + nif + " se encuentra habilitado para dos contratos, por favor pongase en contacto con el CAU para que solucionen este problena.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe ningun usuario habilitado para el NIF: " + nif);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el usuario activo para el NIF: " + nif + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el usuario activo para el NIF: " + nif );
		}
		return resultado;
	}

	@Override
	public ResultadoAccesoBean getContratosColegiado(String numColegiado) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			List<ContratoVO> listaContratos = servicioComunContrato.getContratosPorColegiado(numColegiado, new BigDecimal(EstadoContrato.Habilitado.getValorEnum()));
			if (listaContratos != null && !listaContratos.isEmpty()) {
				resultado.setListaContratos(listaContratos);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe ningun contrato habilitado para el numColegiado: " + numColegiado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los contratos activos para el numColegiado: " + numColegiado + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los contratos activos para el numColegiado: " + numColegiado );
		}
		return resultado;
	}

	@Override
	public ResultadoAccesoBean obtenerListaMenuContrato(Long idUsuario, Long idContrato) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			List<ContratoAplicacionVO> listaAplicacionesContrato = servicioComunFuncion.obtenerListadoAplicacionesContrato(idContrato);
			if (listaAplicacionesContrato != null && !listaAplicacionesContrato.isEmpty()) {
				List<UsuarioFuncionSinAccesoVO> listaFuncionesSinAcceso = obtenerListadoFuncionSinAcceso(idContrato, idUsuario);
				boolean funcionConAcceso;
				for (ContratoAplicacionVO contratoAplicacionVO : listaAplicacionesContrato) {
					List<FuncionVO> listaFuncionesAplicacion = servicioComunFuncion.obtenerListadoFuncionesMenuPorAplicacion(contratoAplicacionVO.getId().getCodigoAplicacion());
					if (listaFuncionesAplicacion != null && !listaFuncionesAplicacion.isEmpty()) {
						for (FuncionVO funcionBBDD : listaFuncionesAplicacion) {
							funcionConAcceso = Boolean.TRUE;
							if (listaFuncionesSinAcceso != null && !listaFuncionesSinAcceso.isEmpty()) {
								for (UsuarioFuncionSinAccesoVO usSinAcceso : listaFuncionesSinAcceso) {
									if (usSinAcceso.getId().getCodigoAplicacion().equals(contratoAplicacionVO.getId().getCodigoAplicacion())
										&& usSinAcceso.getId().getCodigoFuncion().equals(funcionBBDD.getId().getCodigoFuncion())) {
										funcionConAcceso = Boolean.FALSE;
										break;
									}
								}
							}
							if (funcionConAcceso) {
								if ("M".equalsIgnoreCase(funcionBBDD.getTipo())) {
									resultado.addListaMenu(conversor.transform(funcionBBDD, MenuFuncionBean.class));
								} else if ("E".equalsIgnoreCase(funcionBBDD.getTipo())) {
									resultado.addListaPermisos(conversor.transform(funcionBBDD, PermisoUsuarioBean.class));
								}
								if (StringUtils.isNotBlank(funcionBBDD.getUrl())) {
									resultado.addListaUrlsAcceso(funcionBBDD.getUrl());
								}
							}
						}
					}
				}
			} 
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los menus para el contrato: " + idContrato + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los menus para el contrato." );
		}
		return resultado;
	}

	@Override
	public List<UsuarioFuncionSinAccesoVO> obtenerListadoFuncionSinAcceso(Long idContrato, Long idUsuario) {
		List<UsuarioFuncionSinAccesoVO> listaSinAccesoContrato = servicioFuncionSinAcceso.getListaFuncionSinAccesoContrato(idContrato);
		List<UsuarioFuncionSinAccesoVO> listaSinAccesoUsuario = servicioFuncionSinAcceso.getListaFuncionSinAccesoPorContratoYUsuario(idContrato, idUsuario);
		if ((listaSinAccesoContrato != null && !listaSinAccesoContrato.isEmpty()) && (listaSinAccesoUsuario != null && !listaSinAccesoUsuario.isEmpty())) {
			List<UsuarioFuncionSinAccesoVO> listaAux = listaSinAccesoUsuario;
			Boolean existeFuncionSinAcceso = Boolean.FALSE;
			for (UsuarioFuncionSinAccesoVO usAccesoContrato : listaSinAccesoContrato){
				existeFuncionSinAcceso = Boolean.FALSE;
				for (UsuarioFuncionSinAccesoVO usAccesoUsuario : listaSinAccesoUsuario){
					if (usAccesoUsuario.getId().getCodigoAplicacion().equals(usAccesoContrato.getId().getCodigoAplicacion())
							&& usAccesoUsuario.getId().getCodigoFuncion().equals(usAccesoContrato.getId().getCodigoFuncion())) {
						existeFuncionSinAcceso = Boolean.TRUE;
						break;
					}
				}
				if (!existeFuncionSinAcceso) {
					listaAux.add(usAccesoContrato);
				}
			}
			return listaAux;
		} else if (listaSinAccesoContrato != null && !listaSinAccesoContrato.isEmpty()) {
			return listaSinAccesoContrato;
		} else {
			return listaSinAccesoUsuario;
		}
	}

}