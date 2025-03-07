package org.gestoresmadrid.oegam2comun.licenciasCam.gestor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;
import org.springframework.stereotype.Component;

@Component
public class GestorDatosMaestrosLic implements Serializable {

	private static final long serialVersionUID = 820346982038011557L;

	private static String token;

	private static List<DatoMaestroLicBean> tipoActividad;
	private static List<DatoMaestroLicBean> provincias;
	private static List<DatoMaestroLicBean> paises;
	private static List<DatoMaestroLicBean> tipoVias;
	private static List<DatoMaestroLicBean> tipoNumeracion;
	private static List<DatoMaestroLicBean> normaZonal;
	private static List<DatoMaestroLicBean> nivelProteccion;
	private static List<DatoMaestroLicBean> tipoPlanta;
	private static List<DatoMaestroLicBean> tipoEdificacion;
	private static List<DatoMaestroLicBean> tiposUso;
	private static List<DatoMaestroLicBean> tipoCombustible;
	private static List<DatoMaestroLicBean> superficieNoComputable;
	private static List<DatoMaestroLicBean> tipoObra;
	private static List<DatoMaestroLicBean> tiposDocumentos;
	private static List<DatoMaestroLicBean> tiposDocumentosObligatorios;
	private static List<DatoMaestroLicBean> formatoArchivoSoportados;
	private static List<DatoMaestroLicBean> epigrafes;
	private static List<DatoMaestroLicBean> estadoInformacion;
	private static List<DatoMaestroLicBean> situacionLocal;
	private static List<DatoMaestroLicBean> tipoAcceso;
	private static List<DatoMaestroLicBean> tiposLocal;
	private static List<DatoMaestroLicBean> tipoDocumento;
	private static List<DatoMaestroLicBean> tipoSujeto;

	public void iniciarDatosMaestros() {
		token = "";

		tipoActividad = new ArrayList<DatoMaestroLicBean>();
		provincias = new ArrayList<DatoMaestroLicBean>();
		paises = new ArrayList<DatoMaestroLicBean>();
		tipoVias = new ArrayList<DatoMaestroLicBean>();
		tipoNumeracion = new ArrayList<DatoMaestroLicBean>();
		normaZonal = new ArrayList<DatoMaestroLicBean>();
		nivelProteccion = new ArrayList<DatoMaestroLicBean>();
		tipoPlanta = new ArrayList<DatoMaestroLicBean>();
		tipoEdificacion = new ArrayList<DatoMaestroLicBean>();
		tiposUso = new ArrayList<DatoMaestroLicBean>();
		tipoCombustible = new ArrayList<DatoMaestroLicBean>();
		superficieNoComputable = new ArrayList<DatoMaestroLicBean>();
		tipoObra = new ArrayList<DatoMaestroLicBean>();
		tiposDocumentos = new ArrayList<DatoMaestroLicBean>();
		tiposDocumentosObligatorios = new ArrayList<DatoMaestroLicBean>();
		formatoArchivoSoportados = new ArrayList<DatoMaestroLicBean>();
		epigrafes = new ArrayList<DatoMaestroLicBean>();
		// Censo
		estadoInformacion = new ArrayList<DatoMaestroLicBean>();
		situacionLocal = new ArrayList<DatoMaestroLicBean>();
		tipoAcceso = new ArrayList<DatoMaestroLicBean>();
		tiposLocal = new ArrayList<DatoMaestroLicBean>();
		// Cid360
		tipoDocumento = new ArrayList<DatoMaestroLicBean>();
		tipoSujeto = new ArrayList<DatoMaestroLicBean>();
	}

	public void cargarToken(String datoToken) {
		token = datoToken;
	}

	public void cargarTipoActividad(List<DatoMaestroLicBean> datosTipoActividad) {
		tipoActividad = datosTipoActividad;
	}

	public void cargarProvincias(List<DatoMaestroLicBean> datosProvincias) {
		provincias = datosProvincias;
	}

	public void cargarPaises(List<DatoMaestroLicBean> datosPaises) {
		paises = datosPaises;
	}

	public void cargarTipoVias(List<DatoMaestroLicBean> datosTipoVias) {
		tipoVias = datosTipoVias;
	}

