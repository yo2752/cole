package org.gestoresmadrid.oegam2comun.codigoIAE.model.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.codigoIae.model.dao.CodigoIAEDao;
import org.gestoresmadrid.core.codigoIae.model.vo.CodigoIAEVO;
import org.gestoresmadrid.oegam2comun.codigoIAE.model.service.ServicioCodigoIae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trafico.beans.IAEBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioCodigoIaeImpl implements ServicioCodigoIae {

	private static final long serialVersionUID = 4979656672526882742L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioCodigoIaeImpl.class);

	@Autowired
	private CodigoIAEDao codigoIAEDao;

	@Override
	@Transactional
	public List<IAEBean> obtenerCodigosIAE() {
		List<IAEBean> codigos = new ArrayList<IAEBean>();
		List<CodigoIAEVO> lista = codigoIAEDao.listaCodigosIAE();
		if (null != lista && !lista.isEmpty()) {
			for (CodigoIAEVO elemento : lista) {
				IAEBean codigo = new IAEBean();
				codigo.setCodigo_IAE(elemento.getCodigoIAE());
				codigo.setDescripcion(elemento.getDescripcion());
				String descripcion = codigo.getCodigo_IAE() + " - " + codigo.getDescripcion();
				if (descripcion.length() >= 80) {
					codigo.setDescripcion(descripcion.substring(0, 80) + "...");
				} else {
					codigo.setDescripcion(descripcion);
				}
				codigos.add(codigo);
			}
		}
		log.info("ObtenerCodigosIAE.");
		return codigos;
	}

}
