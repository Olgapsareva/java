import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.util.Arrays;

public class ArrayExtractorTest {

    /*private static ArrayExtractor arrayExtractor;

    @BeforeAll
    public static void init() {
        arrayExtractor = new ArrayExtractor();
    }*/

    @Test
    public void testExtractArrayAfterGivenNumber(){
        ArrayExtractor arrayExtractor = new ArrayExtractor();
        int i = 4;
        Integer[] nums = {1,2,4,4,2,3,4,1,7};
        Integer[] result = {1,7};
        Assertions.assertEquals(Arrays.toString(result), Arrays.toString(arrayExtractor.extractArrayAfterGivenNumber(nums, i)));
    }

    @Test
    public void testExtractArrayAfterGivenNumber1(){
        ArrayExtractor arrayExtractor = new ArrayExtractor();
        int i = 4;
        Integer[] nums = {};
        Assertions.assertThrows(MyRuntimeException.class, ()->arrayExtractor.extractArrayAfterGivenNumber(nums, i));
    }

    @Test
   public void testExtractArrayAfterGivenNumber2(){
        ArrayExtractor arrayExtractor = new ArrayExtractor();
        int i = 4;
        Integer[] nums = {1,5,6,8};
        Assertions.assertThrows(MyRuntimeException.class, ()->arrayExtractor.extractArrayAfterGivenNumber(nums, i));
    }

    @Test
    public void testCheckForNumbers(){
        ArrayExtractor arrayExtractor = new ArrayExtractor();
        Integer[] nums = {1,2,4,4,2,3,4,1,7};
        int a = 1;
        int b = 4;
        Assertions.assertEquals(true, arrayExtractor.checkForNumbers(nums, a,b));
    }

    @Test
    public void testCheckForNumbers1(){
        ArrayExtractor arrayExtractor = new ArrayExtractor();
        Integer[] nums = {1,2,4,4,2,3,4,1,7};
        int a = 0;
        int b = 5;
        Assertions.assertFalse(arrayExtractor.checkForNumbers(nums, a,b));
    }

    @Test
    public void testCheckForNumbers2(){
        ArrayExtractor arrayExtractor = new ArrayExtractor();
        Integer[] nums = {};
        int a = 1;
        int b = 4;
        Assertions.assertFalse(arrayExtractor.checkForNumbers(nums, a,b));
    }


}
