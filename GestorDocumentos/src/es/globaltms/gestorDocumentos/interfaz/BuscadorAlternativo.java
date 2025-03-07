package es.globaltms.gestorDocumentos.interfaz;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public interface BuscadorAlternativo {

	public List<File> buscar(String tipo, String subTipo, BigDecimal numExpediente);
	
	public File buscarNombre(String tipo, String subTipo, String nombreFichero);
}
