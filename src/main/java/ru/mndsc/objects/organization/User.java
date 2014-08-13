package ru.mndsc.objects.organization;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;

import ru.mndsc.bigarch.wsclient.bl.organizationservice.AttributeType;
import ru.mndsc.bigarch.wsclient.bl.organizationservice.EntityType;

/**
 * 
 * @author SheringaA
 */
public class User implements IOrganizationEntity, Serializable
{
	private static final byte _EN = 2;
	private static final byte _NONE = 0;
	private static final byte _RU = 1;

	private static final long serialVersionUID = 6L;
	
	private static byte getLang(String ss)
	{
		if (ss.length() >= 3)
		{
			if (ss.charAt(ss.length() - 3) == '@'
					&& ss.charAt(ss.length() - 2) == 'r'
					&& ss.charAt(ss.length() - 1) == 'u')
				return _RU;
			else if (ss.charAt(ss.length() - 3) == '@'
					&& ss.charAt(ss.length() - 2) == 'e'
					&& ss.charAt(ss.length() - 1) == 'n')
				return _EN;
		}
		return _NONE;
	}

	private static String stripLang(String ss)
	{
		if (ss.length() >= 3)
		{
			if (ss.charAt(ss.length() - 3) == '@'
					&& ss.charAt(ss.length() - 2) == 'r'
					&& ss.charAt(ss.length() - 1) == 'u')
				return ss.substring(0, ss.length() - 3);
			else if (ss.charAt(ss.length() - 3) == '@'
					&& ss.charAt(ss.length() - 2) == 'e'
					&& ss.charAt(ss.length() - 1) == 'n')
				return ss.substring(0, ss.length() - 3);
		}

		return ss;
	}
	
	
	private Map<String, String> attributes = new HashMap<String, String>();
	private String currentLocale = "Ru";
	private Department department = new Department();
	private String id;
	private String name;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public User()
	{
	}

	public User(EntityType blObject, String locale)
	{
		setId(blObject.getUid());

		currentLocale = correct_locale(locale);
		
		if (blObject.getAttributes() != null)
		{
			for (AttributeType a : blObject.getAttributes().getAttributeList())
			{
				attributes.put(a.getName(), a.getValue());
			}
		}
	}

	public User(String id)
	{
		super();
		this.setId(id);
		this.setDepartment(new Department());
	}
	
	public User(String id, String firstName, String secondName, String surName,
			String position, String department, Date offlineDateBegin, Date offlineDateEnd, String employeeCategoryR3)
	{
		takeData(id, firstName, secondName, surName, position, department, offlineDateBegin, offlineDateEnd, employeeCategoryR3);
	}

	public User(String id, String domainName, String firstName,
			String lastName, String email, String post, String departmentId,
			String middleName)
	{
		super();
		this.setId(id);
		this.setLogin(domainName);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setMiddleName(middleName);
		this.setEmail(email);
		this.setPosition(post);
		this.setDepartment(new Department());
		this.getDepartment().setId(departmentId);
	}


	public User(String id, String domainName, String firstName,
			String lastName, String email, String post, String departmentId,
			String middleName, Date offlineDateBegin, Date offlineDateEnd, String employeeCategoryR3)
	{
		this(id,  domainName,  firstName,
				 lastName,  email,  post,  departmentId,
				 middleName);
		this.setOfflineDateBegin(offlineDateBegin);
		this.setOfflineDateEnd(offlineDateEnd);
		this.setEmployeeCategoryR3(employeeCategoryR3);
	}
	
	public void _set__FirstName(String nm)
	{
		byte lang = getLang(nm);
		nm = stripLang(nm);

		if (lang == _EN)
			setFirstName(nm, "En");
		if (lang == _RU || lang == _NONE)
			setFirstName(nm, "Ru");
	}
	
	public void _set__LastName(String nm)
	{
		byte lang = getLang(nm);
		nm = stripLang(nm);

		if (lang == _RU || lang == _NONE)
			setLastName(nm, "Ru");
		if (lang == _EN)
			setLastName(nm, "En");
	}

	public void _set__MiddleName(String nm)
	{
		byte lang = getLang(nm);
		nm = stripLang(nm);

		if (lang == _RU || lang == _NONE)
			setMiddleName(nm, "Ru");
		if (lang == _EN)
			setMiddleName(nm, "En");
	}

