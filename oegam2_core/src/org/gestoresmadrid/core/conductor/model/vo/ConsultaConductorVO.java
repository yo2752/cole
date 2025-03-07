package org.gestoresmadrid.core.conductor.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

@Entity
@Table(name = "CONDUCTOR_HABITUAL")
public class ConsultaConductorVO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_CONDUCTOR_HABITUAL")
    @SequenceGenerator(name = "conductor_habitual_secuencia", sequenceName = "CONDUCTOR_HABITUAL_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "conductor_habitual_secuencia")
    private Long idConductor;   
    
    @Column(name="FECHA_INI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIni;
    
    @Column(name="FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    
    @Column(name="FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    
    @Column(name="NIF")
    private String nif; 
    
    @Column(name="NUM_COLEGIADO")
    private String numColegiado;
    
    // bi-directional many-to-one association to Persona
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "NIF", referencedColumnName = "NIF", insertable = false, updatable = false),
            @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
    private PersonaVO persona;
            
    @Column(name="DOI_VEHICULO")
    private String doiVehiculo; 
    
    @Column(name="MATRICULA")
    private String matricula;
    
    @Column(name="BASTIDOR")
    private String bastidor;
        
    @Column(name="SOLICITUD")
    private String solicitud;

    @Column(name="RESPUESTA")
    private String respuesta;
    
    @Column(name="NUM_EXPEDIENTE")
    private BigDecimal numExpediente;   
    
    @Column(name="ID_CONTRATO")
    private Long idContrato;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_CONTRATO",insertable=false,updatable=false)
    private ContratoVO contrato;
    
    @Column(name="ESTADO")
    private BigDecimal estado;
    
    @Column(name = "ID_DIRECCION")
    private Long idDireccion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false)
    private DireccionVO direccion;
    
    @Column(name = "CONSENTIMIENTO")
    private Boolean consentimiento; 

    @Column(name = "OPERACION")
    private String operacion; 
    
    @Column(name="NUM_REGISTRO")
    private String numRegistro;
    
    @Column(name="REF_PROPIA")
    private String refPropia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;
	
    public String getNumRegistro() {
      return numRegistro;
    }

    public void setNumRegistro(String numRegistro) {
      this.numRegistro = numRegistro;
    }

    public Long getIdConductor() {
        return idConductor;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    public PersonaVO getPersona() {
        return persona;
    }

    public void setPersona(PersonaVO persona) {
        this.persona = persona;
    }

    public String getDoiVehiculo() {
        return doiVehiculo;
    }

    public void setDoiVehiculo(String doiVehiculo) {
        this.doiVehiculo = doiVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getBastidor() {
        return bastidor;
    }

    public void setBastidor(String bastidor) {
        this.bastidor = bastidor;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public BigDecimal getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(BigDecimal numExpediente) {
        this.numExpediente = numExpediente;
    }

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public ContratoVO getContrato() {
        return contrato;
    }

    public void setContrato(ContratoVO contrato) {
        this.contrato = contrato;
    }

    public BigDecimal getEstado() {
        return estado;
    }

    public void setEstado(BigDecimal estado) {
        this.estado = estado;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public DireccionVO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionVO direccion) {
        this.direccion = direccion;
    }

    public Boolean getConsentimiento() {
        return consentimiento;
    }

    public void setConsentimiento(Boolean consentimiento) {
        this.consentimiento = consentimiento;
    }

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
    

    
    
}
