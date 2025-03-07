package trafico.utiles;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import trafico.beans.utiles.CampoPdfBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Clase Pdf. Contiene los métodos para poder realizar pequeñas modificaciones a
 * un pdf ya existente. También se puede crear un pdf nuevo simple y realizar
 * sobre el pequeñas operaciones.
 * 
 * @author TB·Solutions
 * @version 1.0, 21/08/2007
 */
public class PdfMaker {

	private static final int _842 = 842;
	private static final int _595 = 595;
	private static final int _560 = 560;
	private static final int _415 = 415;
	private static final int _30 = 30;
	private static final int _295 = 295;
	private static final int _25 = 25;
	private static final int _15 = 15;
	private static final int _13 = 13;
	public static final String ALINEACION_DERECHA = "dcha";
	public static final String ALINEACION_CENTRO = "centro";
	public static final String ALINEACION_IZQUIERDA = "izqda";
	public static final String COURIER = "Courier";
	public static final String HELVETICA = "Helvetica";
	public static final String TIMES = "Times";
	public static final String TIMES_ROMAN = "Times-Roman";

	// log de errores
	private static final ILoggerOegam log = LoggerOegam.getLogger(PdfMaker.class);

	private float tamanoFuente = _13;
	private BaseFont bf = null;
	private Font font = null;

