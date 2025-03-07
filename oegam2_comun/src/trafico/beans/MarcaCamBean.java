package trafico.beans;

import utilidades.estructuras.Fecha;


public class MarcaCamBean{
	private String tipVehi;
	private String cdMarca;	
	private Fecha fecDesde;
	private String dsMarca;
	private Fecha fecHasta;
	
	
	public MarcaCamBean() {
	}
	
	public MarcaCamBean(boolean inicializar) {
	}

	public String getTipVehi() {
		return tipVehi;
	}

	public void setTipVehi(String tipVehi) {
		this.tipVehi = tipVehi;
	}

	public String getCdMarca() {
		return cdMarca;
	}

	public void setCdMarca(String cdMarca) {
		this.cdMarca = cdMarca;
	}

	public Fecha getFecDesde() {
		return fecDesde;
	}

	public void setFecDesde(Fecha fecDesde) {
		this.fecDesde = fecDesde;
	}

	public String getDsMarca() {
		return dsMarca;
	}

	public void setDsMarca(String dsMarca) {
		this.dsMarca = dsMarca;
	}

	public Fecha getFecHasta() {
		return fecHasta;
	}

	public void setFecHasta(Fecha fecHasta) {
		this.fecHasta = fecHasta;
	}

}
