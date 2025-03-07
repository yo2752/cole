package org.gestoresmadrid.oegam2comun.distintivo.view.bean;

import java.io.Serializable;

public class ConsultaVehNoMatrOegamBean implements Serializable {

	private static final long serialVersionUID = 6499006710785116880L;

	private Long id;
	private String matricula;
	private String tipoDistintivo;
	private String descContrato;
	private String estadoPetImp;
	private String estadoDstv;
	private String respPetPermDstv;
	private String fechaAlta;
	private String primeraImpresion;

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getDescContrato() {
		return descContrato;
	}

	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}

	public String getEstadoPetImp() {
		return estadoPetImp;
	}

	public void setEstadoPetImp(String estadoPetImp) {
		this.estadoPetImp = estadoPetImp;
	}

	public String getEstadoDstv() {
		return estadoDstv;
	}

	public void setEstadoDstv(String estadoDstv) {
		this.estadoDstv = estadoDstv;
	}

	public String getRespPetPermDstv() {
		return respPetPermDstv;
	}

	public void setRespPetPermDstv(String respPetPermDstv) {
		this.respPetPermDstv = respPetPermDstv;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getPrimeraImpresion() {
		return primeraImpresion;
	}

	public void setPrimeraImpresion(String primeraImpresion) {
		this.primeraImpresion = primeraImpresion;
	}

}
