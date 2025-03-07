package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamConversiones.jaxb.pegatina.ParametrosPegatinaMatriculacion;
import org.gestoresmadrid.oegamImportacion.bean.CampoPdfBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.PegatinaBean;
import org.gestoresmadrid.oegamImportacion.constantes.ConstantesPDF;
import org.gestoresmadrid.oegamImportacion.schemas.utiles.ConversionFormatoTXTPegatina;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionPegatinas;
import org.gestoresmadrid.oegamImportacion.utiles.PdfMakerImportacion;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Image;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionPegatinaImpl implements ServicioImportacionPegatinas {

	private static final long serialVersionUID = 1889888352353314207L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionPegatinaImpl.class);

	@Autowired
	ConversionFormatoTXTPegatina conversionFormatoTXTPegatina;
	
	@Autowired
	PdfMakerImportacion pdf;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoImportacionBean convertirFicheroImportacion(File fichero) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = conversionFormatoTXTPegatina.convertirFicheroALineas(fichero);
			if (resultado != null && !resultado.getError()) {
				resultado = conversionFormatoTXTPegatina.convertirLineasToBean(resultado.getLineasImportPegatinas());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion de pegatinas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion de pegatinas.");
		}
		return resultado;
	}

	@Override
	public ResultadoImportacionBean generarPdfEtiquetas(ParametrosPegatinaMatriculacion parametrosPegatina, List<PegatinaBean> listaPegatinas) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			byte[] byte1;
			int[] vectPags;
			float tamX = 0;
			float tamY = 0;
			long totalEtiquetas = 0;
			long etiqPagina = 0;
			long etiqPrimeraPag = 0;
			long numPag = 0;
			int num = 1;
			float posX = 0;
			float posY = 0;
			float coordX = 0;
			float coordY = 0;
			Image img = null;

			Integer numEtiquetasFila = parametrosPegatina.getEtiquetasFila();
			Integer numEtiquetasColumna = parametrosPegatina.getEtiquetasColumna();
			Integer primEtiquetaFila = parametrosPegatina.getFilaPrimer();
			Integer primEtiquetaColumna = parametrosPegatina.getColumnaPrimer();
			Float margenIzqdo = utiles.convertirBigDecimalAFloat(parametrosPegatina.getMargenIzqd());
			Float margenSup = utiles.convertirBigDecimalAFloat(parametrosPegatina.getMargenSup());
			Float margenDcho = utiles.convertirBigDecimalAFloat(parametrosPegatina.getMargenDcho());
			Float margenInf = utiles.convertirBigDecimalAFloat(parametrosPegatina.getMargenInf());
			Float espacioHorizontal = utiles.convertirBigDecimalAFloat(parametrosPegatina.getHorizontal());
			Float espacioVertical = utiles.convertirBigDecimalAFloat(parametrosPegatina.getVertical());

			ArrayList<CampoPdfBean> campos = new ArrayList<CampoPdfBean>();
			CampoPdfBean campo;

			totalEtiquetas = parametrosPegatina.getEtiquetasTramite() * listaPegatinas.size();
			etiqPagina = numEtiquetasFila * numEtiquetasColumna;
			etiqPrimeraPag = etiqPagina - (((primEtiquetaFila - 1) * numEtiquetasFila) + (primEtiquetaColumna - 1));

			tamX = margenIzqdo + (ConstantesPDF.ANCHO_ETIQ * numEtiquetasFila) + (espacioVertical * (numEtiquetasFila - 1)) + margenDcho;
			tamY = margenSup + (ConstantesPDF.ALTO_ETIQ * numEtiquetasColumna) + (espacioHorizontal * (numEtiquetasColumna - 1)) + margenInf;

			tamX = milimetrosAPuntos(tamX);
			tamY = milimetrosAPuntos(tamY);
			posX = primEtiquetaColumna;
			posY = primEtiquetaFila;
			pdf.init();
			byte1 = pdf.nuevoPdf(tamX, tamY);

			if (etiqPagina == 1) {
				numPag = totalEtiquetas;
			} else if (etiqPrimeraPag < totalEtiquetas) {
				numPag = ((totalEtiquetas - etiqPrimeraPag) / etiqPagina) + 2;
			} else {
				numPag = 1;
			}
			if (numPag > 1) {
				byte1 = pdf.concatenarPdf(byte1, numPag);
			}

			vectPags = new int[1];
			vectPags[0] = 1;

			String bastidor = "";
			String matricula = "";
			int indiceTramite = 0;
			for (int ind = 1; ind <= totalEtiquetas; ind++) {
				if (num > parametrosPegatina.getEtiquetasTramite()) {
					num = 1;
					indiceTramite = indiceTramite + 1;
				}
				if (num == 1) {
					matricula = listaPegatinas.get(indiceTramite).getMatricula();
					bastidor = listaPegatinas.get(indiceTramite).getBastidor();

					if (bastidor.length() > ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA) {
						bastidor = bastidor.substring(bastidor.length() - ConstantesPDF.NUMERO_CARACTERES_BASTIDOR_PEGATINA);
					}
					String barCode = matricula;

					img = pdf.crearCodigoBarras128(barCode, milimetrosAPuntos(ConstantesPDF.ANCHO_BASE_BARRA), milimetrosAPuntos(ConstantesPDF.ALTO_BARRA), true);
					img = pdf.escalarImagenPorcentaje(img, ConstantesPDF._50, ConstantesPDF._100);
				}
				if (posX > numEtiquetasFila) {
					posX = 1;
					posY = posY + 1;
				}
				if (posY > numEtiquetasColumna) {
					posY = 1;
					vectPags[0] = vectPags[0] + 1;
				}

				coordX = margenIzqdo + ConstantesPDF.POSICION_X_BARCODE + ((posX - 1) * (ConstantesPDF.ANCHO_ETIQ + espacioHorizontal));
				coordY = puntosAmilimetros(tamY) - margenSup - ConstantesPDF.POSICION_Y_BARCODE - ((posY - 1) * (espacioVertical + ConstantesPDF.ALTO_ETIQ));
				campo = new CampoPdfBean(null, img, milimetrosAPuntos(coordX), milimetrosAPuntos(coordY), vectPags[0]);
				campos.add(campo);

				campo = new CampoPdfBean(null, matricula, true, false, 0, vectPags[0], milimetrosAPuntos(coordX - ConstantesPDF.POSICION_X_MATRICULA), milimetrosAPuntos(coordY
						- ConstantesPDF.POSICION_Y_TEXTO));
				campos.add(campo);
				campo = new CampoPdfBean(null, bastidor, true, false, 0, vectPags[0], milimetrosAPuntos(coordX + ConstantesPDF.POSICION_X_BASTIDOR), milimetrosAPuntos(coordY
						- ConstantesPDF.POSICION_Y_TEXTO));
				campos.add(campo);

				posX = posX + 1;
				num = num + 1;
			}
			pdf.establecerFuente(PdfMakerImportacion.HELVETICA, true, false, milimetrosAPuntos(ConstantesPDF.TAM_FUENTE));
			byte1 = pdf.insertarEtiquetas(byte1, campos);
			resultado.setPegatinas(byte1);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir las pegatinas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir las pegatinas.");
		}
		return resultado;
	}

	private static float puntosAmilimetros(float puntos) {
		return (puntos * ConstantesPDF.PTO_TO_MM);
	}

	private static float milimetrosAPuntos(float mm) {
		return (mm * ConstantesPDF.MM_TO_PP);
	}
}
