package org.gestoresmadrid.oegam2.documentos.base.beans.paginacion;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.springframework.stereotype.Component;

@Component
public class ResultTransformerBuscarDocumentosBasePaginatedList implements BeanResultTransformPaginatedList {

	private static final long serialVersionUID = 160735429513722242L;
	private String docId;
	private String carpeta;
	private String numColegiado;
	private Date fechaPresentacion;
	private Boolean indDocYb;
	
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public Boolean getIndDocYb() {
		return indDocYb;
	}
	public void setIndDocYb(Boolean indDocYb) {
		this.indDocYb = indDocYb;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		ResultTransformerBuscarDocumentosBasePaginatedList o = new ResultTransformerBuscarDocumentosBasePaginatedList();
		if (tuple[0]!=null){
			o.setDocId((String)tuple[0]);
		}
		if (tuple[1]!=null){
			o.setCarpeta((String)tuple[1]);
		}
		if (tuple[2]!=null){
			o.setFechaPresentacion((Date)tuple[2]);
		}
		if (tuple[3]!=null){
			o.setNumColegiado((String)tuple[3]);
		}
		if (tuple[4]!=null){
			o.setIndDocYb((Boolean)tuple[4]);
		}
		return o;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<String>();
		proyecciones.add("docId");
		proyecciones.add("carpeta");
		proyecciones.add("fechaPresentacion");
		proyecciones.add("contrato.colegiado.numColegiado");
		proyecciones.add("indDocYb");
		
		return proyecciones;
	}

}