package org.gestoresmadrid.oegam2comun.model.service.impl;

import general.utiles.UtilesCadenaCaracteres;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.constantes.ConstantesTrafico;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.ficheroAEAT.bean.FicheroAEATBean;
import org.gestoresmadrid.core.intervinienteTrafico.model.dao.IntervinienteTraficoDao;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioFicheroAEAT;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trafico.beans.ResumenErroresFicheroAEAT;
import utilidades.estructuras.Fecha;

@Service
@Transactional
public class ServicioFicheroAEATImpl implements ServicioFicheroAEAT {

	private static final String INICIO_IDENTIFICADOR_MODELO_PAGINA = "<T";
	private static final String MODELO_COMUNICACION_VISADO = "VEG";
	private static final String PAGINA = "01";
	private static final String FIN_IDENTIFICADOR_MODELO = ">";
	private static final String FIN_IDENTIFICADOR_REGISTRO = "</TVEG01>";
	private static final String DATOS_VACIO = " ";
	private static final String TRAMITE_VISADO = "006";
	private static final String TIPO_INTERVINIENTE_TITULAR = "004";
	private static final String TIPO_INTERVINIENTE_ADQUIRIENTE = "005";
//	private static final String ESTADO_INICIADO = "1";
	private static final String TIPO_TRAMITE_MATRICULACION = "T1";
	private static final String TIPO_TRAMITE_TRANSMISION_ELECTRONICA = "T7";

	private static final String ETIQUETA_INICIO_MODELO = "<T00601>";
	private static final String TIPO_DECLARACION = "I";
	private static final String IDENTIFICADOR_MODELO = "006";
	private static final String PERIODO = "0A";
	private static final String TIPO_TRANSPORTE = "V";
//	private static final int EJERCICIO_DEVENGO = 2015;
	private static final String VEHICULO_NUEVO = "1";
	private static final String VEHICULO_USADO = "2";
	private static final String VEHICULO_NACIONAL = "1";
	private static final String VEHICULO_UNION_EUROPEA = "2";
	private static final String VEHICULO_FUERA_UNION_EUROPEA = "3";
	private static final String FIN_IDENTIFICADOR_REGISTRO_NRE = "</T00601>";

	@Autowired
	private TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	private PersonaDao personaDao;

	@Autowired
	private IntervinienteTraficoDao intervinienteTraficoDao;

	@Override
	public byte[] generarFichero(List<FicheroAEATBean> listaFicherosAEATBean) {
		String datos = "";
		for (FicheroAEATBean ficheroAEATBean: listaFicherosAEATBean) {
			datos = datos + generarDatos(ficheroAEATBean);
		}
		return datos.getBytes();
	}

	@Override
	public String generarDatos(FicheroAEATBean ficherosAEATBean) {
		StringBuffer sb = new StringBuffer();
		Formatter formatter = new Formatter(sb);
		// 1 - Posición 1. Longitud 2. Inicio del identificador de modelo y página.
		formatter.format("%2S", INICIO_IDENTIFICADOR_MODELO_PAGINA);
		// 2 - Posición 3. Longitud 3. Modelo comunicación de visado de documentación
		formatter.format("%3S", MODELO_COMUNICACION_VISADO);
		// 3 - Posición 6. Longitud 2. Página.
		formatter.format("%2S", PAGINA);
		// 4 - Posición 8. Longitud 1. Fin de identificador de modelo.
		formatter.format("%1S", FIN_IDENTIFICADOR_MODELO);
		// 5 - Posición 9. Longitud 3. Trámite del visado
		formatter.format("%3S",TRAMITE_VISADO);
		// 6 - Posición 12. Longitud 9. NIF del suejto pasivo, titular del trámite o representante legal
		formatter.format("%-9S", getNifTitular(ficherosAEATBean));
		// 7 - Posición 21. Longitud 30. Apellidos del sujeto pasivo, titular del trámite o representante legal; o Razon Social.
		formatter.format("%-30S", getApeRazonSocialTitular(ficherosAEATBean));
		// 8 - Posición 51. Longitud 15. Nombre del sujeto pásivo, titular del trámite o representante legal; o Razon Social.
		formatter.format("%-15S",getNombreTitular(ficherosAEATBean));
		// 9 - Posición 66. Longitud 9. NIF del colegiado
		formatter.format("%-9S", getNifColegiado(ficherosAEATBean));
		// 10 - Posición 75. Longitud 30. Apellidos del colegiado; o Razón Social.
		formatter.format("%-30S", getApeRazonSocialColegiado(ficherosAEATBean));
		// 11 - Posición 105. Longitud 15. Nombre del colegiado; o Raóon Social.
		formatter.format("%-15S", getNombreColegiado(ficherosAEATBean));
		// 12 - Posición 120. Longitud 8. Fecha de recepción de la documentación a visar en COGA.
		formatter.format("%-8S", getFechaPresentacion(ficherosAEATBean));
		// 13 - Posicion 128. Longitud 25. Referencia del COGA
		formatter.format("%-25S", ficherosAEATBean.getTramiteTraficoVO().getNumExpediente());
		// 14 - Posición 153. Longitud 50. Dato específico del trámite en concreto.
		formatter.format("%-50S", getBastidor(ficherosAEATBean));
		// 15 - Posición 203. Longitud 39. Reservado para la AEAT. Rellena con Blancos
		formatter.format("%-39S", DATOS_VACIO);
		// 16 - Posición 242. Longitud 9. Identificador de fin de registro.
		formatter.format("%-9S", FIN_IDENTIFICADOR_REGISTRO);
		// 17 - Posicion 251. Longitud 3. Identificador de fin de registro.
		//formatter.format("%1S", "\r\n");

		formatter.close();

		return sb.toString();
	}

