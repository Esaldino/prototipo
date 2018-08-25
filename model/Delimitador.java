package prototipo.model;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.shape.Circle;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
public class Delimitador extends Rectangle{
	
	private boolean ctr;
	private Circle[] circle;
	
	public Delimitador( double width, double height){
		super(width, height);
	//	setStrokeDashOffset(2d);
	//	getStrokeDashArray().add(5d);
		setFill(Color.TRANSPARENT);
	} 
	
	public void pontosTrans(int tam){
		circle = new Circle[tam];
		//NW
		circle[0] = criaCircle();
		circle[0].setRadius(4);
		
		circle[0].centerXProperty().bind( xProperty() );
		circle[0].centerYProperty().bind( yProperty() );
		//NE
		
		circle[1] = criaCircle();
		
		circle[1].setRadius(4);
		circle[1].centerXProperty().bind( xProperty().add( widthProperty() ) );
		circle[1].centerYProperty().bind( yProperty() );
		//SW
		circle[2] = criaCircle();
		circle[2].setRadius(4);
		
		circle[2].centerXProperty().bind( xProperty() );
		circle[2].centerYProperty().bind( yProperty().add( heightProperty() ) );
		//SE
		circle[3] = criaCircle();
		circle[3].setRadius(4);
		
		circle[3].centerXProperty().bind( xProperty().add( widthProperty() ) );
		circle[3].centerYProperty().bind( yProperty().add( heightProperty() ) );
		
		//LEFT
		circle[4] = criaCircle();
		circle[4].setRadius(4);
		
		circle[4].centerXProperty().bind( xProperty() );
		circle[4].centerYProperty().bind( yProperty().add( heightProperty().divide(2) ) );
		//RIGHT
		circle[5] = criaCircle();
		circle[5].setRadius(4);
		
		circle[5].centerXProperty().bind( xProperty().add( widthProperty() ) );
		circle[5].centerYProperty().bind( yProperty().add( heightProperty().divide(2)) );
		//top
		circle[6] = criaCircle();
		circle[6].setRadius(4);
		
		circle[6].centerXProperty().bind( xProperty().add( widthProperty().divide(2) ) );
		circle[6].centerYProperty().bind( yProperty() );
		//bottom
		circle[7] = criaCircle();
		circle[7].setRadius(4);
		
		circle[7].centerXProperty().bind( xProperty().add( widthProperty().divide(2) ) );
		circle[7].centerYProperty().bind( yProperty().add( heightProperty() ) );
	}
	
	public Circle criaCircle(){
		Circle c = new Circle();
		c.setFill(Color.WHITE);
		return c;
	}
	
	
	
	public void corCircle(Color ...cor){
		for(Circle c:circle){
			if(cor.length==2){
				c.setFill(cor[1]);
			}else{
				c.setCursor(Cursor.HAND);
				c.setFill(cor[0]);
			}
			c.setStroke(cor[0]);
		}
		if(cor.length==2){
			circle[0].setCursor( Cursor.NW_RESIZE );
			circle[1] .setCursor(Cursor.NE_RESIZE  );
			circle[2].setCursor( Cursor.SW_RESIZE );
			circle[3].setCursor( Cursor.SE_RESIZE );
			circle[4].setCursor( Cursor.H_RESIZE );
			circle[5].setCursor( Cursor.H_RESIZE );
			circle[6].setCursor( Cursor.V_RESIZE );
			circle[7].setCursor( Cursor.V_RESIZE );
		}

	}
	
	public Circle[] getCircles(){
		return circle;
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
		corCircle(Color.RED,Color.WHITE);
		setStroke(Color.RED);
	    setCursor(Cursor.MOVE);		
	}
	
	public void desativar(){
		ctr = false;
		corCircle(Color.TRANSPARENT);
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