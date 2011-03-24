/**
 * Copyright (c) 2006-2008, Magnetosoft, LLC
 * All rights reserved.
 * 
 * Licensed under the Magnetosoft License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.magnetosoft.ru/LICENSE
 *
 * file: IOrganizationEntity.java
 */

package ru.magnetosoft.objects.organization;

import java.io.Serializable;

/**
 * <p>
 * Объект данных пользователя из "organization".
 * </p>
 * 
 * @author Malyshkin Fedor (fedor.malyshkin@magnetosoft.ru)
 * @since 0.1
 * 
 */
public interface IOrganizationEntity extends Serializable {
	String getId();

	String getName();

	String getOrgName();
}
