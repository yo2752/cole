package facturacion.gestion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.SexoPersona;
import facturacion.comun.ConstantesFacturacion;
import facturacion.comun.DatosClienteBean;
import facturacion.dao.DatosDAO;
import facturacion.utiles.enumerados.EstadoFacturacion;
import hibernate.entities.facturacion.Factura;
import hibernate.entities.facturacion.FacturaConcepto;
import hibernate.entities.facturacion.FacturaGasto;
import hibernate.entities.facturacion.FacturaHonorario;
import hibernate.entities.facturacion.FacturaSuplido;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloNuevaFactura {

	// log
	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloNuevaFactura.class);
	List<String> mensajeSQL = new ArrayList<>();

	public ResultBean anularFactura(Factura linea,
			DatosClienteBean datosCliente, DatosDAO dao) throws ParseException,
			OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(false);
		mensajeSQL = new ArrayList<String>();
		String textoMensaje = "";

		try {
			resultado = dao.anularFactura(linea.getId(),
					EstadoFacturacion.EstadoFacturaAnulada.getValorEnum());

			if (resultado.getError()) {
				if (resultado.getMensaje() != null) {
					// mensajeError = mensaje(datosCliente, Boolean.FALSE) +
					// " <br> " + resultado.getMensaje());
					mensajeSQL.add(mensaje(datosCliente, Boolean.FALSE));
					mensajeSQL.add(resultado.getMensaje());
				} else {
					textoMensaje = ConstantesFacturacion.PDF_RECTIFICATIVA_MENSAJE_CERO	+ " " + datosCliente.getFactura().getId().getNumFactura();
					mensajeSQL.add(textoMensaje);
				}
				resultado.setListaMensajes(mensajeSQL);
			} else {
				textoMensaje = ConstantesFacturacion.MENSAJE_ERROR_CREAR_RECTIFICATIVA_UNO
						+ " "	+ datosCliente.getFactura().getId().getNumFactura()
						+ " "	+ ConstantesFacturacion.MENSAJE_ERROR_CREAR_RECTIFICATIVA_DOS;
				mensajeSQL.add(textoMensaje);
				resultado.setListaMensajes(mensajeSQL);
			}
		} catch (Exception e) {
			log.error("Al Trata el DAO: " + e);
			resultado.setError(true);
			resultado.setMensaje(mensaje(datosCliente, Boolean.FALSE));
		}
		return resultado;
	}

	// DRC@14-07-2012 Se encarga de rellenar las clases y guardar los datos en
	// BBDD, para la modificacion de factura
	public ResultBean modisficar(DatosClienteBean datosCliente, DatosDAO dao)
			throws ParseException, OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(false);

		try {
			// Si el NIF no existe, guarda primero el cliente y luego el resto
			// de datos de la factura Actual
			//FacturaCliente facturaCliente = rellenarDatosFacturacion(datosCliente, Boolean.TRUE);
			//dao = new DatosDAO();
//			if (datosCliente.getConceptoIdBorrar() != null && datosCliente.getConceptoIdBorrar().length() > 0)
//
//				dao.borrarTransacciones(ConstantesFacturacion.BBDD_FACTURA_CONCEPTO,ConstantesFacturacion.BBDD_ID_FACTURA_CONCEPTO,
//										datosCliente.getConceptoIdBorrar().substring(0,	datosCliente.getConceptoIdBorrar().length() - 2));
//			if (datosCliente.getHonorarioIdBorrar() != null
//					&& datosCliente.getHonorarioIdBorrar().length() > 0)
//
//				dao.borrarTransacciones(ConstantesFacturacion.BBDD_FACTURA_HONORARIO,ConstantesFacturacion.BBDD_ID_FACTURA_HONORARIO,
//										datosCliente.getHonorarioIdBorrar().substring(0,datosCliente.getHonorarioIdBorrar().length() - 2));
//			if (datosCliente.getSuplidoIdBorrar() != null && datosCliente.getSuplidoIdBorrar().length() > 0)
//
//				dao.borrarTransacciones(ConstantesFacturacion.BBDD_FACTURA_SUPLIDO,ConstantesFacturacion.BBDD_ID_FACTURA_SUPLIDO,
//										datosCliente.getSuplidoIdBorrar().substring(0,datosCliente.getSuplidoIdBorrar().length() - 2));
//			if (datosCliente.getGastoIdBorrar() != null && datosCliente.getGastoIdBorrar().length() > 0)
//
//				dao.borrarTransacciones(ConstantesFacturacion.BBDD_FACTURA_GASTO,ConstantesFacturacion.BBDD_ID_FACTURA_GASTO,
//										datosCliente.getGastoIdBorrar().substring(0,datosCliente.getGastoIdBorrar().length() - 2));
//
//			if (datosCliente.getOtroIdBorrar() != null && datosCliente.getOtroIdBorrar().length() > 0)
//
//				dao.borrarTransacciones(ConstantesFacturacion.BBDD_FACTURA_OTRO,ConstantesFacturacion.BBDD_ID_FACTURA_OTRO,
//										datosCliente.getOtroIdBorrar().substring(0,datosCliente.getOtroIdBorrar().length() - 2));
//
//			if (datosCliente.getFondosIdBorrar() != null && datosCliente.getFondosIdBorrar().length() > 0)
//
//				dao.borrarTransacciones(ConstantesFacturacion.BBDD_FACTURA_PROV_FONDO,ConstantesFacturacion.BBDD_ID_FACTURA_PROV_FONDO,
//										datosCliente.getFondosIdBorrar().substring(0,datosCliente.getFondosIdBorrar().length() - 2));

			resultado.setMensaje(mensaje(datosCliente, Boolean.TRUE));
		} catch (Exception e) {
			log.error("Al Trata el DAO: " + e);
			mensajeSQL.add(e.getCause().toString());
			mensajeSQL.add(mensaje(datosCliente, Boolean.FALSE));
			resultado.setError(true);
			resultado.setListaMensajes(mensajeSQL);
		}
		return resultado;
	}

	// DRC@14-07-2012 Se encarga de rellenar las clases y guardar los datos en
	// BBDD, para la modificacion de factura
	public ResultBean deleteSuplido(DatosClienteBean datosCliente,
			DatosDAO dao, long idConcepto) throws ParseException,
			OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(false);

		try {
			// Si el NIF no existe, guarda primero el cliente y luego el resto
			// de datos de la factura Actual
			dao.deleteSuplido(idConcepto);
			resultado.setMensaje(mensaje(datosCliente, Boolean.TRUE));
		} catch (Exception e) {
			log.error("Al Trata el DAO: " + e);
			resultado.setError(true);
			resultado.setMensaje(mensaje(datosCliente, Boolean.FALSE));
		}
		return resultado;
	}

	// DRC@10-07-2012 Se encarga de comprobar que los campos obligatorios se han
	// rellenado y en caso contrario mostrar el mensaje de error,
	// configuracion en un properties
	public ResultBean validarDatosAlta(DatosClienteBean datosCliente)
			throws OegamExcepcion {
		ResultBean resultado = new ResultBean();
		resultado.setError(false);

		if (datosCliente != null && (datosCliente.getFactura().getId().getNumFactura() == null
				|| datosCliente.getFactura().getId().getNumFactura().trim().equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_NUMFACTURA);
			resultado.setError(true);
		}

		if (datosCliente != null && datosCliente.getFactura().getFechaFactura() == null) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_FECHAFACTURA);
			resultado.setError(true);
		}

		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& (datosCliente.getFactura().getPersona().getId().getNif() == null
					|| datosCliente.getFactura().getPersona().getId().getNif().trim().equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_NIF);
			resultado.setError(true);
		}

		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& (datosCliente.getFactura().getPersona().getApellido1RazonSocial() == null
					|| datosCliente.getFactura().getPersona().getApellido1RazonSocial().trim().equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_PRIMERAPELLIDO);
			resultado.setError(true);
		}

		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& (datosCliente.getFactura().getPersona().getSexo() != null && !datosCliente
						.getFactura().getPersona().getSexo()
						.equalsIgnoreCase(SexoPersona.Juridica.getValorEnum()))) {
			if (datosCliente != null
					&& datosCliente.getFactura() != null
					&& datosCliente.getFactura().getPersona() != null
					&& (datosCliente.getFactura().getPersona().getNombre() == null 
							|| datosCliente.getFactura().getPersona().getNombre().trim().equals(""))) {
				resultado.setMensaje(ConstantesFacturacion.ERROR_NOMBRE);
				resultado.setError(true);
			}
		}

		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& datosCliente.getFactura().getPersona().getPersonaDireccions() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual().getMunicipio() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual().getMunicipio().getProvincia() != null
				&& (datosCliente.getFactura().getPersona().getDireccionActual().getMunicipio().getProvincia().getIdProvincia() == null
						|| datosCliente.getFactura().getPersona().getDireccionActual().getMunicipio().getProvincia()
								.getIdProvincia().trim().equalsIgnoreCase("-1") || datosCliente
						.getFactura().getPersona().getDireccionActual()
						.getMunicipio().getProvincia().getIdProvincia().trim()
						.equalsIgnoreCase(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_PROVINCIA);
			resultado.setError(true);
		}

		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual() != null
				&& (datosCliente.getFactura().getPersona().getDireccionActual().getMunicipio().getId().getIdMunicipio() == null 
				||	datosCliente.getFactura().getPersona().getDireccionActual().getMunicipio().getId().getIdMunicipio().trim().equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_MUNICIPIO);
			resultado.setError(true);
		}
		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual() != null
				&& (datosCliente.getFactura().getPersona().getDireccionActual().getCodPostal() == null 
						|| datosCliente.getFactura().getPersona().getDireccionActual().getCodPostal().trim().equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_CODPOSTAL);
			resultado.setError(true);
		}
		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual() != null
				&& (datosCliente.getFactura().getPersona().getDireccionActual().getIdTipoVia() == null || datosCliente
						.getFactura().getPersona().getDireccionActual()
						.getIdTipoVia().trim().equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_TIPOVIA);
			resultado.setError(true);
		}
		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual() != null
				&& (datosCliente.getFactura().getPersona().getDireccionActual()
						.getNombreVia() == null || datosCliente.getFactura()
						.getPersona().getDireccionActual().getNombreVia().trim()
						.equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_NOMBREVIA);
			resultado.setError(true);
		}
		if (datosCliente != null
				&& datosCliente.getFactura() != null
				&& datosCliente.getFactura().getPersona() != null
				&& datosCliente.getFactura().getPersona().getDireccionActual()!= null
				&& (datosCliente.getFactura().getPersona().getDireccionActual()
						.getNumero() == null || datosCliente.getFactura()
						.getPersona().getDireccionActual().getNumero().trim()
						.equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_NUMEROVIA);
			resultado.setError(true);
		}

		// EMISOR
		if (datosCliente != null
				&& (datosCliente.getFactura().getEmisor() == null ||
						datosCliente.getFactura().getEmisor().equals(""))) {
			resultado.setMensaje(ConstantesFacturacion.ERROR_EMISOR);
			resultado.setError(true);
		}

		return resultado;
	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase Factura con los datos
	// del formulario