	public String getApeRazonSocialTitular(FicheroAEATBean ficherosAEATBean) {
		String apeRazonSocialTitular = ficherosAEATBean.getDatosTitular() != null ? (ficherosAEATBean.getDatosTitular().getApellido2() != null ? ficherosAEATBean.getDatosTitular().getApellido1RazonSocial() + " " + ficherosAEATBean.getDatosTitular().getApellido2()
				: ficherosAEATBean.getDatosTitular().getApellido1RazonSocial()) : "";
		apeRazonSocialTitular = UtilesCadenaCaracteres.reemplazarAcentoss(apeRazonSocialTitular.toUpperCase().replace("-", " ").replace(".", ""));
		if (!apeRazonSocialTitular.equals(" ") && apeRazonSocialTitular.length() > 30) {
			apeRazonSocialTitular = apeRazonSocialTitular.substring(0, 30);
		}
		return apeRazonSocialTitular;
	}

	public String getNombreTitular(FicheroAEATBean ficherosAEATBean) {
		String nombreTitular = ficherosAEATBean.getDatosTitular() != null ? (ficherosAEATBean.getDatosTitular().getNombre() != null ? 
				ficherosAEATBean.getDatosTitular().getNombre() : " ") : " ";
		nombreTitular = UtilesCadenaCaracteres.reemplazarAcentoss(nombreTitular.toUpperCase().replace("-", " ").replace(".", ""));

		if (!nombreTitular.equals(" ") && nombreTitular.length() > 15) {
			nombreTitular = nombreTitular.substring(0, 15);
		}

		return nombreTitular;
	}

	public String getNifTitular(FicheroAEATBean ficherosAEATBean) {
		String nifTitular =  ficherosAEATBean.getDatosTitular() != null && ficherosAEATBean.getDatosTitular().getId() != null
				? ficherosAEATBean.getDatosTitular().getId().getNif() : " ";

		nifTitular = UtilesCadenaCaracteres.quitarCaracteresExtranios(nifTitular);
		return nifTitular;
	}

	public String getNifColegiado(FicheroAEATBean ficherosAEATBean){
		String nifColegiado = ficherosAEATBean.getDatosColegiado() != null ? ficherosAEATBean.getDatosColegiado().getId().getNif() : " ";

		nifColegiado = UtilesCadenaCaracteres.quitarCaracteresExtranios(nifColegiado);
		return nifColegiado;
	}

