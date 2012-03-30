package ru.mndsc.bigarchive.server.kernel.organization;

import java.io.InputStream;
import java.util.Collection;

import org.gost19.pacahon.ba_organization_driver.BaOrganizationDriver;

//import ru..mndsc.bigarchive.authorization.AuthorizationManagerPool;
import ru.mndsc.bigarchive.server.base.components.IUserManagementComponent;
import ru.mndsc.objects.organization.Department;
import ru.mndsc.objects.organization.User;

/**
 * Менеджер пользователей.
 * 
 * @author BushenevV
 */
public class Pacahon2UserManager implements IUserManagementComponent
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
	public Department getDepartmentByUid(String uid, String localeName) throws Exception
	{
		try
		{
			return org_driver.getDepartmentByUid(uid, localeName, "NewUserManager.getDepartmentByUid");
		} catch (Exception ex)
		{
			throw new Exception("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

	/**
	 * {@inheritDoc} @@@
	 */
	public String getDepartmentUidByUserUid(String uid) throws Exception
	{
		try
		{
			return org_driver.getDepartmentUidByUserUid(uid, "NewUserManager.getDepartmentUidByUserUid");

		} catch (Exception ex)
		{
			throw new Exception("Cannot get root department", ex);
		}

	} // end getDepartmentUidByUserUid()

	/**
	 * {@inheritDoc} @@@
	 */
	public String getUserUidByLogin(String login) throws Exception
	{
		try
		{
			return org_driver.getUserUidByLoginInternal(login, "NewUserManager.getUserUidByLogin");
		} catch (Exception ex)
		{
			throw new Exception("Cannot getUserUidByLogin", ex);
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
	protected String getUserUidByLoginInternal(String login) throws Exception
	{
		try
		{
			return org_driver.getUserUidByLoginInternal(login, "NewUserManager.getUserUidByLoginInternal");
		} catch (Exception ex)
		{
			throw new Exception("Cannot get user", ex);
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
	public User selectUserByUid(String uid, String localeName) throws Exception
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
	protected User selectUserByUidInternal(String uid, String localeName) throws Exception
	{
		try
		{
			return org_driver.selectUserByUidInternal(uid, localeName, "NewUserManager.selectUserByUidInternal");
		} catch (Exception ex)
		{
			throw new Exception("Cannot get root department", ex);
		}
	} // end selectUserByUid()

//	/**
//	 * {@inheritDoc}
//	 */
//	public void setAuthorizationManagerPool(AuthorizationManagerPool authorizationManagerPool)
//	{
//		return;
//	}

//	public void setCacheComponent(ICacheComponent cacheComponent) throws Exception
//	{
//		this.cacheComponent = cacheComponent;
//	}

	public Collection<Department> getDepartmentTreePath(String uid, String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<String> getDependVersions() throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public String getVersion() throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<Department> getDepartmentsByName(Collection<String> input, String localeName)
			throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<Department> getDepartmentsByUids(Collection<String> input, String localeName)
			throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Department getRootDepartment(String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<Department> getDepartmentTree(Collection<String> input, String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<Department> getChildDepartments(String uid, String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<User> selectAllUsers(String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<User> selectUsersByUids(Collection<String> input, String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public User selectUserByLogin(String login, String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<User> selectUsersByDepartmentUids(Collection<String> input, String localeName)
			throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<User> selectUsersByPost(Collection<String> input, String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

	public Collection<User> selectUsersByName(Collection<String> input, String localeName) throws Exception
	{
		throw new Exception("This method is not implemented.");
	}

} // end UserManager

