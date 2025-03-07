package org.gestoresmadrid.oegam2comun.registradores.utiles;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import utilidades.estructuras.Fecha;

public class UtilesValidaciones {
	
	//Diez dígitos parte entera y dos dígitos parte decimal separados por un punto.
	//NNNNNNNNNN,NN
	public static boolean validarImporte(BigDecimal importe) {
		
		if(validarObligatoriedad(importe)){
			
			String cadena = String.valueOf(importe);

			return cadena.matches("^[0-9]{1,10}+(\\.[0-9]{1,2})?$");
			
		}
		return true;
	}
	
	//Validación de la fecha 
	//Dia/Mes/Año. DD/MM/AAAA
	static int[] diasMes= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	public static boolean validarFecha( Fecha fecha){
		if (null != fecha && null != fecha.getDia()  &&  !"".equals(fecha.getDia()) 
				&& null != fecha.getMes()  &&  !"".equals(fecha.getMes()) 
				&& null != fecha.getAnio()  &&  !"".equals(fecha.getAnio())){
			int dia = Integer.parseInt(fecha.getDia());
			int mes = Integer.parseInt(fecha.getMes());
			int anio = Integer.parseInt(fecha.getAnio());

			if ( anio < 1900 ) {
				return false;
			}
			if ( mes<1 || mes>12 )
				return false;
			// Para febrero y bisiesto el limite es 29
			if ( mes==2 && anio%4==0 )
				return dia>=1 && dia<=29;
				return dia>=0 && dia<=diasMes[mes-1];
		}
		return true;
	}

	//Valida que solo se introduzcan números
	public static boolean validarNumero(BigDecimal numero) {

		if(validarObligatoriedad(numero)){

			String cadena = String.valueOf(numero);
			if(!cadena.matches("\\d*") ){
				return false;
			}
		}
		return true;
	}

	//Valida que solo se introduzcan números
	public static boolean validarNumero(String numero) {

		if(StringUtils.isNotBlank(numero)){

			if(!numero.matches("\\d*") ){
				return false;
			}
		}
		return true;
	}

	public static boolean validarObligatoriedad(BigDecimal valor) {

		if(null == valor || valor.equals(BigDecimal.ZERO)){
			return false;
		}
		return true;
	}

	public static boolean validarObligatoriedad(Fecha valor) {

		if(null == valor || valor.getDia().trim().isEmpty() || valor.getMes().trim().isEmpty() || valor.getAnio().trim().isEmpty()){
			return false;
		}
		return true;
	}

	public static boolean validarObligatoriedad(Date valor) {
		if(null == valor){
			return false;
		}
		return true;
	}

	//Tres enteros y diez decimales separados por un punto.
	//NNN,NNNNNNNNNN
	public static boolean validarPorcentaje (String valor){
		if(StringUtils.isNotBlank(valor)){

			if(!valor.matches("^-?[0-9]{1,10}(\\.[0-9]{1,10})?$|^-?(100)(\\.[0]{1,10})?$") ){
				return false;
			}
		}
		return true;
	}

	public static boolean validarCamposSiNo (String valor){
		if(StringUtils.isNotBlank(valor)){

			if (!"true".equalsIgnoreCase(valor) && !"false".equalsIgnoreCase(valor)){
				return false;
			}
		}
		return true;
	}

	public static boolean validarBastidor(String valor){

		if(StringUtils.isNotBlank(valor)){
			if(!valor.matches("^[a-zA-Z0-9]*$") || valor.length() != 17 ){
				return false;
			}
		}
		return true;
	}

	public static boolean validarMatricula(String valor){

		if(StringUtils.isNotBlank(valor)){

			if(!valor.matches("^[0-9]{4}[A-Z]{3}$") &&  !valor.matches("^[A-Z]{1,2}[0-9]{4}[A-Z]{0,2}$") ){
				return false;
			}
		}
		return true;
	}

	public static boolean validarCodigoPostal(String valor){

		if(StringUtils.isNotBlank(valor)){

			if(!valor.matches("^0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3}$") ){

				return false;
			}
		}
		return true;
	}

	//Diez enteros para expresar los metros y dos para expresar los centímetros separados por un punto.
	//NNNNNNNNNN,NN
	public static boolean validarLongitud(String valor) {

		if(StringUtils.isNotBlank(valor)){

			String cadena = String.valueOf(valor);

			if(!cadena.matches("^[0-9]{1,10}+(\\.[0-9]{1,3})?$") ){
				return false;
			}
		}
		return true;
	}

	//Diez enteros para expresar las toneladas y tres para expresa los kilogramos separados por un punto
	//NNNNNNNNNN,NNN
	public static boolean validarPeso(String valor) {

		if(StringUtils.isNotBlank(valor)){

			String cadena = String.valueOf(valor);

			if(!cadena.matches("^[0-9]{1,10}+(\\.[0-9]{1,3})?$") ){
				return false;
			}
		}
		return true;
	}

	//Diez enteros para expresar lo kilowatios y tres para expresar los watios separados por un punto. 
	//NNNNNNNNNN,NNN
	public static boolean validarPotencia(String valor) {

		if(StringUtils.isNotBlank(valor)){

			String cadena = String.valueOf(valor);

			if(!cadena.matches("^[0-9]{1,10}+(\\.[0-9]{1,3})?$") ){
				return false;
			}
		}
		return true;
	}


	public static boolean validarMail(String valor) {

		if(StringUtils.isNotBlank(valor)){

			String cadena = String.valueOf(valor).toLowerCase();

			if(!cadena.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$") ){
				return false;
			}
		}
		return true;
	}
}
