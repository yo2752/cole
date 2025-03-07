package org.gestoresmadrid.oegamComun.vehiculo.view.bean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ResultadoValVehiculoBean implements Serializable{

	private static final long serialVersionUID = 7485026986722888316L;
	
	Long valorModificado;
	Boolean error;
	String mensaje;
	String textoModificado;
	String bastidorAnt;
	String bastidorNue;
	String codigoItvAnt;
	String codigoItvNue;
	String MatriculaAnt;
	String matriculaNue;
	String tipoVehiculoAnt;
	String tipoVehiculoNue;
	
	public void addTextoModificado(String texto){
		if(StringUtils.isBlank(textoModificado)){
			textoModificado = "";
		}
		textoModificado += texto;
	}
	
	public ResultadoValVehiculoBean(Boolean error) {
		super();
		this.error = error;
		this.valorModificado = new Long(0);
	}

	public Long getValorModificado() {
		return valorModificado;
	}
	public void setValorModificado(Long valorModificado) {
		this.valorModificado = valorModificado;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getTextoModificado() {
		return textoModificado;
	}
	public void setTextoModificado(String textoModificado) {
		this.textoModificado = textoModificado;
	}

	public String getBastidorAnt() {
		return bastidorAnt;
	}

	public void setBastidorAnt(String bastidorAnt) {
		this.bastidorAnt = bastidorAnt;
	}

	public String getBastidorNue() {
		return bastidorNue;
	}

	public void setBastidorNue(String bastidorNue) {
		this.bastidorNue = bastidorNue;
	}

	public String getCodigoItvAnt() {
		return codigoItvAnt;
	}

	public void setCodigoItvAnt(String codigoItvAnt) {
		this.codigoItvAnt = codigoItvAnt;
	}

	public String getCodigoItvNue() {
		return codigoItvNue;
	}

	public void setCodigoItvNue(String codigoItvNue) {
		this.codigoItvNue = codigoItvNue;
	}

	public String getMatriculaAnt() {
		return MatriculaAnt;
	}

	public void setMatriculaAnt(String matriculaAnt) {
		MatriculaAnt = matriculaAnt;
	}

	public String getMatriculaNue() {
		return matriculaNue;
	}

	public void setMatriculaNue(String matriculaNue) {
		this.matriculaNue = matriculaNue;
	}

	public String getTipoVehiculoAnt() {
		return tipoVehiculoAnt;
	}

	public void setTipoVehiculoAnt(String tipoVehiculoAnt) {
		this.tipoVehiculoAnt = tipoVehiculoAnt;
	}

	public String getTipoVehiculoNue() {
		return tipoVehiculoNue;
	}

	public void setTipoVehiculoNue(String tipoVehiculoNue) {
		this.tipoVehiculoNue = tipoVehiculoNue;
	}
	
}
