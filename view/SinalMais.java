package prototipo.view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
public class SinalMais extends Pane{
	
	private Line Hline,Vline;
	
	public SinalMais(){
		setPrefSize(50,23);
		
		getChildren().addAll(getHline(),getVline());
	}
	
	public SinalMais(int w,int h){
		setPrefSize(w,h);
		getChildren().addAll(getHline(),getVline());
		
	}
	
	public void setColor(Color cor){
		Hline.setStroke(cor);
		Vline.setStroke(cor);
	}
	
	public void getTypeDash(){
		Hline.getStrokeDashArray().addAll(2d,2d,2d);
		Vline.getStrokeDashArray().addAll(2d,2d,2d);
	}
	
	private Line getHline(){
		Hline = new Line();
		Hline.setStartX(0);
		Hline.startYProperty().bind(heightProperty().divide(2));
		Hline.setStroke(Color.rgb(100,100,100));
		Hline.endXProperty().bind(widthProperty());
		Hline.endYProperty().bind(heightProperty().divide(2));
		return Hline;
	}
	
	private Line getVline(){
		Vline = new Line();
		Vline.setStartY(0);
		Vline.startXProperty().bind(widthProperty().divide(2));
		Vline.setStroke(Color.rgb(100,100,100));
		Vline.endXProperty().bind(widthProperty().divide(2));
		Vline.endYProperty().bind(heightProperty());
		return Vline;
	}

}