package administracion.acciones;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import trafico.datosSensibles.utiles.UtilesVistaDatosSensibles;
import trafico.utiles.UtilesVistaTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class RecargaCachesAction extends ActionBase implements
		ServletRequestAware {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ServicioRecargaCache servicioRecargaCache;

	@Autowired
	private ServicioDatosSensibles servicioDatosSensiblesImpl;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private static final ILoggerOegam log = LoggerOegam.getLogger(RecargaCachesAction.class);

	private static final String MENSAJE_ERROR_PERMISOS = "No tiene permisos para ejecutar esta acción";
	private static final String MENSAJE_COMBOS_OK = "Combos actualizados con éxito";
	private static final String MENSAJE_COMBOS_ALGUN_FALLO = "Fin de la recarga con errores";
	private static final String MENSAJE_DATOS_SENSIBLES_OK = "Datos sensibles actualizados con éxito";
	private static final String MENSAJE_DATOS_SENSIBLES_ALGUN_FALLO = "Error al recargar datos sensibles";

	private String bastidorCheck;
	private DatosSensiblesBastidorVO bastidorEnBBDD;
	private Boolean existeBastidorEnCache;

	private HttpServletRequest request;

	/**
	 * Método con el que comenzará las funcionalidades de recarga de combos,
	 * comprobando si tiene permisos y mostrando mensajes informativos acerca de
	 * la funcionalidad de recarga de combos.
	 */
	public String inicio() throws Throwable {
		log.debug("CachesAction inicio método Inicio()");

		// Comprueba si tiene permisos de administrador, si no los tiene muestra
		// mensaje de error
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError(MENSAJE_ERROR_PERMISOS);
			return ERROR;
		}

		log.debug("CachesAction fin método Inicio()");
		return SUCCESS;
	}

	/**
	 * 
	 * Se encarga de realizar la llamada a la recarga de combos, e informa del
	 * resultado de la misma.
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String refrescarCombos() throws Throwable {
		log.debug("CachesAction inicio método refrescarCombos()");

		if (log.isInfoEnabled()) {
			log.info("Acceso a refrescar combos desde la IP "
					+ request.getRemoteAddr() + ", con num contrato "
					+ utilesColegiado.getIdContratoSession()
					+ ", num colegiado "
					+ utilesColegiado.getNumColegiadoSession());
		}

		// Comprueba si tiene permisos de administrador, si no los tiene muestra
		// mensaje de error
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError(MENSAJE_ERROR_PERMISOS);
			return ERROR;
		}

		ResultBean resultBean = UtilesVistaTrafico.getInstance()
				.refrescarCombos();
		if (!resultBean.getError()) {
			addActionMessage(MENSAJE_COMBOS_OK);
			if (resultBean.getMensaje() != null
					&& !resultBean.getMensaje().isEmpty()) {
				addActionMessage(resultBean.getMensaje());
			}
		} else {
			addActionError(MENSAJE_COMBOS_ALGUN_FALLO);
			for (String errorMessage : resultBean.getListaMensajes()) {
				addActionError(errorMessage);
			}
		}

		log.debug("CachesAction fin método refrescarCombos()");
		return SUCCESS;
	}

	/**
	 * Se encarga de realizar la llamada a la recarga de datos sensibles, e
	 * informa del resultado.
	 * 
	 * @return
	 */
	public String refrescarGrupos() {
		log.debug("CachesAction inicio método refrescarGrupos()");

		if (log.isInfoEnabled()) {
			log.info("Acceso a refrescar grupos desde la IP "
					+ request.getRemoteAddr() + ", con num contrato "
					+ utilesColegiado.getIdContratoSession()
					+ ", num colegiado "
					+ utilesColegiado.getNumColegiadoSession());
		}

		// Comprueba si tiene permisos de administrador, si no los tiene muestra
		// mensaje de error
		if (!utilesColegiado.tienePermisoAdmin()) {
			addActionError(MENSAJE_ERROR_PERMISOS);
			return ERROR;
		}

		ResultBean resultBean = UtilesVistaDatosSensibles.getInstance()
				.recargarDatosSensibles();

		if (!resultBean.getError()) {
			addActionMessage(MENSAJE_DATOS_SENSIBLES_OK);
			if (resultBean.getMensaje() != null
					&& !resultBean.getMensaje().isEmpty()) {
				addActionMessage(resultBean.getMensaje());
			}
		} else {
			addActionError(MENSAJE_DATOS_SENSIBLES_ALGUN_FALLO);
			for (String errorMessage : resultBean.getListaMensajes()) {
				addActionError(errorMessage);
			}
		}

		log.debug("CachesAction fin método refrescarGrupos()");
		return SUCCESS;
	}

	public String peticionDatosSensibles() {
		servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
		addActionMessage("Creada peticion para refresco de cache.");
		return SUCCESS;
	}

	public String peticionCombos() {
		servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.COMBOS);
		addActionMessage("Creada peticion para refresco de cache.");
		return SUCCESS;
	}

	public String checkBastidor() {
		bastidorEnBBDD = servicioDatosSensiblesImpl.getBastidorVOPorBastidor(bastidorCheck);
		existeBastidorEnCache = servicioDatosSensiblesImpl.comprobarBastidorCache(bastidorCheck);
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getBastidorCheck() {
		return bastidorCheck;
	}

	public void setBastidorCheck(String bastidorCheck) {
		this.bastidorCheck = bastidorCheck;
	}

	public DatosSensiblesBastidorVO getBastidorEnBBDD() {
		return bastidorEnBBDD;
	}

	public void setBastidorEnBBDD(DatosSensiblesBastidorVO bastidorEnBBDD) {
		this.bastidorEnBBDD = bastidorEnBBDD;
	}

	public Boolean getExisteBastidorEnCache() {
		return existeBastidorEnCache;
	}

	public void setExisteBastidorEnCache(Boolean existeBastidorEnCache) {
		this.existeBastidorEnCache = existeBastidorEnCache;
	}

}