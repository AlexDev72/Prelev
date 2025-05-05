package com.prelev.apirest_springboot.service;

import com.prelev.apirest_springboot.modele.Prelevement;

import java.util.List;

public interface PrelevementService {

    Prelevement create(Prelevement prelevement);

    List<Prelevement> Read();

    Prelevement update(Long id, Prelevement prelevement);

    String delete(Long id);
}