	/**
	 * Creamos el objeto pdf que usaremos para realizar modificaciones en el
	 * pdf. Se establece el tipo de fuente a usar.
	 * 
	 * @param log
	 *            Datos del log donde escribiran los metodos de Pdf.
	 */
	public PdfMaker() {
		try {
			// Establecemos los parametros por defecto de las fuentes.
			this.bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,
					BaseFont.NOT_EMBEDDED);
			this.font = new Font(bf, tamanoFuente, Font.NORMAL);

		} catch (Throwable e) {
			log.error(e);
		}
	}

	/**
	 * Creamos un nuevo pdf, con una hoja en blanco del tamaño dado. Si el
	 * tamaño de la página no es correcto se genera por defecto una página en
	 * A4. Si ocurre un error devuelve null.
	 * 
	 * @param TamanoPag
	 *            Tamaño de la página del documento.
	 * @return Devuelve el pdf.
	 */
	public byte[] nuevoPdf(String TamanoPag) {
		log.trace("Entra en nuevoPdf(String)");
		Document document = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		// Creamos un objeto Documento, si el tamaño no es válido se crea página
		// en A4.
		try {
			document = new Document(PageSize.getRectangle(TamanoPag));
			log.info("Se crea pagina de tamaño: " + TamanoPag);
		} catch (Throwable e) {
			log.error(e);
			log.error("Se crea pagina por defecto en A4");
			document = new Document(PageSize.A4);
		}
		pdfOut = new ByteArrayOutputStream();
		try {
			// Creamos un escritor que escucha al documento y direcciona un
			// PDF-stream a un fichero
			PdfWriter.getInstance(document, pdfOut);
			document.open();
			// Creamos una pagina del tamaño indicado.
			log.info("Añadimos pagina del tamaño dado por el usuario");
			document.newPage();
			// Añadimos Chunk.NEXTPAGE para poder dejar la página en blanco
			document.add(Chunk.NEXTPAGE);
			// Cerramos el documento.
			document.close();
			pdfReturn = pdfOut.toByteArray();
		} catch (Throwable e) {
			log.error(e);
		} finally {
			try {
				document.close();
			} catch (Throwable e) {
			}
		}
		// Devolvemos el pdf.
		return pdfReturn;
	}

	/**
	 * Creamos un nuevo pdf, con una hoja en blanco del tamaño dado. Si el
	 * tamaño de la página no es correcto se genera por defecto una página en
	 * A4. Si ocurre un error devuelve null.
	 * 
	 * @param tamX
	 *            Tamaño X de la página del documento.
	 * @param tamY
	 *            Tamaño Y de la página del documento.
	 * 
	 * @return Devuelve el pdf.
	 */
	public byte[] nuevoPdf(float tamX, float tamY) {
		log.trace("Entra en nuevoPdf(float,float)");
		Document document = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		// Creamos un objeto Documento, si el tamaño no es válido se crea página
		// en A4.
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
			// Creamos un escritor que escucha al documento y direcciona un
			// PDF-stream a un fichero
			PdfWriter.getInstance(document, pdfOut);
			document.open();
			// Creamos una pagina del tamaño indicado.
			document.newPage();
			// Añadimos Chunk.NEXTPAGE para poder dejar la página en blanco
			document.add(Chunk.NEXTPAGE);
			// Cerramos el documento.
			document.close();
			pdfReturn = pdfOut.toByteArray();
		} catch (Throwable e) {
			log.error(e);
		} finally {
			try {
				document.close();
			} catch (Throwable e) {
			}
			try {
				pdfOut.close();
			} catch (Throwable e) {
			}
		}
		// Devolvemos el pdf.
		return pdfReturn;
	}

	/**
	 * Abrimos el fichero pdf a partir de su ruta en disco. Si el fichero ocupa
	 * mas de 8MB o la operación no se realiza devuelve null.
	 * 
	 * @param pdfOriginal
	 *            Ruta del fichero pdf que queremos abrir.
	 * @return Devuelve el pdf.
	 */
	public byte[] abrirPdf(String pdf) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		byte[] pdfReturn = null;

		pdfOut = new ByteArrayOutputStream();
		log.trace("Entramos en abrirPdf");
		try {
			reader = new PdfReader(pdf);
			// Comprobamos si el fichero tiene mas de 8 MB o menos.
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
			} catch (Throwable e) {
			}
			try {
				pdfOut.close();
			} catch (Throwable e) {
			}
		}
		return pdfReturn;
	}

	/**
	 * Abrimos el fichero pdf (ficha tecnica) a partir de su ruta en disco. Si el fichero ocupa
	 * mas de 8MB o la operación no se realiza devuelve null.
	 * 
	 * @param pdfOriginal
	 *            Ruta del fichero pdf que queremos abrir.
	 * @return Devuelve el pdf.
	 */
	public byte[] abrirPdfFichaTecnica(String pdf) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		byte[] pdfReturn = null;

		pdfOut = new ByteArrayOutputStream();
		log.trace("Entramos en abrirPdf");
		try {
			reader = new PdfReader(pdf);

			// Mantis 0007604: error al imprimir PDF de ficha técnica
			// Variable unethicalreading: ignorará la presencia de una contraseña de propietario
			PdfReader.unethicalreading = true;
			// Comprobamos si el fichero tiene mas de 8 MB o menos.
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
			} catch (Throwable e) {
			}
			try {
				pdfOut.close();
			} catch (Throwable e) {
			}
		}
		return pdfReturn;
	}

	/**
	 * Abrimos el fichero pdf a partir de su ruta en disco. Si el fichero ocupa
	 * mas de 8MB o la operación no se realiza devuelve null.
	 * 
	 * Para este método pasaremos como parámetro la password de apertura de plantilla.
	 * 
	 * @param pdfOriginal
	 *            Ruta del fichero pdf que queremos abrir.
	 * @return Devuelve el pdf.
	 */
	public byte[] abrirPdf(String pdf, String ownerPassword) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		byte[] pdfReturn = null;

		pdfOut = new ByteArrayOutputStream();
		log.trace("Entramos en abrirPdf");
		try {
			reader = new PdfReader(pdf, ownerPassword.getBytes());
			// Comprobamos si el fichero tiene mas de 8 MB o menos.
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
			} catch (Throwable e) {
			}
			try {
				pdfOut.close();
			} catch (Throwable e) {
			}
		}
		return pdfReturn;
	}

	/**
	 * 
	 * @param pdf
	 * @return
	 */
	public byte[] abrirBasicoPdf(String rutaFicheroPDF) {

		String returnPDF = "";
		StringBuffer contenidoPDF = new StringBuffer();
		BufferedReader br = null;

		try {
			br = new BufferedReader (new FileReader(new File (rutaFicheroPDF)));
			String linea;
			while (( linea = br.readLine()) != null){

				contenidoPDF.append(linea);
				contenidoPDF.append(System.getProperty("line.separator"));

			}
		} catch (Exception e) {
			log.error(e); 
			contenidoPDF = null;
		} finally {
			if (br!= null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error(e); 
				}
			}
		}

		returnPDF = null != contenidoPDF?contenidoPDF.toString():null;
		return null != returnPDF?returnPDF.getBytes():null;
	}

	/**
	 * Encriptamos el fichero y establecemos los permisos y propiedades del
	 * fichero. Si la operación no se ha podido realizar devuelve null.
	 * 
	 * @param pdf
	 *            El pdf que queremos encriptar.
	 * @param userPass
	 *            Contraseña de usuario.
	 * @param ownerPass
	 *            Contraseña del propietario.
	 * @param print
	 *            Permitira o no que el fichero resultante se pueda imprimir o
	 *            no.
	 * @param copy
	 *            Permitira o no que el fichero resultante se pueda copiar o no.
	 * @param rellenar
	 *            Permitira o no que el fichero resultante se pueda rellenar o
	 *            no.
	 * @param modificar
	 *            Permitira o no que el fichero resultante se pueda modificar o
	 *            no.
	 * @param strengthKey
	 *            Establece el tipo de codificacion que vamos a utilizar. (false
	 *            - 40 bits; true 128 bits).
	 * @return Devuelve el fichero pdf en forma de array de bytes.
	 */
	public static byte[] encriptarPdf(byte[] pdf, String userPass, String ownerPass,
			boolean print, boolean copy, boolean rellenar, boolean modificar,
			boolean strengthKey) {
		PdfReader reader = null;
		ByteArrayOutputStream pdfOut = null;
		int permisos = 0;
		byte[] pdfReturn = null;

		pdfOut = new ByteArrayOutputStream();
		// Comprobamos que pdf tenga datos.
		if (pdf != null) {
			// Establecemos los permisos con los que vamos a encriptar el
			// fichero.
			// permisos = PdfWriter.AllowModifyContents;
			if (print){
				permisos = permisos | PdfWriter.ALLOW_PRINTING;
			}
			if (copy){
				permisos = permisos | PdfWriter.ALLOW_COPY;
			}
			if (rellenar){
				permisos = permisos | PdfWriter.ALLOW_FILL_IN;
			}
			if (modificar){
				permisos = permisos | PdfWriter.ALLOW_MODIFY_CONTENTS;
			}
			log.debug("Permisos -> Modificar contenidos : " + modificar
					+ "; Imprimir : " + print + "; Copiar : " + copy
					+ "; Rellenar : " + rellenar);
			try {
				byte[] usuario = userPass.getBytes() == null ? null:userPass.getBytes();
				byte[] propietario = ownerPass.getBytes() == null ? null:ownerPass.getBytes();
				// Encriptamos el pdf.
				reader = new PdfReader(pdf);
				PdfEncryptor.encrypt(reader, pdfOut, usuario,
						propietario, permisos, strengthKey);
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
				pdfReturn = null;
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else {
			log.warn("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Guardamos el pdf en disco.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param pdfDestino
	 *            Ruta de destino donde guardar el pdf.
	 * @return Devuelve si la operación se ha realizado con éxito.
	 */
	public boolean guardarPdfToArchivo(byte[] pdf, String pdfDestino) {
		boolean pdfReturn = false;
		FileOutputStream fo = null;

		log.trace("Entramos en guardarPdfToArchivo");
		// Si el pdf es correcto, lo guardamos al fichero especificado.
		if (pdf != null) {
			try {
				// Creamos un fichero
				File file = new File(pdfDestino);
				fo = new FileOutputStream(file);
				// Escribimos el pdf en ese fichero.
				fo.write(pdf);
				fo.close();
				pdfReturn = true;
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					fo.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Concatenamos el fichero el numero de veces dado. El fichero resultante no
	 * debe ser mayor de 8MB para que la acción se pueda realizar.
	 * 
	 * @param pdf
	 *            Pdf a concatenar.
	 * @param numVeces
	 *            Numero de veces que quiere concatenar el fichero.
	 * @return Devuelve el pdf concatenado.
	 */
	public byte[] concatenarPdf(byte[] pdf, long numVeces) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfCopyFields copy = null;
		byte[] pdfReturn = null;
		int ind;

		log.trace("Entramos en concatenarPdf");
		// Comprobamos que el pdf sea válido.
		if (pdf != null) {
			try {
				pdfOut = new ByteArrayOutputStream();
				reader = new PdfReader(pdf);
				copy = new PdfCopyFields(pdfOut);
				copy.setFullCompression();
				// Concatenamos el pdf el numero de veces dado
				for (ind = 1; ind <= numVeces; ind++){
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
				} catch (Throwable e) {
				}
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Concatenamos dos ficheros. El fichero resultante no debe ser mayor de 8MB
	 * para que la acción se pueda realizar.
	 * 
	 * @param pdf1
	 *            Pdf nº1 a concatenar.
	 * @param pdf2
	 *            Pdf nº2 a concatenar.
	 * @return Devuelve los dos pdf's concatenados
	 */
	public byte[] concatenarPdf(byte[] pdf1, byte[] pdf2) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader1 = null;
		PdfReader reader2 = null;
		PdfCopyFields copy = null;
		byte[] pdfReturn = null;

		log.trace("Entramos en concatenarPdf");
		// Comprobamos si los pdf's son válidos
		if (pdf1 != null && pdf2 != null) {
			try {
				pdfOut = new ByteArrayOutputStream();
				reader1 = new PdfReader(pdf1);
				reader2 = new PdfReader(pdf2);
				copy = new PdfCopyFields(pdfOut);
				copy.setFullCompression();
				// Concatenamos los dos ficheros.
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
				} catch (Throwable e) {
				}
				try {
					reader1.close();
				} catch (Throwable e) {
				}
				try {
					reader2.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Uno de los pdf's no es válido");
		}
		return pdfReturn;
	}

	/**
	 * Concatenamos varios ficheros. El fichero resultante no debe ser mayor de
	 * 8MB para que la acción se pueda realizar.
	 * 
	 * @param listaBytes
	 *            Lista de pdfs a concatenar.
	 * 
	 * @return Devuelve los dos pdf's concatenados
	 */
	public static byte[] concatenarPdf(ArrayList<byte[]> listaBytes) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader1 = null;
		PdfCopyFields copy = null;
		byte[] pdfReturn = null;

		log.trace("Entramos en concatenarPdf");

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
			} catch (Throwable e) {
			}
			try {
				reader1.close();
			} catch (Throwable e) {
			}
			try {
				pdfOut.close();
			} catch (Throwable e) {
			}
		}

		return pdfReturn;
	}

	// METODOS PARA EL TRATAMIENTO DE PAGINAS

	/**
	 * Se obtiene el numero de páginas de un pdf. Si se le pasa un pdf no
	 * válido, devuelve 0.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @return Devuelve el numero de páginas del pdf
	 */
	public int getNumPaginas(byte[] pdf) {
		PdfReader reader = null;
		int n = 0;

		// Comprobamos que el pdf sea válido.
		if (pdf != null) {
			try {
				reader = new PdfReader(pdf);
				n = reader.getNumberOfPages();
				log.debug("Numero de paginas: " + n);
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return n;
	}

	/**
	 * Nos dice el tamaño de la página indicada (A3, A4,.., B0,.., LETTER, ...).
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param numPag
	 *            Numero de la página de la que queremos saber el tamaño.
	 * @return Devuelve una cadena indicando el tamaño de la página (A3, A4,
	 *         ...).
	 */
	public String getTamanoPagina(byte[] pdf, int numPag) {
		PdfReader reader = null;
		Rectangle size = null;
		int numPaginas = 0;
		String pdfReturn = "";

		// Comprobamos si el pdf es válido
		if (pdf != null) {
			numPaginas = getNumPaginas(pdf);
			// Comprobamos que el numero de pagina que nos piden exista en el
			// documento.
			if (numPag <= numPaginas) {
				try {
					reader = new PdfReader(pdf);
					// Obtenemos el tamaño de la página.
					size = reader.getPageSize(numPag);
					reader.close();
					pdfReturn = size.toString();
					log.info("Tamaño de la pagina: " + size);
				} catch (Throwable e) {
					log.error(e);
				} finally {
					try {
						reader.close();
					} catch (Throwable e) {
					}
				}
			} else{
				log.error("La pagina solicitada no existe");
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Extrae la página determinada
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param numPag
	 *            Numero de la página a extraer.
	 * @return Devuelve la página.
	 */
	public byte[] extraerPagina(byte[] pdf, int numPag) {
		PdfReader reader = null;
		PdfCopy copy = null;
		Document document = null;
		int numPaginas = 0;
		byte[] pdfReturn = null;

		ByteArrayOutputStream pdfOut = null;

		log.trace("Entramos en extraerPagina");
		// Comprobamos si el pdf es válido
		if (pdf != null) {
			numPaginas = getNumPaginas(pdf);
			// Comprobamos que el numero de pagina que nos piden exista en el
			// documento.
			if (numPag <= numPaginas) {
				try {
					pdfOut = new ByteArrayOutputStream();
					// Abrimos el pdf.
					reader = new PdfReader(pdf);
					document = new Document();
					copy = new PdfCopy(document, pdfOut);
					document.open();
					// Obtenemos la página que nos han pedido.
					PdfImportedPage page1 = copy
							.getImportedPage(reader, numPag);
					copy.setFullCompression();
					// Copiamos la página en un nuevo pdf.
					copy.addPage(page1);
					copy.close();
					document.close();
					reader.close();
					pdfReturn = pdfOut.toByteArray();
				} catch (Throwable e) {
					log.error(e);
				} finally {
					try {
						reader.close();
					} catch (Throwable e) {
					}
					try {
						document.close();
					} catch (Throwable e) {
					}
					try {
						copy.close();
					} catch (Throwable e) {
					}
				}
			} else{
				log.error("La pagina solicitada no existe");
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Inserta el número de paginas en blanco al final del documento.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param tamano
	 *            Tamaño de la página a añadir (A3, A4, ...)
	 * @param numVeces
	 *            Numero de veces a insertar la página en blanco.
	 * @return Devuelve el pdf con las páginas insertadas.
	 */
	public byte[] insertarPaginaEnBlanco(byte[] pdf, String tamano, int numVeces) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader1 = null;
		PdfReader reader2 = null;
		PdfCopyFields copy = null;
		byte[] pag = null;
		byte[] pdfReturn = null;
		int ind = 0;

		log.trace("Entramos en insertarPaginaEnBlanco");
		// Comprobamos que el pdf es válido.
		if (pdf != null) {
			try {
				pdfOut = new ByteArrayOutputStream();
				// Creamos una página en blanco del tamaño requerido.
				pag = nuevoPdf(tamano);
				reader1 = new PdfReader(pdf);
				reader2 = new PdfReader(pag);
				copy = new PdfCopyFields(pdfOut);
				// Copio el pdf a un nuevo pdf.
				copy.addDocument(reader1);
				// Añado las nuevas hojas en blanco al final del pdf.
				for (ind = 1; ind <= numVeces; ind++){
					copy.addDocument(reader2);
				}
				copy.close();
				reader1.close();
				reader2.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader1.close();
				} catch (Throwable e) {
				}
				try {
					reader2.close();
				} catch (Throwable e) {
				}
				try {
					copy.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Inserta una pagina dada un numero de veces al final del pdf.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param pag
	 *            Pagina que queremos insertar.
	 * @param numVeces
	 *            Numero de veces que queremos insertar la pagina.
	 * @return Devuelve el pdf con la/s hoja/s añadida/s.
	 */
	public byte[] insertarPagina(byte[] pdf, byte[] pag, int numVeces) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader1 = null;
		PdfReader reader2 = null;
		PdfCopyFields copy = null;
		byte[] pdfReturn = null;
		int ind;

		// Comprobamos que el PDF y la página son válidos.
		if ((pdf != null) && (pag != null)) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader1 = new PdfReader(pdf);
				reader2 = new PdfReader(pag);
				copy = new PdfCopyFields(pdfOut);
				// Copiamos el pdf a un nuevo documento
				copy.addDocument(reader1);
				// Añadimos la pagina el numero de veces indicado.
				for (ind = 1; ind <= numVeces; ind++){
					copy.addDocument(reader2);
				}
				copy.close();
				pdfReturn = pdfOut.toByteArray();
				reader1.close();
				reader2.close();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader1.close();
				} catch (Throwable e) {
				}
				try {
					reader2.close();
				} catch (Throwable e) {
				}
				try {
					copy.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf o página no válidos");
		}

		return pdfReturn;
	}

	// METODOS PARA EL USO DE LOS ACROFORMS TEXT

	/**
	 * Escribe el valor de los campos de la plantilla del pdf.
	 * 
	 * @param pdf
	 * @param campos
	 * @return
	 */
	public byte[] setCampos(byte[] pdf, List<CampoPdfBean> campos) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		// Comprobamos que el pdf y la pagina son válidos.
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);

				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				for (CampoPdfBean campo : campos) {

					AcroFields form1 = stamp.getAcroFields();

					//SINCODIG Nuevo bloque de sentencias. Inicio
					String strFuente;
					if(null!=campo.getFuente()){
						strFuente=campo.getFuente();
					} else{
						strFuente = BaseFont.TIMES_ROMAN;
					}
					//SINCODIG Nuevo bloque de sentencias. Fin

					establecerFuente(strFuente, campo.isNegrita(), campo
							.isCursiva(), campo.getTamFuente()); //SINCODIG sustituimos HELVETICA por strFuente

					form1.setFieldProperty(campo.getNombre(), "textfont", bf,
							null);
					form1.setFieldProperty(campo.getNombre(), "textsize",
							tamanoFuente, null);

					// Establecemos el valor del AcroCampo predeterminado
					if(null!=campo.getValorTexto()){
						form1.setField(campo.getNombre(), campo.getValorTexto());
					} else
						form1.setField(campo.getNombre(), "");

					establecerFuente(strFuente, false, false, _13); //SINCODIG sustituimos HELVETICA por strFuente
				}

				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	//PDF_TRANSMISION
	/**
	 * Escribe el valor de los campos de la plantilla del pdf.
	 * 
	 * @param pdf
	 * @param campos
	 * @return
	 */
	public byte[] setCampos(byte[] pdf, ArrayList<CampoPdfBean> campos, float tamanoFuenteDef) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		// Comprobamos que el pdf y la pagina son válidos.
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);

				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				for (CampoPdfBean campo : campos) {
					AcroFields form1 = stamp.getAcroFields();

					//SINCODIG Nuevo bloque de sentencias. Inicio
					String strFuente;
					if(null!=campo.getFuente()){
						strFuente=campo.getFuente();
					} else{
						strFuente=HELVETICA;
					}
					//SINCODIG Nuevo bloque de sentencias. Fin

					if(campo.getTamFuente() == _13){
						campo.setTamFuente(tamanoFuenteDef);
					}

					establecerFuente(strFuente, campo.isNegrita(), campo
							.isCursiva(), campo.getTamFuente()); //SINCODIG sustituimos HELVETICA por strFuente

					form1.setFieldProperty(campo.getNombre(), "textfont", bf,
							null);
					form1.setFieldProperty(campo.getNombre(), "textsize",
							tamanoFuente, null);

					// Establecemos el valor del AcroCampo predeterminado
					form1.setField(campo.getNombre(), campo.getValorTexto());

					establecerFuente(strFuente, false, false, tamanoFuenteDef); //SINCODIG sustituimos HELVETICA por strFuente

				}
				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();

			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Establece el valor del campo Input (AcroForm Text) especificado.
	 * 
	 * @param pdf
	 *            Pdf a rellenar.
	 * @param nombreCampo
	 *            Nombre del campo input que quieres rellenar
	 * @param cadena
	 *            Texto para rellenar el campo.
	 * @return Devuelve el pdf con el campo relleno.
	 */
	public byte[] setCampo(byte[] pdf, String nombreCampo, String cadena) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		log.debug("Campo a escribir: " + nombreCampo + " - Valor: " + cadena);
		// Comprobamos que el pdf y la pagina son válidos.
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);

				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				// Obtenemos los AcroCampos
				AcroFields form1 = stamp.getAcroFields();
				form1.setFieldProperty(nombreCampo, "textfont", bf, null);
				form1.setFieldProperty(nombreCampo, "textsize", tamanoFuente,
						null);

				// Establecemos el valor del AcroCampo predeterminado
				form1.setField(nombreCampo, cadena);
				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Obtenemos el valor de uno de los campos input (Acroform Text).
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param nombreCampo
	 *            Nombre del campo del que queremos obtener el valor.
	 * @return Devuelve el contenido del campo. En caso de no existir el campo
	 *         devuelve null.
	 */
	public String getCampo(byte[] pdf, String nombreCampo) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		String pdfReturn = null;

		log.trace("Entramos en getCampo");
		// Comprobamos que el pdf y la pagina son válidos.
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);
				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				// Obtenemos los AcroCampos
				AcroFields form1 = stamp.getAcroFields();

				// Obtenemos el valor del AcroCampos.
				pdfReturn = form1.getField(nombreCampo);
				stamp.close();
				reader.close();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	// METODOS PARA LA INSERCION DE METAINFORMACION

	/**
	 * Inserta o actualiza la metaInformacion.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param campo
	 *            Campo de la metainformación a actualizar.
	 * @param info
	 *            Información que actualizamos.
	 * @return Devuelve el pdf con la metainformación actualizada.
	 */
	public byte[] setMetaInfo(byte[] pdf, String campo, String info) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		HashMap<String, String>  moreInfo = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		log.trace("Entramos en setMetaInfo");
		// Comprobamos que el pdf es válido
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);
				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				moreInfo = new HashMap<String, String>();
				// Ponemos en la tabla el valor del campo a cambiar.
				moreInfo.put(campo, info);
				// Actualizamos en el fichero pdf.
				stamp.setMoreInfo(moreInfo);
				stamp.close();
				reader.close();
				pdfReturn = pdfOut.toByteArray();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Método para obtener la información de un campo de la metainformación.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param campo
	 *            Campo del que se quiere obtener la información.
	 * @return Devuelve la información contenida en el campo de metainformación
	 *         pedido.
	 */
	public String getMetaInfo(byte[] pdf, String campo) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		HashMap<Object, Object>  moreInfo = null;
		ByteArrayOutputStream pdfOut = null;
		String pdfReturn = null;

		log.trace("Entramos en getMetaInfo");
		if (pdf != null) {
			try {
				// We create a reader for a certain document
				reader = new PdfReader(pdf);
				// We create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				moreInfo = new HashMap<Object, Object> ();
				// Obtenemos el valor del campo.
				pdfReturn = (String) moreInfo.get(campo);
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	// METODO PARA LA INSERCION DE NUMEROS DE PAGINAS

	/**
	 * Permite la inserción de numeros de página en los pdf's.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param pagInf
	 *            Pagina donde empezar a poner los numeros de pagina.
	 * @param pagSup
	 *            Pagina donde acabaran los numeros de pagina.
	 * @param numInicio
	 *            Número de página por el que empezar.
	 * @param alineacion
	 *            Si queremos que este a la izquierda, en el centro o en la
	 *            derecha. (ALINEACION_IZQUIERDA, ALINEACION_CENTRO,
	 *            ALINEACION_DERECHA).
	 * @return Devuelve el pdf con los numeros de páginas incorporador
	 */
	public byte[] insertarNumerosDePaginas(byte[] pdf, int pagInf, int pagSup,
			int numInicio, String alineacion) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		ByteArrayOutputStream pdfOut = null;
		byte[] pdfReturn = null;

		int numPaginas;
		int index;

		log.trace("Entramos en insertarNumerosDePaginas");
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);

				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				numPaginas = getNumPaginas(pdf);

				// Validamos si el intervalo es correcto.
				if ((pagSup <= numPaginas) && (pagInf <= pagSup)) {
					stamp = new PdfStamper(reader, pdfOut);
					for (index = pagInf; index <= pagSup; index++) {
						over = stamp.getOverContent(index);
						over.beginText();
						over.setFontAndSize(bf, tamanoFuente);
						if (alineacion.compareTo(ALINEACION_IZQUIERDA) == 0){
							over.setTextMatrix(_30, _25);
						}
						if (alineacion.compareTo(ALINEACION_CENTRO) == 0){
							over.setTextMatrix(_295, _25);
						}
						if (alineacion.compareTo(ALINEACION_DERECHA) == 0){
							over.setTextMatrix(_560, _25);
						}
						over.showText("" + numInicio);
						over.endText();
						numInicio++;
					}
					stamp.close();
					pdfReturn = pdfOut.toByteArray();
				} else{
					log.error("Intervalo de paginas incorrecto");
				}
				reader.close();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					reader.close();
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	// MÉTODOS PARA LA CREACIÓN DE NUBES DE PUNTOS

	/**
	 * Crea una nube de puntos.
	 * 
	 * @param texto
	 *            Texto a partir del cual se genera la nube de puntos.
	 * @return Devuelve la nube de puntos en forma de imagen
	 *         (com.lowagie.text.Image)
	 */
	public Image crearNubePuntos(String texto) {
		BarcodePDF417 pdf417 = new BarcodePDF417();
		Image imagen = null;

		log.trace("Entramos en crearNubePuntos");
		pdf417.setText(texto);
		try {
			imagen = pdf417.getImage();
		} catch (Throwable e) {
			log.error(e);
		}
		return imagen;
	}

	/**
	 * Crea una nube de puntos.
	 * 
	 * @param texto
	 *            Texto a partir del cual se genera la nube de puntos.
	 * @return Devuelve la nube de puntos en forma de imagen
	 *         (com.lowagie.text.Image)
	 */
	public Image crearNubePuntosMatw(String texto) {
		BarcodePDF417 pdf417 = new BarcodePDF417();
		Image imagen = null;

		log.trace("Entramos en crearNubePuntos");

		try {
			pdf417.setText(texto);
			imagen = pdf417.getImage();
		} catch (Throwable e) {
			log.error(e);
		}
		return imagen;
	}

	// METODO PARA LA CARGA DE UNA IMAGEN

	/**
	 * Carga una imagen.
	 * 
	 * @param rutaImagen
	 *            Ruta de donde esta la imagen.
	 * @return Devuelve la imagen (com.lowagie.text.Image).
	 */
	public Image cargarImagen(String rutaImagen) {
		Image img = null;

		try {
			img = Image.getInstance(rutaImagen);
		} catch (Throwable e) {
			log.error(e);
		}
		return img;
	}

	// METODOS PARA LA INSERCION DE IMAGENES

	/**
	 * Inserta una imagen en el pdf.
	 * 
	 * @param pdf
	 *            Pdf
	 * @param img
	 *            Imagen a insertar (com.lowagie.text.Image).
	 * @param numPaginas
	 *            En que paginas se quiere insertar la imagen.
	 * @param xPos
	 *            Posicion x de la imagen (Con respecto a la esquina inf. izq.
	 *            de la pagina).
	 * @param yPos
	 *            Posicion y de la imagen (Con respecto a la esquina inf. izq.
	 *            de la pagina)
	 * @param size
	 *            Tamaño de la imagen en porcentaje con el original.
	 * @return Devuelve el pdf con la imagen insertada.
	 */
	public byte[] insertarImagen(byte[] pdf, Image img, int[] numPaginas,
			int xPos, int yPos, int size) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		ByteArrayOutputStream pdfOut = null;
		int index = 0;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarImagen");
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);
				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				// Establecemos la posicion y el tamaño de la imagen.
				img.setAbsolutePosition(xPos, yPos);
				img.scalePercent(size);

				// Insertamos la imagen en las páginas dadas.
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
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Inserta una imagen en el pdf.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param img
	 *            Imagen a insertar (com.lowagie.text.Image).
	 * @param numPaginas
	 *            En que paginas se quiere insertar la imagen.
	 * @param xPos
	 *            Posicion x de la imagen (Con respecto a la esquina inf. izq.
	 *            de la pagina).
	 * @param yPos
	 *            Posicion y de la imagen (Con respecto a la esquina inf. izq.
	 *            de la pagina)
	 * @return Devuelve el pdf con la imagen insertada.
	 */
	public byte[] insertarImagen(byte[] pdf, Image img, int[] numPaginas,
			float xPos, float yPos) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		ByteArrayOutputStream pdfOut = null;
		int index;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarImagen");
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);
				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);
				// Establecemos la posición de la imagen.
				img.setAbsolutePosition(xPos, yPos);

				// Insertamos la imagen en las páginas dadas.
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
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	// METODOS PARA EL REESCALADO DE UNA IMAGEN

	/**
	 * Reescala la imagen a los parametros de ancho y alto dados.
	 * 
	 * @param img
	 *            Imagen a reescalar.
	 * @param ancho
	 *            Ancho de la imagen.
	 * @param alto
	 *            Alto de la imagen.
	 * @return Devuelve la imagen reescalada.
	 */
	public Image escalarImagen(Image img, float ancho, float alto) {
		Image image;

		log.trace("Entramos en escalarImagen");
		image = img;
		image.scaleAbsolute(ancho, alto);
		return image;
	}

	/**
	 * Reescala la imagen a los parametros de porcentaje en ancho y alto dados.
	 * 
	 * @param img
	 *            Imagen a reescalar.
	 * @param anchoPercent
	 *            Porcentaje de reescalado de la anchura.
	 * @param altoPercent
	 *            Porcentaje de reescalado de la altura.
	 * @return
	 */
	public Image escalarImagenPorcentaje(Image img, float anchoPercent,
			float altoPercent) {
		Image image;

		log.trace("Entramos en escalarImagenPorcentaje");
		image = img;
		image.scalePercent(anchoPercent, altoPercent);
		return image;
	}

	// METODOS PARA INSERTAR UNA MARCA DE AGUA EN EL DOCUMENTO

	/**
	 * Inserta la marca de agua en el pdf.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param img
	 *            Marca de Agua a insertar en forma de imagen
	 *            (com.lowagie.text.Image).
	 * @param numPaginas
	 *            En que paginas se quiere insertar la imagen.
	 * @param xPos
	 *            Posicion x de la imagen (Con respecto a la esquina inf. izq.
	 *            de la pagina).
	 * @param yPos
	 *            Posicion y de la imagen (Con respecto a la esquina inf. izq.
	 *            de la pagina)
	 * @param size
	 *            Tamaño de la imagen en porcentaje con el original.
	 * @return Devuelve el pdf con la imagen insertada.
	 */
	public byte[] insertarMarcaDeAgua(byte[] pdf, Image img, int[] numPaginas,
			int xPos, int yPos, int size) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte under = null;
		ByteArrayOutputStream pdfOut = null;
		int index;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarMarcaDeAgua");
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);
				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				// Establecemos tamaño y posicion de la marca de agua
				img.setAbsolutePosition(xPos, yPos);
				img.scalePercent(size);

				// Insertamos la marca de agua en las páginas dadas.
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
				} catch (Throwable e) {
				}
				try {
					stamp.close();
				} catch (Throwable e) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	// METODOS PARA LA CREACION DE LOS CODIGOS DE BARRAS

	/**
	 * Crea el código de barras 128 con valores predeterminados.
	 * 
	 * @param codigo
	 *            Texto que queremos codificar.
	 * @param anchoBarras
	 *            Anchura de las barras.
	 * @param alturaBarras
	 *            Altura de las barras.
	 * @param digitoControl
	 *            Digito de control. (El valor por defecto es false)
	 * @return Devuelve una imagen (com.lowagie.text.Image) con el código de
	 *         barras.
	 */
	public Image crearCodigoBarras128(String codigo, float anchoBarras,
			float alturaBarras, boolean digitoControl) {
		Image imagen = null;

		log.trace("Entramos en insertarImagen");
		try {
			codigo = codigo.replace('Ñ', '#');
			// Creamos el código de baras
			Barcode128 code128 = new Barcode128();
			code128.setBarHeight(alturaBarras);
			code128.setX(anchoBarras);
			code128.setChecksumText(digitoControl);
			code128.setCode(codigo);
			imagen = Image.getInstance(code128.createAwtImage(Color.BLACK,
					Color.WHITE), null, true);
		} catch (Throwable e) {
			log.error(e);
		}
		return imagen;
	}

	/**
	 * Crea el código de barras 128 con valores predeterminados y lo inserta en
	 * el pdf.
	 * 
	 * @param codigo
	 *            Texto que queremos codificar.
	 * @param pdf
	 *            Pdf.
	 * @param anchoBarras
	 *            Ancho de las barras. Con valor negativo se usan valores por
	 *            defecto.
	 * @param alturaBarras
	 *            Alto de las barras. Con valor negativo se usan valores por
	 *            defecto.
	 * @param distanciaTexto
	 *            Distancia del texto a las barras. Con valor negativo se usan
	 *            valores por defecto.
	 * @param digitoControl
	 *            Digito de control. (El valor por defecto es false)
	 * @param xPos
	 *            Posicion X.
	 * @param yPos
	 *            Posicion Y
	 * @return Devuelve el pdf
	 */
	public byte[] crearCodigoBarras128ConTexto(String codigo, byte[] pdf,
			float anchoBarras, float alturaBarras, float distanciaTexto,
			boolean digitoControl, float xPos, float yPos) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		ByteArrayOutputStream pdfOut = null;

		Image imagen = null;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarImagen");
		try {
			pdfOut = new ByteArrayOutputStream();
			// Es necesario el tmp para poder crear el código de barras con el
			// texto.
			reader = new PdfReader(pdf);
			stamp = new PdfStamper(reader, pdfOut);
			// Obtenemos el contenido de superficie
			over = stamp.getOverContent(1);
			codigo = codigo.replace('Ñ', '#');
			// Creamos el código de baras
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
			} catch (Throwable e) {
			}
			try {
				stamp.close();
			} catch (Throwable e) {
			}
			try {
				pdfOut.close();
			} catch (Throwable e) {
			}
		}
		return pdfReturn;
	}

	// METODOS PARA ESCRIBIR TEXTO EN EL PDF

	/**
	 * Escribe el texto dado en la página y posicion del pdf indicado
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param texto
	 *            Texto a escribir
	 * @param pag
	 *            Página en la que se va a escribir.
	 * @param x
	 *            Posicion x del texto (Con respecto a la esquina inf. izq. de
	 *            la pagina)
	 * @param y
	 *            Posicion y del texto (Con respecto a la esquina inf. izq. de
	 *            la pagina)
	 * @return Devuelve el pdf.
	 */
	public byte[] escribirTexto(byte[] pdf, String texto, int pag, float x,
			float y) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		int numPaginas = 0;
		byte[] pdfReturn = null;

		log.trace("Entramos en escribirTexto");
		// Comprobamos que el pdf es válido
		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				// Obtenemos el numero de páginas del pdf.
				numPaginas = getNumPaginas(pdf);
				if (pag <= numPaginas) {
					// Usamos PdfStamper para escribir el texto
					stamp = new PdfStamper(reader, pdfOut);
					over = stamp.getOverContent(pag);
					over.beginText();
					over.setFontAndSize(bf, tamanoFuente);
					over.setTextMatrix(x, y);
					over.showText(texto);
					over.endText();
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
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else {
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Escribe el texto dado en una columna con la posición del pdf indicado
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param texto
	 *            Texto a escribir
	 * @param pag
	 *            Página en la que se va a escribir.
	 * @param x
	 *            Posicion x del texto (Con respecto a la esquina inf. izq. de
	 *            la pagina)
	 * @param y
	 *            Posicion y del texto (Con respecto a la esquina inf. izq. de
	 *            la pagina)
	 * @param x2
	 *            Posicion final x de la columna
	 * @param y2
	 *            Posicion final y de la columna
	 * @param separacionLineas
	 *            Separación entre las líneas
	 *            
	 * @return Devuelve el pdf.
	 */
	public byte[] escribirTextoColumna(byte[] pdf, String texto, int pag, float x, float y, float x2, float y2, float separacionLineas) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		int numPaginas = 0;
		byte[] pdfReturn = null;

		log.trace("Entramos en escribirTextoColumna");
		// Comprobamos que el pdf es válido
		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				// Obtenemos el numero de páginas del pdf.
				numPaginas = getNumPaginas(pdf);
				if (pag <= numPaginas) {
					// Usamos PdfStamper para escribir el texto
					stamp = new PdfStamper(reader, pdfOut);
					over = stamp.getOverContent(pag);
					ColumnText ct = new ColumnText(over);
					ct.setSimpleColumn(x, y, x2, y2, separacionLineas, Element.ALIGN_LEFT);
					ct.addText(new Phrase(1, texto, font));
					ct.go();
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
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else {
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Escribe un texto y lo rota.
	 * 
	 * @param texto
	 *            Texto que queremos codificar.
	 * @param pdf
	 *            Pdf.
	 * @param pag
	 *            Numero de pagina en la que escribir el texto.
	 * @param xPos
	 *            Posicion X.
	 * @param yPos
	 *            Posicion Y
	 * @param angulo
	 *            Angulo de rotación del texto.
	 * @param color
	 *            Color del texto.
	 * @return Devuelve el pdf
	 */
	public byte[] escribirTextoRotado(String texto, byte[] pdf, int pag,
			float xPos, float yPos, float angulo, BaseColor color) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte cb = null;
		Image imagen = null;
		int numPaginas = 0;
		byte[] pdfReturn = null;
		float valorPruebaError = _415;
		float xTemplate = _595;
		float yTemplate = _842;

		log.trace("Entramos en escribirTextoRotado");
		// Comprobamos que el pdf es válido
		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				// Obtenemos el numero de páginas del pdf.
				numPaginas = getNumPaginas(pdf);
				if (pag <= numPaginas) {
					// Usamos PdfStamper para escribir el texto
					stamp = new PdfStamper(reader, pdfOut);
					cb = stamp.getUnderContent(pag);

					PdfTemplate template = cb.createTemplate(xTemplate,
							yTemplate);
					template.beginText();
					template.setColorFill(color);
					template.setFontAndSize(bf, tamanoFuente);
					template.setTextMatrix(0, 0);
					template.showText(texto);
					template.endText();

					imagen = Image.getInstance(template);
					imagen.setAbsolutePosition(xPos - valorPruebaError, yPos);
					imagen.setRotationDegrees(angulo);
					cb.addImage(imagen);
					stamp.close();
					pdfReturn = pdfOut.toByteArray();
				} else {
					log.error("No existe la pagina especificada");
				}
				reader.close();
			} catch (Throwable e) {
				log.error(e);
				//e.printStackTrace();
			} finally {
				try {
					stamp.close();
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else {
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Dado el documento pdf y la lista de etiquetas se escriben dichas
	 * etiquetas en la posición correspondiente.
	 * 
	 * @param pdf
	 * @param campos
	 *            Lista de campos. Pueden ser imágenes o texto.
	 * @return
	 */
	public byte[] insertarEtiquetas(byte[] pdf, ArrayList<CampoPdfBean> campos) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		byte[] pdfReturn = null;

		log.trace("Entramos en insertarEtiquetas");
		// Comprobamos que el pdf es válido
		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				// Usamos PdfStamper para escribir el texto
				stamp = new PdfStamper(reader, pdfOut);
				// Obtenemos el numero de páginas del pdf.
				int numPaginas = reader.getNumberOfPages();
				for (CampoPdfBean campo : campos) {
					if (campo.getPagina() <= numPaginas) {
						// Si el campo no es una imagen (es texto), es escribe
						// en el pdf.
						if (!campo.isImagen()) {
							log.trace("Va a escribir el valor: "
									+ campo.getValorTexto() + " en la página "
									+ campo.getPagina());
							over = stamp.getOverContent(campo.getPagina());
							over.beginText();
							over.setFontAndSize(bf, tamanoFuente);
							over
									.setTextMatrix(campo.getPosX(), campo
											.getPosY());
							over.showText(campo.getValorTexto());
							over.endText();
						}
						// Si el campo es una imagen se insserta.
						else {
							log.trace("Va a insertar una imagen en la página "
									+ campo.getPagina());
							over = stamp.getOverContent(campo.getPagina());
							Image img = campo.getValorImagen();
							// Establecemos la posicion y el tamaño de la
							// imagen.
							img.setAbsolutePosition(campo.getPosX(), campo
									.getPosY());
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
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else {
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	/**
	 * Escribe el texto dado en la pagina dentro de un cuadrado/columna
	 * establecido por las coordenadas. Coordenadas con respecto a la esquina
	 * inf. izq. de la página).
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param texto
	 *            Texto a escribir.
	 * @param pag
	 *            Página en la que escribir.
	 * @param x1
	 *            Coordenada x de la esquina inferior izqda. del recuadro.
	 * @param y1
	 *            Coordenada y de la esquina inferior izqda. del recuadro.
	 * @param x2
	 *            Coordenada x de la esquina superior dcha. del recuadro.
	 * @param y2
	 *            Coordenada y de la esquina superior dcha. del recuadro.
	 * @param alineacion
	 *            Alineacion del texto (izqda, centro, dcha).
	 * @return Devuelve el pdf.
	 */
	public byte[] escribirTextoColumna(byte[] pdf, String texto, int pag,
			int x1, int y1, int x2, int y2, String alineacion) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		int numPaginas = 0;
		byte[] pdfReturn = null;

		log.trace("Entramos en escribirTexto");
		// Comprobamos que el pdf es válido
		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				// Obtenemos el numero de páginas del pdf.
				numPaginas = getNumPaginas(pdf);
				if (pag <= numPaginas) {
					// Usamos PdfStamper para escribir el texto
					stamp = new PdfStamper(reader, pdfOut);
					over = stamp.getOverContent(pag);
					ColumnText ct = new ColumnText(over);
					if (alineacion.compareTo("izqda") == 0){
						ct.setSimpleColumn(x1, y1, x2, y2, _15, Element.ALIGN_LEFT);
					} else if (alineacion.compareTo("centro") == 0){
						ct.setSimpleColumn(x1, y1, x2, y2, _15, Element.ALIGN_CENTER);
					} else if (alineacion.compareTo("dcha") == 0){
						ct.setSimpleColumn(x1, y1, x2, y2, _15, Element.ALIGN_RIGHT);
					} else{
						ct.setSimpleColumn(x1, y1, x2, y2, _15, Element.ALIGN_LEFT);
					}
					ct.addText(new Phrase(1, texto + "\n", font));
					ct.go();
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
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else {
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	// METODOS PARA DIBUJAR GRAFICOS

	/**
	 * Inserta una linea en la posicion indicada.
	 * 
	 * @param pdf
	 *            Pdf.
	 * @param pag
	 *            Pagina del pdf en la que insertarlo.
	 * @param ancho
	 *            Anchura de la linea
	 * @param xPosInicial
	 *            Posicion X inicial.
	 * @param yPosInicial
	 *            Posicion Y inicial.
	 * @param xPosFinal
	 *            Posicion X final.
	 * @param yPosFinal
	 *            Posicion Y final.
	 * @return Devuelve el pdf.
	 */
	public byte[] dibujarLinea(byte[] pdf, int pag, float ancho,
			float xPosInicial, float yPosInicial, float xPosFinal,
			float yPosFinal) {
		ByteArrayOutputStream pdfOut = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		PdfContentByte over = null;
		int numPaginas = 0;
		byte[] pdfReturn = null;

		log.trace("Entramos en dibujarLinea");
		// Comprobamos que el pdf es válido
		if (pdf != null) {
			pdfOut = new ByteArrayOutputStream();
			try {
				reader = new PdfReader(pdf);
				// Obtenemos el numero de páginas del pdf.
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
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return pdfReturn;
	}

	// METODO PARA ESTABLECER FUENTES

	/**
	 * Establecemos la fuente con la que se escribira a continuación. (Tipo,
	 * estilo, tamaño)
	 * 
	 * @param fuente
	 *            Establece la fuente de lo que vayamos a escribir a
	 *            continuación. (COURIER, HELVETICA, TIMES o TIMES_ROMAN).
	 * @param negrita
	 *            Si sera en negrita
	 * @param cursiva
	 *            Se estara en cursiva
	 * @param tamanoFuente
	 *            El tamaño de la fuente.
	 * @return Devuelve true si la operación se ha realizado sin problemas.
	 */
	public boolean establecerFuente(String fuente, boolean negrita,
			boolean cursiva, float tamanoFuente) {
		boolean fontReturn = false;

		try {
			// Configuramos la fuente. Los parametros definiran que tipo de
			// fuente se utiliza.
			this.tamanoFuente = tamanoFuente;
			if (fuente.compareTo(TIMES) == 0) {
				if (negrita && cursiva){
					fuente = fuente + "-BoldItalic";
				} else if (negrita){
					fuente = fuente + "-Bold";
				} else if (cursiva){
					fuente = fuente + "-Italic";
				}
			} else if ((fuente.compareTo(HELVETICA) == 0)
					|| (fuente.compareTo(COURIER) == 0)) {
				if (negrita && cursiva){
					fuente = fuente + "-BoldOblique";
				} else if (negrita){
					fuente = fuente + "-Bold";
				} else if (cursiva){
					fuente = fuente + "-Oblique";
				}
			} else if (fuente.compareTo(TIMES_ROMAN) == 0) {
				fuente = "Times-Roman";
			} else {
				fuente = "Helvetica";
				if (negrita && cursiva){
					fuente = fuente + "-BoldOblique";
				} else if (negrita){
					fuente = fuente + "-Bold";
				} else if (cursiva){
					fuente = fuente + "-Oblique";
				}
			}
			// log.info("Fuente Establecida: " + fuente);
			// Se establece las fuentes (BaseFont y Font).
			this.bf = BaseFont.createFont(fuente, BaseFont.CP1252,
					BaseFont.NOT_EMBEDDED);

			// Necesario para la escritura en columnas.
			if (negrita && cursiva){
				font = new Font(bf, tamanoFuente, Font.BOLDITALIC);
			} else if (negrita){
				font = new Font(bf, tamanoFuente, Font.BOLD);
			} else if (cursiva){
				font = new Font(bf, tamanoFuente, Font.ITALIC);
			} else{
				font = new Font(bf, tamanoFuente, Font.NORMAL);
			}
			fontReturn = true;
		} catch (Throwable e) {
			log.error(e);
		}
		return fontReturn;
	}

	public boolean establecerFuente(String fuente, boolean negrita,
			boolean cursiva, float tamanoFuente, Color color) {
		boolean fontReturn = false;

		try {
			// Configuramos la fuente. Los parametros definiran que tipo de
			// fuente se utiliza.
			this.tamanoFuente = tamanoFuente;
			if (fuente.compareTo(TIMES) == 0) {
				if (negrita && cursiva){
					fuente = fuente + "-BoldItalic";
				} else if (negrita){
					fuente = fuente + "-Bold";
				} else if (cursiva){
					fuente = fuente + "-Italic";
				}
			} else if ((fuente.compareTo(HELVETICA) == 0)
					|| (fuente.compareTo(COURIER) == 0)) {
				if (negrita && cursiva){
					fuente = fuente + "-BoldOblique";
				} else if (negrita){
					fuente = fuente + "-Bold";
				} else if (cursiva){
					fuente = fuente + "-Oblique";
				}
			} else if (fuente.compareTo(TIMES_ROMAN) == 0) {
				fuente = "Times-Roman";
			} else {
				fuente = "Helvetica";
				if (negrita && cursiva){
					fuente = fuente + "-BoldOblique";
				} else if (negrita){
					fuente = fuente + "-Bold";
				} else if (cursiva){
					fuente = fuente + "-Oblique";
				}
			}
			// log.info("Fuente Establecida: " + fuente);
			// Se establece las fuentes (BaseFont y Font).
			this.bf = BaseFont.createFont(fuente, BaseFont.CP1252,
					BaseFont.NOT_EMBEDDED);

			// Necesario para la escritura en columnas.
			if (negrita && cursiva){
				font = new Font(bf, tamanoFuente, Font.BOLDITALIC);
			} else if (negrita){
				font = new Font(bf, tamanoFuente, Font.BOLD);
			} else if (cursiva){
				font = new Font(bf, tamanoFuente, Font.BOLD);
			} else{
				font = new Font(bf, tamanoFuente, Font.NORMAL);
			}
			fontReturn = true;

		} catch (Throwable e) {
			log.error(e);
		}
		return fontReturn;
	}

	/**
	 * Obtiene la posición de un campo dado su nombre.
	 * 
	 * @param pdf
	 * @param nombreCampo
	 * @return Un vector de floats de 5 posiciones: [0] -> Página [1] ->
	 *         Posición X de la esquina inferior izquierda [1] -> Posición Y de
	 *         la esquina inferior izquierda [1] -> Posición X de la esquina
	 *         superior derecha [1] -> Posición Y de la esquina superior derecha
	 */
	public List<FieldPosition> getFieldPosition(byte[] pdf, String nombreCampo) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		List<FieldPosition> posiciones = new ArrayList<>();

		log.trace("Entramos en getFieldPosition");

		// Comprobamos que el pdf y la pagina son válidos.
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);
				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				// Obtenemos los AcroCampos
				AcroFields form1 = stamp.getAcroFields();

				// Obtenemos el valor del AcroCampos.
				posiciones = form1.getFieldPositions(nombreCampo);
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					stamp.close();
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return posiciones;
	}

	/**
	 * Obtiene el nombre de todos los campos del pdf.
	 * 
	 * @param pdf
	 * @return Un Set con el nombre de los campos
	 */
	public Set<String> getAllFields(byte[] pdf) {
		PdfReader reader = null;
		PdfStamper stamp = null;
		ByteArrayOutputStream pdfOut = null;
		Set<String> nombres = null;

		log.trace("Entramos en getAllFields");
		// Comprobamos que el pdf y la pagina son válidos.
		if (pdf != null) {
			try {
				// we create a reader for a certain document
				reader = new PdfReader(pdf);
				// we create a stamper that will copy the document to a new file
				pdfOut = new ByteArrayOutputStream();
				stamp = new PdfStamper(reader, pdfOut);

				// Obtenemos los AcroCampos
				AcroFields form1 = stamp.getAcroFields();

				// Obtenemos el valor del AcroCampos.
				Map<String, Item>  fields = form1.getFields();
				nombres = fields.keySet();
			} catch (Throwable e) {
				log.error(e);
			} finally {
				try {
					stamp.close();
				} catch (Throwable ex) {
				}
				try {
					reader.close();
				} catch (Throwable ex) {
				}
				try {
					pdfOut.close();
				} catch (Throwable e) {
				}
			}
		} else{
			log.error("Pdf no válido");
		}
		return nombres;
	}

}