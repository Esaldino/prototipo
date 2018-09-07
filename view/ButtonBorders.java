package prototipo.view;

import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import prototipo.control.Util;
import javafx.scene.Cursor;

public final class ButtonBorders{
	
	private Label[] lados;
	private String[] nomes = {"Top","Right","Bottom","Left"};
	private boolean[] ctr = {false,false,false,false};
	private GridPane ladosBorder;
	
	public ButtonBorders(){
		criarLabel();
		addLayout();
	}
	
	private EventHandler getEvent(Label label,int i){
		return new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				ctr[i] = !ctr[i];
				if(ctr[i]){
					label.setBackground(Util.fundo(Color.rgb(100,100,100),0));
					label.setTextFill(Color.WHITE);
				}else{
					label.setBackground(Util.fundo(Color.TRANSPARENT,0));
					label.setTextFill(Color.BLACK);
				}
			}
		};
	}
	
	public boolean isTop(){
		return ctr[0];
	}
	public boolean isRight(){
		return ctr[1];
	}
	public boolean isBottom(){
		return ctr[2];
	}
	public boolean isLeft(){
		return ctr[3];
	}
	
	private void criarLabel(){
		lados = new Label[4];
		for( int i=0;i<lados.length;i++){
			lados[i] = new Label(nomes[i]);
			lados[i].setCursor(Cursor.HAND);
			lados[i].setBorder(Util.bordas(Color.rgb(100,100,100),0,1));
			lados[i].setPrefSize(50,23);
			EventHandler eh = getEvent(lados[i] ,1);
			lados[i].setOnMouseClicked(eh);
			lados[i].setTextAlignment(TextAlignment.CENTER);
			lados[i].setAlignment(Pos.CENTER);
		}
	}

	
	private void addLayout(){
		
		ladosBorder = new GridPane();
		ladosBorder.setVgap(5);
		ladosBorder.setHgap(5);
		
		SinalMais sinal = new SinalMais();
		sinal.getTypeDash();
		
		ladosBorder.setAlignment(Pos.CENTER);
		//ladosBorder.setGridLinesVisible(true);
		ladosBorder.setHalignment(lados[2],HPos.CENTER);
		ladosBorder.add(lados[2],1,0);
		ladosBorder.setHalignment(lados[1],HPos.CENTER);
		ladosBorder.add(lados[1],0,1);
		ladosBorder.setHalignment(lados[0],HPos.CENTER);
		ladosBorder.add(lados[0],2,1);
		ladosBorder.setHalignment(lados[3],HPos.CENTER);
		ladosBorder.add(lados[3],1,2);
	
		ladosBorder.add(sinal,1,1);
		
	}
	
	public GridPane get(){
		return ladosBorder;
	}
	
}