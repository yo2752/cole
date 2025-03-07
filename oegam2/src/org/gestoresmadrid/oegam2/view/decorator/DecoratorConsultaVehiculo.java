package org.gestoresmadrid.oegam2.view.decorator;

import hibernate.entities.trafico.TipoVehiculo;
import hibernate.entities.trafico.Vehiculo;

import org.displaytag.decorator.TableDecorator;

public class DecoratorConsultaVehiculo extends TableDecorator {

	public String getTipoVehiculoDescripcion() {
		String desc = "";
		Vehiculo fila = (Vehiculo) getCurrentRowObject();
		TipoVehiculo tipo = fila.getTipoVehiculoBean();
		if (tipo != null) {
			if (tipo.getTipoVehiculo() != null) {
				desc += tipo.getTipoVehiculo();
			}
			if (tipo.getDescripcion() != null) {
				if (desc.length() > 0) {
					desc += " - ";
				}
				desc += tipo.getDescripcion();
			}
		}
		return desc;
	}
}