	public String getApeRazonSocialColegiado(FicheroAEATBean ficherosAEATBean) {
		String apeRazonSocialColegiado = ficherosAEATBean.getDatosColegiado() != null ? (ficherosAEATBean.getDatosColegiado().getApellido2() != null ? ficherosAEATBean.getDatosColegiado().getApellido1RazonSocial() + " " + ficherosAEATBean.getDatosColegiado().getApellido2()
			: ficherosAEATBean.getDatosColegiado().getApellido1RazonSocial()) : "";

		apeRazonSocialColegiado = UtilesCadenaCaracteres.reemplazarAcentoss(apeRazonSocialColegiado.toUpperCase().replace("-", " ").replace(".", ""));

		if (!apeRazonSocialColegiado.equals(" ") && apeRazonSocialColegiado.length() > 30) {
			apeRazonSocialColegiado = apeRazonSocialColegiado.substring(0, 30);
		}

		return apeRazonSocialColegiado;
	}

	public String getNombreColegiado(FicheroAEATBean ficherosAEATBean) {
		String nombreColegiado = ficherosAEATBean.getDatosColegiado() != null ? (ficherosAEATBean.getDatosColegiado().getNombre() != null ?
				ficherosAEATBean.getDatosColegiado().getNombre() : " ") : " ";

		nombreColegiado = UtilesCadenaCaracteres.reemplazarAcentoss(nombreColegiado.toUpperCase().replace("-", " ").replace(".", ""));

		if (!nombreColegiado.equals(" ") && nombreColegiado.length() > 15) {
			nombreColegiado = nombreColegiado.substring(0, 15);
		}

		return nombreColegiado;
	}

	public String getFechaPresentacion(FicheroAEATBean ficherosAEATBean) {
		java.text.SimpleDateFormat formatterFecha = new java.text.SimpleDateFormat("yyyyMMdd");
		return ficherosAEATBean.getTramiteTraficoVO().getFechaPresentacion() != null ? 
				formatterFecha.format(ficherosAEATBean.getTramiteTraficoVO().getFechaPresentacion()) : "";
	}

	public String getBastidor(FicheroAEATBean ficherosAEATBean) {
		return ficherosAEATBean.getTramiteTraficoVO().getVehiculo() != null ? 
				ficherosAEATBean.getTramiteTraficoVO().getVehiculo().getBastidor() : "";
	}

	@Override
	public List<FicheroAEATBean> obtenerDatos(String[] codSeleccionados, List<ResumenErroresFicheroAEAT> listResumenErroresFicheroAEAT) {
		List<FicheroAEATBean> listaFicheroAEAT = new ArrayList<>();
		List<Criterion> listCriterion = new ArrayList<>();
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		for (String numExp: codSeleccionados) {
			ResumenErroresFicheroAEAT resumenErroresFicheroAEAT = new ResumenErroresFicheroAEAT();
			FicheroAEATBean fichAEATBean = new FicheroAEATBean();

			listCriterion.add(Restrictions.eq("numExpediente", new BigDecimal(numExp)));

			listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));

			List<TramiteTraficoVO> resultTramiteTraf =  tramiteTraficoDao.buscarPorCriteria(listCriterion, listaAlias, null);

			listCriterion.clear();
			listaAlias.clear();

			TramiteTraficoVO tramiteTraficoVO = resultTramiteTraf.isEmpty() ? null : resultTramiteTraf.get(0);
			VehiculoVO vehiculoVO = tramiteTraficoVO != null ? tramiteTraficoVO.getVehiculo() : null;
			PersonaVO colegiado = new PersonaVO();
			PersonaVO titular = new PersonaVO();

