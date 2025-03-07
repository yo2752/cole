package utilidades.informes;

import java.awt.Font;

import net.sourceforge.barbecue.env.Environment;

public class EnvironmentDefaultOegam implements Environment {
	public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 20);

	@Override
	public int getResolution() {
		return 72;
	}

	@Override
	public Font getDefaultFont() {
		return DEFAULT_FONT;
	}

}
