package prototipo.control;

import javafx.scene.control.Spinner;
import prototipo.model.Chapa;
import prototipo.model.Delimitador;
public class ControlGeometria{
	private Spinner dx;
	private Spinner dy;
	private Spinner dw;
	private Spinner dh;
	
	public ControlGeometria(Spinner ... d ){
		dx = d[0];
		dy = d[1];
		dw = d[2];
		dh = d[3];
		
		//System.out.println(dx);
	}	
	
	public void link( Chapa chapa ){
		Delimitador deli = chapa.get();
	/*	
		dx.getValueFactory().setValue( chapa.getLayoutX()+deli.getX());
		dy.getValueFactory().setValue( chapa.getLayoutX()+deli.getY());
		
		chapa.layoutXProperty().addListener(( obs,olv,nw)->{
			dx.getValueFactory().setValue( (double)nw+deli.getX());
		});
		
		chapa.layoutYProperty().addListener(( obs,olv,nw)->{
			dy.getValueFactory().setValue( (double)nw+deli.getY());
		});
		
		dx.valueProperty().addListener( ( obs,olv,nw)->{
			chapa.setLayoutX( (double)nw+deli.getX() );
		});
*/
	}
}