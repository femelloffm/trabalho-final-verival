package com.bcopstein.casosDeUso.Servicos;

import com.bcopstein.casosDeUso.Politicas.CustoViagem;
import com.bcopstein.casosDeUso.Repositorios.IRepositorioBairros;
import com.bcopstein.casosDeUso.Repositorios.IRepositorioPassageiros;
import com.bcopstein.entidades.Bairro;
import com.bcopstein.entidades.Passageiro;
import com.bcopstein.entidades.Roteiro;
import com.bcopstein.entidades.Viagem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicosPassageiroTest {
    private static Bairro bairro1, bairro2, bairro3;
    private static Passageiro passageiro1, passageiro2, passageiro3;
    private static IRepositorioBairros repoBairros;
    private static IRepositorioPassageiros repoPassageiros;
    private static CustoViagem custoViagem;
    private static ServicosPassageiro servicosPassageiro;

    @BeforeAll
    public static void setup() {
        bairro1 = mock(Bairro.class);
        when(bairro1.getNome()).thenReturn("Higienopolis");
        bairro2 = mock(Bairro.class);
        when(bairro2.getNome()).thenReturn("Partenon");
        bairro3 = mock(Bairro.class);
        when(bairro3.getNome()).thenReturn("Restinga");
        repoBairros = mock(IRepositorioBairros.class);
        when(repoBairros.recuperaListaBairros())
                .thenReturn(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3)));
        when(repoBairros.recuperaPorNome("Higienopolis")).thenReturn(bairro1);
        when(repoBairros.recuperaPorNome("Partenon")).thenReturn(bairro2);
        passageiro1 = mock(Passageiro.class);
        when(passageiro1.getNome()).thenReturn("Juliana Silva");
        passageiro2 = mock(Passageiro.class);
        when(passageiro2.getNome()).thenReturn("Carlos Medeiros");
        passageiro3 = mock(Passageiro.class);
        when(passageiro3.getNome()).thenReturn("Catarina de Souza");
        repoPassageiros = mock(IRepositorioPassageiros.class);
        when(repoPassageiros.listaPassageiros())
                .thenReturn(new ArrayList<>(Arrays.asList(passageiro1, passageiro2, passageiro3)));
        when(repoPassageiros.recuperaPorCPF("12345678947")).thenReturn(passageiro1);
        custoViagem = mock(CustoViagem.class);
        when(custoViagem.custoViagem(any(Roteiro.class), any(Passageiro.class))).thenReturn(20D);
        servicosPassageiro = new ServicosPassageiro(repoBairros, repoPassageiros, custoViagem);
    }

    @Test
    public void testaGetListaBairros() {
        List<String> listaBairros = servicosPassageiro.getListaBairros();
        assertEquals("Higienopolis", listaBairros.get(0));
        assertEquals("Partenon", listaBairros.get(1));
        assertEquals("Restinga", listaBairros.get(2));
    }

    @Test
    public void testaGetPassageirosCadastrados() {
        List<String> listaPassageiros = servicosPassageiro.getPassageirosCadastrados();
        assertEquals("Juliana Silva", listaPassageiros.get(0));
        assertEquals("Carlos Medeiros", listaPassageiros.get(1));
        assertEquals("Catarina de Souza", listaPassageiros.get(2));
    }

    @Test
    public void testaCriaRoteiro() {
        Roteiro roteiro = servicosPassageiro.criaRoteiro("Higienopolis", "Partenon");
        assertEquals(bairro1, roteiro.getBairroOrigem());
        assertEquals(bairro2, roteiro.getBairroDestino());
    }

    @Test
    public void testaCriaViagem() {
        LocalDateTime data = LocalDateTime.of(2020, 3, 24, 12, 30);
        Roteiro roteiro = mock(Roteiro.class);
        Viagem viagem = servicosPassageiro.criaViagem(1, roteiro, "12345678947", data);
        assertEquals(20D, viagem.getValorCobrado());
        assertEquals(data, viagem.getDataHora());
        assertEquals(passageiro1, viagem.getPassageiro());
        assertEquals(roteiro, viagem.getRoteiro());
        assertEquals(1, viagem.getId());
    }
}
