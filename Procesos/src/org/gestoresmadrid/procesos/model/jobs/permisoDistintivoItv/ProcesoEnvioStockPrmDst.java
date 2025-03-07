package org.gestoresmadrid.procesos.model.jobs.permisoDistintivoItv;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.oegam2comun.materiales.model.service.ServicioStockMateriales;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.StockMaterialBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.StockResultadosBean;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProceso;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoEnvioStockPrmDst extends AbstractProceso {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoEnvioStockPrmDst.class);

	private String STOCK_MATERIAL_MINIMO_E = "stock.material.minimo.distintivo.e";
	private String STOCK_MATERIAL_MINIMO_B = "stock.material.minimo.distintivo.b";
	private String STOCK_MATERIAL_MINIMO_C = "stock.material.minimo.distintivo.c";
	private String STOCK_MATERIAL_MINIMO_0 = "stock.material.minimo.distintivo.0";
	private String STOCK_MATERIAL_MINIMO_LO = "stock.material.minimo.distintivo.lo";
	private String STOCK_MATERIAL_MINIMO_LE = "stock.material.minimo.distintivo.le";
	private String STOCK_MATERIAL_MINIMO_BMT = "stock.material.minimo.distintivo.bmt";
	private String STOCK_MATERIAL_MINIMO_CMT = "stock.material.minimo.distintivo.cmt";
	private String STOCK_MATERIAL_MINIMO_PERMISO_CIRCULACION = "stock.material.minimo.permiso.circulacion";

	private String TIPO_PERMISO_CIRCULACION = "PC";

	@Autowired
	ServicioConsultaStock servicioConsultaStock;
	
	@Autowired
	ServicioStockMateriales servicioStockMateriales; 

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	protected void doExecute() throws JobExecutionException {
		Boolean esLaborable = Boolean.TRUE;
		String resultadoEjecucion = null;
		String excepcion = null;
		String propertie = gestorPropiedades.valorPropertie("stock.material.nuevo");

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}

		if (esLaborable) {
			try {
				log.info("Inicio proceso: " + getProceso());
				if (!"SI".equals(propertie)){
					// Obtenemos el stock entero de BBDD
					List<StockResultadosBean> listaStock = servicioConsultaStock.obtenerStock();
					if (listaStock != null && !listaStock.isEmpty()) {
						// Comprobamos los stock que están por debajo del límite
						ArrayList<String> listaAvisoStock = obtenerAvisoStock(listaStock);
						if (listaAvisoStock != null && !listaAvisoStock.isEmpty()) {
							// Comprobamos el stock que está
							ArrayList<String> listaAvisoStockEnviar = obtenerListaStockEnviar(listaAvisoStock);
							// Comprobamos el stock que está
							if (listaAvisoStockEnviar != null && !listaAvisoStockEnviar.isEmpty()) {
								// enviar y montar correo
								ResultBean result = servicioConsultaStock.enviarCorreoEnvioStock(listaAvisoStockEnviar);
								// Crear un envio data por cada par de jefatura y tipo
								if (result != null && !result.getError()) {
									envioData(listaAvisoStockEnviar);
									resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
									excepcion = ConstantesProcesos.EJECUCION_CORRECTA;
									peticionCorrecta();
								} else {
									resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
									excepcion = "Correo no enviado";
									peticionRecuperable();
								}
							} else {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
								excepcion = "No hay stock por debajo del límite sin enviar correo";
								peticionCorrecta();
							}
						} else {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							excepcion = "No hay stock por debajo del límite";
							peticionCorrecta();
						}
						actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
					} else {
						sinPeticiones();
					}
				}else {
					// Obtenemos el stock entero de BBDD
					List<StockMaterialBean> listaStock = servicioStockMateriales.findAll();
					if (listaStock != null && !listaStock.isEmpty()) {
						// Comprobamos los stock que están por debajo del límite
						ArrayList<String> listaAvisoStock = obtenerAvisoStock2(listaStock);
						if (listaAvisoStock != null && !listaAvisoStock.isEmpty()) {
							// Comprobamos el stock que está
							ArrayList<String> listaAvisoStockEnviar = obtenerListaStockEnviar(listaAvisoStock);
							// Comprobamos el stock que está
							if (listaAvisoStockEnviar != null && !listaAvisoStockEnviar.isEmpty()) {
								// enviar y montar correo
								ResultBean result = servicioConsultaStock.enviarCorreoEnvioStock(listaAvisoStockEnviar);
								// Crear un envio data por cada par de jefatura y tipo
								if (result != null && !result.getError()) {
									envioData(listaAvisoStockEnviar);
									resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
									excepcion = ConstantesProcesos.EJECUCION_CORRECTA;
									peticionCorrecta();
								} else {
									resultadoEjecucion = ConstantesProcesos.EJECUCION_NO_CORRECTA;
									excepcion = "Correo no enviado";
									peticionRecuperable();
								}
							} else {
								resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
								excepcion = "No hay stock por debajo del límite sin enviar correo";
								peticionCorrecta();
							}
						} else {
							resultadoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;
							excepcion = "No hay stock por debajo del límite";
							peticionCorrecta();
						}
						actualizarEjecucion(getProceso(), excepcion, resultadoEjecucion, "0");
					} else {
						sinPeticiones();
					}
				
				}
				
			} catch (Exception e) {
				log.error("Ocurrio un error no controlado en el proceso ENVIO_STOCK_PRM_DST, error: ", e);
				String messageException = getMessageException(e);
				errorNoRecuperable();
				actualizarEjecucion(getProceso(), messageException, ConstantesProcesos.EJECUCION_EXCEPCION, "0");
			}
			log.info("Fin proceso: " + getProceso());
		} else {
			peticionCorrecta();
		}
	}

	private ArrayList<String> obtenerListaStockEnviar(ArrayList<String> listaAvisoStock) {
		ArrayList<String> listaAvisoStockEnviar = new ArrayList<String>();
		// Sacamos todos los envios data del día para saber si los que están
		// por debajo del límite ya se envío el correo
		ArrayList<String> listaEnvioDataStock = servicioProcesos.getListaEnvio(getProceso());
		for (String avisoStock : listaAvisoStock) {
			boolean enviar = true;
			String[] str = avisoStock.split("-");
			for (String envioDataStock : listaEnvioDataStock) {
				String aviso = str[0] + "-" + str[1];
				if (aviso.equals(envioDataStock)) {
					enviar = false;
				}
			}
			if (enviar) {
				listaAvisoStockEnviar.add(avisoStock);
			}
		}
		return listaAvisoStockEnviar;
	}

	private ArrayList<String> obtenerAvisoStock(List<StockResultadosBean> listaStock) {
		Long stockMaterialMinimoE = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_E));
		Long stockMaterialMinimoB = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_B));
		Long stockMaterialMinimoC = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_C));
		Long stockMaterialMinimo0 = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_0));
		Long stockMaterialMinimoLO = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_LO));
		Long stockMaterialMinimoLE = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_LE));
		Long stockMaterialMinimoBMT = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_BMT));
		Long stockMaterialMinimoCMT = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_CMT));
		Long stockMaterialMinimoPC = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_PERMISO_CIRCULACION));

		ArrayList<String> avisoStock = new ArrayList<String>();
		for (StockResultadosBean stock : listaStock) {
			if (TipoDistintivo.ECO.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoE) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.B.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoB) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.C.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoC) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.CERO.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimo0) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.CEROMT.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoLO) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.ECOMT.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoLE) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.BMT.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoBMT) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.CMT.getValorEnum().equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoCMT) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			} else if (TIPO_PERMISO_CIRCULACION.equals(stock.getTipo())) {
				if (stock.getUnidades() < stockMaterialMinimoPC) {
					avisoStock.add(stock.getIdJefatura() + "-" + stock.getTipo() + "-" + stock.getUnidades());
				}
			}
		}
		return avisoStock;
	}

	private void envioData(ArrayList<String> listaAvisoStockEnviar) {
		for (String avisoStock : listaAvisoStockEnviar) {
			String[] aviso = avisoStock.split("-");
			String respuesta = aviso[0] + "-" + aviso[1];
			String correcta = ConstantesProcesos.EJECUCION_ENVIO_STOCK + " " + respuesta;
			servicioProcesos.guardarEnvioData(getProceso(), respuesta, correcta, "0");
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.ENVIO_STOCK_PRM_DST.getNombreEnum();
	}
	
	private ArrayList<String> obtenerAvisoStock2(List<StockMaterialBean> listaStock) {
		Long stockMaterialMinimoE = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_E));
		Long stockMaterialMinimoB = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_B));
		Long stockMaterialMinimoC = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_C));
		Long stockMaterialMinimo0 = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_0));
		Long stockMaterialMinimoLO = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_LO));
		Long stockMaterialMinimoLE = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_LE));
		Long stockMaterialMinimoBMT = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_BMT));
		Long stockMaterialMinimoCMT = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_CMT));
		Long stockMaterialMinimoPC = new Long(gestorPropiedades.valorPropertie(STOCK_MATERIAL_MINIMO_PERMISO_CIRCULACION));

		ArrayList<String> avisoStock = new ArrayList<String>();
		for (StockMaterialBean stock : listaStock) {
			if (TipoDistintivo.ECO.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoE) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.B.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoB) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.C.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoC) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.CERO.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimo0) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.CEROMT.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoLO) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.ECOMT.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoLE) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.BMT.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoBMT) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TipoDistintivo.CMT.getValorEnum().equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoCMT) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			} else if (TIPO_PERMISO_CIRCULACION.equals(stock.getTipoMaterial())) {
				if (Long.parseLong(stock.getUnidades()) < stockMaterialMinimoPC) {
					avisoStock.add(stock.getJefatura() + "-" + stock.getTipoMaterial() + "-" + stock.getUnidades());
				}
			}
		}
		return avisoStock;
	}
	
	
	
}