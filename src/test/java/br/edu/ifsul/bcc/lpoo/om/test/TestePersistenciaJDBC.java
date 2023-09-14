
package br.edu.ifsul.bcc.lpoo.om.test;

import br.edu.ifsul.bcc.lpoo.om.model.Cargo;
import br.edu.ifsul.bcc.lpoo.om.model.dao.PersistenciaJDBC;
import org.junit.Test;

/**
 *
 * @author telmo
 */
public class TestePersistenciaJDBC {
    
    //implementar um metodo de teste para abrir e fechar uma conexao
    //usando apenas o jdbc.
    //@Test
    public void testConexaoJDBC() throws Exception{
        //criar um objeto do tipo PersistenciaJPA.
        PersistenciaJDBC jdbc = new PersistenciaJDBC();
        if(jdbc.conexaoAberta()){
            System.out.println("conectou no BD via jdbc ...");
            jdbc.fecharConexao();
        }else{
            System.out.println("nao conectou no BD via jdbc ...");
                        
        }
    }
    
    @Test
    public void testPersistenciaCargoJPA() throws Exception {
        //criar um objeto do tipo PersistenciaJPA.
        PersistenciaJDBC jpa = new PersistenciaJDBC();
        if(jpa.conexaoAberta()){
            
            //Passo 1: encontrar o cargo id = 1
            Cargo cg = (Cargo) jpa.find(Cargo.class, Integer.valueOf("1"));
            if(cg != null){
                System.out.println("Cargo encontrado: ");
                System.out.println("id: "+cg.getId()+" Descricao: "+cg.getDescricao());
            }else{
                 System.out.println("Não encontro o cargo");
            }
        }else{
            System.out.println("nao conectou no BD via jdbc ...");
            //atribuir uma instancia para o cg
            //setar a descricao
            //persistir no banco de dados.
        }
    }
}
