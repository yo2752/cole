package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;

public class DecoratorImprimirTramitesTrafico  extends TableDecorator{

	public String getEstado(){
		TramiteTrafDto fila = (TramiteTrafDto) getCurrentRowObject();
		String result = "<input type=\"hidden\" id=\"estado"+fila.getNumExpediente()+"\" value=\""+fila.getEstado()+"\"/>" +
										EstadoTramiteTrafico.convertirTexto(fila.getEstado());
		return result;
	}
	
}
