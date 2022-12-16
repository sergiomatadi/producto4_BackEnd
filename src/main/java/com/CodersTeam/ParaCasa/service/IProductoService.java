package com.CodersTeam.ParaCasa.service;

import java.util.List;

import com.CodersTeam.ParaCasa.entity.Producto;

public interface IProductoService {
	
	public List<Producto> listarProductos();
	public void create (Producto producto);
	public Producto read (Long id);
	public void delete(Long id);
	

}
