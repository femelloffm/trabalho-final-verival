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
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculoCustoViagemVeraoTest {
    private static Bairro bairro1, bairro2, bairro3, bairro4;

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
    }

    @ParameterizedTest
    @CsvSource({
            "9, 0.0", // on-point
            "10, 3.915", // off-point
            "12, 3.915", // in-point
            "8, 0.0" // out-point
    })
    public void testaDescontoPontuacao(int pontuacaoMedia, double desconto) {
        Passageiro passageiro = mock(Passageiro.class);
        when(passageiro.getPontuacaoMedia()).thenReturn(pontuacaoMedia);
        Roteiro roteiro = mock(Roteiro.class);
        when(roteiro.bairrosPercoridos()).thenReturn(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3)));

        CalculoCustoViagemVerao custoViagemVerao = new CalculoCustoViagemVerao();
        custoViagemVerao.defineRoteiro(roteiro);
        custoViagemVerao.definePassageiro(passageiro);

        assertEquals(desconto, custoViagemVerao.descontoPontuacao());
    }

    @ParameterizedTest
    @MethodSource("geraBairrosPercorridos")
    public void testaDescontoPromocaoSazonal(Collection<Bairro> bairrosPercorridos, double desconto) {
        Roteiro roteiro = mock(Roteiro.class);
        when(roteiro.bairrosPercoridos()).thenReturn(bairrosPercorridos);

        CalculoCustoViagemVerao custoViagemVerao = new CalculoCustoViagemVerao();
        custoViagemVerao.defineRoteiro(roteiro);

        assertEquals(desconto, Math.round(custoViagemVerao.descontoPromocaoSazonal() * 100)/100.0);
    }

    static Stream<Arguments> geraBairrosPercorridos() {
        return Stream.of(
                Arguments.of(new ArrayList<>(Arrays.asList(bairro1, bairro2)), 0.0), // on-point
                Arguments.of(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3)), 4.35), // off-point
                Arguments.of(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3, bairro4)), 5.14), // in-point
                Arguments.of(new ArrayList<>(Collections.singletonList(bairro1)), 0.0) // out-point
        );
    }
}
