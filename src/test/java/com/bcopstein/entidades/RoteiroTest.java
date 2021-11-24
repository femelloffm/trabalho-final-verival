package com.bcopstein.entidades;

import com.bcopstein.entidades.geometria.Reta;
import com.bcopstein.entidades.geometria.SituacaoReta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoteiroTest {
    private static Bairro bairro1, bairro2, bairro3, bairro4;

    @BeforeAll
    public static void setup() {
        bairro1 = mock(Bairro.class);
        when(bairro1.getClassificacao(any(Reta.class))).thenReturn(SituacaoReta.INTERSECTA);
        bairro2 = mock(Bairro.class);
        when(bairro2.getClassificacao(any(Reta.class))).thenReturn(SituacaoReta.TODA_FORA);
        bairro3 = mock(Bairro.class);
        when(bairro3.getClassificacao(any(Reta.class))).thenReturn(SituacaoReta.TODA_DENTRO);
        bairro4 = mock(Bairro.class);
        when(bairro4.getClassificacao(any(Reta.class))).thenReturn(SituacaoReta.INTERSECTA);
    }

    @Test
    public void testaBairrosPercorridos() {
        List<Bairro> listaBairros = new ArrayList<>(Arrays.asList(bairro1, bairro2, bairro3, bairro4));
        Roteiro roteiro = new Roteiro(bairro1, bairro4, listaBairros);
        Collection<Bairro> percorridos = roteiro.bairrosPercoridos();
        assertTrue(percorridos.contains(bairro1));
        assertFalse(percorridos.contains(bairro2));
        assertTrue(percorridos.contains(bairro3));
        assertTrue(percorridos.contains(bairro4));
    }
}
