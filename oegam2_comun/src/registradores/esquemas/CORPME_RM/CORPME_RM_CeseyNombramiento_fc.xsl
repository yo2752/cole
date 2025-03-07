<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY ruta_css "CSS\">
	<!ENTITY ruta_img "img\">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:template match="/">
		<html>
			<head>
				<link rel="stylesheet" href="&ruta_css;contrato.css"/>
			</head>
			<body class="textoCabeceraFondoBlanco">
				<xsl:for-each select="CORPME-RM-Cese-Nombramiento">
					<table align="center" border="2" rules="cols" width="93%" cellpadding="4">
						<tr>
							<th class="titulo" colspan="2" width="100%">REGISTRO MERCANTIL. 
											<xsl:for-each select="./Modelo/Descripcion">
									<xsl:apply-templates/>
								</xsl:for-each>
							</th>
						</tr>
					</table>
					<!--Modelo 1-->
					<xsl:for-each select="./Modelo_1">
						<!--Cabecera Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certificantes"/>
							<xsl:call-template name="sociedad"/>
						</table>
						<!--Cuerpo Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certifica"/>
							<tr align="left">
								<th>Que del libro de Actas de la Sociedad resulta lo siguiente:</th>
							</tr>
							<tr align="left">
								<th>1.-Que en <xsl:call-template name="lugar-reunion"/> el día <xsl:call-template name="dia-reunion"/> se reunió la Junta General <xsl:call-template name="tipo-junta"/> de la Sociedad, con carácter de <xsl:call-template name="caracter-junta"/>, por haberlo así acordado expresamente la totalidad de los socios titulares del cien por cien del capital social.</th>
							</tr>
							<tr align="left">
								<th>2.-Que se formó la lista de asistentes, que fue firmada por todos los socios. Que al final de la reunión fue aprobada el acta por unanimidad, siendo firmada por el Presidente y el Secretario de la Junta.</th>
							</tr>
							<tr align="left">
								<th>3.-Que fueron adoptados por unanimidad los siguientes acuerdos:</th>
							</tr>
							<!--Nombramientos-->
							<xsl:call-template name="nombramientos"/>
							<!--Ceses-->
							<xsl:call-template name="ceses"/>
							<!--Lugar y fecha de certificación-->
							<tr align="left">
								<th>
									<xsl:call-template name="lugar-fecha-certificacion"/>
								</th>
							</tr>
						</table>
						<!--Pie Certificación-->
						<xsl:call-template name="clausula-LOPD"/>
					</xsl:for-each>
					<!--Modelo 2-->
					<xsl:for-each select="./Modelo_2">
						<!--Cabecera manifestación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="aceptacion-nombramiento"/>
						</table>
						<!--Contenido manifestación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<!--Literal Manifiesta-->
							<xsl:call-template name="manifiesta"/>
							<tr align="left">
								<th>Que acepta el citado cargo, y que no se halla incurso en ninguna de las causas de incompatibilidad para el ejercicio del cargo, establecidas en la legislación vigente.</th>
							</tr>
							<xsl:call-template name="lugar-fecha-suscripcion"/>
						</table>
						<!--Pie Certificación-->
						<xsl:call-template name="clausula-LOPD"/>
					</xsl:for-each>
					<!--Modelo 3-->
					<xsl:for-each select="./Modelo_3">
						<!--Cabecera Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certificantes"/>
							<xsl:call-template name="sociedad"/>
						</table>
						<!--Cuerpo Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certifica"/>
							<tr align="left">
								<th>Que del libro de Actas de la Sociedad resulta lo siguiente:</th>
							</tr>
							<xsl:for-each select="./Junta_Accionistas">
								<tr align="left">
									<th>1°.- Que en fecha <xsl:call-template name="dia-reunion"/> y en <xsl:call-template name="lugar-reunion"/>, se reunió la Junta General <xsl:call-template name="tipo-junta"/> de accionistas, debidamente convocada mediante <xsl:call-template name="convocatoria-medios"/>
									</th>
								</tr>
								<tr align="left">
									<th>2°.- Que la Junta se celebró en <xsl:call-template name="numero-convocatoria"/> convocatoria y que, una vez formada la lista de asistentes, resultó la concurrencia, presentes o representados, de
		accionistas que ostentaban la titularidad del <xsl:call-template name="concurrencia-convocatoria"/>
									</th>
								</tr>
							</xsl:for-each>
							<tr align="left">
								<th>3°.-Que fueron adoptados los siguientes acuerdos con el voto favorable del <xsl:call-template name="voto-favorable"/>, presente o representado en la Junta:</th>
							</tr>
							<!--Nombramientos-->
							<xsl:call-template name="nombramientos"/>
							<!--Ceses-->
							<xsl:call-template name="ceses"/>
							<!--Aprobación del acta-->
							<tr align="left">
								<th>4°.-Qué el acta de la Junta fue aprobada <xsl:call-template name="aprobacion-acta"/> habiendo sido firmada por el Presidente y el Secretario de la Junta.</th>
							</tr>
							<!--Lugar y fecha de certificación-->
							<tr align="left">
								<th>
									<xsl:call-template name="lugar-fecha-certificacion"/>
								</th>
							</tr>
						</table>
						<!--Pie Certificación-->
						<xsl:call-template name="clausula-LOPD"/>
					</xsl:for-each>
					<!--Modelo 4-->
					<xsl:for-each select="./Modelo_4">
						<!--Cabecera Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certificantes"/>
							<xsl:call-template name="sociedad"/>
						</table>
						<!--Cuerpo Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certifica"/>
							<tr align="left">
								<th>Que del libro de Actas de la Sociedad resulta lo siguiente:</th>
							</tr>
							<xsl:for-each select="./Junta_Socios">>
								<tr align="left">
									<th>1°.- Que en fecha <xsl:call-template name="dia-reunion"/> y en <xsl:call-template name="lugar-reunion"/>, se reunió la Junta General <xsl:call-template name="tipo-junta"/> de accionistas, debidamente convocada mediante <xsl:call-template name="convocatoria-medios"/>, siendo el contenido de la convocatoria el siguiente: <xsl:call-template name="contenido-convocatoria"/>
									</th>
								</tr>
								<tr align="left">
									<th>2°.- Que la Junta se celebró en <xsl:call-template name="numero-convocatoria"/> convocatoria y que, una vez formada la lista de asistentes, resultó la concurrencia, presentes o representados, de
		accionistas que ostentaban la titularidad del <xsl:call-template name="concurrencia-convocatoria"/>
									</th>
								</tr>
							</xsl:for-each>
							<tr align="left">
								<th>3°.-Que fueron adoptados los siguientes acuerdos con el voto favorable del <xsl:call-template name="voto-favorable"/>, presente o representado en la Junta:</th>
							</tr>
							<!--Nombramientos-->
							<xsl:call-template name="nombramientos"/>
							<!--Ceses-->
							<xsl:call-template name="ceses"/>
							<!--Aprobación del acta-->
							<tr align="left">
								<th>4°.-Que el acta de la Junta fue aprobada por los socios al término de la reunión, habiendo sido firmada por el Presidente y el Secretario de la Junta.</th>
							</tr>
							<!--Lugar y fecha de certificación-->
							<tr align="left">
								<th>
									<xsl:call-template name="lugar-fecha-certificacion"/>
								</th>
							</tr>
						</table>
						<!--Pie Certificación-->
						<xsl:call-template name="clausula-LOPD"/>
					</xsl:for-each>
					<!--Modelo 5-->
					<xsl:for-each select="./Modelo_5">
						<!--Cabecera Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certificantes"/>
							<xsl:call-template name="sociedad"/>
						</table>
						<!--Cuerpo Certificación-->
						<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
							<xsl:call-template name="certifica"/>
							<tr align="left">
								<th>Que del libro de Actas de la Sociedad resulta lo siguiente:</th>
							</tr>
							<xsl:for-each select="./Consejo_Administracion">>
								<tr align="left">
									<th>1°.- Que en fecha <xsl:call-template name="dia-reunion-consejo"/> y en <xsl:call-template name="lugar-reunion-consejo"/>, se reunió el Consejo de Administración, con asistencia de: </th>
									<xsl:call-template name="asistentes-consejo-administracion"/>
								</tr>
								<tr align="left">
									<th>Quienes firmaron la lista de asistencia.</th>
								</tr>
							</xsl:for-each>
							<tr align="left">
								<th>2.-Que los citados señores suponen la totalidad de los miembros del Consejo de Administración.</th>
							</tr>
							<tr align="left">
								<th>3.-Que al final de la reunión fue aprobada el acta por unanimidad, siendo firmada por el Presidente y el Secretario de la reunión, quienes ostentan los citados cargos en el Consejo de Administración.</th>
							</tr>
							<tr align="left">
								<th>4.-Que fueron adoptados por unanimidad los siguientes acuerdos:</th>
							</tr>
							<!--Nombramientos-->
							<xsl:call-template name="nombramientos"/>
							<!--Ceses-->
							<xsl:call-template name="ceses"/>
							<!--Lugar y fecha de certificación-->
							<tr align="left">
								<th>
									<xsl:call-template name="lugar-fecha-certificacion"/>
								</th>
							</tr>
						</table>
						<!--Pie Certificación-->
						<xsl:call-template name="clausula-LOPD"/>
					</xsl:for-each>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
	<xsl:template name="tipo-personalidad">
		<xsl:apply-templates/>
		<xsl:if test=".='1'">
			<span class="textoCabeceraFondoBlanco">Fisica</span>
		</xsl:if>
		<xsl:if test=".='2'">
			<span class="textoCabeceraFondoBlanco">Juridica Publica</span>
		</xsl:if>
		<xsl:if test=".='3'">
			<span class="textoCabeceraFondoBlanco">Juridica Privada</span>
		</xsl:if>
	</xsl:template>
	<xsl:template name="tipo-identificador-administrativo">
		<xsl:if test=".='1'">
			<span>DNI</span>
		</xsl:if>
		<xsl:if test=".='2'">
			<span>CIF</span>
		</xsl:if>
		<xsl:if test=".='3'">
			<span>NIF</span>
		</xsl:if>
		<xsl:if test=".='4'">
			<span>PASAPORTE</span>
		</xsl:if>
	</xsl:template>
	<xsl:template name="certificantes">
		<xsl:call-template name="pon-cabecera">
			<xsl:with-param name="texto">CERTIFICANTE:</xsl:with-param>
		</xsl:call-template>
		<xsl:for-each select="./Certificantes/Certificante">
			<tr align="left">
				<th>
					<xsl:call-template name="identificacion-sujeto"/>
				</th>
			</tr>
			<tr align="left">
				<th>
						en su calidad de
					<xsl:for-each select="Cargo">
						<xsl:apply-templates/>
					</xsl:for-each>
				</th>
			</tr>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="identificacion-sujeto">
		<xsl:for-each select="./Identificacion">
			Don
			<xsl:for-each select="Nombre">
				<xsl:apply-templates/>
			</xsl:for-each>
			<xsl:for-each select="Apellidos">
				<span>&#160;</span>
				<xsl:apply-templates/>
			</xsl:for-each>
			<xsl:for-each select="./Identificador_Administrativo">
				<xsl:for-each select="Tipo">
					<span>&#160;</span>
					<xsl:call-template name="tipo-identificador-administrativo"/>
				</xsl:for-each>
				<xsl:for-each select="Numero_Documento">
					<span>&#160;</span>
					<xsl:apply-templates/>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="identificacion-sujeto-2">
		<xsl:for-each select="./Identificacion">
			<xsl:for-each select="Nombre">
				<xsl:apply-templates/>
			</xsl:for-each>
			<xsl:for-each select="Apellidos">
				<span>&#160;</span>
				<xsl:apply-templates/>
			</xsl:for-each>
			<xsl:for-each select="./Identificador_Administrativo">
				<xsl:for-each select="Tipo">
					<span>&#160;</span>
					<xsl:call-template name="tipo-identificador-administrativo"/>
				</xsl:for-each>
				<xsl:for-each select="Numero_Documento">
					<span>&#160;</span>
					<xsl:apply-templates/>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="sociedad">
		<tr align="left">
			<th>
			   de la Sociedad
				<xsl:for-each select="./Sociedad">
					<xsl:for-each select="Denominacion_Social">
						<xsl:apply-templates/>
					</xsl:for-each>
					<xsl:for-each select="CIF">
						<span>&#160;CIF&#160;</span>
						<xsl:apply-templates/>
					</xsl:for-each>
					<xsl:for-each select="Seccion">
						<span>&#160;Sección&#160;</span>
						<xsl:apply-templates/>
					</xsl:for-each>
					<xsl:for-each select="Hoja">
						<span>&#160;Hoja&#160;</span>
						<xsl:apply-templates/>
					</xsl:for-each>
					<xsl:for-each select="Hoja_Bis">
						<span>&#160;Hoja Bis&#160;</span>
						<xsl:apply-templates/>
					</xsl:for-each>
					<xsl:for-each select="IUS">
						<span>&#160;IUS&#160;</span>
						<xsl:apply-templates/>
					</xsl:for-each>
				</xsl:for-each>
			</th>
		</tr>
	</xsl:template>
	<xsl:template name="sociedad-2">
		<xsl:for-each select="Denominacion_Social">
			<xsl:apply-templates/>
		</xsl:for-each>
		<xsl:for-each select="CIF">
			<span>&#160;CIF&#160;</span>
			<xsl:apply-templates/>
		</xsl:for-each>
		<xsl:for-each select="Seccion">
			<span>&#160;Sección&#160;</span>
			<xsl:apply-templates/>
		</xsl:for-each>
		<xsl:for-each select="Hoja">
			<span>&#160;Hoja&#160;</span>
			<xsl:apply-templates/>
		</xsl:for-each>
		<xsl:for-each select="Hoja_Bis">
			<span>&#160;Hoja Bis&#160;</span>
			<xsl:apply-templates/>
		</xsl:for-each>
		<xsl:for-each select="IUS">
			<span>&#160;IUS&#160;</span>
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="certifica">
		<xsl:call-template name="pon-cabecera">
			<xsl:with-param name="texto">CERTIFICA:</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="manifiesta">
		<xsl:call-template name="pon-cabecera">
			<xsl:with-param name="texto">MANIFIESTA:</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="lugar-reunion">
		<xsl:for-each select="./Reunion_Junta/LugaryFecha_Celebracion/Lugar">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="dia-reunion">
		<xsl:for-each select="./Reunion_Junta/LugaryFecha_Celebracion/Fecha">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="lugar-reunion-consejo">
		<xsl:for-each select="./Reunion_Consejo/LugaryFecha_Celebracion/Lugar">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="dia-reunion-consejo">
		<xsl:for-each select="./Reunion_Consejo/LugaryFecha_Celebracion/Fecha">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="tipo-junta">
		<xsl:for-each select="./Reunion_Junta/Tipo">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="caracter-junta">
		<xsl:for-each select="./Reunion_Junta/Caracter">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="nombramientos">
		<xsl:if test="count(./Acuerdos/Nombramientos/Nombramiento)>0">
			<xsl:call-template name="pon-cabecera-2">
				<xsl:with-param name="texto">NOMBRAR A:</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:for-each select="./Acuerdos/Nombramientos/Nombramiento">
			<tr align="left">
				<th>
					<span>- </span>
					<xsl:call-template name="identificacion-sujeto-2"/>
					<span> COMO </span>
					<xsl:for-each select="./Cargo">
						<xsl:apply-templates/>
					</xsl:for-each>
					<xsl:for-each select="./Facultades_Delegadas">
						<span>&#160;</span>
						<xsl:apply-templates/>
						<span>&#160;</span>
					</xsl:for-each>
					<xsl:call-template name="duracion-cargo"/>
					<xsl:for-each select="./Circustancias_Personales">
						<span>, cuyas circunstancias personales son las siguientes:  </span>
						<xsl:apply-templates/>
					</xsl:for-each>
					
					<xsl:call-template name="aceptacion-cargo"/>
				</th>
			</tr>
		</xsl:for-each>
		<tr/>
	</xsl:template>
	<xsl:template name="asistentes-consejo-administracion">
		<xsl:for-each select="./Reunion_Consejo/Asistentes/Asistente">
			<tr align="left">
				<th>
					<span>- </span>
					<xsl:call-template name="identificacion-sujeto-2"/>
					<span> COMO </span>
					<xsl:for-each select="./Cargo">
						<xsl:apply-templates/>
					</xsl:for-each>
				</th>
			</tr>
		</xsl:for-each>
		<tr/>
	</xsl:template>
	<xsl:template name="aceptacion-nombramiento">
		<tr align="left">
			<th>
				<span>Don </span>
				<xsl:for-each select="./Nombramiento">
					<xsl:call-template name="identificacion-sujeto-2"/>
				</xsl:for-each>
				<span>, que fue nombrado </span>
				<xsl:for-each select="./Nombramiento/Cargo">
					<xsl:apply-templates/>
				</xsl:for-each>
				<span> de la Sociedad </span>
				<xsl:for-each select="./Sociedad">
					<xsl:call-template name="sociedad-2"/>
				</xsl:for-each>
				<xsl:for-each select="./Nombramiento">
					<xsl:call-template name="duracion-cargo"/>
				</xsl:for-each>
				<span> en la Junta General </span>
				<xsl:call-template name="tipo-junta"/>
				<span>, con carácter </span>
				<xsl:call-template name="caracter-junta"/>
				<span>, de la misma celebrada en </span>
				<xsl:call-template name="lugar-reunion"/>
				<span>, </span>
				<span>el día </span>
				<xsl:call-template name="dia-reunion"/>
			</th>
		</tr>
	</xsl:template>
	<xsl:template name="ceses">
		<xsl:if test="count(./Acuerdos/Ceses/Cese)>0">
			<xsl:call-template name="pon-cabecera-2">
				<xsl:with-param name="texto">CESAR A:</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:for-each select="./Acuerdos/Ceses/Cese">
			<tr align="left">
				<th>
					<span>- </span>
					<xsl:call-template name="identificacion-sujeto-2"/>
					<span> COMO </span>
					<xsl:for-each select="./Cargo">
						<xsl:apply-templates/>
					</xsl:for-each>
				</th>
			</tr>
		</xsl:for-each>
		<tr/>
	</xsl:template>
	<xsl:template name="duracion-cargo">
		<xsl:for-each select="./Duracion_Cargo">
			<xsl:if test="./Indefinida='true'">
				<span> POR DURACIÓN INDEFINIDA </span>
			</xsl:if>
			<xsl:if test="count(./Duracion/Meses)>0">
				<span> POR EL PLAZO DE </span>
				<xsl:for-each select="./Duracion/Meses">
					<xsl:apply-templates/>
				</xsl:for-each>
				<span> MESES </span>
			</xsl:if>
			<xsl:if test="count(./Duracion/Años)>0">
				<span> POR EL PLAZO DE </span>
				<xsl:for-each select="./Duracion/Años">
					<xsl:apply-templates/>
				</xsl:for-each>
				<span> AÑOS </span>
			</xsl:if>
			<xsl:if test="count(./Duracion/Periodo)>0">
				<span> POR EL PERIODO COMPRENDIDO ENTRE EL </span>
				<xsl:for-each select="./Duracion/Periodo/Inicio">
					<xsl:apply-templates/>
				</xsl:for-each>
				<span> Y EL </span>
				<xsl:for-each select="./Duracion/Periodo/Fin">
					<xsl:apply-templates/>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="count(./Duracion/Ejercicios/Ejercicio)>0">
				<xsl:if test="count(./Duracion/Ejercicios/Ejercicio)=1">
					<span> QUE COMPRENDERÁ EL EJERCICIO </span>
				</xsl:if>
				<xsl:if test="count(./Duracion/Ejercicios/Ejercicio)>1">
					<span> QUE COMPRENDERÁ LOS EJERCICIOS </span>
				</xsl:if>
				<xsl:for-each select="./Duracion/Ejercicios/Ejercicio">
					<xsl:if test="position()>1">
						<span>, </span>
					</xsl:if>
					<xsl:apply-templates/>
				</xsl:for-each>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="aceptacion-cargo">
		<xsl:for-each select="./Aceptacion_Cargo">
			<xsl:if test=".='EN LA MISMA JUNTA'">
				<span>, presente en la Junta, acepta el cargo y  manifiesta no hallarse incurso en ninguna de las causas de incompatibilidad para el ejercicio del
