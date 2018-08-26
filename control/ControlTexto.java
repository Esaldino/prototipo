package prototipo.control;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.beans.property.StringProperty;


public class ControlTexto{
	
	private StringProperty tf;//recebe o textField da interface
	private StringProperty tfControl;
	
	public ControlTexto(StringProperty tf){
		this.tf = tf;
	}
	
	public void link(StringProperty control){
		limpar();
		tfControl = control;
		tfControl.unbind();
		tf.set(tfControl.get());
		tfControl.bind(tf);	
	}
	
	public void limpar(){
		if(tfControl!=null)
			tfControl.unbind();
		tf.set("");
	}
	
}