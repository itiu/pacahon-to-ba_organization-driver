package ru.magnetosoft.objects.organization;

import java.io.Serializable;
import java.util.Map;

public interface IOrganizationEntity extends Serializable
{
	public String getUid();

	public String getId();

	public String getName();

	public String getOrgName();
	
	public Map<String, String> getAttributes();	
	
	public String getInternalId();
}
