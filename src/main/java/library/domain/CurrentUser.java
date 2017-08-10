package library.domain;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User
{

	private User user;

	public CurrentUser(User user)
	{
		super(user.getIdNumber() + "", user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}

	public User getUser()
	{
		return user;
	}

	public int getId()
	{
		return user.getId();
	}

	public Role getRole()
	{
		return user.getRole();
	}

	@Override
	public String toString()
	{
		return "CurrentUser{" +
				"user=" + user +
				"} " + super.toString();
	}
}
