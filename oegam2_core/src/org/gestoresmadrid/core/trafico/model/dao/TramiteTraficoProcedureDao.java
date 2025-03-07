package org.gestoresmadrid.core.trafico.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.model.procedure.bean.ValidacionTramite;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;

public interface TramiteTraficoProcedureDao extends Serializable {

	ValidacionTramite validarTramiteMatriculacion(TramiteTrafMatrVO tramite);

	ValidacionTramite validarTramiteBaja(TramiteTrafBajaVO tramite);

	ValidacionTramite validarTramiteTelematicamenteBaja(TramiteTrafBajaVO tramiteVO);

	ValidacionTramite validarTramiteDuplicado(TramiteTrafDuplicadoVO tramite);

	ValidacionTramite validarTramiteCTIT(TramiteTrafTranVO trafTranVO);

}