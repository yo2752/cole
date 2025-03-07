package org.gestoresmadrid.oegamComun.interviniente.service.impl;

import java.io.Serializable;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.oegamComun.interviniente.view.bean.ResultadoIntevTraficoBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class ServicioValIntervinieteTrafico implements Serializable{

	private static final long serialVersionUID = 71235916337785508L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValIntervinieteTrafico.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;
	
	public void modificarDatosCombos(IntervinienteTraficoVO interviniente) {
		interviniente.setConceptoRepre(utiles.convertirCombo(interviniente.getConceptoRepre()));
		interviniente.setIdMotivoTutela(utiles.convertirCombo(interviniente.getIdMotivoTutela()));
		interviniente.setCodigoIae(utiles.convertirCombo(interviniente.getCodigoIae()));
	}

	public ResultadoIntevTraficoBean esModificada(IntervinienteTraficoVO intervinienteVO, IntervinienteTraficoVO intervinienteBBDD) {
		ResultadoIntevTraficoBean resultado = new ResultadoIntevTraficoBean(Boolean.FALSE);
		try {
			if (!utiles.sonIgualesObjetos(intervinienteVO.getId(), intervinienteBBDD.getId())
					|| (intervinienteVO.getId() != null && intervinienteBBDD.getId() != null && !utiles.sonIgualesString(intervinienteVO.getId().getNif(), intervinienteBBDD.getId().getNif()))) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.getId().setNif(intervinienteVO.getId().getNif());
			}
			if (!utiles.sonIgualesObjetos(intervinienteVO.getIdDireccion(), intervinienteBBDD.getIdDireccion())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setIdDireccion(intervinienteVO.getIdDireccion());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getCambioDomicilio(), intervinienteBBDD.getCambioDomicilio())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setCambioDomicilio(intervinienteVO.getCambioDomicilio());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getAutonomo(), intervinienteBBDD.getAutonomo())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setAutonomo(intervinienteVO.getAutonomo());
			}
			if (!utiles.sonIgualesCombo(intervinienteVO.getCodigoIae(), intervinienteBBDD.getCodigoIae())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setCodigoIae(utiles.convertirCombo(intervinienteVO.getCodigoIae()));
			}
			if (utilesFecha.compararFechaDate(intervinienteVO.getFechaInicio(), intervinienteBBDD.getFechaInicio()) != 0) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setFechaInicio(intervinienteVO.getFechaInicio());
			}
			if (utilesFecha.compararFechaDate(intervinienteVO.getFechaFin(), intervinienteBBDD.getFechaFin()) != 0) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setFechaFin(intervinienteVO.getFechaFin());
			}
			if (!utiles.sonIgualesCombo(intervinienteVO.getConceptoRepre(), intervinienteBBDD.getConceptoRepre())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setConceptoRepre(utiles.convertirCombo(intervinienteVO.getConceptoRepre()));
			}
			if (!utiles.sonIgualesString(intervinienteVO.getIdMotivoTutela(), intervinienteBBDD.getIdMotivoTutela())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setIdMotivoTutela(intervinienteVO.getIdMotivoTutela());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getHoraInicio(), intervinienteBBDD.getHoraInicio())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setHoraInicio(intervinienteVO.getHoraInicio());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getHoraFin(), intervinienteBBDD.getHoraFin())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setHoraFin(intervinienteVO.getHoraFin());
			}
			if (!utiles.sonIgualesString(intervinienteVO.getDatosDocumento(), intervinienteBBDD.getDatosDocumento())) {
				resultado.setEsModificada(Boolean.TRUE);
				intervinienteBBDD.setDatosDocumento(intervinienteVO.getDatosDocumento());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error al comparar el interviniente de pantalla con el interviniente de la bbdd, error: ", e);
			resultado.setMensaje("Ha sucedido un error al comparar el interviniente de pantalla con el interviniente de la bbdd.");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

}
