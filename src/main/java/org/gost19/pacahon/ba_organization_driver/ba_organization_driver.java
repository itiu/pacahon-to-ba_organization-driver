package org.gost19.pacahon.ba_organization_driver;

import org.gost19.pacahon.client.PacahonClient;
import org.gost19.pacahon.client.predicates;
import org.omg.CORBA.UserException;

import ru.magnetosoft.bigarchive.server.base.beans.Department;
import ru.magnetosoft.bigarchive.server.base.beans.User;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Менеджер пользователей.
 * 
 * @author BushenevV
 */
public class ba_organization_driver
{
	private PacahonClient pacahon_client;
	private String ticket;

	/**
	 * Конструктор.
	 */
	public ba_organization_driver(String endpoint_pretending_organization)
	{
		pacahon_client = new PacahonClient(endpoint_pretending_organization);
		ticket = pacahon_client.get_ticket("user", "9cXsvbvu8=", "NewUserManager.constructor");
	}

	/**
	 * {@inheritDoc} @@@
	 */
	public Department getDepartmentByUid(String uid, String localeName, String from) throws Exception
	{
		try
		{
			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// swrc:name@[localeName]
			// gost19:parentDepartment

			Resource r_department = node.createResource(predicates.zdb + "doc_" + uid);
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "name"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "parentDepartment"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);
			Department dep = null;

			if (result != null)
			{
				dep = new Department();

				ExtendedIterator<Triple> it = result.getGraph().find(null, Node.createURI(predicates.swrc + "name"),
						null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					dep.setName((String) tt.getObject().getLiteral().getValue());
				}
				it = result.getGraph().find(null, Node.createURI(predicates.gost19 + "parentDepartment"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					String val = (String) tt.getObject().getLiteral().getValue();
					val = val.substring("zdb:dep_".length(), val.length());
					dep.setId(val);
				}
			}

			return dep;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

	public Department getDepartmentById(String uid, String locale) throws Exception
	{
		try
		{
			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// swrc:name@[localeName]
			// gost19:parentDepartment

			Resource r_department = node.createResource(predicates.zdb + "doc_" + uid);
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "name"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "parentDepartment"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, "NewOrganizationComponentImpl.getDepartmentById");
			Department dep = null;

			if (result != null)
			{
				dep = new Department();

				ExtendedIterator<Triple> it = result.getGraph().find(null, Node.createURI(predicates.swrc + "name"),
						null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					dep.setName((String) tt.getObject().getLiteral().getValue());
				}
				it = result.getGraph().find(null, Node.createURI(predicates.gost19 + "parentDepartment"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					String val = (String) tt.getObject().getLiteral().getValue();
					val = val.substring("zdb:dep_".length(), val.length());
					dep.setId(val);
				}
			}

			return dep;
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get department", ex);
		}
	}

	/**
	 * {@inheritDoc} @@@
	 */
	public String getDepartmentUidByUserUid(String uid, String from) throws Exception
	{
		try
		{
			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// gost19:department

			String res = null;

			Resource r_department = node.createResource(predicates.zdb + "doc_" + uid);
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "department"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);

			if (result != null)
			{

				ExtendedIterator<Triple> it = result.getGraph().find(null,
						Node.createURI(predicates.gost19 + "department"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					String val = (String) tt.getObject().getLiteral().getValue();
					val = val.substring("zdb:dep_".length(), val.length());
					res = val;
				}
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get root department", ex);
		}

	} // end getDepartmentUidByUserUid()

	/**
	 * Получение уникального идентификатора пользователя по имени учетной записи.
	 * 
	 * @param login
	 *            имя учетной записи
	 * @return уникальный идентификатор пользователя
	 * @throws UserException
	 *             в случае ошибки
	 */
	public String getUserUidByLoginInternal(String login, String from) throws Exception
	{
		try
		{
			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// gost19:department

			String res = null;

			Resource r_department = node.createResource(predicates.query + "any");
			r_department.addProperty(ResourceFactory.createProperty(predicates.auth, "login"),
					node.createLiteral(login));
			r_department.addProperty(ResourceFactory.createProperty(predicates.rdf, "type"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);

			if (result != null)
			{

				ExtendedIterator<Triple> it = result.getGraph().find(null, Node.createURI(predicates.rdf + "type"),
						null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					String val = (String) tt.getSubject().getLocalName();
					val = val.substring("doc_".length(), val.length());
					res = val;
				}
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get user", ex);
		}
	} // end getUserUidByLogin()

	/**
	 * Найти пользователя по уникальному идентификатору.
	 * 
	 * @param uid
	 *            уникальный идентификатор
	 * @param localeName
	 *            имя локали
	 * @return пользователь
	 * @throws UserException
	 *             в случае ошибки
	 */
	public User selectUserByUidInternal(String uid, String localeName, String from) throws Exception
	{
		try
		{
			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданным uid

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs19:login
			// email->swrc:email
			// post->docs19:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			Resource r_department = node.createResource(predicates.zdb + "doc_" + uid);
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "firstName"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "lastName"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "middleName"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.docs19, "domainName"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "email"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.docs19, "position"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.docs19, "department"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);
			User usr = null;

			if (result != null)
			{
				usr = new User();

				ExtendedIterator<Triple> it = result.getGraph().find(null,
						Node.createURI(predicates.swrc + "firstName"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					usr.setFirstName((String) tt.getObject().getLiteral().getValue());
				}

				it = result.getGraph().find(null, Node.createURI(predicates.swrc + "lastName"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					usr.setLastName((String) tt.getObject().getLiteral().getValue());
				}

				it = result.getGraph().find(null, Node.createURI(predicates.gost19 + "middleName"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					usr.setMiddleName((String) tt.getObject().getLiteral().getValue());
				}

				it = result.getGraph().find(null, Node.createURI(predicates.gost19 + "domainName"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					usr.setDomainName((String) tt.getObject().getLiteral().getValue());
				}

				it = result.getGraph().find(null, Node.createURI(predicates.swrc + "email"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					usr.setEmail((String) tt.getObject().getLiteral().getValue());
				}

				it = result.getGraph().find(null, Node.createURI(predicates.docs19 + "position"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					usr.setPost((String) tt.getObject().getLiteral().getValue());
				}

				it = result.getGraph().find(null, Node.createURI(predicates.docs19 + "department"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					String val = (String) tt.getObject().getLiteral().getValue();
					val = val.substring("zdb:dep_".length(), val.length());
					usr.setDepartmentId(val);
				}
			}

			return usr;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get root department", ex);
		}
	} // end selectUserByUid()

} // end UserManager

