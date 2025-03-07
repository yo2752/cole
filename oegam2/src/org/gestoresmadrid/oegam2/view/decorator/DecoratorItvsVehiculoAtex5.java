package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosDefectoItvAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.DatosItvAtex5Dto;

public class DecoratorItvsVehiculoAtex5 extends TableDecorator {

	public String getListaDefectos(){
		DatosItvAtex5Dto datosItvsAtex5 = (DatosItvAtex5Dto) getCurrentRowObject();
		String descripcion = "";

		if (datosItvsAtex5.getListaDefectos() != null && !datosItvsAtex5.getListaDefectos().isEmpty()) {
			for (DatosDefectoItvAtex5Dto datosDefectoItvAtex5Dto : datosItvsAtex5.getListaDefectos()) {
				if (!descripcion.isEmpty()) {
					descripcion += ", ";
				}
				descripcion += datosDefectoItvAtex5Dto.getTipoDefectoItv() + ":" + datosDefectoItvAtex5Dto.getGravedadDefectoItv();
			}
		}
		return descripcion;
	}

}