	public void _set__oFirstName(Object namez, String locale)
	{
		if (namez != null)
		{
			if (namez instanceof JSONArray)
			{
				String nm_ru = "";
				String nm_en = "";

				Iterator<String> subj_it = ((JSONArray) namez).iterator();
				while (subj_it.hasNext())
				{
					String nm = subj_it.next();
					byte lang = getLang(nm);
					nm = stripLang(nm);

					if (lang == _EN)
						nm_en = nm;
					if (lang == _RU || lang == _NONE)
						nm_ru = nm;
				}

				if (locale.equals("Ru"))
				{
					setFirstName(nm_en, "En");
					setFirstName(nm_ru, "Ru");
				} else
				{
					setFirstName(nm_ru, "Ru");
					setFirstName(nm_en, "En");
				}

			} else if (namez instanceof String)
			{
				_set__FirstName((String) namez);

			}
		}
	}

	public void _set__oLastName(Object namez, String locale)
	{
		if (namez != null)
		{
			if (namez instanceof JSONArray)
			{
				String nm_ru = "";
				String nm_en = "";

				Iterator<String> subj_it = ((JSONArray) namez).iterator();
				while (subj_it.hasNext())
				{
					String nm = subj_it.next();
					byte lang = getLang(nm);
					nm = stripLang(nm);

					if (lang == _EN)
						nm_en = nm;
					if (lang == _RU || lang == _NONE)
						nm_ru = nm;
				}
				if (locale.equals("Ru"))
				{
					setLastName(nm_en, "En");
					setLastName(nm_ru, "Ru");
				} else
				{
					setLastName(nm_ru, "Ru");
					setLastName(nm_en, "En");
				}

			} else if (namez instanceof String)
			{
				_set__LastName((String) namez);

			}
		}
	}

	public void _set__oMiddleName(Object namez, String locale)
	{
		if (namez != null)
		{
			if (namez instanceof JSONArray)
			{
				String nm_ru = "";
				String nm_en = "";

				Iterator<String> subj_it = ((JSONArray) namez).iterator();
				while (subj_it.hasNext())
				{
					String nm = subj_it.next();
					byte lang = getLang(nm);
					nm = stripLang(nm);

					if (lang == _EN)
						nm_en = nm;
					if (lang == _RU || lang == _NONE)
						nm_ru = nm;
				}
				if (locale.equals("Ru"))
				{
					setMiddleName(nm_en, "En");
					setMiddleName(nm_ru, "Ru");
				} else
				{
					setMiddleName(nm_ru, "Ru");
					setMiddleName(nm_en, "En");
				}

			} else if (namez instanceof String)
			{
				_set__MiddleName((String) namez);

			}
		}
	}

	public void _set__oName(Object valuez, String locale)
	{
		if (valuez != null)
		{
			if (valuez instanceof JSONArray)
			{
				String nm_ru = "";
				String nm_en = "";

				Iterator<String> subj_it = ((JSONArray) valuez).iterator();
				while (subj_it.hasNext())
				{
					String nm = subj_it.next();
					byte lang = getLang(nm);
					nm = stripLang(nm);

					if (lang == _EN)
						nm_en = nm;
					if (lang == _RU || lang == _NONE)
						nm_ru = nm;
				}

				setName(nm_ru);

			}
		}
	}

	public void _set__oPosition(Object valuez, String locale)
	{
		if (valuez != null)
		{
			if (valuez instanceof JSONArray)
			{
				String nm_ru = "";
				String nm_en = "";

				Iterator<String> subj_it = ((JSONArray) valuez).iterator();
				while (subj_it.hasNext())
				{
					String nm = subj_it.next();
					byte lang = getLang(nm);
					nm = stripLang(nm);

					if (lang == _EN)
						nm_en = nm;
					if (lang == _RU || lang == _NONE)
						nm_ru = nm;
				}
				if (locale.equals("Ru"))
				{
					setPosition(nm_en, "En");
					setPosition(nm_ru, "Ru");
				} else
				{
					setPosition(nm_ru, "Ru");
					setPosition(nm_en, "En");
				}

			} else if (valuez instanceof String)
			{
				_set__Position((String) valuez);

			}
		}
	}

	public void _set__Position(String nm)
	{
		byte lang = getLang(nm);
		nm = stripLang(nm);

		if (lang == _RU || lang == _NONE)
			setPosition(nm, "Ru");
		if (lang == _EN)
			setPosition(nm, "En");
	}

	public void changeLocale(String locale)
	{
		currentLocale = correct_locale(locale);
		setName(null);		
	}	
	
	
	private String correct_locale(String locale)
	{
		if (locale == "ru")
			locale = "Ru";
		if (locale == "en")
			locale = "En";
		return locale;
	}

	public boolean getActive()
	{
		return isActive();
	}
	
	public Map<String, String> getAttributes()
	{
		return attributes;
	}

	public Department getDepartment()
	{
		if (department==null) department = new Department();
		return department;
	}

