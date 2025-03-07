package administracion.acciones;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.oegam2.controller.security.authentication.OegamAuthenticationToken;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegamComun.acceso.model.bean.ResultadoAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioGestionAcceso;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import escrituras.beans.ResultBean;
import escrituras.beans.contratos.ContratoBean;
import escrituras.beans.contratos.UsuarioContratoBean;
import escrituras.modelo.ModeloContratoNuevo;
import general.acciones.ActionBase;
import net.sf.navigator.menu.MenuRepository;
import oegam.constantes.ConstantesSession;
import trafico.modelo.ModeloAccesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.Contrato;
import utilidades.web.GestorArbol;
import utilidades.web.Usuario;

public class CambioContratoAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(CambioContratoAction.class);

	private BigDecimal contratoSeleccionado;
	private ModeloAccesos modeloAccesos;
	private UsuarioDto usuarioNuevoDto;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ModeloContratoNuevo modeloContratoNuevo;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	ServicioGestionAcceso servicioGestionAcceso;

	public String execute() throws Exception{

		boolean habilitado = false;
		String gestionarUsuariosPorContrato=null;
		ResultBean deshabilitarActual;
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		try {
			ResultBean resultGestionContrato = new ResultBean(Boolean.FALSE);
			if ("SI".equals(gestorPropiedades.valorPropertie("nuevo.gestorAccesos"))) {
				UsuarioAccesoBean usuarioAccesoBean = (UsuarioAccesoBean) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO);
				if ("SI".equals(gestorPropiedades.valorPropertie("gestionar.usuarios.contrato"))) {
					resultGestionContrato = gestionarUsuariosContrato(idUsuario, usuarioAccesoBean.getDetalleUsuario().getCorreoElectronico(),
							usuarioAccesoBean.getDetalleUsuario().getApellidosNombre(), usuarioAccesoBean.getNif(), usuarioAccesoBean.getDetalleUsuario().getAnagrama());
				} else if (contratoSeleccionado != null) {
					ResultadoAccesoBean resultadoAcceso = servicioGestionAcceso.gestionarCambioContratoUsuario(utilesColegiado.getIdUsuarioSession(), getContratoSeleccionado() != null ? getContratoSeleccionado().longValue(): null);
					if (resultadoAcceso.getError()) {
						resultGestionContrato.setError(Boolean.TRUE);
						resultGestionContrato.setMensaje(resultadoAcceso.getMensaje());
					}
				}
				if (!resultGestionContrato.getError()) {
					ResultadoAccesoBean resultadoAcceso = servicioGestionAcceso.gestionAcceso(utilesColegiado.getNifUsuario(), getContratoSeleccionado() != null ? getContratoSeleccionado().longValue(): null);
					if (!resultadoAcceso.getError()) {
						UsuarioAccesoBean usuarioAcceso = resultadoAcceso.getUsuarioAcceso();
						resultadoAcceso = servicioGestionAcceso.gestionFavoritos(resultadoAcceso.getUsuarioAcceso());
						if (!resultadoAcceso.getError()) {
							usuarioAcceso.setListaFavoritos(resultadoAcceso.getListaFavoritos());
						}
						resultadoAcceso = servicioGestionAcceso.montarMenuRepository(usuarioAcceso);
						if (!resultadoAcceso.getError()) {
							MenuRepository menuArbol = resultadoAcceso.getMenuArbol();
							resultadoAcceso = servicioGestionAcceso.guardarDatosAccesoSession(usuarioAcceso);
							if (resultadoAcceso.getError()) {
								throw new Exception(resultadoAcceso.getMensaje());
							}
							Claves.setObjetoDeContextoSesion(Claves.CLAVE_ARBOL, menuArbol);
							Claves.setObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ACCESO, usuarioAcceso);
							getSession().put(ConstantesSession.RAZON_SOCIAL_CONTRATOP, usuarioAcceso.getRazonSocialContrato());
							getSession().put(ConstantesSession.CIFP,usuarioAcceso.getCifContrato());
							if (usuarioAcceso.getListaFavoritos() != null && !usuarioAcceso.getListaFavoritos().isEmpty()) {
								Claves.setObjetoDeContextoSesion("favoritos", usuarioAcceso.getListaFavoritos());
							}
							getSession().put(ConstantesSession.ADMINISTRADOR_COMO_EMPLEADO_COLEGIADO_TEMPORALMENTE, true);
							Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
							if (authentication instanceof OegamAuthenticationToken) {
								((OegamAuthenticationToken) authentication).setPrincipal(utilesColegiado.getIdUsuarioSessionBigDecimal());
								((OegamAuthenticationToken) authentication).setCambioContrato(true);
							}
						}
					} else {
						throw new Exception(resultadoAcceso.getMensaje());
					}
				} else {
					throw new Exception(resultGestionContrato.getMensaje());
				}
			} else {
				GestorArbol gestorArbol = (GestorArbol) Claves.getObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL);
				Usuario usuario=  gestorArbol.getUsuario();
				 gestionarUsuariosPorContrato = gestorPropiedades.valorPropertie("gestionar.usuarios.contrato");
				if (gestionarUsuariosPorContrato != null && "SI".equals(gestionarUsuariosPorContrato)){
					resultGestionContrato = gestionarUsuariosContrato(idUsuario, usuario.getCorreo_electronico(),
							usuario.getApellidos_nombre(), usuario.getNif(), usuario.getAnagrama());
					if (!resultGestionContrato.getError()) {
						boolean usuarioValido = getModeloAccesos().crearArbol(usuario.getNif(),getContratoSeleccionado());
						if (!usuarioValido) {
							throw new Exception("No se ha podido crear el árbol del nuevo contrato...");
						}
					}
				} else {
					// Obtener contrato y comprobar posibilidad de realizar el cambio de contrato
					ResultBean detalleContrato = modeloContratoNuevo.obtenerDetalleContrato(getContratoSeleccionado());
					if (detalleContrato.getError()) {
						throw new Exception(detalleContrato.getMensaje());
					}
					// Deshabilita al usuario de su contrato actual
					deshabilitarActual = modeloContratoNuevo.deshabilitarUsuario(idUsuario);
					if (deshabilitarActual.getError()) {
						throw new Exception(deshabilitarActual.getMensaje());
					}
					// Comprueba si el usuario que se intenta dar de alta en el nuevo contrato, estuvo alguna vez dado de alta en ese contrato.
					ContratoBean contratoBean = (ContratoBean) detalleContrato.getAttachment("beanPantalla");
					for (UsuarioContratoBean usuarioContrato : contratoBean.getListaUsuarios()) {
						if (usuarioContrato.getNif().equals(usuario.getNif())) {
							// Lo estuvo. Lo habilita
							ResultBean habilitarActual = modeloContratoNuevo.habilitarUsuario(usuarioContrato.getIdUsuario());
							if (habilitarActual.getError()) {
								throw new Exception(habilitarActual.getMensaje());
							}
							habilitado = true;
							break;
						}
					}
					// No lo estuvo. Lo da de alta
					if (!habilitado) {
						UsuarioContratoBean usuarioContratoBean = new UsuarioContratoBean();
						usuarioContratoBean.setAnagrama(usuario.getAnagrama());
						usuarioContratoBean.setApellidosNombre(usuario.getApellidos_nombre());
						usuarioContratoBean.setCorreoElectronico(usuario.getCorreo_electronico());
						usuarioContratoBean.setEstadoUsuario(Estado.Habilitado.getValorEnum().toString());
						usuarioContratoBean.setIdGrupoUsuario("-1");
						usuarioContratoBean.setNif(usuario.getNif());
						usuarioContratoBean.setNumColegiado(contratoBean.getDatosColegiado().getNumColegiado());
						ResultBean altaUsuarioNuevo = modeloContratoNuevo.guardarUsuario(usuarioContratoBean);
						if (altaUsuarioNuevo.getError()) {
							throw new Exception(altaUsuarioNuevo.getMensaje());
						}
					}
					boolean usuarioValido = getModeloAccesos().crearArbol(usuario.getNif(),getContratoSeleccionado());
					if (!usuarioValido) {
						throw new Exception("No se ha podido crear el árbol del nuevo contrato...");
					}
				}
				// Si no hay fallo, carga en sesión el árbol del nuevo contrato:
				getSession().put(ConstantesSession.ADMINISTRADOR_COMO_EMPLEADO_COLEGIADO_TEMPORALMENTE, true);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication instanceof OegamAuthenticationToken) {
					((OegamAuthenticationToken) authentication).setPrincipal(utilesColegiado.getIdUsuarioSessionBigDecimal());
					((OegamAuthenticationToken) authentication).setCambioContrato(true);
				}
				Contrato contrato= gestorArbol.getContratos().get(0);
				getSession().put(ConstantesSession.RAZON_SOCIAL_CONTRATOP, contrato.getRazon_social());
				getSession().put(ConstantesSession.CIFP, contrato.getCif());
			}
		} catch (Throwable ex) {
			log.error(ex);
			if (gestionarUsuariosPorContrato != null && "SI".equals(gestionarUsuariosPorContrato)){
				// Si hay algún fallo vuelve a habilitar al usuario en el contrato original
				ResultBean habilitarUsuario = servicioUsuario.habilitarUsuario(idUsuario,true);
				ResultBean eliminarUsuario = servicioContrato.eliminarUsuarioContrato(new String[] {idUsuario.toString()},getContratoSeleccionado() != null ? getContratoSeleccionado().longValue(): null);
				// Si se ha producido algún error se envía la excepción
				if (habilitarUsuario.getError() || eliminarUsuario.getError()) {
					throw new Exception(ex);
				} else{
					return SUCCESS;
				}
			} else {
				// Si hay algún fallo vuelve a habilitar al usuario en el contrato original
				modeloContratoNuevo.habilitarUsuario(idUsuario);
				throw new Exception(ex);
			}
		}
		return SUCCESS;
	}

	private ResultBean gestionarUsuariosContrato(BigDecimal idUsuario, String correoElectronico, String apeNombre,
			String nif, String anagrama) {
		ResultBean resultGestion = new ResultBean(Boolean.FALSE);
		try {
			ContratoDto contratoDto = servicioContrato.getContratoDto(getContratoSeleccionado());
			// Si es la primera vez y existe alguno con 6 el parametro es false y si no es true
			List<ContratoUsuarioVO> listaContratosUsuario = servicioUsuario.getContratosAnterioresPorUsuario(ServletActionContext.getRequest().getSession().getId());
			if (listaContratosUsuario != null && !listaContratosUsuario.isEmpty()) {
				resultGestion = servicioUsuario.deshabilitarUsuario(idUsuario, new Date(), false);
			} else {
				resultGestion = servicioUsuario.deshabilitarUsuario(idUsuario, new Date(), true);
			}
			if (resultGestion.getError()) {
				return resultGestion;
			}
			// Usuarios del contrato de la gestoría
			List<UsuarioDto> ListaUsuariosContrato = servicioUsuario.getUsuarioPorNumColegiado(contratoDto.getColegiadoDto().getNumColegiado()) ;
			Boolean habilitado = Boolean.FALSE;
			for (UsuarioDto usuarioCont:ListaUsuariosContrato) {
				// Lo estuvo. Lo habilita
				if (usuarioCont.getNif().equals(utilesColegiado.getNifUsuario())) {
					ResultBean habilitarActual = servicioUsuario.habilitarUsuario(idUsuario, false);
					// Hay que asociar el contrato al usuario
					ResultBean asociacionUsuario=servicioContrato.asociarUsuarioContrato(new String[] {String.valueOf(utilesColegiado.getIdUsuarioSession())},
										getContratoSeleccionado().longValue());
					if (habilitarActual.getError() || asociacionUsuario.getError()) {
						throw new Exception(habilitarActual.getMensaje());
					}
					habilitado = Boolean.TRUE;
					break;
				}
			}
			// No lo estuvo. Lo da de alta
			if (!habilitado) {
				usuarioNuevoDto = new UsuarioDto();
				usuarioNuevoDto.setCorreoElectronico(correoElectronico);
				usuarioNuevoDto.setEstadoUsuario(Estado.Habilitado.getValorEnum().toString());
				usuarioNuevoDto.setApellidosNombre(apeNombre);
				usuarioNuevoDto.setIdGrupo("-1");
				usuarioNuevoDto.setNif(nif);
				usuarioNuevoDto.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
				usuarioNuevoDto.setNumColegiadoNacional(contratoDto.getColegiadoDto().getNumColegiadoNacional());
				usuarioNuevoDto.setAnagrama(anagrama);
				resultGestion = servicioUsuario.guardarUsuario(usuarioNuevoDto);
				if (!resultGestion.getError()) {
					resultGestion = servicioContrato.asociarUsuarioContrato(new String[] {String.valueOf(resultGestion.getAttachment("idUsuario" ))}, getContratoSeleccionado().longValue());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar los contratos del usuario, error: ",e);
			resultGestion.setError(Boolean.TRUE);
			resultGestion.setMensaje("Ha sucedido un error a la hora de gestionar los contratos del usuario.");
		}
		return resultGestion;
	}

	public BigDecimal getContratoSeleccionado() {
		return contratoSeleccionado;
	}

	public void setContratoSeleccionado(BigDecimal contratoSeleccionado) {
		this.contratoSeleccionado = contratoSeleccionado;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloAccesos getModeloAccesos() {
		if (modeloAccesos == null) {
			modeloAccesos = new ModeloAccesos();
		}
		return modeloAccesos;
	}

	public void setModeloAccesos(ModeloAccesos modeloAccesos) {
		this.modeloAccesos = modeloAccesos;
	}

}