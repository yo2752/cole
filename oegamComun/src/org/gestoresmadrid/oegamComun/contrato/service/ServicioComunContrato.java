package org.gestoresmadrid.oegamComun.contrato.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docbase.enumerados.OrdenDocBaseEnum;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioComunContrato extends Serializable{

	ContratoVO getContrato(Long idContrato);

	OrdenDocBaseEnum obtenerOrdenDocBase(Long idContrato);

	List<ContratoVO> getContratosPorColegiado(String numColegiado, BigDecimal estado);

	ContratoVO getContratoPorColegiado(String numColegiado);

	ResultadoBean asociarUsuarioContrato(String[] idUsuarios, Long idContrato);

	ContratoUsuarioVO buscaUsuarioContrato(String idUsuario, Long idContrato);

	ContratoUsuarioVO getContratoPorUsuario(String idUsuario);
	
	List<ContratoUsuarioVO>  getContratosPorUsuario(String idUsuario);

	String getJefaturaContrato(Long idContrato);
	
	List<DatoMaestroBean> getComboContratosHabilitados();

}