	public void cargarTipoNumeracion(List<DatoMaestroLicBean> datosTipoNumeracion) {
		tipoNumeracion = datosTipoNumeracion;
	}

	public void cargarNormaZonal(List<DatoMaestroLicBean> datosNormaZonal) {
		normaZonal = datosNormaZonal;
	}

	public void cargarNivelProteccion(List<DatoMaestroLicBean> datosNivelProteccion) {
		nivelProteccion = datosNivelProteccion;
	}

	public void cargarTipoPlanta(List<DatoMaestroLicBean> datosTipoPlanta) {
		tipoPlanta = datosTipoPlanta;
	}

	public void cargarTipoEdificacion(List<DatoMaestroLicBean> datosTipoEdificacion) {
		tipoEdificacion = datosTipoEdificacion;
	}

	public void cargarTiposUso(List<DatoMaestroLicBean> datosTiposUso) {
		tiposUso = datosTiposUso;
	}

	public void cargarTipoCombustible(List<DatoMaestroLicBean> datosTipoCombustible) {
		tipoCombustible = datosTipoCombustible;
	}

	public void cargarSuperficieNoComputable(List<DatoMaestroLicBean> datosSuperficieNoComputable) {
		superficieNoComputable = datosSuperficieNoComputable;
	}

	public void cargarTipoObra(List<DatoMaestroLicBean> datosTipoObra) {
		tipoObra = datosTipoObra;
	}

	public void cargarTiposDocumentos(List<DatoMaestroLicBean> datosTiposDocumentos) {
		tiposDocumentos = datosTiposDocumentos;
		if (datosTiposDocumentos != null && !datosTiposDocumentos.isEmpty()) {
			for (DatoMaestroLicBean tipoDocumento : datosTiposDocumentos) {
				if (tipoDocumento.isObligatorio()) {
					tiposDocumentosObligatorios.add(tipoDocumento);
				}
			}
		}
	}

	public void cargarFormatoArchivoSoportados(List<DatoMaestroLicBean> datosFormatoArchivoSoportados) {
		formatoArchivoSoportados = datosFormatoArchivoSoportados;
	}

	public void cargarEpigrafes(List<DatoMaestroLicBean> datosEpigrafes) {
		epigrafes = datosEpigrafes;
	}

	public void cargarEstadoInformacion(List<DatoMaestroLicBean> datosEstadoInformacion) {
		estadoInformacion = datosEstadoInformacion;
	}

	public void cargarSituacionLocal(List<DatoMaestroLicBean> datosSituacionLocal) {
		situacionLocal = datosSituacionLocal;
	}

	public void cargarTipoAcceso(List<DatoMaestroLicBean> datosTipoAcceso) {
		tipoAcceso = datosTipoAcceso;
	}

	public void cargarTiposLocal(List<DatoMaestroLicBean> datosTiposLocal) {
		tiposLocal = datosTiposLocal;
	}

	public void cargarTipoDocumento(List<DatoMaestroLicBean> datosTipoDocumento) {
		tipoDocumento = datosTipoDocumento;
	}

	public void cargarTipoSujeto(List<DatoMaestroLicBean> datosTipoSujeto) {
		tipoSujeto = datosTipoSujeto;
	}

	public void refrescarToken(String datoToken) {
		if (StringUtils.isNotBlank(datoToken)) {
			token = datoToken;
		}
	}

	public void refrescarTipoActividad(List<DatoMaestroLicBean> datosTipoActividad) {
		if (datosTipoActividad != null && !datosTipoActividad.isEmpty()) {
			tipoActividad = datosTipoActividad;
		}
	}

	public void refrescarProvincias(List<DatoMaestroLicBean> datosProvincias) {
		if (datosProvincias != null && !datosProvincias.isEmpty()) {
			provincias = datosProvincias;
		}
	}

	public void refrescarPaises(List<DatoMaestroLicBean> datosPaises) {
		if (datosPaises != null && !datosPaises.isEmpty()) {
			paises = datosPaises;
		}
	}

	public void refrescarTipoVias(List<DatoMaestroLicBean> datosTipoVias) {
		if (datosTipoVias != null && !datosTipoVias.isEmpty()) {
			tipoVias = datosTipoVias;
		}
	}

