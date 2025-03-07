package facturacion.ajax.acciones;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import facturacion.beans.TotalesConceptoBean;
import facturacion.beans.TotalesFacturaBean;
import facturacion.comun.ConstantesFacturacion;
import facturacion.comun.DatosClienteBean;
import facturacion.dao.DatosDAO;
import facturacion.utiles.UtilesVistaFacturacion;
import general.acciones.ActionBase;
import hibernate.entities.facturacion.FacturaColegiadoConcepto;
import hibernate.entities.facturacion.FacturaConcepto;
import hibernate.entities.facturacion.FacturaGasto;
import hibernate.entities.facturacion.FacturaHonorario;
import hibernate.entities.facturacion.FacturaSuplido;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class FacturacionAjaxAction extends ActionBase implements ServletRequestAware, ServletResponseAware {	
	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(FacturacionAjaxAction.class);
	
	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; //para ajax	
	private List<FacturaColegiadoConcepto> lFacturaColegiadoConcepto;
	
	// Sirve para recuperar datos de facturacion
	private DatosClienteBean datosCliente;
	
	private String honorarioIdBorrar;	// honorarioIdBorrar que se va a borrar
	private String suplidoIdBorrar;
	private String gastoIdBorrar;
	private String honorarioTodo;	// honorarioIdBorrar que se va a borrar	
	private String suplidoTodo;
	private String gastoTodo;
	
	private String conceptoIdBorrar;	// honorarioIdBorrar que se va a borrar
	private String conceptoTodo;	// Cadena con toda la informacion de Honorarios
	
	//Datos del concepto actual
	
	private String idConceptoActual;
	private String concepto;
	private String idExpAsociado;
	
	private String password;
	private String numFactura;
	
	//Id del Concepto al que se quiere cambiar
	
	private String conceptoIdCambio;

	@Autowired
	UtilesColegiado utilesColegiado;

	public void recuperarNombreConceptoColegiado () throws Throwable{
		log.debug("recuperarNombreConceptoColegiado");
		
		String numColegiado =utilesColegiado.getNumColegiadoSession(); 
		
		lFacturaColegiadoConcepto = new ArrayList<FacturaColegiadoConcepto>();
		DatosDAO dao = new DatosDAO();
		lFacturaColegiadoConcepto.addAll(dao.lRecuperarConceptoColegiado(numColegiado));
		
		String resultado = transformarEnStringNombreConcepto(lFacturaColegiadoConcepto);
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}		
	private String transformarEnStringNombreConcepto(List<FacturaColegiadoConcepto> lFacturaColegiadoConcepto) {
		String respuesta = "";

		for (FacturaColegiadoConcepto f : lFacturaColegiadoConcepto) {
			respuesta = respuesta + f.getNombreColegiadoConcepto()	+ "||";
		}
		
		if (respuesta.length()>=2) respuesta = respuesta.substring(0, respuesta.length() - 2);
		log.debug("respuesta transformarEnStringNombreVias "+respuesta);
		return respuesta;
	}
	
	public void nuevoConcepto () throws Throwable{
		log.debug("Inicio nuevoConcepto");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		//Actualizo el Concepto de Pantalla como el actual con lo que hay en los 
		actualizoConceptoPantalla();

		//Establezco el Concepto como el actual para recoger luego sus Honorarios
		String idConceptActual = String.valueOf(getIdConceptoActual());
		
		FacturaConcepto fConc = datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptActual));
		
		datosCliente.setFacturaConceptoCargado(fConc);
		
		//relleno sus Honorarios que tenga en Pantalla
		rellenarHonorarioFacturacion();
		
		rellenarSuplidoFacturacion();
		
		rellenarGastoFacturacion();
		
		//Le añado un concepto nuevo
		aniadirNuevoConcepto(getDatosCliente());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(datosCliente.getFactura().getFacturaConceptos().size()-1);
		
		getSession().put("datosCliente", datosCliente);
		
		log.debug("Fin nuevoConcepto");
	}
	
	public void nuevoHonorario () throws Throwable{
		log.debug("Inicio nuevoHonorario");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		// DRC@31-08-2012 Recupera los datos de los honorarios introducidos
		// por el usuario en una lista, para relacionarlo con el concepto actual
		
		rellenarHonorarioFacturacion();

		String resultado = aniadirNuevoHonorario(getDatosCliente());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
		
		getSession().put("datosCliente", datosCliente);
		
		log.debug("Fin nuevoHonorario");
	}
	
	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaHonorario con
	// los datos del formulario
	public void rellenarHonorarioFacturacion(){		
		int numHonorario = 0;
		int vueltaHonorario = 0;
		String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;

		try {
			if (datosCliente != null && getHonorarioTodo() != null
					&& getHonorarioTodo().length() > 0)
				numHonorario = getHonorarioTodo().length();

			if (numHonorario > 0) {				
				String todoHonorariosArray = getHonorarioTodo();

				if (todoHonorariosArray.trim().startsWith("\\"))
					todoHonorariosArray = todoHonorariosArray.substring(1,todoHonorariosArray.trim().length());

				if (todoHonorariosArray.trim().endsWith(","))
					todoHonorariosArray = todoHonorariosArray.substring(0,todoHonorariosArray.trim().length() - 1);

				// DEFECTO = 0-Introduzca la descripcion del suplido-0-false-0-0
				//if (!todoHonorariosArray.equalsIgnoreCase(ConstantesFacturacion.HONORARIO_DEFECTO)) {
					StringTokenizer tokens = new StringTokenizer(
							todoHonorariosArray, separador);
					while (tokens.hasMoreTokens()) {						
						tokens.nextToken();
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioDescripcion(tokens.nextToken());
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorario(Float.valueOf(tokens.nextToken()));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioCheckIva(new BigDecimal(convertirChecktoString(tokens.nextToken())));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioIva(Integer.valueOf(tokens.nextToken()));
						if ("0".equals(String.valueOf(datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).getHonorarioCheckIva()))){
							datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioIva(0);
						}
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioTotalIva(Float.parseFloat(tokens.nextToken()));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioCheckDescuento(convertirChecktoString(tokens.nextToken()));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioDescuento(Float.parseFloat(tokens.nextToken()));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioCheckIrpf(new BigDecimal(convertirChecktoString(tokens.nextToken())));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioIrpf(Integer.valueOf((tokens.nextToken())));
						if ("0".equals(String.valueOf(datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).getHonorarioCheckIrpf()))){
							datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioIrpf(0);
						}
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioTotalIrpf(Float.parseFloat(tokens.nextToken()));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setHonorarioTotal(Float.parseFloat(tokens.nextToken()));
						datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(vueltaHonorario).setFacturaConcepto(datosCliente.getFacturaConceptoCargado());
						vueltaHonorario = vueltaHonorario+1;

					}
//					getSession().put("datosCliente", datosCliente);
			}
			
		} catch (Exception e) {
			log.error("Error al recuperar los datos introducidor para los Honorarios de Facturacion: "+ e);
		}
		
	}
	
	public String convertirChecktoString(String cadena) {
		String value = "1";
		if (cadena == null
				|| (cadena != null && (cadena.equalsIgnoreCase("false")
						|| cadena.equalsIgnoreCase("0") || cadena
						.indexOf("off") > 0)))
			value = "0";
		return value;
	}
	
	public void actualizarHonorarios() throws IOException{
		
		log.debug("INICIO actualizarHonorarios");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		//Con esta funcion seteo como Concepto Actual el que el Usuario tiene activo
		actualizoConceptoPantalla();
		
		//Id del Concepto que el usuario ha seleccionado para cambiar
		//String idConcept = String.valueOf(getConceptoIdCambio());
		String idConceptActual = String.valueOf(getIdConceptoActual());
		
		FacturaConcepto fConc = datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptActual));
		
		datosCliente.setFacturaConceptoCargado(fConc);
		
		//relleno sus Honorarios
		rellenarHonorarioFacturacion();

		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print("1");
		
		getSession().put("datosCliente", datosCliente);

		log.debug("FIN actualizarHonorarios");

		
	}
	
