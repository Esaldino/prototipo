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
import javafx.scene.control.Control;
import javafx.scene.control.ComboBox;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

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
	
	public void setControlGeometria(DoubleProperty ... dp){
		controlG = new ControlGeometria(dp);
	}
	
	public void testar(Control node){

		if( node instanceof TextField  ){
			tf.setText("Text-field");
			controlTexto.limpar();
			controlTexto.link( ((TextField)node).promptTextProperty() );
		}else if( node instanceof Button ){
			controlTexto.limpar();
			tf.setText("Button");
			controlTexto.link( ((Button)node).textProperty() );
		}else if( node instanceof Label ){
			controlTexto.limpar();
			tf.setText("Label");
			controlTexto.link( ((Label)node).textProperty() );
		}else if( node instanceof ComboBox ){
			tf.setText("Combo-box");
		}
		
	}
}