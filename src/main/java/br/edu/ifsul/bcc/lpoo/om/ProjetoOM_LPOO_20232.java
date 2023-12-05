

package br.edu.ifsul.bcc.lpoo.om;

import javax.swing.JOptionPane;

/**
 *
 * @author telmo
 */
public class ProjetoOM_LPOO_20232 {
    
    private Controle controle;
    public ProjetoOM_LPOO_20232(){
            try {
                controle = new Controle();//cria a instancia e atribui para o atributo controle.
                ////primeiramente - tenta estabelecer a conexao com o banco de dados.
                if(controle.conectarBD()){
                     //JOptionPane.showMessageDialog(null, "conectou no Banco de Dados!", "Banco de Dados", JOptionPane.PLAIN_MESSAGE);
                    //inicializa a interface gráfica.
                    controle.initComponents();
                }else{
                    JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", 
                            "Banco de Dados", JOptionPane.ERROR_MESSAGE);
                }
        } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no "
                        + "Banco de Dados: "+ex.getLocalizedMessage(), "Banco de Dados", 
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        //cria uma instancia.
        new ProjetoOM_LPOO_20232();
    }
}
