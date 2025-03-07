package org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest;

import java.io.Serializable;

public class PedidoInfoRest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6643787780937562956L;
	
	private Long   id;
	private String fecha;
	private String codigo;
	private String estado;
	private String observaciones;
	private String pedidoDgt;
	private String codInicial;
	private String codFinal;
	private Long   unidades;
	private Long   total;
	
	private AutorRest        autor;
	private ColegioRest      colegio;
	private DelegacionRest   delegacion;
	private MaterialInfoRest material;
	

	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getCodInicial() {
		return codInicial;
	}
	public void setCodInicial(String codInicial) {
		this.codInicial = codInicial;
	}
	public String getCodFinal() {
		return codFinal;
	}
	public void setCodFinal(String codFinal) {
		this.codFinal = codFinal;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getPedidoDgt() {
		return pedidoDgt;
	}
	public void setPedidoDgt(String pedidoDgt) {
		this.pedidoDgt = pedidoDgt;
	}
	public Long getUnidades() {
		return unidades;
	}
	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public AutorRest getAutor() {
		return autor;
	}
	public void setAutor(AutorRest autor) {
		this.autor = autor;
	}
	public ColegioRest getColegio() {
		return colegio;
	}
	public void setColegio(ColegioRest colegio) {
		this.colegio = colegio;
	}
	public DelegacionRest getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(DelegacionRest delegacion) {
		this.delegacion = delegacion;
	}
	public MaterialInfoRest getMaterial() {
		return material;
	}
	public void setMaterial(MaterialInfoRest material) {
		this.material = material;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "PedidoInfoRest [id=" + id + ", fecha=" + fecha + ", codigo=" + codigo + ", estado=" + estado
				+ ", observaciones=" + observaciones + ", pedidoDgt=" + pedidoDgt + ", codInicial=" + codInicial
				+ ", codFinal=" + codFinal + ", unidades=" + unidades + ", total=" + total + "]";
	}
	
	
	
}
