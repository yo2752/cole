package org.gestoresmadrid.oegam2.datosSensibles.controller.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.BastidorErroneoBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesAgrupados;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.DatosSensiblesBastidorDto;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ResultadoDatosSensibles;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import trafico.datosSensibles.utiles.enumerados.TiposDatosSensibles;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class DatosSensiblesAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1L;

	private static ILoggerOegam log = LoggerOegam.getLogger(DatosSensiblesAction.class);

	@Resource
	private ModelPagination modeloDatosSensiblesPaginated;

	@Autowired
	private ServicioDatosSensibles servicioDatosSensiblesImpl;

	@Autowired
	private ServicioRecargaCache servicioRecargaCache;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	private DatosSensiblesBean datosSensiblesBean;

	private PaginatedList listaConsultaDatosSensibles;

	//Parámetros de entrada de parámetros
	private String codigos;
	private String actualizarBastidor;
	private String existeBastidor;
	private static final String CONSULTA_DATOS_SENSIBLES = "consultaDatosSensibles";
	private static final String CONSULTA_BASTIDOR = "consultaBastidor";
	private static final String ALTA_DATOS_SENSIBLES = "altaDatosSensibles";
	private static final String ALTA_BASTIDOR = "altaBastidor";
	private static final String IMPORTAR = "importarDatosSensibles";
	private static final String POP_UP_EVOLUCION = "irEvolucion";
	private static final String asuntoCorreo_Alta = "asunto.correo.datosSensibles";

	private File fichero; // Fichero a importar desde la pagina JSP
	private String ficheroFileName; // nombre del fichero importado
	private String ficheroContentType; // tipo del fichero importado
	private boolean resultadoImportados;
	private boolean esImportacion = false;
	//private PaginatedList listaResultadoImportar;
	private static final String COLUMDEFECT_IMPORTADOS = "bastidor";
	private Boolean flagDisabled;
	private String grupoAdmin;

	// Variables necesarias para la segunda lista
	private String resultadosPorPaginaErroneos;
	private PaginatedList listaResultadoImportarErroneos;
	private int pageError;
	private String dirError;
	private String bastidoresErroneos;
	private String grupoBastidores;
	private String id;

	public String navegarBastidor() {
		try {
			datosSensiblesBean = (DatosSensiblesBean) getSession().get(getKeyCriteriosSession());
			if (datosSensiblesBean == null) {
				addActionError("Ha surgido un errros a la hora de recuperar los Datos sensibles");
				log.error("El bean de los datos sensibles esta vacio");
				return CONSULTA_BASTIDOR;
			}

			List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
			setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de consultar los datos sensibles.");
			log.error("Se ha producido un error a la hora de consultar los datos sensibles, Error ", e);
		}
		return CONSULTA_BASTIDOR;
	}

	public String inicioConsulta(){
		log.info("DatosSensiblesAction inicioConsulta Inicio");
		try {
			if (datosSensiblesBean != null) {
				setBeanCriterios(datosSensiblesBean);
				List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
				setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
			} else {
				cargarFiltroInicial();
			}
			flagDisabled = true;
			log.info("DatosSensiblesAction inicioConsulta Fin");
		} catch (OegamExcepcion e) {
			addActionError("Error a lahora de obtener el grupo del usuario.");
			log.error("Error a lahora de obtener el grupo del usuario, Error: " + e);
		}

		return CONSULTA_DATOS_SENSIBLES;
	}

	public String consultar(){
		log.info("DatosSensiblesAction consultar Inicio");
		try {
			List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
			setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
			if (list == null) {
				addActionMessage("No se han encontrado datos para los datos de consulta introducidos.");
				log.info("No se han encontrado datos para los datos de consulta introducidos.");
			}
			setFlagDisabled(servicioDatosSensiblesImpl.getFlagDisabled(datosSensiblesBean));
			getSession().put(getKeyCriteriosSession(), getBeanCriterios());
			log.info("DatosSensiblesAction consultar Fin");
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de consultar los datos sensibles.");
			log.error("Se ha producido un error a la hora de consultar los datos sensibles, Error ", e);
		}
		return CONSULTA_DATOS_SENSIBLES;
	}

	public String navegar() {
		try {
			datosSensiblesBean = (DatosSensiblesBean) getSession().get(getKeyCriteriosSession());
			if (datosSensiblesBean == null) {
				addActionError("Ha surgido un errros a la hora de recuperar los Datos sensibles");
				log.error("El bean de los datos sensibles esta vacio");
				return CONSULTA_DATOS_SENSIBLES;
			}

			List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
			setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de consultar los datos sensibles.");
			log.error("Se ha producido un error a la hora de consultar los datos sensibles, Error ", e);
		}
		return CONSULTA_DATOS_SENSIBLES;
	}

	public String consultarBastidor() {
		log.info("DatosSensiblesAction consultarBastidor Inicio");
		try {
			List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
			setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
			if (list == null) {
				addActionMessage("No se han encontrado datos para los datos de consulta introducidos.");
				log.error("No se han encontrado datos para los datos de consulta introducidos.");
			}
			getSession().put(getKeyCriteriosSession(), getBeanCriterios());
			log.info("DatosSensiblesAction consultarBastidor Fin");
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de consultar los datos sensibles de bastidores.");
			log.error("Se ha producido un error a la hora de consultar los datos sensibles de bastidores, Error " + e);
		}

		return CONSULTA_BASTIDOR;
	}

	/**
	 * Irá al formulario para introducir un nuevo contrato.
	 * 
	 * @return
	 */
	public String nuevo() {
		try {
			if (datosSensiblesBean != null) {
				setBeanCriterios(datosSensiblesBean);
			} else {
				cargarFiltroInicial();
			}
			flagDisabled = true;
		} catch (ClassCastException e) {
			log.error("Error recuperando el filtro del action. La clave \""
					+ getKeyCriteriosSession()
					+ "\" parece que está siendo reutilizada", e);
		}
		return ALTA_DATOS_SENSIBLES;
	}

	public String nuevoBastidor() {
		try {
			if (datosSensiblesBean != null) {
				setBeanCriterios(datosSensiblesBean);
			} else {
				cargarFiltroInicial();
				if (utilesColegiado.tienePermisoAdmin()) {
					datosSensiblesBean.setGrupo("SAN");
				}
			}
		} catch (ClassCastException e) {
			log.error("Error recuperando el filtro del action. La clave \""
				+ getKeyCriteriosSession()
				+ "\" parece que está siendo reutilizada", e);
		}
		return ALTA_BASTIDOR;
	}

	/**
	 * Irá al formulario para introducir un nuevo Dato Sensible
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String guardar() throws Exception {
		log.info("DatosSensibles guardar Inicio");
		boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		ResultBean resultBean = null;
		ResultadoDatosSensibles resultado = null;
		try {
			if (existeBastidor != null && existeBastidor.equals("true")) {
				setExisteBastidor("false");
				resultado = servicioDatosSensiblesImpl.actualizarBastidorExistente(datosSensiblesBean, utilesColegiado.getIdUsuarioSessionBigDecimal(), "APLICACION");
			} else {
				resultBean = servicioDatosSensiblesImpl.comprobarDatosSensibles(datosSensiblesBean);
				if (resultBean != null) {
					addActionError(resultBean.getListaMensajes().get(0));
					return ALTA_DATOS_SENSIBLES;
				}

				String[] datosUsuario = servicioDatosSensiblesImpl.getDatosUsuarios(datosSensiblesBean.getGrupo(), utilesColegiado.getIdContratoSessionBigDecimal(),
						utilesColegiado.getNumColegiadoSession(),  
						utilesColegiado.getIdUsuarioSessionBigDecimal(),  
						utilesColegiado.getApellidosNombreUsuario(), utilesColegiado.tienePermisoAdmin());

				resultado = servicioDatosSensiblesImpl.guardarDatosSensibles(datosSensiblesBean,
						new BigDecimal(datosUsuario[0]),
						datosUsuario[1],
						new BigDecimal(datosUsuario[2]),
						datosUsuario[3]);
				if (resultado != null && !resultado.isError()) {
					if (TiposDatosSensibles.Bastidor.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
						servicioDatosSensiblesImpl.envioMail("Se ha dado de alta el siguiente Bastidor como dato sensible en la aplicación: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, TipoBastidor.convertirTexto(datosSensiblesBean.getTipoControl()));
					} else if (TiposDatosSensibles.Matricula.toString().equals(datosSensiblesBean.getTipoAgrupacion())) {
						servicioDatosSensiblesImpl.envioMail("Se ha dado de alta la siguiente Matricula como dato sensible en la aplicación: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, null);
					} else {
						servicioDatosSensiblesImpl.envioMail("Se ha dado de alta el siguiente NIF como dato sensible en la aplicación: " + datosSensiblesBean.getTextoAgrupacion() + "<br>", asuntoCorreo_Alta, null);
					}
				}
			}
			if (resultado.isEsBastidor()) {
				if (!resultado.isError() && !resultado.isEsBastidorActualizar()) {
					addActionMessage(resultado.getMensajeError());
					servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
					if (esAdmin) {
						addActionMessage("Creada peticion para refresco de cache.");
					}
					datosSensiblesBean = null;
					cargarFiltroInicial();
				} else if (!resultado.isError() && resultado.isEsBastidorActualizar()) {
					setExisteBastidor("true");
				} else {
					if (resultado.getListaMensajes() == null || resultado.getListaMensajes().isEmpty()) {
						addActionError(resultado.getMensajeError());
					} else if (resultado.getListaMensajes().size() == 1) {
						addActionError(resultado.getListaMensajes().get(0));
					} else {
						for (String mensaje : resultado.getListaMensajes()) {
							addActionError(mensaje);
						}
					}
				}
			} else {
				if (resultado.isError()) {
					if (resultado.getListaMensajes() == null || resultado.getListaMensajes().isEmpty()) {
						addActionError(resultado.getMensajeError());
					} else if (resultado.getListaMensajes().size() == 1) {
						addActionError(resultado.getListaMensajes().get(0));
					} else {
						for (String mensaje : resultado.getListaMensajes()) {
							addActionError(mensaje);
						}
					}
				} else {
					addActionMessage(resultado.getMensajeError());
					servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
					if (esAdmin) {
						addActionMessage("Creada petición para refresco de caché.");
					}
					datosSensiblesBean = null;
					cargarFiltroInicial();
				}
			}
			setFlagDisabled(servicioDatosSensiblesImpl.getFlagDisabled(datosSensiblesBean));
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de dar de alta el dato sensible.");
			log.error("Se ha producido un error a la hora de dar de alta el dato sensible, Error: " + e);
		} catch (ClassCastException e) {
			log.error("Error recuperando el filtro del action. La clave \""
				+ getKeyCriteriosSession()
				+ "\" parece que está siendo reutilizada", e);
		}

		return ALTA_DATOS_SENSIBLES;
	}

	public String guardarBastidor() throws Exception {
		log.info("DatosSensiblesAction guardarBastidor Inicio");
		ResultadoDatosSensibles resultado = null;
		boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		try {
			if (existeBastidor != null && existeBastidor.equals("true")) {
				setExisteBastidor("false");
				resultado = servicioDatosSensiblesImpl.actualizarBastidorExistente(datosSensiblesBean, utilesColegiado.getIdUsuarioSessionBigDecimal(), "APLICACION");
			} else {
				datosSensiblesBean.setTipoAgrupacion(TiposDatosSensibles.Bastidor.getNombreEnum());
				ResultBean resultBean = servicioDatosSensiblesImpl.comprobarDatosSensibles(datosSensiblesBean);
				if (resultBean != null) {
					addActionError(resultBean.getListaMensajes().get(0));
					return ALTA_BASTIDOR;
				}

				String[] datosUsuario = servicioDatosSensiblesImpl.getDatosUsuarios(datosSensiblesBean.getGrupo(),utilesColegiado.getIdContratoSessionBigDecimal(),
						utilesColegiado.getNumColegiadoSession(),
						utilesColegiado.getIdUsuarioSessionBigDecimal(),
						utilesColegiado.getApellidosNombreUsuario(), utilesColegiado.tienePermisoAdmin());

				resultado = servicioDatosSensiblesImpl.guardarDatoSensibleBastidor(datosSensiblesBean,
					new BigDecimal(datosUsuario[0]),
					datosUsuario[1],  
					new BigDecimal(datosUsuario[2]),
					datosUsuario[3], "APLICACION");
			}
			if (!resultado.isError() && !resultado.isEsBastidorActualizar()) {
				addActionMessage(resultado.getMensajeError());
				servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
				if (esAdmin) {
					addActionMessage("Creada petición para refresco de caché.");
				}
				cargarFiltroInicial();
				if (utilesColegiado.tienePermisoAdmin()) {
					datosSensiblesBean.setGrupo("SAN");
				}
			} else if (!resultado.isError() && resultado.isEsBastidorActualizar()) {
				setExisteBastidor("true");
			} else {
				if (resultado.getListaMensajes() == null || resultado.getListaMensajes().isEmpty()) {
					addActionError(resultado.getMensajeError());
				} else if (resultado.getListaMensajes().size() == 1) {
					addActionError(resultado.getListaMensajes().get(0));
				} else {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
			}
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de dar de alta el dato sensible.");
			log.error("Se ha producido un error a la hora de dar de alta el dato sensible, Error: " + e);
		} catch (ClassCastException e) {
			log.error("Error recuperando el filtro del action. La clave \""
				+ getKeyCriteriosSession()
				+ "\" parece que está siendo reutilizada", e);
		}

		log.info("DatosSensiblesAction guardarBastidor Fin");
		return ALTA_BASTIDOR;
	}

	/**
	 * Irá al formulario para eliminar uno o varios Datos Sensibles
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String eliminar() throws Exception {
		log.info("DatosSensiblesAction eliminar Inicio");
		String indices = getCodigos();
		boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		try {
			ResultBean resultBean = servicioDatosSensiblesImpl.eliminarDatosSensible(indices,datosSensiblesBean,
					utilesColegiado.getIdContratoSessionBigDecimal(),
					utilesColegiado.getIdUsuarioSessionBigDecimal());

			if (resultBean != null){
				if (resultBean.getListaMensajes().size() == 1) {
					addActionError(resultBean.getListaMensajes().get(0));
				} else {
					for (String mensaje : resultBean.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
			} else {
				String[] codSeleccionados = indices.split("-");
				for (int i = 0; i < codSeleccionados.length; i++) {
					String[] valores = codSeleccionados[i].split(",");
					addActionMessage("El Dato Sensible " + valores[0] + " se ha eliminado correctamente");
				}
				servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
				if (esAdmin) {
					addActionMessage("Creada petición para refresco de caché.");
				}
			}
			List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
			setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
			setFlagDisabled(servicioDatosSensiblesImpl.getFlagDisabled(datosSensiblesBean));
			log.info("DatosSensiblesAction eliminar Fin");
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de eliminar el datos sensibles.");
			log.error("Se ha producido un error a la hora de eliminar los datos sensibles, Error: " + e);
		}

		return CONSULTA_DATOS_SENSIBLES;
	}

	/**
	 * Irá al formulario para eliminar uno o varios Bastidores
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String eliminarBastidor() throws Exception {
		log.info("DatosSensiblesAction eliminar Inicio");
		String indices = getCodigos();
		boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		try {
			ResultBean resultBean = servicioDatosSensiblesImpl.eliminarBastidores(indices,datosSensiblesBean,
					utilesColegiado.getIdContratoSessionBigDecimal(),
					utilesColegiado.getIdUsuarioSessionBigDecimal(), "APLICACION");

			if (resultBean != null) {
				if (resultBean.getListaMensajes().size() == 1) {
					addActionError(resultBean.getListaMensajes().get(0));
				} else {
					for (String mensaje : resultBean.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
			} else {
				String[] codSeleccionados = indices.split("-");
				for (int i = 0; i < codSeleccionados.length; i++) {
					String[] valores = codSeleccionados[i].split(",");
					addActionMessage("El Bastidor " + valores[0] + " se ha eliminado correctamente");
				}
				servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
				if (esAdmin) {
					addActionMessage("Creada petición para refresco de caché.");
				}
			}

			List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
			setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));

			log.info("DatosSensiblesAction eliminarBastidor Fin");
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de eliminar el bastidor.");
			log.error("Se ha producido un error a la hora de eliminar el bastidor, Error: " + e);
			try {
				List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
				setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
			} catch (OegamExcepcion e1) {
				addActionError("Se ha producido un error a la hora de consultar los bastidores.");
				log.error("Se ha producido un error a la hora de consultar los bastidores, Error: " + e);
			}

		}

		return CONSULTA_BASTIDOR;
	}

	public String inicioBastidor() throws Throwable {
		log.info("DatosSensiblesAction inicioBastidor Inicio");
		try {
			if (datosSensiblesBean != null) {
				setBeanCriterios(datosSensiblesBean);
				List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
				setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
			}else{
				cargarFiltroInicial();
			}
		}catch(OegamExcepcion e){
			addActionError("Error al iniciar la pantalla de bastidores.");
			log.error("Error al iniciar la pantalla de bastidores, Error: " + e);
		} catch (ClassCastException e) {
			log.error("Error recuperando el filtro del action. La clave \""
			+ getKeyCriteriosSession()
			+ "\" parece que está siendo reutilizada", e);
		}

		log.info("DatosSensiblesAction eliminarBastidor Fin");

		return CONSULTA_BASTIDOR;
	}

	public String inicioImportar() {
		esImportacion = true;
		cargarFiltroInicial();
		resultadoImportados = false;
		return IMPORTAR;
	}

	public String importar() {
		if(!utiles.esNombreFicheroValido(ficheroFileName)) {
			addActionError("El nombre del fichero es erroneo");
		}else {
			ResultBean result = servicioDatosSensiblesImpl.comprobarDatosImportacion(fichero, datosSensiblesBean);
			boolean esAdmin = utilesColegiado.tienePermisoAdmin();
			if(result != null){
				addActionError(result.getListaMensajes().get(0));
				return IMPORTAR;
			}
			try {
				FicheroBean ficheroBean = new FicheroBean();
				ficheroBean.setFichero(fichero);
				ficheroBean.setNombreYExtension(ficheroFileName);
				
				String[] datosUsuario = servicioDatosSensiblesImpl.getDatosUsuarios(datosSensiblesBean.getGrupo(), utilesColegiado.getIdContratoSessionBigDecimal(),
						utilesColegiado.getNumColegiadoSession(),
						utilesColegiado.getIdUsuarioSessionBigDecimal(),
						utilesColegiado.getApellidosNombreUsuario(), utilesColegiado.tienePermisoAdmin());

				ResultadoDatosSensibles resultImportacion = servicioDatosSensiblesImpl.importarBastidor(datosSensiblesBean, ficheroBean,
						new BigDecimal(datosUsuario[0]),
						datosUsuario[1],
						new BigDecimal(datosUsuario[2]),
						datosUsuario[3], "IMPORTACION");

				if (resultImportacion.isEsBastidor() && resultImportacion.isError()) {
					if (resultImportacion.getListaMensajes() != null && !resultImportacion.getListaMensajes().isEmpty()) {
						for (String mensaje : resultImportacion.getListaMensajes()) {
							addActionError(mensaje);
						}
					} else {
						addActionError(resultImportacion.getMensajeError());
					}
				}else{
					addActionMessage("El Fichero se ha importado correctamente.");
					if(resultImportacion.getListaMensajesAdvertencias() != null && !resultImportacion.getListaMensajesAdvertencias().isEmpty()){
						for(String mensajeAdv : resultImportacion.getListaMensajesAdvertencias()){
							addActionMessage(mensajeAdv);
						}
					}
				}
				crearListasResultado(esAdmin, resultImportacion);
				getSession().put(getKeyCriteriosSession(), getBeanCriterios());
			} catch (OegamExcepcion e) {
				addActionError("Se ha producido un error a de importar los datos sensibles.");
				log.error("Se ha producido un error a de importar los datos sensibles, Error: " + e);
			}
			resultadoImportados = true;
		}
		return IMPORTAR;
	}

	private void crearListasResultado(boolean esAdmin, ResultadoDatosSensibles resultImportacion) {
		// Lista bastidores erroneos
		if (resultImportacion.getListaBastidoresErroneos() != null && !resultImportacion.getListaBastidoresErroneos().isEmpty()){
			setResultadosPorPaginaErroneos("5");
			setPageError(1);
			setDirError("asc");
			setListaResultadoImportarErroneos(servicioDatosSensiblesImpl.crearListaResultadosImportarErroneosPaginated(resultImportacion.getListaBastidoresErroneos(),
					getResultadosPorPaginaErroneos(),getPageError(),getDirError(),COLUMDEFECT_IMPORTADOS));
			getSession().put("listaBastidoresErroneos", resultImportacion.getListaBastidoresErroneos());
		}
	}

	@SuppressWarnings("unchecked")
	public String navegarImportacion(){
		datosSensiblesBean = (DatosSensiblesBean) getSession().get(getKeyCriteriosSession());
		if (datosSensiblesBean == null) {
			addActionError("Ha surgido un error a la hora de importar los Datos sensibles");
			log.error("El bean de los datos sensibles esta vacio");
			return IMPORTAR;
		}
		/*setListaResultadoImportar(servicioDatosSensiblesImpl.crearListaResultadosImportarPaginated((List<ImportarBastidorDto>) getSession().get("listaBastidoresImportar"),getResultadosPorPagina(),
				getPage(), getDir(), COLUMDEFECT_IMPORTADOS));*/

		setListaResultadoImportarErroneos(servicioDatosSensiblesImpl.crearListaResultadosImportarErroneosPaginated((List<BastidorErroneoBean>) getSession().get("listaBastidoresErroneos"),
				getResultadosPorPaginaErroneos(),getPageError(),getDirError(),COLUMDEFECT_IMPORTADOS));

		resultadoImportados = true;
		return IMPORTAR;
	}

	@SuppressWarnings("unchecked")
	public String enviarPeticion(){
		boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		if((bastidoresErroneos != null && bastidoresErroneos != "") && (grupoBastidores != null && grupoBastidores != "")){
			ResultBean result = servicioDatosSensiblesImpl.getListaBastidoresErroneos(bastidoresErroneos, grupoBastidores);
			List<DatosSensiblesBastidorDto> listaBastidoresDto = (List<DatosSensiblesBastidorDto>) result.getAttachment("listaBastidoresDto");
			if(listaBastidoresDto != null && !listaBastidoresDto.isEmpty()){
				String[] grupo = grupoBastidores.split("-");
				String[] datosUsuario = servicioDatosSensiblesImpl.getDatosUsuarios(grupo[0],utilesColegiado.getIdContratoSessionBigDecimal(),
						utilesColegiado.getNumColegiadoSession(),
						utilesColegiado.getIdUsuarioSessionBigDecimal(),  
						utilesColegiado.getApellidosNombreUsuario(), utilesColegiado.tienePermisoAdmin());
				
				ResultadoDatosSensibles resultado = servicioDatosSensiblesImpl.enviarPeticion(listaBastidoresDto,
						new BigDecimal(datosUsuario[0]), new BigDecimal(datosUsuario[2]));
				if (resultado != null) {
					if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
						for (String mensajeError : resultado.getListaMensajes()) {
							addActionError(mensajeError);
						}
					}
				} else {
					addActionMessage("Se han encolado las peticiones correctamente.");
					servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
					if(esAdmin){
						addActionMessage("Creada peticion para refresco de cache.");
					}
					
					if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
						for (String mensajeError : result.getListaMensajes()) {
							addActionError(mensajeError);
						}
					}
				}
			} else {
				for (String mensaje : result.getListaMensajes()) {
					addActionError(mensaje);
				}
			}
		} else {
			addActionError("No ha seleccionado ningun bastidor para enviar la petición");
		}
		navegarImportacion();
		return IMPORTAR;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return CONSULTA_DATOS_SENSIBLES;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloDatosSensiblesPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if(utilesColegiado.tienePermisoColegio()){
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if(datosSensiblesBean == null){
			datosSensiblesBean = new DatosSensiblesBean();
		}
		datosSensiblesBean.setFecha(utilesFecha.getFechaFracionadaActual());
		try {
			datosSensiblesBean.setGrupo(servicioDatosSensiblesImpl.obtenerGrupo(utilesColegiado.getIdUsuarioSessionBigDecimal()));
			datosSensiblesBean.setEstado(1L);
		} catch (OegamExcepcion e) {
			addActionError("Error a lahora de obtener el grupo del usuario.");
			log.error("Error a lahora de obtener el grupo del usuario, Error: " + e);
		}
		if(esImportacion){
			datosSensiblesBean.setTipoAgrupacion(TiposDatosSensibles.Bastidor.getValorEnum());
		}
	}

	/**
	 * Irá al formulario para activar uno o varios Datos Sensibles
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String activar() throws Exception {
		log.info("DatosSensiblesAction activar Inicio");
		String indices = getCodigos();
		boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		try{
			ResultBean resultBean = servicioDatosSensiblesImpl.activarDatosSensible(indices,datosSensiblesBean, 
					utilesColegiado.getIdContratoSessionBigDecimal(),
					utilesColegiado.getIdUsuarioSessionBigDecimal());

			if (resultBean != null) {
				if (resultBean.getListaMensajes().size() == 1) {
					addActionError(resultBean.getListaMensajes().get(0));
				} else {
					for (String mensaje : resultBean.getListaMensajes()){
						addActionError(mensaje);
					}
				}
			} else {
				String[] codSeleccionados = indices.split("-");
				for (int i = 0; i < codSeleccionados.length; i++) {
					String[] valores = codSeleccionados[i].split(",");
					addActionMessage("El Dato Sensible " + valores[0] + " se ha activado correctamente");
				}
				servicioRecargaCache.guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
				if(esAdmin){
					addActionMessage("Creada peticion para refresco de cache.");
				}
			}
			List<DatosSensiblesAgrupados> list = servicioDatosSensiblesImpl.getListaDatosSensibles(datosSensiblesBean);
			setListaConsultaDatosSensibles(servicioDatosSensiblesImpl.crearListaPaginated(list, getResultadosPorPagina(), getPage(), getDir(), getColumnaPorDefecto()));
			setFlagDisabled(servicioDatosSensiblesImpl.getFlagDisabled(datosSensiblesBean));
			log.info("DatosSensiblesAction eliminar Fin");
		} catch (OegamExcepcion e) {
			addActionError("Se ha producido un error a la hora de eliminar el datos sensibles.");
			log.error("Se ha producido un error a la hora de eliminar los datos sensibles, Error: " + e);
		}

		return CONSULTA_DATOS_SENSIBLES;
	}

	public String abrirEvolucion() {
		if (id != null) {
			return POP_UP_EVOLUCION;
		} else {
			addActionError("Ha sucedido un error a la hora de obtener la evolución del dato sensible.");
			return buscar();
		}
	}

	/**
	 * Metodo por defecto al que se llama desde el buscador,puede sobreescribirse o usar
	 * otro
	 * @return
	 */
	public String buscar() {
		setBusquedaInicial(true);
		return actualizarPaginatedList();
	}

	@Override
	protected Object getBeanCriterios() {
		return datosSensiblesBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.datosSensiblesBean = (DatosSensiblesBean) object;
	}
	
	@Override
	public String getDecorator(){
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorImportarBastidores";
	}

	public ModelPagination getModeloDatosSensiblesPaginated() {
		return modeloDatosSensiblesPaginated;
	}

	public void setModeloDatosSensiblesPaginated(
			ModelPagination modeloDatosSensiblesPaginated) {
		this.modeloDatosSensiblesPaginated = modeloDatosSensiblesPaginated;
	}

	public ServicioDatosSensibles getServicioDatosSensiblesImpl() {
		return servicioDatosSensiblesImpl;
	}

	public void setServicioDatosSensiblesImpl(
			ServicioDatosSensibles servicioDatosSensiblesImpl) {
		this.servicioDatosSensiblesImpl = servicioDatosSensiblesImpl;
	}

	public DatosSensiblesBean getDatosSensiblesBean() {
		return datosSensiblesBean;
	}

	public void setDatosSensiblesBean(DatosSensiblesBean datosSensiblesBean) {
		this.datosSensiblesBean = datosSensiblesBean;
	}

	public String getCodigos() {
		return codigos;
	}

	public void setCodigos(String codigos) {
		this.codigos = codigos;
	}
	
	public PaginatedList getListaConsultaDatosSensibles() {
		return listaConsultaDatosSensibles;
	}

	public void setListaConsultaDatosSensibles(
			PaginatedList listaConsultaDatosSensibles) {
		this.listaConsultaDatosSensibles = listaConsultaDatosSensibles;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public boolean isResultadoImportados() {
		return resultadoImportados;
	}

	public void setResultadoImportados(boolean resultadoImportados) {
		this.resultadoImportados = resultadoImportados;
	}

	public String getFicheroFileName() {
		return ficheroFileName;
	}

	public void setFicheroFileName(String ficheroFileName) {
		this.ficheroFileName = ficheroFileName;
	}

	public String getFicheroContentType() {
		return ficheroContentType;
	}

	public void setFicheroContentType(String ficheroContentType) {
		this.ficheroContentType = ficheroContentType;
	}

	/*public PaginatedList getListaResultadoImportar() {
		return listaResultadoImportar;
	}

	public void setListaResultadoImportar(PaginatedList listaResultadoImportar) {
		this.listaResultadoImportar = listaResultadoImportar;
	}*/

	public Boolean getFlagDisabled() {
		return flagDisabled;
	}

	public void setFlagDisabled(Boolean flagDisabled) {
		this.flagDisabled = flagDisabled;
	}

	public String getGrupoAdmin() {
		return grupoAdmin;
	}

	public void setGrupoAdmin(String grupoAdmin) {
		this.grupoAdmin = grupoAdmin;
	}

	public String getActualizarBastidor() {
		return actualizarBastidor;
	}

	public void setActualizarBastidor(String actualizarBastidor) {
		this.actualizarBastidor = actualizarBastidor;
	}

	public String getExisteBastidor() {
		return existeBastidor;
	}

	public void setExisteBastidor(String existeBastidor) {
		this.existeBastidor = existeBastidor;
	}

	public String getResultadosPorPaginaErroneos() {
		return resultadosPorPaginaErroneos;
	}

	public void setResultadosPorPaginaErroneos(String resultadosPorPaginaErroneos) {
		this.resultadosPorPaginaErroneos = resultadosPorPaginaErroneos;
	}

	public PaginatedList getListaResultadoImportarErroneos() {
		return listaResultadoImportarErroneos;
	}

	public void setListaResultadoImportarErroneos(
			PaginatedList listaResultadoImportarErroneos) {
		this.listaResultadoImportarErroneos = listaResultadoImportarErroneos;
	}

	public int getPageError() {
		return pageError;
	}

	public void setPageError(int pageError) {
		this.pageError = pageError;
	}

	public String getDirError() {
		return dirError;
	}

	public void setDirError(String dirError) {
		this.dirError = dirError;
	}

	public String getBastidoresErroneos() {
		return bastidoresErroneos;
	}

	public void setBastidoresErroneos(String bastidoresErroneos) {
		this.bastidoresErroneos = bastidoresErroneos;
	}

	public String getGrupoBastidores() {
		return grupoBastidores;
	}

	public void setGrupoBastidores(String grupoBastidores) {
		this.grupoBastidores = grupoBastidores;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}