//	public Factura rellenarCabeceraFacturacion(DatosClienteBean datosCliente)
//			throws ParseException {
//		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
//
//		// Cargar Datos Factura y FacturaPK
//		FacturaPK facturapk = new FacturaPK();
//		facturapk.setNumColegiado(datosCliente.getFactura().getId().getNumColegiado());
//		facturapk.setNumFactura(datosCliente.getFactura().getId().getNumFactura());
//
//		//FacturaCliente facturaCliente = new FacturaCliente();
//		//facturaCliente = rellenarDatosFacturacion(datosCliente, Boolean.TRUE);
//
//		Factura factura = new Factura();
//		factura.setNumSerie(datosCliente.getFactura().getNumSerie());
//		factura.setNumCodigo(datosCliente.getFactura().getNumCodigo());
//		factura.setFechaFactura(datosCliente.getFactura().getFechaFactura());
//		//factura.setNumExpediente(datosCliente.getNumFactura().getNumExpediente());
//		factura.setNumeracion(datosCliente.getFactura().getNumeracion());
//		factura.setVisible(new BigDecimal(1));
//
//		List<FacturaConcepto> lFacturaConcepto = new ArrayList<FacturaConcepto>();
//		//lFacturaConcepto = rellenarConceptoFacturacion(datosCliente, factura);
//
//		factura.setId(facturapk);
//		//factura.setFacturaCliente(facturaCliente);
//		factura.setFacturaConceptos(lFacturaConcepto);
//
//		return factura;
//	}

	// DRC@18-07-2012 Devuelve los datos de la Factura, Factura Concepto y
	// Factura Suplido en una Lista
