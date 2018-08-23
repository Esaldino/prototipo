/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipo.control;

import javafx.scene.control.Control;
import static javafx.application.Platform.runLater;
import java.util.LinkedList;

import javafx.scene.control.ContextMenu;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;

import  javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseButton;
import java.util.Arrays;
import javafx.scene.transform.Rotate;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Dimension2D;
import static java.lang.System.out;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.function.DoubleFunction;
import javafx.geometry.Bounds;
import prototipo.model.Chapa;
import prototipo.model.Folha;
import prototipo.model.Delimitador;
import prototipo.model.Marcador;

import javafx.scene.Group;

import javafx.scene.transform.Scale;
//Controladores de propriedades


//beans


/**
 *
 * @author Esaldino
 */
public class Compasso {

	private DoubleProperty escala;
    private Folha folha;//tela onde os objetos seram desenhados
    private static final  Logger LOGGER = Logger.getLogger(Compasso.class.getName());
    /**
     * cordenadas - guarda os pontos extremos de uma figura ou chapa (p0,p1,p2,p3)
     * cordernadaMarcador - guarda os pontos extremos de uma marcador (p0,p1)
     */
    private final Point2D[] cordenadas,cordenadaMarcador;
	
    private Point2D pontosMarcador, pontos,pt;
  
    /*As 3 variaveis a seguir contram o movimento dos objetos e o seu redimensiomannto*/
    private boolean keyCtr;//mapeia se a tecla ctr foi clicado
    private boolean ctr;//faz o mapeamento entre o movimento do marcador e as figuras
    private int novo;//controla o redimensionamento dos objetos
    /**
     * pontos -guarda as cordenadas da figura quando pressionado
     * pontos2 - guarda as coordenadas da chapa quando ela for pressionada
     */
    private LinkedList<Point2D> pontos2;
	private int iterador;
    private Marcador marcador; //cria um linha imaginaria na folha   
    private GestorChapa gc;//processa as chapas
    private LinkedList<Delimitador> panesMarcados = new LinkedList<>();//guarda todas as chapa que estao dentro do marcador
    private LinkedList<Delimitador> deliProcessados = new LinkedList<>();//guarda todas as figuras surgindo quando as chapas foram desmarcadas
    
    //xEixo e yEixo guardam as cordenadas do cursor na folha
    private StringProperty xEixo, yEixo;
	
	private int ativados;
	private double dx;
	private double dy;

	//variaveis que controlam as propriedade
	private Avaliador av;
    
    public Compasso() {
		this.folha = new Folha();
		Scale sc = new Scale(1,1,20d,20d);
		folha.getTransforms().add(sc);
		marcador = new Marcador();
        keyCtr = false;
        gc = new GestorChapa();
		setIterador(9);
        cordenadas = new Point2D[4];
		pontos = new Point2D(0,0);
        cordenadaMarcador = new Point2D[2];
        pontos2 = new LinkedList<>();
        setDefinition();
		ativados = 0;
        ctr = false;
    }

	//Fazendo link com as propriedades
    public void setAvalidor(Avaliador avalidor ){
    	av = avalidor;
    }
	
	
	public void getScala(DoubleProperty escalaView){
		escala = new SimpleDoubleProperty(1);
		escala.bind(escalaView);
		//escala.addListener( (ob,ol,nw)->System.out.println( "Mudou as escala : " +nw  ));
	}
	
	public void addAtivado(){
		ativados +=1;
		System.out.println( "add : " +ativados  );
	}
	public void subAtivado(){
		if( ativados>0)
			ativados-=1;
		System.out.println( "sub : " +ativados  );
	}
	
	public void setIterador(int value){
        this.iterador = value;
	//	System.out.println("iterador mudou : " + value);
    }
    
