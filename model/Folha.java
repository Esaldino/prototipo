package prototipo.model;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import java.util.List;
import javafx.scene.Cursor;

public class Folha extends Pane{
   
    
    private  int sigma;
    public Folha(){
        setId("folha-id");
        setCursor(Cursor.DEFAULT);
        setPrefSize(2000,2000);
    }
    
 
   

       /**
     * Adiciona um figura na folha
     * @param figura 
     */
    

    
    public void desenhar(Node figura) {
        if( !getChildren().contains(figura))
            getChildren().add(figura);
    }
    
    public Node getFigura(int pos) {
        Node figura = null;
        if( pos < tamanho() ){
            figura = getChildren().get(pos); 
        }
        return figura;
    }
    
    public void desenhar(Node figura, int op ){
        if( !getChildren().contains(figura))
            getChildren().add(op,figura);
    }
    
    public int tamanho(){
        return getChildren().size();
    }
    
    /*
        Remove um figura da folha
    */
    public void remove(Node figura) {
        if( contem(figura) )
            getChildren().remove(figura);
    }
   
    
    public boolean contem(Node figura){
        return getChildren().contains(figura);
    }
    
    /**
     * retorna a posicao do figura da folha
     * @param figura
     * @return 
     */
    public int posicao(Node figura){
       int i=0;
       for(Node nos:getChildren() ){
           if( nos == figura )
               break;
           i++;
       }
       return i;
    }
    
    public List<Node> todos(){
        return getChildren();
    }
    public boolean existe(Node figura){
       return getChildren().contains(figura);
    }


}
