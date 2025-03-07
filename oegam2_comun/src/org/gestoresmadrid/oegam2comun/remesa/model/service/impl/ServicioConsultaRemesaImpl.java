package org.gestoresmadrid.oegam2comun.remesa.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioConsultaRemesa;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioRemesas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

@Service
public class ServicioConsultaRemesaImpl implements ServicioConsultaRemesa{

	private static final long serialVersionUID = 7164099637702750468L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaRemesaImpl.class);

	@Autowired
	private ServicioRemesas servicioRemesas;
	
	@Override
	public ResultBean cambiarEstadoBloque(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				if(estadoNuevo != null && !estadoNuevo.isEmpty()){
					List<String> listaErrores = new ArrayList<String>();
					List<String> listaOk = new ArrayList<String>();
					int numCambEstadoOk600 = 0;
					int numCambEstadoOk601 = 0;
					int numCambEstadoError600 = 0;
					int numCambEstadoError601 = 0;
					String[] numExpedientes = codSeleccionados.split("-");
					for(String numExp : numExpedientes){
						ResultBean resultEstado = servicioRemesas.cambiarEstado(new BigDecimal(numExp), new BigDecimal(estadoNuevo), idUsuario,true);
						String tipoRemesa = (String) resultEstado.getAttachment("tipoRemesa");
						if(tipoRemesa == null || tipoRemesa.isEmpty()){
							return resultEstado;
						}
						if(!resultEstado.getError()){
							if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
								numCambEstadoOk600++;
							}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
								numCambEstadoOk601++;
							}
							listaOk.add("Remesa " + numExp + " cambiada de estado correctamente.");
						}else{
							if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
								numCambEstadoError600++;
							}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
								numCambEstadoError601++;
							}
							listaErrores.add("Remesa " + numExp + " fallo en el cambio de estado:" + resultEstado.getListaMensajes().get(0));
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
					resultado.addMensajeALista("Debe seleccionar un estado nuevo para las remesas.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar alguna remesa para cambiar el estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de las remesas,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de cambiar el estado de las remesas.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean anularRemesaPorBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				List<String> listaErrores = new ArrayList<String>();
				List<String> listaOk = new ArrayList<String>();
				int numElimOk600 = 0;
				int numElimOk601 = 0;
				int numElimError600 = 0;
				int numElimError601 = 0;
				String[] numExpedientes = codSeleccionados.split("-");
				for(String numExp : numExpedientes){
					ResultBean resultEliminado = servicioRemesas.cambiarEstado(new BigDecimal(numExp), new BigDecimal(EstadoRemesas.Anulada.getValorEnum()), idUsuario,true);
					String tipoRemesa = (String) resultEliminado.getAttachment("tipoRemesa");
					if(tipoRemesa == null || tipoRemesa.isEmpty()){
						return resultEliminado;
					}
					if(!resultEliminado.getError()){
						if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
							numElimOk600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
							numElimOk601++;
						}
						listaOk.add("Remesa " + numExp + " eliminada.");
					}else{
						if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
							numElimError600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
							numElimError601++;
						}
						listaErrores.add("Remesa " + numExp + " fallo al eliminarla:" + resultEliminado.getListaMensajes().get(0));
					}
				}
				resultado.addAttachment("numOk600", numElimOk600);
				resultado.addAttachment("numOk601", numElimOk601);
				resultado.addAttachment("numErrores600", numElimError600);
				resultado.addAttachment("numErrores601", numElimError601);
				resultado.addAttachment("listaErrores", listaErrores);
				resultado.addAttachment("listaOk", listaOk);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar alguna remesa para eliminar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar las remesas,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de eliminar las remesas.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean generarModelosRemesasBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				List<String> listaErrores = new ArrayList<String>();
				List<String> listaOk = new ArrayList<String>();
				int numGenerarOk600 = 0;
				int numGenerarOk601 = 0;
				int numGenerarError600 = 0;
				int numGenerarError601 = 0;
				String[] numExpedientes = codSeleccionados.split("-");
				for(String numExp : numExpedientes){
					ResultBean resultModelo = servicioRemesas.generarModelo(new BigDecimal(numExp), idUsuario);
					String tipoRemesa = (String) resultModelo.getAttachment("tipoRemesa");
					if(tipoRemesa == null || tipoRemesa.isEmpty()){
						return resultModelo;
					}
					if(!resultModelo.getError()){
						if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
							numGenerarOk600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
							numGenerarOk601++;
						}
						listaOk.add("Los modelos de la Remesa " + numExp + " se han generado correctamente.");
					}else{
						if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
							numGenerarError600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
							numGenerarError601++;
						}
						for(String mensaje : resultModelo.getListaMensajes()){
							listaErrores.add("Remesa " + numExp + " fallo al generar los modelos:" + mensaje);
						}
						
					}
				}
				resultado.addAttachment("numOk600", numGenerarOk600);
				resultado.addAttachment("numOk601", numGenerarOk601);
				resultado.addAttachment("numErrores600", numGenerarError600);
				resultado.addAttachment("numErrores601", numGenerarError601);
				resultado.addAttachment("listaErrores", listaErrores);
				resultado.addAttachment("listaOk", listaOk);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar alguna remesa para poder generar los modelos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los modelos de las remesas,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de generar los modelos de las remesas.");
		}
		return resultado;
	}
	
	@Override
	public ResultBean validarRemesaPorBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numExpedientes = codSeleccionados.split("-");
				List<String> listaErrores = new ArrayList<String>();
				List<String> listaOk = new ArrayList<String>();
				int numValidas600 = 0;
				int numValidas601 = 0;
				int numErrores600 = 0;
				int numErrores601 = 0;
				for(String numExp : numExpedientes){
					ResultBean resultValidar = servicioRemesas.validarRemesa(new BigDecimal(numExp), idUsuario);
					String tipoRemesa = (String) resultValidar.getAttachment("tipoRemesa");
					if(tipoRemesa == null || tipoRemesa.isEmpty()){
						return resultValidar;
					}
					if(resultValidar.getError()){
						if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
							numErrores600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
							numErrores601++;
						}
						for(String mensaje : resultValidar.getListaMensajes()){
							listaErrores.add("Remesa " + numExp + " fallo en la validación: " + mensaje);
						}
					}else{
						if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
							numValidas600++;
						}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
							numValidas601++;
						}
							listaOk.add("Remesa " + numExp + " validada correctamente.");
						}
				}
				resultado.addAttachment("listaErrores", listaErrores);
				resultado.addAttachment("listaOk", listaOk);
				resultado.addAttachment("numOk600", numValidas600);
				resultado.addAttachment("numOk601", numValidas601);
				resultado.addAttachment("numErrores600", numErrores600);
				resultado.addAttachment("numErrores601", numErrores601);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe seleccionar alguna remesa para validar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar las remesas,error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de validar las remesas.");
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultBean presentarEnBloque(String codSeleccionados, DatosBancariosFavoritosDto datosBancarios,	BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				if(datosBancarios != null){
					String[] numExpedientes = codSeleccionados.split("-");
					List<String> listaErrores = new ArrayList<String>();
					List<String> listaOk = new ArrayList<String>();
					int numPresOk600 = 0;
					int numPresOk601 = 0;
					int numPresError600 = 0;
					int numPresError601 = 0;
					for(String numExp : numExpedientes){
						RemesaVO remesaBBDD = servicioRemesas.getRemesaVoPorExpediente(new BigDecimal(numExp), true);
						if(remesaBBDD != null){
							ResultBean resultPresentar = servicioRemesas.presentarRemesas(remesaBBDD, datosBancarios, idUsuario);
							if((String) resultPresentar.getAttachment("tipoRemesa") == null){
								return resultPresentar;
							}
							String tipoRemesa = (String) resultPresentar.getAttachment("tipoRemesa");
							if(resultPresentar.getError()){
								if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
									numPresError600++;
								}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
									numPresError601++;
								}
								for(String mensaje : resultPresentar.getListaMensajes()){
									listaErrores.add(mensaje);
								}
							}else{
								int numOkMD = (Integer) resultPresentar.getAttachment("numOkMD");
								int numErrorMD = (Integer) resultPresentar.getAttachment("numErrorMD");
								List<String> listaOkMD = (List<String>) resultPresentar.getAttachment("listaOk");
								List<String> listaErroresMD = (List<String>) resultPresentar.getAttachment("listaErrores");
								if(numOkMD > 0){
									if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
										numPresOk600++;
									}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
										numPresOk601++;
									}
									aniadirMensajeLista(listaOk,listaOkMD);
								}
								if(numErrorMD > 0){
									if(Modelo.Modelo600.getValorEnum().equals(tipoRemesa)){
										numPresError600++;
									}else if(Modelo.Modelo601.getValorEnum().equals(tipoRemesa)){
										numPresError601++;
									}
									aniadirMensajeLista(listaErrores,listaErroresMD);
								}
							}
						}else {
							resultado.setError(true);
							resultado.addMensajeALista("Para la remesa " + numExp + " no existen datos para poder realizar la presentación.");
							break;
						}
					}
					resultado.addAttachment("numOk600", numPresOk600);
					resultado.addAttachment("numOk601", numPresOk601);
					resultado.addAttachment("numErrores600", numPresError600);
					resultado.addAttachment("numErrores601", numPresError601);
					resultado.addAttachment("listaOk", listaOk);
					resultado.addAttachment("listaErrores", listaErrores);
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("Debe de rellenar los datos bancarios para poder realizar la presentación.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe de seleccioanr alguna remesa para presentar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de presentar en bloque las remesas, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de presentar en bloque las remesas.");
		}
		return resultado;
	}

	private void aniadirMensajeLista(List<String> listaDestino, List<String> lista) {
		for(String mensaje : lista){
			listaDestino.add(mensaje);
		}
	}
	
	@Override
	public ResultBean consultarEstadoRemesa(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultRemesa = servicioRemesas.esEstadoAnulado(numExpediente);
			if(!resultRemesa.getError()){
				Boolean esAnulado = (Boolean) resultRemesa.getAttachment("esAnulado");
				if(esAnulado){
					resultado.setError(true);
					resultado.addMensajeALista("La remesa con número de expediente: " + numExpediente + ", se encuentra en Estado Anulado con lo que no se puede proceder a realizar ninguna acción con el.");
				}
			}else{
				return resultRemesa;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar el estado de la remesa, error: ",e, numExpediente.toString());
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de comprobar el estado de la remesa.");
		}
		return resultado;
	}
}
