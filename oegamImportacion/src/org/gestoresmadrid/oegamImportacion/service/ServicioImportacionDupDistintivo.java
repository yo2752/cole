package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;

public interface ServicioImportacionDupDistintivo extends Serializable{

	ResultadoImportacionBean obtenerDatosFichero(File fichero, String nombreFichero);

	ResultadoImportacionBean importarDuplicado(VehNoMatOegamVO duplicado, ContratoVO contratoVO, Long idUsuario, Boolean esGestoria);

}
