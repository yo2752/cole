package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ConsultaJustificantePantallaBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;


public interface ServicioConsultaJustificanteProfesional extends Serializable{

	ResultadoConsultaJustProfBean pendienteAutorizacionColegioEnBloque(Long[] idsJustificanteInternos, BigDecimal idUsuario);

	ResultadoConsultaJustProfBean forzarJPBloque(Long[] idsJustificanteInternos, BigDecimal idUsuario);

	ResultadoConsultaJustProfBean imprimirJPBloque(Long[] idsJustificanteInternos);

	ResultadoConsultaJustProfBean anularJPBloque(Long[] idsJustificanteInternos, BigDecimal idUsuario);
	
	ResultadoConsultaJustProfBean descargarJustificante(String numExpediente);
	
	ResultadoConsultaJustProfBean descargarJPBloque(String nombreJustificantes, BigDecimal idUsuario);

	ResultadoConsultaJustProfBean generarJPBloque(String arrayIdsJustificantes, BigDecimal idUsuario, Boolean tienePermisoAdmin);

	ResultadoConsultaJustProfBean validarGenerarJpBloque(String sIdJustificantes, BigDecimal idContrato, Boolean esAdmin);

	List<ConsultaJustificantePantallaBean> convertirListaEnBeanPantalla(List<JustificanteProfVO> lista);

}
