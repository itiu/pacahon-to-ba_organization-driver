package org.gost19.pacahon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.gost19.pacahon.client.PacahonClient;

public class BaDriver
{
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static PacahonClient pacahon_client = null;
	public String ticket;
	public long start_time_ticket;
	public int lifetime_ticket = 3600 * 1000;

	public boolean removeSubject(String subject_id, String from) throws Exception
	{
		return pacahon_client.remove_subject(ticket, subject_id, from);
	}

	public void initailize(String endpoint_pretending_organization) throws Exception
	{
		if (pacahon_client == null)
		{
			pacahon_client = new PacahonClient(endpoint_pretending_organization);
			ticket = pacahon_client.get_ticket("user", "9cXsvbvu8=", "BaOrganizationDriver.constructor");
			start_time_ticket = System.currentTimeMillis();
		} else
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

	/**
	 * Transforms String to Date.
	 * 
	 * @param date
	 * @param time
	 * @return XMLGregorianCalendar
	 */
	public static Date string2date(String date)
	{
		date = date.replace('T', ' ');
		date = date.substring(0, date.indexOf('+'));
		GregorianCalendar gcal = new GregorianCalendar();
		try
		{
			if (date.length() < 22)
				gcal.setTime(sdf2.parse(date));
			else
				gcal.setTime(sdf1.parse(date));
			return gcal.getTime();
		} catch (Exception ex)
		{
			ex.hashCode();
		}
		return null;
	}
}
