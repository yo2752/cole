package general.utiles;

public class OraUtiles {
	public static String ORA02291Tasa = "ORA-02291: restricción de integridad (OEGAM2.TRAM_TRAF_SOLIN_TASA_FK) violada - clave principal no encontrada";
	public static String ORA02291NumColegiado = "ORA-02291: restricción de integridad (OEGAM2.VEHICULO_COLEGIADO_FK1) violada - clave principal no encontrada";
	public static String ORA02291NumColegiado2 = "ORA-02291: restricción de integridad (OEGAM2.VEHICULO_COLE_FK) violada - clave principal no encontrada";
	public static String ORA02291Servicio = "ORA-02291: restricción de integridad (OEGAM2.VEH_SERV_FK) violada - clave principal no encontrada";
	
	public static String MensajeORA02291Tasa = "El código de tasa no es un código válido.";
	public static String MensajeORA02291NumColegiado = "El número de colegiado del trámite no está en la base de datos.";
	public static String MensajeORA02291NumColegiado2 = "El número de colegiado del trámite no está en la base de datos.";
	public static String MensajeORA02291Servicio = "El servicio al que se destinará el vehículo no es válido";
	
	
	/**
	 * Si el mensaje SQL_ERRM es conocido, nos devolverá un mensaje específico, sino el propio SQL_ERRM
	 */
	public static String traducirError(String sqlerrm) {
		if (null==sqlerrm) return "Error al ejecutar procedimiento en la base de datos"; //Si pcode!=0, siempre debe devolver un sqlerrm
		if (ORA02291Tasa.equals(sqlerrm)) return MensajeORA02291Tasa;
		if (ORA02291NumColegiado.equals(sqlerrm)) return MensajeORA02291NumColegiado;
		if (ORA02291NumColegiado2.equals(sqlerrm)) return MensajeORA02291NumColegiado2;
		if (ORA02291Servicio.equals(sqlerrm)) return MensajeORA02291Servicio;
		return sqlerrm;
	}
}
