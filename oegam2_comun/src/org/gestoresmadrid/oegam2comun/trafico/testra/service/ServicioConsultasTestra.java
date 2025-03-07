package org.gestoresmadrid.oegam2comun.trafico.testra.service;

import java.io.File;
import java.util.List;

import org.gestoresmadrid.core.trafico.testra.model.vo.ConsultaTestraVO;
import org.gestoresmadrid.oegam2comun.trafico.testra.view.dto.ConsultaTestraDto;

import escrituras.beans.ResultBean;

public interface ServicioConsultasTestra {

	public static final String RECIPIENT = "matriculasTestra.correo.recipient";
	public static final String SUBJECT = "matriculasTestra.correo.subject";

	String consultaTresta(List<String> numerosColegiado);

	List<ConsultaTestraVO> findAllConsultasTestra();

	ResultBean guardarConsulta(ConsultaTestraDto consultaTestra);

	void updateConsultaTestra(ConsultaTestraVO consultaTestra);

	List<String> obtenerNumerosColegiados();

	boolean actualizarActivacion(Short activo, Long idConsulta);

	List<ConsultaTestraVO> getConsultasByColegiado(String numColegiado);

	List<ConsultaTestraDto> lecturaFichero(File ficheroTestra);

	ConsultaTestraVO getConsultaByID(int id);

	ResultBean getConsultaTestraDto(Long idConsultaTestra);
}
