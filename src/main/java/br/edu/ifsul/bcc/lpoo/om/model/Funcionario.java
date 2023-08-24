
package br.edu.ifsul.bcc.lpoo.om.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author telmo
 */
@Entity
@DiscriminatorValue("F")
@Table(name = "tb_funcionario")
public class Funcionario extends Pessoa {
    
    
    @Column(nullable = false, length = 11)
    private String numero_ctps;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)  
    private Calendar data_admmissao;
      
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)    
    private Calendar data_demissao;

    public Funcionario() {
    }

    public String getNumero_ctps() {
        return numero_ctps;
    }

    public void setNumero_ctps(String numero_ctps) {
        this.numero_ctps = numero_ctps;
    }

    public Calendar getData_admmissao() {
        return data_admmissao;
    }

    public void setData_admmissao(Calendar data_admmissao) {
        this.data_admmissao = data_admmissao;
    }

    public Calendar getData_demissao() {
        return data_demissao;
    }

    public void setData_demissao(Calendar data_demissao) {
        this.data_demissao = data_demissao;
    }
    
    
}
