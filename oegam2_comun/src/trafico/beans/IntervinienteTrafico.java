package trafico.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;

import escrituras.beans.Persona;
import trafico.utiles.enumerados.ConceptoTutela;
import trafico.utiles.enumerados.TipoTutela;
import utilidades.estructuras.Fecha;

public class IntervinienteTrafico {

	private String nifInterviniente;
	private Persona persona; 
	private TipoInterviniente tipoInterviniente;	
	private BigDecimal numExpediente;  //comprobar si debería ser un expedienteBean
	private String numColegiado; 
	private String cambioDomicilio; 
	private String autonomo; 
	private String codigoIAE; 
	private Fecha fechaInicio;
	private Fecha fechaFin;
	private ConceptoTutela conceptoRepre; 
	private TipoTutela idMotivoTutela; 
	private String horaInicio; 
	private String horaFin; 
	private String datosDocumento;
	private BigDecimal idUsuario;
	private BigDecimal idContrato;
	private EstadoTramiteTrafico estadoTramite;
	private BigDecimal numInterviniente; 
	
	public IntervinienteTrafico() {
		
	}
	
	public IntervinienteTrafico(boolean inicio) {
		persona=new Persona(true);
		fechaInicio = new Fecha();
		fechaFin = new Fecha();
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public TipoInterviniente getTipoInterviniente() {
		return tipoInterviniente;
	}
	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = TipoInterviniente.convertir(tipoInterviniente);
	}
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getCambioDomicilio() {
		return cambioDomicilio;
	}
	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}
	public String getAutonomo() {
		return autonomo;
	}
	public void setAutonomo(String autonomo) {
		this.autonomo = autonomo;
	}
	public String getCodigoIAE() {
		return codigoIAE;
	}
	public void setCodigoIAE(String codigoIAE) {
		this.codigoIAE = codigoIAE;
	}
	public Fecha getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Fecha getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}
	public ConceptoTutela getConceptoRepre() {
		return conceptoRepre;
	}
	public void setConceptoRepre(String conceptoRepre) {
		this.conceptoRepre = ConceptoTutela.convertir(conceptoRepre);
	}
	public TipoTutela getIdMotivoTutela() {
		return idMotivoTutela;
	}
	public void setIdMotivoTutela(String idMotivoTutela) {
		this.idMotivoTutela = TipoTutela.convertir(idMotivoTutela);
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	

	public String getDatosDocumento() {
		return datosDocumento;
	}

	public void setDatosDocumento(String datosDocumento) {
		this.datosDocumento = datosDocumento;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	
	public String getNifInterviniente() {
		return nifInterviniente;
	}

	public void setNifInterviniente(String nifInterviniente) {
		this.nifInterviniente = nifInterviniente;
	}

	public EstadoTramiteTrafico getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = EstadoTramiteTrafico.convertir(estadoTramite);
	}

	public BigDecimal getNumInterviniente() {
		return numInterviniente;
	}

	public void setNumInterviniente(BigDecimal numInterviniente) {
		this.numInterviniente = numInterviniente;
	}

	
}
