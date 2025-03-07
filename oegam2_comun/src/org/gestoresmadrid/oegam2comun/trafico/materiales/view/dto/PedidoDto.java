package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;
import java.util.Date;

public class PedidoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7907257701189441156L;
	
	private Long    pedidoId;
	private Long    pedidoInvId;
	private Date    fecha;
	private String  codigo;	
	private Long    estado;
	private String  observaciones;
	private String  pedidoDgt;
	private String  codigoInicial;
	private String  codigoFinal;
	private Long    materialId;
	private String  materialNombre;
	private Long    unidades;
    private String  autorNombre;
	private String  jefaturaProvincial;
	private String  jefaturaDescripcion;
	private String  nomEstado;
	private Double  total;
	private boolean pedidoPermisosEntregado; 
	private boolean pedidoPermisos; 
	
	
	public PedidoDto() { }
	
	
	public PedidoDto(String observaciones, String pedidoDgt, Long materialId, Long unidades,
			String jefaturaProvincial) {
		this.observaciones = observaciones;
		this.pedidoDgt = pedidoDgt;
		this.materialId = materialId;
		this.unidades = unidades;
		this.jefaturaProvincial = jefaturaProvincial;
	}


	public Long getPedidoInvId() {
		return pedidoInvId;
	}
	public void setPedidoInvId(Long pedidoInvId) {
		this.pedidoInvId = pedidoInvId;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMaterialNombre() {
		return materialNombre;
	}
	public void setMaterialNombre(String materialNombre) {
		this.materialNombre = materialNombre;
	}
	public Long getUnidades() {
		return unidades;
	}
	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}
	public String getAutorNombre() {
		return autorNombre;
	}
	public void setAutorNombre(String autorNombre) {
		this.autorNombre = autorNombre;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
    
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCodigoInicial() {
		return codigoInicial;
	}
	public void setCodigoInicial(String codigoInicial) {
		this.codigoInicial = codigoInicial;
	}
	public String getCodigoFinal() {
		return codigoFinal;
	}
	public void setCodigoFinal(String codigoFinal) {
		this.codigoFinal = codigoFinal;
	}
	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}
	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNomEstado() {
		return nomEstado;
	}
	public void setNomEstado(String nomEstado) {
		this.nomEstado = nomEstado;
	}
	public String getPedidoDgt() {
		return pedidoDgt;
	}
	public void setPedidoDgt(String pedidoDgt) {
		this.pedidoDgt = pedidoDgt;
	}
	public String getJefaturaDescripcion() {
		return jefaturaDescripcion;
	}
	public void setJefaturaDescripcion(String jefaturaDescripcion) {
		this.jefaturaDescripcion = jefaturaDescripcion;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public boolean isPedidoPermisosEntregado() {
		return pedidoPermisosEntregado;
	}
	public void setPedidoPermisosEntregado(boolean pedidoPermisosEntregado) {
		this.pedidoPermisosEntregado = pedidoPermisosEntregado;
	}
	public boolean isPedidoPermisos() {
		return pedidoPermisos;
	}
	public void setPedidoPermisos(boolean pedidoPermisos) {
		this.pedidoPermisos = pedidoPermisos;
	}
    
}