			if (tramiteTraficoVO == null) {
				resumenErroresFicheroAEAT.setError(true);
				resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
				resumenErroresFicheroAEAT.setDescripcionError("No existe ningun Tramite Iniciado con ese número de Expediente.");
				listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
			} else if (!tramiteTraficoVO.getTipoTramite().equals(TIPO_TRAMITE_MATRICULACION) && !tramiteTraficoVO.getTipoTramite().equals(TIPO_TRAMITE_TRANSMISION_ELECTRONICA)) {
				resumenErroresFicheroAEAT.setError(true);
				resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
				resumenErroresFicheroAEAT.setDescripcionError("Solo se puede generar el Fichero con con los tipos de Tramites de Matriculación y Transmisión Electrónica.");
				listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
			}/*else if(!tramiteTraficoVO.getEstado().equals(new BigDecimal(ESTADO_INICIADO))){
				resumenErroresFicheroAEAT.setError(true);
				resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
				resumenErroresFicheroAEAT.setDescripcionError("Solo se puede generar el Fichero con Tramites en estado INICIADO.");
				listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
			}*/else if (tramiteTraficoVO.getFechaPresentacion() == null) {
				resumenErroresFicheroAEAT.setError(true);
				resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
				resumenErroresFicheroAEAT.setDescripcionError("La fecha de recepción en el COGA no puede ser nula.");
				listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
			} else if (vehiculoVO == null || StringUtils.isBlank(vehiculoVO.getBastidor())) {
				resumenErroresFicheroAEAT.setError(true);
				resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
				resumenErroresFicheroAEAT.setDescripcionError("No existe todavia Bastidor del vehiculo.");
				listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
			} else if (vehiculoVO.getBastidor().length() > 17) {
				resumenErroresFicheroAEAT.setError(true);
				resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
				resumenErroresFicheroAEAT.setDescripcionError("El Bastidor tiene una longitud superior a 17 caracteres.");
				listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
			} else {
				listCriterion.add(Restrictions.eq("id.numColegiado", tramiteTraficoVO.getNumColegiado()));
				listCriterion.add(Restrictions.eq("id.nif", tramiteTraficoVO.getContrato().getColegiado().getUsuario().getNif()));

				List<PersonaVO> resultPersona = personaDao.buscarPorCriteria(listCriterion,null, null);

				colegiado = resultPersona.isEmpty() ? null : resultPersona.get(0);

				listCriterion.clear();
				listaAlias.clear();

				if (colegiado == null) {
					resumenErroresFicheroAEAT.setError(true);
					resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
					resumenErroresFicheroAEAT.setDescripcionError("No existen datos para el Colegiado");
					listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
				} else {
					listCriterion.add(Restrictions.eq("id.numExpediente", tramiteTraficoVO.getNumExpediente()));
					if (tramiteTraficoVO.getTipoTramite().equals(TIPO_TRAMITE_MATRICULACION)) {
						listCriterion.add(Restrictions.eq("id.tipoInterviniente", TIPO_INTERVINIENTE_TITULAR));
					} else if (tramiteTraficoVO.getTipoTramite().equals(TIPO_TRAMITE_TRANSMISION_ELECTRONICA)){
						listCriterion.add(Restrictions.eq("id.tipoInterviniente", TIPO_INTERVINIENTE_ADQUIRIENTE));
					}
					listCriterion.add(Restrictions.eq("id.numColegiado", tramiteTraficoVO.getNumColegiado()));

					listaAlias.add(new AliasQueryBean(DireccionVO.class, "direccion", "direccion", CriteriaSpecification.LEFT_JOIN));
					listaAlias.add(new AliasQueryBean(PersonaVO.class, "persona", "persona", CriteriaSpecification.LEFT_JOIN));

					List<IntervinienteTraficoVO> resultInterTrafico = intervinienteTraficoDao.buscarPorCriteria(listCriterion, listaAlias, null);

					IntervinienteTraficoVO interTraficoVO = resultInterTrafico.isEmpty() ? null : resultInterTrafico.get(0);

					titular = interTraficoVO != null ? interTraficoVO.getPersona() : null;

					listCriterion.clear();
					listaAlias.clear();

					if (titular == null) {
						resumenErroresFicheroAEAT.setError(true);
						resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
						resumenErroresFicheroAEAT.setDescripcionError("No existen datos para el Titular");
						listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
					} else {
						fichAEATBean.setTramiteTraficoVO(tramiteTraficoVO);
						fichAEATBean.setDatosColegiado(colegiado);
						fichAEATBean.setDatosTitular(titular);
						listaFicheroAEAT.add(fichAEATBean);
						resumenErroresFicheroAEAT.setError(false);
						resumenErroresFicheroAEAT.setNumExpedienteError(numExp);
						resumenErroresFicheroAEAT.setDescripcionError("El Expediente se ha cargado correctamente en el Fichero");
						listResumenErroresFicheroAEAT.add(resumenErroresFicheroAEAT);
					}
				}
			}
		}
		return listaFicheroAEAT;
	}

	@Override
	public byte[] generarFicheroLiquidacionNRE06(TramiteTrafMatrDto tramite) {
		String datos = generarDatosLiquidacionNRE06(tramite);
		return datos.getBytes();
	}
	// Mantis 19591. David Sierra Liquidacion NRE 06. Generacion de datos
	public String generarDatosLiquidacionNRE06(TramiteTrafMatrDto tramite) {
		StringBuffer sb = new StringBuffer();
		Formatter formatter = new Formatter(sb);
		// 1 - Posición 1. Longitud 8. Etiqueta de Inicio de Datos del Modelo 576
		formatter.format("%8S", ETIQUETA_INICIO_MODELO);
		// 2 - Posición 9. Longitud 3. Identificador de modelo
		formatter.format("%3S", IDENTIFICADOR_MODELO);
		// 3 - Posición 12. Longitud 1. Tipo declaracion."I" (Ingreso) o "N" (Negativa)
		formatter.format("%1S", TIPO_DECLARACION); 
		// 4 - Posición 13. Longitud 9. Identificación - N.I.F.
		formatter.format("%9S", DATOS_VACIO);
		// 5 - Posición 22. Longitud 4. Ejercicio de Devengo.
		formatter.format("%4S", tramite.getFechaIedtm().getAnio());
		// 6 - Posición 26. Longitud 4. Letras etiquetas.
		formatter.format("%4S", DATOS_VACIO);
		// 7 - Posición 30. Longitud 2. Periodo.
		formatter.format("%2S", PERIODO);
		// 8 - Posición 32. Tipo de transporte
		formatter.format("%1S",TIPO_TRANSPORTE);
		// 9 - Posición 33. Longitud 9. Obligado tributario. NIF.
		formatter.format("%9S", tramite.getTitular().getPersona().getNif());
		// 10 - Posición 42. Longitud 40. Obligado tributario. Apellidos y nombre o Razón social
		String datosTitular ="";
		if ("FISICA".equals(tramite.getTitular().getPersona().getTipoPersona())) {
			datosTitular = tramite.getTitular().getPersona().getApellido1RazonSocial();
			datosTitular += " ";
			datosTitular += tramite.getTitular().getPersona().getApellido2();
			datosTitular += " ";
			datosTitular += tramite.getTitular().getPersona().getNombre();
		} else if ("JURIDICA".equals(tramite.getTitular().getPersona().getTipoPersona())) {
			datosTitular = tramite.getTitular().getPersona().getApellido1RazonSocial();
		}
		formatter.format("%40S", UtilesCadenaCaracteres.reemplazarAcentoss(datosTitular));
		// 11 - Posición 82. Longitud 115. RESERVADO para AEAT
		formatter.format("%115S", DATOS_VACIO);
		// 12 - Posición 197. Longitud 9. RESERVADO para AEAT
		formatter.format("%9S", DATOS_VACIO);
		// 13 - Posición 206. Longitud 40. RESERVADO para AEAT
		formatter.format("%40S", DATOS_VACIO);
		// 14 - Posición 246. Longitud 115. RESERVADO para AEAT
		formatter.format("%115S", DATOS_VACIO);
		// 15 - Posición 361. Longitud 1. Ej. Devengo < 2.008: C.M.T. Medio de transporte nuevo Ej. Devengo >= 2.008: Medio de transporte Nuevo / Usado.

		formatter.format("%1S",
				Integer.parseInt(tramite.getFechaIedtm().getAnio()) < ConstantesTrafico.ANIO_LIMITE_DEVENGO
						? DATOS_VACIO
						: !tramite.getVehiculoDto().getVehiUsado() ? VEHICULO_NUEVO : VEHICULO_USADO);

		// 16 - Posición 362. Longitud 1. Ej.Devengo < 2.008: C.M.T. Adquirido en España o adquirido en un Estado distinto de España. Ej.Devengo >= 2.008: Lugar de adquisición del Vehículo.
		if (Integer.parseInt(tramite.getFechaIedtm().getAnio()) < ConstantesTrafico.ANIO_LIMITE_DEVENGO) {
			formatter.format("%1S", DATOS_VACIO);
		} else {
			if ("S".equals(tramite.getVehiculoDto().getImportado())) {
				formatter.format("%1S", "0".equals(tramite.getVehiculoDto().getProcedencia()) ? VEHICULO_FUERA_UNION_EUROPEA : VEHICULO_UNION_EUROPEA);
			} else {
				formatter.format("%1S", VEHICULO_NACIONAL);
			}
		}
		// 17 - Posición 363. Longitud 1. C.M.T. M.transp.us.
		formatter.format("%1S", DATOS_VACIO);

		// 18 - Posición 364. Longitud 8. Fecha de primera matriculación, puesta en servicio o primera utilización.
		if(Integer.parseInt(tramite.getFechaIedtm().getAnio()) < ConstantesTrafico.ANIO_LIMITE_DEVENGO) {
			if(tramite.getVehiculoDto().getVehiUsado()) {
				Fecha fecha = tramite.getVehiculoDto().getFechaPrimMatri();
				String fechaNRE06 = fecha.getDia();
				fechaNRE06 += fecha.getMes();
				fechaNRE06 += fecha.getAnio();
				int fechaPriMatr = Integer.parseInt(fechaNRE06);
				formatter.format("%8d", fechaPriMatr);
			}
		} else {
			if (tramite.getVehiculoDto().getVehiUsado()) {
				Fecha fecha = tramite.getVehiculoDto().getFechaPrimMatri();
				String fechaNRE06 = fecha.getDia();
				fechaNRE06 += fecha.getMes();
				fechaNRE06 += fecha.getAnio();
				int fechaPriMatr = Integer.parseInt(fechaNRE06);
				formatter.format("%8d", fechaPriMatr);
			} else {
				formatter.format("%8d", tramite.getVehiculoDto().getFechaMatriculacion());
			}
		}

		// 19 - Posición 372. Longitud 40. C.M.T. Vehiculos. Marca // que marca va la marca o marca_sin_editar
		formatter.format("%40S", tramite.getVehiculoDto().getMarcaSinEditar());
		// 20 - Posición 412. Longitud 80. C.M.T. Vehiculos. Modelo-Tipo
		formatter.format("%80S",tramite.getVehiculoDto().getModelo() + "-" + tramite.getVehiculoDto().getTipoVehiculo());
		// 21 - Posición 492. Longitud 22. C.M.T. Vehiculos. Bastidor
		formatter.format("%-22S", tramite.getVehiculoDto().getBastidor());
		// 22 - Posición 514. Longitud 40. Ejercicio Devengo < 2.008: C.M.T. Vehículos. Clasificación
		if (Integer.parseInt(tramite.getFechaIedtm().getAnio()) < ConstantesTrafico.ANIO_LIMITE_DEVENGO) {
			formatter.format("%40S", DATOS_VACIO);
		} else {
			// 22.1 - Posición 514. Longitud 4. C.M.T. Vehiculos. Clasificacion por criterio de construccion y de utilizacion
			String criterios = tramite.getVehiculoDto().getCriterioConstruccion();
			criterios = criterios.concat(tramite.getVehiculoDto().getCriterioUtilizacion());
			formatter.format("%4S", criterios);
			// 22.2 - Posición 518. Longitud 2. C.M.T. Vehiculos. Clasificacion según Directiva 70/156/CEE obligatorio
			formatter.format("%2S", tramite.getVehiculoDto().getIdDirectivaCee());
			// 22.3 - Posición 520. Longitud 8. Reservado AEAT
			formatter.format("%8S", DATOS_VACIO);
			// 22.4 - Posición 528. Longitud 5. Emisiones CO2
			if (tramite.getVehiculoDto().getCo2() != null) {
				double emisiones = Double.parseDouble(tramite.getVehiculoDto().getCo2());
				formatter.format("%5f", emisiones); // Mirar
			} else {
				formatter.format("%5S", DATOS_VACIO);
			}
			// 22.5 - Posicion 533. Longitud 2. Epigrafe
			String epigrafe = "";
			if (tramite.getVehiculoDto().getEpigrafe().length() == 1) {
				epigrafe = "0";
				epigrafe = epigrafe.concat(tramite.getVehiculoDto().getEpigrafe());
			} else {
				epigrafe = tramite.getVehiculoDto().getEpigrafe();
			}
			formatter.format("%2S", epigrafe);
			// 22.6 - Posición 535. Longitud 6. Kilometros/Nº Horas de uso
			if (tramite.getVehiculoDto().getVehiUsado()) {
				if (tramite.getVehiculoDto().getKmUso() != null && tramite.getVehiculoDto().getKmUso().intValue() > 0) {
					formatter.format("%6d", tramite.getVehiculoDto().getKmUso().intValue());
				}
			} else {
				formatter.format("%6S", DATOS_VACIO);
			}
			// 22.7 - Posición 541. Longitud 12. C.M.T. Vehiculos. Nº de serie de Tarjeta ITV
			formatter.format("%12S", tramite.getVehiculoDto().getNumSerie());
			// 22.8 - Posición 553. Longitud 1. C.M.T. Vehiculos. Tipo tarjeta ITV
			formatter.format("%1S", tramite.getVehiculoDto().getTipoItv());
		}

		// 23 - Posición 554. Longitud 1. C.M.T. Vehículos. Motor de gasolina, diesel, otros
		String motor = null;
		if ("G".equals(tramite.getVehiculoDto().getCarburante())) {
			motor = "1";
		} else if ("D".equals(tramite.getVehiculoDto().getCarburante())) {
			motor = "2";
		} else {
			motor = "3";
		}
		formatter.format("%1S", motor);
		// 24 - Posición 555. Longitud 5. C.M.T. Vehiculos. Cilindrada (c.c.)
		int cilindrada = Integer.parseInt(tramite.getVehiculoDto().getCilindrada());
		formatter.format("%5d", cilindrada);
		// 25 - Posición 560. Longitud 40. C.M.T. Embarcaciones. Fabricante o Importador
		formatter.format("%40S", DATOS_VACIO);
		// 26 - Posición 600. Longitud 80. C.M.T. Embarcaciones. Modelo
		formatter.format("%80S", DATOS_VACIO);
		// 27 - Posición 680. Longitud 22. C.M.T. Embarcaciones. Identificación (Nº construccion)
		formatter.format("%22S", DATOS_VACIO);
		// 28 - Posición 702. Longitud 5. C.M.T. Embarcaciones. Eslora máxima (en metros)
		formatter.format("%5S", DATOS_VACIO); // Preguntar como es vacío con los float
		// 29 - Posición 707. Longitud 40.C.M.T. Aeronaves. Fabricante.
		formatter.format("%40S", DATOS_VACIO);
		// 30 - Posición 747. Longitud 80. C.M.T. C.M.T. Aeronaves. Modelo
		formatter.format("%80S", DATOS_VACIO);
		// 31 - Posición 827. Longitud 22. C.M.T. Aeronaves. Nº serie.
		formatter.format("%22S", DATOS_VACIO);
		// 32 - Posición 849. Longitud 22. C.M.T. Aeronaves. Año fabricación.
		formatter.format("%22S", DATOS_VACIO);
		// 33 - Posición 853. Longitud 5. C.M.T. Aeronaves. Peso máximo despegue (en Kg.)
		formatter.format("%10S", DATOS_VACIO);
		// 34 - Posición 863. Longitud 9. C.M.T. Vehículos. Codigo ITV 
		formatter.format("%9S", DATOS_VACIO);
		// 35 - Posición 872. Longitud 223. RESERVADO para AEAT 
		formatter.format("%223S", DATOS_VACIO);
		// 36 - Posición 1095. Longitud 13. Liquidación. Base imponible.
		formatter.format("%11.2f", tramite.getBaseImponible576());
		// 37 - Posición 1108. Longitud 83. RESEVADO para AEAT (Liquidación)
		formatter.format("%83S", DATOS_VACIO);
		// 38 - Posición 1191. Longitud 13. Declación complementaria. Número de justificante de la declaración anterior
		formatter.format("%13S", DATOS_VACIO);
		// 39 - Posición 1204. Longitud 22. RESERVADO para AEAT NRC o Número de Justificante
		formatter.format("%22S", DATOS_VACIO);
		// 40 - Posición 1226. Longitud 8. RESERVADO para AEAT CEM
		formatter.format("%8S", DATOS_VACIO);
		// 41 - Posición 1234. Longitud 30. RESERVADO para AEAT Numero de expediente o referencia
		formatter.format("%30S", DATOS_VACIO);
		// 42 - Posición 1264. Longitud 4. RESERVADO para AEAT Modelo 006.Declaración. Fundamento no sujeción o exención
		formatter.format("%4S", DATOS_VACIO);
		// 43 - Posición 1268. Longitud 12. RESERVADO para AEAT Modelo 006. Delegación
		formatter.format("%12S", DATOS_VACIO);
		// 44 - Posición 1280. Longitud 25. RESERVADO para AEAT Modelo 006. Administración.
		formatter.format("%25S", DATOS_VACIO);
		// 45 - Posición 1305. Longitud 5. RESERVADO para AEAT Modelo 006. Código de Administración.)
		formatter.format("%5S", DATOS_VACIO);
		// 46 - Posición 1310. Longitud 30. RESERVADO para AEAT Expediente AEAT
		formatter.format("%30S", DATOS_VACIO);
		// 47 - Posición 1340. Longitud 1. RESERVADO para AEAT 
		formatter.format("%1S", DATOS_VACIO);
		// 48 - Posición 1341. Longitud 8. DATOS_VACIO para futura modific. Fecha del devengo AAAAMMDD
		formatter.format("%8S", DATOS_VACIO);
		// 49 - Posición 1349. Longitud 9. NIF de la persona que ha introducido el vehículo en España
		if ("A".equals(tramite.getVehiculoDto().getTipoItv())) {
			formatter.format("%9S", tramite.getVehiculoDto().getNifIntegrador().getNif());
		} else {
			formatter.format("%9S", DATOS_VACIO);
		}
		// 50 - Posición 1358. Longitud 40. Apellidos y Nombre o Razón Social del Introductor del vehículo en España.
		String introductor = "";
		if ("A".equals(tramite.getVehiculoDto().getTipoItv())) {
			if ("FISICA".equals(tramite.getVehiculoDto().getNifIntegrador().getTipoPersona())) {
				introductor = tramite.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial();
				introductor += " ";
				introductor += tramite.getVehiculoDto().getNifIntegrador().getApellido2();
				introductor += " ";
				introductor += tramite.getVehiculoDto().getNifIntegrador().getNombre();
			} else if ("JURIDICA".equals(tramite.getVehiculoDto().getNifIntegrador().getTipoPersona())) {
				introductor = tramite.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial();
			}
			formatter.format("%40S", UtilesCadenaCaracteres.reemplazarAcentoss(introductor));
		}
		// 51 - Posición 1398. Longitud 79.RESERVADO para AEAT Diputaciones
		formatter.format("%79S", DATOS_VACIO);
		// 52 - Posición 1477. Longitud 5. RESERVADO para AEAT Centro de Grabacion
		formatter.format("%5S", DATOS_VACIO);
		// 53 - Posición 1477. Longitud 2. C.M.T. Vehiculos. Observaciones
		String observacion = "";
		if ("quad".equals(tramite.getVehiculoDto().getIdDirectivaCee())) {
			observacion = "01";
		} else if ("33".equals(tramite.getVehiculoDto().getCriterioUtilizacion())) {
			observacion = "02";
		} else if ("48".equals(tramite.getVehiculoDto().getCriterioUtilizacion())) {
			observacion = "03";
		} else if (tramite.getVehiculoDto().getTipoVehiculo().startsWith("5")) {
			if ("53".equals(tramite.getVehiculoDto().getTipoVehiculo()) && "L5e".equals(tramite.getVehiculoDto().getIdDirectivaCee())) {
				observacion = "07";
			} else if (new BigDecimal(74).compareTo(tramite.getVehiculoDto().getPotenciaNeta()) > 0) {
				observacion = "05";
			} else {
				observacion = new BigDecimal(0.66).compareTo(tramite.getVehiculoDto().getPotenciaPeso()) > 0 ? "04" : "06";
			}
		} else {
			observacion = "00";
		}
		formatter.format("%2S", observacion);
		// 54 - Posición 1484. Longitud 2. C.M.T. Embarcaciones. Observaciones
		formatter.format("%2S", DATOS_VACIO);
		// 55 - Posición 1486. Longitud 22. Modelo 06- NRE
		formatter.format("%22S", DATOS_VACIO);
		// 56 - Posición 1508. Longitud 1. RESERVADO para AEAT.
		formatter.format("%1S", DATOS_VACIO);
		// 57 - Posición 1509. Longitud 9. Fin Etiqueta de Inicio de Datos del Modelo 576
		formatter.format("%9S", FIN_IDENTIFICADOR_REGISTRO_NRE);

		formatter.close();

		return sb.toString();
	}

}