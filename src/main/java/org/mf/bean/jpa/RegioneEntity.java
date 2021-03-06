/*
 * Created on 5 ago 2016 ( Time 15:52:43 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package org.mf.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "regione"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="regione", catalog="comuni" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="RegioneEntity.countAll", query="SELECT COUNT(x) FROM RegioneEntity x" )
} )
public class RegioneEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="regione_id", nullable=false)
    private Integer    regioneId    ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="nome", length=80)
    private String     nome         ;

    @Column(name="nose", length=15)
    private String     nose         ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="regione", targetEntity=ProvinciaEntity.class)
    private List<ProvinciaEntity> listOfProvincia;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public RegioneEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setRegioneId( Integer regioneId ) {
        this.regioneId = regioneId ;
    }
    public Integer getRegioneId() {
        return this.regioneId;
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

    //--- DATABASE MAPPING : nose ( VARCHAR ) 
    public void setNose( String nose ) {
        this.nose = nose;
    }
    public String getNose() {
        return this.nose;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfProvincia( List<ProvinciaEntity> listOfProvincia ) {
        this.listOfProvincia = listOfProvincia;
    }
    public List<ProvinciaEntity> getListOfProvincia() {
        return this.listOfProvincia;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(regioneId);
        sb.append("]:"); 
        sb.append(nome);
        sb.append("|");
        sb.append(nose);
        return sb.toString(); 
    } 

}
