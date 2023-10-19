
package br.edu.ifsul.bcc.lpoo.om.test;

import br.edu.ifsul.bcc.lpoo.om.model.Cargo;
import br.edu.ifsul.bcc.lpoo.om.model.Cliente;
import br.edu.ifsul.bcc.lpoo.om.model.Veiculo;
import br.edu.ifsul.bcc.lpoo.om.model.dao.PersistenciaJDBC;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
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
    /*
        implementar o seguinte teste
        1) recuperar todos os cargos
        2) se existir ao menos um cargo, imprimir, alterar e remover.
        3) se não existir, criar dois cargos e imprimi.
    */
    
    //@Test
    public void testPersistenciaListCargoJDBC() throws Exception {
        //criar um objeto do tipo PersistenciaJDBC.
        PersistenciaJDBC jdbc = new PersistenciaJDBC();
        if(jdbc.conexaoAberta()){      
          System.out.println("testPersistenciaListCargoJDBC:: conectou no BD via jdbc ...");
          //Passo 1: recuperar a coleção de cargos.
          Collection<Cargo> cll = jdbc.listCargos();
          if(!cll.isEmpty()){
            //Passo 2: caso a coleção não esteja vazia - imprimir, alterar e remover cada item.
            for(Cargo c : cll){
                System.out.println("Cargo : "+c.getDescricao());
                c.setDescricao("descricao alterada");
                jdbc.persist(c);//alterar no banco.
                //remove no bd
                jdbc.remover(c);
            }    
          }else{
              //Passo 3: caso a coleção esteja vazia, criar dois cargos.
              Cargo c = new Cargo();
              c.setDescricao("teste");
              jdbc.persist(c);
              System.out.println("Cadastrou o cargo :"+c.getId()+" : "+c.getDescricao());
          }
                              
          jdbc.fecharConexao();
        }else{
            System.out.println("nao conectou no BD ...");                        
        }
    }
    
        //Exercício 28/09
    /*
       Criar um método de teste para funcionario
         Passo 1: recuperar a coleção de funcionarios.
         Passo 2: caso a coleção não esteja vazia - imprimir (inclusive os cursos), 
                  alterar e remover cada item.
         Passo 3: caso a coleção esteja vazia, criar dois funcionarios com um Curso cada.
    */ 
    
    @Test
    public void testPersitenciaClienteJDBC() throws Exception{
        //criar um objeto do tipo PersistenciaJPA.
        PersistenciaJDBC jdbc = new PersistenciaJDBC();
        if(jdbc.conexaoAberta()){
            System.out.println("conectou no BD via jdbc ...");
            
            Collection<Cliente> list = jdbc.listClientes();
            if(!list.isEmpty()){
                
                //percorrer e remover.
                for(Cliente c : list){
                    
                    System.out.println("Cliente: "+c.getCpf());
                    for(Veiculo v : c.getVeiculo()){
                        System.out.println("\t Veiculo:" + v.getPlaca());
                    }
                    
                    jdbc.remover(c);
                }
                
            }else{
                
                Cliente c = new Cliente();
                //setar demais informações.
                c.setCpf("10001347788");
                c.setNome("Telmo");
                c.setData_nascimento(Calendar.getInstance());
                c.setComplemento(".");
                c.setCep("99010035");
                c.setSenha("123456");
                c.setNumero(".");
                Veiculo v = (Veiculo) jdbc.find(Veiculo.class, "igd1903");
                List<Veiculo> listV = new ArrayList();
                listV.add(v);
                
                c.setVeiculo(listV);
                
                jdbc.persist(c);
                
            }
            
            jdbc.fecharConexao();
        }else{
            System.out.println("nao conectou no BD via jdbc ...");
                        
        }
    }
    
    
    //@Test
    public void testPersistenciaCargoJDBC() throws Exception {
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
