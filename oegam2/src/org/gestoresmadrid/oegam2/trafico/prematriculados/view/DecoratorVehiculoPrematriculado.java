package org.gestoresmadrid.oegam2.trafico.prematriculados.view;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.prematriculados.model.enumerados.EstadoConsultaVehiculoPrematiculado;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;

public class DecoratorVehiculoPrematriculado extends TableDecorator{

	/**
	 * Convierte el valor del Estado en el nombre del enumerado
	 * @return Valor en String del Enumerado
	 */
	public String getEstadoCaracteristicas(){
		VehiculoPrematriculadoVO fila = (VehiculoPrematriculadoVO) getCurrentRowObject();
		return EstadoConsultaVehiculoPrematiculado.convert(fila.getEstadoCaracteristicas()).getNombreEnum();
	}
	
	public String getEstadoFichaTecnica(){
		VehiculoPrematriculadoVO fila = (VehiculoPrematriculadoVO) getCurrentRowObject();
		return EstadoConsultaVehiculoPrematiculado.convert(fila.getEstadoFichaTecnica()).getNombreEnum();
	}
	
	
}
