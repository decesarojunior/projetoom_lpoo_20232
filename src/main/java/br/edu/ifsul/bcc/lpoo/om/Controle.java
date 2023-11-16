package br.edu.ifsul.bcc.lpoo.om;

import br.edu.ifsul.bcc.lpoo.om.gui.JFramePrincipal;
import br.edu.ifsul.bcc.lpoo.om.gui.JMenuBarHome;
import br.edu.ifsul.bcc.lpoo.om.gui.JPanelHome;
import br.edu.ifsul.bcc.lpoo.om.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.bcc.lpoo.om.gui.funcionario.JPanelFuncionario;
import br.edu.ifsul.bcc.lpoo.om.model.Funcionario;
import br.edu.ifsul.bcc.lpoo.om.model.dao.PersistenciaJDBC;
import javax.swing.JOptionPane;



/**
 *
 * @author telmo
 */
public class Controle {
    
    private JFramePrincipal jframe;
    private PersistenciaJDBC conexaoJDBC;
    private JPanelAutenticacao telaAutenticacao;
    private JPanelFuncionario telaFuncionario;
    private JMenuBarHome menuBar;
    private JPanelHome  telaHome;
    

    public Controle() {
        
    }
    
    
    public void autenticar(String cpf, String senha) {
        //  implementar o metodo doLogin da classe PersistenciaJDBC
        //  chamar o doLogin e verificar o retorno.
        // se o retorno for nulo, informar ao usuário
        //se nao for nulo, apresentar a tela de boas vindas e o menu.
        try{

            Funcionario f =  conexaoJDBC.doLogin(cpf, senha);
            
            if(f != null){

                JOptionPane.showMessageDialog(telaAutenticacao, "Funcionario "+f.getCpf()+" autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);

                 jframe.setJMenuBar(menuBar);//adiciona o menu de barra no frame
                 jframe.showTela("tela_home");//muda a tela para o painel de boas vindas (home)

            }else{

                JOptionPane.showMessageDialog(telaAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(telaAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean conectarBD() throws Exception {
            conexaoJDBC = new PersistenciaJDBC();
            if(conexaoJDBC!= null){
                return conexaoJDBC.conexaoAberta();
            }
            return false;
    }
   
    protected void initComponents(){
         
         jframe = new JFramePrincipal();
         
         telaAutenticacao = new JPanelAutenticacao(this);
         
         telaHome = new JPanelHome(this);
         
         menuBar = new JMenuBarHome(this);
         
         telaFuncionario = new JPanelFuncionario(this);
         
         //adicionando no baralho a tela de autenticacao
         jframe.addTela(telaAutenticacao, "tela_autenticacao");
         
         jframe.addTela(telaHome, "tela_home"); //adiciona
         
         jframe.addTela(telaFuncionario, "tela_funcionario");
         
         jframe.showTela("tela_autenticacao");
         
         jframe.setVisible(true);//mostra o JFrame
    }
    
    public void showTela(String nomeTela){
         
        //para cada nova tela de CRUD adicionar um elseif
        
         if(nomeTela.equals("tela_funcionario")){
             
            telaFuncionario.showTela("tela_funcionario_listagem");
                        
         }
         
         jframe.showTela(nomeTela);
         
    }
    
    
}
