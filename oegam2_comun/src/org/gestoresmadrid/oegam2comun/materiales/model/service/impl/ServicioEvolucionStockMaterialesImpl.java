package org.gestoresmadrid.oegam2comun.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionStockMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionStockMaterialesVO;
import org.gestoresmadrid.oegam2comun.materiales.model.service.ServicioEvolucionStockMateriales;
import org.gestoresmadrid.oegam2comun.materiales.view.bean.EvolucionStockMaterialBean;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServicioEvolucionStockMaterialesImpl implements ServicioEvolucionStockMateriales{

	private static final long serialVersionUID = -1577002620590816320L;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	ServicioUsuario servicioUsuario;
	
	@Autowired
	EvolucionStockMaterialDao evolucionStockMaterialDao;
	
	@Override
	public List<EvolucionStockMaterialBean> convertirEvolucionEnBeanPantalla(List<EvolucionStockMaterialesVO> lista) {
		List<EvolucionStockMaterialBean> listaBean = new ArrayList<EvolucionStockMaterialBean>();
		if(lista != null && !lista.isEmpty()) {
			for(EvolucionStockMaterialesVO evo :lista){
				EvolucionStockMaterialBean bean = new EvolucionStockMaterialBean();
				bean.setId(evo.getId());
				bean.setIdStock(evo.getIdStock());
				bean.setUsuarioId(evo.getUsuarioVO().getApellidosNombre());
				bean.setUnidadesAnteriores(evo.getUnidadesAnteriores());
				bean.setUnidadesActuales(evo.getUnidadesActuales());
				bean.setFecha(evo.getFecha_recarga());
				switch (evo.getOperacion()) {
					case "CR":
						bean.setOperacion("CREACION");
						break;
					case "RC":
						bean.setOperacion("RECARGA");
						break;
				}
				listaBean.add(bean);
			}
		}
		return listaBean;
	}
	
}
