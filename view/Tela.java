/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipo.view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;

//prototipo.control
import prototipo.control.Compasso;
import prototipo.control.Util;
import prototipo.control.Tarefa;
//input
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.shape.Circle;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;

import javafx.collections.ObservableList;
import static javafx.collections.FXCollections.observableArrayList;

import javafx.scene.layout.Priority;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import java.util.Arrays;

import java.util.concurrent.Executors;
import javafx.scene.transform.Scale;
import java.util.ArrayList;
import prototipo.control.Avaliador;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import prototipo.control.ControlTela;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.control.ContextMenu;
import javafx.stage.FileChooser;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.Group;
import javafx.scene.text.TextAlignment;
/**
 *
 * @author Esaldino
 */
public class Tela extends Application{
    
    private Compasso cp;
    private Label[] ferramentas;
	private VBox paneBottom;
	private static Stage stagePrincipal;
	private BorderPane bp;
	GridPane grid;
	ColumnConstraints c2;
	RowConstraints r2;
	private VBox vboxRegua;
	private ControlTela ct;
	private HBox hboxRegua;
	private boolean ctrFerramenta;
	Group root;
	private AnchorPane fundo;//REPRESENTA TODA A AREA DE DESENHO, onde de ENCONTRA A FOLHA
    //caminho das imagens das ferramentas
	@Override						   
    public void init(){
       cp =  new Compasso();
	   ct = new ControlTela();
    }
	
	@Override
	public void stop(){
		ct.desligar();
	}
   
    @Override
    public void start( Stage stage ){
		root = new Group();
        bp = new BorderPane();
		root.getChildren().add(bp);
        gerenciarRoot();
		stagePrincipal = stage;
        Scene scene = new Scene( root , 600,400 );
        scene.getStylesheets().addAll(Util.getCss());
		
		bp.prefWidthProperty().bind(scene.widthProperty());
		bp.prefHeightProperty().bind(scene.heightProperty());
		
        stage.setScene(scene);
        stage.show();
		ct.sceneEvent(scene,cp,root);
		
    }
	
	public static Stage getStage(){
		return stagePrincipal;
	}
    
    public void gerenciarRoot(){
		
        bp.setTop( getTop() ); 
        bp.setCenter(getCenter());
        bp.setLeft( paneFerramenta() );
        bp.setRight( getPropriedade() );
		paneBottom = gestorRodape();
        bp.setBottom( paneBottom );
    }
	

