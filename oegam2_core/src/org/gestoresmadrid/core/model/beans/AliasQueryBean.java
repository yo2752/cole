package org.gestoresmadrid.core.model.beans;

public class AliasQueryBean {
	
	private Class clase;
	private String referencia;
	private String aliasName;
	private Integer tipoJoin;
	
	public AliasQueryBean(Class clase, String referencia, String aliasName){
		this.clase=clase;
		this.referencia = referencia;
		this.aliasName=aliasName;
	}
	
	public AliasQueryBean(Class clase, String referencia, String aliasName, Integer tipoJoin){
		this(clase, referencia, aliasName);
		this.tipoJoin=tipoJoin;
	}
	
	public Class getClase() {
		return clase;
	}
	public void setClase(Class clase) {
		this.clase = clase;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public Integer getTipoJoin() {
		return tipoJoin;
	}
	public void setTipoJoin(Integer tipoJoin) {
		this.tipoJoin = tipoJoin;
	}

}
