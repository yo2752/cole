package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsumoMaterial;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.ConsumoMaterialDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioConsumoMaterialImpl implements ServicioConsumoMaterial {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7797442753618442541L;

	@Resource
	TramiteTraficoMatrDao tramiteTraficoMatrDao;
	@Resource
	DocPermDistItvDao docPermDistItvDao;
	@Resource
	JefaturaTraficoDao jefaturaTraficoDao;
	
	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion; 

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;
	
	@Autowired
	Conversor conversor;
	@Autowired
	ServicioConsultaMateriales servicioConsultaMateriales;

	@Override
	public HashMap<String, List<ConsumoMaterialDto>> obtenerConsumoMaterial(Date fecha) {
		HashMap<String, List<ConsumoMaterialDto>> estadisticas = new HashMap<String, List<ConsumoMaterialDto>>();
		List<String> listaJefaturasImpresion = servicioDocPrmDstvFicha.getListaJefaturasImpresionDia(fecha);
		if(listaJefaturasImpresion != null && !listaJefaturasImpresion.isEmpty()){
			for(String jefatura : listaJefaturasImpresion){
				List<ConsumoMaterialValue> listaConsumoJeftTrafico = servicioTramiteTraficoMatriculacion.getListaConsumoDstvJefaturaPorDia(jefatura,fecha);
				List<ConsumoMaterialValue> listaConsumoDupDstv = servicioDistintivoVehNoMat.getListaConsumoDstvDuplicadosJefaturaPorDia(jefatura,fecha);
				if(listaConsumoJeftTrafico != null && !listaConsumoJeftTrafico.isEmpty()){
					if(listaConsumoDupDstv != null && !listaConsumoDupDstv.isEmpty()){
						for(ConsumoMaterialValue consumoDupDstv : listaConsumoDupDstv){
							
						}
					}
					
				} else {
					
				}
			}
		}
		
		List<DocPermDistItvVO> ListDocPermDistItvVO = docPermDistItvDao.getImpresionesPorFecha(fecha);
		HashMap<String, List<Long>> docDistintivos = new HashMap<String, List<Long>>();

		for (DocPermDistItvVO item : ListDocPermDistItvVO) {

			if (docDistintivos.containsKey(item.getJefatura())) {
				List<Long> documentos = docDistintivos.get(item.getJefatura());
				documentos.add(item.getIdDocPermDistItv());
			} else {
				List<Long> documentos = new ArrayList<Long>();
				documentos.add(item.getIdDocPermDistItv());
				docDistintivos.put(item.getJefatura(), documentos);
			}
		}

		if (docDistintivos.size() != 0) {
			HashMap<String, List<ConsumoMaterialValue>> consumos = tramiteTraficoMatrDao.getMaterialesImpresos(docDistintivos);
			HashMap<String, List<ConsumoMaterialValue>> consumosDuplicados = servicioDistintivoVehNoMat.getMaterialesImpresos(docDistintivos);
			for (String key : consumos.keySet()) {
				List<ConsumoMaterialDto> consumoDtoJefatura = new ArrayList<ConsumoMaterialDto>();
				List<ConsumoMaterialValue> consumoJefatura = consumos.get(key);
				for (ConsumoMaterialValue item : consumoJefatura) {
					MaterialVO materialVO = servicioConsultaMateriales.getMaterialForTipo(item.getTipoMaterial());
					if (materialVO != null) {
						item.setNombreMaterial(materialVO.getName());
					}
					consumoDtoJefatura.add(getDtoFromValue(item));
				}

				estadisticas.put(key, consumoDtoJefatura);
			}
			
			Boolean existeConsumoJef = Boolean.FALSE;
			for (String key : consumosDuplicados.keySet()) {
				if(estadisticas.get(key) != null){
					List<ConsumoMaterialDto> listaConsumoJeft = estadisticas.get(key);
					List<ConsumoMaterialValue> listaConsumoDupJeft = consumosDuplicados.get(key);
					for(ConsumoMaterialValue consumoDupMaterial : listaConsumoDupJeft){
						ConsumoMaterialDto consumoAux = null;
						existeConsumoJef = Boolean.FALSE;
						for(ConsumoMaterialDto consumoDto : listaConsumoJeft){
							if(consumoDto.getTipoMaterial().equals(consumoDupMaterial.getTipoMaterial())){
								consumoAux = consumoDto;
								break;
							}
						}
						if(consumoAux == null){
							MaterialVO materialVO = servicioConsultaMateriales.getMaterialForTipo(consumoDupMaterial.getTipoMaterial());
							if (materialVO != null) {
								consumoDupMaterial.setNombreMaterial(materialVO.getName());
							}
							consumoAux = getDtoFromValue(consumoDupMaterial);
						}
						
						
					}
					
				} else {
					
				}
			}
			
		}

		return estadisticas;
	}

	@Override
	@Transactional(readOnly = true)
	public JefaturaTraficoVO getNombreJefaturaForJefatura(String jefatura) {
		return jefaturaTraficoDao.getJefatura(jefatura);
	}

	@Override
	public ConsumoMaterialDto getDtoFromValue(ConsumoMaterialValue consumoMaterialValue) {
		ConsumoMaterialDto consumoMaterialDto = conversor.transform(consumoMaterialValue, ConsumoMaterialDto.class);

		return consumoMaterialDto;
	}

}
