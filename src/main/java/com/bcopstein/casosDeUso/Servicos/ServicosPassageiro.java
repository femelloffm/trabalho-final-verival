package com.bcopstein.casosDeUso.Servicos;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.casosDeUso.Politicas.CustoViagem;
import com.bcopstein.casosDeUso.Repositorios.IRepositorioBairros;
import com.bcopstein.casosDeUso.Repositorios.IRepositorioPassageiros;
import com.bcopstein.entidades.Bairro;
import com.bcopstein.entidades.Passageiro;
import com.bcopstein.entidades.Roteiro;
import com.bcopstein.entidades.Viagem;

public class ServicosPassageiro {
    private IRepositorioBairros repBairros;
    private IRepositorioPassageiros repPassageiros;
    private CustoViagem custoViagem;

    public ServicosPassageiro(IRepositorioBairros repBairros, IRepositorioPassageiros repPassageiros,
            CustoViagem ccv) {
        this.repBairros = repBairros;
        this.repPassageiros = repPassageiros;
        this.custoViagem = ccv;
    }

    public List<String> getListaBairros(){
        return repBairros.recuperaListaBairros()
                .stream()
                .map(b->b.getNome())
                .collect(Collectors.toList());
    }

    public List<String> getPassageirosCadastrados(){
        return repPassageiros.listaPassageiros()
                .stream()
                .map(p->p.getNome())
                .collect(Collectors.toList());
    }

    public Roteiro criaRoteiro(String bairroOrigem,String bairroDestino){
        Collection<Bairro> todosBairros = repBairros.recuperaListaBairros();
        Bairro bOrigem = repBairros.recuperaPorNome(bairroOrigem);
        Bairro bDestino = repBairros.recuperaPorNome(bairroDestino);
        return new Roteiro(bOrigem,bDestino,todosBairros);
    }

    public Viagem criaViagem(int id,Roteiro roteiro,String cpfPassageiro, LocalDateTime data){
        Passageiro passageiro = repPassageiros.recuperaPorCPF(cpfPassageiro);
        double valorCobrado = custoViagem.custoViagem(roteiro, passageiro);
        return new Viagem(id,data,roteiro,passageiro,valorCobrado);
    }
}