public void actualizarSuplidos() throws IOException{
		
		log.debug("INICIO actualizarSuplidos");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		//Con esta funcion seteo como Concepto Actual el que el Usuario tiene activo
		//actualizoConceptoPantalla();
		
		//Id del Concepto que el usuario ha seleccionado para cambiar
		//String idConcept = String.valueOf(getConceptoIdCambio());
		//String idConceptActual = String.valueOf(getIdConceptoActual());
		
		//FacturaConcepto fConc = datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptActual));
		
		//datosCliente.setFacturaConceptoCargado(fConc);
		
		//relleno sus Suplidos
		rellenarSuplidoFacturacion();

		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print("1");
		
		getSession().put("datosCliente", datosCliente);

		log.debug("FIN actualizarSuplidos");

		
	}

public void actualizarGastos() throws IOException{
	
	log.debug("INICIO actualizarGastos");
	
	setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
	
	//Con esta funcion seteo como Concepto Actual el que el Usuario tiene activo
	//actualizoConceptoPantalla();
	
	//Id del Concepto que el usuario ha seleccionado para cambiar
	//String idConcept = String.valueOf(getConceptoIdCambio());
	//String idConceptActual = String.valueOf(getIdConceptoActual());
	
	//FacturaConcepto fConc = datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptActual));
	
	//datosCliente.setFacturaConceptoCargado(fConc);
	
	//relleno sus Gastos
	rellenarGastoFacturacion();

	
	servletResponse.setContentType("text/html; charset=iso-8859-1");
	PrintWriter out = servletResponse.getWriter();
	out.print("1");
	
	getSession().put("datosCliente", datosCliente);

	log.debug("FIN actualizarGastos");

	
}
	
	public void recuperarHonorarioConcepto() throws IOException{
		
		log.debug("INICIO recuperarHonorarioConcepto");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		String todoHonorarioArray = connverHonorarioListToArray(datosCliente.getFacturaConceptoCargado().getFacturaHonorarios());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(todoHonorarioArray);
		
		getSession().put("datosCliente", datosCliente);

		log.debug("FIN recuperarHonorarioConcepto ");
		
	}
	
public void recuperarSuplidoConcepto() throws IOException{
		
		log.debug("INICIO recuperarSuplidoConcepto");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		String todoSuplidoArray = connverSuplidoListToArray(datosCliente.getFacturaConceptoCargado().getFacturaSuplidos());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(todoSuplidoArray);
		
		getSession().put("datosCliente", datosCliente);

		log.debug("FIN recuperarSuplidoConcepto ");
		
	}

