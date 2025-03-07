package trafico.beans.resultTransformer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.transform.ResultTransformer;

public class ConsultaTramiteTraficoNumExpEstadoResultadoBean implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	private Long numExpediente;
	private BigDecimal estado;
	private Date fechaPresentacion;

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	@Override
	public List transformList(List arg0) {
		//No es necesario hacer nada con la lista
		return arg0;
	}

	@Override
	public Object transformTuple(Object[] valores, String[] arg1) {
		ConsultaTramiteTraficoNumExpEstadoResultadoBean c = new ConsultaTramiteTraficoNumExpEstadoResultadoBean();
		c.setNumExpediente((Long)valores[0]);
		c.setEstado((BigDecimal)valores[1]);
		c.setFechaPresentacion((Date)valores[2]);
		return c;
	}

	/**
	 * Este método genera las proyecciones que se van a realizar en la query, y además, otorga el orden de las mismas.
	 * En el método transformTuple se debe utilizar el mismo orden que se indica aquí.
	 * @return
	 */
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<>();
		proyecciones.add("numExpediente");
		proyecciones.add("estado");
		proyecciones.add("fechaPresentacion");
		return proyecciones;
	}
}