package org.gestoresmadrid.oegamBajas.checkBtv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nifGestor","cifGestoria","matricula","doiTitular1","doiTitular2","doiTitular3","textoLegal"})
@XmlRootElement(name = "Datos_Firmados")
public class DatosFirmados {
	
	@XmlElement(name = "DOI_Gestor", required = true)
    protected String nifGestor;
	@XmlElement(name = "DOI_Gestoria", required = true)
    protected String cifGestoria;
    @XmlElement(name = "Matricula", required = true)
    protected String matricula;
    @XmlElement(name = "DOI_Titular1", required = true)
    protected String doiTitular1;
    @XmlElement(name = "DOI_Titular2", required = true)
    protected String doiTitular2;
    @XmlElement(name = "DOI_Titular3", required = true)
    protected String doiTitular3;
    @XmlElement(name = "Texto_Legal", required = true)
    protected String textoLegal;
    
	/**
	 * @return the nifGestor
	 */
	public String getNifGestor() {
		return nifGestor;
	}
	/**
	 * @param nifGestor the nifGestor to set
	 */
	public void setNifGestor(String nifGestor) {
		this.nifGestor = nifGestor;
	}
	public String getCifGestoria() {
		return cifGestoria;
	}
	public void setCifGestoria(String cifGestoria) {
		this.cifGestoria = cifGestoria;
	}
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}
	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * @return the textoLegal
	 */
	public String getTextoLegal() {
		return textoLegal;
	}
	/**
	 * @param textoLegal the textoLegal to set
	 */
	public void setTextoLegal(String textoLegal) {
		this.textoLegal = textoLegal;
	}
	public String getDoiTitular1() {
		return doiTitular1;
	}
	public void setDoiTitular1(String doiTitular1) {
		this.doiTitular1 = doiTitular1;
	}
	public String getDoiTitular2() {
		return doiTitular2;
	}
	public void setDoiTitular2(String doiTitular2) {
		this.doiTitular2 = doiTitular2;
	}
	public String getDoiTitular3() {
		return doiTitular3;
	}
	public void setDoiTitular3(String doiTitular3) {
		this.doiTitular3 = doiTitular3;
	}

}