//	public List<Factura> rellenarListaCabeceraFacturacion(
//			DatosClienteBean datosCliente, Boolean check) throws ParseException {
//		List<Factura> lFactura = new ArrayList<Factura>();
//
//		FacturaPK facturapk = new FacturaPK();
//		Factura factura = new Factura();
//		//FacturaCliente facturaCliente = new FacturaCliente();
//
//		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
//
//		// Cargar Datos Factura y FacturaPK
//		facturapk.setNumColegiado(convertirNulltoString(datosCliente.getFactura().getId().getNumColegiado(), 6));
//		facturapk.setNumFactura(convertirNulltoString(datosCliente.getFactura().getId().getNumFactura(), 20));
//
//		// DRC@31-08-2012 Recupera los datos del Cliente introducidos por el
//		// usuario en una lista, para relacionarlo con la tabla factura
//		//facturaCliente = rellenarDatosFacturacion(datosCliente, Boolean.FALSE);
//		//factura.setFacturaCliente(facturaCliente);
//		factura.setNumSerie(convertirNulltoString(datosCliente.getFactura().getNumSerie(),5));
//		factura.setNumCodigo(convertirNulltoString(datosCliente.getFactura().getNumCodigo(),5));
//		if (datosCliente.getFactura().getFechaFactura() != null)
//			factura.setFechaFactura(datosCliente.getFactura().getFechaFactura());
//
//		if (datosCliente.getFactura().getFechaAlta() != null)
//			factura.setFechaAlta(datosCliente.getFactura().getFechaAlta());
//
//		factura.setNumExpediente(convertirNulltoString(datosCliente.getFactura().getNumExpediente(), 20));
//		factura.setVisible(new BigDecimal(1));
//		factura.setCheckPdf(convertirNulltoString(datosCliente.getFactura().getCheckPdf(), 1));
//
//		factura.setNifEmisor(convertirNulltoString(datosCliente.getFactura().getNifEmisor(), 9));
//		factura.setNumeracion(datosCliente.getFactura().getNumeracion());
//		factura.setImporteTotal(convertirNulltoString(datosCliente.getFactura().getImporteTotal(), 20));
//
//		factura.setId(facturapk);
//
//		if (datosCliente.getFacturaAnulada() != null)
//			factura.setFacAnulada(convertirNulltoString(datosCliente
//					.getFacturaAnulada(), 30));
//
//		if (check) {
//			// DRC@31-08-2012 Recupera los datos de los conceptos introducidos
//			// por el usuario en una lista, para relacionarlo con la tabla
//			// factura
//			List<FacturaConcepto> lFacturaConcepto = new ArrayList<>();
//			lFacturaConcepto = rellenarConceptoFacturacion(datosCliente,factura);
//			factura.setFacturaConceptos(lFacturaConcepto);
//
//			// DRC@31-08-2012 Recupera los datos de los honorarios introducidos
//			// por el usuario en una lista, para relacionarlo con la tabla
//			// factura
//			List<FacturaHonorario> lFacturaHonorario = new ArrayList<>();
//			lFacturaHonorario = rellenarHonorarioFacturacion(datosCliente,
//					factura);
//			factura.setFacturaHonorarios(lFacturaHonorario);
//
//			// DRC@31-08-2012 Recupera los datos de los gasto introducidos por
//			// el usuario en una lista, para relacionarlo con la tabla factura
//			List<FacturaGasto> lFacturaGasto = new ArrayList<FacturaGasto>();
//			lFacturaGasto = rellenarGastoFacturacion(datosCliente, factura);
//			factura.setFacturaGastos(lFacturaGasto);
//
//			// DRC@31-08-2012 Recupera los datos de los suplidos introducidos
//			// por el usuario en una lista, para relacionarlo con la tabla
//			// factura
//			List<FacturaSuplido> lFacturaSuplido = new ArrayList<>();
//			lFacturaSuplido = rellenarSuplidoFacturacion(datosCliente, factura);
//			factura.setFacturaSuplidos(lFacturaSuplido);
//
//			// DRC@31-08-2012 Recupera los datos del Otro introducidos por el
//			// usuario, para relacionarlo con la tabla factura
//			if (!"".equals(datosCliente.getOtro())){
//				List<FacturaOtro> lFacturaOtro = new ArrayList<FacturaOtro>();
//				lFacturaOtro = rellenarOtroFacturacion(datosCliente, factura);
//				factura.setFacturaOtro(lFacturaOtro);
//			}
//
//			// DRC@31-08-2012 Recupera los datos de la Provision de Fondos
//			// introducidos por el usuario, para relacionarlo con la tabla
//			// factura
//
//			if (!"".equals(datosCliente.getFondos())){
//				List<FacturaProvFondo> lFacturaProvFondo = new ArrayList<>();
//				lFacturaProvFondo = rellenarProvisionFondoFacturacion(datosCliente, factura);
//				factura.setFacturaProvFondo(lFacturaProvFondo);	
//			}
//		}
//		lFactura.add(factura);
//		return lFactura;
//	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaCliente con los
	// datos del formulario
