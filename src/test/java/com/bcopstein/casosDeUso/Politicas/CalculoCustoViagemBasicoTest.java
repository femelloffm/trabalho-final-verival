package com.bcopstein.casosDeUso.Politicas;

import com.bcopstein.entidades.Bairro;
import com.bcopstein.entidades.Roteiro;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculoCustoViagemBasicoTest {
    private static Bairro bairro1, bairro2, bairro3;
    private static Roteiro roteiro;

    @BeforeAll
    public static void setup() {
        bairro1 = mock(Bairro.class);
        when(bairro1.getCustoTransporte()).thenReturn(20.0);
        bairro2 = mock(Bairro.class);
        when(bairro2.getCustoTransporte()).thenReturn(9.5);
        bairro3 = mock(Bairro.class);
        when(bairro3.getCustoTransporte()).thenReturn(14.0);

        roteiro = mock(Roteiro.class);
        when(roteiro.bairrosPercoridos()).thenReturn(new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3)));
    }

    @Test
    public void testaCustoViagem() {
        CalculoCustoViagemBasico custoViagem = new CalculoCustoViagemBasico();
        custoViagem.defineRoteiro(roteiro);
        assertEquals(43.5, custoViagem.custoViagem());
    }
}
