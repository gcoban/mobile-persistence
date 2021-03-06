 /*******************************************************************************
  Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
   
  $revision_history$
  08-jan-2015   Steven Davelaar
  1.0           initial creation
 ******************************************************************************/
 package oracle.ateam.sample.mobile.v2.persistence.metadata;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import oracle.adfmf.util.KXmlUtil;
import oracle.adfmf.util.XmlAnyDefinition;

import oracle.ateam.sample.mobile.util.MessageUtils;


/**
 * Class that maps to the top-level node in the persistenceMapping XML file
 */
public class ObjectPersistenceMapping
  extends XmlAnyDefinition
{
  private static ObjectPersistenceMapping instance = null;
  private Map<String,ClassMappingDescriptor> classMappingDescriptors = null;
  private Map<String,OAuthConfig> OAuthConfigSet = null;

  public ObjectPersistenceMapping()
  {
    super();
  }

  public static ObjectPersistenceMapping getInstance()
  {
    if (instance == null)
    {
      InputStream is =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(PersistenceConfig.getPersistenceMappingXML());
      ObjectPersistenceMapping topNode = null;
      try
      {
        // we use mobile-object-persistence as top node name so we can edit it inside JDev!
        topNode =
          (ObjectPersistenceMapping) KXmlUtil.loadFromXml(is, ObjectPersistenceMapping.class,
                                                          "mobileObjectPersistence");
      }
      catch (Exception e)
      {
        MessageUtils.handleError(e);
      }
      instance = topNode;
    }
    return instance;
  }

  /**
   * Returs map with fully-qualified class name as the key and associated ClassMappingDescriptoras instance as value
   * @return
   */
  public Map<String,ClassMappingDescriptor> getClassMappingDescriptors()
  {
    if (classMappingDescriptors == null)
    {
      classMappingDescriptors = new HashMap<String,ClassMappingDescriptor>();
      List<XmlAnyDefinition> descriptors = this.getChildDefinitions("classMappingDescriptor");
      for (XmlAnyDefinition descriptor : descriptors)
      {
        ClassMappingDescriptor cmd = new ClassMappingDescriptor(descriptor);
        classMappingDescriptors.put(cmd.getClazz().getName(), cmd);
      }
    }
    return classMappingDescriptors;
  }

  /**
   * Return ClassMappingDescriptor for corresponding class name
   * @param className
   * @return
   */
  public ClassMappingDescriptor findClassMappingDescriptor(String className)
  {
    return getClassMappingDescriptors().get(className);
  }

  /**
   * Return OAuth configurations as defined in persistenceMapping.xml
   * @return
   */
  public Map<String,OAuthConfig> getOAuthConfigMap()
  {
    if (OAuthConfigSet == null)
    {
      OAuthConfigSet = new HashMap<String,OAuthConfig>();
      XmlAnyDefinition configSet = this.getChildDefinition("oauthConfigSet");
      List<XmlAnyDefinition> configDefs = configSet.getChildDefinitions("oauthConfig");
      for (XmlAnyDefinition configDef : configDefs)
      {
        OAuthConfig oauthConfig = new OAuthConfig(configDef);
        OAuthConfigSet.put(oauthConfig.getConfigName(), oauthConfig);
      }
    }
    return OAuthConfigSet;
  }

  /**
   * Return a specific OAuth configuration
   * @param configName
   * @return
   */
  public OAuthConfig findOAuthConfig(String configName)
  {
    return getOAuthConfigMap().get(configName);
  }
}
