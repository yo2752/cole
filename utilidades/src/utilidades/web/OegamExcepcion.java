package utilidades.web;

import utilidades.mensajes.Claves;
import utilidades.mensajes.GestorFicherosMensajes;

public class OegamExcepcion extends Throwable {
	public enum EnumError{
		error_NoExisteMensajeEnFicheroMensajes (-4),
		error_MensajeIncorrecto (-3),
		error_NoExisteMensajeEnFicheroErrores (-2),
		error_NoExisteFicheroErrores (-1),
		error_00001(1),
		error_00002(2),
		error_00004(4),
		error_00005(5),
		error_00007(7),
		error_00012(12),
		error_00013(13),
		error_00015(15),
		error_00016(16),
		error_00017(17),
		error_00018(18),
		error_00019(19),
		error_00020(20),
		error_00021(21),
		error_00022(22),
		error_00023(23),
		error_00024(24),
		error_00025(25),
		error_00026(26),
		error_00027(27),
		error_00028(28),
		error_00029(29),
		error_00030(30),
		error_00031(31),
		error_00032(32),
		error_00033(33),
		error_00034(34),
		error_00035(35),
		error_00036(36),
		error_00037(37),
		error_00038(38),
		error_00039(39),
		error_00040(40),
		error_00041(41),
		error_00042(42),
		error_00043(43),
		error_00044(44),
		error_00045(45),
		error_00046(46),
		error_00047(47),
		error_00048(48),
		error_00049(49),
		error_00050(50),
		error_00051(51),
		error_00052(52),
		error_00053(53),
		error_00054(54),
		error_00055(55),
		error_00056(56),
		error_00057(57),
		error_00058(58),
		error_00059(59),
		error_00060(60),
		error_00061(61),
		error_00062(62),
		error_00063(63),
		error_00064(64),
		error_No_Se_Pudo_Cambiar_El_Estado_Del_Tramite(65),
		;
		private final long _valor;
		private EnumError(long valor){
			_valor=valor;
		}
		public final long getValor(){
			return _valor;
		}
	}

	private static final long serialVersionUID = 1L;
	private EnumError _codigoError;
	private String _mensajeError1;

	public OegamExcepcion(String causa){
		super(causa);
		_mensajeError1=causa;
	}

	public OegamExcepcion(Throwable causa, EnumError codigoError) {
		super(causa);
		_codigoError = codigoError;
		_mensajeError1 = causa.getMessage();
		//Claves.setObjetoDeContextoSesion(Claves.CLAVE_ERROR_OEGAM,this);
	}

	public OegamExcepcion(String causa, EnumError codigoError) {
		super(causa);
		_codigoError = codigoError;
		_mensajeError1 = causa;
		//Claves.setObjetoDeContextoSesion(Claves.CLAVE_ERROR_OEGAM,this);
	}

	public OegamExcepcion(EnumError codigoError) {
		super();

		_codigoError = codigoError;
		obtenerMensajeDeFichero(_codigoError);
		//Claves.setObjetoDeContextoSesion(Claves.CLAVE_ERROR_OEGAM, this);
	}

	public OegamExcepcion(EnumError codigoError, String mensaje) {
		super();

		_codigoError = codigoError;
		_mensajeError1 = mensaje;

		if (null == mensaje || mensaje.trim().length() == 0) {
			_codigoError = EnumError.error_MensajeIncorrecto;
			_mensajeError1 = "El mensaje no puede ser nulo o cadena vacía";
		}

		//Claves.setObjetoDeContextoSesion(Claves.CLAVE_ERROR_OEGAM, this);
	}

	private void obtenerMensajeDeFichero(EnumError codigoError) {
		if (codigoError.getValor() >= 0) {
			GestorFicherosMensajes gfm = (GestorFicherosMensajes) Claves
					.getObjetoDeContextoAplicacion(Claves.CLAVE_GESTOR_ERRORES);
			if (null == gfm) {
				try {
					gfm = new GestorFicherosMensajes("erroresOegam");
					Claves.setObjetoDeContextoAplicacion(Claves.CLAVE_GESTOR_ERRORES, gfm);
				} catch (Throwable e) {
					_codigoError = EnumError.error_NoExisteFicheroErrores;
					_mensajeError1 = "No se encuentra el fichero de errores erroresOegam.properties";
					//Claves.setObjetoDeContextoSesion(Claves.CLAVE_ERROR_OEGAM, this);
					return;
				}
			}

			try {
				_mensajeError1 = gfm.getMensaje("error." + _codigoError.getValor());
			} catch (Throwable e) {
				_codigoError = EnumError.error_NoExisteMensajeEnFicheroErrores;
				_mensajeError1 = "No se encuentra el mensaje en el fichero de errores erroresOegam.properties";
				//Claves.setObjetoDeContextoSesion(Claves.CLAVE_ERROR_OEGAM, this);
			}
		} else {
			_codigoError = EnumError.error_NoExisteMensajeEnFicheroErrores;
			_mensajeError1 = "No se encuentra el mensaje en el fichero de errores erroresOegam.properties";
			//Claves.setObjetoDeContextoSesion(Claves.CLAVE_ERROR_OEGAM, this);
		}

	}

	public String getMensajeError1() {
		return _mensajeError1;
	}

	public EnumError getCodigoError() {
		return _codigoError;
	}

}