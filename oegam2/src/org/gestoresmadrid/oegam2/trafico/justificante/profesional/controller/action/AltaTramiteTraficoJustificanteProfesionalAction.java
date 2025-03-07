package org.gestoresmadrid.oegam2.trafico.justificante.profesional.controller.action;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTraficoJustificante;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.springframework.beans.factory.annotation.Autowired;

import trafico.beans.schemas.generated.transTelematica.TipoSINO;

public class AltaTramiteTraficoJustificanteProfesionalAction extends ActionBase {

	private static final long serialVersionUID = 5116447055338013524L;

	private JustificanteProfDto justificanteProfDto;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private TramiteTrafDto tramiteTrafDto;

	private String tipoIntervinienteBuscar;
	private String nifBusqueda;

	private String matriculaBusqueda;

	private IntervinienteTraficoDto titular;

	private boolean checkIdFuerzasArmadas;
	private boolean esGeneradoResumen;

	// Desde el tramite
	private TramiteTrafTranDto tramiteTrafTranDto;
	private TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto;
	private int numPeticiones;
	private String numExpediente;
	private String tipoTramiteJustificante;

	public String inicio() throws Throwable {
		if (tramiteTrafDto == null) {
			tramiteTrafDto = new TramiteTrafDto();
		}
		if (tramiteTrafDto.getJefaturaTraficoDto() == null) {
			tramiteTrafDto.setJefaturaTraficoDto(new JefaturaTraficoDto());
		}
		tramiteTrafDto.getJefaturaTraficoDto().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		tramiteTrafDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		if (justificanteProfDto == null) {
			justificanteProfDto = new JustificanteProfDto();
		}
		justificanteProfDto.setEstado(BigDecimal.ONE);
		esGeneradoResumen = false;
		return SUCCESS;
	}

	public String generarDesdeResumen() {
		clearMessages();
		if (numPeticiones == 1) {
			addActionMessage("Para solicitar por segunda vez este justificante deberá rellenar el campo observaciones. Rellénelo y pulse Generar.");
		} else if (numPeticiones == 2) {
			addActionMessage("Para solicitar por tercera vez este justificante deberá rellenar el campo observaciones. Rellénelo y pulse Generar.");
		}

		if (tipoTramiteJustificante.equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum()) || tipoTramiteJustificante.equals(TipoTramiteTrafico.Transmision.getValorEnum())) {
			tramiteTrafDto = tramiteTrafTranDto;
			titular = tramiteTrafTranDto.getAdquiriente();
		} else if (tipoTramiteJustificante.equals(TipoTramiteTrafico.Duplicado.getValorEnum())) {
			tramiteTrafDto = tramiteTrafDuplicadoDto;
			titular = tramiteTrafDuplicadoDto.getTitular();
		}

