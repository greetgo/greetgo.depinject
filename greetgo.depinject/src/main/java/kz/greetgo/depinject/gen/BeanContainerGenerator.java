package kz.greetgo.depinject.gen;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

public class BeanContainerGenerator extends Generator {
  
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName)
      throws UnableToCompleteException {
    
    TypeOracle typeOracle = context.getTypeOracle();
    JClassType typeInfo = typeOracle.findType(typeName);
    if (typeInfo == null) {
      logger.log(Type.ERROR, "No info about " + typeName);
      throw new UnableToCompleteException();
    }
    typeInfo = typeInfo.isInterface();
    if (typeInfo == null) {
      logger.log(Type.ERROR, typeName + " must be interface");
      throw new UnableToCompleteException();
    }
    
    String implTypeName = typeName + "Impl";
    
    if (null != typeOracle.findType(implTypeName)) {
      int index = 2;
      while (true) {
        String name = implTypeName + index;
        if (null == typeOracle.findType(name)) {
          implTypeName = name;
          break;
        }
        index++;
      }
    }
    
    if (context.tryReuseTypeFromCache(implTypeName)) return implTypeName;
    
    int lastPointIndex = implTypeName.lastIndexOf('.');
    String implTypePackageName = implTypeName.substring(0, lastPointIndex);
    String implTypeSimpleName = implTypeName.substring(lastPointIndex + 1);
    
    PrintWriter writer = context.tryCreate(logger, implTypePackageName, implTypeSimpleName);
    if (writer == null) {
      logger.log(Type.ERROR, "Cannot create writer: implTypePackageName = " + implTypePackageName
          + ", implTypeSimpleName = " + implTypeSimpleName);
      throw new UnableToCompleteException();
    }
    
    try {
      BeanContainerImplGenerator g = new BeanContainerImplGenerator();
      g.beanContainerIface = Class.forName(typeName);
      
      g.implClassName = implTypeSimpleName;
      g.packageName = implTypePackageName;
      
      g.generateTo(writer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      context.commit(logger, writer);
    }
    
    return implTypeName;
  }
}