	public void refrescarTipoNumeracion(List<DatoMaestroLicBean> datosTipoNumeracion) {
		if (datosTipoNumeracion != null && !datosTipoNumeracion.isEmpty()) {
			tipoNumeracion = datosTipoNumeracion;
		}
	}

	public void refrescarNormaZonal(List<DatoMaestroLicBean> datosNormaZonal) {
		if (datosNormaZonal != null && !datosNormaZonal.isEmpty()) {
			normaZonal = datosNormaZonal;
		}
	}

	public void refrescarNivelProteccion(List<DatoMaestroLicBean> datosNivelProteccion) {
		if (datosNivelProteccion != null && !datosNivelProteccion.isEmpty()) {
			nivelProteccion = datosNivelProteccion;
		}
	}

	public void refrescarTipoPlanta(List<DatoMaestroLicBean> datosTipoPlanta) {
		if (datosTipoPlanta != null && !datosTipoPlanta.isEmpty()) {
			tipoPlanta = datosTipoPlanta;
		}
	}

	public void refrescarTipoEdificacion(List<DatoMaestroLicBean> datosTipoEdificacion) {
		if (datosTipoEdificacion != null && !datosTipoEdificacion.isEmpty()) {
			tipoEdificacion = datosTipoEdificacion;
		}
	}

	public void refrescarTiposUso(List<DatoMaestroLicBean> datosTiposUso) {
		if (datosTiposUso != null && !datosTiposUso.isEmpty()) {
			tiposUso = datosTiposUso;
		}
	}

	public void refrescarTipoCombustible(List<DatoMaestroLicBean> datosTipoCombustible) {
		if (datosTipoCombustible != null && !datosTipoCombustible.isEmpty()) {
			tipoCombustible = datosTipoCombustible;
		}
	}

	public void refrescarSuperficieNoComputable(List<DatoMaestroLicBean> datosSuperficieNoComputable) {
		if (datosSuperficieNoComputable != null && !datosSuperficieNoComputable.isEmpty()) {
			superficieNoComputable = datosSuperficieNoComputable;
		}
	}

	public void refrescarTipoObra(List<DatoMaestroLicBean> datosTipoObra) {
		if (datosTipoObra != null && !datosTipoObra.isEmpty()) {
			tipoObra = datosTipoObra;
		}
	}

	public void refrescarTiposDocumentos(List<DatoMaestroLicBean> datosTiposDocumentos) {
		if (datosTiposDocumentos != null && !datosTiposDocumentos.isEmpty()) {
			tiposDocumentos = datosTiposDocumentos;
			for (DatoMaestroLicBean tipoDocumento : datosTiposDocumentos) {
				if (tipoDocumento.isObligatorio()) {
					tiposDocumentosObligatorios.add(tipoDocumento);
				}
			}
		}
	}

	public void refrescarFormatoArchivoSoportados(List<DatoMaestroLicBean> datosFormatoArchivoSoportados) {
		if (datosFormatoArchivoSoportados != null && !datosFormatoArchivoSoportados.isEmpty()) {
			formatoArchivoSoportados = datosFormatoArchivoSoportados;
		}
	}

	public void refrescarEpigrafes(List<DatoMaestroLicBean> datosEpigrafes) {
		if (datosEpigrafes != null && !datosEpigrafes.isEmpty()) {
			epigrafes = datosEpigrafes;
		}
	}

	public void refrescarEstadoInformacion(List<DatoMaestroLicBean> datosEstadoInformacion) {
		if (datosEstadoInformacion != null && !datosEstadoInformacion.isEmpty()) {
			estadoInformacion = datosEstadoInformacion;
		}
	}

	public void refrescarSituacionLocal(List<DatoMaestroLicBean> datosSituacionLocal) {
		if (datosSituacionLocal != null && !datosSituacionLocal.isEmpty()) {
			situacionLocal = datosSituacionLocal;
		}
	}

	public void refrescarTipoAcceso(List<DatoMaestroLicBean> datosTipoAcceso) {
		if (datosTipoAcceso != null && !datosTipoAcceso.isEmpty()) {
			tipoAcceso = datosTipoAcceso;
		}
	}

