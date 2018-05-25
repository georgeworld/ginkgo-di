package com.georgeinfo.ginkgo.injection.context;

/**
 * 上下文容器提供者，目前没有什么真实的工作，只是转发一下真正的context对象而已，以后可能会填充真实的工作
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ContextProvider {
    private final ApplicationContext context;

    public ContextProvider(ApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext build() {
        context.buildContext();
        return context;
    }


}
