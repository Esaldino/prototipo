package prototipo.model;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Control;
import prototipo.control.Util;
import javafx.scene.transform.Rotate;
import javafx.scene.Node;
public class Chapa extends AnchorPane{

	/*public Chapa(){
		
	//	setStyle("-fx-background-color:blue");
	}*/
	public Delimitador get(){
		return (Delimitador) getChildren().get(1);
	}
	
	public int getRotacao(){
		return (int)((Rotate)getControl().getTransforms().get(0)).getAngle();
	}
	public Control getControl(){
		return (Control) getChildren().get(0);
	}

	public void addi(Node...figuras){
		getChildren().addAll(figuras);
	}

	
}