	public String getDepartmentId()
	{
		return department.getId();
	}

	public String getDomainName()
	{
		return attributes.get("domainName");
	}

	public String getEmail()
	{
		return attributes.get("email");
	}

	public String getEmployeeCategoryR3() {
		return attributes.get("employeeCategoryR3");
	}

	public String getFirstName()
	{
		return getFirstName(currentLocale);
	}

	public String getFirstName(String locale)
	{
		return attributes.get("firstName" + locale);
	}

	public String getHint()
	{
		String result = "";
		result = getLastName() != null ? result += getLastName() + " " : result;
		result = getFirstName() != null ? result += getFirstName() + " " : result;
		result = getMiddleName() != null ? result += getMiddleName() + " \n" : result;
		result = getPosition() != null ? result += getPosition() + " \n" : result;
		result = getEmail() != null ? result += getEmail() + " \n" : result;
		result = getTelephone() != null ? result += getTelephone() : result;
		return result;
	}

	public String getId()
	{
		return id;
	}

	public String getInternalId()
	{
		return attributes.get("id");
	}

	public String getLastName()
	{
		return getLastName(currentLocale);
	}
	
	public String getLastName(String locale)
	{
		return attributes.get("surname"+locale);
	}

	public String getLogin()
	{
		return attributes.get("domainName");
	}

	public String getMiddleName()
	{
		return getMiddleName(currentLocale);
	}

	public String getMiddleName(String locale)
	{
		return attributes.get("secondName"+locale);
	}

	public String getName()
	{
		if (name != null)
		{
			return name;
		}
		if (getSurName() != null && getFirstName() != null
				&& getSurName().trim().length() > 0
				&& getFirstName().trim().length() > 0)
		{
			if (getSecondName() != null && getSecondName().trim().length() > 0)
			{
				name = getSurName() + " "
						+ getFirstName().trim().toUpperCase().charAt(0) + "."
						+ getSecondName().trim().toUpperCase().charAt(0) + ".";
			} else
			{
				name = getSurName() + " "
						+ getFirstName().trim().toUpperCase().charAt(0) + ".";
			}

		} else if (getPosition() != null && getPosition().trim().length() > 0)
		{
			name = getPosition();
		} else if (getLogin() != null)
		{
			name = getLogin();
		} else
		{
			name = " ";
		}
		return name;
	}

	public Date getOfflineDateBegin() {
		try {
			return sdf.parse(attributes.get("offlineDateBegin"));
		} catch (Exception e) {
			return null;
		}
	}

	public Date getOfflineDateEnd() {
		try {
			return sdf.parse(attributes.get("offlineDateEnd"));
		} catch (Exception e) {
			return null;
		}
	}

	public String getOrgName()
	{
		if (getLastName() != null && getFirstName() != null && getMiddleName() != null
				&& getLastName().trim().length() > 0
				&& getFirstName().trim().length() > 0
				&& getMiddleName().trim().length() > 0)
		{
			return getLastName() + " "
					+ getFirstName().trim().substring(1, 2).toUpperCase() + "."
					+ getMiddleName().trim().substring(1, 2).toUpperCase() + ".";
		} else
		{
			return getLogin();
		}
	}

	public Map<String, String> getOtherAttributes()
	{
		return attributes;
	}

	public String getPasswd()
	{
		return attributes.get("password");
	}

	public String getPhone()
	{
		return attributes.get("phone");
	}

	public String getPosition()
	{
		return getPosition(currentLocale);
	}
	
	public String getPosition(String locale)
	{
		return attributes.get("post"+locale);
	}

	public String getRepresentatiom()
	{
		String name = "";
		if (getLastName() != null && getFirstName() != null && getLastName().length() > 0
				&& getFirstName().length() > 0)
		{
			if (getMiddleName() != null && getMiddleName().length() > 0)
			{
				name = getLastName() + " " + getFirstName().toUpperCase().charAt(0) + "."
						+ getMiddleName().toUpperCase().charAt(0) + ".";
			} else
			{
				name = getLastName() + " " + getFirstName().toUpperCase().charAt(0) + ".";
			}
		} else if (getPosition() != null && getPosition().length() > 0)
		{
			name = getPosition();
		} else
		{
			name = getLogin();
		}
		return name;
	}

	public String getSecondName()
	{
		return getSecondName(currentLocale);
	}
	
	public String getSecondName(String locale)
	{
		return attributes.get("secondName"+locale);
	}

	public String getSurName()
	{
		return getSurName(currentLocale);
	}
	
	public String getSurName(String locale)
	{
		return attributes.get("surname"+locale);
	}

