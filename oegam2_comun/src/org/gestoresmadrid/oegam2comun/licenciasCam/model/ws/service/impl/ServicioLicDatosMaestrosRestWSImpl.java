package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicDatosMaestrosRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.LicenciasCamDatosMaestrosRest;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLicDatosMaestrosRestWSImpl implements ServicioLicDatosMaestrosRestWS {

	private static final long serialVersionUID = -2770346673208502061L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLicDatosMaestrosRestWSImpl.class);
	private static final String RESPONSE = "RESPONSE";

	@Autowired
	LicenciasCamDatosMaestrosRest licenciasCamDMRest;

	@Override
	public String obtenerToken() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerToken();
			if (resultado != null && !resultado.getError()) {
				return (String) resultado.getAttachment(RESPONSE);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el token para las peticion de las Licencias CAM, error: ", e);
		}
		return "";
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoActividad() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoActividad();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de actividad para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerProvincias() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerProvincias();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener las provincias para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerPaises() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerPaises();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener los países para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoViasRest() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoVias();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de vías para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoNumeracion() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoNumeracion();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de numeración para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerNormaZonal() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerNormaZonal();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener la norma zonal para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerNivelProteccion() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerNivelProteccion();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el nivel de protección para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoPlanta() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoPlanta();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo planta para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoEdificacion() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoEdificacion();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de edificación para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTiposUso() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTiposUso();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener los tipos de uso para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoCombustible() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoCombustible();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de combustible para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerSuperficieNoComputable() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerSuperficieNoComputable();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener la superficie no computable para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoObra() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoObra();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de obra para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTiposDocumentos() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTiposDocumentos();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), true);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener los tipos de documentos para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerFormatoArchivosSoportados() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerFormatoArchivosSoportados();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el formato de archivos soportados para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerEpigrafes() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerEpigrafes();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener los epígrafes para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerEstadoInformacion() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerEstadoInformacion();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el estado de la información para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerSituacionLocal() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerSituacionLocal();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener la situación del local para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoAcceso() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoAcceso();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de acceso de documentos para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTiposLocal() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTiposLocal();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener los tipos de local de archivos soportados para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoDocumento() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoDocumento();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuesta(resultado.getAttachment(RESPONSE), false);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de documento para las Licencias CAM, error: ", e);
		}
		return null;
	}

	@Override
	public List<DatoMaestroLicBean> obtenerTipoSujeto() {
		try {
			ResultBean resultado = licenciasCamDMRest.obtenerTipoSujeto();
			if (resultado != null && !resultado.getError()) {
				return gestionarRespuestaCid360(resultado.getAttachment(RESPONSE));
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener los tipos de sujeto para las Licencias CAM, error: ", e);
		}
		return null;
	}

	private List<DatoMaestroLicBean> gestionarRespuesta(Object response, boolean tiposDocumentos) {
		try {
			if (response != null) {
				List<LinkedHashMap<String, String>> lista = (List<LinkedHashMap<String, String>>) response;
				if (lista != null) {
					List<DatoMaestroLicBean> list = new ArrayList<DatoMaestroLicBean>();
					for (LinkedHashMap<String, String> linked : lista) {
						DatoMaestroLicBean bean = new DatoMaestroLicBean();
						if (tiposDocumentos) {
							if (!"Foliado".equals(linked.get("descripcion"))) {
								bean.setCodigo(linked.get("cdElemento"));
								bean.setDescripcion(linked.get("cdElemento") + " - " + linked.get("descripcion"));
								if (linked.get("valor1") != null) {
									Object obj = linked.get("valor1");
									String obligatorio = obj.toString();
									if ("1".equals(obligatorio)) {
										bean.setObligatorio(true);
										bean.setDescripcion(linked.get("cdElemento") + " - " + linked.get("descripcion") + " (*)");
									} else {
										bean.setObligatorio(false);
									}
								}
								list.add(bean);
							}
						} else {
							bean.setCodigo(linked.get("cdElemento"));
							bean.setDescripcion(linked.get("descripcion"));
							list.add(bean);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar los datos de la response", e);
		}
		return null;
	}

	private List<DatoMaestroLicBean> gestionarRespuestaCid360(Object response) {
		try {
			if (response != null) {
				List<LinkedHashMap<String, String>> lista = (List<LinkedHashMap<String, String>>) response;
				if (lista != null) {
					List<DatoMaestroLicBean> list = new ArrayList<DatoMaestroLicBean>();
					for (LinkedHashMap<String, String> linked : lista) {
						DatoMaestroLicBean bean = new DatoMaestroLicBean();
						bean.setCodigo(linked.get("codigo"));
						bean.setDescripcion(linked.get("descripcion"));
						list.add(bean);
					}
					return list;
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar los datos de la response", e);
		}
		return null;
	}
}