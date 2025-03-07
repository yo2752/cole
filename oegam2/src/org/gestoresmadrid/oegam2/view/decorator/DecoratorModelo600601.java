package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.bean.Modelo600_601Bean;

public class DecoratorModelo600601 extends TableDecorator{
	
	public String addRowClass(){
		Modelo600_601Bean modelo600_601Bean = (Modelo600_601Bean) getCurrentRowObject();
		if(EstadoModelos.Anulado.getValorEnum().equals(modelo600_601Bean.getEstado().toString())){
			return "enlaceAnulado anulado";
		}
		if(EstadoModelos.FinalizadoOK.getValorEnum().equals(modelo600_601Bean.getEstado().toString())){
			 return "enlaceImpreso impreso";
		}
		return null;
	}
}
