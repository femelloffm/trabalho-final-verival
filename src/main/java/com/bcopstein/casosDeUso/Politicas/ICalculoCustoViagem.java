package com.bcopstein.casosDeUso.Politicas;

import com.bcopstein.entidades.Passageiro;
import com.bcopstein.entidades.Roteiro;

public interface ICalculoCustoViagem {
    void defineRoteiro(Roteiro roteiro);
    void definePassageiro(Passageiro passageiro);
	public Roteiro getRoteiro();
	public Passageiro getPassageiro();
    double calculoCustoBasico();
    double descontoPontuacao();
    double descontoPromocaoSazonal();
    double custoViagem();
}