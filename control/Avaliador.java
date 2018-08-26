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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Labeled;
import javafx.scene.control.ComboBox;
import prototipo.model.Chapa;
import javafx.scene.Node;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.paint.Color;

public class  Avaliador{
	private TextField tf;
	private ControlTexto controlTexto;
	private ControlGeometria controlG;
	private ControlFonte controlFonte;
	private ControlFundo controlFundo;
	public Avaliador( TextField textField){
		tf = textField;
	}
	/*
		Link a textfield texto com os dados das figuras
	*/
	public void setControlTexto(StringProperty tf){
		controlTexto = new ControlTexto(tf);
	}

	public void setControlFundo(ObjectProperty<Color> obc){
		controlFundo = new ControlFundo(obc);
	}
	
	public void setControlGeometria(Spinner ... dp){
		controlG = new ControlGeometria(dp);
		System.out.println("ativou");
	}

	public void setControlFonte(ObjectProperty<Double> obs,ObjectProperty<String> obf,
							ObjectProperty<FontWeight> obw,ObjectProperty<FontPosture> obp,
							ObjectProperty<Color> obc){
		controlFonte = new ControlFonte(obs,obf,obw,obp,obc);
		System.out.println("ativou Fonte");
	}

	
	public void testar(Chapa chapa,GestorChapa gc){
		//ativa as proriedades geometria
		controlG.link(chapa,gc);
		controlFundo.link(chapa);

		//Ativas as outroas propriedades
		Node figura = chapa.getControl();
		if( figura instanceof TextField  ){
			tf.setText("Text-field");
			controlTexto.link( ((TextField)figura).promptTextProperty() );
			controlFonte.link(chapa,1);
		}else if( figura instanceof Button ){
			tf.setText("Button");
			controlTexto.link( ((Button)figura).textProperty() );
			controlFonte.link(chapa,3);
		}else if( figura instanceof Label ){
			tf.setText("Label");
			controlTexto.link( ((Label)figura).textProperty() );
			controlFonte.link(chapa,3);
		}else if( figura instanceof ComboBox ){
			tf.setText("Combo-box");
			controlFonte.link(chapa,2);
		}
	}
}