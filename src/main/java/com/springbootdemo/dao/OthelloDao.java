package com.springbootdemo.dao;

import java.util.List;

import com.springbootdemo.entity.Othello;

public interface OthelloDao {

	void insertOthello(Othello othello);

	List<Othello> getAll();

}
