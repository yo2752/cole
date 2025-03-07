package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.TasaCargadaVO;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ConsultaTasaNuevaBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoConsultaTasasBean;

import utilidades.web.OegamExcepcion;

public interface ServicioConsultaTasasNueva extends Serializable {

	ResultadoConsultaTasasBean exportar(String idCodigoTasa, BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin) throws OegamExcepcion;

	ResultadoConsultaTasasBean generarCertificado(String idCodigoTasa, BigDecimal idUsuario);

	ResultadoConsultaTasasBean comprobarDatosObligatoriosGeneracionTasasEtiqueta(String idCodigoTasa, String idCodigoTasaPegatina);

	List<ConsultaTasaNuevaBean> convertirListaEnBeanPantalla(List<TasaCargadaVO> lista);

	ResultadoConsultaTasasBean desasignarTasaB(String idCodigoTasa, BigDecimal numExpediente, BigDecimal idUsuarioDeSesion, BigDecimal idContrato, Boolean tienePermisoAdmin);

	ResultadoConsultaTasasBean eliminarTasaB(String idCodigoTasa, BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin);

}