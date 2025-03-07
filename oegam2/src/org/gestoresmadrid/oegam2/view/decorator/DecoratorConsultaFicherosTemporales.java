package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.ficheros.temporales.model.vo.FicherosTemporalesVO;
import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorConsultaFicherosTemporales extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorConsultaFicherosTemporales() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getTipoDocumento() {
		FicherosTemporalesVO fila = (FicherosTemporalesVO) getCurrentRowObject();
		return TipoFicheros.convertirTexto(fila.getTipoDocumento());
	}

	public String getFecha() {
		FicherosTemporalesVO fila = (FicherosTemporalesVO) getCurrentRowObject();
		return utilesFecha.formatoFecha("dd/MM/yyyy", fila.getFecha());
	}

}