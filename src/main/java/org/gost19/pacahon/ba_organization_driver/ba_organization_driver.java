package org.gost19.pacahon.ba_organization_driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.gost19.pacahon.client.PacahonClient;
import org.gost19.pacahon.client.predicates;

import ru.magnetosoft.objects.organization.Department;
import ru.magnetosoft.objects.organization.User;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.impl.LiteralLabel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
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

	private String correct_locale(String locale)
	{
		if (locale == "ru")
			locale = "Ru";
		if (locale == "en")
			locale = "En";
		return locale;
	}

	public List<Department> getOrganizationRoots(String locale, String from) throws Exception
	{
		locale = correct_locale(locale);

		try
		{
			List<Department> res = new ArrayList<Department>();

			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			Resource r_department = node.createResource(predicates.query + "any");
			r_department.addProperty(ResourceFactory.createProperty(predicates.rdf, "type"),
					ResourceFactory.createProperty(predicates.docs19, "organization_card"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "active"),
					node.createLiteral("true"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "name"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "externalIdentifer"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);

			ResIterator subj_it = result.listSubjects();
			while (subj_it.hasNext())
			{
				Resource ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, result.getGraph(), locale, from);
				res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}

	public List<Department> getDepartmentsByParentId(String parentId, String locale, boolean withActive, String from)
			throws Exception
	{
		locale = correct_locale(locale);

		try
		{
			List<Department> res = new ArrayList<Department>();

			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			Resource r_department = node.createResource(predicates.query + "any");
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "name"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "parentDepartment"),
					ResourceFactory.createProperty(predicates.zdb, "dep_" + parentId));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "active"),
					node.createLiteral("true"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "externalIdentifer"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);

			ResIterator subj_it = result.listSubjects();
			while (subj_it.hasNext())
			{
				Resource ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, result.getGraph(), locale, from);
				res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}

	public List<User> getUsersByDepartmentId(String departmentId, String locale, boolean withEmail, boolean withActive,
			String from) throws Exception
	{
		locale = correct_locale(locale);
		Department dd = getDepartmentByExtId(departmentId, locale, from);

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
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "active"),
					node.createLiteral("true"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.rdf, "type"),
					ResourceFactory.createProperty(predicates.docs19, "employee_card"));

			Model result = pacahon_client.get(ticket, node, from);

			ResIterator subj_it = result.listSubjects();
			while (subj_it.hasNext())
			{
				Resource ss = subj_it.next();

				User usr = getUserFromGraph(ss, result.getGraph(), null);

				if (usr != null)
				{
					usr.setDepartment(dd);
					res.add(usr);
				}
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
		locale = correct_locale(locale);

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
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "externalIdentifer"),
					ResourceFactory.createProperty(predicates.query, "get"));

			Model result = pacahon_client.get(ticket, node, from);
			Department dep = null;

			ResIterator subj_it = result.listSubjects();
			if (subj_it.hasNext())
			{
				Resource ss = subj_it.next();
				dep = getDepartmentFromGraph(ss, result.getGraph(), locale, from);
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
	public Department getDepartmentByExtId(String externalIdentifer, String locale, String from) throws Exception
	{
		locale = correct_locale(locale);

		try
		{
			Model node = ModelFactory.createDefaultModel();
			node.setNsPrefixes(predicates.getPrefixs());

			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// swrc:name@[localeName]
			// gost19:parentDepartment

			Resource r_department = node.createResource(predicates.query + "any");
			r_department.addProperty(ResourceFactory.createProperty(predicates.swrc, "name"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "parentDepartment"),
					ResourceFactory.createProperty(predicates.query, "get"));
			r_department.addProperty(ResourceFactory.createProperty(predicates.gost19, "externalIdentifer"),
					node.createLiteral(externalIdentifer));

			Model result = pacahon_client.get(ticket, node, from);
			Department dep = null;

			ResIterator subj_it = result.listSubjects();
			if (subj_it.hasNext())
			{
				Resource ss = subj_it.next();
				dep = getDepartmentFromGraph(ss, result.getGraph(), locale, from);
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

	public User getUserByLogin(String login, String locale, String from) throws Exception
	{
		locale = correct_locale(locale);

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

			ResIterator subj_it = result.listSubjects();
			if (subj_it.hasNext())
			{
				Resource ss = subj_it.next();

				usr = getUserFromGraph(ss, result.getGraph(), null);
			}

			return usr;
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get user", ex);
		}

	}

	public List<User> getUsersByFullTextSearch(String words, String locale, boolean withEmail, boolean withActive,
			String from) throws Exception
	{
		locale = correct_locale(locale);

		String[] tokens = words.split(" ");

		StringBuffer str_tokens = new StringBuffer();

		for (String token : tokens)
		{
			if (str_tokens.length() > 0)
				str_tokens.append(',');
			str_tokens.append('^');
			str_tokens.append(token);
		}

		try
		{
			HashMap<String, User> res = new HashMap<String, User>();

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
			r_department.addProperty(ResourceFactory.createProperty(predicates.query, "fulltext"),
					node.createLiteral(str_tokens.toString()));
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
					ResourceFactory.createProperty(predicates.query, "get_reifed"));

			Model result = pacahon_client.get(ticket, node, from);

			ResIterator subj_it = result.listSubjects();
			while (subj_it.hasNext())
			{
				Resource ss = subj_it.next();
				String id = ss.getLocalName();

				User usr = null;

				if (id == null)
				{
					updateUserReifedData(ss, result.getGraph(), res);
				} else
					usr = getUserFromGraph(ss, result.getGraph(), res);

				if (usr != null)
					res.put(usr.getId(), usr);
			}

			return new ArrayList<User>(res.values());
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}
	}

	public List<User> getUsersByUids(Collection<String> ids, String locale, String from) throws Exception
	{
		locale = correct_locale(locale);

		try
		{
			HashMap<String, User> res = new HashMap<String, User>();

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
			while (subj_it.hasNext())
			{
				Resource ss = subj_it.next();

				getUserFromGraph(ss, result.getGraph(), res);
			}

			return new ArrayList<User>(res.values());
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
	public User selectUserByUidInternal(String uid, String locale, String from) throws Exception
	{
		locale = correct_locale(locale);

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

			ResIterator subj_it = result.listSubjects();
			if (subj_it.hasNext())
			{
				Resource ss = subj_it.next();

				String id = ss.getLocalName();

				usr = getUserFromGraph(ss, result.getGraph(), null);
			}

			return usr;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get root department", ex);
		}
	} // end selectUserByUid()

	public Department getDepartmentByUserUid(String uid, String locale, String from) throws Exception
	{
		locale = correct_locale(locale);

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

	private Department getDepartmentFromGraph(Resource ss, Graph gg, String locale, String from)
	{
		locale = correct_locale(locale);

		Department dep = new Department();

		String id = ss.getLocalName();
		id = id.substring("doc_".length(), id.length());
		dep.setId(id);

		ExtendedIterator<Triple> it = gg.find(ss.asNode(), Node.createURI(predicates.swrc + "name"), null);

		String val_en = null;
		String val_ru = null;
		while (it.hasNext())
		{
			Triple tt = it.next();
			LiteralLabel ll = tt.getObject().getLiteral();
			if (ll.language().equals("en"))
				val_en = (String) tt.getObject().getLiteral().getValue();
			if (ll.language().equals("ru"))
				val_ru = (String) tt.getObject().getLiteral().getValue();
		}

		if (val_en != null)
			dep.setName(val_en, "En");

		if (val_ru != null)
			dep.setName(val_ru, "Ru");

		it = gg.find(ss.asNode(), Node.createURI(predicates.gost19 + "parentDepartment"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			String val = (String) tt.getObject().getLiteral().getValue();
			val = val.substring("zdb:dep_".length(), val.length());
			dep.setId(val);
		}
		it = gg.find(ss.asNode(), Node.createURI(predicates.gost19 + "externalIdentifer"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			String val = (String) tt.getObject().getLiteral().getValue();
			dep.setInternalId(val);
		}

		return dep;
	}

	private void updateUserReifedData(Resource reifed_ss, Graph gg, HashMap<String, User> users)
	{
		ExtendedIterator<Triple> it;

		it = gg.find(reifed_ss.asNode(), Node.createURI(predicates.rdf + "Subject"), null);
		String subject = null;
		if (it.hasNext())
		{
			Triple tt = it.next();
			subject = (String) tt.getObject().getLiteral().getValue();
		}

		String id = subject.substring("zdb:doc_".length(), subject.length());

		User user = users.get(id);

		if (user == null)
		{
			user = new User();
			user.setId(id);
			users.put(id, user);
		}

		it = gg.find(reifed_ss.asNode(), Node.createURI(predicates.rdf + "Predicate"), null);
		String predicate = null;
		if (it.hasNext())
		{
			Triple tt = it.next();
			predicate = (String) tt.getObject().getLiteral().getValue();
		}
		if (predicate.equals("docs19:department"))
		{
			Department dep = new Department();

			it = gg.find(reifed_ss.asNode(), Node.createURI(predicates.swrc + "name"), null);

			String val_en = null;
			String val_ru = null;
			while (it.hasNext())
			{
				Triple tt = it.next();
				LiteralLabel ll = tt.getObject().getLiteral();
				if (ll.language().equals("en"))
					val_en = (String) tt.getObject().getLiteral().getValue();
				else if (ll.language().equals("ru"))
					val_ru = (String) tt.getObject().getLiteral().getValue();
				else if (ll.language().equals(""))
					val_ru = (String) tt.getObject().getLiteral().getValue();
			}

			if (val_en != null)
				dep.setName(val_en, "En");

			if (val_ru != null)
				dep.setName(val_ru, "Ru");

			user.setDepartment(dep);
		}

		// это уже излишне для данного документа, так как в карточке пользователя содержится только одно подразделение
		/*
		 * it = gg.find(reifed_ss.asNode(), Node.createURI(predicates.rdf + "Object"), null); String object = null; if
		 * (it.hasNext()) { Triple tt = it.next(); object = (String) tt.getObject().getLiteral().getValue(); }
		 */

	}

	private User getUserFromGraph(Resource ss, Graph gg, HashMap<String, User> users)
	{
		String id = ss.getLocalName();

		if (id == null)
		{
			return null;
		}

		id = id.substring("doc_".length(), id.length());

		User usr = null;

		if (users != null)
		{
			usr = users.get(id);

			if (usr == null)
			{
				usr = new User();
				usr.setId(id);
				users.put(id, usr);
			}
		} else
		{
			usr = new User();
			usr.setId(id);
		}

		ExtendedIterator<Triple> it = gg.find(ss.asNode(), Node.createURI(predicates.swrc + "firstName"), null);
		{
			String val_en = null;
			String val_ru = null;
			while (it.hasNext())
			{
				Triple tt = it.next();
				LiteralLabel ll = tt.getObject().getLiteral();
				if (ll.language().equals("en"))
					val_en = (String) tt.getObject().getLiteral().getValue();
				if (ll.language().equals("ru"))
					val_ru = (String) tt.getObject().getLiteral().getValue();
			}

			if (val_en != null)
				usr.setFirstName(val_en, "En");

			if (val_ru != null)
				usr.setFirstName(val_ru, "Ru");
		}

		it = gg.find(ss.asNode(), Node.createURI(predicates.swrc + "lastName"), null);
		{
			String val_en = null;
			String val_ru = null;
			while (it.hasNext())
			{
				Triple tt = it.next();
				LiteralLabel ll = tt.getObject().getLiteral();
				if (ll.language().equals("en"))
					val_en = (String) tt.getObject().getLiteral().getValue();
				if (ll.language().equals("ru"))
					val_ru = (String) tt.getObject().getLiteral().getValue();
			}

			if (val_en != null)
				usr.setLastName(val_en, "En");

			if (val_ru != null)
				usr.setLastName(val_ru, "Ru");
		}

		it = gg.find(ss.asNode(), Node.createURI(predicates.gost19 + "middleName"), null);
		{
			String val_en = null;
			String val_ru = null;
			while (it.hasNext())
			{
				Triple tt = it.next();
				LiteralLabel ll = tt.getObject().getLiteral();
				if (ll.language().equals("en"))
					val_en = (String) tt.getObject().getLiteral().getValue();
				if (ll.language().equals("ru"))
					val_ru = (String) tt.getObject().getLiteral().getValue();
			}

			if (val_en != null)
				usr.setMiddleName(val_en, "En");

			if (val_ru != null)
				usr.setMiddleName(val_ru, "Ru");
		}

		it = gg.find(ss.asNode(), Node.createURI(predicates.gost19 + "domainName"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setLogin((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(ss.asNode(), Node.createURI(predicates.swrc + "email"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			usr.setEmail((String) tt.getObject().getLiteral().getValue());
		}

		it = gg.find(ss.asNode(), Node.createURI(predicates.docs19 + "position"), null);
		{
			String val_en = null;
			String val_ru = null;
			while (it.hasNext())
			{
				Triple tt = it.next();
				LiteralLabel ll = tt.getObject().getLiteral();
				if (ll.language().equals("en"))
					val_en = (String) tt.getObject().getLiteral().getValue();
				if (ll.language().equals("ru"))
					val_ru = (String) tt.getObject().getLiteral().getValue();
			}

			if (val_en != null)
				usr.setPosition(val_en, "En");

			if (val_ru != null)
				usr.setPosition(val_ru, "Ru");
		}

		it = gg.find(ss.asNode(), Node.createURI(predicates.docs19 + "department"), null);
		if (it.hasNext())
		{
			Triple tt = it.next();
			String val = (String) tt.getObject().getLiteral().getValue();
			val = val.substring("zdb:dep_".length(), val.length());

			// usr.setDepartment(getDepartmentByUid(val, locale, from));
		}

		return usr;
	}

} // end UserManager

