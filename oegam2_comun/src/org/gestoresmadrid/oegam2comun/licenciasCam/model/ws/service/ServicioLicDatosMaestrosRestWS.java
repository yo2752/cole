package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;

public interface ServicioLicDatosMaestrosRestWS extends Serializable {

	String obtenerToken();

	List<DatoMaestroLicBean> obtenerTipoActividad();

	List<DatoMaestroLicBean> obtenerProvincias();

	List<DatoMaestroLicBean> obtenerPaises();

	List<DatoMaestroLicBean> obtenerTipoViasRest();

	List<DatoMaestroLicBean> obtenerTipoNumeracion();

	List<DatoMaestroLicBean> obtenerNormaZonal();

	List<DatoMaestroLicBean> obtenerNivelProteccion();

	List<DatoMaestroLicBean> obtenerTipoPlanta();

	List<DatoMaestroLicBean> obtenerTipoEdificacion();

	List<DatoMaestroLicBean> obtenerTiposUso();

	List<DatoMaestroLicBean> obtenerTipoCombustible();

	List<DatoMaestroLicBean> obtenerSuperficieNoComputable();

	List<DatoMaestroLicBean> obtenerTipoObra();

	List<DatoMaestroLicBean> obtenerTiposDocumentos();

	List<DatoMaestroLicBean> obtenerFormatoArchivosSoportados();

	List<DatoMaestroLicBean> obtenerEpigrafes();

	List<DatoMaestroLicBean> obtenerEstadoInformacion();

	List<DatoMaestroLicBean> obtenerSituacionLocal();

	List<DatoMaestroLicBean> obtenerTipoAcceso();

	List<DatoMaestroLicBean> obtenerTiposLocal();

	List<DatoMaestroLicBean> obtenerTipoDocumento();

	List<DatoMaestroLicBean> obtenerTipoSujeto();
}
