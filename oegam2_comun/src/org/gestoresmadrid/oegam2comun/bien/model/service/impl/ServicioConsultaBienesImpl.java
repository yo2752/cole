package org.gestoresmadrid.oegam2comun.bien.model.service.impl;

import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioConsultaBienes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

@Service
public class ServicioConsultaBienesImpl implements ServicioConsultaBienes {

	private static final long serialVersionUID = -7659680126738061103L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaBienesImpl.class);

	@Autowired
	private ServicioBien servicioBien;

	@Override
	public ResultBean eliminarBloque(String codSeleccionados) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] idBienes = codSeleccionados.split("-");
				boolean existeError = false;
				boolean existenRemesas = false;
				boolean existenModelos = false;
				boolean existenInmuebles = false;
				for (String idBien : idBienes) {
					ResultBean resultEliminar = servicioBien.eliminarBien(Long.parseLong(idBien));
					if (resultEliminar.getError()) {
						log.error(resultEliminar.getListaMensajes().get(0));
						existeError = true;
						if (resultEliminar.getAttachment("existenRemesas") != null) {
							existenRemesas = true;
						}
						if (resultEliminar.getAttachment("existenModelos") != null) {
							existenModelos = true;
						}
						if (resultEliminar.getAttachment("existenInmuebles") != null) {
							existenInmuebles = true;
						}
					}
				}
				if (existeError) {
					resultado.setError(true);
					String mensaje = "Alguno de los bienes no se han podido eliminar";
					if (existenModelos && existenRemesas && existenInmuebles) {
						mensaje += ", porque está asociado a alguna remesa, modelo e inmueble.";
					} else if (existenModelos && existenInmuebles) {
						mensaje += ", porque está asociado a algún modelo e inmueble.";
					} else if (existenRemesas && existenInmuebles) {
						mensaje += ", porque está asociado a alguna remesa e inmueble.";
					} else if (existenModelos && existenRemesas) {
						mensaje += ", porque está asociado a alguna remesa y modelo.";
					} else if (existenModelos) {
						mensaje += ", porque está asociado a algún modelo.";
					} else if (existenRemesas) {
						mensaje += ", porque está asociado a alguna remesa.";
					} else if (existenInmuebles) {
						mensaje += ", porque está asociado a algún inmueble.";
					}
					resultado.addMensajeALista(mensaje);
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar algún bien para eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los bienes.");
		}
		return resultado;
	}

}
