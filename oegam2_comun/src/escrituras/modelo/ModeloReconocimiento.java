package escrituras.modelo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.SexoPersona;
import general.utiles.enumerados.EstadoReconocimientoMedico;
import hibernate.dao.general.ReconocimientoMedicoDAO;
import hibernate.entities.general.ReconocimientoMedico;
import hibernate.entities.general.TipoTramiteRenovacion;
import hibernate.entities.trafico.filters.ReconocimientoMedicoFilter;
import trafico.utiles.constantes.ConstantesReconocimiento;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloReconocimiento extends ModeloBase {
	public static final String FILTRO = "filtro";
	public static final String INICIALIZACIONES = "inicializaciones";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloReconocimiento.class);

	@Autowired
	UtilesFecha utilesFecha;

	public ModeloReconocimiento() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Metodo que devuelve la lista de Reconocimientos médicos
	 * Para establecer el filtro y las inicializaciones, se usa el setParametrosBusqueda
	 * 
	 * Map<String, String> filtro = new HashMap<String, String>();
	 * filtro.put("idReconocimiento", "456");
	 * filtro.put("persona.nombre", "pepe");
	 * 
	 * String[] initialized = {"colegiado", "clinica"};
	 * 
	 * setParametrosBusqueda(new HashMap<String, Object>());
	 * getParametrosBusqueda().put(ModeloReconocimiento.FILTRO, filter);
	 * getParametrosBusqueda().put(ModeloReconocimiento.INICIALIZACIONES, initialized);
	 * 
	 * @return
	 */
	public List<ReconocimientoMedico> list() {
		ReconocimientoMedicoFilter filter = new ReconocimientoMedicoFilter();
		String[] initialized = null;
		if (getParametrosBusqueda() != null) {
			try {
				@SuppressWarnings("unchecked")
				Map<String, String> filtro = (HashMap<String, String>) getParametrosBusqueda().get(FILTRO);
				if (filtro != null) {
					for (String key: filtro.keySet()) {
						try {
							BeanUtils.setProperty(filter, key, filtro.get(key));
						} catch (Exception e) {
							log.error("Error al establecer propierdad "+ key + " del filtro. ", e);
						}
					}
				}
				initialized = (String[]) getParametrosBusqueda().get(INICIALIZACIONES);
			} catch(Exception e) {
				log.error("Error al obtener lista de reconocimientos medicos. ", e);
			}
		}
		ReconocimientoMedicoDAO dao = new ReconocimientoMedicoDAO();
		return dao.list(filter, initialized);
	}

	/**
	 * Devuelve el listado de entidades ReconocimientoMedico que cumplen el filtro
	 * 
	 * @param filter - Los parametros que vengan informados, se usan en el Criteria
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public List<ReconocimientoMedico> list(ReconocimientoMedicoFilter filter, String... initialized) {
		ReconocimientoMedicoDAO dao = new ReconocimientoMedicoDAO();
		return dao.list(filter, initialized);
	}

	/**
	 * Devuelve la entidad ReconocimientoMedico por identificador
	 * 
	 * @param identificador - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazzyExceptions
	 * @return
	 */
	public ReconocimientoMedico get(Long identificador, String... initialized){
		ReconocimientoMedicoDAO dao = new ReconocimientoMedicoDAO();
		return dao.get(identificador, initialized);
	}

	/**
	 * Guarda en BBDD el objeto ReconocimientoMedico;
	 * 
	 * @param reconocimientoMedico
	 * @return 
	 */
	public ResultBean saveOrUpdate(ReconocimientoMedico reconocimientoMedico){
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);

		ReconocimientoMedicoDAO dao = new ReconocimientoMedicoDAO();
		if ( reconocimientoMedico.getIdReconocimiento()==null ) {
			reconocimientoMedico.setFechaAlta(Calendar.getInstance().getTime());
			reconocimientoMedico.setEstado(new BigDecimal(EstadoReconocimientoMedico.Iniciado.getValorEnum()));
		}
		reconocimientoMedico.setFechaUltModif(Calendar.getInstance().getTime());
		try {
			dao.saveOrUpdate(reconocimientoMedico);
		} catch (RuntimeException re) {
			resultado.setMensaje(re.getLocalizedMessage());
			resultado.setError(Boolean.TRUE);
		}

		return resultado;
	}

	/**
	 * Borra de BBDD el objeto ReconocimientoMedico pasado
	 * 
	 * @param reconocimientoMedico
	 */
	public ResultBean remove(ReconocimientoMedico reconocimientoMedico) {
		ResultBean resultado = new ResultBean();
		resultado.setError(Boolean.FALSE);
		try {
			ReconocimientoMedicoDAO dao = new ReconocimientoMedicoDAO();
			dao.remove(reconocimientoMedico);
		} catch (RuntimeException re) {
			resultado.setMensaje(re.getLocalizedMessage());
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	/**
	 * Borra de BBDD el objeto ReconocimientoMedico con ID la pasada por parametro
	 * @param identificador
	 */
	public ResultBean remove(Long identificador) {
		return this.remove(this.get(identificador));
	}

	public ResultBean validarDatosAlta(ReconocimientoMedico reconocimientoMedico) throws OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(false);

		if (reconocimientoMedico != null
				&& reconocimientoMedico.getFechaReconocimiento() == null){
			resultado.setMensaje(ConstantesReconocimiento.ERROR_FECHARECONOCIMIENTO);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& (reconocimientoMedico.getTipoTramiteRenovacion() == null ||
						reconocimientoMedico.getTipoTramiteRenovacion().getIdTipoTramiteRenovacion() == null ||
						reconocimientoMedico.getTipoTramiteRenovacion().getIdTipoTramiteRenovacion().trim().isEmpty() ||
						reconocimientoMedico.getTipoTramiteRenovacion().getIdTipoTramiteRenovacion().trim().equals("-1") )){
			resultado.setMensaje(ConstantesReconocimiento.ERROR_TIPORECONOCIMIENTO);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& (reconocimientoMedico.getPersona().getId().getNif() == null || 
						reconocimientoMedico.getPersona().getId().getNif().trim().equals(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_NIF);
			resultado.setError(true);
		}

		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& (reconocimientoMedico.getPersona().getApellido1RazonSocial() == null ||
						reconocimientoMedico.getPersona().getApellido1RazonSocial().trim().equals(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_PRIMERAPELLIDO);
			resultado.setError(true);
		}

		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& (reconocimientoMedico.getPersona().getSexo() != null && !reconocimientoMedico
						.getPersona().getSexo()
						.equalsIgnoreCase(SexoPersona.Juridica.getValorEnum()))) {
			if (reconocimientoMedico != null
					&& reconocimientoMedico.getPersona() != null
					&& (reconocimientoMedico.getPersona().getNombre() == null 
							|| reconocimientoMedico.getPersona().getNombre().trim().equals(""))) {
				resultado.setMensaje(ConstantesReconocimiento.ERROR_NOMBRE);
				resultado.setError(true);
			}
		}

		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& reconocimientoMedico.getPersona().getPersonaDireccions() != null
				&& reconocimientoMedico.getPersona().getDireccionActual() != null
				&& reconocimientoMedico.getPersona().getDireccionActual().getMunicipio() != null
				&& reconocimientoMedico.getPersona().getDireccionActual().getMunicipio().getProvincia() != null
				&& (reconocimientoMedico.getPersona().getDireccionActual().getMunicipio().getProvincia().getIdProvincia() == null
						|| reconocimientoMedico.getPersona().getDireccionActual().getMunicipio().getProvincia()
								.getIdProvincia().trim().equalsIgnoreCase("-1") || reconocimientoMedico
						.getPersona().getDireccionActual()
						.getMunicipio().getProvincia().getIdProvincia().trim()
						.equalsIgnoreCase(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_PROVINCIA);
			resultado.setError(true);
		}

		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& reconocimientoMedico.getPersona().getDireccionActual() != null
				&& (reconocimientoMedico.getPersona().getDireccionActual().getMunicipio().getId().getIdMunicipio() == null 
				||	reconocimientoMedico.getPersona().getDireccionActual().getMunicipio().getId().getIdMunicipio().trim().equals(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_MUNICIPIO);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& reconocimientoMedico.getPersona().getDireccionActual() != null
				&& (reconocimientoMedico.getPersona().getDireccionActual().getCodPostal() == null 
						|| reconocimientoMedico.getPersona().getDireccionActual().getCodPostal().trim().equals(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_CODPOSTAL);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& reconocimientoMedico.getPersona().getDireccionActual() != null
				&& (reconocimientoMedico.getPersona().getDireccionActual().getIdTipoVia() == null || reconocimientoMedico
						.getPersona().getDireccionActual()
						.getIdTipoVia().trim().equals(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_TIPOVIA);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& reconocimientoMedico.getPersona().getDireccionActual() != null
				&& (reconocimientoMedico.getPersona().getDireccionActual()
						.getNombreVia() == null || reconocimientoMedico
						.getPersona().getDireccionActual().getNombreVia().trim()
						.equals(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_NOMBREVIA);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& reconocimientoMedico.getPersona().getDireccionActual()!= null
				&& (reconocimientoMedico.getPersona().getDireccionActual()
						.getNumero() == null || reconocimientoMedico
						.getPersona().getDireccionActual().getNumero().trim()
						.equals(""))) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_NUMEROVIA);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& reconocimientoMedico.getPersona().getFechaCaducidadCarnet() == null) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_FECHACADUCIDAD);
			resultado.setError(true);
		}
		if (reconocimientoMedico != null
				&& reconocimientoMedico.getPersona() != null
				&& (reconocimientoMedico.getPersona().getTelefonos() == null || reconocimientoMedico.getPersona().getTelefonos().trim().isEmpty())) {
			resultado.setMensaje(ConstantesReconocimiento.ERROR_TELEFONO);
			resultado.setError(true);
		}

		return resultado;
	}

	/**
	 * Devuelve el listado de entidades TipoTramiteRenovacion
	 * 
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazyExceptions
	 * @return
	 */
	public List<TipoTramiteRenovacion> listTipoTramiteRenovacion(String... initialized) {
		ReconocimientoMedicoDAO dao = new ReconocimientoMedicoDAO();
		return dao.listTipoTramiteRenovacion(initialized);
	}

	/**
	 * Devuelve la entidad TipoTramiteRenovacion por identificador
	 * 
	 * @param identificador - identificador
	 * @param initialized - Conjunto de parametros que queremos cargar para evitar LazyExceptions
	 * @return
	 */
	public TipoTramiteRenovacion getTipoTramiteRenovacion(String identificador, String... initialized) {
		ReconocimientoMedicoDAO dao = new ReconocimientoMedicoDAO();
		return dao.getTipoTramiteRenovacion(identificador, initialized);
	}

	/**
	 * Calcula el importe del reconocimiento según el tipo del mismo y la fecha (laborable o fin de semana/festivo)
	 * @param tipo
	 * @param fecha
	 * @return
	 */
	public BigDecimal calcularImporte(String tipo, Fecha fecha){
		if ( fecha != null && !fecha.isfechaNula() && tipo != null && !tipo.trim().isEmpty()) {
			TipoTramiteRenovacion tipoTramiteRenovacion = getTipoTramiteRenovacion(tipo);
			if (tipoTramiteRenovacion != null) {
				try {
					if (utilesFecha.esFechaLaborableConsiderandoFestivos(fecha)) {
						return tipoTramiteRenovacion.getImporteLaborable();
					} else {
						return tipoTramiteRenovacion.getImporteNoLaborable();
					}
				} catch (OegamExcepcion e) {}
			}
		}
		return null;
	}

	@Override
	@Deprecated
	/**
	 * Utilizar el método list
	 */
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

}