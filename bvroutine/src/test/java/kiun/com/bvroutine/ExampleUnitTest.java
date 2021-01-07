package kiun.com.bvroutine;

import org.junit.Test;

import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;
import kiun.com.bvroutine.utils.ClassUtil;
import kiun.com.bvroutine.utils.ObjectUtil;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Verifys({
            @Verify(value = NotNull.class, desc = "A的错误")
    })
    class VerifyA{

//        @Verifys({
//                @Verify(value = NotNull.class, desc = "fieldA的错误")
//        })
        private String fieldA;
    }

    @Verifys({
            @Verify(value = NotNull.class, desc = "B的错误")
    })
    class VerifyB extends VerifyA{
        private String fieldB;
    }

    @Test
    public void addition_isCorrect() throws Exception {

    }

    @Test
    public void testVerify(){
        ReqPrj172ProblemListBean reqPrj172ProblemListBean = new ReqPrj172ProblemListBean();

        int a = 0;
    }

    @Test
    public void testValue(){

        byte bt = 0;
        char c = '1';
        Short s = 10;
        int i = 10;
        double l = 10.0;
        Boolean b = false;
        String str = "1212";

        ObjectUtilTest objectUtilTest = ObjectUtil.newObject(ObjectUtilTest.class, s);

        assert objectUtilTest != null;
    }

    @Test
    public void testObject(){
        ObjectUtilTest objectUtilTest = ObjectUtil.newObject(ObjectUtilTest.class, new ObjectContext());

        assert objectUtilTest != null;
    }
}