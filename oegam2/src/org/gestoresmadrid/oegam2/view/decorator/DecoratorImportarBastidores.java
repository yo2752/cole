package org.gestoresmadrid.oegam2.view.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoImportacionBastidores;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.dto.ImportarBastidorDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DecoratorImportarBastidores extends TableDecorator {

	@Autowired
	UtilesFecha utilesFecha;

	public DecoratorImportarBastidores() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getTipoImportado() {
		ImportarBastidorDto fila = (ImportarBastidorDto) getCurrentRowObject();
		return TipoBastidor.convertirTexto(fila.getTipoImportado());
	}

	public String getTipoRegistro() {
		ImportarBastidorDto fila = (ImportarBastidorDto) getCurrentRowObject();
		return TipoImportacionBastidores.convertirTexto(fila.getTipoRegistro());
	}

	public String getFechaEnvio() {
		ImportarBastidorDto fila = (ImportarBastidorDto) getCurrentRowObject();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date fechaActual = null;

		fechaActual = fila.getFechaEnvio() != null && !fila.getFechaEnvio().equals("")
				? utilesFecha.formatoFecha("yyyyMMdd", fila.getFechaEnvio())
				: new Date();

		return sdf.format(fechaActual);
	}

}