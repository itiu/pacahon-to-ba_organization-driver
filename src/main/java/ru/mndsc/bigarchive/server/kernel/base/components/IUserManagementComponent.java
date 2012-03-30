package ru.mndsc.bigarchive.server.base.components;

import java.util.Collection;
import java.util.List;
import ru.mndsc.objects.organization.Department;
import ru.mndsc.objects.organization.User;

/**
 * Компонент для работы с пользователями.
 * 
 * @author KodanevY
 */
public interface IUserManagementComponent {
	/**
	 * Выборка пользователей по первым буквам имени или фамилии.
	 * 
	 * @param input
	 *            список строк для поиска
	 * @param localeName
	 *            имя локали
	 * @return коллекция пользователей
	 * @throws Exception ошибка
	 */
	Collection<User> selectUsersByName(Collection<String> input,
			String localeName) throws Exception;
	
	/**
	 * Выборка пользователей являющихся админами приложения.
	 * 
	 * @param localeName
	 *            имя локали
	 * @return коллекция идэнтификаторов и логинов пользователей
	 * @throws Exception ошибка
	 */
//	List<String> getAllAdmins(String localeName) throws Exception;

	/**
	 * Выборка пользователей по первым буквам должности.
	 * 
	 * @param input
	 *            список строк для поиска
	 * @param localeName
	 *            имя локали
	 * @return коллекция пользователей 
	 * @throws Exception ошибка
	 */
	Collection<User> selectUsersByPost(Collection<String> input,
			String localeName) throws Exception;

	/**
	 * Выборка пользователей по первым буквам названия подразделения.
	 * 
	 * @param input
	 *            список строк для поиска
	 * @param localeName
	 *            имя локали
	 * @return коллекция пользователей 
	 * @throws Exception ошибка
	 */
	Collection<User> selectUsersByDepartmentUids(Collection<String> input,
			String localeName) throws Exception;

	/**
	 * Поиск пользователя по доменному имени.
	 * 
	 * @param login
	 *            доменное имя
	 * @param localeName
	 *            имя локали
	 * @return пользователь
	 * @throws Exception ошибка
	 */
	User selectUserByLogin(String login, String localeName)
			throws Exception;

	/**
	 * Поиск пользователя по UID.
	 * 
	 * @param uid
	 *            уникальный идентификатор пользователя
	 * @param localeName
	 *            имя локали
	 * @return пользователь
	 * @throws Exception ошибка
	 */
	User selectUserByUid(String uid, String localeName) throws Exception;

	/**
	 * Получение UID пользователя по доменному мени.
	 * 
	 * @param login
	 *            доменное имя
	 * @return идентификатор пользователя
	 * @throws Exception ошибка
	 */
	String getUserUidByLogin(String login) throws Exception;

	/**
	 * Получение UID подразделения по UID пользователя.
	 * 
	 * @param uid
	 *            уникальный идентификатор пользователя
	 * @return идентификатор подразделения
	 * @throws Exception ошибка
	 */
	String getDepartmentUidByUserUid(String uid) throws Exception;

	/**
	 * Получение пользователей по их UID-ам.
	 * 
	 * @param input
	 *            список UID-ов пользователей
	 * @param localeName
	 *            локаль
	 * @return коллекция пользователей
	 * @throws Exception ошибка
	 */
	Collection<User> selectUsersByUids(Collection<String> input,
			String localeName) throws Exception;

	/**
	 * Получение всех пользователей.
	 * 
	 * @param localeName
	 *            локаль
	 * @return коллекция пользователей
	 * @throws Exception ошибка
	 */
	Collection<User> selectAllUsers(String localeName) throws Exception;

	/**
	 * Получение дочерних подразделений.
	 * 
	 * @param uid
	 *            уникальный идентификатор подразделения
	 * @param localeName
	 *            имя локали
	 * @return коллекция подразделений
	 * @throws Exception ошибка
	 */
	Collection<Department> getChildDepartments(String uid, String localeName)
			throws Exception;

	/**
	 * Получение списка подразделений, образующих дерево поиска.
	 * 
	 * @param input
	 *            идентификаторы пользователя
	 * @param localeName
	 *            имя локали
	 * @return коллекция подразделений
	 * @throws Exception ошибка
	 */
	Collection<Department> getDepartmentTree(Collection<String> input,
			String localeName) throws Exception;

	/**
	 * Получение коневого подразделения.
	 * 
	 * @param localeName
	 *            имя локали
	 * @return подразделение
	 * @throws Exception ошибка
	 */
	Department getRootDepartment(String localeName) throws Exception;

	/**
	 * Получение получение подразделения по его UID.
	 * 
	 * @param uid
	 *            уникальный идентификатор подразделения
	 * @param localeName
	 *            имя локали
	 * @return подразделение
	 * @throws Exception ошибка
	 */
	Department getDepartmentByUid(String uid, String localeName)
			throws Exception;

	/**
	 * Получение списка подразделений по их UID-ам.
	 * 
	 * @param input
	 *            список UID-ов подразделений
	 * @param localeName
	 *            имя локали
	 * @return колекция подразделений
	 * @throws Exception ошибка
	 */
	Collection<Department> getDepartmentsByUids(Collection<String> input,
			String localeName) throws Exception;

	/**
	 * Получение списка подразделений по ключевым словам.
	 * 
	 * @param input
	 *            список ключевых слов
	 * @param localeName
	 *            имя локали
	 * @return колекция подразделений
	 * @throws Exception ошибка
	 */
	Collection<Department> getDepartmentsByName(Collection<String> input,
			String localeName) throws Exception;

	/**
	 * Получение версии.
	 * 
	 * @return версия в формате groupId:artifactId:version
	 * @throws Exception ошибка
	 */
	String getVersion() throws Exception;

	/**
	 * Получение версий используемых сервисов.
	 * 
	 * @return список версий в формате groupId:artifactId:version
	 * @throws Exception ошибка
	 */
	Collection<String> getDependVersions() throws Exception;

	/**
	 * Получение пути от указанного подразделения к корневому.
	 * 
	 * @param uid
	 *            уникальный идентификатор подразделения
	 * @param localeName
	 *            имя локали
	 * @return колекция подразделений
	 * @throws Exception ошибка
	 */
	Collection<Department> getDepartmentTreePath(String uid, String localeName)
			throws Exception;

	/**
	 * Проверить является ли данный пользователь админом.
	 * 
	 * @param login
	 *            логин пользователя
	 * @return true если пользователь обладает правом администрирования
	 * @throws AuthorizationException
	 *             ошибка авторизации
	 */
//	boolean isAdmin(SessionTicket ticket);

//	public void setAuthorizationManagerPool(AuthorizationManagerPool authorizationManagerPool);

} // end IUserManagementComponent
