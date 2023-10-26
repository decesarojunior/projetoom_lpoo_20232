package br.edu.ifsul.bcc.lpoo.om;

import br.edu.ifsul.bcc.lpoo.om.gui.JFramePrincipal;
import br.edu.ifsul.bcc.lpoo.om.model.dao.PersistenciaJDBC;



/**
 *
 * @author telmo
 */
public class Controle {
    
    private JFramePrincipal jframe;
    private PersistenciaJDBC conexaoJDBC;
    

    public Controle() {
        
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
         
         jframe.setVisible(true);//mostra o JFrame
    }
    
    
}
