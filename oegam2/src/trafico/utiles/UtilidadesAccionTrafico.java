package trafico.utiles;

import org.displaytag.pagination.PaginatedList;

import escrituras.utiles.UtilesVista;
import general.accionTramite.beans.paginacion.BeanCriteriosAccionTramite;
import general.accionTramite.factoria.FactoriaAccionTramite;
import general.accionTramite.modelo.paginacion.ModeloBuscadorAccionTramite;
import general.acciones.PaginatedListActionSkeleton;
import general.modelo.ModeloSkeletonPaginatedList;
import utilidades.web.OegamExcepcion;

public class UtilidadesAccionTrafico {

	private static UtilidadesAccionTrafico utilidadesAccionTrafico = null;
	
	public static UtilidadesAccionTrafico getInstance(){
		if (utilidadesAccionTrafico==null){
			utilidadesAccionTrafico = new UtilidadesAccionTrafico();
		}
		return utilidadesAccionTrafico;
	}
	
	private UtilidadesAccionTrafico(){}
	
	public PaginatedList generarListaAcciones(String numExpediente) throws NumberFormatException, OegamExcepcion {
		FactoriaAccionTramite factoria = new FactoriaAccionTramite();
		ModeloSkeletonPaginatedList modeloAcciones = new ModeloBuscadorAccionTramite(factoria);
		BeanCriteriosAccionTramite criterios = (BeanCriteriosAccionTramite) factoria.crearCriterios();
		criterios.setNumExpediente(numExpediente);
		return modeloAcciones.buscarPag(criterios, Integer.parseInt(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO), 1, PaginatedListActionSkeleton.ORDEN_DESC, "id.numExpediente");
	}
}
