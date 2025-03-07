package org.gestoresmadrid.oegam2.controller.envioData.action;

import java.util.List;

import org.gestoresmadrid.oegam2comun.envioData.model.service.ServicioEnvioData;
import org.gestoresmadrid.oegam2comun.envioData.view.bean.ResultadoEnvioDataBean;
import org.gestoresmadrid.oegam2comun.envioData.view.dto.EnvioDataDto;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EnvioDataAction extends ActionBase{

	private static final long serialVersionUID = -5705753168768406944L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(EnvioDataAction.class);

	private static final String LISTA_ENVIO_DATA = "pagListaEnvioData";

	private List<EnvioDataDto> listaEnvioData;
	private String proceso;
	private String tipoActualizacion;
	private String cola;

	@Autowired
	ServicioEnvioData servicioEnvioData;

	public String listar() {
		try {
			ResultadoEnvioDataBean resultado = servicioEnvioData.listarEnvioData(ConstantesProcesos.EJECUCION_CORRECTA);
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			} else {
				listaEnvioData = resultado.getListaEnvioData();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de envio data, error: ", e);
			addActionError("Ha sucedido un error a la hora de obtener la lista de envio data.");
		}
		return LISTA_ENVIO_DATA;
	}

	public String actualizarFecha(){
		try {
			ResultadoEnvioDataBean resultado = servicioEnvioData.actualizarFechaEnvioData(proceso, tipoActualizacion, cola);
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			listaEnvioData = resultado.getListaEnvioData();
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la fecha del envio data, error: ", e);
			addActionError("Ha sucedido un error a la hora de actualizar la fecha del envio data.");
		}
		return LISTA_ENVIO_DATA;
	}

	public List<EnvioDataDto> getListaEnvioData() {
		return listaEnvioData;
	}

	public void setListaEnvioData(List<EnvioDataDto> listaEnvioData) {
		this.listaEnvioData = listaEnvioData;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getTipoActualizacion() {
		return tipoActualizacion;
	}

	public void setTipoActualizacion(String tipoActualizacion) {
		this.tipoActualizacion = tipoActualizacion;
	}

	public String getCola() {
		return cola;
	}

	public void setCola(String cola) {
		this.cola = cola;
	}

}