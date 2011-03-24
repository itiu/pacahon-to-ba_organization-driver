package ru.magnetosoft.objects.organization;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import ru.magnetosoft.objects.ObjectsHelper;

/**
 * 
 * @author SheringaA
 */
public class OrganizationUnit implements Serializable
{
	public enum Type
	{
		User, Department, Position, Unknown;
	}

	public Type type = Type.Unknown;

	private static final long serialVersionUID = 7236181962950149947L;
	private String id;
	private String name;
	private String position;
	private String department;
	private Department parentNode = new Department();
	private String tag;
	private String email;
	private long tabNomer;
	private String telephone;
	private String hint = "";
	private Map<String, String> attributes = new HashMap<String, String>();

	public OrganizationUnit(Type _type)
	{
		type = _type;
	}

	public OrganizationUnit(User user)
	{
		if (user != null)
		{
			id = user.getId();
			name = user.getName();
			position = user.getPosition();
			telephone = user.getTelephone();
			if (user.getDepartment() != null)
			{
				parentNode = user.getDepartment();
				department = user.getDepartment().getName();
			}
			email = user.getEmail();
			tabNomer = user.getTabNomer();

			hint = "";
			hint = user.getLastName() != null ? hint += user.getLastName() + " " : hint;
			hint = user.getFirstName() != null ? hint += user.getFirstName() + " " : hint;
			hint = user.getMiddleName() != null ? hint += user.getMiddleName() + " \n" : hint;
			hint = position != null ? hint += position + " \n" : hint;
			hint = email != null ? hint += email + " \n" : hint;
			hint = telephone != null ? hint += telephone : hint;
			attributes = user.getAttributes();
			attributes.put("department", department);
			attributes.put("name", name);
			attributes.put("post", position);
		} else
		{
			id = "";
			name = "Пользователь";
			position = "Должность";
			telephone = "Телефон";
			department = "Подразделение";
			email = "E-mail";
			tabNomer = 0;
			hint = "";
		}
		tag = ObjectsHelper.userTag;
		type = Type.User;
	}

	public OrganizationUnit(Department department)
	{
		id = department.getId();
		name = department.getName();
		this.department = department.getName();
		parentNode = department;
		tag = ObjectsHelper.deptTag;
		attributes = department.getAttributes();
		attributes.put("id", department.getInternalId());
		attributes.put("name", name);
		type = Type.Department;
	}

	public OrganizationUnit(String id)
	{
		name = id;
		tag = ObjectsHelper.posnTag;
		attributes.put("name", name);
		type = Type.Position;
	}

	public void changeLocale(String locale)
	{
		hint = "";
		if ("en".equals(locale))
		{
			setName(attributes.get("nameEn"));
			if (attributes.get("nameEn") == null || "".equals(attributes.get("nameEn"))
					|| "null".equals(attributes.get("nameEn")))
			{
				setName(attributes.get("nameRu"));
			}
			hint = attributes.get("surnameEn") != null ? hint += attributes.get("surnameEn") + " " : hint;
			hint = attributes.get("firstNameEn") != null ? hint += attributes.get("firstNameEn") + " " : hint;
			hint = attributes.get("secondnameEn") != null ? hint += attributes.get("secondnameEn") + " \n" : hint;
			hint = attributes.get("postEn") != null ? hint += attributes.get("postEn") + " \n" : hint;
		} else if ("ru".equals(locale))
		{
			setName(attributes.get("nameRu"));
			hint = attributes.get("surnameRu") != null ? hint += attributes.get("surnameRu") + " " : hint;
			hint = attributes.get("firstNameRu") != null ? hint += attributes.get("firstNameRu") + " " : hint;
			hint = attributes.get("secondnameRu") != null ? hint += attributes.get("secondnameRu") + " \n" : hint;
			hint = attributes.get("postRu") != null ? hint += attributes.get("postRu") + " \n" : hint;
		}
		hint = email != null ? hint += email + " \n" : hint;
		hint = telephone != null ? hint += telephone : hint;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public String getPosition()
	{
		return position;
	}

	private String correct_locale(String locale)
	{
		if (locale == "ru")
			locale = "Ru";
		if (locale == "en")
			locale = "En";
		return locale;
	}

	public void setPosition(String position, String locale)
	{
		locale = correct_locale(locale);
		attributes.put("post" + locale, position);
		this.position = position;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public long getTabNomer()
	{
		return tabNomer;
	}

	public void setTabNomer(long tabNomer)
	{
		this.tabNomer = tabNomer;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getHint()
	{
		return this.hint;
	}

	public void setHint(String hint)
	{
		this.hint = hint;
	}

	public Map<String, String> getAttributes()
	{
		return attributes;
	}

	public Department getParentNode()
	{
		return parentNode;
	}

	public void setParentNode(Department parentNode)
	{
		this.parentNode = parentNode;
	}
}