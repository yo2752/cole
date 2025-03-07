package org.gestoresmadrid.oegamCreditos.view.utiles;

import java.util.List;

import org.gestoresmadrid.oegamCreditos.service.ServicioTipoCreditos;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoDto;
import org.gestoresmadrid.oegamCreditos.view.dto.TipoCreditoTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaCredito {

	private static UtilesVistaCredito utilesVistaCredito;

	@Autowired
	private ServicioTipoCreditos servicioTipoCredito;

	public synchronized static UtilesVistaCredito getInstance() {
		if (utilesVistaCredito == null) {
			utilesVistaCredito = new UtilesVistaCredito();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaCredito);
		}
		return utilesVistaCredito;
	}

	public List<TipoCreditoDto> obtenerTiposCreditos() {
		return servicioTipoCredito.getTipoCreditos();
	}

	public List<TipoCreditoDto> obtenerTiposCreditosIncrementales() {
		return servicioTipoCredito.getTipoCreditosIncrementales();
	}

	public boolean chequearTipoTramite(String tipoTramite, String tipoCredito) {
		List<TipoCreditoTramiteDto> listTipoCreditoTramite = servicioTipoCredito.getListaTiposCreditosTramite(tipoCredito);
		if (listTipoCreditoTramite != null && !listTipoCreditoTramite.isEmpty()) {
			for (TipoCreditoTramiteDto tipo : listTipoCreditoTramite) {
				if (tipo.getTipoTramite().equalsIgnoreCase(tipoTramite)) {
					return true;
				}
			}
		}
		return false;
	}
}
