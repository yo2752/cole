package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.MaterialesResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.MaterialInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.ComboMaterialDto;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioConsultaMaterialesImpl implements ServicioConsultaMateriales {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7989411018836714110L;
	
	private static final String COD_PERMISO = "PC";
	
	@Resource  MaterialDao  materialDao;

	@Autowired Conversor conversor;
	
	@Override
	public List<MaterialesResultadosBean> convertirListaEnBeanPantalla(List<MaterialVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<MaterialesResultadosBean> listaBean = new ArrayList<MaterialesResultadosBean>();
			
			for (MaterialVO materialVO : lista) {
				MaterialesResultadosBean materialesResultadosBean = conversor.transform(materialVO, MaterialesResultadosBean.class);
				listaBean.add(materialesResultadosBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}


	@Override
	@Transactional(readOnly=true)
	public List<ComboMaterialDto> getMateriales() {
		List<MaterialVO> listMaterialVO = materialDao.getListaMateriales();
		
		List<ComboMaterialDto> listaMateriales = new ArrayList<ComboMaterialDto>();
		for(MaterialVO material: listMaterialVO) {
			ComboMaterialDto item = new ComboMaterialDto(material.getMaterialId(), material.getName());
			listaMateriales.add(item);
		}
		
		return listaMateriales;
	}

	@Override
	@Transactional(readOnly=true)
	public MaterialVO getMaterialForPrimaryKey(Long materialId) {
		MaterialVO materialVO = materialDao.findByPrimaryKey(materialId);
		return materialVO;
	}

	@Override
	@Transactional(readOnly=true)
	public MaterialVO getMaterialForTipo(String tipo) {
		MaterialVO materialVO = materialDao.findMaterialByTipo(tipo);
		return materialVO;
	}
	
	@Override
	@Transactional(readOnly=true)
	public LinkedHashMap<Long, String> getTipoDistintivo() {
		LinkedHashMap<Long, String> tipoDistintivo = new LinkedHashMap<Long, String>();
		
		List<MaterialVO> listMaterialVO = materialDao.getListaMateriales();
		Iterator<MaterialVO> it = listMaterialVO.iterator();
		while (it.hasNext()) {
			MaterialVO materialVO = (MaterialVO) it.next();
			if (COD_PERMISO.equals(materialVO.getTipo())) {
				continue;
			}
			tipoDistintivo.put(materialVO.getMaterialId(), materialVO.getName());
		}
		return tipoDistintivo;
	}

	@Override
	@Transactional(readOnly=true)
	public LinkedHashMap<Long, String> getPermisoConducir() {
		LinkedHashMap<Long, String> tipoDistintivo = new LinkedHashMap<Long, String>();
		
		MaterialVO materialVO = materialDao.findMaterialByTipo(COD_PERMISO);
		tipoDistintivo.put(materialVO.getMaterialId(), materialVO.getName());
		return tipoDistintivo;
	}

	@Override
	@Transactional(readOnly=true)
	public MaterialDto obtenerMaterialForPrimaryKey(Long materialId) {
		MaterialVO materialVO = materialDao.findByPrimaryKey(materialId);
		return getDtoFromVO(materialVO);
	}

	@Override
	@Transactional(readOnly=true)
	public List<MaterialVO> obtenerTodos() {
		return materialDao.getListaMateriales();
	}
	
	@Override
	@Transactional(readOnly=true)
	public String[] obtenerTodosTiposMaterial() {
		List<MaterialVO> materiales = materialDao.getListaMateriales();
		String[] tipos = new String[materiales.size()];
		int index = 0;
		for(MaterialVO item: materiales) {
			if ("PC".equals(item.getTipo())) {
				tipos[index++] = item.getTipo();
			} else {
				TipoDistintivo tipoDistintivo = TipoDistintivo.convertir(item.getTipo());
				tipos[index++] = tipoDistintivo.getNombreEnum();
			}
		}
		return tipos;
	}
	
	@Override
	public MaterialDto getDtoFromInfoRest(MaterialInfoRest materialInfoRest) {
		MaterialDto materialDto = conversor.transform(materialInfoRest, MaterialDto.class);
		materialDto.setTipo(ponerTipo(materialDto.getColor()));
		materialDto.setFecha(new Date());
		materialDto.setMaterialId(materialInfoRest.getId());
		return materialDto;
	}


	@Override
	public MaterialVO getVOFromDto(MaterialDto materialDto) {
		MaterialVO materialVO = conversor.transform(materialDto, MaterialVO.class);
		materialVO.setFechaAlta(materialDto.getFecha());
		materialVO.setName(materialDto.getNombre());
		return materialVO;
	}


	@Override
	public MaterialDto getDtoFromVO(MaterialVO materialVO) {
		MaterialDto materialDto = conversor.transform(materialVO, MaterialDto.class);
		materialDto.setFecha(materialVO.getFechaAlta());
		materialDto.setNombre(materialVO.getName());
		return materialDto;
	}

	@Override
	public MaterialVO getVOFromInfoRest(MaterialInfoRest materialInfoRest) {
		MaterialVO materialVO = conversor.transform(materialInfoRest, MaterialVO.class);
		materialVO.setTipo(ponerTipo(materialVO.getColor()));
		materialVO.setFechaAlta(new Date());
		return materialVO;
	}

	private String ponerTipo(String color) {
		String tipoDistintivo = "";
		
		if ( color.equalsIgnoreCase("gray") ) {
			tipoDistintivo = "PC";
		} else if (color.equalsIgnoreCase("blue")) {
			tipoDistintivo = TipoDistintivo.CERO.getValorEnum();
		} else if (color.equalsIgnoreCase("aquamarine")) {
			tipoDistintivo = TipoDistintivo.ECO.getValorEnum();
		} else if (color.equalsIgnoreCase("green")) {
			tipoDistintivo = TipoDistintivo.C.getValorEnum();
		} else if (color.equalsIgnoreCase("yellow")) {
			tipoDistintivo = TipoDistintivo.B.getValorEnum();
		} else if (color.equalsIgnoreCase("#FFED21")) {
			tipoDistintivo = TipoDistintivo.BMT.getValorEnum();
		} else if (color.equalsIgnoreCase("#64FF49")) {
			tipoDistintivo = TipoDistintivo.CMT.getValorEnum();
		} else if (color.equalsIgnoreCase("#CA34E8")) {
			tipoDistintivo = TipoDistintivo.ECOMT.getValorEnum();
		} else if (color.equalsIgnoreCase("#3C21FF")) {
			tipoDistintivo = TipoDistintivo.CEROMT.getValorEnum();
		} 
		
		return tipoDistintivo;
	}
	
}
