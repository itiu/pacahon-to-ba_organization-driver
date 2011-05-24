package ru.magnetosoft.objects.organization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;

import ru.magnetosoft.bigarch.wsclient.bl.organizationservice.AttributeType;
import ru.magnetosoft.bigarch.wsclient.bl.organizationservice.EntityType;

/**
 * 
 * @author SheringaA
 */
public class User implements IOrganizationEntity, Serializable
{
	private static final byte _NONE = 0;
	private static final byte _RU = 1;
	private static final byte _EN = 2;

	private static byte getLang(String ss)
	{
		if (ss.length() >= 3)
		{
			if (ss.charAt(ss.length() - 3) == '@' && ss.charAt(ss.length() - 2) == 'r'
					&& ss.charAt(ss.length() - 1) == 'u')
				return _RU;
			else if (ss.charAt(ss.length() - 3) == '@' && ss.charAt(ss.length() - 2) == 'e'
					&& ss.charAt(ss.length() - 1) == 'n')
				return _EN;
		}
		return _NONE;
	}

	private static String stripLang(String ss)
	{
		if (ss.length() >= 3)
		{
			if (ss.charAt(ss.length() - 3) == '@' && ss.charAt(ss.length() - 2) == 'r'
					&& ss.charAt(ss.length() - 1) == 'u')
				return ss.substring(0, ss.length() - 3);
			else if (ss.charAt(ss.length() - 3) == '@' && ss.charAt(ss.length() - 2) == 'e'
					&& ss.charAt(ss.length() - 1) == 'n')
				return ss.substring(0, ss.length() - 3);
		}

		return ss;
	}

	private static final long serialVersionUID = 6L;
	private String id;
	private String name;
	private String firstName;
	private String middleName;
	private String lastName;
	private String position;
	private Department department;
	private String email;
	private String login;
	private long tabNomer;
	private String telephone;
	private String internalId;
	private boolean active;
	private String department_name;

	private Map<String, String> attributes = new HashMap<String, String>();

	public User()
	{
	}

	public User(String id, String firstName, String secondName, String surName, String position, String department)
	{
		takeData(id, firstName, secondName, surName, position, department);
	}

	public User(String id, String domainName, String firstName, String lastName, String email, String post,
			String departmentId, String middleName)
	{
		super();
		this.id = id;
		this.login = domainName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.email = email;
		this.position = post;
		this.department = new Department();
		this.department.setId(departmentId);
	}

	public User(String id)
	{
		super();
		this.id = id;
		this.department = new Department();
	}

	public void setDepartmentName(String _department)
	{
		// super.setDepartmentName(department);
		department_name = _department;
	}

	public String getDepartmentName()
	{
		// return super.getDepartmentName();
		return department_name;

	}

	public boolean isActive()
	{
		return active;
	}

	public void setOtherAttributes(Map<String, String> otherAttributes)
	{
		this.attributes = otherAttributes;
	}

	public Map<String, String> getOtherAttributes()
	{
		return attributes;
	}

	public void takeData(User personResponse)
	{
		if (personResponse.department != null)
		{
			takeData(personResponse.id, personResponse.firstName, personResponse.middleName, personResponse.lastName,
					personResponse.position, personResponse.department.getName());
		} else
		{
			takeData(personResponse.id, personResponse.firstName, personResponse.middleName, personResponse.lastName,
					personResponse.position, personResponse.department_name);
		}

	}

	public void takeData(String id, String firstName, String secondName, String surName, String position,
			String department)
	{
		this.id = id;
		this.firstName = firstName;
		this.middleName = secondName;
		this.lastName = surName;
		this.position = position;

		if (this.department != null)
			this.department.setName(department);
		else
			this.department_name = department;

	}

	public String getOrgName()
	{
		if (lastName != null && firstName != null && middleName != null && lastName.trim().length() > 0
				&& firstName.trim().length() > 0 && middleName.trim().length() > 0)
		{
			return lastName + " " + firstName.trim().substring(1, 2).toUpperCase() + "."
					+ middleName.trim().substring(1, 2).toUpperCase() + ".";
		} else
		{
			return login;
		}
	}

