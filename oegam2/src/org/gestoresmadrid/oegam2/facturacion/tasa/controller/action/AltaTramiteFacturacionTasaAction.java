package org.gestoresmadrid.oegam2.facturacion.tasa.controller.action;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioFacturacionTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaTasaPegatina;
import org.gestoresmadrid.oegam2comun.tasas.view.dto.TasaPegatinaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class AltaTramiteFacturacionTasaAction extends ActionBase {

	private static final long serialVersionUID = 4182486494616423686L;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioFacturacionTasa servicioFacturacionTasa;

	@Autowired
	private ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegatina;

	@Autowired
	UtilesColegiado utilesColegiado;

	private BigDecimal numExpediente;

	private String nifBusqueda;

	private TramiteTrafFacturacionDto tramiteTrafFacturacionDto;

	public String inicio() {
		if (tramiteTrafFacturacionDto == null) {
			tramiteTrafFacturacionDto = new TramiteTrafFacturacionDto();
		}
		return SUCCESS;
	}

	public String cargarExpediente() {
		if (numExpediente != null) {
			tramiteTrafFacturacionDto = servicioFacturacionTasa.getFacturacionTasa(numExpediente, null);
			if (tramiteTrafFacturacionDto == null) {
				addActionError("No se ha podido recuperar el trámite de facturación de tasa.");
			}
			// recuperar tipoTasa
			TasaPegatinaDto tasaPegatinaDto = servicioPersistenciaTasaPegatina.getTasaDto(tramiteTrafFacturacionDto.getCodTasa());
			if (tasaPegatinaDto != null) {
				tramiteTrafFacturacionDto.setTipoTasa(tasaPegatinaDto.getTipoTasa());
			}
		}
		return SUCCESS;
	}

	// Consultar persona
	public String consultarPersona() {
		if (nifBusqueda != null && !nifBusqueda.equals("")) {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(nifBusqueda.toUpperCase(), utilesColegiado.getNumColegiadoSession());
			if (interviniente == null) {
				addActionMessage("No existen datos para ese nif.");
			} else {
				if (tramiteTrafFacturacionDto == null) {
					tramiteTrafFacturacionDto = new TramiteTrafFacturacionDto();
				}
				tramiteTrafFacturacionDto.setPersona(interviniente.getPersona());
				tramiteTrafFacturacionDto.setDireccion(interviniente.getDireccion());
			}
		} else {
			addActionError("Se debe de rellenar el nif.");
		}
		return SUCCESS;
	}

	public String guardarFacturacion() {
		if (tramiteTrafFacturacionDto != null && tramiteTrafFacturacionDto.getCodTasa() != null &&
				!tramiteTrafFacturacionDto.getCodTasa().isEmpty() && !"-1".equals(tramiteTrafFacturacionDto.getCodTasa())) {
			try {
				tramiteTrafFacturacionDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				tramiteTrafFacturacionDto.setIdContrato(utilesColegiado.getIdContratoSession());
				ResultBean resultado = servicioFacturacionTasa.guardar(tramiteTrafFacturacionDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					tramiteTrafFacturacionDto.setNumExpediente(null);
					addActionError("Error al guardar los datos de facturación");
				} else {
					addActionMessage("Datos de Facturación guardados.");
				}
			} catch (Exception e) {
				addActionError("Error al guardar los datos de facturación");
			}
		} else {
			addActionError("Falta el código de tasa");
		}
		return SUCCESS;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioPersonaDireccion getServicioPersonaDireccion() {
		return servicioPersonaDireccion;
	}

	public void setServicioPersonaDireccion(ServicioPersonaDireccion servicioPersonaDireccion) {
		this.servicioPersonaDireccion = servicioPersonaDireccion;
	}

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

	public TramiteTrafFacturacionDto getTramiteTrafFacturacionDto() {
		return tramiteTrafFacturacionDto;
	}

	public void setTramiteTrafFacturacionDto(TramiteTrafFacturacionDto tramiteTrafFacturacionDto) {
		this.tramiteTrafFacturacionDto = tramiteTrafFacturacionDto;
	}

	public ServicioFacturacionTasa getServicioFacturacionTasa() {
		return servicioFacturacionTasa;
	}

	public void setServicioFacturacionTasa(ServicioFacturacionTasa servicioFacturacionTasa) {
		this.servicioFacturacionTasa = servicioFacturacionTasa;
	}

	public ServicioPersistenciaTasaPegatina getServicioPersistenciaTasaPegatina() {
		return servicioPersistenciaTasaPegatina;
	}

	public void setServicioPersistenciaTasaPegatina(ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegatina) {
		this.servicioPersistenciaTasaPegatina = servicioPersistenciaTasaPegatina;
	}
}