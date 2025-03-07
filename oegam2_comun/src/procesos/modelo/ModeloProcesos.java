package procesos.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import procesos.beans.ProcesoBean;
import procesos.daos.BeanPQTomarProcesos;
import procesos.daos.ProcesosCursor;
import procesos.enumerados.EstadoProcesoEnum;
import trafico.beans.daos.pq_proceso.BeanPQCAMBIAR_PROCESOS;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloProcesos extends ModeloBasePQ {

	private static final String FIN_GRABAR_CONFIGURACION_EN_BBDD = "Fin grabarConfiguracionEnBBDD";
	private static final String GRABAR_CONFIGURACION_EN_BBDD = "grabarConfiguracionEnBBDD";
	private static final String INFO_DE_PROCESO_ALMACENADA = "info de proceso almacenada";

	private static final String MENSAJE_TIEMPO_CORRECTO = "TIEMPO_CORRECTO";
	private static final String MENSAJE_TIEMPO_ERRONEO_RECUPERABLE="TIEMPO_ERRONEO_RECUPERABLE"; 
	private static final String MENSAJE_TIEMPO_ERRONEO_NO_RECUPERABLE="TIEMPO_ERRONEO_NO_RECUPERABLE";
	private static final String MENSAJE_TIEMPO_SIN_DATOS="TIEMPO_SIN_DATOS";
	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloProcesos.class);

	private static final String NOMBRE_HOST_PROCESO= "nombreHostProceso";

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	Utiles utiles;
	
	@Autowired
	ServicioProcesos servicioProcesos;

	public HashMap <String, Object> tomarProcesos (BeanPQTomarProcesos beanPQTomarProcesos){

		ListaRegistros listaRegistros = new ListaRegistros();

		ResultBean resultBean = new ResultBean(); 
		resultBean.setError(false); 
		beanPQTomarProcesos.setP_NODO(getNodoProceso()); 

		//Realiza la llamada genérica para la consulta
		RespuestaGenerica resultadoProcesos = 
				ejecutarProc(beanPQTomarProcesos,valoresSchemas.getSchema(),ValoresCatalog.PQ_PROCESO,"TOMAR_PROCESOS",ProcesosCursor.class);		

		//Recuperamos información de respuesta

		BigDecimal pCodeProcesos = (BigDecimal)resultadoProcesos.getParametro(ConstantesPQ.P_CODE);
		log.info(ConstantesPQ.LOG_P_CODE + pCodeProcesos);
		log.info(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoProcesos.getParametro(ConstantesPQ.P_SQLERRM));
		log.info(ConstantesPQ.LOG_P_CUENTA + resultadoProcesos.getParametro("CUENTA"));


		if (pCodeProcesos.equals(ConstantesPQ.pCodeOk))
		{
			resultBean.setMensaje((String)resultadoProcesos.getParametro(ConstantesPQ.P_SQLERRM)); 
			resultBean.setError(false); 

			List<Object> listaCursor = resultadoProcesos.getListaCursor();
			listaRegistros.setLista(listaCursor);
			listaRegistros.setTamano(listaCursor.size());
		}
		else
		{
			resultBean.setError(true); 
			resultBean.setMensaje((String)resultadoProcesos.getParametro(ConstantesPQ.P_SQLERRM)); 
		}

		HashMap<String,Object> resultadoTomarProcesos = new HashMap<String, Object>();  
		resultadoTomarProcesos.put(ConstantesPQ.BEANPANTALLA, listaRegistros); 
		resultadoTomarProcesos.put(ConstantesPQ.RESULTBEAN, resultBean); 

		return resultadoTomarProcesos;

	}


	public void grabarConfiguracionEnBBDD(ProcesoBean[] lista){

		log.info(GRABAR_CONFIGURACION_EN_BBDD); 

		for (ProcesoBean proceso : lista) 
		{ 
			guardarProceso(proceso); 
		}

		log.info(FIN_GRABAR_CONFIGURACION_EN_BBDD); 
	}

	public void guardarProceso(ProcesoBean proceso) {
		log.info("guardar proceso");
		BeanPQCAMBIAR_PROCESOS beanPQCAMBIARPROCESOS = new BeanPQCAMBIAR_PROCESOS(); 

		beanPQCAMBIARPROCESOS.setP_NODO(getNodoProceso()); 

		beanPQCAMBIARPROCESOS = procesoABeanPQ(proceso, beanPQCAMBIARPROCESOS);

		beanPQCAMBIARPROCESOS.execute();

		if (beanPQCAMBIARPROCESOS.getP_CODE().equals(ConstantesPQ.pCodeOk)) 
			log.info(beanPQCAMBIARPROCESOS.getP_PROCESO() +" " + INFO_DE_PROCESO_ALMACENADA); 
		else {log.error(beanPQCAMBIARPROCESOS.getP_PROCESO()); 
		log.error(beanPQCAMBIARPROCESOS.getP_SQLERRM());
		}


		//proceso.setClase(beanPQCAMBIARPROCESOS.getP_CLASE().toString()); 
		proceso.setDescripcion(beanPQCAMBIARPROCESOS.getP_DESCRIPCION()); 
		//proceso.setEstadoInstancias(beanPQCAMBIARPROCESOS.getP_ESTADO()); 
		proceso.setHoraFin(beanPQCAMBIARPROCESOS.getP_HORA_FIN()); 
		proceso.setHoraInicio(beanPQCAMBIARPROCESOS.getP_HORA_INICIO());
		proceso.setIntentosMax(beanPQCAMBIARPROCESOS.getP_N_INTENTOS_MAX()); 
		proceso.setNodo(beanPQCAMBIARPROCESOS.getP_NODO());
		proceso.setNombre(beanPQCAMBIARPROCESOS.getP_PROCESO()); 
		proceso.setNumero(beanPQCAMBIARPROCESOS.getP_HILOS_COLAS().intValue());
		//proceso.setNumActivos(beanPQCAMBIARPROCESOS.get)
		proceso.setTiempoCorrecto(beanPQCAMBIARPROCESOS.getP_TIEMPO_CORRECTO()); 
		proceso.setTiempoNoRecuperable(beanPQCAMBIARPROCESOS.getP_TIEMPO_ERR_NO_RECUPERABLE()); 
		proceso.setTiempoRecuperable(beanPQCAMBIARPROCESOS.getP_TIEMPO_ERRONEO_RECUPERABLE());
		proceso.setTiempoSinDatos(beanPQCAMBIARPROCESOS.getP_TIEMPO_SIN_DATOS()); 

	}


	private BeanPQCAMBIAR_PROCESOS procesoABeanPQ(ProcesoBean proceso,
			BeanPQCAMBIAR_PROCESOS beanPQCAMBIARPROCESOS) {


		beanPQCAMBIARPROCESOS.setP_NODO(getNodoProceso()); 
		beanPQCAMBIARPROCESOS.setP_PROCESO(proceso.getNombre()); 
		beanPQCAMBIARPROCESOS.setP_TIPO(new BigDecimal(proceso.getTipo()));
		beanPQCAMBIARPROCESOS.setP_HILOS_COLAS(new BigDecimal(proceso.getNumero())); 
		beanPQCAMBIARPROCESOS.setP_CLASE(proceso.getClase().getCanonicalName()); 
		beanPQCAMBIARPROCESOS.setP_DESCRIPCION(proceso.getDescripcion());


		if (proceso.isActivo())
			beanPQCAMBIARPROCESOS.setP_ESTADO(new BigDecimal(1)); 
		else beanPQCAMBIARPROCESOS.setP_ESTADO(new BigDecimal(0)); 

		beanPQCAMBIARPROCESOS.setP_HORA_INICIO(proceso.getHoraInicio());
		beanPQCAMBIARPROCESOS.setP_HORA_FIN(proceso.getHoraFin());
		//	beanPQCAMBIARPROCESOS.setP_INTERVALO(proceso.getIntervalo()); 
		beanPQCAMBIARPROCESOS.setP_N_INTENTOS_MAX(proceso.getIntentosMax()); 

		beanPQCAMBIARPROCESOS.setP_TIEMPO_CORRECTO(proceso.getTiempoCorrecto()); 
		beanPQCAMBIARPROCESOS.setP_TIEMPO_ERR_NO_RECUPERABLE(proceso.getTiempoNoRecuperable()); 
		beanPQCAMBIARPROCESOS.setP_TIEMPO_ERRONEO_RECUPERABLE(proceso.getTiempoRecuperable());
		beanPQCAMBIARPROCESOS.setP_TIEMPO_SIN_DATOS(proceso.getTiempoSinDatos());

		return beanPQCAMBIARPROCESOS; 
	}


	// Obtiene la configuración de los procesos desde la bd y no desde procesos.xml:
	public ArrayList<ProcesoBean> obtenerConfiguracionDesdeBD() {

		ArrayList<ProcesoBean> listaProcesos = new ArrayList<ProcesoBean>();
		try {
			BeanPQTomarProcesos beanPQTomarProcesos = new BeanPQTomarProcesos();
			HashMap<String,Object> resultadoTomarProcesos = tomarProcesos(beanPQTomarProcesos);
			ListaRegistros listaRegistros = (ListaRegistros) resultadoTomarProcesos.get(ConstantesPQ.BEANPANTALLA); 
			ResultBean resultBean = (ResultBean) resultadoTomarProcesos.get(ConstantesPQ.RESULTBEAN);
			if(resultBean.getError()){
				throw new Exception();
			}else{
				List<Object> lista = listaRegistros.getLista();
				for(Object temp:lista){
					ProcesoBean procesoBean = null;
					ProcesosCursor procesosCursor = null;
					try{
						procesosCursor = (ProcesosCursor)temp;
						procesoBean = new ProcesoBean();
						Integer estado = utiles.convertirBigDecimalAInteger((BigDecimal)procesosCursor.getESTADO());
						if(estado == EstadoProcesoEnum.ACTIVO.getValorEnum()){
							procesoBean.setEstadoInstancias(EstadoProcesoEnum.ACTIVO.getNombreEnum());
							procesoBean.setActivo(true);
						}else if(estado == EstadoProcesoEnum.PARADO.getValorEnum()){
							procesoBean.setEstadoInstancias(EstadoProcesoEnum.PARADO.getNombreEnum());
							procesoBean.setActivo(false);
						}
						procesoBean.setIntentosMax(procesosCursor.getN_INTENTOS_MAX());
						procesoBean.setNombre((String)procesosCursor.getPROCESO());
						Integer numero = utiles.convertirBigDecimalAInteger((BigDecimal)procesosCursor.getHILOS_COLAS());
						procesoBean.setNumero(numero);
						procesoBean.setDescripcion((String)procesosCursor.getDESCRIPCION());
						procesoBean.setClase(Class.forName((String)procesosCursor.getCLASE()));
						procesoBean.setTipo((utiles.convertirBigDecimalAInteger((BigDecimal)procesosCursor.getTIPO())));
						procesoBean.setHoraInicio((String)procesosCursor.getHORA_INICIO());
						procesoBean.setHoraFin((String)procesosCursor.getHORA_FIN());
						//procesoBean.setIntervalo((String)procesosCursor.getINTERVALO());
						procesoBean.setOrden((utiles.convertirBigDecimalAInteger((BigDecimal)procesosCursor.getORDEN())));
						procesoBean.setTiempoCorrecto((BigDecimal)procesosCursor.getTIEMPO_CORRECTO()); 
						procesoBean.setTiempoRecuperable((BigDecimal)procesosCursor.getTIEMPO_ERRONEO_RECUPERABLE());
						procesoBean.setTiempoNoRecuperable((BigDecimal)procesosCursor.getTIEMPO_ERRONEO_NO_RECUPERABLE());
						procesoBean.setTiempoSinDatos((BigDecimal)procesosCursor.getTIEMPO_SIN_DATOS());

						compruebaTiemposPorDefecto(procesoBean); 

						listaProcesos.add(procesoBean);
					}catch(Exception ex){
						log.error("No se ha podido configurar correctamente el proceso " + procesosCursor!=null?procesosCursor.getDESCRIPCION():"");
						log.error("Este error implica que no se ha añadido el proceso a la lista");
						log.error(ex);
						continue;
					}
				} 
			}
		} catch (Exception e) {
			log.error("No ha sido posible obtener la lista de procesos a monitorizar. Causa: " + e.getMessage());
		}
		return listaProcesos;
	}

	private ProcesoBean compruebaTiemposPorDefecto(ProcesoBean procesoBean) {

		if (procesoBean.getTiempoCorrecto()==null || procesoBean.getTiempoCorrecto().equals(new BigDecimal(0)))
			procesoBean.setTiempoCorrecto(new BigDecimal(getTiempoPorDefecto(MENSAJE_TIEMPO_CORRECTO)));

		if (procesoBean.getTiempoRecuperable()==null || procesoBean.getTiempoRecuperable().equals(new BigDecimal(0)))
			procesoBean.setTiempoRecuperable(new BigDecimal(getTiempoPorDefecto(MENSAJE_TIEMPO_ERRONEO_RECUPERABLE)));

		if (procesoBean.getTiempoNoRecuperable()==null || procesoBean.getTiempoNoRecuperable().equals(new BigDecimal(0)))
			procesoBean.setTiempoNoRecuperable(new BigDecimal(getTiempoPorDefecto(MENSAJE_TIEMPO_ERRONEO_NO_RECUPERABLE)));

		if (procesoBean.getTiempoSinDatos()==null || procesoBean.getTiempoSinDatos().equals(new BigDecimal(0)))
			procesoBean.setTiempoSinDatos(new BigDecimal(getTiempoPorDefecto(MENSAJE_TIEMPO_SIN_DATOS)));


		return procesoBean;
	}

	public  int getTiempoPorDefecto(String mensajeTiempo){
		return Integer.parseInt(gestorPropiedades.valorPropertie(mensajeTiempo));
	}



	public String getNodoProceso(){
		return gestorPropiedades.valorPropertie(NOMBRE_HOST_PROCESO);
	}

	
	public BigDecimal getIntentosMaximos(String nombreProceso, String nodo){
		return servicioProcesos.getIntentosMaximos(nombreProceso, nodo);
	}
	
	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {return null;
	}

}
