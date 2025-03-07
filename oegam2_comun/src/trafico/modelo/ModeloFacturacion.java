package trafico.modelo;

import org.displaytag.properties.SortOrderEnum;

import trafico.beans.daos.pq_facturacion.BeanFACTURACION;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.modelo.ModeloBase;

public class ModeloFacturacion extends ModeloBase{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloFacturacion.class);
	
	public ModeloFacturacion() {
		super();
	}
	
	public static BeanFACTURACION trataMensaje(BeanFACTURACION beanFacturacion){
		
		beanFacturacion.execute(); 
		log.info("P Code : " + beanFacturacion.getP_CODE()); 
		log.info("sqlSERR:" + beanFacturacion.getP_SQLERRM()); 
		
		return beanFacturacion; 
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}
	
}
