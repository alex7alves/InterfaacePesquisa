/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pesquisa;


import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;

/**
 *
 * @author alve
 */
public class visor extends javax.swing.JFrame {

    /**
     * Creates new form visor
     */
    Font font =  new Font("Dialog", Font.PLAIN, 12);
    private boolean compilado;
    private boolean hastring;
    private boolean hablocoComentado;
    private boolean sobrescrever;
    private boolean tasalvo;
    private boolean taaberto;
    private String nome;
    private String caminho;
    private int aumentar,diminuir;
    final UndoManager Gerente = new UndoManager();
    private int cont_Lista_String,cont_aux;
    private String[] StringArray_Lista;
    private List lista = new ArrayList();
    private List listaAux = new ArrayList();
    private boolean desfazer,refazer,evento,OutroEvento;
    private boolean naopode;
    private  String naopermite = "'!@#$%¨&*()/\\"; 
    public visor() {
       
        tasalvo = false;
        taaberto=false;
        hastring = false;
        hablocoComentado=false;
        compilado = false;
        sobrescrever= false;
       // cont_Lista_String=0;
        desfazer=true;
        refazer=true;
        evento=true;
        OutroEvento=false;
        cont_aux=0;
        naopode =false;
        initComponents();
          
          /* Gerente = new UndoManager();
        jTextPane1.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                Gerente.addEdit(e.getEdit());
                             
                                   
            }
        });*/
     
        
           
        TextLineNumber contadorLinhas = new TextLineNumber(jTextPane1);  
        jScrollPane3.setRowHeaderView(contadorLinhas);  
         
       
      
       PalavraReservada();    
        
         
   
       
       
    }
    
     private int ultimo (String texto, int indice) {
        while (--indice >= 0) {
            if (String.valueOf(texto.charAt(indice)).matches("\\W")) {
                break;
            }
        }
        return indice;
    }