public void recuperarGastoConcepto() throws IOException{
	
	log.debug("INICIO recuperarGastoConcepto");
	
	setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
	
	String todoGastoArray = connverGastoListToArray(datosCliente.getFacturaConceptoCargado().getFacturaGastos());
	
	servletResponse.setContentType("text/html; charset=iso-8859-1");
	PrintWriter out = servletResponse.getWriter();
	out.print(todoGastoArray);
	
	getSession().put("datosCliente", datosCliente);

	log.debug("FIN recuperarGastoConcepto ");
	
}
	
	
	
	private String aniadirNuevoHonorario(DatosClienteBean datosClienteBean) {
		String todoHonorarioArray = "";

		int MaxHonorarioConcepto = 0;
		
		for (int i=0;i< datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().size();i++){
			if (datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(i).getIdHonorario() > MaxHonorarioConcepto ){
				MaxHonorarioConcepto = (int) datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(i).getIdHonorario();
			}
		}
	
		FacturaHonorario fHonorario = new FacturaHonorario();
		fHonorario.setIdHonorario(0);
//		fHonorario.setHonorario("");
		fHonorario.setHonorarioCheckDescuento("0");
		fHonorario.setHonorarioCheckIva(new BigDecimal("1"));
		fHonorario.setHonorarioCheckIrpf(new BigDecimal("0"));
		fHonorario.setHonorario(0F);
		fHonorario.setHonorarioDescripcion("Introduzca la descripcion");
		fHonorario.setHonorarioDescuento(0F);
		fHonorario.setHonorarioIva(ConstantesFacturacion.IVA_DEFECTO);
		fHonorario.setHonorarioIrpf(ConstantesFacturacion.IRPF_DEFECTO);
		fHonorario.setHonorarioTotalIrpf(0F);
		fHonorario.setHonorarioTotalIva(0F);		
		fHonorario.setHonorarioTotal(0F);
		fHonorario.setFacturaConcepto(datosCliente.getFacturaConceptoCargado());
  	  	//datosCliente.setHonorarioTodo(todoHonorarioArray);
		
		//Actualizo el Concepto cargado
		datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().add(fHonorario);
		
		// Rellena los honorarios de la Factura
  	  	todoHonorarioArray = connverHonorarioListToArray(datosCliente.getFacturaConceptoCargado().getFacturaHonorarios());

		log.debug("respuesta aniadirNuevoHonorario "+todoHonorarioArray);
		return todoHonorarioArray;
	}
	
	private void aniadirNuevoConcepto(DatosClienteBean datosClienteBean) {
		
		log.debug("INICIO aniadirNuevoConcepto ");
		
		//String todoHonorarioConcepto = "";

		int MaxConcepto = 0;
		
		for (int i=0;i< datosCliente.getFactura().getFacturaConceptos().size();i++){
			if (datosCliente.getFactura().getFacturaConceptos().get(i).getIdConcepto() > MaxConcepto ){
				MaxConcepto = (int) datosCliente.getFactura().getFacturaConceptos().get(i).getIdConcepto();
			}
		}
	
		//Creamos un Honorario para que se muestre por pantalla
		List<FacturaHonorario> factHonorario = new ArrayList<FacturaHonorario>();
		FacturaHonorario fHonorario = new FacturaHonorario();

		fHonorario.setIdHonorario(0);
//		fHonorario.setHonorario("");
		fHonorario.setHonorarioCheckDescuento("0");
		fHonorario.setHonorarioCheckIva(new BigDecimal("1"));
		fHonorario.setHonorarioCheckIrpf(new BigDecimal("0"));
		fHonorario.setHonorario(0F);		
		fHonorario.setHonorarioDescripcion("Introduzca la descripcion");
		fHonorario.setHonorarioDescuento(0F);
		fHonorario.setHonorarioIva(ConstantesFacturacion.IVA_DEFECTO);
		fHonorario.setHonorarioIrpf(ConstantesFacturacion.IRPF_DEFECTO);
		fHonorario.setHonorarioTotalIrpf(0F);
		fHonorario.setHonorarioTotalIva(0F);		
		fHonorario.setHonorarioTotal(0F);

		//Creamos un Suplido para que se muestre por pantalla.
		List<FacturaSuplido> factSuplidoList = new ArrayList<FacturaSuplido>();
		FacturaSuplido facturaSuplido = new FacturaSuplido();
		facturaSuplido.setIdSuplido(0);
		facturaSuplido.setSuplidoCheckDescuento("0");
		facturaSuplido.setSuplido(0F);
		facturaSuplido.setSuplidoDescripcion("Introduzca la descripcion");
		facturaSuplido.setSuplidoDescuento(0F);	
		facturaSuplido.setSuplidoTotal(0F);
		
		//Creamos un Gasto para que se muestre por pantalla.
		List<FacturaGasto> factGastosList = new ArrayList<FacturaGasto>();
		FacturaGasto facturagasto = new FacturaGasto();
		facturagasto.setIdGasto(0);
		facturagasto.setGastoDescripcion("Introduzca la descripcion");
		facturagasto.setGasto(0F);
		facturagasto.setGastoCheck("1");
		facturagasto.setGastoIva(ConstantesFacturacion.IVA_DEFECTO);
		facturagasto.setGastoTotal(0F);
		
	
  	  	//datosCliente.setHonorarioTodo(todoHonorarioArray);
		
		FacturaConcepto fConcepto = new FacturaConcepto();
		fConcepto.setIdConcepto(0);
		//fConcepto.setNumColegiado(Colegiado);
		fConcepto.setNumExpediente(null);
		//fConcepto.setNumFactura(null);
		
		fHonorario.setFacturaConcepto(fConcepto);
		factHonorario.add(fHonorario);
		
		facturaSuplido.setFacturaConcepto(fConcepto);
		factSuplidoList.add(facturaSuplido);
		
		facturagasto.setFacturaConcepto(fConcepto);
		factGastosList.add(facturagasto);
		
		fConcepto.setFacturaHonorarios(factHonorario);
		fConcepto.setFacturaSuplidos(factSuplidoList);
		fConcepto.setFacturaGastos(factGastosList);
		
		fConcepto.setFactura(datosCliente.getFactura());
		//Actualizo el Concepto cargado
		datosCliente.getFactura().getFacturaConceptos().add(fConcepto);
		
		//datosCliente.getFactura().set
		// Rellena los honorarios de la Factura
		//todoHonorarioConcepto = connverConceptoListToArray(datosCliente.getFactura().getFacturaConceptos());

		log.debug("FIN aniadirNuevoConcepto ");		
	}
	
	// DRC@19-07-2012 Se utiliza para recoger todos los honorarios que han sido
	// seleccionados por la consulta a BBDD.
	public String connverHonorarioListToArray(List<FacturaHonorario> lFacturaHonorario) {
		String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;
		;
		String todoHonorariooArray = separador;

		FacturaHonorario facturaHonorario = new FacturaHonorario();

		int tam = 0;
		if (lFacturaHonorario != null && lFacturaHonorario.size() > 0)
			tam = lFacturaHonorario.size();
		for (int i = 0; i < tam; i++) {
			facturaHonorario = lFacturaHonorario.get(i);
			todoHonorariooArray += facturaHonorario.getIdHonorario()
					+ separador + facturaHonorario.getHonorarioDescripcion()
					+ separador + facturaHonorario.getHonorario() + separador
					+ facturaHonorario.getHonorarioCheckIva() + separador
					+ facturaHonorario.getHonorarioIva() + separador
					+ facturaHonorario.getHonorarioTotalIva() + separador
					+ facturaHonorario.getHonorarioCheckDescuento() + separador
					+ facturaHonorario.getHonorarioDescuento() + separador
					+ facturaHonorario.getHonorarioCheckIrpf() + separador
					+ facturaHonorario.getHonorarioIrpf() + separador
					+ facturaHonorario.getHonorarioTotalIrpf() + separador
					+ facturaHonorario.getHonorarioTotal() + separador;
		}
		if (lFacturaHonorario.size() == 0)
			todoHonorariooArray = null;

		if (lFacturaHonorario.size() > 0)
			todoHonorariooArray = todoHonorariooArray.substring(1,
					todoHonorariooArray.length() - 1);

		return todoHonorariooArray;
	}
	
	public String connverConceptoListToArray(List<FacturaConcepto> lFacturaConcepto) {
		String separador = ConstantesFacturacion.FACTURACION_SEPARADOR_CONCEPTO;
		String todoConceptoArray = separador;

		FacturaConcepto facturaConcepto = new FacturaConcepto();

		int tam = 0;
		if (lFacturaConcepto != null && lFacturaConcepto.size() > 0)
			tam = lFacturaConcepto.size();
		for (int i = 0; i < tam; i++) {
			facturaConcepto = lFacturaConcepto.get(i);
			todoConceptoArray += facturaConcepto.getIdConcepto() + separador + facturaConcepto.getConcepto() + separador + facturaConcepto.getNumExpediente() + separador;
		}
		if (lFacturaConcepto.size() == 0)
			todoConceptoArray = null;

		if (lFacturaConcepto.size() > 0)
			todoConceptoArray = todoConceptoArray.substring(1,todoConceptoArray.length() - 1);

		return todoConceptoArray;
	}
	
	public void eliminarConcepto()  throws Throwable{
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		String todoHonorarioConcepto = "";
		
		int honorarioConcepto = 0;
		boolean encontrado = false;
		
		//actualizoConceptoPantalla();
		
		int tam = datosCliente.getFactura().getFacturaConceptos().size();
		if (tam > 1){
			for (int i=0;i< tam;i++){
				
				if (encontrado){
					//datosCliente.getFactura().getFacturaConceptos().get(i).setIdConcepto(0);
				} else{
					//datosCliente.getFactura().getFacturaConceptos().get(i).setIdConcepto(0);
				}				
				if (encontrado == false && getConceptoIdBorrar().equals(String.valueOf(i))){
					encontrado = true;
					honorarioConcepto = i;
					
				}				
			}
			
			datosCliente.getFactura().getFacturaConceptos().remove(honorarioConcepto);
			
		}
		
		todoHonorarioConcepto = connverConceptoListToArray(datosCliente.getFactura().getFacturaConceptos());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(todoHonorarioConcepto);
		
		getSession().put("datosCliente", datosCliente);

		log.debug("respuesta eliminarConcepto "+todoHonorarioConcepto);

	}
	
	public void eliminarHonorario()  throws Throwable{
	
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		String todoHonorarioArray = "";
		
		int honorarioBorrar = 0;
		boolean encontrado = false;
		
		rellenarHonorarioFacturacion();
		
		int tam = datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().size();
		if (tam > 1){
			for (int i=0;i< tam;i++){
				
				if (encontrado){
					//datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(i).setIdHonorario(0);
				} else{
					//datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().get(i).setIdHonorario(0);
				}				
				if (encontrado == false && getHonorarioIdBorrar().equals(String.valueOf(i))){
					encontrado = true;
					honorarioBorrar = i;
					
				}				
			}
			
			datosCliente.getFacturaConceptoCargado().getFacturaHonorarios().remove(honorarioBorrar);
			
		}
		
		todoHonorarioArray = connverHonorarioListToArray(datosCliente.getFacturaConceptoCargado().getFacturaHonorarios());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(todoHonorarioArray);
		
		getSession().put("datosCliente", datosCliente);

		log.debug("respuesta eliminarHonorario "+todoHonorarioArray);

	}
	
	public void cambioConcepto()throws Throwable{
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
	
		String idConceptCambio = String.valueOf(getConceptoIdCambio());
		
		FacturaConcepto fConc = datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptCambio));
		
		datosCliente.setFacturaConceptoCargado(fConc);
			
		String todoConceptoArray = connverConceptoListToArray(datosCliente.getFactura().getFacturaConceptos());
		
		getSession().put("datosCliente", datosCliente);
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(todoConceptoArray);

	}
	
	public void actualizoConceptoPantalla(){
		
		String idConceptActual = String.valueOf(getIdConceptoActual());
		
		if (!"-1".equals(idConceptActual)){
			datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptActual)).setConcepto(getConcepto());
			if ("-1".equals(getIdExpAsociado())){
				datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptActual)).setNumExpediente(" ");
			} else{
				datosCliente.getFactura().getFacturaConceptos().get(Integer.valueOf(idConceptActual)).setNumExpediente(getIdExpAsociado());
			}
		}
		
	}
	
	public void nuevoSuplido() throws Throwable{
		log.debug("recuperarNombreConceptoColegiado");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		// DRC@31-08-2012 Recupera los datos de los honorarios introducidos
		// por el usuario en una lista, para relacionarlo con el concepto actual
		
		rellenarSuplidoFacturacion();		

		String resultado = aniadirNuevoSuplido(getDatosCliente());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
		
		getSession().put("datosCliente", datosCliente);
	}
	
	public void nuevoGasto() throws Throwable{
		log.debug("recuperarNombreConceptoColegiado");
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		// DRC@31-08-2012 Recupera los datos de los honorarios introducidos
		// por el usuario en una lista, para relacionarlo con el concepto actual
		
		rellenarGastoFacturacion();		

		String resultado = aniadirNuevoGasto(getDatosCliente());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
		
		getSession().put("datosCliente", datosCliente);
	}
	
	// DRC@10-07-2012 Se encarga de rellenar en la clase FacturaHonorario con
		// los datos del formulario
		public void rellenarSuplidoFacturacion(){		
			int numSuplido = 0;
			int vueltaSuplido = 0;
			String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;

			try {
				if (datosCliente != null && getSuplidoTodo() != null
						&& getSuplidoTodo().length() > 0)
					numSuplido = getSuplidoTodo().length();

				if (numSuplido > 0) {				
					String todoSuplidosArray = getSuplidoTodo();

					if (todoSuplidosArray.trim().startsWith("\\"))
						todoSuplidosArray = todoSuplidosArray.substring(1,
								todoSuplidosArray.trim().length());

					if (todoSuplidosArray.trim().endsWith(","))
						todoSuplidosArray = todoSuplidosArray.substring(0,
								todoSuplidosArray.trim().length() - 1);

					// DEFECTO = 0-Introduzca la descripcion del suplido-0-false-0-0
					//if (!todoSuplidosArray.equalsIgnoreCase(ConstantesFacturacion.Suplido_DEFECTO)) {
						StringTokenizer tokens = new StringTokenizer(
								todoSuplidosArray, separador);
						while (tokens.hasMoreTokens()) {				
							tokens.nextToken();
							//datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setIdSuplido(0);
							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoDescripcion(tokens.nextToken());
							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplido(Float.valueOf(tokens.nextToken()));
//							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoCheckIva(new BigDecimal(convertirChecktoString(tokens.nextToken())));
//							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoIva(tokens.nextToken());
//							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoTotalIva(tokens.nextToken());
							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoCheckDescuento(convertirChecktoString(tokens.nextToken()));
							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoDescuento(Float.valueOf(tokens.nextToken()));
//							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoCheckIrpf(new BigDecimal(convertirChecktoString(tokens.nextToken())));
//							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoIrpf(tokens.nextToken());
//							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoTotalIrpf(tokens.nextToken());
							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setSuplidoTotal(Float.valueOf(tokens.nextToken()));
							datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(vueltaSuplido).setFacturaConcepto(datosCliente.getFacturaConceptoCargado());
							vueltaSuplido = vueltaSuplido+1;

						}
//						getSession().put("datosCliente", datosCliente);
				}
				
			} catch (Exception e) {
				log.error("Error al recuperar los datos introducidor para los Suplidos de Facturacion: "+ e);
			}
			
		}
		
		public void rellenarGastoFacturacion(){		
			int numGasto = 0;
			int vueltaGasto = 0;
			String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;

			try {
				if (datosCliente != null && getGastoTodo() != null
						&& getGastoTodo().length() > 0)
					numGasto = getGastoTodo().length();

				if (numGasto > 0) {				
					String todoGastosArray = getGastoTodo();

					if (todoGastosArray.trim().startsWith("\\"))
						todoGastosArray = todoGastosArray.substring(1,
								todoGastosArray.trim().length());

					if (todoGastosArray.trim().endsWith(","))
						todoGastosArray = todoGastosArray.substring(0,
								todoGastosArray.trim().length() - 1);

					// DEFECTO = 0-Introduzca la descripcion del Gasto-0-false-0-0
					//if (!todoGastosArray.equalsIgnoreCase(ConstantesFacturacion.Gasto_DEFECTO)) {
						StringTokenizer tokens = new StringTokenizer(
								todoGastosArray, separador);
						while (tokens.hasMoreTokens()) {				
							tokens.nextToken();
							//datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setIdGasto(0);
							datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setGastoDescripcion(tokens.nextToken());
							datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setGasto(Float.valueOf(tokens.nextToken()));
							datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setGastoCheck(convertirChecktoString(tokens.nextToken()));
							datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setGastoIva(Integer.valueOf(tokens.nextToken()));
							if ("0".equals(String.valueOf(datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).getGastoCheck()))){
								datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setGastoIva(0);
							}
							datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setGastoTotalIva(Float.valueOf(tokens.nextToken()));
							datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setGastoTotal(Float.valueOf(tokens.nextToken()));
							datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(vueltaGasto).setFacturaConcepto(datosCliente.getFacturaConceptoCargado());
							vueltaGasto = vueltaGasto+1;

						}
//						getSession().put("datosCliente", datosCliente);
				}
				
			} catch (Exception e) {
				log.error("Error al recuperar los datos introducidor para los Gastos de Facturacion: "+ e);
			}
			
		}
		

		private String aniadirNuevoSuplido(DatosClienteBean datosClienteBean) {
			String todoSuplidoArray = "";

			int MaxSuplidoConcepto = 0;
			
			for (int i=0;i< datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().size();i++){
				if (datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(i).getIdSuplido() > MaxSuplidoConcepto ){
					MaxSuplidoConcepto = (int) datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(i).getIdSuplido();
				}
			}
		
			FacturaSuplido fSuplido = new FacturaSuplido();
			fSuplido.setIdSuplido(0);
			fSuplido.setSuplidoCheckDescuento("0");
//			fSuplido.setSuplidoCheckIva(new BigDecimal("1"));
//			fSuplido.setSuplidoCheckIrpf(new BigDecimal("0"));
			fSuplido.setSuplido(0F);
			fSuplido.setSuplidoDescripcion("Introduzca la descripcion");
			fSuplido.setSuplidoDescuento(0F);
//			fSuplido.setSuplidoIva(null);
//			fSuplido.setSuplidoIrpf(null);
//			fSuplido.setSuplidoTotalIrpf("0");
//			fSuplido.setSuplidoTotalIva("0");		
			fSuplido.setSuplidoTotal(0F);
			fSuplido.setFacturaConcepto(datosCliente.getFacturaConceptoCargado());
			
	  	  	//datosCliente.setSuplidoTodo(todoSuplidoArray);
			
			//Actualizo el Concepto cargado
			datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().add(fSuplido);
			
			// Rellena los Suplidos de la Factura
	  	  	todoSuplidoArray = connverSuplidoListToArray(datosCliente.getFacturaConceptoCargado().getFacturaSuplidos());

			log.debug("respuesta transformarEnStringDatosVehiculo "+todoSuplidoArray);
			return todoSuplidoArray;
		}
		
		private String aniadirNuevoGasto(DatosClienteBean datosClienteBean) {
			String todoGastoArray = "";

			int MaxGastoConcepto = 0;
			
			for (int i=0;i< datosCliente.getFacturaConceptoCargado().getFacturaGastos().size();i++){
				if (datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(i).getIdGasto() > MaxGastoConcepto ){
					MaxGastoConcepto = (int) datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(i).getIdGasto();
				}
			}
		
			FacturaGasto fGasto = new FacturaGasto();
			fGasto.setIdGasto(0);
			fGasto.setGastoDescripcion("Introduzca la descripcion");
			fGasto.setGasto(0F);
			fGasto.setGastoCheck("1");
			fGasto.setGastoIva(ConstantesFacturacion.IVA_DEFECTO);
			fGasto.setGastoTotal(0F);
			fGasto.setFacturaConcepto(datosCliente.getFacturaConceptoCargado());

			//Actualizo el Concepto cargado
			datosCliente.getFacturaConceptoCargado().getFacturaGastos().add(fGasto);
			
			// Rellena los Gastos de la Factura
	  	  	todoGastoArray = connverGastoListToArray(datosCliente.getFacturaConceptoCargado().getFacturaGastos());

			log.debug("respuesta transformarEnStringDatosVehiculo "+todoGastoArray);
			return todoGastoArray;
		}
		
		public String connverSuplidoListToArray(List<FacturaSuplido> lFacturaSuplido) {
			String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;
			;
			String todoSuplidoArray = separador;

			FacturaSuplido facturaSuplido = new FacturaSuplido();

			int tam = 0;
			if (lFacturaSuplido != null && lFacturaSuplido.size() > 0)
				tam = lFacturaSuplido.size();
			for (int i = 0; i < tam; i++) {
				facturaSuplido = lFacturaSuplido.get(i);
				todoSuplidoArray += facturaSuplido.getIdSuplido()
						+ separador + facturaSuplido.getSuplidoDescripcion()
						+ separador + facturaSuplido.getSuplido() + separador
//						+ facturaSuplido.getSuplidoCheckIva() + separador
//						+ facturaSuplido.getSuplidoIva() + separador
//						+ facturaSuplido.getSuplidoTotalIva() + separador
						+ facturaSuplido.getSuplidoCheckDescuento() + separador
						+ facturaSuplido.getSuplidoDescuento() + separador
//						+ facturaSuplido.getSuplidoCheckIrpf() + separador
//						+ facturaSuplido.getSuplidoIrpf() + separador
//						+ facturaSuplido.getSuplidoTotalIrpf() + separador
						+ facturaSuplido.getSuplidoTotal() + separador;
			}
			if (lFacturaSuplido.size() == 0)
				todoSuplidoArray = null;

			if (lFacturaSuplido.size() > 0)
				todoSuplidoArray = todoSuplidoArray.substring(1,
						todoSuplidoArray.length() - 1);

			return todoSuplidoArray;
		}
		
		public String connverGastoListToArray(List<FacturaGasto> lFacturaGasto) {
			String separador = ConstantesFacturacion.FACTURACION_SEPARADOR;
			;
			String todoGastoArray = separador;

			FacturaGasto facturaGasto = new FacturaGasto();

			int tam = 0;
			if (lFacturaGasto != null && lFacturaGasto.size() > 0)
				tam = lFacturaGasto.size();
			for (int i = 0; i < tam; i++) {
				facturaGasto = lFacturaGasto.get(i);
				todoGastoArray += facturaGasto.getIdGasto()
						+ separador + facturaGasto.getGastoDescripcion()
						+ separador + facturaGasto.getGasto() + separador
						+ facturaGasto.getGastoCheck() + separador
						+ facturaGasto.getGastoIva() + separador
//						+ facturaGasto.getGastoTotalIva() + separador
//						+ facturaGasto.getGastoCheckDescuento() + separador
//						+ facturaGasto.getGastoDescuento() + separador
//						+ facturaGasto.getGastoCheckIrpf() + separador
//						+ facturaGasto.getGastoIrpf() + separador
//						+ facturaGasto.getGastoTotalIrpf() + separador
						+ facturaGasto.getGastoTotal() + separador;
			}
			if (lFacturaGasto.size() == 0)
				todoGastoArray = null;

			if (lFacturaGasto.size() > 0)
				todoGastoArray = todoGastoArray.substring(1,
						todoGastoArray.length() - 1);

			return todoGastoArray;
		}
		
		public void eliminarSuplido()  throws Throwable{
			
			setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
			
			String todoSuplidoArray = "";
			
			int suplidoBorrar = 0;
			boolean encontrado = false;
			
			rellenarSuplidoFacturacion();
			
			int tam = datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().size();
			if (tam > 1){
				for (int i=0;i< tam;i++){
					
					if (encontrado){
						//datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(i).setIdSuplido(0);
					} else{
						//datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().get(i).setIdSuplido(0);
					}				
					if (encontrado == false && getSuplidoIdBorrar().equals(String.valueOf(i))){
						encontrado = true;
						suplidoBorrar = i;
						
					}				
				}
				
				datosCliente.getFacturaConceptoCargado().getFacturaSuplidos().remove(suplidoBorrar);
				
			}
			
			todoSuplidoArray = connverSuplidoListToArray(datosCliente.getFacturaConceptoCargado().getFacturaSuplidos());
			
			servletResponse.setContentType("text/html; charset=iso-8859-1");
			PrintWriter out = servletResponse.getWriter();
			out.print(todoSuplidoArray);
			
			getSession().put("datosCliente", datosCliente);

			log.debug("respuesta eliminarSuplido "+todoSuplidoArray);

		}
		
		public void eliminarGasto()  throws Throwable{
			
			setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
			
			String todoGastoArray = "";
			
			int gastoBorrar = 0;
			boolean encontrado = false;
			
			rellenarGastoFacturacion();
			
			int tam = datosCliente.getFacturaConceptoCargado().getFacturaGastos().size();
			if (tam > 1){
				for (int i=0;i< tam;i++){
					
					if (encontrado){
						//datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(i).setIdGasto(0);
					} else{
						//datosCliente.getFacturaConceptoCargado().getFacturaGastos().get(i).setIdGasto(0);
					}				
					if (encontrado == false && getGastoIdBorrar().equals(String.valueOf(i))){
						encontrado = true;
						gastoBorrar = i;
						
					}				
				}
				
				datosCliente.getFacturaConceptoCargado().getFacturaGastos().remove(gastoBorrar);
				
			}
			
			todoGastoArray = connverGastoListToArray(datosCliente.getFacturaConceptoCargado().getFacturaGastos());
			
			servletResponse.setContentType("text/html; charset=iso-8859-1");
			PrintWriter out = servletResponse.getWriter();
			out.print(todoGastoArray);
			
			getSession().put("datosCliente", datosCliente);

			log.debug("respuesta eliminarSuplido "+todoGastoArray);

		}
	
	public void compruebaPassword(){
		DatosDAO dao = new DatosDAO();
		//Se encripta la password insertada por el usuario.
		String passwordInsertada = utilesColegiado.encriptaEnSHA(getPassword());
		//Se obtiene la password de BD. Esta viene encriptada.
		String passwordGuardada = dao.getClaveColegiado(utilesColegiado.getNumColegiadoSession());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = null;
		try {
			out = servletResponse.getWriter();
		} catch (IOException e) {
			log.error("Error al comprobar password de facturacion. ", e);
		}
		
		if (passwordInsertada.equals(passwordGuardada)){
			out.print("true");
		}else{
			out.print("false");
		}
	}
		
	public void compruebaEstadoFactura(){
		DatosDAO dao = new DatosDAO();
		String estadoFactura = dao.checkPDF(getNumFactura(), utilesColegiado.getNumColegiadoSession());
		
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = null;
		try {
			out = servletResponse.getWriter();
		} catch (IOException e) {
			log.error("Error al comprobar el estado de una factura. ", e);
		}
		
		if (estadoFactura.equals("1")){
			out.print("true");
		}else{
			out.print("false");
		}
	}
	public String getSuplidoTodo() {
		return suplidoTodo;
	}
	public void setSuplidoTodo(String suplidoTodo) {
		this.suplidoTodo = suplidoTodo;
	}
	public String getGastoTodo() {
		return gastoTodo;
	}
	public void setGastoTodo(String gastoTodo) {
		this.gastoTodo = gastoTodo;
	}
	public String getGastoIdBorrar() {
		return gastoIdBorrar;
	}
	public void setGastoIdBorrar(String gastoIdBorrar) {
		this.gastoIdBorrar = gastoIdBorrar;
	}
	public String getSuplidoIdBorrar() {
		return suplidoIdBorrar;
	}
	public void setSuplidoIdBorrar(String suplidoIdBorrar) {
		this.suplidoIdBorrar = suplidoIdBorrar;
	}
		

	public String getHonorarioIdBorrar() {
		return honorarioIdBorrar;
	}
	
	public void setHonorarioIdBorrar(String honorarioIdBorrar) {
		this.honorarioIdBorrar = honorarioIdBorrar;
	}
	
	public DatosClienteBean getDatosCliente() {
		return datosCliente;
	}
	public void setDatosCliente(DatosClienteBean datosCliente) {
		this.datosCliente = datosCliente;
	}
	private HttpServletRequest getServletRequest() {
		return servletRequest;
	}
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}
	public String getHonorarioTodo() {
		return honorarioTodo;
	}
	public void setHonorarioTodo(String honorarioTodo) {
		this.honorarioTodo = honorarioTodo;
	}
	
	public String getConceptoIdBorrar() {
		return conceptoIdBorrar;
	}
	public void setConceptoIdBorrar(String conceptoIdBorrar) {
		this.conceptoIdBorrar = conceptoIdBorrar;
	}
	public String getConceptoTodo() {
		return conceptoTodo;
	}
	public void setConceptoTodo(String conceptoTodo) {
		this.conceptoTodo = conceptoTodo;
	}
	public String getIdConceptoActual() {
		return idConceptoActual;
	}
	public void setIdConceptoActual(String idConceptoActual) {
		this.idConceptoActual = idConceptoActual;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getIdExpAsociado() {
		return idExpAsociado;
	}
	public void setIdExpAsociado(String idExpAsociado) {
		this.idExpAsociado = idExpAsociado;
	}
	
	public String getConceptoIdCambio() {
		return conceptoIdCambio;
	}
	public void setConceptoIdCambio(String conceptoIdCambio) {
		this.conceptoIdCambio = conceptoIdCambio;
	}
	
	public void calcularFacturaAjax() throws IOException{
		
		String calculo = "";
		
		setDatosCliente((DatosClienteBean) getSession().get("datosCliente"));
		
		TotalesFacturaBean totalesFacturaBean = UtilesVistaFacturacion.calcularFactura(datosCliente);
		
		// Recorro la lista de conceptos
		for(int i = 0; i < totalesFacturaBean.getTotalesConceptos().size(); i++){
			
			// Recupero el concepto actual
			TotalesConceptoBean concepto = totalesFacturaBean.getTotalesConceptos().get(i);
			
			calculo += "[CONCEPTO]" + concepto.getTotalConcepto() + "[FIN_TOTAL_CONCEPTO]";
//			
//			// Recorro la lista de honorarios
//			for(int j = 0; j < concepto.getTotalesHonorarios().size(); j++){
//				
//				String honorario = concepto.getTotalesHonorarios().get(j);
//				
//				calculo += "[HONORARIO]" + honorario;
//				
//			}
//			
//			// Recorro la lista de suplidos
//			for(int k = 0; k < concepto.getTotalesSuplidos().size(); k++){
//				
//				String suplido = concepto.getTotalesSuplidos().get(k);
//				
//				calculo += "[SUPLIDO]" + suplido;
//				
//			}
//			
//			// Recorro la lista de gastos
//			for(int l = 0; l < concepto.getTotalesGastos().size(); l++){
//				
//				String gasto = concepto.getTotalesGastos().get(l);
//				
//				calculo += "[GASTO]" + gasto;
//				
//			}
//			
		}
		
		calculo += "[FACTURA]" + totalesFacturaBean.getTotalFactura() + "[FIN_FACTURA]";
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(calculo);
		//return totalesFacturaBean;
		
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNumFactura() {
		return numFactura;
	}
	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}
	
}