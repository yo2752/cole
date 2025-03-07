package general.modelo;

import general.beans.daos.pq_accesos.BeanPQNOTIFICACION;

import java.math.BigDecimal;

public class ModeloNotificaciones {

	public static BigDecimal notificar(BigDecimal idUsuario){
		
		BeanPQNOTIFICACION notificacion = new BeanPQNOTIFICACION();		
		notificacion.setP_ID_USUARIO(idUsuario);	
		notificacion.execute();
		
		return (BigDecimal)notificacion.getP_RENOVAR();	
	}
	
}
