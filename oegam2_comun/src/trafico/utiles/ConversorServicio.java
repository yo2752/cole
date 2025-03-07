package trafico.utiles;

import trafico.beans.jaxb.matriculacion.TipoServicio;

/**
 * 
 * Esta clase realiza la conversión de los códigos de servicio OEGAM
 * matriculables eléctronicamente a los solicitados por DGT
 * @author 
 *
 */
public class ConversorServicio {

	public static TipoServicio darServicioDGTdeServicioOEGAM(String servicio) {

		TipoServicio servicioDGT = null;

		if (servicio == null) {
			return servicioDGT;
		}

		if (servicio.equals("0")) {			//Particular
			servicioDGT = TipoServicio.B_00;
		} else if (servicio.equals("1")) {	//Publico
			servicioDGT = TipoServicio.A_00;
		}/* else if (servicio.equals("2")) {//Autotaxi
			servicioDGT = TipoServicio.A_04;
		} */else if (servicio.equals("3")) {//Alquiler con
			servicioDGT = TipoServicio.A_02;
		} else if (servicio.equals("4")) {	//Alquiler sin
			servicioDGT = TipoServicio.A_01;
		} /*else if (servicio.equals("5")) {//Escuela de conductores
			servicioDGT = TipoServicio.A_03;
		}*/ else if (servicio.equals("6")) {//Agricola
			servicioDGT = TipoServicio.B_06;
		} else if (servicio.equals("7")) {	//Obras y servicios
			servicioDGT = TipoServicio.B_09;
		} else if (servicio.equals("8")) {	//Transporte escolar
			servicioDGT = TipoServicio.A_12;
		} else if (servicio.equals("9")) {	//Mercancías peligrosas
			servicioDGT = TipoServicio.A_10;
		}

		return servicioDGT;
	}

	public static String darServicioOEGAMdeServicioDGT(String servicioDGT) {

		String servicio = null;

		if (TipoServicio.A_00.value().equals(servicioDGT)) {		//Particular
			servicio = "1";
		} else if (TipoServicio.B_00.value().equals(servicioDGT)) { //Alquiler sin conductor
			servicio = "0";
		} else if (TipoServicio.A_01.value().equals(servicioDGT)) { //Alquiler sin conductor
			servicio = "4";
		} else if (TipoServicio.A_02.value().equals(servicioDGT)) { //Alquiler con conductor
			servicio = "3";
		} /*else if (TipoServicio.A_03.value().equals(servicioDGT)) { //Escuela de Conductores
			servicio = "5";
		} else if (TipoServicio.A_04.value().equals(servicioDGT)) { //Taxi
			servicio = "2";
		}*/ else if (TipoServicio.B_06.value().equals(servicioDGT)) { //Agrícola
			servicio = "6";
		} else if (TipoServicio.B_09.value().equals(servicioDGT)) { //Público
			servicio = "7";
		} else if (TipoServicio.A_10.value().equals(servicioDGT)) { //Mercanciás peligrosas
			servicio = "9";
		} else if (TipoServicio.A_12.value().equals(servicioDGT)) { //Transporte Escolsr
			servicio = "8";
		} else if (TipoServicio.B_17.value().equals(servicioDGT)) { //Vivienda
			servicio = "0";
		} else if (TipoServicio.B_18.value().equals(servicioDGT)) { //Actividad Económica
			servicio = "0";
		} else if (TipoServicio.B_19.value().equals(servicioDGT)) { //Recreativo
			servicio = "0";
		} else if (TipoServicio.B_21.value().equals(servicioDGT)) { //Ferias
			servicio = "0";
		} else {
			servicio = "1"; //El resto Público
		}

		return servicio;
	}

}