    public int getIterador(){
        return iterador;
    }
    

                    
    public void setDefinition() {
       
        folha.setOnMouseMoved(mouseEvent->{
             setPositionMouse( mouseEvent.getX(),mouseEvent.getY() );
        });
		
		folha.setOnMouseEntered( mouseEvent->{
	//		System.out.println("Entrou na folha");
		});	

		folha.setOnMouseMoved( mouseEvent->{ 
		//	System.out.println("movendo x " + mouseEvent.getX() + " movendo y " + mouseEvent.getY() );
		});
        
       folha.setOnMousePressed( mouseEvent->{ 
			if( !ctr )
				setIterador(9);
			
            if( mouseEvent.getClickCount()==2 ){
                pontos2.clear();
				desmarcarTodos();
            } else if ( getIterador() == 9 && !ctr ) {
				marcador.setX(mouseEvent.getX());
				marcador.setY(mouseEvent.getY());
				pontosMarcador = new Point2D(mouseEvent.getX(),mouseEvent.getY());
				
				folha.desenhar(marcador); 
            } 
        });
        
        folha.setOnMouseReleased(mouseEvent->{
            if( getIterador() == 9 ){
				if(marcador != null ){
					marcador.setHeight(0);
					marcador.setWidth(0);
				}if( folha.contem(marcador))
					folha.remove(marcador);
            }
			
			if(deliProcessados.size()>0)
                deliProcessados.clear();
            if(panesMarcados.size()>0)
                panesMarcados.clear();
     
			
		//	actulizarFigura();
        });
        
        folha.setOnMouseDragged( mouseEvent->{
            //processa o marcador
			
            if(getIterador()==9 && !ctr ){
				
				double x = mouseEvent.getX()-pontosMarcador.getX();
				double y = mouseEvent.getY()-pontosMarcador.getY();
				
				if( x > 0)
					marcador.setWidth(x);
				else{
					marcador.setX(mouseEvent.getX());
					marcador.setWidth(-x);
				}
				
				if( y > 0)
					marcador.setHeight(y);
				else{
					marcador.setY(mouseEvent.getY());
					marcador.setHeight(-y);
				}

                cordenadaMarcador[0] = new Point2D(marcador.getX(),marcador.getY());
                cordenadaMarcador[1] = new Point2D(marcador.getWidth()+marcador.getX() , marcador.getHeight()+marcador.getY());
                
				calcular();
            }
        });
    }

      
	  
     //calcula os pontos extremos da chapa
    public void setCordernadas( Chapa chapa ){
	//	visualizar(chapa);
		Point2D pt = gc.getPosition(chapa);
		Dimension2D dm = gc.getDimension(chapa);
        cordenadas[0] =  new Point2D(pt.getX(),pt.getY());//cima
        cordenadas[1] =  new Point2D(pt.getX()+dm.getWidth(),pt.getY());//esquerdo
        cordenadas[2] =  new Point2D(pt.getX(),pt.getY()+dm.getHeight() );//baixo
        cordenadas[3] =  new Point2D(pt.getX()+dm.getWidth(),pt.getY()+dm.getHeight());//direito*/
    }
	

    /**
     * Calcula quais figuras ou chapas foram afetadas pelo marcador
     * se for figura marca
     * se for chapa desmarca
     * 
     * nao deve marcar figuras que surgiram da desmarcao de uma chapa
     * nao deve desmarcar chapa de figuras que foram marcadas
     */  
    public void calcular(){
	//	boolean ctr = false;
        for( Chapa chapa : gc.getAll() ){
           
			
			Delimitador deli = chapa.get();
			if(  panesMarcados.contains(deli) ) continue;
			setCordernadas(chapa);
			//verifica se os pontos da chapa é ponto interior do marcador
			for(int i=0; i<cordenadas.length; i++)
				if( (cordenadas[i].getX()>cordenadaMarcador[0].getX() && cordenadas[i].getX()<cordenadaMarcador[1].getX()) && 
					( cordenadas[i].getY()>cordenadaMarcador[0].getY() && cordenadas[i].getY()<cordenadaMarcador[1].getY() ) ){
					panesMarcados.add(deli);
					break;
			}
        }
		
		for(Delimitador deli:panesMarcados) {
			
			if( deliProcessados.contains( deli ) )
				continue;
			deliProcessados.add(deli);
			
			if( deli.isAtivado())
				deli.desativar();
			else
				deli.ativar();
		}
    }
      
    public double getWidth(){
        return folha.getPrefWidth();
    }
    public double getHeight(){
        return folha.getPrefHeight();
    }
	
    public Folha getFolha(){
        return folha;
    }
    public void ativarMarcador(){
        setIterador(9);
    }
	
    public StringProperty getX(){
        if(xEixo==null)
            xEixo = new SimpleStringProperty();
        return xEixo;
    }
    
