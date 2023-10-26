
package br.edu.ifsul.bcc.lpoo.om.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
 * @author telmo
 * 
 */
//heranca: JFramePrincipal herda as caracteristicas de JFrame
public class JFramePrincipal extends JFrame {
    
    public JFramePrincipal(){
    
        initComponents();
    }
    
    private void initComponents(){
        
        //customização do JFrame
        //defini o titulo do Jframe
        this.setTitle("Sisteminha para CRUD - Oficina Mecânica"); //seta o título do jframe
        
         //defini o tamanho mínimo
        this.setMinimumSize(new Dimension(600,600)); //tamanho minimo quando for reduzido.
        
        //define a abertura em modo maximizado
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // por padrão abre maximizado.
        
         //defini o comportamento de fechar o processo no fechamento do Jframe
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );// finaliza o processo quando o frame é fechado.  
        
        
         
    }
    
}
