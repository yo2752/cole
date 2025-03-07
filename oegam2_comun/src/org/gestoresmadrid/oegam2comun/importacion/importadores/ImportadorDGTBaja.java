package org.gestoresmadrid.oegam2comun.importacion.importadores;

import org.gestoresmadrid.oegam2comun.importacion.trama.TramaBaja;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import trafico.utiles.constantes.ConstantesDGT;
import trafico.utiles.importacion.interfaces.IImportadorDGT;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class ImportadorDGTBaja implements IImportadorDGT {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportadorDGTBaja.class);

	@Autowired
	private TramaBaja tramaBaja;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public ImportadorDGTBaja() {}

	public ResultBean validaFormato(String lineaTramite, int numLinea) {
		ResultBean resultBean = new ResultBean();

		log.trace("Validando formato de linea de baja");

		if (numLinea == 1) {
			log.info("La primera línea del fichero no es de tipo PDF");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe ser un registro de tipo PDF");
		} else if (!validaTamLinea(lineaTramite)) {
			// Comprobar que la longitud de la linea es correcta
			log.info("La longitud de la línea de registro de Baja no es correcta");
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Linea " + numLinea + " La longitud no es correcta para un trámite de Baja");
		} else {
			log.debug("La longitud de la línea de Baja es " + lineaTramite.length());
		}

		return resultBean;
	}

	private boolean validaTamLinea(String lineaTramite) {
		
			if (lineaTramite.length() == ConstantesDGT.TAM_LINEA_BAJA_MAYOR_BTV) {
				return true;
			} else if (lineaTramite.length() == ConstantesDGT.TAM_LINEA_BAJA_MENOR_BTV) {
				return true;
			}else if (lineaTramite.length() == ConstantesDGT.TAM_LINEA_BAJA_MAYOR) {
				return true;
			} else if (lineaTramite.length() == ConstantesDGT.TAM_LINEA_BAJA_MENOR) {
				return true;
			} else {
				return false;
			}
	
	}

	public ResultBean importaValores(String lineaTramite, String colegiadoCabecera) {
		ResultBean resultado = new ResultBean();
		resultado = tramaBaja.obtenerDto(lineaTramite, colegiadoCabecera);
		return resultado;
	}

	public String getKeyBean() {
		return ConstantesDGT.DTO_BAJA;
	}
}