package org.gestoresmadrid.oegam2comun.comun.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.comun.BeanDatosFiscales;
import org.gestoresmadrid.oegam2comun.comun.DatosFiscales;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.entities.general.Colegiado;

@Service
public class DatosFiscalesImpl implements DatosFiscales {

	private static final long serialVersionUID = -4436144317375788142L;

	@Autowired
	ServicioComunContrato servicoContrato;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	public BeanDatosFiscales obtenerDatosFiscales(BigDecimal numExpediente) {
		BeanDatosFiscales datosFiscales = new BeanDatosFiscales();
		TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, Boolean.FALSE);
		datosFiscales.setExternalSystemFiscall(tramite.getContrato().getColegio().getCif());
		datosFiscales.setAgencyfiscalld(tramite.getContrato().getColegiado().getUsuario().getNif());
		datosFiscales.setAgentfiscalid(tramite.getContrato().getColegiado().getUsuario().getNif());
		return datosFiscales;
	}

	public BeanDatosFiscales obtenerDatosFiscalesDelColegiado(String numColegiado) {
		BeanDatosFiscales datosFiscales = new BeanDatosFiscales();
		Colegiado colegiadoCompleto = utilesColegiado.getColegiado(numColegiado);
		ContratoVO contrato = servicoContrato.getContratoPorColegiado(numColegiado);
		datosFiscales.setExternalSystemFiscall(contrato.getColegio().getCif());
		datosFiscales.setAgencyfiscalld(colegiadoCompleto.getUsuario().getNif());
		datosFiscales.setAgentfiscalid(colegiadoCompleto.getUsuario().getNif());
		return datosFiscales;
	}
}
