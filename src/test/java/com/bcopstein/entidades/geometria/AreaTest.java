package com.bcopstein.entidades.geometria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class AreaTest {
    private Area area;

    @BeforeEach
    public void setup() {
        this.area = new Area(new Ponto(20, 110), new Ponto(200, 30));
    }

    @Test
    public void testaPontoCentral() {
        Ponto pontocentral = area.pontoCentral();
        assertEquals(110, pontocentral.getX());
        assertEquals(70, pontocentral.getY());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 130, 1", // acima e no meio do eixo x
            "210, 130, 5", // acima e à direita
            "10, 130, 9", // acima e à esquerda
            "100, 20, 2", // embaixo e no meio do eixo x
            "210, 20, 6", // embaixo e à direita
            "10, 20, 10", // embaixo e à esquerda
            "50, 50, 0" // dentro da área
    })
    public void testaCodificaPonto(int x, int y, byte resultado) {
        assertEquals(resultado, area.codificaPonto(new Ponto(x,y)));
    }

    @ParameterizedTest
    @CsvSource({
            "50, 50, 60, 60, TODA_DENTRO",
            "1000, 1000, 600, 600, TODA_FORA",
            "120, 50, 100, 10, INTERSECTA",
    })
    public void testaClassifica(int x1, int y1, int x2, int y2, String classificacao) {
        Reta reta = new Reta(new Ponto(x1, y1), new Ponto(x2, y2));
        assertEquals(SituacaoReta.valueOf(classificacao), area.classifica(reta));
    }


    @ParameterizedTest
    @CsvSource({
            "110, 40, 110, 20", // on-point (supEsq x)
            "120, 40, 110, 20", // out-point (supEsq x)
            "90, 150, 110, 150", // on-point (supEsq y)
            "90, 140, 110, 150" // out-point (supEsq y)
    })
    public void testaExcecaoNoConstrutorComLimites(int x1, int y1, int x2, int y2) {
        assertThrows(IllegalArgumentException.class, () -> new Area(new Ponto(x1, y1), new Ponto(x2, y2)),
            "O retangulo deve ser definido pela diagonal principal");
    }

    @ParameterizedTest
    @CsvSource({
            "109, 40, 110, 20", // off-point (supEsq x)
            "90, 40, 110, 20", // in-point (supEsq x)
            "90, 151, 110, 150", // off-point (supEsq y)
            "90, 160, 110, 150" // in-point (supEsq y)
    })
    public void testaAusenciaDeExcecaoConstrutorComLimites(int x1, int y1, int x2, int y2) {
        assertDoesNotThrow(() -> new Area(new Ponto(x1, y1), new Ponto(x2, y2)));
    }
}
