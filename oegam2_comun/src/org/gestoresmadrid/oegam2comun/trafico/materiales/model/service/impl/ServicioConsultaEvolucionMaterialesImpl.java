package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionMaterialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaEvolucionMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.EvolucionMaterialesResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.EvolucionMaterialDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioConsultaEvolucionMaterialesImpl implements ServicioConsultaEvolucionMateriales {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6814145404528057701L;

	@Resource private EvolucionMaterialDao   evolucionMaterialDao;

	@Autowired private Conversor conversor;
	
	@Override
	public List<EvolucionMaterialesResultadosBean> convertirListaEnBeanPantalla(List<EvolucionMaterialVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<EvolucionMaterialesResultadosBean> listaBean = new ArrayList<EvolucionMaterialesResultadosBean>();
			
			for (EvolucionMaterialVO evolucionMaterialVO : lista) {
				EvolucionMaterialesResultadosBean evolucionMaterialesResultadosBean = conversor.transform(evolucionMaterialVO, EvolucionMaterialesResultadosBean.class);
				String estado = EstadoMaterial.convertirTexto(evolucionMaterialVO.getEstado());
				evolucionMaterialesResultadosBean.setEstado(estado);
				listaBean.add(evolucionMaterialesResultadosBean);
			}
			return listaBean;
		}
		return Collections.emptyList();	
	}

	@Override
	public EvolucionMaterialVO getEvolucionMaterialFromMaterial(MaterialVO materialVO, EstadoMaterial estado) {
		EvolucionMaterialVO evolucioMaterialVO = conversor.transform(materialVO, EvolucionMaterialVO.class);
		evolucioMaterialVO.setEstado(estado.getValorEnum());
		return evolucioMaterialVO;
	}

	@Override
	@Transactional(readOnly=true)
	public EvolucionMaterialDto obtenerMaterialForPrimaryKey(Long evolucionMaterialId) {
		// TODO Auto-generated method stub
		EvolucionMaterialVO evolucioMaterialVO = evolucionMaterialDao.findEvolucionMaterialByPrimaryKey(evolucionMaterialId);
		EvolucionMaterialDto evolucioMaterialDto = conversor.transform(evolucioMaterialVO, EvolucionMaterialDto.class);
		
		return evolucioMaterialDto;
	}

}
