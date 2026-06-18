import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SpyExampleTest {

    @Test
    void testSpyList() {
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        spyList.add("Hello");

        verify(spyList, times(1)).add("Hello");

        assertEquals(1, spyList.size());
        assertEquals("Hello", spyList.get(0));
    }
}