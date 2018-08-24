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
import prototipo.model.Figura;
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
import java.util.concurrent.ExecutorService;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.StringConverter;
import prototipo.control.Avaliador;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import prototipo.control.ControlTela;
/**
 *
 * @author Esaldino
 */
public class Tela extends Application{
    
    private Compasso cp;
    private Label[] ferramentas;
	private VBox paneBottom;
	private Stage stagePrincipal;
	private BorderPane root;
//	private ExecutorService executor;
	//private Tarefa tarefa;
	GridPane grid;
	ColumnConstraints c2;
	RowConstraints r2;
	private VBox vboxRegua;
	private HBox hboxRegua;
	private AnchorPane fundo;//REPRESENTA TODA A AREA DE DESENHO, onde de ENCONTRA A FOLHA
	private DoubleProperty escala;
    //caminho das imagens das ferramentas
    private String[] imagens = {
                        "icons8-button-50.png",
                        "icons8-font-size-filled-50.png",
                        "icons8-checked-checkbox-50.png",
                        "icons8-text-box-filled-50.png",
                        "icons8-dropdown-field-50.png",
                        "icons8-unchecked-radio-button-filled-50.png",
                        "icons8-xlarge-icons-filled-50.png",
                        "icons8-unchecked-checkbox-filled-50.png",
                        "icons8-horizontal-line-filled-50.png",
                        "icons8-circle-filled-50.png",
						"localizadorPoint.png"
                        };
    //decricao dos tooltips das ferramentas
    private String[] descricao = {
                                    "Botão",
                                    "Rótulo",
                                    "Checkbox",
                                    "Campo texto",
                                    "Combo box",
                                    "Radio box",
                                    "Imagem",
                                    "Rectângulo",
                                    "Linha",
                                    "Círculo",
									"Localizador"
                               };
	String[] descFile;
	String[] file;
	@Override						   
    public void init(){
		
	//	executor = Executors.newCachedThreadPool();
        cp =  new Compasso();
		//tarefa = new Tarefa();
		try{
			descFile = Util.getDescricao(getClass().getResource("nota/desc1.txt").toURI());
			file     = Util.getFile(getClass().getResource("icones").toURI());
		}catch(Exception ex ){
			ex.printStackTrace();
			System.exit(1);
		}
		ControlTela ct = new ControlTela(cp);
    }
	
	@Override
	public void stop(){
		ct.desligar();
	}
   
