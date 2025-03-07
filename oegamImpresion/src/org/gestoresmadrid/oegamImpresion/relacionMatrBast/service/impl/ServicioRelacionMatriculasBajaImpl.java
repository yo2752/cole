package org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.relacionMatrBast.service.ServicioRelacionMatriculasBaja;
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
public class ServicioRelacionMatriculasBajaImpl implements ServicioRelacionMatriculasBaja {

	private static final long serialVersionUID = -7256621912787067000L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioRelacionMatriculasBajaImpl.class);

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
			List<TramiteTrafBajaVO> listaTramites = new ArrayList<TramiteTrafBajaVO>();
			for (BigDecimal numExpediente : listaExpediente) {
				listaTramites.add(servicioTramiteTrafico.getTramiteBajaVO(numExpediente, true));
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
			log.error("Ha sucedido un error a la hora de imprimir las relaciones de matriculas de Baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir las relaciones de matriculas de Baja.");
		}
		return resultado;
	}

	private byte[] imprimir(List<TramiteTrafBajaVO> listaTramites) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		boolean mismoTitular = esMismoTitular(listaTramites);
		return generaXML(listaTramites, pdf, mismoTitular, listaTramites.get(0).getContrato());
	}

	private byte[] generaXML(List<TramiteTrafBajaVO> listaTramites, PdfMaker pdf, boolean mismoTitular, ContratoVO contrato) {
		int numLinea = 0;
		int numeroPagina = 1;
		int numExpedientesEscritos = 0;

		float xPosInicial = ConstantesPDF._f73;
		float xPosFinal = ConstantesPDF._f537;
		float yPos = ConstantesPDF._f612;

		byte[] bytePag = crearPagina(listaTramites, pdf, mismoTitular, contrato, numeroPagina);

		byte[] bytePdf = null;
		for (TramiteTrafBajaVO tramite : listaTramites) {
			if (numLinea > ConstantesPDF._24) {
				if (numExpedientesEscritos == ConstantesPDF._25) {
					bytePdf = bytePag;
				} else {
					bytePdf = pdf.concatenarPdf(bytePdf, bytePag);
				}
				numLinea = 0;
				yPos = ConstantesPDF._f612;
				numeroPagina++;

				bytePag = crearPagina(listaTramites, pdf, mismoTitular, contrato, numeroPagina);
			}

			numExpedientesEscritos++;

			bytePag = procesarMatriculas(tramite, bytePag, pdf, numLinea, numExpedientesEscritos, mismoTitular);

			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);
			bytePag = pdf.dibujarLinea(bytePag, ConstantesPDF._1, ConstantesPDF._0_5F, xPosInicial, yPos, xPosFinal, yPos);

			yPos = yPos - ConstantesPDF._18_8F;

			numLinea++;
		}

		if (numExpedientesEscritos <= ConstantesPDF._25) {
			bytePdf = bytePag;
		} else {
			bytePdf = pdf.concatenarPdf(bytePdf, bytePag);
		}

		return bytePdf;
	}

	private byte[] procesarMatriculas(TramiteTrafBajaVO tramite, byte[] bytePag, PdfMaker pdf, int numLinea, int numExpedientesEscritos, boolean mismoTitular) {
		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NUM + "." + numLinea, Integer.toString(numExpedientesEscritos));
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, mismoTitular ? ConstantesPDF._10 : ConstantesPDF._8);

		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NUM_EXPEDIENTE + "." + numLinea, tramite.getNumExpediente().toString());
		bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_FECHA + "." + numLinea, utilesFecha.formatoFecha(tramite.getFechaPresentacion()));

		if (StringUtils.isNotBlank(tramite.getRespuesta())) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_DGT + "." + numLinea, tramite.getRespuesta());
		} else {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_DGT + "." + numLinea, EstadoTramiteTrafico.convertirTexto(tramite.getEstado().toString()));
		}
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);

		if (tramite.getVehiculo() != null) {
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_BASTIDOR + "." + numLinea, tramite.getVehiculo().getMatricula());
		} else {
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_BASTIDOR + "." + numLinea, "");
		}

		if (!mismoTitular) {
			IntervinienteTraficoVO titular = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._8);
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_NIF + "." + numLinea, titular.getId().getNif());
			bytePag = pdf.setCampo(bytePag, ConstantesPDF.ID_TITULAR + "." + numLinea, (titular.getPersona().getApellido1RazonSocial() != null ? titular.getPersona().getApellido1RazonSocial() : "")
					+ (titular.getPersona().getApellido2() != null ? " " + titular.getPersona().getApellido2() : "") + (titular.getPersona().getNombre() != null ? ", " + titular.getPersona()
							.getNombre() : ""));
		}

		return bytePag;
	}

	private byte[] crearPagina(List<TramiteTrafBajaVO> listaTramites, PdfMaker pdf, boolean mismoTitular, ContratoVO contrato, int numeroPagina) {
		byte[] bytePdf = pdf.abrirPdf(getRuta(mismoTitular));
		bytePdf = procesarCabecera(listaTramites, bytePdf, pdf, contrato, numeroPagina);
		if (mismoTitular) {
			bytePdf = procesarTitular(listaTramites, bytePdf, pdf);
		}
		return bytePdf;
	}

	private byte[] procesarTitular(List<TramiteTrafBajaVO> listaTramites, byte[] bytePdf, PdfMaker pdf) {
		IntervinienteTraficoVO titular = getMismoTitular(listaTramites);

		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF, titular.getId().getNif());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR, (titular.getPersona().getApellido1RazonSocial() != null ? titular.getPersona().getApellido1RazonSocial() : "") + (titular.getPersona()
				.getApellido2() != null ? " " + titular.getPersona().getApellido2() : "") + (titular.getPersona().getNombre() != null ? ", " + titular.getPersona().getNombre() : ""));

		return bytePdf;
	}

	private byte[] procesarCabecera(List<TramiteTrafBajaVO> listaTramites, byte[] bytePdf, PdfMaker pdf, ContratoVO contrato, int numeroPagina) {
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);

		int numExpedientesTotales = getNumExpedientesTotales(listaTramites);
		int paginasTotales = calcularPaginaTotales(numExpedientesTotales);

		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_PAG, Integer.toString(paginasTotales));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_PAG, Integer.toString(numeroPagina));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_EXPEDIENTES, Integer.toString(numExpedientesTotales));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_CODIGO_COLEGIADO, contrato.getColegiado().getNumColegiado());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_GESTORIA, contrato.getRazonSocial());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TRAMITE, " BAJA ");
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF_GESTORIA, contrato.getCif());

		return bytePdf;
	}

	private boolean esMismoTitular(List<TramiteTrafBajaVO> listaTramites) {
		boolean mismoTitular = true;
		String dniTitular = null;

		for (TramiteTrafBajaVO tramite : listaTramites) {
			IntervinienteTraficoVO titular = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());
			if (titular != null) {
				if (dniTitular == null) {
					dniTitular = titular.getId().getNif();
				} else if (!dniTitular.equals(titular.getId().getNif())) {
					mismoTitular = false;
					break;
				}
			} else {
				mismoTitular = false;
				break;
			}
		}
		return mismoTitular;
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTrafBajaVO tramite, String tipoInterviniente) {
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

	private String getRuta(boolean mismoTitular) {
		if (mismoTitular) {
			return this.getClass().getResource(gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.ListadoMatriculas1_BD.getNombreEnum()).getFile();
		} else {
			return this.getClass().getResource(gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.ListadoMatriculas2_BD.getNombreEnum()).getFile();
		}
	}

	private int getNumExpedientesTotales(List<TramiteTrafBajaVO> listaTramites) {
		return listaTramites.size();
	}

	private int calcularPaginaTotales(int numExpedientes) {
		if (numExpedientes % ConstantesPDF._25 == 0) {
			return numExpedientes / ConstantesPDF._25;
		} else {
			return (numExpedientes / ConstantesPDF._25) + ConstantesPDF._1;
		}
	}

	private IntervinienteTraficoVO getMismoTitular(List<TramiteTrafBajaVO> listaTramites) {
		return buscarInterviniente(listaTramites.get(0), TipoInterviniente.Titular.getValorEnum());
	}
}
