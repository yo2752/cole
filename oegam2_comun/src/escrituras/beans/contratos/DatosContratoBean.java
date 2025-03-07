package escrituras.beans.contratos;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.Estado;

import trafico.beans.JefaturaTrafico;
import utilidades.estructuras.Fecha;
import escrituras.beans.ColegioBean;
import escrituras.beans.Direccion;
 
/**
 * Bean de pantalla para los datos generales de un contrato.
 * @author juan.gomez
 *
 *
 *ID_CONTRATO	NUMBER
 *ID_TIPO_CONTRATO	NUMBER
 *COLEGIO	VARCHAR2(10 BYTE)
 *ESTADO_CONTRATO	NUMBER
 *CIF	VARCHAR2(9 BYTE)
 *RAZON_SOCIAL	VARCHAR2(120 BYTE)
 *ANAGRAMA_CONTRATO	VARCHAR2(5 BYTE)
 *ID_PROVINCIA	VARCHAR2(2 BYTE)
 *ID_MUNICIPIO	VARCHAR2(3 BYTE)
 *COD_POSTAL	VARCHAR2(5 BYTE)
 *ID_TIPO_VIA	NVARCHAR2(5 CHAR)
 *VIA	VARCHAR2(120 BYTE)
 *NUMERO	VARCHAR2(5 BYTE)
 *LETRA	VARCHAR2(5 BYTE)
 *ESCALERA	VARCHAR2(5 BYTE)
 *PISO	VARCHAR2(5 BYTE)
 *PUERTA	VARCHAR2(5 BYTE)
 *JEFATURA_PROVINCIAL	VARCHAR2(3 BYTE)
 *TELEFONO	NUMBER
 *CORREO_ELECTRONICO	VARCHAR2(100 BYTE)
 *FECHA_INICIO	DATE
 *FECHA_FIN	DATE
 */

public class DatosContratoBean {
	
	private BigDecimal idContrato;	
	private BigDecimal idTipoContrato; 
	private ColegioBean colegio;
	private Estado estadoContrato;
	private String cif;
	private String razonSocial;
	private String anagramaContrato;
	private Direccion direccion;
	private JefaturaTrafico jefatura;
	private String telefono;
	private String correoElectronico;
	private Fecha fechaInicio;
	private Fecha fechaFin;

	
	public DatosContratoBean() {		
	}
	
	public DatosContratoBean(Boolean inicio) {
		this();
		fechaInicio=new Fecha();
		fechaFin= new Fecha();
		direccion= new Direccion(true);
		colegio= new ColegioBean();
		jefatura = new JefaturaTrafico(true);
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(BigDecimal idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public ColegioBean getColegio() {
		return colegio;
	}

	public void setColegio(ColegioBean colegio) {
		this.colegio = colegio;
	}

	public Estado getEstadoContrato() {
		return estadoContrato;
	}

	public void setEstadoContrato(String estadoContrato) {
		this.estadoContrato = Estado.convertir(estadoContrato);
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getAnagramaContrato() {
		return anagramaContrato;
	}

	public void setAnagramaContrato(String anagramaContrato) {
		this.anagramaContrato = anagramaContrato;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public JefaturaTrafico getJefatura() {
		return jefatura;
	}

	public void setJefatura(JefaturaTrafico jefatura) {
		this.jefatura = jefatura;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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

}
