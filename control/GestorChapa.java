package prototipo.control;

import javafx.scene.Node;
import javafx.scene.control.Control;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.geometry.Point2D;
import javafx.geometry.Dimension2D;
import prototipo.model.Chapa;
import prototipo.model.Delimitador;
import static java.util.stream.Collectors.toList;
public class GestorChapa {
    
    private LinkedList<Chapa> gestorChapa;
    private HashMap<Chapa, Point2D> position;
    private HashMap<Chapa, Dimension2D> dimension;
	private Chapa ultima;
	
    public GestorChapa(){
        gestorChapa = new LinkedList<>();
        position = new HashMap<>();
		dimension = new HashMap<>();
    }
    
    public void setPosition(Chapa pane){
        position.put( pane, new Point2D( pane.getLayoutX(),pane.getLayoutY() ) );
    }
	
	public void setDimesion(Chapa pane){
		Delimitador del = pane.get();
		dimension.put( pane, new Dimension2D( del.getWidth(),del.getHeight() ) );
    }
    
    
    public Point2D getPosition(Chapa pane){
        return position.get(pane);
    }
	
	public Dimension2D getDimension(Chapa pane){;
        return dimension.get(pane);
    }
    
    public void clearPosition(){
        position.clear();
    }
	public void clearRotation(){
        dimension.clear();
    }
	public void clear(){
        gestorChapa.clear();
    }
    
    public Chapa get(){
        return gestorChapa.peekLast();
    }
    
    public int position(Chapa chapa){
        return gestorChapa.indexOf(chapa);
    }
    
    public void add(Chapa chapa){
		setPosition(chapa);
		setDimesion(chapa);
        gestorChapa.add(chapa);
    }
    
    public Chapa getForExists(Node figura){
        return gestorChapa.stream()
                .filter((Chapa chapa)->chapa.getChildren().get(0)==figura)
                .collect(toList()).get(0);
    }

    public boolean exists(Node figura){
        return gestorChapa
                .stream()
                .filter((Chapa chapa)->chapa.getChildren().get(0)==figura)
                .collect(toList()).size()> 0 ? true:false;
    }
   
    
    public void reset(){
       clearRotation();
	   clearPosition();
	   clear();
    }
   
    public LinkedList<Chapa> getAll(){
        return gestorChapa;
    }
	
	public void setLast(Chapa chapa){
		ultima =chapa;
	}
	
	public Chapa getChapa(){
		return ultima;
	}
	
	
    
    public void remove(Chapa pane){
        gestorChapa.remove(pane);
		position.remove(pane);
		dimension.remove(pane);
    }
    
    public long size(){
        return gestorChapa.size();
    }
	
	public void actulizarFigura(){
		for(Chapa chapa :getAll() ){
            setPosition(chapa);
			setDimesion(chapa);
        }
	}
}
