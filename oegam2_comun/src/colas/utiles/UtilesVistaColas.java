package colas.utiles;

import java.util.Arrays;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaColas {
	
	private static UtilesVistaColas utilesVistaColas;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaColas.class);
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public static UtilesVistaColas getInstance() {
		if (utilesVistaColas == null) {
			utilesVistaColas = new UtilesVistaColas();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaColas);
		}
		return utilesVistaColas;
	}
	
	private ProcesosEnum[] listaProcesosEnum;
	private EstadoPeticiones[] listaEstadoPeticionesEnum;

	public EstadoPeticiones[] getComboEstados() {
		if(listaEstadoPeticionesEnum == null) {
			listaEstadoPeticionesEnum = EstadoPeticiones.values();
		}
		return listaEstadoPeticionesEnum;
    }
	
	public ProcesosEnum[] getComboProcesos() {
		ProcesosEnum[] listaProcesosEnumAux = null;
		if(listaProcesosEnum == null) {
			listaProcesosEnumAux = ProcesosEnum.values();
			String[] nombres = new String[listaProcesosEnumAux.length];
			for(int i = 0; i < listaProcesosEnumAux.length; i ++) {
				nombres[i] = listaProcesosEnumAux[i].getNombreEnum();
			}
			Arrays.sort(nombres);
			listaProcesosEnum = new ProcesosEnum[listaProcesosEnumAux.length];
			for(int i = 0; i < nombres.length; i ++) {
				for(int j = 0; j < listaProcesosEnumAux.length; j ++) {
					if(nombres[i].equals(listaProcesosEnumAux[j].getNombreEnum())) {
						listaProcesosEnum[i] = listaProcesosEnumAux[j];
					}
				}
			}
		}
		return listaProcesosEnum;
    }
	
	public DatoMaestroBean[] getNumColas(){
		String numColas = gestorPropiedades.valorPropertie("numero.colas.procesos");
		DatoMaestroBean[] numsColas = null;
		if(numColas != null && !numColas.isEmpty()){
			try {
				Integer intNumColas = Integer.parseInt(numColas);
				numsColas = new DatoMaestroBean[intNumColas];
				int contador = 1;
				for(int i = 0;i<intNumColas;i++){
					numsColas[i] = new DatoMaestroBean(String.valueOf(contador),String.valueOf(contador));
					contador++;
				}
				return numsColas;
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de parsear el numero de colas de la BBDD, error: ",e);
			}
		}
		return  new DatoMaestroBean[0];
	}
}
