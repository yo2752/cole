<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="html">
	<html>
		<body>
			<h3 class="support">
				<span style="color: rgb(255, 255, 255); 
						text-shadow: -1px 0px rgb(0, 0, 0), 1px 0px rgb(0, 0, 0), 0px 1px rgb(0, 0, 0), 0px -1px rgb(0, 0, 0); 
						font-weight: normal; font-size: 1.8em; font-family: Arial;">Status OEGAM
				</span>
			</h3>
			<div style="text-align: left; padding-left: 20px; color: white; display: table" id="status_oegam_interno">
				<div style="display: table-row;">
					<div style="display: table-cell;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[1]/tr[2]/td[1]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[1]/tr[2]/td[3]/strong"/>
					</div>
					<div style="display: table-cell; padding-left: 30px;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[2]/tr[2]/td[1]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[2]/tr[2]/td[3]/strong"/>
					</div>
				</div>
				<div style="display: table-row;">
					<div style="display: table-cell;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[1]/tr[2]/td[4]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[1]/tr[2]/td[6]/strong"/>
					</div>
					<div style="display: table-cell; padding-left: 30px;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[2]/tr[2]/td[4]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[2]/tr[2]/td[6]/strong"/>
					</div>
				</div>
				<div style="display: table-row;">
					<div style="display: table-cell;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[1]/tr[2]/td[7]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[1]/tr[2]/td[9]/strong"/>
					</div>
					<div style="display: table-cell; padding-left: 30px;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[2]/tr[2]/td[7]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[2]/tr[2]/td[9]/strong"/>
					</div>
				</div>
				<div style="display: table-row;">
					<div style="display: table-cell;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[1]/tr[2]/td[10]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[1]/tr[2]/td[12]/strong"/>
					</div>
					<div style="display: table-cell; padding-left: 30px;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[2]/tr[2]/td[10]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[2]/tr[2]/td[12]/strong"/>
					</div>
				</div>
				<div style="display: table-row;">
					<div style="display: table-cell;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[2]/tr[2]/td[13]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[2]/tr[2]/td[15]/strong"/>
					</div>
					<div style="display: table-cell; padding-left: 30px;">
						<img>
							<xsl:attribute name="src"><xsl:value-of select="body/table[2]/tr[2]/td[16]/img/@src"/>
							</xsl:attribute>
							<xsl:attribute name="style">vertical-align: middle;</xsl:attribute>
						</img>

						<xsl:value-of select="body/table[2]/tr[2]/td[18]/strong"/>
					</div>
				</div>
			</div>
		</body>
	</html>
	</xsl:template>

</xsl:stylesheet>