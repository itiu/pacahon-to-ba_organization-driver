
package ru.mndsc.bigarch.wsclient.bl.organizationservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.5-b03-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "OrganizationEntityEndpoint", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface OrganizationEntityEndpoint {


    /**
     * 
     * @param uid
     * @return
     *     returns ru.mndsc.bigarch.wsclient.bl.organizationservice.EntityType
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEntity", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetEntity")
    @ResponseWrapper(localName = "getEntityResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetEntityResponse")
    public EntityType getEntity(
        @WebParam(name = "uid", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String uid)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param query
     * @param context
     * @return
     *     returns ru.mndsc.bigarch.wsclient.bl.organizationservice.EntityContainerType
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryEntityContainer", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.QueryEntityContainer")
    @ResponseWrapper(localName = "queryEntityContainerResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.QueryEntityContainerResponse")
    public EntityContainerType queryEntityContainer(
        @WebParam(name = "context", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String context,
        @WebParam(name = "query", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        PreparedQueryType query)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param container
     * @param context
     * @return
     *     returns long
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "writeEntityContainer", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.WriteEntityContainer")
    @ResponseWrapper(localName = "writeEntityContainerResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.WriteEntityContainerResponse")
    public long writeEntityContainer(
        @WebParam(name = "context", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String context,
        @WebParam(name = "container", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        EntityContainerType container)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param entity
     * @param context
     * @return
     *     returns java.lang.String
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "writeEntity", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.WriteEntity")
    @ResponseWrapper(localName = "writeEntityResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.WriteEntityResponse")
    public String writeEntity(
        @WebParam(name = "context", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String context,
        @WebParam(name = "entity", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        EntityType entity)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param query
     * @param context
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "queryUids", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.QueryUids")
    @ResponseWrapper(localName = "queryUidsResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.QueryUidsResponse")
    public List<String> queryUids(
        @WebParam(name = "context", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String context,
        @WebParam(name = "query", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        PreparedQueryType query)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param fields
     * @return
     *     returns boolean
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validateFields", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.ValidateFields")
    @ResponseWrapper(localName = "validateFieldsResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.ValidateFieldsResponse")
    public boolean validateFields(
        @WebParam(name = "fields", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        List<String> fields)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param uid
     * @param adjType
     * @param relationName
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAdjacency", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetAdjacency")
    @ResponseWrapper(localName = "getAdjacencyResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetAdjacencyResponse")
    public List<String> getAdjacency(
        @WebParam(name = "uid", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String uid,
        @WebParam(name = "relationName", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String relationName,
        @WebParam(name = "adjType", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        AdjacencyTypeEnum adjType)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param rootUid
     * @param name
     * @param relationList
     */
    @WebMethod
    @RequestWrapper(localName = "createTreeAccessor", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.CreateTreeAccessor")
    @ResponseWrapper(localName = "createTreeAccessorResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.CreateTreeAccessorResponse")
    public void createTreeAccessor(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name,
        @WebParam(name = "rootUid", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String rootUid,
        @WebParam(name = "relationList", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        List<RelationMappingType> relationList);

    /**
     * 
     * @param name
     */
    @WebMethod
    @RequestWrapper(localName = "removeTreeAccessor", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.RemoveTreeAccessor")
    @ResponseWrapper(localName = "removeTreeAccessorResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.RemoveTreeAccessorResponse")
    public void removeTreeAccessor(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name);

    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTARoot", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTARoot")
    @ResponseWrapper(localName = "getTARootResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTARootResponse")
    public String getTARoot(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param uid
     * @param name
     * @return
     *     returns java.lang.String
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTAParentNode", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTAParentNode")
    @ResponseWrapper(localName = "getTAParentNodeResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTAParentNodeResponse")
    public String getTAParentNode(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name,
        @WebParam(name = "uid", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String uid)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param uid
     * @param name
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTAChildNodes", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTAChildNodes")
    @ResponseWrapper(localName = "getTAChildNodesResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTAChildNodesResponse")
    public List<String> getTAChildNodes(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name,
        @WebParam(name = "uid", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String uid)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param uid
     * @param name
     * @return
     *     returns java.util.List<java.lang.String>
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTAPath", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTAPath")
    @ResponseWrapper(localName = "getTAPathResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTAPathResponse")
    public List<String> getTAPath(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name,
        @WebParam(name = "uid", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String uid)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param uid
     * @param name
     * @return
     *     returns ru.mndsc.bigarch.wsclient.bl.organizationservice.TreeNodeType
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTASubtree", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTASubtree")
    @ResponseWrapper(localName = "getTASubtreeResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTASubtreeResponse")
    public TreeNodeType getTASubtree(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name,
        @WebParam(name = "uid", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String uid)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @param uids
     * @param name
     * @return
     *     returns ru.mndsc.bigarch.wsclient.bl.organizationservice.TreeNodeType
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTACovereageSubtree", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTACovereageSubtree")
    @ResponseWrapper(localName = "getTACovereageSubtreeResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetTACovereageSubtreeResponse")
    public TreeNodeType getTACovereageSubtree(
        @WebParam(name = "name", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        String name,
        @WebParam(name = "uids", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        List<String> uids)
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "cleanup", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.Cleanup")
    @ResponseWrapper(localName = "cleanupResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.CleanupResponse")
    public void cleanup()
        throws OrganizationServiceException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getVersion", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetVersion")
    @ResponseWrapper(localName = "getVersionResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetVersionResponse")
    public String getVersion();

    /**
     * 
     * @param uids
     * @return
     *     returns java.util.List<ru.mndsc.bigarch.wsclient.bl.organizationservice.EntityType>
     * @throws OrganizationServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEntities", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetEntities")
    @ResponseWrapper(localName = "getEntitiesResponse", targetNamespace = "http://organization.zdms_component.mndsc.ru/", className = "ru.mndsc.bigarch.wsclient.bl.organizationservice.GetEntitiesResponse")
    public List<EntityType> getEntities(
        @WebParam(name = "uids", targetNamespace = "http://organization.zdms_component.mndsc.ru/")
        List<String> uids)
        throws OrganizationServiceException_Exception
    ;

}