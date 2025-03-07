package org.gestoresmadrid.oegamImportacion.utiles;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.gestoresmadrid.oegamImportacion.bean.CampoPdfBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
@Scope("prototype")
public class PdfMakerImportacion implements Serializable {

	private static final long serialVersionUID = -1162787877923468547L;

	private static final int _13 = 13;
	public static final String ALINEACION_DERECHA = "dcha";
	public static final String ALINEACION_CENTRO = "centro";
	public static final String ALINEACION_IZQUIERDA = "izqda";
	public static final String COURIER = "Courier";
	public static final String HELVETICA = "Helvetica";
	public static final String TIMES = "Times";
	public static final String TIMES_ROMAN = "Times-Roman";

	private static final ILoggerOegam log = LoggerOegam.getLogger(PdfMakerImportacion.class);

	private float tamanoFuente = _13;
	private BaseFont bf = null;
	private Font font = null;

	public void init() {
		try {
			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, tamanoFuente, Font.NORMAL);
		} catch (Throwable e) {
			log.error(e);
		}
	}

	public byte[] nuevoPdf(float tamX, float tamY) {
		log.trace("Entra en nuevoPdf(float,float)");
		Document document = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		try {
			Rectangle pageSize = new Rectangle(tamX, tamY);
			document = new Document(pageSize);
			log.trace("Se crea pagina de tamaño X = " + tamX + " e Y = " + tamY);
		} catch (Throwable e) {
			log.error(e);
			log.error("Se crea pagina por defecto en A4");
			document = new Document(PageSize.A4);

		}
		pdfOut = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, pdfOut);
			document.open();
			document.newPage();
			document.add(Chunk.NEXTPAGE);
			document.close();
			pdfReturn = pdfOut.toByteArray();
		} catch (Throwable e) {
			log.error(e);
		} finally {
			try {
				document.close();
			} catch (Throwable e) {}
			try {
				pdfOut.close();
			} catch (Throwable e) {}
		}
		return pdfReturn;
	}

	public Image crearCodigoBarras128(String codigo, float anchoBarras, float alturaBarras, boolean digitoControl) {
		Image imagen = null;
		try {
			codigo = codigo.replace('Ñ', '#');
			Barcode128 code128 = new Barcode128();
			code128.setBarHeight(alturaBarras);
			code128.setX(anchoBarras);
			code128.setChecksumText(digitoControl);
			code128.setCode(codigo);
			imagen = Image.getInstance(code128.createAwtImage(Color.BLACK, Color.WHITE), null, true);
		} catch (Throwable e) {
			log.error(e);
		}
		return imagen;
	}

	public Image escalarImagenPorcentaje(Image img, float anchoPercent, float altoPercent) {
		Image image = img;
		image.scalePercent(anchoPercent, altoPercent);
		return image;
	}

	public boolean establecerFuente(String fuente, boolean negrita, boolean cursiva, float tamanoFuente) {
		boolean fontReturn = false;
		try {
			this.tamanoFuente = tamanoFuente;
			if (fuente.compareTo(TIMES) == 0) {
				if (negrita && cursiva) {
					fuente = fuente + "-BoldItalic";
				} else if (negrita) {
					fuente = fuente + "-Bold";
				} else if (cursiva) {
					fuente = fuente + "-Italic";
				}
			} else if ((fuente.compareTo(HELVETICA) == 0) || (fuente.compareTo(COURIER) == 0)) {
				if (negrita && cursiva) {
					fuente = fuente + "-BoldOblique";
				} else if (negrita) {
					fuente = fuente + "-Bold";
				} else if (cursiva) {
					fuente = fuente + "-Oblique";
				}
			} else if (fuente.compareTo(TIMES_ROMAN) == 0) {
				fuente = "Times-Roman";
			} else {
				fuente = "Helvetica";
				if (negrita && cursiva) {
					fuente = fuente + "-BoldOblique";
				} else if (negrita) {
					fuente = fuente + "-Bold";
				} else if (cursiva) {
					fuente = fuente + "-Oblique";
				}
			}
			this.bf = BaseFont.createFont(fuente, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

			if (negrita && cursiva) {
				font = new Font(bf, tamanoFuente, Font.BOLDITALIC);
			} else if (negrita) {
				font = new Font(bf, tamanoFuente, Font.BOLD);
			} else if (cursiva) {
				font = new Font(bf, tamanoFuente, Font.ITALIC);
			} else {
				font = new Font(bf, tamanoFuente, Font.NORMAL);
			}
			fontReturn = true;
		} catch (Throwable e) {
			log.error(e);
		}
		return fontReturn;
	}

	public byte[] insertarEtiquetas(byte[] pdf, ArrayList<CampoPdfBean> campos) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		byte[] pdfReturn = null;

		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				stamp = new PdfStamper(reader, pdfOut);
				int numPaginas = reader.getNumberOfPages();
				for (CampoPdfBean campo : campos) {
					if (campo.getPagina() <= numPaginas) {
						if (!campo.isImagen()) {
							log.trace("Va a escribir el valor: " + campo.getValorTexto() + " en la página " + campo.getPagina());
							over = stamp.getOverContent(campo.getPagina());
							over.beginText();
							over.setFontAndSize(bf, tamanoFuente);
							over.setTextMatrix(campo.getPosX(), campo.getPosY());
							over.showText(campo.getValorTexto());
							over.endText();
						} else {
							log.trace("Va a insertar una imagen en la página " + campo.getPagina());
							over = stamp.getOverContent(campo.getPagina());
							Image img = campo.getValorImagen();
							img.setAbsolutePosition(campo.getPosX(), campo.getPosY());
							over.addImage(img);
						}

					} else {
						log.error("No existe la pagina especificada");
					}
				}
				stamp.close();
				pdfReturn = pdfOut.toByteArray();
				reader.close();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					stamp.close();
				} catch (Throwable ex) {}
				try {
					reader.close();
				} catch (Throwable ex) {}
				try {
					pdfOut.close();
				} catch (Throwable e) {}
			}
		} else {
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	public byte[] concatenarPdf(byte[] pdf, long numVeces) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfCopyFields copy = null;
		byte[] pdfReturn = null;
		int ind;

		if (pdf != null) {
			try {
				pdfOut = new ByteArrayOutputStream();
				reader = new PdfReader(pdf);
				copy = new PdfCopyFields(pdfOut);
				copy.setFullCompression();
				for (ind = 1; ind <= numVeces; ind++) {
					copy.addDocument(reader);
				}
				copy.close();
				pdfReturn = pdfOut.toByteArray();
				reader.close();
			} catch (Throwable e) {
				log.error(e);
				pdfReturn = null;
			} finally {
				try {
					copy.close();
				} catch (Throwable e) {}
				try {
					reader.close();
				} catch (Throwable e) {}
				try {
					pdfOut.close();
				} catch (Throwable e) {}
			}
		} else {
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}
}
