package ru.mndsc.objects.organization;

import java.util.Collection;


/**
 * Компонент для работы с пользователями.
 * 
 * @author KodanevY
 */
public interface IUserManager
{
	/**
	 * Выборка пользователей по первым буквам имени или фамилии.
	 * 
	 * @param input
	 *            список строк для поиска
	 * @param localeName
	 *            имя локали
	 * @return коллекция пользователей @ ошибка
	 */
	Collection<User> selectUsersByName(Collection<String> input, String localeName);

	/**
	 * Выборка пользователей являющихся админами приложения.
	 * 
	 * @param localeName
	 *            имя локали
	 * @return коллекция идэнтификаторов и логинов пользователей @ ошибка
	 */
	// List<String> getAllAdmins(String localeName) ;

	/**
	 * Выборка пользователей по первым буквам должности.
	 * 
	 * @param input
	 *            список строк для поиска
	 * @param localeName
	 *            имя локали
	 * @return коллекция пользователей @ ошибка
	 */
	Collection<User> selectUsersByPost(Collection<String> input, String localeName);

	/**
	 * Выборка пользователей по первым буквам названия подразделения.
	 * 
	 * @param input
	 *            список строк для поиска
	 * @param localeName
	 *            имя локали
	 * @return коллекция пользователей @ ошибка
	 */
	Collection<User> selectUsersByDepartmentUids(Collection<String> input, String localeName);

	/**
	 * Поиск пользователя по доменному имени.
	 * 
	 * @param login
	 *            доменное имя
	 * @param localeName
	 *            имя локали
	 * @return пользователь @ ошибка
	 */
	User selectUserByLogin(String login, String localeName);

	/**
	 * Поиск пользователя по UID.
	 * 
	 * @param uid
	 *            уникальный идентификатор пользователя
	 * @param localeName
	 *            имя локали
	 * @return пользователь @ ошибка
	 */
	User selectUserByUid(String uid, String localeName);

	/**
	 * Получение UID пользователя по доменному мени.
	 * 
	 * @param login
	 *            доменное имя
	 * @return идентификатор пользователя @ ошибка
	 */
	String getUserUidByLogin(String login);

	/**
	 * Получение UID подразделения по UID пользователя.
	 * 
	 * @param uid
	 *            уникальный идентификатор пользователя
	 * @return идентификатор подразделения @ ошибка
	 */
	String getDepartmentUidByUserUid(String uid);

	/**
	 * Получение пользователей по их UID-ам.
	 * 
	 * @param input
	 *            список UID-ов пользователей
	 * @param localeName
	 *            локаль
	 * @return коллекция пользователей @ ошибка
	 */
	Collection<User> selectUsersByUids(Collection<String> input, String localeName);

	/**
	 * Получение всех пользователей.
	 * 
	 * @param localeName
	 *            локаль
	 * @return коллекция пользователей @ ошибка
	 */
	Collection<User> selectAllUsers(String localeName);

	/**
	 * Получение дочерних подразделений.
	 * 
	 * @param uid
	 *            уникальный идентификатор подразделения
	 * @param localeName
	 *            имя локали
	 * @return коллекция подразделений @ ошибка
	 */
	Collection<Department> getChildDepartments(String uid, String localeName);

	/**
	 * Получение списка подразделений, образующих дерево поиска.
	 * 
	 * @param input
	 *            идентификаторы пользователя
	 * @param localeName
	 *            имя локали
	 * @return коллекция подразделений @ ошибка
	 */
	Collection<Department> getDepartmentTree(Collection<String> input, String localeName);

	/**
	 * Получение коневого подразделения.
	 * 
	 * @param localeName
	 *            имя локали
	 * @return подразделение @ ошибка
	 */
	Department getRootDepartment(String localeName);

	/**
	 * Получение получение подразделения по его UID.
	 * 
	 * @param uid
	 *            уникальный идентификатор подразделения
	 * @param localeName
	 *            имя локали
	 * @return подразделение @ ошибка
	 * @throws UserException 
	 */
	Department getDepartmentByUid(String uid, String localeName);

	/**
	 * Получение списка подразделений по их UID-ам.
	 * 
	 * @param input
	 *            список UID-ов подразделений
	 * @param localeName
	 *            имя локали
	 * @return колекция подразделений @ ошибка
	 */
	Collection<Department> getDepartmentsByUids(Collection<String> input, String localeName);

	/**
	 * Получение списка подразделений по ключевым словам.
	 * 
	 * @param input
	 *            список ключевых слов
	 * @param localeName
	 *            имя локали
	 * @return колекция подразделений @ ошибка
	 */
	Collection<Department> getDepartmentsByName(Collection<String> input, String localeName);

	/**
	 * Получение версии.
	 * 
	 * @return версия в формате groupId:artifactId:version @ ошибка
	 */
	String getVersion();

	/**
	 * Получение версий используемых сервисов.
	 * 
	 * @return список версий в формате groupId:artifactId:version @ ошибка
	 */
	Collection<String> getDependVersions();

	/**
	 * Получение пути от указанного подразделения к корневому.
	 * 
	 * @param uid
	 *            уникальный идентификатор подразделения
	 * @param localeName
	 *            имя локали
	 * @return колекция подразделений @ ошибка
	 */
	Collection<Department> getDepartmentTreePath(String uid, String localeName);

	/**
	 * Проверить является ли данный пользователь админом.
	 * 
	 * @param login
	 *            логин пользователя
	 * @return true если пользователь обладает правом администрирования
	 * @throws AuthorizationException
	 *             ошибка авторизации
	 */
	// boolean isAdmin(SessionTicket ticket);

	// public void setAuthorizationManagerPool(AuthorizationManagerPool
	// authorizationManagerPool);

} // end IUserManager
