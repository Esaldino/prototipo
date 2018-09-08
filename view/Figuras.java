package prototipo.view;


import javafx.stage.FileChooser;
//layout
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderStrokeStyle;
//components
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Menu;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

import javafx.geometry.Orientation;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ColorPicker;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;

//prototipo.control
import prototipo.control.Compasso;
import prototipo.control.Util;
import javafx.scene.control.Control;

import java.util.HashMap;

public class Figuras {
	
	
	private static ContextMenu menuCtext;

	public static ContextMenu getMenu(){
		return menuCtext;
	}
	
	
	public static void setMenu(ContextMenu cm){
		menuCtext = cm;
	}
	
	
	public static Control get(int pos,Compasso cp){
		Control figura = null;
		switch(pos){
			case 0:figura=botao(cp);break;
			case 1:figura=label(cp);break;
			case 2:figura=ckeckbox(cp);break;
			case 3:figura=textfield(cp);break;
			case 4:figura=combo(cp);break;
			case 5:figura=radio(cp);break;
			case 6:figura=image(cp);break;
			case 7:figura=rectangulo(cp);break;
			case 8:figura=linha(cp);break;
			case 9:figura=circlo(cp);break;
		}
		return figura;
	}
	
	public static Button botao(Compasso cp) {
        Button button = new Button("Button");
		button.setPrefSize(80,50);
		button.setFocusTraversable(false);
        button.setId("node");
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
        return button;
    }

    public static Label label(Compasso cp) {
        Label label = new Label("Texto");
		label.setPrefSize(50,30);
		label.setFocusTraversable(false);
        label.setId("node");
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
        return label;
    }
    
    public static Label circlo(Compasso cp) {
        Label labelCirco = new Label();
        labelCirco.setBackground(Util.fundo(Color.RED,100));
		labelCirco.setPrefSize(50,50);
		labelCirco.setFocusTraversable(false);
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
		return labelCirco;
    }
     
    public static Label rectangulo(Compasso cp) {
        Label rect = new Label();
		rect.setPrefSize(40,50);
        rect.setBackground(Util.fundo(Color.GRAY,0));
		rect.setFocusTraversable(false);
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
		return rect;
    }
    
   public static Label linha(Compasso cp) {
        Separator linha = new Separator();
		 Label labelLina = new Label("",linha);
        linha.setOrientation(Orientation.HORIZONTAL);
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
		return labelLina;
    }
    
    public static ComboBox combo(Compasso cp) {
        ComboBox<String> combo = new ComboBox<>();
        combo.setValue("comboBox");
		combo.setPrefSize(90,40);
        combo.setId("node");
        combo.setEditable(false);
		combo.setFocusTraversable(false);
        combo.getSelectionModel().selectFirst();
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
		return combo;
    }
	
	
     
    public static RadioButton radio(Compasso cp) {
        RadioButton radiob = new RadioButton("radio");
		radiob.setPrefSize(100,50);
        radiob.setId("node");
		radiob.setFocusTraversable(false);
        radiob.setSelected(true);
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
		return radiob;
    }
     
    public static CheckBox ckeckbox(Compasso cp) {
        CheckBox ck = new CheckBox("check-box");
		ck.setPrefSize(100,60);
		ck.setFocusTraversable(false);
        ck.setId("node");
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
		return ck;

    }
    
    
    public static Label textfield(Compasso cp){
    	Label tf = new Label();
		tf.setPrefSize(130,43);
        tf.setBackground(Util.fundo(Color.WHITE,0));
		tf.setFocusTraversable(false);
        tf.setId("node");
		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
		setMenu(menuContexto);
		return tf;
    }
    
	
	
    public static Label image(Compasso cp){
		
       Image image = new Image( Figuras.class.getResourceAsStream("../ficheiro/image/photos1.png"));
        ImageView mv = new ImageView(image);
        mv.setId("node");
        mv.setFitHeight(60);
        mv.setFitWidth(100);
        Label labelImage = new Label("",mv);
        labelImage.setPrefSize(100,60);
        labelImage.setId("node");
        mv.fitWidthProperty().bindBidirectional(labelImage.prefWidthProperty());
        mv.fitHeightProperty().bindBidirectional( labelImage.prefHeightProperty());
		
        MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
        MenuItem menuItem3 = new MenuItem("Alterar foto");
        menuItem3.setOnAction(actionEvent->{
        		FileChooser fs = new FileChooser();
        		String dados =Util.processaImage(fs.showOpenDialog(Tela.getStage()));
        		if(!dados.isEmpty()){
        			Image image1 = new Image(dados);
        			mv.setImage(image1);
        		}
        		
        });
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem3,menuItem2);
        setMenu(menuContexto);

		return labelImage;

    }
}