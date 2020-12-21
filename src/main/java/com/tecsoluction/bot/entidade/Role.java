package com.tecsoluction.bot.entidade;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsoluction.bot.framework.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> users;
    
    
    
    
    public Role() {
		// TODO Auto-generated constructor stub
	}

 
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return name;
    }

}