//	public FacturaCliente rellenarDatosFacturacion(
//			DatosClienteBean datosCliente, Boolean check) throws ParseException {
//
//		FacturaCliente facturaCliente = new FacturaCliente();
//		facturaCliente.setNif(convertirNulltoString(datosCliente.getFactura().getPersona().getNif(), 9));
//		facturaCliente.setSexo(convertirNulltoString(datosCliente.getFactura().getPersona().getSexo(), 1));
//		facturaCliente.setPrimerApellido(convertirNulltoString(datosCliente.getFactura().getPersona().getApellido1RazonSocial(), 100));
//		facturaCliente.setSegundoApellido(convertirNulltoString(datosCliente.getFactura().getPersona().getApellido2(), 100));
//		facturaCliente.setNombreApellido(convertirNulltoString(datosCliente.getFactura().getPersona().getNombre(), 100));
//		if (datosCliente != null
//				&& datosCliente.getFactura() != null
//				&& datosCliente.getFactura().getPersona() != null
//				&& datosCliente.getFactura().getPersona()
//						.getFechaNacimientoBean() != null
//				&& datosCliente.getFactura().getPersona()
//						.getFechaNacimientoBean().getFecha() != null)
//		facturaCliente.setFechaNacimiento(datosCliente.getFactura().getPersona().getFechaNacimientoBean().getFecha());
//		facturaCliente.setTelefono(convertirNulltoString(datosCliente.getFactura().getPersona().getTelefonos(), 9));
//		facturaCliente.setCorreoElectronico(convertirNulltoString(datosCliente.getFactura().getPersona().getCorreoElectronico(), 100));
//		facturaCliente.setProvincia(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia(), 3));
//		facturaCliente.setMunicipio(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getMunicipio().getIdMunicipio(), 3));
//		facturaCliente.setPueblo(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getPueblo(), 100));
//		facturaCliente.setCodPostal(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getCodPostal(), 5));
//		facturaCliente.setTipoVia(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getTipoVia().getIdTipoVia(), 30));
//		facturaCliente.setNombreVia(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getNombreVia(),100));
//
//		if (datosCliente.getFactura().getPersona().getDireccion().getNumero() != null) {
//			facturaCliente.setNumeroVia(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getNumero().toString(), 3));
//		} else {
//			facturaCliente.setNumeroVia("");
//		}
//		facturaCliente.setLetra(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getLetra(), 3));
//		facturaCliente.setEscalera(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getEscalera(), 10));
//		facturaCliente.setPiso(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getPlanta(), 3));
//		facturaCliente.setPuerta(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getPuerta(), 5));
//		facturaCliente.setBloque(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getBloque(), 3));
//		facturaCliente.setKm(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getPuntoKilometrico(), 3));
//		facturaCliente.setHm(convertirNulltoString(datosCliente.getFactura().getPersona().getDireccion().getHm(), 3));
//
//		if (check) {
//			List<Factura> factura = new ArrayList<Factura>();
//			factura = rellenarListaCabeceraFacturacion(datosCliente,
//					Boolean.TRUE);
//			facturaCliente.setFacturas(factura);
//		}
//		return facturaCliente;
//	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaConcepto con los
	// datos del formulario
