package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;

public interface ServicioImportacionSolicitud extends Serializable {

	ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga);

	ResultadoValidacionImporBean validarTramiteSolicitud(TramiteTraficoInteveVO tramiteInteveVO);

	ResultadoImportacionBean guardarImportacion(TramiteTraficoInteveVO tramiteInteveVO, Long idUsuario);

}