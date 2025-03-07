package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;

public interface ServicioImportacionDuplicado extends Serializable {

	ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga);

	ResultadoValidacionImporBean validarTramiteImportacion(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO);

	ResultadoImportacionBean guardarImportacion(TramiteTrafDuplicadoVO tramiteDuplicadoVO, Long idUsuario);

	ResultadoImportacionBean obtenerDatosFichero(File fichero, ContratoVO contratoBBDD, Boolean tienePermisoAdmin,Long idUsuario, Boolean esSiga);

	ResultadoImportacionBean convertirFicheroXmlImportacion(File fichero, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga);

	ResultadoValidacionImporBean validarTramiteImportacionExcel(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO);
}
