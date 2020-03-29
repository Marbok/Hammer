package context;

import beaninfo.BeanInfo;
import exceptions.ContextException;
import metadata.json.JsonFileDefinition;
import org.apache.commons.lang3.StringUtils;
import util.CollectionsUtil;
import util.JsonProcessor;

import java.io.IOException;
import java.util.*;

public class JsonContext implements Context {

    private Map<String, BeanInfo> beanInfos = new HashMap<>();

    public JsonContext(String filePath) {
        if (StringUtils.isEmpty(filePath))
            throw new NullPointerException("filePath equals null or empty");

        Queue<String> pathsContext = new LinkedList<>();
        pathsContext.add(filePath);
        fillBeanInfos(pathsContext);
    }

    private void fillBeanInfos(Queue<String> pathsContext) {
        JsonFileDefinition parse;
        String path;
        while (CollectionsUtil.isNonEmpty(pathsContext)) {
            path = pathsContext.poll();
            try {
                parse = new JsonProcessor(path).parse(JsonFileDefinition.class);
                if (CollectionsUtil.isNonEmpty(parse.getImports()))
                    pathsContext.addAll(parse.getImports());
                if (CollectionsUtil.isNonEmpty(parse.getBeans()))
                    parse.getBeans().forEach(bean -> {
                        if (beanInfos.get(bean.getBeanName()) == null)
                            beanInfos.put(bean.getBeanName(), BeanInfo.map(bean));
                        else
                            throw new ContextException("Bean " + bean.getBeanName() + " in initialized twice");
                    });
            } catch (IOException e) {
                throw new ContextException("Error in file: " + path, e);
            }
        }
    }

    @Override
    public BeanInfo getBeanInfo(String beanName) {
        var beanInfo = beanInfos.get(beanName);
        if (beanInfo == null) {
            throw new ContextException(beanName + " is absent");
        }
        return beanInfo;
    }

    @Override
    public Collection<BeanInfo> getAllBeanInfo() {
        return beanInfos.values();
    }
}
