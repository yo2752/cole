package org.gestoresmadrid.core.trafico.eeff.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EEFF_LIBERACION")
public class EeffLiberacionVO extends EeffVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5641250471704360313L;
	@Id
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;  
//	NIF	VARCHAR2(9 BYTE)
	@Column(name="NIF")
	private String nif;
//	ID_DIRECCION	NUMBER
	@Column(name="ID_DIRECCION")
	private String idDireccion;
//	N_FACTURA	VARCHAR2(35 BYTE)
	@Column(name="N_FACTURA")
	private String numFactura;
//	FECHA_FACTURA	DATE
	@Column(name="FECHA_FACTURA")
	private Date fechaFactura;
//	IMPORTE	NUMBER(15,2)
	@Column(name="IMPORTE")
	private Double importe ;
//	NOMBRE	VARCHAR2(32 BYTE)
	@Column(name="NOMBRE")
	private String nombre;
//	PRIMER_APELLIDO	VARCHAR2(32 BYTE)
	@Column(name="PRIMER_APELLIDO")
	private String primerApellido;
//	SEGUNDO_APELLIDO	VARCHAR2(32 BYTE)
	@Column(name="SEGUNDO_APELLIDO")
	private String segundoApellido;
//	TIPO_VIA	VARCHAR2(100 BYTE)
	@Column(name="TIPO_VIA")
	private String tipoVia;
//	NOMBRE_VIA	VARCHAR2(100 BYTE)
	@Column(name="NOMBRE_VIA")
	private String nombreVia;
//	BLOQUE	VARCHAR2(5 BYTE)
	@Column(name="BLOQUE")
	private String bloque;
//	NUMERO	VARCHAR2(4 BYTE)
	@Column(name="NUMERO")
	private String numero;
//	ESCALERA	VARCHAR2(100 BYTE)
	@Column(name="ESCALERA")
	private String escalera;
//	PISO	VARCHAR2(100 BYTE)
	@Column(name="PISO")
	private String piso;
//	PUERTA	VARCHAR2(100 BYTE)
	@Column(name="PUERTA")
	private String puerta;
//	PROVINCIA	VARCHAR2(100 BYTE)
	@Column(name="PROVINCIA")
	private String provincia;
//	MUNICIPIO	VARCHAR2(100 BYTE)
	@Column(name="MUNICIPIO")
	private String municipio;
//	CODIGO_POSTAL	VARCHAR2(5 BYTE)
	@Column(name="CODIGO_POSTAL")
	private String codPostal;
//	EXENTO	NUMBER(1,0)
	@Column(name="EXENTO")
	private boolean exento;	

	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	public String getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}
//	public Date getFechaFactura() { 
//		return fechaFactura;
//	}
	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura; 
	}
	public Date getFechaFactura() {
		
		return this.fechaFactura;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getTipoVia() {
		return tipoVia;
	}
	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}
	public String getNombreVia() {
		return nombreVia;
	}
	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEscalera() {
		return escalera;
	}
	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public boolean isExento() {
		return exento;
	}
	public void setExento(boolean exento) {
		this.exento = exento;
	}

}
