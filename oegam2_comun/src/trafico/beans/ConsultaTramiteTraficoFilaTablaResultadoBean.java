package trafico.beans;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;

import hibernate.entities.trafico.Vehiculo;
import trafico.utiles.enumerados.TipoTramiteTrafico;

public class ConsultaTramiteTraficoFilaTablaResultadoBean implements BeanResultTransformPaginatedList {

	private static final long serialVersionUID = 1L;
	private Long numExpediente;
	private String refPropia;
	private Vector<Vehiculo> vehiculo;
	private Vector<Tasa> tasa;
	private String tipoTramite;
	private String estado;
	private Short presentadoJpt;
	private String tipoDistintivo;
	private String respuesta;

	public Long getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getTipoDistintivo() {
		return tipoDistintivo;
	}
	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}
	public String getRefPropia() {
		return refPropia;
	}
	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}
	public Vector<Vehiculo> getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vector<Vehiculo> vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Vector<Tasa> getTasa() {
		return tasa;
	}
	public void setTasa(Vector<Tasa> tasa) {
		this.tasa = tasa;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Short getPresentadoJpt() {
		return presentadoJpt;
	}
	public void setPresentadoJpt(Short presentadoJpt) {
		this.presentadoJpt = presentadoJpt;
	}

	@Override
	public List transformList(List arg0) {
		//No es necesario hacer nada con la lista
		return arg0;
	}

	@Override
	public Object transformTuple(Object[] valores, String[] arg1) {
		ConsultaTramiteTraficoFilaTablaResultadoBean c = new ConsultaTramiteTraficoFilaTablaResultadoBean();
		c.setNumExpediente((Long)valores[0]);
		c.setRefPropia((String)valores[1]);
		Vector<Vehiculo> vectorVehiculo = new Vector<Vehiculo>();
		Vehiculo v = new Vehiculo();
		v.setBastidor((String)valores[2]);
		v.setMatricula((String)valores[3]);
		if (v.getBastidor()!=null || v.getMatricula()!=null){
			vectorVehiculo.add(v);
		}
		c.setVehiculo(vectorVehiculo);
		Vector<Tasa> vectorTasa = new Vector<Tasa>();
		Tasa t = new Tasa();
		t.setTipoTasa((String)valores[4]);
		t.setCodigoTasa((String)valores[5]);
		if (t.getTipoTasa() != null || t.getCodigoTasa() != null){
			vectorTasa.add(t);
		}
		c.setTasa(vectorTasa);
		c.setTipoTramite(TipoTramiteTrafico.convertirTexto((String)valores[6]));
		c.setEstado(EstadoTramiteTrafico.convertirTexto(((BigDecimal)valores[7]).toString()));
		c.setPresentadoJpt((Short)valores[8]);
		c.setRespuesta((String)valores[9]);
		return c;
	}

	/**
	 * Este método genera las proyecciones que se van a realizar en la query, y además, otorga el orden de las mismas.
	 * En el método transformTuple se debe utilizar el mismo orden que se indica aquí.
	 * @return
	 */
	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<String>();
		proyecciones.add("numExpediente");
		proyecciones.add("refPropia");
		proyecciones.add("vehiculo.bastidor");
		proyecciones.add("vehiculo.matricula");
		proyecciones.add("tasa.tipoTasa");
		proyecciones.add("tasa.codigoTasa");
		proyecciones.add("tipoTramite");
		proyecciones.add("estado");
		proyecciones.add("presentadoJpt");
		proyecciones.add("respuesta");
		return proyecciones;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}