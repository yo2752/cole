package org.gestoresmadrid.oegam2comun.consultaDev.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.consultaDev.model.vo.ConsultaDevVO;
import org.gestoresmadrid.oegam2comun.consultaDev.model.dto.ConsultaDevDto;

import es.dgt.www.nostra.esquemas.consultaDEV.respuesta.Respuesta;
import escrituras.beans.ResultBean;

public interface ServicioDev extends Serializable{
	
	public final String NOMBRE_XML = "CONSULTA_DEV_";
	public final String NOMBRE_MASIVAS_TXT = "ALTA_MASIVA_DEV_";
	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";
	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public final String descConsultaDevDto = "consultaDevDto";
	public final String idConsultaDevDto = "idConsultaDev";
	public final String cifConsultaDevDto = "cifConsultaDev";
	public static String cobrarCreditos = "cobrar.creditos.consulta.dev";

	ResultBean getConsultaDev(Long idConsultaDev);

	ResultBean consultar(ConsultaDevDto consultaDev, BigDecimal idUsuario, Boolean esAccionCons);

	ConsultaDevVO getConsultaDevVO(Long idConsultaDev, Boolean completo);

	ResultBean cambiarEstado(Long idConsultaDev, BigDecimal idUsuario, BigDecimal estadoNuevo, Boolean accionesAsociadas);

	ResultBean actualizarConsultaDevVO(ConsultaDevVO consultaDevVO);

	ResultBean guardarDatosRespuesta(ConsultaDevVO consultaDevVO, Respuesta respuesta);

	ResultBean guardar(ConsultaDevDto consultaDev, BigDecimal idUsuario);

	void guardarFicheroAltaMasiva(File fichero, BigDecimal idContrato);

}
