package org.gestoresmadrid.oegam2comun.administracion.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.enumerados.EstadoRecargaCacheEnum;
import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;
import org.gestoresmadrid.core.administracion.model.vo.RecargaCacheVO;

import escrituras.beans.ResultBean;

public interface ServicioRecargaCache extends Serializable {

	public static final String PROPERTY_FRONTALES_ACTIVOS = "frontales.activos.ip";
	public static final String URL_RECARGA_COMBOS = "/recargaCombosServlet";
	public static final String URL_RECARGA_DATOS_SENSIBLES = "/recargaDatosSensiblesServlet";

	void guardarPeticion(RecargaCacheVO peticion);

	void guardarPeticion(TipoRecargaCacheEnum tipo);

	void marcarPeticionTratada(RecargaCacheVO peticion, EstadoRecargaCacheEnum estado, String error);

	void marcarPeticionTratada(RecargaCacheVO peticion, EstadoRecargaCacheEnum estado);

	List<RecargaCacheVO> recuperarSolicitudesPendientes();

	void registrarPeticionRecargaCombos();

	void registrarPeticionRecargaDatosSensibles();

	ResultBean refrescarCache(List<RecargaCacheVO> peticionesPendientes);
}