    public StringProperty getY(){
        if(yEixo==null)
            yEixo = new SimpleStringProperty();
        return yEixo;
    }  
    
    public void setPositionMouse(double x, double y ){
        getX().set(String.format("%g",x));
        getY().set(String.format("%g",y));
    }
    
    
    public void setKeyCtr(boolean estado){
        this.keyCtr = estado;
        //out.println( "Pressionado" ); 
    }
    
    /*Menu contexto para as figuras*/
    public ContextMenu getMenuContexto1(){
		//menuitem apagar figura
        MenuItem menuItem = new MenuItem("Apagar");
        menuItem.setOnAction(actionEvent->apagarFigura());//event
		//menuitem propriedades
        MenuItem menuItem2 = new MenuItem("Propriedade");
		//menuitem recuar
        MenuItem menuItem3 = new MenuItem("Recuar");
		menuItem3.setOnAction(actionEvent->recuarFigura());//event
		//menusItem rodar
        MenuItem menuItem6 = new MenuItem("Rodar 90º direita");
        menuItem6.setOnAction(actionEvent->rodar(90));//event
        MenuItem menuItem7 = new MenuItem("Rodar 90º esquerda");
		menuItem7.setOnAction(actionEvent->rodar(-90));
        //menuItem avancar
        MenuItem menuItem4 = new MenuItem(Util.codeUTF_8("Avançar"));
        menuItem4.setOnAction(actionEvent->avancarFigura());//event
		//contextMenu
        ContextMenu menuContexto = new ContextMenu();
		//add contextMenu
        menuContexto.getItems().addAll(menuItem,menuItem4,menuItem3,menuItem6,menuItem7,menuItem2);
        return menuContexto;
    }


    public void apagarFigura(){
	    LOGGER.info("figura apagada");
		   try{
				for(Chapa chapa : gc.getAll() ){
					Delimitador deli = chapa.get();
					if( deli.isAtivado() )
						folha.remove(chapa);
				}
		   }catch( Exception ex ){
			   ex.printStackTrace();
		   }
    }
    
 
   
    public void desmarcarTodos(){
		try{
			for(Chapa chapa : gc.getAll() ){
				desmarcarComp(chapa);
			}
		}catch( Exception ex ){
		   ex.printStackTrace();
		}
    }
    
    //desmarca as figuras
    public void desmarcarComp(Chapa chapa){
	
		Delimitador noAtivado = chapa.get();
		if( noAtivado.isAtivado()){
			noAtivado.desativar();
			subAtivado();
		}
    }

    /**
     * Traz a figura para frente
     */
    public void avancarFigura(){
        LOGGER.info("Avancando a figura");
        for( Chapa chapa: gc.getAll() ){
			Delimitador deli = chapa.get();
	
            int pos = folha.posicao(chapa);
			
            if( pos >= folha.tamanho()-1 || !deli.isAtivado() )
                continue;
			
            Node figuraSeguir = folha.getFigura(pos+1);
            folha.remove(figuraSeguir);
            folha.remove(chapa);
            folha.desenhar(figuraSeguir,pos);
            folha.desenhar(chapa,pos+1);
        }
    }
    
    
     /**
     * Traz a figura para tras
     */
    public void recuarFigura(){
        LOGGER.info("Recuando a figura");
        for( Chapa chapa: gc.getAll() ){
			Delimitador deli = chapa.get();
            int pos = folha.posicao(chapa);
			
            if( pos -1 < 0 || !deli.isAtivado())
                continue;
			
            Node figuraAnterior = folha.getFigura(pos-1);
            folha.remove(figuraAnterior);
            folha.remove(chapa);
            folha.desenhar(chapa,pos-1);
            folha.desenhar(figuraAnterior,pos);
        }
    }
   

	public void rodar(int angle){
	   LOGGER.info("Rodando a figura");
	   setIterador(10);
       for(Chapa chapa:gc.getAll()){
		   Delimitador deli = chapa.get();
		   if( !deli.isAtivado() )
			   continue;
		   
			Control figura = chapa.getControl();
			Rotate r = (Rotate)figura.getTransforms().get(0);
			double rotacao =r.getAngle()+angle;
			if(rotacao>360)
				rotacao=90;
			r.setAngle(rotacao);
			deli.actualiza(figura.getBoundsInParent());
			actulizarFigura();
        }
    }
	
