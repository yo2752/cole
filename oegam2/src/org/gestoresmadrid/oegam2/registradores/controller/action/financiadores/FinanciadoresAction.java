package org.gestoresmadrid.oegam2.registradores.controller.action.financiadores;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFinanciador;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class FinanciadoresAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9149856648361108723L;
	private static final String RESULTADO_EDIT = "editar";
	private static final String POP_UP_ANIADIR_REPRESENTANTE_FINANCIADOR = "popUpRepresentanteFinanciadorRegistro";
	public static final String INTERVINIENTE_GUARDADO = "INTERVINIENTEGUARDADO";

	private static ILoggerOegam log = LoggerOegam.getLogger(FinanciadoresAction.class);

	private String identificador;
	private String idRepresentante;
	private IntervinienteRegistroDto financiador;
	private List<IntervinienteRegistroDto> representantes;
	private IntervinienteRegistroDto representante;

	@Autowired
	private ServicioFinanciador servicioFinanciador;

	@Resource
	private ModelPagination modeloFinanciadoresPaginated;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		log.debug("start");
		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {
			financiador = servicioFinanciador.getFinanciador(identificador);
			financiador.setDireccion(servicioDireccion.getDireccionDto(financiador.getIdDireccion()));
		}
		if (financiador != null)
			representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
		log.debug("return");
		return RESULTADO_EDIT;
	}

	public String actualizar() {
		log.debug("start");
		try {
			ResultRegistro resultObject;
			if (getFinanciador().getIdInterviniente() == 0) {
				financiador.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				financiador.getPersona().setNif(financiador.getNif());
				financiador.getPersona().setNumColegiado(financiador.getNumColegiado());
				financiador.getPersona().setEstado("1");
			} else {
				financiador.setPersona(servicioPersona.getPersona(financiador.getNif(), financiador.getNumColegiado()));
				financiador.setTramites(servicioFinanciador.getTramites(financiador.getIdInterviniente()));
			}
			financiador.setTipoInterviniente(TipoInterviniente.Financiador.getValorEnum());
			financiador.getPersona().setDireccionDto(financiador.getDireccion());
			financiador.getPersona().setTipoPersona(TipoPersonaRegistro.convertirTextoDesdeXML(financiador.getTipoPersona()));
			resultObject = servicioFinanciador.guardarFinanciador(financiador, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultObject.isError()) {
				if (resultObject.getValidaciones() != null && !resultObject.getValidaciones().isEmpty()) {
					for (String validacion : resultObject.getValidaciones()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultObject.getMensaje());
				}
			} else {
				financiador = (IntervinienteRegistroDto) resultObject.getObj();
				addActionMessage("Financiador guardado correctamente");
			}
		} catch (Exception e) {
			log.error("Error recuperando financiador " + getIdentificador(), e);
			addActionError("No se ha encontrado el financiador con id " + getIdentificador());
		}

		if (financiador.getIdInterviniente() != 0)
			representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
		return RESULTADO_EDIT;
	}

	public String borrarRepresentante() {
		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {
			try {
				ResultBean response = servicioFinanciador.borrarRepresentante(getIdentificador());
				if (response.getError()) {
					for (String mensaje : response.getListaMensajes())
						addActionError(mensaje);
				} else {
					addActionMessage("Representante borrado correctamente");
				}
			} catch (Exception e) {
				log.error("Error borrando representante con id=[" + getIdentificador() + "]", e);
				addActionError("No se pudo borrar el representante" + " (" + e.getMessage() + ").");
			}
		}
		representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
		return RESULTADO_EDIT;
	}

	public String cargarPopUpRepresentanteFinanciadorRegistro() {
		ResultRegistro resultObject;
		// validaciones
		resultObject = servicioIntervinienteRegistro.validarInterviniente(financiador, "");
		if (resultObject.isError()) {
			if (resultObject.getValidaciones() != null && !resultObject.getValidaciones().isEmpty()) {
				for (String validacion : resultObject.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultObject.getMensaje());
			}
			representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
			return RESULTADO_EDIT;
		}

		if (getIdRepresentante() != null && !getIdRepresentante().trim().isEmpty()) {
			// Actualización
			representante = servicioFinanciador.getRepresentante(idRepresentante);
			representante.setDireccion(servicioDireccion.getDireccionDto(representante.getIdDireccion()));
			if (null != representante.getNotario() && null != representante.getNotario().getFecOtorgamiento()) {
				representante.getNotario().setFecOtorgamientoNotario(utilesFecha.getFechaConDate(representante.getNotario().getFecOtorgamiento()));
			}
		}
		return POP_UP_ANIADIR_REPRESENTANTE_FINANCIADOR;
	}

	public String aniadirRepresentante() {
		ResultRegistro resultObjectRepresentante = new ResultRegistro();
		ResultRegistro resultObjectFinanciador;

		financiador.setNumColegiado(utilesColegiado.getNumColegiadoCabecera());
		financiador.setTipoInterviniente(TipoInterviniente.Financiador.getValorEnum());
		financiador.getPersona().setNif(financiador.getNif());
		financiador.getPersona().setNumColegiado(financiador.getNumColegiado());
		financiador.getPersona().setEstado("1");
		financiador.getPersona().setDireccionDto(financiador.getDireccion());
		financiador.getPersona().setTipoPersona(TipoPersonaRegistro.convertirTextoDesdeXML(financiador.getTipoPersona()));
		resultObjectFinanciador = servicioFinanciador.guardarFinanciador(financiador, utilesColegiado.getIdUsuarioSessionBigDecimal());

		financiador = (IntervinienteRegistroDto) resultObjectFinanciador.getObj();
		if (resultObjectFinanciador.isError()) {
			if (resultObjectFinanciador.getValidaciones() != null && !resultObjectFinanciador.getValidaciones().isEmpty()) {
				for (String validacion : resultObjectFinanciador.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultObjectFinanciador.getMensaje());
			}
			representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
			return RESULTADO_EDIT;
		}

		if (representante.getIdInterviniente() == 0) {
			// Nuevo representante
			representante.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			representante.setTipoInterviniente(TipoInterviniente.RepresentanteFinanciador.getValorEnum());
			representante.setIdRepresentado(financiador.getIdInterviniente());

		}
		representante.getPersona().setNif(representante.getNif());
		representante.getPersona().setNumColegiado(representante.getNumColegiado());
		representante.getPersona().setEstado("1");
		representante.getPersona().setDireccionDto(representante.getDireccion());
		representante.getPersona().setTipoPersona(TipoPersonaRegistro.convertirTextoDesdeXML(representante.getTipoPersona()));
		if (null != representante.getNotario() && null != representante.getNotario().getFecOtorgamientoNotario()) {
			try {
				representante.getNotario().setFecOtorgamiento(representante.getNotario().getFecOtorgamientoNotario().getTimestamp());
			} catch (ParseException e) {
				resultObjectRepresentante.setError(Boolean.TRUE);
				resultObjectRepresentante.setMensaje("Error de formato de fecha");
			}
		}
		resultObjectRepresentante = servicioFinanciador.guardarFinanciador(representante, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultObjectRepresentante.isError()) {
			if (resultObjectRepresentante.getValidaciones() != null && !resultObjectRepresentante.getValidaciones().isEmpty()) {
				for (String validacion : resultObjectRepresentante.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultObjectRepresentante.getMensaje());
			}
			representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
		} else {
			addActionMessage("Representante añadido correctamente");
			if (0 != financiador.getIdInterviniente())
				representantes = servicioFinanciador.getRepresentantes(financiador.getIdInterviniente());
		}

		return RESULTADO_EDIT;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public IntervinienteRegistroDto getFinanciador() {
		return financiador;
	}

	public void setFinanciador(IntervinienteRegistroDto financiador) {
		this.financiador = financiador;
	}

	public List<IntervinienteRegistroDto> getRepresentantes() {
		return representantes;
	}

	public void setRepresentantes(List<IntervinienteRegistroDto> representantes) {
		this.representantes = representantes;
	}

	public IntervinienteRegistroDto getRepresentante() {
		if (representante == null)
			representante = new IntervinienteRegistroDto();
		return representante;
	}

	public void setRepresentante(IntervinienteRegistroDto representante) {
		this.representante = representante;
	}

	public String getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(String idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

}
