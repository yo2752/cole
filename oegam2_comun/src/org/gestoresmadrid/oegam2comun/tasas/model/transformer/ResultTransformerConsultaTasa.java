package org.gestoresmadrid.oegam2comun.tasas.model.transformer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;

public class ResultTransformerConsultaTasa implements BeanResultTransformPaginatedList {

	private static final long serialVersionUID = -2160334183211842383L;

	private String codigoTasa;
	private String tipoTasa;
	private String tipoAlmacen;
	private Date fechaAlta;

	private String numExpediente;
	private Date fechaAsignacion;
	private String apellidosNombre;

	private FormatoTasa formato;
	private BigDecimal importadoIcogam;

	private String bloqueada;

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getApellidosNombre() {
		return apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public FormatoTasa getFormato() {
		return formato;
	}

	public void setFormato(FormatoTasa formato) {
		this.formato = formato;
	}

	public BigDecimal getImportadoIcogam() {
		return importadoIcogam;
	}

	public void setImportadoIcogam(BigDecimal importadoIcogam) {
		this.importadoIcogam = importadoIcogam;
	}

	public String getBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(String bloqueada) {
		this.bloqueada = bloqueada;
	}
	
	public String getTipoAlmacen() {
		return tipoAlmacen;
	}

	public void setTipoAlmacen(String tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		ResultTransformerConsultaTasa rt = new ResultTransformerConsultaTasa();
		if (tuple[0] != null) {
			rt.setCodigoTasa((String) tuple[0]);
		}
		if (tuple[1] != null) {
			rt.setTipoTasa((String) tuple[1]);
		}
		if (tuple[2] != null) {
			rt.setFechaAlta((Date) tuple[2]);
		}

		rt.setNumExpediente(tuple[3] != null ? tuple[3].toString() : null);
		rt.setFechaAsignacion((Date) tuple[4]);

		rt.setApellidosNombre((String) tuple[5]);
		rt.setFormato(FormatoTasa.convertir((Integer) tuple[6]));
		rt.setImportadoIcogam((BigDecimal) tuple[7]);
		rt.setBloqueada((String) tuple[8]);
		rt.setTipoAlmacen((String) tuple[9]);
		return rt;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<String>();
		proyecciones.add("codigoTasa");
		proyecciones.add("tipoTasa");
		proyecciones.add("fechaAlta");
		proyecciones.add("tramiteTrafico.numExpediente");
		proyecciones.add("fechaAsignacion");
		proyecciones.add("usuario.apellidosNombre");
		proyecciones.add("formato");
		proyecciones.add("importadoIcogam");
		proyecciones.add("bloqueada");
		proyecciones.add("tipoAlmacen");
		return proyecciones;
	}
}
