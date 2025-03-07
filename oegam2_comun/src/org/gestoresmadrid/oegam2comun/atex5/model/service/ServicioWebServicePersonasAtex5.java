package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoWSAtex5Bean;

public interface ServicioWebServicePersonasAtex5 extends Serializable{

	public static final String VERSION_CONSULTA = "5";
	public static final String URL_WS = "webservice.atex5.url";
	public static final String TIMEOUT = "webservice.atex5.timeOut";
	public static final String ALIAS_CONTRATO_INFORMATICA = "contrato.informatica.alias";
	public static final String ID_CONTRATO_INFORMATICA = "contrato.informatica";
	
	ResultadoWSAtex5Bean generarConsultaPersonaAtex5(ColaBean colaBean);

	void cambiarEstadoConsulta(BigDecimal numExpediente, BigDecimal idUsuario, EstadoAtex5 estado);

	void devolverCreditos(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario);

}