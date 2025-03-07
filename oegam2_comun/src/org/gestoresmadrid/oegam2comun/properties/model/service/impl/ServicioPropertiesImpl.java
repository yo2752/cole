package org.gestoresmadrid.oegam2comun.properties.model.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.gestoresmadrid.core.properties.model.dao.PropertiesDao;
import org.gestoresmadrid.core.properties.model.vo.PropertiesVO;
import org.gestoresmadrid.oegam2comun.properties.model.service.ServicioProperties;
import org.gestoresmadrid.oegam2comun.properties.view.dto.PropertiesDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import general.utiles.Constantes;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPropertiesImpl implements ServicioProperties {

	private static final long serialVersionUID = -3311469261873527506L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPropertiesImpl.class);

	@Autowired
	PropertiesDao propertiesDao;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	@Transactional(readOnly = true)
	public ResultBean refrescarProperties() {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			HashMap<String, String> mapaPropiedades = null;
			List<PropertiesDto> listaPropertiesDtos = getlistaProperties();
			if (listaPropertiesDtos != null && !listaPropertiesDtos.isEmpty()) {
				mapaPropiedades = crearMapaPropiedades(listaPropertiesDtos);
				if (!mapaPropiedades.isEmpty()) {
					cargarPreferentes(mapaPropiedades);
					if (mapaPropiedades.isEmpty()) {
						log.error("Ha sucedido un error a la hora de cargar el mapa de propiedades preferentes para los procesos.");
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Ha sucedido un error a la hora de cargar el mapa de propiedades preferentes para los procesos.");
					} else {
						gestorPropiedades.refrescarPropperties(mapaPropiedades);
					}
				} else {
					log.error("Ha sucedido un error a la hora de crear el mapa de propiedades para los procesos.");
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de crear el mapa de propiedades para los procesos.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error la hora de refrecar las properties de procesos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error la hora de refrecar las properties de procesos.");
		}
		return resultado;
	}

	private void cargarPreferentes(HashMap<String, String> mapaPropiedades) throws IOException {
		Properties oegamProperties = new Properties();
		oegamProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constantes.OEGAM_PROPERTIES));
		if (oegamProperties.propertyNames() != null) {
			@SuppressWarnings("unchecked")
			Enumeration<String> propiedadesFichero = (Enumeration<String>) oegamProperties.propertyNames();
			while (propiedadesFichero.hasMoreElements()) {
				String propiedadFichero = propiedadesFichero.nextElement();
				mapaPropiedades.put(propiedadFichero, oegamProperties.getProperty(propiedadFichero));
			}
		}
	}

	private HashMap<String, String> crearMapaPropiedades(List<PropertiesDto> listaPropertiesDtos) {
		HashMap<String, String> mapaPropiedades = new HashMap<String, String>();
		for (PropertiesDto prop : listaPropertiesDtos) {
			mapaPropiedades.put(prop.getNombre(), prop.getValor());
		}
		return mapaPropiedades;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PropertiesDto> getlistaProperties() {
		List<PropertiesDto> lista = new ArrayList<PropertiesDto>();
		try {
			List<PropertiesVO> listaBBDD = propertiesDao.getListaPropertiesPorContexto(gestorPropiedades.valorPropertie("entorno"));
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
			log.error("Ha sucedido un error a la hora de obtener las properties, error: ", e);
			lista = Collections.emptyList();
		}
		return lista;
	}

	@Override
	@Transactional
	public void actualizarProperties(String nombre, String valor, String entorno) {
		PropertiesVO propertie = propertiesDao.getPropertiePorNombreYEntorno(nombre, entorno);
		if (propertie != null) {
			propertie.setValor(valor);
			propertiesDao.actualizar(propertie);
		}
	}

	@Override
	@Transactional
	public ResultBean actualizarPropertiePantalla(String id, String valorNuevo) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			PropertiesVO propertiesBBDD = propertiesDao.getPropertiePorId(new Long(id));
			resultado = validarDatosPantalla(propertiesBBDD, valorNuevo);
			if (!resultado.getError()) {
				propertiesBBDD.setValor(valorNuevo);
				propertiesDao.actualizar(propertiesBBDD);
				gestorPropiedades.cargarPropertie(propertiesBBDD.getNombre(), propertiesBBDD.getValor());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la propertie por pantalla, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar la propertie por pantalla.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean validarDatosPantalla(PropertiesVO propertiesBBDD, String valorNuevo) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		if (propertiesBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se han encontrado datos que actualizar.");
		} else if (valorNuevo == null || valorNuevo.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar un valor nuevo para la propertie.");
		}
		return resultado;
	}

}
