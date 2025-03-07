package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarEvolucionMaterial;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGuardarMaterialesImpl implements ServicioGuardarMateriales {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2275127857906344957L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGuardarMaterialesImpl.class);

	@Resource MaterialDao   materialDao;
	
	@Autowired ServicioConsultaMateriales       servicioConsultaMateriales;
	@Autowired ServicioGuardarEvolucionMaterial servicioGuardarEvolucionMaterial;
	
	@Override
	@Transactional
	public ResultBean salvarMaterial(MaterialDto materialDto) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			MaterialVO nuevo = servicioConsultaMateriales.getVOFromDto(materialDto);

			MaterialVO original = materialDao.findByPrimaryKey(materialDto.getMaterialId());
			
			if (original == null) {
				MaterialVO material = (MaterialVO) materialDao.guardarOActualizar(nuevo);
				
				@SuppressWarnings("unused")
				ResultBean resultEvolucion = servicioGuardarEvolucionMaterial.guardarEvolucionMaterial(material, EstadoMaterial.Activo);
			} else {
				if (!isIgual(original, nuevo)) {
					@SuppressWarnings("unused")
					MaterialVO material = (MaterialVO) materialDao.guardarOActualizar(nuevo);
					
					@SuppressWarnings("unused")
					ResultBean resultEvolucion = servicioGuardarEvolucionMaterial.guardarEvolucionMaterial(original, EstadoMaterial.Inactivo);
					resultEvolucion = servicioGuardarEvolucionMaterial.guardarEvolucionMaterial(nuevo, EstadoMaterial.Activo);
				}
			}
			
			log.info("Material guardado correctamente");
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
		}
		
		return result;
			
	}
	
	@Override
	@Transactional
	public ResultBean salvarMaterial(MaterialVO materialvo) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			MaterialVO original = materialDao.findByPrimaryKey(materialvo.getMaterialId());
			if (!materialvo.equals(original)) {
				log.info("Modificacion del material " + materialvo.getName());
				
				ResultBean resultEvolucion = null;
				if (original != null) {
					resultEvolucion = servicioGuardarEvolucionMaterial.guardarEvolucionMaterial(original, EstadoMaterial.Inactivo);
					if (!resultEvolucion.getError()) {
						original.setName(materialvo.getName());
						original.setCodigo(materialvo.getCodigo());
						original.setColor(materialvo.getColor());
						original.setDescripcion(materialvo.getDescripcion());
						original.setFechaAlta(new Date());
						original.setPrecio(materialvo.getPrecio());
						original.setTipo(materialvo.getTipo());
						
						@SuppressWarnings("unused")
						MaterialVO material = (MaterialVO) materialDao.guardarOActualizar(original);
					}
				} else {
					MaterialVO material = (MaterialVO) materialDao.guardarOActualizar(materialvo);
					resultEvolucion = servicioGuardarEvolucionMaterial.guardarEvolucionMaterial(material, EstadoMaterial.Activo);
				}


			}
			
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
		}
		
		return result;
	}
	
	private boolean isIgual(MaterialVO original, MaterialVO nuevo) {
		if (!original.getName().equals(nuevo.getName())) {
			return false;
		} else if (!original.getDescripcion().equals(nuevo.getDescripcion())) {
			return false;
		} else if (original.getPrecio() != nuevo.getPrecio()) {
			return false;
		} else if (!original.getColor().equals(nuevo.getColor())) {
			return false;
		} else if (!original.getCodigo().equals(nuevo.getCodigo())) {
			return false;
		} else {
			return true;
		}
		
		
	}

}
