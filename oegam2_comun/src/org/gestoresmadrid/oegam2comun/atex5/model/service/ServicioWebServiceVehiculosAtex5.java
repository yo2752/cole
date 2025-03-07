package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoWSAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Vehiculo;

public interface ServicioWebServiceVehiculosAtex5 extends Serializable {

	public static final String VERSION_CONSULTA = "5";
	public static final String URL_WS = "webservice.atex5.url";
	public static final String TIMEOUT = "webservice.atex5.timeOut";
	public static final String ALIAS_CONTRATO_INFORMATICA = "contrato.informatica.alias";
	public static final String ID_CONTRATO_INFORMATICA = "contrato.informatica";

	ResultadoWSAtex5Bean generarConsultaVehiculoAtex5(ColaBean colaBean);

	ResultadoWSAtex5Bean generarConsultaVehiculoAvpoAtex5(ColaBean colaBean, String matricula, String bastidor);

	void cambiarEstadoConsulta(BigDecimal idConsultaVehiculoAtex5, BigDecimal idUsuario, EstadoAtex5 estado, String respuesta);

	void devolverCreditos(BigDecimal idConsultaVehiculoAtex5, BigDecimal idContrato, BigDecimal idUsuario, String tipoTramite, ConceptoCreditoFacturado concepto, int numeroSolicitudes);

	Vehiculo prepararVehiculoByte(byte[] xml);

	Vehiculo prepararVehiculoFile(File fichero);

	void guardarConsultasTGate(BigDecimal numExpediente, BigDecimal idUsuario, String numColegiado, String matricula, String bastidor, String origen, String tipoServicio, String respuesta);

	void asignarTasa();

}