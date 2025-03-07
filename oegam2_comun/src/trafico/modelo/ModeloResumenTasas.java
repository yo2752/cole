package trafico.modelo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.modelo.ModeloBase;
import trafico.beans.MarcaBean;
import trafico.beans.daos.BeanPQTasasResumenResultado;
import trafico.beans.daos.pq_tasas.BeanPQRESUMEN;
import utilidades.estructuras.FechaFraccionada;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloResumenTasas extends ModeloBase{
	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloResumenTasas.class);

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloResumenTasas() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public static void main(String[] args) {
		List<Object> listaObject = new ArrayList<Object>();
		MarcaBean marca = new MarcaBean(); 
		listaObject.add(marca); 
	}

	/*
	 *   PROCEDURE RESUMEN  ( P_NUM_COLEGIADO in tramite_trafico.num_colegiado%type,
                      P_ID_CONTRATO in tasa.ID_CONTRATO%type,
                      P_ID_USUARIO in tasa.ID_USUARIO%type,
                      P_TIPO_TASA in tasa.TIPO_TASA%type,
                      P_FECHA_ALTA_DESDE in tasa.FECHA_ALTA%type,
                      P_FECHA_ALTA_HASTA in tasa.FECHA_ALTA%type,
                      P_CODE OUT NUMBER,
                      P_SQLERRM OUT VARCHAR2,
                      C_Tasas OUT SYS_REFCURSOR);
	 */

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina,
			SortOrderEnum orden, String columnaOrden) {
		ListaRegistros listaRegistros = new ListaRegistros();

		String numColegiado= "";
		if(utilesColegiado.tienePermisoColegio() || utilesColegiado.tienePermisoAdmin()){
			numColegiado = (String)getParametrosBusqueda().get("numColegiado");
		}else{
			numColegiado= utilesColegiado.getNumColegiadoSession();
		}

		BeanPQRESUMEN beanResumenTasa = new BeanPQRESUMEN();
		beanResumenTasa.setP_NUM_COLEGIADO(!"".equals(numColegiado)?numColegiado:null);
		beanResumenTasa.setP_ID_CONTRATO(utilesColegiado.getIdContratoSessionBigDecimal());
		beanResumenTasa.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanResumenTasa.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		String tipoTasa = (String)getParametrosBusqueda().get("tipoTasa");
		beanResumenTasa.setP_TIPO_TASA(!"".equals(tipoTasa) && !"-1".equals(tipoTasa)?tipoTasa:null);
		FechaFraccionada fechaAlta = (FechaFraccionada)getParametrosBusqueda().get("fechaAlta");
		Timestamp fechaAltaDesde = null;
		Timestamp fechaAltaHasta = null;
		if(fechaAlta!=null){
			Date fechaInicio = null;
			if((fechaInicio = fechaAlta.getFechaInicio()) != null){
				fechaAltaDesde = utilesFecha.getTimestamp(fechaInicio);
			}

			Date fechaFin = null;
			if((fechaFin = fechaAlta.getFechaFin()) != null){
				fechaAltaHasta = utilesFecha.getTimestamp(fechaFin);
			}
		}
		beanResumenTasa.setP_FECHA_ALTA_DESDE(fechaAltaDesde);
		beanResumenTasa.setP_FECHA_ALTA_HASTA(fechaAltaHasta);

		List<Object> listaCursor = beanResumenTasa.execute(BeanPQTasasResumenResultado.class); 

		//Completamos algunos parámetros para la llamada al procedimiento personalizada
//		HashMap<String,Object> parametrosBusqueda = new HashMap<>();
//		parametrosBusqueda.put(NombresParametrosBusqueda.CATALOG,ValoresCatalog.PQ_TASAS);
//		parametrosBusqueda.put(NombresParametrosBusqueda.SCHEMA,ValoresSchemas.getSchema());
//		parametrosBusqueda.put(NombresParametrosBusqueda.PROCEDURE,"RESUMEN");
//		parametrosBusqueda.put(NombresParametrosBusqueda.CLASEBEANCURSOR, new BeanPQTasasResumenResultado().getClass());

		//LLAMAMOS AL PROCEDIMIENTO ALMACENADO DE FORMA GENERICA
//		HashMap<String,Object> respuesta = ModeloGenerico.ejecutarGenerico(parametrosBusqueda,beanResumenTasa);

		//Recuperamos el mapa de parámetros, y la lista del cursor
//		HashMap<String,Object> mapaParametros = (HashMap<String,Object>)respuesta.get("mapaParametros");
//		List<Object> listaCursor = (List<Object>)respuesta.get("listaCursor");

		int tamRegs = listaCursor.size();
		listaRegistros.setTamano(tamRegs);

		List <BeanPQTasasResumenResultado> regsAux = new ArrayList<>();

		for (Object reg : listaCursor) {
			BeanPQTasasResumenResultado regAux = (BeanPQTasasResumenResultado) reg;
			regsAux.add(regAux);
		}

		listaCursor = new ArrayList<Object>();
		listaCursor.addAll(regsAux);

		listaRegistros.setLista(listaCursor);

		return listaRegistros;
	}
}