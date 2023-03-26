package ${repositoryImplPackage};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${repositoryPackage}.${repositoryName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 持久层实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${repositoryImplName} implements ${repositoryName} {

    @Autowired
    private ${table.mapperName} ${myMapper};

}
