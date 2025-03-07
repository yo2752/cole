package org.gestoresmadrid.oegam2comun.importacion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioImportar extends Serializable {

	ResultBean importarBaja(TramiteTrafBajaDto tramiteTrafBajaDto, BigDecimal idUsuario, Boolean tienePermisoBtv, Boolean esSiga);

	ResultBean importarDuplicado(TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto, BigDecimal idUsuario);

	ResultBean importarMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario, Boolean tienePermisoLiberarEEFF, Boolean esAdmin) throws OegamExcepcion;

}
