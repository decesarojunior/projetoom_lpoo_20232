package br.edu.ifsul.bcc.lpoo.om;

import br.edu.ifsul.bcc.lpoo.om.gui.JFramePrincipal;
import br.edu.ifsul.bcc.lpoo.om.gui.JMenuBarHome;
import br.edu.ifsul.bcc.lpoo.om.gui.autenticacao.JPanelAutenticacao;
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
    private JMenuBarHome menuBar;
    

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
         
         menuBar = new JMenuBarHome(this);
         
         //adicionando no baralho a tela de autenticacao
         jframe.addTela(telaAutenticacao, "tela_autenticacao");
         
         jframe.showTela("tela_autenticacao");
         
         jframe.setVisible(true);//mostra o JFrame
    }
    
    
}
