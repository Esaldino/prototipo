package prototipo.control;

import javafx.scene.control.Spinner;
import prototipo.model.Chapa;
import prototipo.model.Delimitador;
import javafx.scene.control.Control;

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
	
	public void link( Chapa chapa , GestorChapa gc){
		
		
		Delimitador deli = chapa.get();
		Control figura = chapa.getControl();
		
		dx.getValueFactory().setValue( chapa.getLayoutX()+deli.getX());
		dy.getValueFactory().setValue( chapa.getLayoutX()+deli.getY());
		dh.getValueFactory().setValue( deli.getHeight());
		dw.getValueFactory().setValue( deli.getWidth());
		
		dx.setOnMouseClicked( mouseEvent->{
			chapa.setLayoutX((double)dx.getValue()+deli.getX());
			gc.actulizarFigura();
		});
		
		dx.getEditor().setOnKeyReleased( mouseEvent->{
			chapa.setLayoutX((double)dx.getValue()+deli.getX());
			gc.actulizarFigura();
		});
		
		dy.setOnMouseClicked( mouseEvent->{
			chapa.setLayoutY((double)dy.getValue()+deli.getY());
			gc.actulizarFigura();
		});
		
		dy.getEditor().setOnKeyReleased( mouseEvent->{
			chapa.setLayoutY((double)dy.getValue()+deli.getY());
			gc.actulizarFigura();
		});
		
		
		dw.setOnMouseClicked( mouseEvent->{
			deli.setHeight((double)dx.getValue());
			gc.actulizarFigura();
		});
		
		dw.getEditor().setOnKeyReleased( mouseEvent->{
			deli.setHeight((double)dx.getValue());
			gc.actulizarFigura();
		});
		
		dh.setOnMouseClicked( mouseEvent->{
			deli.setWidth((double)dx.getValue());
			gc.actulizarFigura();
		});
		
		dh.getEditor().setOnKeyReleased( mouseEvent->{
			deli.setWidth((double)dx.getValue());
			gc.actulizarFigura();
		});
	
	

	}
}