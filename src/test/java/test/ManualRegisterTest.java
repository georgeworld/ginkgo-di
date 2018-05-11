package test;

import com.georgeinfo.ginkgo.injection.BAC;
import com.georgeinfo.ginkgo.injection.DI;
import com.georgeinfo.ginkgo.injection.context.BaseApplicationContext;
import com.georgeinfo.ginkgo.injection.exception.DIException;
import org.junit.*;
import test.service.AgeService;
import test.service.interfacesvc.NameService;
import test.service.interfacesvc.NameServiceImpl;

/**
 * 手动注册bean，完成依赖注入环境的初始化，并执行DI测试
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ManualRegisterTest {

    public ManualRegisterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //初始化DI环境，在正式的项目中，在项目的入口处初始化一次即可
        System.out.println("-- 手动注册Bean，开始初始化DI环境 ------");
        try {
            BaseApplicationContext context = BAC.manual()
                    .add(AgeService.class)
                    .add(NameServiceImpl.class)
                    .build();
            System.out.println("## 当前DI容器中，一共有【" + context.getBeanSize() + "】个bean实例");
        } catch (DIException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        System.out.println("-- 调用DI结束 --------------------------");
    }

    // 测试从DI容器中获取bean实例
    @Test
    public void testDI() {
        //测试按照Bean ID来获取bean
        AgeService ageService = null;
        try {
            ageService = DI.getBeanById("ageService");
            System.out.println("## 按照Bean ID获取到bean实例，并执行其方法的结果是：age=" + ageService.getMyAge());
        } catch (DIException ex) {
            ex.printStackTrace();
        }

        //测试按照接口类，来获取Bean
        NameService nameService = null;
        try {
            nameService = DI.getBeanByInterface(NameService.class);
            System.out.println("## 按照接口类，获取到bean实例，并执行其方法的结果是：name=" + nameService.getMyName());
        } catch (DIException ex) {
            ex.printStackTrace();
        }
    }
}