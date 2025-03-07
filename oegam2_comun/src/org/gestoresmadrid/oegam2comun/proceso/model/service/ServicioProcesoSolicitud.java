package org.gestoresmadrid.oegam2comun.proceso.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.SolicitudesColaBean;

import escrituras.beans.ResultBean;

public interface ServicioProcesoSolicitud extends Serializable {

	public static String NOMBRE_HOST_PROC = "nombreHostProceso";

	ColaVO tomarSolicitud(String proceso, String cola, String nodo);

	void actualizarSolicitud(ColaVO colaVO);

	void borrarCola(ColaVO colaBBDD);

	ColaVO tomarSolicitudPorIdEnvio(Long idEnvio);

	List<Object[]> solicitudesAgrupadasPorHilos(String proceso);

	List<Object[]> solicitudesAgrupadasPorHilos();

	Integer buscarPeticionesPendientesMonitorizacion(String proceso);

	List<SolicitudesColaBean> getListaSolcitudesCola(ConsultaGestionColaBean consultaGestionCola);

	ResultBean eliminarSolicitudes(String idEnvio);

	ResultBean finalizarErrorServicio(String idEnvio);

	ResultBean finalizarError(String idEnvio);

	ResultBean reactivarSolicitud(String idEnvio);

	ResultBean establecerMaxPrioridad(String idEnvio);

}
