package org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculasCtit;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioRelacionMatriculasCtitImpl implements ServicioRelacionMatriculasCtit {

	private static final long serialVersionUID = 6243247606633804723L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioRelacionMatriculasCtitImpl.class);

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoImpresionBean imprimirRelacionMatricula(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		try {
			List<TramiteTrafTranVO> listaTramites = new ArrayList<TramiteTrafTranVO>();
			for (BigDecimal numExpediente : listaExpediente) {
				listaTramites.add(servicioTramiteTrafico.getTramiteTransmisionVO(numExpediente, true));
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
			log.error("Ha sucedido un error a la hora de imprimir las relaciones de matriculas de Ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir las relaciones de matriculas de Ctit.");
		}
		return resultado;
	}

	private byte[] imprimir(List<TramiteTrafTranVO> listaTramites) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		boolean mismoTransmitente = esMismoTransmitente(listaTramites);
		return generaXML(listaTramites, pdf, mismoTransmitente, listaTramites.get(0).getContrato());
	}

	private byte[] generaXML(List<TramiteTrafTranVO> listaTramites, PdfMaker pdf, boolean mismoTransmitente, ContratoVO contrato) {
		int numLinea = 0;
		int numeroPagina = 1;
		int numExpedientesEscritos = 0;

		float xPosInicial = ConstantesPDF._f73;
		float xPosFinal = ConstantesPDF._f537;
		float yPos = ConstantesPDF._f610;

		byte[] bytePag = crearPagina(listaTramites, pdf, mismoTransmitente, contrato, numeroPagina);

		byte[] bytePdf = null;
		for (TramiteTrafTranVO tramite : listaTramites) {
			if (numLinea > ConstantesPDF._24) {
				if (numExpedientesEscritos == ConstantesPDF._25) {
					bytePdf = bytePag;
				} else {
					bytePdf = pdf.concatenarPdf(bytePdf, bytePag);
				}
				numLinea = 0;
				yPos = ConstantesPDF._f612;
				numeroPagina++;

				bytePag = crearPagina(listaTramites, pdf, mismoTransmitente, contrato, numeroPagina);
			}

			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._5);
			numExpedientesEscritos++;

			bytePag = procesarMatriculas(tramite, bytePag, pdf, numLinea, numExpedientesEscritos, mismoTransmitente);
			if (mismoTransmitente) {
				bytePag = procesarIntervinientesMismoTransmitente(tramite, bytePag, pdf, numLinea);
				bytePag = procesarRespuestaDGT(tramite, bytePag, pdf, numLinea);
				pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			} else {
				bytePag = procesarIntervinientes(tramite, bytePag, pdf, numLinea);
				pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);
			}

			bytePag = pdf.dibujarLinea(bytePag, ConstantesPDF._1, ConstantesPDF._0_5F, xPosInicial, yPos, xPosFinal, yPos);

			if (mismoTransmitente) {
				yPos = yPos - ConstantesPDF._18_35F;
			} else {
				yPos = yPos - ConstantesPDF._18_8F;
			}

			numLinea++;
		}

		if (numExpedientesEscritos <= ConstantesPDF._25) {
			bytePdf = bytePag;
		} else {
			bytePdf = pdf.concatenarPdf(bytePdf, bytePag);
		}

		return bytePdf;
	}

	private byte[] procesarIntervinientes(TramiteTrafTranVO tramite, byte[] bytePag, PdfMaker pdf, int numLinea) {
		IntervinienteTraficoVO adquiriente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
		if (adquiriente != null) {
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_ADQUIRIENTE + "." + numLinea, (adquiriente.getId().getNif() != null ? "Adquiriente: " + adquiriente.getId().getNif() + " - " : "")
					+ (adquiriente.getPersona().getApellido1RazonSocial() != null ? adquiriente.getPersona().getApellido1RazonSocial() : "") + (adquiriente.getPersona().getApellido2() != null ? " "
							+ adquiriente.getPersona().getApellido2() : "") + (adquiriente.getPersona().getNombre() != null ? ", " + adquiriente.getPersona().getNombre() : ""));
		}

		IntervinienteTraficoVO transmitente = buscarInterviniente(tramite, TipoInterviniente.TransmitenteTrafico.getValorEnum());
		if (transmitente != null) {
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_TRANSMITENTE + "." + numLinea, (transmitente.getId().getNif() != null ? "Transmitente: " + transmitente.getId().getNif() + " - " : "")
					+ (transmitente.getPersona().getApellido1RazonSocial() != null ? transmitente.getPersona().getApellido1RazonSocial() : "") + (transmitente.getPersona().getApellido2() != null ? " "
							+ transmitente.getPersona().getApellido2() : "") + (transmitente.getPersona().getNombre() != null ? ", " + transmitente.getPersona().getNombre() : ""));
		}

		IntervinienteTraficoVO compraventa = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
		if (compraventa != null) {
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_POSEEDOR + "." + numLinea, (compraventa.getId().getNif() != null ? "Poseedor: AA" + compraventa.getId().getNif() + " - " : "")
					+ (compraventa.getPersona().getApellido1RazonSocial() != null ? compraventa.getPersona().getApellido1RazonSocial() : "") + (compraventa.getPersona().getApellido2() != null ? " "
							+ compraventa.getPersona().getApellido2() : "") + (compraventa.getPersona().getNombre() != null ? ", " + compraventa.getPersona().getNombre() : ""));
		}

		return bytePag;
	}

	private byte[] procesarRespuestaDGT(TramiteTrafTranVO tramite, byte[] bytePag, PdfMaker pdf, int numLinea) {
		String respuestaDgt = "";
		if (new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) || new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
				.getValorEnum()).equals(tramite.getEstado())) {
			respuestaDgt = ConstantesPDF.RESPUESTA_OK;
		} else {
			respuestaDgt = ConstantesPDF.RESPUESTA_ERROR;
		}
		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_DGT + "." + numLinea, respuestaDgt);
		return bytePag;
	}

	private byte[] procesarIntervinientesMismoTransmitente(TramiteTrafTranVO tramite, byte[] bytePag, PdfMaker pdf, int numLinea) {
		String tipoTransferencia = TipoTransferencia.convertirTexto(tramite.getTipoTransferencia());
		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_TIPO_TRANSFERENCIA + "." + numLinea, tipoTransferencia);

		if (tramite.getTipoTransferencia().equals(TipoTransferencia.tipo5.getValorEnum())) {
			IntervinienteTraficoVO compraventa = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
			if (compraventa != null) {
				String datosCompraventa = (compraventa.getPersona().getApellido1RazonSocial() != null ? compraventa.getPersona().getApellido1RazonSocial() : "") + (compraventa.getPersona()
						.getNombre() != null ? ", " + compraventa.getPersona().getNombre() : "");
				if (datosCompraventa.length() > 27) {
					datosCompraventa = datosCompraventa.substring(0, 24);
					datosCompraventa += "..";
				}
				bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NIF_AD + "." + numLinea, (compraventa.getId().getNif() != null ? compraventa.getId().getNif() : ""));
				bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_ADQUI + "." + numLinea, datosCompraventa);
			}
		} else {
			IntervinienteTraficoVO adquiriente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
			if (adquiriente != null) {
				String datosAquiriente = (adquiriente.getPersona().getApellido1RazonSocial() != null ? adquiriente.getPersona().getApellido1RazonSocial() : "") + (adquiriente.getPersona()
						.getNombre() != null ? ", " + adquiriente.getPersona().getNombre() : "");
				if (datosAquiriente.length() > 27) {
					datosAquiriente = datosAquiriente.substring(0, 24);
					datosAquiriente += "..";
				}
				bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NIF_AD + "." + numLinea, (adquiriente.getId().getNif() != null ? adquiriente.getId().getNif() : ""));
				bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_ADQUI + "." + numLinea, datosAquiriente);
			}
		}

		IntervinienteTraficoVO transmitente = buscarInterviniente(tramite, TipoInterviniente.TransmitenteTrafico.getValorEnum());
		if (transmitente != null) {
			String datosTransmitente = (transmitente.getPersona().getApellido1RazonSocial() != null ? transmitente.getPersona().getApellido1RazonSocial() : "") + (transmitente.getPersona()
					.getNombre() != null ? ", " + transmitente.getPersona().getNombre() : "");
			if (datosTransmitente.length() > 27) {
				datosTransmitente = datosTransmitente.substring(0, 24);
				datosTransmitente += "..";
			}
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_TRANS + "." + numLinea, datosTransmitente);
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NIF_TRANSMITENTE + "." + numLinea, (transmitente.getId().getNif() != null ? transmitente.getId().getNif() : ""));
		}

		return bytePag;
	}

	private byte[] procesarMatriculas(TramiteTrafTranVO tramite, byte[] bytePag, PdfMaker pdf, int numLinea, int numExpedientesEscritos, boolean mismoTransmitente) {
		if (mismoTransmitente) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
		} else {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._8);
		}

		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NUM + "." + numLinea, Integer.toString(numExpedientesEscritos));
		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NUM_EXPEDIENTE + "." + numLinea, tramite.getNumExpediente().toString());
		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_FECHA + "." + numLinea, utilesFecha.formatoFecha(tramite.getFechaPresentacion()));
		if (tramite.getVehiculo() != null) {
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_BASTIDOR + "." + numLinea, tramite.getVehiculo().getMatricula());
		}

		return bytePag;
	}

	private byte[] crearPagina(List<TramiteTrafTranVO> listaTramites, PdfMaker pdf, boolean mismoTransmitente, ContratoVO contrato, int numeroPagina) {
		byte[] bytePdf = pdf.abrirPdf(getRuta(mismoTransmitente));
		bytePdf = procesarCabecera(listaTramites, bytePdf, pdf, contrato, numeroPagina);
		if (mismoTransmitente) {
			bytePdf = procesarTransmitente(listaTramites, bytePdf, pdf);
		}
		return bytePdf;
	}

	private byte[] procesarTransmitente(List<TramiteTrafTranVO> listaTramites, byte[] bytePdf, PdfMaker pdf) {
		IntervinienteTraficoVO transmitente = getMismoTransmitente(listaTramites);

		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF, transmitente.getId().getNif());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR, (transmitente.getPersona().getApellido1RazonSocial() != null ? transmitente.getPersona().getApellido1RazonSocial() : "")
				+ (transmitente.getPersona().getApellido2() != null ? " " + transmitente.getPersona().getApellido2() : "") + (transmitente.getPersona().getNombre() != null ? ", " + transmitente
						.getPersona().getNombre() : ""));

		return bytePdf;
	}

	private byte[] procesarCabecera(List<TramiteTrafTranVO> listaTramites, byte[] bytePdf, PdfMaker pdf, ContratoVO contrato, int numeroPagina) {
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);

		int numExpedientesTotales = getNumExpedientesTotales(listaTramites);
		int paginasTotales = calcularPaginaTotales(numExpedientesTotales);

		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_PAG, Integer.toString(paginasTotales));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_PAG, Integer.toString(numeroPagina));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_EXPEDIENTES, Integer.toString(numExpedientesTotales));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_CODIGO_COLEGIADO, contrato.getColegiado().getNumColegiado());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_GESTORIA, contrato.getRazonSocial());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TRAMITE, "TRANSMISIÓN ELECTRÓNICA");
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF_GESTORIA, contrato.getCif());

		return bytePdf;
	}

	private boolean esMismoTransmitente(List<TramiteTrafTranVO> listaTramites) {
		boolean mismoTransmitente = true;
		String dniTransmitente = null;

		for (TramiteTrafTranVO tramite : listaTramites) {
			IntervinienteTraficoVO transmitente = buscarInterviniente(tramite, TipoInterviniente.TransmitenteTrafico.getValorEnum());
			if (transmitente != null) {
				if (dniTransmitente == null) {
					dniTransmitente = transmitente.getId().getNif();
				} else if (!dniTransmitente.equals(transmitente.getId().getNif())) {
					mismoTransmitente = false;
					break;
				}
			} else {
				mismoTransmitente = false;
				break;
			}
		}
		return mismoTransmitente;
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTrafTranVO tramite, String tipoInterviniente) {
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

	private String getRuta(boolean mismoTransmitente) {
		if (mismoTransmitente) {
			return this.getClass().getResource(gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.ListadoMatriculas1.getNombreEnum()).getFile();
		} else {
			return this.getClass().getResource(gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.ListadoMatriculasTrans2.getNombreEnum()).getFile();
		}
	}

	private int getNumExpedientesTotales(List<TramiteTrafTranVO> listaTramites) {
		return listaTramites.size();
	}

	private int calcularPaginaTotales(int numExpedientes) {
		if (numExpedientes % ConstantesPDF._25 == 0) {
			return numExpedientes / ConstantesPDF._25;
		} else {
			return (numExpedientes / ConstantesPDF._25) + ConstantesPDF._1;
		}
	}

	private IntervinienteTraficoVO getMismoTransmitente(List<TramiteTrafTranVO> listaTramites) {
		return buscarInterviniente(listaTramites.get(0), TipoInterviniente.TransmitenteTrafico.getValorEnum());
	}
}
