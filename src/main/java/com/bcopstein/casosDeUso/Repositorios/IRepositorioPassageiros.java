package com.bcopstein.casosDeUso.Repositorios;

import java.util.List;

import com.bcopstein.entidades.Passageiro;

public interface IRepositorioPassageiros {
    List<Passageiro> listaPassageiros();
    Passageiro recuperaPorCPF(String cpf);
    void atualizaPassageiro(Passageiro passageiro);
}