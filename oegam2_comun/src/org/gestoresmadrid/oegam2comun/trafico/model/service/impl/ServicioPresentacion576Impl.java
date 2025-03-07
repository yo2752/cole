package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.CausaHechoImponible;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Fabricacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.LugarAdquisicion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.PaisFabricacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioPresentacion576;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import comunicaciones.http.HttpsProtocol;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.utiles.BasicText;
import general.utiles.UtilHttpClient;
import oegam.constantes.ConstantesSession;
import trafico.beans.matriculacion.modelo576.Respuesta;
import trafico.beans.matriculacion.modelo576.RespuestaJsonRecibida;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesAEAT;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPresentacion576Impl implements ServicioPresentacion576 {

	private static final long serialVersionUID = -831524160731012180L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPresentacion576Impl.class);

	private static final String KEY_STORE_CLAVE_PRIVADA_URL = "aeat.keystore.url";
	private static final String KEY_STORE_CLAVE_PRIVADA_PASSWORD = "aeat.keystore.password";

	private static final String URL = "aeat.descarga.justificante576";
	private static final String XML_HEAD = "<?xml";
	private static final String XML_ERROR = "error";

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilHttpClient utilHttpClient;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesConversiones utilesConversiones;

	@Override
	public ResultBean tramitar576(TramiteTrafMatrDto tramiteTrafMatrDto, String alias) throws Exception {
		tramiteTrafMatrDto = rellenarRequeridosPresentacion(tramiteTrafMatrDto);
		Map<String, Object> mapaReturn = tramitar(tramiteTrafMatrDto, alias);
		return devolverResultado(mapaReturn, tramiteTrafMatrDto);
	}

	private ResultBean devolverResultado(Map<String, Object> mapaReturn, TramiteTrafMatrDto tramiteTrafMatrDto) {
		ResultBean result = new ResultBean(false);

		@SuppressWarnings("unchecked")
		ArrayList<String> datosRequeridos = (ArrayList<String>) mapaReturn.get("datosRequeridos");
		if (datosRequeridos != null && datosRequeridos.size() > 0) {
			result.addMensajeALista("Faltan los siguientes datos requeridos para la presentacion del 576:");
			String faltanDatos = "";
			for (int i = 0; i < datosRequeridos.size(); i++) {
				if (i == 0) {
					faltanDatos += datosRequeridos.get(i);
				} else {
					faltanDatos += ", " + datosRequeridos.get(i);
				}
			}
			result.addMensajeALista(faltanDatos);
			result.setError(true);
		}

		@SuppressWarnings("unchecked")
		ArrayList<String> erroresFormato = (ArrayList<String>) mapaReturn.get("erroresFormato");
		if (erroresFormato != null && erroresFormato.size() > 0) {
			result.addMensajeALista("Errores de formato que bloquean la presentacion del 576:");
			for (int i = 0; i < erroresFormato.size(); i++) {
				result.addMensajeALista(erroresFormato.get(i));
			}
			result.setError(true);
		}

		if (result != null && result.getError()) {
			return result;
		}

		// Se obtiene el resultado de la presentación del Modelo 576
		ResultBean resultPresentacion = (ResultBean) mapaReturn.get("resultadoPresentacion");
		// Si tenemos errores en la presentación
		if (resultPresentacion == null || resultPresentacion.getError()) {
			result.addMensajeALista("Errores en el proceso de presentacion en la AEAT");
			result.setError(true);
			String respuesta576 = "";

			@SuppressWarnings("unchecked")
			List<String> errores = (List<String>) resultPresentacion.getAttachment(ConstantesAEAT.DELIMITADOR_ERRORES);
			if (!CollectionUtils.isEmpty(errores)) {
				for (String error : errores) {
					String mensajeError = error.trim();
					mensajeError = error.replaceAll(" +", " ");
					respuesta576 += mensajeError + ". ";
					result.addMensajeALista(mensajeError);
				}
			} else if (!CollectionUtils.isEmpty(resultPresentacion.getListaMensajes())) {
				for (String mensajeError : resultPresentacion.getListaMensajes()) {
					if (mensajeError != null && !mensajeError.isEmpty()) {
						String[] arrayErroresPresentacion = mensajeError.split(ConstantesAEAT.DELIMITADOR_ERRORES);
						for (String actionError : arrayErroresPresentacion) {
							if (actionError != null && !actionError.isEmpty()) {
								respuesta576 += actionError + ". ";
								result.addMensajeALista(actionError);
							}
						}
					}
				}
			}
			if (respuesta576.length() > 398) {
				respuesta576 = respuesta576.substring(0, 397);
			}

			//tramiteTrafMatrDto.setRespuesta576(respuesta576);
			tramiteTrafMatrDto.setRespuesta576("Error al presentar en la AEAT.");
		// Si no hay errores en la presentación
		} else {
			if (resultPresentacion.getAttachment(ConstantesSession.RESPUESTA576) != null) {
				result.addAttachment(ConstantesSession.RESPUESTA576, resultPresentacion.getAttachment(ConstantesSession.RESPUESTA576));
				// Se extrae de la respuesta el CEM:
				String cem = ((RespuestaJsonRecibida)resultPresentacion.getAttachment("respuestaJsonRecibida")).getRespuesta().getCorrecta().getCem();
				if(!StringUtils.isEmpty(cem)){
					tramiteTrafMatrDto.setCem(cem);
					tramiteTrafMatrDto.setRespuesta576("La presentacion en la AEAT se ha realizado correctamente.");
				} else {
					log.error("El campo CEM de la respuesta ha sido nulo o vacío");
				}
			}
		}

		return result;
	}

	private ArrayList<String> comprobarRequeridos(TramiteTrafMatrDto tramiteTrafMatrDto, ArrayList<String> datosRequeridosF01) {
		ArrayList<String> datosRequeridos = new ArrayList<String>();

		if (tramiteTrafMatrDto.getTitular() == null || tramiteTrafMatrDto.getTitular().getPersona() == null || tramiteTrafMatrDto.getTitular().getPersona().getNif() == null) {
			datosRequeridos.add("Titular");
		}

		if (tramiteTrafMatrDto.getCuotaIngresar576() != null && tramiteTrafMatrDto.getCuotaIngresar576().intValue() > 0) {
			if (tramiteTrafMatrDto.getNrc576() == null || tramiteTrafMatrDto.getNrc576().equals("")) {
				datosRequeridos.add("NRC");
			}
		}

		if (tramiteTrafMatrDto.getCuotaIngresar576() != null && tramiteTrafMatrDto.getCuotaIngresar576().intValue() > 0) {
			if (tramiteTrafMatrDto.getImporte576() == null || tramiteTrafMatrDto.getImporte576().equals("")) {
				datosRequeridos.add("Cuota a ingresar");
			}
		}

		if (datosRequeridosF01 != null && datosRequeridosF01.size() > 0) {
			for (String datoRequerido : datosRequeridosF01) {
				datosRequeridos.add(datoRequerido);
			}
		}
		return datosRequeridos;
	}

	private LinkedHashMap<String, String> rellenarRequest(TramiteTrafMatrDto tramiteTrafMatrDto, String f01) {
		LinkedHashMap<String, String> request = new LinkedHashMap<String, String>();

		// CAMPO MODELO
		request.put(ConstantesAEAT.MODELO, ConstantesAEAT.ID_MODELO);
		// CAMPO EJERCICIO
		request.put(ConstantesAEAT.EJERCICIO, String.valueOf(tramiteTrafMatrDto.getEjercicio576()));
		// CAMPO PERIODO
		request.put(ConstantesAEAT.PERIODO_, ConstantesAEAT.PERIODO);

		// CAMPO NRC
		if (tramiteTrafMatrDto.getCuotaIngresar576() != null && tramiteTrafMatrDto.getCuotaIngresar576().intValue() > 0) {
			request.put(ConstantesAEAT.NRC, (tramiteTrafMatrDto.getNrc576()));
		} else {
			request.put(ConstantesAEAT.NRC, ConstantesAEAT.RESERVADO_VACIO);
		}
		// CAMPO IDI
		request.put(ConstantesAEAT.IDI, (ConstantesAEAT.IDI_VALOR));
		// CAMPO F01
		request.put(ConstantesAEAT.F01, f01);
		// CAMPO FIR
		request.put(ConstantesAEAT.FIR, (ConstantesAEAT.FIR_VALOR));
		// CAMPO FIRNIF
		request.put(ConstantesAEAT.FIRNIF, (ConstantesAEAT.FIRNIF_VALOR));
		// CAMPO FIRNOMBRE
		request.put(ConstantesAEAT.FIRNOMBRE, (ConstantesAEAT.FIRNOMBRE_VALOR));

		return request;
	}

	private Map<String, Object> tramitar(TramiteTrafMatrDto tramiteTrafMatrDto, String alias) throws Exception {
		Map<String, Object> mapaReturn = new HashMap<String, Object>();
		boolean errores = false;

		HashMap<String, Object> mapa = construirVariableF01(tramiteTrafMatrDto);

		@SuppressWarnings("unchecked")
		ArrayList<String> datosRequeridosF01 = (ArrayList<String>) mapa.get("datosRequeridosF01");
		ArrayList<String> datosRequeridos = comprobarRequeridos(tramiteTrafMatrDto, datosRequeridosF01);
		if (datosRequeridos.size() > 0) {
			mapaReturn.put("datosRequeridos", datosRequeridos);
			errores = true;
		}

		@SuppressWarnings("unchecked")
		ArrayList<String> erroresFormato = (ArrayList<String>) mapa.get("erroresFormato");
		if (erroresFormato != null && erroresFormato.size() > 0) {
			mapaReturn.put("erroresFormato", erroresFormato);
			errores = true;
		}

		if (errores) {
			return mapaReturn;
		}

		String f01 = (String) mapa.get(ConstantesAEAT.F01);
		if (f01.length() != ConstantesAEAT.TAM_F01_COMPLETA_NUEVA) {
			log.error("ERROR PRESENTACION 576. La variable f01 tiene una longitud incorrecta: " + f01.length());
			log.error("Numero de expediente: " + tramiteTrafMatrDto.getNumExpediente());
			log.error(f01);
			throw new Exception("ERROR PRESENTACION 576. La variable f01 tiene una longitud incorrecta: " + f01.length());
		}

		LinkedHashMap<String, String> request = rellenarRequest(tramiteTrafMatrDto, f01);

		mapaReturn.put(ConstantesAEAT.FICHERO_DECLARACION, mapa.get(ConstantesAEAT.FICHERO_DECLARACION));

		ResultBean resultadoInvocacion = invocacionPostModeloAEAT(tramiteTrafMatrDto, Constantes.VALOR_TRAMITE_AEAT576, request);
		mapaReturn.put("resultadoPresentacion", resultadoInvocacion);

		try {
//			guardarTxtEnvio576(resultadoInvocacion.getAttachment("jsonEnviado").toString(), tramiteTrafMatrDto.getNumExpediente().toString());
			guardarTxtEnvio576((String) mapa.get(ConstantesAEAT.FICHERO_DECLARACION), tramiteTrafMatrDto.getNumExpediente().toString());
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el txt de 576, error: ", e);
		}

		return mapaReturn;
	}

	private TramiteTrafMatrDto rellenarRequeridosPresentacion(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getCuotaIngresar576() == null) {
			tramiteTrafMatrDto.setCuotaIngresar576(BigDecimal.ZERO);
		}
		if (tramiteTrafMatrDto.getBaseImpoReducida576() == null) {
			tramiteTrafMatrDto.setBaseImpoReducida576(BigDecimal.ZERO);
		}
		if (tramiteTrafMatrDto.getTipoGravamen576() == null) {
			tramiteTrafMatrDto.setTipoGravamen576(BigDecimal.ZERO);
		}
		if (tramiteTrafMatrDto.getCuota576() == null) {
			tramiteTrafMatrDto.setCuota576(BigDecimal.ZERO);
		}
		if (tramiteTrafMatrDto.getDeduccionLineal576() == null) {
			tramiteTrafMatrDto.setDeduccionLineal576(BigDecimal.ZERO);
		}
		if (tramiteTrafMatrDto.getImporte576() == null) {
			tramiteTrafMatrDto.setImporte576(BigDecimal.ZERO);
		}
		if (tramiteTrafMatrDto.getDeducir576() == null) {
			tramiteTrafMatrDto.setDeducir576(BigDecimal.ZERO);
		}
		if (tramiteTrafMatrDto.getLiquidacion576() == null) {
			tramiteTrafMatrDto.setLiquidacion576(BigDecimal.ZERO);
		}
		return tramiteTrafMatrDto;
	}

	private HashMap<String, Object> construirVariableF01(TramiteTrafMatrDto tramiteTrafMatrDto) throws Exception {
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		ArrayList<String> datosRequeridosF01 = new ArrayList<String>();
		ArrayList<String> erroresFormato = new ArrayList<String>();
		String cabecera = null;

		// CABECERA
		cabecera = ConstantesAEAT.TAG_CABECERA_DATOS_INICIO;
		if (tramiteTrafMatrDto.getEjercicio576() != null && !"-1".equals(tramiteTrafMatrDto.getEjercicio576().toString())) {
			cabecera += tramiteTrafMatrDto.getEjercicio576();
		} else {
			datosRequeridosF01.add("Ejercicio de devengo");
		}

		cabecera += ConstantesAEAT.TAG_CABECERA_DATOS_FIN;
		cabecera += ConstantesAEAT.TAG_INICIO_AUX + BasicText.changeSize("", ConstantesAEAT.TAM_BLANCOS_AUX) + ConstantesAEAT.TAG_FIN_AUX;

		// En MAYUSCULAS el nombre de los 'campos' descritos en la documentacion: DISEÑ‘O DE REGISTRO
		// ETIQUETA DE INICIO
		String cadenaDeDatos = ConstantesAEAT.TAG_INICIO_DATOS;
		// IDENTIFICADOR DE MODELO
		cadenaDeDatos += ConstantesAEAT.ID_MODELO;

		// TIPO DE DECLARACION + RESERVADOS AEAT DE 9
		if (tramiteTrafMatrDto.getCuotaIngresar576() == null) {
			datosRequeridosF01.add("Cuota a ingresar");
		} else if (tramiteTrafMatrDto.getCuotaIngresar576().intValue() > 0) {
			cadenaDeDatos += BasicText.changeSize(ConstantesAEAT.INGRESO, 1);
		} else {
			cadenaDeDatos += BasicText.changeSize(ConstantesAEAT.NEGATIVA, 1);
		}
		// RESERVADO AEAT 9
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 9);

		// EJERCICIO DE DEVENGO + RESERVADOS AEAT DE 4
		if (tramiteTrafMatrDto.getEjercicio576() != null) {
			cadenaDeDatos += BasicText.changeSize(String.valueOf(tramiteTrafMatrDto.getEjercicio576()), 8);
		}

		// PERIODO
		cadenaDeDatos += ConstantesAEAT.PERIODO;
		// TIPO DE TRANSPORTE
		cadenaDeDatos += ConstantesAEAT.TIPO_TRANSPORTE;
		String datosPersonales = null;
		if (tramiteTrafMatrDto.getTitular() != null && tramiteTrafMatrDto.getTitular().getPersona() != null) {
			if (utilesConversiones.isNifNie(tramiteTrafMatrDto.getTitular().getPersona().getNif())) {
				datosPersonales = tramiteTrafMatrDto.getTitular().getPersona().getNif();
				if (tramiteTrafMatrDto.getTitular().getPersona().getApellido1RazonSocial() != null) {
					datosPersonales += tramiteTrafMatrDto.getTitular().getPersona().getApellido1RazonSocial() + " ";
				}
				if (tramiteTrafMatrDto.getTitular().getPersona().getApellido2() != null) {
					datosPersonales += tramiteTrafMatrDto.getTitular().getPersona().getApellido2() + " ";
				}
				if (tramiteTrafMatrDto.getTitular().getPersona().getNombre() != null) {
					datosPersonales += tramiteTrafMatrDto.getTitular().getPersona().getNombre();
				}
			} else {
				datosPersonales = tramiteTrafMatrDto.getTitular().getPersona().getNif();
				datosPersonales += tramiteTrafMatrDto.getTitular().getPersona().getApellido1RazonSocial();
			}
			// Comprueba la longitud de 'datosPersonales' para rellenar con espacios la longitud requerida (49)
			if (datosPersonales.length() > 49) {
				erroresFormato.add("La longitud del nombre y los apellidos o la razon social no puede superar los 49 caracteres");
			} else if (datosPersonales.length() < 49) {
				datosPersonales = BasicText.changeSize(datosPersonales, 49);
			}
			// NIF/CIF NOMBRE,APELLIDOS/RAZON SOCIAL
			cadenaDeDatos += datosPersonales;
		}

		// RESERVADOS AEAT 279
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos.toString(), cadenaDeDatos.length() + 279);

		int ejercicio = 0;
		if (tramiteTrafMatrDto.getEjercicio576() != null) {
			// MEDIO DE TRANSPORTE NUEVO/USADO (marca longitud cadenaDeDatos : 360)
			ejercicio = tramiteTrafMatrDto.getEjercicio576().intValue();
			if (ejercicio < 2008) {
				// Segun documentacion: si ejercicio < 2008 establecer siempre 'nuevo'.
				cadenaDeDatos += ConstantesAEAT.NUEVO;
			} else {
				if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getVehiUsado() != null) {
					if (!tramiteTrafMatrDto.getVehiculoDto().getVehiUsado()) {
						cadenaDeDatos += ConstantesAEAT.NUEVO;
					} else {
						cadenaDeDatos += ConstantesAEAT.USADO;
					}
				} else {
					datosRequeridosF01.add("Nuevo o usado");
				}
			}
		}

		// LUGAR DE ADQUISICION DEL VEHICULO
		LugarAdquisicion lugarAdquisicion = null;
		String idFabricacion = null;
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getFabricacion() != null) {
			idFabricacion = tramiteTrafMatrDto.getVehiculoDto().getFabricacion();
		}
		String idPaisFabricacion = null;
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getPaisFabricacion() != null) {
			idPaisFabricacion = tramiteTrafMatrDto.getVehiculoDto().getPaisFabricacion();
		}
		if ((idFabricacion == null || idFabricacion.equals("")) && (idPaisFabricacion == null || idPaisFabricacion.equals(""))) {
			datosRequeridosF01.add("Pais de fabricacion");
		} else if (idPaisFabricacion != null && !idPaisFabricacion.equals("")) {
			lugarAdquisicion = PaisFabricacion.equivalenciaLugarAdquisicion(idPaisFabricacion);
		} else if (idFabricacion != null && !idFabricacion.equals("")) {
			lugarAdquisicion = Fabricacion.equivalenciaLugarAdquisicion(idFabricacion);
		}

		if (lugarAdquisicion != null) {
			if (ejercicio < 2008 && (lugarAdquisicion == LugarAdquisicion.OtrosEstadosMiembrosDeLaUE || lugarAdquisicion == LugarAdquisicion.EstadoNoMiembroDeLaUE)) {
				// Segun documentacion: si ejercicio < 2008 si es de fuera de espaÃ±a -> 2
				cadenaDeDatos += LugarAdquisicion.OtrosEstadosMiembrosDeLaUE.getValorEnum();
			} else {
				cadenaDeDatos += lugarAdquisicion.getValorEnum();
			}
		}

		// RESERVADO AEAT 1
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 1);

		// FECHA DE PRIMERA MATRICULACION (VEHICULOS USADOS)
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getVehiUsado()) {
			if (tramiteTrafMatrDto.getVehiculoDto().getFechaPrimMatri() != null && !tramiteTrafMatrDto.getVehiculoDto().getFechaPrimMatri().isfechaNula()) {
				Fecha fecha = tramiteTrafMatrDto.getVehiculoDto().getFechaPrimMatri();
				// Construye una cadena acorde al formato del 576 -> DDMMYYYY
				String fechaPara576 = fecha.getDia();
				fechaPara576 += fecha.getMes();
				fechaPara576 += fecha.getAnio();
				cadenaDeDatos += fechaPara576;
			} else {
				datosRequeridosF01.add("Fecha de la primera matriculacion (vehiculos usados)");
			}
		} else {
			// Campo no obligatorio. Rellena con blancos 8 posiciones:
			// cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 8);
			cadenaDeDatos += dameStringCeros(8);
		}

		// MARCA DEL VEHICULO
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCodigoMarca() != null) {
			// Recupera la descripcion con el codigo:
			String nombreMarca = servicioVehiculo.obtenerNombreMarca(tramiteTrafMatrDto.getVehiculoDto().getCodigoMarca(), true);
			if (nombreMarca != null && !nombreMarca.isEmpty()) {
				// Comprueba que no exceda la longitud permitida (40)
				if (nombreMarca.length() > 40) {
					erroresFormato.add("La longitud de la marca excede el maximo de 40 caracteres");
				} else {
					// Rellena con blancos hasta 40 (longitud requerida)
					if (nombreMarca.length() < 40) {
						nombreMarca = BasicText.changeSize(nombreMarca, 40);
					}
					cadenaDeDatos += nombreMarca;
				}
			} else {
				datosRequeridosF01.add("Marca");
			}
		} else {
			datosRequeridosF01.add("Marca");
		}

		// MODELO-TIPO
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getModelo() != null && !tramiteTrafMatrDto.getVehiculoDto().getModelo().equals("")) {
			// Comprueba que no exceda la longitud permitida (80)
			if (tramiteTrafMatrDto.getVehiculoDto().getModelo().length() > 80) {
				erroresFormato.add("La longitud de modelo-tipo excede el maximo de 80 caracteres");
			} else {
				String modeloTipo = tramiteTrafMatrDto.getVehiculoDto().getModelo();
				// Rellena con blancos hasta 80 (longitud requerida)
				if (modeloTipo.length() < 80) {
					modeloTipo = BasicText.changeSize(modeloTipo, 80);
				}
				cadenaDeDatos += modeloTipo;
			}
		} else {
			datosRequeridosF01.add("Modelo");
		}

		// N DE IDENTIFICACION (BASTIDOR)
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getBastidor() != null && !tramiteTrafMatrDto.getVehiculoDto().getBastidor().equals("")) {
			// Comprueba que no exceda la longitud permitida (22)
			if (tramiteTrafMatrDto.getVehiculoDto().getBastidor().length() > 22) {
				erroresFormato.add("La longitud del bastidor excede el máximo de 22 caracteres");
			} else {
				String bastidor = tramiteTrafMatrDto.getVehiculoDto().getBastidor();
				// Rellena con blancos hasta 22 (longitud requerida)
				if (bastidor.length() < 22) {
					bastidor = BasicText.changeSize(bastidor, 22);
				}
				cadenaDeDatos += bastidor;
			}
		} else {
			datosRequeridosF01.add("Bastidor");
		}

		// CLASIFICACION POR CRITERIO DE CONSTRUCCION Y DE UTILIZACION
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCriterioConstruccion() != null && !tramiteTrafMatrDto.getVehiculoDto().getCriterioConstruccion()
				.isEmpty()) {
			String criterioConstruccion = tramiteTrafMatrDto.getVehiculoDto().getCriterioConstruccion();
			// Comprueba la longitud:
			if (criterioConstruccion.length() > 2) {
				erroresFormato.add("La longitud del criterio de construcción excede el máximo de 2 caracteres");
			} else {
				if (criterioConstruccion.length() < 2) {
					criterioConstruccion = BasicText.changeSize(criterioConstruccion, 2);
				}
				cadenaDeDatos += criterioConstruccion;
			}
		} else {
			datosRequeridosF01.add("Criterio de construccion");
		}
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCriterioUtilizacion() != null && !tramiteTrafMatrDto.getVehiculoDto().getCriterioUtilizacion()
				.isEmpty()) {
			String criterioUtilizacion = tramiteTrafMatrDto.getVehiculoDto().getCriterioUtilizacion();
			// Comprueba la longitud:
			if (criterioUtilizacion.length() > 2) {
				erroresFormato.add("La longitud del criterio de utilización excede el máximo de 2 caracteres");
			} else {
				if (criterioUtilizacion.length() < 2) {
					criterioUtilizacion = BasicText.changeSize(criterioUtilizacion, 2);
				}
				cadenaDeDatos += criterioUtilizacion;
			}
		} else {
			datosRequeridosF01.add("Criterio de utilizacion");
		}

		// CLASIFICACION SEGUN DIRECTIVA CEE
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getIdDirectivaCee() != null && !tramiteTrafMatrDto.getVehiculoDto().getIdDirectivaCee().isEmpty()) {
			String idDirectivaCEE = tramiteTrafMatrDto.getVehiculoDto().getIdDirectivaCee();
			// Comprueba la longitud:
			if (idDirectivaCEE.length() > 2) {
				// Si la longitud es mayor que 2, no se envia
				cadenaDeDatos += "  ";
				// erroresFormato.add("La longitud del identificador de la homologacion EU excede el maximo de 2 caracteres");
			} else {
				if (idDirectivaCEE.length() < 2) {
					idDirectivaCEE = BasicText.changeSize(idDirectivaCEE, 2);
				}
				cadenaDeDatos += idDirectivaCEE;
			}
		} else {
			datosRequeridosF01.add("Clasificacion segun homologacion EU");
		}

		// RESERVADOS AEAT 8
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 8);

		// EMISIONES CO2
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCo2() != null && !tramiteTrafMatrDto.getVehiculoDto().getCo2().equals("")) {
			String co2 = tramiteTrafMatrDto.getVehiculoDto().getCo2();
			// Segun documentacion, debe ir en gramos por kilometro, 5 posiciones, sin decimales y rellenando con ceros por la izquierda:
			// Elimina, si tiene los decimales y su notacion
			if (co2.contains(".")) {
				co2 = co2.substring(0, co2.indexOf("."));
			}
			// Comprueba la longitud:
			if (co2.length() > 5) {
				// Longitud de la parte entera mayor de la permitida:
				erroresFormato.add("La longitud de la emisión de CO2 excede el máximo de 5 dígitos enteros");
			} else {
				// Completa con ceros si la longitud del numero es menor que la requerida
				if (co2.length() < 5) {
					co2 = utiles.rellenarCeros(co2.replace(".", ""), 5);
				}
				cadenaDeDatos += co2;
			}
		} else {
			datosRequeridosF01.add("Emision de CO2");
		}

		// EPIGRAFE
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getEpigrafe() != null && !tramiteTrafMatrDto.getVehiculoDto().getEpigrafe().isEmpty()) {
			String epigrafe = tramiteTrafMatrDto.getVehiculoDto().getEpigrafe();
			// Comprueba la longitud:
			if (epigrafe.length() > 2) {
				erroresFormato.add("La longitud del epigrafe excede el máximo de 2 caracteres");
			} else {
				if (epigrafe.length() < 2) {
					epigrafe = BasicText.changeSize(epigrafe, 2, '0', false);
				}
				cadenaDeDatos += epigrafe;
			}
		} else {
			datosRequeridosF01.add("Epigrafe");
		}

		// KILOMETROS
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getVehiUsado()) {
			if (tramiteTrafMatrDto.getVehiculoDto().getKmUso() != null) {
				String cadKilometros = tramiteTrafMatrDto.getVehiculoDto().getKmUso().toString();
				// Comprueba la longitud:
				if (cadKilometros.length() > 6) {
					erroresFormato.add("La longitud de los kilometros de uso excede el máximo de 6 caracteres");
				} else {
					// Completa con ceros si la longitud del numero es menor que la requerida
					if (cadKilometros.length() < 6) {
						cadKilometros = utiles.rellenarCeros(cadKilometros, 6);
					}
					cadenaDeDatos += cadKilometros;
				}
			} else {
				datosRequeridosF01.add("Kilometros de uso");
			}
		} else {
			// Campo no obligatorio. Rellena con blancos 6 posiciones:
			// cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 6);
			cadenaDeDatos += dameStringCeros(6);
		}

		// NUMERO DE SERIE ITV
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getNumSerie() != null) {
			String numSerieItv = tramiteTrafMatrDto.getVehiculoDto().getNumSerie();
			// Comprueba la longitud:
			if (numSerieItv.length() > 12) {
				erroresFormato.add("La longitud del numero de serie de la tarjeta itv excede el maximo de 12 caracteres");
			} else {
				if (numSerieItv.length() < 12) {
					numSerieItv = BasicText.changeSize(numSerieItv, 12);
				}
				cadenaDeDatos += numSerieItv;
			}
		} else {
			datosRequeridosF01.add("Numero de serie de la tarjeta ITV");
		}

		// TIPO DE TARJETA ITV
		String tipoTarjetaItv = null;
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getTipoTarjetaITV() != null && !tramiteTrafMatrDto.getVehiculoDto().getTipoTarjetaITV().isEmpty()) {
			tipoTarjetaItv = tramiteTrafMatrDto.getVehiculoDto().getTipoTarjetaITV();
			if (tipoTarjetaItv.equals("A") || tipoTarjetaItv.equals("B") || tipoTarjetaItv.equals("C") || tipoTarjetaItv.equals("D")) {
				cadenaDeDatos += tipoTarjetaItv;
			} else if (tipoTarjetaItv.equals("AL") || tipoTarjetaItv.equals("AR") || tipoTarjetaItv.equals("AT")) {
				tipoTarjetaItv = "A";
				cadenaDeDatos += tipoTarjetaItv;
			} else if (tipoTarjetaItv.equals("BL") || tipoTarjetaItv.equals("BR") || tipoTarjetaItv.equals("BT")) {
				tipoTarjetaItv = "B";
				cadenaDeDatos += tipoTarjetaItv;
			} else if (!tipoTarjetaItv.equals("A") && !tipoTarjetaItv.equals("B") && !tipoTarjetaItv.equals("C") && !tipoTarjetaItv.equals("D")) {
				erroresFormato.add("Solo se consideran validas el tipo A,B,C o D para la tarjeta itv");
			}
		} else {
			datosRequeridosF01.add("Tipo de la tarjeta ITV");
		}

		// COMBUSTIBLE DEL VEHICULO
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCarburante() != null && !tramiteTrafMatrDto.getVehiculoDto().getCarburante().isEmpty()) {
			String idCarburante = tramiteTrafMatrDto.getVehiculoDto().getCarburante();
			Combustible combustible = Combustible.convertir(idCarburante);
			if (combustible == Combustible.Gasolina) {
				idCarburante = ConstantesAEAT.GASOLINA;
			} else if (combustible == Combustible.Diesel) {
				idCarburante = ConstantesAEAT.DIESEL;
			} else {
				// Segun documentacion solo admite gasolina, diesel y otros
				idCarburante = ConstantesAEAT.OTROS;
			}
			cadenaDeDatos += idCarburante;
		} else {
			datosRequeridosF01.add("Tipo de combustible");
		}

		// CILINDRADA
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCilindrada() != null && !tramiteTrafMatrDto.getVehiculoDto().getCilindrada().equals("")) {
			String cilindrada = tramiteTrafMatrDto.getVehiculoDto().getCilindrada();
			// Comprueba la longitud:
			if (cilindrada.length() > 5) {
				erroresFormato.add("La longitud de la cilindrada excede el maximo de 5 caracteres");
			} else {
				//Convertimos posibles valores decimales a enteros
				cilindrada = String.valueOf(new Double(cilindrada).intValue());
				// Completa con ceros si la longitud del numero es menor que la requerida
				if (cilindrada.length() < 5) {
					cilindrada = utiles.rellenarCeros(cilindrada, 5);
				}
				cadenaDeDatos += cilindrada;
			}
		} else {
			datosRequeridosF01.add("Cilindrada");
		}

		// RESERVADOS AEAT 303
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 142);
		cadenaDeDatos += dameStringCeros(5);
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 142);
		cadenaDeDatos += dameStringCeros(14);

		// CODIGO ITV
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCodItv() != null && !tramiteTrafMatrDto.getVehiculoDto().getCodItv().equals("")) {
			String codigoItv = tramiteTrafMatrDto.getVehiculoDto().getCodItv();
			// Comprueba la longitud:
			if (codigoItv.length() > 9 || codigoItv.length() < 8) {
				erroresFormato.add("La longitud del codigo itv debe ser 8 o 9 caracteres");
			} else {
				if (codigoItv.length() < 9) {
					codigoItv = BasicText.changeSize(codigoItv, 9);
				}
				cadenaDeDatos += codigoItv;
			}
		} else {
			// Campo no obligatorio. Rellena con blancos 9 posiciones:
			cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 9);
		}

		// RESERVADOS AEAT 223 (marca longitud cadenaDeDatos : 1094)
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 223);

		// LIQUIDACION. BASE IMPONIBLE
		if (tramiteTrafMatrDto.getBaseImponible576() != null) {
			// Segun documentacion debe ir con 11 enteros y dos decimales
			BigDecimal baseImponible = tramiteTrafMatrDto.getBaseImponible576();
			// Establece la parte decimal:
			baseImponible = utiles.getDecimales(2, baseImponible);
			// Establece la parte entera:
			String cadBaseImponible = utiles.rellenarCeros(baseImponible.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadBaseImponible = cadBaseImponible.replace(".", "");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadBaseImponible;
		} else {
			datosRequeridosF01.add("Base imponible");
		}

		// LIQUIDACION. BASE IMPONIBLE REDUCIDA
		if (tramiteTrafMatrDto.getBaseImpoReducida576() != null) {
			// Segun documentacion debe ir con 11 enteros y dos decimales
			BigDecimal baseImponibleReducida = tramiteTrafMatrDto.getBaseImpoReducida576();
			// Establece la parte decimal:
			baseImponibleReducida = utiles.getDecimales(2, baseImponibleReducida);
			// Establece la parte entera:
			String cadBaseImponibleReducida = utiles.rellenarCeros(baseImponibleReducida.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadBaseImponibleReducida = cadBaseImponibleReducida.replace(".", "");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadBaseImponibleReducida;
		} else {
			datosRequeridosF01.add("Base imponible reducida");
		}

		// LIQUIDACION. TIPO DE GRAVAMEN
		if (tramiteTrafMatrDto.getTipoGravamen576() != null) {
			// Segun documentacion debe ir con 3 enteros y 2 decimales
			BigDecimal tipoGravamen = tramiteTrafMatrDto.getTipoGravamen576();
			// Establece la parte decimal:
			tipoGravamen = utiles.getDecimales(2, tipoGravamen);
			// Establece la parte entera:
			if (tipoGravamen.intValue() < 100) {
				// Faltan enteros. Rellena con ceros:
				String cadTipoGravamen = utiles.rellenarCeros(tipoGravamen.toString().replace(".", ""), 5);
				// Quita el punto de los decimales porque excede la longitud:
				cadTipoGravamen = cadTipoGravamen.replace(".", "");
				cadenaDeDatos += cadTipoGravamen;
			} else {
				// Quita el punto de los decimales porque excede la longitud:
				cadenaDeDatos += tipoGravamen.toString().replace(".", "");
			}
		} else {
			datosRequeridosF01.add("Tipo de gravamen");
		}

		// LIQUIDACION. CUOTA
		if (tramiteTrafMatrDto.getCuota576() != null) {
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal cuota = tramiteTrafMatrDto.getCuota576();
			// Establece la parte decimal:
			cuota = utiles.getDecimales(2, cuota);
			// Establece la parte entera:
			String cadCuota = utiles.rellenarCeros(cuota.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadCuota = cadCuota.replace(".", "");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadCuota;
		} else {
			datosRequeridosF01.add("Cuota");
		}

		// LIQUIDACION. DEDUCCION LINEAL
		if (tramiteTrafMatrDto.getDeduccionLineal576() != null) {
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal deduccionLineal = tramiteTrafMatrDto.getDeduccionLineal576();
			// Establece la parte decimal:
			deduccionLineal = utiles.getDecimales(2, deduccionLineal);
			// Establece la parte entera:
			String cadDeduccionLineal = utiles.rellenarCeros(deduccionLineal.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadDeduccionLineal = cadDeduccionLineal.replace(".", "");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadDeduccionLineal;
		} else {
			datosRequeridosF01.add("Deduccion lineal");
		}

		// LIQUIDACION. CUOTA A INGRESAR
		if (tramiteTrafMatrDto.getCuotaIngresar576() != null) {
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal cuotaAIngresar = tramiteTrafMatrDto.getCuotaIngresar576();
			// Establece la parte decimal:
			cuotaAIngresar = utiles.getDecimales(2, cuotaAIngresar);
			// Establece la parte entera:
			String cadCuotaAIngresar = utiles.rellenarCeros(cuotaAIngresar.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadCuotaAIngresar = cadCuotaAIngresar.replace(".", "");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadCuotaAIngresar;
		} else {
			datosRequeridosF01.add("Cuota a ingresar");
		}

		// LIQUIDACION. A DEDUCIR
		if (tramiteTrafMatrDto.getDeducir576() != null) {
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal aDeducir = tramiteTrafMatrDto.getDeducir576();
			// Establece la parte decimal:
			aDeducir = utiles.getDecimales(2, aDeducir);
			// Establece la parte entera:
			String cadADeducir = utiles.rellenarCeros(aDeducir.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadADeducir = cadADeducir.replace(".", "");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadADeducir;
		} else {
			datosRequeridosF01.add("Cantidad a deducir");
		}

		// LIQUIDACION. RESULTADO DE LA LIQUIDACION
		if (tramiteTrafMatrDto.getLiquidacion576() != null) {
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal liquidacion = tramiteTrafMatrDto.getLiquidacion576();
			// Establece la parte decimal:
			liquidacion = utiles.getDecimales(2, liquidacion);
			// Establece la parte entera:
			String cadLiquidacion = utiles.rellenarCeros(liquidacion.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadLiquidacion = cadLiquidacion.replace(".", "");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadLiquidacion;
		} else {
			datosRequeridosF01.add("Resultado de la liquidacion");
		}

		// DECLARACION COMPLEMENTARIA
		if (tramiteTrafMatrDto.getNumDeclaracionComp576() != null && !tramiteTrafMatrDto.getNumDeclaracionComp576().isEmpty()) {
			String declaracionComplementaria = tramiteTrafMatrDto.getNumDeclaracionComp576();
			// Comprueba la longitud de 'declaracionComplementaria' para rellenar con espacios la longitud requerida (13)
			if (declaracionComplementaria.length() > 13) {
				erroresFormato.add("La longitud del numero de la declaracion complementaria no puede superar los 13 caracteres");
			} else if (declaracionComplementaria.length() < 13) {
				declaracionComplementaria = BasicText.changeSize(declaracionComplementaria, 13);
			}
			cadenaDeDatos += declaracionComplementaria;
		} else {
			cadenaDeDatos += dameStringCeros(13);
		}

		// RESERVADOS AEAT 136 (marca longitud cadenaDeDatos : 1190)
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 136);

		// CAUSA DEL HECHO IMPONIBLE
		if (tramiteTrafMatrDto.getCausaHechoImpon576() != null && !"-1".equals(tramiteTrafMatrDto.getCausaHechoImpon576())) {
			cadenaDeDatos += CausaHechoImponible.convertir(tramiteTrafMatrDto.getCausaHechoImpon576());
		} else {
			datosRequeridosF01.add("Causa hecho imponible");
		}

		// RESERVADOS AEAT 8
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 8);

		// NIF Y NOMBRE DEL INTRODUCTOR DEL VEHICULO EN ESPAÃ‘A (SI TIPO TARJETA ITV 'A')
		if (tipoTarjetaItv != null && tipoTarjetaItv.equalsIgnoreCase("A")) {
			if (tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador() == null) {
				datosRequeridosF01.add("Para tipo tarjeta itv 'A' requerido datos del introductor del vehiculo en EspaÃ±a");
			} else {
				if (tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getNif() == null) {
					datosRequeridosF01.add("Para tipo tarjeta itv 'A' falta nif/cif del introductor del vehiculo en EspaÃ±a");
				}
				String datosIntegrador = null;
				if (utilesConversiones.isNifNie(tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getNif())) {
					datosIntegrador = tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getNif();
					if (tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getNombre() != null) {
						datosIntegrador += tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getNombre() + " ";
					}
					if (tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial() != null) {
						datosIntegrador += tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial() + " ";
					}
					if (tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getApellido2() != null) {
						datosIntegrador += tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getApellido2();
					}
				} else {
					datosIntegrador = tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getNif();
					datosIntegrador += tramiteTrafMatrDto.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial();
				}
				// Comprueba la longitud de 'datosIntegrador' para rellenar con espacios la longitud requerida (49)
				if (datosIntegrador.length() > 49) {
					erroresFormato.add("Error formato: La longitud del nombre y los apellidos o la razon social " + "no puede superar los 40 caracteres");
				} else if (datosIntegrador.length() < 49) {
					datosIntegrador = BasicText.changeSize(datosIntegrador, 49);
				}
				cadenaDeDatos += datosIntegrador;
			}
		} else {
			// El tipo de tarjeta es distinto de A. Rellena los 49 espacios del integrador con blancos:
			cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 49);
		}

		// RESERVADOS AEAT 84
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 84);

		// VEHICULO OBSERVACIONES
		if (tramiteTrafMatrDto.getObservaciones576() != null && !"-1".equals(tramiteTrafMatrDto.getObservaciones576())) {
			cadenaDeDatos += tramiteTrafMatrDto.getObservaciones576();
		} else {
			datosRequeridosF01.add("Observaciones");
		}

		// RESERVADOS AEAT 25
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 25);

		// Establece el pie de la cadena de datos:
		String pie = "</T57601>";
		cadenaDeDatos += pie;

		mapa.put(ConstantesAEAT.FICHERO_DECLARACION, cadenaDeDatos);

		cadenaDeDatos += ConstantesAEAT.TAG_PIE_DATOS_INICIO;
		if (tramiteTrafMatrDto.getEjercicio576() != null) {
			cadenaDeDatos += String.valueOf(tramiteTrafMatrDto.getEjercicio576());
		}
		cadenaDeDatos += ConstantesAEAT.TAG_PIE_DATOS_FIN;

		// Le aÃ±ado la cabecera:
		cabecera += cadenaDeDatos;

		mapa.put(ConstantesAEAT.F01, cabecera);

		// Mete la lista de los datos requeridos que faltan en el mapa del return:
		mapa.put("datosRequeridosF01", datosRequeridosF01);
		mapa.put("erroresFormato", erroresFormato);
		return mapa;
	}

	private ResultBean invocacionPostModeloAEAT(TramiteTrafMatrDto tramiteTrafMatrDto, String modelo, LinkedHashMap<String, String> request) throws Exception {
		log.info("Se va a crear el protocolo de comunicacion");
		log.info("El modelo a presentar: " + modelo);
		ResultBean result = new ResultBean();
		try {
			String host = gestorPropiedades.valorPropertie("trafico.576.host");
			if (host == null || host.equals("")) {
				log.error("Error :" + " No se ha configurado la propiedad que indica el host de la AEAT para la presentacion del 576");
				result = new ResultBean();
				result.setMensaje("Error :" + " No se ha configurado la propiedad que indica el host de la AEAT para la presentacion del 576");
				result.setError(Boolean.TRUE);
			}

			result = utilHttpClient.executeMethod(host, gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_URL), gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_PASSWORD), request);

			String numExpediente = tramiteTrafMatrDto.getNumExpediente().toString();

			guardarRespuesta576((String) result.getAttachment("respuestaRecibidaSintratrar") == null ? result.getMensaje() : (String) result.getAttachment("respuestaRecibidaSintratrar"), numExpediente);

			if (!result.getError().booleanValue()) {
				// Todavia puede contener errores la respuesta. Segun documentacion, en caso de error, la respuesta JSON tendrá un campo 'errores'
				Respuesta respuesta = ((RespuestaJsonRecibida) result.getAttachment("respuestaJsonRecibida")).getRespuesta();
				if (!CollectionUtils.isEmpty(respuesta.getErrores())) {
					result.setError(true);
					result.addAttachment(ConstantesAEAT.DELIMITADOR_ERRORES, respuesta.getErrores());
				} else {
					result.setError(Boolean.FALSE);
					byte[] bytesPdf = descargarPdfADisco(respuesta.getCorrecta().getUrlpPdf(), numExpediente, tramiteTrafMatrDto.getFechaAlta());
					result.addAttachment(ConstantesSession.RESPUESTA576, bytesPdf);
					if (bytesPdf == null){
						result.setError(Boolean.TRUE);
						result.setMensaje("Error durante la descarga del pdf");
					}
				}
			} else {
				result.setError(Boolean.TRUE);
				result.addAttachment(ConstantesSession.RESPUESTA576, result.getMensaje());
			}
			result.addAttachment(ConstantesSession.TRAMITE_EN_CURSO, tramiteTrafMatrDto);

		} catch (Throwable e) {
			log.error("Error :" + e);
			result = new ResultBean();
			result.setMensaje("Desactivada la presentación telemática del 576 a través de Oegam, puede descargar el fichero para su presentación pulsando el botón 'Descargar AEAT'");
			result.setError(Boolean.TRUE);
		}
		return result;
	}

	private void guardarRespuesta576(String mensaje, String numExpediente) throws Exception, OegamExcepcion {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(mensaje.getBytes());
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.TIPO576RESPTXT);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento(numExpediente + "_576_RESP");

		gestorDocumentos.guardarByte(ficheroBean);
	}

	private void guardarTxtEnvio576(String mensaje, String numExpediente) throws Exception, OegamExcepcion {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(mensaje.getBytes());
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.TIPO576ENVTXT);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento(numExpediente + "_576_ENV");

		gestorDocumentos.guardarByte(ficheroBean);
	}

	private byte[] descargarPdfADisco(String urlPDF, String numExpediente, Fecha fechaCreacion) throws Exception, OegamExcepcion {
		byte[] bytesPdf = null;
		int codigoRespuesta;
		HttpsProtocol protocolo = new HttpsProtocol();
		protocolo.establecerHttps();
		GetMethod getMethod = new GetMethod(urlPDF);
		HttpClient httpClient = new HttpClient();
		try {
			codigoRespuesta = httpClient.executeMethod(getMethod);
		} catch (Exception e) {
			getMethod = new GetMethod(urlPDF.replace("www1", "www2"));
			codigoRespuesta = httpClient.executeMethod(getMethod);
		}

		// Comprueba el codigo de respuesta recibido:
		if (codigoRespuesta != HttpStatus.SC_OK) {
			return null;
		}
		bytesPdf = getMethod.getResponseBody();
		getMethod.releaseConnection();
		//
		String nombreFichero = numExpediente + "_576";

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.TIPO576);
		fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		fichero.setNombreDocumento(nombreFichero);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
		fichero.setSobreescribir(false);
		fichero.setFicheroByte(bytesPdf);

		gestorDocumentos.guardarByte(fichero);

		return bytesPdf;
	}

	@Override
	public byte[] descargarPdf(String csv) {
		byte[] result = null;
		if (csv != null && !csv.isEmpty()) {
			List<String> urls = getUrlsDescarga();
			for (int i = 0; i < urls.size() && result == null; i++) {
				String href = urls.get(i) + csv;
				if (log.isDebugEnabled()) {
					log.debug("Descargando pdf de la url: " + href);
				}
				try {
					result = descargarPdf(href);
				} catch (Exception e) {
					log.error("Ocurrio un error en la descarga de PDF", e);
				}
			}
		}
		if (result != null) {
			String resultado = new String(result);
			if (resultado != null && resultado.contains(XML_HEAD) && resultado.contains(XML_ERROR)) {
				log.error("La descarga del justificante 576 no ha obtenido un PDF: " + resultado);
				result = null;
			}
		}
		return result;
	}

	private List<String> getUrlsDescarga() {
		if (log.isDebugEnabled()) {
			log.debug("Recuperando URLs de " + URL);
		}
		List<String> urls = new ArrayList<String>();
		String url = null;
		// El bucle lo va a recorrer siempre para 0 y 1.
		for (int i = 0; url != null || i < 2; i++) {
			url = gestorPropiedades.valorPropertie(URL + (i == 0 ? "" : ("." + i)));
			if (url != null) {
				urls.add(url);
			}
		}
		return urls;
	}

	private String dameStringCeros(Integer num) {
		String ceros = "";
		if (num == null || num < 1) {
			return null;
		} else {
			for (int i = 1; i <= num; i++) {
				ceros += "0";
			}
			return ceros;
		}
	}
}