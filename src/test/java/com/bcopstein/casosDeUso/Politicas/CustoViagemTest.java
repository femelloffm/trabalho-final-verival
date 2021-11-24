package com.bcopstein.casosDeUso.Politicas;

import com.bcopstein.entidades.Passageiro;
import com.bcopstein.entidades.Roteiro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustoViagemTest {
    @Test
    public void testaCustoViagem() {
        Roteiro roteiro = mock(Roteiro.class);
        Passageiro passageiro = mock(Passageiro.class);
        ICalculoCustoViagem calculoCusto = mock(ICalculoCustoViagem.class);
        when(calculoCusto.custoViagem()).thenReturn(25D);

        CustoViagem custoViagem = new CustoViagem(calculoCusto);
        assertEquals(25D, custoViagem.custoViagem(roteiro, passageiro));
    }
}