	/**
	*
	* CRIA O AMBIENTE DE DESENHO , A TELA E AS REGUAS
	*/
	public ScrollPane getCenter(){
		fundo = new AnchorPane();
        fundo.setId("fundo-id");
        ScrollPane sc = new ScrollPane();
		sc.setFocusTraversable(false);
		
		int tam = 34;
		sc.setVmax(cp.getWidth()-tam);
		sc.setHmax(cp.getHeight()-tam);

        fundo.prefWidthProperty().bind(sc.widthProperty());
        fundo.prefHeightProperty().bind(sc.heightProperty());
        fundo.getChildren().add(cp.getFolha());

		Pane pane = new Pane();
		pane.getStyleClass().add("regua");
		vboxRegua = new VBox();//ergua vertica
		vboxRegua.getStyleClass().add("regua");
		hboxRegua = new HBox();//regua horizontal
		hboxRegua.getStyleClass().add("regua");
		
		c2 = new ColumnConstraints(300, cp.getWidth() ,Double.MAX_VALUE);	
		r2 = new RowConstraints(300,cp.getHeight(),Double.MAX_VALUE);
		ct.scalaEvent(c2,r2,cp);
		ct.tarefasRegua(hboxRegua,vboxRegua,cp);
		ct.setEscala(1d);
		cp.getScala(ct.getEscala());
		grid = new GridPane();
		Scale scale = new Scale();
		scale.xProperty().bind(ct.getEscala());
		scale.yProperty().bind(ct.getEscala());
		System.out.println( Double.MAX_VALUE);
		grid.getTransforms().add(scale);
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setId("grid-center");
		//cria a regua na horizontal
		
		//columnConstraints
	    ColumnConstraints c1 = new ColumnConstraints(tam);
		c1.setHgrow(Priority.NEVER);
		
		c2.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().addAll(c1,c2);
		//rowconstraints
		RowConstraints r1 = new RowConstraints(tam);
		r1.setVgrow(Priority.NEVER);
		
		r2.setVgrow(Priority.ALWAYS);
		grid.getRowConstraints().addAll(r1,r2);
		
		grid.add(pane,0,0);grid.add(vboxRegua,0,1);
		grid.add(hboxRegua,1,0);grid.add(fundo,1,1);
		
		sc.setContent(grid);
		return sc;
	}
	/**
     * cria o menu bar
     * @return 
     */
    public MenuBar getMenuBar(){
        MenuItem open = new MenuItem("Abrir");
        MenuItem nw = new MenuItem("Novo");
        MenuItem save = new MenuItem("Salvar");
        MenuItem saveAs = new MenuItem( "Salvar como" );
        MenuItem close = new MenuItem( "Fechar" );
        
        Menu file = new Menu("Ficheiro");
        file.getItems().addAll( nw, open,save,saveAs , close);
        
        MenuBar barMenu = new MenuBar();
        barMenu.getMenus().add( file );
        return barMenu;
    }
	
	
	public VBox getTop(){
	VBox vbox  = new VBox();
		AnchorPane anchor = new AnchorPane();
		HBox tl = new HBox();
		try{
			String[][] file    = Util.getIcn1();
			Label[] b = new Label[file.length];
			for(int i = 0;i<file.length;i++){
				b[i] = getImage(file[i][0],file[i][1]);
				tl.getChildren().add(b[i]);
			}
			ct.eventosTopFicheiro(b);
		}catch(Exception ex ){
			ex.printStackTrace();
			System.exit(1);
		}

		HBox t2 = new HBox();
		try{
			String[][] file    = Util.getIcn2();
			Label[] b = new Label[file.length];
			for(int i = 0;i<file.length;i++){
				b[i] = getImage(file[i][0],file[i][1]);
				t2.getChildren().add(b[i]);
			}
			ct.eventosTopFiguras(b,cp);
		}catch(Exception ex ){
			ex.printStackTrace();
			System.exit(1);
		}

		anchor.setLeftAnchor(tl,10d);
		anchor.setRightAnchor(t2,10d);
		anchor.getChildren().addAll(tl,t2);
		vbox.getChildren().addAll(getMenuBar(),anchor);
		return vbox;
	}
    
    
    
    public Pane paneFerramenta(){
      VBox vbox = new VBox();
        try{
			String[][] descFile  = Util.getIcn3();
			ferramentas = new Label[descFile.length];
			for(int i = 0;i<descFile.length;i++){
				ferramentas[i] = getImage(descFile[i][0],descFile[i][1]);
				ct.eventoFerramenta(ferramentas[i],i,cp);
            	vbox.getChildren().add(ferramentas[i]);
			}
		}catch(Exception ex ){
			ex.printStackTrace();
			System.exit(1);
		}
        //Rec
        return vbox;
    }

    public Label getImage( String file,String descricao){
        Image image = new Image(file);
        ImageView mv = new ImageView(image);
        mv.setFitHeight(30);
        mv.setFitWidth(30);
        Label label = new Label("",mv);
       	label.setPrefSize(40,30);
        Tooltip tool = new Tooltip(descricao);
        label.setTooltip(tool);
        label.setId("label-id");
        return label;
    }
    
    
    
