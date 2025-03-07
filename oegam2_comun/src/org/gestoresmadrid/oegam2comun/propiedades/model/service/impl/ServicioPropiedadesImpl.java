package org.gestoresmadrid.oegam2comun.propiedades.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.gestoresmadrid.core.properties.model.dao.PropertiesDao;
import org.gestoresmadrid.core.properties.model.vo.PropertiesVO;
import org.gestoresmadrid.oegam2comun.properties.view.dto.PropertiesDto;
import org.gestoresmadrid.oegam2comun.propiedades.model.service.ServicioPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPropiedadesImpl implements ServicioPropiedades{

	private static final long serialVersionUID = -3311469261873527506L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPropiedadesImpl.class);
	
	@Autowired
	PropertiesDao propertiesDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<PropertiesDto> getlistaPropiedades() {
		List<PropertiesDto> lista = new ArrayList<PropertiesDto>();
		String entorno = dameEntorno();
		try {
			List<PropertiesVO> listaBBDD = propertiesDao.getListaPropertiesPorContexto(entorno);
			if(listaBBDD != null && !listaBBDD.isEmpty()){
				for(PropertiesVO propertiesVO : listaBBDD){
					PropertiesDto propertiesDto = new PropertiesDto();
					propertiesDto.setComentario(propertiesVO.getComentario());
					propertiesDto.setId(propertiesVO.getId());
					propertiesDto.setIdContext(propertiesVO.getIdContext());
					propertiesDto.setNombre(propertiesVO.getNombre());
					propertiesDto.setValor(propertiesVO.getValor());
					lista.add(propertiesDto);
				} 
				return lista;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener las properties, error: ",e);
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	@Override
	@Transactional
	public void actualizarProperties(String nombre, String valor, String entorno) {
		PropertiesVO propertie = propertiesDao.getPropertiePorNombreYEntorno(nombre,entorno);
		if(propertie != null) {
			propertie.setValor(valor);
			propertiesDao.actualizar(propertie);
		}
	}
	
	private String dameEntorno() {
		String entorno = "";
		try {
			Properties oegamProperties = new Properties();
			oegamProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("oegam.properties"));
			entorno = oegamProperties.getProperty("entorno"); 
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cargar las properties de oegam.properties, error: ",e);
		}
		return entorno;
	}

}
