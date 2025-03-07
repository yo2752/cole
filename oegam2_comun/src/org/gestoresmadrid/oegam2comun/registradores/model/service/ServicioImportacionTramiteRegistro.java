package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import escrituras.beans.ResultBeanImportacion;

public interface ServicioImportacionTramiteRegistro extends Serializable {

	ResultBeanImportacion importar(File fichero, String idContrato, BigDecimal usuario, String tipoTramite);
	ResultBeanImportacion importarCancelacion();
	ResultBeanImportacion importarFinanciacion(BigDecimal usuario);
	//IMPLEMENTED JVG. 25/05/2018.
	//ResultBeanImportacion importarLeasing();  
	ResultBeanImportacion importarLeasing(BigDecimal usuario);
	//END IMPLEMENTED.
	ResultBeanImportacion importarRenting();
	ResultBeanImportacion importarFinanciadores();  
	ResultBeanImportacion importarCyNJuntaAccionistas();  
	ResultBeanImportacion importarAyNReunionJunta();  
	ResultBeanImportacion importarDelegacionFacultades();  
	ResultBeanImportacion importarCyNReunionJunta();
	ResultBeanImportacion importarCyNJuntaSocios();
	ResultBeanImportacion importarEscrituras(BigDecimal usuario);
	ResultBeanImportacion importarLeasing();
	
}
