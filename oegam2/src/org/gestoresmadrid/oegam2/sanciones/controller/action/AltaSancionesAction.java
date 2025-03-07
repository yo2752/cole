package org.gestoresmadrid.oegam2.sanciones.controller.action;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamSanciones.service.ServicioSanciones;
import org.gestoresmadrid.oegamSanciones.view.beans.ResultadoSancionesBean;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaSancionesAction extends ActionBase {

	private static final long serialVersionUID = -2537367781507439976L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaSancionesAction.class);

	private SancionDto sancionDto;
	private String idSancion;

	@Autowired
	private ServicioSanciones servicioSanciones;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;
	
	public String inicio() {
		if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}
	}

	public String guardar() {
		try {

			if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_SANCION_DOCUMENTOS_ALTA)) {
				addActionMessage("No tiene permiso para operar con una sanción.");
				return SUCCESS;
			}

			if (sancionDto.getIdSancion() == null) {
				sancionDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				sancionDto.setFechaAlta(utilesFecha.getFechaHoraActualLEG());
			}

			ResultadoSancionesBean resultadoGuardar = servicioSanciones.guardarSancion(sancionDto, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if (resultadoGuardar.getError()) {
				addActionError(resultadoGuardar.getMensaje());
			} else {
				addActionMessage(resultadoGuardar.getMensaje());
			}
		} catch (Exception e) {
			log.error("Error al guardar la Sanción: ", e);
			addActionError(e.getMessage());
		}

		return SUCCESS;
	}

	public String modificar() {

		if (StringUtils.isNotBlank(idSancion)) {
			sancionDto = new SancionDto();
			try {
				sancionDto.setIdSancion(Integer.parseInt(idSancion));
				sancionDto = servicioSanciones.getSancion(sancionDto);
			} catch (Exception e) {
				log.error("Error al recuperar la Sanción: ", e);
				addActionError("Error al recuperar la Sanción");
			}
		}

		return SUCCESS;
	}

	public String getDetallePersona() {
		if (StringUtils.isNotBlank(sancionDto.getDni())) {
			ResultBean resultPers = servicioPersona.buscarPersona(sancionDto.getDni().toUpperCase(), utilesColegiado.getNumColegiadoSession());
			if (!resultPers.getError()) {
				PersonaDto persona = (PersonaDto) resultPers.getAttachment(ServicioPersona.PERSONA);
				if (persona != null) {
					sancionDto.setNombre(persona.getNombre());
					StringBuffer apellidos = persona.getApellido1RazonSocial() != null ? new StringBuffer(persona.getApellido1RazonSocial() + " ") : new StringBuffer("");
					apellidos.append(persona.getApellido2() != null ? new StringBuffer(persona.getApellido2()) : new StringBuffer(""));
					sancionDto.setApellidos(apellidos.toString());
				} else {
					addActionError("No se han recuperado datos para el NIF/CIF indicado");
				}
			}
		} else {
			addActionError("Se debe de rellenar el NIF/CIF");
		}

		return SUCCESS;
	}

	public SancionDto getSancionDto() {
		return sancionDto;
	}

	public void setSancionDto(SancionDto sancionDto) {
		this.sancionDto = sancionDto;
	}

	public String getIdSancion() {
		return idSancion;
	}

	public void setIdSancion(String idSancion) {
		this.idSancion = idSancion;
	}

}
