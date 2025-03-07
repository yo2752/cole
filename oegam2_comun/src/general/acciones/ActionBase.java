package general.acciones;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import oegam.constantes.ConstantesSession;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.Menu;

import com.opensymphony.xwork2.ActionSupport;

import escrituras.beans.ResultBean;

public abstract class ActionBase extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;

	/*
	 * Para mostrar mensaje en la página de error:
	 */
	private String mensajeError;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ActionBase.class);

	private static final String DOUBLE_SUBMIT = "dobleSubmit";

	private Map <String,Object> session;

	protected AddScriptsBean addScripts;

	private LinkedList<Menu> recientes;
	// Mantis 17350
	private ArrayList<Menu> favoritos;
	// Mantis 34940
	private List<SemaforoDto> listaSemaforosSesion;

	/*
	 *  Almacena un Long de timestamp para evitar dobles submit
	 *  Devuelve true si se trata de un doble submit (se recargó la página)
	 */
	private Long dobleSubmit;

	protected boolean comprobarDobleSubmit(){
		if(getSession().get(DOUBLE_SUBMIT) == null){
			// Es el primer submit. Lo pone en sesion:
			getSession().put(DOUBLE_SUBMIT, dobleSubmit);
			return false;
		} else {
			// Comprueba si es el mismo
			Long valorAnterior = (Long)getSession().get(DOUBLE_SUBMIT);
			log.debug("Valor nuevo de dobleSubmit   : " + dobleSubmit);
			log.debug("Valor guardado de dobleSubmit: " + valorAnterior);

			// Lo pone en sesión para la siguiente:
			getSession().put(DOUBLE_SUBMIT, dobleSubmit);
			return (dobleSubmit != null && dobleSubmit.equals(valorAnterior));
		}
	}

	/**
	 * Añade los mensajes del result al action
	 * @param resultado
	 */
	protected void aniadirMensajeError(ResultBean resultado) {
		if (resultado != null && resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
			aniadirListaErrores(resultado.getListaMensajes());
		}
	}

	/**
	 * Añade los mensajes de aviso al action
	 * @param resultado
	 */
	protected void aniadirMensajeAviso(ResultBean resultado, List<String> listaAvisos) {
		if (resultado != null && resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
			aniadirListaMessage(resultado.getListaMensajes());
		} else if (listaAvisos != null && !listaAvisos.isEmpty()) {
			aniadirListaMessage(listaAvisos);
		}
	}

	/**
	 * Añade los mensajes de la lista al action error
	 * @param resultado
	 */
	protected void aniadirListaErrores(List<String> lista){
		if (lista != null && !lista.isEmpty()) {
			for (String mensaje : lista) {
				addActionError(mensaje);
			}
		}
	}

	/**
	 * Añade los mensajes de la lista al action message
	 * @param resultado
	 */
	protected void aniadirListaMessage(List<String> lista){
		if (lista != null && !lista.isEmpty()) {
			for (String mensaje : lista) {
				addActionMessage(mensaje);
			}
		}
	}

	/**
	 * Limpia los valores de la sesion
	 *
	 */

	protected void limpiarCamposSession(){
		getSession().put(ConstantesSession.NIF_PERSONA, null);
		getSession().put(ConstantesSession.NOMBRE_PERSONA, null);
		getSession().put(ConstantesSession.APELLIDO_1_PERSONA, null);
		getSession().put(ConstantesSession.APELLIDO_2_PERSONA, null);
		getSession().put(ConstantesSession.TIPO_PERSONA, null);
		getSession().put(ConstantesSession.ESTADO_CIVIL_PERSONA, null);
		getSession().put(ConstantesSession.ESTADO_PERSONA, null);
		getSession().put(ConstantesSession.LISTA_PERSONAS, null);
		getSession().put(ConstantesSession.NUM_COLEGIADO_BUSCAR_PERSONAS, null);
		getSession().put(ConstantesSession.ESTADO_PERSONA_BUSCAR_PERSONAS, null);
		getSession().put(ConstantesSession.ID_CONTRATO_BUSCAR_PERSONAS, null);
		getSession().put(ConstantesSession.ID_USUARIO_BUSCAR_PERSONAS, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);
		/**
		 * Contrato Action
		 */
		getSession().put(ConstantesSession.CIF_CONTRATO, null);
		getSession().put(ConstantesSession.ESTADO_CONTRATO,null);
		getSession().put(ConstantesSession.ESTADO_CONTRATO_USUARIO,null);
		getSession().put(ConstantesSession.LISTA_CONTRATOS, null);
		getSession().put(ConstantesSession.RAZON_SOCIAL_CONTRATO, null);
		getSession().put(ConstantesSession.NUM_COLEGIADO_CONTRATO, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);
		getSession().put(ConstantesSession.SESSION_RESULTBEAN_CONTRATO,null);
		getSession().put(ConstantesSession.SESSION_RESULTBEAN_USUARIOS_CONTRATO,null);
		getSession().put(ConstantesSession.SESSION_RESULTBEAN_ALTA_CONTRATO,null);
		getSession().put(ConstantesSession.LISTA_CONTRATOS, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);
		getSession().put(ConstantesSession.NUM_EXPEDIENTE_CONSULTA, null);
		getSession().put(ConstantesSession.CONSULTA_TRAMITE_TRAFICO, null);

		// Mantenimiento web service
		getSession().put(ConstantesSession.CODIGO_WEB_SERVICE, null);
		getSession().put(ConstantesSession.MENSAJE_WEB_SERVICE, null);
		getSession().put(ConstantesSession.DESCRIPCION_WEB_SERVICE, null);
		getSession().put(ConstantesSession.SIN_DESCRIPCION_WEB_SERVICE, null);
		getSession().put(ConstantesSession.RECUPERABLE_WEB_SERVICE, null);
		// MODELO 600
		getSession().put(ConstantesSession.MUNICIPIOS_SUJETO_PASIVO_MODELO600,null);
		getSession().put(ConstantesSession.MUNICIPIOS_TRANSMITENTE_PASIVO_MODELO600, null);
		getSession().put(ConstantesSession.MUNICIPIOS_PRESENTADOR_MODELO600, null);
		getSession().put(ConstantesSession.MUNICIPIOS_BIENES_URBANOS_MODELO600, null);
		getSession().put(ConstantesSession.MUNICIPIOS_BIENES_RUSTICOS_MODELO600, null);	
		getSession().put(ConstantesSession.FECHA_PRIMERA_LIQUIDACION_MODELO600, null);
		getSession().put(ConstantesSession.FECHA_PRESENTACION_MODELO600, null);
		getSession().put(ConstantesSession.FECHA_DEVENGO_MODELO600, null);
		getSession().put(ConstantesSession.FECHA_USUFRUCTO_MODELO600, null);
		getSession().put(ConstantesSession.BONIFICACION_MODELO600, null);

		// MODELO 601
		getSession().put(ConstantesSession.MUNICIPIOS_SUJETO_PASIVO_MODELO601,null);
		getSession().put(ConstantesSession.MUNICIPIOS_TRANSMITENTE_PASIVO_MODELO601, null);
		getSession().put(ConstantesSession.MUNICIPIOS_PRESENTADOR_MODELO601, null);
		getSession().put(ConstantesSession.MUNICIPIOS_BIENES_URBANOS_MODELO601, null);
		getSession().put(ConstantesSession.MUNICIPIOS_BIENES_RUSTICOS_MODELO601, null);	
		getSession().put(ConstantesSession.FECHA_PRIMERA_LIQUIDACION_MODELO601, null);
		getSession().put(ConstantesSession.FECHA_PRESENTACION_MODELO601, null);
		getSession().put(ConstantesSession.FECHA_DEVENGO_MODELO601, null);
		getSession().put(ConstantesSession.FECHA_USUFRUCTO_MODELO601, null);
		getSession().put(ConstantesSession.BONIFICACION_MODELO601, null);

		// REMESA 600
		getSession().put(ConstantesSession.MUNICIPIOS_SUJETO_PASIVO_REMESA600,null);
		getSession().put(ConstantesSession.MUNICIPIOS_TRANSMITENTE_PASIVO_REMESA600, null);
		getSession().put(ConstantesSession.MUNICIPIOS_PRESENTADOR_REMESA600, null);
		getSession().put(ConstantesSession.MUNICIPIOS_BIENES_URBANOS_REMESA600, null);
		getSession().put(ConstantesSession.MUNICIPIOS_BIENES_RUSTICOS_REMESA600, null);	
		getSession().put(ConstantesSession.FECHA_PRIMERA_LIQUIDACION_REMESA600, null);
		getSession().put(ConstantesSession.FECHA_PRESENTACION_REMESA600, null);
		getSession().put(ConstantesSession.FECHA_DEVENGO_REMESA600, null);
		getSession().put(ConstantesSession.FECHA_USUFRUCTO_REMESA600, null);
		getSession().put(ConstantesSession.BONIFICACION_REMESA600, null);

		getSession().put(ConstantesSession.TIPO_BIEN, null);
		getSession().put(ConstantesSession.PROVINCIA_BIEN, null);
		getSession().put(ConstantesSession.MUNICIPIO_BIEN, null);
		getSession().put(ConstantesSession.NOMBREVIA_BIEN, null);
		getSession().put(ConstantesSession.REF_CATASTRAL_BIEN, null);
		getSession().put(ConstantesSession.BIEN, null);
		getSession().put(ConstantesSession.TIPO_INMUEBLE_BIEN, null);
		getSession().put(ConstantesSession.ESTADO_BIEN, null);
		getSession().put(ConstantesSession.NUMERO_BIEN, null);
		getSession().put(ConstantesSession.CODIGO_POSTAL_BIEN, null);
		getSession().put(ConstantesSession.ESTADO_BIEN, null);
		getSession().put(ConstantesSession.LISTA_BIENES, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);

		getSession().put(ConstantesSession.TIPO_BIEN_RUSTICO, null);
		getSession().put(ConstantesSession.PROVINCIA_RUSTICO, null);
		getSession().put(ConstantesSession.MUNICIPIO_RUSTICO, null);
		getSession().put(ConstantesSession.NOMBREVIA_RUSTICO, null);
		getSession().put(ConstantesSession.REFCATASTRAL_RUSTICO, null);
		getSession().put(ConstantesSession.TIPO_INMUEBLE_RUSTICO, null);
		getSession().put(ConstantesSession.ESTADO_BIEN_RUSTICO, null);
		getSession().put(ConstantesSession.NUMERO_RUSTICO, null);
		getSession().put(ConstantesSession.CODIGO_POSTAL_RUSTICO, null);
		getSession().put(ConstantesSession.POLIGONO_RUSTICO, null);
		getSession().put(ConstantesSession.PARCELA_RUSTICO, null);
		getSession().put(ConstantesSession.PARAJE_RUSTICO, null);
		getSession().put(ConstantesSession.SISTEMA_EXPLOTACION_RUSTICO, null);
		getSession().put(ConstantesSession.USO_RUSTICO, null);
		getSession().put(ConstantesSession.LISTA_BIENES_RUSTICOS, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);

		getSession().put(ConstantesSession.TIPO_BIEN_URBANO, null);
		getSession().put(ConstantesSession.PROVINCIA_URBANO, null);
		getSession().put(ConstantesSession.MUNICIPIO_URBANO, null);
		getSession().put(ConstantesSession.NOMBREVIA_URBANO, null);
		getSession().put(ConstantesSession.REFCATASTRAL_URBANO, null);
		getSession().put(ConstantesSession.TIPO_INMUEBLE_URBANO, null);
		getSession().put(ConstantesSession.ESTADO_BIEN, null);
		getSession().put(ConstantesSession.NUMERO_URBANO, null);
		getSession().put(ConstantesSession.CODIGO_POSTAL_URBANO, null);
		getSession().put(ConstantesSession.SITUACION_URBANO, null);
		getSession().put(ConstantesSession.FECHA_CONSTRUCCION_URBANO, null);
		getSession().put(ConstantesSession.ARRENDAMIENTO_URBANO, null);
		getSession().put(ConstantesSession.VPO_URBANO, null);
		getSession().put(ConstantesSession.LISTA_BIENES_URBANOS, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);

		getSession().put(ConstantesSession.MODELO_MODELO600_601, null);
		getSession().put(ConstantesSession.CONCEPTO_MODELO600_601, null);
		getSession().put(ConstantesSession.CODIGO_NOTARIO, null);
		getSession().put(ConstantesSession.FECHA_PRESENTACION_MODELO600_601, null);
		getSession().put(ConstantesSession.LISTA_MODELOS600_601, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);

		getSession().put(ConstantesSession.NIF_NOTARIO, null);
		getSession().put(ConstantesSession.CODIGO_NOTARIO, null);
		getSession().put(ConstantesSession.NOMBRE_NOTARIO, null);
		getSession().put(ConstantesSession.APELLIDO_1_NOTARIO, null);
		getSession().put(ConstantesSession.APELLIDO_2_NOTARIO, null);
		getSession().put(ConstantesSession.ESTADO_NOTARIO, null);
		getSession().put(ConstantesSession.LISTA_NOTARIOS, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);

		getSession().put(ConstantesSession.CIF, null);
		getSession().put(ConstantesSession.DENOMINACION_SOCIAL, null);
		getSession().put(ConstantesSession.EMAIL, null);
		getSession().put(ConstantesSession.HOJA, null);
		getSession().put(ConstantesSession.HOJA_BIS, null);
		getSession().put(ConstantesSession.IUS, null);
		getSession().put(ConstantesSession.LISTA_SOCIEDADES, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);
		getSession().put(ConstantesSession.SECCION, null);
		getSession().put(ConstantesSession.TIPO_PERSONA, null);
		getSession().put(ConstantesSession.TIPO_TRAMITE, null);
		/**
		 * Credito action
		 */
		getSession().put(ConstantesSession.LISTA_CREDITOS, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);
		getSession().put(ConstantesSession.NUM_COLEGIADO_CREDITOS, null);

		/**
		 * Historico action
		 */
		getSession().put(ConstantesSession.FECHA_ALTA_HISTORICO, null);
		getSession().put(ConstantesSession.LISTA_HISTORICO_HISTORICO, null);
		getSession().put(ConstantesSession.NUM_COLEGIADO_HISTORICO, null);
		getSession().put(ConstantesSession.RESULTADOS_PAGINA, null);
		getSession().put(ConstantesSession.TIPO_CREDITO_HISTORICO, null);

		/**
		 * ContadoresUso action
		 */
		getSession().put(ConstantesSession.APLICACION_CONTADORES_USO, null);
		getSession().put(ConstantesSession.FECHA_ALTA_CONTADORES_USO, null);
		getSession().put(ConstantesSession.LISTA_CONTADORES_USO, null);
		getSession().put(ConstantesSession.NUM_COLEGIADO_CONTADORES_USO, null);
		getSession().put(ConstantesSession.RAZON_SOCIAL_CONTADORES_USO, null);

		getSession().put(ConstantesSession.CONSULTA_CONTRATO, null);
		getSession().put(ConstantesSession.LISTA_CONSULTA_CONTRATOS, null);
		getSession().put(ConstantesSession.LISTA_APLICACIONES_CONTRATO,null);
		getSession().put(ConstantesSession.LISTA_USUARIOS_CONTRATO, null);

		getSession().put(ConstantesSession.NUM_COLEGIADO_TRAMITE, null);

		/**
		 * JustificantesProfesionales action
		 */
		getSession().put(ConstantesSession.FECHA_JUSTIFICANTE, null);
		getSession().put(ConstantesSession.ID_JUSTIFICANTE, null);
		getSession().put(ConstantesSession.MATRICULA_CONSULTA, null);
	}

	protected void inicializarScripts(){
		addScripts = new AddScriptsBean();
		addScripts.setScripts(new ArrayList<ScriptFeaturesBean>());
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public Long getDobleSubmit() {
		return dobleSubmit;
	}

	public void setDobleSubmit(Long dobleSubmit) {
		this.dobleSubmit = dobleSubmit;
	}

	public AddScriptsBean getAddScripts() {
		return addScripts;
	}

	public void setAddScripts(AddScriptsBean addScripts) {
		this.addScripts = addScripts;
	}
	// Mantis 17350
	@SuppressWarnings("unchecked")
	public ArrayList<Menu> getFavoritos() {
		if (favoritos == null) {
			favoritos = (ArrayList<Menu>) Claves.getObjetoDeContextoSesion("favoritos");
		}
		return favoritos;
	}

	public void setFavoritos(ArrayList<Menu> favoritos) {
		this.favoritos = favoritos;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Menu> getRecientes() {
		if (recientes == null) {
			recientes = (LinkedList<Menu>) Claves.getObjetoDeContextoSesion("recientes");
		}
		return recientes;
	}

	public void setRecientes(LinkedList<Menu> recientes) {
		this.recientes = recientes;
	}

	// Mantis 34940
	@SuppressWarnings("unchecked")
	public List<SemaforoDto> getListaSemaforosSesion() {
		if (getSession().get(ConstantesSession.LISTA_SEMAFOROS_NODO) != null) {
			listaSemaforosSesion = (List<SemaforoDto>)
					getSession().get(ConstantesSession.LISTA_SEMAFOROS_NODO);
		}
		return listaSemaforosSesion;
	}

	public void setListaSemaforosSesion(List<SemaforoDto> listaSemaforosSesion) {
		this.listaSemaforosSesion = listaSemaforosSesion;
	}

}