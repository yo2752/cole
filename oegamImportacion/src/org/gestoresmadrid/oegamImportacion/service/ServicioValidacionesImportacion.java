package org.gestoresmadrid.oegamImportacion.service;

import java.io.Serializable;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;

public interface ServicioValidacionesImportacion extends Serializable {

	ResultadoValidacionImporBean validarVehiculo(VehiculoVO vehiculo, String tipoTramite);

	ResultadoValidacionImporBean validarTramiteBaja(TramiteTrafBajaVO tramiteBajaVO);

	ResultadoValidacionImporBean validarTramiteCtit(TramiteTrafTranVO tramiteCtitVO);

	ResultadoValidacionImporBean validarTramiteMatw(TramiteTrafMatrVO tramiteCtitVO);

	ResultadoValidacionImporBean validarTramiteDuplicado(TramiteTrafDuplicadoVO tramiteDuplicadoVO);

	ResultadoValidacionImporBean validarAutoliquidacion(AutoliquidacionBean autoliquidacion);

	ResultadoValidacionImporBean validarTramiteSolicitud(TramiteTraficoInteveVO tramiteSolicitudVO);

	ResultadoValidacionImporBean validarInterviniente(IntervinienteTraficoVO intervinienteTraficoVO, String tipoTramite);

	ResultadoValidacionImporBean validarPosibleDuplicado(String matricula, String nif, String tipoTramite, String numColegiado);

	ResultadoValidacionImporBean validarPosibleDuplicadoCtit(String bastidor, String matricula, String nif, String tipoTramite, String numColegiado, String tipoTransferencia);

	ResultadoValidacionImporBean validarPosibleDuplicadoExcel(String matricula, String nif, String tipoTramite,String numColegiado);
}
