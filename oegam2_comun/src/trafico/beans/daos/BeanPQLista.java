package trafico.beans.daos;

import java.math.BigDecimal;

/**
 * Bean del que heredan todos los beanPQ que vayan a listar. 
 * @author Cesar Sanchez
 *
 */


public class BeanPQLista extends BeanPQGeneral {

	
	 private BigDecimal PAGINA;	
	 private BigDecimal NUM_REG; 
	 private String COLUMNA_ORDEN;
	 private String     ORDEN; 
	 private BigDecimal CUENTA;
	
	 public BigDecimal getPAGINA() {
		return PAGINA;
	}
	public void setPAGINA(BigDecimal pAGINA) {
		PAGINA = pAGINA;
	}
	public BigDecimal getNUM_REG() {
		return NUM_REG;
	}
	public void setNUM_REG(BigDecimal nUMREG) {
		NUM_REG = nUMREG;
	}
	public String getCOLUMNA_ORDEN() {
		return COLUMNA_ORDEN;
	}
	public void setCOLUMNA_ORDEN(String columnaOrden) {
		COLUMNA_ORDEN = columnaOrden;
	}
	public String getORDEN() {
		return ORDEN;
	}
	public void setORDEN(String oRDEN) {
		ORDEN = oRDEN;
	}
	public BigDecimal getCUENTA() {
		return CUENTA;
	}
	public void setCUENTA(BigDecimal cUENTA) {
		CUENTA = cUENTA;
	}
	 
	 
	
}
