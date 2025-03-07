package org.gestoresmadrid.oegam2comun.modelo600_601.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioConsultaModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaModelo600_601Impl implements ServicioConsultaModelo600_601{

	private static final long serialVersionUID = 8546199454535843406L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaModelo600_601Impl.class);

	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;

	@Override
	public ResultBean autoLiquidarModelosEnBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				List<String> listaErrores = new ArrayList<>();
				List<String> listaOk = new ArrayList<>();
				int numAutoLiqOk600 = 0;
				int numAutoLiqOk601 = 0;
				int numAutoLiqError600 = 0;
				int numAutoLiqError601 = 0;
				String[] numExpedientes = codSeleccionados.split("-");
				for(String numExp : numExpedientes){
					ResultBean resultAutoLiq = servicioModelo600_601.autoLiquidarModelo(new BigDecimal(numExp), idUsuario,null);
					String tipoModelo = (String) resultAutoLiq.getAttachment("tipoModelo");
					if(tipoModelo == null || tipoModelo.isEmpty()){
						return resultAutoLiq;
					}
					if(resultAutoLiq.getError()){
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numAutoLiqError600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numAutoLiqError601++;
						}
						for(String mensaje : resultAutoLiq.getListaMensajes()){
							listaErrores.add("Modelo " + numExp + " fallo al autoliquidar el modelo:" + mensaje);
						}
					}else{
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numAutoLiqOk600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numAutoLiqOk601++;
						}
						listaOk.add("Modelo " + numExp + "  autoliquidado correctamente.");
					}
				}
				resultado.addAttachment("numOk600", numAutoLiqOk600);
				resultado.addAttachment("numOk601", numAutoLiqOk601);
				resultado.addAttachment("numErrores600", numAutoLiqError600);
				resultado.addAttachment("numErrores601", numAutoLiqError601);
				resultado.addAttachment("listaErrores", listaErrores);
				resultado.addAttachment("listaOk", listaOk);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar algún modelo para poder autoliquidar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autoliquidar los modelo,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de autoliquidar los modelos.");
		}
		return resultado;
	}

	@Override
	public ResultBean cambiarEstadoBloque(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				if(estadoNuevo != null && !estadoNuevo.isEmpty()){
					List<String> listaErrores = new ArrayList<>();
					List<String> listaOk = new ArrayList<>();
					int numCambEstadoOk600 = 0;
					int numCambEstadoOk601 = 0;
					int numCambEstadoError600 = 0;
					int numCambEstadoError601 = 0;
					String[] numExpedientes = codSeleccionados.split("-");
					for(String numExp : numExpedientes){
						ResultBean resultCambiarEstado = servicioModelo600_601.cambiarEstado(new BigDecimal(numExp), new BigDecimal(estadoNuevo), idUsuario,true);
						String tipoModelo = (String) resultCambiarEstado.getAttachment("tipoModelo");
						if(tipoModelo == null || tipoModelo.isEmpty()){
							return resultCambiarEstado;
						}
						if(resultCambiarEstado.getError()){
							if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
								numCambEstadoError600++;
							}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
								numCambEstadoError601++;
							}
							for(String mensaje : resultCambiarEstado.getListaMensajes()){
								listaErrores.add("Modelo " + numExp + " fallo en el cambio de estado:" + mensaje);
							}
						}else{
							if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
								numCambEstadoOk600++;
							}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
								numCambEstadoOk601++;
							}
							listaOk.add("Modelo " + numExp + "  cambiado de estado correctamente.");
						}
					}
					resultado.addAttachment("numOk600", numCambEstadoOk600);
					resultado.addAttachment("numOk601", numCambEstadoOk601);
					resultado.addAttachment("numErrores600", numCambEstadoError600);
					resultado.addAttachment("numErrores601", numCambEstadoError601);
					resultado.addAttachment("listaErrores", listaErrores);
					resultado.addAttachment("listaOk", listaOk);
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("Debe seleccionar un estado nuevo para los modelos.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar algún modelo para cambiar el estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de los modelo,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de cambiar el estado de los modelos.");
		}
		return resultado;
	}

	@Override
	public ResultBean presentarEnBloque(String codSeleccionados, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario, File fichero) {
		ResultBean resultado = new ResultBean(false);
		ResultBean resultPresentar = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numExpedientes = codSeleccionados.split("-");
				List<String> listaErrores = new ArrayList<>();
				List<String> listaOk = new ArrayList<>();
				int numPresOk600 = 0;
				int numPresOk601 = 0;
				int numPresError600 = 0;
				int numPresError601 = 0;
				for(String numExp : numExpedientes){
					if (fichero!=null){
						resultPresentar = servicioModelo600_601.presentar(new BigDecimal(numExp),datosBancarios,idUsuario, fichero, numExp+".pdf");
					}else{
						resultPresentar = servicioModelo600_601.presentar(new BigDecimal(numExp),datosBancarios,idUsuario, null, null);
					}
					String tipoModelo = (String) resultPresentar.getAttachment("tipoModelo");
					if(tipoModelo == null || tipoModelo.isEmpty()){
						return resultPresentar;
					}
					if(resultPresentar.getError()){
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numPresError600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numPresError601++;
						}
						for(String mensaje : resultPresentar.getListaMensajes()){
							listaErrores.add(mensaje);
						}
					}else{
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numPresOk600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numPresOk601++;
						}
						listaOk.add("Solicitud de presentación del modelo " + numExp + " generada.");
					}
				}
				resultado.addAttachment("numOk600", numPresOk600);
				resultado.addAttachment("numOk601", numPresOk601);
				resultado.addAttachment("numErrores600", numPresError600);
				resultado.addAttachment("numErrores601", numPresError601);
				resultado.addAttachment("listaErrores", listaErrores);
				resultado.addAttachment("listaOk", listaOk);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe de seleccioanr algún modelo para presentar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de presentar los modelos, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de presentar los modelos.");
		}
		return resultado;
	}

	@Override
	public ResultBean validarModeloEnBloque(String codSeleccionados, BigDecimal idUsuarioDeSesion) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numExpedientes = codSeleccionados.split("-");
				List<String> listaErrores = new ArrayList<>();
				List<String> listaOk = new ArrayList<>();
				int numValidas600 = 0;
				int numValidas601 = 0;
				int numErrores600 = 0;
				int numErrores601 = 0;
				for(String numExp : numExpedientes){
					ResultBean resultValidar = servicioModelo600_601.validarModelo(new BigDecimal(numExp),idUsuarioDeSesion);
					String tipoModelo = (String) resultValidar.getAttachment("tipoModelo");
					if(tipoModelo == null || tipoModelo.isEmpty()){
						return resultValidar;
					}
					if(resultValidar.getError()){
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numErrores600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numErrores601++;
						}
						for(String mensaje : resultValidar.getListaMensajes()){
							listaErrores.add("Modelo " + numExp + " fallo en la validación:" + mensaje);
						}
					}else{
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numValidas600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numValidas601++;
						}
						listaOk.add("Modelo " + numExp + " validado correctamente.");
					}
				}
				resultado.addAttachment("listaErrores", listaErrores);
				resultado.addAttachment("listaOk", listaOk);
				resultado.addAttachment("numOk600", numValidas600);
				resultado.addAttachment("numOk601", numValidas601);
				resultado.addAttachment("numErrores600", numErrores600);
				resultado.addAttachment("numErrores601", numErrores601);
			}else {
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar algún modelo para validar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los modelos,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de validar los modelos.");
		}
		return resultado;
	}

	@Override
	public ResultBean consultarEstadoAnuladoModelo(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultModelo = servicioModelo600_601.esEstadoAnulado(numExpediente);
			if(!resultModelo.getError()){
				Boolean esAnulado = (Boolean) resultModelo.getAttachment("esAnulado");
				if(esAnulado){
					resultado.setError(true);
					resultado.addMensajeALista("El modelo con número de expediente: " + numExpediente + ", se encuentra en Estado Anulado con lo que no se puede proceder a realizar ninguna acción con el.");
				}
			}else{
				return resultModelo;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el estado del modelo, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar el estado del modelo.");
		}
		return resultado;
	}

	@Override
	public ResultBean anularModelosEnBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numExpedientes = codSeleccionados.split("-");
				List<String> listaErrores = new ArrayList<>();
				List<String> listaOk = new ArrayList<>();
				int numAnuladas600 = 0;
				int numAnuladas601 = 0;
				int numErroresAnul600 = 0;
				int numErroresAnul601 = 0;
				for(String numExp : numExpedientes){
					ResultBean resultAnular = servicioModelo600_601.cambiarEstado(new BigDecimal(numExp), new BigDecimal(EstadoModelos.Anulado.getValorEnum()), idUsuario,true);
					String tipoModelo = (String) resultAnular.getAttachment("tipoModelo");
					if(tipoModelo == null || tipoModelo.isEmpty()){
						return resultAnular;
					}
					if(resultAnular.getError()){
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numErroresAnul600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numErroresAnul601++;
						}
						for(String mensaje : resultAnular.getListaMensajes()){
							listaErrores.add("El modelo " + numExp + " no se ha podido eliminar por el siguiente error: " + mensaje);
						}
					}else{
						if(Modelo.Modelo600.getValorEnum().equals(tipoModelo)){
							numAnuladas600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoModelo)){
							numAnuladas601++;
						}
						listaOk.add("El modelo " + numExp + " se ha eliminado correctamente.");
					}
				}
				resultado.addAttachment("numOk600", numAnuladas600);
				resultado.addAttachment("numOk601", numAnuladas601);
				resultado.addAttachment("numErrores600", numErroresAnul600);
				resultado.addAttachment("numErrores601", numErroresAnul601);
				resultado.addAttachment("listaErrores", listaErrores);
				resultado.addAttachment("listaOk", listaOk);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe de seleccioanr algún modelo para eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los modelos en bloque, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los modelos en bloque.");
		}
		return resultado;
	}
}