import com.ywm.utils.ValidateCodeUtils;
import org.junit.Test;

/**
 * @author: YEWENMAO
 * @data: 2020/10/28 20:25
 */
public class ValidateTest {
    @Test
    public void Test() {

        System.out.println(ValidateCodeUtils.generateValidateCode4String(4));
    }
}
