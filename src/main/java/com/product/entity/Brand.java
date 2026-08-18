package com.product.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Brand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer brandId;
	
	@Column(unique = true)
	private String brandName;
	
	private String brandImagePath;
	
	private String publicId;
	
	@OneToMany(mappedBy = "brand",
			  cascade = CascadeType.ALL,
			  orphanRemoval = true)//cascade = CascadeType.ALL,orphanRemoval = true
	private List<Products> product;

}
