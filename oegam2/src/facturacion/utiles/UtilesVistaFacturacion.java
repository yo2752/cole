package facturacion.utiles;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;

import facturacion.beans.TotalesConceptoBean;
import facturacion.beans.TotalesFacturaBean;
import facturacion.comun.DatosClienteBean;
import facturacion.utiles.enumerados.ClienteFacturacion;
import facturacion.utiles.enumerados.EsDuplicado;
import facturacion.utiles.enumerados.EstadoFacturacion;
import facturacion.utiles.enumerados.FormaPago;
import hibernate.entities.facturacion.Factura;
import hibernate.entities.facturacion.FacturaConcepto;
import hibernate.entities.facturacion.FacturaGasto;
import hibernate.entities.facturacion.FacturaHonorario;
import hibernate.entities.facturacion.FacturaIrpf;
import hibernate.entities.facturacion.FacturaIva;
import hibernate.entities.facturacion.FacturaSuplido;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaFacturacion {
	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaFacturacion.class);

	private static List<FacturaIva> ivas;
	private static List<FacturaIrpf> irpfs;

	public static List<FacturaIva> listaIvas() {
		if (ivas == null) {
			ivas = ContextoSpring.getInstance().getBean(ServicioFacturacion.class).listaIvas();
		}
		return ivas;
	}

	// Recoge de la tabla Factura_IRPF de BBDD todos los IRPF's disponibles
	public static List<FacturaIrpf> listaIrpfs() {
		if (irpfs == null) {
			irpfs = ContextoSpring.getInstance().getBean(ServicioFacturacion.class).listaIrpfs();
		}
		return irpfs;
	}

	/**
	 * @author: Santiago Cuenca Calcula los importes parciales y totales para la
	 *          factura
	 * @return totales
	 */
	public static TotalesFacturaBean calcularFactura(DatosClienteBean datosCliente) {

		/* Declaración de sumatorios de subtotales */
		Float sumatorioHonorarios = new Float(0);
		Float sumatorioSuplidos = new Float(0);
		Float sumatorioGastos = new Float(0);
		Float totalConcepto = new Float(0);
		Float sumatorioConceptos = new Float(0);

		/* Declaración del bean que almacenará los totales */
		TotalesFacturaBean totalesFacturaBean = new TotalesFacturaBean();

		/* Declaración de objetos que contendrán la factura y los conceptos */
		Factura factura = datosCliente.getFactura();
		List<FacturaConcepto> conceptos = factura.getFacturaConceptos();

		for (int i = 0; i < conceptos.size(); i++) {

			// Le inserto un nuevo bean de totalesConcepto al bean de totalesFactura y
			// reseteo los sumatorios de honorarios, suplidos y gastos
			totalesFacturaBean.getTotalesConceptos().add(i, new TotalesConceptoBean());
			sumatorioHonorarios = sumatorioSuplidos = sumatorioGastos = new Float(0);

			// Trabajo con los honorarios del concepto actual (i)
			List<FacturaHonorario> honorariosConcepto = conceptos.get(i).getFacturaHonorarios();
			for (int j = 0; j < honorariosConcepto.size(); j++) {

				// Le inserto un nuevo elemento al bean de totalesHonorarios
				totalesFacturaBean.getTotalesConceptos().get(i).getTotalesHonorarios().add(j, "");

				// Capturo en "honorario" el honorario actual (j)
				FacturaHonorario honorario = honorariosConcepto.get(j);

				// Obtengo los valores para los cálculos
				Float baseImponible = honorario.getHonorario() != null ? honorario.getHonorario() : 0F;
				Float iva = Float.valueOf(honorario.getHonorarioIva()) != null ? honorario.getHonorarioIva() : 0F;
				Float irpf = Float.valueOf(honorario.getHonorarioIrpf()) != null ? honorario.getHonorarioIrpf() : 0F;
				Float descuento = honorario.getHonorarioDescuento() != null ? honorario.getHonorarioDescuento() : 0F;

				// Calculo el IVA, IRPF y el Descuento aplicables a la base imponible
				Float calculoIva = calcularPorcentaje(baseImponible, iva);
				Float calculoIrpf = calcularPorcentaje(baseImponible, irpf);
				Float calculoDescuento = calcularPorcentaje(baseImponible, descuento);

				// Calculo el total del honorario
				Float totalHonorario = (baseImponible + calculoIva - calculoIrpf - calculoDescuento);

				// Almaceno el total en el honorario
//				honorario.setHonorarioTotal(totalHonorario!=null ? totalHonorario.toString() : BigDecimal.ZERO.toString());
//				totalesFacturaBean.getTotalesConceptos().get(i).getTotalesHonorarios().set(j, honorario.getHonorarioTotal());

				// Hago un sumatorio de subtotales de honorarios para más adelante setear el
				// total del concepto
				sumatorioHonorarios += totalHonorario;

			}

			// Trabajo con los suplidos del honorario actual (i)
			List<FacturaSuplido> suplidosConcepto = conceptos.get(i).getFacturaSuplidos();
			for (int k = 0; k < suplidosConcepto.size(); k++) {

				// Le inserto un nuevo elemento al bean de totalesSuplidos
				totalesFacturaBean.getTotalesConceptos().get(i).getTotalesSuplidos().add(k, "");

				// Capturo en "suplido" el suplido actual (k)
				FacturaSuplido suplido = suplidosConcepto.get(k);

				// Obtengo los valores para los cálculos
				Float baseImponible = suplido.getSuplido() != null ? suplido.getSuplido() : 0F;
				Float descuento = suplido.getSuplidoDescuento() != null ? suplido.getSuplidoDescuento() : 0F;

				// Calculo del descuento aplicables a la base imponible
				Float calculoDescuento = calcularPorcentaje(baseImponible, descuento);

				// Calculo el total del suplido
				Float totalSuplido = (baseImponible - calculoDescuento);

				// Almaceno el total en el suplido
				// suplido.setSuplidoTotal(totalSuplido!=null ? totalSuplido.toString() :
				// BigDecimal.ZERO.toString());
				// totalesFacturaBean.getTotalesConceptos().get(i).getTotalesSuplidos().set(k,
				// suplido.getSuplidoTotal());

				// Hago un sumatorio de subtotales de suplidos para más adelante setear el total
				// del concepto
				sumatorioSuplidos += totalSuplido;

			}
			// Trabajo con los gastos del concepto actual (i)
			List<FacturaGasto> gastosConcepto = conceptos.get(i).getFacturaGastos();
			for (int l = 0; l < gastosConcepto.size(); l++) {

				// Le inserto un nuevo elemento al bean de totalesSuplidos
				totalesFacturaBean.getTotalesConceptos().get(i).getTotalesGastos().add(l, "");

				// Capturo en "gasto" el gasto actual (l)
				FacturaGasto gasto = gastosConcepto.get(l);

				// Obtengo los valores para los cálculos
				Float baseImponible = gasto.getGasto() != null ? gasto.getGasto() : 0F;
				Float iva = Float.valueOf(gasto.getGastoIva()) != null ? gasto.getGastoIva() : 0F;

				// Calculo el IVA aplicable a la base imponible
				Float calculoIva = calcularPorcentaje(baseImponible, iva);

				// Calculo el total del gasto
				Float totalGasto = (baseImponible + calculoIva);

				// Almaceno el total en el gasto
				// gasto.setGastoTotal(totalGasto!=null ? totalGasto.toString() :
				// BigDecimal.ZERO.toString());
				// totalesFacturaBean.getTotalesConceptos().get(i).getTotalesGastos().set(l,
				// gasto.getGastoTotal());

				// Hago un sumatorio de subtotales de gastos para más adelante setear el total
				// del concepto
				sumatorioGastos += totalGasto;
			}

			totalConcepto = sumatorioHonorarios + sumatorioSuplidos + sumatorioGastos;
			totalesFacturaBean.getTotalesConceptos().get(i)
					.setTotalConcepto(totalConcepto != null ? totalConcepto.toString() : BigDecimal.ZERO.toString());
			sumatorioConceptos += totalConcepto;
		}

//		float otroTotal = 0f;
//		float provFondoTotal = 0f;
//
//		try{
//			otroTotal = Float.parseFloat(factura.getFacturaOtro().getOtroTotal());
//			provFondoTotal = Float.parseFloat(factura.getFacturaProvFondo().getFondoTotal());
//		}catch(Exception e){
//			//Que se quede en cero...
//		}
//
//		sumatorioConceptos += otroTotal + provFondoTotal;

		totalesFacturaBean.setTotalFactura(
				sumatorioConceptos != null ? sumatorioConceptos.toString() : BigDecimal.ZERO.toString());

		return totalesFacturaBean;
	}

	public static Float calcularPorcentaje(Float base, Float porcentaje) {
		return new Float((base * porcentaje) / 100);
	}

	public static String traducirFecha(String fecha) {
		String nFecha = fecha;
		if ((fecha.length()) == 8) {
			nFecha = fecha.substring(6) + "20" + fecha.substring(6, 8);
		}
		return nFecha;
	}

	public static ClienteFacturacion[] getComboClienteFacturacion() {
		return ClienteFacturacion.values();
	}

	public static EstadoFacturacion[] getComboEstadoFacturacion() {
		return EstadoFacturacion.values();
	}

	public static FormaPago[] getComboFormaPago() {
		return FormaPago.values();
	}

	public static EsDuplicado[] getComboEsDuplicado() {
		return EsDuplicado.values();
	}

//	public static List<String> getNumExpedientes(){
//		if(comboNumExpedientes==null){
//			comboNumExpedientes = new ArrayList<String>();
//			String[] expedientes = new String[]{"5", "10", "15", "20", "50", "100","200","500"};
//
//			for(int i=0; i<expedientes.length;i++){
//				comboNumExpedientes.add(expedientes[i]);
//			}
//		}
//		return comboNumExpedientes;
//	}

}