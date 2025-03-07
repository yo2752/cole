package trafico.beans;

import java.util.List;

import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.hibernate.transform.ResultTransformer;

import hibernate.entities.trafico.Vehiculo;

public class ConsultaTramiteTraficoSolicitudFilaTablaResultadoBean implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	private Long numExpediente;
	private Vehiculo vehiculo;
	private Tasa tasa;

	public Long getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Tasa getTasa() {
		return tasa;
	}
	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

	@Override
	public List transformList(List arg0) {
		// No es necesario hacer nada con la lista
		return arg0;
	}
	@Override
	public Object transformTuple(Object[] valores, String[] arg1) {
		ConsultaTramiteTraficoSolicitudFilaTablaResultadoBean c = new ConsultaTramiteTraficoSolicitudFilaTablaResultadoBean();
		c.setNumExpediente((Long)valores[0]);
		Vehiculo v = new Vehiculo();
		String matricula = (String)valores[5];
		String bastidor = (String)valores[6];
		if (matricula != null || bastidor != null) {
			v.setBastidor(bastidor);
			v.setMatricula(matricula);
		} else {
			v.setBastidor((String)valores[1]);
			v.setMatricula((String)valores[2]);
		}
		c.setVehiculo(v);
		Tasa t = new Tasa();
		t.setTipoTasa((String)valores[3]);
		t.setCodigoTasa((String)valores[4]);
		c.setTasa(t);
		return c;
	}

}