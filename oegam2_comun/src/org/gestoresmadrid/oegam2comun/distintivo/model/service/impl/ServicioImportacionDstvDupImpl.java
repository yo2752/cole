package org.gestoresmadrid.oegam2comun.distintivo.model.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioImportacionDstvDup;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.DuplicadoDstvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultImportacionDstvDupBean;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionDstvDupImpl implements ServicioImportacionDstvDup{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionDstvDupImpl.class);

	private static final long serialVersionUID = 2247124703384368263L;

	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;

	@Autowired
	ServicioContrato servicioContrato;

	@Override
	public ResultImportacionDstvDupBean importarDuplicados(Long idContrato, File fichero, String ficheroFileName, BigDecimal idUsuario) {
		ResultImportacionDstvDupBean resultado = new ResultImportacionDstvDupBean(Boolean.FALSE);
		try {
			ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(idContrato));
			if(contrato != null){
				resultado = validarFichero(fichero, ficheroFileName);
				if (!resultado.getError()) {
					resultado = leerFichero(fichero);
					if (!resultado.getError()) {
						for (DuplicadoDstvBean duplicadoDstvBean : resultado.getListaDuplicados()) {
							try {
								ResultadoDistintivoDgtBean resultImport = servicioDistintivoVehNoMat.guardarImportacionDstv(duplicadoDstvBean, contrato, idUsuario);
								if (resultImport.getError()) {
									resultado.addResumenKO(resultImport.getMensaje());
								} else {
									String mensaje = "Matricula: " + duplicadoDstvBean.getMatricula() + " importada correctamente";
									if (duplicadoDstvBean.getMensajeAviso() != null && !duplicadoDstvBean.getMensajeAviso().isEmpty()) {
										mensaje += ". " + duplicadoDstvBean.getMensajeAviso();
									}
									resultado.addResumenOK(mensaje);
								}
							} catch (Exception e) {
								log.error("Ha sucedido un error a la hora de importar el duplicado con matricula: " + duplicadoDstvBean.getMatricula() +
										" para el contrato: " + idContrato + ", error: ",e);
								resultado.addResumenKO("Ha sucedido un error a la hora de importar el duplicado con matricula: " + duplicadoDstvBean.getMatricula());
							}
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos para el contrato indicado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la importacion del fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la importación del fichero.");
		}
		return resultado;
	}

	private ResultImportacionDstvDupBean validarFichero(File fichero, String ficheroFileName) {
		ResultImportacionDstvDupBean resultado = new ResultImportacionDstvDupBean(Boolean.FALSE);
		if (fichero == null || ficheroFileName == null || ficheroFileName.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar un fichero para importar.");
		} else {
			String ext = FilenameUtils.getExtension(ficheroFileName);
			if (!"txt".equals(ext)) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden importar fichero con extensión .txt");
			}
		}
		return resultado;
	}

	private ResultImportacionDstvDupBean leerFichero(File fichero) {
		ResultImportacionDstvDupBean resultado = new ResultImportacionDstvDupBean(Boolean.FALSE);
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fichero),"ISO-8859-1"));
			String line = null;
			int cont = 1;
			while ((line = input.readLine()) != null) {
				String[] duplicado = line.split(";");
				ResultImportacionDstvDupBean resultVal = validacionDatosImportacion(duplicado);
				if (resultVal.getError()) {
					resultado.addResumenKO("Error en la linea: " + cont + ",error: " + resultVal.getMensaje());
				} else {
					DuplicadoDstvBean dstvBean = new DuplicadoDstvBean();
					dstvBean.setMatricula(duplicado[0].trim().toUpperCase());
					dstvBean.setBastidor(duplicado[1].trim().toUpperCase());
					if (resultVal.getMensaje() != null && !resultVal.getMensaje().isEmpty()) {
						dstvBean.setMensajeAviso(resultVal.getMensaje());
					} else if (duplicado.length == 3) {
						dstvBean.setNive(duplicado[2].trim().toUpperCase());
					}
					resultado.addListaDuplicados(dstvBean);
				}
				cont++;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de leer el fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de leer el fichero.");
		}
		return resultado;
	}

	private ResultImportacionDstvDupBean validacionDatosImportacion(String[] duplicado) {
		ResultImportacionDstvDupBean resultado = new ResultImportacionDstvDupBean(Boolean.FALSE);
		if (duplicado == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No hay datos para importar.");
		} else if (duplicado.length < 2) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La matricula y el bastidor son obligatorios para poder realizar la importación.");
		} else if (duplicado.length > 3) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede realizar la importación porque contiene más datos de lo esperado.");
		} else {
			String matricula = duplicado[0].trim();
			String bastidor = duplicado[1].trim();
			if (bastidor.length() == 0) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque debe de rellenar los datos de la matrícula.");
			} else if (matricula.length() > 8) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque la matricula contiene más de 8 caracteres.");
			} else if (matricula.length() < 7 ) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque la matricula contiene menos de 7 caracteres.");
			} else if (bastidor.length() == 0) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque debe de rellenar los datos del bastidor.");
			} else if (bastidor.length() > 20) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede realizar la importación porque el bastidor contiene más de 20 caracteres.");
			}
			if (duplicado.length == 3) {
				String nive = duplicado[2].trim();
				if (nive.length() == 0) {
					resultado.setError(Boolean.FALSE);
					resultado.setMensaje("El NIVE no se puede guardar porque debe de rellenar sus datos.");
				} else if (nive.length() > 32) {
					resultado.setError(Boolean.FALSE);
					resultado.setMensaje("El NIVE no se puede guardar porque contiene más de 32 caracteres.");
				}
			}
		}
		return resultado;
	}
}