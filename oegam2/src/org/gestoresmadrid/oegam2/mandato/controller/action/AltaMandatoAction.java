package org.gestoresmadrid.oegam2.mandato.controller.action;

import org.gestoresmadrid.oegam2comun.mandato.model.service.ServicioMandato;
import org.gestoresmadrid.oegam2comun.mandato.view.dto.MandatoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class AltaMandatoAction extends ActionBase {

	private static final long serialVersionUID = -7044148169166896467L;

	private static final String ALTA_MANDATO = "altaMandato";

	private MandatoDto mandatoDto;

	private String codSeleccionados;

	@Autowired
	ServicioMandato servicioMandato;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		return ALTA_MANDATO;
	}

	public String guardar() {
		ResultBean resultado = servicioMandato.guardarMandato(mandatoDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			addActionMessage("Mandato con código " + mandatoDto.getCodigoMandato() + " guardado correctamente");
			mandatoDto = new MandatoDto();
		}
		return ALTA_MANDATO;
	}

	public String modificar() {
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] idMandatos = codSeleccionados.split("-");
			if (idMandatos.length == 1) {
				Long idMandato = new Long(idMandatos[0]);
				mandatoDto = servicioMandato.getMandatoDto(idMandato);
			} else {
				addActionError("Debe seleccionar sólo un mandato para realizar la operación");
			}
		} else {
			addActionError("Debe seleccionar algún mandato para realizar su operacion.");
		}
		return ALTA_MANDATO;
	}

	public MandatoDto getMandatoDto() {
		return mandatoDto;
	}

	public void setMandatoDto(MandatoDto mandatoDto) {
		this.mandatoDto = mandatoDto;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}
}
