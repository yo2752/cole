package org.gestoresmadrid.oegamConversiones.schemas.utiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@Component
@Scope("prototype")
public class XMLManejadorErrores implements ErrorHandler, ValidationEventHandler, Serializable {

	private static final long serialVersionUID = -5330147772074427715L;
	private List<String> listaErrores = new ArrayList<String>();
	private List<String> listaLineas = new ArrayList<String>();
	private int maxErrores = 9;

	@Autowired
	XmlElementos xmlElementos;

	public void iniciar() {
		listaErrores = new ArrayList<String>();
		listaLineas = new ArrayList<String>();
		xmlElementos.iniciar();
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		String mensaje = xmlElementos.devuelveErrorElementoYLinea(exception.getMessage(), exception.getLineNumber());
		if (listaErrores.size() < maxErrores) {
			if (listaErrores.size() == 0)
				listaErrores.add("Errores al validar el archivo");
			if (mensaje != null) {
				listaErrores.add(mensaje);
			}
			if (!listaLineas.contains(String.valueOf(exception.getLineNumber()))) {
				listaLineas.add(String.valueOf(exception.getLineNumber()));
			}
		} else if (listaErrores.size() == maxErrores) {
			listaErrores.add("...");
		}
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		String mensaje = xmlElementos.devuelveErrorElementoYLinea(exception.getMessage(), exception.getLineNumber());
		if (mensaje != null) {
			listaErrores.add(mensaje);
		}
		if (!listaLineas.contains(String.valueOf(exception.getLineNumber()))) {
			listaLineas.add(String.valueOf(exception.getLineNumber()));
		}
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {

	}

	public List<String> getListaErrores() {
		return listaErrores;
	}

	public String getLineasErrores() {

		if (listaLineas.size() > 0) {
			StringBuilder cadena = new StringBuilder();
			cadena.append("Errores de Formato en las líneas : [");
			for (String numero : listaLineas) {
				cadena.append(" " + numero + ",");
			}
			cadena.delete(cadena.length() - 1, cadena.length());
			cadena.append(" ].");
			return cadena.toString();
		}
		return "";
	}

	@Override
	public boolean handleEvent(ValidationEvent event) {
		String mensaje = xmlElementos.devuelveErrorElementoYLinea(event.getMessage(), event.getLocator().getLineNumber());
		if (listaErrores.size() < maxErrores) {
			if (listaErrores.size() == 0)
				listaErrores.add("Errores al validar el archivo");
			if (mensaje != null) {
				listaErrores.add(mensaje);
			}
			if (!listaLineas.contains(String.valueOf(event.getLocator().getLineNumber()))) {
				listaLineas.add(String.valueOf(event.getLocator().getLineNumber()));
			}
		} else if (listaErrores.size() == maxErrores) {
			listaErrores.add("...");
		}
		return true;
	}
}