	private String correct_locale(String locale)
	{
		if (locale == "ru")
			locale = "Ru";
		if (locale == "en")
			locale = "En";
		return locale;
	}

	public String getRepresentatiom()
	{
		String name = "";
		if (lastName != null && firstName != null && lastName.length() > 0 && firstName.length() > 0)
		{
			if (middleName != null && middleName.length() > 0)
			{
				name = lastName + " " + firstName.toUpperCase().charAt(0) + "." + middleName.toUpperCase().charAt(0)
						+ ".";
			} else
			{
				name = lastName + " " + firstName.toUpperCase().charAt(0) + ".";
			}
		} else if (position != null && position.length() > 0)
		{
			name = position;
		} else
		{
			name = login;
		}
		return name;
	}

	public boolean getActive()
	{
		return active;
	} // end getActive()

	public void setActive(boolean active)
	{
		this.active = active;
	} // end setActive()

	public String getDomainName()
	{
		return login;
	} // end getDomainName()

	public void setDomainName(String domainName)
	{
		this.login = domainName;
	} // end setDomainName()

	public String getDepartmentId()
	{
		return department.getId();
	} // end getDepartmentId()

	public void setDepartmentId(String departmentId)
	{
		if (department == null)
			department = new Department();

		this.department.setId(departmentId);
	} // end setDepartmentId()

	public User(EntityType blObject, String locale)
	{
		setId(blObject.getUid());

		if (blObject.getAttributes() != null)
		{
			for (AttributeType a : blObject.getAttributes().getAttributeList())
			{
				attributes.put(a.getName(), a.getValue());
				if (a.getName().equalsIgnoreCase("firstName" + locale))
				{
					setFirstName(a.getValue(), locale);
				} else if (a.getName().equalsIgnoreCase("secondName" + locale))
				{
					setMiddleName(a.getValue(), locale);
				} else if (a.getName().equalsIgnoreCase("surname" + locale))
				{
					setLastName(a.getValue(), locale);
				} else if (a.getName().equals("domainName"))
				{
					setLogin(a.getValue());
				} else if (a.getName().equals("email"))
				{
					setEmail(a.getValue());
				} else if (a.getName().equalsIgnoreCase("post" + locale))
				{
					setPosition(a.getValue(), locale);
				} else if (a.getName().equalsIgnoreCase("pid"))
				{
					setTabNomer(a.getValue());
				} else if (a.getName().equalsIgnoreCase("phone"))
				{
					setTelephone(a.getValue());
				} else if (a.getName().equalsIgnoreCase("id"))
				{
					setInternalId(a.getValue());
				} else if (a.getName().equalsIgnoreCase("name" + locale))
				{
					setName(a.getValue());
				}
			}
		}
	}

	public String getHint()
	{
		String result = "";
		result = lastName != null ? result += lastName + " " : result;
		result = firstName != null ? result += firstName + " " : result;
		result = middleName != null ? result += middleName + " \n" : result;
		result = position != null ? result += position + " \n" : result;
		result = email != null ? result += email + " \n" : result;
		result = telephone != null ? result += telephone : result;
		return result;
	}

	public void changeLocale(String locale)
	{
		name = null;
		if ("en".equals(locale))
		{
			setFirstName(attributes.get("firstNameEn"), "En");
			setMiddleName(attributes.get("secondNameEn"), locale);
			setLastName(attributes.get("surnameEn"), locale);
			setPosition(attributes.get("postEn"), locale);
			setName(attributes.get("nameEn"));
			if (attributes.get("firstNameEn") == null || "".equals(attributes.get("firstNameEn"))
					|| "null".equals(attributes.get("firstNameEn")))
			{
				setFirstName(attributes.get("firstNameRu"), "Ru");
			}
			if (attributes.get("secondNameEn") == null || "".equals(attributes.get("secondNameEn"))
					|| "null".equals(attributes.get("secondNameEn")))
			{
				setMiddleName(attributes.get("secondNameRu"), locale);
			}
			if (attributes.get("surnameEn") == null || "".equals(attributes.get("surnameEn"))
					|| "null".equals(attributes.get("surnameEn")))
			{
				setLastName(attributes.get("surnameRu"), locale);
			}
			if (attributes.get("postEn") == null || "".equals(attributes.get("postEn"))
					|| "null".equals(attributes.get("postEn")))
			{
				setPosition(attributes.get("postRu"), locale);
			}
			if (attributes.get("nameEn") == null || "".equals(attributes.get("nameEn"))
					|| "null".equals(attributes.get("nameEn")))
			{
				setName(attributes.get("nameRu"));
			}
		} else if ("ru".equals(locale))
		{
			setFirstName(attributes.get("firstNameRu"), "Ru");
			setMiddleName(attributes.get("secondNameRu"), "Ru");
			setLastName(attributes.get("surnameRu"), "Ru");
			setPosition(attributes.get("postRu"), "Ru");
			setName(attributes.get("nameRu"));
		}
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName, String locale)
	{
		locale = correct_locale(locale);

		attributes.put("firstName" + locale, firstName);

		this.firstName = firstName;
	}