//	public List<FacturaConcepto> rellenarConceptoFacturacion(
//			DatosClienteBean datosCliente, Factura factura)
//			throws ParseException {
//		mensajeSQL = new ArrayList<String>();
//		// Cargar Datos Factura_Concepto y Factura_ConceptoPK
//		List<FacturaConcepto> lFacturaConcepto = new ArrayList<>();
//		int numConcepto = 0;
//
//		try {
//			if (datosCliente != null && datosCliente.getConceptoTodo() != null
//					&& datosCliente.getConceptoTodo().length() > 0)
//				numConcepto = datosCliente.getConceptoTodo().length();
//
//			if (numConcepto > 0) {
//				FacturaConcepto facturaConcepto = null;
//				String todoConceptoArray = datosCliente.getConceptoTodo();
//				String separador = ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO;
//				if (todoConceptoArray.trim().startsWith(ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO))
//					todoConceptoArray = todoConceptoArray.substring(ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO.length(), todoConceptoArray.trim().length());
//
//				if (todoConceptoArray.trim().endsWith(
//						ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO))
//					todoConceptoArray = todoConceptoArray.substring(0,todoConceptoArray.trim().length() - 1);
//
//				if (!todoConceptoArray.equalsIgnoreCase(ConstantesFacturacion.CONCEPTO_DEFECTO)) {
//					StringTokenizer tokens = new StringTokenizer(todoConceptoArray, separador);
//					while (tokens.hasMoreTokens()) {
//						facturaConcepto = new FacturaConcepto();
//						facturaConcepto.setIdConcepto(Long.valueOf(tokens.nextToken()));
//						facturaConcepto.setNumColegiado(datosCliente.getNumColegiado());
//						facturaConcepto.setNumFactura(datosCliente.getNumFactura());
//						facturaConcepto.setConcepto(tokens.nextToken());
//						facturaConcepto.setNumExpediente(tokens.nextToken());
// 						facturaConcepto.setFactura(factura);
//						if (!facturaConcepto.getConcepto().equalsIgnoreCase(ConstantesFacturacion.DESCRIPCION_DEFECTO))
//							lFacturaConcepto.add(facturaConcepto);
//					}
//				}
//			}
//			return lFacturaConcepto;
//		} catch (Exception e) {
//			mensajeSQL.add(e.getCause().toString());
//			log.error("Error al recuperar los datos introducidor para los Conceptos de Facturacion: "+ e);
//			return lFacturaConcepto;
//		}
//	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaSuplido con los
	// datos del formulario