	/*Verifica os limites da figura em relação a folha*/
	public boolean verificarLimite(double x, double y ,double w,double h){
		return ( (x>=0&&y>=0)&&( x<getWidth()-w-10&&y<getHeight()-h-20) ) ?true:false;
	}
	
	public void moverFigura(double dx, double dy){
		for( Chapa chapa : gc.getAll() ){
			Delimitador deli = chapa.get();
			
			if( !deli.isAtivado() ) continue;
			
			Point2D pos = gc.getPosition(chapa);
			Dimension2D dm = gc.getDimension(chapa);
			double px = dx + pos.getX();
			double py = dy + pos.getY();

			chapa.setLayoutX(px);
			chapa.setLayoutY(py);
		}
	}
	
	public void process( Chapa chapa,Delimitador deli){
		
		System.out.println("Processa");
		if( !keyCtr  )
			desmarcarTodos();
		
		if( !deli.isAtivado() ){
			deli.ativar();
			if( ativados < 1 )
				setInstancia(chapa);
			addAtivado();//determina o numero de objetos marcador
		}
		
	}

	public void setInstancia(Chapa chapa){
		 av.testar(chapa.getControl());
	}
	
	public void criar(Control figura){
		
		Chapa chapa  = new Chapa();
		Rotate r = new Rotate(0,0,0);
	
		figura.getTransforms().addAll(r);
		Delimitador deli  = new Delimitador( figura.getPrefWidth() , figura.getPrefHeight() );
		chapa.addi(figura,deli);
		process(chapa,deli);
		
		gc.add(chapa);
		folha.desenhar(chapa);
		
		deli.setOnMousePressed(mouseEvent->{

			System.out.println("Pressionou na marcador");
			ctr = true;
			double x = mouseEvent.getScreenX()/escala.get();
			double y = mouseEvent.getScreenY()/escala.get();
			pontos = new Point2D(x,y);
			deli.actualiza(figura.getBoundsInParent());
			pontosMarcador = new Point2D(deli.getX(),deli.getY());//guarda a pos do delimitador
			pt = new Point2D(figura.getLayoutX(),figura.getLayoutY());

			if( mouseEvent.getButton() == MouseButton.PRIMARY && novo == 0 && !deli.isAtivado() )
				process(chapa,deli);
        });

        
       deli.setOnMouseReleased(mouseEvent->{
            //actualiza as posicao e a rotacao das figuras
            actulizarFigura();
			deli.actualiza(figura.getBoundsInParent());
            ctr=false;
			 setIterador(9);//ativa o deli
			
        });
		
		deli.setOnMouseDragged(mouseEvent->{
			
			if( mouseEvent.getButton() != MouseButton.PRIMARY )
				return;
				double x = mouseEvent.getScreenX()/escala.get();
				double y = mouseEvent.getScreenY()/escala.get(); 
				dx = x - pontos.getX(); 
				dy = y - pontos.getY();
			if( novo==0){
				moverFigura(dx,dy);
			} else if(novo>0&&novo<9){
				redimensionarDelimitador(dx,dy,chapa);
			} 
        });//onMouseDragged
        
        
        deli.setOnMouseClicked( mouseEvent->{
            ContextMenu mc = getMenuContexto1();
            if( mouseEvent.getButton()==MouseButton.SECONDARY ){
				if( !deli.isAtivado() ){
					deli.ativar();
					ativados++;//determina o numero de objetos deli
				}
				mc.show( deli , mouseEvent.getScreenX() , mouseEvent.getScreenY() );
            }else
               mc.hide();           

            if( !keyCtr && mouseEvent.getClickCount()==2 )
                desmarcarComp( chapa );
            
        });
		
		

        //calcula os limites latereis das figuras
        deli.setOnMouseEntered( mouseEvent->{
			//calcula os pontos extremos da chapa
			
			if(!deli.isAtivado())
				return;
			
			Dimension2D dm = gc.getDimension(chapa);
			cordenadas[0] =  new Point2D(deli.getValueX(),deli.getValueY());//cima
			cordenadas[1] =  new Point2D(deli.getValueX()+dm.getWidth(),deli.getValueY());//esquerdo
			cordenadas[2] =  new Point2D(deli.getValueX(),deli.getValueY()+dm.getHeight() );//baixo
			cordenadas[3] =  new Point2D(deli.getValueX()+dm.getWidth(),deli.getValueY()+dm.getHeight());//direito*/
       
	   });
        
        deli.setOnMouseMoved( mouseEvent->{

             if( ctr )
                 return;
			double x =  mouseEvent.getX();                         
			double y =  mouseEvent.getY();
			
            int sigma = 10;
            //determina que o cursor esta por cima,baixo e nas laterais da figura
			 if( (x > cordenadas[0].getX()-sigma && x < cordenadas[0].getX()+sigma)
                    &&( y>cordenadas[0].getY()-sigma && y<cordenadas[0].getY()+sigma) ){
					novo = 5;
				//	out.println("Chegou no pontos P0");
					deli.setCursor( Cursor.NW_RESIZE );
            } else  if( (x > cordenadas[1].getX()-sigma && x < cordenadas[1].getX()+sigma)
                    &&( y>cordenadas[1].getY()-sigma && y<cordenadas[1].getY()+sigma)){
					novo=6;
               // out.println("Chegou no pontos P1");
					deli.setCursor( Cursor.NE_RESIZE  );
            }else  if((x > cordenadas[2].getX()-sigma && x < cordenadas[2].getX()+sigma)
                    &&( y>cordenadas[2].getY()-sigma && y<cordenadas[2].getY()+sigma)){
					novo=7;
            //    out.println("Chegou no pontos P2");
					deli.setCursor( Cursor.SW_RESIZE );
            }else  if( (x > cordenadas[3].getX()-sigma && x < cordenadas[3].getX()+sigma)
                    &&( y>cordenadas[3].getY()-sigma && y<cordenadas[3].getY()+sigma)){
					novo=8;
            //    out.println("Chegou no pontos P3");
					deli.setCursor( Cursor.SE_RESIZE );
            }else if(cordenadas[0].getX() - x < 0 && cordenadas[0].getY()== y  ){
               if( cordenadas[1].getX() - x > 0 && cordenadas[1].getY()== y  ){
					novo=1;
                    //cima
                    deli.setCursor( Cursor.N_RESIZE );
               }
            }else if( cordenadas[0].getX() == x && cordenadas[0].getY() -  y < 0 ){
                if( cordenadas[2].getX()== x  && cordenadas[2].getY() - y > 0 ){
					novo=2;
                     //lado esquerdo
                    deli.setCursor( Cursor.H_RESIZE );
                }
            } else if( cordenadas[2].getX() - x < 0 && cordenadas[2].getY() == y  ){
                if( cordenadas[3].getX() - x > 0 && cordenadas[3].getY() == y ){
					novo=3;
                 // BAIXO
                    deli.setCursor( Cursor.V_RESIZE );
                }
            }else if( cordenadas[1].getX() == x && cordenadas[1].getY() - y < 0  ){
                if( cordenadas[3].getX() == x  && cordenadas[3].getY() - y > 0 ){
					novo=4;
                    //lado direiro
                    deli.setCursor( Cursor.H_RESIZE );
                }
            }else{
				if(deli.isAtivado())
					deli.setCursor( Cursor.MOVE );	
				novo=0;
			}
			setIterador(novo);
        });	
	}
	

	
	public void redimensionarDelimitador(double dx,double dy,Chapa pane){
		  //redimensiona as figuras
		  

			Dimension2D d2d = gc.getDimension(pane);
			Delimitador deli = pane.get();
			Control figura = pane.getControl();
			int rotacao = (int)((Rotate)figura.getTransforms().get(0)).getAngle();
			
			if(rotacao<0)
				rotacao = 360+rotacao;
			double novaLargura=0;
			double novaAltura=0;
			double novoY =0;
			double intervalo = 45;
			double novoX=0;
            switch( getIterador() ){
                case 1:// CIMA ->desloca a altura - trabalhando com a cordenada Y
						novaAltura = d2d.getHeight()-dy;
						novoY      = pontosMarcador.getY()+dy;
						
						if( novaAltura<=intervalo )
							return;
						
						deli.setAltura(novaAltura);
						deli.setPontoY(novoY);
						
						
						switch(rotacao){
							case 0:
							case 360:figura.setLayoutY(pt.getY()+dy);
							case 180:figura.setPrefHeight(novaAltura);
									 break;
							case 90:figura.setLayoutY(pt.getY()+dy);
							case 270:figura.setPrefWidth(novaAltura);
						}
						
						ctr=true;
						break;
                case 2: //Esquerdo - desloca a largura -trabalhando com o eixo X
						novaLargura = d2d.getWidth()-dx;
						novoX = pontosMarcador.getX()+dx;
						
						if( novaLargura<=intervalo )
							return;
						
						deli.setLargura(novaLargura);
						deli.setPontoX(novoX);
                        
						switch(rotacao){
							case 0:
							case 360:figura.setLayoutX(pt.getX()+dx);
							case 180:figura.setPrefWidth(novaLargura);
									break;
							case 270:figura.setLayoutX(pt.getX()+dx);
							case 90:figura.setPrefHeight(novaLargura);
						}
						
						ctr=true;
                        break;
                case 3: //Baixo -> Descola a altura - trabalhando com a altura 
						novaAltura = d2d.getHeight()+dy;
						
						if( novaAltura<=intervalo )
							return;
						
						deli.setAltura(novaAltura);
						
						switch(rotacao){
							case 180:figura.setLayoutY(pt.getY()+dy);
							case 0:
							case 360:figura.setPrefHeight(novaAltura);
									 break;
							case 270:figura.setLayoutY(pt.getY()+dy);
							case 90: figura.setPrefWidth(novaAltura);
									break;
							
							
						}
						
						
						ctr=true;
                        break;
						
                case 4: //direito - > Desloca a largura - trabalhando a largura
						novaLargura = d2d.getWidth()+dx;
						
						if( novaLargura<=intervalo )
							return;
						
						deli.setLargura(novaLargura);
						
						switch(rotacao){
							case 180:figura.setLayoutX(pt.getX()+dx);
							case 0:
							case 360:figura.setPrefWidth(novaLargura);
								 break;
							case 90:figura.setLayoutX(pt.getX()+dx);
							case 270:figura.setPrefHeight(novaLargura);
									break;	
						}
						ctr=true;
                        break;
						
                case 5: //NW - Desloca a a altura e largura - trbalhanco com eixo xy
					    novaAltura = d2d.getHeight()-dy;
						novoY      = pontosMarcador.getY()+dy;
						
						if( novaAltura>intervalo ){
							deli.setAltura(novaAltura);
							deli.setPontoY(novoY);
							dy=pt.getY()+dy;
						}else{
							novaAltura = deli.getHeight();
							dy=figura.getLayoutY();
						}
						
						novaLargura = d2d.getWidth()-dx;
						novoX       = pontosMarcador.getX()+dx;	
						
						if(novaLargura>intervalo){
							deli.setLargura(novaLargura);
							deli.setPontoX(novoX);
							dx=pt.getX()+dx;
						}else{
							novaLargura = deli.getWidth();
							dx=figura.getLayoutX();
						}
	
						switch(rotacao){
							case 0:
							case 360:figura.setLayoutX(dx);
								     figura.setLayoutY(dy);
							case 180:figura.setPrefWidth(novaLargura);
									 figura.setPrefHeight(novaAltura);
									 break;
							case 90: 
								     figura.setLayoutY(dy);
									 figura.setPrefWidth( novaAltura );
									 figura.setPrefHeight( novaLargura );
									 break;
							case 270:figura.setLayoutX(dx);
									 figura.setPrefWidth( novaAltura );
									 figura.setPrefHeight( novaLargura );
						}							
								
						ctr=true;
                        break;
                case 6: //NE - desloca a altura e largura-trabalhando com eio Y e largura
						novaAltura = d2d.getHeight()-dy;
						novoY      = pontosMarcador.getY()+dy;
						
						if(novaAltura>intervalo){
							deli.setAltura(novaAltura);
							deli.setPontoY(novoY);
							dy=pt.getY()+dy;
						}else{
							dy=figura.getLayoutY();
							novaAltura=deli.getHeight();
						}
							
							
						novaLargura = d2d.getWidth()+dx;
						
						if(novaLargura>intervalo){
							deli.setLargura(novaLargura);
							dx=pt.getX()+dx;
						}else{
							dx=figura.getLayoutX();
							novaLargura=deli.getWidth();
						}
						
						switch(rotacao){
							
							case 0:
							case 360:figura.setLayoutY(dy);
									 figura.setPrefWidth(novaLargura);
									 figura.setPrefHeight(novaAltura);
									 break;
							case 90: figura.setLayoutX(dx);
									 figura.setLayoutY(dy);
							case 270:figura.setPrefWidth(novaAltura);
									 figura.setPrefHeight(novaLargura);
									break;
							case 180:figura.setLayoutX(dx);
									 figura.setPrefWidth(novaLargura);
									 figura.setPrefHeight(novaAltura);
									 break;
		
						}
						
						
						ctr=true;
                        break;
                 case 7://SW - Desloca altura e largura - trabalhando o eio X e altura
						novaLargura = d2d.getWidth()-dx;
						novoX = pontosMarcador.getX()+dx;
								
								
						if(novaLargura>intervalo){
							deli.setLargura(novaLargura);
							deli.setPontoX(novoX);
							dx = pt.getX()+dx;
						}else{
							novaLargura=deli.getWidth();
							dx = figura.getLayoutX();
						}
								
						novaAltura = d2d.getHeight()+dy;
						
						if( novaAltura>intervalo){
							deli.setAltura(novaAltura);
							dy=pt.getY()+dy;
						}else{
							novaAltura=deli.getHeight();
							dy=figura.getLayoutY();
						}
						
						switch( rotacao ){
							
							case 0:
							case 360:figura.setLayoutX(dx);
									 figura.setPrefWidth(novaLargura);
									 figura.setPrefHeight(novaAltura);
									 break;
							case 90:figura.setPrefWidth(novaAltura);
									figura.setPrefHeight(novaLargura);
									break;
							case 180:figura.setLayoutY(dy);
									figura.setPrefWidth(novaLargura);
									figura.setPrefHeight(novaAltura);
									break;
							case 270:
									figura.setLayoutX(dx);
									figura.setLayoutY(dy);
									figura.setPrefWidth(novaAltura);
									figura.setPrefHeight(novaLargura);
							
						}
						ctr=true;
                        break;
                case 8://SE -Desloca a largura e altura - trabalhando com a largura e altura
						novaLargura = d2d.getWidth()+dx;
						
						
						if(novaLargura>intervalo){
							deli.setLargura(novaLargura);
							dx = pt.getX()+dx;
						}else{
							novaLargura=deli.getWidth();
							dx=figura.getLayoutX();
						}
							
						novaAltura = d2d.getHeight()+dy;
						
						if(novaAltura>intervalo){
							deli.setAltura(novaAltura);
							dy=pt.getY()+dy;
						}else{
							novaAltura=deli.getHeight();
							dy=figura.getLayoutY();
						}
						
						switch(rotacao){
							case 180:figura.setLayoutX(dx);
									 figura.setLayoutY(dy);
							case 0:
							case 360:
									figura.setPrefWidth(novaLargura);
									figura.setPrefHeight(novaAltura);
									break;
							case 90:figura.setLayoutX(dx);
									figura.setPrefWidth(novaAltura);
									figura.setPrefHeight(novaLargura);
									break;
							case 270:
									figura.setLayoutY(dy);
									figura.setPrefWidth(novaAltura);
									figura.setPrefHeight(novaLargura);
							
						}
					    ctr=true;
            }
			
	}
    
	public void visualizar( Chapa chapa){
		Delimitador deli = chapa.get();
		Dimension2D dm = gc.getDimension(chapa);
		Control figura = chapa.getControl();
		out.println("\n\nx figura : " + figura.getLayoutX() + " y figura" + figura.getLayoutY() );
		out.println("w figura : " + figura.getPrefWidth() + " h figura" + figura.getPrefHeight() );
		out.println("x  : " + deli.getX() + " y " + deli.getY() );
		out.println("x  : " + deli.getLayoutBounds() );
		
	}

	public void actulizarFigura(){
		out.println("Actualizou");
		for(Chapa chapa :gc.getAll() ){
            gc.setPosition(chapa);
			gc.setDimesion(chapa);
        }
	}

}
