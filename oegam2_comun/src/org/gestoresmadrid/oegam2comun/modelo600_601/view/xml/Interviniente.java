package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nif","nombre","apellido1","apellido2","nombreRazonsocial","provincia","provinciaDesc","municipio","municipioDesc","siglas",
		"siglasDesc","nombreVia","numero","escalera","piso","puerta","cp","telf","particIgual","grdoPartic","fechaNacimiento","parentesco","parentescoDesc",
		"tituloSucesorio","tituloSucesorioDesc","grupo","patrimonio","patrimonioDesc","minusvalia","grdoMinusvalia"})
@XmlRootElement(name = "interviniente")
public class Interviniente {

	 @XmlAttribute(name = "tipo", required = true)
	 protected String tipoIntervienite;
	 @XmlElement(name = "nif", required = true)
	 protected String nif;
	 @XmlElement(name = "nombre", required = true)
	 protected String nombre;
	 @XmlElement(name = "apellido1", required = true)
	 protected String apellido1;
	 @XmlElement(name = "apellido2", required = true,defaultValue="")
	 protected String apellido2;
	 @XmlElement(name = "nombre_razonsocial", required = true)
	 protected String nombreRazonsocial;
	 @XmlElement(name = "provincia", required = true)
	 protected String provincia;
	 @XmlElement(name = "provinciaDesc", required = true)
	 protected String provinciaDesc;
	 @XmlElement(name = "municipio", required = true)
	 protected String municipio;
	 @XmlElement(name = "municipioDesc", required = true)
	 protected String municipioDesc;
	 @XmlElement(name = "siglas", required = true)
	 protected String siglas;
	 @XmlElement(name = "siglasDesc", required = true)
	 protected String siglasDesc;
	 @XmlElement(name = "nombre_via", required = true)
	 protected String nombreVia;
	 @XmlElement(name = "numero", required = true)
	 protected String numero;
	 @XmlElement(name = "escalera", required = true)
	 protected String escalera;
	 @XmlElement(name = "piso", required = true)
	 protected String piso;
	 @XmlElement(name = "puerta", required = true)
	 protected String puerta;
	 @XmlElement(name = "cp", required = true)
	 protected String cp;
	 @XmlElement(name = "telf", required = true)
	 protected String telf;
	 @XmlElement(name = "partic_igual", required = true)
	 protected String particIgual;
	 @XmlElement(name = "grdo_partic", required = true)
	 protected String grdoPartic;
	 @XmlElement(name = "fec_nacimiento", required = true)
	 protected String fechaNacimiento;
	 @XmlElement(name = "parentesco", required = true,defaultValue="")
	 protected String parentesco;
	 @XmlElement(name = "parentescoDesc", required = true)
	 protected String parentescoDesc;
	 @XmlElement(name = "titulo_sucesorio", required = true)
	 protected String tituloSucesorio;
	 @XmlElement(name = "titulo_sucesorioDesc", required = true)
	 protected String tituloSucesorioDesc;
	 @XmlElement(name = "grupo", required = true)
	 protected String grupo;
	 @XmlElement(name = "patrimonio", required = true)
	 protected String patrimonio;
	 @XmlElement(name = "patrimonioDesc", required = true)
	 protected String patrimonioDesc;
	 @XmlElement(name = "minusvalia", required = true)
	 protected String minusvalia;
	 @XmlElement(name = "grdo_minusvalia", required = true)
	 protected String grdoMinusvalia;
	 
	public String getTipoIntervienite() {
		return tipoIntervienite;
	}
	public void setTipoIntervienite(String tipoIntervienite) {
		this.tipoIntervienite = tipoIntervienite;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getNombreRazonsocial() {
		return nombreRazonsocial;
	}
	public void setNombreRazonsocial(String nombreRazonsocial) {
		this.nombreRazonsocial = nombreRazonsocial;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getProvinciaDesc() {
		return provinciaDesc;
	}
	public void setProvinciaDesc(String provinciaDesc) {
		this.provinciaDesc = provinciaDesc;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getMunicipioDesc() {
		return municipioDesc;
	}
	public void setMunicipioDesc(String municipioDesc) {
		this.municipioDesc = municipioDesc;
	}
	public String getSiglas() {
		return siglas;
	}
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	public String getSiglasDesc() {
		return siglasDesc;
	}
	public void setSiglasDesc(String siglasDesc) {
		this.siglasDesc = siglasDesc;
	}
	public String getNombreVia() {
		return nombreVia;
	}
	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEscalera() {
		return escalera;
	}
	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getTelf() {
		return telf;
	}
	public void setTelf(String telf) {
		this.telf = telf;
	}
	public String getParticIgual() {
		return particIgual;
	}
	public void setParticIgual(String particIgual) {
		this.particIgual = particIgual;
	}
	public String getGrdoPartic() {
		return grdoPartic;
	}
	public void setGrdoPartic(String grdoPartic) {
		this.grdoPartic = grdoPartic;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	public String getParentescoDesc() {
		return parentescoDesc;
	}
	public void setParentescoDesc(String parentescoDesc) {
		this.parentescoDesc = parentescoDesc;
	}
	public String getTituloSucesorio() {
		return tituloSucesorio;
	}
	public void setTituloSucesorio(String tituloSucesorio) {
		this.tituloSucesorio = tituloSucesorio;
	}
	public String getTituloSucesorioDesc() {
		return tituloSucesorioDesc;
	}
	public void setTituloSucesorioDesc(String tituloSucesorioDesc) {
		this.tituloSucesorioDesc = tituloSucesorioDesc;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getPatrimonioDesc() {
		return patrimonioDesc;
	}
	public void setPatrimonioDesc(String patrimonioDesc) {
		this.patrimonioDesc = patrimonioDesc;
	}
	public String getMinusvalia() {
		return minusvalia;
	}
	public void setMinusvalia(String minusvalia) {
		this.minusvalia = minusvalia;
	}
	public String getGrdoMinusvalia() {
		return grdoMinusvalia;
	}
	public void setGrdoMinusvalia(String grdoMinusvalia) {
		this.grdoMinusvalia = grdoMinusvalia;
	}
	 
}
