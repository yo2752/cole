package trafico.beans.jaxb.matw.dgt.tipos.complejos;

import java.util.Arrays;
import java.util.List;

public class TipoVehiculoDGT {
	
	private static List<String> tipoVehiculo = Arrays.asList(
			"50","51","52","53","54",
			"90","91","92",
			"R0","R1","R2","R3","R4","R5","R6","R7","R8","R9","RA","RB","RC","RD","RE","RF","RH",
			"S0","S1","S2","S3","S4","S5","S6","S7","S8","S9","SA","SB","SC","SD","SE","SF","SH");
	
	public static boolean contains(String tipo) {
		return tipoVehiculo.contains(tipo);
	}
	
}