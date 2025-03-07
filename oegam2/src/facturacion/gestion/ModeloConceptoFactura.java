package facturacion.gestion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import escrituras.beans.ResultBean;
import facturacion.comun.ConstantesFacturacion;
import facturacion.dao.DatosDAO;
import hibernate.entities.facturacion.FacturaColegiadoConcepto;
import hibernate.entities.facturacion.FacturaConcepto;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloConceptoFactura {

	// log
	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloConceptoFactura.class);

	public FacturaConcepto cargarDatosModificacionFactura(FacturaColegiadoConcepto detalle) throws OegamExcepcion {
		return new FacturaConcepto();
	}

	// DRC@10-07-2012 Se encarga de comprobar que los campos obligatorios se han rellenado y en caso contrario mostrar el mensaje de error,
	//                configuracion en un properties
	public ResultBean validarDatosAltaConcepto(FacturaConcepto datosConcepto) throws OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(false);

		if (datosConcepto != null && (datosConcepto.getNumColegiado() == null || datosConcepto.getNumColegiado().trim().equals(""))) { 
			resultado.setMensaje(ConstantesFacturacion.ERROR_CONCEPTO_NUMCOLEGIADO);
			resultado.setError(true);
		}

		if (datosConcepto != null) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_CONCEPTO_ID);
			resultado.setError(true);
		}

		if (datosConcepto != null && "".equals(datosConcepto.getConcepto())) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_CONCEPTO_NOMBRE);
			resultado.setError(true);
		}

		if (!resultado.getError()) {
			resultado.setMensaje(mensaje(datosConcepto, Boolean.TRUE));
		}

		return resultado;
	}

	public ResultBean validarDatosAltaClave(FacturaConcepto datosConcepto) throws OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(false);

		if (datosConcepto != null && (datosConcepto.getNumColegiado() == null || datosConcepto.getNumColegiado().trim().equals(""))) { 
			resultado.setMensaje("");
			resultado.setError(true);
		}

		if (!resultado.getError()) {
			resultado.setMensaje(mensaje(datosConcepto, Boolean.TRUE));
		}

		return resultado;
	}

	// DRC@10-07-2012 Se encarga de rellenar las clases y guardar los datos en BBDD, para el alta de nueva factura
	public List<String> alta(FacturaConcepto datosConcepto, DatosDAO dao) throws ParseException, OegamExcepcion {
		ResultBean resultado = new ResultBean();
//		String param1 = "";
//		String param2 = "";
		resultado.setError(false);
		List<String> mensajeSQL = new ArrayList<>();

		try {
			/**
			 * DRC@01-08-2012 Se encarga de guardar los datos en las entidades pertienntes:
			 * param datosCliente: Recoge todos los valores introducidos por el usuario en la web.
			 * param Boolean: Determina si guarda informacion perteneciente. 
			 * param Estado Facturacion: Determina el estado de la factura.
			 */
			List<FacturaColegiadoConcepto> facturaColegiadoConcepto = rellenarConcepto(datosConcepto);
			dao = new DatosDAO();
//			if (datosConcepto.getConceptoIdBorrar() != null && datosConcepto.getConceptoIdBorrar().length() > 0) {
//				//datosCliente.getConceptoIdBorrar().substring(0, datosCliente.getConceptoIdBorrar().length() - 2),
//				param1 = datosConcepto.getConceptoColBorrar().substring(0, datosConcepto.getConceptoColBorrar().length() - 2);
//				param2 = datosConcepto.getConceptoIdBorrar().substring(0, datosConcepto.getConceptoIdBorrar().length() - 2);
//				dao.borrarTransaccionesDosCampos(ConstantesFacturacion.BBDD_FACTURA_COLEGIADO_CONCEPTO, ConstantesFacturacion.BBDD_ID_FACTURA_COL_CONCEPTO, param1, ConstantesFacturacion.BBDD_ID2_FACTURA_COL_CONCEPTO, param2);
//			}

			resultado = dao.addColegiadoConcepto(facturaColegiadoConcepto);

			if (resultado.getError()) {
				if (resultado.getMensaje() != null) {
					mensajeSQL.add(mensaje(datosConcepto, Boolean.FALSE));
					mensajeSQL.add(resultado.getMensaje());
				} else {
					mensajeSQL.add(ConstantesFacturacion.MENSAJE_COLEGIADO_CONCEPTO_ERROR_UNO);
				}
			} else {
				mensajeSQL.add(ConstantesFacturacion.MENSAJE_COLEGIADO_CONCEPTO_OK_UNO);
			}
		} catch (Exception e) {
			log.error("Al Trata el metodo alta: " + e.getCause().toString());
			resultado.setError(true);
			mensajeSQL.add(ConstantesFacturacion.MENSAJE_COLEGIADO_CONCEPTO_OK_UNO);
		}
		return mensajeSQL;
	}

	public void eliminarConceptosFacturas( DatosDAO dao, List<String> listaIdConceptos, String numColegiado){
//		String camposIn=" ";
//		Boolean firstTime=true;
//		for (String idConcepto : listaIdConceptos){
//			if (firstTime){
//				camposIn = camposIn + " " + idConcepto;
//			} else {
//				camposIn = camposIn + ", ";
//				camposIn = camposIn + " " + idConcepto;
//			}
//			firstTime=false;
//		}
		try {
			dao.borrarTransaccionesDosCampos(numColegiado, listaIdConceptos);
		} catch (Exception e) {
			log.error("Al borrar los conceptos del colegiado: " + numColegiado + "E: " + e.getCause().toString());
		}
	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaConcepto con los datos del formulario
	public List<FacturaColegiadoConcepto> rellenarConcepto (FacturaConcepto datosConcepto) throws ParseException {
		return null;
//		List<String> mensajeSQL = new ArrayList<String>();
//		// Cargar Datos Factura_Concepto y Factura_ConceptoPK
//		List<FacturaColegiadoConcepto> lFacturaColegiadoConcepto = new ArrayList<FacturaColegiadoConcepto>();
//		int numConcepto = 1;
//
//		try {
//			if (numConcepto > 0) {
//				FacturaColegiadoConcepto facturaColegiadoConcepto = new FacturaColegiadoConcepto();
//				FacturaColegiadoConceptoPK facturaColegiadoConceptoPK = new FacturaColegiadoConceptoPK();
//
//				FacturaPK facturapk = new FacturaPK();
////				String todoConcepto = datosConcepto.getConceptoTodoArray();
////				String separador = ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO;
////				if (todoConcepto.trim().startsWith(ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO))
////					todoConcepto = todoConcepto.substring(ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO.length(), todoConcepto.trim().length());
////
////				if (todoConcepto.trim().endsWith(ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO))
////					todoConcepto = todoConcepto.substring(0, todoConcepto.trim().length()-1);
//
//				if (!todoConcepto.equalsIgnoreCase(ConstantesFacturacion.CONCEPTO_DEFECTO)) {
//					StringTokenizer tokens = new StringTokenizer(todoConcepto, separador);
//					while(tokens.hasMoreTokens()){
//						facturaColegiadoConcepto   = new FacturaColegiadoConcepto();
//						facturaColegiadoConceptoPK = new FacturaColegiadoConceptoPK();
//						facturaColegiadoConceptoPK.setNumColegiado(tokens.nextToken());
//						facturaColegiadoConceptoPK.setIdColegiadoConcepto(Long.valueOf(tokens.nextToken()));
//						facturaColegiadoConcepto.setId(facturaColegiadoConceptoPK);
//						facturaColegiadoConcepto.setNombreColegiadoConcepto(tokens.nextToken());
//
//						if (!facturaColegiadoConcepto.getNombreColegiadoConcepto().equalsIgnoreCase(ConstantesFacturacion.DESCRIPCION_DEFECTO))
//							lFacturaColegiadoConcepto.add(facturaColegiadoConcepto);
//					}
//				}
//			}
//			return lFacturaColegiadoConcepto;
//		} catch (Exception e) {
//			mensajeSQL.add(e.getCause().toString());
//			log.error("Error al recuperar los datos introducidor para los Conceptos del Colegiado para la Facturacion: " + e);
//			return lFacturaColegiadoConcepto;
//		}
	}

	//DRC@19-07-2012 Se utiliza para recoger todos los suplidos que han sido seleccionados por la consulta a BBDD.
	public String convertirConceptoListToString(List<FacturaColegiadoConcepto> lFacturaConcepto) {
		return null;
//		String separador = ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO;
//		String todoConcepto = separador;
//
//		FacturaColegiadoConcepto facturaConcepto = new FacturaColegiadoConcepto();
//
//		int tam = 0;
//		if (lFacturaConcepto != null && lFacturaConcepto.size() > 0)
//			tam = lFacturaConcepto.size();
//		for (int i=0; i<tam; i++) {
//			facturaConcepto = lFacturaConcepto.get(i);
//			todoConcepto += facturaConcepto.getId().getNumColegiado()		 + separador +
//							facturaConcepto.getId().getIdColegiadoConcepto() + separador +
//							facturaConcepto.getNombreColegiadoConcepto()	 + separador;
//		}
//
//		if (lFacturaConcepto.size() == 0)
//			todoConcepto = null;
//
//		if (lFacturaConcepto.size() > 0)
//			todoConcepto = todoConcepto.substring(1, todoConcepto.length()-1);
//
//		return todoConcepto;
	}

	// DRC@10-07-2012 Se encarga de formar el mensaje que se mostrara en pantalla
	public String mensaje (FacturaConcepto datosConcepto, Boolean result) throws OegamExcepcion {
		String identificador = "";
		identificador = String.valueOf(datosConcepto.getIdConcepto());
		return identificador;
	}
}