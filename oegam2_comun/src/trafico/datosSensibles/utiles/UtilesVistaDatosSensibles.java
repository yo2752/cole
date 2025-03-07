package trafico.datosSensibles.utiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.oegam2comun.grupo.model.service.ServicioGrupo;
import org.gestoresmadrid.oegam2comun.model.service.ServicioIpNoValida;
import org.gestoresmadrid.oegam2comun.view.dto.GruposDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import trafico.datosSensibles.beans.daos.DatosSensiblesDAOImpl;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaDatosSensibles {
	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaDatosSensibles.class);

	private Map<String, String> grupos;
	private List<GruposDto> gruposUsuarios;

	private List<String> matriculasSensibles;
	private List<String> bastidoresSensibles;
	private List<String> nifsSensibles;
	private Map<String, String> ipPermitidas;

	@Autowired
	private ServicioIpNoValida servicioIpNoValida;

	@Autowired
	private ServicioGrupo servicioGrupo;

	private static UtilesVistaDatosSensibles utilesVistaDatosSensibles;

	private UtilesVistaDatosSensibles() {
		super();
	}

	public static UtilesVistaDatosSensibles getInstance() {
		if (utilesVistaDatosSensibles == null) {
			utilesVistaDatosSensibles = new UtilesVistaDatosSensibles();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaDatosSensibles);
		}
		return utilesVistaDatosSensibles;
	}

	/**
	 * Autor: Julio Molina
	 * Fecha: 21/12/2012
	 * Método para que se devuelva el nombre del Grupo a partir de su Id y se muestre en la JSP
	 */
	public String traducirGrupo(String idGrupo) {
		if (idGrupo != null && !idGrupo.trim().isEmpty()) {
			if (gruposUsuarios == null) {
				recargarGrupos();
			}
			if (gruposUsuarios != null) {
				for (GruposDto g : gruposUsuarios) {
					if (g.getIdGrupo().equals(idGrupo)) {
						return g.getDescGrupo();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Autor: Julio Molina
	 * Fecha: 21/12/2012
	 * Método para que coja de la tabla Grupos los Grupos con sus Correos para que se puedan enviar
	 */
	public Map<String, String> direccionesGrupos() {
		if (grupos == null) {
			recargarGrupos();
		}
		return grupos;
	}

	/**
	 * Autor: Julio Molina
	 * Fecha: 27/12/2012
	 * Método para que liste todos los grupos que existen y se le puedan asiganar a un Usuario
	 */
	public List<GruposDto> listarGrupos() {
		if (gruposUsuarios == null) {
			recargarGrupos();
		}
		return gruposUsuarios;
	}

	public List<String> getBastidoresSensibles() {
		if (bastidoresSensibles == null) {
			recargaBastidores();
		}
		return bastidoresSensibles;
	}

	public List<String> getMatriculasSensibles() {
		if (matriculasSensibles == null) {
			recargaMatriculas();
		}
		return matriculasSensibles;
	}

	public List<String> getNifsSensibles() {
		if (nifsSensibles == null) {
			recargaNifs();
		}
		return nifsSensibles;
	}

	public Map<String, String> getIpPermitidas() {
		if (ipPermitidas == null) {
			boolean recargaIp = recargarIP();
		}
		return ipPermitidas;
	}

	/**
	 * Realiza la carga del listado de grupos y de su mapa
	 */
	public synchronized boolean recargarGrupos() {
		log.debug("Recargando informacion de los grupos cacheados");
		List<GruposDto> list = servicioGrupo.getGrupos();
		if (list != null) {
			Map<String, String> map = new HashMap<>();

			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getIdGrupo(), list.get(i).getCorreoElectronico());
			}

			if (map != null) {
				grupos = map;
				gruposUsuarios = list;
				return true;
			}
		}
		return false;
	}

	/**
	 * Realiza la carga del listado de matrículas
	 */
	public synchronized boolean recargaBastidores() {
		log.debug("Recargando informacion de los bastidores cacheados");
		DatosSensiblesDAOImpl datosSensiblesDAOImpl = new DatosSensiblesDAOImpl();
		List<String> tempBasti = datosSensiblesDAOImpl.getListadoBastidoresSensibles();
		if (tempBasti != null) {
			List<String> aux = bastidoresSensibles;
			bastidoresSensibles = tempBasti;
			// Comprobar si no ha sido inicializado previamente
			if (aux != null) {
				aux.clear();
			}
			return true;
		}
		return false;
	}

	/**
	 * Realiza la carga del listado de matrículas
	 */
	public synchronized boolean recargaMatriculas() {
		log.debug("Recargando informacion de las matriculas cacheadas");
		DatosSensiblesDAOImpl datosSensiblesDAOImpl = new DatosSensiblesDAOImpl();
		List<String> tempMatr = datosSensiblesDAOImpl.getListadoMatriculasSensibles();
		if (tempMatr != null) {
			List<String> aux = matriculasSensibles;
			matriculasSensibles = tempMatr;
			// Comprobar si no ha sido inicializado previamente
			if (aux != null) {
				aux.clear();
			}
			return true;
		}
		return false;
	}

	/**
	 * Realiza la carga del listado de matrículas
	 */
	public synchronized boolean recargaNifs() {
		log.debug("Recargando informacion de los NIFs cacheados");
		DatosSensiblesDAOImpl datosSensiblesDAOImpl = new DatosSensiblesDAOImpl();
		List<String> tempNif = datosSensiblesDAOImpl.getListadoNifsSensibles();
		if (tempNif != null) {
			List<String> aux = nifsSensibles;
			nifsSensibles = tempNif;
			// Comprobar si no ha sido inicializado previamente
			if (aux != null) {
				aux.clear();
			}
			return true;
		}
		return false;
	}

	public ResultBean recargarDatosSensibles() {
		ResultBean resultBean = new ResultBean();
		StringBuffer sb = new StringBuffer();
		// Recarga de grupos
		try {
			if (recargarGrupos()) {
				sb.append(", grupos");
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("Error al recargar grupos");
			}
		} catch (Exception e) {
			log.error("Error inesperado recargando grupos", e);
			resultBean.setError(true);
			resultBean.getListaMensajes().add("Error al recargar grupos");
		}
		// Recarga de bastidores
		try {
			if (recargaBastidores()) {
				sb.append(", bastidores");
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("Error al recargar bastidores");
			}
		} catch (Exception e) {
			log.error("Error inesperado recargando bastidores", e);
			resultBean.setError(true);
			resultBean.getListaMensajes().add("Error al recargar bastidores");
		}
		// Recarga de matrículas
		try {
			if (recargaMatriculas()) {
				sb.append(", matriculas");
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("Error al recargar matriculas");
			}
		} catch (Exception e) {
			log.error("Error inesperado matriculas bastidores", e);
			resultBean.setError(true);
			resultBean.getListaMensajes().add("Error al recargar matriculas");
		}
		// Recarga de NIFs
		try {
			if (recargaNifs()) {
				sb.append(", nifs");
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("Error al recargar NIFs");
			}
		} catch (Exception e) {
			log.error("Error inesperado nifs bastidores", e);
			resultBean.setError(true);
			resultBean.getListaMensajes().add("Error al recargar NIFs");
		}
		try {
			if (recargarIP()) {
				sb.append(", ip No Permitidas");
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("Error al recargar las IP no permitidas");
			}
		} catch (Exception e) {
			log.error("Error inesperado ip permitidas", e);
			resultBean.setError(true);
			resultBean.getListaMensajes().add("Error al recargar las IP permitidas");
		}

		if (sb.length() > 0) {
			if (resultBean.getError()) {
				resultBean.getListaMensajes().add("Datos sensibles recargados con éxito: " + sb.substring(2));
			} else {
				resultBean.setMensaje("Datos sensibles recargados con éxito: " + sb.substring(2));
			}
		}
		return resultBean;
	}

	private boolean recargarIP() {
		ipPermitidas = servicioIpNoValida.obtenerIP();
		return ipPermitidas != null;
	}
}