    public  Pane getPropriedade(){
        VBox vbox = new VBox();
        vbox.getStyleClass().add("barra-prop");
		vbox.setPrefWidth(160);

		Label labelProp = new Label("Propriedade");
		
		//Descriao
		
		GridPane gridToll2 =getGrid(true,5);
		Label labelTipo = getLabel("Tipo");
		TextField tft = new TextField();
		tft.setPrefColumnCount(9);
		
		Avaliador av = new Avaliador(tft);
		cp.setAvalidor( av );
		tft.getStyleClass().add("toll");
		
		Label labelText = getLabel("Texto");
		TextField tftxt = new TextField();
		av.setControlTexto( tftxt.textProperty() );//link
		tftxt.getStyleClass().add("toll");
		
		tftxt.setPrefColumnCount(9);
	    gridToll2.add(labelTipo,0,0);gridToll2.add(tft,1,0);
		gridToll2.add(labelText,0,1);gridToll2.add(tftxt,1,1);
		
		//geometry
		Label labelX = getLabel("X");
		Label labelY = getLabel("Y");

		Spinner tfx = getSpinner(0,cp.getWidth(),0);
		Spinner tfy = getSpinner(0,cp.getWidth(),0);
		Label labelW = getLabel("Largura");
		Label labelH = getLabel("Altura");


		Spinner tfW = getSpinner(0,cp.getWidth(),0);
		Spinner tfH = getSpinner(0,cp.getWidth(),0);
		
		cp.setSpinner(tfx.getValueFactory(),
					tfy.getValueFactory(),
					tfW.getValueFactory(),
					tfH.getValueFactory());
		av.setControlGeometria(tfx,tfy,tfW,tfH);
		GridPane gridToll = getGrid(false,5);
	
		VBox v1 = getTool(labelX,tfx);
		VBox v2 = getTool(labelY,tfy);
		VBox v3 = getTool(labelW,tfW);
		VBox v4 = getTool(labelH,tfH);
		
		gridToll.add(v1,0,0);gridToll.add(v2,1,0);
		gridToll.add(v3,0,1);gridToll.add(v4,1,1);
		

		
		
		//Fundo
		
		Label labelFundo = getLabel("Cor");
		
		ColorPicker colorPiker = getColorPicker();
		av.setControlFundo(colorPiker.valueProperty());
		
		GridPane gridToll3 =getGrid(true,5);
		
		gridToll3.add(labelFundo,0,0);gridToll3.add(colorPiker,1,0);
		
		//Bordas
		
		VBox vBorder = new VBox(8);
		Label labelCor = getLabel("Cor");

		ColorPicker colorPiker1 = getColorPicker();
		Label labelEstilo = getLabel("Estilo");

		ObservableList<String> ob = observableArrayList(Util.codeUTF_8("Sólida"),
																		"Tracejado",
																		"Pontilhada",
																		"Nenhuma");
		ComboBox<String> comboBox = new ComboBox(ob);
		comboBox.getStyleClass().add("toll");		
		comboBox.getSelectionModel().select(0);
		
		Label labelRaddi = getLabel("Radius");

		Slider slider1 = new Slider(0,1,1);
		slider1.getStyleClass().add("toll");
		slider1.setBlockIncrement(0.01);
		slider1.setPrefWidth(90);
		
		Label labelRadiDisplay = getLabel("0");
		ct.eventSlider(slider1,labelRadiDisplay);

		
		HBox hbox1 = new HBox(6);
		hbox1.getChildren().addAll(slider1,labelRadiDisplay);

		Label labelLArgura = getLabel("largura");

		Spinner spinner1 = new Spinner(0,5,1);
		spinner1.getStyleClass().add("toll");
		
		GridPane gridToll4 =getGrid(true,5);
		gridToll4.add(labelCor,0,0);gridToll4.add(colorPiker1,1,0);
		gridToll4.add(labelEstilo,0,1);gridToll4.add(comboBox,1,1);
		gridToll4.add(labelRaddi,0,2);gridToll4.add(hbox1,1,2);
		gridToll4.add(labelLArgura,0,3);gridToll4.add(spinner1,1,3);
		
		ButtonBorders bd = new ButtonBorders();

		vBorder.getChildren().addAll(gridToll4,bd.get());
		
		//font

		Label labelFont = getLabel( "Familia ");
		ObservableList<String> obsf = observableArrayList(Font.getFamilies());
		ComboBox<String> cbfont = new ComboBox(obsf);
		cbfont.getSelectionModel().select(0);

		Label labelSize = getLabel( "Tamanho ");
		Spinner spSize = getSpinner(0,72,12);

		Label labelWeigt = getLabel( "Peso ");
		ObservableList<FontWeight> obsw = observableArrayList(FontWeight.values());
		ComboBox<FontWeight> cb = new ComboBox(obsw);
		cb.getSelectionModel().select(3);

		Label labelEst = getLabel( "Estilo ");
		ObservableList<FontPosture> obse = observableArrayList(FontPosture.values());
		ComboBox<FontPosture> cbEstilo = new ComboBox(obse);
		cbEstilo.getSelectionModel().select(0);

		Label fonteCor = getLabel("Cor");
		ColorPicker cpF = new ColorPicker(Color.BLACK);

		av.setControlFonte(spSize.getValueFactory().valueProperty(),
							cbfont.valueProperty(),
							cb.valueProperty(),
							cbEstilo.valueProperty(),
							cpF.valueProperty());

		GridPane gridFont = getGrid(true,5);
		gridFont.add(labelFont,0,0);gridFont.add(cbfont,1,0);
		gridFont.add(labelSize,0,1);gridFont.add(spSize,1,1);
		gridFont.add(labelWeigt,0,2);gridFont.add(cb,1,2);
		gridFont.add(labelEst,0,3);gridFont.add(cbEstilo,1,3);
		gridFont.add(fonteCor,0,4);gridFont.add(cpF,1,4);

					
		//TilePane
		TitledPane tile1 = getTile("Descricao",gridToll2,true);
		TitledPane tile6 = getTile("Fonte",gridFont,true);
		TitledPane tile2 = getTile("Geomtria",gridToll,true);
		TitledPane tile4 = getTile("Fundo",gridToll3,false);
		TitledPane tile5 = getTile("Bordas",vBorder,false);
	
		ct.eventosTitled(tile1,tile2,tile4,tile5,tile6);
	    vbox.getChildren().addAll(labelProp,tile1,tile2,tile6,tile4,tile5);
		
        return vbox;
    }
	
