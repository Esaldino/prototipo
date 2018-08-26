/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipo.control;

/**
 *
 * @author Esaldino
 */

import static java.nio.charset.StandardCharsets.UTF_8;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.BorderStroke;
import static javafx.scene.layout.BorderStrokeStyle.DASHED;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.URI;
public class Util {
    
    public static String codeUTF_8(String texto){
        return new String( texto.getBytes(), UTF_8 );
    }
    
    
    public static Background fundo(Color cor,int radii ){
        BackgroundFill bf = new BackgroundFill(cor,new CornerRadii(radii,true), Insets.EMPTY );
        return new Background(bf);
    }
    
    
    public static Border bordas( Color cor , int radii,int width){
       BorderStroke bk  =  new BorderStroke(cor,DASHED,new CornerRadii(radii),new BorderWidths(width));
       return new Border(bk);
    }


    public static String[] getDescricao(URI uri) throws Exception{

         ArrayList<String> desc = new ArrayList<>();

            Scanner sc = new Scanner( new File(uri) )
                                    .useDelimiter(";");

            while( sc.hasNext() ){
                desc.add(sc.next());
                
            }
       //     System.out.println(desc);
        return desc.toArray(  new String[desc.size()]   );

    }

    public static String[] getFile(URI uri) throws Exception{
        ArrayList<String> desc = new ArrayList<>();
      
            File file = new File(uri);

            for( File nome : file.listFiles() ){

                desc.add(nome.toURL().toString());
            }
       // desc.forEach( item->System.out.println(item));
        return desc.toArray(  new String[desc.size()]  );
    }

}
