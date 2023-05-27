package com.datatransfer.dt2.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Table(name = "schedule_test")
@Entity
public class Schedule implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private Integer time;
	

}
