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
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
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

    public static String processaImage(File file){
            String dados = "";
            try{
               dados = file.toURL().toString();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return dados;
    }

    public static String[][] getIcon(String nota){
        int i=0;
        String[][] file = null;
        try{
            int tam = new File(Util.class.getResource("../ficheiro/"+nota).toURI()).list().length;
            String[] dados;
            file = new String[tam][2];
            System.out.println("Entrou nota : " + nota + " tamanho : " + tam); 
            Scanner sc = new Scanner( Util.class.getResourceAsStream("../ficheiro/nota/"+nota+".txt") )
                                        .useDelimiter(";");
            while( sc.hasNext() ){
                dados = sc.next().split("-"); 
                file[i][0] = Util.class.getResource("../ficheiro/"+nota+"/"+dados[1]).toString();
                file[i][1] = dados[0];
                System.out.println( file[i][0]  + "  " + file[i][1] );
                i++; 
            }
            sc.close();
        }catch(Exception ex ){
            ex.printStackTrace();
        }
        return file;
    }

    public static String[][] getIcn1(){
        return getIcon("icones1");
    }

    public static String[][] getIcn2(){
        return getIcon("icones2");
    }

    public static String[][] getIcn3(){
        return getIcon("icones3");
    }

  /*  public static String[] getFile(URI uri) throws Exception{
        List<String> desc = new ArrayList<>();
            File file = new File(uri);

            for( File nome : file.listFiles() ){
                desc.add(nome.toURL().toString());
            }
         desc =  desc.stream().sorted().collect(Collectors.toList());
     //   desc.forEach( item->System.out.println(item));
        return desc.toArray(  new String[desc.size()]  );
    }*/

    public static String[] getCss(){
        return getFilesCss("../ficheiro/estilo/");
    }

    public static String[] getFilesCss(String dir){
        List<String> desc = new ArrayList<>();
        try{
          File file = new File(Util.class.getResource(dir).toURI());
          for(File f: file.listFiles()){
             desc.add(Util.class.getResource(dir+f.getName()).toString());
          }
        }catch( Exception ex ){
            ex.printStackTrace();
        }
        return desc.toArray(new String[desc.size()]);
    }

}