    private int inicio (String texto, int indice) {
        while (indice < texto.length()) {
            if (String.valueOf(texto.charAt(indice)).matches("\\W")) {
                break;
            }
            indice++;
        }
        return indice;
    }
    
    
    private void  PalavraReservada( ){
        
        
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        
        final AttributeSet azul = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        final AttributeSet amarelo = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.YELLOW);
        final  AttributeSet verde = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.GREEN);
        final  AttributeSet vermelho = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
        final AttributeSet preto = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = jTextPane1.getText(0, getLength());
                      
             
                 
                int x = ultimo(text, offset);
                if (x < 0) x = 0;
                    int y= inicio(text, offset + str.length());
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
                            if (text.substring(inp, fimp).matches("(\\W)*(inteiro|repita|enquanto|se|senao|fim|numero|e|senaose|nao|mod|texto)"))
                            {
                               
                                if(inp==0 && naopode==false){
                                    setCharacterAttributes(inp, fimp - inp, azul, false);
                                
                                
                                }else {
                                    setCharacterAttributes(inp+1, fimp - inp, azul, false);
                                    naopode=false;
                                }
                                        
                                   
                                 
                            }
                            else if  (text.substring(inp, fimp).matches("(\\W)*[1-9]+"))
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
                        int z = text.length();
    
                        int cont=0,f=0,e=0,aux=0,g=0,h=0,inc=0,finc=0,contfimc=0;
                        boolean duplabarra =false,coment=false, aspas1=false, aspas2=false;
                        for(int i =0;i<z; i++){
           
           
                           if(text.charAt(i)=='\"') {
             
                               cont++;
                               if(cont ==1) {
                                    f=i;
                         
                               }
                     
                               else if(cont ==2) {
                                    e=i;
                 
                                    setCharacterAttributes(f, e-f+1, amarelo, false);
                                    cont=0;
                                    hastring= true;
                                 
                                }
                            }
                        }
                      
                        try {
                            if(z>=3){
                                for(int j=0;j<z; j++){
                            
                                    if(text.charAt(j)=='/'&& text.charAt(j+1)=='/' ) {
                              
                                     
                                        duplabarra= true;
                                        if( duplabarra == true ){
                                            g=j;
                                           
                                        }   
                                    }else if(text.charAt(j)=='\n' &&  duplabarra == true ) {
                                        h=j;
                                        
                                        duplabarra = false;
                                        setCharacterAttributes(g, h-g+1, vermelho, false);
                                         
                                    }
                            
                                }    
                            
                                for(int i3 =0;i3<z; i3++){
                            
                                    if( text.charAt(i3)=='/'&& text.charAt(i3+1)=='*' ) {
                                            
                                      aux ++;
                                      coment = true;
                                      if( aux==1){
                                        inc=i3;
                                        if(text.charAt(i3-1)=='\"' ){
                                            aspas1 =true;
                          
                                        }
                                      }
                                    
                                    }else if(text.charAt(i3)=='*' && text.charAt(i3+1)=='/') {
                                        finc=i3;
                                       if(text.charAt(i3+2)=='\"'){
                                            aspas2=true;
                                        }
                                        if(aspas1!= true && aspas2 != true ){
                                        if(coment == true){
                                            setCharacterAttributes(inc,finc-inc+2, vermelho, false);
                                            aux=0;
                                            coment=false;
                                            hablocoComentado=true;
                                           
                                        }
                                        
                                    }
                                    }else {
                                        
                                        aspas1=false;
                                        aspas2=false;
                                    }
                            
                                }     
                            } 
                        
                        }catch (Exception ex) {
                        }
                      
                        
                        
                       if(hastring==true ||  hablocoComentado==true){
                           
                        
                            final int indice=0;  
                            x = indice;
                            if (x < 0) x = 0;
                                y= text.length();
                                inp = x;
                                fimp = x;
           
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
                                           if (text.substring(inp, fimp).matches("(\\W)*(inteiro|repita|enquanto|se|senao|fim|numero|e|senaose|nao|mod|texto)"))
                                            {
                                                if(inp==0 && naopode==false){
                                                        setCharacterAttributes(inp, fimp - inp, azul, false);
                                
                                
                                                }else {
                                                        setCharacterAttributes(inp+1, fimp - inp,azul, false);
                                                        naopode=false;
                                                }
                                               
                                            }
                                            else if  (text.substring(inp, fimp).matches("(\\W)*[1-9]+"))
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
                        
                        
                      
            }            
        };
   
        jTextPane1.setDocument(doc) ; 
             
    
    }
    
    
    private void colorir(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.setText(msg);
    }

     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2Abrir = new javax.swing.JMenuItem();
        jMenuItem3Salvar = new javax.swing.JMenuItem();
        jMenuItem4Salvarcomo = new javax.swing.JMenuItem();
        jMenuItem5Sair = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10Colar = new javax.swing.JMenuItem();
        jMenuItem11Desfazer = new javax.swing.JMenuItem();
        jMenuItem12Refazer = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor");
        setExtendedState(getExtendedState());

        jLabel1.setText("Saida");

        jTextPane1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTextPane1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPane1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextPane1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPane1KeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jTextPane1);

        jTextPane2.setMaximumSize(new java.awt.Dimension(1440, 500));
        jScrollPane1.setViewportView(jTextPane2);

        jMenu1.setText("Arquivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Novo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2Abrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2Abrir.setText("Abrir");
        jMenuItem2Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2AbrirActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2Abrir);

        jMenuItem3Salvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3Salvar.setText("Salvar");
        jMenuItem3Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3SalvarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3Salvar);

        jMenuItem4Salvarcomo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4Salvarcomo.setText("Salvar como");
        jMenuItem4Salvarcomo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4SalvarcomoActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4Salvarcomo);

        jMenuItem5Sair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5Sair.setText("Sair");
        jMenuItem5Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5SairActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5Sair);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Copiar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuItem10Colar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10Colar.setText("Colar");
        jMenuItem10Colar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ColarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10Colar);

        jMenuItem11Desfazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem11Desfazer.setText("Desfazer");
        jMenuItem11Desfazer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11DesfazerActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem11Desfazer);

        jMenuItem12Refazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem12Refazer.setText("Refazer");
        jMenuItem12Refazer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12RefazerActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem12Refazer);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Recortar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Selecionar tudo");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenu4.setText("Fonte");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4.setText("Aumentar a fonte");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("Diminuir a fonte");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem10.setText("Tamanho normal");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenu2.add(jMenu4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Compilar");

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem6.setText("Compilar");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem7.setText("Executar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 514, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // Botão copiar
        JTextPane copiar = jTextPane1; 
        copiar.copy();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem11DesfazerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11DesfazerActionPerformed
        // Botão desfazer
        try {  
            int h,h2;  
            h= lista.size();       
            String s = lista.remove(h-1).toString();
            listaAux.add(s);
            h2= lista.size();
            jTextPane1.setText(lista.get(h2-1).toString());
        } catch (Exception ex) {
        }
       
    }//GEN-LAST:event_jMenuItem11DesfazerActionPerformed

    private void jMenuItem12RefazerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12RefazerActionPerformed
        // Botão refazer
        try {
            int re1,re2;
               
            re1= listaAux.size();
            jTextPane1.setText(listaAux.get(re1-1).toString());
            listaAux.remove(re1-1).toString();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jMenuItem12RefazerActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // botão novo 
        new visor().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2AbrirActionPerformed
        // botão abrir
        try {
         
                JFileChooser abrir = new JFileChooser();
                abrir.setCurrentDirectory(null);
                abrir.setFileFilter(new FileNameExtensionFilter("Arquivos por","por"));  
                int retorno =  abrir.showOpenDialog(this);   
             
                FileReader fr = new FileReader(abrir.getSelectedFile()); 
      
                BufferedReader br = new BufferedReader(fr); 
                String linha; 
                StringBuffer sb = new StringBuffer(); 
                while((linha = br.readLine()) != null) { 
                    sb.append(linha).append("\n"); 
                } 
                fr.close(); 
                nome=abrir.getSelectedFile().getName();
                caminho=abrir.getSelectedFile().getPath();
                
                tasalvo = true;
                taaberto =true;
          
                jTextPane1.setText(sb.toString()); 
               
                         
            }catch(Exception erro) {
        
            }
    }//GEN-LAST:event_jMenuItem2AbrirActionPerformed

    private void jMenuItem3SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3SalvarActionPerformed
        // botão salvar
        
        JFileChooser sc = new JFileChooser();
        File nov; 
        
        if(tasalvo==false){
            int retorno = sc.showSaveDialog(this);   
        
            if(retorno!=JFileChooser.APPROVE_OPTION){
                return;
            }
            nov=sc.getSelectedFile();
             
            caminho=nov.getPath();
           
            tasalvo= true;
            try {
                String t1 = jTextPane1.getText();
                  
                BufferedWriter grava1 = new BufferedWriter(new FileWriter(nov));  
                grava1.write(t1.toString());
                grava1.close();
               
                  
            }catch(Exception erro) {
        
    
            }
        }
            
        if(taaberto==true){
            
                try {
                    
                    File out2= new File(caminho);
                     
                    String t2 = jTextPane1.getText();
                   
                    PrintWriter  grava1 = new PrintWriter(new BufferedWriter(new FileWriter(out2)));            
             
                    grava1.flush();
                    grava1.print(t2);
                      
                    grava1.close(); 
                    
        
                }catch(Exception erro) {    
                }
        }
       
        else{
               
                try {
                       
                    File out = new File(caminho);
               
                    String t1 = jTextPane1.getText();
                
                    PrintWriter  grava1 = new PrintWriter(new BufferedWriter(new FileWriter(out)));            
              
                    grava1.print(t1);
                  
                    grava1.close(); 
                     
        
                }catch(Exception erro) {
               
               }
        }
    }//GEN-LAST:event_jMenuItem3SalvarActionPerformed

    private void jMenuItem4SalvarcomoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4SalvarcomoActionPerformed
        // Botão salvar como
        
        
        JFileChooser salvar = new JFileChooser();
        int retorno= salvar.showSaveDialog(this);
         
        if(retorno!=JFileChooser.APPROVE_OPTION){
            return;
        }
        File arquivo= salvar.getSelectedFile();
        caminho = arquivo.getPath();
        try {
            String t = jTextPane1.getText();
            BufferedWriter grava = new BufferedWriter(new FileWriter(arquivo));  
            grava.write(t.toString());
            grava.close();
        }catch(Exception erro) {
        
        }
        
    }//GEN-LAST:event_jMenuItem4SalvarcomoActionPerformed

    private void jMenuItem5SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5SairActionPerformed
        // Botão sair
        //  System.exit(0);
        this.dispose();
    }//GEN-LAST:event_jMenuItem5SairActionPerformed

    private void jMenuItem10ColarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ColarActionPerformed
        // Botão colar
        JTextPane colar = jTextPane1; 
        colar.paste();
    }//GEN-LAST:event_jMenuItem10ColarActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // Botão compilar
        
       
        
        JFileChooser sc = new JFileChooser();
        File nov; 
        
        if(tasalvo==false){
            int retorno = sc.showSaveDialog(this);   
        
            if(retorno!=JFileChooser.APPROVE_OPTION){
                return;
            }
            nov=sc.getSelectedFile();
             
            caminho=nov.getPath();
           
            tasalvo= true;
            try {
                String t1 = jTextPane1.getText();
                  
                BufferedWriter grava1 = new BufferedWriter(new FileWriter(nov));  
                grava1.write(t1.toString());
                grava1.close();
               
                  
            }catch(Exception erro) {
        
    
            }
        }
            
        if(taaberto==true){
            
            try {
                    
                File out2= new File(caminho);
                     
                String t2 = jTextPane1.getText();
                   
                PrintWriter  grava1 = new PrintWriter(new BufferedWriter(new FileWriter(out2)));            
             
                grava1.flush();
                grava1.print(t2);
                      
                grava1.close(); 
                    
        
            }catch(Exception erro) {    
            }
        }
       
        else{
               
            try {
                       
                File out = new File(caminho);
               
                String t1 = jTextPane1.getText();
                
                PrintWriter  grava1 = new PrintWriter(new BufferedWriter(new FileWriter(out)));            
              
                grava1.print(t1);
                  
                grava1.close(); 
                     
        
            }catch(Exception erro) {
               
            }
        }
               
                   
        try{
           // String con2 = "cd C:\\Users\\alve\\Desktop\\oUTROS2\\portugol-master && lua main.lua "+caminho+ " > novo ";
            String con2 = "cd C:\\Portugol-IDE\\IDE\\portugol-master && lua main.lua "+caminho+ " > novo ";
            Process p = Runtime.getRuntime().exec("cmd /c" +con2 );
            p.waitFor();
            if(p.exitValue()==0){
                //String v = "C:\\Users\\alve\\Desktop\\oUTROS2\\portugol-master\\novo";
                String v = "C:\\Portugol-IDE\\IDE\\portugol-master\\novo";
                FileReader fr = new FileReader( v); 
      
                BufferedReader br = new BufferedReader(fr); 
                String linha; 
                StringBuffer sb = new StringBuffer(); 
                while((linha = br.readLine()) != null) { 
                    sb.append(linha).append("\n"); 
                }    
                br.close();  
                fr.close();   
                String a = sb.toString() ;
                if(a == null || a.trim().isEmpty()) {
                    colorir(jTextPane2, "compilação terminada com sucesso", Color.GREEN);
                    compilado = true;
                }else {
                    colorir(jTextPane2,a , Color.RED);
                    compilado = false;
                }
            }       
        }catch(Exception ex) {
            ex.printStackTrace();
     
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // Botão executar
      
        if(compilado == true) {
            try {
           
                //String tudo = "cd C:\\\\Users\\\\alve\\\\Desktop\\\\oUTROS2\\\\portugol-master && echo  @ lua main2.lua "+caminho+" > kkk.bat && echo @ pause >> kkk.bat && echo  @ taskkill /f /im cmd.exe >> kkk.bat ";
                String tudo = "cd C:\\Portugol-IDE\\IDE\\portugol-master && echo @ title Executando > compilar.bat  && echo  @ lua main2.lua "+caminho+" >> compilar.bat && echo @ pause >> compilar.bat && echo  @ taskkill /f /im cmd.exe >> compilar.bat ";
                Process p = Runtime.getRuntime().exec("cmd /c" +tudo );    
        
                p.waitFor();
      
                if(p.exitValue()==0){
                    String k= " cd C:\\Portugol-IDE\\IDE\\portugol-master && start compilar.bat";
                    Process p2 = Runtime.getRuntime().exec("cmd /c" + k); 
                    p2.waitFor();
 
                }
      
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        } else {   
               colorir(jTextPane2, "o código não foi compilaado ou apresenta erros ", Color.RED);
        }
        
     
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // Botão recortar
        
        JTextPane recortar = jTextPane1; 
        recortar.cut();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // botão selecionar tudo
        jTextPane1.selectAll();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTextPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyPressed
        // TODO add your handling code here:
        // System.out.println("algo foi escrit");
    
    }//GEN-LAST:event_jTextPane1KeyPressed

    private void jTextPane1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyTyped
        // Botão de eventos
       
        String st1,st2;
        st1="";
        if(evento==true){
            lista.add("");
            st1= jTextPane1.getText();
            evento = false;
        }
        
        st2= jTextPane1.getText();
        if(st1.equals(st2) == false){
            lista.add(jTextPane1.getText());
          
        }
       
    }//GEN-LAST:event_jTextPane1KeyTyped

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // Aumentar o tamanho da fonte
                 
        aumentar  =  font.getSize(); 
        aumentar =  aumentar + 2;
        font = new Font("Dialog", Font.PLAIN, aumentar);              
        jTextPane1.setFont( font);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // Botão diminuir fonte
        
        diminuir =  font.getSize();
        diminuir =  diminuir - 2;
        font = new Font("Dialog", Font.PLAIN, diminuir);
        jTextPane1.setFont( font);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // fonte normal
                       
        font = new Font("Dialog", Font.PLAIN, 12);      
        jTextPane1.setFont( font);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jTextPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPane1KeyReleased

    private void jTextPane1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTextPane1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPane1AncestorAdded
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(visor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(visor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(visor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(visor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new visor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem10Colar;
    private javax.swing.JMenuItem jMenuItem11Desfazer;
    private javax.swing.JMenuItem jMenuItem12Refazer;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem2Abrir;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem3Salvar;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem4Salvarcomo;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem5Sair;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables
}
