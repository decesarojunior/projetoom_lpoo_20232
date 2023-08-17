
package br.edu.ifsul.bcc.lpoo.om.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author telmo
 */
@Entity
@DiscriminatorValue(value = "C")
public class Cliente extends Pessoa{
    
    @Column(nullable = true, length = 200)
    private String observacoes;

    public Cliente() {
    }

    /**
     * @return the observacoes
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * @param observacoes the observacoes to set
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    
    
}
