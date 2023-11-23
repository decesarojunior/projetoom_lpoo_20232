
package br.edu.ifsul.bcc.lpoo.om.gui.funcionario;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.model.Funcionario;
import javax.swing.JPanel;

/**
 *
 * @author telmo
 */
public class JPanelFuncionarioFormulario extends JPanel {
    
    public Controle controle;
    public JPanelFuncionario pnlFuncionario;

    public JPanelFuncionarioFormulario(Controle controle, JPanelFuncionario pnlFuncionario) {
        this.controle = controle;
        this.pnlFuncionario = pnlFuncionario;
    }
    
    public void setFuncionarioFormulario(Funcionario f){
        //seta nos campos
    }
    
    
    
}
