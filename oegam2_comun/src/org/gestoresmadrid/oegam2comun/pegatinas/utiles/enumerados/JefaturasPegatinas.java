package org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados;

public enum JefaturasPegatinas {

	AV("AV", ""),
	CR("CR",""),
	CU("CU",""),
	GU("GU",""),
	SG("SG",""),
	M("M",""),
	M1("M","1"),
	M2("M","2");
	
	private String jefatura;
	private String sucursal;
	
	private JefaturasPegatinas(String jefatura, String sucursal) {
		this.jefatura = jefatura;
		this.sucursal = sucursal;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

}
