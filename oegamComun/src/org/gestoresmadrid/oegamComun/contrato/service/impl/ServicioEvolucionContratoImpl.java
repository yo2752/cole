package org.gestoresmadrid.oegamComun.contrato.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.evolucionContrato.model.dao.EvolucionContratoDao;
import org.gestoresmadrid.core.evolucionContrato.model.vo.EvolucionContratoPK;
import org.gestoresmadrid.core.evolucionContrato.model.vo.EvolucionContratoVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioEvolucionContrato;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.evolucionContrato.view.bean.EvolucionContratoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.FechaFraccionada;

@Service
public class ServicioEvolucionContratoImpl implements ServicioEvolucionContrato{

	private static final long serialVersionUID = -647318022283843070L;

	@Autowired
	EvolucionContratoDao evolucionContratoDao;
	
	@Autowired
	Conversor conversor;
	
	@Override
	@Transactional
	public void guardarEvolucionContrato(Long idContrato, BigDecimal idUsuario,String motivo, String solicitante, Date fecha,
			TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt) {
			EvolucionContratoVO evolucionContratoVO = rellenarEvolucion(idContrato,idUsuario,motivo,solicitante,fecha,tipoActualizacion,estadoNuevo,estadoAnt);
			evolucionContratoDao.guardar(evolucionContratoVO);
	}

	@Override
	public void guardarEvolucionContratoAlias(Long idContrato, BigDecimal idUsuario, FechaFraccionada fecha,
			TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt) {
		EvolucionContratoVO evolucionContratoVO = rellenarEvolucionAlias(idContrato,idUsuario,fecha,tipoActualizacion,estadoNuevo,estadoAnt);
		evolucionContratoDao.guardar(evolucionContratoVO);
		
	}
	
	
	private EvolucionContratoVO rellenarEvolucionAlias(Long idContrato, BigDecimal idUsuario, FechaFraccionada fecha,
			TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt) {
		EvolucionContratoVO evolucionContratoVO = new EvolucionContratoVO();
		EvolucionContratoPK id = new EvolucionContratoPK();
		Date fechaCambio = new Date();
		id.setIdContrato(idContrato);
		id.setIdUsuario(idUsuario.longValue());
		id.setFechaCambio(fechaCambio);
		evolucionContratoVO.setId(id);
		evolucionContratoVO.setEstadoAnt(estadoAnt);
		evolucionContratoVO.setEstadoNuevo(estadoNuevo);
		evolucionContratoVO.setTipoActualizacion(tipoActualizacion.getValorEnum());
		return evolucionContratoVO;
	}

	private EvolucionContratoVO rellenarEvolucion(Long idContrato, BigDecimal idUsuario, String motivo, String solicitante,	Date fecha, TipoActualizacion tipoActualizacion,
			BigDecimal estadoNuevo, BigDecimal estadoAnt) {
		EvolucionContratoVO evolucionContratoVO = new EvolucionContratoVO();
		EvolucionContratoPK id = new EvolucionContratoPK();
		id.setIdContrato(idContrato);
		id.setIdUsuario(idUsuario.longValue());
		id.setFechaCambio(fecha);
		evolucionContratoVO.setId(id);
		evolucionContratoVO.setEstadoAnt(estadoAnt);
		evolucionContratoVO.setEstadoNuevo(estadoNuevo);
		evolucionContratoVO.setMotivo(motivo);
		evolucionContratoVO.setSolicitante(solicitante);
		evolucionContratoVO.setTipoActualizacion(tipoActualizacion.getValorEnum());
		return evolucionContratoVO;
	}
	
	@Override
	public List<EvolucionContratoBean> convertirListaEnBeanPantalla(List<EvolucionContratoVO> list) {
		if(list != null && !list.isEmpty()){
			List<EvolucionContratoBean> listaEvolBean = new ArrayList<EvolucionContratoBean>();
			for(EvolucionContratoVO evolucionContratoVO : list){
				EvolucionContratoBean evolucionContratoBean = new EvolucionContratoBean();
				evolucionContratoBean.setFechaCambio(evolucionContratoVO.getId().getFechaCambio());
				evolucionContratoBean.setMotivo(evolucionContratoVO.getMotivo());
				evolucionContratoBean.setSolicitante(evolucionContratoVO.getSolicitante());
				if(evolucionContratoVO.getEstadoAnt() != null){
					evolucionContratoBean.setEstadoAnt(EstadoContrato.convertirTexto(evolucionContratoVO.getEstadoAnt().toString()));
				}
				if(evolucionContratoVO.getEstadoNuevo() != null){
					evolucionContratoBean.setEstadoNuevo(EstadoContrato.convertirTexto(evolucionContratoVO.getEstadoNuevo().toString()));
				}
				evolucionContratoBean.setApellidosNombre(evolucionContratoVO.getUsuario().getApellidosNombre());
				evolucionContratoBean.setRazonSocial(evolucionContratoVO.getContrato().getRazonSocial());
				evolucionContratoBean.setTipoAct(TipoActualizacion.getNombrePorValor(evolucionContratoVO.getTipoActualizacion()));
				listaEvolBean.add(evolucionContratoBean);
			}
			return listaEvolBean;
		}
		return Collections.emptyList();
	}
}
