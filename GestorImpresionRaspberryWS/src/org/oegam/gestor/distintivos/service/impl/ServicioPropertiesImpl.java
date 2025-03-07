package org.oegam.gestor.distintivos.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.gestoresmadrid.core.properties.model.dao.PropertiesDao;
import org.gestoresmadrid.core.properties.model.vo.PropertiesVO;
import org.oegam.gestor.distintivos.constants.GestorImprDocConst;
import org.oegam.gestor.distintivos.integracion.dto.PropertiesDto;
import org.oegam.gestor.distintivos.service.ServicioProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPropertiesImpl implements ServicioProperties {

	private static final long serialVersionUID = 6120119894389207519L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPropertiesImpl.class);

	@Autowired
	PropertiesDao propertiesDao;

	@Transactional
	@Override
	public HashMap<String, String> refrescarProperties() {
		HashMap<String,String> mapaPropiedades = null;
		try {
			List<PropertiesDto> listaPropertiesDtos = getlistaPropertiesProceso();
			if (listaPropertiesDtos != null && !listaPropertiesDtos.isEmpty()) {
				mapaPropiedades = crearMapaPropiedades(listaPropertiesDtos);
				if (mapaPropiedades != null && !mapaPropiedades.isEmpty()) {
					cargarPreferentes(mapaPropiedades);
					if (mapaPropiedades == null || mapaPropiedades.isEmpty()) {
						log.error("Ha sucedido un error a la hora de cargar el mapa de propiedades preferentes para los procesos.");
					}
				} else {
					log.error("Ha sucedido un error a la hora de crear el mapa de propiedades para los procesos.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error la hora de refrecar las properties de procesos, error: ", e);
		}
		return mapaPropiedades;
	}

	private void cargarPreferentes(HashMap<String,String> mapaPropiedades) throws IOException {
		Properties oegamProperties = new Properties();
		oegamProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(GestorImprDocConst.OEGAM_PROPERTIES));
		if (oegamProperties != null && oegamProperties.propertyNames() != null) {
			@SuppressWarnings("unchecked")
			Enumeration<String> propiedadesFichero = (Enumeration<String>) oegamProperties.propertyNames();
			while (propiedadesFichero.hasMoreElements()) {
				String propiedadFichero = (String)propiedadesFichero.nextElement();
				mapaPropiedades.put(propiedadFichero, oegamProperties.getProperty(propiedadFichero));
			}
		}
	}

	private HashMap<String, String> crearMapaPropiedades(List<PropertiesDto> listaPropertiesDtos) {
		HashMap<String,String> mapaPropiedades =  new HashMap<>();
		for (PropertiesDto prop : listaPropertiesDtos) {
			mapaPropiedades.put(prop.getNombre(), prop.getValor());
		}
		return mapaPropiedades;
	}

	@Override
	@Transactional(readOnly=true)
	public List<PropertiesDto> getlistaPropertiesProceso() {
		List<PropertiesDto> lista = new ArrayList<>();
		try {
			List<PropertiesVO> listaBBDD = propertiesDao.getListaPropertiesPorContexto(GestorImprDocConst.CONTEXTO_PROPERTIES_FRONTAL);
			if (listaBBDD != null && !listaBBDD.isEmpty()) {
				for (PropertiesVO propertiesVO : listaBBDD) {
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
			log.error("Ha sucedido un error a la hora de obtener las properties para los procesos, error: ", e);
			lista = Collections.emptyList();
		}
		return lista;
	}

}