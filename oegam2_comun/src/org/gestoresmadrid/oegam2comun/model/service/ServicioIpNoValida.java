package org.gestoresmadrid.oegam2comun.model.service;

import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.general.model.vo.IpNoPermitidasVO;

public interface ServicioIpNoValida {

	/**
	 * Método que devuelve una lista con las IPs no permitidas
	 * @return List
	 */
	public List<IpNoPermitidasVO> getListaIPNoPermitidas();

	public boolean validarIP(String ipAccesso, List<IpNoPermitidasVO> listaIPNoPermitidas);

	public Map<String, String> obtenerIP();

}