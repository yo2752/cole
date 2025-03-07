package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasVO;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;

public class DecoratorJefatura extends TableDecorator {
	
	public String getJefatura() {
		if (getCurrentRowObject() instanceof PegatinasStockVO){
			PegatinasStockVO stock = (PegatinasStockVO) getCurrentRowObject();
			return JefaturasJPTEnum.convertirJefatura(stock.getJefatura());
		}else if (getCurrentRowObject() instanceof PegatinasVO){
			PegatinasVO pegatina = (PegatinasVO) getCurrentRowObject();
			return JefaturasJPTEnum.convertirJefatura(pegatina.getJefatura());
		}else{
			return "";
		}
	}

}
