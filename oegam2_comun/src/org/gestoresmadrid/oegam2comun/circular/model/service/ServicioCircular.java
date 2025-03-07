package org.gestoresmadrid.oegam2comun.circular.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.circular.model.vo.CircularVO;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;
import org.gestoresmadrid.oegam2comun.circular.view.dto.CircularDto;

public interface ServicioCircular extends Serializable{

	List<CircularDto> convertirListaEnBeanPantallaConsulta(List<CircularVO> list);

	ResultadoCircularBean guardarCircular(CircularDto circularDto, BigDecimal idUsuario);

	ResultadoCircularBean cambiarEstados(String codSeleccionados, String valorEstadoCambio, BigDecimal idUsuario);

	CircularVO getCircularVO(Long id);

	ResultadoCircularBean revertir(String[] codSeleccionados, BigDecimal idUsuario);

	ResultadoCircularBean gestionarCircularesContrato(Long idContrato, Boolean esInterceptor);

	ResultadoCircularBean gestionarCirculares();

	ResultadoCircularBean enviarMail(String mensaje);

	ResultadoCircularBean aceptarCircular(CircularDto circularDto, Long idContrato, Long idUsuario);

}
