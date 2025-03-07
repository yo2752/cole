package org.gestoresmadrid.oegam2comun.evolucionConsultaKo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.enumerados.OperacionConsultaKo;
import org.gestoresmadrid.core.evolucionConsultaKo.model.vo.EvolucionConsultaKoVO;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.evolucionConsultaKo.view.bean.EvolucionConsultaKoBean;

public interface ServicioEvolucionConsultaKo extends Serializable{

	List<EvolucionConsultaKoBean> convertirListaEnBeanPantallaConsulta(List<EvolucionConsultaKoVO> lista);

	ResultadoConsultaKoBean guardarEvolucion(Long id, String matricula, BigDecimal idUsuario, String tipoTramite, String tipo,
			OperacionConsultaKo operacion, Date fecha, String estadoAnt, String estadoNuevo);

	

}
