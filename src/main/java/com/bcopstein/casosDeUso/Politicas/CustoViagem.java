package com.bcopstein.casosDeUso.Politicas;

import com.bcopstein.entidades.Passageiro;
import com.bcopstein.entidades.Roteiro;

public class CustoViagem {
    private ICalculoCustoViagem ccv;
    
    public CustoViagem(ICalculoCustoViagem ccv){
        this.ccv = ccv;
    }            

    public double custoViagem(Roteiro roteiro,Passageiro passageiro){
        ccv.defineRoteiro(roteiro);
        ccv.definePassageiro(passageiro);
        return ccv.custoViagem();
    }
}