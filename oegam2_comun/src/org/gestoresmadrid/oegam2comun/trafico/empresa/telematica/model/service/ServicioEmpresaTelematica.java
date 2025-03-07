package org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.empresa.telematica.model.vo.EmpresaTelematicaVO;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.ResultadoEmpresaTelematicaBean;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.dto.EmpresaTelematicaDto;

import escrituras.beans.ResultBean;

public interface ServicioEmpresaTelematica {
	
	ResultBean comprobarEmpresaTelematica(String nombreEmpresa, String cifEmpresa, String codPostal, String numColegiado, Long idContrato, String idMuncipio, String idProvincia);
	ResultadoEmpresaTelematicaBean getEmpresaTelematicaPantalla(String id);
	EmpresaTelematicaVO getEmpresaTelematicaVO(String id);
	ResultadoEmpresaTelematicaBean guardarEmpresaTelematica(EmpresaTelematicaDto empresaTelematicaDto, BigDecimal idUsuario, String numColegiado);
	ResultadoEmpresaTelematicaBean cambiarEstados(String codSeleccionados, String estadoNuevo, BigDecimal usuario);
	List<EmpresaTelematicaDto> getListaEmpresasSTContrato(Long idContrato);
	Boolean esEmpresaTelematica(String nombreEmpresa, String cifEmpresa, String codPostal, String numColegiado, Long numContrato, String idMunicipio, String idProvincia);
}