	public void setFirstName(String firstName)
	{
		attributes.put("firstNameRu", firstName);

		this.firstName = firstName;
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

	public void _set__Position(String nm)
	{
		byte lang = getLang(nm);
		nm = stripLang(nm);

		if (lang == _RU || lang == _NONE)
			setPosition(nm, "Ru");
		if (lang == _EN)
			setPosition(nm, "En");
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName, String locale)
	{
		locale = correct_locale(locale);

		attributes.put("surname" + locale, lastName);

		this.lastName = lastName;
	}

	public void setLastName(String lastName)
	{
		attributes.put("surnameRu", lastName);

		this.lastName = lastName;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	/*
	 * public String getName() { if (lastName != null && firstName != null &&
	 * lastName.length() > 0 && firstName.length() > 0) { if (middleName != null
	 * && middleName.length() > 0) { name = lastName + " " +
	 * firstName.toUpperCase().charAt(0) + "." +
	 * middleName.toUpperCase().charAt(0) + "."; } else { name = lastName + " "
	 * + firstName.toUpperCase().charAt(0) + "."; } } else if (position != null
	 * && position.length() > 0) { name = position; } else { name = login; }
	 * return name; }
	 */
	public String getName()
	{
		if (name != null)
		{
			return name;
		}
		if (getSurName() != null && getFirstName() != null && getSurName().trim().length() > 0
				&& getFirstName().trim().length() > 0)
		{
			if (getSecondName() != null && getSecondName().trim().length() > 0)
			{
				name = getSurName() + " " + getFirstName().trim().toUpperCase().charAt(0) + "."
						+ getSecondName().trim().toUpperCase().charAt(0) + ".";
			} else
			{
				name = getSurName() + " " + getFirstName().trim().toUpperCase().charAt(0) + ".";
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

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position, String locale)
	{
		locale = correct_locale(locale);

		attributes.put("post" + locale, position);

		this.position = position;
	}

	public void setPosition(String position)
	{
		attributes.put("postRu", position);

		this.position = position;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public void setDepartment(String department_name)
	{
		this.department.setName(department_name, "Ru");
	}

	public long getTabNomer()
	{
		return tabNomer;
	}

	public void setTabNomer(String tabNomer)
	{
		try
		{
			this.tabNomer = new Long(tabNomer);
		} catch (Exception ex)
		{
			this.tabNomer = 0;
		}
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName, String locale)
	{
		locale = correct_locale(locale);

		attributes.put("secondName" + locale, middleName);

		this.middleName = middleName;
	}

	public void setSurName(String lastName)
	{
		attributes.put("lastNameRu", lastName);

		this.lastName = lastName;
	}

	public String getSurName()
	{
		return this.lastName;
	}

	public void setSecondName(String middleName)
	{
		attributes.put("secondNameRu", middleName);

		this.middleName = middleName;
	}

	public String getSecondName()
	{
		return this.middleName;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public String getPhone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getInternalId()
	{
		return internalId;
	}

	public void setInternalId(String internalId)
	{
		this.internalId = internalId;
	}

	public Map<String, String> getAttributes()
	{
		return attributes;
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

}
