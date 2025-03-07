package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;

public interface ServicioImportacionMatw extends Serializable {

	ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Boolean tienePermisoLiberarEEFF, Long idUsuario, Boolean esSiga);

	ResultadoValidacionImporBean validarTramiteImportacion(TramiteTrafMatrVO tramiteMatrVO);

	ResultadoImportacionBean guardarImportacion(TramiteTrafMatrVO tramiteMatrVO, Long idUsuario, Boolean tienePermisoLiberarEEFF);
}
