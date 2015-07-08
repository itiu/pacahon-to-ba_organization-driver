package ru.mndsc.objects.organization;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import ru.mndsc.bigarch.wsclient.bl.organizationservice.AttributeType;
import ru.mndsc.bigarch.wsclient.bl.organizationservice.EntityType;

/**
 * 
 * @author SheringaA
 */
public class Department implements IOrganizationEntity, Serializable
{
	public static final byte _NONE = 0;
	public static final byte _RU = 1;
	public static final byte _EN = 2;

	public static final byte _ORGANIZATION = 0;
	public static final byte _GROUP = 1;
	public static final byte _DEPARTMENT = 2;

	public byte type = _DEPARTMENT;

	public String uid;
	public String unit;

	private static final long serialVersionUID = 1;
	private String name;
	private String id;
	private String secondId;
	private Map<String, String> attributes = new HashMap<String, String>();

	private boolean chosen;
	private String previousId;
	private String nameEn;
	private String headId;

	private String organizationId;

	public boolean doNotSyncronize;
	public boolean isActive;

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

	public String getUid()
	{
		return uid;
	}

	public String getOrgName()
	{
		return name;
	}

	public String getNameEn()
	{
		return nameEn;
	} // end getName_en()

	public String getHeadId()
	{
		return headId;
	}

	public void setHeadId(String headId)
	{
		this.headId = headId;
	}

	public void setNameEn(String nameEn)
	{
		this.nameEn = nameEn;
	} // end setName_en()

	public String getPreviousId()
	{
		return previousId;
	} // end getPreviousId()

	public void setPreviousId(String previousId)
	{
		this.previousId = previousId;
	} // end setPreviousId()

	public void setParentDepartmentId(String previousId)
	{
		this.previousId = previousId;
	} // end setPreviousId()

	public boolean isChosen()
	{
		return chosen;
	}

	public void setChosen(boolean chosen)
	{
		this.chosen = chosen;
	}

	public Department()
	{
	}

	public Department(EntityType blObject, String locale)
	{
		setId(blObject.getUid());
		if (blObject.getAttributes() != null)
		{
			for (AttributeType a : blObject.getAttributes().getAttributeList())
			{
				attributes.put(a.getName(), a.getValue());
				if (a.getName().equalsIgnoreCase("name" + locale))
				{
					setName(a.getValue(), locale);
				} else if (a.getName().equalsIgnoreCase("id"))
				{
					setInternalId(a.getValue());
				} else if (a.getName().equalsIgnoreCase("headId"))
				{
					setHeadId(a.getValue());
				}
			}
		}
	}

	public void changeLocale(String locale)
	{
		if ("en".equals(locale))
		{
			setName(attributes.get("nameEn"), "En");
			if (attributes.get("nameEn") == null || "".equals(attributes.get("nameEn"))
					|| "null".equals(attributes.get("nameEn")))
			{
				setName(attributes.get("nameRu"), "Ru");
			}
		} else if ("ru".equals(locale))
		{
			setName(attributes.get("nameRu"), "Ru");
		}
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName(String locale)
	{
		locale = correct_locale(locale);
		name = attributes.get("name" + locale);

		return name;
	}

	public String getName()
	{
		return name;
	}

	private String correct_locale(String locale)
	{
		if (locale == "ru")
			locale = "Ru";
		if (locale == "en")
			locale = "En";
		return locale;
	}

	public void setName(String name, String locale)
	{
		locale = correct_locale(locale);
		attributes.put("name" + locale, name);
		this.name = name;
	}

	public void setName(String name)
	{
		attributes.put("nameRu", name);
		this.name = name;
	}

	public void set__Name(String nm)
	{
		byte lang = getLang(nm);
		nm = stripLang(nm);

		if (lang == _RU || lang == _NONE)
			setName(nm, "Ru");
		if (lang == _EN)
			setName(nm, "En");
	}

	public String getInternalId()
	{
		return secondId;
	}

	public String getExtId()
	{
		return secondId;
	}

	public void setInternalId(String internalId)
	{
		this.secondId = internalId;
	}

	public String getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(String organizationId)
	{
		this.organizationId = organizationId;
	}

	public Map<String, String> getAttributes()
	{
		return attributes;
	}
	
	public String toString ()
	{
		return uid; 
	}
}
