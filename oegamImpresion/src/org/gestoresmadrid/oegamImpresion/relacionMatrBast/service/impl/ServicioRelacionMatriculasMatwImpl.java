package org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.impl;

import java.awt.Color;
import java.awt.Image;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoCarpeta;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculasMatw;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilesNube;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilidadesConversiones;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.BarcodePDF417;

import net.sf.jasperreports.engine.JRException;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioRelacionMatriculasMatwImpl implements ServicioRelacionMatriculasMatw {

	private static final long serialVersionUID = 7008543029541599057L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioRelacionMatriculasMatwImpl.class);

	private static final String CABECERA = "expediente";

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunPersona servicioPersona;

	@Autowired
	ServicioComunVehiculo servicioVehiculo;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	UtilesNube utilesNube;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoImpresionBean imprimirRelacionMatricula(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> listaTramites = new ArrayList<TramiteTrafMatrVO>();
			for (BigDecimal numExpediente : listaExpediente) {
				listaTramites.add(servicioTramiteTrafico.getTramiteMatriculacionVO(numExpediente, true));
			}
			if (listaTramites != null && !listaTramites.isEmpty()) {
				byte[] bytePdf = imprimir(listaTramites);
				if (bytePdf != null) {
					resultado.setPdf(bytePdf);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha generado nada para imprimir.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Sin trámites para realizar la impresión.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir las relaciones de matriculas de Matriculacion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir las relaciones de matriculas de Matriculacion.");
		}
		return resultado;
	}

	private byte[] imprimir(List<TramiteTrafMatrVO> listaTramites) throws ParserConfigurationException, JRException {
		ContratoVO contrato = listaTramites.get(0).getContrato();
		PersonaVO datosColegiado = servicioPersona.obtenerColegiadoCompleto(contrato.getColegiado().getNumColegiado(), contrato.getColegiado().getUsuario().getNif());

		String xmlDatos = generaXML(listaTramites);

		Map<String, Object> params = obtenerParametros(listaTramites, contrato, datosColegiado);

		ReportExporter re = new ReportExporter();

		return re.generarInforme(getRuta(), getNombreInforme(), "pdf", xmlDatos, CABECERA, params, null);
	}

	private Map<String, Object> obtenerParametros(List<TramiteTrafMatrVO> listaTramites, ContratoVO contrato, PersonaVO datosColegiado) {
		Map<String, Object> params = new HashMap<String, Object>();

		TipoCarpeta tipoCarpeta = obtenerTipoCarpeta(listaTramites.get(0));

		Image nubePuntos417;
		try {
			nubePuntos417 = getBarcode(utilesNube.getDatosNubePuntosListadoBastidores(listaTramites));
		} catch (Exception e) {
			log.error("Error al generar el PDF del listado de Bastidores.\n" + e);
			return null;
		}

		params.put("SUBREPORT_DIR", getRuta());
		params.put("IMG_DIR", getRuta());
		params.put("CODIGO_CARPETA", tipoCarpeta.getValorEnum());
		params.put("NOMBRE_CARPETA", tipoCarpeta.getNombreEnum());
		params.put("COLOR_CARPETA", "");
		params.put("ESTADO_CARPETA", "");
		params.put("GESTOR", contrato.getColegiado().getUsuario() != null ? contrato.getColegiado().getNumColegiado() + " - " + contrato.getColegiado().getUsuario().getApellidosNombre() : contrato
				.getColegiado().getNumColegiado() + "");
		params.put("GESTORIA", contrato.getRazonSocial());
		params.put("NIF_GESTORIA", contrato.getColegiado().getUsuario().getNif());
		params.put("TOTAL_EXP", listaTramites.size() + "");
		params.put("TOTAL_EITV", getNumEITV(listaTramites) + "");
		params.put("TOTAL_AMBIENTALES", getTotalAmbientales(listaTramites) + "");
		params.put("NUBE_PUNTOS", nubePuntos417);
		params.put("FECHA_PRESENTACION", listaTramites.get(0).getFechaPresentacion());

		return params;
	}

	private TipoCarpeta obtenerTipoCarpeta(TramiteTrafMatrVO tramite) {
		if (new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) || new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
				.getValorEnum()).equals(tramite.getEstado())) {
			return TipoCarpeta.CARPETA_MATE;
		} else {
			boolean verdeTipoA = checkVerdeTipoA(tramite);
			if (verdeTipoA) {
				return TipoCarpeta.CARPETA_MATE;
			} else {
				return TipoCarpeta.CARPETA_MATE;
			}
		}
	}

	private Boolean checkVerdeTipoA(TramiteTrafMatrVO tramite) {
		boolean verdeTipoA = false;
		if (tramite.getVehiculo() != null && TipoTarjetaITV.A.getValorEnum().equals(tramite.getVehiculo().getIdTipoTarjetaItv())) {
			verdeTipoA = true;
		}
		return verdeTipoA;
	}

	private java.awt.Image getBarcode(String datosNubePuntos) throws Exception {
		BarcodePDF417 barcode = new BarcodePDF417();
		barcode.setText(datosNubePuntos);
		barcode.setAspectRatio(.25f);
		return barcode.createAwtImage(Color.BLACK, Color.WHITE);
	}

	private int getNumEITV(List<TramiteTrafMatrVO> listaTramites) {
		int contador = 0;
		if (listaTramites != null && !listaTramites.isEmpty()) {
			for (TramiteTrafMatrVO tramite : listaTramites) {
				if (tramite.getVehiculo() != null && tramite.getVehiculo().getNive() != null) {
					contador++;
				}
			}
		}
		return contador;
	}

	private int getTotalAmbientales(List<TramiteTrafMatrVO> listaTramites) {
		int contador = 0;
		if (listaTramites != null && !listaTramites.isEmpty()) {
			for (TramiteTrafMatrVO tramite : listaTramites) {
				if (tramite.getVehiculo() != null) {
					if (isValidoAmbiental(tramite.getVehiculo())) {
						contador++;
					}
				}
			}
		}
		return contador;
	}

	private String generaXML(List<TramiteTrafMatrVO> listaTramites) {
		String xml = "";

		xml = "<?xml version='1.0' encoding='UTF-8' standalone='no' ?> <" + CABECERA + "s>";

		for (TramiteTrafMatrVO tramite : listaTramites) {
			IntervinienteTraficoVO titular = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());
			xml = xml + "<" + CABECERA + ">";
			xml = xml + "<numexpediente>";
			if (tramite.getNumExpediente() != null) {
				xml = xml + tramite.getNumExpediente();
			}
			xml = xml + "</numexpediente>";
			xml = xml + "<matricula>";
			if (tramite.getVehiculo() != null && tramite.getVehiculo().getMatricula() != null) {
				xml = xml + tramite.getVehiculo().getMatricula();
			}
			xml = xml + "</matricula>";
			xml = xml + "<bastidor>";
			if (tramite.getVehiculo() != null && tramite.getVehiculo().getBastidor() != null) {
				xml = xml + tramite.getVehiculo().getBastidor();
			}
			xml = xml + "</bastidor>";
			xml = xml + "<titular>";
			xml = xml + "<nif>";
			if (titular != null) {
				xml = xml + titular.getId().getNif();
			}
			xml = xml + "</nif>";
			xml = xml + "<nombreapp>";
			if (titular != null) {
				String nomApp = "";
				if (titular.getPersona().getNombre() != null) {
					nomApp = titular.getPersona().getNombre();
				}

				if (titular.getPersona().getApellido1RazonSocial() != null) {
					nomApp += " " + titular.getPersona().getApellido1RazonSocial();
				}

				if (titular.getPersona().getApellido2() != null) {
					nomApp += " " + titular.getPersona().getApellido2();
				}
				xml = xml + nomApp;
			}
			xml = xml + "</nombreapp>";
			xml = xml + "</titular>";
			xml = xml + "<tasa>";
			if (tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null) {
				xml = xml + tramite.getTasa().getCodigoTasa();
			}
			xml = xml + "</tasa>";
			xml = xml + "<fichaelectr>";
			if (tramite.getVehiculo() != null && tramite.getVehiculo().getNive() != null) {
				xml = xml + "SI";
			}
			xml = xml + "</fichaelectr>";
			xml = xml + "<distambiental>";
			if (tramite.getVehiculo() != null && isValidoAmbiental(tramite.getVehiculo())) {
				xml = xml + "SI";
			}
			xml = xml + "</distambiental>";

			xml = xml + "</" + CABECERA + ">";
		}
		xml = xml + "</" + CABECERA + "s>";
		return xml;
	}

	private boolean isValidoAmbiental(VehiculoVO vehiculo) {
		boolean validacion = false;
		if (vehiculo.getIdDirectivaCee() != null && (vehiculo.getIdDirectivaCee().contains("M") || vehiculo.getIdDirectivaCee().contains("N") || vehiculo.getIdDirectivaCee().contains("L"))) {
			if (isDistintivoCero(vehiculo)) {
				validacion = true;
			} else if (isDistintivoEco(vehiculo)) {
				validacion = true;
			}
		}
		return validacion;
	}

	private boolean isDistintivoCero(VehiculoVO vehiculo) {
		boolean validacion = false;
		if ((vehiculo.getMatricula() != null) && (("ordinaria".equals(utilidadesConversiones.analizarMatriculaVehiculo(vehiculo.getMatricula())) || "ciclomotor".equals(utilidadesConversiones
				.analizarMatriculaVehiculo(vehiculo.getMatricula()))))) {
			if (vehiculo.getIdCarburante() != null && (Combustible.Electrico.getValorEnum().equals(vehiculo.getIdCarburante()) || Combustible.Diesel.getValorEnum().equals(vehiculo.getIdCarburante())
					|| Combustible.Gasolina.getValorEnum().equals(vehiculo.getIdCarburante()))) {
				if (vehiculo.getCategoriaElectrica() != null) {
					if (("BEV".equals(vehiculo.getCategoriaElectrica()) || "REEV".equals(vehiculo.getCategoriaElectrica()))) {
						validacion = true;
					} else if ((vehiculo.getAutonomiaElectrica() != null) && ("PHEV".equals(vehiculo.getCategoriaElectrica()) && ((vehiculo.getAutonomiaElectrica().compareTo(new BigDecimal(40)) == 1)
							|| vehiculo.getAutonomiaElectrica().compareTo(new BigDecimal(40)) == 0))) {
						validacion = true;
					} else if (Combustible.Hidrogeno.getValorEnum().equals(vehiculo.getIdCarburante()))
						validacion = true;
				}
			}
		}
		return validacion;
	}

	private boolean isDistintivoEco(VehiculoVO vehiculo) {
		boolean validacion = false;
		if ((vehiculo.getMatricula() != null) && (("ordinaria".equals(utilidadesConversiones.analizarMatriculaVehiculo(vehiculo.getMatricula()))))) {
			if (vehiculo.getCategoriaElectrica() != null) {
				if ("PHEV".equals(vehiculo.getCategoriaElectrica()) && vehiculo.getAutonomiaElectrica() != null && vehiculo.getAutonomiaElectrica().compareTo(new BigDecimal(40)) == -1) {
					validacion = true;
				} else if ("HEV".equals(vehiculo.getCategoriaElectrica())) {
					validacion = true;
				}
			} else if ("GNC".equals(vehiculo.getIdCarburante()) || "GNL".equals(vehiculo.getIdCarburante()) || "GLP".equals(vehiculo.getIdCarburante())) {
				validacion = true;
			}
		}

		return validacion;
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTrafMatrVO tramite, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		if (tramite.getIntervinienteTraficosAsList() != null && !tramite.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO inter : tramite.getIntervinienteTraficosAsList()) {
				if (tipoInterviniente.equals(inter.getId().getTipoInterviniente())) {
					interviniente = inter;
					break;
				}
			}
		}
		return interviniente;
	}

	private String getRuta() {
		return gestorPropiedades.valorPropertie("matw.plantillas");
	}

	private String getNombreInforme() {
		return gestorPropiedades.valorPropertie("matw.plantillas.relacionMatriculas");
	}
}
