
package br.edu.ifsul.bcc.lpoo.om.gui.funcionario;
import br.edu.ifsul.bcc.lpoo.om.Controle;
import br.edu.ifsul.bcc.lpoo.om.model.Funcionario;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author telmo
 */
public class JPanelFuncionarioFormulario extends JPanel implements ActionListener{
    
    private GridBagLayout gridBagLayout;
    private GridBagConstraints posicionador;
    
    public Controle controle;
    public JPanelFuncionario pnlFuncionario;
    
    private JLabel lblCPF;
    private JTextField txfCPF;
    
    private JLabel lblNome;
    private JTextField txfNome;
    
    private JLabel lblSenha;
    private JPasswordField psfSenha;
    
    private JLabel lblNumero_ctps; 
    private JTextField txfNumero_ctps;
    private JLabel lblDataAdmissao;
    private JTextField txfDataAdmissao;
    
    private JLabel lblCargo;
    private JComboBox cbxCargo;
    private DefaultComboBoxModel modeloComboCargo;
    
    private JButton btnSalvar;
    private JButton btnCancelar;
 
    public JPanelFuncionarioFormulario(Controle controle, JPanelFuncionario pnlFuncionario) {
        this.controle = controle;
        this.pnlFuncionario = pnlFuncionario;
        
        initComponents();
    }
    
    public void populaComboCargo(){
        
    }
    
    public void setFuncionarioFormulario(Funcionario f){
        if(f == null){
            txfCPF.setText("");
            txfNome.setText("");
            psfSenha.setText("");
            txfNumero_ctps.setText("");
            txfDataAdmissao.setText("");
            cbxCargo.setSelectedIndex(0);
        }else{
            txfCPF.setText(f.getCpf());
            txfNome.setText(f.getNome());
            psfSenha.setText(f.getSenha());
            txfNumero_ctps.setText(f.getNumero_ctps());
            
            txfDataAdmissao.setText(f.getData_admmissao_string());
            cbxCargo.setSelectedItem(f.getCargo());
                    
        }
    }
    
    private void initComponents(){
        
        lblCPF = new JLabel("CPF");
        txfCPF = new JTextField(20);
    
        lblNome = new JLabel("Nome:");
        txfNome = new JTextField(30);
    
        lblSenha = new JLabel("Senha:");
        psfSenha = new JPasswordField(10);
    
        lblNumero_ctps = new JLabel("Numero da CTPS:"); 
        txfNumero_ctps = new JTextField(10);
    
        lblDataAdmissao = new JLabel("Data de Admissão:");
        txfDataAdmissao = new JTextField(10);
    
        lblCargo = new JLabel("Cargo:");
        cbxCargo = new JComboBox();    
        modeloComboCargo = new DefaultComboBoxModel(new Object[]{"Selecione"});
        cbxCargo.setModel(modeloComboCargo);
    
        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        this.add(lblCPF, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        this.add(txfCPF, posicionador);
                
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        this.add(lblNome, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        this.add(txfNome, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        this.add(lblSenha, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        this.add(psfSenha, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        this.add(lblNumero_ctps, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        this.add(txfNumero_ctps, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        this.add(lblDataAdmissao, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        this.add(txfDataAdmissao, posicionador); 
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 0;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END;
        this.add(lblCargo, posicionador);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 1;
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;
        this.add(cbxCargo, posicionador);  
        
    }
    
   

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
