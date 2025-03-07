package org.gestoresmadrid.oegam2comun.licenciasCam.utiles;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipioCam;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoViaCam;
import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesLicencias implements Serializable {

	private static final long serialVersionUID = -5276547833815152101L;

	private static UtilesLicencias utilesLicencia;

	@Autowired
	ServicioMunicipioCam servicioMunicipioCam;

	@Autowired
	ServicioPueblo servicioPueblo;

	@Autowired
	ServicioTipoViaCam servicioTipoViaCam;

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	public UtilesLicencias() {
		super();
	}

	public static UtilesLicencias getInstance() {
		if (utilesLicencia == null) {
			utilesLicencia = new UtilesLicencias();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesLicencia);
		}
		return utilesLicencia;
	}

	public List<EstadoLicenciasCam> getEstadoLicencias() {
		List<EstadoLicenciasCam> listEstadoLicencias = Arrays.asList(EstadoLicenciasCam.values());
		Collections.sort(listEstadoLicencias, new ComparadorEstadoLicencias());
		return listEstadoLicencias;
	}

	public Boolean esEditable(LcTramiteDto lcTramiteDto) {
		if (lcTramiteDto != null) {
			if (lcTramiteDto.getEstado() == null) {
				return true;
			} else if (EstadoLicenciasCam.Iniciado.getValorEnum().equals(lcTramiteDto.getEstado().toString()) || EstadoLicenciasCam.Validado.getValorEnum().equals(lcTramiteDto.getEstado().toString())
					|| EstadoLicenciasCam.No_Tramitable.getValorEnum().equals(lcTramiteDto.getEstado().toString()) || EstadoLicenciasCam.Finalizado_Con_Error.getValorEnum().equals(lcTramiteDto
							.getEstado().toString()) || EstadoLicenciasCam.No_Presentada.getValorEnum().equals(lcTramiteDto.getEstado().toString())) {
				return true;
			}
		}
		return false;
	}

	public boolean esValidable(LcTramiteDto lcTramiteDto) {
		if (lcTramiteDto != null) {
			if (lcTramiteDto.getEstado() != null && EstadoLicenciasCam.Iniciado.getValorEnum().equals(lcTramiteDto.getEstado().toString())) {
				return true;
			}
		}
		return false;
	}

	public boolean esComprobable(LcTramiteDto lcTramiteDto) {
		if (lcTramiteDto != null) {
			if (lcTramiteDto.getEstado() != null && EstadoLicenciasCam.Validado.getValorEnum().equals(lcTramiteDto.getEstado().toString())) {
				return true;
			}
		}
		return false;
	}

	public boolean esEnviable(LcTramiteDto lcTramiteDto) {
		if (lcTramiteDto != null) {
			if (lcTramiteDto.getEstado() != null && EstadoLicenciasCam.Tramitable.getValorEnum().equals(lcTramiteDto.getEstado().toString())) {
				return true;
			}
		}
		return false;
	}

	public boolean esConsultable(LcTramiteDto lcTramiteDto) {
		if (lcTramiteDto != null) {
			if (lcTramiteDto.getEstado() != null && EstadoLicenciasCam.Entrada_Cam.getValorEnum().equals(lcTramiteDto.getEstado().toString())) {
				return true;
			}
		}
		return false;
	}

	public boolean esPresentable(LcTramiteDto lcTramiteDto) {
		if (lcTramiteDto != null) {
			if (lcTramiteDto.getEstado() != null && EstadoLicenciasCam.Registrada.getValorEnum().equals(lcTramiteDto.getEstado().toString())) {
				return true;
			}
		}
		return false;
	}

	public Boolean esAnulado(String estado) {
		return (estado.equalsIgnoreCase(EstadoLicenciasCam.Anulado.getValorEnum()));
	}

	private class ComparadorEstadoLicencias implements Comparator<EstadoLicenciasCam> {
		@Override
		public int compare(EstadoLicenciasCam o1, EstadoLicenciasCam o2) {
			return (o1.getNombreEnum()).compareTo(o2.getNombreEnum());
		}
	}

	public List<DatoMaestroLicBean> tipoActividad() {
		return gestorDatosMaestrosLic.obtenerTipoActividad();
	}

	public List<DatoMaestroLicBean> provincias() {
		return gestorDatosMaestrosLic.obtenerProvincias();
	}

	public List<DatoMaestroLicBean> paises() {
		return gestorDatosMaestrosLic.obtenerPaises();
	}

	public List<DatoMaestroLicBean> tipoNumeracion() {
		return gestorDatosMaestrosLic.obtenerTipoNumeracion();
	}

	public List<DatoMaestroLicBean> tipoVias() {
		return gestorDatosMaestrosLic.obtenerTipoVias();
	}

	public List<DatoMaestroLicBean> normaZonal() {
		return gestorDatosMaestrosLic.obtenerNormaZonal();
	}

	public List<DatoMaestroLicBean> nivelProteccion() {
		return gestorDatosMaestrosLic.obtenerNivelProteccion();
	}

	public List<DatoMaestroLicBean> tipoPlanta() {
		return gestorDatosMaestrosLic.obtenerTipoPlanta();
	}

	public String obtenerDescripcionPlanta(String valor) {
		if (StringUtils.isNotBlank(valor)) {
			for (DatoMaestroLicBean tipoPlanta : tipoPlanta()) {
				if (tipoPlanta.getCodigo().equals(valor)) {
					return tipoPlanta.getDescripcion();
				}
			}
		}
		return null;
	}

	public List<DatoMaestroLicBean> tipoEdificacion() {
		return gestorDatosMaestrosLic.obtenerTipoEdificacion();
	}

	public List<DatoMaestroLicBean> tiposUso() {
		return gestorDatosMaestrosLic.obtenerTiposUso();
	}

	public String obtenerDescripcionTipoUso(String valor) {
		if (StringUtils.isNotBlank(valor)) {
			for (DatoMaestroLicBean tipoUso : tiposUso()) {
				if (tipoUso.getCodigo().equals(valor)) {
					return tipoUso.getDescripcion();
				}
			}
		}
		return null;
	}

	public List<DatoMaestroLicBean> tipoCombustible() {
		return gestorDatosMaestrosLic.obtenerTipoCombustible();
	}

	public List<DatoMaestroLicBean> superficieNoComputable() {
		return gestorDatosMaestrosLic.obtenerSuperficieNoComputable();
	}

	public String obtenerDescripcionSuperficieNoComputable(String valor) {
		if (StringUtils.isNotBlank(valor)) {
			for (DatoMaestroLicBean superficieNoComputable : superficieNoComputable()) {
				if (superficieNoComputable.getCodigo().equals(valor)) {
					return superficieNoComputable.getDescripcion();
				}
			}
		}
		return null;
	}

	public List<DatoMaestroLicBean> tipoObra() {
		return gestorDatosMaestrosLic.obtenerTipoObra();
	}

	public String obtenerDescripcionTipoObra(String valor) {
		if (StringUtils.isNotBlank(valor)) {
			for (DatoMaestroLicBean tipoObra : tipoObra()) {
				if (tipoObra.getCodigo().equals(valor)) {
					return tipoObra.getDescripcion();
				}
			}
		}
		return null;
	}

	public List<DatoMaestroLicBean> tiposDocumentos() {
		return gestorDatosMaestrosLic.obtenerTiposDocumentos();
	}

	public List<DatoMaestroLicBean> formatoArchivosSoportados() {
		return gestorDatosMaestrosLic.obtenerFormatoArchivoSoportados();
	}

	public List<DatoMaestroLicBean> epigrafes() {
		return gestorDatosMaestrosLic.obtenerEpigrafes();
	}

	public String obtenerDescripcionEpigrafe(String valor) {
		if (StringUtils.isNotBlank(valor)) {
			for (DatoMaestroLicBean epigrafe : epigrafes()) {
				if (epigrafe.getCodigo().equals(valor)) {
					return epigrafe.getDescripcion();
				}
			}
		}
		return null;
	}

	public List<DatoMaestroLicBean> estadoInformacion() {
		return gestorDatosMaestrosLic.obtenerEstadoInformacion();
	}

	public List<DatoMaestroLicBean> situacionLocal() {
		return gestorDatosMaestrosLic.obtenerSituacionLocal();
	}

	public List<DatoMaestroLicBean> tipoAcceso() {
		return gestorDatosMaestrosLic.obtenerTipoAcceso();
	}

	public List<DatoMaestroLicBean> tiposLocal() {
		return gestorDatosMaestrosLic.obtenerTiposLocal();
	}

	public List<DatoMaestroLicBean> tipoDocumento() {
		return gestorDatosMaestrosLic.obtenerTipoDocumento();
	}

	public List<DatoMaestroLicBean> tipoSujeto() {
		return gestorDatosMaestrosLic.obtenerTipoSujeto();
	}

	public String obtenerDescripcionTipoSujeto(String valor) {
		if (StringUtils.isNotBlank(valor)) {
			for (DatoMaestroLicBean tipo : tipoSujeto()) {
				if (tipo.getCodigo().equals(valor)) {
					return tipo.getDescripcion();
				}
			}
		}
		return null;
	}
}
