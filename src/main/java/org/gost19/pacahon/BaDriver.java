package org.gost19.pacahon;

import org.gost19.pacahon.client.PacahonClient;
import org.gost19.pacahon.client.predicates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BaDriver
{
	public static PacahonClient pacahon_client = null;
	public String ticket;
	public long start_time_ticket;
	public int lifetime_ticket = 3600 * 1000;

	public void initailize(String endpoint_pretending_organization) throws Exception
	{
		if (pacahon_client == null)
		{
			pacahon_client = new PacahonClient(endpoint_pretending_organization);
			ticket = pacahon_client.get_ticket("user", "9cXsvbvu8=", "BaOrganizationDriver.constructor");
			start_time_ticket = System.currentTimeMillis();
		}
		else
		{
			ticket = pacahon_client.get_ticket("user", "9cXsvbvu8=", "BaOrganizationDriver.constructor");
			start_time_ticket = System.currentTimeMillis();			
		}

	}

	public void recheck_ticket()
	{
		try
		{
			long now = System.currentTimeMillis();
			if (now - start_time_ticket > lifetime_ticket)
			{
				ticket = pacahon_client.get_ticket("user", "9cXsvbvu8=", "NewUserManager.recheck_ticket");
				start_time_ticket = System.currentTimeMillis();
			}
		} catch (Exception ex)
		{
			throw new IllegalStateException(ex);
		}
	}

}
