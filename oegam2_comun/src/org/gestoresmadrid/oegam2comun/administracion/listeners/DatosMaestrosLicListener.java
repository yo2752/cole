package org.gestoresmadrid.oegam2comun.administracion.listeners;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicDatosMaestrosRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DatosMaestrosLicListener implements ServletContextListener {

	private static final ILoggerOegam log = LoggerOegam.getLogger(DatosMaestrosLicListener.class);

	@Autowired
	ServicioLicDatosMaestrosRestWS servicioLicDatosMaestrosRestWS;

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			gestorDatosMaestrosLic.eliminarDatosMaestros();
		} catch (Throwable t) {
			log.error("Error al destruir el contexto.");
			log.error(t);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		String habilitarCargaDM = gestorPropiedades.valorPropertie("licencias.cam.habilitar.carga.datos.maestros");
		if ("SI".equals(habilitarCargaDM)) {
			gestorDatosMaestrosLic.iniciarDatosMaestros();

			String token = servicioLicDatosMaestrosRestWS.obtenerToken();
			if (StringUtils.isNotBlank(token)) {
				gestorDatosMaestrosLic.cargarToken(token);
			} else {
				log.error("No se ha podido obtener el token a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoActividad = servicioLicDatosMaestrosRestWS.obtenerTipoActividad();
			if (tipoActividad != null && !tipoActividad.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoActividad(tipoActividad);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos de actividad a través del servicio REST.");
			}

			List<DatoMaestroLicBean> provincias = servicioLicDatosMaestrosRestWS.obtenerProvincias();
			if (provincias != null && !provincias.isEmpty()) {
				gestorDatosMaestrosLic.cargarProvincias(provincias);
			} else {
				log.error("No se ha podido obtener la lista maestra de provincias a través del servicio REST.");
			}

			List<DatoMaestroLicBean> paises = servicioLicDatosMaestrosRestWS.obtenerPaises();
			if (paises != null && !paises.isEmpty()) {
				gestorDatosMaestrosLic.cargarPaises(paises);
			} else {
				log.error("No se ha podido obtener la lista maestra de países a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoVias = servicioLicDatosMaestrosRestWS.obtenerTipoViasRest();
			if (tipoVias != null && !tipoVias.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoVias(tipoVias);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos de vía a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoNumeracion = servicioLicDatosMaestrosRestWS.obtenerTipoNumeracion();
			if (tipoNumeracion != null && !tipoNumeracion.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoNumeracion(tipoNumeracion);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos numeración a través del servicio REST.");
			}

			List<DatoMaestroLicBean> zonaNormal = servicioLicDatosMaestrosRestWS.obtenerNormaZonal();
			if (zonaNormal != null && !zonaNormal.isEmpty()) {
				gestorDatosMaestrosLic.cargarNormaZonal(zonaNormal);
			} else {
				log.error("No se ha podido obtener la lista maestra de zona normal a través del servicio REST.");
			}

			List<DatoMaestroLicBean> nivelProteccion = servicioLicDatosMaestrosRestWS.obtenerNivelProteccion();
			if (nivelProteccion != null && !nivelProteccion.isEmpty()) {
				gestorDatosMaestrosLic.cargarNivelProteccion(nivelProteccion);
			} else {
				log.error("No se ha podido obtener la lista maestra de nivel de protección del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoPlanta = servicioLicDatosMaestrosRestWS.obtenerTipoPlanta();
			if (tipoPlanta != null && !tipoPlanta.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoPlanta(tipoPlanta);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipo planta a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoEdificacion = servicioLicDatosMaestrosRestWS.obtenerTipoEdificacion();
			if (tipoEdificacion != null && !tipoEdificacion.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoEdificacion(tipoEdificacion);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipo de edificación a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tiposUso = servicioLicDatosMaestrosRestWS.obtenerTiposUso();
			if (tiposUso != null && !tiposUso.isEmpty()) {
				gestorDatosMaestrosLic.cargarTiposUso(tiposUso);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos de uso a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoCombustible = servicioLicDatosMaestrosRestWS.obtenerTipoCombustible();
			if (tipoCombustible != null && !tipoCombustible.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoCombustible(tipoCombustible);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos de combustible a través del servicio REST.");
			}

			List<DatoMaestroLicBean> superficieNoComputable = servicioLicDatosMaestrosRestWS.obtenerSuperficieNoComputable();
			if (superficieNoComputable != null && !superficieNoComputable.isEmpty()) {
				gestorDatosMaestrosLic.cargarSuperficieNoComputable(superficieNoComputable);
			} else {
				log.error("No se ha podido obtener la lista maestra de superficie no computable a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoObra = servicioLicDatosMaestrosRestWS.obtenerTipoObra();
			if (tipoObra != null && !tipoObra.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoObra(tipoObra);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipo de obra a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tiposDocumentos = servicioLicDatosMaestrosRestWS.obtenerTiposDocumentos();
			if (tiposDocumentos != null && !tiposDocumentos.isEmpty()) {
				gestorDatosMaestrosLic.cargarTiposDocumentos(tiposDocumentos);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos de documento a través del servicio REST.");
			}

			List<DatoMaestroLicBean> formatoArchivosSoportados = servicioLicDatosMaestrosRestWS.obtenerFormatoArchivosSoportados();
			if (formatoArchivosSoportados != null && !formatoArchivosSoportados.isEmpty()) {
				gestorDatosMaestrosLic.cargarFormatoArchivoSoportados(formatoArchivosSoportados);
			} else {
				log.error("No se ha podido obtener la lista maestra de formatos de archivos no soportados a través del servicio REST.");
			}

			List<DatoMaestroLicBean> epigrafes = servicioLicDatosMaestrosRestWS.obtenerEpigrafes();
			if (epigrafes != null && !epigrafes.isEmpty()) {
				gestorDatosMaestrosLic.cargarEpigrafes(epigrafes);
			} else {
				log.error("No se ha podido obtener la lista maestra de epígrafes través del servicio REST.");
			}

			List<DatoMaestroLicBean> estadoInformacion = servicioLicDatosMaestrosRestWS.obtenerEstadoInformacion();
			if (estadoInformacion != null && !estadoInformacion.isEmpty()) {
				gestorDatosMaestrosLic.cargarEstadoInformacion(estadoInformacion);
			} else {
				log.error("No se ha podido obtener la lista maestra de estado de información a través del servicio REST.");
			}

			List<DatoMaestroLicBean> situacionLocal = servicioLicDatosMaestrosRestWS.obtenerSituacionLocal();
			if (situacionLocal != null && !situacionLocal.isEmpty()) {
				gestorDatosMaestrosLic.cargarSituacionLocal(situacionLocal);
			} else {
				log.error("No se ha podido obtener la lista maestra de situación del local a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoAcceso = servicioLicDatosMaestrosRestWS.obtenerTipoAcceso();
			if (tipoAcceso != null && !tipoAcceso.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoAcceso(tipoAcceso);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos de acceso a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tiposLocal = servicioLicDatosMaestrosRestWS.obtenerTiposLocal();
			if (tiposLocal != null && !tiposLocal.isEmpty()) {
				gestorDatosMaestrosLic.cargarTiposLocal(tiposLocal);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos local a través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoDocumento = servicioLicDatosMaestrosRestWS.obtenerTipoDocumento();
			if (tipoDocumento != null && !tipoDocumento.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoDocumento(tipoDocumento);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipo de documento través del servicio REST.");
			}

			List<DatoMaestroLicBean> tipoSujeto = servicioLicDatosMaestrosRestWS.obtenerTipoSujeto();
			if (tipoSujeto != null && !tipoSujeto.isEmpty()) {
				gestorDatosMaestrosLic.cargarTipoSujeto(tipoSujeto);
			} else {
				log.error("No se ha podido obtener la lista maestra de tipos de sujeto través del servicio REST.");
			}
		}
	}
}