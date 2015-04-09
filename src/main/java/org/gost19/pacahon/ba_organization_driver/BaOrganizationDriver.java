package org.gost19.pacahon.ba_organization_driver;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.gost19.pacahon.BaDriver;
import org.gost19.pacahon.client.Predicates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ru.mndsc.objects.organization.Department;
import ru.mndsc.objects.organization.IOrganizationEntity;
import ru.mndsc.objects.organization.User;

/**
 * Менеджер пользователей.
 * 
 * @author BushenevV
 */
public class BaOrganizationDriver extends BaDriver
{
	public static final byte _ORGANIZATION = 0;
	public static final byte _GROUP = 1;
	public static final byte _DEPARTMENT = 2;
	public static final byte _USER = 3;
	public static final byte _UNKNOWN = -1;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
		return getOrganizationRoots(false, locale, from);
	}
	
	public synchronized List<Department> getOrganizationRoots(boolean withActive, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			List<Department> res = new ArrayList<Department>();

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put("a", Predicates.query__get);
			arg.put(Predicates.gost19__tag, "root");
			if (withActive == true)
				arg.put(Predicates.docs__active, "true");
			else
				arg.put(Predicates.docs__active, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getOrganizationRoots");

			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, locale, from + ":getOrganizationRoots");
				dep.getAttributes().put("active", "true");
				dep.isActive = true;
				res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}
	public synchronized List<Department> getDepartmentsByParent(Department dd, String locale, boolean withActive, String from) throws Exception {
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			List<Department> res = new ArrayList<Department>();

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put("a", Predicates.docs__unit_card);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.docs__parentUnit, dd.unit);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);

			if (withActive == true)
				arg.put(Predicates.docs__active, "true");
			else
				arg.put(Predicates.docs__active, Predicates.query__get);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentsByParentId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentsByParentId");

				if (withActive == true)
					dep.getAttributes().put("active", "true");

				if (dep != null)
					res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}
	}

	
	public synchronized List<Department> getDepartmentsByParentId(String parentExtId, String locale, boolean withActive, String from)
			throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			//			String departmentId = null;
			Department dd = getDepartmentByExtId(parentExtId, locale, from + ":getDepartmentsByParentId");

			//			departmentId = dd.getId();

			List<Department> res = new ArrayList<Department>();

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put("a", Predicates.docs__unit_card);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.docs__parentUnit, dd.unit);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);

			if (withActive == true)
				arg.put(Predicates.docs__active, "true");
			else
				arg.put(Predicates.docs__active, Predicates.query__get);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentsByParentId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentsByParentId");

				if (withActive == true)
					dep.getAttributes().put("active", "true");

				if (dep != null)
					res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}

	public synchronized List<User> getUsersByDepartmentId(String departmentExtId, String locale, boolean withEmail, boolean withActive,
			String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			List<User> res = new ArrayList<User>();

			Department dd = getDepartmentByExtId(departmentExtId, locale, from + ":getUsersByDepartmentId");
			if (dd == null)
				return res;

			//			departmentId = dd.getId();

			// выберем нижеперечисленные предикаты из субьекта с заданными uids

			// gost19:department
			// firstName->swrc:firstName@[localeName]
			// surname->swrc:middlename@[localeName]
			// domainName->docs:login
			// email->swrc:email
			// post->docs:position@[localeName]
			// secondName->swrc:lastName@[localeName]

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put("a", Predicates.docs__employee_card);

			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.swrc__firstName, Predicates.query__get);
			arg.put(Predicates.swrc__lastName, Predicates.query__get);
			arg.put(Predicates.gost19__middleName, Predicates.query__get);
			arg.put(Predicates.auth__login, Predicates.query__get);
			arg.put(Predicates.swrc__email, Predicates.query__get);
			arg.put(Predicates.docs__position, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);
			arg.put(Predicates.gost19__internal_phone, Predicates.query__get);
			arg.put(Predicates.swrc__phone, Predicates.query__get);
			arg.put(Predicates.gost19__work_mobile, Predicates.query__get);
			arg.put(Predicates.gost19__mobile, Predicates.query__get);			
			arg.put(Predicates.gost19__offlineDateBegin, Predicates.query__get);
			arg.put(Predicates.gost19__offlineDateEnd, Predicates.query__get);
			arg.put(Predicates.gost19__employeeCategoryR3, Predicates.query__get);
			arg.put(Predicates.docs + "parentUnit", dd.unit);

			JSONArray aa = new JSONArray();
			if (withActive == true)
				aa.add("true");
			aa.add(Predicates.query__get);
			arg.put(Predicates.docs__active, aa);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getUsersByDepartmentId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				User usr = getUserFromGraph(ss, null, locale, true);
				if (usr != null)
				{
					usr.setDepartment(dd);

					if (withActive == true)
						usr.getAttributes().put("active", "true");

					res.add(usr);
				}
			}
			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}

	}

	public synchronized List<User> getFullUsersByDepartmentId(String departmentExtId, String locale, String from, boolean withActive)
			throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			//			String departmentId = null;

			Department dd = getDepartmentByExtId(departmentExtId, locale, from + ":getFullUsersByDepartmentId");

			//			departmentId = dd.getId();

			List<User> res = new ArrayList<User>();

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put("a", Predicates.docs__employee_card);

			if (withActive == true)
				arg.put(Predicates.docs__active, "true");

			arg.put(Predicates.docs__parentUnit, dd.unit);
			arg.put(Predicates.query__all_predicates, "query:get");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getFullUsersByDepartmentId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				User usr = getUserFromGraph(ss, null, locale, true);
				if (usr != null)
				{
					if (usr.getId().equals("a43e5522-60d5-4ae6-be31-b52b8db223b6"))
					{
						usr.setDepartment(dd);
					}
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

	public synchronized Department getDepartmentByUid(String uid, String locale, String from) throws Exception
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

			arg.put("@", Predicates.zdb + "doc_" + uid);
			JSONArray aa = new JSONArray();
			aa.add(Predicates.docs__department_card);
			aa.add(Predicates.query__get);
			arg.put("a", aa);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			arg.put(Predicates.docs__parentUnit, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);
			arg.put(Predicates.swrc__organization, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);
			arg.put(Predicates.docs__active, Predicates.query__get);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentByUid");
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

	public synchronized List<Department> getDepartmentsByName(String words, String locale, boolean withActive, String from) throws Exception
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

			arg.put("@", Predicates.query__any);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			arg.put(Predicates.query__fulltext, str_tokens.toString());
			arg.put("a", Predicates.docs__unit_card);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);

			if (withActive == true)
				arg.put(Predicates.docs__active, "true");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentsByName (FT)");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				Department dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentsByName (FT)");
				if (withActive == true)
					dep.getAttributes().put("active", "true");
				res.add(dep);
			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}

	}

	public synchronized List<Department> getAllDepartments(boolean withActive, String from) throws Exception
	{
		recheck_ticket();

		try
		{
			List<Department> res = new ArrayList<Department>();

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			JSONArray aa = new JSONArray();
			aa.add(Predicates.docs__unit_card);
			aa.add(Predicates.query__get);
			arg.put("a", aa);
			arg.put(Predicates.docs__parentUnit, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);

			if (withActive == true)
				arg.put(Predicates.docs__active, "true");

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getAllDepartments");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				try
				{
					Department dep = getDepartmentFromGraph(ss, "ru", from + ":getAllDepartments");
					if (withActive == true)
						dep.getAttributes().put("active", "true");
					res.add(dep);

				} catch (Exception ex)
				{
					System.out.println("Cannot get department : " + ss);
					ex.printStackTrace();
				}

			}

			return res;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get all departments", ex);
		}

	}

	public synchronized JSONArray getAsJSONArray(String uid, String from) throws Exception
	{
		recheck_ticket();

		if (uid == null)
			throw new Exception("uid is null");

		try
		{
			Department dep = null;

			JSONObject arg = new JSONObject();

			arg.put("@", uid);
			arg.put(Predicates.query__all_predicates, Predicates.query__get_reifed);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getAsJSONArray");
			return result;
		} catch (Exception ex)
		{
			throw new Exception("Cannot getAsJSONArray", ex);
		}
	} // end getDepartmentByUid()

	public synchronized Department getDepartmentByExtId(String externalIdentifer, String locale, String from) throws Exception
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

			arg.put("@", Predicates.query__any);
			JSONArray aa = new JSONArray();
			aa.add(Predicates.docs__department_card);
			aa.add(Predicates.query__get);
			arg.put("a", aa);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			arg.put(Predicates.docs__parentUnit, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.swrc__organization, Predicates.query__get);
			arg.put(Predicates.gost19__externalIdentifer, externalIdentifer);
			arg.put(Predicates.docs__unit, Predicates.query__get);
			arg.put(Predicates.docs__active, Predicates.query__get);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentByExtId");
			Iterator<JSONObject> subj_it = result.iterator();
			while (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				dep = getDepartmentFromGraph(ss, locale, from + ":getDepartmentByExtId");
				if (dep != null)
					dep.setInternalId(externalIdentifer);
			}

			return dep;
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

	public synchronized String getDepartmentUidByUserUid(String uid, String from) throws Exception
	{
		recheck_ticket();
		try
		{
			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// gost19:department

			String res = null;

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.zdb + "doc_" + uid);
			arg.put(Predicates.docs__parentUnit, Predicates.query__get);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentUidByUserUid");
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				String val = (String) ss.get(Predicates.docs__parentUnit);
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
	public synchronized String getUserUidByLoginInternal(String login, String from) throws Exception
	{
		if (login == null || login.length() < 1)
			return null;

		recheck_ticket();
		try
		{
			// выберем нижеперечисленные предикаты из субьекта с заданным uid
			// gost19:department

			String res = null;

			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put(Predicates.swrc__name, Predicates.query__get);
			arg.put(Predicates.auth__login, login.toUpperCase());
			arg.put(Predicates.docs__active, "true");

			arg.put("a", Predicates.query__get);

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

	public synchronized User getUserByLogin(String login, String locale, String from) throws Exception
	{
		return getUserByLogin(login, locale, from, true);
	}
	
	public synchronized User getUserByLogin(String login, String locale, String from, boolean active) throws Exception
	{
		if (login == null || login.length() < 1)
			return null;

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

			arg.put("@", Predicates.query__any);
			arg.put(Predicates.swrc__firstName, Predicates.query__get);
			arg.put(Predicates.swrc__lastName, Predicates.query__get);
			arg.put(Predicates.gost19__middleName, Predicates.query__get);
			arg.put(Predicates.swrc__email, Predicates.query__get);
			arg.put(Predicates.docs__position, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);
			arg.put(Predicates.auth__login, login.toUpperCase());
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.gost19__internal_phone, Predicates.query__get);
			arg.put(Predicates.swrc__phone, Predicates.query__get);
			arg.put(Predicates.gost19__work_mobile, Predicates.query__get);
			arg.put(Predicates.gost19__mobile, Predicates.query__get);
			arg.put(Predicates.gost19__offlineDateBegin, Predicates.query__get);
			arg.put(Predicates.gost19__offlineDateEnd, Predicates.query__get);
			arg.put(Predicates.gost19__employeeCategoryR3, Predicates.query__get);
			arg.put(Predicates.docs__parentUnit, Predicates.query__get);
			if (active) {
				arg.put(Predicates.docs__active, "true");
			}
			arg.put(Predicates.auth__credential, Predicates.query__get);
			arg.put("a", Predicates.docs__employee_card);

			User usr = null;
			JSONArray result = pacahon_client.get(ticket, arg, from + ":getUserByLogin");
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				usr = getUserFromGraph(ss, null, locale, true);
				usr.getAttributes().put("active", "true");
				usr.setLogin(login);
			}
			return usr;
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get user", ex);
		}
	}

	public synchronized List<User> getUsersByFullTextSearch(String words, String locale, boolean withEmail, boolean withActive, String from)
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

			arg.put("@", Predicates.query__any);
			arg.put(Predicates.swrc__firstName, Predicates.query__get);
			arg.put(Predicates.swrc__lastName, Predicates.query__get);
			arg.put(Predicates.gost19__middleName, Predicates.query__get);
			arg.put(Predicates.auth__login, Predicates.query__get);
			arg.put(Predicates.swrc__email, Predicates.query__get);
			arg.put(Predicates.docs__position, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get_reifed);
			arg.put(Predicates.gost19__internal_phone, Predicates.query__get);
			arg.put(Predicates.swrc__phone, Predicates.query__get);
			arg.put(Predicates.gost19__work_mobile, Predicates.query__get);
			arg.put(Predicates.gost19__mobile, Predicates.query__get);
			arg.put(Predicates.gost19__offlineDateBegin, Predicates.query__get);
			arg.put(Predicates.gost19__offlineDateEnd, Predicates.query__get);
			arg.put(Predicates.gost19__employeeCategoryR3, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.docs__parentUnit, Predicates.query__get_reifed);
			arg.put(Predicates.query__fulltext, str_tokens.toString());
			arg.put("a", Predicates.docs__employee_card);

			if (withActive == true)
				arg.put(Predicates.docs__active, "true");

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
				{
					if (withActive == true)
						usr.getAttributes().put("active", "true");

					if (withEmail == true)
					{
						if (usr.getLogin() != null && usr.getLogin().length() > 1)
							res.put(usr.getId(), usr);
					} else
						res.put(usr.getId(), usr);
				}
			}
			return new ArrayList<User>(res.values());

		} catch (Exception ex)
		{
			throw new Exception("Cannot get users", ex);
		}
	}

	public synchronized List<User> getUsersByUids(Collection<String> ids, String locale, String from) throws Exception
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
			arg.put(Predicates.swrc__firstName, Predicates.query__get);
			arg.put(Predicates.swrc__lastName, Predicates.query__get);
			arg.put(Predicates.gost19__middleName, Predicates.query__get);
			arg.put(Predicates.auth__login, Predicates.query__get);
			arg.put(Predicates.swrc__email, Predicates.query__get);
			arg.put(Predicates.docs__position, Predicates.query__get);
			arg.put(Predicates.docs__unit, Predicates.query__get);
			arg.put(Predicates.gost19__internal_phone, Predicates.query__get);
			arg.put(Predicates.swrc__phone, Predicates.query__get);
			arg.put(Predicates.gost19__work_mobile, Predicates.query__get);
			arg.put(Predicates.gost19__mobile, Predicates.query__get);
			arg.put(Predicates.gost19__offlineDateBegin, Predicates.query__get);
			arg.put(Predicates.gost19__offlineDateEnd, Predicates.query__get);
			arg.put(Predicates.gost19__employeeCategoryR3, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);
			arg.put(Predicates.docs__parentUnit, Predicates.query__get);
			arg.put(Predicates.gost19__externalIdentifer, Predicates.query__get);

			arg.put("a", Predicates.docs__employee_card);

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
	public synchronized IOrganizationEntity selectUnitByUidInternal(String uid, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.zdb + "doc_" + uid);
			arg.put(Predicates.query__all_predicates, Predicates.query__get);

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
			throw new Exception("ex! selectUnitByUidInternal", ex);
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
	public synchronized User selectUserByUidInternal(String uid, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.zdb + "doc_" + uid);
			arg.put(Predicates.query__all_predicates, Predicates.query__get);
			arg.put("a", Predicates.docs__employee_card);
			//			arg.put(Predicates.swrc__firstName, Predicates.query__get);
			//			arg.put(Predicates.swrc__lastName, Predicates.query__get);
			//			arg.put(Predicates.gost19__middleName, Predicates.query__get);
			//			arg.put(Predicates.auth__login, Predicates.query__get);
			//			arg.put(Predicates.swrc__email, Predicates.query__get);
			//			arg.put(Predicates.docs__position, Predicates.query__get);
			//			arg.put(Predicates.docs__unit, Predicates.query__get);
			//			arg.put(Predicates.gost19__synchronize, Predicates.query__get);

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

	/**
	 * Найти пользователя по уникальному идентификатору.
	 * 
	 * @param uid
	 *            уникальный идентификатор
	 * @param localeName
	 *            имя локали
	 * @return пользователь
	 */
	public synchronized User selectUserByPid(String pid, String locale, String from) throws Exception
	{
		recheck_ticket();
		locale = correct_locale(locale);

		try
		{
			JSONObject arg = new JSONObject();

			arg.put("@", Predicates.query__any);
			arg.put(Predicates.query__all_predicates, Predicates.query__get);
			arg.put("a", Predicates.docs__employee_card);
			arg.put(Predicates.gost19__externalIdentifer, pid);

			User usr = null;
			JSONArray result = pacahon_client.get(ticket, arg, from + ":selectUserByPid");
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				usr = getUserFromGraph(ss, null, locale, true);
			}

			return usr;
		} catch (Exception ex)
		{
			throw new Exception("ex! selectUserByPid", ex);
		}
	} // end selectUserByPid()

	public synchronized Department getDepartmentByUserUid(String uid, String locale, String from) throws Exception
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

			arg.put("@", Predicates.zdb + "doc_" + uid);
			arg.put(Predicates.docs__unit, Predicates.query__get);
			arg.put(Predicates.gost19__synchronize, Predicates.query__get);

			JSONArray result = pacahon_client.get(ticket, arg, from + ":getDepartmentByUserUid");
			Iterator<JSONObject> subj_it = result.iterator();
			if (subj_it.hasNext())
			{
				JSONObject ss = subj_it.next();
				String val = (String) ss.get(Predicates.docs__unit);
				if (val != null)
				{
					val = val.substring("zdb:dep_".length(), val.length());
					department = val;
				}
				return getDepartmentByUid(department, locale, from + ":getDepartmentByUserUid");
			}

			return null;
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get department", ex);
		}
	}

	private byte getType(Object rdf_type)
	{
		if (rdf_type == null)
			return _UNKNOWN;

		if (rdf_type instanceof JSONArray)
		{
			JSONArray rdf_type_array = (JSONArray) rdf_type;

			for (int i = 0; i < rdf_type_array.size(); i++)
			{
				String a = (String) rdf_type_array.get(i);
				if (a.equals("docs:employee_card"))
				{
					return _USER;
				} else if (a.equals("docs:department_card"))
				{
					return _DEPARTMENT;
				} else if (a.equals("docs:organization_card"))
				{
					return _ORGANIZATION;
				} else if (a.equals("docs:organization_card"))
				{
					return _GROUP;
				}

			}
		} else if (rdf_type instanceof String)
		{
			String a = (String) rdf_type;
			if (a.equals("docs:employee_card"))
			{
				return _USER;
			} else if (a.equals("docs:department_card"))
			{
				return _DEPARTMENT;
			} else if (a.equals("docs:organization_card"))
			{
				return _ORGANIZATION;
			} else if (a.equals("docs:organization_card"))
			{
				return _GROUP;
			}
		}

		return _UNKNOWN;
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

	String doc_prefix = "doc_";

	private Department getDepartmentFromGraph(JSONObject oo, String locale, String from)
	{
		Object rdf_type = oo.get("a");

		byte type = getType(rdf_type);

		if (type != _DEPARTMENT && type != _ORGANIZATION && type == _GROUP)
			return null;

		recheck_ticket();
		locale = correct_locale(locale);

		Department dep = new Department();

		dep.type = type;

		String id = (String) oo.get("@");
		dep.uid = id;
		id = id.substring(id.indexOf(doc_prefix) + doc_prefix.length(), id.length());
		dep.setId(id);

		Object namez = oo.get(Predicates.swrc__name);

		if (namez != null)
		{
			if (namez instanceof JSONArray)
			{
				Iterator<String> subj_it = ((JSONArray) namez).iterator();
				while (subj_it.hasNext())
				{
					String nm = subj_it.next();
					dep.set__Name(nm);
					dep.getAttributes().put("name", nm);
				}

			} else if (namez instanceof String)
			{
				dep.set__Name((String) namez);
				dep.getAttributes().put("name", (String) namez);
			}
		}

		String parentDepartment = (String) oo.get(Predicates.docs__parentUnit);

		if (parentDepartment != null)
		{
			String val = parentDepartment.substring(parentDepartment.indexOf("_") + 1, parentDepartment.length());
			dep.setParentDepartmentId(val);
			dep.getAttributes().put("parentId", val);
		}

		Object valuez = oo.get(Predicates.gost19__synchronize);
		if (valuez != null)
			dep.getAttributes().put("doNotSynchronize", "1");
		else
			dep.getAttributes().put("doNotSynchronize", "0");

		String externalIdentifer = (String) oo.get(Predicates.gost19__externalIdentifer);
		if (externalIdentifer != null)
		{
			dep.setInternalId(externalIdentifer);
			dep.getAttributes().put("id", dep.getInternalId());
		}

		String organization = (String) oo.get(Predicates.swrc__organization);
		if (organization != null)
		{
			dep.getAttributes().put(Predicates.swrc__organization, organization);
		}

		String unit = (String) oo.get(Predicates.docs__unit);
		if (unit != null)
		{
			dep.getAttributes().put(Predicates.docs__unit, unit);
			dep.unit = unit;
		}

		dep.getAttributes().put("@", dep.uid);

		if (rdf_type != null)
			dep.getAttributes().put("a", rdf_type.toString());

		valuez = oo.get(Predicates.docs__active);
		if (valuez != null)
		{
			dep.getAttributes().put("active", (String) valuez);
			if (valuez.equals("true"))
				dep.isActive = true;
		}

		return dep;
	}

	private User getUserFromGraph(JSONObject oo, HashMap<String, User> users, String locale, boolean isUser)
	{
		String uid;
		Object rdf_type = oo.get("a");

		if (isUser == false)
		{
			isUser = isUser(rdf_type);
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
		usr.setUid(uid);

		usr._set__oFirstName(oo.get(Predicates.swrc__firstName), locale);
		usr._set__oLastName(oo.get(Predicates.swrc__lastName), locale);
		usr._set__oMiddleName(oo.get(Predicates.gost19__middleName), locale);
		usr._set__oPosition(oo.get(Predicates.docs__position), locale);

		Object valuez = oo.get(Predicates.auth__login);	
		if (valuez != null)
		{
			usr.setDomainName((String)valuez);
		}
		
		valuez = oo.get(Predicates.auth__credential);
		if (valuez != null)
		{
			usr.setPasswd((String) valuez);
		}

		valuez = oo.get(Predicates.swrc__email);
		if (valuez != null)
		{
			usr.setEmail((String) valuez);
		}

		valuez = oo.get(Predicates.gost19__synchronize);
		if (valuez != null)
			usr.getAttributes().put("doNotSynchronize", "1");
		else
			usr.getAttributes().put("doNotSynchronize", "0");

		valuez = oo.get(Predicates.gost19__internal_phone);
		if (valuez != null)
		{
			usr.setTelephone((String) valuez);
		}

		valuez = oo.get(Predicates.docs__active);
		if (valuez != null)
		{
			if (((String) valuez).equalsIgnoreCase("true"))
				usr.setActive(true);
			else
				usr.setActive(false);
		}

		valuez = oo.get(Predicates.gost19__mobile);
		if (valuez != null)
		{
			usr.getAttributes().put("mobilePrivate", (String) valuez);
		}

		valuez = oo.get(Predicates.gost19__offlineDateBegin);
		if (valuez != null)
		{
			try {
				usr.setOfflineDateBegin(sdf.parse((String) valuez));
			} catch (ParseException e) {
				usr.setOfflineDateBegin(null);
			}
		}

		valuez = oo.get(Predicates.gost19__offlineDateEnd);
		if (valuez != null)
		{
			try {
				usr.setOfflineDateEnd(sdf.parse((String) valuez));
			} catch (ParseException e) {
				usr.setOfflineDateEnd(null);
			}
		}

		valuez = oo.get(Predicates.gost19__employeeCategoryR3);
		if (valuez != null)
		{
			usr.setEmployeeCategoryR3((String) valuez);
		}

		valuez = oo.get(Predicates.swrc__phone);
		if (valuez != null)
		{
			usr.getAttributes().put("phoneExt", (String) valuez);
		}

		valuez = oo.get(Predicates.gost19__work_mobile);
		if (valuez != null)
		{
			usr.getAttributes().put("mobile", (String) valuez);
		}

		String parentDepartment = (String) oo.get(Predicates.docs__parentUnit);

		if (parentDepartment != null)
		{
			String val = parentDepartment.substring(parentDepartment.indexOf("_") + 1, parentDepartment.length());
			usr.setDepartmentId(val);
		}

		String externalIdentifer = (String) oo.get(Predicates.gost19__externalIdentifer);
		if (externalIdentifer != null)
		{
			usr.setTabNomer(externalIdentifer);
		}

		if (rdf_type != null)
			usr.getAttributes().put("a", rdf_type.toString());

		return usr;
	}

	private void updateUserReifedData(JSONObject oo, HashMap<String, User> users)
	{
		String subject = (String) oo.get(Predicates.rdf__subject);
		String id = subject.substring("zdb:doc_".length(), subject.length());
		User user = users.get(id);
		if (user == null)
		{
			user = new User();
			user.setId(id);
			users.put(id, user);
		}

		String predicate = (String) oo.get(Predicates.rdf__predicate);
		if (predicate.equals(Predicates.docs__parentUnit))
		{
			Department dep = new Department();
			Object namez = oo.get(Predicates.swrc__name);

			if (namez != null)
			{
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
			}

			user.setDepartment(dep);
		}
		// это уже излишне для данного документа, так как в карточке
		// пользователя содержится только одно подразделение
	}

	/**
	 * Calculates SHA from string. Returns base64-ed string.
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String calculateHash(String password) throws Exception
	{
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		sha.update(password.getBytes("utf8"));
		return new String(Base64.encodeBase64(sha.digest()));
	}
	public synchronized void removeOrganizationEntity(String type, Map<String, String> attributes, String from) throws Exception
	{
		attributes.put(Predicates.docs__active, "false");
	    updateOrganizationEntity(type, attributes, from);
	}
	public synchronized void updateOrganizationEntity(String type, Map<String, String> attributes, String from) throws Exception
	{
		String uid = attributes.get("@");

		if (uid == null)
			throw new Exception("uid is null");

		// считать предыдущие данные
		JSONArray exist_data = getAsJSONArray(uid, from + ":updateOrganizationEntity");

		HashMap<String, JSONObject> reif_data = new HashMap<String, JSONObject>();

		JSONObject base = null;
		for (Object oo : exist_data)
		{
			JSONObject iy = (JSONObject) oo;

			if (iy.containsValue(Predicates.rdf__Statement) == true)
			{
				String reif_key = iy.get(Predicates.rdf__subject).hashCode() + "" + iy.get(Predicates.rdf__predicate).hashCode()
						+ "" + iy.get(Predicates.rdf__object).hashCode();
				reif_data.put(reif_key, iy);
			} else
			{
				base = iy;
			}
		}

		JSONArray arg = new JSONArray();

		// обновим изменившиеся аттрибуты
		String parentDepartmentId = null;

		if (type.equals("contact"))
			parentDepartmentId = attributes.get("departmentId");
		else
			parentDepartmentId = attributes.get("parentId");

		Department parent_dep = null;

		if (parentDepartmentId!=null && parentDepartmentId.length() == 36)
			parent_dep = getDepartmentByUid(parentDepartmentId, "Ru", from + ":updateOrganizationEntity");
		else
			parent_dep = getDepartmentByExtId(parentDepartmentId, "Ru", from + ":updateOrganizationEntity");

		if (parent_dep != null)
		{
			String org = parent_dep.getAttributes().get(Predicates.swrc__organization);
			if (org != null)
				base.put(Predicates.swrc__organization, org);

			if (parent_dep.unit == null) {
				parent_dep.unit = "zdb:dep_" + parent_dep.getId(); 
			}
			
			String parent_untit = parent_dep.unit;

			JSONObject parent_reif_data = generate_reifed_data(uid, Predicates.docs__parentUnit, parent_untit, parent_dep);
			arg.add(parent_reif_data);

			
			String val = attributes.get("doNotSynchronize");
			if (val != null && val.equals("1"))
				base.put(Predicates.gost19__synchronize, "none");
			else
				base.remove(Predicates.gost19__synchronize);

			//		base.remove(Predicates.docs__parentUnit);
			base.put(Predicates.docs__parentUnit, parent_untit);
		}

		if (type.equals("contact"))
		{
			add_att("pid", attributes, Predicates.gost19__externalIdentifer, base);

			add_lang_att("firstName", attributes, Predicates.swrc__firstName, base);
			add_lang_att("secondName", attributes, Predicates.gost19__middleName, base);
			add_lang_att("surname", attributes, Predicates.swrc__lastName, base);
			add_lang_att("post", attributes, Predicates.docs__position, base);

			String val = attributes.get("domainName");
			if (val != null)
				base.put(Predicates.auth__login, val.toUpperCase());

			val = attributes.get("doNotSynchronize");
			if (val != null && val.equals("1"))
				base.put(Predicates.gost19__synchronize, "none");
			else
				base.remove(Predicates.gost19__synchronize);
			
			val = attributes.get("password");
			if (val != null && val.length() > 3)
			{
				if (val.indexOf('*') < 0)
				{
					val = calculateHash (val);
					base.put(Predicates.auth__credential, val);
				}
			}

			add_att("phone", attributes, Predicates.gost19__internal_phone, base);
			add_att("phoneExt", attributes, Predicates.swrc__phone, base);
			add_att("email", attributes, Predicates.swrc__email, base);
			add_att("mobile", attributes, Predicates.gost19__work_mobile, base);
			add_att("offlineDateBegin", attributes, Predicates.gost19__offlineDateBegin, base);
			add_att("offlineDateEnd", attributes, Predicates.gost19__offlineDateEnd, base);
			add_att("employeeCategoryR3", attributes, Predicates.gost19__employeeCategoryR3, base);
			//			add_att ("departmentId", attributes, Predicates.docs__parentUnit);
		} else
		{
			add_lang_att("name", attributes, Predicates.swrc__name, base);
			add_att("id", attributes, Predicates.gost19__externalIdentifer, base);
		}

		add_att("active", attributes, Predicates.docs__active, base);

		arg.add(base);

		try
		{
			this.removeSubject(uid, from + ":updateOrganizationEntity");
			pacahon_client.put(ticket, arg, from + ":createOrganizationEntity");
		} catch (Exception ex)
		{
			throw new IllegalStateException(ex);
		}

	}

	private void add_att(String att_name, Map<String, String> attributes, String predicate, JSONObject dest)
	{
		String val = attributes.get(att_name);

		if (val != null)
			dest.put(predicate, val);
	}

	private void add_lang_att(String att_name, Map<String, String> attributes, String predicate, JSONObject dest)
	{
		String valRu = attributes.get(att_name + "Ru");
		String valEn = attributes.get(att_name + "En");

		if (valRu != null && valRu.endsWith("@ru") == false)
			valRu += "@ru";

		if (valEn != null && valEn.endsWith("@en") == false)
			valEn += "@en";

		if (valRu != null && valEn != null)
		{
			JSONArray namez = new JSONArray();
			namez.add(valRu);
			namez.add(valEn);
			dest.put(predicate, namez);
		} else
		{
			if (valRu != null)
				dest.put(predicate, valRu);
			else if (valEn != null)
				dest.put(predicate, valEn);
		}

	}

	public synchronized void createOrganizationEntity(String type, Map<String, String> attributes, String from, String _uid) throws Exception
	{
		String parentDepartmentId = null;
		if (type.equals("contact"))
			parentDepartmentId = attributes.get("departmentId");
		else
			parentDepartmentId = attributes.get("parentId");

		Department parent_dep = null;

		if (parentDepartmentId != null)
		{
			if (parentDepartmentId.length() == 36)
				parent_dep = getDepartmentByUid(parentDepartmentId, "Ru", from + ":createOrganizationEntity");
			else
				parent_dep = getDepartmentByExtId(parentDepartmentId, "Ru", from + ":createOrganizationEntity");

			if (parent_dep == null)
			{
				// такого подразделения еще нет в базе !!! что делать?
				throw new Exception("хозяин!, родительского подразделения [" + parentDepartmentId
						+ "] еще нет в базе !!! не знаю что делать?");
			}
		}

		String id = null;
		String uid = null;

		if (_uid != null)
			id = _uid;
		else
			id = UUID.randomUUID().toString();

		uid = "zdb:doc_" + id;

		JSONArray arg = new JSONArray();
		JSONObject base = new JSONObject();

		if (parent_dep != null)
		{
			String org = parent_dep.getAttributes().get(Predicates.swrc__organization);
			if (org != null)
				base.put(Predicates.swrc__organization, org);

			if (parent_dep.unit == null) {
				parent_dep.unit = "zdb:dep_" + parent_dep.getId(); 
			}
			
			base.put(Predicates.docs__parentUnit, parent_dep.unit);

			JSONObject dep_info = get_reif_subject(uid, Predicates.docs__parentUnit, parent_dep.unit);

			add_lang_att("name", attributes, Predicates.swrc__name, dep_info);

			arg.add(dep_info);
		}

		base.put("@", uid);
		arg.add(base);

		if (type.equals("contact"))
		{
			JSONArray rdf_type_content = new JSONArray();

			rdf_type_content.add(Predicates.auth__Authenticated);
			rdf_type_content.add(Predicates.docs__employee_card);
			base.put("a", rdf_type_content);

			add_att("pid", attributes, Predicates.gost19__externalIdentifer, base);

			add_lang_att("firstName", attributes, Predicates.swrc__firstName, base);
			add_lang_att("secondName", attributes, Predicates.gost19__middleName, base);
			add_lang_att("surname", attributes, Predicates.swrc__lastName, base);
			add_lang_att("post", attributes, Predicates.docs__position, base);

			add_att("phone", attributes, Predicates.gost19__internal_phone, base);
			add_att("phoneExt", attributes, Predicates.swrc__phone, base);
			add_att("email", attributes, Predicates.swrc__email, base);
			add_att("mobile", attributes, Predicates.gost19__work_mobile, base);
			add_att("offlineDateBegin", attributes, Predicates.gost19__offlineDateBegin, base);
			add_att("offlineDateEnd", attributes, Predicates.gost19__offlineDateEnd, base);
			add_att("employeeCategoryR3", attributes, Predicates.gost19__employeeCategoryR3, base);

			String val = attributes.get("domainName");
			if (val != null)
				base.put(Predicates.auth__login, val.toUpperCase());

			base.put(Predicates.docs__unit, "zdb:person_" + id);
		} else
		{
			JSONArray rdf_type_content = new JSONArray();
			rdf_type_content.add(Predicates.docs__unit_card);
			rdf_type_content.add(Predicates.docs__department_card);
			base.put("a", rdf_type_content);
			base.put(Predicates.docs__active, "true");
			base.put(Predicates.gost19__externalIdentifer, attributes.get("id"));

			base.put(Predicates.docs__unit, "zdb:dep_" + id);

			add_lang_att("name", attributes, Predicates.swrc__name, base);
		}

		add_att("active", attributes, Predicates.docs__active, base);

		pacahon_client.put(ticket, arg, from + ":createOrganizationEntity");

	}

	private JSONObject generate_reifed_data(String subject, String predicate, String object, Department dep)
	{
		JSONObject dep_info = get_reif_subject(subject, predicate, object);

		add_lang_att("name", dep.getAttributes(), Predicates.swrc__name, dep_info);

		return dep_info;
	}

	private JSONObject get_reif_subject(String s_target, String p_target, String o_target)
	{
		if (s_target==null || p_target==null || o_target==null) {
			System.out.println("!!!");
		}
		String addinfo_subject = Predicates.gost19 + "add_info_" + s_target.hashCode() + "" + p_target.hashCode() + ""
				+ o_target.hashCode();

		JSONObject one = new JSONObject();
		one.put("@", addinfo_subject);

		one.put(Predicates.rdf__type, Predicates.rdf__Statement);
		one.put(Predicates.rdf__subject, s_target);
		one.put(Predicates.rdf__predicate, p_target);
		one.put(Predicates.rdf__object, o_target);

		return one;
	}

	public static void main(String[] args) throws Exception
	{
		BaOrganizationDriver drv = new BaOrganizationDriver(null);

		List<User> users = drv.getUsersByFullTextSearch("карп", "ru", false, false, "test");

		users.size();

		List<Department> deps = drv.getOrganizationRoots(true, "ru", "test");

		Iterator<Department> it = deps.iterator();

		while (it.hasNext())
		{
			Department dd = it.next();
			drv.getUsersByDepartmentId(dd.getInternalId(), "ru", false, false, "test");

		}

	}

}
