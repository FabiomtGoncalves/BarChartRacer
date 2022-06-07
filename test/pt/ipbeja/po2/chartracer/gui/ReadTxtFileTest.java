package pt.ipbeja.po2.chartracer.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadTxtFileTest {
    @Test
    void testReadFile() {
        String[][] file = ReadTxtFile.readFileToStringArray2D("src/pt/ipbeja/po2/chartracer/cities.txt", ",");
        assertEquals(file[5][0], "1500");
        assertEquals(file[5][1], "Beijing");
        assertEquals(file[5][2], "China");
        assertEquals(file[5][3], "672");
        assertEquals(file[5][4], "East Asia");
        assertEquals(file[305][0], "1521");
        assertEquals(file[305][1], "Nanjing");
        assertEquals(file[305][2], "China");
        assertEquals(file[305][3], "153");
        assertEquals(file[305][4], "East Asia");
    }
}
