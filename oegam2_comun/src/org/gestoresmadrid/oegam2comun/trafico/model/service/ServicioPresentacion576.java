package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;

import escrituras.beans.ResultBean;

public interface ServicioPresentacion576 extends Serializable {

	ResultBean tramitar576(TramiteTrafMatrDto tramiteTrafMatrDto, String alias) throws Exception;

	byte[] descargarPdf(String csv);
}