//	public List<FacturaSuplido> rellenarSuplidoFacturacion(
//			DatosClienteBean datosCliente, Factura factura)
//			throws ParseException {
//		mensajeSQL = new ArrayList<String>();
//		List<FacturaSuplido> lFacturaSuplido = new ArrayList<>();
//		int numSuplido = 0;
//		try {
//			if (datosCliente != null && datosCliente.getTodoSuplidos() != null
//					&& datosCliente.getTodoSuplidos().length() > 0)
//				numSuplido = datosCliente.getTodoSuplidos().length();
//
//			if (numSuplido > 0) {
//				FacturaSuplido facturaSuplido = null;
//				String todoSuplidosArray = datosCliente.getTodoSuplidos();
//				String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;
//				if (todoSuplidosArray.trim().startsWith("-"))
//					todoSuplidosArray = todoSuplidosArray.substring(1,todoSuplidosArray.trim().length());
//
//				if (todoSuplidosArray.trim().endsWith(","))
//					todoSuplidosArray = todoSuplidosArray.substring(0,todoSuplidosArray.trim().length() - 1);
//
//				if (!todoSuplidosArray.equalsIgnoreCase(ConstantesFacturacion.SUPLIDO_DEFECTO)) {
//					StringTokenizer tokens = new StringTokenizer(todoSuplidosArray, separador);
//					while (tokens.hasMoreTokens()) {
//						facturaSuplido = new FacturaSuplido();
//						facturaSuplido.setIdSuplido(Long.valueOf(tokens.nextToken()));
//						facturaSuplido.setSuplidoDescripcion(tokens.nextToken());
//						facturaSuplido.setSuplido(tokens.nextToken());
//						facturaSuplido.setSuplidoCheckDescuento(new BigDecimal(convertirChecktoString(tokens.nextToken())));
//						facturaSuplido.setSuplidoDescuento(tokens.nextToken());
//						facturaSuplido.setSuplidoTotal(tokens.nextToken());
//						facturaSuplido.setFactura(factura);
//						if (!facturaSuplido.getSuplido().equalsIgnoreCase("0"))
//							lFacturaSuplido.add(facturaSuplido);
//					}
//				}
//			}
//			return lFacturaSuplido;
//		} catch (Exception e) {
//			mensajeSQL.add(e.getCause().toString());
//			log.error("Error al recuperar los datos introducidor para los Suplidos de Facturacion: " + e);
//		}
//		return lFacturaSuplido;
//	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaOtro con los
	// datos del formulario
//	public List<FacturaOtro> rellenarOtroFacturacion(DatosClienteBean datosCliente,
//			Factura factura) throws ParseException {
//		mensajeSQL = new ArrayList<String>();
//		List<FacturaOtro> lFacturaOtro = new ArrayList<>();
//		try {
//			FacturaOtro facturaOtro = new FacturaOtro();
//			facturaOtro.setIdOtro(Long.valueOf(convertirNulltoString(datosCliente.getOtroId(), 9)));
//			facturaOtro.setOtro(convertirNulltoString(datosCliente.getOtro(),10));
//			facturaOtro.setOtroDescripcion(convertirNulltoString(datosCliente.getOtroDescripcion(), 255));
//			facturaOtro.setOtroCheckIva(new BigDecimal(convertirChecktoString(datosCliente.getOtroCheckIva())));
//			facturaOtro.setOtroIva(convertirNulltoString(datosCliente.getOtroIva(), 2));
//			facturaOtro.setOtroTotalIva(convertirNulltoString(datosCliente.getOtrosTotalIva(), 10));
//			facturaOtro.setOtroCheckDescuento(new BigDecimal(convertirChecktoString(datosCliente.getOtroCheckDescuento())));
//			facturaOtro.setOtroDescuento(convertirNulltoString(datosCliente.getOtroDescuento(), 2));
////			if ("".equals(facturaOtro.getOtroDescuento())){
////				facturaOtro.setOtroDescuento("0");
////			}
//			facturaOtro.setOtroTotal(convertirNulltoString(datosCliente.getOtrosTotal(), 20));
//			facturaOtro.setFactura(factura);
//			lFacturaOtro.add(facturaOtro);
//			return lFacturaOtro;
//		} catch (Exception e) {
//			mensajeSQL.add(e.getCause().toString());
//			log.error("Error al recuperar los datos introducidor para los Otros de Facturacion: "
//							+ e);
//		}
//		return lFacturaOtro;
//	}
//
//	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaProvisionFondo
//	// con los datos del formulario
//	public List<FacturaProvFondo> rellenarProvisionFondoFacturacion(
//			DatosClienteBean datosCliente, Factura factura)
//			throws ParseException {
//		mensajeSQL = new ArrayList<String>();
//		List<FacturaProvFondo> lFacturaProvFondo = new ArrayList<FacturaProvFondo>();
//		try {
//			FacturaProvFondo facturaProvFondo = new FacturaProvFondo();
//			facturaProvFondo.setIdFondo(Long.valueOf(convertirNulltoString(datosCliente.getFondosId(), 9)));
//			facturaProvFondo.setFondo(convertirNulltoString(datosCliente.getFondos(), 10));
//			facturaProvFondo.setFondoDescripcion(convertirNulltoString(datosCliente.getFondosDescripcion(), 255));
//			facturaProvFondo.setFondoCheckIva(new BigDecimal(convertirChecktoString(datosCliente.getFondosCheckIva())));
//			facturaProvFondo.setFondoIva(convertirNulltoString(datosCliente.getFondosIva(), 3));
//			facturaProvFondo.setFondoTotalIva(convertirNulltoString(datosCliente.getFondosTotalIva(), 10));
//			facturaProvFondo.setFondoTotal(convertirNulltoString(datosCliente.getFondosTotal(), 20));
//			facturaProvFondo.setFactura(factura);
//			lFacturaProvFondo.add(facturaProvFondo);
//			return lFacturaProvFondo;
//		} catch (Exception e) {
//			mensajeSQL.add(e.getCause().toString());
//			log.error("Error al recuperar los datos introducidor para los Provision Fondos de Facturacion: " + e);
//		}
//		return lFacturaProvFondo;
//	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaHonorario con
	// los datos del formulario
