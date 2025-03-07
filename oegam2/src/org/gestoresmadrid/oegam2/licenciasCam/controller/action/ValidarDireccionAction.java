package org.gestoresmadrid.oegam2.licenciasCam.controller.action;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicBDCRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Numero;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Pais;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Poblacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Provincia;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Vial;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoValidacionDireccionBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class ValidarDireccionAction extends ActionBase {

	private static final long serialVersionUID = -5166487647992448919L;

	private static Logger log = Logger.getLogger(ValidarDireccionAction.class);

	private static final String ALTA_LICENCIAS_CAM = "altaSolicitudLicencia";
	private static final String SUCCESS = "success";

	private LcTramiteDto lcTramiteDto;

	private List<Pais> paises;

	private List<Provincia> provincias;

	private List<Poblacion> poblaciones;

	private List<Vial> viales;

	private List<Numero> numeros;

	private String estadoCerrarPopUp = "0";

	private String nomPais;
	private String nomProvincia;
	private String nomPoblacion;
	private String nomVial;
	private String numero;

	private String tipoInterviniente;

	@Autowired
	ServicioLicBDCRestWS servicioLicBDCRestWS;

	public String inicio() {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			ResultadoValidacionDireccionBean resultValid = new ResultadoValidacionDireccionBean(Boolean.FALSE);
			if (StringUtils.isNotBlank(tipoInterviniente)) {
				resultValid = validarInterviniente();
			} else {
				resultValid = validarEmplazamiento();
			}
			if (resultValid != null && !resultValid.getError()) {
				if (resultValid.getErrorPais() != null && resultValid.getErrorPais()) {
					paises = resultValid.getPaises();
				} else if (resultValid.getErrorProvincia() != null && resultValid.getErrorProvincia()) {
					provincias = resultValid.getProvincias();
				} else if (resultValid.getErrorPoblacion() != null && resultValid.getErrorPoblacion()) {
					poblaciones = resultValid.getPoblaciones();
				} else if (resultValid.getErrorVial() != null && resultValid.getErrorVial()) {
					viales = resultValid.getViales();
				} else if (resultValid.getErrorNumero() != null && resultValid.getErrorNumero()) {
					numeros = resultValid.getNumeros();
				} else {
					estadoCerrarPopUp = "1";
					addActionMessage("Validación de la dirección correcta");
					return ALTA_LICENCIAS_CAM;
				}
			} else {
				estadoCerrarPopUp = "1";
				addActionError(resultValid.getMensaje());
				return ALTA_LICENCIAS_CAM;
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Error al validar la dirección");
			log.error(e.getMessage());
		}
		if (resultado != null && resultado.getError()) {
			estadoCerrarPopUp = "1";
			addActionError(resultado.getMensaje());
		}
		return SUCCESS;
	}

	private ResultadoValidacionDireccionBean validarEmplazamiento() {
		ResultadoValidacionDireccionBean resultValid = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		if (lcTramiteDto != null && lcTramiteDto.getLcIdDirEmplazamiento() != null) {
			resultValid = servicioLicBDCRestWS.validarDireccionRest(lcTramiteDto.getLcIdDirEmplazamiento());
		} else {
			resultValid.setError(Boolean.TRUE);
			resultValid.setMensaje("No existe la dirección o dirección incorrecta para poder validarla");
		}
		return resultValid;
	}

	private ResultadoValidacionDireccionBean validarInterviniente() {
		ResultadoValidacionDireccionBean resultValid = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		if (lcTramiteDto != null) {
			LcDireccionDto direccion = null;
			if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equals(tipoInterviniente)) {
				if (lcTramiteDto.getIntervinienteInteresado() != null && lcTramiteDto.getIntervinienteInteresado().getLcDireccion() != null) {
					direccion = lcTramiteDto.getIntervinienteInteresado().getLcDireccion();
				} else {
					resultValid.setError(Boolean.TRUE);
					resultValid.setMensaje("No existe la dirección o dirección incorrecta para poder validarla");
				}
			} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equals(tipoInterviniente)) {
				if (lcTramiteDto.getIntervinienteRepresentante() != null && lcTramiteDto.getIntervinienteRepresentante().getLcDireccion() != null) {
					direccion = lcTramiteDto.getIntervinienteRepresentante().getLcDireccion();
				} else {
					resultValid.setError(Boolean.TRUE);
					resultValid.setMensaje("No existe la dirección o dirección incorrecta para poder validarla");
				}
			} else if (TipoInterviniente.Notificacion.getValorEnum().equals(tipoInterviniente)) {
				if (lcTramiteDto.getIntervinienteNotificacion() != null && lcTramiteDto.getIntervinienteNotificacion().getLcDireccion() != null) {
					direccion = lcTramiteDto.getIntervinienteNotificacion().getLcDireccion();
				} else {
					resultValid.setError(Boolean.TRUE);
					resultValid.setMensaje("No existe la dirección o dirección incorrecta para poder validarla");
				}
			}
			if (direccion != null) {
				resultValid = servicioLicBDCRestWS.validarDireccionCompletaRest(direccion);
			}
		} else {
			resultValid.setError(Boolean.TRUE);
			resultValid.setMensaje("No existe la dirección o dirección incorrecta para poder validarla");
		}
		return resultValid;
	}

	public String seleccionarPais() {
		// Solo para los intervinientes
		if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteInteresado().getLcDireccion().setPais(nomPais);
		} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteRepresentante().getLcDireccion().setPais(nomPais);
		} else if (TipoInterviniente.Notificacion.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteNotificacion().getLcDireccion().setPais(nomPais);
		}
		return ALTA_LICENCIAS_CAM;
	}

	public String seleccionarProvincia() {
		// Solo para los intervinientes
		if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteInteresado().getLcDireccion().setProvincia(nomProvincia);
		} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteRepresentante().getLcDireccion().setProvincia(nomProvincia);
		} else if (TipoInterviniente.Notificacion.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteNotificacion().getLcDireccion().setProvincia(nomProvincia);
		}
		return ALTA_LICENCIAS_CAM;
	}

	public String seleccionarPoblacion() {
		// Solo para los intervinientes
		if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteInteresado().getLcDireccion().setPoblacion(nomPoblacion);
		} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteRepresentante().getLcDireccion().setPoblacion(nomPoblacion);
		} else if (TipoInterviniente.Notificacion.getValorEnum().equals(tipoInterviniente)) {
			lcTramiteDto.getIntervinienteNotificacion().getLcDireccion().setPoblacion(nomPoblacion);
		}
		return ALTA_LICENCIAS_CAM;
	}

	public String seleccionarVial() {
		if (StringUtils.isNotBlank(tipoInterviniente)) {
			if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equals(tipoInterviniente)) {
				lcTramiteDto.getIntervinienteInteresado().getLcDireccion().setNombreVia(nomVial);
			} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equals(tipoInterviniente)) {
				lcTramiteDto.getIntervinienteRepresentante().getLcDireccion().setNombreVia(nomVial);
			} else if (TipoInterviniente.Notificacion.getValorEnum().equals(tipoInterviniente)) {
				lcTramiteDto.getIntervinienteNotificacion().getLcDireccion().setNombreVia(nomVial);
			}
		} else {
			lcTramiteDto.getLcIdDirEmplazamiento().setNombreVia(nomVial);
		}
		return ALTA_LICENCIAS_CAM;
	}

	public String seleccionarNumero() {
		if (StringUtils.isNotBlank(tipoInterviniente)) {
			if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equals(tipoInterviniente)) {
				lcTramiteDto.getIntervinienteInteresado().getLcDireccion().setNumCalle(new BigDecimal(numero));
			} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equals(tipoInterviniente)) {
				lcTramiteDto.getIntervinienteRepresentante().getLcDireccion().setNumCalle(new BigDecimal(numero));
			} else if (TipoInterviniente.Notificacion.getValorEnum().equals(tipoInterviniente)) {
				lcTramiteDto.getIntervinienteNotificacion().getLcDireccion().setNumCalle(new BigDecimal(numero));
			}
		} else {
			lcTramiteDto.getLcIdDirEmplazamiento().setNumCalle(new BigDecimal(numero));
		}
		return ALTA_LICENCIAS_CAM;
	}

	public LcTramiteDto getLcTramiteDto() {
		return lcTramiteDto;
	}

	public void setLcTramiteDto(LcTramiteDto lcTramiteDto) {
		this.lcTramiteDto = lcTramiteDto;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public List<Provincia> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	public List<Poblacion> getPoblaciones() {
		return poblaciones;
	}

	public void setPoblaciones(List<Poblacion> poblaciones) {
		this.poblaciones = poblaciones;
	}

	public List<Vial> getViales() {
		return viales;
	}

	public void setViales(List<Vial> viales) {
		this.viales = viales;
	}

	public List<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	public ServicioLicBDCRestWS getServicioLicBDCRestWS() {
		return servicioLicBDCRestWS;
	}

	public void setServicioLicBDCRestWS(ServicioLicBDCRestWS servicioLicBDCRestWS) {
		this.servicioLicBDCRestWS = servicioLicBDCRestWS;
	}

	public String getEstadoCerrarPopUp() {
		return estadoCerrarPopUp;
	}

	public void setEstadoCerrarPopUp(String estadoCerrarPopUp) {
		this.estadoCerrarPopUp = estadoCerrarPopUp;
	}

	public String getNomPais() {
		return nomPais;
	}

	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}

	public String getNomProvincia() {
		return nomProvincia;
	}

	public void setNomProvincia(String nomProvincia) {
		this.nomProvincia = nomProvincia;
	}

	public String getNomPoblacion() {
		return nomPoblacion;
	}

	public void setNomPoblacion(String nomPoblacion) {
		this.nomPoblacion = nomPoblacion;
	}

	public String getNomVial() {
		return nomVial;
	}

	public void setNomVial(String nomVial) {
		this.nomVial = nomVial;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
}
