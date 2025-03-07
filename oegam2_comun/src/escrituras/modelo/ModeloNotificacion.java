package escrituras.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.general.model.vo.NotificacionVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.dao.NotificacionDao;
import general.beans.RespuestaGenerica;
import general.beans.daos.pq_accesos.BeanPQNOTIFICACION;
import general.modelo.ModeloBasePQ;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import oegam.constantes.ValoresSchemas;
import trafico.beans.daos.BeanPQTramiteBuscarResult;
import trafico.beans.daos.pq_notificacion.BeanPQBUSCAR;
import trafico.beans.daos.pq_notificacion.BeanPQELIMINAR;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;

public class ModeloNotificacion extends ModeloBasePQ{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloNotificacion.class);

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioNotificacion servicioNotificacion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloNotificacion() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina,
			SortOrderEnum orden, String columnaOrden) {

		ListaRegistros listaRegistros = new ListaRegistros();

		RespuestaGenerica respuesta = null;

		try{
			BigDecimal idUsuario = (BigDecimal)getParametrosBusqueda().get(ConstantesSession.ID_USUARIO);

			BeanPQBUSCAR beanPQBUSCAR = new BeanPQBUSCAR();
			beanPQBUSCAR.setP_ID_USUARIO(idUsuario!=null ?idUsuario:null);
			beanPQBUSCAR.setPAGINA(utiles.convertirIntegerABigDecimal(pagina));
			beanPQBUSCAR.setNUM_REG(utiles.convertirIntegerABigDecimal(numeroElementosPagina));
			beanPQBUSCAR.setCOLUMNA_ORDEN(columnaOrden);
			beanPQBUSCAR.setORDEN(orden.getName().toUpperCase());

			respuesta = ejecutarProc(beanPQBUSCAR, valoresSchemas.getSchema(), ValoresCatalog.PQ_NOTIFICACION, "BUSCAR", NotificacionDao.class);

			//Recuperamos información de respuesta
			log.debug(ConstantesPQ.LOG_P_CODE + (BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE));
			log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM));
			log.debug(ConstantesPQ.LOG_P_CUENTA + respuesta.getParametro("CUENTA"));

			List<Object> listaCursor = respuesta.getListaCursor();
			List<Object> listaBeanVista = new ArrayList<Object>(); 

			for (Object notificacion : listaCursor) {
				NotificacionDao notificacionAux = (NotificacionDao) notificacion;
				listaBeanVista.add(notificacionAux);
				// Mantis 18211. David Sierra: Listado Notificaciones. Se muestran las notificaciones
				// de todos los colegiados solo al usuario con permiso de administrador
				String numColegiado = utilesColegiado.getNumColegiadoSession();
				String token = null;
				if (notificacionAux.getId_Tramite().toString().length() >= 15) {
					token = notificacionAux.getId_Tramite().toString().substring(0,4);;
				}

				if("2056".equals(numColegiado) && !utilesColegiado.tienePermisoAdmin() &&
						token!=null && !token.equals(numColegiado)) {
					listaBeanVista.remove(notificacionAux);
				}

			}

			BigDecimal tamRegs = (BigDecimal) respuesta.getParametro("CUENTA");//listaCursor.size();
			listaRegistros.setTamano(utiles.convertirBigDecimalAInteger(tamRegs));

			listaRegistros.setLista(listaBeanVista);

			return listaRegistros;

		} catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR+e.getMessage());
			//e.printStackTrace();
			return listaRegistros;
		}

	}

	public String eliminar(BigDecimal idNotificacion){

		RespuestaGenerica respuesta = null;

		try{
			BeanPQELIMINAR beanPQELIMINAR = new BeanPQELIMINAR();
			beanPQELIMINAR.setP_ID_NOTIFICACION(idNotificacion);

			//LLAMAMOS AL PROCEDIMIENTO ALMACENADO DE FORMA GENERICA
			respuesta = ejecutarProc(beanPQELIMINAR,valoresSchemas.getSchema(),ValoresCatalog.PQ_NOTIFICACION,"ELIMINAR",BeanPQTramiteBuscarResult.class);

			log.debug(ConstantesPQ.LOG_P_CODE + (BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE));
			log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM));
			log.debug(ConstantesPQ.LOG_P_CUENTA + respuesta.getParametro("CUENTA"));

			if(((BigDecimal)respuesta.getParametro(ConstantesPQ.P_CODE) !=null)){
				return (String)respuesta.getParametro(ConstantesPQ.P_SQLERRM);
			}else{
				return "ERROR";
			}
		} catch (Throwable e) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR+e.getMessage());
			return null;
		}
	}

	public BigDecimal notificar(BigDecimal idUsuario){
		BeanPQNOTIFICACION notificacion = new BeanPQNOTIFICACION();
		notificacion.setP_ID_USUARIO(idUsuario);
		notificacion.execute();

		return (BigDecimal)notificacion.getP_RENOVAR();
	}

	public NotificacionDao  buscarByIdTramite(String idNotificacion){
		NotificacionDao notificacionDao=new NotificacionDao();

		NotificacionVO notificacion = servicioNotificacion.getNotificacionById(Long.parseLong(idNotificacion));
		if(null != notificacion) {
			notificacionDao.setId_Notificacion(new BigDecimal(notificacion.getIdNotificacion()));
			notificacionDao.setId_Tramite(notificacion.getIdTramite());
			return notificacionDao;
		}
		return null;
	}

}