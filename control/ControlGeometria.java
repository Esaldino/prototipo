package prototipo.control;

import javafx.scene.control.Spinner;
import prototipo.model.Chapa;
import prototipo.model.Delimitador;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

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
		
		dx.setOnMousePressed( mouseEvent->{
			if( mouseEvent.getButton()!=MouseButton.PRIMARY)
				return;
			chapa.setLayoutX((double)dx.getValue()+deli.getX());
			gc.actulizarFigura();
		});
		
		dx.getEditor().setOnKeyPressed( keyEvent->{
			if( keyEvent.getCode() != KeyCode.ENTER )
				return;
			chapa.setLayoutX((double)dx.getValue()+deli.getX());
			gc.actulizarFigura();
		});
		
		dy.setOnMousePressed( mouseEvent->{
			if( mouseEvent.getButton()!=MouseButton.PRIMARY)
				return;
			chapa.setLayoutY((double)dy.getValue()+deli.getY());
			gc.actulizarFigura();
		});
		
		dy.getEditor().setOnKeyPressed( keyEvent->{
			if( keyEvent.getCode() != KeyCode.ENTER )
				return;
			chapa.setLayoutY((double)dy.getValue()+deli.getY());
			gc.actulizarFigura();
		});
		
		
		dw.setOnMousePressed( mouseEvent->{
			if( mouseEvent.getButton()!=MouseButton.PRIMARY)
				return;
			deli.setHeight((double)dx.getValue());
			gc.actulizarFigura();
		});
		
		dw.getEditor().setOnKeyPressed( keyEvent->{
			if( keyEvent.getCode() != KeyCode.ENTER )
				return;
			deli.setHeight((double)dx.getValue());
			gc.actulizarFigura();
		});
		
		dh.setOnMousePressed( mouseEvent->{
			if( mouseEvent.getButton()!=MouseButton.PRIMARY)
				return;
			deli.setWidth((double)dx.getValue());
			gc.actulizarFigura();
		});
		
		dh.getEditor().setOnKeyPressed( keyEvent->{
			if( keyEvent.getCode() != KeyCode.ENTER )
				return;
			deli.setWidth((double)dx.getValue());
			gc.actulizarFigura();
		});
	
	

	}
}