    @Override
    public void start( Stage stage ){
        root = new BorderPane();
        gerenciarRoot(root);
		stagePrincipal = stage;
        Scene scene = new Scene( root , 600,400 );
        scene.getStylesheets().addAll( 
									getClass().getResource("estilo/mainfx.css").toExternalForm(),
									getClass().getResource("estilo/regua.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
		ct.sceneEvent(scene);

		
    }
    
    public void gerenciarRoot(BorderPane root){
        root.setTop( getTop() ); 
        root.setCenter(getCenter());
        root.setLeft( paneFerramenta() );
        root.setRight( getPropriedade() );
		paneBottom = gestorRodape();
        root.setBottom( paneBottom );
    }
	

	/**
	*
	* CRIA O AMBIENTE DE DESENHO , A TELA E AS REGUAS
	*/
	public ScrollPane getCenter(){
		fundo = new AnchorPane();
		//fundo.setPrefSize(2000,2000);
		//fundo.setPadding(new Insets(10));
        fundo.setId("fundo-id");
        ScrollPane sc = new ScrollPane();
		
		int tam = 34;
		sc.setVmax(cp.getWidth()-tam);
		sc.setHmax(cp.getHeight()-tam);
		
		

		sc.setFocusTraversable(false);
		
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
		
		escala = new SimpleDoubleProperty();
		cp.scalaEvent(escala,c2,r2);
	/*	escala.addListener( (obsv,olv,nv)->{
			c2.setPrefWidth( cp.getWidth()*(double)nv  );
			r2.setPrefHeight( cp.getWidth()*(double)nv   );
		});*/
		
		ct.tarefasRegua(hboxRegua,vboxRegua);
	/*	Task<ArrayList<BorderPane>> task1 = tarefa.reguaH(cp.getWidth());
			task1.stateProperty().addListener((obs,old,nw)->{
				if(nw==State.SUCCEEDED ){
					hboxRegua.getChildren().addAll( task1.getValue() );
				}
			});
			executor.submit(task1);
			//cria a regua na vertical
		Task<ArrayList<BorderPane>> task2 = tarefa.reguaV(cp.getWidth());
			task2.stateProperty().addListener((obs,old,nw)->{
				if(nw==State.SUCCEEDED ){
					vboxRegua.getChildren().addAll( task2.getValue() );
				}
			});
			executor.submit(task2);*/
			
		escala.set(1d);
		grid = new GridPane();
		Scale scale = new Scale();
		scale.xProperty().bind(escala);
		scale.yProperty().bind(escala);
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
		
		FlowPane tl = new FlowPane(10,2);
	
		Label[] b = new Label[file.length];
		for(int i = 0;i<file.length;i++){
			b[i] = getImageMenu(file[i],descFile[i]);
			tl.getChildren().add(b[i]);
		}
		
		ct.eventosTop(b);
		b[5].setOnMouseClicked(actionEvent->escala.set(escala.get()+0.5));

		b[6].setOnMouseClicked(actionEvent->escala.set(escala.get()-0.5));

		vbox.getChildren().addAll(getMenuBar(),tl);
		
		return vbox;
	}
    
    
    
    public Pane paneFerramenta(){
        VBox vbox = new VBox();
        ferramentas = new Label[imagens.length];
        EventosFerramenta eventos = new EventosFerramenta();
        for(int i=0; i<imagens.length;i++){
            ferramentas[i] = getImagem(imagens[i],Util.codeUTF_8(descricao[i]));
            ferramentas[i].setOnMouseClicked(eventos);
            vbox.getChildren().add(ferramentas[i]);
        }
        //Rec
        return vbox;
    }

    public Label getImageMenu( String file,String descricao){
        Image image = new Image(file);
        ImageView mv = new ImageView(image);
        Label label = new Label("",mv);
        label.setPrefSize(50,50);
        Tooltip tool = new Tooltip(descricao);
        label.setTooltip(tool);
        label.setId("label-id");
        return label;
    }
    
    public Label getImagem( String url,String descricao){
		url= "image/"+url;
        Image image = new Image(getClass().getResourceAsStream(url));
        ImageView mv = new ImageView(image);
        Label label = new Label("",mv);
        Tooltip tool = new Tooltip(descricao);
        label.setTooltip(tool);
        label.setId("label-id");
        return label;
    }
    
    
    public  Pane getPropriedade(){
        VBox vbox = new VBox();
        vbox.getStyleClass().add("barra-prop");
		vbox.setPrefWidth(230);

		Label labelProp = new Label("Propriedade");
		
		//Descriao
		
		GridPane gridToll2 =getGrid(true);
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

		Spinner tfx = getSpinner();
		Spinner tfy = getSpinner();
		
		Label labelW = getLabel("Largura");
		Label labelH = getLabel("Altura");


		Spinner tfW = getSpinner();
		Spinner tfH = getSpinner();
		
		
		av.setControlGeometria(tfx,tfy,tfW,tfH);
		GridPane gridToll = getGrid(false);
	
		VBox v1 = getTool(labelX,tfx);
		VBox v2 = getTool(labelY,tfy);
		VBox v3 = getTool(labelW,tfW);
		VBox v4 = getTool(labelH,tfH);
		
		gridToll.add(v1,0,0);gridToll.add(v2,1,0);
		gridToll.add(v3,0,1);gridToll.add(v4,1,1);
		

		
		
		//Fundo
		
		Label labelFundo = getLabel("Cor");
		
		ColorPicker colorPiker = getColorPicker();
		
		Label labelOpacity = getLabel("Opacidade");

		Slider slider = new Slider(0,1,1);
		slider.setPrefWidth(90);
		Label labelOpacityDisplay = getLabel("0");

		slider.setBlockIncrement(0.01);

		slider.valueProperty().addListener( (obs,ol,nw)->{
			System.out.println();
			int valor = (int)((double)nw * 100);
			labelOpacityDisplay.setText(String.format("%d",valor)+"%");
		});
		
		HBox hbox = new HBox(6);
		hbox.getChildren().addAll(slider,labelOpacityDisplay);
		
		GridPane gridToll3 =getGrid(true);
		
		gridToll3.add(labelFundo,0,0);gridToll3.add(colorPiker,1,0);
		gridToll3.setValignment( hbox, VPos.CENTER);
		gridToll3.setHalignment(hbox,HPos.RIGHT);
		gridToll3.add(labelOpacity,0,1);gridToll3.add(hbox,1,1);
		
		//Bordas
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


		slider1.valueProperty().addListener( (obs,ol,nw)->{
			int valor = (int)((double)nw * 100);
			labelRadiDisplay.setText(String.format("%d",valor)+"%");
		});
		
		HBox hbox1 = new HBox(6);
		hbox1.getChildren().addAll(slider1,labelRadiDisplay);

		Label labelLArgura = getLabel("largura");

		Spinner spinner1 = new Spinner(0,5,1);
		spinner1.getStyleClass().add("toll");
		
		GridPane gridToll4 =getGrid(true);
		gridToll4.setValignment( hbox, VPos.CENTER);
		gridToll4.setHalignment(hbox,HPos.RIGHT);
		gridToll4.add(labelCor,0,0);gridToll4.add(colorPiker1,1,0);
		gridToll4.add(labelEstilo,0,1);gridToll4.add(comboBox,1,1);
		gridToll4.add(labelRaddi,0,2);gridToll4.add(hbox1,1,2);
		gridToll4.add(labelLArgura,0,3);gridToll4.add(spinner1,1,3);
		//Rotacao
		Label labelAngle = getLabel(Util.codeUTF_8("Ângulo"));

		Spinner sp = new Spinner(-360,360,0,90);
		sp.getStyleClass().add("toll");
		
		GridPane gridToll5 =getGrid(true);
		gridToll5.add(labelAngle,0,0);gridToll5.add(sp,1,0);
		//font

		Label labelFont = getLabel( "Familia ");
		ObservableList<String> obsf = observableArrayList("Arial","Carier");
		ComboBox cbfont = new ComboBox(obsf);

		Label labelSize = getLabel( "Tamanho ");
		Spinner spSize = new Spinner(1,100,12);

		Label labelWeigt = getLabel( "Peso ");
		ObservableList<String> obsw = observableArrayList("Normal","Negrito","Medio");
		ComboBox cb = new ComboBox(obsw);

		Label labelEst = getLabel( "Estilo ");
		ObservableList<String> obse = observableArrayList("Italico","Regular");
		ComboBox cbEstilo = new ComboBox(obse);

		GridPane gridFont = getGrid(true);
		gridFont.add(labelFont,0,0);gridFont.add(cbfont,1,0);
		gridFont.add(labelSize,0,1);gridFont.add(spSize,1,1);
		gridFont.add(labelWeigt,0,2);gridFont.add(cb,1,2);
		gridFont.add(labelEst,0,3);gridFont.add(cbEstilo,1,3);

					
		//TilePane
		TitledPane tile1 = getTile("Descricao",gridToll2,true);
		TitledPane tile6 = getTile("Fonte",gridFont,true);
		TitledPane tile2 = getTile("Geomtria",gridToll,true);
		TitledPane tile3 = getTile(Util.codeUTF_8("Rotação"),gridToll5,false);
		TitledPane tile4 = getTile("Fundo",gridToll3,false);
		TitledPane tile5 = getTile("Bordas",gridToll4,false);

		tile1.setOnMouseClicked( actionEvent->{
			if( tile3.isExpanded() )
				tile3.setExpanded(false);
		});
		
		tile3.setOnMouseClicked( actionEvent->{
			if( tile1.isExpanded() )
				tile1.setExpanded(false);
		});

		tile6.setOnMouseClicked( actionEvent->{
			if( tile4.isExpanded() )
				tile4.setExpanded(false);
			if( tile5.isExpanded() )
				tile5.setExpanded(false);
		});
		
		tile4.setOnMouseClicked( actionEvent->{
			if( tile5.isExpanded() )
				tile5.setExpanded(false);
			if( tile1.isExpanded() )
				tile1.setExpanded(false);
			
		});
		
		tile5.setOnMouseClicked( actionEvent->{
			if( tile4.isExpanded() )
				tile4.setExpanded(false);

			if( tile6.isExpanded() )
				tile6.setExpanded(false);
		});
	    vbox.getChildren().addAll(labelProp,tile1,tile2,tile3,tile6,tile4,tile5);
		
        return vbox;
    }
	
	public Spinner getSpinner(){
		Spinner spinner = new Spinner(new DoubleSpinnerValueFactory(0,cp.getWidth(),0));
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
	
	public GridPane getGrid(boolean ctr){
		
		GridPane gridPane =new GridPane();
		if(ctr){
			ColumnConstraints col1 = new ColumnConstraints(65);
			ColumnConstraints col2 = new ColumnConstraints(150);
			gridPane.getColumnConstraints().addAll(col1,col2);
		}
			
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		return gridPane;
	}
	public VBox getTool( Control n1, Control n2){
//		n1.setPrefWidth(75);
//		n2.setPrefWidth(75);
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
		texto1.setOnAction(actionEvent->{
			paneBottom.getChildren().remove(0);
			int value = fundo.getChildren().size();
			System.out.println("Valendo : " + value );
			if(value>1)
				fundo.getChildren().remove(1, value);//remove os marcadores
		});
		Label labelX = new Label("x");
		Label labelY = new Label("y");
		Spinner tfX = new Spinner(0,cp.getWidth(),0);
		tfX.setEditable(true);
		Spinner tfY = new Spinner(0,cp.getHeight(),0);
		tfY.setEditable(true);
		Button button = new Button("Buscar");
		CheckBox ck1 = new CheckBox("Marcar");
		CheckBox ck2 = new CheckBox("Posicionar");
		button.setOnMouseClicked( actionEvent->{
			Double x = (double)tfX.getValue();
			Double y = (double)tfY.getValue();
			if( ck1.isSelected() ){
				Circle c  = new Circle(x.doubleValue(),y.doubleValue(),5);
				c.setFill(Color.RED);
				fundo.getChildren().add(c);
			}
			
			if( ck2.isSelected() ){
				try{
					
					Double totalX = stagePrincipal.getScene().getX() + 
								    ((Pane)root.getLeft()).getWidth() + 
								    cp.getFolha().getLayoutX() +
								    stagePrincipal.getX() +
								    x.intValue();
					Double totalY = stagePrincipal.getScene().getY() +
									((MenuBar)root.getTop()).getHeight() +
									cp.getFolha().getLayoutY() +
									stagePrincipal.getY() +
								    y.intValue();
	
					/*GraphicsDevice[] ge = getLocalGraphicsEnvironment().getScreenDevices();
					System.out.println(ge[0].getDisplayMode().getWidth());
					System.out.println(ge[0].getDisplayMode().getHeight());
					System.out.println(ge[0].getType());*/
					Robot r = new Robot();
					r.mouseMove(totalX.intValue(),totalY.intValue());
					
					
				}catch(Exception awtEx ){
					awtEx.printStackTrace();
				}
			}
		});
		
		
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

		//
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
	
    
    private class EventosFerramenta implements EventHandler<MouseEvent>{
		
        @Override
        public void handle(MouseEvent me){
            if( me.getButton()==MouseButton.PRIMARY){
                Label labelSource = (Label)me.getSource();
                laco:for( int i=0; i<ferramentas.length;i++){
                    if(labelSource == ferramentas[i]){
                        switch(i){
                            case 0:cp.criar(criarFiguraBotao());break;
                            case 1:cp.criar(criarFiguraLabel());break;
                            case 2:cp.criar(criarFiguraCheckBox());break;
                            case 3:cp.criar(criarFiguraTextField());break;
                            case 4:cp.criar(criarFiguraCombo());break;
                            case 5:cp.criar(criarFiguraRadio());break;
                            case 6:cp.criar(criarFiguraImage());break;
                            case 7:cp.criar(criarFiguraRect());break;
                            case 8:cp.criar(criarFiguraLine());break;
                            case 9:cp.criar(criarFiguraCircle());break;
							case 10:localPane();break;
                        }
                        break laco;
                    }
                }
            }
        }
	}
	
	public Button criarFiguraBotao() {
        Button button = new Button("Button");
		button.setPrefSize(80,50);
		button.setFocusTraversable(false);
        button.setId("node");
        return button;
    }

    public Label criarFiguraLabel() {
        Label label = new Label("Texto");
		label.setPrefSize(50,30);
		label.setFocusTraversable(false);
        label.setId("node");
        return label;
    }
    
     public Label criarFiguraCircle() {
        Label labelCirco = new Label();
        labelCirco.setBackground(Util.fundo(Color.RED,100));
		labelCirco.setPrefSize(50,50);
		labelCirco.setFocusTraversable(false);
        return labelCirco;
    }
     
    public Label criarFiguraRect() {
        Label rect = new Label();
		rect.setPrefSize(40,50);
        rect.setBackground(Util.fundo(Color.GRAY,0));
		rect.setFocusTraversable(false);
        return rect;
    }
    
    public Separator criarFiguraLine() {
        Separator linha = new Separator();
        linha.setOrientation(Orientation.HORIZONTAL);
        return linha;
    }
    
     public ComboBox criarFiguraCombo() {
        ObservableList<String> lista = FXCollections.observableArrayList("lista");
        ComboBox combo = new ComboBox(lista);
		combo.setPrefSize(90,40);
        combo.setId("node");
        combo.setEditable(false);
		combo.setFocusTraversable(false);
        combo.getSelectionModel().selectFirst();
        return combo;
    }
     
    public RadioButton criarFiguraRadio() {
        RadioButton radio = new RadioButton("radio");
		radio.setPrefSize(100,50);
        radio.setId("node");
		radio.setFocusTraversable(false);
        radio.setSelected(true);
        return radio;
    }
     
    public CheckBox criarFiguraCheckBox() {
        CheckBox ck = new CheckBox("check-box");
		ck.setPrefSize(100,60);
		ck.setFocusTraversable(false);
        ck.setId("node");
        return ck;
    }
    
    
    public TextField criarFiguraTextField(){
        TextField tf = new TextField();
		tf.setPromptText("texto");
        tf.setId("node");
		tf.setPrefSize(130,43);
		tf.setFocusTraversable(false);
        tf.setEditable(false);
        return tf;
    }
    
    public Label criarFiguraImage(){
        Image image = new Image( getClass().getResourceAsStream("image/photos1.png"));
        ImageView mv = new ImageView(image);
        mv.setId("node");
        mv.setFitHeight(60);
        mv.setFitWidth(100);
        Label labelImage = new Label("",mv);
		labelImage.setPrefSize(100,60);
        labelImage.setId("node");
        mv.fitWidthProperty().bindBidirectional(labelImage.prefWidthProperty());
        mv.fitHeightProperty().bindBidirectional( labelImage.prefHeightProperty());
        return labelImage;
    }
}
    
   
