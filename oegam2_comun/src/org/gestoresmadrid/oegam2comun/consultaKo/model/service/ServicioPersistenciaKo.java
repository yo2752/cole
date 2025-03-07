package org.gestoresmadrid.oegam2comun.consultaKo.model.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.enumerados.OperacionConsultaKo;
import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;

public interface ServicioPersistenciaKo extends Serializable {

	List<ConsultaKoVO> getListaConsultaKO(Long[] idsConsultaKO);

	void actualizarEstadoGenExcel(Long id, String estadoKO,Long idUsuario, Date fecha, OperacionConsultaKo operacion, String nombreFichero, Date fechaGenExcel);

	List<ConsultaKoVO> getListaPorEstadoDoc(String estadoDocKO);

	ResultadoConsultaKoBean solicitarGenExcelDemanda(List<Long> listaIdsConsultaKO, Long idUsuario, Date fecha);

	ConsultaKoVO getConsultaKo(Long id);

	void guardarConEvolucion(ConsultaKoVO consultaKoVo, String estadoAnt, Date fecha, OperacionConsultaKo operacion);

	List<ConsultaKoVO> getListaKoPorEstado(String estadoKo);

	ResultadoConsultaKoBean cambiarEstadoConsultaConEvolucion(Long idConsultaKO, String estadoNuevo, Long idUsuario);

}