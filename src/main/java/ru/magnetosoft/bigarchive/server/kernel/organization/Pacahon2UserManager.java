package ru.magnetosoft.bigarchive.server.kernel.organization;

import java.io.InputStream;
import java.util.Collection;

import org.gost19.pacahon.ba_organization_driver.BaOrganizationDriver;

//import ru.magnetosoft.bigarchive.authorization.AuthorizationManagerPool;
import ru.magnetosoft.bigarchive.server.base.components.ICacheComponent;
import ru.magnetosoft.bigarchive.server.base.components.IUserManagementComponent;
import ru.magnetosoft.bigarchive.server.base.composer.ComponentInitializationException;
import ru.magnetosoft.bigarchive.server.base.exceptions.UserException;
import ru.magnetosoft.bigarchive.server.base.spi.IInitializable;
import ru.magnetosoft.bigarchive.server.kernel.cache.EhcacheComponentImpl;
import ru.magnetosoft.bigarchive.server.kernel.organization.cache.IOrganizationCache;
import ru.magnetosoft.bigarchive.server.kernel.organization.cache.OrganizationCache;
//import ru.magnetosoft.magnet.subsystem.logging.ILogger;
//import ru.magnetosoft.magnet.subsystem.logging.LogManager;
import ru.magnetosoft.objects.organization.Department;
import ru.magnetosoft.objects.organization.User;

/**
 * Менеджер пользователей.
 * 
 * @author BushenevV
 */
public class Pacahon2UserManager implements IUserManagementComponent, IInitializable
{
	private BaOrganizationDriver org_driver;

	private static final String CACHE_CONFIGURATION_NAME = "ba-server-organization-cache";

	private IOrganizationCache cache = null;
	private ICacheComponent cacheComponent;
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
	public Department getDepartmentByUid(String uid, String localeName) throws UserException
	{
		try
		{
			return org_driver.getDepartmentByUid(uid, localeName, "NewUserManager.getDepartmentByUid");
		} catch (Exception ex)
		{
			throw new UserException("Cannot get department", ex);
		}
	} // end getDepartmentByUid()

	/**
	 * {@inheritDoc} @@@
	 */
	public String getDepartmentUidByUserUid(String uid) throws UserException
	{
		try
		{
			return org_driver.getDepartmentUidByUserUid(uid, "NewUserManager.getDepartmentUidByUserUid");

		} catch (Exception ex)
		{
			throw new UserException("Cannot get root department", ex);
		}

	} // end getDepartmentUidByUserUid()

	/**
	 * {@inheritDoc} @@@
	 */
	public String getUserUidByLogin(String login) throws UserException
	{
		String result = cache.getUserUidByLogin(login);
		if (null != result)
		{
			return result;
		}
		try
		{
			result = org_driver.getUserUidByLoginInternal(login, "NewUserManager.getUserUidByLogin");
		} catch (Exception ex)
		{
			throw new UserException("Cannot getUserUidByLogin", ex);
		}
		
		if (result != null)
		cache.putUserUidByLogin(result, login);
		
		return result;
	}

	/**
	 * Получение уникального идентификатора пользователя по имени учетной записи.
	 * 
	 * @param login
	 *            имя учетной записи
	 * @return уникальный идентификатор пользователя
	 * @throws UserException
	 *             в случае ошибки
	 */
	protected String getUserUidByLoginInternal(String login) throws UserException
	{
		try
		{
			return org_driver.getUserUidByLoginInternal(login, "NewUserManager.getUserUidByLoginInternal");
		} catch (Exception ex)
		{
			throw new UserException("Cannot get user", ex);
		}
	} // end getUserUidByLogin()

	/**
	 * {@inheritDoc}
	 */
	public void initialize(InputStream configurationData) throws ComponentInitializationException
	{
		cacheComponent = new EhcacheComponentImpl();		
		cache = new OrganizationCache(cacheComponent.getCache(CACHE_CONFIGURATION_NAME));
	} // end initialize()

	/**
	 * {@inheritDoc} @@@
	 */
	public User selectUserByUid(String uid, String localeName) throws UserException
	{
		User result = cache.selectUserByUid(uid, localeName);
		if (null != result)
		{
			return result;
		}
		result = selectUserByUidInternal(uid, localeName);
		
		if (result != null)
			cache.putUserByUid(result, uid, localeName);
		
		return result;
	}

	/**
	 * Найти пользователя по уникальному идентификатору.
	 * 
	 * @param uid
	 *            уникальный идентификатор
	 * @param localeName
	 *            имя локали
	 * @return пользователь
	 * @throws UserException
	 *             в случае ошибки
	 */
	protected User selectUserByUidInternal(String uid, String localeName) throws UserException
	{
		try
		{
			return org_driver.selectUserByUidInternal(uid, localeName, "NewUserManager.selectUserByUidInternal");
		} catch (Exception ex)
		{
			throw new UserException("Cannot get root department", ex);
		}
	} // end selectUserByUid()

//	/**
//	 * {@inheritDoc}
//	 */
//	public void setAuthorizationManagerPool(AuthorizationManagerPool authorizationManagerPool)
//	{
//		return;
//	}

	public void setCacheComponent(ICacheComponent cacheComponent) throws UserException
	{
		this.cacheComponent = cacheComponent;
	}

	public Collection<Department> getDepartmentTreePath(String uid, String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<String> getDependVersions() throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public String getVersion() throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<Department> getDepartmentsByName(Collection<String> input, String localeName)
			throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<Department> getDepartmentsByUids(Collection<String> input, String localeName)
			throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Department getRootDepartment(String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<Department> getDepartmentTree(Collection<String> input, String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<Department> getChildDepartments(String uid, String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<User> selectAllUsers(String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<User> selectUsersByUids(Collection<String> input, String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public User selectUserByLogin(String login, String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<User> selectUsersByDepartmentUids(Collection<String> input, String localeName)
			throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<User> selectUsersByPost(Collection<String> input, String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

	public Collection<User> selectUsersByName(Collection<String> input, String localeName) throws UserException
	{
		throw new UserException("This method is not implemented.");
	}

} // end UserManager

