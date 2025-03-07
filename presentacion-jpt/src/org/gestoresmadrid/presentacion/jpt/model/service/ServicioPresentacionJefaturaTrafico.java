package org.gestoresmadrid.presentacion.jpt.model.service;

import java.text.ParseException;
import java.util.List;

import org.gestoresmadrid.core.presentacion.jpt.model.beans.ResumenJPTBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import escrituras.beans.ResultBean;

public interface ServicioPresentacionJefaturaTrafico {

	public static final String RECIPIENT = "presentacionJPT.correo.recipient";
	public static final String SUBJECT = "Expedientes no presentados en jefatura provincial de tráfico";
	public static final String DIRECCIONES_OCULTAS = "presentacionJPT.correo.direccionesOcultas";
	
	public static final String NOPRESENTADOSJPT = "NoPresentadosJpt";
	public static final String TRANSMISION = "Transmision";
	public static final String MATRICULACION_2015 = "Matriculacion_2015";
	public static final String MATRICULACION_2016 = "Matriculacion_2016";
	public static final String ANIO_2015 = "2015";
	public static final String ANIO_2016 = "2016";
	public static final String TIPO_MATRICULACION = "T1";
	public static final String TIPO_TRANSMISION = "T7";

	/**
	 * Obtiene el resumen de los tramites que van a ser presentados en jefatura
	 * @param docId docId del documento base que contiene la relacion de expedientes
	 * @return Listado con el resumen de los tramites que van a ser presentados
	 */
	ResumenJPTBean getResumen(String docId);

	List<ResumenJPTBean> getResumenMultiple(List<String> listadoIds);

	/**
	 * Confirma la presentacion en jefafura de los tramites asociados a ese docId, actualizando fecha, y evolucion
	 * @param docId docId del documento base que contiene la relacion de expedientes
	 * @param idUsuario identificador del usuario (en sesion) que realiza la accion
	 * @return ResultBean en el que se guarda en error false si todo ha ido bien, o true si ocurrio algun error. Y en listaMensajes, los tramites que dieron fallo
	 */
	ResumenJPTBean presentarTramites(String docId, UsuarioDto usuario);

	List<ResumenJPTBean> presentarTramitesMultiple(List<String> listadoDocId, UsuarioDto usuario);

	/**
	 * Obtiene los trámites que no se han presentado en JPT
	 * @return ResultBean en el que se guarda en error false si todo ha ido bien, o true si ocurrio algun error. Y en listaMensajes, los tramites que no se han presentado
	 */
	ResultBean obtenerNoPresentados();

	void enviarCorreo(List<TramiteTrafDto> noPresentados) throws ParseException, Exception;
}
