package org.gestoresmadrid.oegamComun.evolucionUsuario.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.evolucionUsuario.model.dao.EvolucionUsuarioDao;
import org.gestoresmadrid.core.evolucionUsuario.model.vo.EvolucionUsuarioPK;
import org.gestoresmadrid.core.evolucionUsuario.model.vo.EvolucionUsuarioVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.evolucionUsuario.service.ServicioEvolucionUsuario;
import org.gestoresmadrid.oegamComun.evolucionUsuario.view.bean.EvolucionUsuarioBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEvolucionUsuarioImpl implements ServicioEvolucionUsuario{

	private static final long serialVersionUID = -647318022283843070L;

	@Autowired
	EvolucionUsuarioDao evolucionUsuarioDao;
	
	@Autowired
	Conversor conversor;
	
	@Override
	@Transactional
	public void guardarEvolucionUsuario(BigDecimal idUsuario,Timestamp fecha,Long idContrato,TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt,Long contratoAnt,String nif) {
			EvolucionUsuarioVO evolucionUsuarioVO = rellenarEvolucion(idContrato,idUsuario,fecha,tipoActualizacion,estadoNuevo,estadoAnt,contratoAnt,nif);
			evolucionUsuarioDao.guardar(evolucionUsuarioVO);
	}

	private EvolucionUsuarioVO rellenarEvolucion(Long idContrato, BigDecimal idUsuario,Timestamp fecha, TipoActualizacion tipoActualizacion,
			BigDecimal estadoNuevo, BigDecimal estadoAnt,Long contratoAnt,String nif) {
		EvolucionUsuarioVO evolucionUsuarioVO = new EvolucionUsuarioVO();
		EvolucionUsuarioPK id = new EvolucionUsuarioPK();
		id.setIdUsuario(idUsuario.longValue());
		id.setFechaCambio(fecha);
		if (idContrato!=null) {
			evolucionUsuarioVO.setIdContrato(idContrato);
		}
				
		if (contratoAnt!=null) {
			evolucionUsuarioVO.setContratoAnt(contratoAnt);
		}
		evolucionUsuarioVO.setId(id);
		evolucionUsuarioVO.setEstadoAnt(estadoAnt);
		evolucionUsuarioVO.setEstadoNuevo(estadoNuevo);
		evolucionUsuarioVO.setTipoActuacion(tipoActualizacion.getValorEnum());
		evolucionUsuarioVO.setContratoAnt(contratoAnt);
		evolucionUsuarioVO.setNif(nif);
		return evolucionUsuarioVO;
	}
	
	@Override
	public List<EvolucionUsuarioBean> convertirListaEnBeanPantalla(List<EvolucionUsuarioVO> list) {
		if(list != null && !list.isEmpty()){
			List<EvolucionUsuarioBean> listaEvolBean = new ArrayList<EvolucionUsuarioBean>();
			for(EvolucionUsuarioVO evolucionUsuarioVO : list){
				EvolucionUsuarioBean evolucionUsuarioBean = new EvolucionUsuarioBean();
				evolucionUsuarioBean.setFechaCambio(evolucionUsuarioVO.getId().getFechaCambio());
			
				if(evolucionUsuarioVO.getEstadoAnt() != null){
					evolucionUsuarioBean.setEstadoAnt(EstadoUsuario.convertirTexto(evolucionUsuarioVO.getEstadoAnt().toString()));
				}
				if(evolucionUsuarioVO.getEstadoNuevo() != null){
					evolucionUsuarioBean.setEstadoNuevo(EstadoUsuario.convertirTexto(evolucionUsuarioVO.getEstadoNuevo().toString()));
				}
				evolucionUsuarioBean.setApellidosNombre(evolucionUsuarioVO.getUsuario().getApellidosNombre());
				evolucionUsuarioBean.setTipoAct(TipoActualizacion.getNombrePorValor(evolucionUsuarioVO.getTipoActuacion()));
				if (evolucionUsuarioVO.getContrato()!=null) {
				evolucionUsuarioBean.setContratoAnt(evolucionUsuarioVO.getContrato().getColegiado().getNumColegiado() + "-" +
						evolucionUsuarioVO.getContrato().getVia());
				}
				
				if (evolucionUsuarioVO.getNif()!=null)
				{
					evolucionUsuarioBean.setNif(evolucionUsuarioVO.getNif());
				}
				
				listaEvolBean.add(evolucionUsuarioBean);
			}
			return listaEvolBean;
		}
		return Collections.emptyList();
	}
}
