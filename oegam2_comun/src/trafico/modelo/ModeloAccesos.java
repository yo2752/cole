package trafico.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.ContratoAplicacionVO;
import org.gestoresmadrid.core.general.model.vo.FuncionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.favoritos.model.service.ServicioFavoritos;
import org.gestoresmadrid.oegam2comun.model.service.ServicioAplicacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegamComun.acceso.model.service.ServicioAccesos;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.funcion.service.ServicioComunFuncion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import general.modelo.ModeloGenerico;
import oegam.constantes.ValoresSchemas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.Aplicacion;
import utilidades.web.Contrato;
import utilidades.web.GestorArbol;
import utilidades.web.Menu;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;
import utilidades.web.Usuario;

public class ModeloAccesos extends ModeloGenerico {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloAccesos.class);

	@Autowired
	private  ServicioFavoritos servicioFavoritos;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioComunContrato servicioComunContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioAplicacion servicioAplicacion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	private ServicioComunFuncion servicioComunFuncion;

	@Autowired
	private ServicioAccesos servicioAccesos;

	@Autowired
	private Conversor conversor;

	public ModeloAccesos() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public boolean crearArbol(String strNif, BigDecimal idContratoSeleccionado) throws Throwable{

		log.info("Inicio crearArbol() en Claves.java");
		GestorArbol ga =null;
		List<UsuarioVO> listaUsuarios =null;
		List<Contrato> contratos=null;

		try {
			listaUsuarios = servicioUsuario.getUsuarioPorNif(strNif);

			String gestionarUsuariosPorContrato = gestorPropiedades.valorPropertie("gestionar.usuarios.contrato");

			if (listaUsuarios != null && !listaUsuarios.isEmpty()) {

				Usuario usuarioAcceso = conversor.transform(listaUsuarios.get(0), Usuario.class);

				listaUsuarios.get(0).setUltimaConexion(new Date());

				servicioUsuario.guardar(listaUsuarios.get(0));

				// PROPERTIE PARA CARGAR LOS CONTRATOS
				if (gestionarUsuariosPorContrato != null && "SI".equals(gestionarUsuariosPorContrato)){
					contratos = buscarContratosUsuario(usuarioAcceso.getId_usuario());
					if (contratos == null || contratos.isEmpty()) {
						contratos = buscarContratos(usuarioAcceso.getNum_colegiado());
					}
				} else {
					contratos = buscarContratos(usuarioAcceso.getNum_colegiado());
				}

				BigDecimal idContrato = getContratoId(contratos,idContratoSeleccionado);

				ga = new GestorArbol(contratos, idContrato);

				List<Menu> listaMenus = buscarMenus(ga.getIdContrato(), usuarioAcceso.getId_usuario());
				List<Aplicacion> aplicaciones = buscarAplicaciones();
				ga.getArbol(usuarioAcceso, listaMenus, aplicaciones);

				Claves.setObjetoDeContextoSesion(Claves.CLAVE_GESTOR_ARBOL, ga);
				recuperarFavoritos(ga);
				log.info("Fin crearArbol() en Claves.java");
				return true;
			} else {
				return false;
			}
		} catch (OegamExcepcion e) {
			//Mantis 12974 - ASG
			if (e.getMensajeError1().contains("id del contrato")){
				throw new OegamExcepcion("Contrato deshabilitado");
			}else{
				throw new OegamExcepcion(EnumError.error_00054,e.getMessage());
			}
		} catch (IndexOutOfBoundsException ie){
			//Mantis 12974 - ASG
			throw new OegamExcepcion("Usuario deshabilitado");
		}
	}

	/**
	 * Mantis 17350. David Sierra. Se carga la lista de favoritos del usuario en el menú
	 * @param ga
	 */
	private void recuperarFavoritos(GestorArbol ga) {
		if (ga != null && ga.get_listaMenus() != null) {
			List<Menu> favoritos = new ArrayList<Menu>();
			List<FuncionVO> listaFavoritos;

			listaFavoritos = servicioFavoritos.getFuncionCompleta(utilesColegiado.getIdContratoSessionBigDecimal());

			// Mantis 17350 Se comprueba si se han recuperado favoritos para cargarlos en el menú
			if (!listaFavoritos.isEmpty()) {
				List<Menu> menus = ga.get_listaMenus();
				for (Menu menu : menus) {
					for (FuncionVO funcion : listaFavoritos) {
						if (funcion.getId().getCodigoFuncion().equals(menu.getCodigo_funcion()) && funcion.getId().getCodigoAplicacion().equals(menu.getCodigo_aplicacion())) {
							favoritos.add(menu);
						}
					}
				}
			}
			// Mantis 17350. Agregamos en sesion la lista de favoritos
			Claves.setObjetoDeContextoSesion("favoritos", favoritos);
		}
	}

	private List<Contrato> buscarContratos(String numColegiado){
		List<ContratoVO> cotratosVO = servicioComunContrato.getContratosPorColegiado(numColegiado, new BigDecimal(EstadoContrato.Habilitado.getValorEnum()));
		return conversor.transform(cotratosVO, Contrato.class);
	}

	private List<Contrato> buscarContratosUsuario(BigDecimal idUsuario) throws OegamExcepcion{
		return servicioContrato.getContratosPorUsuario(idUsuario);
	}

	private List<Aplicacion> buscarAplicaciones() throws OegamExcepcion{
		List<AplicacionDto> aplicaciones = servicioAplicacion.getAplicaciones();
		return conversor.transform(aplicaciones, Aplicacion.class);
	}

	public boolean crearArbol(String strNif) throws Throwable{
		return crearArbol(strNif,null);
	}

	protected List<Menu> buscarMenus(BigDecimal idContrato, BigDecimal idUsuario) throws OegamExcepcion {
		List<Menu> listaMenus = new ArrayList<Menu>();

		List<ContratoAplicacionVO> listaAplicacionesContrato = servicioComunFuncion.obtenerListadoAplicacionesContrato(idContrato.longValue());
		if (listaAplicacionesContrato != null && !listaAplicacionesContrato.isEmpty()) {
			List<UsuarioFuncionSinAccesoVO> listaFuncionesSinAcceso = servicioAccesos.obtenerListadoFuncionSinAcceso(idContrato.longValue(), idUsuario.longValue());
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
							listaMenus.add(conversor.transform(funcionBBDD, Menu.class));
						}
					}
				}
			}
		}

		return listaMenus;
	}

	protected BigDecimal getContratoId(List<Contrato> contratos, BigDecimal idContratoSeleccionado) {
		BigDecimal idContrato = null;

		if (null != contratos && !contratos.isEmpty()) {
			if (idContratoSeleccionado == null) {// Cuando no hay contrato seleccionado recupera el único contrato de la lista.
				idContrato = (contratos.get(0)).getId_contrato();
			} else {
				idContrato = idContratoSeleccionado;
			}
		}

		return idContrato;
	}

	public GestorArbol crearArbolParaIntegracionSiga(BigDecimal idUsuario, BigDecimal idContratoSeleccionado) throws Throwable {
		log.info("Inicio crearArbol() en Claves.java");
		GestorArbol ga = null;
		UsuarioVO usuarioVO = null;

		try {
			usuarioVO = servicioUsuario.getUsuario(idUsuario);

			if (usuarioVO != null) {
				Usuario usuarioAcceso = conversor.transform(usuarioVO, Usuario.class);

				List<Contrato> contratos = buscarContratos(usuarioAcceso.getNum_colegiado());
				BigDecimal idContrato = getContratoId(contratos, idContratoSeleccionado);
				ga = new GestorArbol(contratos, idContrato);
				List<Menu> listaMenus = buscarMenus(ga.getIdContrato(), usuarioAcceso.getId_usuario());
				ga.set_listaMenus(listaMenus);

				log.info("Fin crearArbol() en Claves.java");
				return ga;
			} else {
				return null;
			}
		} catch (OegamExcepcion e) {
			throw new OegamExcepcion(EnumError.error_00054, e.getMessage());
		}
	}
}