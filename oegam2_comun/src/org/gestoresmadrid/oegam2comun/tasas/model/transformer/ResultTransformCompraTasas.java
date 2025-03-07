package org.gestoresmadrid.oegam2comun.tasas.model.transformer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.tasas.model.enumeration.EstadoCompra;
import org.springframework.stereotype.Component;

@Component
public class ResultTransformCompraTasas implements BeanResultTransformPaginatedList {

	private static final long serialVersionUID = 631193691647207542L;
	private String numJustificante791;
	private String nAutoliquidacion;
	private String nrc;
	private BigDecimal importeTotalTasas; 
	private Date fechaAlta;
	private Date fechaCompra;
	private EstadoCompra estado;
	private Long idCompra;
	private String refPropia;
	

	public String getNumJustificante791() {
		return numJustificante791;
	}
	public void setNumJustificante791(String numJustificante791) {
		this.numJustificante791 = numJustificante791;
	}
	public String getnAutoliquidacion() {
		return nAutoliquidacion;
	}
	public void setnAutoliquidacion(String nAutoliquidacion) {
		this.nAutoliquidacion = nAutoliquidacion;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public BigDecimal getImporteTotalTasas() {
		return importeTotalTasas;
	}
	public void setImporteTotalTasas(BigDecimal importeTotalTasas) {
		this.importeTotalTasas = importeTotalTasas;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public EstadoCompra getEstado() {
		return estado;
	}
	public void setEstado(EstadoCompra estado) {
		this.estado = estado;
	}
	public Long getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}
	public String getRefPropia() {
		return refPropia;
	}
	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		ResultTransformCompraTasas r = new ResultTransformCompraTasas();
		if (tuple[0]!=null){
			r.setNumJustificante791((String)tuple[0]);
		}
		if (tuple[1]!=null){
			r.setnAutoliquidacion((String)tuple[1]);
		}
		if (tuple[2]!=null){
			r.setNrc((String)tuple[2]);
		}
		if (tuple[3]!=null){
			r.setImporteTotalTasas((BigDecimal)tuple[3]);
		}
		if (tuple[4]!=null){
			r.setFechaAlta((Date)tuple[4]);
		}
		if (tuple[5]!=null){
			r.setFechaCompra((Date)tuple[5]);
		}
		if (tuple[6]!=null){
			r.setEstado(EstadoCompra.convertir((BigDecimal)tuple[6]));
		}
		if (tuple[7]!=null){
			r.setIdCompra((Long)tuple[7]);
		}
		if (tuple[8]!=null){
			r.setRefPropia((String)tuple[8]);
		}
		return r;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones= new Vector<String>();
		proyecciones.add("numJustificante791");
		proyecciones.add("nAutoliquidacion");
		proyecciones.add("nrc");
		proyecciones.add("importeTotalTasas");
		proyecciones.add("fechaAlta");
		proyecciones.add("fechaCompra");
		proyecciones.add("estado");
		proyecciones.add("idCompra");
		proyecciones.add("refPropia");
		return proyecciones;
	}
}