package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"listaIntervinientes","detalle","liquidacion","listaBienesUrbanos","listaBienesRusticos","listaOtrosBienes"})
@XmlRootElement(name = "declaracion")
public class Declaracion {
	@XmlAttribute(name = "codigo", required = true)
	protected String codigo;
	@XmlAttribute(name = "modelo", required = true)
	protected String modelo;
	@XmlAttribute(name = "version_xsd_modelo", required = false)
	protected String version_xsd_modelo;
	@XmlElementWrapper(name="lista_intervinientes")
	@XmlElement(name = "interviniente", required = true)
	protected List<Interviniente> listaIntervinientes;
	@XmlElement(name = "Detalle", required = true)
	protected Detalle detalle;
	@XmlElement(name = "Liquidacion", required = true)
	protected Liquidacion liquidacion;
	@XmlElementWrapper(name="lista_bienes_urbanos")
	@XmlElement(name = "bien_urbano", required = false)
	protected List<BienUrbano> listaBienesUrbanos;
	@XmlElementWrapper(name="lista_bienes_rusticos")
	@XmlElement(name = "bien_rustico", required = false)
	protected List<BienRustico> listaBienesRusticos;
	@XmlElementWrapper(name="lista_otros_bienes")
	@XmlElement(name = "otro_bien", required = false)
	protected List<OtroBien> listaOtrosBienes;
	
	public List<Interviniente> getListaIntervinientes() {
		return listaIntervinientes;
	}
	public void setListaIntervinientes(List<Interviniente> listaIntervinientes) {
		this.listaIntervinientes = listaIntervinientes;
	}
	public Detalle getDetalle() {
		return detalle;
	}
	public void setDetalle(Detalle detalle) {
		this.detalle = detalle;
	}
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}
	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}
	public List<BienUrbano> getListaBienesUrbanos() {
		return listaBienesUrbanos;
	}
	public void setListaBienesUrbanos(List<BienUrbano> listaBienesUrbanos) {
		this.listaBienesUrbanos = listaBienesUrbanos;
	}
	public List<BienRustico> getListaBienesRusticos() {
		return listaBienesRusticos;
	}
	public void setListaBienesRusticos(List<BienRustico> listaBienesRusticos) {
		this.listaBienesRusticos = listaBienesRusticos;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getVersion_xsd_modelo() {
		return version_xsd_modelo;
	}
	public void setVersion_xsd_modelo(String version_xsd_modelo) {
		this.version_xsd_modelo = version_xsd_modelo;
	}
	/**
	 * @return the listaOtrosBienes
	 */
	public List<OtroBien> getListaOtrosBienes() {
		return listaOtrosBienes;
	}
	/**
	 * @param listaOtrosBienes the listaOtrosBienes to set
	 */
	public void setListaOtrosBienes(List<OtroBien> listaOtrosBienes) {
		this.listaOtrosBienes = listaOtrosBienes;
	}
	
}
