
package br.edu.ifsul.bcc.lpoo.om.gui.funcionario;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.model.Funcionario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author telmo
 */
public class JPanelFuncionarioListagem extends JPanel {
    
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txtFiltro;
    private JButton btnFiltro;
    private FlowLayout layoutFlowNorte;
    
    private JPanel pnlCentro;
    private JScrollPane scpCentro;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private BorderLayout layoutBorderCentro;
    
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnRemover;
    private FlowLayout layoutFlowSul;
    
    private BorderLayout layoutBorder;
    
    public Controle controle;
    public JPanelFuncionario pnlFuncionario;

    public JPanelFuncionarioListagem(Controle controle, JPanelFuncionario pnlFuncionario) {
        this.controle = controle;
        this.pnlFuncionario = pnlFuncionario;
        
       initComponents();
    }
    
    private void initComponents(){
        layoutBorder = new BorderLayout();
        this.setLayout(layoutBorder);
        
        pnlCentro = new JPanel();
        layoutBorderCentro = new BorderLayout();
        pnlCentro.setLayout(layoutBorderCentro);
        tabela = new JTable();
        
        modeloTabela = new DefaultTableModel(
          new String [] {
                  "CPF", "Nome"}, 0);
          
        tabela.setModel(modeloTabela);
        
        scpCentro = new JScrollPane();
        scpCentro.setViewportView(tabela);
        pnlCentro.add(scpCentro, BorderLayout.CENTER);
        
        this.add(pnlCentro, BorderLayout.CENTER);
    }
    
     public void populaTable(){
        
        DefaultTableModel model =  (DefaultTableModel) tabela.getModel();//recuperacao do modelo da tabela

        model.setRowCount(0);//elimina as linhas existentes (reset na tabela)

        try {

            Collection<Funcionario> listFuncionarios =  controle.getConexaoJDBC().listFuncionarios();
            for(Funcionario f : listFuncionarios){
                                
                model.addRow(new Object[]{f, //chame o toString
                                          f.getNome() 
                                          });

            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Funcionarios -:"+ex.getLocalizedMessage(), "Jogadores", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }        
        
    }
    
    
}
