package com.bcopstein.entidades;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class PassageiroTest {
    @Test
    public void testaNovoPassageiro() {
        Passageiro passageiro = Passageiro.novoPassageiro("01232145612", "Jose Almeida");
        assertEquals("01232145612", passageiro.getCPF());
        assertEquals("Jose Almeida", passageiro.getNome());
        assertEquals(8, passageiro.getPontuacaoAcumulada());
        assertEquals(1, passageiro.getQtdadeAvaliacoes());
    }

    @Test
    public void testaPontuacaoMedia() {
        Passageiro passageiro = Passageiro.passageiroExistente("01236925874","Eduardo Carlos", 30, 4);
        assertEquals(7, passageiro.getPontuacaoMedia());
    }

    @ParameterizedTest
    @CsvSource({
            "0", // on-point
            "-1"  // out-point
    })
    public void testaExcecaoEmInfoPontuacao(int pontuacao) {
        Passageiro passageiro = Passageiro.novoPassageiro("01236925874","Eduardo Carlos");
        assertThrows(IllegalArgumentException.class, () -> passageiro.infoPontuacao(pontuacao));
    }

    @ParameterizedTest
    @CsvSource({
            "1", // off-point
            "5"  // in-point
    })
    public void testaInfoPontuacao(int pontuacao) {
        Passageiro passageiro = Passageiro.passageiroExistente("01236925874",
                "Eduardo Carlos", 8, 2);
        assertDoesNotThrow(() -> passageiro.infoPontuacao(pontuacao));
        assertEquals(8+pontuacao, passageiro.getPontuacaoAcumulada());
        assertEquals(3, passageiro.getQtdadeAvaliacoes());
    }
}
