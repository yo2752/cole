package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ConsultaEEFFBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoConsultaEEFF;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaEEFFImpl implements ServicioConsultaEEFF{

	private static final long serialVersionUID = 4294639736774577112L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaEEFFImpl.class);
	
	@Autowired
	ServicioEEFFNuevo servicioEEFF;
	
	@Autowired
	Conversor conversor;
	
	@Override
	public List<ConsultaEEFFBean> convertirListaEnBeanPantalla(List<ConsultaEEFFVO> lista) {
		if(lista != null && !lista.isEmpty()){
			List<ConsultaEEFFBean> listaBean =  new ArrayList<ConsultaEEFFBean>();
			for(ConsultaEEFFVO consultaEEFFVO : lista){
				ConsultaEEFFBean consultaEEFFBean = conversor.transform(consultaEEFFVO, ConsultaEEFFBean.class);
				if(consultaEEFFBean.getRespuesta() != null && !consultaEEFFBean.getRespuesta().isEmpty()
					&& consultaEEFFBean.getRespuesta().substring(0, 4).equals("EEFF")){
					consultaEEFFBean.setRespuesta("ERROR");
				}
				if(consultaEEFFBean.getEstado() != null){
					consultaEEFFBean.setDescEstado(EstadoConsultaEEFF.convertirEstadoBigDecimal(consultaEEFFBean.getEstado()));
				}
				listaBean.add(consultaEEFFBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}
	
	@Override
	public ResultadoConsultaEEFF consultarBloque(String numsExpedientes, BigDecimal idUsuario) {
		ResultadoConsultaEEFF resultadoConsultaEEFF = new ResultadoConsultaEEFF();
		if(numsExpedientes != null && !numsExpedientes.isEmpty()){
			String[] numsExp = numsExpedientes.split("_");
			for(String numExp : numsExp){
				try {
					ResultBean resultConsulta = servicioEEFF.consultarEEFF(new BigDecimal(numExp), idUsuario,Boolean.TRUE);
					if(resultConsulta.getError()){
						resultadoConsultaEEFF.addError();
						resultadoConsultaEEFF.addListaError("Para la consulta EEFF: " + numExp + " se han encontrado los siguientes errores: ");
						for(String mensaje : resultConsulta.getListaMensajes()){
							resultadoConsultaEEFF.addListaError(mensaje);
						}
					} else {
						resultadoConsultaEEFF.addOk();
						resultadoConsultaEEFF.addListaOk("La consulta EEFF con número de expediente: " + numExp + ", se ha encolado correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de realizar la consulta EEFF para el expediente: " + numExp + ", error: ",e, numExp);
					resultadoConsultaEEFF.addError();
					resultadoConsultaEEFF.addListaError("Ha sucedido un error a la hora de realizar la consulta EEFF para el expediente: " + numExp);
				}
			}
		} else {
			resultadoConsultaEEFF.setError(Boolean.TRUE);
			resultadoConsultaEEFF.setMensaje("Debe de seleccionar alguna consulta EEFF.");
		}
		return resultadoConsultaEEFF;
	}

	@Override
	public ResultadoConsultaEEFF cambiarEstadoBloque(String codSeleccionados, String estado, BigDecimal idUsuario) {
		ResultadoConsultaEEFF resultadoConsultaEEFF = new ResultadoConsultaEEFF();
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] numsExpedientes = codSeleccionados.split("_");
			for(String numExp : numsExpedientes){
				try {
					ResultBean resultCambEstado = servicioEEFF.cambiarEstadoConsultaEEFF(new BigDecimal(numExp), EstadoConsultaEEFF.convertir(estado), idUsuario, Boolean.TRUE);
					if(resultCambEstado.getError()){
						resultadoConsultaEEFF.addError();
						resultadoConsultaEEFF.addListaError(resultCambEstado.getMensaje());
					} else {
						resultadoConsultaEEFF.addOk();
						resultadoConsultaEEFF.addListaOk("La consulta EEFF con número de expediente: " + numExp + ", se ha modificado correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta EEFF: " + numExp + ", error: ",e, numExp);
					resultadoConsultaEEFF.addError();
					resultadoConsultaEEFF.addListaError("Ha sucedido un error a la hora de cambiar el estado de la consulta EEFF: " + numExp);
				}
			}
		} else {
			resultadoConsultaEEFF.setError(Boolean.TRUE);
			resultadoConsultaEEFF.setMensaje("Debe de seleccionar alguna consulta EEFF.");
		}
		return resultadoConsultaEEFF;
	}
	
	@Override
	public ResultadoConsultaEEFF eliminar(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoConsultaEEFF resultadoConsultaEEFF = new ResultadoConsultaEEFF();
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] numsExpedientes = codSeleccionados.split("_");
			for(String numExp : numsExpedientes){
				try {
					ResultBean resultEliminar = servicioEEFF.cambiarEstadoConsultaEEFF(new BigDecimal(numExp), EstadoConsultaEEFF.Anulada, idUsuario, Boolean.TRUE);
					if(resultEliminar.getError()){
						resultadoConsultaEEFF.addError();
						resultadoConsultaEEFF.addListaError(resultEliminar.getMensaje());
					} else {
						resultadoConsultaEEFF.addOk();
						resultadoConsultaEEFF.addListaOk("La consulta EEFF con número de expediente: " + numExp + ", se ha anulado correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de anular la consulta EEFF: " + numExp + ", error: ",e, numExp);
					resultadoConsultaEEFF.addError();
					resultadoConsultaEEFF.addListaError("Ha sucedido un error a la hora de anular la consulta EEFF: " + numExp);
				}
			}
		} else {
			resultadoConsultaEEFF.setError(Boolean.TRUE);
			resultadoConsultaEEFF.setMensaje("Debe de seleccionar alguna consulta EEFF.");
		}
		return resultadoConsultaEEFF;
	}
	
}
