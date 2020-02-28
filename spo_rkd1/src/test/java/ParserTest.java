import abbreviations.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void whenGetAbbreviationsThenTheyAreInSet() {
        Parser parser = new Parser();
        String string = "ПЗССС-МБ НП должна обеспечивать связь С существующей ЦЗССС ВКИП,"
        + "TDM/TDMA для дуплексного обмена; SCPC (DAMA) для передачи информации в по ГОСТ 1 и ГОСТ РВ 1488.";
        HashSet<String> expect = new HashSet<>();
        expect.add("ПЗССС-МБ");
        expect.add("НП");
        expect.add("ЦЗССС");
        expect.add("ВКИП");
        expect.add("TDM/TDMA");
        expect.add("ГОСТ РВ");
        expect.add("ГОСТ");
        expect.add("SCPC (DAMA)");
        Set<String> result = parser.getAbbreviations(string);
        Assert.assertThat(result, is(expect));
    }

}