	public Spinner getSpinner(double min,double max,double pref){
		Spinner spinner = new Spinner(new DoubleSpinnerValueFactory(min,max,pref));
		spinner.setEditable(true);
		spinner.getStyleClass().add("toll");
		return spinner;
	}
    public Label getLabel(String text ){
    	Label label = new Label(text);
    	label.getStyleClass().add("toll-label");
    	return label;
    }
	
	public ColorPicker getColorPicker(){
		ColorPicker colorPicker = new ColorPicker(Color.WHITE);
		colorPicker.setPrefHeight(35);
		return colorPicker;
	}
	
	public TitledPane getTile(String descricao, Node node ,boolean ctr){
		TitledPane titlePane = new TitledPane(descricao,node);
		titlePane.setExpanded(ctr);
		return titlePane;
	}
	
	public GridPane getGrid(boolean ctr,int i){
		
		GridPane gridPane =new GridPane();
		if(ctr){
			ColumnConstraints col1 = new ColumnConstraints(60);
			ColumnConstraints col2 = new ColumnConstraints(110);
			gridPane.getColumnConstraints().addAll(col1,col2);
		}
			
		gridPane.setVgap(i);
		gridPane.setHgap(i);
		return gridPane;
	}
	
	public VBox getTool( Control n1, Control n2){
		VBox v = new VBox();
		v.getChildren().addAll(n1,n2);
		return v; 
	}
	
	public VBox gestorRodape(){
		VBox vbox  = new VBox();
		vbox.getChildren().add(getVisualPosiction());
		return vbox;
	}
	
	
	/*
		Permite localizar um ponto na tela
	*/
	public void localPane(){
		GridPane gridPane = new GridPane();
		Text texto = new Text("Localizador");
		Button texto1 = new Button("Fechar");
		ct.eventButton(texto1,fundo,paneBottom);
		Label labelX = new Label("x");
		Label labelY = new Label("y");
		Spinner tfX = new Spinner(0,cp.getWidth(),0);
		tfX.setEditable(true);
		Spinner tfY = new Spinner(0,cp.getHeight(),0);
		tfY.setEditable(true);
		Button button = new Button("Buscar");
		CheckBox ck1 = new CheckBox("Marcar");
		CheckBox ck2 = new CheckBox("Posicionar");
		ct.eventButton1(button,fundo,ck1,ck2,tfX,tfY);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(ck1,ck2);
		//hbox
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(texto,labelX,tfX,labelY,tfY,button,vbox);
		//columncontraints
		ColumnConstraints col1 = new ColumnConstraints(100,400,Integer.MAX_VALUE);
		col1.setHgrow(Priority.ALWAYS);
		ColumnConstraints col2 = new ColumnConstraints(100);
		gridPane.getColumnConstraints().addAll(col1,col2);
		//add
		gridPane.add(hbox,0,0);
		gridPane.setHalignment(texto1, HPos.RIGHT);
		gridPane.setMargin(texto1, new Insets(4));
		gridPane.setValignment(texto1, VPos.CENTER);
		gridPane.add(texto1,1,0);
		if( paneBottom.getChildren().size()>1 )
			paneBottom.getChildren().remove(0);
		paneBottom.getChildren().add(0,gridPane);
	}
	
    
    
