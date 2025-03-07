package escrituras.beans.dao;

import java.math.BigDecimal;


/**
 * Bean que almacena datos de la tabla PERSONA
 *
 */
/** P_NUM_COLEGIADO IN COLEGIADO.NUM_COLEGIADO%TYPE,
                      P_TIPO_CREDITO IN CREDITOS.TIPO_CREDITO%TYPE,
                      CREDITOS_SUMAR IN NUMBER,
                      P_ID_USUARIO IN USUARIO.ID_USUARIO%TYPE,
                      P_CREDITOS OUT NUMBER,
                      p_code out number,
                      P_SQLERRM OUT VARCHAR2) AS */

public class CargarCreditoDao {
     	
	private String num_Colegiado;
	private BigDecimal id_Contrato;
	private String tipo_Credito;
	private BigDecimal id_Usuario;
	private BigDecimal creditos_Sumar;
	private BigDecimal creditos;
	

	
	public BigDecimal getId_Usuario() {
		return id_Usuario;
	}
	public void setId_Usuario(BigDecimal idUsuario) {
		id_Usuario = idUsuario;
	}
	public BigDecimal getCreditos_Sumar() {
		return creditos_Sumar;
	}
	public void setCreditos_Sumar(BigDecimal creditosSumar) {
		creditos_Sumar = creditosSumar;
	}
	public String getTipo_Credito() {
		return tipo_Credito;
	}
	public void setTipo_Credito(String tipoCredito) {
		tipo_Credito = tipoCredito;
	}
	
	public String getNum_Colegiado() {
		return num_Colegiado;
	}
	public void setNum_Colegiado(String numColegiado) {
		num_Colegiado = numColegiado;
	}
	public BigDecimal getCreditos() {
		return creditos;
	}
	public void setCreditos(BigDecimal creditos) {
		this.creditos = creditos;
	}
	public BigDecimal getId_Contrato() {
		return id_Contrato;
	}
	public void setId_Contrato(BigDecimal idContrato) {
		id_Contrato = idContrato;
	}
	
	
	
	
	
}