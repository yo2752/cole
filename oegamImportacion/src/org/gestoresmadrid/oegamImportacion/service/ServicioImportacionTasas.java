package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.oegamImportacion.bean.ImportarTramiteBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.tasa.bean.TasaImportacionBean;

public interface ServicioImportacionTasas extends Serializable{

	ResultadoImportacionBean obtenerDatosFichero(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, ContratoVO contratoVO, Long idUsuario, Boolean esAdmin);

	ResultadoImportacionBean importarTasasPegatinaTasa(TasaImportacionBean tasaBean, ContratoVO contratoVO, Long idUsuario, Boolean esGestoria);

	ResultadoImportacionBean importarTasasElectronica(TasaImportacionBean tasaBean, ContratoVO contratoVO, Long idUsuario, Boolean esGestoria);
}