	public void refrescarTiposLocal(List<DatoMaestroLicBean> datosTiposLocal) {
		if (datosTiposLocal != null && !datosTiposLocal.isEmpty()) {
			tiposLocal = datosTiposLocal;
		}
	}

	public void refrescarTipoDocumento(List<DatoMaestroLicBean> datosTipoDocumento) {
		if (datosTipoDocumento != null && !datosTipoDocumento.isEmpty()) {
			tipoDocumento = datosTipoDocumento;
		}
	}

	public void refrescarTipoSujeto(List<DatoMaestroLicBean> datosTipoSujeto) {
		if (datosTipoSujeto != null && !datosTipoSujeto.isEmpty()) {
			tipoSujeto = datosTipoSujeto;
		}
	}

	public String obtenerToken() {
		return token;
	}

	public List<DatoMaestroLicBean> obtenerTipoActividad() {
		return tipoActividad;
	}

	public List<DatoMaestroLicBean> obtenerProvincias() {
		return provincias;
	}

	public List<DatoMaestroLicBean> obtenerPaises() {
		return paises;
	}

	public List<DatoMaestroLicBean> obtenerTipoNumeracion() {
		return tipoNumeracion;
	}

	public List<DatoMaestroLicBean> obtenerTipoVias() {
		return tipoVias;
	}

	public List<DatoMaestroLicBean> obtenerNormaZonal() {
		return normaZonal;
	}

	public List<DatoMaestroLicBean> obtenerNivelProteccion() {
		return nivelProteccion;
	}

	public List<DatoMaestroLicBean> obtenerTipoPlanta() {
		return tipoPlanta;
	}

	public List<DatoMaestroLicBean> obtenerTipoEdificacion() {
		return tipoEdificacion;
	}

	public List<DatoMaestroLicBean> obtenerTiposUso() {
		return tiposUso;
	}

	public List<DatoMaestroLicBean> obtenerTipoCombustible() {
		return tipoCombustible;
	}

	public List<DatoMaestroLicBean> obtenerSuperficieNoComputable() {
		return superficieNoComputable;
	}

	public List<DatoMaestroLicBean> obtenerTipoObra() {
		return tipoObra;
	}

	public List<DatoMaestroLicBean> obtenerTiposDocumentos() {
		return tiposDocumentos;
	}

	public List<DatoMaestroLicBean> obtenerTiposDocumentosObligatorios() {
		return tiposDocumentosObligatorios;
	}

	public List<DatoMaestroLicBean> obtenerFormatoArchivoSoportados() {
		return formatoArchivoSoportados;
	}

	public List<DatoMaestroLicBean> obtenerEpigrafes() {
		return epigrafes;
	}

	public List<DatoMaestroLicBean> obtenerEstadoInformacion() {
		return estadoInformacion;
	}

	public List<DatoMaestroLicBean> obtenerSituacionLocal() {
		return situacionLocal;
	}

	public List<DatoMaestroLicBean> obtenerTipoAcceso() {
		return tipoAcceso;
	}

	public List<DatoMaestroLicBean> obtenerTiposLocal() {
		return tiposLocal;
	}

	public List<DatoMaestroLicBean> obtenerTipoDocumento() {
		return tipoDocumento;
	}

	public List<DatoMaestroLicBean> obtenerTipoSujeto() {
		return tipoSujeto;
	}

	public void eliminarDatosMaestros() {
		token = "";

		tipoActividad = null;
		provincias = null;
		paises = null;
		tipoVias = null;
		tipoNumeracion = null;
		normaZonal = null;
		nivelProteccion = null;
		tipoPlanta = null;
		tipoEdificacion = null;
		tiposUso = null;
		tipoCombustible = null;
		superficieNoComputable = null;
		tipoObra = null;
		tiposDocumentos = null;
		tiposDocumentosObligatorios = null;
		formatoArchivoSoportados = null;
		epigrafes = null;
		estadoInformacion = null;
		situacionLocal = null;
		tipoAcceso = null;
		tiposLocal = null;
		tipoDocumento = null;
		tipoSujeto = null;
	}
}
