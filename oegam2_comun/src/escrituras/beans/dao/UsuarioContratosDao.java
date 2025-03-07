package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
/*Select id_Contrato, Co.Num_Colegiado, C.Id_Contrato, C.Id_Tipo_Contrato, Tc.Tipo_Contrato,
C.Estado_Contrato, C.Cif, C.Razon_Social, C.Anagrama_Contrato, C.Id_Tipo_Via, V.Nombre Tipo_Via,
C.Via, C.Numero, C.Letra, C.Escalera, C.Piso, C.Puerta, C.Id_Provincia, P.Nombre Provincia,
c.id_municipio, m.nombre municipio, c.cod_postal, c.telefono, c.correo_electronico, 
c.fecha_inicio, c.fecha_fin*/

public class UsuarioContratosDao {

	private BigDecimal id_Contrato;
	
	private String colegio;
	private String jefatura_Provincial;
	private String num_Colegiado;	
	private BigDecimal  estado_Contrato;
	private String nombre;
	private String razon_Social;
	private String cif;
	
	
	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}
	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}
	public String getNum_Colegiado() {
		return num_Colegiado;
	}
	public void setNum_Colegiado(String numColegiado) {
		num_Colegiado = numColegiado;
	}
	public BigDecimal getEstado_Contrato() {
		return estado_Contrato;
	}
	public void setEstado_Contrato(BigDecimal estadoContrato) {
		estado_Contrato = estadoContrato;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRazon_Social() {
		return razon_Social;
	}
	public void setRazon_Social(String razonSocial) {
		razon_Social = razonSocial;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	public String getJefatura_Provincial() {
		return jefatura_Provincial;
	}
	public void setJefatura_Provincial(String jefaturaProvincial) {
		jefatura_Provincial = jefaturaProvincial;
	}
	
	
	
}