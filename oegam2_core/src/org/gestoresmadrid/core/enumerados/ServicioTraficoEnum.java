package org.gestoresmadrid.core.enumerados;

/**
 * Enumerado de tipos de servicios para vehiculos
 * @author David Sierra
 *
 */

public enum ServicioTraficoEnum {
	
	/* Enumeraciones */
	PUBL_SIN_ESPECIFICAR("A00", "PUBL-Sin Especificar"),
	PUBL_ALQUILER_SIN_CONDUCTOR("A01", "PUBL-Alquiler sin conductor"),
	PUBL_ALQUILER_CON_CONDUCTOR("A02", "PUBL-Alquiler con conductor"),
	PUBL_APRENDIZAJE_DE_CONDUCCION("A03", "PUBL-Aprendizaje de conducción"),
	PUBL_TAXI("A04", "PUBL-Taxi"),
	PUBL_AUXILIO_EN_CARRETERA("A05", "PUBL-Auxilio en carretera"),
	PUBL_AMBULANCIA("A07", "PUBL-Ambulancia"),
	PUBL_FUNERARIO("A08", "PUBL-Funerario"),
	PUBL_OBRAS("A09", "PUBL-Obras"),
	PUBL_MERCANCIAS_PELIGROSAS("A10", "PUBL-Mercancías peligrosas"),
	PUBL_BASURERO("A11", "PUBL-Basurero"),
	PUBL_TRANSPORTE_ESCOLAR("A12", "PUBL-Transporte escolar"),
	PUBL_POLICIA("A13", "PUBL-Policía"),
	PUBL_BOMBEROS("A14", "PUBL-Bomberos"),
	PUBL_PROTECCION_CIVIL_Y_SALVAMENTO("A15", "PUBL-Protección civil y salvamento"),
	PUBL_DEFENSA("A16", "PUBL-Defensa"),
	PUBL_ACTIVIDAD_ECONOMICA("A18", "PUBL-Actividad económica"),
	PUBL_MERCANCIAS_PERECEDERAS("A20", "PUBL_Mercancías perecederas"),
	PART_SIN_ESPECIFICAR("B00", "PART-Sin Especificar"),
	PART_APRENDIZAJE_DE_CONDUCCION("B03", "PART-Aprendizaje de conducción"),
	PART_AGRICOLA("B06", "PART-Agrícola"),
	PART_AMBULANCIAS("B07", "PART-Ambulancias"),
	PART_OBRAS("B09", "PART-Obras"),
	PART_VIVIENDA("B17", "PART-Vivienda"),
	PART_ACTIVIDAD_ECONOMICA("B18", "PART-Actividad económica"),
	PART_RECREATIVO("B19", "PART-Recreativo"),
	PART_VEHICULO_PARA_FERIAS("B21", "PART-Vehículo para ferias");
	
	
	/* Atributos */
	private String valorEnum;
	private String nombreEnum;
	
	/* Constructor */
	private ServicioTraficoEnum(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	/* Getters y setters */
	public String getValorEnum() {
		return valorEnum;
	}
	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}
	public String getNombreEnum() {
		return nombreEnum;
	}
	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	
	public static ServicioTraficoEnum convertir(String valorEnum) {
		for (ServicioTraficoEnum element : ServicioTraficoEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (ServicioTraficoEnum element : ServicioTraficoEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

}
