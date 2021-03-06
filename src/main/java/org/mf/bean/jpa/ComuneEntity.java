/*
 * Created on 1 ago 2016 ( Time 13:27:29 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package org.mf.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;



import javax.persistence.*;

/**
 * Persistent class for entity stored in table "comune"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="comune", catalog="comuni" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="ComuneEntity.countAll", query="SELECT COUNT(x) FROM ComuneEntity x" )
} )
public class ComuneEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="comune_id", nullable=false)
    private Integer    comuneId     ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="nome", length=80)
    private String     nome         ;

    @Column(name="codice_catastale", length=4)
    private String     codicecatastale ;

    @Column(name="abitanti")
    private Integer    abitanti     ;

    @Column(name="capoluogo")
    private Boolean    capoluogo    ;

	// "provinciaId" (column "provincia_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="provincia_id", referencedColumnName="provincia_id")
    private ProvinciaEntity provincia   ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ComuneEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setComuneId( Integer comuneId ) {
        this.comuneId = comuneId ;
    }
    public Integer getComuneId() {
        return this.comuneId;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : nome ( VARCHAR ) 
    public void setNome( String nome ) {
        this.nome = nome;
    }
    public String getNome() {
        return this.nome;
    }

    //--- DATABASE MAPPING : codiceCatastale ( VARCHAR ) 
    public void setCodicecatastale( String codicecatastale ) {
        this.codicecatastale = codicecatastale;
    }
    public String getCodicecatastale() {
        return this.codicecatastale;
    }

    //--- DATABASE MAPPING : abitanti ( INT ) 
    public void setAbitanti( Integer abitanti ) {
        this.abitanti = abitanti;
    }
    public Integer getAbitanti() {
        return this.abitanti;
    }

    //--- DATABASE MAPPING : capoluogo ( BIT ) 
    public void setCapoluogo( Boolean capoluogo ) {
        this.capoluogo = capoluogo;
    }
    public Boolean getCapoluogo() {
        return this.capoluogo;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setProvincia( ProvinciaEntity provincia ) {
        this.provincia = provincia;
    }
    public ProvinciaEntity getProvincia() {
        return this.provincia;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(comuneId);
        sb.append("]:"); 
        sb.append(nome);
        sb.append("|");
        sb.append(codicecatastale);
        sb.append("|");
        sb.append(abitanti);
        sb.append("|");
        sb.append(capoluogo);
        return sb.toString(); 
    } 

}
