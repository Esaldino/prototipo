package prototipo.control;

import javafx.concurrent.Task;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;


import javafx.geometry.Insets;
public class Tarefa {

	
    private static final int TAM_BLOCO1= 10;//tamanhos dos blocos em  10px
	private static final int TAM_BLOCO2= 100;//tamanhos dos blocos em  100px
	private static final int DIM = 30;//dimensao do traco de maior valor
	private double intervalo;
	
	
	/*
		Cria a regua na vertical
		retorna uma lista de componentes formando os tracos de uma regua
	*/
	public Task<ArrayList<BorderPane>> reguaH(double value){
		double intervalo = value/TAM_BLOCO2;
		return new Task<ArrayList<BorderPane>>(){
			
			@Override
			protected ArrayList<BorderPane> call() throws Exception{
				ArrayList<BorderPane> al = new ArrayList<>();
				for( int i = 0 ; i < intervalo ; i++ ){
					try{
						BorderPane borderPane = new BorderPane();//representa um bloco que equivale a 100px na regua
						borderPane.setPrefSize(TAM_BLOCO2,DIM);
						Label label = new Label( String.valueOf( i*TAM_BLOCO2 ) ) ;
						label.getStyleClass().add("label-regua");
						borderPane.setAlignment(label,Pos.BOTTOM_LEFT);
						borderPane.getStyleClass().add("border-regua1");
						HBox hbox = new HBox();
						/*
							criar os blocos horizontais que representam 10px   na regua
						*/
						for(int j=0;j<TAM_BLOCO1;j++){
							Pane pane = new Pane();
							if(j!=TAM_BLOCO1-1)
								pane.getStyleClass().add("pane-regua1");
							pane.setPrefSize(TAM_BLOCO1,6);
							hbox.getChildren().add(pane);
						}
						borderPane.setTop(hbox);
						borderPane.setCenter(label );
						al.add(borderPane);
						Thread.sleep(5);
					}catch(InterruptedException iex ){
						iex.printStackTrace();
					}
				}//for
				return al;
			}//call
		};//Task
		
	}//regua
	
	
	/*
		Cria a regua na vertical
		retorna uma lista de componentes formando os tracos de uma regua
	*/
	public Task<ArrayList<BorderPane>> reguaV(double value){
		
		double intervalo = value/TAM_BLOCO2;
		
		
		return new Task<ArrayList<BorderPane>>(){
			
			@Override
			protected ArrayList<BorderPane> call() throws Exception{
				ArrayList<BorderPane> al = new ArrayList<>();
				for( int i = 0 ; i < intervalo ; i++ ){
					try{
						BorderPane borderPane = new BorderPane();//representa um bloco que equivale a 100px na regua
						borderPane.setPrefSize(DIM,TAM_BLOCO2);
						Label label = new Label( String.valueOf( i*TAM_BLOCO2 ) ) ;
						label.getStyleClass().add("label-regua");
						borderPane.setAlignment(label,Pos.TOP_RIGHT);
						borderPane.getStyleClass().add("border-regua");
						VBox vbox = new VBox();
						/*
							criar os blocos verticais que representam 10px  na regua
						*/
						for(int j=0;j<TAM_BLOCO1;j++){
							Pane pane = new Pane();
							if(j!=TAM_BLOCO1-1)
								pane.getStyleClass().add("pane-regua");
							pane.setPrefSize(6,TAM_BLOCO1);
							vbox.getChildren().add(pane);
						}
						borderPane.setLeft(vbox);
						borderPane.setCenter(label );
						al.add(borderPane);
						Thread.sleep(5);
					}catch(InterruptedException iex ){
						iex.printStackTrace();
					}
				}//for
				return al;
			}//call
		};//Task
		
	}//regua
}