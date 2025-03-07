package org.gestoresmadrid.oegam2comun.distintivo.model.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioSolicitudesMasivasDstv;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;

@Service
public class ServicioSolicitudesMasivasDstvImpl implements ServicioSolicitudesMasivasDstv{

	private static final long serialVersionUID = 1561366337711016151L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSolicitudesMasivasDstvImpl.class);

	@Autowired
	ServicioDistintivoDgt servicioDistintivoDgt;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoDistintivoDgtBean tratarSolicitudesMasivas(File fichero, String nombrFichero, String tipoSolicitud ,Long idContrato, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			resultado = validarPeticion(fichero, nombrFichero, tipoSolicitud ,idContrato);
			if(!resultado.getError()){
				resultado = leerFichero(fichero, tipoSolicitud);
				if(!resultado.getError()){
					if("2".equals(tipoSolicitud)){
						String numMinMatriculas = gestorPropiedades.valorPropertie("cantidad.distintivos.solicitudes.masivas");
						if(numMinMatriculas != null && !numMinMatriculas.isEmpty()){
							if(resultado.getListaMatriculas().size() >= Integer.parseInt(numMinMatriculas)){
								resultado = servicioDistintivoDgt.solicitarImpresionDemandaMasivo(resultado.getListaMatriculas(),
										resultado.getNifTitularMasivo(), idContrato, idUsuario);
							} else {
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("Para poder generar de forma masiva en un mismo documento los distintivos de un titular, dichos distintivos tienen que superar la cantidad de " + numMinMatriculas);
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Ha sucedido un error a la hora de determinar el minimo de matriculas para poder generar los distintivos de un mismo titular.");
						}
					} else {
						for(String matricula : resultado.getListaMatriculas()){
							try {
								ResultadoDistintivoDgtBean resultSolcitud = null;
								if("0".equals(tipoSolicitud)){
									resultSolcitud = servicioDistintivoDgt.solicitarDistintivoMasivos(matricula, idContrato, idUsuario);
								} else if("1".equals(tipoSolicitud)){
									resultSolcitud = servicioDistintivoDgt.solicitarImpresionDstvMasivo(matricula, idContrato, idUsuario);
								}
								if(!resultSolcitud.getError()){
									resultado.addResumenOK("Distintivo para la matricula: " + matricula + " solicitado correctamente.");
								} else {
									resultado.addResumenKO(resultSolcitud.getMensaje());
								}
									
							} catch (Exception e) {
								log.error("Ha sucedido un error a la hora de realizar la acción solicitada sobre la matricula: " + matricula + ", error: ",e);
								resultado.addResumenKO("Ha sucedido un error a la hora de realizar la acción solicitada sobre la matricula: " + matricula + ".");
							}
						}
					}
				}
				
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar las solictudes masivas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar las solictudes masivas.");
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean leerFichero(File fichero, String tipoSolicitud) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), "ISO-8859-1"));
			String dato = null;
			int cont = 1;
			Boolean existenDatos = Boolean.FALSE;
			while ((dato = input.readLine()) != null){
				if("2".equals(tipoSolicitud) && cont == 1){
					int nifValido = NIFValidator.isValidDniNieCif(dato.trim().toUpperCase());
					if(nifValido >= 0){
						resultado.setNifTitularMasivo(dato.trim().toUpperCase());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La primera linea del fichero debe de ser el NIF/CIF del Titular.");
						return resultado;
					}
				} else {
					ResultadoDistintivoDgtBean resultVal = validacionDatosFichero(dato, cont);
					if (resultVal.getError()) {
						resultado.addResumenKO("Error en la linea: " + cont + ",error: " + resultVal.getMensaje());
					} else {
						resultado.addListaMatriculas(dato.trim().replace("-", "").toUpperCase());
						existenDatos = Boolean.TRUE;
					}
				}
				cont++;
			}
			if (!existenDatos) {
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de leer el fichero, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de leer el fichero.");
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validacionDatosFichero(String matricula, int linea) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (matricula == null || matricula.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Los datos en la linea "+ linea + " no son correctos.");
		} else if(matricula.trim().replace("-", "").length() > 8){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede realizar ninguna accion sobre la matricula: " + matricula + " porque contiene mas de 8 caracteres.");
		} else if (matricula.trim().replace("-", "").length() < 7 ){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede realizar ninguna accion sobre la matricula: " + matricula + " porque contiene menos de 7 caracteres.");
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validarPeticion(File fichero, String nombreFichero, String tipoSolicitud, Long idContrato) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (tipoSolicitud == null || tipoSolicitud.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar alguna acción para realizar sobre las solicitudes.");
		} else if (idContrato == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar algun contrato para realizar las solicitudes.");
		} else if (fichero == null || nombreFichero == null || nombreFichero.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de adjuntar algún fichero para poder realizar las solicitudes.");
		} else if (!"0".equals(tipoSolicitud) && !"1".equals(tipoSolicitud) && !"2".equals(tipoSolicitud)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar alguna acción valida para poder tratar el fichero.");
		} else {
			String ext = FilenameUtils.getExtension(nombreFichero);
			if (!"txt".equals(ext)){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Solo se pueden tratar ficheros con extensión .txt");
			}
		}
		return resultado;
	}
}