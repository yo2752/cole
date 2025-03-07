package org.gestoresmadrid.oegamImportacion.utiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import utilidades.mensajes.Claves;

@Component
public class UtilidadesFicheroImportacion implements Serializable {

	private static final long serialVersionUID = 8669071907350112805L;

	public UtilidadesFicheroImportacion() {}

	public List<String> obtenerLineasFicheroTexto(File ficheroTexto) throws Throwable {
		List<String> list = new ArrayList<String>();
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroTexto), Claves.ENCODING_ISO88591));
			String line = null;
			while ((line = input.readLine()) != null) {
				list.add(line);
			}
			return list;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
}
