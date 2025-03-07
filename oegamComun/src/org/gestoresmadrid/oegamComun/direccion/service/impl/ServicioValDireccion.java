package org.gestoresmadrid.oegamComun.direccion.service.impl;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.oegamComun.direccion.view.bean.ResultadoDireccionBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class ServicioValDireccion implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValDireccion.class);
	
	@Autowired
	Utiles utiles;
	
	public ResultadoDireccionBean comprobarDireccionCorrecta(DireccionVO direccion) {
		ResultadoDireccionBean resultado = new ResultadoDireccionBean(Boolean.FALSE);
		try {
			if (utiles.convertirCombo(direccion.getIdMunicipio()) == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un municipio");
			} else if (utiles.convertirCombo(direccion.getIdProvincia()) == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar una provincia");
			} else if (utiles.convertirCombo(direccion.getIdTipoVia()) == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar una via");
			} else if (utiles.convertirNulltoString(direccion.getNombreVia()) == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un nombre de via");
			} else if (utiles.convertirNulltoString(direccion.getNumero()) == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un numero de via");
			}else if(StringUtils.isNotBlank(direccion.getNombreVia())){
				String viaSinEditar = getViaSinEditar(direccion.getNombreVia());
				direccion.setViaSineditar(viaSinEditar);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar la direccion, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de comprobar la direccion.");
		}
		return resultado;
	}
	
	private String getViaSinEditar(String nombreVia) {
		nombreVia = nombreVia.toUpperCase();
		nombreVia = nombreVia.replace("(", " ");
		nombreVia = nombreVia.replace(")", " ");
		nombreVia = nombreVia.replace("-", " ");
		nombreVia = nombreVia.replace("/", " ");
		nombreVia = nombreVia.replace("_", " ");
		nombreVia = nombreVia.replace(",", " ");
		nombreVia = nombreVia.replace(" DE ", "");
		nombreVia = nombreVia.replace(" LA ", "");
		nombreVia = nombreVia.replace(" LAS ", "");
		nombreVia = nombreVia.replace(" LOS ", "");
		nombreVia = nombreVia.replace(" EL ", "");
		nombreVia = nombreVia.replace(" DEL ", "");
		nombreVia = nombreVia.replace(" UN ", "");
		nombreVia = nombreVia.replace(" UNOS ", "");
		nombreVia = nombreVia.replace(" ", "");
		nombreVia = nombreVia.replace("ª", "");
		nombreVia = nombreVia.replace("º", "");
		nombreVia = nombreVia.replace("\\", "");
		nombreVia = nombreVia.replace(";", "");
		nombreVia = nombreVia.replace(".", "");
		nombreVia = nombreVia.replace(":", "");
		nombreVia = nombreVia.replace("Á", "A");
		nombreVia = nombreVia.replace("É", "A");
		nombreVia = nombreVia.replace("Í", "A");
		nombreVia = nombreVia.replace("Ó", "A");
		nombreVia = nombreVia.replace("Ú", "A");
		return nombreVia;
	}

	public Boolean esModificadaDireccion(DireccionVO direccionVO, DireccionVO direccionBBDD) {
		Boolean actualiza = false;
		direccionVO.setIdDireccion(direccionBBDD.getIdDireccion());
		if (!utiles.sonIgualesCombo(direccionVO.getIdTipoVia(), direccionBBDD.getIdTipoVia())) {
			actualiza = true;
			direccionBBDD.setIdTipoVia(utiles.convertirCombo(direccionVO.getIdTipoVia()));
		}
		if (!utiles.sonIgualesString(direccionVO.getNumero(), direccionBBDD.getNumero())) {
			actualiza = true;
			direccionBBDD.setNumero(direccionVO.getNumero());
		}
		if (!utiles.sonIgualesString(direccionVO.getPueblo(), direccionBBDD.getPueblo())) {
			actualiza = true;
			direccionBBDD.setPueblo(direccionVO.getPueblo());
		}
		if (!utiles.sonIgualesString(direccionVO.getPuebloCorreos(), direccionBBDD.getPuebloCorreos())) {
			actualiza = true;
			direccionBBDD.setPuebloCorreos(direccionVO.getPuebloCorreos());
		}
		if (!utiles.sonIgualesString(direccionVO.getLetra(), direccionBBDD.getLetra())) {
			actualiza = true;
			direccionBBDD.setLetra(direccionVO.getLetra());
		}
		if (!utiles.sonIgualesString(direccionVO.getIdMunicipio(), direccionBBDD.getIdMunicipio())) {
			actualiza = true;
			direccionBBDD.setIdMunicipio(direccionVO.getIdMunicipio());
		}
		if (!utiles.sonIgualesString(direccionVO.getIdProvincia(), direccionBBDD.getIdProvincia())) {
			actualiza = true;
			direccionBBDD.setIdProvincia(direccionVO.getIdProvincia());
		}
		if (!utiles.sonIgualesString(direccionVO.getEscalera(), direccionBBDD.getEscalera())) {
			actualiza = true;
			direccionBBDD.setEscalera(direccionVO.getEscalera());
		}
		if (!utiles.sonIgualesString(direccionVO.getBloque(), direccionBBDD.getBloque())) {
			actualiza = true;
			direccionBBDD.setBloque(direccionVO.getBloque());
		}
		if (!utiles.sonIgualesString(direccionVO.getPlanta(), direccionBBDD.getPlanta())) {
			actualiza = true;
			direccionBBDD.setPlanta(direccionVO.getPlanta());
		}
		if (!utiles.sonIgualesString(direccionVO.getPuerta(), direccionBBDD.getPuerta())) {
			actualiza = true;
			direccionBBDD.setPuerta(direccionVO.getPuerta());
		}
		if (!utiles.sonIgualesObjetos(direccionVO.getKm(), direccionBBDD.getKm())) {
			actualiza = true;
			direccionBBDD.setKm(direccionVO.getKm());
		}
		if (!utiles.sonIgualesObjetos(direccionVO.getHm(), direccionBBDD.getHm())) {
			actualiza = true;
			direccionBBDD.setHm(direccionVO.getHm());
		}
		if (!utiles.sonIgualesString(direccionVO.getCodPostal(), direccionBBDD.getCodPostal())) {
			actualiza = true;
			direccionBBDD.setCodPostal(direccionVO.getCodPostal());
		}
		if (!utiles.sonIgualesString(direccionVO.getCodPostalCorreos(), direccionBBDD.getCodPostalCorreos())) {
			actualiza = true;
			direccionBBDD.setCodPostalCorreos(direccionVO.getCodPostalCorreos());
		}
		if (!utiles.sonIgualesString(direccionVO.getViaSineditar(), direccionBBDD.getViaSineditar())) {
			actualiza = true;
			direccionBBDD.setViaSineditar(direccionVO.getViaSineditar());
		}
		if (!utiles.sonIgualesString(direccionVO.getNombreVia(), direccionBBDD.getNombreVia())) {
			actualiza = true;
			direccionBBDD.setNombreVia(direccionVO.getNombreVia());
		}
		if (!utiles.sonIgualesString(direccionVO.getPais(), direccionBBDD.getPais())) {
			actualiza = true;
			direccionBBDD.setPais(direccionVO.getPais());
		}
		if (!utiles.sonIgualesString(direccionVO.getRegionExtranjera(), direccionBBDD.getRegionExtranjera())) {
			actualiza = true;
			direccionBBDD.setRegionExtranjera(direccionVO.getRegionExtranjera());
		}
		return actualiza;
	}

}