//	public List<FacturaHonorario> rellenarHonorarioFacturacion(DatosClienteBean datosCliente, Factura factura)
//			throws ParseException {
//		mensajeSQL = new ArrayList<String>();
//		List<FacturaHonorario> lFacturaHonorario = new ArrayList<FacturaHonorario>();
//		int numHonorario = 0;
//		String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;
//
//		try {
//			if (datosCliente != null && datosCliente.getHonorarioTodo() != null
//					&& datosCliente.getHonorarioTodo().length() > 0)
//				numHonorario = datosCliente.getHonorarioTodo().length();
//
//			if (numHonorario > 0) {
//				FacturaHonorario facturaHonorario = null;
//				String todoHonorariosArray = datosCliente.getHonorarioTodo();
//
//				if (todoHonorariosArray.trim().startsWith("-"))
//					todoHonorariosArray = todoHonorariosArray.substring(1,
//							todoHonorariosArray.trim().length());
//
//				if (todoHonorariosArray.trim().endsWith(","))
//					todoHonorariosArray = todoHonorariosArray.substring(0,
//							todoHonorariosArray.trim().length() - 1);
//
//				// DEFECTO = 0-Introduzca la descripcion del suplido-0-false-0-0
//				if (!todoHonorariosArray
//						.equalsIgnoreCase(ConstantesFacturacion.HONORARIO_DEFECTO)) {
//					StringTokenizer tokens = new StringTokenizer(
//							todoHonorariosArray, separador);
//					while (tokens.hasMoreTokens()) {
//						facturaHonorario = new FacturaHonorario();
//						facturaHonorario.setIdHonorario(Long.valueOf(tokens.nextToken()));
//						facturaHonorario.setHonorarioDescripcion(tokens.nextToken());
//						facturaHonorario.setHonorario(tokens.nextToken());
//						facturaHonorario.setHonorarioCheckIva(new BigDecimal(convertirChecktoString(tokens.nextToken())));
//						facturaHonorario.setHonorarioIva(tokens.nextToken());
//						facturaHonorario.setHonorarioTotalIva(tokens.nextToken());
//						facturaHonorario.setHonorarioCheckDescuento(convertirChecktoString(tokens.nextToken()));
//						facturaHonorario.setHonorarioDescuento(tokens.nextToken());
//						facturaHonorario.setHonorarioCheckIrpf(new BigDecimal(convertirChecktoString(tokens.nextToken())));
//						facturaHonorario.setHonorarioIrpf(tokens.nextToken());
//						facturaHonorario.setHonorarioTotalIrpf(tokens.nextToken());
//						facturaHonorario.setHonorarioTotal(tokens.nextToken());
//
//						facturaHonorario.setFactura(factura);
//
//						if (!facturaHonorario.getHonorario().equalsIgnoreCase("0"))
//							lFacturaHonorario.add(facturaHonorario);
//					}
//				}
//			}
//			return lFacturaHonorario;
//		} catch (Exception e) {
//			mensajeSQL.add(e.getCause().toString());
//			log.error("Error al recuperar los datos introducidor para los Honorarios de Facturacion: "+ e);
//		}
//		return lFacturaHonorario;
//	}

	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaHonorario con
	// los datos del formulario