	public long getTabNomer()
	{
		try
		{
			return Long.parseLong(attributes.get("pid"));
		} catch (Exception ex)
		{
			return 0;
		}
	}

	public String getTelephone()
	{
		return getPhone();
	}

	public String getUid()
	{
		return attributes.get("@");
	}

	public boolean isActive()
	{
		return attributes.get("active").equalsIgnoreCase("true");
	}
	
	public void setActive(boolean active)
	{
		attributes.put("active", Boolean.toString(active));
	}

	public void setDepartment(Department department)
	{
		this.department = department;
		if (department.getId()!=null) {
			attributes.put("departmentId", department.getId());
		}
	}

	public void setDepartment(String department_name)
	{		
		setDepartment(department_name, currentLocale);
	}
	
	public void setDepartment(String department_name, String locale)
	{		
		this.getDepartment().setName(department_name, locale);
	}
	
	public void setDepartmentId(String departmentId)
	{
		if (department == null)
			department = new Department();

		this.department.setId(departmentId);
		attributes.put("departmentId", departmentId);
	}

	public void setDomainName(String domainName)
	{
		attributes.put("domainName", domainName);
	}


	public void setEmail(String email)
	{
		attributes.put("email", email);
	}

	public void setEmployeeCategoryR3(String employeeCategoryR3) {
		attributes.put("employeeCategoryR3",employeeCategoryR3);
	}
	
	public void setFirstName(String firstName)
	{
		setFirstName(firstName, currentLocale);
	}

	public void setFirstName(String firstName, String locale)
	{
		attributes.put("firstName" + locale, firstName);
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public void setInternalId(String internalId)
	{
		attributes.put("id", internalId);
	}

	public void setLastName(String lastName)
	{
		setLastName(lastName, currentLocale);
	}

	public void setLastName(String lastName, String locale)
	{
		attributes.put("surname" + locale, lastName);
	}

	public void setLogin(String login)
	{
		attributes.put("domainName", login);
	}

	public void setMiddleName(String middleName)
	{
		setMiddleName(middleName, currentLocale);
	}
	
	public void setMiddleName(String middleName, String locale)
	{
		attributes.put("secondName" + locale, middleName);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOfflineDateBegin(Date offlineDateBegin) {
		attributes.put("offlineDateBegin",sdf.format(offlineDateBegin));
	}

	public void setOfflineDateEnd(Date offlineDateEnd) {
		attributes.put("offlineDateEnd",sdf.format(offlineDateEnd));
	}

	public void setOtherAttributes(Map<String, String> otherAttributes)
	{
		this.attributes = otherAttributes;
	}

	public void setPasswd(String passwd)
	{
		attributes.put("password", passwd);
	}

	public void setPosition(String position)
	{
		setPosition(position, currentLocale);
	}
	
	public void setPosition(String position, String locale)
	{
		attributes.put("post" + locale, position);
	}

	public void setSecondName(String middleName)
	{
		setSecondName(middleName, currentLocale);
	}

	private void setSecondName(String middleName, String locale)
	{
		attributes.put("secondName"+locale, middleName);
	}

	public void setSurName(String lastName)
	{
		setSurName(lastName, currentLocale);
	}
	
	public void setSurName(String lastName, String locale)
	{
		attributes.put("surname"+locale, lastName);
	}

	public void setTabNomer(String tabNomer)
	{
		attributes.put("pid", tabNomer);
	}

	public void setTelephone(String telephone)
	{
		attributes.put("phone", telephone);
	}

	public void setUid(String uid)
	{
		attributes.put("@", uid);
	}

	public void takeData(String id, String firstName, String secondName,
			String surName, String position, String department, Date offlineDateBegin, Date offlineDateEnd, String employeeCategoryR3)
	{
		this.id = id;
		this.setFirstName(firstName);
		this.setMiddleName(secondName);
		this.setLastName(surName);
		this.setPosition(position);		
		if (offlineDateBegin!=null) this.setOfflineDateBegin(offlineDateBegin);
		if (offlineDateEnd!=null) this.setOfflineDateEnd(offlineDateEnd);
		this.setEmployeeCategoryR3(employeeCategoryR3);

		if (this.department != null)
			this.department.setName(department);
		else
			this.setDepartment(department);

	}

	public void takeData(User personResponse)
	{
		takeData(personResponse.id, personResponse.getFirstName(),
				personResponse.getMiddleName(), personResponse.getLastName(),
				personResponse.getPosition(),
				personResponse.department.getName(),
				personResponse.getOfflineDateBegin(), personResponse.getOfflineDateEnd(), personResponse.getEmployeeCategoryR3());
	}
}
