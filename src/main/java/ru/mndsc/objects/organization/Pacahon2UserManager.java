package ru.mndsc.objects.organization;

import java.util.Collection;

import org.gost19.pacahon.ba_organization_driver.BaOrganizationDriver;


/**
 * Менеджер пользователей.
 * 
 * @author BushenevV
 */
public class Pacahon2UserManager implements IUserManager
{
	private BaOrganizationDriver org_driver;

//	private static final String CACHE_CONFIGURATION_NAME = "ba-server-organization-cache";

//	private IOrganizationCache cache = null;
//	private ICacheComponent cacheComponent;
//	private final ILogger log;

	/**
	 * Конструктор.
	 */
	public Pacahon2UserManager(String endpoint_pretending_organization)
	{
//		log = LogManager.createLogger(NewUserManager.class);
		try
		{
			org_driver = new BaOrganizationDriver(endpoint_pretending_organization);
		} catch (Exception ex)
		{
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * {@inheritDoc} @@@
	 */
	public Department getDepartmentByUid(String uid, String localeName)
	{
		try
		{
			return org_driver.getDepartmentByUid(uid, localeName, "NewUserManager.getDepartmentByUid");
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

	/**
	 * {@inheritDoc} @@@
	 */
	public String getDepartmentUidByUserUid(String uid)
	{
		try
		{
			return org_driver.getDepartmentUidByUserUid(uid, "NewUserManager.getDepartmentUidByUserUid");

		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get root department", ex);
		}

	} // end getDepartmentUidByUserUid()

	/**
	 * {@inheritDoc} @@@
	 */
	public String getUserUidByLogin(String login)
	{
		try
		{
			return org_driver.getUserUidByLoginInternal(login, "NewUserManager.getUserUidByLogin");
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot getUserUidByLogin", ex);
		}
	}

	/**
	 * Получение уникального идентификатора пользователя по имени учетной записи.
	 * 
	 * @param login
	 *            имя учетной записи
	 * @return уникальный идентификатор пользователя
	 * @throws Exception
	 *             в случае ошибки
	 */
	protected String getUserUidByLoginInternal(String login)
	{
		try
		{
			return org_driver.getUserUidByLoginInternal(login, "NewUserManager.getUserUidByLoginInternal");
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get user", ex);
		}
	} // end getUserUidByLogin()

	/**
	 * {@inheritDoc}
	 */
//	public void initialize(InputStream configurationData) throws ComponentInitializationException
//	{
//		cacheComponent = new EhcacheComponentImpl();		
//	} // end initialize()

	/**
	 * {@inheritDoc} @@@
	 */
	public User selectUserByUid(String uid, String localeName)
	{
		return selectUserByUidInternal(uid, localeName);
	}

	/**
	 * Найти пользователя по уникальному идентификатору.
	 * 
	 * @param uid
	 *            уникальный идентификатор
	 * @param localeName
	 *            имя локали
	 * @return пользователь
	 * @throws Exception
	 *             в случае ошибки
	 */
	protected User selectUserByUidInternal(String uid, String localeName)
	{
		try
		{
			return org_driver.selectUserByUidInternal(uid, localeName, "NewUserManager.selectUserByUidInternal");
		} catch (Exception ex)
		{
			throw new IllegalStateException("Cannot get root department", ex);
		}
	}

	public Collection<Department> getDepartmentTreePath(String uid, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<String> getDependVersions()
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public String getVersion()
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<Department> getDepartmentsByName(Collection<String> input, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<Department> getDepartmentsByUids(Collection<String> input, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Department getRootDepartment(String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<Department> getDepartmentTree(Collection<String> input, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<Department> getChildDepartments(String uid, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<User> selectAllUsers(String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<User> selectUsersByUids(Collection<String> input, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public User selectUserByLogin(String login, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<User> selectUsersByDepartmentUids(Collection<String> input, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<User> selectUsersByPost(Collection<String> input, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

	public Collection<User> selectUsersByName(Collection<String> input, String localeName)
	{
		throw new IllegalStateException("This method is not implemented.");
	}

} 

