package com.springbootdemo.service;

import java.util.List;

import com.springbootdemo.entity.Othello;

public interface OthelloDbService {
	void save(Othello othello);

	List<Othello> getAll();

}
