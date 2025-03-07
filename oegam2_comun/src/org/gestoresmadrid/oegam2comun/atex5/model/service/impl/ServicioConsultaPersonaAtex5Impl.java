package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioConsultaPersonaAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioPersonaAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ConsultaPersonaAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaPersonaAtex5Dto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaPersonaAtex5Impl implements ServicioConsultaPersonaAtex5 {

	private static final long serialVersionUID = 7169438353650430138L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaPersonaAtex5Impl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioPersonaAtex5 servicioPersonaAtex5;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public List<ConsultaPersonaAtex5Bean> convertirListaEnBeanPantalla(List<ConsultaPersonaAtex5VO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<ConsultaPersonaAtex5Bean> listaBean = new ArrayList<ConsultaPersonaAtex5Bean>();
			for (ConsultaPersonaAtex5VO consultaPersonaAtex5VO : lista) {
				ConsultaPersonaAtex5Bean consultaPersonaAtex5Bean = conversor.transform(consultaPersonaAtex5VO, ConsultaPersonaAtex5Bean.class);
				consultaPersonaAtex5Bean.setEstado(EstadoAtex5.convertirEstadoBigDecimal(consultaPersonaAtex5VO.getEstado()));
				if (consultaPersonaAtex5VO.getContrato() != null && consultaPersonaAtex5VO.getContrato().getProvincia() != null) {
					consultaPersonaAtex5Bean.setDescContrato(consultaPersonaAtex5VO.getContrato().getProvincia().getNombre() + " - " + consultaPersonaAtex5VO.getContrato().getVia());
				}
				listaBean.add(consultaPersonaAtex5Bean);
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
				ResultadoAtex5Bean resultCambioEstado = servicioPersonaAtex5.cambiarEstado(new BigDecimal(numExpediente), idUsuario, new BigDecimal(estadoNuevo), Boolean.TRUE);
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
	public ResultadoAtex5Bean eliminar(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean();
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultadoAtex5Bean resultCambioEstado = servicioPersonaAtex5.cambiarEstado(new BigDecimal(numExpediente), idUsuario, new BigDecimal(EstadoAtex5.Anulado.getValorEnum()), Boolean.TRUE);
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
				ResultadoAtex5Bean resultGetConsulta = servicioPersonaAtex5.getConsultaPersonaAtex5(new BigDecimal(numExpediente));
				if (resultGetConsulta.getError()) {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				} else {
					ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto = resultGetConsulta.getConsultaPersonaAtex5Dto();
					ResultadoAtex5Bean resultConsultar = servicioPersonaAtex5.consultarPersonaAtex5(consultaPersonaAtex5Dto, idUsuario);
					if (resultConsultar.getError()) {
						resultado.addError();
						resultado.addListaError("La consulta persona atex5 con numExpediente: " + consultaPersonaAtex5Dto.getNumExpediente() + ", ha fallado por la siguiente razon: "
								+ resultConsultar.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk("La consulta persona atex5 con numExpediente: " + consultaPersonaAtex5Dto.getNumExpediente() + ", se encuentra "
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
	public ResultadoAtex5Bean imprimirPdfs(String codSeleccionados) {
		ResultadoAtex5Bean resultadoAtex5Bean = new ResultadoAtex5Bean();
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] sNumsExpedientes = codSeleccionados.split("-");
				Boolean esZip = sNumsExpedientes.length > 1;
				if (esZip) {
					url = gestorPropiedades.valorPropertie(ServicioPersonaAtex5.RUTA_FICH_TEMP) + "zip" + System.currentTimeMillis();
					out = new FileOutputStream(url);
					zip = new ZipOutputStream(out);
				}
				for (String numExpediente : sNumsExpedientes) {
					ResultadoAtex5Bean resultGetConsulta = servicioPersonaAtex5.getConsultaPersonaAtex5(new BigDecimal(numExpediente));
					if (resultGetConsulta.getError()) {
						resultadoAtex5Bean.setError(Boolean.TRUE);
						resultadoAtex5Bean.setMensaje(resultGetConsulta.getMensaje());
						break;
					} else {
						ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto = resultGetConsulta.getConsultaPersonaAtex5Dto();
						if (consultaPersonaAtex5Dto != null) {
							ResultadoAtex5Bean resultDescargar = servicioPersonaAtex5.imprimirPdf(consultaPersonaAtex5Dto.getNumExpediente());
							if (resultDescargar.getError()) {
								resultadoAtex5Bean.setError(Boolean.TRUE);
								resultadoAtex5Bean.setMensaje(resultDescargar.getMensaje());
								break;
							} else {
								if (esZip) {
									ZipEntry zipEntry = new ZipEntry(resultDescargar.getNombreFichero());
									zip.putNextEntry(zipEntry);
									zip.write(gestorDocumentos.transformFiletoByte(resultDescargar.getFicheroDescarga()));
									zip.closeEntry();
								} else {
									resultadoAtex5Bean.setNombreFichero(resultDescargar.getNombreFichero());
									resultadoAtex5Bean.setFicheroDescarga(resultDescargar.getFicheroDescarga());
								}
							}
						} else {
							resultadoAtex5Bean.setError(Boolean.TRUE);
							resultadoAtex5Bean.setMensaje("No se ha encontrado el tramite de consulta de persona Atex5.");
							break;
						}
					}
				}
				if (esZip && !resultadoAtex5Bean.getError()) {
					zip.close();
					File fichero = new File(url);
					resultadoAtex5Bean.setNombreFichero(ServicioPersonaAtex5.NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
					resultadoAtex5Bean.setFicheroDescarga(fichero);
					if (fichero.delete()) {
						log.info("Se ha eliminado correctamente el fichero");
					} else {
						log.info("No se ha eliminado el fichero");
					}
				} else {
					if(esZip && zip != null){
						zip.close();
						File fichero = new File(url);
						fichero.delete();
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
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				File eliminarZip = new File(url);
				eliminarZip.delete();
			}
		}
		return resultadoAtex5Bean;
	}

}
