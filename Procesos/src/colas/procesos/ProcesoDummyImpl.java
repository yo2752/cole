package colas.procesos;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaTramiteTrafico;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.procesos.utiles.UtilesProcesos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoDummyImpl extends ProcesoBase implements ProcesoDummy {
	
	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoDummy.class);
	private UtilesProcesos utilesProcesos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	ServicioPersistenciaTramiteTrafico servicioPersistenciaTramiteTrafico;
	
	public ProcesoDummyImpl(ColaBean solicitud, Class<?> requesterClass, JobExecutionContext jobExecutionContext) throws Throwable {
		
		/* Se inicializa log con la clase del proceso que llama */
		log = LoggerOegam.getLogger(requesterClass!=null ? requesterClass : ProcesoBase.class);
		
		/* Dummy user check begins */
		String nombreProceso = requesterClass!=null && requesterClass.getSimpleName()!=null ? requesterClass.getSimpleName().toLowerCase().replaceFirst("proceso", "") : PROCESO_BASE;
		boolean dummy = checkDummyUser(Long.valueOf(String.valueOf(solicitud.getIdTramite())), nombreProceso);
		if(dummy){
			String dummyMessage = getDummyMessage(nombreProceso);
			String dummyFin = getTipoFinalizacion(nombreProceso);
			if(dummyFin!=null && dummyFin.equals(FINALIZADO_CON_ERROR) && dummyMessage!=null){
				finalizarTransaccionConErrorNoRecuperable(solicitud, dummyMessage, jobExecutionContext);
				throw new Throwable(dummyMessage);
			} else {
				throw new Throwable();
			}
		}
		/* Dummy user check ends */
		
	}
	
	public boolean checkDummyUser(Long numExpediente, String proceso){
		TramiteTraficoVO tramite = servicioPersistenciaTramiteTrafico.getTramite(new BigDecimal(numExpediente), Boolean.FALSE);
		String numColegiado = (tramite!=null && tramite.getNumColegiado()!=null) ? tramite.getNumColegiado() : "";
		String dummyUsers = (gestorPropiedades.valorPropertie(proceso + ".dummy.users")!=null ? gestorPropiedades.valorPropertie(proceso + ".dummy.users") : "");
		String[] dummyUsersArray = (String[]) dummyUsers.split(",");
		for(String dummy : dummyUsersArray) {
			if(dummy!=null && !"".equals(dummy) && dummy.equals(numColegiado)){
				return true;
			}
		}
		return false;
	}
	
	public String getDummyMessage(String proceso) {
		return (gestorPropiedades.valorPropertie(proceso + ".dummy.message")!=null ? gestorPropiedades.valorPropertie(proceso + ".dummy.message") : null);
	}

	public String getTipoFinalizacion(String proceso) {
		String dummyFin = null;
		String finalizacion = (gestorPropiedades.valorPropertie(proceso + ".dummy.fin")!=null ? gestorPropiedades.valorPropertie(proceso + ".dummy.fin") : null);
		if(finalizacion!=null){
			switch(Integer.valueOf(finalizacion)){
				case 1:
					dummyFin = ERROR_SERVICIO;
				break;
				case 2:
					dummyFin = FINALIZADO_CON_ERROR;
				break;
				default:
					dummyFin = ERROR_SERVICIO;
				break;
			}
		} else{
			return null;
		}
		return dummyFin;
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public UtilesProcesos getUtilesProcesos() {
		if (utilesProcesos == null) {
			utilesProcesos = new UtilesProcesos();
		}
		return utilesProcesos;
	}

	public void setUtilesProcesos(UtilesProcesos utilesProcesos) {
		this.utilesProcesos = utilesProcesos;
	}
}
