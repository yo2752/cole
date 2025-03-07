package org.gestoresmadrid.oegam2comun.envioData.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.envioData.view.bean.ResultadoEnvioDataBean;
import org.gestoresmadrid.oegam2comun.envioData.view.dto.EnvioDataDto;

public interface ServicioEnvioData extends Serializable {

	public static String ACTUALIZAR_FECHA_ULTIMA_LABORAL = "0";
	public static String ACTUALIZAR_FECHA_HOY = "1";

	void actualizarEjecucion(String proceso, BigDecimal idTramite, String respuesta, String resultadoEjecucion, String numCola);

	Date getUltimaFechaEnvioData(String proceso, String resultadoEjecucion, String hiloCola);

	Boolean hayEnvioDataProcesoCola(String proceso, String hiloCola);

	ResultadoEnvioDataBean listarEnvioData(String tipoEjecucion);

	ResultadoEnvioDataBean actualizarFechaEnvioData(String proceso, String tipoActualizacion, String cola);

	List<EnvioDataDto> recuperarUltimasEjecucionesProceso(String proceso);

	List<EnvioDataDto> getListaEnviosDataProceso(String proceso);

	List<EnvioDataDto> getListaEnviosDataProcesoPorCola(String proceso);

	List<EnvioDataDto> getListaUltimosEnvioDataCorrectos(ConsultaGestionColaBean consultaGestionCola, String orden, String dir);

	List<EnvioDataVO> getListaUltimoEnvioPorEjecucion(String proceso, String cola, String correcta,String orden, String dir);

	EnvioDataVO actualizarEnvioData(EnvioDataVO envioDataVO);

	List<EnvioDataVO> getlistaEnvioData(String tipoEjecucion);
}
