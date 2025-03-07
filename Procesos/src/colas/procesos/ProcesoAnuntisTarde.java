package colas.procesos;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * Proceso anuntis tarde
 * @author 1
 *
 */
public class ProcesoAnuntisTarde extends ProcesoBase {

	/* INICIO MÉTODOS */
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		ProcesoAVPOAnuntis  pat = new ProcesoAVPOAnuntis();
		
		pat.execute(jobExecutionContext);

	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		
	}

}