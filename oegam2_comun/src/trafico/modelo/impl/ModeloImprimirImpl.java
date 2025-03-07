package trafico.modelo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import colas.modelo.ModeloSolicitud;
import escrituras.beans.ResultBean;
import hibernate.dao.trafico.TramiteTraficoDAO;
import oegam.constantes.ConstantesPQ;
import procesos.daos.BeanPQCrearSolicitud;
import trafico.modelo.interfaz.ModeloImprimirInterface;
import utilidades.web.OegamExcepcion;

public class ModeloImprimirImpl implements ModeloImprimirInterface {

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloImprimirImpl() {
	}

	@Override
	public ResultBean crearSolicitud(String nombreFichero, String tipoTramiteTrafico, String idTramite) throws OegamExcepcion {
		BeanPQCrearSolicitud solicitudBean = new BeanPQCrearSolicitud();

		solicitudBean.setP_PROCESO(ProcesosEnum.IMPRIMIRTRAMITES.getNombreEnum());
		solicitudBean.setP_TIPO_TRAMITE(tipoTramiteTrafico);
		solicitudBean.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		solicitudBean.setP_XML_ENVIAR(nombreFichero + ".txt");
		solicitudBean.setP_ID_TRAMITE(new BigDecimal(idTramite));

		HashMap<String,Object> resul = new ModeloSolicitud().crearSolicitudProcesos2(solicitudBean);
		return (ResultBean) resul.get(ConstantesPQ.RESULTBEAN);
	}

	/**
	 * Método que comprueba si los expedientes son del mismo tipo. Debería ir a un modelo más genérico, o en utilidades.
	 * @param numExpedientes
	 * @return
	 */
	public TipoTramiteTrafico recuperarTipoTramiteSiEsElMismo(Long[] numExpedientes) {
		ProjectionList listaProyecciones =  Projections.projectionList();
		listaProyecciones.add(Projections.distinct(Projections.property("tipoTramite")));
		List<Criterion> criterionList= new ArrayList<>();
		criterionList.add(Restrictions.in("numExpediente", numExpedientes));
		List<?> listaTipo = new TramiteTraficoDAO().buscarPorCriteria(criterionList, null, listaProyecciones);
		if (listaTipo==null || listaTipo.size()!=1){
			return null;
		}
		return TipoTramiteTrafico.convertir((String)listaTipo.get(0));
	}

	/**************** Fin de métodos para validar antes de Imprimir Trámites ************************/
}
