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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProController {
	@Autowired
	private ProductRepo proRepo;
	@GetMapping
	public List<Product> getAllProducts(){
		return proRepo.findAll();
	}
	@GetMapping("/{id}")
	public Product getById(@PathVariable int id) {
		return proRepo.findById(id).orElse(null);
	}
	@PostMapping
	public Product savePro(@RequestBody Product pro) {
		return proRepo.save(pro);
	}
	@PutMapping("/{id}")
	public Product upadteProduct(@PathVariable int id, @RequestBody Product pro) {
		Product product=proRepo.findById(id).orElse(null);
		if(pro.getName()!=null) {
			product.setName(pro.getName());
		}
		if(pro.getCost()!=0) {
			product.setCost(pro.getCost());
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
	public List<Product> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Product> result=proRepo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<Product>();
	}
	@GetMapping("/sort")
	public List<Product> getSorted(@RequestParam String field, @RequestParam String direction){
		Direction sort=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return proRepo.findAll(Sort.by(sort, field));
	}
}
