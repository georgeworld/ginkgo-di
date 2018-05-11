package com.georgeinfo.ginkgo.injection.context;

/**
 * 上下文容器提供者，目前没有什么真实的工作，只是转发一下真正的context对象而已，以后可能会填充真实的工作
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ContextProvider {
    private final BaseApplicationContext context;

    public ContextProvider(BaseApplicationContext context) {
        this.context = context;
    }

    public BaseApplicationContext build() {
        context.buildContext();
        return context;
    }


}
