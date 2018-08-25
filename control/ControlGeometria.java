package prototipo.control;

import javafx.scene.control.Spinner;
import prototipo.model.Chapa;
import prototipo.model.Delimitador;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import java.util.function.DoubleConsumer;

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
	}	
	
	public void link( Chapa chapa , GestorChapa gc){
		
		
		Delimitador deli = chapa.get();
		Control figura = chapa.getControl();
		
		dx.getValueFactory().setValue( chapa.getLayoutX()+deli.getX());
		dy.getValueFactory().setValue( chapa.getLayoutX()+deli.getY());
		dh.getValueFactory().setValue( deli.getHeight());
		dw.getValueFactory().setValue( deli.getWidth());

		DoubleConsumer dcw = (value)->{
			switch(chapa.getRotacao()){
				case 180:figura.setLayoutX(value);
				case 0:
				case 360:deli.setWidth(value);
					    figura.setPrefWidth(value);
					    break;
				case 90:deli.setWidth(value);
						figura.setLayoutX(value);
						figura.setPrefHeight(value);
						break;
				case 270:deli.setWidth(value);
						figura.setPrefHeight(value);
			}	
		};

		DoubleConsumer dch = (value)->{
			switch(chapa.getRotacao()){
				case 180:figura.setLayoutY(value);
				case 0:
				case 360:deli.setHeight(value);
					    figura.setPrefHeight(value);
					    break;
				case 90:deli.setHeight(value);
						figura.setPrefWidth(value);
						break;
				case 270:figura.setLayoutY(value);
						deli.setHeight(value);
						figura.setPrefWidth(value);
			}	
		};
		
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
			dcw.accept((double)dw.getValue());
			gc.actulizarFigura();
		});
		
		dw.getEditor().setOnKeyPressed( keyEvent->{
			if( keyEvent.getCode() != KeyCode.ENTER )
				return;
			dcw.accept((double)dw.getValue());
			gc.actulizarFigura();
		});
		
		dh.setOnMousePressed( mouseEvent->{
			if( mouseEvent.getButton()!=MouseButton.PRIMARY)
				return;
			dch.accept((double)dh.getValue());
			gc.actulizarFigura();
		});
		
		dh.getEditor().setOnKeyPressed( keyEvent->{
			if( keyEvent.getCode() != KeyCode.ENTER )
				return;
			dch.accept((double)dh.getValue());
			gc.actulizarFigura();
		});
	
	

	}
}