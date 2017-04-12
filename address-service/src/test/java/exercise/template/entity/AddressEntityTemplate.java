package exercise.template.entity;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import exercise.entity.AddressEntity;
import exercise.entity.CepDetails;

public class AddressEntityTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(AddressEntity.class).addTemplate("default", new Rule(){{
			add("id", 0L);
			add("cep", null);
			add("number", 0L);
			add("complement", "");
		}});

		Fixture.of(AddressEntity.class).addTemplate("address-paulista", new Rule(){{
			add("id", 1L);
			add("cep", one(CepDetails.class, "paulista"));
			add("number", 287L);
			add("complement", "cj. 16");
		}});
		
		Fixture.of(AddressEntity.class).addTemplate("address-candido-de-abreu", new Rule(){{
			add("id", 2L);
			add("cep", one(CepDetails.class, "candido-de-abreu"));
			add("number", 513L);
			add("complement", "cj. 65");
		}});
		
		Fixture.of(AddressEntity.class).addTemplate("address-maracana", new Rule(){{
			add("id", 3L);
			add("cep", one(CepDetails.class, "estadio-maracana"));
			add("number", 1000L);
			add("complement", null);
		}});
	}

}
