package pt.ipbeja.po2.chartracer.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.po2.chartracer.gui.BarChartRacerStart;
import pt.ipbeja.po2.chartracer.gui.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelTest {
    private View view;


    @Test
    void readFileToStringArray2D() {
        Board board = new Board();
        BarChartRacerStart barChartRacerStart = new BarChartRacerStart();
        Model model = new Model(board, barChartRacerStart);

        String[][] file = model.readFileToStringArray2D("src/pt/ipbeja/po2/chartracer/datasets/cities.txt", ",");
        assertEquals(file[5][0], "1500");
        assertEquals(file[5][1], "Beijing");
        assertEquals(file[5][2], "China");
        assertEquals(file[5][3], "672");
        assertEquals(file[5][4], "East Asia");
        assertEquals(file[4797][0], "1842");
        assertEquals(file[4797][1], "London");
        assertEquals(file[4797][2], "United Kingdom");
        assertEquals(file[4797][3], "1990");
        assertEquals(file[4797][4], "Europe");
    }
}