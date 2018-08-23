package prototipo.model;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.geometry.Bounds;
public class Delimitador extends Rectangle{
	
	private boolean ctr;
	
	public Delimitador( double width, double height){
		super(width, height);
		setStrokeDashOffset(2d);
		getStrokeDashArray().add(5d);
		setFill(Color.TRANSPARENT);
	} 
	
	public double getValueX(){
		return getX();
	}
	
	public double getValueY(){
		return getY();
	}
	
	public boolean isAtivado(){
		return ctr;
	}
	public void ativar(){
		ctr = true;
		setStroke(Color.RED);
	    setCursor(Cursor.MOVE);		
	}
	
	public void desativar(){
		ctr = false;
		setStroke(Color.TRANSPARENT);	
		setCursor(Cursor.HAND);
	}
	
	public void actualiza(Bounds b){
		setX(b.getMinX());
		setY(b.getMinY());
		setWidth( b.getWidth());
		setHeight(b.getHeight());
	}
	
	public void setPontoX(double value){
		setX(value);
	}
	public void setPontoY(double value){
		setY(value);
	}
	
	public void setAltura(double value){
			setHeight(value);
	}
	
	public void setLargura(double value){
			setWidth(value);
	}
	
	
}