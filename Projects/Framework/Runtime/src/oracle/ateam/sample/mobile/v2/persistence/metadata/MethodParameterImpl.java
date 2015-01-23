 /*******************************************************************************
  Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
   
  $revision_history$
  08-jan-2015   Steven Davelaar
  1.0           initial creation
 ******************************************************************************/
 package oracle.ateam.sample.mobile.v2.persistence.metadata;
import oracle.adfmf.util.Utility;



/**
 * Implementation of SOAP method parameter or REST resource parameter that can be instantiated programmatically
 */
public class MethodParameterImpl
  implements MethodParameter
{
  
  private String name;
  private String valueProvider;
  private String dataObjectAttribute;
  private String value;
  private String javaType;
  private boolean pathParam = false;

  public MethodParameterImpl(String name)
  {
    this.name= name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setValueProvider(String valueProvider)
  {
    this.valueProvider = valueProvider;
  }

  public String getValueProvider()
  {
    return valueProvider;
  }

  public void setDataObjectAttribute(String dataObjectAttribute)
  {
    this.dataObjectAttribute = dataObjectAttribute;
  }

  public String getDataObjectAttribute()
  {
    return dataObjectAttribute;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getValue()
  {
    return value;
  }

  public void setJavaType(String javaType)
  {
    this.javaType = javaType;
  }

  public String getJavaType()
  {
    return javaType;
  }

  public MethodParameterImpl()
  {
    super();
  }


  public boolean isPathParam()
  {
    return pathParam;
  }

  public void setPathParam(boolean pathParam)
  {
    this.pathParam = pathParam;
  }


  public Class getJavaTypeClass()
  {
    String type = getJavaType();
    if (type==null)
    {
      type = "java.lang.String";
    }
    try
    {
      return Utility.loadClass(type);
    }
    catch (ClassNotFoundException e)
    {
  //    MessageUtils.handleError(e);
    }
    return String.class;
  }

  public boolean isLiteralValue()
  {
    return MethodParameter.LITERAL_VALUE.equalsIgnoreCase(getValueProvider());
  }

  public boolean isELExpression()
  {
    return MethodParameter.EL_EXPRESSION.equalsIgnoreCase(getValueProvider());
  }

  public boolean isDataObjectAttribute()
  {
    return MethodParameter.DATA_OBJECT_ATTRIBUTE.equalsIgnoreCase(getValueProvider());
  }

  public boolean isSerializedDataObject()
  {
    return MethodParameter.SERIALIZED_DATA_OBJECT.equalsIgnoreCase(getValueProvider());
  }

  public boolean isSearchValue()
  {
    return MethodParameter.SEARCH_VALUE.equalsIgnoreCase(getValueProvider());
  }

}
