package com.bcopstein.entidades;

import com.bcopstein.entidades.geometria.Area;
import com.bcopstein.entidades.geometria.Ponto;
import com.bcopstein.entidades.geometria.Reta;
import com.bcopstein.entidades.geometria.SituacaoReta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BairroTest {
    private static Area area;
    private static Reta r1, r2;

    @BeforeAll
    public static void setup() {
        r1 = new Reta(new Ponto(10,10), new Ponto(20,10));
        r2 = new Reta(new Ponto(10,10), new Ponto(20,20));
        area = mock(Area.class);
        when(area.classifica(r1)).thenReturn(SituacaoReta.TODA_FORA);
        when(area.classifica(r2)).thenReturn(SituacaoReta.INTERSECTA);
        when(area.pontoCentral()).thenReturn(new Ponto(20,20));
    }

    @Test
    public void testaGetClassificacaoReta() {
        Bairro bairro = new Bairro("Auxiliadora", area, 10);
        assertEquals(SituacaoReta.INTERSECTA, bairro.getClassificacao(r2));
    }

    @Test
    public void testaGetPontoCentral() {
        Bairro bairro = new Bairro("Auxiliadora", area, 10);
        Ponto pontoCentral = bairro.getPontoCentral();
        assertEquals(20, pontoCentral.getX());
        assertEquals(20, pontoCentral.getY());
    }

    @Test
    public void testaNovoBairroQuadrado() {
        Bairro novoBairro = Bairro.novoBairroQuadrado("Higienopolis", new Ponto(20,60), 40, 15);
        assertEquals("Higienopolis", novoBairro.getNome());
        assertEquals(20, novoBairro.getArea().getPSupEsq().getX());
        assertEquals(60, novoBairro.getArea().getPSupEsq().getY());
        assertEquals(60, novoBairro.getArea().getPInfDir().getX());
        assertEquals(20, novoBairro.getArea().getPInfDir().getY());
        assertEquals(15, novoBairro.getCustoTransporte());
    }

    @Test
    public void testaNovoBairroRetangular() {
        Bairro novoBairro = Bairro.novoBairroRetangular("Higienopolis", new Ponto(20,60), 40, 10, 15);
        assertEquals("Higienopolis", novoBairro.getNome());
        assertEquals(20, novoBairro.getArea().getPSupEsq().getX());
        assertEquals(60, novoBairro.getArea().getPSupEsq().getY());
        assertEquals(60, novoBairro.getArea().getPInfDir().getX());
        assertEquals(50, novoBairro.getArea().getPInfDir().getY());
        assertEquals(15, novoBairro.getCustoTransporte());
    }

    @ParameterizedTest
    @CsvSource({
            "-0.01", // off-point
            "-1.0" // in-point
    })
    public void testaExcecaoEmAlteraCustoTransporte(double valor) {
        Bairro bairro = new Bairro("Auxiliadora", area, 10);
        assertThrows(IllegalArgumentException.class, () -> bairro.alteraCustoTransporte(valor));
    }

    @ParameterizedTest
    @CsvSource({
            "0.0", // on-point
            "1.0"  // out-point
    })
    public void testaAlteraCustoTransporte(double valor) {
        Bairro bairro = new Bairro("Auxiliadora", area, 10);
        assertDoesNotThrow(() -> bairro.alteraCustoTransporte(valor));
        assertEquals(valor, bairro.getCustoTransporte());
    }
}
