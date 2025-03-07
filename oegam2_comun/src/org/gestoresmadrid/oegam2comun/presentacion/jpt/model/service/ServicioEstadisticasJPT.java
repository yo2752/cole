package org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service;

import java.text.ParseException;
import java.util.ArrayList;

import org.gestoresmadrid.oegam2comun.presentacion.jpt.view.beans.EstadisticasJPTBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.view.beans.TipoEstadisticasJPTBean;

import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ServicioEstadisticasJPT {


	public String sFormatoFechaIntervalo = "dd/MM/yyyy";

	public String propertieFechaJpt = "generacionJpt.fecha";
	
	ArrayList<TipoEstadisticasJPTBean> getlistaTipoEstadisticasJPT();

	ArrayList<TipoEstadisticasJPTBean> getlistaTipoEstadisticasJPTNoVisibles();

	ResultBean comprobarBean(EstadisticasJPTBean estadisticasPresentacionJPTBean);

	ResultBean guardarEstadisticas(EstadisticasJPTBean estadisticasPresentacionJPTBean);

	byte[] generarEstadisticas(Fecha fechaEstadistica, String jefaturaJpt) throws ParseException, Exception, OegamExcepcion;

	void getEstadisticasConIncidencias(EstadisticasJPTBean estadisticasJPTBean);

	String getJefaturaProvincialPorUsuario(long idUsuario);

	ResultBean comprobarFechaGeneracionEstadisticas(Fecha fechaEstadistica);
}
