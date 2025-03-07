package org.gestoresmadrid.oegam2.utiles;

import java.util.Arrays;

import org.gestoresmadrid.core.cola.enumerados.EstadoCola;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaGestionCola {

	private static UtilesVistaGestionCola utilesVistaGestionCola;

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaGestionCola.class);

	@Autowired
	ServicioCola servicioCola;

	public static UtilesVistaGestionCola getInstance() {
		if (utilesVistaGestionCola == null) {
			utilesVistaGestionCola = new UtilesVistaGestionCola();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaGestionCola);
		}
		return utilesVistaGestionCola;
	}

	public TipoTramiteTrafico[] getTiposTramites() {
		return TipoTramiteTrafico.values();
	}

	public DatoMaestroBean[] getNumColas() {
		DatoMaestroBean[] numsColas = null;
		try {
			Integer intNumColas = servicioCola.obtenerMaxCola();
			numsColas = new DatoMaestroBean[intNumColas];
			int contador = 1;
			for (int i = 0; i < intNumColas; i++) {
				numsColas[i] = new DatoMaestroBean(String.valueOf(contador), String.valueOf(contador));
				contador++;
			}
			return numsColas;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de parsear el numero de colas de la BBDD, error: ", e);
		}
		return new DatoMaestroBean[0];
	}

	public EstadoCola[] getEstadosCola() {
		return EstadoCola.getListaEstadoColaSinHilo();
	}

	public ProcesosEnum[] getListaProcesos() {
		ProcesosEnum[] listaProcesosEnumAux = ProcesosEnum.values();
		ProcesosEnum[] listaProcesosEnum = new ProcesosEnum[listaProcesosEnumAux.length];
		String[] nombres = new String[listaProcesosEnumAux.length];
		for (int i = 0; i < listaProcesosEnumAux.length; i++) {
			nombres[i] = listaProcesosEnumAux[i].getNombreEnum();
		}
		Arrays.sort(nombres);
		listaProcesosEnum = new ProcesosEnum[listaProcesosEnumAux.length];
		for (int i = 0; i < nombres.length; i++) {
			for (int j = 0; j < listaProcesosEnumAux.length; j++) {
				if (nombres[i].equals(listaProcesosEnumAux[j].getNombreEnum())) {
					listaProcesosEnum[i] = listaProcesosEnumAux[j];
				}
			}
		}
		return listaProcesosEnum;
	}
}