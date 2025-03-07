package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;

public interface ServicioImportacionCtit extends Serializable {

	ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga);

	ResultadoValidacionImporBean validarTramiteImportacion(TramiteTrafTranVO tramiteCtitVO);

	ResultadoImportacionBean guardarImportacion(TramiteTrafTranVO tramiteTranVO, Long idUsuario);
}
