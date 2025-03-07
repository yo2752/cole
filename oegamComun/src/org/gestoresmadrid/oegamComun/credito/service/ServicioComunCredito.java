package org.gestoresmadrid.oegamComun.credito.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioComunCredito extends Serializable {

	public static String INCREMENTAL = "I";
	public static String DECREMENTAL = "D";

	ResultadoBean descontarCreditos(String tipoTramite, Long idContrato, Long idUsuario, List<BigDecimal> numsExpediente);

	ResultadoBean devolverCredito(String tipoTramite, Long idContrato, Long idUsuario, List<BigDecimal> numsExpediente);

	ResultadoBean creditosDisponiblesComprobandoPendientes(Long numCreditos, Long idContrato, String proceso, String tipoTramite);

	ResultadoBean creditosDisponibles(Long numCreditos, Long idContrato, String tipoTramite);

	ResultadoBean descontarCreditosInteve(String tipoTramite, Long idContrato, Long idUsuario, BigDecimal numExpediente, Long numCreditos);

	String getTipoCreditosACobrar(String tipoTramite, String tipoTransferencia);

	String getTipoTramitePorTipoCredito(String tipoCredito);

	ResultadoBean validarTotalCreditos(Long idContrato, String tipoTramiteCredito);

}
