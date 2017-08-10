package library.domain.helper;


public enum Category
{
	BOOK("Book"), Magazine("Magazine"), Thesis("Thesis");

	private final String text;

	Category(final String text)
	{
		this.text = text;
	}

	// Index starts at 1
	public static Category fromInt(int i)
	{
		for (Category b : Category.values())
		{
			if (b.ordinal() == i - 1)
			{
				return b;
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return text;
	}
}
