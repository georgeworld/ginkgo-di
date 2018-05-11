package test.service.interfacesvc;

import com.georgeinfo.ginkgo.injection.annotation.Service;

/**
 * @author George (GeorgeWorld@qq.com)
 */
@Service
public class NameServiceImpl implements NameService {
    public String getMyName() {
        return "吴亦凡";
    }
}
