package com.product.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(unique = true)
	private String categoryName;
	
	private String cateDescription;
	
	private String catImageUrl;
	
	private String catPublicId;
	
	@OneToMany(mappedBy = "category",
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)//cascade = CascadeType.ALL,orphanRemoval = true
	private List<Products> product;

}
