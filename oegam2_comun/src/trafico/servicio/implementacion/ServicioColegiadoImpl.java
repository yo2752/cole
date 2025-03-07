package trafico.servicio.implementacion;

import hibernate.entities.general.Colegio;
import trafico.modelo.impl.ModeloColegiadoImpl;
import trafico.modelo.interfaz.ModeloColegiadoInt;
import trafico.servicio.interfaz.ServicioColegiadoInt;

public class ServicioColegiadoImpl implements ServicioColegiadoInt {

	@Override
	public Colegio obtenerColegioContrato(String colegio) {
		ModeloColegiadoInt modelo = new ModeloColegiadoImpl();
		return modelo.obtenerColegioContrato(colegio);
	}

}