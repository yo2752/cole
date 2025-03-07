package org.gestoresmadrid.oegamComun.utiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesBastidorDao;
import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesMatriculaDao;
import org.gestoresmadrid.core.datosSensibles.model.dao.DatosSensiblesNifDao;
import org.gestoresmadrid.core.general.model.dao.GrupoDao;
import org.gestoresmadrid.core.general.model.vo.GrupoVO;
import org.gestoresmadrid.oegamComun.general.service.ServicioComunIpNoValida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class UtilesDatosSensibles implements Serializable {

	private static final long serialVersionUID = -5256727477853831360L;

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesDatosSensibles.class);

	private Map<String, String> grupos;
	private List<String> matriculasSensibles;
	private List<String> bastidoresSensibles;
	private List<String> nifsSensibles;
	private Map<String, String> ipPermitidas;

	@Autowired
	private GrupoDao grupoDao;

	@Autowired
	private DatosSensiblesMatriculaDao datosSensiblesMatriculaDao;

	@Autowired
	private DatosSensiblesNifDao datosSensiblesNifDao;

	@Autowired
	private DatosSensiblesBastidorDao datosSensiblesBastidorDao;

	@Autowired
	private ServicioComunIpNoValida servicioIpNoValida;

	public Map<String, String> direccionesGrupos() {
		if (grupos == null) {
			recargarGrupos();
		}
		return grupos;
	}

	public List<String> getMatriculasSensibles() {
		if (matriculasSensibles == null) {
			recargaMatriculas();
		}
		return matriculasSensibles;
	}

	public List<String> getBastidoresSensibles() {
		if (bastidoresSensibles == null) {
			recargaMatriculas();
		}
		return bastidoresSensibles;
	}

	public List<String> getNifsSensibles() {
		if (nifsSensibles == null) {
			recargaMatriculas();
		}
		return nifsSensibles;
	}

	public void recargarDatosSensibles() {
		try {
			recargarGrupos();
		} catch (Exception e) {
			log.error("Error inesperado recargando grupos", e);
		}

		try {
			recargaBastidores();
		} catch (Exception e) {
			log.error("Error inesperado recargando bastidores", e);
		}

		try {
			recargaMatriculas();
		} catch (Exception e) {
			log.error("Error inesperado matriculas bastidores", e);
		}

		try {
			recargaNifs();
		} catch (Exception e) {
			log.error("Error inesperado nifs bastidores", e);
		}

		try {
			recargarIP();
		} catch (Exception e) {
			log.error("Error inesperado ip permitidas", e);
		}
	}

	private boolean recargarIP() {
		ipPermitidas = servicioIpNoValida.obtenerIP();
		if (ipPermitidas != null) {
			return true;
		}
		return false;
	}

	private boolean recargarGrupos() {
		List<GrupoVO> list = grupoDao.getGrupos();
		if (list != null) {
			Map<String, String> map = new HashMap<String, String>();

			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getIdGrupo(), list.get(i).getCorreoElectronico());
			}

			if (map != null) {
				grupos = map;
				return true;
			}
		}
		return false;
	}

	private boolean recargaNifs() {
		List<String> tempNif = datosSensiblesNifDao.getListadoNifsSensibles();
		if (tempNif != null) {
			nifsSensibles = tempNif;
			return true;
		}
		return false;
	}

	private boolean recargaMatriculas() {
		List<String> tempMatr = datosSensiblesMatriculaDao.getListadoMatriculasSensibles();
		if (tempMatr != null) {
			matriculasSensibles = tempMatr;
			return true;
		}
		return false;
	}

	private boolean recargaBastidores() {
		List<String> tempBasti = datosSensiblesBastidorDao.getListadoBastidoresSensibles();
		if (tempBasti != null) {
			bastidoresSensibles = tempBasti;
			return true;
		}
		return false;
	}
}