mismo establecidas en la legislación vigente.</span>
			</xsl:if>
			<xsl:if test=".='CON POSTERIORIDAD'">
				<span>, no hallándose presente en la sesión, aceptará el cargo con posterioridad.</span>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="lugar-fecha-certificacion">
		<tr align="left">
			<th>
				<xsl:for-each select="./LugaryFecha_Certificacion">
					<span>Y para que así conste, expido la presente certificación en  </span>
					<xsl:for-each select="./Lugar">
						<xsl:apply-templates/>
					</xsl:for-each>
					<span>, a </span>
					<xsl:for-each select="./Fecha">
						<xsl:apply-templates/>
					</xsl:for-each>
				</xsl:for-each>
			</th>
		</tr>
	</xsl:template>
	<xsl:template name="lugar-fecha-suscripcion">
		<tr align="left">
			<th>
				<xsl:for-each select="./LugaryFecha">
					<span>Y para que conste, suscribe el presente documento en  </span>
					<xsl:for-each select="./Lugar">
						<xsl:apply-templates/>
					</xsl:for-each>
					<span>, a </span>
					<xsl:for-each select="./Fecha">
						<xsl:apply-templates/>
					</xsl:for-each>
				</xsl:for-each>
			</th>
		</tr>
	</xsl:template>
	<xsl:template name="convocatoria-medios">
		<xsl:if test="count(./Convocatoria/Medios/Medio_Publicacion)>0">
			<span> anuncios publicados: </span>
		</xsl:if>
		<xsl:for-each select="./Convocatoria/Medios/Medio_Publicacion">
			<span>
				<xsl:value-of select="position()"/>) En </span>
			<xsl:for-each select="./Descripcion">
				<xsl:apply-templates/>
			</xsl:for-each>
			<span> en fecha </span>
			<xsl:for-each select="./Fecha">
				<xsl:apply-templates/>
			</xsl:for-each>
			<span>&#160;</span>
		</xsl:for-each>
		<xsl:for-each select="./Convocatoria/Medios/Otros_Medios">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="numero-convocatoria">
		<xsl:for-each select="./Convocatoria/Numero_Convocatoria">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="concurrencia-convocatoria">
		<xsl:for-each select="./Convocatoria/Porcentaje_Capital_Asistente">
			<xsl:apply-templates/>
			<span> por ciento del capital social suscrito con derecho a voto </span>
		</xsl:for-each>
		<xsl:for-each select="./Convocatoria/Porcentaje_Socios_Asistentes">
			<span> y el </span>
			<xsl:apply-templates/>
			<span> por ciento de los socios</span>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="voto-favorable">
		<xsl:for-each select="./Acuerdos/Porcentaje_Capital_Acuerdo">
			<xsl:apply-templates/>
			<span> por ciento del capital social suscrito con derecho a voto </span>
		</xsl:for-each>
		<xsl:for-each select="./Acuerdos/Porcentaje_Sociosl_Acuerdo">
			<span> y el </span>
			<xsl:apply-templates/>
			<span> por ciento de los socios</span>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="aprobacion-acta">
		<xsl:for-each select="./Aprobacion_Acta/Fecha">
			<span> el día </span>
			<xsl:apply-templates/>
			<span>&#160;</span>
		</xsl:for-each>
		<xsl:for-each select="./Aprobacion_Acta/Forma">
			<xsl:apply-templates/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="contenido-convocatoria">
		<xsl:for-each select="./Contenido_Convocatoria">
			<table align="left" border="0.5" rules="cols" width="93%" cellpadding="4">
				<tr align="left">
					<th>
						<xsl:apply-templates/>
					</th>
				</tr>
			</table>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="clausula-LOPD">
		<table align="center" border="1" rules="cols" width="93%" cellpadding="4">
			<tr align="left">
				<th>
					<xsl:for-each select="/CORPME-RM-Cese-Nombramiento/Clausula_LOPD">
						<xsl:apply-templates/>
					</xsl:for-each>
				</th>
			</tr>
		</table>
	</xsl:template>
	<xsl:template name="pon-cabecera">
		<xsl:param name="texto"/>
		<tr align="left" class="titulo">
			<th>
				<img src="&ruta_img;activo.gif" border="0" align="middle">&#160; <xsl:value-of select="$texto"/>
				</img>
			</th>
		</tr>
	</xsl:template>
	<xsl:template name="pon-cabecera-2">
		<xsl:param name="texto"/>
		<tr align="left" class="interiorTitulo">
			<th>
				<img src="&ruta_img;activo.gif" border="0" align="middle">&#160; <xsl:value-of select="$texto"/>
				</img>
			</th>
		</tr>
	</xsl:template>
</xsl:stylesheet>
