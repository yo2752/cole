package org.gestoresmadrid.oegamImpresion.utiles;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PdfMaker {

	private static final ILoggerOegam log = LoggerOegam.getLogger(PdfMaker.class);

	public static final String COURIER = "Courier";
	public static final String HELVETICA = "Helvetica";
	public static final String TIMES = "Times";
	public static final String TIMES_ROMAN = "Times-Roman";

	private static final int _13 = 13;
	private float tamanoFuente = _13;

	private BaseFont bf = null;
	private Font font = null;

	public PdfMaker() {
		try {
			this.bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			this.font = new Font(bf, tamanoFuente, Font.NORMAL);
		} catch (Throwable e) {
			log.error(e);
		}
	}

	public byte[] abrirPdf(String pdf) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		byte[] pdfReturn = null;

		pdfOut = new ByteArrayOutputStream();
		log.trace("Entramos en abrirPdf");
		try {
			reader = new PdfReader(pdf);
			PdfCopyFields copy = new PdfCopyFields(pdfOut);
			copy.addDocument(reader);
			copy.close();
			pdfReturn = pdfOut.toByteArray();
		} catch (Throwable e) {
			log.error(e);
			pdfReturn = null;
		} finally {
			try {
				reader.close();
			} catch (Throwable e) {}
			try {
				pdfOut.close();
			} catch (Throwable e) {}
		}
		return pdfReturn;
	}

	public Set<String> getAllFields(byte[] pdf) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		Set<String> nombres = null;

		log.trace("Entramos en getAllFields");
		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				AcroFields form1 = stamp.getAcroFields();
				Map<String, Item> fields = form1.getFields();
				nombres = fields.keySet();
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
		return nombres;
	}

	public List<FieldPosition> getFieldPosition(byte[] pdf, String nombreCampo) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		List<FieldPosition> posiciones = new ArrayList<FieldPosition>();

		log.trace("Entramos en getFieldPosition");

		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				AcroFields form1 = stamp.getAcroFields();
				posiciones = form1.getFieldPositions(nombreCampo);
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
		return posiciones;
	}

	public byte[] crearCodigoBarras128ConTexto(String codigo, byte[] pdf, float anchoBarras, float alturaBarras, float distanciaTexto, boolean digitoControl, float xPos, float yPos) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		ByteArrayOutputStream pdfOut = null;

		Image imagen = null;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarImagen");
		try {
			pdfOut = new ByteArrayOutputStream();
			reader = new PdfReader(pdf);
			stamp = new PdfStamper(reader, pdfOut);
			over = stamp.getOverContent(1);
			codigo = codigo.replace('Ñ', '#');
			Barcode128 code128 = new Barcode128();
			if (anchoBarras >= 0) {
				code128.setX(anchoBarras);
			}
			if (alturaBarras >= 0) {
				code128.setBarHeight(alturaBarras);
			}
			if (distanciaTexto >= 0) {
				code128.setBaseline(distanciaTexto);
			}
			code128.setChecksumText(digitoControl);
			code128.setCode(codigo);
			imagen = code128.createImageWithBarcode(over, null, null);
			imagen.setAbsolutePosition(xPos, yPos);
			over.addImage(imagen);
			reader.close();
			stamp.close();
			pdfReturn = pdfOut.toByteArray();
		} catch (Throwable e) {
			log.error(e);
		} finally {
			try {
				reader.close();
			} catch (Throwable e) {}
			try {
				stamp.close();
			} catch (Throwable e) {}
			try {
				pdfOut.close();
			} catch (Throwable e) {}
		}
		return pdfReturn;
	}

	public byte[] setCampos(byte[] pdf, List<CampoPdfBean> campos) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				for (CampoPdfBean campo : campos) {
					AcroFields form1 = stamp.getAcroFields();
					String strFuente;
					if (null != campo.getFuente()) {
						strFuente = campo.getFuente();
					} else {
						strFuente = BaseFont.TIMES_ROMAN;
					}
					establecerFuente(strFuente, campo.isNegrita(), campo.isCursiva(), campo.getTamFuente());

					form1.setFieldProperty(campo.getNombre(), "textfont", bf, null);
					form1.setFieldProperty(campo.getNombre(), "textsize", tamanoFuente, null);

					if (null != campo.getValorTexto()) {
						form1.setField(campo.getNombre(), campo.getValorTexto());
					} else
						form1.setField(campo.getNombre(), "");

					establecerFuente(strFuente, false, false, _13);
				}
				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();

			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {}
				try {
					stamp.close();
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

	public byte[] setCampo(byte[] pdf, String nombreCampo, String cadena) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		log.debug("Campo a escribir: " + nombreCampo + " - Valor: " + cadena);
		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				AcroFields form1 = stamp.getAcroFields();
				form1.setFieldProperty(nombreCampo, "textfont", bf, null);
				form1.setFieldProperty(nombreCampo, "textsize", tamanoFuente, null);
				form1.setField(nombreCampo, cadena);
				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {}
				try {
					stamp.close();
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

	public Image cargarImagen(String rutaImagen) {
		Image img = null;
		try {
			img = Image.getInstance(rutaImagen);
		} catch (Throwable e) {
			log.error(e);
		}
		return img;
	}

	public byte[] insertarMarcaDeAgua(byte[] pdf, Image img, int[] numPaginas, int xPos, int yPos, int size) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte under = null;
		ByteArrayOutputStream pdfOut = null;
		int index;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarMarcaDeAgua");
		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				img.setAbsolutePosition(xPos, yPos);
				img.scalePercent(size);
				for (index = 0; index < numPaginas.length; index++) {
					under = stamp.getUnderContent(numPaginas[index]);
					under.addImage(img);
				}
				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {}
				try {
					stamp.close();
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

	public Image crearNubePuntos(String texto) {
		BarcodePDF417 pdf417 = new BarcodePDF417();
		Image imagen = null;
		try {
			pdf417.setText(texto);
			imagen = pdf417.getImage();
		} catch (Throwable e) {
			log.error(e);
		}
		return imagen;
	}

	public Image escalarImagen(Image img, float ancho, float alto) {
		Image image;
		image = img;
		image.scaleAbsolute(ancho, alto);
		return image;
	}

	public byte[] insertarImagen(byte[] pdf, Image img, int[] numPaginas, float xPos, float yPos) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		ByteArrayOutputStream pdfOut = null;
		int index;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarImagen");
		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				img.setAbsolutePosition(xPos, yPos);
				for (index = 0; index < numPaginas.length; index++) {
					over = stamp.getOverContent(numPaginas[index]);
					over.addImage(img);
				}
				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {}
				try {
					stamp.close();
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

	public static byte[] encriptarPdf(byte[] pdf, String userPass, String ownerPass, boolean print, boolean copy, boolean rellenar, boolean modificar, boolean strengthKey) {
		PdfReader reader = null;
		ByteArrayOutputStream pdfOut = null;
		int permisos = 0;
		byte[] pdfReturn = null;

		pdfOut = new ByteArrayOutputStream();
		if (pdf != null) {
			if (print) {
				permisos = permisos | PdfWriter.ALLOW_PRINTING;
			}
			if (copy) {
				permisos = permisos | PdfWriter.ALLOW_COPY;
			}
			if (rellenar) {
				permisos = permisos | PdfWriter.ALLOW_FILL_IN;
			}
			if (modificar) {
				permisos = permisos | PdfWriter.ALLOW_MODIFY_CONTENTS;
			}
			log.debug("Permisos -> Modificar contenidos : " + modificar + "; Imprimir : " + print + "; Copiar : " + copy + "; Rellenar : " + rellenar);
			try {
				byte[] usuario = userPass.getBytes() == null ? null : userPass.getBytes();
				byte[] propietario = ownerPass.getBytes() == null ? null : ownerPass.getBytes();
				reader = new PdfReader(pdf);
				PdfEncryptor.encrypt(reader, pdfOut, usuario, propietario, permisos, strengthKey);
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
				pdfReturn = null;
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {}
				try {
					pdfOut.close();
				} catch (Throwable e) {}
			}
		} else {
			log.warn("Pdf no válido");
		}
		return pdfReturn;
	}

	public static byte[] concatenarPdf(ArrayList<byte[]> listaBytes) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader1 = null;
		PdfCopyFields copy = null;
		byte[] pdfReturn = null;
		try {
			pdfOut = new ByteArrayOutputStream();
			copy = new PdfCopyFields(pdfOut);
			for (byte[] byte1 : listaBytes) {
				if (byte1 != null) {
					reader1 = new PdfReader(byte1);
					copy.addDocument(reader1);
					reader1.close();
				}
			}
			copy.close();
			pdfReturn = pdfOut.toByteArray();
		} catch (Throwable e) {
			log.error(e);
		} finally {
			try {
				copy.close();
			} catch (Throwable e) {}
			try {
				reader1.close();
			} catch (Throwable e) {}
			try {
				pdfOut.close();
			} catch (Throwable e) {}
		}

		return pdfReturn;
	}

	public byte[] concatenarPdf(byte[] pdf1, byte[] pdf2) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader1 = null;
		PdfReader reader2 = null;
		PdfCopyFields copy = null;
		byte[] pdfReturn = null;

		if (pdf1 != null && pdf2 != null) {
			try {
				pdfOut = new ByteArrayOutputStream();
				reader1 = new PdfReader(pdf1);
				reader2 = new PdfReader(pdf2);
				copy = new PdfCopyFields(pdfOut);
				copy.setFullCompression();
				copy.addDocument(reader1);
				copy.addDocument(reader2);
				copy.close();
				pdfReturn = pdfOut.toByteArray();
				reader1.close();
				reader2.close();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					copy.close();
				} catch (Throwable e) {}
				try {
					reader1.close();
				} catch (Throwable e) {}
				try {
					reader2.close();
				} catch (Throwable e) {}
				try {
					pdfOut.close();
				} catch (Throwable e) {}
			}
		} else {
			log.error("Uno de los pdf's no es válido");
		}
		return pdfReturn;
	}

	public byte[] dibujarLinea(byte[] pdf, int pag, float ancho, float xPosInicial, float yPosInicial, float xPosFinal, float yPosFinal) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		int numPaginas = 0;
		byte[] pdfReturn = null;

		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				numPaginas = getNumPaginas(pdf);
				if (pag <= numPaginas) {
					// Usamos PdfStamper para escribir el texto
					stamp = new PdfStamper(reader, pdfOut);
					over = stamp.getOverContent(pag);
					over.setLineWidth(ancho);
					over.moveTo(xPosInicial, yPosInicial);
					over.lineTo(xPosFinal, yPosFinal);
					over.stroke();
					stamp.close();
					pdfReturn = pdfOut.toByteArray();
				} else {
					log.error("No existe la pagina especificada");
				}
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

	public int getNumPaginas(byte[] pdf) {
		PdfReader reader = null;
		int n = 0;
		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				n = reader.getNumberOfPages();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {}
			}
		} else {
			log.error("Pdf no válido");
		}
		return n;
	}
}
