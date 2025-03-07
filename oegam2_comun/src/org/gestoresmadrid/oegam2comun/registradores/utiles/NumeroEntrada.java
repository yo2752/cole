package org.gestoresmadrid.oegam2comun.registradores.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.gestoresmadrid.oegam2comun.registradores.corpme.CORPMEAcuseEntrada;

public class NumeroEntrada {

	// Propiedad que almacena la jerarquía de objetos java construída por JAXB a partir
	// del file recibido en el constructor
	private CORPMEAcuseEntrada entrada;

	// El constructor recibe el fichero de la respuesta recibida y construye mediante el
	// api Jaxb una jerarquía de objetos java que permite el acceso a la información contenida
	// en el xml que se ha recibido como respuesta
	public NumeroEntrada(File fichero) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		entrada = (CORPMEAcuseEntrada) unmarshaller.unmarshal(new FileInputStream(fichero));
	}

	public CORPMEAcuseEntrada getEntrada() {
		return entrada;
	}

	public void setEntrada(CORPMEAcuseEntrada entrada) {
		this.entrada = entrada;
	}
}
