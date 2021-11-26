package com.bcopstein.casosDeUso.Politicas;

import com.bcopstein.entidades.Bairro;
import com.bcopstein.entidades.Passageiro;
import com.bcopstein.entidades.Roteiro;

public class CalculoCustoViagemBasico implements ICalculoCustoViagem {
    private Roteiro roteiro;
    private Passageiro passageiro;

    @Override
    public void defineRoteiro(Roteiro roteiro) {
        this.roteiro = roteiro;
    }

    @Override
    public void definePassageiro(Passageiro passageiro){
        this.passageiro = passageiro;
    }

	public Roteiro getRoteiro() {
		return roteiro;
	}

	public Passageiro getPassageiro() {
		return passageiro;
	}
    
    @Override
    public double calculoCustoBasico() {
        return roteiro.bairrosPercoridos()
               .stream()
               .mapToDouble(Bairro::getCustoTransporte)
               .sum();
    }

    @Override
    public double descontoPontuacao() {
        return 0.0;
    }

    @Override
    public double descontoPromocaoSazonal() {
        return 0.0;
    }

    @Override
    public double custoViagem() {
        return calculoCustoBasico() - 
                descontoPontuacao() -
                descontoPromocaoSazonal();
    }
}