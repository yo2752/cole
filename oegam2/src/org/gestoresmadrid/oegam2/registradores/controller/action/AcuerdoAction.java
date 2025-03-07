package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoAcuerdo;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAcuerdo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioCertifCargo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AcuerdoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CertifCargoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class AcuerdoAction extends ActionBase {

	private static final long serialVersionUID = 5126961373983953142L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioAcuerdo servicioAcuerdo;

	@Autowired
	private ServicioCertifCargo servicioCertifCargo;

	@Autowired
	UtilesColegiado utilesColegiado;

	private TramiteRegistroDto tramiteRegistro;

	private String tipoAcuerdo;

	private Long idAcuerdo;
	private String nifCargo;
	private String codigoCargo;

	public String alta() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "alta acuerdo.");

		String returnStruts = devolverRespuesta();

		if (new BigDecimal(EstadoTramiteRegistro.Finalizado_error.getValorEnum()).equals(tramiteRegistro.getEstado())) {
			UtilesRegistradores.aEstadoIniciado(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
		}

		if (!UtilesRegistradores.permitidaModificacion(tramiteRegistro.getEstado())) {
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			addActionError("El estado actual del trámite no permite la modificación de los datos guardados del mismo");
			return returnStruts;
		}

		AcuerdoDto acuerdo;
		if (TipoAcuerdo.Cese.getValorEnum().equalsIgnoreCase(tipoAcuerdo)) {
			acuerdo = getTramiteRegistro().getAcuerdoCese();
			getTramiteRegistro().setAcuerdoCese(new AcuerdoDto());
			acuerdo.setTipoAcuerdo(TipoAcuerdo.Cese.getValorEnum());
		} else {
			acuerdo = getTramiteRegistro().getAcuerdoNombramiento();
			getTramiteRegistro().setAcuerdoNombramiento(new AcuerdoDto());
			acuerdo.setTipoAcuerdo(TipoAcuerdo.Nombramiento.getValorEnum());
		}

		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		ResultBean result = servicioAcuerdo.guardarAcuerdo(acuerdo, tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (result != null) {
			if (!result.getError()) {
				if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
					AcuerdoDto acuerdoCer = (AcuerdoDto) result.getAttachment(ServicioAcuerdo.ACUERDO);
					servicioCertifCargo.guardarCertifCargo(acuerdoToCertif(acuerdoCer));
				}
				addActionMessage("Acuerdo dado de alta");
			} else {
				addActionError("Error al dar de alta un acuerdo");
				if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
					for (String mensaje : result.getListaMensajes())
						addActionError(mensaje);
				}
			}
		}
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "alta.");
		return returnStruts;
	}

	private CertifCargoDto acuerdoToCertif(AcuerdoDto acuerdo) {
		CertifCargoDto certifCargo = new CertifCargoDto();
		certifCargo.setCifSociedad(acuerdo.getCifSociedad());
		certifCargo.setCodigoCargo(acuerdo.getCodigoCargo());
		certifCargo.setIdTramiteRegistro(acuerdo.getIdTramiteRegistro());
		certifCargo.setNifCargo(acuerdo.getNifCargo());
		certifCargo.setNumColegiado(acuerdo.getNumColegiado());
		certifCargo.setSociedadCargo(acuerdo.getSociedadCargo());

		return certifCargo;
	}

	public String eliminar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminar acuerdo.");
		String returnStruts = devolverRespuesta();

		if (new BigDecimal(EstadoTramiteRegistro.Finalizado_error.getValorEnum()).equals(tramiteRegistro.getEstado())) {
			UtilesRegistradores.aEstadoIniciado(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
		}

		if (!UtilesRegistradores.permitidaModificacion(tramiteRegistro.getEstado())) {
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			addActionError("El estado actual del trámite no permite la modificación de los datos guardados del mismo");
			return returnStruts;
		}

		servicioAcuerdo.eliminarAcuerdo(idAcuerdo);

		if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			servicioCertifCargo.eliminarCertifCargo(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getSociedad().getNif(), nifCargo, codigoCargo);
		}

		addActionMessage("Acuerdo eliminado");
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "eliminar.");
		return returnStruts;
	}

	public String prepararModificacionNombramiento() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "modificación nombramiendo.");
		String returnStruts = devolverRespuesta();

		if (new BigDecimal(EstadoTramiteRegistro.Finalizado_error.getValorEnum()).equals(tramiteRegistro.getEstado())) {
			UtilesRegistradores.aEstadoIniciado(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
		}

		if (!UtilesRegistradores.permitidaModificacion(tramiteRegistro.getEstado())) {
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			addActionError("El estado actual del trámite no permite la modificación de los datos guardados del mismo");
			return returnStruts;
		}

		getTramiteRegistro().setAcuerdoNombramiento(servicioAcuerdo.getAcuerdoDto(idAcuerdo));

		addActionMessage("Acuerdo cargado");
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "modificación nombramiendo.");
		return returnStruts;
	}

	private String devolverRespuesta() {
		if (TipoTramiteRegistro.MODELO_1.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo1";
		} else if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo2";
		} else if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo3";
		} else if (TipoTramiteRegistro.MODELO_4.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo4";
		} else if (TipoTramiteRegistro.MODELO_5.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			return "tramiteRegistroModelo5";
		}
		return "";
	}

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public Long getIdAcuerdo() {
		return idAcuerdo;
	}

	public void setIdAcuerdo(Long idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}

	public String getNifCargo() {
		return nifCargo;
	}

	public void setNifCargo(String nifCargo) {
		this.nifCargo = nifCargo;
	}

	public String getCodigoCargo() {
		return codigoCargo;
	}

	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getTipoAcuerdo() {
		return tipoAcuerdo;
	}

	public void setTipoAcuerdo(String tipoAcuerdo) {
		this.tipoAcuerdo = tipoAcuerdo;
	}
}