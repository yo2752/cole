package org.gestoresmadrid.oegam2comun.distintivo.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultImportacionDstvDupBean;

public interface ServicioImportacionDstvDup extends Serializable {

	ResultImportacionDstvDupBean importarDuplicados(Long idContrato, File fichero, String ficheroFileName, BigDecimal idUsuario);

}