		if (justificanteProfDto == null) {
			justificanteProfDto = new JustificanteProfDto();
		}
		justificanteProfDto.setEstado(BigDecimal.ONE);
		esGeneradoResumen = true;
		return SUCCESS;
	}

	public String generarJustificante() throws Throwable {
		ResultBean result = new ResultBean();

		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES)) {
			addActionError("No tiene permiso para realizar un justificante.");
			return SUCCESS;
		}

		if (TipoTramiteTraficoJustificante.TransmisionElectronica.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
			TramiteTrafTranDto tramite = getTramiteTrafTranDto(tramiteTrafDto);
			tramite.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

			result = servicioJustificanteProfesional.generarJustificanteTransmision(tramite, justificanteProfDto,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
		} else if (TipoTramiteTraficoJustificante.Duplicado.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
			TramiteTrafDuplicadoDto tramite = new TramiteTrafDuplicadoDto(tramiteTrafDto);
			titular.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
			tramite.setTitular(titular);
			tramite.setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());
			tramite.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
			if (tramite.getIdContrato() == null) {
				tramite.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			}

			result = servicioJustificanteProfesional.generarJustificanteDuplicado(tramite, justificanteProfDto,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
		} else if (TipoTramiteTraficoJustificante.Transmision.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
			TramiteTrafTranDto tramite = getTramiteTrafTranDto(tramiteTrafDto);
			tramite.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

			result = servicioJustificanteProfesional.generarJustificanteTransmision(tramite, justificanteProfDto,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
		}
		if (result != null) {
			String mensajes = "";
			for (String mensaje : result.getListaMensajes()) {
				mensajes = mensajes + mensaje + " ";
			}

			if (!result.getError()) {
				BigDecimal numExpediente = (BigDecimal) result.getAttachment(ServicioJustificanteProfesional.NUM_EXPEDIENTE);
				justificanteProfDto = (JustificanteProfDto) result.getAttachment(ServicioJustificanteProfesional.JUSTIFICANTE);
				tramiteTrafDto.setNumExpediente(numExpediente);
				addActionMessage(mensajes);
			} else {
				addActionError("Error al generar el Justificante: " + mensajes);
			}
		}
		esGeneradoResumen = false;
		return SUCCESS;
	}

	private TramiteTrafTranDto getTramiteTrafTranDto(TramiteTrafDto tramiteTrafDto) {
		TramiteTrafTranDto tramite = new TramiteTrafTranDto(tramiteTrafDto);
		tramite.setElectronica("S");
		tramite.setTipoTramite(tramiteTrafDto.getTipoTramite());
		titular.setTipoInterviniente(TipoInterviniente.Adquiriente.getValorEnum());
		tramite.setAdquiriente(titular);
		tramite.setImprPermisoCircu(TipoSINO.SI.value());

		if (tramite.getIdContrato() == null) {
			tramite.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}

		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(tramiteTrafDto.getNumColegiado(), tramite.getIdContrato());
		IntervinienteTraficoDto presentador = new IntervinienteTraficoDto();
		presentador.setPersona(colegiado);
		presentador.setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());
		tramite.setPresentador(presentador);

		tramite.setAdquiriente(titular);
		esGeneradoResumen = false;
		return tramite;
	}

	public String consultarPersona() {
		if (nifBusqueda != null && !"".equals(nifBusqueda)) {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(nifBusqueda.toUpperCase(), tramiteTrafDto.getNumColegiado());
			if (interviniente == null) {
				addActionMessage("No existen datos para ese NIF.");
				interviniente = new IntervinienteTraficoDto();
				PersonaDto persona = new PersonaDto();
				persona.setNif(nifBusqueda);
				interviniente.setPersona(persona);
			}
			setTitular(interviniente);
		} else {
			addActionError("Se debe de rellenar el NIF");
		}
		esGeneradoResumen = false;
		return SUCCESS;
	}

	public String buscarVehiculo() {
		VehiculoDto vehiculoDto = null;
		if (matriculaBusqueda != null && !matriculaBusqueda.equals("")) {
			vehiculoDto = servicioVehiculo.getVehiculoDto(null, tramiteTrafDto.getNumColegiado(), matriculaBusqueda.toUpperCase(), null, null, EstadoVehiculo.Activo);
		}
		if (vehiculoDto == null) {
			vehiculoDto = new VehiculoDto();
			vehiculoDto.setMatricula(matriculaBusqueda);
		}
		tramiteTrafDto.setVehiculoDto(vehiculoDto);
		esGeneradoResumen = false;
		return SUCCESS;
	}

	public ServicioJustificanteProfesional getServicioJustificanteProfesional() {
		return servicioJustificanteProfesional;
	}

	public void setServicioJustificanteProfesional(ServicioJustificanteProfesional servicioJustificanteProfesional) {
		this.servicioJustificanteProfesional = servicioJustificanteProfesional;
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
	}

	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}

	public String getMatriculaBusqueda() {
		return matriculaBusqueda;
	}

	public void setMatriculaBusqueda(String matriculaBusqueda) {
		this.matriculaBusqueda = matriculaBusqueda;
	}

	public JustificanteProfDto getJustificanteProfDto() {
		return justificanteProfDto;
	}

	public void setJustificanteProfDto(JustificanteProfDto justificanteProfDto) {
		this.justificanteProfDto = justificanteProfDto;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

	public TramiteTrafDto getTramiteTrafDto() {
		return tramiteTrafDto;
	}

	public void setTramiteTrafDto(TramiteTrafDto tramiteTrafDto) {
		this.tramiteTrafDto = tramiteTrafDto;
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	public boolean isCheckIdFuerzasArmadas() {
		return checkIdFuerzasArmadas;
	}

	public void setCheckIdFuerzasArmadas(boolean checkIdFuerzasArmadas) {
		this.checkIdFuerzasArmadas = checkIdFuerzasArmadas;
	}

	public TramiteTrafTranDto getTramiteTrafTranDto() {
		return tramiteTrafTranDto;
	}

	public void setTramiteTrafTranDto(TramiteTrafTranDto tramiteTrafTranDto) {
		this.tramiteTrafTranDto = tramiteTrafTranDto;
	}

	public int getNumPeticiones() {
		return numPeticiones;
	}

	public void setNumPeticiones(int numPeticiones) {
		this.numPeticiones = numPeticiones;
	}

	public boolean isEsGeneradoResumen() {
		return esGeneradoResumen;
	}

	public void setEsGeneradoResumen(boolean esGeneradoResumen) {
		this.esGeneradoResumen = esGeneradoResumen;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public TramiteTrafDuplicadoDto getTramiteTrafDuplicadoDto() {
		return tramiteTrafDuplicadoDto;
	}

	public void setTramiteTrafDuplicadoDto(TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto) {
		this.tramiteTrafDuplicadoDto = tramiteTrafDuplicadoDto;
	}

	public String getTipoTramiteJustificante() {
		return tipoTramiteJustificante;
	}

	public void setTipoTramiteJustificante(String tipoTramiteJustificante) {
		this.tipoTramiteJustificante = tipoTramiteJustificante;
	}
}