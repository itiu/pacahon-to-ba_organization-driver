package ru.magnetosoft.objects.organization;

import java.io.Serializable;

public interface IOrganizationEntity extends Serializable
{
	String getUid();

	String getId();

	String getName();

	String getOrgName();
}
