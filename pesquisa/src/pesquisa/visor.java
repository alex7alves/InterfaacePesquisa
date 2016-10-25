/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pesquisa;



import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Alex Alves
 */
public class visor extends javax.swing.JFrame {

    /**
     * Creates new form visor
     */
    Font font =  new Font("Dialog", Font.PLAIN, 12);
    private boolean compilado;
    private boolean sobrescrever;
    private boolean tasalvo;
    private boolean taaberto;
    private String nome=null;
    private String caminho=null;
    public int aumentar,diminuir,TamanhoFonte;
    final UndoManager Gerente = new UndoManager();
    private int cont_Lista_String,cont_aux;
    private String[] StringArray_Lista;
    private List lista = new ArrayList();
    private List listaAux = new ArrayList();
    private boolean desfazer,refazer,evento,OutroEvento;
    private String pegaAberto,PegaSalvo;
    int flag=0;
    String auxf;
    private String local=null, pegaTexto=null;
    private static boolean AlterouTamanho=false;
    public visor() {
      
        tasalvo = false;
        taaberto=false;
        compilado = false;
        sobrescrever= false;
        desfazer=true;
        refazer=true;
        evento=true;
        OutroEvento=false;
        cont_aux=0;
        
        initComponents();
        try{
            jTextPane1.setEditorKit(new MeuTab());
          
        }catch(Exception e){
		
        }

        Fechar();
     
  
        TextLineNumber contadorLinhas = new TextLineNumber(jTextPane1);  
        jScrollPane3.setRowHeaderView(contadorLinhas);  
         
        ColorePane cp = new ColorePane();
        cp.PalavraReservada(jTextPane1);  
        URL pegaImagem = this.getClass().getResource("Letter-Q-icon.png");
        Image imagem = Toolkit.getDefaultToolkit().getImage(pegaImagem);
        this.setIconImage(imagem);
    }
    public visor(String[] a) {
      
        tasalvo = false;
        taaberto=false;
        compilado = false;
        sobrescrever= false;
        desfazer=true;
        refazer=true;
        evento=true;
        OutroEvento=false;
        cont_aux=0;
        
        initComponents();
        try{
            jTextPane1.setEditorKit(new MeuTab());
          
        }catch(Exception e){
		
        }
        Fechar();
        
        TextLineNumber contadorLinhas = new TextLineNumber(jTextPane1);  
        jScrollPane3.setRowHeaderView(contadorLinhas);  
         
        ColorePane cp = new ColorePane();
        cp.PalavraReservada(jTextPane1);  
        URL pegaImagem = this.getClass().getResource("Letter-Q-icon.png");
        Image imagem = Toolkit.getDefaultToolkit().getImage(pegaImagem);
        this.setIconImage(imagem);
        Parametro(a);
     
    }
    public visor(String a,int Tam, boolean aberto, boolean salvo,String nom,String cam,String l,boolean ev) {
      
        tasalvo = salvo;
        taaberto=aberto;
        compilado = false;
        sobrescrever= false;
        desfazer=true;
        refazer=true;
        evento=true;
        OutroEvento=false;
        cont_aux=0;
        nome =nom;
        caminho = cam;
        local = l;
        TamanhoFonte= Tam;
        evento =ev;
        setExtendedState( MAXIMIZED_BOTH );
        initComponents();
        try{
            jTextPane1.setEditorKit(new MeuTab());
          
        }catch(Exception e){
		
        }
        Fechar();
        
        //setSize(largura,altura);
        if(nome == null || nome.trim().isEmpty()) {        
        
        }else {
             this.setTitle(nome + "- QuoGol IDE");   
        }
        jTextPane1.setText(a);
        font = new Font("Dialog", Font.PLAIN,TamanhoFonte);
        jTextPane1.setFont(font);
        TextLineNumber contadorLinhas = new TextLineNumber(jTextPane1);  
        jScrollPane3.setRowHeaderView(contadorLinhas);  
         
        ColorePane cp = new ColorePane();
        cp.PalavraReservada(jTextPane1);  
        URL pegaImagem = this.getClass().getResource("Letter-Q-icon.png");
        Image imagem = Toolkit.getDefaultToolkit().getImage(pegaImagem);
        this.setIconImage(imagem);
        jTextPane1.setText(a);
        font = new Font("Dialog", Font.PLAIN,TamanhoFonte);
        jTextPane1.setFont(font);
        contadorLinhas = new TextLineNumber(jTextPane1);  
        jScrollPane3.setRowHeaderView(contadorLinhas);  
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
    public void Parametro(String[] args) {
            if(args.length >= 1) {
                    File file = new File(args[0]);
                    try {
        
             
                 
               
                        FileReader fr = new FileReader(file); 
      
                        //BufferedReader br = new BufferedReader(fr); 
                        BufferedReader br=  new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
                        String linha; 
                        StringBuffer sb = new StringBuffer(); 
                        while((linha = br.readLine()) != null) { 
                            sb.append(linha).append("\n"); 
                        } 
                        fr.close(); 
                        final String nom,path;
                        nome = file.getName();
                        caminho = file.getPath();
                        local =file.getParent();
                        this.setTitle(nome+"- QuoGol IDE");
                        jTextPane1.setText(sb.toString()); 
                        tasalvo = true;
                        taaberto =true;
                        pegaAberto =  jTextPane1.getText();
                        colorir(jTextPane2,"", Color.RED);
                        
            }catch(Exception erro) {
        
            }
       
        }
    }
    

    public void Fechar(){

        //  impelentando o x
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
              
               String comparar ;
               comparar = jTextPane1.getText();
                
       if( comparar.equals(pegaAberto)==true ||  comparar.equals(PegaSalvo)==true || evento==true){
     // if( comparar.equals(pegaTexto)==true || evento==true){          
                dispose();
        }else {
             
                // desabilitando o x
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                int retorno = JOptionPane.showConfirmDialog(null,"Deseja salvar as alterações?","Sair - Portugol IDE",JOptionPane.YES_NO_CANCEL_OPTION);     
		if (retorno ==0){
			Salvar(); 
                        dispose();
                }
                else if (retorno ==2){
		 	
                }
                  else if (retorno ==1){
		 	dispose();
                }
               
            }
            }
       
        });
        
    }
    private void Abrir(){
        // botão abrir
        try {
        
                JFileChooser abrir = new JFileChooser();
                abrir.setCurrentDirectory(null);
                abrir.setFileFilter(new FileNameExtensionFilter("Arquivos gol","gol"));  
                int retorno =  abrir.showOpenDialog(this);   
                if(retorno == JFileChooser.APPROVE_OPTION){
                    FileReader fr = new FileReader(abrir.getSelectedFile()); 
                    String c = abrir.getSelectedFile().getPath();
                   // BufferedReader br = new BufferedReader(fr); 
                    String linha; 
                    StringBuffer sb = new StringBuffer(); 
                    BufferedReader br=  new BufferedReader(new InputStreamReader(new FileInputStream(c), "UTF-8"));
                  while((linha = br.readLine()) != null) { 
                        sb.append(linha).append("\n"); 
                    } 
                    fr.close(); 
                    nome=abrir.getSelectedFile().getName();
                    caminho=abrir.getSelectedFile().getPath();
                    local =abrir.getSelectedFile().getParent();
                    tasalvo = true;
                    taaberto =true;
                    this.setTitle(nome+"- QuoGol IDE");
                    jTextPane1.setText(sb.toString()); 
                    pegaAberto =  jTextPane1.getText();
                    pegaTexto  =  jTextPane1.getText();
                    colorir(jTextPane2,"", Color.RED);
                }         
            }catch(Exception erro) {
        
            }
        
    }
    private void Compilar(){
        // botão compilar
        
        Salvar();
        
        try{
      
          
            String con2 = "cd C:\\QuoGol-IDE\\IDE\\portugol-master && chcp 65001 && lua53 compila.lua "+"\""+caminho+"\""+ " > "+"\""+local+"\\CompilarGol\"";
            Process p = Runtime.getRuntime().exec("cmd /c" +con2 );
            p.waitFor();
         
            if(p.exitValue()==0){
               
                
                //BufferedReader br=  new BufferedReader(new InputStreamReader(new FileInputStream("C:\\QuoGol-IDE\\IDE\\portugol-master\\novo"), "UTF-8"));
                BufferedReader br=  new BufferedReader(new InputStreamReader(new FileInputStream(local+"\\CompilarGol"), "UTF-8"));
                String linha; 
                StringBuffer sb = new StringBuffer(); 
                while((linha = br.readLine()) != null) { 
                    sb.append(linha).append("\n"); 
                }    
                br.close();  
                
                String a = sb.toString() ;
       
            
                if(a == null || a.trim().isEmpty()) {
                  
                    colorir(jTextPane2, "compilação terminada com sucesso",  new Color(25,200,70));
                    compilado = true;
                }else {
                    colorir(jTextPane2,a , Color.RED);
                    compilado = false;
                }
            
            }else {
               // con2 = "cd C:\\QuoGol-IDE\\IDE\\portugol-master && chcp 65001 && lua53 compila.lua "+"\""+caminho+"\""+ " 2> novo ";
               con2 = "cd C:\\QuoGol-IDE\\IDE\\portugol-master && chcp 65001 && lua53 compila.lua "+"\""+caminho+"\""+ " 2> "+"\""+local+"\\CompilarGol\""; 
               Process p2 = Runtime.getRuntime().exec("cmd /c" +con2 );
            
                p2.waitFor();
           
               
                BufferedReader br=  new BufferedReader(new InputStreamReader(new FileInputStream(local+"\\CompilarGol"), "UTF-8"));
                String linha; 
                StringBuffer sb = new StringBuffer(); 
                while((linha = br.readLine()) != null) { 
                    sb.append(linha).append("\n"); 
                }    
                br.close();  
                 
                String a = sb.toString() ;
               
                if(a == null || a.trim().isEmpty()) {
                  
                    colorir(jTextPane2, "compilação terminada com sucesso", new Color(25,200,70));
                    compilado = true;
                }else {
                    colorir(jTextPane2,"Erro na compilacao :\n "+a , Color.RED);
                    compilado = false;
                }
            
            }       
        }catch(Exception ex) {
            ex.printStackTrace();
     
        }
    }
    private void Salvar(){
        JFileChooser sc = new JFileChooser();
        File nov; 
        
        if(tasalvo==false){
            int retorno = sc.showSaveDialog(this);   
        
            if(retorno!=JFileChooser.APPROVE_OPTION){
                return;
            }
            nov=sc.getSelectedFile();
             
            caminho=nov.getPath();
            nome = nov.getName();
            local =nov.getParent();
            this.setTitle(nome+"- QuoGol IDE");
            tasalvo= true;
            try {
                String t1 = jTextPane1.getText();
                BufferedWriter grava1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nov),"UTF-8"));
               // BufferedWriter grava1 = new BufferedWriter(new FileWriter(nov));  
                grava1.write(t1.toString());
                grava1.close();
                PegaSalvo =  jTextPane1.getText();
                pegaTexto  =  jTextPane1.getText();    
                  
            }catch(Exception erro) {
        
    
            }
        }
            
        if(taaberto==true){
            
                try {
                    
                    File out2= new File(caminho);
                     
                    String t2 = jTextPane1.getText();
                    PrintWriter  grava1 = new PrintWriter (new OutputStreamWriter (new FileOutputStream (out2), "UTF-8"));
                   // PrintWriter  grava1 = new PrintWriter(new BufferedWriter(new FileWriter(out2)));            
             
                    grava1.flush();
                    grava1.print(t2);
                      
                    grava1.close(); 
                    PegaSalvo =  jTextPane1.getText();
                    pegaTexto  =  jTextPane1.getText();
                }catch(Exception erro) {    
                }
        }
       
        else{
               
                try {
                       
                    File out = new File(caminho);
               
                    String t1 = jTextPane1.getText();
                    PrintWriter  grava1 = new PrintWriter (new OutputStreamWriter (new FileOutputStream (out), "UTF-8"));
                  //  PrintWriter  grava1 = new PrintWriter(new BufferedWriter(new FileWriter(out)));            
              
                    grava1.print(t1);
                  
                    grava1.close(); 
                    PegaSalvo =  jTextPane1.getText(); 
                    pegaTexto =  jTextPane1.getText();
                   
                }catch(Exception erro) {
               
               }
        }
    }
    private void SalvarComo(){
        JFileChooser salvar = new JFileChooser();
        salvar.setDialogTitle("Salvar como");
        int retorno= salvar.showSaveDialog(this);
       
        if(retorno!=JFileChooser.APPROVE_OPTION){
            return;
        }
        File arquivo= salvar.getSelectedFile();
        caminho = arquivo.getPath();
        nome=arquivo.getName();
        local =arquivo.getParent();
        try {
            String t = jTextPane1.getText();
            BufferedWriter grava = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo),"UTF-8"));
           // BufferedWriter grava = new BufferedWriter(new FileWriter(arquivo));  
            grava.write(t.toString());
            grava.close();
            this.setTitle(nome+"- QuoGol IDE");
            colorir(jTextPane2,"", Color.RED);
            PegaSalvo =  jTextPane1.getText();
            pegaTexto  =  jTextPane1.getText();
        }catch(Exception erro) {
        
        }
    }
    public void Executar(){
         if(compilado == true) {
            try {
               
               // String tudo = "cd C:\\QuoGol-IDE\\IDE\\portugol-master && echo @echo off > compilar.bat &&  echo @ chcp 65001  ^> null >> compilar.bat && echo @ title Executando >> compilar.bat  && echo  @ lua53 main2.lua "+"\""+caminho+"\""+" >> compilar.bat && echo @ pause >> compilar.bat && echo  @ taskkill /f /im cmd.exe >> compilar.bat ";
                String tudo = "cd "+ "\""+ local+ "\""+ "&& echo @echo off >"+ "\""+local+"\\ExecutarGol.bat\""  +" &&  echo @ chcp 65001  ^> null >>" + "\""+local+"\\ExecutarGol.bat\"" +"&& echo @ title Executando >>" + "\""+local+"\\ExecutarGol.bat\""  + " && echo cd  C:\\QuoGol-IDE\\IDE\\portugol-master" + " >> "+ "\""+local+"\\ExecutarGol.bat\""+ " && echo lua53 main2.lua "+"\""+caminho+"\""+" >>"+ "\""+local+"\\ExecutarGol.bat\""+ "&& echo @ pause >>"+ "\""+local+"\\ExecutarGol.bat\""+ "&& echo  @ taskkill /f /im cmd.exe >>"+ "\""+local+"\\ExecutarGol.bat\"" ;         
                Process p = Runtime.getRuntime().exec("cmd /c" +tudo );    
        
                p.waitFor();
      
                if(p.exitValue()==0){
                    String k= " cd "+"\""+local+"\""+" && start ExecutarGol.bat";
                    Process p2 = Runtime.getRuntime().exec("cmd /c" + k); 
                    p2.waitFor();
 
                }
      
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        } else {   
               colorir(jTextPane2, "O código não foi compilado ou apresenta erros ", Color.RED);
        }
    }
    public void Aumentar(){
        aumentar  =  font.getSize(); 
        aumentar =  aumentar + 2;
        TamanhoFonte=aumentar;
        AlterouTamanho=true;
        font = new Font("Dialog", Font.PLAIN, aumentar);              
        jTextPane1.setFont( font);             
        jTextPane2.setFont(font);
        TextLineNumber conl = new TextLineNumber(jTextPane1);
        jScrollPane3.setRowHeaderView(conl);  
    }
    public void Diminuir(){
        diminuir =  font.getSize();
        diminuir =  diminuir - 2;
        TamanhoFonte = diminuir;
        AlterouTamanho=true;
        font = new Font("Dialog", Font.PLAIN, diminuir);
        jTextPane1.setFont( font);
        jTextPane2.setFont(font);
        TextLineNumber conl = new TextLineNumber(jTextPane1);        
        jScrollPane3.setRowHeaderView(conl);  
    }
    public void BordaLabel(JLabel jb){
        jb.setBorder(BorderFactory.createLineBorder(Color.black)); // seta borda preta
        jb.setBorder(BorderFactory.createRaisedBevelBorder());    // eleva borda para cima
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QuoGol IDE");
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
        jTextPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextPane1FocusGained(evt);
            }
        });
        jTextPane1.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                jTextPane1AncestorResized(evt);
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

        jTextPane2.setEditable(false);
        jTextPane2.setMaximumSize(new java.awt.Dimension(1440, 500));
        jScrollPane1.setViewportView(jTextPane2);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/page-add32b.png"))); // NOI18N
        jLabel3.setToolTipText("Novo aruivo");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/folder-page-icon.png"))); // NOI18N
        jLabel4.setToolTipText("Abrir");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/Save-icon.png"))); // NOI18N
        jLabel6.setToolTipText("Salvar");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/Actions-document-save-all-icon.png"))); // NOI18N
        jLabel5.setToolTipText("Salvar como");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/Zoom-In-icon.png"))); // NOI18N
        jLabel7.setToolTipText("Aumentar fonte");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/cog-icon.png"))); // NOI18N
        jLabel8.setToolTipText("Compilar");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/control-end-blue-icon.png"))); // NOI18N
        jLabel9.setToolTipText("Executar");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/Zoom-Out-icon.png"))); // NOI18N
        jLabel10.setToolTipText("Diminuir fonte");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pesquisa/Computer-icon.png"))); // NOI18N
        jLabel11.setToolTipText("Tela cheia");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });

        jMenu1.setText("Arquivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/page_white_add.png"))); // NOI18N
        jMenuItem1.setText("Novo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2Abrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2Abrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/folder_page.png"))); // NOI18N
        jMenuItem2Abrir.setText("Abrir");
        jMenuItem2Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2AbrirActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2Abrir);

        jMenuItem3Salvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3Salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/disk.png"))); // NOI18N
        jMenuItem3Salvar.setText("Salvar");
        jMenuItem3Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3SalvarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3Salvar);

        jMenuItem4Salvarcomo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4Salvarcomo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/disk_multiple.png"))); // NOI18N
        jMenuItem4Salvarcomo.setText("Salvar como");
        jMenuItem4Salvarcomo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4SalvarcomoActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4Salvarcomo);

        jMenuItem5Sair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5Sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bomb.png"))); // NOI18N
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
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/book_next.png"))); // NOI18N
        jMenuItem9.setText("Copiar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuItem10Colar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10Colar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/book_edit.png"))); // NOI18N
        jMenuItem10Colar.setText("Colar");
        jMenuItem10Colar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ColarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10Colar);

        jMenuItem11Desfazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem11Desfazer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/arrow_turn_left.png"))); // NOI18N
        jMenuItem11Desfazer.setText("Desfazer");
        jMenuItem11Desfazer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11DesfazerActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem11Desfazer);

        jMenuItem12Refazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItem12Refazer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/arrow_turn_right.png"))); // NOI18N
        jMenuItem12Refazer.setText("Refazer");
        jMenuItem12Refazer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12RefazerActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem12Refazer);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cut_red.png"))); // NOI18N
        jMenuItem2.setText("Recortar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/book_open.png"))); // NOI18N
        jMenuItem3.setText("Selecionar tudo");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/lightbulb.png"))); // NOI18N
        jMenu4.setText("Fonte");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/magnifier_zoom_in.png"))); // NOI18N
        jMenuItem4.setText("Aumentar a fonte");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/magifier_zoom_out.png"))); // NOI18N
        jMenuItem5.setText("Diminuir a fonte");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/accept.png"))); // NOI18N
        jMenuItem10.setText("Tamanho normal");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenu2.add(jMenu4);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/application.png"))); // NOI18N
        jMenuItem8.setText("Tela cheia");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Compilar");

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cog.png"))); // NOI18N
        jMenuItem6.setText("Compilar");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/resultset_next.png"))); // NOI18N
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
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane3)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            String l = listaAux.remove(re1-1).toString();
            lista.add(l);
            
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jMenuItem12RefazerActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // botão novo 
        new visor().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2AbrirActionPerformed
        // botão abrir
        Abrir();
    }//GEN-LAST:event_jMenuItem2AbrirActionPerformed

    private void jMenuItem3SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3SalvarActionPerformed
        // botão salvar
       Salvar();
    }//GEN-LAST:event_jMenuItem3SalvarActionPerformed

    private void jMenuItem4SalvarcomoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4SalvarcomoActionPerformed
        // Botão salvar como
       SalvarComo();
        
    }//GEN-LAST:event_jMenuItem4SalvarcomoActionPerformed

    private void jMenuItem5SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5SairActionPerformed
        // Botão sair
       
        String comparar ;
        comparar = jTextPane1.getText();
                
        if( comparar.equals(pegaAberto)==true ||  comparar.equals(PegaSalvo)==true || evento==true){
                
                dispose();
        }else {
           
                int retorno = JOptionPane.showConfirmDialog(null,"Deseja salvar as alterações?","Sair - Portugol IDE",JOptionPane.YES_NO_CANCEL_OPTION);     
		if (retorno ==0){
			Salvar(); 	
                }
                else if (retorno ==2){
		 	
                }
                else if (retorno ==1){
		 	dispose();
                }
               
            }
            
               
    }//GEN-LAST:event_jMenuItem5SairActionPerformed

    private void jMenuItem10ColarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ColarActionPerformed
        // Botão colar
        JTextPane colar = jTextPane1; 
        colar.paste();
        ColorePane cp = new ColorePane();
        cp.PalavraReservada(jTextPane1);  
    }//GEN-LAST:event_jMenuItem10ColarActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // Botão compilar
       Compilar();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // Botão executar
      
        Executar();
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
                 
       Aumentar();
      
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // Botão diminuir fonte
        
       Diminuir();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // fonte normal
                       
        font = new Font("Dialog", Font.PLAIN, 12);   
        TamanhoFonte=12;
        AlterouTamanho=true;
        jTextPane1.setFont( font);
        jTextPane2.setFont(font);
        TextLineNumber conl = new TextLineNumber(jTextPane1); 
        jScrollPane3.setRowHeaderView(conl);  
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jTextPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPane1KeyReleased

    private void jTextPane1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTextPane1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPane1AncestorAdded

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // Imagem do novo 
         new visor().setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // Imagem abrir
        Abrir();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // imagem salvar
        Salvar();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // Imagem salvarComo
        SalvarComo();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // Aumentar fonte
        Aumentar();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // Imagem compilar
        Compilar();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // Imagem executar
        Executar();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // diminuir fonte
        Diminuir();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // da imagem novo
        BordaLabel(jLabel3);
     
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // da imagem novo
        jLabel3.setBorder(null);
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // Imagem abrir
        BordaLabel(jLabel4);
        
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // imagem abrir 
         jLabel4.setBorder(null);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // da imagem salvar
        BordaLabel(jLabel6);
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // da imagem salvar
        jLabel6.setBorder(null);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // da imagem salvar como
        BordaLabel(jLabel5);
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        // da imagem salvar como
        jLabel5.setBorder(null);
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        // da imagem compilar
        BordaLabel(jLabel8);
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        // da imagem compilar
        jLabel8.setBorder(null);
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // da imagem exeutar
        BordaLabel(jLabel9);
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // Da imagem exeutar
        jLabel9.setBorder(null);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // da imagem aumentar
        BordaLabel(jLabel7);
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // da imagem aumentar
        jLabel7.setBorder(null);
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        // da imagem diminuir
        BordaLabel(jLabel10);
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        // da imagem diminuir
        jLabel10.setBorder(null);
    }//GEN-LAST:event_jLabel10MouseExited

    private void jTextPane1AncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jTextPane1AncestorResized
        // 
       
    }//GEN-LAST:event_jTextPane1AncestorResized

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // Tela cheia
        evento =false;
        if(AlterouTamanho==false){
            TamanhoFonte =12;
        }
        new TelaCheia(jTextPane1.getText(),TamanhoFonte,taaberto,tasalvo,nome,caminho,local,evento).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        // Tela cheia
        BordaLabel(jLabel11);
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        // Tela cheia
         jLabel11.setBorder(null);
    }//GEN-LAST:event_jLabel11MouseExited

    private void jTextPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPane1FocusGained

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        
         // Botão  Tela cheia
        evento =false;
        if(AlterouTamanho==false){
            TamanhoFonte =12;
        }
        new TelaCheia(jTextPane1.getText(),TamanhoFonte,taaberto,tasalvo,nome,caminho,local,evento).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem8ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(  final String[] args) {
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
                new visor(args).setVisible(true);
             

            } 
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables
}
