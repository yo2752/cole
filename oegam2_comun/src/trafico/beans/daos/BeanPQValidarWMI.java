package trafico.beans.daos;

import java.math.BigDecimal;

public class BeanPQValidarWMI extends BeanPQGeneral {
	
	private String P_BASTIDOR;
	private String P_CAT_EU;
	private BigDecimal P_MARCA;
	private String P_FABRICANTE;
	private String P_REVISADO;
	
	public String getP_BASTIDOR() {
		return P_BASTIDOR;
	}
	public void setP_BASTIDOR(String pBASTIDOR) {
		P_BASTIDOR = pBASTIDOR;
	}
	public String getP_CAT_EU() {
		return P_CAT_EU;
	}
	public void setP_CAT_EU(String pCATEU) {
		P_CAT_EU = pCATEU;
	}
	public BigDecimal getP_MARCA() {
		return P_MARCA;
	}
	public void setP_MARCA(BigDecimal pMARCA) {
		P_MARCA = pMARCA;
	}
	public String getP_FABRICANTE() {
		return P_FABRICANTE;
	}
	public void setP_FABRICANTE(String pFABRICANTE) {
		P_FABRICANTE = pFABRICANTE;
	}
	public String getP_REVISADO() {
		return P_REVISADO;
	}
	public void setP_REVISADO(String pREVISADO) {
		P_REVISADO = pREVISADO;
	}
	
}
