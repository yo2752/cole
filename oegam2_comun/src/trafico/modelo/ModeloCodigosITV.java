package trafico.modelo;

import java.math.BigDecimal;

import org.displaytag.properties.SortOrderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.daos.BeanPQFicheroCargaCodigosITV;
import trafico.beans.daos.BeanPQFicheroEliminarCodigosITV;
import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;

/**
 * Esta será una clase que contendrá todos los métodos necesarios para la gestión de los códigos ITV 
 * en el subsistema de tráfico.
 * @author ammiguez
 *
 */
public class ModeloCodigosITV extends ModeloBasePQ{

	@Autowired
	ValoresSchemas valoresSchemas;

	public ModeloCodigosITV() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public ResultBean insertarCodigosITV(String nombreFicheroInsertar){
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = "";
		BeanPQFicheroCargaCodigosITV beanCodigosITV = new BeanPQFicheroCargaCodigosITV();
		beanCodigosITV.setP_NOMITV(nombreFicheroInsertar);

		//Realizo la llamada
		RespuestaGenerica resultadoBD = 
			ejecutarProc(beanCodigosITV,valoresSchemas.getSchema(),ValoresCatalog.PQ_CODITV,"CARGA",BeanPQGeneral.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultadoBD.getParametro(ConstantesPQ.P_CODE);
		if(!"0".equals(pCodeTramite.toString())){
			mensaje += resultadoBD.getParametro(ConstantesPQ.P_SQLERRM);
			error = true;
		}

		//Rellenamos los datos de resultados y lo devolvemos.
		resultado.setError(error);
		resultado.setMensaje(mensaje);

		return resultado;
	}

	public ResultBean eliminarCodigosITV(String nombreFicheroEliminar){
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = "";
		BeanPQFicheroEliminarCodigosITV beanCodigosITV = new BeanPQFicheroEliminarCodigosITV();
		beanCodigosITV.setP_NOMITV(nombreFicheroEliminar);

		//Realizo la llamada
		RespuestaGenerica resultadoBD = 
			ejecutarProc(beanCodigosITV,valoresSchemas.getSchema(),ValoresCatalog.PQ_CODITV,"ELIMINAR",BeanPQGeneral.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)resultadoBD.getParametro(ConstantesPQ.P_CODE);
		if(!"0".equals(pCodeTramite.toString())){
			mensaje += resultadoBD.getParametro(ConstantesPQ.P_SQLERRM);
			error = true;
		}

		//Rellenamos los datos de resultados y lo devolvemos.
		resultado.setError(error);
		resultado.setMensaje(mensaje);

		return resultado;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

}