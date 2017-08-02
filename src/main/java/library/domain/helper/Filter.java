package library.domain.helper;

/**
 * Created by CudoXY on 7/23/2017.
 */
public class Filter
{
	public enum CategoryFilter
	{
		BOOK("Book"), MAGAZINE("Magazine"), THESIS("Thesis");

		private final String text;

		CategoryFilter(final String text)
		{
			this.text = text;
		}

		public static CategoryFilter fromInt(int i)
		{
			for (CategoryFilter b : CategoryFilter.values())
			{
				if (b.ordinal() == i)
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

	private CategoryFilter categoryFilter;
	private SearchFilter searchFilter;
	private String query;

	public CategoryFilter getCategoryFilter()
	{
		return categoryFilter;
	}

	public void setCategoryFilter(CategoryFilter categoryFilter)
	{
		this.categoryFilter = categoryFilter;
	}

	public SearchFilter getSearchFilter()
	{
		return searchFilter;
	}

	public void setSearchFilter(SearchFilter searchFilter)
	{
		this.searchFilter = searchFilter;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}
}
