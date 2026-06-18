package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class SpyExampleTest {

    @Test
    void shouldUseSpy() {
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        spyList.add("Spring");
        
        verify(spyList).add("Spring");
    }
}