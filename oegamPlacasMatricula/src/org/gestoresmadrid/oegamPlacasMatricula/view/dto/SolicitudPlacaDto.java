package org.gestoresmadrid.oegamPlacasMatricula.view.dto;

import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

import utilidades.estructuras.Fecha;

public class SolicitudPlacaDto {

		private Integer idSolicitud;
		
		private BigDecimal numExpediente;
		
		private Fecha fechaSolicitud;

		private UsuarioVO usuario;
		
		private Integer tipoDelantera;

		private Integer tipoTrasera;
		
		private Integer tipoAdicional;
		
		private TramiteTraficoVO tramiteTrafico;
		
		private Integer estado = 0;
		
		private String descEstado;
		
		private String descContrato;

		private String numColegiado;
		
	    private VehiculoVO vehiculo;
		
		private ContratoVO contrato;
		
		private Integer idContrato;
		
		private PersonaVO titular;
	    
		private String nifTitular;

		private Long idUsuario;
		
		private Integer duplicada;
		
		private String bastidor;
		
		private String matricula;
		
		private String tipoVehiculo;
		
		private String refPropia;
		
		private String mostrarSeccionPersona = "false";
		
		public VehiculoVO getVehiculo() {
			return vehiculo;
		}

		public void setVehiculo(VehiculoVO vehiculo) {
			this.vehiculo = vehiculo;
		}

		public Integer getIdSolicitud() {
			return this.idSolicitud;
		}

		public void setIdSolicitud(Integer idSolicitud) {
			this.idSolicitud = idSolicitud;
		}

		public BigDecimal getNumExpediente() {
			return numExpediente;
		}

		public void setNumExpediente(BigDecimal numExpediente) {
			this.numExpediente = numExpediente;
		}

		public Fecha getFechaSolicitud() {
			return fechaSolicitud;
		}

		public void setFechaSolicitud(Fecha fechaSolicitud) {
			this.fechaSolicitud = fechaSolicitud;
		}

		public Integer getTipoDelantera() {
			return this.tipoDelantera;
		}

		public void setTipoDelantera(Integer tipoDelantera) {
			this.tipoDelantera = tipoDelantera;
		}

		public Integer getTipoTrasera() {
			return this.tipoTrasera;
		}

		public void setTipoTrasera(Integer tipoTrasera) {
			this.tipoTrasera = tipoTrasera;
		}
		
		public Integer getTipoAdicional() {
			return this.tipoAdicional;
		}

		public void setTipoAdicional(Integer tipoAdicional) {
			this.tipoAdicional = tipoAdicional;
		}

		public TramiteTraficoVO getTramiteTrafico() {
			return this.tramiteTrafico;
		}

		public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
			this.tramiteTrafico = tramiteTrafico;
		}
		
		public Integer getEstado() {
			return this.estado;
		}

		public void setEstado(Integer estado) {
			this.estado = estado;
		}

		public String getDescEstado() {
			return descEstado;
		}

		public void setDescEstado(String descEstado) {
			this.descEstado = descEstado;
		}

		public String getDescContrato() {
			return descContrato;
		}

		public void setDescContrato(String descContrato) {
			this.descContrato = descContrato;
		}

		public String getNifTitular() {
			return nifTitular;
		}

		public void setNifTitular(String nifTitular) {
			this.nifTitular = nifTitular;
		}

		public Long getIdUsuario() {
			return idUsuario;
		}

		public void setIdUsuario(Long idUsuario) {
			this.idUsuario = idUsuario;
		}

		public Integer getDuplicada() {
			return duplicada;
		}

		public void setDuplicada(Integer duplicada) {
			this.duplicada = duplicada;
		}

		public String getBastidor() {
			return bastidor;
		}

		public void setBastidor(String bastidor) {
			this.bastidor = bastidor;
		}

		public String getNumColegiado() {
			return numColegiado;
		}

		public void setNumColegiado(String numColegiado) {
			this.numColegiado = numColegiado;
		}

		public UsuarioVO getUsuario() {
			return usuario;
		}

		public void setUsuario(UsuarioVO usuario) {
			this.usuario = usuario;
		}

		public ContratoVO getContrato() {
			return contrato;
		}

		public void setContrato(ContratoVO contrato) {
			this.contrato = contrato;
		}

		public Integer getIdContrato() {
			return idContrato;
		}

		public void setIdContrato(Integer idContrato) {
			this.idContrato = idContrato;
		}

		public PersonaVO getTitular() {
			return titular;
		}

		public void setTitular(PersonaVO titular) {
			this.titular = titular;
		}

		public String getMatricula() {
			return matricula;
		}

		public void setMatricula(String matricula) {
			this.matricula = matricula;
		}

		public String getTipoVehiculo() {
			return tipoVehiculo;
		}

		public void setTipoVehiculo(String tipoVehiculo) {
			this.tipoVehiculo = tipoVehiculo;
		}

		public String getRefPropia() {
			return refPropia;
		}

		public void setRefPropia(String refPropia) {
			this.refPropia = refPropia;
		}

		public String getMostrarSeccionPersona() {
			return mostrarSeccionPersona;
		}

		public void setMostrarSeccionPersona(String mostrarSeccionPersona) {
			this.mostrarSeccionPersona = mostrarSeccionPersona;
		}

}
