package prototipo.control;

import javafx.beans.property.DoubleProperty;

public class ControlGeometria{
	private DoubleProperty dx;
	private DoubleProperty dy;
	private DoubleProperty dw;
	private DoubleProperty dh;
	
	public ControlGeometria(Doubleproperty ... d ){
		dx = d[0];
		dy = d[1];
		dw = d[2];
		dh = d[3];
	}	
}