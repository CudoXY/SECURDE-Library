package library.domain.validator;

import library.domain.Material;
import library.services.material.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FormSaveMaterialValidator implements Validator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(FormSaveMaterialValidator.class);
	private final MaterialService materialService;

	@Autowired
	public FormSaveMaterialValidator(MaterialService materialService)
	{
		this.materialService = materialService;
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(Material.class);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		LOGGER.debug("Validating {}", target);
		Material material = (Material) target;
		validateId(errors, material);
	}

	private void validateId(Errors errors, Material form)
	{
		if (materialService.getMaterialById(form.getId()) != null)
		{
			errors.reject("id.exists", "Material with the same location already exists");
		}
	}
}
