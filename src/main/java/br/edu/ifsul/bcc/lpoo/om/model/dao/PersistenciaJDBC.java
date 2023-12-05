
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
                          this.con.prepareStatement("insert into tb_pessoa (  nome, "
                                                                            + "senha, "
                                                                            + "cpf, "
                                                                            + "data_nascimento, tipo) values "
                                                                            + "( "
                                                                            + "?, "
                                                                            + "?, "
                                                                            + "?, "
                                                                            + "?, "
                                                                            + "'F') ");
                  
                  ps.setString(1, func.getNome());
                  ps.setString(2, func.getSenha());
                  ps.setString(3, func.getCpf());
                  if(func.getData_nascimento() != null){
                    ps.setDate(4, new java.sql.Date(func.getData_nascimento().getTimeInMillis()));
                  }else{
                      ps.setDate(4, null);
                  }
                  //demais campos...
                
                  ps.execute();
                  
                  ps.close();

                  //insert em tb_funcionario
                  PreparedStatement ps2 = 
                      this.con.prepareStatement("insert into tb_funcionario (numero_ctps, cpf, data_admissao, cargo_id) values (?, ?, now(), ?) returning data_admissao"); 
                      ps2.setString(1, func.getNumero_ctps());
                      ps2.setString(2, func.getCpf());
                      ps2.setInt(3, func.getCargo().getId());
                      

                  ResultSet rs2 = ps2.executeQuery();
                  if(rs2.next()){
                      
                        Calendar dt_adm = Calendar.getInstance();
                        dt_adm.setTimeInMillis(rs2.getDate("data_admissao").getTime());
                        func.setData_admmissao(dt_adm);
                  
                        //se necessário o insert em tb_funcionario_curso
                        if (func.getCursos() != null && !func.getCursos().isEmpty()){

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
                          this.con.prepareStatement("update tb_pessoa set nome = ?, senha = ?, data_nascimento = ? where cpf = ? ");
                    //setar os demais campos e parametros.
                    ps.setString(1, func.getNome());
                    ps.setString(2, func.getSenha());
                    if(func.getData_nascimento() != null){
                      ps.setDate(3, new java.sql.Date(func.getData_nascimento().getTimeInMillis()));                    
                    }else{
                      ps.setDate(3, null);  
                    }
                    ps.setString(4, func.getCpf());
                  
                    //update tb_funcionario.
                    PreparedStatement ps2 = 
                          this.con.prepareStatement("update tb_funcionario set numero_ctps = ?, cargo_id = ? where cpf = ? ");
                    //setar os demais campos e parametros.
                    ps2.setString(1, func.getNumero_ctps());
                    ps2.setInt(2, func.getCargo().getId());
                    ps2.setString(3, func.getCpf());                 
                    //atualizar os respectivos registros em tb_funcionario_curso para o func
                   
                     //passo 1 - remove todos os cursos do funcionario na tabela associativa 
                     PreparedStatement ps3 = 
                          this.con.prepareStatement("delete from tb_funcionario_curso where funcionario_cpf = ?");
                          ps3.setString(1, func.getCpf());
                     ps3.execute();
                     
                    //passo 2: insere novamente, caso necessario. 
                    if (func.getCursos() != null && !func.getCursos().isEmpty()){
                            
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
                        if (!cli.getVeiculos().isEmpty()){

                            for(Veiculo vs : cli.getVeiculos()){

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
            Funcionario f = (Funcionario) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from "
                        + "tb_funcionario where cpf = ?;");
                //definir os valores para os parametros
                ps.setString(1, f.getCpf());
                
            ps.execute();
            ps.close();
            
            
            ps = this.con.prepareStatement("delete from "
                        + "tb_pessoa where cpf = ?;");
                //definir os valores para os parametros
                ps.setString(1, f.getCpf());
                
            ps.execute();
            ps.close();
            
            
            
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
        
        PreparedStatement ps = this.con.prepareStatement("select p.nome, p.senha, p.cpf, f.data_admissao, f.numero_ctps, c.id, c.descricao from tb_pessoa p, "
                                                                    + "tb_funcionario f left join tb_cargo c on (f.cargo_id=c.id) where p.cpf=f.cpf");        
        ResultSet rs = ps.executeQuery();//executa o sql e retorna
        
        colecaoRetorno = new ArrayList();//inicializa a collecao
        
        while(rs.next()){//percorre o ResultSet
            
            Funcionario func = new Funcionario();//inicializa
            func.setCpf(rs.getString("cpf"));
            func.setNome(rs.getString("nome"));
            func.setSenha(rs.getString("senha"));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(rs.getDate("data_admissao").getTime());
            func.setData_admmissao(c);
            func.setNumero_ctps(rs.getString("numero_ctps"));
            Cargo cg = new Cargo();
            cg.setId(rs.getInt("id"));
            cg.setDescricao(rs.getString("descricao"));
            func.setCargo(cg);
            
            //seta as informações do rs
            /*
            PreparedStatement ps2 = this.con.prepareStatement("selecte ... from tb_funcionario_cur");
            ResultSet rs2 = ps.executeQuery();//executa o sql e retorna
            Collection<Curso> colecaoCursos = new ArrayList();
            while(rs2.next()){
                Curso crs = new Curso();
                
                colecaoCursos.add(crs);
            }
            rs2.close();
            
            func.setCursos(colecaoCursos);
            */
            
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
            
            cli.setVeiculos(colecaoVeiculos);
          
            colecaoRetorno.add(cli);//adiciona na colecao
        }
        
        ps.close();//fecha o cursor
        
        return colecaoRetorno; //retorna a colecao.
        
    }

    @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {
       
        Funcionario funcionario = null;        
        PreparedStatement ps = 
            this.con.prepareStatement("select p.cpf, to_char(p.data_nascimento, 'dd/mm/yyyy') as data_cadastro, p.nome "
                                        + " from tb_pessoa p "
                                        + " where p.cpf = ? and p.senha = ? and tipo = 'F' ");
                        
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();//o ponteiro do REsultSet inicialmente está na linha -1
            
            if(rs.next()){//se a matriz (ResultSet) tem uma linha

                funcionario = new Funcionario();
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setNome(rs.getString("nome"));
                //...recupera demais campos do ResultSet
            }
            ps.close();
            
            return funcionario;  
        
    }
    
}
