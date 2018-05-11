![image](https://raw.githubusercontent.com/georgeworld/georgeworld.github.com/master/ginkgo/di/img/ginkgo-di-logo.png)
# Ginkgo DI Framework
A Simple dependency injection framework(DI/IOC)，
一个精简的DI依赖注入框架（IOC），适用于那些不方便引入Spring依赖的项目，目前功能包括使用注解式注册Bean，使用手动硬编码式注册Bean，按照接口获取并使用托管Bean对象，按照bean Id的方式获取bean对象等。

# 用法
## 使用注解扫包式初始化DI环境
<pre><code>
@Before
    public void setUp() {
        //初始化DI环境，在正式的项目中，在项目的入口处初始化一次即可
        System.out.println("-- 扫包解析注解方式，开始初始化DI环境 ---");
        try {
            BaseApplicationContext context = BAC
                    .annotation()
                    .setBeanPackPath("test.service")
                    .build();
            System.out.println("## 当前DI容器中，一共有【" + context.getBeanSize() + "】个bean实例");
        } catch (ScannerException e) {
            e.printStackTrace();
        } catch (DIException e) {
            e.printStackTrace();
        }
    }
</pre></code>

## 使用手动编码时注册类方式，初始化DI环境
<pre><code>
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
</pre></code>

## 使用DI获取托管bean对象
<pre><code>
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
</pre></code>

## 执行结果
![image](https://raw.githubusercontent.com/georgeworld/georgeworld.github.com/master/ginkgo/di/img/result-of-di.png)<br>
