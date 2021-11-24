package com.bcopstein.casosDeUso.Repositorios;

import java.util.List;

import com.bcopstein.entidades.Bairro;

public interface IRepositorioBairros {
    Bairro recuperaPorNome(String nomeBairro);
    List<Bairro> recuperaListaBairros();
}