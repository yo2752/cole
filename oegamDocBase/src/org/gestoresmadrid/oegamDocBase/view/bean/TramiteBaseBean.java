package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TramiteBaseBean implements Serializable{
	
	private static final long serialVersionUID = 5527775222089069823L;
	
	private int numero;
	private String numExpediente;
	private String matricula;
	private String refPropia;
	private String bastidor;
	private String numColegiado;
	private String fechaPresentacion;
	private String tipoTasa;
	private String impresionPermiso;
	private String cmc;
	private String cmv;
	private String cm;
	private String poderesFicha;
	private Boolean esNubePuntos;
	private List<BigDecimal> listaTramitesSimultaneos;

	public void addListaTramitesSimultaneos(BigDecimal numExpediente){
		if(listaTramitesSimultaneos == null){
			listaTramitesSimultaneos = new ArrayList<BigDecimal>();
		}
		listaTramitesSimultaneos.add(numExpediente);
	}
	
	public TramiteBaseBean() {
		super();
		this.esNubePuntos = Boolean.FALSE;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getBastidor() {
		return bastidor;
	}
	
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	
	public String getNumColegiado() {
		return numColegiado;
	}
	
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	public String getTipoTasa() {
		return tipoTasa;
	}
	
	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public String getCmc() {
		return cmc;
	}

	public void setCmc(String cmc) {
		this.cmc = cmc;
	}

	public String getCmv() {
		return cmv;
	}

	public void setCmv(String cmv) {
		this.cmv = cmv;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}
	

	public String getImpresionPermiso() {
		return impresionPermiso;
	}

	public void setImpresionPermiso(String impresionPermiso) {
		this.impresionPermiso = impresionPermiso;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public List<BigDecimal> getListaTramitesSimultaneos() {
		return listaTramitesSimultaneos;
	}

	public void setListaTramitesSimultaneos(List<BigDecimal> listaTramitesSimultaneos) {
		this.listaTramitesSimultaneos = listaTramitesSimultaneos;
	}

	public Boolean getEsNubePuntos() {
		return esNubePuntos;
	}

	public void setEsNubePuntos(Boolean esNubePuntos) {
		this.esNubePuntos = esNubePuntos;
	}

	public String getCm() {
		return cm;
	}

	public void setCm(String cm) {
		this.cm = cm;
	}

	public String getPoderesFicha() {
		return poderesFicha;
	}

	public void setPoderesFicha(String poderesFicha) {
		this.poderesFicha = poderesFicha;
	}

	
	/* FIN Mantis 0011883 */
	
	/* FIN GETTERS Y SETTERS */

}