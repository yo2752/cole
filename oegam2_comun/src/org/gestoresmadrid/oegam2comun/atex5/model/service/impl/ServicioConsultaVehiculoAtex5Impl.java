package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.enumerados.TipoTramiteAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioConsultaVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaVehiculoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaVehiculoAtex5Impl implements ServicioConsultaVehiculoAtex5 {

	private static final long serialVersionUID = -2685596698335184554L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaVehiculoAtex5Impl.class);

	@Autowired
	private ServicioVehiculoAtex5 servicioVehiculoAtex5;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public List<ConsultaVehiculoAtex5Bean> convertirListaEnBeanPantalla(List<ConsultaVehiculoAtex5VO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<ConsultaVehiculoAtex5Bean> listaBean = new ArrayList<ConsultaVehiculoAtex5Bean>();
			for (ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO : lista) {
				ConsultaVehiculoAtex5Bean consultaVehiculoAtex5Bean = conversor.transform(consultaVehiculoAtex5VO, ConsultaVehiculoAtex5Bean.class);
				consultaVehiculoAtex5Bean.setEstado(EstadoAtex5.convertirEstadoBigDecimal(consultaVehiculoAtex5VO.getEstado()));
				listaBean.add(consultaVehiculoAtex5Bean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoAtex5Bean cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean();
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultadoAtex5Bean resultCambioEstado = servicioVehiculoAtex5.cambiarEstado(new BigDecimal(numExpediente), idUsuario, new BigDecimal(estadoNuevo), Boolean.TRUE, TipoTramiteAtex5.Consulta_Vehiculo.getValorEnum(), ConceptoCreditoFacturado.DVX5);
				if (resultCambioEstado.getError()) {
					resultado.addError();
					resultado.addListaError(resultCambioEstado.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(resultCambioEstado.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para cambiar su estado.");
		}
		return resultado;
	}
	

	@Override
	public ResultadoAtex5Bean asignarTasa(String codSeleccionados,String codigoTasa, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean();
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			resultado = servicioVehiculoAtex5.validarTasasVehiculoAtex5(sNumExpedientes);
			if(!resultado.getError()){
				ResultadoAtex5Bean resultCambioEstado = servicioVehiculoAtex5.asignarTasa(sNumExpedientes, codigoTasa, idUsuario,TipoTramiteAtex5.Consulta_Vehiculo.getValorEnum());
					if (resultCambioEstado.getError()) {
						resultado.addError();
						resultado.addListaError(resultCambioEstado.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk(resultCambioEstado.getMensaje());
					}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultado.getMensaje());
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar los expedientes para asignar la tasa.");
		}
		return resultado;
	}
	@Override
	public ResultadoAtex5Bean desasignarTasa(String numExpediente, String idCodigoTasa,BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		if(numExpediente != null && !numExpediente.isEmpty()){
			String[] nNumExpediente = numExpediente.split("-");
			resultado = servicioVehiculoAtex5.validarDesasignarTasasVehiculoAtex5(nNumExpediente);
			if(!resultado.getError()){
				ResultadoAtex5Bean result = servicioVehiculoAtex5.desasignarTasa(nNumExpediente,resultado.getListaTasa(),idUsuario);
				if(result.getError()){
					resultado.addError();
					resultado.addListaError(result.getMensaje());
				}else{
					resultado.addOk();
					resultado.addListaOk(result.getMensaje());
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultado.getMensaje());
			}
		}else{
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar los expedientes apra desasignar la tasa.");
		}
		
		return resultado;
	}

	@Override
	public ResultadoAtex5Bean eliminar(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean();
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultadoAtex5Bean resultCambioEstado = servicioVehiculoAtex5.cambiarEstado(new BigDecimal(numExpediente), idUsuario, new BigDecimal(EstadoAtex5.Anulado.getValorEnum()), Boolean.TRUE, TipoTramiteAtex5.Consulta_Vehiculo.getValorEnum(), ConceptoCreditoFacturado.DVX5);
				if (resultCambioEstado.getError()) {
					resultado.addError();
					resultado.addListaError(resultCambioEstado.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(resultCambioEstado.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para eliminar.");
		}
		return resultado;
	}

	@Override
	public ResultadoAtex5Bean consultar(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean();
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultadoAtex5Bean resultGetConsulta = servicioVehiculoAtex5.getConsultaVehiculoAtex5(new BigDecimal(numExpediente));
				if (resultGetConsulta.getError()) {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				} else {
					ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto = resultGetConsulta.getConsultaVehiculoAtex5Dto();
					ResultadoAtex5Bean resultConsultar = servicioVehiculoAtex5.consultarVehiculoAtex5(consultaVehiculoAtex5Dto, idUsuario);
					if (resultConsultar.getError()) {
						resultado.addError();
						resultado.addListaError("La consulta vehiculo atex5 con numExpediente: " + consultaVehiculoAtex5Dto.getNumExpediente() + ", ha fallado por la siguiente razon: "
								+ resultConsultar.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk("La consulta vehiculo atex5 con numExpediente: " + consultaVehiculoAtex5Dto.getNumExpediente() + ", se encuentra "
								+ EstadoAtex5.Pdte_Respuesta_DGT.getNombreEnum() + ".");
					}
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para realizar su consulta.");
		}
		return resultado;
	}

	@Override
	public ResultadoAtex5Bean imprimirPdf(String codSeleccionados) {
		ResultadoAtex5Bean resultadoAtex5Bean = new ResultadoAtex5Bean(Boolean.FALSE);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] sNumsExpedientes = codSeleccionados.split("-");
				if(sNumsExpedientes.length == 1){
					return servicioVehiculoAtex5.descargarPdf(new BigDecimal(sNumsExpedientes[0]));
				} else {
					url = gestorPropiedades.valorPropertie(ServicioVehiculoAtex5.RUTA_FICH_TEMP) + "zip" + System.currentTimeMillis();
					out = new FileOutputStream(url);
					zip = new ZipOutputStream(out);
					for (String numExpediente : sNumsExpedientes) {
						resultadoAtex5Bean = servicioVehiculoAtex5.descargarPdf(new BigDecimal(numExpediente));
						if (!resultadoAtex5Bean.getError()) {
								ZipEntry zipEntry = new ZipEntry(resultadoAtex5Bean.getNombreFichero());
								zip.putNextEntry(zipEntry);
								zip.write(gestorDocumentos.transformFiletoByte(resultadoAtex5Bean.getFicheroDescarga()));
								zip.closeEntry();
						} else {
							break;
						}
					}
					zip.close();
					if(!resultadoAtex5Bean.getError()){
						File fichero = new File(url);
						resultadoAtex5Bean.setEsZip(Boolean.TRUE);
						resultadoAtex5Bean.setNombreFichero(ServicioVehiculoAtex5.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
						resultadoAtex5Bean.setFicheroDescarga(fichero);
						if (fichero.delete()) {
							log.info("Se ha eliminado correctamente el fichero");
						} else {
							log.info("No se ha eliminado el fichero");
						}
					}
				}
			} else {
				resultadoAtex5Bean.setError(Boolean.TRUE);
				resultadoAtex5Bean.setMensaje("Debe seleccionar alguna consulta para poder realizar la descarga.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar los xml de la consulta, error: ", e);
			resultadoAtex5Bean.setError(Boolean.TRUE);
			resultadoAtex5Bean.setMensaje("Ha sucedido un error a la hora de descargar los xml de la consulta.");
		}
		return resultadoAtex5Bean;
	}

	@Override
	public void borrarFichero(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

}
