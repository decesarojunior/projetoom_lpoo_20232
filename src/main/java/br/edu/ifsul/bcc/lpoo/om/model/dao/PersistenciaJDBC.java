
package br.edu.ifsul.bcc.lpoo.om.model.dao;

import br.edu.ifsul.bcc.lpoo.om.model.Cargo;
import br.edu.ifsul.bcc.lpoo.om.model.Cliente;
import br.edu.ifsul.bcc.lpoo.om.model.Curso;
import br.edu.ifsul.bcc.lpoo.om.model.Funcionario;
import br.edu.ifsul.bcc.lpoo.om.model.Veiculo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 *
 * @author telmo
 */
public class PersistenciaJDBC implements InterfacePersistencia{
    
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_om_lpoo_20232";
    private Connection con = null;


    public PersistenciaJDBC() throws ClassNotFoundException, SQLException{
        
        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");
            
        this.con = DriverManager.getConnection(URL, USER, SENHA); 

    }

    @Override
    public Boolean conexaoAberta() {
        
        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void fecharConexao() {
        try{                               
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        }catch(SQLException e){            
            e.printStackTrace();//gera uma pilha de erro na saida.
        } 
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
         if(c == Cargo.class){
             PreparedStatement ps = this.con.prepareStatement(""
                     + "select id, descricao from tb_cargo where id = ?");
             
             ps.setInt(1, Integer.valueOf(id.toString()));
             
             ResultSet rs = ps.executeQuery();
             if(rs.next()){
                 Cargo crg = new Cargo();
                 crg.setId(rs.getInt("id"));
                 crg.setDescricao(rs.getString("descricao"));
                 return crg;
             }
             ps.close();
             rs.close();
             
         }else if(c == Curso.class){
             
         }else if(c == Funcionario.class){
             
             //select em tb_funcionario
             //select na tabela associativa.
             
             
         }else if(c == Veiculo.class){
             
             PreparedStatement ps = this.con.prepareStatement(
                    "select placa, ano, data_ultimo_servico, modelo from tb_veiculo where placa = ?");
             
             ps.setString(1, id.toString());
             
             ResultSet rs = ps.executeQuery();
             Veiculo v = null;
             if(rs.next()){
                 v = new Veiculo();
                    v.setPlaca(rs.getString("placa"));
                    v.setAno(rs.getInt("ano"));
                    
                    if(rs.getDate("data_ultimo_servico") != null){
                        Calendar cld = Calendar.getInstance();
                        cld.setTimeInMillis(rs.getDate("data_ultimo_servico").getTime());
                        v.setData_ultimo_servico(cld);
                    }
                    v.setModelo(rs.getString("modelo"));                                  
             }
             ps.close();
             rs.close();
             return v;
         }
         
         return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        if(o instanceof Cargo){
            Cargo crg = (Cargo) o;
            if(crg.getId() == null){
                //executar um insert
                PreparedStatement ps = this.con.prepareStatement("insert into "
                        + "tb_cargo (id, descricao) values (nextval('seq_cargo_id') , ? ) returning id");
                //definir os valores para os parametros
                ps.setString(1, crg.getDescricao());
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    crg.setId(rs.getInt("id"));
                }
                ps.close();
            }else{
                //executar um update
                PreparedStatement ps = this.con.prepareStatement("udpate tb_cargo set descricao = ? "
                        + "where id = ?");
               
                //definir os valores para os parametros.
                ps.setString(1, crg.getDescricao());
                ps.setInt(2, crg.getId());
                ps.execute();
                ps.close();
            }
        }else if( o instanceof Curso){
            
        }else if(o instanceof Funcionario){
            
              Funcionario func = (Funcionario) o;
              
               //verificar a acao: insert ou update.
              if(func.getData_admmissao() == null){
                  
                  //insert tb_pessoa
                  PreparedStatement ps = 
                          this.con.prepareStatement("insert into tb_pessoa ( "
                                                                            + "cpf, "
                                                                            + "data_nascimento) values "
                                                                            + "( "
                                                                            + "?, "
                                                                            + "?) ");
                  
                  ps.setString(1, func.getCpf());        
                  ps.setDate(2, new java.sql.Date(func.getData_nascimento().getTimeInMillis()));
                  //demais campos...
                
                  ps.execute();
                  
                  ps.close();

                  //insert em tb_funcionario
                  PreparedStatement ps2 = 
                      this.con.prepareStatement("insert into tb_funcionario (data_admmissao) values (now())  ..."); 
                     //setar os parametros...

                  ResultSet rs2 = ps2.executeQuery();
                  if(rs2.next()){
                      
                        Calendar dt_adm = Calendar.getInstance();
                        dt_adm.setTimeInMillis(rs2.getDate("data_admmissao").getTime());
                        func.setData_admmissao(dt_adm);
                  
                        //se necessário o insert em tb_funcionario_curso
                        if (!func.getCursos().isEmpty()){

                            for(Curso crs : func.getCursos()){

                                PreparedStatement ps3 = this.con.prepareStatement("insert into tb_funcionario_curso "
                                                                                + "(funcionario_cpf, curso_id) "
                                                                                + "values "
                                                                                + "(?, ?)");
                                ps3.setString(1, func.getCpf());
                                ps3.setInt(2, crs.getId());

                                ps3.execute();
                                ps3.close();
                            }

                        }


                    }

                  
              }else{
                  
                    //update tb_pessoa.
                    PreparedStatement ps = 
                          this.con.prepareStatement("update tb_pessoa set data_nascimento = ? where cpf = ? ");
                    //setar os demais campos e parametros.
                  
                    //update tb_funcionario.
                    PreparedStatement ps2 = 
                          this.con.prepareStatement("update tb_funcionario set numero_ctps = ? where cpf = ? ");
                    //setar os demais campos e parametros.
                                     
                    //atualizar os respectivos registros em tb_funcionario_curso para o func
                   
                     //passo 1 - remove todos os cursos do funcionario na tabela associativa 
                     PreparedStatement ps3 = 
                          this.con.prepareStatement("delete from tb_funcionario_curso where cpf = ?");
                     
                     ps3.execute();
                     
                    //passo 2: insere novamente, caso necessario. 
                    if (!func.getCursos().isEmpty()){
                            
                            for(Curso crs : func.getCursos()){
                                
                                PreparedStatement ps4 = this.con.prepareStatement("insert into tb_funcionario_curso "
                                                                                + "(funcionario_cpf, curso_id) "
                                                                                + "values "
                                                                                + "(?, ?)");
                                ps4.setString(1, func.getCpf());
                                ps4.setInt(2, crs.getId());
                                
                                ps4.execute();
                                ps4.close();
                            }
                            
                        }
                   
                     
                    
                    
              }     
             
            
        }else if(o instanceof Cliente){
            
              Cliente cli = (Cliente) o;
              
               //verificar a acao: insert ou update.
              if(cli.getTipo() == null){
                  
                  //insert tb_pessoa
                  PreparedStatement ps = 
                          this.con.prepareStatement("insert into tb_pessoa ( "
                                                                            + "cpf, "
                                                                            + "data_nascimento, tipo, nome, senha) values "
                                                                            + "( "
                                                                            + "?, "
                                                                            + "?, 'C', ?, ? ) ");
                  
                  ps.setString(1, cli.getCpf());        
                  ps.setDate(2, new java.sql.Date(cli.getData_nascimento().getTimeInMillis()));
                  ps.setString(3, cli.getNome());
                  ps.setString(4, cli.getSenha());
                
                  ps.execute();
                  
                  ps.close();

                  //insert em tb_funcionario
                  PreparedStatement ps2 = 
                      this.con.prepareStatement("insert into tb_cliente (cpf, observacoes) values ( ?, ? )"); 
                      ps2.setString(1, cli.getCpf());
                      ps2.setString(2, cli.getObservacoes());

                  Boolean ret = ps2.execute();
                  System.out.println(ret); 
                  if(!ret){
                      
                        //se necessário o insert em tb_funcionario_curso
                        if (!cli.getVeiculo().isEmpty()){

                            for(Veiculo vs : cli.getVeiculo()){

                                PreparedStatement ps3 = this.con.prepareStatement("insert into tb_cliente_veiculo "
                                                                                + "(cliente_cpf, veiculo_id) "
                                                                                + "values "
                                                                                + "(?, ?)");
                                ps3.setString(1, cli.getCpf());
                                ps3.setString(2, vs.getPlaca());

                                ps3.execute();
                                ps3.close();
                            }

                        }


                    }

                  
              }     
            
            
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        
        if(o instanceof Cargo){
            Cargo crg = (Cargo) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from "
                        + "tb_cargo where id = ?;");
                //definir os valores para os parametros
                ps.setInt(1, crg.getId());
            ps.execute();
            ps.close();
                
           
        }else if(o instanceof Funcionario){
                
            //remove os respectivos registro na tabela associativa
            //remover em tb_funcionario
            
        }else if(o instanceof Cliente){
            
            Cliente c = (Cliente) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from "
                        + "tb_cliente_veiculo where cliente_cpf = ?;");
                //definir os valores para os parametros
                ps.setString(1, c.getCpf());
                
            ps.execute();
            ps.close();
            
            ps = this.con.prepareStatement("delete from "
                        + "tb_cliente where cpf = ?;");
                //definir os valores para os parametros
                ps.setString(1, c.getCpf());
                
            ps.execute();
            ps.close();
            
            
            ps = this.con.prepareStatement("delete from "
                        + "tb_pessoa where cpf = ?;");
                //definir os valores para os parametros
                ps.setString(1, c.getCpf());
                
            ps.execute();
            ps.close();


        }
        
        
    }

    @Override
    public Collection<Cargo> listCargos() throws Exception {
        Collection<Cargo> colecaoRetorno = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, descricao "
                                                        + "from tb_cargo "
                                                        + "order by id asc");
        
        ResultSet rs = ps.executeQuery();//executa o sql e retorna
        
        colecaoRetorno = new ArrayList();//inicializa a collecao
        
        while(rs.next()){//percorre o ResultSet
            
            Cargo crg = new Cargo();//inicializa o Cargo
            
            // recuperar as informações da célula e setar no crg
            crg.setId(rs.getInt("id"));
            crg.setDescricao(rs.getString("descricao"));
            
            colecaoRetorno.add(crg);//adiciona na colecao
        }
        
        ps.close();//fecha o cursor
        
        return colecaoRetorno; //retorna a colecao.
    }

    @Override
    public Collection<Funcionario> listFuncionarios() throws Exception {
        Collection<Funcionario> colecaoRetorno = null;
        
        PreparedStatement ps = this.con.prepareStatement("");        
        ResultSet rs = ps.executeQuery();//executa o sql e retorna
        
        colecaoRetorno = new ArrayList();//inicializa a collecao
        
        while(rs.next()){//percorre o ResultSet
            
            Funcionario func = new Funcionario();//inicializa o Cargo
            //seta as informações do rs
            PreparedStatement ps2 = this.con.prepareStatement("selecte ... from tb_funcionario_cur");
            ResultSet rs2 = ps.executeQuery();//executa o sql e retorna
            Collection<Curso> colecaoCursos = new ArrayList();
            while(rs2.next()){
                Curso crs = new Curso();
                
                colecaoCursos.add(crs);
            }
            rs2.close();
            
            func.setCursos(colecaoCursos);
          
            colecaoRetorno.add(func);//adiciona na colecao
        }
        
        ps.close();//fecha o cursor
        
        return colecaoRetorno; //retorna a colecao.
    }

    @Override
    public Collection<Cliente> listClientes() throws Exception {
       
        Collection<Cliente> colecaoRetorno = null;
        
        PreparedStatement ps = this.con.prepareStatement("select p.nome, p.senha, p.cpf from tb_pessoa p, "
                                                                    + "tb_cliente c where p.cpf=c.cpf");

                                                                        
        ResultSet rs = ps.executeQuery();//executa o sql e retorna
        
        colecaoRetorno = new ArrayList();//inicializa a collecao
        
        while(rs.next()){//percorre o ResultSet
            
            Cliente cli = new Cliente();//inicializa o Cliente
            //seta as informações do rs
            cli.setCpf(rs.getString("cpf"));
            cli.setNome(rs.getString("nome"));
            cli.setSenha(rs.getString("senha"));
            
            PreparedStatement ps2 = this.con.prepareStatement(" select v.placa "
                    + "from tb_cliente_veiculo cv, tb_cliente c, tb_veiculo v "
                    + " where c.cpf=cv.cliente_cpf and cv.veiculo_id=v.placa and c.cpf = ? ");
                    ps2.setString(1, cli.getCpf());
                    
            ResultSet rs2 = ps2.executeQuery();//executa o sql e retorna
            Collection<Veiculo> colecaoVeiculos = new ArrayList();
            while(rs2.next()){
                Veiculo v = new Veiculo();
                v.setPlaca(rs2.getString("placa"));
                colecaoVeiculos.add(v);
            }
            rs2.close();
            
            cli.setVeiculo(colecaoVeiculos);
          
            colecaoRetorno.add(cli);//adiciona na colecao
        }
        
        ps.close();//fecha o cursor
        
        return colecaoRetorno; //retorna a colecao.
        
    }
    
}
