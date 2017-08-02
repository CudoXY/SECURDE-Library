package library.domain.helper;


public enum SearchFilter
{
	TITLE("Title"), AUTHOR("Author"), PUBLISHER("Publisher");

	private final String text;

	SearchFilter(final String text)
	{
		this.text = text;
	}

	// Index starts at 1
	public static SearchFilter fromInt(int i)
	{
		for (SearchFilter b : SearchFilter.values())
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
