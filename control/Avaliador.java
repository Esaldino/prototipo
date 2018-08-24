package prototipo.control;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import prototipo.model.Chapa;
import javafx.scene.Node;

public class  Avaliador{
	private TextField tf;
	private ControlTexto controlTexto;
	private ControlGeometria controlG;

	public Avaliador( TextField textField){
		tf = textField;
	}
	/*
		Link a textfield texto com os dados das figuras
	*/
	public void setControlTexto(StringProperty tf){
		controlTexto = new ControlTexto(tf);
	}
	
	public void setControlGeometria(Spinner ... dp){
		controlG = new ControlGeometria(dp);
		System.out.println("ativou");
	}
	
	public void testar(Chapa chapa){
		//ativa as proriedades geometria
		controlG.link(chapa);
		//Ativas as outroas propriedades
		Node figura = chapa.getControl();
		if( figura instanceof TextField  ){
			tf.setText("Text-field");
			controlTexto.limpar();
			controlTexto.link( ((TextField)figura).promptTextProperty() );
		}else if( figura instanceof Button ){
			controlTexto.limpar();
			tf.setText("Button");
			controlTexto.link( ((Button)figura).textProperty() );
		}else if( figura instanceof Label ){
			controlTexto.limpar();
			tf.setText("Label");
			controlTexto.link( ((Label)figura).textProperty() );
		}else if( figura instanceof ComboBox ){
			tf.setText("Combo-box");
		}
		
	}
}