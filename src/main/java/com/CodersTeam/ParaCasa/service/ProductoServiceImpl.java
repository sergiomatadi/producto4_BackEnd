 package com.CodersTeam.ParaCasa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CodersTeam.ParaCasa.entity.Producto;
import com.CodersTeam.ParaCasa.repository.ProductoRepository;


@Service
public class ProductoServiceImpl implements IProductoService {
	
	
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public List<Producto> listarProductos() {
		
		return  (List<Producto>)productoRepository.findAll();
	}

	@Override
	public void create(Producto producto) {
		productoRepository.save(producto);

	}

	@Override
	public Producto read(Long id) {
		return productoRepository.findById(id).orElse(null);

	}

	@Override
	public void delete(Long id) {
		productoRepository.deleteById(id);

	}

}
