package prototipo.model;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Control;
import prototipo.control.Util;
import javafx.scene.Node;
public class Chapa extends Group{

	
	public Delimitador get(){
		return (Delimitador) getChildren().get(1);
	}
	
	public Control getControl(){
		return (Control) getChildren().get(0);
	}
	
/*	public void definirBorda(int valueBorder, int raddii,Color cor){
		setBorder(Util.bordas(cor,raddii,valueBorder));
	}*/
	public void addi(Node...figuras){
		getChildren().addAll(figuras);
	}
	
}