/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pesquisa;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author alve
 */
public class ColorePane {
   private boolean hastring;
   private boolean hablocoComentado;
   private boolean naopode;
   int  x2 ;
   DefaultStyledDocument doc2;
   private  String naopermite = "'!@#$%¨&*()/\\";  
   public ColorePane() {
       
       
       hastring = false;
       hablocoComentado=false;
       naopode =false;
   }
   
   
   
  
   void  PalavraReservada(final JTextPane pane ){
    
       final StyleContext cont = StyleContext.getDefaultStyleContext();
        
        final AttributeSet azul = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        final AttributeSet amarelo = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(185,155,10));
        final  AttributeSet verde = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground,  new Color(25,200,70));
        final  AttributeSet vermelho = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
        final AttributeSet preto = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = pane.getText(0, getLength());
                      
             
                 
                final int indice=0;  
                int x = indice;
                if (x < 0) x = 0;
                    int y= text.length();
                    int inp = x;
                    int fimp = x;

                                while (fimp <= y) {
                     
                                    if (fimp == y || String.valueOf(text.charAt(fimp)).matches("\\W")) {
                                            if(inp==0) {
                                                for(int i=0; i<naopermite.length();i++){
                                                    if( text.charAt(0)!=naopermite.charAt(i)){
                                        
                                                    }
                                                    else{
                                                        naopode=true;
                                                    } 
                                                }
                                            } 
                                           if (text.substring(inp, fimp).matches("(\\W)*(sorteie|sementesorteio|imprima|escreva|leia|textoComp|textoSub|textoPos|funcao|retorna|retorne|novo|inteiro|repita|enquanto|se|senao|fim|numero|e|senaose|nao|mod|texto|booleano|verdadeiro|falso|ou)"))
                                            {
                                                if(inp==0 && naopode==false){
                                                        setCharacterAttributes(inp, fimp - inp, azul, false);
                                
                                
                                                }else {
                                                        setCharacterAttributes(inp+1, fimp - inp,azul, false);
                                                        naopode=false;
                                                }
                                               
                                            }
                                            else if  (text.substring(inp, fimp).matches("(\\W)*[0-9]+"))
                                            {
                                                    if(inp==0 && naopode==false){
                                                        setCharacterAttributes(inp, fimp - inp, verde, false);
                                
                                
                                                    }else {
                                                        setCharacterAttributes(inp+1, fimp - inp, verde, false);
                                                        naopode=false;
                                                    }
                                          
                                            }
                                         
                                            else
                                            {
                                                setCharacterAttributes(inp, fimp - inp, preto, false);
                                               
                                            }
                                            inp = fimp;
                                    }
                                    fimp++;
                                }
                                int z2 = text.length();
    
                                int cont2 =0,f2=0,e2=0,aux2=0,g2=0,h2=0,inc2=0,finc2=0,contfimc2=0;
                                boolean duplabarra2 =false,coment2=false,aspas21=false,aspas22=false;
                                for(int i4 =0;i4<z2; i4++){
                                    if(text.charAt(i4)==','|| text.charAt(i4)=='['||text.charAt(i4)==']'||text.charAt(i4)=='/'|| text.charAt(i4)=='(' || text.charAt(i4)==')' || text.charAt(i4)=='+'|| text.charAt(i4)=='-' || text.charAt(i4)=='*'|| text.charAt(i4)=='>'||text.charAt(i4)=='<'|| text.charAt(i4)=='=') {
                                         int cv =i4;
                                        setCharacterAttributes(cv,1, preto, false);
                                        
                                    }
                                    if(text.charAt(i4)=='\"') {
                                    
                                    
                                        cont2++;
                             
                                        if(cont2 ==1) {
                                            f2=i4;
                                     
                                    
                                        }
                     
                                        else if(cont2 ==2) {
                                         e2=i4;
                           
                                        setCharacterAttributes(f2, e2-f2+1, amarelo, false);
                                        cont2=0;
                                                 
                                        }
                          
                                    }
                                }
                                    
                                try {
                                    if(z2>=3){
                                        for(int j =0;j<z2; j++){
                            
                                            if(text.charAt(j)=='/'&& text.charAt(j+1)=='/' ) {
                              
                                     
                                            duplabarra2= true;
                                            if( duplabarra2 == true ){
                                                    g2=j;
                                           
                                            }   
                                            }else if(text.charAt(j)=='\n' &&  duplabarra2 == true) {
                                                h2=j;
                                        
                                                duplabarra2 = false;
                                                setCharacterAttributes(g2, h2-g2+1, vermelho, false);
                                       
                                            }
                            
                                        }    
                                
                                        for(int i3 =0;i3<z2; i3++){
                            
                                            if(text.charAt(i3)=='/'&& text.charAt(i3+1)=='*' ) {
                                            
                                                aux2 ++;
                                                coment2 = true;
                                                if( aux2==1){
                                                    inc2=i3;
                                                    if(text.charAt(i3-1)=='\"' ){
                                                        aspas21 =true;
                          
                                                    }
                                                }  
                                            }else if(text.charAt(i3)=='*' && text.charAt(i3+1)=='/' ) {
                                                finc2=i3;
                                                if(aspas21!= true && aspas22 != true ){
                                                    if(coment2 == true){
                                                        setCharacterAttributes(inc2,finc2-inc2+2, vermelho, false);
                                                        aux2=0;
                                                        coment2=false;
                                                        hablocoComentado=true;
                                            
                                                    }
                                                }else{
                                        
                                                    aspas21=false;
                                                    aspas22=false;
                                                }
                                            }
                            
                                        }  
                                
                                    } 
                        
                        
                                }catch (Exception ex) {
                                }
                        }   
                        
                        
                      
                      
        };
       
        pane.setDocument(doc) ; 
             
    
    } 
}
