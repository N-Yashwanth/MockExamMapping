package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public class CatController {
	@Autowired
	private CategoryRepo proRepo;
	@GetMapping
	public List<Category> getAllProducts(){
		return proRepo.findAll();
	}
	@GetMapping("/{id}")
	public Category getById(@PathVariable int id) {
		return proRepo.findById(id).orElse(null);
	}
	@PostMapping
	public Category savePro(@RequestBody Category pro) {
		return proRepo.save(pro);
	}
	@PutMapping("/{id}")
	public Category upadteProduct(@PathVariable int id, @RequestBody Category pro) {
		Category product=proRepo.findById(id).orElse(null);
		if(pro.getName()!=null) {
			product.setName(pro.getName());
		}
		return proRepo.save(product);
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		if(proRepo.existsById(id)) {
			return "Deleted Successfully";
		}
		else {
			return "Not Present";
		}
	}
	@GetMapping("/page/{pageNo}/{pageSize}")
	public List<Category> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Category> result=proRepo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<Category>();
	}
	@GetMapping("/sort")
	public List<Category> getSorted(@RequestParam String field, @RequestParam String direction){
		Direction sort=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return proRepo.findAll(Sort.by(sort, field));
	}
}
