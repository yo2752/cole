package org.oegam.gestor.distintivos.constants;

public final class GestorImprDocConst {
	public static final String IMPRESORA = "172.26.0.66:2222";

	public static final String CONTEXTO_PROPERTIES_FRONTAL = "FRONTAL";
	public static final String OEGAM_PROPERTIES = "oegam.properties";
	public static final String PROPIEDADES = "propiedades";

	public final class COLA {
		public static final long JUSTIFICANTE  = 13L;
		public static final long PERMISO       = 5L;
		public static final long FICHA_TECNICA = 3L;
		public static final long DIST_0        = 2L;
		public static final long DIST_ECO      = 1L;
		public static final long DIST_C        = 4L;
		public static final long DIST_B        = 6L;
	}

	public final class EXCEPTIONS {
		public static final String NO_DATA_FOUND = "0001";
		public static final String HIBERNATE     = "0002";
		public static final String IOEXCEPTION   = "0003";
		public static final String OEGAM         = "0004";
		public static final String BADREQUEST    = "0005";
		public static final String JOBNOTEXISTS  = "0006";
		public static final String TIPONOTEXISTS = "0007";
		public static final String JEFANOTEXISTS = "0008";
		public static final String JEFANOTAUTHOR = "0009";
	}

	public final class EXCEPTION_MESSAGE {
		public static final String NO_DATA_FOUND = "No se han encontrado ficheros para imprimir para docid %d";
		public static final String BADREQUEST    = "Mal requerimiento";
		public static final String JOBNOTEXISTS  = "Job no existe";
		public static final String TIPONOTEXISTS = "No se ha encontrado Tipo para esta material";
		public static final String JEFANOTEXISTS = "No se ha encontrado Jefatura para el certificado";
		public static final String JEFANOTAUTHOR = "No estas autorizado para trabajar con estos documentos";
	}

}