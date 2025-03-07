package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioConvocatoria;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMedioConvocatoria;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioReunion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MedioConvocatoriaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReunionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class ReunionAction extends ActionBase {

	private static final long serialVersionUID = -3472360959386768552L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioReunion servicioReunion;

	@Autowired
	private ServicioMedioConvocatoria servicioMedioConvocatoria;

	@Autowired
	private ServicioConvocatoria servicioConvocatoria;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private TramiteRegistroDto tramiteRegistro;

	private MedioConvocatoriaDto medioConvocatoria;

	private Long idMedio;

	public String guardar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "guardar reunion.");
		String returnStruts = "";
		try {
			returnStruts = devolverRespuesta();

			if (TipoTramiteRegistro.MODELO_3.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
				tramiteRegistro.getReunion().setMedios(servicioMedioConvocatoria.getMediosConvocatorias(tramiteRegistro.getIdTramiteRegistro()));
				if (null == tramiteRegistro.getReunion().getMedios() || tramiteRegistro.getReunion().getMedios().isEmpty()) {
					ReunionDto reunion = tramiteRegistro.getReunion();
					tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
					if (null != tramiteRegistro.getReunion() && null != tramiteRegistro.getReunion().getMedios())
						reunion.setMedios(tramiteRegistro.getReunion().getMedios());
					tramiteRegistro.setReunion(reunion);
					addActionError("Faltan datos obligatorios, añadir un medio de publicación al menos");
					log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO3_FIN + "seleccionar.");
					return returnStruts;
				}
			}
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			ResultBean result = servicioReunion.guardarReunion(tramiteRegistro.getReunion(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			if (result != null && !result.getError()) {
				servicioConvocatoria.guardarConvocatoria(tramiteRegistro.getReunion().getConvocatoria(), tramiteRegistro.getIdTramiteRegistro(), (Long) result.getAttachment(
						ServicioReunion.ID_REUNION));
				addActionMessage("Selección realizada");
			} else {
				addActionError("Error al seleccionar la reunión: " + result.getMensaje());
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		return returnStruts;
	}

	public String altaMedio() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "alta medio.");
		String returnStruts = devolverRespuesta();
		ReunionDto reunion = tramiteRegistro.getReunion();
		if (new BigDecimal(EstadoTramiteRegistro.Finalizado_error.getValorEnum()).equals(tramiteRegistro.getEstado())) {
			UtilesRegistradores.aEstadoIniciado(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
		}

		if (!UtilesRegistradores.permitidaModificacion(tramiteRegistro.getEstado())) {
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			addActionError("El estado actual del trámite no permite la modificación de los datos guardados del mismo");
			return returnStruts;
		}

		if (tramiteRegistro.getReunion() == null || tramiteRegistro.getReunion().getIdReunion() == null) {
			ResultBean result = servicioReunion.guardarReunion(tramiteRegistro.getReunion(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			if (result.getError()) {
				tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
				for (String mensaje : result.getListaMensajes()) {
					addActionError(mensaje);
				}
				addActionError("No se ha podido crear la reunión.");
				return returnStruts;
			}
			tramiteRegistro.getReunion().setIdReunion((Long) result.getAttachment(ServicioReunion.ID_REUNION));
		}

		ResultBean result = servicioMedioConvocatoria.guardarMedioConvocatoria(medioConvocatoria, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getReunion().getIdReunion());

		if (!result.getError()) {
			medioConvocatoria = new MedioConvocatoriaDto();
			addActionMessage("Medio dado de alta");
		} else {
			addActionError("Error al dar de alta un medio");
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}
		}
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		reunion.setMedios(tramiteRegistro.getReunion().getMedios());
		tramiteRegistro.setReunion(reunion);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "alta.");
		return returnStruts;
	}

	public String eliminarMedio() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminar medio.");
		String returnStruts = devolverRespuesta();
		ReunionDto reunion = tramiteRegistro.getReunion();
		if (new BigDecimal(EstadoTramiteRegistro.Finalizado_error.getValorEnum()).equals(tramiteRegistro.getEstado())) {
			UtilesRegistradores.aEstadoIniciado(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
		}

		if (!UtilesRegistradores.permitidaModificacion(tramiteRegistro.getEstado())) {
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
			addActionError("El estado actual del trámite no permite la modificación de los datos guardados del mismo");
			return returnStruts;
		}

		servicioMedioConvocatoria.eliminarMedio(idMedio, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getReunion().getIdReunion());

		addActionMessage("Medio eliminado");
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		reunion.setMedios(tramiteRegistro.getReunion().getMedios());
		tramiteRegistro.setReunion(reunion);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "eliminar.");
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

	public MedioConvocatoriaDto getMedioConvocatoria() {
		return medioConvocatoria;
	}

	public void setMedioConvocatoria(MedioConvocatoriaDto medioConvocatoria) {
		this.medioConvocatoria = medioConvocatoria;
	}

	public Long getIdMedio() {
		return idMedio;
	}

	public void setIdMedio(Long idMedio) {
		this.idMedio = idMedio;
	}
}