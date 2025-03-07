package org.gestoresmadrid.oegam2.trafico.empresa.telematica.controller.action;

import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.ServicioEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.EstadoEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.ResultadoEmpresaTelematicaBean;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.dto.EmpresaTelematicaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaEmpresaTelematicaAction extends ActionBase {

	private static final long serialVersionUID = 699362389643440552L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(AltaEmpresaTelematicaAction.class);

	private EmpresaTelematicaDto empresaTelematicaDto;
	private String id;

	@Autowired
	ServicioEmpresaTelematica servicio;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public String inicio() {
		if (id == null) {
			cargarValoresIniciales();
		} else {
			ResultadoEmpresaTelematicaBean salida = servicio.getEmpresaTelematicaPantalla(id);
			if (!salida.getError()) {
				empresaTelematicaDto = salida.getEmpresaTelematica();
			} else {
				LOG.error(salida.getMensajeError());
				addActionError(salida.getMensajeError());
				cargarValoresIniciales();
			}
		}
		return SUCCESS;
	}

	public String guardar() {
		ResultadoEmpresaTelematicaBean salida = servicio.guardarEmpresaTelematica(empresaTelematicaDto,
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getNumColegiadoSession());

		if (salida.getError()) {
			if (salida.getListaMensajeError() != null) {
				for (String error : salida.getListaMensajeError()) {
					addActionError(error);
				}

			}
			addActionError(salida.getMensajeError());
			if (empresaTelematicaDto.getId() != null) {
				recuperarEmpresaTelematicaPantalla(empresaTelematicaDto.getId().toString());
			}
		} else {
			recuperarEmpresaTelematicaPantalla(salida.getId().toString(),
					"La empresa telemática se ha guardado correctamente.");
		}
		return SUCCESS;
	}

	private void recuperarEmpresaTelematicaPantalla(String id, String mensajeOk) {
		ResultadoEmpresaTelematicaBean salida = servicio.getEmpresaTelematicaPantalla(id);
		if (!salida.getError()) {
			empresaTelematicaDto = salida.getEmpresaTelematica();
			addActionMessage(mensajeOk);
		} else {
			addActionError(salida.getMensajeError());
		}
	}

	private void recuperarEmpresaTelematicaPantalla(String id) {
		ResultadoEmpresaTelematicaBean salida = servicio.getEmpresaTelematicaPantalla(id);
		if (!salida.getError()) {
			empresaTelematicaDto = salida.getEmpresaTelematica();
		}
	}

	private void cargarValoresIniciales() {
		empresaTelematicaDto = new EmpresaTelematicaDto();
		empresaTelematicaDto.setEstado(EstadoEmpresaTelematica.Activo.getValorEnum());
		if (!utilesColegiado.tienePermisoAdmin()) {
			empresaTelematicaDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	public EmpresaTelematicaDto getEmpresaTelematicaDto() {
		return empresaTelematicaDto;
	}

	public void setEmpresaTelematicaDto(EmpresaTelematicaDto empresaTelematicaDto) {
		this.empresaTelematicaDto = empresaTelematicaDto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}