package org.gost19.pacahon.ba_organization_driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gost19.pacahon.client.PacahonClient;
import org.gost19.pacahon.client.predicates;
import org.omg.CORBA.UserException;

import ru.magnetosoft.objects.organization.Department;
import ru.magnetosoft.objects.organization.User;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
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

	public List<Department> getOrganizationRoots(String locale, String from) throws Exception
	{
		try
		{
			List<Department> res = new ArrayList<Department>();

			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			Resource r_department = node.createResource(predicates.query + "any");
			r_department.addProperty(ResourceFactory.createProperty(predicates.rdf, "type"),
					ResourceFactory.createProperty(predicates.docs19, "organization_card"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "name"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "externalIdentifer"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);

			ResIterator subj_it = result.listSubjects();
			while (subj_it.hasNext())
			{
				Department dep = new Department();
				Resource ss = subj_it.next();

				Statement pp = ss.getProperty(ResourceFactory.createProperty(predicates.swrc, "name"));
				dep.setName((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.gost19, "externalIdentifer"));
				dep.setInternalId((String) pp.getLiteral().getValue());

				String id = ss.getLocalName();
				id = id.substring("doc_".length(), id.length());
				dep.setId(id);

				res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}

	}

	public List<User> getUsersByDepartmentId(String departmentId, String locale, boolean withEmail, boolean withActive,
			String from) throws Exception
	{
		try
		{
			List<User> res = new ArrayList<User>();

			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданными uids

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs19:login
			// email->swrc:email
			// post->docs19:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			Resource r_department = node.createResource(predicates.query + "any");
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
					ResourceFactory.createProperty(predicates.zdb, "dep_" + departmentId));
			r_department.addProperty(ResourceFactory.createProperty(predicates.docs19, "department"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);

			ResIterator subj_it = result.listSubjects();
			User usr = new User();
			while (subj_it.hasNext())
			{
				Resource ss = subj_it.next();

				Statement pp = ss.getProperty(ResourceFactory.createProperty(predicates.swrc, "firstName"));
				if (pp != null)
					usr.setFirstName((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.swrc, "lastName"));
				if (pp != null)
					usr.setLastName((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.gost19, "middleName"));
				if (pp != null)
					usr.setMiddleName((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.gost19, "domainName"));
				if (pp != null)
					usr.setDomainName((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.swrc, "email"));
				if (pp != null)
					usr.setEmail((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.docs19, "position"));
				if (pp != null)
					usr.setPost((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.docs19, "department"));
				if (pp != null)
				{
					RDFNode oo = pp.getObject();
					usr.setDepartmentId(oo.toString());
				}

				// it = result.getGraph().find(null, Node.createURI(predicates.docs19 + "department"), null);
				// if (it.hasNext())
				// {
				// Triple tt = it.next();
				// String val = (String) tt.getObject().getLiteral().getValue();
				// val = val.substring("zdb:dep_".length(), val.length());
				// usr.setDepartment(getDepartmentByUserUid(usr.getId(), locale, from));
				// }
				res.add(usr);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}

	}

	/**
	 * {@inheritDoc} @@@
	 */
	public Department getDepartmentByUid(String uid, String locale, String from) throws Exception
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
				dep = getDepartmentFromGraph(result.getGraph(), locale, from);
			}

			return dep;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

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

	private Department getDepartmentFromGraph(Graph gg, String locale, String from)
	{
		Department dep = new Department();

		ExtendedIterator<Triple> it = gg.find(null, Node.createURI(predicates.swrc + "name"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			dep.setName((String) tt.getObject().getLiteral().getValue());
		}
		it = gg.find(null, Node.createURI(predicates.gost19 + "parentDepartment"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			String val = (String) tt.getObject().getLiteral().getValue();
			val = val.substring("zdb:dep_".length(), val.length());
			dep.setId(val);
		}
		return dep;
	}

	private User getUserFromGraph(Graph gg, String locale, String from)
	{
		User usr = new User();

		ExtendedIterator<Triple> it = gg.find(null, Node.createURI(predicates.swrc + "firstName"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setFirstName((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(null, Node.createURI(predicates.swrc + "lastName"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setLastName((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(null, Node.createURI(predicates.gost19 + "middleName"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setMiddleName((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(null, Node.createURI(predicates.gost19 + "domainName"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setLogin((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(null, Node.createURI(predicates.swrc + "email"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setEmail((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(null, Node.createURI(predicates.docs19 + "position"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setPosition((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(null, Node.createURI(predicates.docs19 + "department"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			String val = (String) tt.getObject().getLiteral().getValue();
			val = val.substring("zdb:dep_".length(), val.length());

			// usr.setDepartment(getDepartmentByUid(val, locale, from));
		}
		return usr;
	}

	public User getUserByLogin(String login, String locale, String from) throws Exception
	{

		try
		{
			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданным uid

			// gost19:department // firstName->swrc:firstName@[localeName] // surname->swrc:middlename@[localeName] //
			// domainName->docs19:login // email->swrc:email // post->docs19:position@[localeName] //
			// secondName->swrc:lastName@[localeName]

			Resource r_department = node.createResource(predicates.query + "any");
			r_department.addProperty(ResourceFactory.createProperty(predicates.auth, "login"),
					node.createLiteral(login));
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
				usr = getUserFromGraph(result.getGraph(), locale, from);
			}

			return usr;
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get user", ex);
		}

	}

	public List<User> getUsersByName(String tokens, String locale, boolean withEmail, boolean withActive, String from)
			throws Exception
	{
		try
		{
			List<User> res = new ArrayList<User>();

			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданными uids

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs19:login
			// email->swrc:email
			// post->docs19:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			Resource r_department = node.createResource(predicates.query + "any");
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "firstName"),
					node.createLiteral("[" + tokens + "]"));
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

			if (result != null)
			{
				User usr = new User();

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
					usr.setDepartment(getDepartmentByUserUid(usr.getId(), locale, from));
				}
				res.add(usr);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}
	}

	public List<User> getUsersByUids(Collection<String> ids, String locale, String from) throws Exception
	{
		try
		{
			List<User> res = new ArrayList<User>();

			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданными uids

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs19:login
			// email->swrc:email
			// post->docs19:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			StringBuffer sb = new StringBuffer();
			sb.append("\"[");
			boolean first = true;
			for (String id : ids)
			{
				if (!first)
					sb.append(",");

				sb.append("zdb:doc_");
				sb.append(id);
				first = false;
			}
			sb.append("\"]");

			Resource r_department = node.createResource(sb.toString());
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

			ResIterator subj_it = result.listSubjects();
			User usr = new User();
			while (subj_it.hasNext())
			{
				Resource ss = subj_it.next();

				Statement pp = ss.getProperty(ResourceFactory.createProperty(predicates.swrc, "firstName"));
				usr.setFirstName((String) pp.getLiteral().getValue());

				pp = ss.getProperty(ResourceFactory.createProperty(predicates.swrc, "lastName"));
				usr.setLastName((String) pp.getLiteral().getValue());

				// it = result.getGraph().find(null, Node.createURI(predicates.gost19 + "middleName"), null);
				// if (it.hasNext())
				// {
				// Triple tt = it.next();
				// usr.setMiddleName((String) tt.getObject().getLiteral().getValue());
				// }
				//
				// it = result.getGraph().find(null, Node.createURI(predicates.gost19 + "domainName"), null);
				// if (it.hasNext())
				// {
				// Triple tt = it.next();
				// usr.setDomainName((String) tt.getObject().getLiteral().getValue());
				// }

				// it = result.getGraph().find(null, Node.createURI(predicates.swrc + "email"), null);
				// if (it.hasNext())
				// {
				// Triple tt = it.next();
				// usr.setEmail((String) tt.getObject().getLiteral().getValue());
				// }

				// it = result.getGraph().find(null, Node.createURI(predicates.docs19 + "position"), null);
				// if (it.hasNext())
				// {
				// Triple tt = it.next();
				// usr.setPost((String) tt.getObject().getLiteral().getValue());
				// }

				// it = result.getGraph().find(null, Node.createURI(predicates.docs19 + "department"), null);
				// if (it.hasNext())
				// {
				// Triple tt = it.next();
				// String val = (String) tt.getObject().getLiteral().getValue();
				// val = val.substring("zdb:dep_".length(), val.length());
				// usr.setDepartment(getDepartmentByUserUid(usr.getId(), locale, from));
				// }
				res.add(usr);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}

	}

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

	public Department getDepartmentByUserUid(String uid, String locale, String from) throws Exception
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
			r_department.addProperty(ResourceFactory.createProperty(predicates.docs19, "department"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);
			String department = null;

			if (result != null)
			{
				ExtendedIterator<Triple> it = result.getGraph().find(null,
						Node.createURI(predicates.docs19 + "department"), null);
				if (it.hasNext())
				{
					Triple tt = it.next();
					String val = (String) tt.getObject().getLiteral().getValue();
					val = val.substring("zdb:dep_".length(), val.length());
					department = val;
				}
				return getDepartmentByUid(department, locale, from);
			}

			return null;
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get department", ex);
		}
	}
	/*
	 * public List<Department> getDepartmentsByIds(Collection<String> ids, String locale, String from) throws Exception
	 * { try { List<Department> res = new ArrayList<Department>();
	 * 
	 * Model node = ModelFactory.createDefaultModel(); node.setNsPrefixes(predicates.getPrefixs());
	 * 
	 * StringBuffer sb = new StringBuffer(); sb.append("\"["); boolean first = true; for (String id : ids) { if (!first)
	 * sb.append(",");
	 * 
	 * sb.append("zdb:doc_"); sb.append(id); first = false; } sb.append("\"]");
	 * 
	 * Resource r_department = node.createResource(sb.toString());
	 * 
	 * dep = getDepartmentFromGraph (result.getGraph(), locale, from);
	 * 
	 * }
	 */

} // end UserManager

