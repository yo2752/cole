package org.gestoresmadrid.oegamComun.acceso.service.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.favoritos.model.vo.ContratoFavoritosVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.oegamComun.acceso.model.bean.DetalleUsuarioBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.MenuAplicacionBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.MenuFuncionBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.PermisoUsuarioBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.ResultadoAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioAccesos;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioGestionAcceso;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.gestoresmadrid.oegamComun.usuario.service.ServicioComunUsuario;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import net.sf.navigator.menu.MenuRepository;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.Menu;
import utilidades.web.MiMenuComponent;

@Service
public class ServicioGestionAccesoImpl implements ServicioGestionAcceso {

	private static final long serialVersionUID = 5319788552540093484L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionAccesoImpl.class);

	@Autowired
	ServicioAccesos servicioAccesos;

	@Autowired
	ServicioSesion servicioSesion;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioComunUsuario servicioComunUsuario;

	@Autowired
	ServicioComunContrato servicioComunContrato;

	@Override
	public ResultadoAccesoBean gestionarCambioContratoUsuario(Long idUsuario, Long idContrato) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			UsuarioVO usuarioBBDD =  servicioComunUsuario.getUsuario(idUsuario);
			if (usuarioBBDD != null) {
				ResultadoBean resultUsuario = servicioComunUsuario.deshabilitarUsuario(new BigDecimal(idUsuario), new Date(), Boolean.FALSE);
				if (!resultUsuario.getError()) {
					ContratoVO contratoBBDD = servicioComunContrato.getContrato(idContrato);
					UsuarioVO usuarioContrato = servicioComunUsuario.getUsuarioPorNifYColegiado(usuarioBBDD.getNif(), contratoBBDD.getColegiado().getNumColegiado());
					if (usuarioContrato != null) {
						resultUsuario = servicioComunUsuario.habilitarUsuario(new BigDecimal(usuarioContrato.getIdUsuario()), Boolean.FALSE);
					} else {
						resultUsuario = servicioComunUsuario.altaUsuarioDesdeOtro(usuarioBBDD, contratoBBDD.getColegiado().getNumColegiado());
					}
					if (resultUsuario.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultUsuario.getMensaje());
						servicioComunUsuario.habilitarUsuario(new BigDecimal(idUsuario), Boolean.FALSE);
					}
				}
			} else {
				resultado.setMensaje("No existe datos del usuario.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar el cambio de contrato: " + idContrato + " para el usuario: " + idUsuario + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de gestionar el cambio de contrato: " + idContrato + " para el usuario: " + idUsuario);
		}
		return resultado;
	}

	@Override
	public ResultadoAccesoBean gestionAcceso(String nif, Long idContrao) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			resultado = servicioAccesos.getUsuariosActivo(nif);
			if (!resultado.getError()) {
				UsuarioVO usuarioAccesoVO = resultado.getUsuario();
				resultado = servicioAccesos.getContratosColegiado(usuarioAccesoVO.getNumColegiado());
				if (!resultado.getError()) {
					List<ContratoVO> listaContratos = resultado.getListaContratos();
					UsuarioAccesoBean usuarioAccesoBean = generarUsuarioAccesoBean(usuarioAccesoVO, listaContratos,
							idContrao);
					resultado = servicioAccesos.obtenerListaMenuContrato(usuarioAccesoBean.getIdUsuario(), usuarioAccesoBean.getIdContrato());
					if (!resultado.getError()) {
						if (resultado.getListaMenu() != null && !resultado.getListaMenu().isEmpty()) {
							usuarioAccesoBean.setListaMenu(resultado.getListaMenu());
						}
						if (resultado.getListaPermisos() != null && !resultado.getListaPermisos().isEmpty()) {
							usuarioAccesoBean.setListaPermisos(resultado.getListaPermisos());
							tratarPermisos(usuarioAccesoBean);
						}
						resultado = servicioAccesos.generarUserAuthorization(resultado.getListaUrlsAcceso());
						if (!resultado.getError()) {
							usuarioAccesoBean.setUserAuthorizations(resultado.getUserAuthorizations());

							servicioAccesos.actualizarDatosConexionUsuario(usuarioAccesoBean.getIdUsuario());
							resultado.setUsuarioAcceso(usuarioAccesoBean);
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se han podido recuperar ningún menú para el NIF: " + nif);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar el acceso para el NIF: " + nif + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de gestionar el acceso para el NIF: " + nif);
		}
		return resultado;
	}

	private void tratarPermisos(UsuarioAccesoBean usuarioAccesoBean) {
		for (PermisoUsuarioBean permiso : usuarioAccesoBean.getListaPermisos()) {
			if ("OA110".equals(permiso.getCodigoFuncion())) {
				usuarioAccesoBean.setTienePermisoAdministracion(Boolean.TRUE);
			} else if ("OA001".equals(permiso.getCodigoFuncion())) {
				usuarioAccesoBean.setTienePermisoColegio(Boolean.TRUE);
			} else if ("OP001".equals(permiso.getCodigoFuncion())) {
				usuarioAccesoBean.setTienePermisoEspecial(Boolean.TRUE);
			}
		}
	}

	@Override
	public ResultadoAccesoBean gestionFavoritos(UsuarioAccesoBean usuarioAcceso) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			resultado = servicioAccesos.obtenerFavoritosContrato(usuarioAcceso.getIdContrato());
			if (!resultado.getError() && resultado.getListaFavoritosContrato() != null && !resultado.getListaFavoritosContrato().isEmpty()) {
				for (MenuFuncionBean menuFuncion : usuarioAcceso.getListaMenu()) {
					for (ContratoFavoritosVO contratoFavoritosVO : resultado.getListaFavoritosContrato()) {
						if (menuFuncion.getCodigoFuncion().equals(contratoFavoritosVO.getFuncion().getId().getCodigoFuncion())
								&& menuFuncion.getCodigoAplicacion().equals(contratoFavoritosVO.getFuncion().getId().getCodigoAplicacion())) {
							resultado.addListaFavoritos(conversor.transform(menuFuncion, Menu.class));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los menus favoritos para el contrato: " + usuarioAcceso.getIdContrato() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los menús favoritos para el contrato." );
		}
		return resultado;
	}

	private UsuarioAccesoBean generarUsuarioAccesoBean(UsuarioVO usuarioAccesoVO, List<ContratoVO> listaContratos, Long idContrato) {
		UsuarioAccesoBean usuarioAccesoBean = new UsuarioAccesoBean();
		usuarioAccesoBean.setIdUsuario(usuarioAccesoVO.getIdUsuario());
		usuarioAccesoBean.setNif(usuarioAccesoVO.getNif());
		usuarioAccesoBean.setNumColegiado(usuarioAccesoVO.getNumColegiado());
		usuarioAccesoBean.setAliasColegiado(usuarioAccesoVO.getNumColegiado());
		if (idContrato == null) {
			usuarioAccesoBean.setIdContrato(listaContratos.get(0).getIdContrato());
			usuarioAccesoBean.setIdJefatura(listaContratos.get(0).getJefaturaTrafico().getJefaturaProvincial());
			usuarioAccesoBean.setRazonSocialContrato(listaContratos.get(0).getRazonSocial());
			usuarioAccesoBean.setAliasColegiado(listaContratos.get(0).getColegiado().getAlias());
			usuarioAccesoBean.setNombreProvinciaContrato(listaContratos.get(0).getProvincia().getNombre());
			usuarioAccesoBean.setIdProvinciaContrato(listaContratos.get(0).getProvincia().getIdProvincia());
			usuarioAccesoBean.setColegio(listaContratos.get(0).getColegio().getColegio());
			usuarioAccesoBean.setCifContrato(listaContratos.get(0).getCif());
		}
		usuarioAccesoBean.setNumColegiadoNacional(usuarioAccesoVO.getNumColegiadoNacional());
		for (ContratoVO contratoVO : listaContratos) {
			if (idContrato != null && idContrato.equals(contratoVO.getIdContrato())) {
				usuarioAccesoBean.setIdContrato(idContrato);
				usuarioAccesoBean.setRazonSocialContrato(contratoVO.getRazonSocial());
				usuarioAccesoBean.setIdJefatura(contratoVO.getJefaturaTrafico().getJefaturaProvincial());
				usuarioAccesoBean.setAliasColegiado(contratoVO.getColegiado().getAlias());
				usuarioAccesoBean.setNombreProvinciaContrato(contratoVO.getProvincia().getNombre());
				usuarioAccesoBean.setIdProvinciaContrato(contratoVO.getProvincia().getIdProvincia());
				usuarioAccesoBean.setColegio(contratoVO.getColegio().getColegio());
				usuarioAccesoBean.setCifContrato(contratoVO.getCif());
			}
			usuarioAccesoBean.addIdContratoToListaIdsContratos(contratoVO.getIdContrato());
			usuarioAccesoBean.addListaComboContratos(contratoVO.getIdContrato(), usuarioAccesoVO.getNumColegiado() + " , " + contratoVO.getVia());
		}
		usuarioAccesoBean.setDetalleUsuario(conversor.transform(usuarioAccesoVO, DetalleUsuarioBean.class));
		return usuarioAccesoBean;
	}

	@Override
	public ResultadoAccesoBean guardarDatosAccesoSession(UsuarioAccesoBean usuarioAcceso) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			servicioSesion.insert(ServletActionContext.getRequest().getSession(true).getId(), usuarioAcceso.getNumColegiado(),
					getIpConexion(), getIpFrontal(), new BigDecimal(usuarioAcceso.getIdUsuario()), usuarioAcceso.getDetalleUsuario().getApellidosNombre());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de la conexion para el idUsuario: " + usuarioAcceso.getIdUsuario() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de la conexión para el usuario." );
		}
		return resultado;
	}

	private String getIpFrontal() {
		String ipFrontal = "";
		try {
			int num = InetAddress.getLocalHost().toString().indexOf('/');
			if (num > 0) {
				ipFrontal = InetAddress.getLocalHost().toString().substring(num + 1);
			}
		} catch (UnknownHostException e1) {
			log.error("Error UnknownHostException: ", e1);
		}
		return ipFrontal;
	}

	private String getIpConexion() {
		String ipConexion = ServletActionContext.getRequest().getRemoteAddr();

		if (ServletActionContext.getRequest().getHeader("client-ip") != null) {
			ipConexion = ServletActionContext.getRequest().getHeader("client-ip"); 
		}
		return ipConexion;
	}

	@Override
	public ResultadoAccesoBean montarMenuRepository(UsuarioAccesoBean usuarioAcceso) {
		ResultadoAccesoBean resultado = new ResultadoAccesoBean(Boolean.FALSE);
		try {
			MenuRepository menuArbol=new MenuRepository();
			Map<?,?> application=ActionContext.getContext().getApplication();
			MenuRepository defaultRepository = (MenuRepository) application.get(MenuRepository.MENU_REPOSITORY_KEY);
			menuArbol.setDisplayers(defaultRepository.getDisplayers());
			MiMenuComponent mc = null;
			MiMenuComponent parentMenu = null;
			List<MenuAplicacionBean> listaMenuAplicaciones = generarMapaMenu(usuarioAcceso.getListaMenu());
			for(MenuAplicacionBean menuAplicacion : listaMenuAplicaciones) {
				mc = new MiMenuComponent();
				mc.setName(menuAplicacion.getCodigoAplicacion());
				mc.setTitle(menuAplicacion.getDescAplicacion());
				menuArbol.addMenu(mc);
				TreeMap<Long, List<MenuFuncionBean>> mapaMenuOrdenado = new TreeMap<>(menuAplicacion.getMapaMenuFuncion());
				for (Map.Entry<Long, List<MenuFuncionBean>> entry : mapaMenuOrdenado.entrySet()) {
					for (MenuFuncionBean menuFuncion : entry.getValue()) {
						mc = new MiMenuComponent();
						mc.setName(menuFuncion.getCodigoFuncion());
						mc.setTitle(menuFuncion.getDescFuncion());
						mc.setLocation(menuFuncion.getUrl());
						if (StringUtils.isNotBlank(menuFuncion.getCodigoFuncionPadre())) {
							parentMenu = (MiMenuComponent)menuArbol.getMenu(menuFuncion.getCodigoFuncionPadre());
						} else {
							parentMenu = (MiMenuComponent)menuArbol.getMenu(menuFuncion.getCodigoAplicacion());
						}
						mc.setParent(parentMenu);
						menuArbol.addMenu(mc);
					}
				}
			}
			resultado.setMenuArbol(menuArbol);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de montar el menu para la pantalla, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de montar el menú para la pantalla." );
		}
		return resultado;
	}

	private List<MenuAplicacionBean> generarMapaMenu(List<MenuFuncionBean> listaMenu) {
		List<MenuFuncionBean> listaAplicaciones = obtenerListaAplicacionesParaGenMapa(listaMenu);
		List<MenuAplicacionBean> listaMenuAplicacion = new ArrayList<>();
		for(MenuFuncionBean aplicacion : listaAplicaciones){
			MenuAplicacionBean menuAplicacion = new MenuAplicacionBean();
			menuAplicacion.setCodigoAplicacion(aplicacion.getCodigoAplicacion());
			menuAplicacion.setDescAplicacion(aplicacion.getDescAplicacion());
			for (MenuFuncionBean menu : listaMenu) {
				if (!menuAplicacion.getMapaMenuFuncion().containsKey(menu.getNivel())) {
					menuAplicacion.getMapaMenuFuncion().put(menu.getNivel(), new ArrayList<MenuFuncionBean>());
				}
				menuAplicacion.getMapaMenuFuncion().get(menu.getNivel()).add(menu);
			}
			listaMenuAplicacion.add(menuAplicacion);
		}
		return listaMenuAplicacion;
	}

	private List<MenuFuncionBean> obtenerListaAplicacionesParaGenMapa(List<MenuFuncionBean> listaMenu) {
		List<MenuFuncionBean> listaAplicaciones = new ArrayList<>();
		boolean existeAplicacion;
		if (listaMenu != null && !listaMenu.isEmpty()) {
			for (MenuFuncionBean menuAplicaciones : listaMenu) {
				existeAplicacion = Boolean.FALSE;
				if (!listaAplicaciones.isEmpty()) {
					for (MenuFuncionBean aplicacion : listaAplicaciones) {
						if (aplicacion.getCodigoAplicacion().equals(menuAplicaciones.getCodigoAplicacion())) {
							existeAplicacion = Boolean.TRUE;
							break;
						}
					}
				}
				if (!existeAplicacion) {
					listaAplicaciones.add(menuAplicaciones);
				}
			}
		}
		return listaAplicaciones;
	}

}