package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Formatter;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.ficheroSolicitud05.beans.ResultadoFicheroSolicitud05Bean;
import org.gestoresmadrid.oegam2comun.model.service.ServicioFicheroSolicitud05;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import general.utiles.UtilesCadenaCaracteres;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioFicheroSolicitud05Impl implements ServicioFicheroSolicitud05 {

	private static final long serialVersionUID = -5997252009845373925L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioFicheroSolicitud05Impl.class);

	private static final String IDENTIFICADOR_INICIO_DATOS = "<T00501>";
	private static final String IDENTIFICADOR_FIN_DATOS = "</T00501>";
	private static final String IDENTIFICADOR_MODELO = "005";
	private static final String TIPO_DECLARACION = " ";
	private static final String PERIODO = "0A";
	private static final String TIPO_TRANSPORTE = "V";
	private static final String NUEVO = "1";
	private static final String USADO = "2";
	private static final String VEHICULO_NACIONAL = "1";
	private static final String VEHICULO_UNION_EUROPEA = "2";
	private static final String VEHICULO_FUERA_UNION_EUROPEA = "3";
	private static final String DATOS_VACIO = " ";
	private static final String TIPO_TRAMITE_MATRICULACION = "T1";

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTrans;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatr;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoFicheroSolicitud05Bean generarDatosSolicitud05(TramiteTrafDto tramiteTrafDto, MarcaDgtDto marcaDgtDto) throws OegamExcepcion {
		ResultadoFicheroSolicitud05Bean resultado = new ResultadoFicheroSolicitud05Bean(Boolean.FALSE);
		try {
			StringBuffer sb = new StringBuffer();
			Formatter formatter = new Formatter(sb);
			// 1 - Posici�n 1. Longitud 8. RESERVADO para AEAT.(Etiqueta de Inicio de Datos del Modelo 576)
			formatter.format("%8S", IDENTIFICADOR_INICIO_DATOS);
			// 2 - Posici�n 9. Longitud 3. Inicio del identificador de modelo.
			formatter.format("%3S", IDENTIFICADOR_MODELO);
			// 3 - Posici�n 12. Longitud 1. Tipo declaraci�n
			formatter.format("%1S", TIPO_DECLARACION);
			// 4 - Posici�n 13. Longitud 9. RESERVADO para AEAT.(NIF)
			formatter.format("%9S", DATOS_VACIO);
			// 5 - Posici�n 22.Longitud 4. Ejercicio de Devengo
			formatter.format("%4S", tramiteTrafDto.getFechaIedtm().getAnio());
			// 6 - Posici�n 26.Longitud 4. RESERVADO para AEAT.(Letras etiqueta)
			formatter.format("%4S", DATOS_VACIO);
			// 7 - Posici�n 30.Longitud 2. Periodo
			formatter.format("%2S", PERIODO);
			// 8 - Posici�n 32.Longitud 1. Tipo de transporte(V:Vehiculos)
			formatter.format("%1S", TIPO_TRANSPORTE);
			// 9 - Posici�n 33.Longitud 9. Obligado tributario. NIF
			formatter.format("%-9S", tramiteTrafDto.getTitular().getPersona().getNif());
			// 10 - Posici�n 42.Longitud 40.Obligado tributario.Apellidos y nombre o Raz�n social
			String datosTitular = "";
			if ("FISICA".equals(tramiteTrafDto.getTitular().getPersona().getTipoPersona())) {
				datosTitular = tramiteTrafDto.getTitular().getPersona().getApellido1RazonSocial();
				datosTitular += " ";
				datosTitular += tramiteTrafDto.getTitular().getPersona().getApellido2();
				datosTitular += " ";
				datosTitular += tramiteTrafDto.getTitular().getPersona().getNombre();
			} else if ("JURIDICA".equals(tramiteTrafDto.getTitular().getPersona().getTipoPersona())) {
				datosTitular = tramiteTrafDto.getTitular().getPersona().getApellido1RazonSocial();
			}
			formatter.format("%-40S", UtilesCadenaCaracteres.reemplazarAcentoss(datosTitular));
			// 11 - Posici�n 82.Longitud 9. C�nyuge(para exenci�n RE1). NIF
			formatter.format("%-9S", DATOS_VACIO);
			// 12 - Posici�n 91.Longitud 40. Conyuge. Apellidos y nombre o Raz�n Social
			formatter.format("%-40S", DATOS_VACIO);
			// 13 - Posici�n 131.Longitud 66. RESERVADO para AEAT
			formatter.format("%66S", DATOS_VACIO);
			// 14 - Posici�n 197.Longitud 9. RESERVADO para AEAT(Representante. NIF.)
			formatter.format("%9S", DATOS_VACIO);
			// 15 - Posici�n 206.Longitud 40. RESERVADO para AEAT(Representante. Apellidos y nombre o Raz�n Social.)
			formatter.format("%-40S", DATOS_VACIO);
			// 16 - Posici�n 246.Longitud 30. N�mero de identificaci�n del titulo de familia numerosa
			formatter.format("%30S", DATOS_VACIO);
			// 17 - Posici�n 276.Longitud 8. Fecha de efectos del reconocimiento
			formatter.format("%8S", DATOS_VACIO);
			// 18 - Posici�n 284.Longitud 40. Organismo/Comunidad Aut�noma de reconocimiento
			formatter.format("%40S", DATOS_VACIO);
			// 19 - Posici�n 324.Longitud 37. RESERVADO para AEAT
			formatter.format("%37S", DATOS_VACIO);
			// 20 - Posici�n 361.Longitud 1. Medio de transporte Nuevo / Usado
			formatter.format("%1S", !tramiteTrafDto.getVehiculoDto().getVehiUsado() ? NUEVO : USADO);

			// 21 - Posici�n 362. Longitud 1. Lugar de adquisici�n del Veh�culo
			if (tramiteTrafDto.getVehiculoDto().getImportado()) {
				formatter.format("%1S", "0".equals(tramiteTrafDto.getVehiculoDto().getProcedencia()) ? VEHICULO_FUERA_UNION_EUROPEA : VEHICULO_UNION_EUROPEA);
			} else {
				formatter.format("%1S", VEHICULO_NACIONAL);
			}
			// 22 - Posici�n 363. Longitud 1. RESERVADO para AEAT
			formatter.format("%1S", DATOS_VACIO);
			// 23 - Posici�n 364. Longitud 8. Medio de transporte nuevo o usado.Fecha de primera matriculaci�n, puesta en servicio o primera utilizaci�n.
			if (tramiteTrafDto.getVehiculoDto().getVehiUsado()) {
				Fecha fecha = tramiteTrafDto.getVehiculoDto().getFechaPrimMatri();
				String fecha05 = fecha.getDia();
				fecha05 += fecha.getMes();
				fecha05 += fecha.getAnio();
				int fechaPriMatr = Integer.parseInt(fecha05);
				formatter.format("%8d", fechaPriMatr);
			} else {
				formatter.format("%8S", DATOS_VACIO);
			}
			// 24 - Posici�n 372. Longitud 40. C.M.T. Veh�culos. Marca
			formatter.format("%40S",marcaDgtDto.getMarcaSinEditar());
			// 25 - Posici�n 412. Longitud 80. C.M.T. Veh�culos. Modelo-Tipo
			formatter.format("%80S",tramiteTrafDto.getVehiculoDto().getModelo() + "-" + tramiteTrafDto.getVehiculoDto().getTipoVehiculo());
			// 26 - Posici�n 492. Longitud 22. C.M.T. Veh�culos. Num. Identificador(Bastidor)
			formatter.format("%-22S",tramiteTrafDto.getVehiculoDto().getBastidor());
			// 27 - Posici�n 514. Longitud 4. C.M.T. Veh�culos. Clasificaci�n por criterio de construcci�n y de utilizaci�n (RD	2822/1998).
			String criterios = tramiteTrafDto.getVehiculoDto().getCriterioConstruccion();
			criterios = criterios.concat(tramiteTrafDto.getVehiculoDto().getCriterioUtilizacion());
			formatter.format("%4S", criterios);
			// 28 - Posici�n 518. Longitud 2. C.M.T. Vehiculos. Clasificacion
			formatter.format("%2S",tramiteTrafDto.getVehiculoDto().getIdDirectivaCee());
			// 29 - Posici�n 520. Longitud 8. RESERVADO para AEAT
			formatter.format("%8S", DATOS_VACIO);
			// 30 - Posici�n 528. Longitud 5. C.M.T. Veh�culos. Emisiones CO2
			if (tramiteTrafDto.getVehiculoDto().getCo2() != null) {
				double num = Double.parseDouble(tramiteTrafDto.getVehiculoDto().getCo2());
				String str = String.valueOf(num);
				int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
				formatter.format("%5S", intNumber);
			} else {
				formatter.format("%5S", DATOS_VACIO);
			}

			// 31 - Posici�n 533. Longitud 2. RESERVADO para AEAT
			formatter.format("%2S", DATOS_VACIO);
			// 32 - Posici�n 535. Longitud 6. Kil�metros/N� Horas de uso
			if (tramiteTrafDto.getVehiculoDto().getVehiUsado()
					&& tramiteTrafDto.getVehiculoDto().getKmUso() != null
					&& tramiteTrafDto.getVehiculoDto().getKmUso().intValue() > 0) {
				formatter.format("%6d", tramiteTrafDto.getVehiculoDto().getKmUso().intValue());
			} else {
				formatter.format("%6S", DATOS_VACIO);
			}
			// 33 - Posici�n 541. Longitud 12. C.M.T. Veh�culos. N� de serie de Tarjeta ITV
			formatter.format("%12S", tramiteTrafDto.getVehiculoDto().getNumSerie());
			// 34 - Posici�n 553. Longitud 1. C.M.T. Veh�culos. Tipo tarjeta ITV 
			formatter.format("%1S", tramiteTrafDto.getVehiculoDto().getTipoTarjetaITV());
			// 35 - Posici�n 554. Longitud 1. RESERVADO para AEAT.C.M.T.Veh�culos. Motor de gasolina (1), diesel (2), otros (3) 
			String motor = null;
			if ("G".equals(tramiteTrafDto.getVehiculoDto().getCarburante())) {
				motor = "1";
			} else if ("D".equals(tramiteTrafDto.getVehiculoDto().getCarburante())) {
				motor = "2";
			} else {
				motor = "3";
			}
			formatter.format("%1S", motor);
			// 36 - Posici�n 555. Longitud 5.  RESERVADO para AEAT. C.M.T.Veh�culos. Cilindrada (c.c.)
			int cilindrada = Integer.parseInt(tramiteTrafDto.getVehiculoDto().getCilindrada());
			formatter.format("%5d", cilindrada);
			// 37 - Posici�n 560. Longitud 40. RESERVADO para AEAT. C.M.T. Embarcaciones. Fabricante o Importador
			formatter.format("%40S", DATOS_VACIO);
			// 38 - Posici�n 600. Longitud 80. C.M.T. Embarcaciones. Modelo
			formatter.format("%80S", DATOS_VACIO);
			// 39 - Posici�n 680. Longitud 22. C.M.T. Embarcaciones. Identificaci�n (N� construcci�n)
			formatter.format("%22S", DATOS_VACIO);
			// 40 - Posici�n 702. Longitud 5. C.M.T. Embarcaciones. Eslora m�xima (en metros)
			formatter.format("%5S", DATOS_VACIO); // Preguntar como es vac�o con los float
			// 41 - Posici�n 707. Longitud 40.C.M.T. Aeronaves. Fabricante.
			formatter.format("%40S", DATOS_VACIO);
			// 42 - Posici�n 747. Longitud 80. C.M.T. C.M.T. Aeronaves. Modelo
			formatter.format("%80S", DATOS_VACIO);
			// 43 - Posici�n 827. Longitud 22. C.M.T. Aeronaves. N� serie.
			formatter.format("%22S", DATOS_VACIO);
			// 44 - Posici�n 849. Longitud 22. C.M.T. Aeronaves. A�o fabricaci�n.
			formatter.format("%4S", DATOS_VACIO);
			// 45 - Posici�n 853. Longitud 5. C.M.T. Aeronaves. Peso m�ximo despegue (en Kg.)
			formatter.format("%10S", DATOS_VACIO);
			// 46 - Posici�n 863. Longitud 9. C.M.T. Veh�culos. C�digo ITV 
			formatter.format("%9S", DATOS_VACIO);
			// 47 - Posici�n 872. Longitud 223. RESERVADO para AEAT
			formatter.format("%223S", DATOS_VACIO);
			// 48 - Posici�n 1095. Longitud 13. Liquidaci�n. Base imponible.Es obligatorio pero el dato?
			BigDecimal baseImponible576 = obtenerBaseImponible(tramiteTrafDto);
			if (BigDecimal.ZERO.equals(baseImponible576)) {
				formatter.format("%13S", DATOS_VACIO);
			} else {
				formatter.format("%11.2f", baseImponible576);
			}
			// 49 - Posici�n 1108. Longitud 13. RESERVADO para AEAT  Liquidaci�n. Base imponible reducida.
			formatter.format("%13S", DATOS_VACIO);
			// 50 - Posici�n 1121. Longitud 5. RESERVADO para AEAT  Liquidaci�n. Tipo %.
			formatter.format("%5S", DATOS_VACIO);
			// 51 - Posici�n 1126. Longitud 13. RESERVADO para AEAT  Liquidaci�n. Cuota.
			formatter.format("%13S", DATOS_VACIO);
			// 52 - Posici�n 1139. Longitud 13. RESERVADO para AEAT  Deducci�n lineal
			formatter.format("%13S", DATOS_VACIO);
			// 53 - Posici�n 1152. Longitud 13. RESERVADO para AEAT  Liquidaci�n. Cuota a ingresar.
			formatter.format("%13S", DATOS_VACIO);
			// 54 - Posici�n 1165. Longitud 13. RESERVADO para AEAT   Liquidaci�n. A deducir
			formatter.format("%13S", DATOS_VACIO);
			// 55 - Posici�n 1178. Longitud 13. RESERVADO para AEAT  Liquidaci�n. Resultado de la liquidaci�n
			formatter.format("%13S", DATOS_VACIO);
			// 56 - Posici�n 1191. Longitud 13. RESERVADO para AEAT  Declaraci�n complementaria. N�mero de justificante de la declaraci�n anterior
			formatter.format("%13S", DATOS_VACIO);
			// 57 - Posici�n 1204. Longitud 22. RESERVADO para AEAT  NRC o N�mero de Justificante
			formatter.format("%22S", DATOS_VACIO);
			// 58 - Posici�n 1226. Longitud 8. RESERVADO para AEAT  CEM
			formatter.format("%8S", DATOS_VACIO);
			// 59 - Posici�n 1234. Longitud 30. RESERVADO para AEAT  N�mero de expediente o referencia
			formatter.format("%30S", DATOS_VACIO);
			// 60 - Posici�n 1264. Longitud 4. RESERVADO para AEAT  Declaraci�n. Fundamento no sujeci�n o exenci�n
			String idReduccion05 = obtenerIdReduccion(tramiteTrafDto.getNumExpediente(),tramiteTrafDto.getTipoTramite());
			formatter.format("%4S", idReduccion05);
			// 61 - Posicion 1268. Longitud 12. RESERVADO para AEAT  Modelo 006. Delegacion
			formatter.format("%12S", DATOS_VACIO);
			// 62 - Posici�n 1280. Longitud 25. RESERVADO para AEAT  Modelo 006. Administracion.
			formatter.format("%25S", DATOS_VACIO);
			// 63 - Posici�n 1305. Longitud 5. RESERVADO para AEAT  Modelo 006. Codigo de Administracion.)
			formatter.format("%5S", DATOS_VACIO);
			// 64 - Posici�n 1310. Longitud 30. RESERVADO para AEAT  Expediente AEAT
			formatter.format("%30S", DATOS_VACIO);
			// 65 - Posici�n 1340. Longitud 1. RESERVADO para AEAT  Modelo 576: Causa del Hecho Imponible.
			formatter.format("%1S", DATOS_VACIO);
			// 66 - Posici�n 1341. Longitud 8. RESERVADO para AEAT  Modelo 576. Fecha del devengo AAAAMMDD
			formatter.format("%8S", DATOS_VACIO);
			// 67 - Posici�n 1349. Longitud 9. NIF de la persona que ha introducido el vehiculo en Espa�a
			if ("A".equals(tramiteTrafDto.getVehiculoDto().getTipoTarjetaITV())) {
				formatter.format("%9S", tramiteTrafDto.getVehiculoDto().getNifIntegrador().getNif());
			} else {
				formatter.format("%9S", DATOS_VACIO);
			}
			// 68 - Posici�n 1358. Longitud 40. Apellidos y Nombre o Raz�n Social del Introductor del veh�culo en Espa�a.
			String introductor = "";
			if ("A".equals(tramiteTrafDto.getVehiculoDto().getTipoTarjetaITV())) {
				if ("FISICA".equals(tramiteTrafDto.getVehiculoDto().getNifIntegrador().getTipoPersona())) {
					introductor = tramiteTrafDto.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial();
					introductor += " ";
					introductor += tramiteTrafDto.getVehiculoDto().getNifIntegrador().getApellido2();
					introductor += " ";
					introductor += tramiteTrafDto.getVehiculoDto().getNifIntegrador().getNombre();
				} else if ("JURIDICA".equals(tramiteTrafDto.getVehiculoDto().getNifIntegrador().getTipoPersona())) {
					introductor = tramiteTrafDto.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial();
				}
				formatter.format("%40S", UtilesCadenaCaracteres.reemplazarAcentoss(introductor));
			} else {
				formatter.format("%40S", DATOS_VACIO);
			}
			// 69 - Posici�n 1398. RESERVADO para AEAT  Longitud 79. Diputaciones
			formatter.format("%79S", DATOS_VACIO);
			// 70 - Posici�n 1477. RESERVADO para AEAT  Longitud 5. Centro de Grabacion
			formatter.format("%5S", DATOS_VACIO);
			// 71 - Posici�n 1482. Longitud 2. C.M.T. Vehiculos. Observaciones
			String observacion = "";
			if ("quad".equals(tramiteTrafDto.getVehiculoDto().getIdDirectivaCee())) {
				observacion = "01";
			} else if ("33".equals(tramiteTrafDto.getVehiculoDto().getCriterioUtilizacion())) {
				observacion = "02";
			} else if ("48".equals(tramiteTrafDto.getVehiculoDto().getCriterioUtilizacion())) {
				observacion = "03";
			} else if (tramiteTrafDto.getVehiculoDto().getTipoVehiculo().startsWith("5")) {
				if ("53".equals(tramiteTrafDto.getVehiculoDto().getTipoVehiculo()) && "L5e".equals(tramiteTrafDto.getVehiculoDto().getIdDirectivaCee())) {
					observacion = "07";
				} else if (new BigDecimal(74).compareTo(tramiteTrafDto.getVehiculoDto().getPotenciaNeta()) > 0) {
					observacion = "05";
				} else {
					observacion = BigDecimal.valueOf(0.66).compareTo(tramiteTrafDto.getVehiculoDto().getPotenciaPeso()) > 0 ? "04" : "06";
				}
			} else {
				observacion = "00";
			}
			formatter.format("%2S", observacion);
			// 72 - Posici�n 1484. Longitud 2. RESERVADO para AEAT  C.M.T. Embarcaciones. Observaciones
			formatter.format("%2S", DATOS_VACIO);
			// 73 - Posici�n 1486. Longitud 22. RESERVADO para AEAT.
			formatter.format("%22S", DATOS_VACIO);
			// 74 - Posici�n 1508. Longitud 1. RESERVADO para AEAT.
			formatter.format("%1S", DATOS_VACIO);
			// 75 - Posici�n 1509. Longitud 9. RESERVADO para AEAT. (Fin Etiqueta de Inicio de Datos del Modelo 005)
			formatter.format("%9S", IDENTIFICADOR_FIN_DATOS);
			formatter.format("%1S", "\r\n");
			formatter.close();
			resultado.setsFichero(sb.toString());
		} catch (Exception e) {
			log.error("Hubo un error al recuperar los datos para generar le fichero Solicitud 05, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Hubo un error al recuperar los datos para generar le fichero Solicitud 05");
		}
		return resultado;
	}

	@Override
	public ResultadoFicheroSolicitud05Bean guardarFichero(byte[] bytesFichero, String nombreFichero) {
		ResultadoFicheroSolicitud05Bean resultado = new ResultadoFicheroSolicitud05Bean(Boolean.FALSE);
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.AEAT);
			fichero.setSubTipo(ConstantesGestorFicheros.AEAT_FICHEROSSOLICITUDES_05);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
			fichero.setNombreDocumento(nombreFichero);
			fichero.setFecha(utilesFecha.getFechaActual());
			fichero.setFicheroByte(bytesFichero);
			fichero.setSobreescribir(true);
			File file = gestorDocumentos.guardarFichero(fichero);
			if (file == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("Hubo un error al guardar el fichero Solicitud 05.");
			}
		} catch (OegamExcepcion e) {
			log.error("Hubo un error al guardar el fichero Solicitud 05, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Hubo un error al guardar el fichero Solicitud 05.");
		}
		return resultado;
	}

	@Override
	public ResultadoFicheroSolicitud05Bean descargarFichero(String nombreFichero) {
		ResultadoFicheroSolicitud05Bean resultado = new ResultadoFicheroSolicitud05Bean(Boolean.FALSE);
		try {
			if (nombreFichero != null && !nombreFichero.isEmpty()) {
				FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.AEAT, ConstantesGestorFicheros.AEAT_FICHEROSSOLICITUDES_05,
						utilesFecha.getFechaActual(), nombreFichero, ConstantesGestorFicheros.EXTENSION_TXT);
				if (fichero != null && fichero.getFile() != null) {
					resultado.setFichero(fichero.getFile());
					resultado.setNombreFichero(nombreFichero+ ConstantesGestorFicheros.EXTENSION_TXT);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No se ha podido recuperar el fichero para con el nombre:" +nombreFichero);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se ha podido recuperar el fichero porque el nombre del fichero no existe.");
			}
		} catch (Throwable e) {
			log.error("Hubo un error al recuperar los ficheros 05, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Hubo un error al recuperar los ficheros 05");
		}
		return resultado;
	}

	@Override
	public ResultadoFicheroSolicitud05Bean validarDatosObligatorios(TramiteTrafDto tramiteTraficoDto ,MarcaDgtDto marcaDgtDto) throws OegamExcepcion {
		ResultadoFicheroSolicitud05Bean resultBean = new ResultadoFicheroSolicitud05Bean(Boolean.FALSE);

		if (!"1".equals(tramiteTraficoDto.getEstado())) {
			resultBean.setError(Boolean.TRUE);
			resultBean.aniadirMensajeListaError("Solo se puede generar el Fichero con Tramites en estado INICIADO");
		} else if ("1".equals(tramiteTraficoDto.getEstado()) && !tramiteTraficoDto.getTipoTramite().equals(TIPO_TRAMITE_MATRICULACION)) {
			resultBean.setError(Boolean.TRUE);
			resultBean.aniadirMensajeListaError("Solo se puede generar el Fichero para los tipos de Tr�mites de Matriculaci�n");
		} else {
			if (tramiteTraficoDto.getTitular() == null || tramiteTraficoDto.getTitular().getPersona() == null) {
				resultBean.setError(Boolean.TRUE);
				resultBean.aniadirMensajeListaError("El nombre del Titular es un campo obligatorio para solicitar el modelo 05");
			} else {
				if (TipoPersona.Fisica.getValorEnum().equals(tramiteTraficoDto.getTitular().getPersona().getTipoPersona())
						&& tramiteTraficoDto.getTitular().getPersona().getNombre() == null) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("El nombre del Titular es un campo obligatorio para solicitar el modelo 05");
				}
				if (tramiteTraficoDto.getTitular().getPersona().getApellido1RazonSocial() == null) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("El primer apellido / raz�n social del titular es un campo obligatorio para solicitar el modelo 05");
				}
				if (TipoPersona.Fisica.getValorEnum().equals(tramiteTraficoDto.getTitular().getPersona().getTipoPersona())
						&& tramiteTraficoDto.getTitular().getPersona().getApellido2() == null) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("El segundo apellido del Titular es un campo obligatorio para solicitar el modelo 05");
				}
				if (tramiteTraficoDto.getVehiculoDto().getModelo() == null) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("El modelo del veh�culo es un campo obligatorio para solicitar el modelo 05");
				}
				if (marcaDgtDto.getMarcaSinEditar() == null) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("La marca del veh�culo es un campo obligatorio para solicitar el modelo 05");
				}
				if (tramiteTraficoDto.getVehiculoDto().getBastidor() == null) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("El bastidor del veh�culo es un campo obligatorio para solicitar el modelo 05");
				}
				if(tramiteTraficoDto.getVehiculoDto().getIdDirectivaCee() == null){
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("La categor�a EU del veh�culo es un campo obligatorio para solicitar el modelo 05");
				}
				if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoDto.getTipoTramite())
						&& !TipoTramiteTrafico.TransmisionElectronica.getValorEnum()
								.equals(tramiteTraficoDto.getTipoTramite())) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("La categor�a EU del veh�culo es un campo obligatorio para solicitar el modelo 05");
				}
				String idReduccion05 = obtenerIdReduccion(tramiteTraficoDto.getNumExpediente(),tramiteTraficoDto.getTipoTramite());
				if (idReduccion05 == null) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("No hay ninguna exenci�n marcada para generar el modelo 05");
				} else if (idReduccion05 != null && !idReduccion05.isEmpty() && !"ER3".equals(idReduccion05)) {
					resultBean.setError(Boolean.TRUE);
					resultBean.aniadirMensajeListaError("El modelo 05 no se puede generar para ese tipo de Exenci�n");
				}
			}
		}
		return resultBean;
	}

	private String obtenerIdReduccion(BigDecimal numExpediente, String tipoTramite) throws OegamExcepcion {
		String idReduccion05 = null;
		if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)){
			TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatr.getTramiteMatriculacion(numExpediente, Boolean.FALSE, Boolean.FALSE);
			if(tramiteTrafMatrDto != null && tramiteTrafMatrDto.getIdReduccion05() != null && !tramiteTrafMatrDto.getIdReduccion05().isEmpty()){
				idReduccion05 = tramiteTrafMatrDto.getIdReduccion05();
			}
		} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
			TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTrans.getTramiteTransmision(numExpediente, Boolean.FALSE);
			if (tramiteTrafTranDto != null && tramiteTrafTranDto.getIdReduccion05() != null && !tramiteTrafTranDto.getIdReduccion05().isEmpty()) {
				idReduccion05 = tramiteTrafTranDto.getIdReduccion05();
			}
		}
		return idReduccion05;
	}

	private BigDecimal obtenerBaseImponible(TramiteTrafDto tramiteTrafDto) throws OegamExcepcion {
		BigDecimal baseImponible = null;
		if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafDto.getTipoTramite())) {
			TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatr.getTramiteMatriculacion(tramiteTrafDto.getNumExpediente(), Boolean.FALSE, Boolean.FALSE);
			if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getIdReduccion05() != null && !tramiteTrafMatrDto.getIdReduccion05().isEmpty()) {
				baseImponible = tramiteTrafMatrDto.getBaseImponible576();
			}
		}
		return baseImponible;
	}

}