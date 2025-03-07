package colas.procesos;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Proceso anuntis de mañana
 * @author 1
 *
 */
public class ProcesoAnuntisManana extends ProcesoBase {

	/* INICIO MÉTODOS */
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		ProcesoAVPOAnuntis pam = new ProcesoAVPOAnuntis();
		
		pam.execute(jobExecutionContext);

	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		
	}

}