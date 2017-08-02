package library.interceptor;

import library.domain.helper.Filter;
import library.domain.helper.SearchFilter;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SearchInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception
	{
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception
	{

		Filter f = new Filter();
		f.setSearchFilter(SearchFilter.fromInt(1));

		if (model != null)
		{
			model.getModelMap().
					addAttribute("filter",
							f);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception
	{

	}

}
