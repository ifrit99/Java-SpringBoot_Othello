package com.springbootdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootdemo.dao.OthelloDao;
import com.springbootdemo.entity.Othello;

@Service
public class OthelloDbServiceImpl implements OthelloDbService {

	private final OthelloDao dao;

	@Autowired
	OthelloDbServiceImpl(OthelloDao dao) {
		this.dao = dao;
	}

	@Override
	public void save(Othello othello) {
		dao.insertOthello(othello);

	}

	@Override
	public List<Othello> getAll() {
		return dao.getAll();
	}

}
