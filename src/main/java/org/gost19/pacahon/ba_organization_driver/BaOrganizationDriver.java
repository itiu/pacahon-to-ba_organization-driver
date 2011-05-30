package org.gost19.pacahon.ba_organization_driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.gost19.pacahon.BaDriver;
import org.gost19.pacahon.client.predicates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ru.magnetosoft.objects.organization.Department;
import ru.magnetosoft.objects.organization.IOrganizationEntity;
import ru.magnetosoft.objects.organization.User;

/**
 * Менеджер пользователей.
 * 
 * @author BushenevV
 */
public class BaOrganizationDriver extends BaDriver
{
	public BaOrganizationDriver(String endpoint_pretending_organization) throws Exception
	{
		super.initailize(endpoint_pretending_organization);
	}

	private String correct_locale(String locale)
	{
		if (locale == "ru")
			locale = "Ru";
		if (locale == "en")
			locale = "En";
		return locale;
	}

	public synchronized List<Department> getOrganizationRoots(String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			List<Department> res = new ArrayList<Department>();

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put("a", predicates._query + "get");
			arg.put(predicates._gost19 + "tag", "root");
			arg.put(predicates._docs + "active", "true");
			arg.put(predicates._swrc + "name", predicates._query + "get");
			arg.put(predicates._gost19 + "externalIdentifer", predicates._query + "get");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getOrganizationRoots");

			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, locale, from);
				res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}

	public List<Department> getDepartmentsByParentId(String parentExtId, String locale, boolean withActive, String from)
			throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			String departmentId = null;
			Department dd = getDepartmentByExtId(parentExtId, locale, from + ":getDepartmentsByParentId");

			departmentId = dd.getId();

			List<Department> res = new ArrayList<Department>();

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put("a", "docs:unit_card");
			arg.put(predicates._swrc + "name", predicates._query + "get");
			arg.put(predicates._docs + "parentUnit", predicates._zdb + "dep_" + departmentId);
			arg.put(predicates._gost19 + "externalIdentifer", predicates._query + "get");

			if (withActive == true)
				arg.put(predicates._docs + "active", "true");

			JSONArray result = pacahon_client.get(ticket, arg, from);
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentsByParentId");

				if (dep != null)
					res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}

	public List<User> getUsersByDepartmentId(String departmentExtId, String locale, boolean withEmail,
			boolean withActive, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			String departmentId = null;

			Department dd = getDepartmentByExtId(departmentExtId, locale, from + ":getUsersByDepartmentId");

			departmentId = dd.getId();

			List<User> res = new ArrayList<User>();

			// выберем нижеперечисленные предикаты из субьекта с заданными uids

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs:login
			// email->swrc:email
			// post->docs:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put("a", predicates._docs + "employee_card");

			arg.put(predicates._gost19 + "synchronize", predicates._query + "get");
			arg.put(predicates._swrc + "firstName", predicates._query + "get");
			arg.put(predicates._swrc + "lastName", predicates._query + "get");
			arg.put(predicates._gost19 + "middleName", predicates._query + "get");
			arg.put(predicates._auth + "login", predicates._query + "get");
			arg.put(predicates._swrc + "email", predicates._query + "get");
			arg.put(predicates._docs + "position", predicates._query + "get");
			arg.put(predicates._docs + "unit", predicates._query + "get");
			arg.put(predicates._gost19 + "externalIdentifer", predicates._query + "get");
			arg.put(predicates._docs + "parentUnit", predicates._zdb + "dep_" + departmentId);

			if (withActive == true)
				arg.put(predicates._docs + "active", "true");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getUsersByDepartmentId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				User usr = getUserFromGraph(ss, null, locale, true);
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

	public List<User> getFullUsersByDepartmentId(String departmentExtId, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			String departmentId = null;

			Department dd = getDepartmentByExtId(departmentExtId, locale, from + ":getFullUsersByDepartmentId");

			departmentId = dd.getId();

			List<User> res = new ArrayList<User>();

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put("a", predicates._docs + "employee_card");
			arg.put(predicates._docs + "active", "true");
			arg.put(predicates._docs + "parentUnit", predicates._zdb + "dep_" + departmentId);
			arg.put(predicates._query + "all_predicates", "query:get");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getFullUsersByDepartmentId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				User usr = getUserFromGraph(ss, null, locale, true);
				if (usr != null)
				{
					usr.setDepartment(dd);
					usr.getAttributes().put("departmentId", departmentExtId);
					res.add(usr);
				}
			}
			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}

	}

	public Department getDepartmentByUid(String uid, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{

			Department dep = null;
			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// swrc:name@[localeName]
			// gost19:parentDepartment

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._zdb + "doc_" + uid);
			arg.put("a", predicates._query + "get");
			arg.put(predicates._swrc + "name", predicates._query + "get");
			arg.put(predicates._docs + "parentUnit", predicates._query + "get");
			arg.put(predicates._gost19 + "externalIdentifer", predicates._query + "get");

			JSONArray result = pacahon_client.get(ticket, arg, from);
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentByUid");
			}

			return dep;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

	public List<Department> getDepartmentsByName(String words, String locale, boolean withActive, String from)
			throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		String[] tokens = words.split(" ");

		StringBuffer str_tokens = new StringBuffer();

		for (String token : tokens)
		{
			if (str_tokens.length() > 0)
				str_tokens.append(',');
			str_tokens.append(token.toLowerCase());
		}

		try
		{
			List<Department> res = new ArrayList<Department>();

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put(predicates._swrc + "name", predicates._query + "get");
			arg.put(predicates._query + "fulltext", str_tokens.toString());
			arg.put("a", predicates._docs + "unit_card");
			arg.put(predicates._gost19 + "externalIdentifer", predicates._query + "get");

			if (withActive == true)
				arg.put(predicates._docs + "active", "true");

			JSONArray result = pacahon_client.get(ticket, arg, from);
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentsByName (FT)");
				res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}

	public Department getDepartmentByExtId(String externalIdentifer, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// swrc:name@[localeName]
			// gost19:parentDepartment
			Department dep = null;

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put("a", predicates._query + "get");
			arg.put(predicates._swrc + "name", predicates._query + "get");
			arg.put(predicates._docs + "parentUnit", predicates._query + "get");
			arg.put(predicates._gost19 + "externalIdentifer", externalIdentifer);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentByExtId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentByExtId");
			}

			return dep;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

	public String getDepartmentUidByUserUid(String uid, String from) throws Exception
	{
		recheck_ticket();
		try
		{
			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// gost19:department

			String res = null;

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._zdb + "doc_" + uid);
			arg.put(predicates._docs + "unit", predicates._query + "get");

			JSONArray result = pacahon_client.get(ticket, arg, from);
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				String val = (String) ss.get(predicates._docs + "unit");
				val = val.substring("zdb:dep_".length(), val.length());
				res = val;
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get root department", ex);
		}

	} // end getDepartmentUidByUserUid()

	/**
	 * Получение уникального идентификатора пользователя по имени учетной
	 * записи.
	 * 
	 * @param login
	 *            имя учетной записи
	 * @return уникальный идентификатор пользователя
	 */
	public String getUserUidByLoginInternal(String login, String from) throws Exception
	{
		recheck_ticket();
		try
		{
			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// gost19:department

			String res = null;

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put(predicates._swrc + "name", predicates._query + "get");
			arg.put(predicates._auth + "login", login.toUpperCase());
			arg.put(predicates._docs + "active", "true");

			arg.put("a", predicates._query + "get");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getUserUidByLoginInternal");
			Iterator<JSONObject> subj_it = result.iterator();

			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				String val = (String) ss.get("@");
				val = val.substring("zdb:doc_".length(), val.length());
				res = val;
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get user", ex);
		}
	} // end getUserUidByLogin()

	public User getUserByLogin(String login, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			// выберем нижеперечисленные предикаты из субьекта с заданным uid

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs:login
			// email->swrc:email
			// post->docs:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put(predicates._swrc + "firstName", predicates._query + "get");
			arg.put(predicates._swrc + "lastName", predicates._query + "get");
			arg.put(predicates._gost19 + "middleName", predicates._query + "get");
			arg.put(predicates._swrc + "email", predicates._query + "get");
			arg.put(predicates._docs + "position", predicates._query + "get");
			arg.put(predicates._docs + "unit", predicates._query + "get");
			arg.put(predicates._auth + "login", login.toUpperCase());
			arg.put(predicates._docs + "active", "true");
			arg.put("a", predicates._docs + "employee_card");

			User usr = null;
			JSONArray result = pacahon_client.get(ticket, arg, from + ":getUserByLogin");
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				usr = getUserFromGraph(ss, null, locale, true);
				usr.setLogin(login);
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
		recheck_ticket();
		locale = correct_locale(locale);

		String[] tokens = words.split(" ");

		StringBuffer str_tokens = new StringBuffer();

		for (String token : tokens)
		{
			if (str_tokens.length() > 0)
				str_tokens.append(',');
			str_tokens.append('^');
			str_tokens.append(token.toLowerCase());
		}

		try
		{
			HashMap<String, User> res = new HashMap<String, User>();

			// выберем нижеперечисленные предикаты из субьекта с заданными uids

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs:login
			// email->swrc:email
			// post->docs:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			JSONObject arg = new JSONObject();

			arg.put("@", predicates._query + "any");
			arg.put(predicates._swrc + "firstName", predicates._query + "get");
			arg.put(predicates._swrc + "lastName", predicates._query + "get");
			arg.put(predicates._gost19 + "middleName", predicates._query + "get");
			arg.put(predicates._auth + "login", predicates._query + "get");
			arg.put(predicates._swrc + "email", predicates._query + "get");
			arg.put(predicates._docs + "position", predicates._query + "get");
			arg.put(predicates._docs + "unit", predicates._query + "get_reifed");
			arg.put(predicates._query + "fulltext", str_tokens.toString());
			arg.put("a", predicates._docs + "employee_card");

			if (withActive == true)
				arg.put(predicates._docs + "active", "true");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getUsersByFullTextSearch");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				User usr = null;

				String id = (String) ss.get("@");

				if (id.charAt(0) == '_' && id.charAt(1) == ':' && id.charAt(2) == 'R')
				{
					updateUserReifedData(ss, res);
				} else
					usr = getUserFromGraph(ss, res, locale, true);

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
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			HashMap<String, User> res = new HashMap<String, User>();

			// выберем нижеперечисленные предикаты из субьекта с заданными uids

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs:login
			// email->swrc:email
			// post->docs:position@[localeName]
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
			sb.append("]\"");

			JSONObject arg = new JSONObject();

			arg.put("@", sb.toString());
			arg.put(predicates._swrc + "firstName", predicates._query + "get");
			arg.put(predicates._swrc + "lastName", predicates._query + "get");
			arg.put(predicates._gost19 + "middleName", predicates._query + "get");
			arg.put(predicates._auth + "login", predicates._query + "get");
			arg.put(predicates._swrc + "email", predicates._query + "get");
			arg.put(predicates._docs + "position", predicates._query + "get");
			arg.put(predicates._docs + "unit", predicates._query + "get");
			arg.put("a", predicates._docs + "employee_card");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getUsersByUids");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				User usr = getUserFromGraph(ss, null, locale, true);
				if (usr != null)
				{
					// usr.setDepartment(dd);
					res.put(usr.getId(), usr);
				}
			}

			return new ArrayList<User>(res.values());
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}

	}

	/**
	 * Найти орг-единицу по уникальному идентификатору.
	 * 
	 * @param uid
	 *            уникальный идентификатор
	 * @param localeName
	 *            имя локали
	 * @return пользователь
	 */
	public IOrganizationEntity selectUnitByUidInternal(String uid, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			JSONObject arg = new JSONObject();

			arg.put("@", predicates._zdb + "doc_" + uid);
			arg.put(predicates._query + "all_predicates", predicates._query + "get");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":selectUnitByUidInternal");
			Iterator<JSONObject> subj_it = result.iterator();

			IOrganizationEntity ou = null;
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				ou = getUserFromGraph(ss, null, locale, false);
				if (ou == null)
					ou = getDepartmentFromGraph(ss, null, locale);
			}

			return ou;
		} catch (Exception ex)
		{
			throw new Exception("ex! selectUserByUidInternal", ex);
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
	 */
	public User selectUserByUidInternal(String uid, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			JSONObject arg = new JSONObject();

			arg.put("@", predicates._zdb + "doc_" + uid);
			arg.put(predicates._swrc + "firstName", predicates._query + "get");
			arg.put(predicates._swrc + "lastName", predicates._query + "get");
			arg.put(predicates._gost19 + "middleName", predicates._query + "get");
			arg.put(predicates._auth + "login", predicates._query + "get");
			arg.put(predicates._swrc + "email", predicates._query + "get");
			arg.put(predicates._docs + "position", predicates._query + "get");
			arg.put(predicates._docs + "unit", predicates._query + "get");
			arg.put("a", predicates._docs + "employee_card");

			User usr = null;
			JSONArray result = pacahon_client.get(ticket, arg, from + ":selectUserByUidInternal");
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				usr = getUserFromGraph(ss, null, locale, true);
			}

			return usr;
		} catch (Exception ex)
		{
			throw new Exception("ex! selectUserByUidInternal", ex);
		}
	} // end selectUserByUid()

	public Department getDepartmentByUserUid(String uid, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			// выберем нижеперечисленные предикаты из субьекта с заданным uid

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs:login
			// email->swrc:email
			// post->docs:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			String department = null;
			JSONObject arg = new JSONObject();

			arg.put("@", predicates.zdb + "doc_" + uid);
			arg.put(predicates._docs + "unit", predicates._query + "get");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentByUserUid");
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				String val = (String) ss.get(predicates._docs + "unit");
				if (val != null)
				{
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

	private boolean isUser(Object rdf_type)
	{
		boolean res = false;

		//		Object rdf_type = oo.get("a");
		if (rdf_type == null)
			rdf_type = "docs:employee_card";

		if (rdf_type instanceof JSONArray)
		{
			JSONArray rdf_type_array = (JSONArray) rdf_type;

			for (int i = 0; i < rdf_type_array.size(); i++)
			{
				String a = (String) rdf_type_array.get(i);
				if (a.equals("docs:employee_card"))
				{
					return true;
				}

			}
		} else if (rdf_type instanceof String)
		{
			if (((String) rdf_type).equals("docs:employee_card"))
			{
				return true;
			}
		}

		return false;
	}

	/*
	 * public List<Department> getDepartmentsByIds(Collection<String> ids,
	 * String locale, String from) throws Exception { try { List<Department> res
	 * = new ArrayList<Department>();
	 * 
	 * Model node = ModelFactory.createDefaultModel();
	 * node.setNsPrefixes(predicates.getPrefixs());
	 * 
	 * StringBuffer sb = new StringBuffer(); sb.append("\"["); boolean first =
	 * true; for (String id : ids) { if (!first) sb.append(",");
	 * 
	 * sb.append("zdb:doc_"); sb.append(id); first = false; } sb.append("\"]");
	 * 
	 * Resource r_department = node.createResource(sb.toString());
	 * 
	 * dep = getDepartmentFromGraph (result.getGraph(), locale, from);
	 * 
	 * }
	 */

	String doc_prefix = "doc_";

	private Department getDepartmentFromGraph(JSONObject oo, String locale, String from)
	{
		Object rdf_type = oo.get("a");

		if (rdf_type != null && isUser(rdf_type) == true)
			return null;

		recheck_ticket();
		locale = correct_locale(locale);

		Department dep = new Department();

		String id = (String) oo.get("@");
		dep.uid = id;
		id = id.substring(id.indexOf(doc_prefix) + doc_prefix.length(), id.length());
		dep.setId(id);

		Object namez = oo.get(predicates._swrc + "name");

		if (namez instanceof JSONArray)
		{
			Iterator<String> subj_it = ((JSONArray) namez).iterator();
			while (subj_it.hasNext())
			{
				dep.set__Name(subj_it.next());
			}

		} else if (namez instanceof String)
		{
			dep.set__Name((String) namez);

		}

		String parentDepartment = (String) oo.get(predicates._gost19 + "parentDepartment");

		if (parentDepartment != null)
		{
			String val = parentDepartment.substring("zdb:dep_".length(), parentDepartment.length());
			dep.setParentDepartmentId(val);
		}

		String externalIdentifer = (String) oo.get(predicates._gost19 + "externalIdentifer");
		if (externalIdentifer != null)
		{
			dep.setInternalId(externalIdentifer);
		}

		dep.getAttributes().put("@", dep.uid);

		if (rdf_type != null)
			dep.getAttributes().put("a", rdf_type.toString());

		return dep;
	}

	private User getUserFromGraph(JSONObject oo, HashMap<String, User> users, String locale, boolean isUser)
	{
		String uid;
		Object rdf_type = oo.get("a");

		if (isUser == false)
		{
			isUser = isUser(oo);
		}

		if (isUser == false)
			return null;

		String id = (String) oo.get("@");
		if (id == null)
		{
			return null;
		}

		uid = id;
		id = id.substring(id.indexOf(doc_prefix) + doc_prefix.length(), id.length());

		User usr = null;

		if (users != null)
		{
			usr = users.get(id);

			if (usr == null)
			{
				usr = new User();
				users.put(id, usr);
			}
		} else
		{
			usr = new User();
		}

		usr.setId(id);
		usr.uid = uid;

		usr._set__oFirstName(oo.get(predicates._swrc + "firstName"), locale);
		usr._set__oLastName(oo.get(predicates._swrc + "lastName"), locale);
		usr._set__oMiddleName(oo.get(predicates._gost19 + "middleName"), locale);
		usr._set__oPosition(oo.get(predicates._docs + "position"), locale);

		Object valuez = oo.get(predicates._auth + "login");
		if (valuez != null)
		{
			usr.setLogin((String) valuez);
		}

		valuez = oo.get(predicates._swrc + "email");
		if (valuez != null)
		{
			usr.setEmail((String) valuez);
			usr.getAttributes().put("email", (String) valuez);
		}

		valuez = oo.get(predicates._gost19 + "synchronize");
		if (valuez != null)
		{
			usr.getAttributes().put("doNotSynchronize", "1");
		}

		valuez = oo.get(predicates._gost19 + "internal_phone");
		if (valuez != null)
		{
			usr.getAttributes().put("phone", (String) valuez);
		}

		valuez = oo.get(predicates._docs + "active");
		if (valuez != null)
		{
			usr.getAttributes().put("active", (String) valuez);
		}

		valuez = oo.get(predicates._gost19 + "mobile");
		if (valuez != null)
		{
			usr.getAttributes().put("mobilePrivate", (String) valuez);
		}

		valuez = oo.get(predicates._swrc + "phone");
		if (valuez != null)
		{
			usr.getAttributes().put("phoneExt", (String) valuez);
		}

		valuez = oo.get(predicates._gost19 + "work_mobile");
		if (valuez != null)
		{
			usr.getAttributes().put("mobile", (String) valuez);
		}

		//		valuez = oo.get(predicates._docs + "unit");
		//		if (valuez != null)
		//		{
		// val = val.substring("zdb:dep_".length(), val.length());
		// usr.setDepartment(getDepartmentByUid(val, locale, from));
		//		}

		String externalIdentifer = (String) oo.get(predicates._gost19 + "externalIdentifer");
		if (externalIdentifer != null)
		{
			usr.setTabNomer(externalIdentifer);
			usr.getAttributes().put("pid", externalIdentifer);
		}

		usr.getAttributes().put("@", usr.uid);
		if (rdf_type != null)
			usr.getAttributes().put("a", rdf_type.toString());

		return usr;
	}

	private void updateUserReifedData(JSONObject oo, HashMap<String, User> users)
	{
		String subject = (String) oo.get(predicates._rdf + "Subject");
		String id = subject.substring("zdb:doc_".length(), subject.length());
		User user = users.get(id);
		if (user == null)
		{
			user = new User();
			user.setId(id);
			users.put(id, user);
		}

		String predicate = (String) oo.get(predicates._rdf + "Predicate");
		if (predicate.equals("docs:unit"))
		{
			Department dep = new Department();
			Object namez = oo.get(predicates._swrc + "name");

			if (namez instanceof JSONArray)
			{
				Iterator<String> subj_it = ((JSONArray) namez).iterator();
				while (subj_it.hasNext())
				{
					dep.set__Name(subj_it.next());
				}

			} else if (namez instanceof String)
			{
				dep.set__Name((String) namez);

			}

			user.setDepartment(dep);
		}
		// это уже излишне для данного документа, так как в карточке
		// пользователя содержится только одно подразделение
		/*
		 * it = gg.find(reifed_ss.asNode(), Node.createURI(predicates.rdf +
		 * "Object"), null); String object = null; if (it.hasNext()) { Triple tt
		 * = it.next(); object = (String)
		 * tt.getObject().getLiteral().getValue(); }
		 */

	}

	public void updateOrganizationEntity(String type, Map<String, String> attributes, String from) throws Exception
	{
		String uid = attributes.get("@");
		if (uid == null)
			throw new IllegalStateException("@ is null");

		try
		{
			this.removeSubject(uid, from + ":updateOrganizationEntity");
		} catch (Exception ex)
		{
			throw new IllegalStateException(ex);
		}

		createOrganizationEntity(type, attributes, from);
	}

	public String createOrganizationEntity(String type, Map<String, String> attributes, String from) throws Exception
	{
		String uid = attributes.get("@");
		if (uid == null)
		{
			// это совсем новый субьект
		} else
		{

			JSONArray arg = new JSONArray();
			JSONObject one = new JSONObject();
			arg.add(one);

			one.put("@", predicates.zdb + "doc_" + uid);
			one.put(predicates._docs + "unit", predicates._query + "get");

			pacahon_client.put(ticket, arg, from + ":createOrganizationEntity");

			Iterator<Entry<String, String>> it = attributes.entrySet().iterator();

			while (it.hasNext())
			{
				Entry<String, String> ee = it.next();
				String key = ee.getKey();
				String val = ee.getValue();

				//			if ()

			}
		}
		// среди атрибутов нет идентификатора 
		if (type.equals("contact"))
		{
			return "NewOrgEntityId";
		} else if (type.equals("department"))
		{
			return "NewOrgEntityId";
		} else
		{
			throw new IllegalStateException("Incompatible type");
		}
	}

	public static void main(String[] args) throws Exception
	{
		BaOrganizationDriver drv = new BaOrganizationDriver(null);

		List<User> users = drv.getUsersByFullTextSearch("карп", "ru", false, false, "test");

		users.size();

		List<Department> deps = drv.getOrganizationRoots("ru", "test");

		Iterator<Department> it = deps.iterator();

		while (it.hasNext())
		{
			Department dd = it.next();
			drv.getUsersByDepartmentId(dd.getInternalId(), "ru", false, false, "test");

		}

	}

}
