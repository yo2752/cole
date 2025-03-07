package org.gestoresmadrid.oegam2comun.distintivo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ConsultaVehNoMatrOegamBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;

public interface ServicioConsultaDistintivoVehNoMat extends Serializable {

	ResultadoDistintivoDgtBean solicitarDstv(String codSeleccionados, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean solictarImpresionDstv(String codSeleccionados, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean generarDemandaDistintivos(String codSeleccionados, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean revertir(String codSeleccionados, BigDecimal idUsuario);

	ResultadoDistintivoDgtBean cambiarEstadoDstv(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario);

	List<ConsultaVehNoMatrOegamBean> convertirListaEnBeanPantallaConsulta(List<VehNoMatOegamVO> listaBBDD);

	ResultadoDistintivoDgtBean autorizarDstv(String codSeleccionados, BigDecimal idUsuario);

}