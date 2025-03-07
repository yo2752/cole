package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;



public interface ServicioVehiculoAtex5 extends Serializable {

	public final String CONSULTA_VEHICULO_ATEX5_DTO = "consultaVehiculoAtex5Dto";
	public final String NUM_EXPEDIENTE = "numExpediente";
	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";
	public final String COBRAR_CREDITOS = "cobrar.properties.atex5";
	public final String NOMBRE_CONSULTA_VEHICULO_XML = "CONSULTA_VEHICULO_ATEX5_";
	public final String NOMBRE_ZIP = "CONSULTAS_VEHICULO_ATEX5";
	public final String NOMBRE_FICHERO = "nombreFichero";
	public final String FICHERO = "fichero";
	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public final String ID_ATEX5_SIN_ANTECEDENTES = "ATEX00700";
	
	ResultadoAtex5Bean getConsultaVehiculoAtex5(BigDecimal numExpediente);

	ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	ResultadoAtex5Bean guardarConsultaVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto, BigDecimal idUsuario);

	ResultadoAtex5Bean consultarVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto, BigDecimal idUsuario);

	ResultadoAtex5Bean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo, Boolean accionesAsociadas, String tipoTramite, ConceptoCreditoFacturado concepto);

	ResultadoAtex5Bean devolverCreditosWS(BigDecimal idConsultaVehiculoAtex5, BigDecimal idUsuario, BigDecimal idContrato, String tipoTramite, ConceptoCreditoFacturado concepto, int numeroSolicitudes);

	ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorIdConsulta(BigDecimal idTramite, Boolean tramiteCompleto);

	ResultadoAtex5Bean imprimirPdf(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5, String xmlResultado);

	ResultadoAtex5Bean obtenerXmlProceso(BigDecimal idTramite);

	ConsultaVehiculoAtex5Dto getConsultaVehiculoAtex5PorIdConsultaDto(BigDecimal idConsultaVehiculoAtex5, Boolean tramiteCompleto);

	void cambiarEstadoProceso(BigDecimal idConsultaVehiculoAtex5, BigDecimal idUsuario, BigDecimal estadoNuevo, String respuesta);

	ResultadoAtex5Bean asignarTasa(String[] sNumExpedientes,String codigoTasa, BigDecimal idUsuario, String tipoTramite);

	ResultadoAtex5Bean validarTasasVehiculoAtex5(String[] sNumExpedientes);

	ResultadoAtex5Bean desasignarTasa(String[] nNumExpediente, List<String> list,BigDecimal idUsuario);

	ResultadoAtex5Bean validarDesasignarTasasVehiculoAtex5(String[] nNumExpediente);

	List<ConsultaVehiculoAtex5VO> getListaConsultasFinalizadas();

	ResultadoAtex5Bean asignarTasasProceso(List<ConsultaVehiculoAtex5VO> listaVehiculoAtex5BBDD);

	ResultadoAtex5Bean descargarPdf(BigDecimal numExpediente);

}