    public Pane getVisualPosiction(){	
        AnchorPane anhor = new AnchorPane();
        HBox hbox = new HBox(10);
        Label l1 = new Label("WIDTH: " + cp.getWidth());
        Label l2 = new Label("HEGHT:  " + cp.getHeight());
        Label l3 = new Label("X: ");
        Label l4 = new Label("Y: ");
        Label l5 = new Label();
        l5.textProperty().bind(cp.getX() );
        Label l6 = new Label();
        l6.textProperty().bind(cp.getY() );
        hbox.getChildren().addAll(l1,l2);
        hbox.getChildren().addAll(l3,l5,l4,l6);
        anhor.getChildren().add(hbox);
        return anhor;
    }
	/*
    
    private class EventosFerramenta implements EventHandler<MouseEvent>{
		
        @Override
        public void handle(MouseEvent me){ 
			System.out.println( "Clicou na ferramenta" );
            if( me.getButton()==MouseButton.PRIMARY){
                Label labelSource = (Label)me.getSource();
                laco:for( int i=0; i<ferramentas.length;i++){
                    if(labelSource == ferramentas[i]){
                        switch(i){
                            case 0:criarFiguraBotao();break;
                            case 1:criarFiguraLabel();break;
                            case 2:criarFiguraCheckBox();break;
                            case 3:criarFiguraTextField();break;
                            case 4:criarFiguraCombo();break;
                            case 5:criarFiguraRadio();break;
                            case 6:criarFiguraImage();break;
                            case 7:criarFiguraRect();break;
                            case 8:criarFiguraLine();break;
                            case 9:criarFiguraCircle();break;
							case 10:localPane();break;
                        }
                        break laco;
                    }
                }
            }
        }
	}
	
	public void criarFiguraBotao() {
        Button button = new Button("Button");
		
		button.setPrefSize(80,50);
		button.setFocusTraversable(false);

		MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);
        button.setId("node");
        cp.criar(button,menuContexto);
    }

    public void criarFiguraLabel() {
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
        cp.criar(label,menuContexto);
    }
    
    public void criarFiguraCircle() {
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

        cp.criar(labelCirco,menuContexto);
    }
     
    public void criarFiguraRect() {
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
        cp.criar(rect,menuContexto);
    }
    
    public void criarFiguraLine() {
        Separator linha = new Separator();
        linha.setOrientation(Orientation.HORIZONTAL);

          MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);

        cp.criar(linha,menuContexto);
    }
    
    public void criarFiguraCombo() {
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

        cp.criar(combo,menuContexto);
    }
     
    public void criarFiguraRadio() {
        RadioButton radio = new RadioButton("radio");
		radio.setPrefSize(100,50);
        radio.setId("node");
		radio.setFocusTraversable(false);
        radio.setSelected(true);

        MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->cp.apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem2);

        cp.criar(radio,menuContexto);
    }
     
    public void criarFiguraCheckBox() {
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
        cp.criar(ck,menuContexto);
    }
    
    
    public void criarFiguraTextField(){
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

        cp.criar(tf,menuContexto);
    }
    
    public void criarFiguraImage(){
        Image image = new Image( getClass().getResourceAsStream("../ficheiro/image/photos1.png"));
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
        		String dados =Util.processaImage(fs.showOpenDialog(stagePrincipal));
        		if(!dados.isEmpty()){
        			Image image1 = new Image(dados);
        			mv.setImage(image1);
        		}
        		
        });
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem3,menuItem2);

        cp.criar(labelImage,menuContexto);
    }
*/

}
    
   
