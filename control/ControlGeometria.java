package prototipo.control;

import javafx.scene.control.Spinner;
import prototipo.model.Chapa;
import prototipo.model.Delimitador;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
public class ControlGeometria{
	private Spinner dx;
	private Spinner dy;
	private Spinner dw;
	private Spinner dh;
	private IntegerProperty x;
	
	public IntegerProperty semaforoX(){
		if( semaforoX==null)
			x = new SimpleIntegerProperty();
		return x;
	}
	
	public ControlGeometria(Spinner ... d ){
		dx = d[0];
		dy = d[1];
		dw = d[2];
		dh = d[3];
			//System.out.println(dx);
	}	
	
	public void link( Chapa chapa ){
		Delimitador deli = chapa.get();
		semaforoX.set(0);
		
		dx.getValueFactory().setValue( chapa.getLayoutX()+deli.getX());
		dy.getValueFactory().setValue( chapa.getLayoutX()+deli.getY());
		/*
		chapa.layoutXProperty().addListener(( obs,olv,nw)->{
			dx.getValueFactory().setValue( (double)nw+deli.getX());
		});*/
		
		chapa.layoutXProperty().addListener(( obs,olv,nw)->{
			semaforoX.set(1);
			//dy.getValueFactory().setValue( (double)nw+deli.getY());
		});
	

	}
}