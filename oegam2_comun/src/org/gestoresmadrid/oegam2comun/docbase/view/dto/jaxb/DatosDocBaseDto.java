package org.gestoresmadrid.oegam2comun.docbase.view.dto.jaxb;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("DatosDocBase")
public class DatosDocBaseDto {
	
	private String usuario;
	private String contrato;
	private String colegiado;
	private String nif;
	private String nombreGestor;
	private String carpeta;
	private String docId;
	@XStreamImplicit(itemFieldName="matricula")
	private List<String> listaMatriculas;
	private String bastidor;
	
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public String getColegiado() {
		return colegiado;
	}
	public void setColegiado(String colegiado) {
		this.colegiado = colegiado;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombreGestor() {
		return nombreGestor;
	}
	public void setNombreGestor(String nombreGestor) {
		this.nombreGestor = nombreGestor;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	public List<String> getListaMatriculas() {
		return listaMatriculas;
	}
	public void setListaMatriculas(List<String> listaMatriculas) {
		this.listaMatriculas = listaMatriculas;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
