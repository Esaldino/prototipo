package prototipo.model;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Control;
import prototipo.control.Util;
import javafx.scene.Node;
public class Chapa extends AnchorPane{

	public Chapa(){
		
		setStyle("-fx-background-color:blue");
		setPrefSize(150,100);
	}
	public Delimitador get(){
		return (Delimitador) getChildren().get(1);
	}
	
	public Control getControl(){
		return (Control) getChildren().get(0);
	}

	public void addi(Node...figuras){
		getChildren().addAll(figuras);
	}

	
}