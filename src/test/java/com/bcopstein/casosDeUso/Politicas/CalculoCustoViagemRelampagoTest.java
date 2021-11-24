package com.bcopstein.casosDeUso.Politicas;

import com.bcopstein.entidades.Bairro;
import com.bcopstein.entidades.Passageiro;
import com.bcopstein.entidades.Roteiro;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculoCustoViagemRelampagoTest {
    private static Bairro bairro1, bairro2, bairro3, bairro4, bairro5;

    @BeforeAll
    public static void setup() {
        bairro1 = mock(Bairro.class);
        when(bairro1.getCustoTransporte()).thenReturn(20.0);
        bairro2 = mock(Bairro.class);
        when(bairro2.getCustoTransporte()).thenReturn(9.5);
        bairro3 = mock(Bairro.class);
        when(bairro3.getCustoTransporte()).thenReturn(14.0);
        bairro4 = mock(Bairro.class);
        when(bairro4.getCustoTransporte()).thenReturn(7.9);
        bairro5 = mock(Bairro.class);
        when(bairro5.getCustoTransporte()).thenReturn(10.0);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 40, 0.0", // on-point pontuacao media
            "6, 40, 2.175", // off-point pontuacao media
            "8, 40, 2.175",  // in-point pontuacao media
            "4, 40, 0.0",  // out-point pontuacao media
            "6, 30, 0.0",  // on-point qtdade avaliacoes
            "6, 31, 2.175",  // off-point qtdade avaliacoes
            "6, 39, 2.175",  // in-point qtdade avaliacoes
            "6, 25, 0.0",  // out-point qtdade avaliacoes
    })
    public void testaDescontoPontuacao(int pontuacaoMedia, int qtadeAvaliacoes, double desconto) {
        Passageiro passageiro = mock(Passageiro.class);
        when(passageiro.getPontuacaoMedia()).thenReturn(pontuacaoMedia);
        when(passageiro.getQtdadeAvaliacoes()).thenReturn(qtadeAvaliacoes);
        Roteiro roteiro = mock(Roteiro.class);
        when(roteiro.bairrosPercoridos()).thenReturn(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3)));

        CalculoCustoViagemRelampago custoViagemRelampago = new CalculoCustoViagemRelampago();
        custoViagemRelampago.definePassageiro(passageiro);
        custoViagemRelampago.defineRoteiro(roteiro);

        assertEquals(desconto, Math.round(custoViagemRelampago.descontoPontuacao() * 1000)/1000.0);
    }

    @ParameterizedTest
    @MethodSource("geraBairrosPercorridos")
    public void testaDescontoPromocaoSazonal(Collection<Bairro> bairrosPercorridos, double desconto) {
        Roteiro roteiro = mock(Roteiro.class);
        when(roteiro.bairrosPercoridos()).thenReturn(bairrosPercorridos);

        CalculoCustoViagemRelampago custoViagemRelampago = new CalculoCustoViagemRelampago();
        custoViagemRelampago.defineRoteiro(roteiro);

        assertEquals(desconto, Math.round(custoViagemRelampago.descontoPromocaoSazonal() * 100)/100.0);
    }

    static Stream<Arguments> geraBairrosPercorridos() {
        return Stream.of(
                Arguments.of(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3)), 0.0), // on-point
                Arguments.of(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3, bairro4)), 2.57), // off-point
                Arguments.of(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3, bairro4, bairro5)), 3.07), // in-point
                Arguments.of(new ArrayList<>(Arrays.asList(bairro1, bairro2)), 0.0) // out-point
        );
    }
}
