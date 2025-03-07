package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum SituacionJuridica {

	TD("Titularidad dominical del arrendador financiero", "1"),
	RD("Reserva de dominio", "0"),
	PD("Prohibición de disponer", "1");

	private String nombreEnum;
	private String activo;

	private SituacionJuridica(String nombreEnum, String activo) {
		this.nombreEnum = nombreEnum;
		this.activo = activo;
	}

	@Override
	public String toString() {
		return nombreEnum;
		
	}
	
	public String getKey(){
		return name();
	}
	
	public static String convertirTexto(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(SituacionJuridica situacionJuridica : SituacionJuridica.values()){
				if(situacionJuridica.getKey().equals(valor)){
					return situacionJuridica.toString();
				}
			}
		}
		return null;
	}

	/**
	 * @return the nombreEnum
	 */
	public String getNombreEnum() {
		return nombreEnum;
	}

	/**
	 * @param nombreEnum the nombreEnum to set
	 */
	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	/**
	 * @return the activo
	 */
	public String getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(String activo) {
		this.activo = activo;
	}

}