//	public List<FacturaGasto> rellenarGastoFacturacion(
//			DatosClienteBean datosCliente, Factura factura)
//			throws ParseException {
//		mensajeSQL = new ArrayList<String>();
//		List<FacturaGasto> lFacturaGasto = new ArrayList<FacturaGasto>();
//		int FacturaGasto = 0;
//		String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;
//
//		try {
//			if (datosCliente != null && datosCliente.getGastoTodo() != null
//					&& datosCliente.getGastoTodo().length() > 0)
//				FacturaGasto = datosCliente.getGastoTodo().length();
//
//			if (FacturaGasto > 0) {
//				FacturaGasto facturaGasto = null;
//				String todoGastosArray = datosCliente.getGastoTodo();
//
//				if (todoGastosArray.trim().startsWith("-"))
//					todoGastosArray = todoGastosArray.substring(1,
//							todoGastosArray.trim().length());
//
//				if (todoGastosArray.trim().endsWith(","))
//					todoGastosArray = todoGastosArray.substring(0,
//							todoGastosArray.trim().length() - 1);
//
//				// DEFECTO = 0-Introduzca la descripcion del suplido-0-false-0-0
//				if (!todoGastosArray
//						.equalsIgnoreCase(ConstantesFacturacion.GASTO_DEFECTO)) {
//					StringTokenizer tokens = new StringTokenizer(
//							todoGastosArray, separador);
//					while (tokens.hasMoreTokens()) {
//						facturaGasto = new FacturaGasto();
//						facturaGasto.setIdGasto(Long.valueOf(tokens.nextToken()));
//						facturaGasto.setGastoDescricpcion(tokens.nextToken());
//						facturaGasto.setGasto(tokens.nextToken());
//						facturaGasto.setGastoCheck(new BigDecimal(convertirChecktoString(tokens.nextToken())));
//						facturaGasto.setGastoIva(tokens.nextToken());
//						facturaGasto.setGastoTotalIva(tokens.nextToken());
//						facturaGasto.setGastoTotal(tokens.nextToken());
//						facturaGasto.setFactura(factura);
//
//						if (!facturaGasto.getGasto().equalsIgnoreCase("0"))
//							lFacturaGasto.add(facturaGasto);
//					}
//				}
//			}
//			return lFacturaGasto;
//		} catch (Exception e) {
//			mensajeSQL.add(e.getCause().toString());
//			log
//					.error("Error al recuperar los datos introducidor para los Gastos de Facturacion: "
//							+ e);
//		}
//		return lFacturaGasto;
//	}

	// DRC@10-07-2012 Se encarga de formar el mensaje que se mostrara en
	// pantalla
	public String mensaje(DatosClienteBean datosCliente, Boolean result)
			throws OegamExcepcion {
		String factura = "";
		String mensaje = "";
		if (datosCliente.getFactura().getId().getNumFactura() != null) {
			factura = datosCliente.getFactura().getId().getNumFactura();
		}
		if (result) {
			mensaje = ConstantesFacturacion.MENSAJE_OK_FACTURA
					+ " " + factura + " ";
			mensaje += ConstantesFacturacion.MENSAJE_OK;
		} else {
			mensaje = ConstantesFacturacion.MENSAJE_ERROR_FACTURA
					+ " " + factura + " ";
			mensaje += ConstantesFacturacion.MENSAJE_ERROR;
		}
		return mensaje;
	}

	// DRC@19-07-2012 Se utiliza para recoger todos los suplidos que han sido
	// seleccionados por la consulta a BBDD.
	public String connverSuplidoListToArray(List<FacturaSuplido> lFacturaSuplido) {
		return null;
	}

	// DRC@19-07-2012 Se utiliza para recoger todos los honorarios que han sido
	// seleccionados por la consulta a BBDD.
	public String connverHonorarioListToArray(List<FacturaHonorario> lFacturaHonorario) {
		return null;
	}

	// DRC@19-07-2012 Se utiliza para recoger todos los gastos que han sido
	// seleccionados por la consulta a BBDD.
	public String connverGastoListToArray(List<FacturaGasto> lFacturaGasto) {
		return null;
	}

	// DRC@19-07-2012 Se utiliza para recoger todos los suplidos que han sido
	// seleccionados por la consulta a BBDD.
	public String connverConceptoListToArray(List<FacturaConcepto> lFacturaConcepto) {
		return null;
	}

	// DRC@10-07-2012 Se encarga de convertir los campos null a vacio, al
	// recoger los datos de consulta de factura
	public String convertirNulltoString(String cadena, int tam) {
		try {
			if (cadena != null) {
				return cadena.length() > tam ? cadena.substring(0, tam) : cadena;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	// DRC@10-07-2012 Se encarga de convertir los campos null a vacio, al
	// recoger los datos de consulta de factura
	public Long convertirNulltoLong(Long cadena) {
		try {
			if (cadena != null)
				return cadena;
			else
				return 0L;
		} catch (Exception e) {
			return 0L;
		}
	}

	// DRC@10-07-2012 Se encarga de convertir los campos check (true/false) en
	// (1/0)
	public String convertirChecktoString(String cadena) {
		String value = "1";
		if (cadena == null || (cadena != null
				&& (cadena.equalsIgnoreCase("false") || cadena.equalsIgnoreCase("0") || cadena.indexOf("off") > 0)))
			value = "0";
		return value;
	}
}