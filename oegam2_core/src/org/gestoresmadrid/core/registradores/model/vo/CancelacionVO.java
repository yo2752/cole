package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the CANCELACION database table.
 * 
 */
@Entity
@Table(name="CANCELACION")
public class CancelacionVO implements Serializable {


	private static final long serialVersionUID = -3505659823583474411L;

	@Id
	@SequenceGenerator(name = "cancelacion_secuencia", sequenceName = "CANCELACION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cancelacion_secuencia")
	@Column(name="ID_CANCELACION")
	private long idCancelacion;

	@Column(name="CAUSA_CANCELACION")
	private String causaCancelacion;

	@Column(name="CLAUSULA_LOPD")
	private String clausulaLopd;

	private String estado;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Column(name="ID_CONTRATO")
	private BigDecimal idContrato;

	@Column(name="ID_DATOS_INSCRIPCION")
	private BigDecimal idDatosInscripcion;

	@Column(name="IMPORTE_COMISION_CANCELACION")
	private BigDecimal importeComisionCancelacion;

	private String modelo;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="NUM_EXPEDIENTE")
	private String numExpediente;

	@Column(name="NUMERO_IMPRESO")
	private String numeroImpreso;

	@Column(name="REFERENCIA_PROPIA")
	private String referenciaPropia;

	@Column(name="SITUACION_JURIDICA_CANCELACION")
	private String situacionJuridicaCancelacion;

	@Column(name="TITULO_DOCUMENTO")
	private String tituloDocumento;

	//bi-directional many-to-one association to BienCancelado
	@ManyToOne
	@JoinColumn(name="ID_BIEN_CANCELADO")
	private BienCanceladoVO bienCancelado;

	//bi-directional many-to-one association to DatoFirma
	@ManyToOne
	@JoinColumn(name="ID_DATO_FIRMA")
	private DatoFirmaVO datoFirma;

	//bi-directional many-to-one association to EntidadCancelacion
	@ManyToOne
	@JoinColumn(name="ENTIDAD_SUSCRIPTORA")
	private EntidadCancelacionVO entidadCancelacion1;

	//bi-directional many-to-one association to EntidadCancelacion
	@ManyToOne
	@JoinColumn(name="ENTIDAD_SUCESORA")
	private EntidadCancelacionVO entidadCancelacion2;

	//bi-directional many-to-one association to IntervinienteRegistro
	@ManyToOne
	@JoinColumn(name="SOLICITANTE")
	private IntervinienteRegistroVO intervinienteRegistro1;

	//bi-directional many-to-one association to IntervinienteRegistro
	@ManyToOne
	@JoinColumn(name="COMPRADOR_ARRENDATARIO")
	private IntervinienteRegistroVO intervinienteRegistro2;

	//bi-directional many-to-one association to Mensaje
	@OneToMany(mappedBy="cancelacion")
	private List<MensajeVO> mensajes;

	public CancelacionVO() {
	}

	public long getIdCancelacion() {
		return this.idCancelacion;
	}

	public void setIdCancelacion(long idCancelacion) {
		this.idCancelacion = idCancelacion;
	}

	public String getCausaCancelacion() {
		return this.causaCancelacion;
	}

	public void setCausaCancelacion(String causaCancelacion) {
		this.causaCancelacion = causaCancelacion;
	}

	public String getClausulaLopd() {
		return this.clausulaLopd;
	}

	public void setClausulaLopd(String clausulaLopd) {
		this.clausulaLopd = clausulaLopd;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public BigDecimal getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getIdDatosInscripcion() {
		return this.idDatosInscripcion;
	}

	public void setIdDatosInscripcion(BigDecimal idDatosInscripcion) {
		this.idDatosInscripcion = idDatosInscripcion;
	}

	public BigDecimal getImporteComisionCancelacion() {
		return this.importeComisionCancelacion;
	}

	public void setImporteComisionCancelacion(BigDecimal importeComisionCancelacion) {
		this.importeComisionCancelacion = importeComisionCancelacion;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNumeroImpreso() {
		return this.numeroImpreso;
	}

	public void setNumeroImpreso(String numeroImpreso) {
		this.numeroImpreso = numeroImpreso;
	}

	public String getReferenciaPropia() {
		return this.referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}

	public String getSituacionJuridicaCancelacion() {
		return this.situacionJuridicaCancelacion;
	}

	public void setSituacionJuridicaCancelacion(String situacionJuridicaCancelacion) {
		this.situacionJuridicaCancelacion = situacionJuridicaCancelacion;
	}

	public String getTituloDocumento() {
		return this.tituloDocumento;
	}

	public void setTituloDocumento(String tituloDocumento) {
		this.tituloDocumento = tituloDocumento;
	}

	public BienCanceladoVO getBienCancelado() {
		return this.bienCancelado;
	}

	public void setBienCancelado(BienCanceladoVO bienCancelado) {
		this.bienCancelado = bienCancelado;
	}

	public DatoFirmaVO getDatoFirma() {
		return this.datoFirma;
	}

	public void setDatoFirma(DatoFirmaVO datoFirma) {
		this.datoFirma = datoFirma;
	}

	public EntidadCancelacionVO getEntidadCancelacion1() {
		return this.entidadCancelacion1;
	}

	public void setEntidadCancelacion1(EntidadCancelacionVO entidadCancelacion1) {
		this.entidadCancelacion1 = entidadCancelacion1;
	}

	public EntidadCancelacionVO getEntidadCancelacion2() {
		return this.entidadCancelacion2;
	}

	public void setEntidadCancelacion2(EntidadCancelacionVO entidadCancelacion2) {
		this.entidadCancelacion2 = entidadCancelacion2;
	}

	public IntervinienteRegistroVO getIntervinienteRegistro1() {
		return this.intervinienteRegistro1;
	}

	public void setIntervinienteRegistro1(IntervinienteRegistroVO intervinienteRegistro1) {
		this.intervinienteRegistro1 = intervinienteRegistro1;
	}

	public IntervinienteRegistroVO getIntervinienteRegistro2() {
		return this.intervinienteRegistro2;
	}

	public void setIntervinienteRegistro2(IntervinienteRegistroVO intervinienteRegistro2) {
		this.intervinienteRegistro2 = intervinienteRegistro2;
	}

	public List<MensajeVO> getMensajes() {
		return this.mensajes;
	}

	public void setMensajes(List<MensajeVO> mensajes) {
		this.mensajes = mensajes;
	}

	public MensajeVO addMensaje(MensajeVO mensaje) {
		getMensajes().add(mensaje);
		mensaje.setCancelacion(this);

		return mensaje;
	}

	public MensajeVO removeMensaje(MensajeVO mensaje) {
		getMensajes().remove(mensaje);
		mensaje.setCancelacion(null);

		return mensaje;
	}

}