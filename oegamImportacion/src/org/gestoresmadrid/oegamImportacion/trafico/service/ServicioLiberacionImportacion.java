package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;

public interface ServicioLiberacionImportacion extends Serializable {

	String guardarDatosLiberacion(TramiteTrafMatrVO tramiteMatrVO);
}
